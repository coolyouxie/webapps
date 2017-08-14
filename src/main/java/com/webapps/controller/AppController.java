package com.webapps.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.BannerConfigRequestForm;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IBannerConfigService;
import com.webapps.service.IRecruitmentService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value="appServer")
public class AppController {
	
	@Autowired
	private IBannerConfigService iBannerConfigService;
	
	@Autowired
	private IRecruitmentService iRecruitmentService;
	
	/**
	 * app端登录接口
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login")
	public String login(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/register")
	public String register(String params){
		
		return null;
	}
	
	/**
	 * 获取验证码，并保存到数据库
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getValidatCode")
	public String getValidatCode(String params){
		
		return null;
	}
	
	/**
	 * app端获取用户推荐列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUserRecommomend")
	public String getUserRecommomend(String params){
		
		return null;
	}

	/**
	 * 获取用户报名列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUserEnroll")
	public String getUserEnroll(String params){
		
		return null;
	}
	
	/**
	 * 获取发布单列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getRecruitmentList", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String getRecruitmentList(@RequestBody String params){
		ResultDto<List<Recruitment>> dto = new ResultDto<List<Recruitment>>();
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		int currentPage = jsonObj.getInt("page");
		int rows = jsonObj.getInt("rows");
		String companyName = jsonObj.getString("companyName");
		Page page = new Page();
		page.setPage(currentPage);
		page.setRows(rows);
		RecruitmentRequestForm form = new RecruitmentRequestForm();
		if(StringUtils.isNotBlank(companyName)){
			Company company = new Company();
			company.setName(companyName);
			form.setCompany(company);
		}
		try {
			page = iRecruitmentService.loadRecruitmentList(page, form);
			if(page!=null){
				dto.setData(page.getResultList());
				dto.setResult("S");
			}
		} catch (Exception e) {
			dto.setResult("F");
			dto.setErrorMsg("查询异常，请稍后再试");
			e.printStackTrace();
		}
		String result = JSONUtil.toJSONString(JSONObject.fromObject(dto));
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getRecruitmentDetail")
	public String getRecruitmentDetail(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getCompanyInfo")
	public String getCompanyInfo(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getFeeConfig")
	public String getFeeConfig(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBanner")
	public String getBanner(String params){
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getMessageConfig")
	public String getMessageConfig(String params){
		
		return null;
	}
	
	/**
	 * 提现申请
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/applyCashback")
	public String applyCashback(String params){
		
		return null;
	}
	
	/**
	 * 查询所有Banner信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryBannerConfig")
	public String queryBannerConfig(){
		ResultDto<List<BannerConfig>> dto = new ResultDto<List<BannerConfig>>();
		BannerConfigRequestForm form = new BannerConfigRequestForm();
		Page page = new Page();
		page.setStartRow(0);
		page.setEndRow(5);
		try {
			page = iBannerConfigService.loadBannerConfigList(page, form);
			dto.setData(page.getResultList());
			dto.setResult("S");
		} catch (Exception e1) {
			dto.setResult("F");
			dto.setErrorMsg("获取数据异常");
			e1.printStackTrace();
		}
		String result = JSONUtil.toJSONString(JSONObject.fromObject(dto));
		return result;
	}
	
}
