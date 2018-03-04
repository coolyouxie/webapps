package com.webapps.common.entity;

import com.webapps.common.utils.DateUtil;

import java.util.Date;

public class UserAwardExchange extends Entity {

	private Integer userId;
	private String userName;
	private String userMobile;
	private Date enrollTime;
	private Integer awardConfigId;
	private String awardName;
	private int status;
	private Date exchangeTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Date getEnrollTime() {
		return enrollTime;
	}

	public void setEnrollTime(Date enrollTime) {
		this.enrollTime = enrollTime;
	}

	public Integer getAwardConfigId() {
		return awardConfigId;
	}

	public void setAwardConfigId(Integer awardConfigId) {
		this.awardConfigId = awardConfigId;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public String getEnrollTimeFullStr() {
		if(this.getEnrollTime()!=null){
			return DateUtil.format(this.getEnrollTime(),DateUtil.FULL_PATTERN);
		}
		return "";
	}

	public String getExchangeTimeFullStr() {
		if(this.getExchangeTime()!=null){
			return DateUtil.format(this.getExchangeTime(),DateUtil.FULL_PATTERN);
		}
		return "";
	}

	public String getStatusStr(){
		if(this.getStatus()==0){
			return "未领取";
		}else{
			return "已领取";
		}
	}
}
