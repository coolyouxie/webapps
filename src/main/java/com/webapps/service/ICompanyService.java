package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.form.CompanyRequestForm;

public interface ICompanyService {
	
	Page loadCompanyList(Page page, CompanyRequestForm company) throws Exception;
	
	Company getById(Integer id) throws Exception;
	
	ResultDto<Company> saveCompany(Company company) throws Exception;
	
	ResultDto<Company> deleteCompanyById(Integer id) throws Exception;

}
