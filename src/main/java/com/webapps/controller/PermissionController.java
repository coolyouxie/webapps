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
import com.webapps.common.entity.Permission;
import com.webapps.common.entity.PermissionRelation;
import com.webapps.common.form.PermissionRequestForm;
import com.webapps.service.IPermissionService;

@Controller
@RequestMapping(value="permission")
public class PermissionController {

    private static Logger logger = Logger.getLogger(PermissionController.class);

	@Autowired
	private IPermissionService iPermissionService;
	
	@RequestMapping("/toMenuPermissionListPage")
	public String toMenuPermissionListPage(HttpServletRequest request,HttpServletResponse response){
		return "/permission/menuPermissionList";
	}

	@RequestMapping("/toOperatePermissionListPage")
	public String toOperatePermissionListPage(HttpServletRequest request,HttpServletResponse response){
		return "/permission/operatePermissionList";
	}

	@RequestMapping("/toAddMenuPermissionPage")
	public String toAddMenuPermissionPage(Model model,HttpServletRequest request,HttpServletResponse response){
		List<Permission> list = iPermissionService.loadAllOperatePermission();
		model.addAttribute("permissions",list);
		return "/permission/addMenuPermission";
	}

	@RequestMapping("/toAddOperatePermissionPage")
	public String toAddOperatePermissionPage(Model model,Integer userId,HttpServletRequest request,HttpServletResponse response){
		List<PermissionRelation> list = iPermissionService.loadAllPermissions(userId);
		model.addAttribute("permissions",list);
		return "/permission/addOperatePermission";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadMenuPermissionList",produces ="text/html;charset=UTF-8")
	public String loadMenuPermissionList(Model model, Page page, PermissionRequestForm form){
		try {
			if(form!=null&& StringUtils.isNotBlank(form.getName())){
				form.setName(URLDecoder.decode(form.getName(),"UTF-8"));
			}
			form.setLevel(2);
			page = iPermissionService.loadPermissionList(page, form);
			return JSON.toJSONString(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/loadOperatePermissionList",produces ="text/html;charset=UTF-8")
	public String loadOperatePermissionList(Model model, Page page, PermissionRequestForm form){
		try {
			if(form!=null&& StringUtils.isNotBlank(form.getName())){
				form.setName(URLDecoder.decode(form.getName(),"UTF-8"));
			}
			form.setLevel(3);
			page = iPermissionService.loadPermissionList(page, form);
			return JSON.toJSONString(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/saveMenuPermission")
	public String saveMenuPermission(Model model,PermissionRequestForm form,HttpServletRequest request){
		ResultDto<String> dto = new ResultDto<>();
		if(null!=form){
			try {
				dto = iPermissionService.saveMenuPermission(form);
				return JSON.toJSONString(dto);
			}catch (Exception e){
				e.printStackTrace();
				dto.setResult("F");
				dto.setErrorMsg("保存权限信息异常");
				return JSON.toJSONString(dto);
			}
		}
		dto = new ResultDto<>();
		dto.setErrorMsg("权限参数为空");
		dto.setResult("F");
		return JSON.toJSONString(dto);
	}
	
	@ResponseBody
	@RequestMapping("/saveOperatePermission")
	public String saveOperatePermission(Model model,PermissionRequestForm form,HttpServletRequest request){
		ResultDto<String> dto = new ResultDto<>();
		if(null!=form){
			try {
				dto = iPermissionService.saveOperatePermission(form);
				return JSON.toJSONString(dto);
			}catch (Exception e){
				e.printStackTrace();
				dto.setResult("F");
				dto.setErrorMsg("保存权限信息异常");
				return JSON.toJSONString(dto);
			}
		}
		dto = new ResultDto<>();
		dto.setErrorMsg("权限参数为空");
		dto.setResult("F");
		return JSON.toJSONString(dto);
	}
	
	@ResponseBody
	@RequestMapping("/deleteById")
	public String deleteById(Model model,Integer id,HttpServletRequest request){
		try {
			ResultDto<Permission> dto = iPermissionService.deletePermissionById(id);
			return JSON.toJSONString(dto);
		} catch (Exception e) {
			e.printStackTrace();
			ResultDto<Permission> dto = new ResultDto<Permission>();
			dto.setErrorMsg("删除权限信息时异常，请稍后再试");
			dto.setResult("F");
			return JSON.toJSONString(dto);
		}
	}
	
	@RequestMapping("/getMenuPermissionById")
	public String getMenuPermissionById(Model model,Integer id,String type){
		try {
			Permission p = iPermissionService.getMenuPermissionById(id);
			model.addAttribute("permission", p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("edit".equalsIgnoreCase(type)){
			return "/permission/editMenuPermission";
		}else if("show".equals(type)){
			return "/permission/showMenuPermission";
		}
		return null;
	}
	
	@RequestMapping("/getOperatePermissionById")
	public String getOperatePermissionById(Model model,Integer id,String type){
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
			return "/permission/editOperatePermission";
		}else if("show".equals(type)){
			return "/permission/showOperatePermission";
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/validatePermission")
	public String validatePermission(Model model,PermissionRequestForm form){
		ResultDto<String> dto = null;
		try {
			dto = iPermissionService.validatePermission(form);
			return JSON.toJSONString(dto);
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("验证权限时异常");
			dto.setResult("F");
			return JSON.toJSONString(dto);
		}
	}

    /**
     * 修改和添加用户操作权限
     * @param model
     * @param userId
     * @return
     */
	@RequestMapping("/toAddUserPermissionPage")
	public String toAddUserPermissionPage(Model model,Integer userId){
        List<PermissionRelation> list = null;
        try {
            list = iPermissionService.loadAllPermissions(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("userId",userId);
		model.addAttribute("permissions",list);
		return "/permission/addUserPermission";
	}

	@ResponseBody
    @RequestMapping("/saveUserPermission")
    public String saveUserPermission(Model model,Integer userId,int[] menuId,int[] operateId){
	    ResultDto<String> dto = null;
        try {
            dto = iPermissionService.saveUserPermisson(userId,menuId,operateId);
            return JSON.toJSONString(dto);
        } catch (Exception e) {
            logger.error("保存用户权限异常");
            e.printStackTrace();
            dto = new ResultDto<>();
            dto.setResult("F");
            dto.setErrorMsg("保存用户权限异常，请稍后重试");
        }
        return JSON.toJSONString(dto);
    }

}
