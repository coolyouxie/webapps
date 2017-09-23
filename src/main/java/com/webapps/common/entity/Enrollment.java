package com.webapps.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Enrollment extends Entity {
	
	private User user;
	
	private Recruitment recruitment;
	
	private Company company;
	
	private Integer state;
	
	private Integer isTalked;
	
	private String talkResult;
	
	private String remark;
	
	private String failedReason;
	
	private Date entryDate;
	
	private BigDecimal reward;

	private Integer cashbackDays;

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public BigDecimal getReward() {
		return reward;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCashbackDays() {
		return cashbackDays;
	}

	public void setCashbackDays(Integer cashbackDays) {
		this.cashbackDays = cashbackDays;
	}

}
