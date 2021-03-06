package com.webapps.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.AliSmsMsg;
import com.webapps.common.entity.User;
import com.webapps.common.utils.DateUtil;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.common.utils.SmsUtil;
import com.webapps.mapper.IAliSmsMsgMapper;
import com.webapps.mapper.IUserMapper;
import com.webapps.service.IAliSmsMsgService;

@Service
@Transactional
public class AliSmsMsgService implements IAliSmsMsgService {
	
	private static Logger logger = Logger.getLogger(AliSmsMsgService.class);
	
	@Autowired
	private IAliSmsMsgMapper iAliSmsMsgMapper;
	
	@Autowired
	private IUserMapper iUserMapper;

	@Override
	public ResultDto<AliSmsMsg> getAliSmsCode(String phoneNum,Integer type) {
		ResultDto<AliSmsMsg> dto = new ResultDto<AliSmsMsg>();
		try {
			User user = iUserMapper.queryUserByAccount(phoneNum);
			if(type==1){
				if(user!=null&&user.getId()!=null){
					dto.setResult("F");
					dto.setErrorMsg("该手机号已被注册");
					return dto;
				}
			}else if(type==2){
				if(user==null){
					dto.setResult("F");
					dto.setErrorMsg("该手机号还未注册会员信息");
					return dto;
				}
			}
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
			asm.setValidateCode((String)resultMap.get("validateCode"));
			asm.setType(type);
			int count = iAliSmsMsgMapper.insert(asm);
			if(count==1){
				dto.setResult("S");
			}else{
				dto.setErrorMsg("短信保存数据库失败，请稍后重试");
				dto.setResult("F");
			}
			AliSmsMsg asm1 = new AliSmsMsg();
			asm1.setId(asm.getId());
			asm1.setCreateTime(null);
			asm1.setUpdateTime(null);
			dto.setData(asm1);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	/**
	 * 发送用户邀请码到指定的手机号码
	 * @param phoneNum
	 * @param inviteCode
	 * @return
	 * @throws ClientException 
	 */
	public ResultDto<String> sendInviteCode(String phoneNum , String inviteCode) throws ClientException{
		ResultDto<String> dto = new ResultDto<String>();
		Map<String,Object> resultMap = SmsUtil.sendInviteSms(phoneNum,inviteCode);
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
		asm.setValidateCode((String)resultMap.get("validateCode"));
		asm.setType(3);
		int count = iAliSmsMsgMapper.insert(asm);
		if(count==1){
			dto.setResult("S");
		}else{
			dto.setErrorMsg("短信保存数据库失败，请稍后重试");
			dto.setResult("F");
		}
		return dto;
	}

	@Override
	public ResultDto<String> validateAliSmsCode(Integer aliSmsMsgId, String msgCode) {
		ResultDto<String> dto = new ResultDto<String>();
		AliSmsMsg asm = null;
		try {
			asm = iAliSmsMsgMapper.getById(aliSmsMsgId);
			if(asm==null){
				dto.setResult("F");
				dto.setErrorMsg("请先发送验证码");
				return dto;
			}
			int minitus = DateUtil.getMinsBetweenTwoDate(asm.getCreateTime(), new Date());
			int overtimeMins = Integer.valueOf((String)PropertiesUtil.getProperty("code_timeout"));
			if(minitus>overtimeMins){
				dto.setErrorMsg("验证码已过期");
				dto.setResult("F");
				return dto;
			}
			if(!asm.getValidateCode().equals(msgCode.trim())){
				dto.setErrorMsg("验证码错误");
				dto.setResult("F");
				return dto;
			}
			dto.setResult("S");
			return dto;
		}catch(Exception e){
			logger.error("查询验证码异常："+e.getMessage());
		}
		return null;
	}

	@Override
	public ResultDto<String> validateAliSmsCode(String phoneNum, Integer type, String msgCode) {
		ResultDto<String> dto = new ResultDto<String>();
		try {
			List<AliSmsMsg> list = iAliSmsMsgMapper.getByPhoneNumTypeAndState(phoneNum, type, 1);
			if(CollectionUtils.isEmpty(list)){
				dto.setErrorMsg("验证码错误");
				dto.setResult("F");
				return dto;
			}
			AliSmsMsg asm = list.get(0);
			int minitus = DateUtil.getMinsBetweenTwoDate(asm.getCreateTime(), new Date());
			int overtimeMins = Integer.valueOf((String)PropertiesUtil.getProperty("code_timeout"));
			if(minitus>overtimeMins){
				dto.setErrorMsg("验证码已过期");
				dto.setResult("F");
				return dto;
			}
			if(!asm.getValidateCode().equals(msgCode.trim())){
				dto.setErrorMsg("输入难码错误");
				dto.setResult("F");
				return dto;
			}
			dto.setResult("S");
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("校验验证码时异常");
			dto.setResult("F");
			return dto;
		}
	}

}
