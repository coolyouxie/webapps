package com.webapps.mapper;

import com.webapps.common.entity.UserPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IUserPermissionMapper extends IBaseMapper<UserPermission>{

    /**
     * 根据permission_id,parent_permission_id,data_state字段查询权限数据
     * @param
     * @return
     * @throws Exception
     */
    List<UserPermission> queryByConditions(@Param("obj") UserPermission up)throws Exception;

    int batchDeleteInLogic(@Param("list") List<UserPermission> list);

}
