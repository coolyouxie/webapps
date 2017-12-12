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

}
