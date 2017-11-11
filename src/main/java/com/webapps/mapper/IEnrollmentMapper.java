package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;
import org.springframework.stereotype.Repository;

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
}
