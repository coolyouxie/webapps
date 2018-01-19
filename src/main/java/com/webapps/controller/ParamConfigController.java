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
import com.webapps.common.entity.ParamConfig;
import com.webapps.common.form.ParamConfigRequestForm;
import com.webapps.service.IParamConfigService;

@Controller
@RequestMapping("paramConfig")
public class ParamConfigController {
	
	@Autowired
	private IParamConfigService iParamConfigService;
	
	@RequestMapping("/toParamConfigPage")
	public String toParamConfigPage(Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			List<ParamConfig> list = iParamConfigService.queryAll();
			model.addAttribute("paramConfigList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/config/paramConfig";
	}
		
	/**
	 * 新增或更新发布单信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveParamConfig")
	public ResultDto<ParamConfig> saveParamConfig(Model model,ParamConfigRequestForm form,HttpServletRequest request,HttpServletResponse response){
		ResultDto<ParamConfig> dto = new ResultDto<ParamConfig>();
		if(null!=form){
			int result;
			try {
				result = iParamConfigService.saveParamConfig(form);
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
