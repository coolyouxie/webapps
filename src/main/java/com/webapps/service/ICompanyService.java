package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.form.CompanyRequestForm;

public interface ICompanyService {
	
	public Page loadCompanyList(Page page,CompanyRequestForm company) throws Exception;
	
	public Company getById(Integer id) throws Exception;
	
	public ResultDto<Company> saveCompany(Company company) throws Exception;
	
	public ResultDto<Company> deleteCompanyById(Integer id) throws Exception;

}
