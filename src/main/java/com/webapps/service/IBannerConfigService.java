package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.form.BannerConfigRequestForm;

public interface IBannerConfigService {
	
	ResultDto<BannerConfig> saveBannerConfig(BannerConfigRequestForm form);
	
	int deleteBannerConfigById(BannerConfigRequestForm form);
	
	Page loadBannerConfigList(Page page, BannerConfigRequestForm form);
	
	BannerConfig getById(Integer id);
	
	List<BannerConfig> queryAll();
	
}
