package com.webapps.common.entity;

public class PermissionRelation extends Entity {
	
	private Integer permissionId;
	
	private Integer parentPermissionId;

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Integer getParentPermissionId() {
		return parentPermissionId;
	}

	public void setParentPermissionId(Integer parentPermissionId) {
		this.parentPermissionId = parentPermissionId;
	}

	
}
