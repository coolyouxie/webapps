package com.webapps.common.form;

import java.io.Serializable;

import com.webapps.common.entity.Permission;

public class PermissionRequestForm extends Permission implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;
	
	private Integer childPermission[];

	public String getApplyTimeStart() {
		return applyTimeStart;
	}

	public void setApplyTimeStart(String applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}

	public String getApplyTimeEnd() {
		return applyTimeEnd;
	}

	public void setApplyTimeEnd(String applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}

	public Integer[] getChildPermission() {
		return childPermission;
	}

	public void setChildPermission(Integer childPermission[]) {
		this.childPermission = childPermission;
	}

}
