package com.webapps.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.User;
import com.webapps.common.form.EnrollApprovalRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IEnrollApprovalService;
import com.webapps.service.IEnrollmentService;

@Controller
@RequestMapping("enrollApproval")
public class EnrollApprovalController {
	
	@Autowired
	private IEnrollmentService iEnrollmentService;
	
	@Autowired
	private IEnrollApprovalService iEnrollApprovalService;
	
	@RequestMapping("/toEnrollApprovalListPage")
	public String toEnrollApprovalListPage(HttpServletRequest request,HttpServletResponse response){
		return "/enrollApproval/enrollApprovalList";
	}
	
	@ResponseBody
	@RequestMapping("/loadEnrollApprovalList")
	public Page loadEnrollApprovalList(Model model,Page page,EnrollApprovalRequestForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			page = iEnrollApprovalService.loadEnrollApprovalList(page, form);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/getById")
	public String getById(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		try {
			EnrollApproval ea = iEnrollApprovalService.getById(id);
			model.addAttribute("enrollApproval", ea);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollApproval/showEnrollApproval";
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
	
	
	@ResponseBody
	@RequestMapping(value="/enrollApprovalById")
	public String enrollApprovalById(Model model,Integer id,Integer state,String remark,Integer approvalType,String[] cashbackData,HttpServletRequest request){
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		User user = (User)request.getSession().getAttribute("user");
		try {
			if(approvalType==1){
//				dto = iEnrollApprovalService.enrollApproval(id, state, remark, reward,user.getId(),cashbackDays);
				//使用新的分阶段期满返费入职审核
				dto = iEnrollApprovalService.entryApproveById(id,state,remark,user.getId(),cashbackData);
			}else{
//				dto = iEnrollApprovalService.expireApproval(id, state, remark,user.getId());
				//使用新的分阶段期满审核方法
				dto = iEnrollApprovalService.expireApprovalById(id, state, remark,user.getId());
			}
		} catch (Exception e) {
			dto.setResult("F");
			dto.setErrorMsg("审核异常，请稍后重试");
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

}
