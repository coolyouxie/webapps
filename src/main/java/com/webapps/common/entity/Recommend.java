package com.webapps.common.entity;

public class Recommend extends Entity {
	
	private Integer userId;
	
	private Integer recruitmentId;
	
	private String name;
	
	private String mobile;
	
	private Integer state;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRecruitmentId() {
		return recruitmentId;
	}

	public void setRecruitmentId(Integer recruitmentId) {
		this.recruitmentId = recruitmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	

}
