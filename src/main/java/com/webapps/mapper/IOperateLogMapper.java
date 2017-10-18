package com.webapps.mapper;

import com.webapps.common.entity.OperateLog;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IOperateLogMapper extends IBaseMapper<OperateLog>,IPageMapper<OperateLog,OperateLog>{
	
}
