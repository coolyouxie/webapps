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
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.CompanyRequestForm;
import com.webapps.common.form.PictureRequestForm;
import com.webapps.service.ICompanyService;
import com.webapps.service.IPictureService;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("company")
public class CompanyController {
	
	@Autowired
	private ICompanyService iCompanyService;
	
	@Autowired
	private IPictureService iPictureService;
	
	@RequestMapping("/toCompanyListPage")
	public String toCompanyListPage(HttpServletRequest request,HttpServletResponse response){
		return "/company/companyList";
	}
	
	@ResponseBody
	@RequestMapping("/loadCompanyList")
	public Page loadCompanyList(Model model,Page page,CompanyRequestForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			page = iCompanyService.loadCompanyList(page, form);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/toCompanyInfoPage")
	public String toCompanyInfoPage(Model model,String type,Integer id,HttpServletRequest request,HttpServletResponse response){
		//如果是新增直跳转页面
		if("add".equals(type)){
			return "/company/addCompany";
		}
		if("addPicture".equals(type)){
			return "/company/addCompanyPicture";
		}
		//如果不是新增，则先根据ID查询出公司信息后再跳转页面
		try {
			Company company = iCompanyService.getById(id);
			model.addAttribute("company", company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("show".equals(type)){
			return "/company/showCompany";
		}else if("edit".equals(type)){
			return "/company/editCompany";
		}
		return null;
	}
	
	@RequestMapping("/saveCompany")
	public String saveCompany(Model model,CompanyRequestForm company,HttpServletRequest request,HttpServletResponse response){
		if(null!=company){
			ResultDto<Company> dto = null;
			try {
				dto = iCompanyService.saveCompany(company);
				if("add_next".equals(company.getHandleType())){
					if("insert_success".equals(dto.getResult())){
						model.addAttribute("company", dto.getData());
						model.addAttribute("result", "S");
						return "/company/editCompanyPicture";
					}
					if("insert_failed".equals(dto.getResult())){
						model.addAttribute("company", company);
						model.addAttribute("result",dto.getErrorMsg());
						return "/company/addCompany";
					}
				}
				if("add_save".equals(company.getHandleType())){
					if("insert_success".equals(dto.getResult())){
						return "/company/companyList";
					}
					if("insert_failed".equals(dto.getResult())){
						model.addAttribute("company", company);
						model.addAttribute("result",dto.getErrorMsg());
						return "/company/addCompany";
					}
				}
				if("edit_next".equals(company.getHandleType())){
					if("update_success".equals(dto.getResult())){
						model.addAttribute("company", dto.getData());
						model.addAttribute("result","S");
						return "/company/editCompanyPicture";
					}
					if("update_fail".equals(dto.getResult())){
						model.addAttribute("company", dto.getData());
						model.addAttribute("result",dto.getErrorMsg());
						return "/company/editCompany";
					}
				}
				if("edit_save".equals(company.getHandleType())){
					if("update_success".equals(dto.getResult())){
						return "/company/companyList";
					}
					if("update_fail".equals(dto.getResult())){
						model.addAttribute("company", dto.getData());
						model.addAttribute("result",dto.getErrorMsg());
						return "/company/editCompany";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(company.getId()!=null){
					model.addAttribute("company", company);
					model.addAttribute("result", "更新公司信息时异常，请稍后再试");
					return "/compan/editCompany";
				}else{
					model.addAttribute("company", company);
					model.addAttribute("result", "新增公司信息时异常，请稍后再试");
					return "/compan/addCompany";
				}
			}
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/deleteCompanyById")
	public String deleteCompanyById(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		try {
			ResultDto<Company> dto = iCompanyService.deleteCompanyById(id);
			return JSONUtils.valueToString(JSONObject.fromObject(dto));
		} catch (Exception e) {
			e.printStackTrace();
			ResultDto<Company> dto = new ResultDto<Company>();
			dto.setErrorMsg("删除公司信息时异常，请稍后再试");
			dto.setResult("F");
			return JSONUtils.valueToString(JSONObject.fromObject(dto));
		}
	}
	
	@RequestMapping("/getById")
	public String getById(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		try {
			Company company = iCompanyService.getById(id);
			model.addAttribute("company", company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/company/showCompany";
	}
	
	@ResponseBody
	@RequestMapping(value="/addCompanyPicture")
	public String addCompanyPicture(Model model,PictureRequestForm form){
		ResultDto<Picture> dto = iPictureService.saveCompanyPicture(form);
		return JSONUtils.valueToString(JSONObject.fromObject(dto));
	}
	
	

}
