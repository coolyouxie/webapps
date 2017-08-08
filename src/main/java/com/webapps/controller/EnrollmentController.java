package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.service.IEnrollmentService;

@Controller
@RequestMapping("enrollment")
public class EnrollmentController {
	
	@Autowired
	private IEnrollmentService iEnrollmentService;
	
	@RequestMapping("/toEnrollmentListPage")
	public String toEnrollmentListPage(HttpServletRequest request,HttpServletResponse response){
		return "/enrollment/enrollmentList";
	}
	
	@ResponseBody
	@RequestMapping("/loadEnrollmentList")
	public Page loadEnrollmentList(Model model,Page page,EnrollmentRequestForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			page = iEnrollmentService.loadEnrollmentList(page, form);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/getById")
	public String getById(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		try {
			Enrollment em = iEnrollmentService.getById(id);
			model.addAttribute("enrollment", em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollment/showEnrollment";
	}
	
	

}
