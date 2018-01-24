package com.webapps.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.dto.RateDto;
import com.webapps.common.entity.Company;
import com.webapps.common.form.CompanyRequestForm;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.common.form.RateDtoRequestForm;

public interface IRateService {
	
	Page loadRateDtoList(Page page, RateDtoRequestForm form) throws Exception;

	Page loadEntryStatisticsList(Page page,RateDtoRequestForm form) throws Exception;

	Page loadExpireStatisticsList(Page page,RateDtoRequestForm form) throws Exception;

	Page loadEntryOrExprieStatisticsList(Page page,RateDtoRequestForm form) throws Exception;
	
	void exportStatistics(HttpSession session,HttpServletResponse response,int talkerId,int state,int type);

	void exportRecruitProcess(HttpSession session, HttpServletResponse response,
							  EnrollmentRequestForm form)throws Exception;

}
