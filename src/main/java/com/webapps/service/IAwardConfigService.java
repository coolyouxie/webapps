package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.AwardConfig;
import com.webapps.common.entity.User;
import com.webapps.common.form.AwardConfigRequestForm;
import com.webapps.common.form.BillRecordRequestForm;

import java.math.BigDecimal;
import java.util.List;

public interface IAwardConfigService {
	

	Page loadPageList(Page page, AwardConfigRequestForm form) throws Exception;
	
	/**
	 * 增加方法，用于收入信息的新增操作
	 * @author xieshuai
	 * @since 2018-03-02
	 * @param config
	 * @return
	 */
	public ResultDto<String> addAwardConfig(AwardConfig config) throws Exception;

	public ResultDto<String> deleteAwardConfigById(Integer id )throws Exception;

	List<AwardConfig> queryAllAwardConfig() throws Exception;
	
}
