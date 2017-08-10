package com.webapps.common.entity;

import java.math.BigDecimal;

public class UserWallet extends Entity {

	private Integer userId;
	
	private BigDecimal fee;

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
	
	
}
