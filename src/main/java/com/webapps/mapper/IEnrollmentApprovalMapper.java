package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.common.form.EnrollmentResponseForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IEnrollmentApprovalMapper extends IBaseMapper<Enrollment>,IPageMapper<EnrollmentResponseForm,EnrollmentRequestForm>{
	
	public List<EnrollmentResponseForm> queryListByFkId(@Param("obj")EnrollmentRequestForm form)throws Exception;
	
	public int countByFkIds(@Param("obj")Enrollment em)throws Exception;
	
}
