package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Agency;
import com.webapps.common.form.AgencyRequestForm;

import java.util.List;

public interface IAgencyService {
	
	Page loadAgencyList(Page page, AgencyRequestForm agency) throws Exception;
	
	Agency getById(Integer id) throws Exception;
	
	ResultDto<String> saveAgency(Agency agency) throws Exception;
	
	ResultDto<String> deleteAgencyById(Integer id) throws Exception;
	
	ResultDto<List<Agency>> queryAllAgencyBy(AgencyRequestForm agency)throws Exception;

}
