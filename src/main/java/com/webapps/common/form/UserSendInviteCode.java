package com.webapps.common.form;

/**
 * 用户发送邀请码，app端发送的对象
 * @author scorpio.yang
 * @since 2018-01-15
 *
 */
public class UserSendInviteCode {

	private Integer fromUserId;

	private String toPhone;
	
	/**
	 * 增加被邀请人姓名，用户红包列表用户名称显示
	 * @author scorpio.yang
	 * @since 2018-01-20
	 */
	private String inviteUserName;

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToPhone() {
		return toPhone;
	}

	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

	public String getInviteUserName() {
		return inviteUserName;
	}

	public void setInviteUserName(String inviteUserName) {
		this.inviteUserName = inviteUserName;
	}
	
	
}
