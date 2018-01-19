package com.webapps.common.form;

import java.io.Serializable;

import com.webapps.common.entity.ParamConfig;

public class ParamConfigRequestForm extends ParamConfig implements RequestForm,Serializable{

	private static final long serialVersionUID = 4105928640238854180L;

	private String keyWords;
	
	/**
	 * 操作类型（查询，新增，更新，删除）
	 */
	private String handleType;

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	
}
