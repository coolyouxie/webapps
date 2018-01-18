package com.webapps.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IEnrollmentMapper extends IBaseMapper<Enrollment>,IPageMapper<Enrollment,EnrollmentRequestForm>{
	
	List<Enrollment> queryListByFkId(@Param("obj") EnrollmentRequestForm form)throws Exception;
	
	int countByFkIds(@Param("obj") Enrollment em)throws Exception;
	
	int saveTalkInfoById(@Param("obj") Enrollment em, @Param("id") Integer id)throws Exception;
	
	int cancelEnroll(@Param("obj") Enrollment em, @Param("id") Integer id)throws Exception;
	
	List<Enrollment> queryListByUserIdAndState(@Param("userId") Integer userId, @Param("emId") Integer emId)throws Exception;

	List<Enrollment> queryListByUserIdAndStateNew(@Param("userId") Integer userId, @Param("state") Integer state)throws Exception;
	
	void batchUpdate(@Param("list") List<Enrollment> list)throws Exception;

	List<Enrollment> queryListByUserIdStateAndId(@Param("userId") Integer user, @Param("id") Integer id)throws Exception;

	int batchUpdateToDelete(List<Enrollment> list);

	int batchUpdateToHistory(@Param("list") List<Enrollment> list);
	
	int updateTalkInfo(@Param("id")Integer id,@Param("talkerId")Integer talkerId,@Param("isTalked")int isTalked,
			@Param("talkerName")String talkerName,@Param("updateTime")Date updateTime)throws Exception;
	
	int updateEntryApproveInfo(@Param("id")Integer id,@Param("entryApproverId")Integer entryApproverId,
			@Param("isEntryApproved")int isEntryApproved,@Param("entryApproverName")String entryApproverName,
			@Param("updateTime")Date updateTime)throws Exception;
	
	int updateExpireApproveInfo(@Param("id")Integer id,@Param("expireApproverId")Integer expireApproverId,
			@Param("isExpireApproved")int isExpireApproved,@Param("expireApproverName")String expireApproverName,
			@Param("updateTime")Date updateTime)throws Exception;
	
	int updateInterviewTime(@Param("id")Integer id,@Param("interviewTime")Date interviewTime)throws Exception;
	
	int updateToNotLatest(@Param("userId")Integer userId)throws Exception;
	
	List<Enrollment> findLatestByUserId(@Param("userId")Integer userId)throws Exception;
	
}
