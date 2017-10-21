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
import com.webapps.common.entity.Company;
import com.webapps.common.form.CompanyRequestForm;
import com.webapps.common.form.PictureRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.ICompanyService;
import com.webapps.service.IPictureService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value="picture")
public class PictureController {
	
	@Autowired
	private IPictureService iPictureService;
	
	@ResponseBody
	@RequestMapping("/loadCompanyPicList")
	public Page loadCompanyList(Model model,Page page,Integer fkId,HttpServletRequest request,HttpServletResponse response){
		PictureRequestForm form = new PictureRequestForm();
		form.setFkId(fkId);
		try {
			page = iPictureService.loadPictureList(page, form);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
