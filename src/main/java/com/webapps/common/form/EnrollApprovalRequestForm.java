package com.webapps.common.form;

import java.io.Serializable;

import com.webapps.common.entity.EnrollApproval;

public class EnrollApprovalRequestForm extends EnrollApproval implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;
	
	private String keyWords;
	
	/**
	 * 操作类型（查询，新增，更新，删除）
	 */
	private String handleType;
	
	/**
	 * approvalType 审核类型，1入职审核，2期满审核
	 */
	private Integer approvalType;

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

	public Integer getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(Integer approvalType) {
		this.approvalType = approvalType;
	}

}
