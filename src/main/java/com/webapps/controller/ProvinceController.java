package com.webapps.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.entity.Province;
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
	
	/**
	 * 根据父级行政ID找到父级行政区下所有的子行政区
	 * @param model
	 * @param parentId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryProvinceByParentId")
	public String queryProvinceByParentId(Model model,Integer parentId,HttpServletRequest request,HttpServletResponse response){
		if(parentId!=null){
			try {
				List<Province> provinces = iProvinceService.queryProvinceByParentId(parentId);
				model.addAttribute("provinces", provinces);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 根据行政区等级查询所有行政区
	 * @param model
	 * @param level
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryProvinceByLevel")
	public String queryProvinceByLevel(Model model,Integer level,HttpServletRequest request,HttpServletResponse response){
		if(level!=null){
			try {
				List<Province> provinces = iProvinceService.queryProvinceByLevel(level);
				model.addAttribute("provinces", provinces);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
