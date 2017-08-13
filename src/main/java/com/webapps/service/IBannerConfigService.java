package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.form.BannerConfigRequestForm;

public interface IBannerConfigService {
	
	public ResultDto<BannerConfig> saveBannerConfig(BannerConfigRequestForm form)throws Exception;
	
	public int deleteBannerConfigById(Integer id)throws Exception;
	
	public Page loadBannerConfigList(Page page,BannerConfigRequestForm form)throws Exception;
	
	public BannerConfig getById(Integer id)throws Exception;
	
	public List<BannerConfig> queryAll()throws Exception;
	
}
