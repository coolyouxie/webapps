package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.form.BannerConfigRequestForm;
import com.webapps.service.IBannerConfigService;

@Controller
@RequestMapping(value="bannerConfig")
public class BannerConfigController {
	
	@Autowired
	private IBannerConfigService iBannerConfigService;
	
	@RequestMapping(value="/toBannerConfigPage")
	public String toBannerConfigPage(Model model,HttpServletRequest request,HttpServletResponse response){
		return "/config/bannerConfigList";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadBannerConfigList")
	public Page loadBannerConfigList(Model model,Page page,BannerConfigRequestForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			page = iBannerConfigService.loadBannerConfigList(page,form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping(value="/toBannerConfigAddPage")
	public String toBannerConfigAddPage(){
		return "/config/addBannerConfig";
	}
	
	@RequestMapping(value="/toBannerConfigEditPage")
	public String toBannerConfigEditPage(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		try {
			BannerConfig bc = iBannerConfigService.getById(id);
			model.addAttribute("bannerConfig", bc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/config/editBannerConfig";
	}
	
	/**
	 * 新增或更新发布单信息
	 * @param model
	 * @param recruitment
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveBannerConfig")
	public ResultDto<BannerConfig> saveBannerConfig(Model model,BannerConfigRequestForm form,HttpServletRequest request,HttpServletResponse response){
		ResultDto<BannerConfig> dto = null;
		if(null!=form){
			try {
				dto = iBannerConfigService.saveBannerConfig(form);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@ResponseBody
	@RequestMapping("/deleteBannerConfigById")
	public ResultDto<BannerConfig> deleteBannerConfigById(Model model, Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		ResultDto<BannerConfig> dto = new ResultDto<BannerConfig>();
		int result;
		try {
			result = iBannerConfigService.deleteBannerConfigById(id);
			if (result == 0) {
				dto.setResult("fail");
				dto.setErrorMsg("删除失败，请稍后重试");
			} else {
				dto.setResult("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	@RequestMapping(value="/toAddBannerPicturePage")
	public String toAddBannerPicturePage(Model model,Integer id,Integer type,String title){
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("title", title);
		return "/config/addBannerPicture";
	}
}
