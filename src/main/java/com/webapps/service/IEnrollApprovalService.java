package com.webapps.service;

import java.math.BigDecimal;
import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.dto.EnrollApprovalInfoDto;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.entity.EnrollmentExtra;
import com.webapps.common.form.EnrollApprovalRequestForm;

import net.sf.json.JSONObject;

public interface IEnrollApprovalService {
	
	/**
	 * 分页查询报名、入职、期满、离职等状态的审核记录
	 * @param page
	 * @return
	 * @throws Exception
	 */
    Page loadEnrollApprovalList(Page page, EnrollApprovalRequestForm form)throws Exception;
	
	/**
	 *
	 * @return
	 * @throws Exception
	 */
    ResultDto<EnrollApproval> approvalById(Integer id, Integer state, String remark, Integer approverId)throws Exception;
	
	EnrollApproval getById(Integer id)throws Exception;

	ResultDto<EnrollApproval> applyEntryApproval(JSONObject params);
	
	ResultDto<EnrollApproval> applyExpireApproval(JSONObject params);

	/**
	 * 分阶段申请期满返费
	 * @param params
	 * @return
	 */
    ResultDto<EnrollApproval> applyExpireApprovalWithCashbackDays(JSONObject params);
	
	ResultDto<List<EnrollApproval>> queryByUserIdTypeAndState(Integer userId, Integer type, Integer state);

	/**
	 * 分阶段期满入职审核
	 * @param id
	 * @param state
	 * @param failedReason
	 * @param approverId
	 * @param cashbackData
	 * @return
	 */
    ResultDto<EnrollApproval> entryApproveById(Integer id, Integer state, String failedReason,
                                               Integer approverId, String[] cashbackData);

	/**
	 * 分阶段期满审核
	 * @param id
	 * @param state
	 * @param failedReason
	 * @param approverId
	 * @return
	 * @throws Exception
	 */
    ResultDto<EnrollApproval> expireApprovalById(Integer id, Integer state, String failedReason,
                                                 Integer approverId)throws Exception;
	
	EnrollApprovalInfoDto loadEnrollApprovalInfo(Integer enrollApprovalId)throws Exception;

	Page getUserApprovalPage(Page page, EnrollApprovalRequestForm form);
														
}
