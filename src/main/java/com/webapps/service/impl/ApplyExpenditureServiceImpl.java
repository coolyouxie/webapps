package com.webapps.service.impl;

import java.util.Date;
import java.util.List;

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
import com.webapps.common.entity.UserWallet;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import com.webapps.mapper.IApplyExpenditureMapper;
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
	public ResultDto<String> approveById(Integer id, Integer state, Integer approverId, String reason) {
		
		return null;
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
				Date createTime = new Date();
				ApplyExpenditure ae = new ApplyExpenditure();
				ae.setCreateTime(createTime);
				ae.setDataState(1);
				ae.setFee(uw.getFee());
				ae.setState(0);
				ae.setWalletId(uw.getId());
				iApplyExpenditureMapper.insert(ae);
				dto.setResult("S");
				return dto;
			}
			uw = iUserWalletMapper.getById(walletId);
			if(uw==null){
				dto.setResult("F");
				dto.setErrorMsg("未找到用户的钱包信息");
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
