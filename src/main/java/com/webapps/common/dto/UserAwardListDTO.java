package com.webapps.common.dto;

import java.util.List;

public class UserAwardListDTO {
	private Integer inviteUserId;
	private String inviteUserName;
	private String mobile;
	private List<AwardInfoDTO> awardInfo;
	public Integer getInviteUserId() {
		return inviteUserId;
	}
	public void setInviteUserId(Integer inviteUserId) {
		this.inviteUserId = inviteUserId;
	}
	public String getInviteUserName() {
		return inviteUserName;
	}
	public void setInviteUserName(String inviteUserName) {
		this.inviteUserName = inviteUserName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public List<AwardInfoDTO> getAwardInfo() {
		return awardInfo;
	}
	public void setAwardInfo(List<AwardInfoDTO> awardInfo) {
		this.awardInfo = awardInfo;
	}
}
