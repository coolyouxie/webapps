package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.AwardConfig;
import com.webapps.common.entity.FeeConfig;
import com.webapps.common.form.AwardConfigRequestForm;
import com.webapps.common.form.FeeConfigRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IAwardConfigService;
import com.webapps.service.IFeeConfigService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("awardConfig")
public class AwardConfigController {

	private static Logger logger = Logger.getLogger(AwardConfigController.class);
	
	@Autowired
	private IAwardConfigService iAwardConfigService;
	
	@RequestMapping("/toAwardConfigPage")
	public String toAwardConfigPage(Model model,HttpServletRequest request,HttpServletResponse response){
		return "/config/awardConfigList";
	}

	@ResponseBody
	@RequestMapping(value = "/loadAwardConfigList")
	public String loadAwardConfigList(Page page,AwardConfigRequestForm form){
		try {
			page = iAwardConfigService.loadPageList(page,form);
			return JSONUtil.toJSONString(JSONObject.fromObject(page));
		} catch (Exception e) {
			logger.error("查询奖品配置信息异常");
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/toAddAwardConfig")
	public String toAddAwardConfig(){
		return "/config/addAwardConfig";
	}
		
	/**
	 * 新增或更新发布单信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/saveAwardConfig")
	public String saveAwardConfig(Model model, AwardConfig config, HttpServletRequest request, HttpServletResponse response){
		if(null!=config){
			try {
				ResultDto<String> dto = iAwardConfigService.addAwardConfig(config);
				return "/config/awardConfigList";
			} catch (Exception e) {
				logger.error("新增奖品配置信息异常");
				e.printStackTrace();
			}
		}
		return "/config/addAwardConfig";
	}

	@ResponseBody
	@RequestMapping(value = "/deleteById")
	public String deleteById(Model model,Integer id){
		ResultDto<String> dto = new ResultDto<>();
		try {
			dto = iAwardConfigService.deleteAwardConfigById(id);
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
