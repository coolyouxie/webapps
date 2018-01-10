package com.webapps.common.dto;


/**
 * 专员待审核记录统计类
 * @author xieshuai
 *
 */
public class UserApproveCountDto {

	private int count;

	private Integer userId;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
