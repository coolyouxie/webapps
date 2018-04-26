package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;

import javax.servlet.http.HttpSession;
import java.util.List;
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
	User queryByInviteCode(String inviteCode);

	List<User> queryUserByType(int type);
	
	void transactionTest() throws Exception;

	ResultDto<String> resetPassword(HttpSession session, Integer id, String oldPwd,
										   String newPwd, String confirmNewPwd)throws Exception;
	/**
	 * 根据微信号判断账户是否存在
	 * @param weixin
	 * @return
	 * @throws Exception
	 */
	ResultDto<String> queryUserByWeixin(String weixin)throws Exception;
}
