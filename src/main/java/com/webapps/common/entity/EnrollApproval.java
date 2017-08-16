package com.webapps.common.entity;

import com.webapps.common.utils.DateUtil;

public class EnrollApproval extends Enrollment {
	
	private Integer state;
	
	private Integer operatorId;

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

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

}
