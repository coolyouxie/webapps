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
	
	@ResponseBody
	@RequestMapping("/saveUser")
	public String saveUser(User user,HttpServletRequest request){
		if(null != user){
			try {
				iUserService.saveUser(user);
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
		ResultDto<String> dto = new ResultDto<String>();
		dto.setResult("success");
		return JSONUtils.valueToString(dto);
	}
	
	@ResponseBody
	@RequestMapping("getById")
	public ResultDto<User> getById(Integer id,HttpServletRequest request,HttpSession session){
		ResultDto<User> dto = new ResultDto<User>();
		if(id!=null){
			try {
				User user = iUserService.getById(id);
				dto.setData(user);
				dto.setResult("success");
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		dto.setResult("fail");
		return dto;
	}
	
	/**
	 * 新增或修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("addOrEdit")
	public ResultDto<User> addOrEdit(User user){
		return null;
	}
	
}
