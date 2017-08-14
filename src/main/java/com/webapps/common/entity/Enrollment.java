package com.webapps.common.entity;

import com.webapps.common.utils.DateUtil;

public class Enrollment extends Entity {
	
	private User user;
	
	private Recruitment recruitment;
	
	private Company company;
	
	private Integer state;
	
	private Integer isTalked;
	
	private String talkResult;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Recruitment getRecruitment() {
		return recruitment;
	}

	public void setRecruitment(Recruitment recruitment) {
		this.recruitment = recruitment;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String getCreateTimeStr(){
		if(this.getCreateTime()!=null){
			return DateUtil.format(this.getCreateTime(), DateUtil.SIMPLE_PATTERN);
		}
		return null;
	}

	public Integer getIsTalked() {
		return isTalked;
	}

	public void setIsTalked(Integer isTalked) {
		this.isTalked = isTalked;
	}

	public String getTalkResult() {
		return talkResult;
	}

	public void setTalkResult(String talkResult) {
		this.talkResult = talkResult;
	}

}
