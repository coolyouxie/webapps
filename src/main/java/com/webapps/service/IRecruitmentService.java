package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.RecruitmentRequestForm;

public interface IRecruitmentService {
	
	public ResultDto<RecruitmentRequestForm> saveRecruitment(RecruitmentRequestForm form);
	
	public List<Recruitment> queryListByCompanyId(Integer companyId)throws Exception;
	
	public int deleteRecruitmentById(Integer id)throws Exception;
	
	public Page loadRecruitmentList(Page page,RecruitmentRequestForm form)throws Exception;
	
	public Recruitment getById(Integer id)throws Exception;

}
