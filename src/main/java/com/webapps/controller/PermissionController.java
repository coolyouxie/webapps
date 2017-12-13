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
import java.util.List;

@Controller
@RequestMapping(value="permission")
public class PermissionController {
	
	@Autowired
	private ICompanyService iCompanyService;
	
	@Autowired
	private IPictureService iPictureService;

	@Autowired
	private IPermissionService iPermissionService;
	
	@RequestMapping("/toPermissionListPage")
	public String toPermissionListPage(HttpServletRequest request,HttpServletResponse response){
		return "/manage/permissionList";
	}

	@RequestMapping("/toAddPermissionPage")
	public String toAddPermissionPage(HttpServletRequest request,HttpServletResponse response){
		return "/manage/addPermission";
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
		ResultDto<Permission> dto = new ResultDto<Permission>();
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
			PermissionRequestForm form = new PermissionRequestForm();
			form.setLevel(3);
			List<Permission> childrenPermission = iPermissionService.queryByConditions(form);
			model.addAttribute("permission", p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("edit".equalsIgnoreCase(type)){
			return "/manage/editPermission";
		}else if("show".equals(type)){
			return "/manage/permissionInfo";
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/validatePermission")
	public String validatePermission(Model model,PermissionRequestForm form){
		ResultDto<String> dto = null;
		try {
			dto = iPermissionService.validatePermission(form);
			return JSONUtil.toJSONString(JSONObject.fromObject(dto));
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("验证权限时异常");
			dto.setResult("F");
			return JSONUtil.toJSONString(JSONObject.fromObject(dto));
		}
	}

}
