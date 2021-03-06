package com.webapps.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.User;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.service.IEnrollmentService;

@Controller
@RequestMapping("enrollment")
public class EnrollmentController {
	
	@Autowired
	private IEnrollmentService iEnrollmentService;

	@RequestMapping(value="/toEnrollmentListPage")
	public String toEnrollmentListPage(Model model,HttpServletRequest request,HttpServletResponse response){
		String intentionCities = (String)PropertiesUtil.getProperty("intentionCities");
		String[] cities = intentionCities.split(",");
		List<JSONObject> list = new ArrayList<JSONObject>();
		RecruitProcessController.getIntentionCities(cities, list);
		model.addAttribute("intentionCities",list);
		return "/enrollment/enrollmentList";
	}

	/**
	 * 加载报名列表数据
	 * @param model
	 * @param page
	 * @param form
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loadEnrollmentList",method=RequestMethod.POST,produces ="text/html;charset=UTF-8")
	public String loadEnrollmentList(Model model,Page page,EnrollmentRequestForm form,HttpSession session){
		try {
			//只有中文需要这样转一下，可能是配置不对，也可能是插件bug，具体原因未知.
			if(form!=null&&form.getCompany()!=null&& StringUtils.isNotBlank(form.getCompany().getName())){
				String companyName = form.getCompany().getName().trim();
				companyName = URLDecoder.decode(companyName,"UTF-8");
				form.getCompany().setName(companyName);
			}
			if(form!=null&& StringUtils.isNotBlank(form.getTalkerName())){
				String talkerName = form.getTalkerName().trim();
				talkerName = URLDecoder.decode(talkerName,"UTF-8");
				form.setTalkerName(talkerName);
			}
			if(form!=null&&form.getUser()!=null&& StringUtils.isNotBlank(form.getUser().getName())){
				String userName = form.getUser().getName().trim();
				userName = URLDecoder.decode(userName,"UTF-8");
				form.getUser().setName(userName);
			}
			User user = (User)session.getAttribute("user");
			if(user!=null&&(user.getUserType()!=1&&user.getUserType()!=2)){
				form.setTalkerId(user.getId());
			}
			page = iEnrollmentService.loadEnrollmentList(page, form);
			return JSONObject.toJSONString(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value="/getById")
	public String getById(Model model,Integer id){
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
	 * @param em
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveTalkInfo")
	public String saveTalkInfo(EnrollmentRequestForm em){
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
	
	@ResponseBody
	@RequestMapping(value="/cancelEnroll")
	public String cancelEnroll(Model model,EnrollmentRequestForm form){
		ResultDto<Enrollment> dto = iEnrollmentService.cancelEnroll(form);
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	
	@ResponseBody
	@RequestMapping(value="/updateInterviewTime")
	public String updateInterviewTime(EnrollmentRequestForm form){
		ResultDto<String> dto = iEnrollmentService.updateInterviewTime(form);
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	@ResponseBody
	@RequestMapping(value = "/updateTalkerInfo")
	public String updateTalkerInfo(EnrollmentRequestForm form){
		ResultDto<String> dto = iEnrollmentService.updateTalkerInfo(form);
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	

}
