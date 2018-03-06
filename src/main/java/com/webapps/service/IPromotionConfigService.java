package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.PromotionConfig;
import com.webapps.common.form.PromotionConfigRequestForm;

import java.rmi.server.ExportException;
import java.util.List;

public interface IPromotionConfigService {
	

	Page loadPageList(Page page, PromotionConfigRequestForm form) throws Exception;
	
	/**
	 * 增加方法，用于收入信息的新增操作
	 * @author xieshuai
	 * @since 2018-03-02
	 * @param config
	 * @return
	 */
	public ResultDto<PromotionConfig> addPromotionConfig(PromotionConfig config) throws Exception;

	public ResultDto<String> deletePromotionConfigById(Integer id)throws Exception;

	List<PromotionConfig> queryAllPromotionConfig() throws Exception;

	ResultDto<String> updateStatusById(Integer id,int status)throws Exception;

	ResultDto<String> updatePromotionConfig(PromotionConfigRequestForm form)throws Exception;

	PromotionConfig getById(Integer id)throws Exception;

	ResultDto<String> updateStatusDate(PromotionConfigRequestForm form)throws Exception;
	
	List<PromotionConfig> queryPromotionConfig(PromotionConfigRequestForm form)throws Exception;
	
}
