package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.dto.RateDto;
import com.webapps.common.entity.Company;
import com.webapps.common.form.CompanyRequestForm;
import com.webapps.common.form.RateDtoRequestForm;

public interface IRateService {
	
	Page loadRateDtoList(Page page, RateDtoRequestForm form) throws Exception;

}
