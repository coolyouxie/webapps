package com.webapps.common.form;

import com.webapps.common.dto.RateDto;
import com.webapps.common.entity.Province;

import java.io.Serializable;

public class RateDtoRequestForm extends RateDto implements Serializable{

	private static final long serialVersionUID = -7915557175531200072L;
	
	private Double rateStart;

	private Double rateEnd;

	public Double getRateStart() {
		return rateStart;
	}

	public void setRateStart(Double rateStart) {
		this.rateStart = rateStart;
	}

	public Double getRateEnd() {
		return rateEnd;
	}

	public void setRateEnd(Double rateEnd) {
		this.rateEnd = rateEnd;
	}
}
