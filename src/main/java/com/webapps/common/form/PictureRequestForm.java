package com.webapps.common.form;

import java.io.Serializable;

import com.webapps.common.entity.Picture;

public class PictureRequestForm extends Picture implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;
	
	private String keyWords;
	
	/**
	 * 操作类型（查询，新增，更新，删除）
	 */
	private String handleType;
	
	private String picUrl1;
	
	private String picUrl2;

	private Integer[] types;

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

	public String getPicUrl1() {
		return picUrl1;
	}

	public void setPicUrl1(String picUrl1) {
		this.picUrl1 = picUrl1;
	}

	public String getPicUrl2() {
		return picUrl2;
	}

	public void setPicUrl2(String picUrl2) {
		this.picUrl2 = picUrl2;
	}

	public Integer[] getTypes() {
		return types;
	}

	public void setTypes(Integer[] types) {
		this.types = types;
	}
}
