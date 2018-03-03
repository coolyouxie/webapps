package com.webapps.common.form;

import com.webapps.common.entity.AwardConfig;

import java.io.Serializable;

public class AwardConfigRequestForm extends AwardConfig implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;

	/**
	 * 操作类型（查询，新增，更新，删除）
	 */
	private String handleType;

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}


}
