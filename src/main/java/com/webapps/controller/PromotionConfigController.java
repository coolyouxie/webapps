package com.webapps.controller;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webapps.common.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Picture;
import com.webapps.common.entity.PromotionConfig;
import com.webapps.common.form.PromotionConfigRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IPictureService;
import com.webapps.service.IPromotionConfigService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("promotionConfig")
public class PromotionConfigController {

	private static Logger logger = Logger.getLogger(PromotionConfigController.class);
	
	@Autowired
	private IPromotionConfigService iPromotionConfigService;
	
	@Autowired
	private IPictureService iPictureService;
	
	@RequestMapping("/toPromotionConfigPage")
	public String toPromotionConfigPage(Model model,HttpServletRequest request,HttpServletResponse response){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,1);
		model.addAttribute("tomorrow",DateUtil.format(c.getTime(),DateUtil.SIMPLE_PATTERN));
		return "/config/promotionConfigList";
	}

	@ResponseBody
	@RequestMapping(value = "/loadPromotionConfigList")
	public String loadPromotionConfigList(Page page,PromotionConfigRequestForm form){
		try {
			if(form!=null&&StringUtils.isNotBlank(form.getName())){
				form.setName(URLDecoder.decode(form.getName(),"UTF-8"));
			}
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

	@RequestMapping(value = "/toEditPromotionConfig")
	public String toEditPromotionConfig(Model model,Integer id){
		try {
			PromotionConfig pc = iPromotionConfigService.getById(id);
			model.addAttribute("config",pc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/config/editPromotionConfig";
	}

	@RequestMapping(value = "/toEditPromotionPicture")
	public String toEditPromotionPicture(Model model,Integer id){
		model.addAttribute("id",id);
		model.addAttribute("types",8);
		return "/config/editPromotionConfigPicture";
	}

	@ResponseBody
	@RequestMapping(value = "/updatePromotionConfig",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
	public String updatePromotionConfig(Model model,PromotionConfigRequestForm form){
		ResultDto<String> dto = null;
		try {
			if(form!=null&&StringUtils.isNotBlank(form.getName())){
				form.setName(URLDecoder.decode(form.getName(),"UTF-8"));
			}
			if(form!=null&&StringUtils.isNotBlank(form.getEffectiveDateStart())){
				form.setEffectiveDate(DateUtil.parseDateByStr(form.getEffectiveDateStart(),DateUtil.SIMPLE_PATTERN));
			}
			if(form!=null&&StringUtils.isNotBlank(form.getExpiryDateStart())){
				form.setExpiryDate(DateUtil.parseDateByStr(form.getExpiryDateStart(),DateUtil.SIMPLE_PATTERN));
			}
			 dto = iPromotionConfigService.updatePromotionConfig(form);
		} catch (Exception e) {
			dto = new ResultDto<>();
			dto.setResult("F");
			dto.setErrorMsg("更新活动信息时异常");
			logger.error("更新活动信息时异常");
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}


		
	/**
	 * 新增或更新活动发布信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/savePromotionConfig")
	public String savePromotionConfig(Model model, PromotionConfigRequestForm config, HttpServletRequest request, HttpServletResponse response){
		if(null!=config){
			try {
				if(config!=null&& StringUtils.isNotBlank(config.getEffectiveDateStart())){
					config.setEffectiveDate(DateUtil.parseDateByStr(config.getEffectiveDateStart(),DateUtil.SIMPLE_PATTERN));
				}
				if(config!=null&& StringUtils.isNotBlank(config.getExpiryDateStart())){
					config.setExpiryDate(DateUtil.parseDateByStr(config.getExpiryDateStart(),DateUtil.SIMPLE_PATTERN));
				}
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
	
	@ResponseBody
	@RequestMapping(value="/addPromotionConfigPicture")
	public String addPromotionConfigPicture(Model model,Integer fkId,String picUrl){
		Picture pic = new Picture();
		pic.setCreateTime(new Date());
		pic.setDataState(1);
		pic.setPicUrl(picUrl);
		pic.setFkId(fkId);
		pic.setType(8);
		ResultDto<Picture> dto = null;
		try {
			dto = iPictureService.savePicture(pic);
		} catch (Exception e) {
			logger.error("保存图片信息异常");
			e.printStackTrace();
			dto = new ResultDto<>();
			dto.setErrorMsg("保存图片信息异常");
			dto.setResult("F");
		}
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

	@ResponseBody
	@RequestMapping(value="/updateStatusById")
	public String updateStatusById(Model model,Integer id,int status){
		ResultDto<String> dto = null;
		try {
			dto = iPromotionConfigService.updateStatusById(id,status);
		} catch (Exception e) {
			dto = new ResultDto<>();
			dto.setResult("S");
			dto.setErrorMsg("更新活动状态异常");
			logger.error("更新活动状态异常");
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

	@ResponseBody
	@RequestMapping(value = "/updateStatusDate")
	public String updateStatusDate(Model model,PromotionConfigRequestForm form){
		ResultDto<String> dto = null;
		if(form!=null&&StringUtils.isNotBlank(form.getEffectiveDateStart())){
			form.setEffectiveDate(DateUtil.parseDateByStr(form.getEffectiveDateStart(),DateUtil.SIMPLE_PATTERN));
		}
		if(form!=null&&StringUtils.isNotBlank(form.getExpiryDateStart())){
			form.setExpiryDate(DateUtil.parseDateByStr(form.getExpiryDateStart(),DateUtil.SIMPLE_PATTERN));
		}
		try {
			dto = iPromotionConfigService.updateStatusDate(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

}
