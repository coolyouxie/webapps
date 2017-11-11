package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;

public interface IEnrollmentService {
	
	Page loadEnrollmentList(Page page, EnrollmentRequestForm enrollment) throws Exception;
	
	Enrollment getById(Integer id) throws Exception;
	
	ResultDto<Enrollment> saveEnrollment(Enrollment enrollment) throws Exception;
	
	ResultDto<Enrollment> deleteEnrollmentById(Integer id) throws Exception;
	
	List<Enrollment> queryEnrollmentListByUserId(Integer id)throws Exception;
	
	int saveTalkInfoById(Enrollment em)throws Exception;
	
	/**
	 * 用户报名接口
	 * @param params
	 * @return
	 * @throws Exception
	 */
    ResultDto<Enrollment> userEnroll(Enrollment em)throws Exception;
	
	ResultDto<Enrollment> updateEnrollment(EnrollmentRequestForm form);
	
	ResultDto<Enrollment> cancelEnroll(EnrollmentRequestForm form);

	/**
	 * 根据用户ID查询用户当前有效的报名信息（这里的有效指的是用户入职审核通过和期满审核通过的最近的一条记录）
	 * @param userId
	 * @return
	 */
    Enrollment getCurrentStateEnrollmentByUserId(Integer userId);
}
