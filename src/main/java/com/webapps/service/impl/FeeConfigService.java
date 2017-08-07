package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.FeeConfig;
import com.webapps.common.form.FeeConfigRequestForm;
import com.webapps.mapper.IFeeConfigMapper;
import com.webapps.service.IFeeConfigService;

@Service
@Transactional
public class FeeConfigService implements IFeeConfigService {
	
	@Autowired
	private IFeeConfigMapper iFeeConfigMapper;

	@Override
	public int saveFeeConfig(FeeConfigRequestForm form) throws Exception {
		int result = 0;
		if(form.getId()==null){
			result = iFeeConfigMapper.insert(form);
		}else{
			result = iFeeConfigMapper.updateById(form.getId(),form);
		}
		return result;
	}

	@Override
	public int deleteFeeConfigById(Integer id) throws Exception {
		int result = iFeeConfigMapper.deleteByIdInLogic(id);
		return result;
	}

	@Override
	public Page loadFeeConfigList(Page page, FeeConfigRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iFeeConfigMapper.queryCount(form);
		List<FeeConfig> list = iFeeConfigMapper.queryPage(startRow, endRow, form);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public FeeConfig getById(Integer id) throws Exception {
		FeeConfig feeConfig = iFeeConfigMapper.getById(id);
		return feeConfig;
	}

	@Override
	public List<FeeConfig> queryAll() throws Exception {
		List<FeeConfig> list = iFeeConfigMapper.queryAll();
		return list;
	}

}
