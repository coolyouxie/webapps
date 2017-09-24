package com.webapps.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EnrollmentExtra extends Entity {

	private Integer enrollmentId;
	
	private Integer state;
	
	private BigDecimal fee;

	private Integer cashbackDays;

	public Integer getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Integer enrollmentId) {
		this.enrollmentId = enrollmentId;
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

	public Integer getCashbackDays() {
		return cashbackDays;
	}

	public void setCashbackDays(Integer cashbackDays) {
		this.cashbackDays = cashbackDays;
	}

}
