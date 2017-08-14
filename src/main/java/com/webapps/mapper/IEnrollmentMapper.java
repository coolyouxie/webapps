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
	
}
