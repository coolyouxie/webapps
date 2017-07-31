package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.Province;
import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;

public interface IProvinceService {
	
	public Page loadUserList(Page page,UserRequestForm user) throws Exception;
	
	public User login(User user)throws Exception;
	
	public User insert(User user)throws Exception;
	
	public User saveUser(User user)throws Exception;
	
	public User getById(Integer id)throws Exception;
	
	public List<Province> setProvinceParentId()throws Exception;
	

}
