package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IUserMapper extends IBaseMapper<User>{
	
	public int queryCount(@Param("user")UserRequestForm user);
	
	public List<User> queryUserList(@Param("startRow")int startRow,@Param("endRow")int endRow,@Param("user")UserRequestForm user);
	
	public User queryUserByAccount(String account);
	
	public int deleteByIdInLogic(Integer id);

}
