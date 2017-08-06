package com.webapps.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.webapps.common.entity.User;

public class UserSessionVerifyInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String applicationName = "/"
                + request.getContextPath().split("/")[request.getContextPath().split("/").length - 1];
        String requestUrl = request.getRequestURL().toString();
        if (requestUrl.endsWith(applicationName)) {
            return true;
        }
        if(requestUrl.endsWith(applicationName+"/")){
        	return true;
        }
		User user = (User)request.getSession().getAttribute("user");
		if(null==user){
			String path = request.getContextPath();
			response.sendRedirect(path+"/login.jsp");
		}else{
			return true;
		}
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
