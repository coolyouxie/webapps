package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IEnrollmentMapper extends IBaseMapper<Enrollment>,IPageMapper<Enrollment,EnrollmentRequestForm>{
	
	public List<Enrollment> queryListByFkId(@Param("obj")EnrollmentRequestForm form)throws Exception;
	
	public int countByFkIds(@Param("obj")Enrollment em)throws Exception;
	
	public int saveTalkInfoById(@Param("obj")Enrollment em,@Param("id")Integer id)throws Exception;
	
	public int cancelEnroll(@Param("obj")EnrollmentRequestForm em,@Param("id")Integer id)throws Exception;
	
	public List<Enrollment> queryListByUserIdAndState(@Param("userId")Integer userId)throws Exception;

	public List<Enrollment> queryListByUserIdAndStateNew(@Param("userId")Integer userId,@Param("state")Integer state)throws Exception;
	
	public void batchUpdate(@Param("list")List<Enrollment> list)throws Exception;

	public List<Enrollment> queryListByUserIdStateAndId(@Param("userId")Integer user,@Param("id")Integer id)throws Exception;

	public int batchUpdateToDelete(List<Enrollment> list);

	public int batchUpdateToHistory(List<Enrollment> list);
}
