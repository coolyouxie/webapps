package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IUserMapper extends IBaseMapper<User>,IPageMapper<User,UserRequestForm>{
	
	User queryUserByAccount(String account);
	
	List<User> queryUsersByUserType(int userType)throws Exception;
	
	User queryByInviteCode(String inviteCode);
	
	List<User> queryByConditin(@Param("obj")UserRequestForm form)throws Exception;
	
}
