package com.webapps.common.form;

import java.io.Serializable;

import com.webapps.common.entity.BillRecord;

public class BillRecordRequestForm extends BillRecord implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;
	
	private String keyWords;
	
	/**
	 * 操作类型（查询，新增，更新，删除）
	 */
	private String handleType;
	
	private Integer step;

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

}
