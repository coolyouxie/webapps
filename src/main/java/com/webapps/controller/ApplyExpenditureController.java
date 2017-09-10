package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IApplyExpenditureService;

import net.sf.json.JSONObject;

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
	@RequestMapping(value="/loadPageList")
	public Page loadPageList(Page page,ApplyExpenditureRequestForm form){
		page = iApplyExpenditureService.loadPageList(page, form);
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value="/approvalById")
	public String approvalById(Integer id,Integer state,String reason){
		ResultDto<String> dto = iApplyExpenditureService.approveById(id, state, null, reason);
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

}
