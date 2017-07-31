package com.webapps.service;

import java.util.List;

import com.webapps.common.entity.Province;

public interface IProvinceService {
	
	public Province getById(Integer id)throws Exception;
	
	/**
	 * 初始化数据时使用的方法
	 * @return
	 * @throws Exception
	 */
	public List<Province> setProvinceParentId()throws Exception;
	
	/**
	 * 根据父级行政区ID查询行政区
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Province> queryProvinceByParentId(Integer parentId)throws Exception;
	
	/**
	 * 根据行政区等级查询行政区信息
	 * @param level
	 * @return
	 * @throws Exception
	 */
	public List<Province> queryProvinceByLevel(Integer level)throws Exception;

}
