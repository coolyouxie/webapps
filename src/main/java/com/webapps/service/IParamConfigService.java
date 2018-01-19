package com.webapps.service;

import java.math.BigDecimal;
import java.util.List;

import com.webapps.common.entity.ParamConfig;
import com.webapps.common.form.ParamConfigRequestForm;
import com.webapps.service.impl.ParamConfigService.ParamConfigType;

public interface IParamConfigService {
	
	int saveParamConfig(ParamConfigRequestForm form)throws Exception;
	
	int deleteParamConfigById(Integer id)throws Exception;
	
	ParamConfig getById(Integer id)throws Exception;
	
	List<ParamConfig> queryAll()throws Exception;

	/**
	 * 根据paramConfig对象，获取最终的红包金额
	 * @author scorpio.yang
	 * @since 2018-01-17
	 * @param pc
	 * @return
	 */
	public BigDecimal getParamConfigPrice(ParamConfig pc);
	
	/**
	 * 根据红包类型，获取对应的红包配置对象
	 * @author scorpio.yang
	 * @since 2018-01-19
	 * @param type
	 * @return
	 */
	public ParamConfig getParamConfigByAwardType(ParamConfigType type);
	
}
