package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.ICompanyService;
import com.webapps.service.IRecruitmentService;

import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("recruitment")
public class RecruitmentController {
	
	@Autowired
	private ICompanyService iCompanyService;
	
	@Autowired
	private IRecruitmentService iRecruitmentService;
	
	@RequestMapping(value="/toRecruitmentPage")
	public String toRecruitmentPage(){
		return "/recruitment/recruitmentList";
	}
	
	@RequestMapping("/toAddRecruitmentPage")
	public String toAddRecruitmentPage(Model model,Integer companyId,HttpServletRequest request,HttpServletResponse response){
		Company company;
		try {
			company = iCompanyService.getById(companyId);
			model.addAttribute("company", company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/recruitment/addRecruitment";
	}
	
	@ResponseBody
	@RequestMapping("/loadRecruitmentList")
	public Page loadRecruitmentList(Model model,Page page,RecruitmentRequestForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			page = iRecruitmentService.loadRecruitmentList(page, form);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/toRecruitmentInfoPage")
	public String toRecruitmentInfoPage(Model model,String type,Integer id,HttpServletRequest request,HttpServletResponse response){
		//如果是新增直跳转页面
		if("add".equals(type)){
			return "/recruitment/addRecruitment";
		}
		Recruitment r = null;
		try {
			r = iRecruitmentService.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("recruitment", r);
		if("editFromList".equals(type)||"edit".equals(type)){
			model.addAttribute("handleType", type);
			return "/recruitment/editRecruitment";
		}
		if("show".equals(type)){
			return "/recruitment/showRecruitment";
		}
		return null;
	}
	
	/**
	 * 新增或更新发布单信息
	 * @param model
	 * @param recruitment
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/saveRecruitment")
	public String saveRecruitment(Model model, RecruitmentRequestForm recruitment, HttpServletRequest request,
			HttpServletResponse response) {
		ResultDto<RecruitmentRequestForm> dto = null;
		Integer companyId = recruitment.getCompanyId();
		String handleType = recruitment.getHandleType();
		model.addAttribute("id", companyId);
		 model.addAttribute("type",handleType);
		if (null != recruitment) {
			dto = iRecruitmentService.saveRecruitment(recruitment);
			model.addAttribute("recruitment", recruitment);
			if ("F".equals(dto.getResult())) {
				if (recruitment.getId() == null) {
					dto.setResult("F");
					dto.setErrorMsg("新增发布单失败，请稍后重试");
					return "/recruitment/addRecruitment";
				} else {
					dto.setResult("F");
					dto.setErrorMsg("更新发布单失败，请稍后重试");
					return "/recruitment/editRecruitment";
				}
			} else if ("S".equals(dto.getResult())) {
				if ("editFromList".equals(handleType)) {
					return "redirect:/recruitment/toRecruitmentPage";
				} else if ("edit".equals(handleType)) {
					return "redirect:/company/toCompanyInfoPage";
				}
			}
		}
		return "redirect:/company/toCompanyInfoPage";
	}
	
	@ResponseBody
	@RequestMapping("/deleteRecruitmentById")
	public String deleteRecruitmentById(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		ResultDto<Recruitment> dto = new ResultDto<Recruitment>();
		try {
			int result = iRecruitmentService.deleteRecruitmentById(id);
			if(result==1){
				dto.setErrorMsg("删除成功");
				dto.setResult("S");
			}else{
				dto.setErrorMsg("删除失败，稍后重试");
				dto.setResult("F");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("删除公司信息时异常，请稍后再试");
			dto.setResult("F");
		}
		String result = JSONUtil.toJSONObjectString(dto);
		return result;
	}
	
	@RequestMapping("/getById")
	public String getById(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		try {
			Recruitment r = iRecruitmentService.getById(id);
			model.addAttribute("recruitment", r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/recruitment/showRecruitment";
	}
	
	

}
