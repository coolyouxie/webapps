package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollApprovalRequestForm;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.common.form.EnrollmentResponseForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IEnrollApprovalMapper extends IBaseMapper<EnrollApproval>,IPageMapper<EnrollApproval,EnrollApprovalRequestForm>{
	
	public List<EnrollApproval> queryListByFkId(@Param("obj")EnrollApprovalRequestForm form)throws Exception;
	
	public int countByFkIds(@Param("obj")EnrollApproval em)throws Exception;
	
	public List<EnrollApproval> queryByUserIdTypeAndState(@Param("userId")Integer userId,
														  @Param("type")Integer type ,
														  @Param("state")Integer state)throws Exception;

	public List<EnrollApproval> queryByUserIdEnrollmentIdTypeAndState(@Param("userId")Integer userId,
																	  @Param("enrollmentId")Integer enrollmentId,
																	  @Param("type")Integer type ,
																	  @Param("state")Integer state)throws Exception;
}
