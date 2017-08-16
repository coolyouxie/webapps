package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.form.EnrollApprovalRequestForm;

public interface IEnrollApprovalService {
	
	/**
	 * 分页查询报名、入职、期满、离职等状态的审核记录
	 * @param page
	 * @param ea
	 * @param approvalType 审核状态，1入职审核，2期满审核
	 * @return
	 * @throws Exception
	 */
	public Page loadEnrollApprovalList(Page page,EnrollApprovalRequestForm form)throws Exception;
	
	/**
	 * 
	 * @param ea
	 * @return
	 * @throws Exception
	 */
	public ResultDto<EnrollApproval> approvalById(Integer id,Integer state,String remark)throws Exception;
	
	public EnrollApproval getById(Integer id)throws Exception;

}
