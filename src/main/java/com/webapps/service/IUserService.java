package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;

import java.util.Map;

public interface IUserService {
	
	Page loadUserList(Page page, UserRequestForm user) throws Exception;
	
	User login(User user)throws Exception;
	
	int insert(User user)throws Exception;
	
	ResultDto<User> saveUser(User user);
	
	User getById(Integer id)throws Exception;
	
	ResultDto<User> deleteUserById(Integer id) throws Exception;
	
	ResultDto<String> getSmsValidateMsg(String phoneNum);
	
	ResultDto<String> resetPassword(String phoneNum, String password);

	Map<String,String> loadUserPermission(Integer userId);
	
	/**
	 * 根据邀请码查询用户对象。
	 * @author scorpio.yang
	 * @since 2018-01-16
	 * @param inviteCode
	 * @return
	 */
	public User queryByInviteCode(String inviteCode);
	
}
