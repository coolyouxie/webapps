package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.FeeConfig;
import com.webapps.common.form.FeeConfigRequestForm;

public interface IFeeConfigService {
	
	int saveFeeConfig(FeeConfigRequestForm form)throws Exception;
	
	int deleteFeeConfigById(Integer id)throws Exception;
	
	Page loadFeeConfigList(Page page, FeeConfigRequestForm form)throws Exception;
	
	FeeConfig getById(Integer id)throws Exception;
	
	List<FeeConfig> queryAll()throws Exception;

}
