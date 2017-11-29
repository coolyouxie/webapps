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
import com.webapps.common.dto.EnrollApprovalInfoDto;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.entity.User;
import com.webapps.common.form.EnrollApprovalRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.ICompanyService;
import com.webapps.service.IEnrollApprovalService;
import com.webapps.service.IEnrollmentService;
import com.webapps.service.IRecruitmentService;
import com.webapps.service.IUserService;

@Controller
@RequestMapping("enrollApproval")
public class EnrollApprovalController {
	
	@Autowired
	private IEnrollmentService iEnrollmentService;
	
	@Autowired
	private IEnrollApprovalService iEnrollApprovalService;
	
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private IRecruitmentService iRecruitmentService;
	
	@Autowired
	private ICompanyService iCompanyService;
	
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
				//使用新的分阶段期满返费入职审核
				dto = iEnrollApprovalService.entryApproveById(id,state,remark,user.getId(),cashbackData);
			}else{
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
	
	@RequestMapping(value="/toShowEntryInfoPage")
	public String toShowEntryInfoPage(Model model,Integer enrollApprovalId){
		try {
			EnrollApprovalInfoDto dto = iEnrollApprovalService.loadEnrollApprovalInfo(enrollApprovalId);
			model.addAttribute("dto", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollApproval/showEntryInfo";
	}
	
	@RequestMapping(value="/toEditEntryInfoPage")
	public String toEditEntryInfoPage(Model model,Integer enrollApprovalId){
		try {
			EnrollApproval enrollApproval = iEnrollApprovalService.getById(enrollApprovalId);
			Enrollment enrollment = null;
			if(enrollApproval!=null){
				enrollment = iEnrollmentService.getById(enrollApproval.getEnrollmentId());
			}
			if(enrollment!=null){
				if(enrollment!=null&&enrollment.getUser()!=null&&enrollment.getUser().getId()!=null){
					User user = iUserService.getById(enrollment.getUser().getId());
					enrollment.setUser(user);
				}
				if(enrollment.getRecruitment()!=null&&enrollment.getRecruitment().getId()!=null){
					Recruitment recruitment = iRecruitmentService.getById(enrollment.getRecruitment().getId());
					enrollment.setRecruitment(recruitment);
				}
				if(enrollment.getCompany()!=null&&enrollment.getCompany().getId()!=null){
					Company company = iCompanyService.getById(enrollment.getCompany().getId());
					enrollment.setCompany(company);
				}
				model.addAttribute("enrollment", enrollment);
				model.addAttribute("enrollApprovalId", enrollApprovalId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollApproval/editEntryInfo";
	}

	@RequestMapping(value="/toShowExpireInfoPage")
	public String toShowExpireInfoPage(Model model,Integer enrollApprovalId){
		try {
			EnrollApprovalInfoDto dto = iEnrollApprovalService.loadEnrollApprovalInfo(enrollApprovalId);
			model.addAttribute("dto", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollApproval/showExpireInfo";
	}
	
	@RequestMapping(value="/toEditExpireInfoPage")
	public String toEditExpireInfoPage(Model model,Integer enrollApprovalId){
		try {
			EnrollApprovalInfoDto dto = iEnrollApprovalService.loadEnrollApprovalInfo(enrollApprovalId);
			model.addAttribute("dto", dto);
			model.addAttribute("enrollApprovalId", enrollApprovalId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollApproval/editExpireInfo";
	}
	
}
