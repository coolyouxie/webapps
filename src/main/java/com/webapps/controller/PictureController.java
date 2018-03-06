package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.form.PictureRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IPictureService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value="picture")
public class PictureController {

	private static Logger logger = Logger.getLogger(PictureController.class);
	
	@Autowired
	private IPictureService iPictureService;
	
	@ResponseBody
	@RequestMapping(value="/loadCompanyPicList",produces = "text/html;charset=UTF-8")
	public String loadCompanyList(Model model,Page page,Integer fkId){
		PictureRequestForm form = new PictureRequestForm();
		form.setFkId(fkId);
		try {
			page = iPictureService.loadPictureList(page, form);
			return JSONUtil.toJSONString(JSONObject.fromObject(page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value="/loadPromotionConfigPicturesByFkId",produces = "text/html;charset=UTF-8")
	public String loadPromotionConfigPicturesByFkId(Model model, Page page,String types,Integer fkId){
		PictureRequestForm form = new PictureRequestForm();
		form.setFkId(fkId);
		if(StringUtils.isNotBlank(types)){
			String[] array = types.split(",");
			Integer[] typeArray = new Integer[array.length];
			for(int i=0;i<array.length;i++){
				typeArray[i] = Integer.valueOf(array[i]);
			}
			form.setTypes(typeArray);
		}
		try {
			page = iPictureService.queryPageByFkIdTypes(page,form);
			return JSONUtil.toJSONString(JSONObject.fromObject(page));
		} catch (Exception e) {
			logger.error("查询图片信息异常");
			e.printStackTrace();
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadPicture")
	public String uploadPicture(Model model,HttpServletRequest request){
		ResultDto<String> dto = iPictureService.uploadCompanyPicture(request);
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

	@ResponseBody
	@RequestMapping(value = "/deleteById")
	public String deleteById(Model model,Integer id){
		ResultDto<String> dto = iPictureService.deleteById(id);
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}
	
	
	

}
