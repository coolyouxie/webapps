package com.webapps.common.form;

import com.webapps.common.entity.UserAwardExchange;

import java.io.Serializable;

public class UserAwardExchangeRequestForm extends UserAwardExchange implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;

	private String enrollTimeStart;

	private String enrollTimeEnd;

	public String getEnrollTimeStart() {
		return enrollTimeStart;
	}

	public void setEnrollTimeStart(String enrollTimeStart) {
		this.enrollTimeStart = enrollTimeStart;
	}

	public String getEnrollTimeEnd() {
		return enrollTimeEnd;
	}

	public void setEnrollTimeEnd(String enrollTimeEnd) {
		this.enrollTimeEnd = enrollTimeEnd;
	}
}
