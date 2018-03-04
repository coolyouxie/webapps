package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.PromotionConfig;
import com.webapps.common.form.PromotionConfigRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IPromotionConfigService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("promotionConfig")
public class PromotionConfigController {

	private static Logger logger = Logger.getLogger(PromotionConfigController.class);
	
	@Autowired
	private IPromotionConfigService iPromotionConfigService;
	
	@RequestMapping("/toPromotionConfigPage")
	public String toPromotionConfigPage(Model model,HttpServletRequest request,HttpServletResponse response){
		return "/config/promotionConfigList";
	}

	@ResponseBody
	@RequestMapping(value = "/loadPromotionConfigList")
	public String loadPromotionConfigList(Page page,PromotionConfigRequestForm form){
		try {
			page = iPromotionConfigService.loadPageList(page,form);
			return JSONUtil.toJSONString(JSONObject.fromObject(page));
		} catch (Exception e) {
			logger.error("查询活动发布信息异常");
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/toAddPromotionConfig")
	public String toAddPromotionConfig(){
		return "/config/addPromotionConfig";
	}
		
	/**
	 * 新增或更新发布单信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/savePromotionConfig")
	public String savePromotionConfig(Model model, PromotionConfig config, HttpServletRequest request, HttpServletResponse response){
		if(null!=config){
			try {
				ResultDto<PromotionConfig> dto = iPromotionConfigService.addPromotionConfig(config);
				model.addAttribute("id",dto.getData().getId());
				return "/config/addPromotionConfigPicture";
			} catch (Exception e) {
				logger.error("新增活动发布信息异常");
				e.printStackTrace();
			}
		}
		return "/config/addPromotionConfig";
	}

	@ResponseBody
	@RequestMapping(value = "/deleteById")
	public String deleteById(Model model,Integer id){
		ResultDto<String> dto = new ResultDto<>();
		try {
			dto = iPromotionConfigService.deletePromotionConfigById(id);
			return JSONUtil.toJSONString(JSONObject.fromObject(dto));
		} catch (Exception e) {
			logger.error("删除奖品配置信息异常");
			e.printStackTrace();
			dto.setErrorMsg("删除奖品配置信息异常，请稍后重试");
			dto.setResult("F");
		}
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

}
