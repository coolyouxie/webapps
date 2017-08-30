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
import com.webapps.common.entity.MessageConfig;
import com.webapps.common.form.MessageConfigRequestForm;
import com.webapps.service.IMessageConfigService;

@Controller
@RequestMapping(value="messageConfig")
public class MessageConfigController {
	
	@Autowired
	private IMessageConfigService iMessageConfigService;
	
	@RequestMapping(value="/toMessageConfigPage")
	public String toMessageConfigPage(Model model,HttpServletRequest request,HttpServletResponse response){
		return "/config/messageConfigList";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadMessageConfigList")
	public Page loadMessageConfigList(Model model,Page page,MessageConfigRequestForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			page = iMessageConfigService.loadMessageConfigList(page,form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping(value="/toMessageConfigAddPage")
	public String toMessageConfigAddPage(){
		return "/config/addMessageConfig";
	}
	
	@RequestMapping(value="/toMessageConfigEditPage")
	public String toMessageConfigEditPage(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		try {
			MessageConfig bc = iMessageConfigService.getById(id);
			model.addAttribute("bannerConfig", bc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/config/editMessageConfig";
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
	@RequestMapping("/saveMessageConfig")
	public ResultDto<MessageConfig> saveMessageConfig(Model model,MessageConfigRequestForm form,HttpServletRequest request,HttpServletResponse response){
		ResultDto<MessageConfig> dto = null;
		if(null!=form){
			try {
				dto = iMessageConfigService.saveMessageConfig(form);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@ResponseBody
	@RequestMapping(value="/deleteMessageConfigById")
	public ResultDto<MessageConfig> deleteMessageConfigById(Model model, MessageConfigRequestForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ResultDto<MessageConfig> dto = new ResultDto<MessageConfig>();
		int result;
		try {
			result = iMessageConfigService.deleteMessageConfigById(form);
			if (result == 0) {
				dto.setResult("F");
				dto.setErrorMsg("删除失败，请稍后重试");
			} else {
				dto.setResult("S");
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
