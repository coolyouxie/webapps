package com.webapps.service;

import com.aliyuncs.exceptions.ClientException;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.AliSmsMsg;

public interface IAliSmsMsgService {
	
	ResultDto<AliSmsMsg> getAliSmsCode(String phoneNum, Integer type);
	
	ResultDto<String> validateAliSmsCode(Integer aliSmsMsgId, String msgCode);
	
	ResultDto<String> validateAliSmsCode(String phoneNum, Integer type, String msgCode);
	
	/**
	 * 发送用户邀请码到指定的手机号码
	 * @param phoneNum
	 * @param inviteCode
	 * @return
	 * @throws ClientException 
	 */
	public ResultDto<String> sendInviteCode(String phoneNum , String inviteCode) throws ClientException;

}
