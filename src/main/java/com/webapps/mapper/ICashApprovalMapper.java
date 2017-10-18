package com.webapps.mapper;

import java.util.List;

import com.webapps.common.entity.FeeConfig;
import com.webapps.common.form.FeeConfigRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface ICashApprovalMapper extends IBaseMapper<FeeConfig>,IPageMapper<FeeConfig,FeeConfigRequestForm>{
	
	List<FeeConfig> queryAllByDataState(Integer dataState);
	
}
