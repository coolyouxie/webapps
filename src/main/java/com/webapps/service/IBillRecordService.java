package com.webapps.service;

import java.math.BigDecimal;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.User;
import com.webapps.common.form.BillRecordRequestForm;

public interface IBillRecordService {
	

	Page loadPageList(Page page, BillRecordRequestForm form) throws Exception;
	
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
	public ResultDto<String> addBillRecord(int walletId, BigDecimal fee, int type, User user, Integer enrollmentId);
	
}
