package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webapps.common.entity.PermissionRelation;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IPermissionRelationMapper extends IBaseMapper<PermissionRelation>{

    /**
     * 根据permission_id,parent_permission_id,data_state字段查询权限数据
     * @param form
     * @return
     * @throws Exception
     */
    List<PermissionRelation> queryByConditions(@Param("obj")PermissionRelation pr)throws Exception;
    
    int batchDeleteInLogic(@Param("list")List<PermissionRelation> list);

}
