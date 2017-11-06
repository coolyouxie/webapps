package com.webapps.common.entity;

import java.math.BigDecimal;

public class UserReward extends Entity {

	private Integer userId;
	
	private BigDecimal fee;
	
	private int type;
	
	private int leftTimes;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLeftTimes() {
		return leftTimes;
	}

	public void setLeftTimes(int leftTimes) {
		this.leftTimes = leftTimes;
	}

		
	
}
