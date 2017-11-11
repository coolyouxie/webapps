package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.Province;
import com.webapps.common.entity.User;
import com.webapps.common.form.ProvinceRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IProvinceMapper extends IBaseMapper<Province>{
	
	Province getByCode(String code) throws Exception;
	
	int batchUpdate(List<Province> list) throws Exception;
	
	List<Province> queryByParentId(Integer parentId) throws Exception;
	
	List<Province> queryByLevel(Integer level) throws Exception;

}
