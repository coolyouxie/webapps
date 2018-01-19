package com.webapps.common.entity;

import java.math.BigDecimal;

/**
 * 用户红包信息
 * @author scorpio.yang
 * @since 2018-01-17
 */
public class UserAward extends Entity{

	private Integer userId;
	private Integer inviteUserId;
	private Integer paramConfigId;
	private BigDecimal price;
	private Integer stat;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getInviteUserId() {
		return inviteUserId;
	}
	public void setInviteUserId(Integer inviteUserId) {
		this.inviteUserId = inviteUserId;
	}
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
	
}
