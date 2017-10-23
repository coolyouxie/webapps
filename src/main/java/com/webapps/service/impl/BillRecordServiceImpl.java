package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.BillRecord;
import com.webapps.common.form.BillRecordRequestForm;
import com.webapps.mapper.IBillRecordMapper;
import com.webapps.service.IBillRecordService;

@Service
@Transactional
public class BillRecordServiceImpl implements IBillRecordService {
	
	@Autowired
	private IBillRecordMapper iBillRecordMapper;

	@Override
	public Page loadPageList(Page page, BillRecordRequestForm form) {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iBillRecordMapper.queryCount(form);
		List<BillRecord> list = iBillRecordMapper.queryPage(startRow, rows, form);
		page.setRecords(count);
		page.setResultList(list);
		page.countRecords(count);
		return page;
	}

}
