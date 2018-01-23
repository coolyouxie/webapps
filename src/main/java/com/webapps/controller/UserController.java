package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webapps.common.entity.Picture;
import com.webapps.common.utils.JSONUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
import com.webapps.service.IPictureService;

import net.sf.json.util.JSONUtils;

import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private IUserService iUserService;

	@Autowired
	private  IPictureService iPictureService;

	
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
	@RequestMapping(value="/loadUserList",produces = "text/html;charset=UTF-8")
	public String loadUserList(Page page,UserRequestForm user){
		try {
			if(user!=null&& StringUtils.isNotBlank(user.getName())){
				user.setName(URLDecoder.decode(user.getName(),"UTF-8"));
			}
			page = iUserService.loadUserList(page, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(JSONObject.fromObject(page));
	}
	
	@RequestMapping("/saveUser")
	public String saveUser(Model model,User user,HttpServletRequest request){
		if(null != user){
			try {
				ResultDto<User> dto = iUserService.saveUser(user);
				if(null!=dto&&"S".equals(dto.getResult())){
					return "/user/userlist";
				}
				if(null!=dto&&"F".equals(dto.getResult())){
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
				ResultDto<List<Picture>> dto = iPictureService.queryUserPictures(id);
				if (null!=dto&&!"F".equals(dto.getResult())){
					user.setPictures(dto.getData());
				}
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
			dto.setResult("F");
		}
		return JSONUtils.valueToString(dto);
	}
	
	@ResponseBody
	@RequestMapping(value="/transactionTest")
	public String transactionTest(){
		try {
			iUserService.transactionTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
