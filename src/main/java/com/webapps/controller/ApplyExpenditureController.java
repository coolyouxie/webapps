package com.webapps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@Autowired
	private IUserService iUserService;
	
	@RequestMapping("/toExpenditureApproveListPage")
	public String toCompanyListPage(HttpServletRequest request,HttpServletResponse response){
		return "/enrollApproval/expenditureApproveList";
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
	@RequestMapping(value="/expenditureApprovalById")
	public String expenditureApprovalById(Integer id,Integer state,String reason,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		ResultDto<String> dto = iApplyExpenditureService.approveById(id, state, user.getId(), reason);
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

	@RequestMapping(value="/toApproveExpenditurePage")
	public String toApproveExpenditurePage(Model model, Integer id){
		ApplyExpenditure dto = iApplyExpenditureService.loadById(id);
		model.addAttribute("dto",dto);
		return "/enrollApproval/approveExpenditure";
	}

	@RequestMapping(value="/toShowExpenditurePage")
	public String toShowExpenditurePage(Model model, Integer id){
		ApplyExpenditure dto = iApplyExpenditureService.loadById(id);
		model.addAttribute("dto",dto);
		try {
			if(dto.getApproverId()!=null){
				User approver = iUserService.getById(dto.getApproverId());
				model.addAttribute("approver",approver);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollApproval/showExpenditureInfo";
	}
}
