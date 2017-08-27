package com.webapps.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.AliSmsMsg;
import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;
import com.webapps.common.utils.SmsUtil;
import com.webapps.mapper.IAliSmsMsgMapper;
import com.webapps.service.IAliSmsMsgService;

@Service
@Transactional
public class AliSmsMsgService implements IAliSmsMsgService {
	
	private static Logger logger = Logger.getLogger(AliSmsMsgService.class);
	
	@Autowired
	private IAliSmsMsgMapper iAliSmsMsgMapper;

	@Override
	public ResultDto<AliSmsMsg> getAliSmsCode(String phoneNum,Integer type) {
		ResultDto<AliSmsMsg> dto = new ResultDto<AliSmsMsg>();
		try {
			Map<String,Object> resultMap = SmsUtil.sendSms(phoneNum);
			if(resultMap==null){
				dto.setResult("F");
				dto.setErrorMsg("短信发送失败，请稍后重试");
				logger.error("短信发送失败，请稍后重试");
				return dto;
			}
			SendSmsResponse response = (SendSmsResponse) resultMap.get("response");
			if(response==null){
				dto.setResult("F");
				dto.setErrorMsg("短信响应内容为空，请稍后重试");
				logger.error("短信响应内容为空，请稍后重试");
				return dto;
			}
			SendSmsRequest request = (SendSmsRequest) resultMap.get("request");
			AliSmsMsg asm = new AliSmsMsg();
			asm.setBizId(response.getBizId());
			asm.setCode(response.getCode());
			asm.setCreateTime(new Date());
			asm.setDataState(1);
			asm.setMessage(response.getMessage());
			asm.setPhoneNumbers(phoneNum);
			asm.setRequestId(response.getRequestId());
			asm.setSignName(request.getSignName());
			asm.setSmsUpExtendCode(request.getSmsUpExtendCode());
			asm.setTemplateParam(request.getTemplateParam());
			asm.setTemplateCode(request.getTemplateCode());
			int count = iAliSmsMsgMapper.insert(asm);
			if(count==1){
				dto.setResult("S");
			}else{
				dto.setErrorMsg("短信保存数据库失败，请稍后重试");
				dto.setResult("F");
			}
			asm.setBizId(null);
			asm.setOutId(null);
			asm.setTemplateParam(null);
			asm.setTemplateCode(null);
			asm.setSmsUpExtendCode(null);
			asm.setSignName(null);
			asm.setRequestId(null);
			dto.setData(asm);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public ResultDto<String> validateAliSmsCode(Integer aliSmsMsgId, String msgCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
