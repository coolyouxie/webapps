package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Permission;
import com.webapps.common.form.PermissionRequestForm;

public interface IPermissionService {
	
	Page loadPermissionList(Page page, PermissionRequestForm form) throws Exception;
	
	Permission getById(Integer id) throws Exception;
	
	ResultDto<Permission> savePermission(Permission permission) throws Exception;
	
	ResultDto<Permission> deletePermissionById(Integer id) throws Exception;

	ResultDto<String> validatePermission(PermissionRequestForm form) throws Exception;

}
