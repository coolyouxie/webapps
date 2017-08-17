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
import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.common.utils.JSONUtil;
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
	
	/**
	 * 保存报名列表中的沟通信息
	 * @param model
	 * @param em
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveTalkInfo")
	public String saveTalkInfo(Model model,Enrollment em){
		ResultDto<String> dto = new ResultDto<String>();
		try {
			int count = iEnrollmentService.saveTalkInfoById(em);
			if(count==1){
				dto.setResult("S");
			}else{
				dto.setErrorMsg("更新数据失败，请稍后再试");
				dto.setResult("F");
			}
		} catch (Exception e) {
			dto.setErrorMsg("更新数据异常，请稍后再试");
			dto.setResult("F");
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	

}
