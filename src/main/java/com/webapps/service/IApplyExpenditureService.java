package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.form.ApplyExpenditureRequestForm;

public interface IApplyExpenditureService {
	

	public Page loadPageList(Page page,ApplyExpenditureRequestForm apply);
	
	public ResultDto<String> approveById(Integer id,Integer state,Integer approverId,String reason);
	
	public ResultDto<String> applyExpenditure(Integer userId,Integer walletId);
	
	
	
}
