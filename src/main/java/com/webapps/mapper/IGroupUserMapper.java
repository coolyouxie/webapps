package com.webapps.mapper;

import com.webapps.common.entity.AliSmsMsg;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.entity.GroupUser;
import com.webapps.common.form.GroupUserRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IGroupUserMapper extends IBaseMapper<GroupUser> ,IPageMapper<GroupUser,GroupUserRequestForm>{

	public int batchInsert(List<GroupUser> list)throws Exception;

}
