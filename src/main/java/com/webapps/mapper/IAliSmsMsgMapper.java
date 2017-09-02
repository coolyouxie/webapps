package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.AliSmsMsg;
import com.webapps.common.entity.BannerConfig;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IAliSmsMsgMapper extends IBaseMapper<AliSmsMsg>{
	
	public List<AliSmsMsg> getByPhoneNumTypeAndState(@Param("phoneNum")String phoneNum,@Param("type")Integer type,@Param("dataState") Integer dataState)throws Exception;
	
	public int batchDeleteInLogic(List<BannerConfig> list)throws Exception;
}