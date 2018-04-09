package com.webapps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Province;
import com.webapps.service.IProvinceService;

@Controller
@RequestMapping(value="commom")
public class CommonController {
	
	@Autowired
	private IProvinceService iProvinceService;
	
	@ResponseBody
	@RequestMapping(value="/loadProvinceByLevel")
	public String loadProvinceByLevel(Model model,Integer level){
		ResultDto<List<Province>> dto = iProvinceService.queryProvinceByLevel(level);
		model.addAttribute("response", dto);
		return JSON.toJSONString(dto);
	}

}
