package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.AliSmsMsg;
import com.webapps.common.entity.BannerConfig;
import org.springframework.stereotype.Repository;


@Repository
public interface IAliSmsMsgMapper extends IBaseMapper<AliSmsMsg>{
	
	List<AliSmsMsg> getByPhoneNumTypeAndState(@Param("phoneNum") String phoneNum, @Param("type") Integer type, @Param("dataState") Integer dataState)throws Exception;
	
	int batchDeleteInLogic(List<BannerConfig> list)throws Exception;
	
	List<AliSmsMsg> queryByPhoneNumType(@Param("phoneNum") String phoneNum, @Param("type") Integer type) throws Exception;
}
