package com.webapps.service;

import java.util.List;

import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.RecruitmentRequestForm;

public interface IRecruitmentService {
	
	public int saveRecruitment(RecruitmentRequestForm form)throws Exception;
	
	public List<Recruitment> queryListByCompanyId(Integer companyId)throws Exception;
	
	public int deleteRecruitmentById(Integer id)throws Exception;

}
