package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BillRecord;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.FeeConfig;
import com.webapps.common.entity.Recommend;
import com.webapps.common.entity.User;
import com.webapps.common.entity.UserWallet;
import com.webapps.common.form.EnrollApprovalRequestForm;
import com.webapps.common.utils.DateUtil;
import com.webapps.common.utils.PropertyUtil;
import com.webapps.mapper.IBillRecordMapper;
import com.webapps.mapper.IEnrollApprovalMapper;
import com.webapps.mapper.IEnrollmentMapper;
import com.webapps.mapper.IFeeConfigMapper;
import com.webapps.mapper.IRecommendMapper;
import com.webapps.mapper.IUserMapper;
import com.webapps.mapper.IUserWalletMapper;
import com.webapps.mapper.IWalletRecordMapper;
import com.webapps.service.IEnrollApprovalService;

import net.sf.json.JSONObject;

@Service
@Transactional
public class EnrollApprovalServiceImpl implements IEnrollApprovalService {
	
	private Logger logger = Logger.getLogger(EnrollApprovalServiceImpl.class);
	
	@Autowired
	private IEnrollApprovalMapper iEnrollApprovalMapper;
	
	@Autowired
	private IEnrollmentMapper iEnrollmentMapper;
	
	@Autowired
	private IWalletRecordMapper iWalletRecordMapper;
	
	@Autowired
	private IUserWalletMapper iUserWalletMapper;
	
	@Autowired
	private IUserMapper iUserMapper;
	
	@Autowired
	private IRecommendMapper iRecommendMapper;
	
	@Autowired
	private IFeeConfigMapper iFeeConfigMapper;
	
	@Autowired
	private IBillRecordMapper iBillRecordMapper;

	@Override
	public Page loadEnrollApprovalList(Page page, EnrollApprovalRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iEnrollApprovalMapper.queryCount(form);
		List<EnrollApproval> result = iEnrollApprovalMapper.queryPage(startRow, endRow, form);
		page.setResultList(result);
		page.setRecords(count);
		return page;
	}

	/**
	 * approvalType：1入职审核，2期满审核
	 */
	@Override
	public ResultDto<EnrollApproval> approvalById(Integer id,Integer state,String remark) throws Exception {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		EnrollApproval ea = iEnrollApprovalMapper.getById(id);
		ea.setState(state);
		ea.setRemark(remark);
		int result = iEnrollApprovalMapper.updateById(id, ea);
		dto.setData(ea);
		if(result == 1){
			dto.setResult("S");
		}else{
			dto.setResult("F");
			dto.setErrorMsg("审核信息更新失败，请稍后再试");
		}
		return dto;
	}

