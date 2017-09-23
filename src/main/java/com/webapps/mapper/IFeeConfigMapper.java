package com.webapps.mapper;

import java.util.List;

import com.webapps.common.entity.FeeConfig;
import com.webapps.common.form.FeeConfigRequestForm;
import org.apache.ibatis.annotations.Param;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IFeeConfigMapper extends IBaseMapper<FeeConfig>,IPageMapper<FeeConfig,FeeConfigRequestForm>{
	
	List<FeeConfig> queryAllByDataState(@Param("dataState") Integer dataState);
	
}
