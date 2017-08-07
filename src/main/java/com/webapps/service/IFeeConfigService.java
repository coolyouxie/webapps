package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.FeeConfig;
import com.webapps.common.form.FeeConfigRequestForm;

public interface IFeeConfigService {
	
	public int saveFeeConfig(FeeConfigRequestForm form)throws Exception;
	
	public int deleteFeeConfigById(Integer id)throws Exception;
	
	public Page loadFeeConfigList(Page page,FeeConfigRequestForm form)throws Exception;
	
	public FeeConfig getById(Integer id)throws Exception;
	
	public List<FeeConfig> queryAll()throws Exception;

}
