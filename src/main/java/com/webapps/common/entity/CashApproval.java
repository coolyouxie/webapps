package com.webapps.common.entity;

import java.math.BigDecimal;

public class CashApproval extends Entity {

	private Integer userId;

	/**
	 * 提现审核状态
	 */
	private Integer state;

	private BigDecimal fee;

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

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

}
