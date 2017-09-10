package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;

public interface IUserService {
	
	public Page loadUserList(Page page,UserRequestForm user) throws Exception;
	
	public User login(User user)throws Exception;
	
	public int insert(User user)throws Exception;
	
	public ResultDto<User> saveUser(User user);
	
	public User getById(Integer id)throws Exception;
	
	public ResultDto<User> deleteUserById(Integer id) throws Exception;
	
	public ResultDto<String> getSmsValidateMsg(String phoneNum);
	
	public ResultDto<String> resetPassword(String phoneNum,String password);
	

}
