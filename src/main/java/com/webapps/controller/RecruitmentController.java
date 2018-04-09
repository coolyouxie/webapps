package com.webapps.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.entity.Tag;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.ICompanyService;
import com.webapps.service.IRecruitmentService;
import com.webapps.service.ITagService;

@Controller
@RequestMapping("recruitment")
public class RecruitmentController {

	private static Logger logger = Logger.getLogger(RecruitmentController.class);
	
	@Autowired
	private ICompanyService iCompanyService;
	
	@Autowired
	private IRecruitmentService iRecruitmentService;

	@Autowired
	private ITagService iTagService;
	
	@RequestMapping(value="/toRecruitmentPage")
	public String toRecruitmentPage(){
		return "/recruitment/recruitmentList";
	}
	
	@RequestMapping("/toAddRecruitmentPage")
	public String toAddRecruitmentPage(Model model,Integer companyId,HttpServletRequest request,HttpServletResponse response){
		Company company;
		try {
			company = iCompanyService.getById(companyId);
			List<Tag> list = iTagService.queryAllTags();
			model.addAttribute("company", company);
			model.addAttribute("tags",list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/recruitment/addRecruitment";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadRecruitmentList",produces = "text/html;charset=UTF-8")
	public String loadRecruitmentList(Model model,Page page,RecruitmentRequestForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			if(form!=null&& StringUtils.isNotBlank(form.getTitle())){
				form.setTitle(URLDecoder.decode(form.getTitle(),"UTF-8"));
			}
			if(form!=null&&form.getCompany()!=null&& StringUtils.isNotBlank(form.getCompany().getName())){
				form.getCompany().setName(URLDecoder.decode(form.getCompany().getName(),"UTF-8"));
			}
			page = iRecruitmentService.loadRecruitmentList(page, form);
			return JSON.toJSONString(page);
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
		try {
			List<Tag> list = iTagService.queryAllTagsWithCheckStatus(id);
			model.addAttribute("tags", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		Integer companyId = recruitment.getCompany().getId();
		String handleType = recruitment.getHandleType();
		model.addAttribute("id", companyId);
		model.addAttribute("type",handleType);
		if (null != recruitment) {
			try {
				dto = iRecruitmentService.saveRecruitment(recruitment);
			}catch (Exception e){
				logger.error("保存发布单信息时异常");
				e.printStackTrace();
				dto.setResult("F");
				dto.setErrorMsg("保存发布单信息时异常");
			}
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
				} else if ("edit".equals(handleType)||"add".equals(handleType)) {
					model.addAttribute("type","show");
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
			try {
				List<Tag> list = iTagService.queryAllTagsWithCheckStatus(id);
				model.addAttribute("tags", list);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("recruitment", r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/recruitment/showRecruitment";
	}
	
	

}
