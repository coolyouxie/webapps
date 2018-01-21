package com.webapps.common.dto;

import java.util.List;

import com.webapps.common.entity.FeeConfig;
import com.webapps.common.entity.ParamConfig;

public class AppConfigDTO {
	
	private List<FeeConfig> feeConfigList;
	private List<ParamConfig> paramConfigList;
	
	public List<FeeConfig> getFeeConfigList() {
		return feeConfigList;
	}
	public void setFeeConfigList(List<FeeConfig> feeConfigList) {
		this.feeConfigList = feeConfigList;
	}
	public List<ParamConfig> getParamConfigList() {
		return paramConfigList;
	}
	public void setParamConfigList(List<ParamConfig> paramConfigList) {
		this.paramConfigList = paramConfigList;
	}

}
