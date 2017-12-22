package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Permission;
import com.webapps.common.form.PermissionRequestForm;

import java.util.List;

public interface IPermissionService {
	
	Page loadPermissionList(Page page, PermissionRequestForm form) throws Exception;
	
	Permission getById(Integer id) throws Exception;
	
	ResultDto<String> saveOperatePermission(Permission permission) throws Exception;
	
	ResultDto<String> saveMenuPermission(PermissionRequestForm permission) throws Exception;
	
	ResultDto<Permission> deletePermissionById(Integer id) throws Exception;

	ResultDto<String> validatePermission(PermissionRequestForm form) throws Exception;

	List<Permission> queryByConditions(PermissionRequestForm form) throws Exception;
	
	List<Permission> loadAllOperatePermission();
	
	List<Permission> loadAllMenuPermission();
	
	List<Permission> loadUserPermission(Integer userId);

	Permission getMenuPermissionById(Integer id);

	List<Permission> loadAllPermissions(Integer userId);

}
