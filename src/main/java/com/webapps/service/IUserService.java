package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;

public interface IUserService {
	
	public Page loadUserList(Page page,UserRequestForm user) throws Exception;
	
	public User login(User user)throws Exception;
	
	public User insert(User user)throws Exception;
	
	public User saveUser(User user)throws Exception;
	
	public User getById(Integer id)throws Exception;
	

}
