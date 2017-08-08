package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;

public interface IEnrollmentService {
	
	public Page loadEnrollmentList(Page page,EnrollmentRequestForm enrollment) throws Exception;
	
	public Enrollment getById(Integer id) throws Exception;
	
	public ResultDto<Enrollment> saveEnrollment(Enrollment enrollment) throws Exception;
	
	public ResultDto<Enrollment> deleteEnrollmentById(Integer id) throws Exception;

}
