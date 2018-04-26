package com.webapps.controller;

import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.User;
import com.webapps.common.utils.ValidatorCodeUtil;
import com.webapps.service.IUserService;

@Controller
@RequestMapping(value="/login")
public class LoginController {

	@Autowired
	private IUserService iUserService;
	
	@RequestMapping(value="/toLoginPage")
	public String toLoginPage(Model model){
		return "redirect:/login.jsp";
	}

	@RequestMapping(value = "/userLogin",method = RequestMethod.POST)
	public String login(Model model,
			@RequestParam("account")String account,
			@RequestParam("password")String password,
			HttpServletRequest request) {
		if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
			model.addAttribute("loginResult", "用户名或密码不能为空！");
			return "redirect:/login.jsp";
		}
		try {
			User user = new User();
			user.setAccount(account);
			user.setPassword(password);
			User temp = iUserService.login(user);
			if (temp != null) {
				if(temp.getUserType()==3){
					model.addAttribute("loginResult", "无登录权限！");
					return "redirect:/permissionDenied.jsp";
				}
				Map<String,String> results = iUserService.loadUserPermission(temp.getId());
				request.getSession().setAttribute("user", temp);
				request.getSession().setAttribute("perMap", results);
				return "redirect:/index.jsp";
			} else{
				return "forward:/index";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward:/views/index";
	}

	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(Model model,@RequestParam("userId") Integer userId,HttpServletRequest request){
		request.getSession().removeAttribute("user");
		return "redirect:/login.jsp";
	}
	
	@RequestMapping(value="/loginFromWeixin",method=RequestMethod.POST)
	public String loginFromWeixin(Model model,@RequestParam("weixin")String weixin,
			HttpServletRequest request){
		try {
			ResultDto<User> dto = iUserService.queryUserByWeixin(weixin);
			if("S".equals(dto.getResult())){
				request.getSession().setAttribute("user", dto.getData());
				return "/weixin/success";
			}else{
				return "/weixin/fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/weixin/page";
	}
	
	/*
     * @Resource默认按 byName 自动注入,是J2EE提供的， 需导入Package: javax.annotation.Resource;
     * @Resource有两个中重要的属性：name和type ，而Spring将@Resource注解的name属性解析为bean的
     * 名字，而type属性则解析为bean的类型。所以如果使用name属性，则使用byName的自动注入策略，而使用 type属性时则使用
     * byType自动注入策略。如果既不指定name也不指定type属性，这时将通过反射机制使用by Name自动注入策略。
     */

    @RequestMapping(value = "/getValidatorImage")
    public void getValidatorImage(HttpServletRequest request, HttpServletResponse response
            ) throws IOException {
        // 禁止图片缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 将图像输出到servlet输出流中
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(ValidatorCodeUtil.getImage(request), "jpeg", sos);
        sos.close();
    }
}
