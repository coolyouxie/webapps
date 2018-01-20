package com.webapps.service.impl;

import com.webapps.common.bean.Page;
import com.webapps.common.dto.RateDto;
import com.webapps.common.form.RateDtoRequestForm;
import com.webapps.mapper.IRateDtoMapper;
import com.webapps.service.IRateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RateServiceImpl implements IRateService {
	
	private static Logger logger = Logger.getLogger(RateServiceImpl.class);

	@Autowired
	private IRateDtoMapper iRateDtoMapper;

	@Override
	public Page loadRateDtoList(Page page, RateDtoRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iRateDtoMapper.queryCount(form);
		List<RateDto> list = iRateDtoMapper.queryPage(startRow, rows, form);
		page.setResultList(list);
		page.setRecords(count);
		page.countRecords(count);
		return page;
	}
}
