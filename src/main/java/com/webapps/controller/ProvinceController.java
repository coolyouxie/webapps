package com.webapps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webapps.service.IProvinceService;

@Controller
@RequestMapping("/province")
public class ProvinceController {
	
	@Autowired
	private IProvinceService iProvinceService;
	
	@RequestMapping("/setProvinceParentId")
	public String setProvinceParentId(){
		try {
			iProvinceService.setProvinceParentId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
