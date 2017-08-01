package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.Province;
import com.webapps.common.entity.User;
import com.webapps.common.form.ProvinceRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IProvinceMapper extends IBaseMapper<Province,ProvinceRequestForm>{
	
	public Province getByCode(String code);
	
	public int batchUpdate(List<Province> list);
	
	public List<Province> queryByParentId(Integer parentId);
	
	public List<Province> queryByLevel(Integer level);

}
