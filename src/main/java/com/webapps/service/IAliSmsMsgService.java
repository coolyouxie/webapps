package com.webapps.service;

import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.AliSmsMsg;

public interface IAliSmsMsgService {
	
	ResultDto<AliSmsMsg> getAliSmsCode(String phoneNum, Integer type);
	
	ResultDto<String> validateAliSmsCode(Integer aliSmsMsgId, String msgCode);
	
	ResultDto<String> validateAliSmsCode(String phoneNum, Integer type, String msgCode);
	

}
