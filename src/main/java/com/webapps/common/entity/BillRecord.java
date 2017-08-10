package com.webapps.common.entity;

import java.math.BigDecimal;

public class BillRecord extends Entity {

	private Integer userId;
	
	private BigDecimal fee;
	
	private Integer type;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
