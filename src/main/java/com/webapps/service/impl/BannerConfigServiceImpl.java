package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.form.BannerConfigRequestForm;
import com.webapps.mapper.IBannerConfigMapper;
import com.webapps.service.IBannerConfigService;

@Service
@Transactional
public class BannerConfigServiceImpl implements IBannerConfigService {
	
	@Autowired
	private IBannerConfigMapper iBannerConfigMapper;

	@Override
	public int saveBannerConfig(BannerConfigRequestForm form) throws Exception {
		int result = 0;
		if(form.getId()!=null){
			result = iBannerConfigMapper.updateById(form.getId(), form);
		}else{
			result = iBannerConfigMapper.insert(form);
		}
		return result;
	}

	@Override
	public int deleteBannerConfigById(Integer id) throws Exception {
		int result = iBannerConfigMapper.deleteByIdInLogic(id);
		return result;
	}

	@Override
	public Page loadBannerConfigList(Page page, BannerConfigRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iBannerConfigMapper.queryCount(form);
		List<BannerConfig> list = iBannerConfigMapper.queryPage(startRow, endRow, form);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public BannerConfig getById(Integer id) throws Exception {
		BannerConfig bc = iBannerConfigMapper.getById(id);
		return bc;
	}

	@Override
	public List<BannerConfig> queryAll() throws Exception {
		List<BannerConfig> list = iBannerConfigMapper.queryAll();
		return list;
	}

}
