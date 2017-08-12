package com.webapps.common.form;

import java.io.Serializable;

import com.webapps.common.entity.Enrollment;

public class EnrollmentRequestForm extends Enrollment implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;
	
	private String keyWords;
	
	/**
	 * 操作类型（查询，新增，更新，删除）
	 */
	private String handleType;
	
	private Integer step;
	
	private String companyName;
	
	private String userName;
	
	private String userMobile;
	
	private String enrollTimeStart;
	
	private String enrollTimeEnd;

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

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

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
