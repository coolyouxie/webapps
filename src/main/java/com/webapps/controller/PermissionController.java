package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Permission;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.CompanyRequestForm;
import com.webapps.common.form.PermissionRequestForm;
import com.webapps.common.form.PictureRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.ICompanyService;
import com.webapps.service.IPermissionService;
import com.webapps.service.IPictureService;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Controller
@RequestMapping("permission")
public class PermissionController {
	
	@Autowired
	private ICompanyService iCompanyService;
	
	@Autowired
	private IPictureService iPictureService;

	@Autowired
	private IPermissionService iPermissionService;
	
	@RequestMapping("/toPermissionListPage")
	public String toCompanyListPage(HttpServletRequest request,HttpServletResponse response){
		return "/manage/permissionList";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadPermissionList",produces ="text/html;charset=UTF-8")
	public String loadPermissionList(Model model, Page page, PermissionRequestForm form){
		try {
			if(form!=null&& StringUtils.isNotBlank(form.getName())){
				form.setName(URLDecoder.decode(form.getName(),"UTF-8"));
			}
			page = iPermissionService.loadPermissionList(page, form);
			return JSONUtil.toJSONString(JSONObject.fromObject(page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/savePermission")
	public String savePermission(Model model,PermissionRequestForm form,HttpServletRequest request){
		ResultDto<Permission> dto = null;
		if(null!=form){
			try {
				dto = iPermissionService.savePermission(form);
				return JSONUtils.valueToString(JSONObject.fromObject(dto));
			}catch (Exception e){
				e.printStackTrace();
				dto.setResult("F");
				dto.setErrorMsg("保存权限信息异常");
				return JSONUtils.valueToString(JSONObject.fromObject(dto));
			}
		}
		dto = new ResultDto<>();
		dto.setErrorMsg("权限参数为空");
		dto.setResult("F");
		return JSONUtils.valueToString(JSONObject.fromObject(dto));
	}
	
	@ResponseBody
	@RequestMapping("/deleteById")
	public String deleteById(Model model,Integer id,HttpServletRequest request){
		try {
			ResultDto<Permission> dto = iPermissionService.deletePermissionById(id);
			return JSONUtils.valueToString(JSONObject.fromObject(dto));
		} catch (Exception e) {
			e.printStackTrace();
			ResultDto<Permission> dto = new ResultDto<Permission>();
			dto.setErrorMsg("删除权限信息时异常，请稍后再试");
			dto.setResult("F");
			return JSONUtils.valueToString(JSONObject.fromObject(dto));
		}
	}
	
	@RequestMapping("/getById")
	public String getById(Model model,Integer id,String type){
		try {
			Permission p = iPermissionService.getById(id);
			model.addAttribute("permission", p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("edit".equalsIgnoreCase(type)){
			return "/permission/toEditPage";
		}else if("show".equals(type)){
			return "/permission/toShowPage";
		}
		return null;
	}

}
