package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.RecruitmentRequestForm;

public interface IRecruitmentService {
	
	ResultDto<RecruitmentRequestForm> saveRecruitment(RecruitmentRequestForm form);
	
	List<Recruitment> queryListByCompanyId(Integer companyId)throws Exception;
	
	int deleteRecruitmentById(Integer id)throws Exception;
	
	Page loadRecruitmentList(Page page, RecruitmentRequestForm form)throws Exception;
	
	Recruitment getById(Integer id)throws Exception;

	List<Recruitment> queryListByType(int type)throws Exception;

}
