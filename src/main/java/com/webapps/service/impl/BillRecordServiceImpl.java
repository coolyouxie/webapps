package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BillRecord;
import com.webapps.common.entity.User;
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

	/**
	 * 增加方法，用于收入信息的新增操作
	 * @author scorpio.yang
	 * @since 2018-01-23
	 * @param walletId
	 * @param fee
	 * @param type
	 * @param user
	 * @param enrollmentId
	 * @return
	 */
	public ResultDto<String> addBillRecord(int walletId, BigDecimal fee, int type, User user, Integer enrollmentId){
		ResultDto<String> dto = new ResultDto<String>();
		BillRecord br = new BillRecord();
		br.setWalletId(walletId);
		br.setFee(fee);
		br.setType(type);
		br.setUser(user);
		br.setEnrollmentId(enrollmentId);
		int i = iBillRecordMapper.insert(br);
		if(i == 1) {
			dto.setResult("S");
			dto.setErrorMsg("收入信息保存成功。");
		}else {
			dto.setResult("F");
			dto.setErrorMsg("收入信息保存异常。");
			dto.setData("收入信息保存失败");
		}
		return dto;
	}
	
}
