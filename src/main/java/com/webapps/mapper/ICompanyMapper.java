package com.webapps.mapper;

import com.webapps.common.entity.Company;
import com.webapps.common.entity.User;
import com.webapps.common.form.CompanyRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface ICompanyMapper extends IBaseMapper<Company,CompanyRequestForm>{
	
	public User queryCompanyByAccount(String account);

}
