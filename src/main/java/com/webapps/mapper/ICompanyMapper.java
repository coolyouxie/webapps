package com.webapps.mapper;

import com.webapps.common.entity.Company;
import com.webapps.common.entity.User;
import com.webapps.common.form.CompanyRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface ICompanyMapper extends IBaseMapper<Company>,IPageMapper<Company,CompanyRequestForm>{
	
	User queryCompanyByAccount(String account);

}
