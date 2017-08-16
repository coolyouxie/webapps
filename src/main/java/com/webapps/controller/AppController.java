package com.webapps.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.Recommend;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.BannerConfigRequestForm;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IBannerConfigService;
import com.webapps.service.IEnrollmentService;
import com.webapps.service.IRecommendService;
import com.webapps.service.IRecruitmentService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value="appServer")
public class AppController {
	
	private static Logger logger = Logger.getLogger(AppController.class);
	
	@Autowired
	private IBannerConfigService iBannerConfigService;
	
	@Autowired
	private IRecruitmentService iRecruitmentService;
	
	@Autowired
	private IEnrollmentService iEnrollmentService;
	
	@Autowired
	private IRecommendService iRecommendService;
	
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
	
	@ResponseBody
	@RequestMapping(value="/userRecommend", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String userRecommend(@RequestBody String params){
		logger.info("获取用户推荐列表getUserRecommomendList接收参数:"+params);
		Gson gson = new Gson();
		Recommend r = gson.fromJson(params, Recommend.class);
		ResultDto<Recommend> dto = null;
		try {
			dto = iRecommendService.saveRecommend(r);
			if("success".equals(dto.getResult())){
				dto.setResult("S");
			}else{
				dto.setResult("F");
			}
		} catch (Exception e) {
			dto.setErrorMsg("保存异常，请稍后再试");
			dto.setResult("F");
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	/**
	 * app端获取用户推荐列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUserRecommendList", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String getUserRecommendList(@RequestBody String params){
		logger.info("获取用户推荐列表getUserRecommomendList接收参数:"+params);
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		Integer userId = jsonObj.getInt("userId");
		ResultDto<List<Recommend>> dto = new ResultDto<List<Recommend>>();
		try {
			List<Recommend> list = iRecommendService.queryRecommendListByUserId(userId);
			dto.setData(list);
			dto.setResult("S");
		} catch (Exception e) {
			dto.setErrorMsg("查询异常，请稍后再试");
			dto.setResult("F");
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	/**
	 * 获取用户报名列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUserEnrollmentList", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String getUserEnrollmentList(@RequestBody String params){
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		Integer userId = jsonObj.getInt("userId");
		ResultDto<List<Enrollment>> dto = new ResultDto<List<Enrollment>>();
		try {
			List<Enrollment> list = iEnrollmentService.queryEnrollmentListByUserId(userId);
			dto.setData(list);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("查询异常，请稍后再试");
			dto.setResult("F");
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	/**
	 * 用户报名接口
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userEnroll", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String userEnroll(@RequestBody String params){
		logger.info("用户报名接口userEnroll接收参数:"+params);
		Gson gson = new Gson();
		Enrollment em = gson.fromJson(params, Enrollment.class);
		ResultDto<Enrollment> dto = null;
		if(StringUtils.isNotBlank(params)){
			try {
				dto = iEnrollmentService.userEnroll(em);
			} catch (Exception e) {
				dto = new ResultDto<Enrollment>();
				dto.setErrorMsg("报名异常，请稍后再试");
				dto.setResult("F");
				e.printStackTrace();
			}
		}else{
			dto = new ResultDto<Enrollment>();
			dto.setErrorMsg("参数为空");
			dto.setResult("F");
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
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
	@RequestMapping(value="/getRecruitmentDetail", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String getRecruitmentDetail(@RequestBody String params){
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer id = obj.getInt("id");
		ResultDto<Recruitment> dto = new ResultDto<Recruitment>();
		try {
			Recruitment r = iRecruitmentService.getById(id);
			dto.setData(r);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("查询信息异常，请稍后再试");
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
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
	@RequestMapping(value="/getBannerConfig")
	public String getBannerConfig(String params){
		
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
	@RequestMapping(value="/queryBannerConfig", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
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
