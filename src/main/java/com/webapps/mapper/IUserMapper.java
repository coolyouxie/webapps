package com.webapps.mapper;

import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IUserMapper extends IBaseMapper<User>,IPageMapper<User,UserRequestForm>{
	
	public User queryUserByAccount(String account);
	
}
