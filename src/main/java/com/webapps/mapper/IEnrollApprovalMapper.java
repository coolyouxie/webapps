package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.form.EnrollApprovalRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IEnrollApprovalMapper extends IBaseMapper<EnrollApproval>,IPageMapper<EnrollApproval,EnrollApprovalRequestForm>{
	
	List<EnrollApproval> queryListByFkId(@Param("obj") EnrollApprovalRequestForm form)throws Exception;
	
	int countByFkIds(@Param("obj") EnrollApproval em)throws Exception;
	
	List<EnrollApproval> queryByUserIdTypeAndState(@Param("userId") Integer userId,
                                                   @Param("type") Integer type,
                                                   @Param("state") Integer state)throws Exception;

	List<EnrollApproval> queryByUserIdEnrollmentIdTypeAndState(@Param("userId") Integer userId,
                                                               @Param("enrollmentId") Integer enrollmentId,
                                                               @Param("type") Integer type,
                                                               @Param("state") Integer state)throws Exception;
	
	int batchDeleteInLogic(List<EnrollApproval> list)throws Exception;
}
