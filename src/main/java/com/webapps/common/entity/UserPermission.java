package com.webapps.common.entity;

public class UserPermission extends Entity {
	
	private Integer permissionRelationId;
	
	private Integer userId;

	public Integer getPermissionRelationId() {
		return permissionRelationId;
	}

	public void setPermissionRelationId(Integer permissionRelationId) {
		this.permissionRelationId = permissionRelationId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
