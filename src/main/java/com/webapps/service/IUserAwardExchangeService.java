package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.UserAwardExchange;
import com.webapps.common.form.UserAwardExchangeRequestForm;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface IUserAwardExchangeService {
	
	Page loadUserAwardExchangeList(Page page, UserAwardExchangeRequestForm form) throws Exception;

	UserAwardExchange getById(Integer id) throws Exception;
	
	ResultDto<UserAwardExchange> saveUserAwardExchange(UserAwardExchange uae) throws Exception;
	
	ResultDto<String> deleteUserAwardExchangeById(Integer id) throws Exception;

	void exportExcel(HttpSession session, HttpServletResponse response,UserAwardExchangeRequestForm form)throws Exception;

	List<UserAwardExchange> queryUserAwardExchangeByUserId(Integer userId)throws Exception;

	List<UserAwardExchange> queryAllUserLottery()throws Exception;

}
