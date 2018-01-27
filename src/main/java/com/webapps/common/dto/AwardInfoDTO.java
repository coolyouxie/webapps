package com.webapps.common.dto;

import java.math.BigDecimal;

public class AwardInfoDTO {

	private Integer id;
	private Integer paramConfigId;
	private BigDecimal price;
	private Integer stat;
	public Integer getParamConfigId() {
		return paramConfigId;
	}
	public void setParamConfigId(Integer paramConfigId) {
		this.paramConfigId = paramConfigId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStat() {
		return stat;
	}
	public void setStat(Integer stat) {
		this.stat = stat;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
