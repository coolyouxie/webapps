package com.webapps.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.webapps.common.entity.User;
import com.webapps.common.utils.ValidatorCodeUtil;

public class UserValidateInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		String validateCode = (String)arg0.getSession().getAttribute(ValidatorCodeUtil.sessionKey);
		String checkCode = (String)arg0.getParameter("checkCode");
		String path = arg0.getContextPath();
		if(StringUtils.isBlank(validateCode)){
			arg1.sendRedirect(path+"/login.jsp");
			return false;
		}else if(!validateCode.equalsIgnoreCase(checkCode)){
			arg1.sendRedirect(path+"/login.jsp");
			return false;
		}
		return true;
	}

}
