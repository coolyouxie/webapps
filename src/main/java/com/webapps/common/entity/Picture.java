package com.webapps.common.entity;

public class Picture extends Entity {
	
	private Integer fkId;
	
	private String picUrl;
	
	private Integer type;
	
	public Integer getFkId() {
		return fkId;
	}
	public void setFkId(Integer fkId) {
		this.fkId = fkId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	

}
