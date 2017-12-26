package com.webapps.common.entity;

public class UserPermission extends PermissionRelation {
	
	private Integer permissionRelationId;
	
	private Integer userId;

	private Integer prId;

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

	public Integer getPrId() {
		return prId;
	}

	public void setPrId(Integer prId) {
		this.prId = prId;
	}
}
