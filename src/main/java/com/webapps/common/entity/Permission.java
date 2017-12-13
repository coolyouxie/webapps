package com.webapps.common.entity;

import java.util.List;

public class Permission extends Entity {
	
	private String name;
	
	private String code;
	
	private int level;
	
	private Integer type;
	
	private String parentCode;

	private List<Permission> childrenPermissions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public List<Permission> getChildrenPermissions() {
		return childrenPermissions;
	}

	public void setChildrenPermissions(List<Permission> childrenPermissions) {
		this.childrenPermissions = childrenPermissions;
	}
}
