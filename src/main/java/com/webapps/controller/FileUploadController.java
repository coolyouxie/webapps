package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.ResultDto;
import com.webapps.service.IPictureService;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("fileUpload")
public class FileUploadController {
	
	@Autowired
	private IPictureService iPictureService;
	
	/**
	 * 好传图片
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pictureUpload")
	public String pictureUpload(HttpServletRequest request,HttpServletResponse response){
		ResultDto<String> dto = new ResultDto<String>();
		try {
			iPictureService.savePicture(null, request);
			dto.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = JSONUtils.valueToString(JSONObject.fromObject(dto));
		return result;
	}

}
