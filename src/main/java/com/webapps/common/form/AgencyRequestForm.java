package com.webapps.common.form;

import com.webapps.common.entity.Agency;

import java.io.Serializable;

public class AgencyRequestForm extends Agency implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;

	private String keywords;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
