package com.webapps.controller.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Welcome {
	
	@RequestMapping("/")
	public String welcome(){
		return "forward:/ftl/login.jsp";
	}

}
