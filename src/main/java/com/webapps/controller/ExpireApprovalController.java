package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.dto.EnrollApprovalInfoDto;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.entity.User;
import com.webapps.common.form.EnrollApprovalRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IEnrollApprovalService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("expireApproval")
public class ExpireApprovalController {

	@Autowired
	private IEnrollApprovalService iEnrollApprovalService;

	@RequestMapping("/toExpireApprovalListPage")
	public String toExpireApprovalListPage(HttpServletRequest request,HttpServletResponse response){
		return "/enrollApproval/expireApprovalList";
	}
	
	@ResponseBody
	@RequestMapping(value="/loadExpireApprovalList",produces = "text/html;charset=UTF-8")
	public String loadExpireApprovalList(Model model,Page page,EnrollApprovalRequestForm form){
		Page page1 = iEnrollApprovalService.getUserApprovalPage(page, form);
		if (page1 != null) return JSONUtil.toJSONString(JSONObject.fromObject(page));
		return null;
	}

	@RequestMapping("/getById")
	public String getById(Model model,Integer id,HttpServletRequest request,HttpServletResponse response){
		try {
			EnrollApproval ea = iEnrollApprovalService.getById(id);
			model.addAttribute("enrollApproval", ea);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollApproval/showExpireApproval";
	}
	
	@ResponseBody
	@RequestMapping(value="/expireApprovalById")
	public String expireApprovalById(Model model,Integer id,Integer state,String remark,String[] cashbackData,HttpServletRequest request){
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		User user = (User)request.getSession().getAttribute("user");
		try {
			//使用新的分阶段期满审核方法
			dto = iEnrollApprovalService.expireApprovalById(id, state, remark,user.getId());
		} catch (Exception e) {
			dto.setResult("F");
			dto.setErrorMsg("审核异常，请稍后重试");
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@RequestMapping(value="/toShowExpireInfoPage")
	public String toShowExpireInfoPage(Model model,Integer enrollApprovalId){
		try {
			EnrollApprovalInfoDto dto = iEnrollApprovalService.loadEnrollApprovalInfo(enrollApprovalId);
			model.addAttribute("dto", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollApproval/showExpireInfo";
	}
	
	@RequestMapping(value="/toEditExpireInfoPage")
	public String toEditExpireInfoPage(Model model,Integer enrollApprovalId){
		try {
			EnrollApprovalInfoDto dto = iEnrollApprovalService.loadEnrollApprovalInfo(enrollApprovalId);
			model.addAttribute("dto", dto);
			model.addAttribute("enrollApprovalId", enrollApprovalId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enrollApproval/editExpireInfo";
	}
	
}
