package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.form.BannerConfigRequestForm;

public interface IBannerConfigService {
	
	public ResultDto<BannerConfig> saveBannerConfig(BannerConfigRequestForm form);
	
	public int deleteBannerConfigById(BannerConfigRequestForm form);
	
	public Page loadBannerConfigList(Page page,BannerConfigRequestForm form);
	
	public BannerConfig getById(Integer id);
	
	public List<BannerConfig> queryAll();
	
}
