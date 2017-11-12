package com.webapps.common.form;

import com.webapps.common.entity.Company;
import com.webapps.common.entity.GroupUser;

import java.io.Serializable;

public class GroupUserRequestForm extends GroupUser implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;
	
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

}
