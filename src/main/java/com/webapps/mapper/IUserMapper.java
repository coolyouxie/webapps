package com.webapps.mapper;

import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IUserMapper extends IBaseMapper<User>,IPageMapper<User,UserRequestForm>{
	
	public User queryUserByAccount(String account);
	
}
