package com.webapps.common.entity;

import java.util.List;

public class Permission extends Entity {
	
	private String name;
	
	private String code;
	
	private int level;
	
	private Integer type;

	private boolean checkFlag;

	private List<Permission> childPermissions;

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

	public List<Permission> getChildPermissions() {
		return childPermissions;
	}

	public void setChildPermissions(List<Permission> childPermissions) {
		this.childPermissions = childPermissions;
	}

	public boolean getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(boolean checkFlag) {
		this.checkFlag = checkFlag;
	}
}
