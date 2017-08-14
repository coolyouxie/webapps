package com.webapps.common.entity;

import com.webapps.common.utils.DateUtil;

public class EnrollmentApproval extends Enrollment {
	
	private Integer state;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreateTimeStr(){
		if(this.getCreateTime()!=null){
			return DateUtil.format(this.getCreateTime(), DateUtil.SIMPLE_PATTERN);
		}
		return null;
	}

}
