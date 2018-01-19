package com.webapps.common.entity;

import java.math.BigDecimal;

public class ParamConfig extends Entity{

	private String name;
	
	private Integer type;
	
	private BigDecimal minNum;
	
	private BigDecimal maxNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getMinNum() {
		return minNum;
	}

	public void setMinNum(BigDecimal minNum) {
		this.minNum = minNum;
	}

	public BigDecimal getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(BigDecimal maxNum) {
		this.maxNum = maxNum;
	}
	
	
}
