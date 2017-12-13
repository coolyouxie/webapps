package com.webapps.mapper;

import com.webapps.common.entity.Permission;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.PermissionRequestForm;
import com.webapps.common.form.PictureRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IPermissionMapper extends IBaseMapper<Permission>,IPageMapper<Permission,PermissionRequestForm>{

    List<Permission> getByNameOrCode(@Param("name")String name,@Param("code")String code)throws Exception;

    /**
     * 根据name,code,parentCode,level,type字段查询权限数据
     * @param form
     * @return
     * @throws Exception
     */
    List<Permission> queryByConditions(@Param("obj")PermissionRequestForm form)throws Exception;

    /**
     * id为0时，查询所有层级为3的权限
     * id为不0时，查询所有层级为3的权限并且标记哪些权限为id对应权限的子权限
     * @return
     */
    List<Permission> queryAllLevel3PermByParentId(Integer id);

}
