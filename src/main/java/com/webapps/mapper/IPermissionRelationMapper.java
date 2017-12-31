package com.webapps.mapper;

import java.util.List;

import com.webapps.common.entity.Permission;
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
     * @param
     * @return
     * @throws Exception
     */
    List<PermissionRelation> queryByConditions(@Param("obj")PermissionRelation pr)throws Exception;
    
    int batchDeleteInLogic(@Param("list")List<PermissionRelation> list);

    List<PermissionRelation> queryPermissionRelationWithUserCheckFlag(@Param("userId")Integer userId) throws Exception;

    int batchDelete(@Param("list")List<PermissionRelation> list)throws Exception;

    int deleteByPermissionId(int permissionId)throws Exception;

    int deleteByParentPermissionId(int parentPermissionId)throws Exception;

    int deleteByFkIds(int parentPermissionId,int permissionId)throws Exception;

}
