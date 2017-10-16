package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.entity.BillRecord;
import com.webapps.common.entity.UserWallet;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import com.webapps.mapper.IApplyExpenditureMapper;
import com.webapps.mapper.IBillRecordMapper;
import com.webapps.mapper.IUserWalletMapper;
import com.webapps.service.IApplyExpenditureService;

@Service
@Transactional
public class ApplyExpenditureServiceImpl implements IApplyExpenditureService {
	
	private static Logger logger = Logger.getLogger(ApplyExpenditureServiceImpl.class);
	
	@Autowired
	private IApplyExpenditureMapper iApplyExpenditureMapper;
	
	@Autowired
	private IUserWalletMapper iUserWalletMapper;
	
	@Autowired
	private IBillRecordMapper iBillRecordMapper;

	@Override
	public Page loadPageList(Page page, ApplyExpenditureRequestForm apply) {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iApplyExpenditureMapper.queryCount(apply);
		List<ApplyExpenditure> list = iApplyExpenditureMapper.queryPage(startRow, endRow, apply);
		page.setRecords(count);
		page.setResultList(list);
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class})
	public ResultDto<String> approveById(Integer id, Integer state, Integer approverId, String reason) {
		ResultDto<String> dto = new ResultDto<String>();
		try {
			ApplyExpenditure ae = iApplyExpenditureMapper.getById(id);
			if(ae==null){
				dto.setErrorMsg("未查询申请提现信息");
				dto.setResult("F");
				return dto;
			}
			UserWallet uw = iUserWalletMapper.getById(ae.getWalletId());
			if(state==1){
				//审核通过后记录账单信息
				this.saveBillRecrod(ae, uw);
			}
			if(state==2){
				//如果审批没有通过则还原钱包金额
				ae.setReason(reason);
				BigDecimal leftFee = uw.getFee();
				if(leftFee==null){
					leftFee = new BigDecimal(0);
				}
				BigDecimal totalFee = leftFee.add(ae.getFee());
				uw.setFee(totalFee);
				uw.setUpdateTime(new Date());
				iUserWalletMapper.updateById(uw.getId(), uw);
			}
			ae.setApproverId(approverId);
			ae.setState(state);
			ae.setUpdateTime(new Date());
			iApplyExpenditureMapper.updateById(ae.getId(), ae);
			dto.setResult("S");
			return dto;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			logger.error("提现审核异常，请稍后重试:"+e.getMessage());
			dto.setErrorMsg("提现审核异常，请稍后重试");
			dto.setResult("F");
			return dto;
		}
	}
	
	/**
	 * 保存期满审核成功后的返费账单信息
	 * @param enrollment
	 * @param uw
	 */
	private void saveBillRecrod(ApplyExpenditure ae, UserWallet uw) {
		BillRecord br = new BillRecord();
		br.setFee(ae.getFee());
		br.setType(-1);
		br.setDataState(1);
		br.setWalletId(uw.getId());
		br.setCreateTime(new Date());
		iBillRecordMapper.insert(br);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class,MyBatisSystemException.class})
	public ResultDto<String> applyExpenditure(Integer userId, Integer walletId) {
		ResultDto<String> dto = new ResultDto<String>();
		UserWallet uw = null;
		try {
			if(walletId==null||walletId==-1){
				uw = iUserWalletMapper.queryByUserId(userId);
				if(uw==null){
					dto.setResult("F");
					dto.setErrorMsg("未找到用户的钱包信息");
					return dto;
				}
				List<ApplyExpenditure> list = iApplyExpenditureMapper.queryByWalletIdAndState(uw.getId(), 0);
				if(CollectionUtils.isNotEmpty(list)){
					dto.setErrorMsg("当前已存在待审核的提现申请，请等待审核完成后再次提交");
					dto.setResult("F");
					return dto;
				}
				Date createTime = new Date();
				ApplyExpenditure ae = new ApplyExpenditure();
				ae.setCreateTime(createTime);
				ae.setDataState(1);
				ae.setFee(uw.getFee());
				ae.setState(0);
				ae.setWalletId(uw.getId());
				iApplyExpenditureMapper.insert(ae);
				uw.setFee(uw.getFee().subtract(ae.getFee()));
				uw.setUpdateTime(createTime);
				iUserWalletMapper.updateById(uw.getId(), uw);
				dto.setResult("S");
				return dto;
			}
			uw = iUserWalletMapper.getById(walletId);
			if(uw==null){
				dto.setResult("F");
				dto.setErrorMsg("未找到用户的钱包信息");
				return dto;
			}
			List<ApplyExpenditure> list = iApplyExpenditureMapper.queryByWalletIdAndState(uw.getId(), 0);
			if(CollectionUtils.isNotEmpty(list)){
				dto.setErrorMsg("当前已存在待审核的提现申请，请等待审核完成后再次提交");
				dto.setResult("F");
				return dto;
			}
			Date createTime = new Date();
			ApplyExpenditure ae = new ApplyExpenditure();
			ae.setCreateTime(createTime);
			ae.setDataState(1);
			ae.setFee(uw.getFee());
			ae.setState(0);
			ae.setWalletId(uw.getId());
			iApplyExpenditureMapper.insert(ae);
			uw.setFee(uw.getFee().subtract(ae.getFee()));
			//提交审核
			uw.setState(1);
			uw.setUpdateTime(createTime);
			iUserWalletMapper.updateById(uw.getId(), uw);
			dto.setResult("S");
			return dto;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			logger.error("申请提现异常，请稍后重试");
			dto.setErrorMsg("");
			dto.setResult("F");
			return dto;
		}
	}

}
