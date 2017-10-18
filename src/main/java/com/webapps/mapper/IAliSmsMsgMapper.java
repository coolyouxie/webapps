package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.AliSmsMsg;
import com.webapps.common.entity.BannerConfig;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IAliSmsMsgMapper extends IBaseMapper<AliSmsMsg>{
	
	public List<AliSmsMsg> getByPhoneNumTypeAndState(@Param("phoneNum")String phoneNum,@Param("type")Integer type,@Param("dataState") Integer dataState)throws Exception;
	
	public int batchDeleteInLogic(List<BannerConfig> list)throws Exception;
	
	public List<AliSmsMsg> queryByPhoneNumType(@Param("phoneNum")String phoneNum,@Param("type")Integer type) throws Exception;
}
