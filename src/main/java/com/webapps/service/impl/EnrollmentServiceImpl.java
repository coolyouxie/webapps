package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.mapper.IEnrollmentMapper;
import com.webapps.service.IEnrollmentService;

public class EnrollmentServiceImpl implements IEnrollmentService {
	
	@Autowired
	private IEnrollmentMapper iEnrollmentMapper;

	@Override
	public Page loadEnrollmentList(Page page, EnrollmentRequestForm enrollment) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iEnrollmentMapper.queryCount(enrollment);
		List<Enrollment> list = iEnrollmentMapper.queryPage(startRow, endRow, enrollment);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public Enrollment getById(Integer id) throws Exception {
		Enrollment em = iEnrollmentMapper.getById(id);
		return em;
	}

	/**
	 * 报名，在APP端也要限制，如果发现某用户已经报了某个发布单，则报名按钮置灰，显示“已报名”
	 */
	@Override
	public ResultDto<Enrollment> saveEnrollment(Enrollment enrollment) throws Exception {
		ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
		int count = iEnrollmentMapper.countByFkIds(enrollment);
		if(count>=1){
			dto.setResult("fail");
			dto.setErrorMsg("不能重复报名");
			return dto;
		}
		int result = iEnrollmentMapper.insert(enrollment);
		if(result==1){
			dto.setResult("success");
			return dto;
		}else{
			dto.setResult("fail");
			dto.setErrorMsg("报名失败，请稍后再试");
			return dto;
		}
	}

	@Override
	public ResultDto<Enrollment> deleteEnrollmentById(Integer id) throws Exception {
		ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
		int result = iEnrollmentMapper.deleteByIdInLogic(id);
		if(result==1){
			dto.setResult("success");
			return dto;
		}else{
			dto.setResult("fail");
			dto.setErrorMsg("取消失败");
			return dto;
		}
	}

}
