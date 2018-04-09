package com.webapps.controller;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.webapps.common.bean.Page;
import com.webapps.common.entity.Recommend;
import com.webapps.common.form.RecommendRequestForm;
import com.webapps.service.IRecommendService;

@Controller
@RequestMapping("recommend")
public class RecommendController {
	
	@Autowired
	private IRecommendService iRecommendService;
	
	@RequestMapping("/toRecommendListPage")
	public String toEnrollmentListPage(HttpServletRequest request,HttpServletResponse response){
		return "/recommend/recommendList";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadRecommendList",produces = "text/html;charset=UTF-8")
	public String loadEnrollmentList(Model model,Page page,RecommendRequestForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			if(form!=null&& StringUtils.isNotBlank(form.getName())){
				form.setName(URLDecoder.decode(form.getName(),"UTF-8"));
			}
			if(form!=null&&form.getUser()!=null&& StringUtils.isNotBlank(form.getUser().getName())){
				form.getUser().setName(URLDecoder.decode(form.getUser().getName(),"UTF-8"));
			}
			page = iRecommendService.loadRecommendList(page, form);
			return JSON.toJSONString(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getById")
	public String getById(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		try {
			Recommend em = iRecommendService.getById(id);
			model.addAttribute("recommend", em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/recommend/showRecommend";
	}
	
	

}
