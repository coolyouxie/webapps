package com.webapps.common.form;

import java.io.Serializable;

import com.webapps.common.entity.User;

public class UserRequestForm extends User implements Serializable{

	private static final long serialVersionUID = -7915557175531200072L;
	
	private String keyWords;

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

}
