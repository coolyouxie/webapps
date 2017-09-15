package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.form.BillRecordRequestForm;

public interface IBillRecordService {
	

	public Page loadPageList(Page page,BillRecordRequestForm form);
	
}
