package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;
import com.webapps.service.IUserService;

import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private IUserService iUserService;
	
	@RequestMapping("/toUserListPage")
	public ModelAndView toUserListPage(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/user/userlist");
		return mv;
	}
	
	@RequestMapping("/toUserInfoPage")
	public String toUserEditPage(Model model,String type,Integer id,HttpServletRequest request){
		model.addAttribute("type", type);
		if("add".equals(type)){
			return "/user/adduser";
		}
		try {
			User user = iUserService.getById(id);
			model.addAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("edit".equals(type)){
			return "/user/edituser";
		}
		if("show".equals(type)){
			return "/user/showuser";
		}
		return "/user/useredit";
	}

	@ResponseBody
	@RequestMapping("/loadUserList")
	public Page loadUserList(Page page,UserRequestForm user,HttpSession session){
		try {
			page = iUserService.loadUserList(page, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping("/saveUser")
	public String saveUser(Model model,User user,HttpServletRequest request){
		if(null != user){
			try {
				ResultDto<User> dto = iUserService.saveUser(user);
				if(null!=dto&&"success".equals(dto.getResult())){
					return "/user/userlist";
				}
				if(null!=dto&&"fail".equals(dto.getResult())){
					model.addAttribute("user", user);
					model.addAttribute("result", dto.getErrorMsg());
					return "/user/adduser";
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(user.getId()!=null){
					model.addAttribute("result", "更新用户信息异常，请稍后重试");
					return "/user/edituser";
				}else{
					model.addAttribute("result", "新增用户信息异常，请稍后重试");
					return "/user/adduser";
				}
			}
		}
		return "/user/userlist";
	}
	
	@RequestMapping("/getById")
	public String getById(Model model,Integer id,HttpServletRequest request,HttpSession session){
		if(id!=null){
			try {
				User user = iUserService.getById(id);
				model.addAttribute("user", user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "/user/showUser";
	}
	
	@ResponseBody
	@RequestMapping("/deleteUserById")
	public String deleteUserById(Integer id,HttpServletRequest request,HttpSession session){
		ResultDto<User> dto = null;
		try {
			dto = iUserService.deleteUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
			dto = new ResultDto<User>();
			dto.setResult("fail");
		}
		return JSONUtils.valueToString(dto);
	}
	
}
