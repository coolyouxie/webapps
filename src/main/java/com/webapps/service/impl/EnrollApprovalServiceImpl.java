package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.webapps.common.entity.*;
import com.webapps.mapper.*;
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
import com.webapps.common.form.EnrollApprovalRequestForm;
import com.webapps.common.utils.DateUtil;
import com.webapps.common.utils.PropertyUtil;
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
	private IUserWalletMapper iUserWalletMapper;
	
	@Autowired
	private IUserMapper iUserMapper;
	
	@Autowired
	private IRecommendMapper iRecommendMapper;
	
	@Autowired
	private IFeeConfigMapper iFeeConfigMapper;
	
	@Autowired
	private IBillRecordMapper iBillRecordMapper;

	@Autowired
	private IEnrollmentExtraMapper iEnrollmentExtraMapper;

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
	public ResultDto<EnrollApproval> approvalById(Integer id,Integer state,String remark,Integer approverId) throws Exception {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		EnrollApproval ea = iEnrollApprovalMapper.getById(id);
		ea.setState(state);
		ea.setRemark(remark);
		//审核操作人id
		ea.setOperatorId(approverId);
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

	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class,MyBatisSystemException.class})
	public ResultDto<EnrollApproval> enrollApproval(Integer id, Integer state, 
			String failedReason,BigDecimal reward,Integer approverId,Integer cashbackDays) throws Exception {
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
			User user = null;
			if(enrollment.getUser()==null||enrollment.getUser().getId()==null){
				dto.setErrorMsg("审核时部分信息未找到");
				dto.setResult("F");
				return dto;
			}
			user = iUserMapper.getById(enrollment.getUser().getId());
			if(user==null){
				dto.setErrorMsg("审核时获取用户信息失败");
				dto.setResult("F");
				return dto;
			}
			if(enrollment.getState()==20&&ea.getType()==1){
				//入职审核
				if(state==1){
					enrollment.setState(21);
					enrollment.setUpdateTime(new Date());
					enrollment.setReward(reward);
					enrollment.setCashbackDays(cashbackDays);
					ea.setState(1);
					ea.setUpdateTime(new Date());
					ea.setReward(reward);
					ea.setCashbackDays(cashbackDays);
					//更新用户状态到已入职
					user.setCurrentState(2);
					user.setUpdateTime(new Date());
					//先将之前牌入职审核通过和期满审核通过的审核记录状态个性为已离职状态
					List<Enrollment> list = iEnrollmentMapper.queryListByUserIdStateAndId(user.getId(),id);
					cancelOldEnrollment(list);
					iUserMapper.updateById(user.getId(), user);
				}else{
					approvalFailed(failedReason, ea, enrollment);
				}
				ea.setOperatorId(approverId);
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
	public ResultDto<EnrollApproval> expireApproval(Integer id, Integer state, String failedReason,Integer approverId) throws Exception {
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
			User user = null;
			if(enrollment.getUser()==null||enrollment.getUser().getId()==null){
				dto.setErrorMsg("审核时部分信息未找到");
				dto.setResult("F");
				return dto;
			}
			user = iUserMapper.getById(enrollment.getUser().getId());
			if(user==null){
				dto.setErrorMsg("审核时获取用户信息失败");
				dto.setResult("F");
				return dto;
			}
			if(enrollment.getState()==30&&ea.getType()==2){
				//期满审核
				if(state==1){
					enrollment.setState(31);
					enrollment.setUpdateTime(new Date());
					ea.setState(1);
					ea.setUpdateTime(new Date());
					ea.setReward(enrollment.getReward());
					//这里还需要将日志记录到t_wallet_record表中
					saveUserWalletAndRecord(enrollment);
					//更新用户状态到已期满
					user.setUpdateTime(new Date());
					user.setCurrentState(3);
					//先将之前牌入职审核通过和期满审核通过的审核记录状态个性为已离职状态
//					List<Enrollment> list = iEnrollmentMapper.queryListByUserIdAndState(user.getId());
//					if(CollectionUtils.isNotEmpty(list)){
//						for(Enrollment em :list){
//							em.setState(4);
//							em.setUpdateTime(new Date());
//						}
//						iEnrollmentMapper.batchUpdate(list);
//					}
					iUserMapper.updateById(user.getId(), user);
				}else{
					enrollment.setState(32);
					enrollment.setUpdateTime(new Date());
					enrollment.setFailedReason(failedReason);
					ea.setState(2);
					ea.setUpdateTime(new Date());
					ea.setFailedReason(failedReason);
				}
				ea.setOperatorId(approverId);
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
			e.printStackTrace();
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
			saveBillRecrod(enrollment, uw,3,null);
		}else{
			BigDecimal fee = uw.getFee();
			BigDecimal reward = enrollment.getReward();
			fee = fee.add(reward);
			uw.setFee(fee);
			uw.setUpdateTime(new Date());
			iUserWalletMapper.updateById(uw.getId(), uw);
			saveBillRecrod(enrollment, uw,3,null);
		}
		if(user.getIsPayedRecommendFee()==1){
			//如果已经支付过推荐费，则不需要再次支付
			dto.setResult("S");
			return dto;
		}
		List<Recommend> reList = iRecommendMapper.queryByMobile(user.getTelephone());
		if(CollectionUtils.isEmpty(reList)){
			dto.setResult("S");
			return dto;
		}
		Recommend re = reList.get(0);
		Integer overDays = Integer.valueOf((String)PropertyUtil.getProperty("recommend_over_days"));
		//如果用户注册时间超过了推荐超期天数，则不返费给推荐者
		Date registerDate = user.getCreateTime();
		Date recommendDate = re.getCreateTime();
		double days = DateUtil.getDaysBetweenTwoDates(recommendDate, registerDate);
		if(days>overDays){
			//更新用户推荐费为已支付
			return updateUserIsPayedRecommedFee(dto, user);
		}
		//如果用户入职后没在规定天数入职成功，也不能返费给推荐者
		Integer entryOverDays = Integer.valueOf((String)PropertyUtil.getProperty("entry_over_days"));
		Date entryDate = enrollment.getEntryDate();
		days = DateUtil.getDaysBetweenTwoDates(registerDate, entryDate);
		if(days>entryOverDays){
			return updateUserIsPayedRecommedFee(dto, user);
		}
		//如果即满足在推荐有效期注册会员，又满足在入职期内成功入职的，则返费给推荐者
		UserWallet uw1 = iUserWalletMapper.queryByUserId(re.getUser().getId());
		List<FeeConfig> fcList = iFeeConfigMapper.queryAllByDataState(1);
		FeeConfig fc = null;
		for (FeeConfig temp:fcList){
			if(temp.getType()==3){
				fc = temp;
			}
		}
		if(uw1==null||uw1.getId()==null){
			uw1 = new UserWallet();
			uw1.setFee(fc.getFee());
			uw1.setCreateTime(new Date());
			uw1.setDataState(1);
			uw1.setState(0);
			uw1.setUserId(re.getUser().getId());
			iUserWalletMapper.insert(uw1);
			saveBillRecrod(enrollment, uw1,1,fc);
			//更新用户推荐费为已支付
			return updateUserIsPayedRecommedFee(dto, user);
		}
		BigDecimal fee1 = uw1.getFee();
		fee1 = fee1.add(fc.getFee());
		uw1.setFee(fee1);
		uw1.setUpdateTime(new Date());
		iUserWalletMapper.updateById(uw1.getId(),uw1);
		//更新用户推荐费为已支付
		user.setIsPayedRecommendFee(1);
		user.setUpdateTime(new Date());
		iUserMapper.updateById(user.getId(),user);
		saveBillRecrod(enrollment, uw1,1,fc);
		dto.setResult("S");
		return dto;
	}

	/**
	 * 分阶段返费审核时，保存各个流程中的金额信息
	 * @param enrollment
	 * @param fee
	 * @return
	 * @throws Exception
	 */
	private ResultDto<String> saveUserWalletAndRecord(Enrollment enrollment,BigDecimal fee)throws Exception{
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
			uw.setFee(fee);
			uw.setUserId(user.getId());
			uw.setState(0);
			iUserWalletMapper.insert(uw);
			//保存账单记录
			saveBillRecrod(uw,3,null,fee);
		}else{
			BigDecimal fee1 = uw.getFee();
			fee1 = fee1.add(fee);
			uw.setFee(fee1);
			uw.setUpdateTime(new Date());
			iUserWalletMapper.updateById(uw.getId(), uw);
			saveBillRecrod(uw,3,null,fee);
		}
		//2.判断是否需要支付推荐费给推荐用户
		if(user.getIsPayedRecommendFee()==1){
			//如果已经支付过推荐费，则不需要再次支付
			dto.setResult("S");
			return dto;
		}
		List<Recommend> reList = iRecommendMapper.queryByMobile(user.getTelephone());
		if(CollectionUtils.isEmpty(reList)){
			dto.setResult("S");
			return dto;
		}
		Recommend re = reList.get(0);
		Integer overDays = Integer.valueOf((String)PropertyUtil.getProperty("recommend_over_days"));
		//如果用户注册时间超过了推荐超期天数，则不返费给推荐者
		Date registerDate = user.getCreateTime();
		Date recommendDate = re.getCreateTime();
		double days = DateUtil.getDaysBetweenTwoDates(recommendDate, registerDate);
		if(days>overDays){
			//更新用户推荐费为已支付
			return updateUserIsPayedRecommedFee(dto, user);
		}
		//如果用户入职后没在规定天数入职成功，也不能返费给推荐者
		Integer entryOverDays = Integer.valueOf((String)PropertyUtil.getProperty("entry_over_days"));
		Date entryDate = enrollment.getEntryDate();
		days = DateUtil.getDaysBetweenTwoDates(registerDate, entryDate);
		if(days>entryOverDays){
			return updateUserIsPayedRecommedFee(dto, user);
		}
		//如果即满足在推荐有效期注册会员，又满足在入职期内成功入职的，则返费给推荐者
		UserWallet uw1 = iUserWalletMapper.queryByUserId(re.getUser().getId());
		List<FeeConfig> fcList = iFeeConfigMapper.queryAllByDataState(1);
		FeeConfig fc = null;
		for (FeeConfig temp:fcList){
			if(temp.getType()==3){
				fc = temp;
			}
		}
		if(uw1==null||uw1.getId()==null){
			return saveRecommendFee(enrollment, dto, user, re, fc);
		}
		BigDecimal fee1 = uw1.getFee();
		fee1 = fee1.add(fc.getFee());
		uw1.setFee(fee1);
		uw1.setUpdateTime(new Date());
		iUserWalletMapper.updateById(uw1.getId(),uw1);
		//更新用户推荐费为已支付
		user.setIsPayedRecommendFee(1);
		user.setUpdateTime(new Date());
		iUserMapper.updateById(user.getId(),user);
		saveBillRecrod(enrollment, uw1,1,fc);
		dto.setResult("S");
		return dto;
	}

	/**
	 * 保存推荐用户的推荐费
	 * @param enrollment
	 * @param dto
	 * @param user
	 * @param re
	 * @param fc
	 * @return
	 * @throws Exception
	 */
	private ResultDto<String> saveRecommendFee(Enrollment enrollment, ResultDto<String> dto, User user, Recommend re, FeeConfig fc) throws Exception {
		UserWallet uw1;
		uw1 = new UserWallet();
		uw1.setFee(fc.getFee());
		uw1.setCreateTime(new Date());
		uw1.setDataState(1);
		uw1.setState(0);
		uw1.setUserId(re.getUser().getId());
		iUserWalletMapper.insert(uw1);
		saveBillRecrod(enrollment, uw1,1,fc);
		//更新用户推荐费为已支付
		return updateUserIsPayedRecommedFee(dto, user);
	}

	private ResultDto<String> updateUserIsPayedRecommedFee(ResultDto<String> dto, User user) throws Exception {
		//更新用户推荐费为已支付
		user.setIsPayedRecommendFee(1);
		user.setUpdateTime(new Date());
		iUserMapper.updateById(user.getId(),user);
		dto.setResult("S");
		return dto;
	}

	/**
	 * 保存期满审核成功后的返费账单信息
	 * @param enrollment
	 * @param uw
	 * @param type :-1提现支出，1推荐收入，2红包收入，3期满返费
	 */
	private void saveBillRecrod(Enrollment enrollment, UserWallet uw,Integer type,FeeConfig fc) {
		BillRecord br = new BillRecord();
		if(type==3){
			br.setFee(enrollment.getReward());
		}else{
			br.setFee(fc.getFee());
		}
		br.setType(type);
		br.setDataState(1);
		br.setWalletId(uw.getId());
		br.setCreateTime(new Date());
		iBillRecordMapper.insert(br);
	}
	private void saveBillRecrod(UserWallet uw,Integer type,FeeConfig fc,BigDecimal fee) {
		BillRecord br = new BillRecord();
		if(type==3){
			br.setFee(fee);
		}else{
			br.setFee(fc.getFee());
		}
		br.setType(type);
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
				EnrollApproval ea = saveEnrollApprova(entryDate, enrollment,1,null,null);
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

	/**
	 * 保存待审核信息（入职审核与期满审核共用）
	 * @param entryDate
	 * @param enrollment
	 * @param type
	 * @return
	 */
	private EnrollApproval saveEnrollApprova(Date entryDate, Enrollment enrollment,Integer type,BigDecimal reward,Integer cashbackDays) {
		EnrollApproval ea = new EnrollApproval();
		ea.setEnrollmentId(enrollment.getId());
		ea.setDataState(1);
		//入职审核时填写此数据
		ea.setEntryDate(entryDate);
		ea.setState(0);
		ea.setType(type);
		ea.setCashbackDays(cashbackDays);
		ea.setReward(reward);
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
		Date today = new Date();
		try {
			Enrollment enrollment = iEnrollmentMapper.getById(enrollmentId);
			if(enrollment==null){
				dto.setErrorMsg("报名信息无效，请稍后重试");
				dto.setResult("F");
				return dto;
			}
			if(enrollment.getState()!=21&&enrollment.getState()!=32){
				dto.setErrorMsg("报名信息不是已入职状态，不可审核");
				dto.setResult("F");
				return dto;
			}
			//计算当前时间与入职时间的天数差，获取期满审核申请的期满天数
			Integer cashbackDays = -1;
			cashbackDays = new Double(DateUtil.getDaysBetweenTwoDates(enrollment.getEntryDate(),today)).intValue();
			//要判断该用户之前是否存已申请但未审核通过的期满返费申请，如果有且未支付，则逻辑删除
			List<EnrollmentExtra> extraList0 = iEnrollmentExtraMapper.queryListByEnrollmentIdAndState(
					enrollmentId,0);
			List<EnrollmentExtra> extraList = new ArrayList<EnrollmentExtra>();
			if(CollectionUtils.isEmpty(extraList0)){
				dto.setResult("F");
				dto.setErrorMsg("无可用的期满待审核信息，请确认输入的期满天是否有效");
				return dto;
			}

			int maxCashbackDays = -1;
			for(EnrollmentExtra extra:extraList0){
				//把期满天数内的数据取出
				if(extra.getCashbackDays()<cashbackDays){
					extraList.add(extra);
				}
				//找到最后一次期满天数
				if(extra.getCashbackDays()>maxCashbackDays){
					maxCashbackDays = extra.getCashbackDays();
				}
			}
			if(cashbackDays>maxCashbackDays){
				enrollment.setState(30);
			}else {
				enrollment.setState(50);
			}
			User user = new User();
			user.setId(userId);
			enrollment.setUser(user);
			iEnrollmentMapper.updateById(enrollmentId, enrollment);

			//找到该用户之前的期满审核申请记录
			List<EnrollApproval> list = iEnrollApprovalMapper.queryByUserIdEnrollmentIdTypeAndState(userId,enrollmentId,2,0);
			if(CollectionUtils.isNotEmpty(list)){
				//将之前发起的期满申请待审核记录更新为无效状态
				for(EnrollApproval temp :list){
					temp.setUpdateTime(new Date());
					temp.setDataState(0);
					iEnrollApprovalMapper.updateById(temp.getId(),temp);
				}
			}
			//如果期满天数内只有一条记录则以这条记录发起一次期满审核
			Integer cashbackDaysForApproval = 0;
			if(extraList.size()==1){
				EnrollmentExtra extra = extraList.get(0);
				BigDecimal fee = extra.getFee();
				cashbackDaysForApproval = extra.getCashbackDays();
				EnrollApproval ea = new EnrollApproval();
				ea.setEntryDate(enrollment.getEntryDate());
				ea.setType(2);
				ea.setReward(fee);
				ea.setCashbackDays(cashbackDaysForApproval);
				ea.setDataState(1);
				ea.setCreateTime(new Date());
				ea.setEnrollmentId(enrollmentId);
				ea.setState(0);
				ea.setUser(user);
				iEnrollApprovalMapper.insert(ea);
				dto.setResult("S");
				return dto;
			}
			//如果期满天数内有多条记录，则需要将这些记录的金额相加作为一条期满审核记录
			BigDecimal fee = new BigDecimal(0);
			for(EnrollmentExtra extra:extraList){
				fee = fee.add(extra.getFee());
				if(cashbackDaysForApproval<extra.getCashbackDays()){
					cashbackDaysForApproval = extra.getCashbackDays();
				}
			}
			EnrollApproval ea = new EnrollApproval();
			ea.setType(2);
			ea.setEntryDate(enrollment.getEntryDate());
			ea.setReward(fee);
			ea.setCashbackDays(cashbackDaysForApproval);
			ea.setDataState(1);
			ea.setCreateTime(new Date());
			ea.setEnrollmentId(enrollmentId);
			ea.setUser(user);
			ea.setState(0);
			iEnrollApprovalMapper.insert(ea);
			dto.setResult("S");
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class})
	public ResultDto<EnrollApproval> entryApproveById(Integer id, Integer state, String failedReason,
													  Integer approverId, String[] cashbackData) {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		try {
			EnrollApproval ea = iEnrollApprovalMapper.getById(id);
			if(ea==null){
				dto.setErrorMsg("未找到待审核信息，请刷新页面后再试");
				dto.setResult("F");
				return dto;
			}
			Enrollment em = iEnrollmentMapper.getById(ea.getEnrollmentId());
			if(em==null){
				dto.setErrorMsg("未找到报名信息，请确认后再次审核");
				dto.setResult("F");
				return dto;
			}
			User user = null;
			if(em.getUser()==null||em.getUser().getId()==null){
				dto.setErrorMsg("入职审核时用户信息未找到");
				dto.setResult("F");
				return dto;
			}
			user = iUserMapper.getById(em.getUser().getId());
			if(user==null){
				dto.setErrorMsg("审核时获取用户信息失败");
				dto.setResult("F");
				return dto;
			}
			if(em.getState()==20&&ea.getType()==1){
				//入职审核
				if(state==1){
					approvalSuccess(id, ea, em, user);
					updateHistoryEnrollment(em.getUser().getId(),em.getId());
					createEnrollmentExtraInfo(cashbackData, em);
				}else{
					approvalFailed(failedReason, ea, em);
				}
				ea.setOperatorId(approverId);
				iEnrollmentMapper.updateById(em.getId(), em);
				iEnrollApprovalMapper.updateById(ea.getId(), ea);
				dto.setResult("S");
				return dto;
			}else{
				dto.setErrorMsg("审核状态不匹配，请刷新页面后再试");
				dto.setResult("F");
				return dto;
			}
		} catch (Exception e) {
			//异常时回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			//打印异常信息
			logger.error("入职审核异常");
			e.printStackTrace();
			//返回失败结果
			dto.setErrorMsg("入职审核异常");
			dto.setResult("F");
			return dto;
		}
	}

	private void updateHistoryEnrollment(Integer userId,Integer emId) throws Exception{
		List<Enrollment> list = iEnrollmentMapper.queryListByUserIdAndState(userId,emId);
		if(CollectionUtils.isNotEmpty(list)){
			for(Enrollment e:list){
				e.setUpdateTime(new Date());
				e.setIsHistory(1);
			}
			iEnrollmentMapper.batchUpdateToHistory(list);
		}
	}

	/**
	 * 生成分阶段期满返费信息
	 * @param cashbackData
	 * @param em
	 */
	private void createEnrollmentExtraInfo(String[] cashbackData, Enrollment em) {
		Date createDate = new Date();
		//生成分阶段的返费信息
		for(int i=0;i<cashbackData.length;i++){
            String data = cashbackData[i];
            String[] dataArray = data.split(":");
            BigDecimal fee = new BigDecimal(dataArray[0]);
			Integer cashbackDays = Integer.valueOf(dataArray[1]);
            EnrollmentExtra ee = new EnrollmentExtra();
            ee.setFee(fee);
            ee.setEnrollmentId(em.getId());
            ee.setCashbackDays(cashbackDays);
            ee.setDataState(1);
            ee.setCreateTime(createDate);
            ee.setState(0);
            iEnrollmentExtraMapper.insert(ee);
        }
	}

	/**
	 * 入职审核不通过的情况
	 * @param failedReason
	 * @param ea
	 * @param em
	 */
	private void approvalFailed(String failedReason, EnrollApproval ea, Enrollment em) {
		em.setState(22);
		em.setFailedReason(failedReason);
		em.setUpdateTime(new Date());
		ea.setFailedReason(failedReason);
		ea.setState(2);
		ea.setUpdateTime(new Date());
	}

	/**
	 * 入职审核通过的情况
	 * @param id
	 * @param ea
	 * @param em
	 * @param user
	 * @throws Exception
	 */
	private void approvalSuccess(Integer id, EnrollApproval ea, Enrollment em, User user) throws Exception {
		em.setState(21);
		em.setUpdateTime(new Date());
		BigDecimal reward = new BigDecimal(0);
		em.setReward(reward);
		ea.setState(1);
		ea.setUpdateTime(new Date());
		ea.setReward(reward);
		//更新用户状态到已入职
		user.setCurrentState(2);
		user.setUpdateTime(new Date());
		//先将之前牌入职审核通过和期满审核通过的审核记录状态个性为已离职状态
		List<Enrollment> list = iEnrollmentMapper.queryListByUserIdStateAndId(user.getId(),id);
		cancelOldEnrollment(list);
		iUserMapper.updateById(user.getId(), user);
	}

	/**
	 * 入职审核通过后取消之前的所有报名状态的报名记录
	 * @param list
	 */
	private void cancelOldEnrollment(List<Enrollment> list) {
		if(CollectionUtils.isNotEmpty(list)){
            for(Enrollment em1 :list){
                em1.setDataState(0);
                em1.setUpdateTime(new Date());
            }
            iEnrollmentMapper.batchUpdateToDelete(list);
        }
	}

	@Override
	public ResultDto<EnrollApproval> applyExpireApprovalWithCashbackDays(JSONObject params) {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		Integer enrollmentId = params.getInt("enrollmentId");
		Integer userId = params.getInt("userId");
		Integer cashbackDays = params.getInt("cashbackDays");
		try {
			//先找到用户的报名信息
			Enrollment enrollment = iEnrollmentMapper.getById(enrollmentId);
			if(enrollment==null){
				dto.setErrorMsg("报名信息无效，请稍后重试");
				dto.setResult("F");
				return dto;
			}
			//判断用户报名信息的状态
			if(enrollment.getState()!=21&&enrollment.getState()!=32){
				dto.setErrorMsg("报名信息不是已入职状态，不可审核");
				dto.setResult("F");
				return dto;
			}
			//查询用户输入的返费期限天数内未支付过的分阶段期满返费信息
			List<EnrollmentExtra> emExtraList = iEnrollmentExtraMapper.queryListByEnrollmentIdStateAndCashbackDays(
					enrollmentId,0,cashbackDays);
			if(CollectionUtils.isEmpty(emExtraList)){
				dto.setResult("F");
				dto.setErrorMsg("无可用的期满待审核数据");
				return dto;
			}
			int maxCashbackDays = -1;
			BigDecimal reward = new BigDecimal(0);
			for(EnrollmentExtra ee :emExtraList){
				ee.setState(1);
				ee.setUpdateTime(new Date());
				reward = reward.add(ee.getFee());
				if(maxCashbackDays < ee.getCashbackDays()){
					maxCashbackDays = ee.getCashbackDays();
				}
			}
			// 如果当前发起的期满审核申请的期满天数大于最大的期满，
			// 说明是此次期满审核是该用户此次报名的最后一次期审核，此时更新用户的报名信息状态为已期满
			if(cashbackDays>maxCashbackDays){
				//全部期满待审核
				enrollment.setState(30);
			}else {
				//分阶段期满待审核
				enrollment.setState(50);
			}
			enrollment.setUpdateTime(new Date());
			User user = new User();
			user.setId(userId);
			enrollment.setUser(user);
			iEnrollmentMapper.updateById(enrollmentId, enrollment);
			EnrollApproval ea = saveEnrollApprova(null, enrollment,2,reward, cashbackDays);
			dto.setResult("S");
			dto.setData(ea);
			return dto;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			dto.setErrorMsg("发起分阶段期满审核申请失败，请稍后重试");
			dto.setResult("F");
			return dto;
		}
	}

	@Override
	public ResultDto<EnrollApproval> expireApprovalById(Integer id, Integer state, String failedReason, Integer approverId) throws Exception {
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
			User user = null;
			if(enrollment.getUser()==null||enrollment.getUser().getId()==null){
				dto.setErrorMsg("审核时用户信息未找到");
				dto.setResult("F");
				return dto;
			}
			user = iUserMapper.getById(enrollment.getUser().getId());
			if(user==null){
				dto.setErrorMsg("审核时获取用户信息失败");
				dto.setResult("F");
				return dto;
			}
			if((enrollment.getState()==50||enrollment.getState()==30)&&ea.getType()==2){
				//期满审核
				if(state==1){
					if(enrollment.getState()==50){
						enrollment.setState(51);
					}
					if(enrollment.getState()==30){
						enrollment.setState(31);
					}
					enrollment.setUpdateTime(new Date());
					ea.setState(1);
					ea.setUpdateTime(new Date());
					//更新分阶段期满返费信息状态
					List<EnrollmentExtra> extraList = iEnrollmentExtraMapper.queryListByEnrollmentIdStateAndCashbackDays(
							enrollment.getId(),0,ea.getCashbackDays()+1);
					BigDecimal fee = new BigDecimal(0);
					if(CollectionUtils.isNotEmpty(extraList)){
						for(EnrollmentExtra extra:extraList){
							extra.setState(1);
							extra.setUpdateTime(new Date());
							fee = fee.add(extra.getFee());
							iEnrollmentExtraMapper.updateById(extra.getId(),extra);
						}
					}
					//这里还需要将日志记录到t_wallet_record表中
					if(fee.compareTo(new BigDecimal(0))==0){
						fee = ea.getReward();//这里是用来测试的数据，可能会做进一步修改
					}
					saveUserWalletAndRecord(enrollment,fee);
					//更新用户状态到已期满
					user.setUpdateTime(new Date());
					if (enrollment.getState()==31){
						user.setCurrentState(3);
					}
					iUserMapper.updateById(user.getId(), user);
				}else{
					if (enrollment.getState()==30){
						enrollment.setState(32);
					}
					if(enrollment.getState()==50){
						enrollment.setState(52);
					}
					enrollment.setUpdateTime(new Date());
					enrollment.setFailedReason(failedReason);
					ea.setState(2);
					ea.setUpdateTime(new Date());
					ea.setFailedReason(failedReason);
				}
				ea.setOperatorId(approverId);
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
			logger.error("分阶段期满审核异常："+e.getMessage());
			e.printStackTrace();
			dto.setErrorMsg("分阶段期满审核异常");
			dto.setResult("F");
			return dto;
		}
	}

}