	@Override
	public EnrollApproval getById(Integer id) throws Exception {
		EnrollApproval ea = iEnrollApprovalMapper.getById(id);
		return ea;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class,MyBatisSystemException.class})
	public ResultDto<EnrollApproval> enrollApproval(Integer id, Integer state, 
			String failedReason,BigDecimal reward) throws Exception {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		try {
			EnrollApproval ea = iEnrollApprovalMapper.getById(id);
			if(ea==null){
				dto.setErrorMsg("未找到待审核信息，请刷新页面后再试");
				dto.setResult("F");
				return dto;
			}
			Enrollment enrollment = iEnrollmentMapper.getById(ea.getEnrollmentId());
			if(enrollment==null){
				dto.setErrorMsg("未找到报名信息，请确认后再次审核");
				dto.setResult("F");
				return dto;
			}
			if(enrollment.getState()==20&&ea.getType()==1){
				//入职审核
				if(state==1){
					enrollment.setState(21);
					enrollment.setUpdateTime(new Date());
					enrollment.setReward(reward);
					ea.setState(1);
					ea.setUpdateTime(new Date());
					ea.setReward(reward);
				}else{
					enrollment.setState(22);
					enrollment.setUpdateTime(new Date());
					ea.setState(2);
					ea.setUpdateTime(new Date());
				}
				iEnrollmentMapper.updateById(enrollment.getId(), enrollment);
				iEnrollApprovalMapper.updateById(ea.getId(), ea);
				dto.setResult("S");
				return dto;
			}else{
				dto.setErrorMsg("审核状态不匹配，请刷新页面后再试");
				dto.setResult("F");
				return dto;
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("入职审核异常："+e.getMessage());
			dto.setErrorMsg("入职审核异常");
			dto.setResult("F");
			return dto;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class,MyBatisSystemException.class})
	public ResultDto<EnrollApproval> expireApproval(Integer id, Integer state, String failedReason) throws Exception {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		try {
			EnrollApproval ea = iEnrollApprovalMapper.getById(id);
			if(ea==null){
				dto.setErrorMsg("未找到待审核信息，请刷新页面后再试");
				dto.setResult("F");
				return dto;
			}
			Enrollment enrollment = iEnrollmentMapper.getById(ea.getEnrollmentId());
			if(enrollment==null){
				dto.setErrorMsg("未找到报名信息，请确认后再次审核");
				dto.setResult("F");
				return dto;
			}
			if(enrollment.getState()==30&&ea.getType()==2){
				//期满审核
				if(state==1){
					enrollment.setState(21);
					enrollment.setUpdateTime(new Date());
					ea.setState(1);
					ea.setUpdateTime(new Date());
					//这里还需要将日志记录到t_wallet_record表中
					saveUserWalletAndRecord(enrollment);
					
				}else{
					enrollment.setState(32);
					enrollment.setUpdateTime(new Date());
					enrollment.setFailedReason(failedReason);
					ea.setState(2);
					ea.setUpdateTime(new Date());
					ea.setFailedReason(failedReason);
				}
				iEnrollmentMapper.updateById(enrollment.getId(), enrollment);
				iEnrollApprovalMapper.updateById(ea.getId(), ea);
				dto.setResult("S");
				return dto;
			}else{
				dto.setErrorMsg("审核状态不匹配，请刷新页面后再试");
				dto.setResult("F");
				return dto;
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("期满审核异常："+e.getMessage());
			dto.setErrorMsg("期满审核异常");
			dto.setResult("F");
			return dto;
		}
	}
	
	private ResultDto<String> saveUserWalletAndRecord(Enrollment enrollment)throws Exception{
		ResultDto<String> dto = new ResultDto<String>();
		//1先保存期满者自己的返费
		//先找到注册用户信息
		User user = iUserMapper.getById(enrollment.getUser().getId());
		if(user==null){
			dto.setErrorMsg("审核时查询用户信息失败");
			dto.setResult("F");
			return dto;
		}
		UserWallet uw = iUserWalletMapper.queryByUserId(user.getId());
		if(uw==null||uw.getId()==null){
			uw = new UserWallet();
			uw.setCreateTime(new Date());
			uw.setDataState(1);
			uw.setFee(enrollment.getReward());
			uw.setUserId(user.getId());
			uw.setState(0);
			iUserWalletMapper.insert(uw);
			//保存账单记录
			saveBillRecrod(enrollment, uw);
		}else{
			BigDecimal fee = uw.getFee();
			BigDecimal reward = enrollment.getReward();
			fee = fee.add(reward);
			uw.setFee(fee);
			uw.setUpdateTime(new Date());
			iUserWalletMapper.updateById(uw.getId(), uw);
			saveBillRecrod(enrollment, uw);
		}
		List<Recommend> reList = iRecommendMapper.queryByMobile(user.getTelephone());
		if(CollectionUtils.isEmpty(reList)){
			dto.setResult("S");
			return dto;
		}
		Recommend re = reList.get(0);
		Integer overDays = (Integer) PropertyUtil.getProperty("recommend_over_days");
		//如果用户注册时间超过了推荐超期天数，则不返费给推荐者
		Date registerDate = user.getCreateTime();
		Date recommendDate = re.getCreateTime();
		double days = DateUtil.getDaysBetweenTwoDates(recommendDate, registerDate);
		if(days>overDays){
			dto.setResult("S");
			return dto;
		}
		//如果用户入职后没在规定天数入职成功，也不能返费给推荐者
		Integer entryOverDays = (Integer) PropertyUtil.getProperty("entry_over_days");
		Date entryDate = enrollment.getEntryDate();
		days = DateUtil.getDaysBetweenTwoDates(registerDate, entryDate);
		if(days>entryOverDays){
			dto.setResult("S");
			return dto;
		}
		//如果即满足在推荐有效期注册会员，又满足在入职期内成功入职的，则返费给推荐者
		UserWallet uw1 = iUserWalletMapper.queryByUserId(re.getUser().getId());
		FeeConfig fc = iFeeConfigMapper.getById(3);
		if(uw1==null||uw1.getId()==null){
			uw1 = new UserWallet();
			uw1.setFee(fc.getFee());
			uw1.setCreateTime(new Date());
			uw1.setDataState(1);
			uw1.setState(0);
			uw1.setUserId(re.getUser().getId());
			iUserWalletMapper.insert(uw1);
			saveBillRecrod(enrollment, uw1);
			dto.setResult("S");
			return dto;
		}
		BigDecimal fee1 = uw1.getFee();
		fee1 = fee1.add(fc.getFee());
		uw1.setFee(fee1);
		uw1.setUpdateTime(new Date());
		iUserWalletMapper.updateById(uw1.getId(),uw1);
		saveBillRecrod(enrollment, uw1);
		dto.setResult("S");
		return dto;
	}

	/**
	 * 保存期满审核成功后的返费账单信息
	 * @param enrollment
	 * @param uw
	 */
	private void saveBillRecrod(Enrollment enrollment, UserWallet uw) {
		BillRecord br = new BillRecord();
		br.setFee(enrollment.getReward());
		br.setType(3);
		br.setDataState(1);
		br.setWalletId(uw.getId());
		br.setCreateTime(new Date());
		iBillRecordMapper.insert(br);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class,MyBatisSystemException.class})
	public ResultDto<EnrollApproval> applyEntryApproval(JSONObject params) {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		Integer enrollemntId = params.getInt("enrollemntId");
		String entryDateStr = params.getString("entryDate");
		Integer userId = params.getInt("userId");
		if(StringUtils.isBlank(entryDateStr)){
			dto.setErrorMsg("入职日期未填写");
			dto.setResult("F");
			return dto;
		}
		Date entryDate = DateUtil.parseDateByStr(entryDateStr, "yyyy-MM-dd");
		try {
			Enrollment enrollment = iEnrollmentMapper.getById(enrollemntId);
			if(enrollment!=null){
				if(enrollment.getState()!=1&&enrollment.getState()!=22){
					dto.setErrorMsg("报名信息不是入职待审核状态，不可审核");
					dto.setResult("F");
					return dto;
				}
				enrollment.setState(20);
				enrollment.setUpdateTime(new Date());
				enrollment.setEntryDate(entryDate);
				iEnrollmentMapper.updateById(enrollemntId, enrollment);
				User user = new User();
				user.setId(userId);
				enrollment.setUser(user);
				EnrollApproval ea = saveEnrollApprova(entryDate, enrollment,1);
				dto.setResult("S");
				dto.setData(ea);
				return dto;
			}else{
				dto.setErrorMsg("报名信息无效，请稍后重试");
				dto.setResult("F");
				return dto;
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			dto.setErrorMsg("发起入职审核申请失败，请稍后重试");
			dto.setResult("F");
			return dto;
		}
	}

	private EnrollApproval saveEnrollApprova(Date entryDate, Enrollment enrollment,Integer type) {
		EnrollApproval ea = new EnrollApproval();
		ea.setEnrollmentId(enrollment.getId());
		ea.setDataState(1);
		ea.setEntryDate(entryDate);
		ea.setState(0);
		ea.setType(type);
		User user = new User();
		user.setId(enrollment.getUser().getId());
		ea.setUser(user);
		iEnrollApprovalMapper.insert(ea);
		return ea;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class,MyBatisSystemException.class})
	public ResultDto<EnrollApproval> applyExpireApproval(JSONObject params) {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		Integer enrollmentId = params.getInt("enrollmentId");
		Integer userId = params.getInt("userId");
		try {
			Enrollment enrollment = iEnrollmentMapper.getById(enrollmentId);
			if(enrollment==null){
				dto.setErrorMsg("报名信息无效，请稍后重试");
				dto.setResult("F");
				return dto;
			}
			if(enrollment.getState()!=21&&enrollment.getState()==32){
				dto.setErrorMsg("报名信息不是已入职状态，不可审核");
				dto.setResult("F");
				return dto;
			}
			enrollment.setState(30);
			enrollment.setUpdateTime(new Date());
			User user = new User();
			user.setId(userId);
			enrollment.setUser(user);
			iEnrollmentMapper.updateById(enrollmentId, enrollment);
			EnrollApproval ea = saveEnrollApprova(null, enrollment,2);
			dto.setResult("S");
			dto.setData(ea);
			return dto;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			dto.setErrorMsg("发起期满审核申请失败，请稍后重试");
			dto.setResult("F");
			return dto;
		}
	}

	@Override
	public ResultDto<List<EnrollApproval>> queryByUserIdTypeAndState(Integer userId, Integer type, Integer state) {
		ResultDto<List<EnrollApproval>> dto = new ResultDto<List<EnrollApproval>>();
		try {
			List<EnrollApproval> list = iEnrollApprovalMapper.queryByUserIdTypeAndState(userId, type, state);
			dto.setData(list);
			dto.setResult("S");
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("查询期满审核账单异常");
			return dto;
		}
	}

}
