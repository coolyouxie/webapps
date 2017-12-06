package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.tools.internal.ws.wsdl.document.http.HTTPUrlEncoded;
import com.webapps.common.form.RequestForm;
import com.webapps.common.utils.DataDecodeUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IEnrollmentService;

import java.net.URLDecoder;

@Controller
@RequestMapping("enrollment")
public class EnrollmentController {
	
	@Autowired
	private IEnrollmentService iEnrollmentService;
	
	@RequestMapping(value="/toEnrollmentListPage")
	public String toEnrollmentListPage(HttpServletRequest request,HttpServletResponse response){
		return "/enrollment/enrollmentList";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadEnrollmentList",method=RequestMethod.POST,produces ="text/html;charset=UTF-8")
	public String loadEnrollmentList(Model model,Page page,EnrollmentRequestForm form){
		try {
			if(form!=null&&form.getCompany()!=null&& StringUtils.isNotBlank(form.getCompany().getName())){
				String companyName = form.getCompany().getName().trim();
				companyName = URLDecoder.decode(companyName,"UTF-8");
				form.getCompany().setName(companyName);
			}
			if(form!=null&&form.getUser()!=null&& StringUtils.isNotBlank(form.getUser().getName())){
				String userName = form.getUser().getName().trim();
				userName = URLDecoder.decode(userName,"UTF-8");
				form.getUser().setName(userName);
			}
			page = iEnrollmentService.loadEnrollmentList(page, form);
			return JSONUtil.toJSONString(JSONObject.fromObject(page));
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
	public String saveTalkInfo(Enrollment em){
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
	
	

}
