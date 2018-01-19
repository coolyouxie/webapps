package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webapps.common.entity.UserAward;
import com.webapps.common.form.UserAwardRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IUserAwardMapper extends IBaseMapper<UserAward>,IPageMapper<UserAward,UserAwardRequestForm>{
	
	public List<UserAward> queryPage(@Param("startRow") int startRow, @Param("endRow") int endRow, @Param("userId") int userId);
	
}
