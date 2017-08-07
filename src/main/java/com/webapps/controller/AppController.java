package com.webapps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="appServer")
public class AppController {
	
	/**
	 * app端登录接口
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login")
	public String login(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/register")
	public String register(String params){
		
		return null;
	}
	
	/**
	 * 获取验证码，并保存到数据库
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getValidatCode")
	public String getValidatCode(String params){
		
		return null;
	}
	
	/**
	 * app端获取用户推荐列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUserRecommomend")
	public String getUserRecommomend(String params){
		
		return null;
	}

	/**
	 * 获取用户报名列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUserEnroll")
	public String getUserEnroll(String params){
		
		return null;
	}
	
	/**
	 * 获取发布单列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getRecruitmentList")
	public String getRecruitmentList(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getRecruitmentDetail")
	public String getRecruitmentDetail(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getCompanyInfo")
	public String getCompanyInfo(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getFeeConfig")
	public String getFeeConfig(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBanner")
	public String getBanner(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBanner")
	public String getMessageConfig(String params){
		
		return null;
	}
	
	/**
	 * 提现申请
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/applyCashback")
	public String applyCashback(String params){
		
		return null;
	}
	
}
