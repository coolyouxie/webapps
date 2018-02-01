package com.webapps.mapper;

import java.util.Date;
import java.util.List;

import com.webapps.common.form.EnrollmentRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.form.EnrollApprovalRequestForm;

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
	
	int updateApproveInfo(@Param("id")Integer id,@Param("approverId")Integer approverId,
			@Param("approverName")String approverName,@Param("approveTime")Date approveTime)throws Exception;

	int queryExpireApproveCount(@Param("startRow")int startRow, @Param("endRow")int endRow,
								@Param("obj")EnrollApprovalRequestForm form)throws Exception;

	List<EnrollApproval> queryExpireApproveList(@Param("startRow")int startRow, @Param("endRow")int endRow,
												@Param("obj")EnrollApprovalRequestForm form)throws Exception;
}
