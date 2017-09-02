package com.webapps.common.entity;

import java.math.BigDecimal;

public class UserWallet extends Entity {

	private Integer userId;
	
	private BigDecimal fee;
	
	private Integer state;

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
