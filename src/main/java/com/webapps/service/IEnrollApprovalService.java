package com.webapps.service;

import java.math.BigDecimal;
import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.form.EnrollApprovalRequestForm;

import net.sf.json.JSONObject;

public interface IEnrollApprovalService {
	
	/**
	 * 分页查询报名、入职、期满、离职等状态的审核记录
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page loadEnrollApprovalList(Page page,EnrollApprovalRequestForm form)throws Exception;
	
	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public ResultDto<EnrollApproval> approvalById(Integer id,Integer state,String remark,Integer approverId)throws Exception;
	
	public EnrollApproval getById(Integer id)throws Exception;
	
	/**
	 * 根据等审核记录ID，审核类型（入职、期满等）和审核状态对人员信息进行审核操作
	 * approvalType		1入职审核，2期满审核
	 * @param id
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public ResultDto<EnrollApproval> enrollApproval(Integer id,Integer state,String failedReason,
			BigDecimal reward,Integer approverId,Integer cashbackDays)throws Exception;
	
	public ResultDto<EnrollApproval> expireApproval(Integer id,Integer state,String failedReason,
			Integer approverId)throws Exception;
	
	public ResultDto<EnrollApproval> applyEntryApproval(JSONObject params);
	
	public ResultDto<EnrollApproval> applyExpireApproval(JSONObject params);
	
	public ResultDto<List<EnrollApproval>> queryByUserIdTypeAndState(Integer userId,Integer type,Integer state);
	

}
