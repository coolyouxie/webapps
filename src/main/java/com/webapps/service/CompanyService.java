package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.Company;

public interface CompanyService {
	
	public Page loadCompanyList(Page page,Company company);

}
