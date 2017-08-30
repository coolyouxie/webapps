package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.MessageConfig;
import com.webapps.common.form.MessageConfigRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IMessageConfigMapper extends IBaseMapper<MessageConfig>,IPageMapper<MessageConfig,MessageConfigRequestForm>{
	
	public List<MessageConfig> getByFkIdAndType(@Param("fkId")Integer fkId,@Param("type")Integer type)throws Exception;
	
	public List<MessageConfig> getByFkIdTypeAndBelongType(@Param("fkId")Integer fkId,@Param("type")Integer type,@Param("belongType")Integer belongType)throws Exception;
	
	public int batchDeleteInLogic(List<MessageConfig> list)throws Exception;
}
