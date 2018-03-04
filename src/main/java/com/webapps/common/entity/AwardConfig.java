package com.webapps.common.entity;

import java.math.BigDecimal;

public class AwardConfig extends Entity {

	private String name;
	
	private BigDecimal pr;

	private Integer fkId;

	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPr() {
		return pr;
	}

	public void setPr(BigDecimal pr) {
		this.pr = pr;
	}

	public Integer getFkId() {
		return fkId;
	}

	public void setFkId(Integer fkId) {
		this.fkId = fkId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
