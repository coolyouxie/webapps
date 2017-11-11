package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.form.ApplyExpenditureRequestForm;

public interface IApplyExpenditureService {
	

	Page loadPageList(Page page, ApplyExpenditureRequestForm apply);
	
	ResultDto<String> approveById(Integer id, Integer state, Integer approverId, String reason);
	
	ResultDto<String> applyExpenditure(Integer userId, Integer walletId);
	
	
	
}
