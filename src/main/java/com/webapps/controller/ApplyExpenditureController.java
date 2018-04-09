package com.webapps.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.entity.User;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import com.webapps.common.form.BillRecordRequestForm;
import com.webapps.common.form.EnrollApprovalRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IApplyExpenditureService;
import com.webapps.service.IBillRecordService;
import com.webapps.service.IEnrollApprovalService;
import com.webapps.service.IUserService;

@Controller
@RequestMapping(value="applyExpenditure")
public class ApplyExpenditureController {

	private static Logger logger = Logger.getLogger(ApplyExpenditureController.class);
	
	@Autowired
	private IApplyExpenditureService iApplyExpenditureService;

	@Autowired
	private IUserService iUserService;

	@Autowired
	private IEnrollApprovalService iEnrollApprovalService;

	@Autowired
	private IBillRecordService iBillRecordService;

	
	@RequestMapping("/toExpenditureApproveListPage")
	public String toCompanyListPage(HttpServletRequest request,HttpServletResponse response){
		return "/enrollApproval/expenditureApproveList";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadPageList",produces = "text/html;charset=UTF-8")
	public String loadPageList(Page page,ApplyExpenditureRequestForm form,HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		if(user!=null&&user.getUserType()!=1&&user.getUserType()!=2){
			if(form!=null&&form.getUser()!=null&& StringUtils.isNotBlank(form.getUser().getName())){
				try {
					form.getUser().setName(URLDecoder.decode(form.getUser().getName(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
//			form.setApproverId(user.getId());
		}
		page = iApplyExpenditureService.loadPageList(page, form);
		return JSONObject.toJSONString(page);
	}
	
	@ResponseBody
	@RequestMapping(value="/expenditureApprovalById")
	public String expenditureApprovalById(Integer id,Integer state,String reason,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		ResultDto<String> dto = iApplyExpenditureService.approveById(id, state, user.getId(), reason);
		return JSON.toJSONString(dto);
	}

	@RequestMapping(value="/toApproveExpenditurePage")
	public String toApproveExpenditurePage(Model model, Integer id){
		ApplyExpenditure dto = iApplyExpenditureService.loadById(id);
		if(dto!=null){
			Integer walletId = dto.getWalletId();
			ApplyExpenditureRequestForm form = new ApplyExpenditureRequestForm();
			form.setWalletId(walletId);
		}
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

	@ResponseBody
	@RequestMapping(value = "/loadExpireApproveSuccessPage")
	public String loadExpireApproveSuccessPage(Model model,Page page,Integer userId){
		EnrollApprovalRequestForm form = new EnrollApprovalRequestForm();
		User user = new User();
		user.setId(userId);
		form.setUser(user);
		form.setType(2);
		form.setState(1);
		page = iEnrollApprovalService.loadExpireApprovePage(page,form);
		return JSONUtil.toJSONString(page);
	}

	@ResponseBody
	@RequestMapping(value="/loadBillRecordPage")
	public String loadBillRecordPage(Model model,Page page,BillRecordRequestForm form){
		try {
			page = iBillRecordService.loadPageList(page, form);
			return JSONUtil.toJSONString(page);
		} catch (Exception e) {
			logger.error("查询红包记录异常");
			e.printStackTrace();
			return JSONUtil.toJSONString(page);
		}
	}
}
