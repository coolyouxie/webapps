package com.webapps.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webapps.common.entity.GroupUser;
import com.webapps.common.form.GroupUserRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IGroupUserMapper extends IBaseMapper<GroupUser> ,IPageMapper<GroupUser,GroupUserRequestForm>{

	public int batchInsert(List<GroupUser> list)throws Exception;

}
