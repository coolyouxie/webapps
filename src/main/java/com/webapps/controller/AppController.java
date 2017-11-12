package com.webapps.controller;

import java.util.*;

import com.webapps.common.entity.*;
import com.webapps.service.*;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONString;
import net.sf.json.util.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import com.webapps.common.form.BannerConfigRequestForm;
import com.webapps.common.form.BillRecordRequestForm;
import com.webapps.common.form.MessageConfigRequestForm;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.common.utils.PropertyUtil;
import com.webapps.mapper.IEnrollmentExtraMapper;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value = "appServer")
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

	@Autowired
	private IAliSmsMsgService iAliSmsMsgService;

	@Autowired
	private IUserService iUserService;

	@Autowired
	private ICompanyService iCompanyService;
	
	@Autowired
	private IFeeConfigService iFeeConfigService;
	
	@Autowired
	private IMessageConfigService iMessageConfigService;
	
	@Autowired
	private IEnrollApprovalService iEnrollApprovalService;
	
	@Autowired
	private IApplyExpenditureService iApplyExpenditureService;
	
	@Autowired
	private IUserWalletService iUserWalletService;
	
	@Autowired
	private IBillRecordService iBillRecordService;
	
	@Autowired
	private IPictureService iPictureService;

	@Autowired
	private IEnrollmentExtraMapper iEnrollmentExtraMapper;
	
	@Autowired
	private IUserRewardService iUserRewardService;

	@Autowired
	private IGroupUserService iGroupUserService;

	/**
	 * app端登录接口
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String login(@RequestBody String params) {
		logger.info("用户登录接口login接收参数：" + params);
		ResultDto<User> dto = new ResultDto<User>();
		Gson gson = new Gson();
		User user = gson.fromJson(params, User.class);
		try {
			user = iUserService.login(user);
			if (user != null) {
				user.setPassword(null);
				dto.setData(user);
				dto.setResult("S");
			} else {
				dto.setErrorMsg("密码错误或用户不存在");
				dto.setResult("F");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("登录异常，请稍后重试");
			dto.setResult("F");
		}
		return JSONUtil.toJSONObjectString(JSONObject.fromObject(dto));
	}
	
	/**
	 * 填写用户信息，如密码等
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String register(@RequestBody String params) {
		User user = new User();
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		String smsCode = jsonObj.getString("smsCode");
		Integer asmId = jsonObj.getInt("asmId");
		String password = jsonObj.getString("password");
		String telephone = jsonObj.getString("telephone");
		user.setPassword(password);
		user.setTelephone(telephone);
		user.setAccount(telephone);
		user.setCurrentState(0);
		user.setIsPayedRecommendFee(0);
		ResultDto<User> dto = null;
		try {
			ResultDto<String> dto1 = iAliSmsMsgService.validateAliSmsCode(asmId, smsCode);
			if(dto1.getResult().equals("S")){
				//默认APP注册的全部为普通会员
				user.setUserType(3);
				user.setMobile(telephone);
				dto = iUserService.saveUser(user);
				User user1 = new User();
				user1.setId(user.getId());
				dto.setData(user1);
			}else{
				return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto = new ResultDto<User>();
			dto.setResult("F");
			dto.setErrorMsg("注册时异常，请稍后重试");
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@ResponseBody
	@RequestMapping(value="/resetPassword", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String resetPassword(@RequestBody String params){
		JSONObject obj = JSONObject.fromObject(params);
		String phoneNumber = obj.getString("phoneNumber");
		String password = obj.getString("password");
		String smsCode = obj.getString("smsCode");
//		Integer type = obj.getInt("type");
		ResultDto<String> dto1 = iAliSmsMsgService.validateAliSmsCode(phoneNumber, 2, smsCode);
		ResultDto<String> dto2 = null;
		if("S".equals(dto1.getResult())){
			dto2 = iUserService.resetPassword(phoneNumber, password);
		}
		return JSONUtil.toJSONString(JSONObject.fromObject(dto2));
	}
	
	/**
	 * 获取验证码，并保存到数据库
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getValidateCode", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getValidateCode(@RequestBody String params) {
		Gson gson = new Gson();
		AliSmsMsg asm = gson.fromJson(params, AliSmsMsg.class);
		ResultDto<AliSmsMsg> dto = iAliSmsMsgService.getAliSmsCode(asm.getPhoneNumbers(), asm.getType());
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@ResponseBody
	@RequestMapping(value = "/validateSmsCode", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String validateSmsCode(@RequestBody String params) {
		Gson gson = new Gson();
		AliSmsMsg asm = gson.fromJson(params, AliSmsMsg.class);
		ResultDto<String> dto = iAliSmsMsgService.validateAliSmsCode(asm.getId(), asm.getValidateCode());
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	@ResponseBody
	@RequestMapping(value = "/userRecommend", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String userRecommend(@RequestBody String params) {
		logger.info("获取用户推荐列表getUserRecommomendList接收参数:" + params);
		Gson gson = new Gson();
		Recommend r = gson.fromJson(params, Recommend.class);
		ResultDto<Recommend> dto = null;
		try {
			dto = iRecommendService.userRecommend(r);
		} catch (Exception e) {
			dto.setErrorMsg("保存推荐信息异常，请稍后再试");
			dto.setResult("F");
			logger.error(e.getMessage());
			return gson.toJson(dto);
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	/**
	 * app端获取用户推荐列表
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserRecommendList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getUserRecommendList(@RequestBody String params) {
		logger.info("获取用户推荐列表getUserRecommomendList接收参数:" + params);
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
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserEnrollmentList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getUserEnrollmentList(@RequestBody String params) {
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
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userEnroll", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String userEnroll(@RequestBody String params) {
		logger.info("用户报名接口userEnroll接收参数:" + params);
		Gson gson = new Gson();
		Enrollment em = gson.fromJson(params, Enrollment.class);
		ResultDto<Enrollment> dto = null;
		if (StringUtils.isNotBlank(params)) {
			try {
				dto = iEnrollmentService.userEnroll(em);
			} catch (Exception e) {
				dto = new ResultDto<Enrollment>();
				dto.setErrorMsg("报名异常，请稍后再试");
				dto.setResult("F");
				e.printStackTrace();
			}
		} else {
			dto = new ResultDto<Enrollment>();
			dto.setErrorMsg("参数为空");
			dto.setResult("F");
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	/**
	 * 获取发布单列表
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRecruitmentList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getRecruitmentList(@RequestBody String params) {
		ResultDto<List<Recruitment>> dto = new ResultDto<List<Recruitment>>();
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		int currentPage = jsonObj.getInt("page");
		int rows = jsonObj.getInt("rows");
		RecruitmentRequestForm form = new RecruitmentRequestForm();
		if(jsonObj.containsKey("publishType")&&StringUtils.isNotBlank(jsonObj.getString("publishType"))){
			Integer publishType = jsonObj.getInt("publishType");
			if(publishType!=0){
				form.setPublishType(publishType);
			}
		}
		String companyName = jsonObj.getString("companyName");
		Page page = new Page();
		page.setPage(currentPage);
		page.setRows(rows);
		if (StringUtils.isNotBlank(companyName)) {
			Company company = new Company();
			company.setName(companyName);
			form.setCompany(company);
		}
		try {
			page = iRecruitmentService.loadRecruitmentList(page, form);
			if (page != null) {
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

	/**
	 * 获取发布单详情
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRecruitmentDetail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getRecruitmentDetail(@RequestBody String params) {
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer id = obj.getInt("id");
		ResultDto<Recruitment> dto = new ResultDto<Recruitment>();
		try {
			Recruitment r = iRecruitmentService.getById(id);
			Company company = iCompanyService.getById(r.getCompany().getId());
			r.setCompany(company);
			dto.setData(r);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("查询信息异常，请稍后再试");
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	/**
	 * 获取公司详情信息
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCompanyInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getCompanyInfo(@RequestBody String params) {
		Gson gson = new Gson();
		ResultDto<Company> dto = new ResultDto<Company>();
		Company c = gson.fromJson(params, Company.class);
		try {
			c = iCompanyService.getById(c.getId());
			dto.setData(c);
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("查询企业信息异常，请稍后重试");
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	/**
	 * 获取金额配置信息
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFeeConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getFeeConfig(@RequestBody String params) {
		ResultDto<List<FeeConfig>> dto = new ResultDto<List<FeeConfig>>();
		try {
			List<FeeConfig> temp = iFeeConfigService.queryAll();
			dto.setData(temp);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("查询信息异常，请稍后再试");
			dto.setResult("F");
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	/**
	 * 获取banner配置
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBannerConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getBannerConfig(@RequestBody String params) {

		return null;

	}

	/**
	 * 获取消息配置
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getMessageConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getMessageConfig(@RequestBody String params) {
		ResultDto<List<MessageConfig>> dto = new ResultDto<List<MessageConfig>>();
		JSONObject jsonObj = JSONObject.fromObject(params);
		Integer rows = jsonObj.getInt("rows");
		Integer curPage = jsonObj.getInt("page");
		Page page = new Page();
		page.setRows(rows);
		page.setPage(curPage);
		page = iMessageConfigService.loadMessageConfigList(page, new MessageConfigRequestForm());
		if(page!=null){
			List<MessageConfig> list = page.getResultList();
			dto.setData(list);
			dto.setResult("S");
		}else{
			dto.setResult("F");
			dto.setErrorMsg("无更多数据");
		}
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}
	
	/**
	 * 获取用户钱包信息
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserWallet", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getUserWallet(@RequestBody String params) {
		JSONObject obj = JSONObject.fromObject(params);
		Integer userId = obj.getInt("userId");
		UserWallet uw = iUserWalletService.getUserWalletByUserId(userId);
		ResultDto<UserWallet> dto = new ResultDto<UserWallet>();
		if(uw==null){
			dto.setErrorMsg("用户钱包信息获取失败");
			dto.setResult("F");
		}else{
			dto.setData(uw);
			dto.setResult("S");
		}
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

	/**
	 * 提现申请
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/applyCashback", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String applyCashback(@RequestBody String params) {
		JSONObject obj = JSONObject.fromObject(params);
		Integer userId = obj.getInt("userId");
		Integer walletId = obj.getInt("walletId");
		ResultDto<String> dto = iApplyExpenditureService.applyExpenditure(userId, walletId);
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

	/**
	 * 查询所有Banner信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryBannerConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String queryBannerConfig() {
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

	/*
	 * 更新用户信息
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String updateUserInfo(@RequestBody String params) {
		logger.info("更新用户信息接口updateUserInfo接收参数：" + params);
		Gson gson = new Gson();
		User ur = gson.fromJson(params, User.class);
		ResultDto<User> dto = null;
		try {
			dto = iUserService.saveUser(ur);
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("保存用户信息异常，请稍后再试");
			dto.setResult("F");
			logger.error(e.getMessage());
			return gson.toJson(dto);
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	/*
	 * 获取用户信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getUserInfo(@RequestBody String params) {
		logger.info("获取用户信息接口getUserInfo接收参数：" + params);
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer id = obj.getInt("id");
		ResultDto<User> dto = new ResultDto<User>();
		try {
			User ur = iUserService.getById(id);
			if (ur != null) {
				ur.setPassword(null);
			}
			dto.setData(ur);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("获取数据异常");
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@ResponseBody
	@RequestMapping(value = "/applyApproval", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String applyApproval(@RequestBody String params) {
		ResultDto<EnrollApproval> dto = null;
		logger.info("获取用户申请审核接口applyApproval接收参数：" + params);
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer type = obj.getInt("approvaType");
		try {
			if(type==1){
				dto = iEnrollApprovalService.applyEntryApproval(obj);
			}else if(type==2) {
				dto = iEnrollApprovalService.applyExpireApproval(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("发起审核异常");
			return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	/**
	 * 查询提现记录
	 * @param params
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/applyExpenditureList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String applyExpenditureList(@RequestBody String params) {
		ResultDto<List<ApplyExpenditure>> dto = new ResultDto<List<ApplyExpenditure>>();
		logger.info("获取用户提现申请接口applyExpenditureList接收参数：" + params);
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer walletId = obj.getInt("walletId");
		Page page = new Page();
		try {
			ApplyExpenditureRequestForm apply = new ApplyExpenditureRequestForm();
			apply.setWalletId(walletId);
			page = iApplyExpenditureService.loadPageList(page, apply);
			dto.setData(page.getResultList());
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("提现记录查询异常");
			return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	/**
	 * 查询进账记录
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/expireApprovalSuccessList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String expireApprovalSuccessList(@RequestBody String params) {
		logger.info("获取用户期满审核通过接口expireApprovalSuccessList接收参数：" + params);
		ResultDto<List<EnrollApproval>> dto = new ResultDto<List<EnrollApproval>>();
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer userId = obj.getInt("userId");
		try {
			dto = iEnrollApprovalService.queryByUserIdTypeAndState(userId, 2, 1);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("提现记录查询异常");
			return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loanBillRecordList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String loanBillRecordList(@RequestBody String params) {
		logger.info("获取用户期满审核通过接口expireApprovalSuccessList接收参数：" + params);
		ResultDto<List<BillRecord>> dto = new ResultDto<List<BillRecord>>();
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer walletId = obj.getInt("walletId");
		BillRecordRequestForm form = new BillRecordRequestForm();
		form.setWalletId(walletId);
		form.setType(1);
		Page page = new Page();
		page.setPage(1);
		page.setEndRow(10);
		try {
			page = iBillRecordService.loadPageList(page, form);
			dto.setResult("S");
			dto.setData(page.getResultList());
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("推荐费记录查询异常");
			return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@ResponseBody
	@RequestMapping(value = "/getContactInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getContactInfo() {
		ResultDto<JSONObject> dto = new ResultDto<JSONObject>();
		try {
			JSONObject obj = new JSONObject();
			String weiXin = (String) PropertyUtil.getProperty("weiXin");
			String telephone = (String) PropertyUtil.getProperty("telephone");
			String qq = (String) PropertyUtil.getProperty("qq");
			obj.put("weiXin", weiXin);
			obj.put("telephone", telephone);
			obj.put("qq", qq);
			dto.setData(obj);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("查询联系方式异常");
			return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	/**
	 * 获取用户状态
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserState", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getUserState(@RequestBody String params) {
		logger.info("获取用户期满审核通过接口expireApprovalSuccessList接收参数：" + params);
		ResultDto<JSONObject> dto = new ResultDto<JSONObject>();
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer userId = obj.getInt("userId");
		try{
			User user = iUserService.getById(userId);
			List<Enrollment> list = iEnrollmentService.queryEnrollmentListByUserId(userId);
			Enrollment em = null;
			String reason = "";
			Integer currentState = user.getCurrentState();
			if(CollectionUtils.isNotEmpty(list)){
				for(Enrollment temp:list){
					if(temp.getState()==21||temp.getState()==31){
						em = temp;
						break;
					}
				}
				for(Enrollment temp:list){
					if(temp.getState()==22||temp.getState()==32){
						reason = temp.getFailedReason();
						break;
					}
				}
				if(list.get(0).getState()==21||list.get(0).getState()==31
						||list.get(0).getState()==1){
					reason = "";
				}
			}
			JSONObject result = new JSONObject();
			result.put("currentState",currentState);
			result.put("enrollment",em);
			result.put("reason",reason);
			dto.setResult("S");
			dto.setData(result);
			return JSONUtil.toJSONString(JSONObject.fromObject(dto));
		}catch (Exception e){
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("获取用户状态时异常");
			return JSONUtil.toJSONString(JSONObject.fromObject(dto));
		}
	}
	
	/**
	 * app端保存和更新图片
	 * @param files
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/uploadImg", produces = "text/html;charset=UTF-8")
	public String uploadImg(@RequestParam(value = "file", required = true) MultipartFile[] files,
			@RequestParam("userId")String userId){
		ResultDto<String> dto = null;
		dto = iPictureService.uploadImgForApp(files, userId);
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}
	
	/**
	 * 获取用户图片
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUserPictures",produces = "text/html;charset=UTF-8")
	public String getUserPictures(@RequestBody String params){
		JSONObject obj = JSONObject.fromObject(params);
		Integer userId = obj.getInt("userId");
		ResultDto<List<Picture>> dto = null;
		dto = iPictureService.queryUserPictures(userId);
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

	@ResponseBody
	@RequestMapping(value = "/getEnrollmentExtraInfo",produces = "text/html;charset=UTF-8")
	public String getEnrollmentextraInfo(@RequestBody String params){
		JSONObject obj = JSONObject.fromObject(params);
		Integer emId = obj.getInt("enrollmentId");
		ResultDto<List<EnrollmentExtra>> dto = new ResultDto<List<EnrollmentExtra>>();
		try {
			List<EnrollmentExtra> list = iEnrollmentExtraMapper.queryListByEnrollmentId(emId);
			dto.setData(list);
			dto.setResult("S");
			return(JSONUtil.toJSONString(JSONObject.fromObject(dto)));
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("报名扩展信息查询异常");
			dto.setResult("F");
			return(JSONUtil.toJSONString(JSONObject.fromObject(dto)));
		}
	}
	
	@RequestMapping(value="/toSharePage")
	public String toSharePage(Model model){
		String downloadUrl = (String)PropertyUtil.getProperty("downloadUrl");
		String androidVersion = (String)PropertyUtil.getProperty("androidVersion");
		String iosUrl = (String)PropertyUtil.getProperty("iosUrl");
		model.addAttribute("downloadUrl",downloadUrl);
		model.addAttribute("androidVersion",androidVersion);
		model.addAttribute("iosUrl",iosUrl);
		return "/common/share";
	}

	@ResponseBody
	@RequestMapping(value = "/appVersion",produces = "text/html;charset=UTF-8")
	public String appVersion(){
		String iosVersion = (String)PropertyUtil.getProperty("iosVersion");
		String androidVersion = (String)PropertyUtil.getProperty("androidVersion");
		String iosUrl = (String)PropertyUtil.getProperty("iosUrl");
		String androidUrl = (String)PropertyUtil.getProperty("androidUrl");
		String iosUpdateLog = (String)PropertyUtil.getProperty("iosUpdateLog");
		String androidUpdateLog = (String)PropertyUtil.getProperty("androidUpdateLog");
		ResultDto<JSONObject> dto = new ResultDto<JSONObject>();
		JSONObject obj = new JSONObject();
		obj.put("iosVersion",iosVersion);
		obj.put("androidVersion",androidVersion);
		obj.put("iosUrl",iosUrl);
		obj.put("androidUrl",androidUrl);
		obj.put("iosUpdateLog",iosUpdateLog);
		obj.put("androidUpdateLog",androidUpdateLog);
		dto.setData(obj);
		dto.setResult("S");
		return JSONUtil.toJSONString(JSONObject.fromObject(dto));
	}

	@RequestMapping(value="/download")
	public String downloadApp(Model model){
		String downloadUrl = (String)PropertyUtil.getProperty("downloadUrl");
		String androidVersion = (String)PropertyUtil.getProperty("androidVersion");
		String iosUrl = (String)PropertyUtil.getProperty("iosUrl");
		model.addAttribute("downloadUrl",downloadUrl);
		model.addAttribute("androidVersion",androidVersion);
		model.addAttribute("iosUrl",iosUrl);
		return "/common/download";
	}
	
	@RequestMapping(value="/toPrizeDraw")
	public String toPrizeDraw(Model model, String params){
		JSONObject obj = JSONObject.fromObject(params);
		Integer userId = obj.getInt("userId");
		ResultDto<UserReward> dto = iUserRewardService.getUserRewardByUserIdAndType(userId, 2);
		model.addAttribute("userReward", dto.getData());
		model.addAttribute("result", dto.getResult());
		return "/common/prizeDraw";
	}

	@ResponseBody
	@RequestMapping(value = "/queryRecruitmentListByType",produces = "text/html;charset=UTF-8")
	public String queryRecruitmentListByType(@RequestBody String params){
		ResultDto<List<Recruitment>> dto = new ResultDto<List<Recruitment>>();
		try {
			List<Recruitment> list = iRecruitmentService.queryListByType(1);
			dto.setData(list);
			dto.setResult("S");
			return JSONUtil.toJSONString(JSONObject.fromObject(dto));
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("查询发布单异常");
			return JSONUtil.toJSONString(JSONObject.fromObject(dto));
		}
	}

	@ResponseBody
	@RequestMapping(value="/batchEnroll",produces ="text/html;charset=UTF-8" )
	public String saveGroupUser(@RequestBody String params){
		ResultDto<String> dto = null;
		try {
			JSONObject object = JSONObject.fromObject(params);
			GroupUser leader = (GroupUser) JSONObject.toBean(JSONObject.fromObject(object.getString("leader")),GroupUser.class);
			JSONArray array = JSONArray.fromObject(object.getString("users"));
			List list = (List) JSONArray.toCollection(array,GroupUser.class);
			if(CollectionUtils.isNotEmpty(array)) {
				dto = iGroupUserService.batchInsert(list, leader);
			}
			return JSONUtil.toJSONString(JSONObject.fromObject(dto));
		} catch (Exception e) {
			e.printStackTrace();
			if(dto==null){
				dto = new ResultDto<>();
				dto.setErrorMsg("保存信息异常");
				dto.setResult("F");
			}
			return JSONUtil.toJSONString(JSONObject.fromObject(dto));
		}
	}

	public static void main(String[] args){
		GroupUser user = new GroupUser();
		user.setUserName("test");
		user.setUserMobile("17321004552");
		GroupUser user1 = new GroupUser();
		user1.setUserName("test");
		user1.setUserMobile("17321004552");
		List<GroupUser> list = new ArrayList<>();
		list.add(user);
		list.add(user1);
		Map<String,Object> map = new HashMap<>();
		GroupUser leader = new GroupUser();
		leader.setLeaderName("leader");
		leader.setLeaderMobile("17321004552");
		map.put("leader",leader);
		map.put("users",list);
		String jsonStr = JSONUtil.toJSONString(map);
		System.out.println(jsonStr);
//		JSONObject object = JSONObject.fromObject(jsonStr);
		JSONArray array = JSONArray.fromObject(jsonStr);
		System.out.println();
//		List list1 = (List)JSONArray.toCollection(array, User.class);
//		System.out.println(((User)list1.get(0)).getMobile());
	}
}
