package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.User;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IApplyExpenditureService;

import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
@RequestMapping(value="applyExpenditure")
public class ApplyExpenditureController {
	
	@Autowired
	private IApplyExpenditureService iApplyExpenditureService;
	
	@RequestMapping("/toApplyExpenditureListPage")
	public String toCompanyListPage(HttpServletRequest request,HttpServletResponse response){
		return "/enrollApproval/applyExpenditureList";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadPageList",produces = "text/html;charset=UTF-8")
	public String loadPageList(Page page,ApplyExpenditureRequestForm form,HttpServletRequest request){
		if(form!=null&&form.getUser()!=null&& StringUtils.isNotBlank(form.getUser().getName())){
			try {
				form.getUser().setName(URLDecoder.decode(form.getUser().getName(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		page = iApplyExpenditureService.loadPageList(page, form);
		return JSONUtil.toJSONString(JSONObject.fromObject(page));
	}
	
	@ResponseBody
	@RequestMapping(value="/approvalById")
	public String approvalById(Integer id,Integer state,String reason,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		ResultDto<String> dto = iApplyExpenditureService.approveById(id, state, user.getId(), reason);
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

}
