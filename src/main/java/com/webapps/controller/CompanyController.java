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
import com.webapps.common.form.CompanyRequestForm;
import com.webapps.service.ICompanyService;

import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("company")
public class CompanyController {
	
	@Autowired
	private ICompanyService iCompanyService;
	
	@RequestMapping("/toCompanyListPage")
	public String toCompanyListPage(HttpServletRequest request,HttpServletResponse response){
		return "/company/companylist";
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
			return "/company/addcompany";
		}
		if("addPicture".equals(type)){
			return "/company/addcompanypicture";
		}
		//如果不是新增，则先根据ID查询出公司信息后再跳转页面
		try {
			Company company = iCompanyService.getById(id);
			model.addAttribute("company", company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("show".equals(type)){
			return "/company/showcompany";
		}else if("edit".equals(type)){
			return "/company/editcompany";
		}
		return null;
	}
	
	@RequestMapping("/saveCompany")
	public String saveCompany(Model model,CompanyRequestForm company,HttpServletRequest request,HttpServletResponse response){
		if(null!=company){
			ResultDto<Company> dto = null;
			try {
				dto = iCompanyService.saveCompany(company);
				if("insert_success".equals(dto.getResult())){
					model.addAttribute("id", dto.getData().getId());
					return "/company/addcompanypicture";
				}
				if("insert_failed".equals(dto.getResult())){
					model.addAttribute("company", company);
					model.addAttribute("result",dto.getErrorMsg());
					return "/company/addcompany";
				}
				if("update_success".equals(dto.getResult())){
					model.addAttribute("company", company);
					model.addAttribute("result",dto.getErrorMsg());
					return "/company/companylist";
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(company.getId()!=null){
					model.addAttribute("company", company);
					model.addAttribute("result", "更新公司信息时异常，请稍后再试");
					return "/compan/editcompany";
				}else{
					model.addAttribute("company", company);
					model.addAttribute("result", "新增公司信息时异常，请稍后再试");
					return "/compan/addcompany";
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
			return JSONUtils.valueToString(dto);
		} catch (Exception e) {
			e.printStackTrace();
			ResultDto<Company> dto = new ResultDto<Company>();
			dto.setErrorMsg("删除公司信息时异常，请稍后再试");
			dto.setResult("fail");
			return JSONUtils.valueToString(dto);
		}
	}
	
	

}
