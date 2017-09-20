package com.webapps.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.FeeConfig;
import com.webapps.common.form.FeeConfigRequestForm;
import com.webapps.service.IFeeConfigService;

@Controller
@RequestMapping("feeConfig")
public class FeeConfigController {
	
	@Autowired
	private IFeeConfigService iFeeConfigService;
	
	@RequestMapping("/toFeeConfigPage")
	public String toFeeConfigPage(Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			List<FeeConfig> list = iFeeConfigService.queryAll();
			if(CollectionUtils.isNotEmpty(list)){
				for(FeeConfig fc:list){
					if(fc.getType()!=null) {
						if (fc.getType() == 1) {
							model.addAttribute("cashback", fc);
						} else if (fc.getType() == 2) {
							model.addAttribute("redPackets", fc);
						} else if (fc.getType() == 3) {
							model.addAttribute("recommendFee", fc);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/config/feeConfig";
	}
		
	/**
	 * 新增或更新发布单信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveFeeConfig")
	public ResultDto<FeeConfig> saveFeeConfig(Model model,FeeConfigRequestForm form,HttpServletRequest request,HttpServletResponse response){
		ResultDto<FeeConfig> dto = new ResultDto<FeeConfig>();
		if(null!=form){
			int result;
			try {
				result = iFeeConfigService.saveFeeConfig(form);
				if(result == 0){
					if(form.getId()==null){
						dto.setResult("F");
						dto.setErrorMsg("新增配置信息失败，请稍后重试");
					}else{
						dto.setResult("F");
						dto.setErrorMsg("更新配置信息失败，请稍后重试");
					}
				}else{
					dto.setResult("S");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

}
