package com.webapps.service;

import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.AliSmsMsg;

public interface IAliSmsMsgService {
	
	public ResultDto<AliSmsMsg> getAliSmsCode(String phoneNum,Integer type);
	
	public ResultDto<String> validateAliSmsCode(Integer aliSmsMsgId,String msgCode);
	

}
