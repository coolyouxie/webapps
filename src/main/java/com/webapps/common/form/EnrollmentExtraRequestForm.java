package com.webapps.common.form;

import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.entity.EnrollmentExtra;

import java.io.Serializable;

public class EnrollmentExtraRequestForm extends EnrollmentExtra implements RequestForm,Serializable{

	private static final long serialVersionUID = -7915557175531200072L;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;

	public String getApplyTimeStart() {
		return applyTimeStart;
	}

	public void setApplyTimeStart(String applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}

	public String getApplyTimeEnd() {
		return applyTimeEnd;
	}

	public void setApplyTimeEnd(String applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}

}
