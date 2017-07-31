package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.Company;
import com.webapps.common.entity.User;
import com.webapps.common.form.CompanyRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface ICompanyMapper extends IBaseMapper<Company>{
	
	public int queryCount(@Param("company")CompanyRequestForm company);
	
	public List<Company> queryCompanyList(@Param("startRow")int startRow,@Param("endRow")int endRow,@Param("company")CompanyRequestForm company);
	
	public User queryCompanyByAccount(String account);

}
