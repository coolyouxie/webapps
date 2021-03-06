package com.webapps.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.dto.AppConfigDTO;
import com.webapps.common.dto.UserAwardListDTO;
import com.webapps.common.entity.AliSmsMsg;
import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.entity.AwardConfig;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.entity.BillRecord;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.EnrollmentExtra;
import com.webapps.common.entity.FeeConfig;
import com.webapps.common.entity.GroupUser;
import com.webapps.common.entity.MessageConfig;
import com.webapps.common.entity.ParamConfig;
import com.webapps.common.entity.Picture;
import com.webapps.common.entity.PromotionConfig;
import com.webapps.common.entity.Recommend;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.entity.User;
import com.webapps.common.entity.UserAwardExchange;
import com.webapps.common.entity.UserReward;
import com.webapps.common.entity.UserWallet;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import com.webapps.common.form.BannerConfigRequestForm;
import com.webapps.common.form.BillRecordRequestForm;
import com.webapps.common.form.MessageConfigRequestForm;
import com.webapps.common.form.PromotionConfigRequestForm;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.common.form.UserAwardExchangeRequestForm;
import com.webapps.common.form.UserSendInviteCode;
import com.webapps.common.utils.DataUtil;
import com.webapps.common.utils.DateUtil;
import com.webapps.common.utils.JSONUtil;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.mapper.IEnrollmentExtraMapper;
import com.webapps.service.IAliSmsMsgService;
import com.webapps.service.IApplyExpenditureService;
import com.webapps.service.IAwardConfigService;
import com.webapps.service.IBannerConfigService;
import com.webapps.service.IBillRecordService;
import com.webapps.service.ICompanyService;
import com.webapps.service.IEnrollApprovalService;
import com.webapps.service.IEnrollmentService;
import com.webapps.service.IFeeConfigService;
import com.webapps.service.IGroupUserService;
import com.webapps.service.IMessageConfigService;
import com.webapps.service.IParamConfigService;
import com.webapps.service.IPictureService;
import com.webapps.service.IPromotionConfigService;
import com.webapps.service.IRecommendService;
import com.webapps.service.IRecruitmentService;
import com.webapps.service.IUserAwardExchangeService;
import com.webapps.service.IUserAwardService;
import com.webapps.service.IUserRewardService;
import com.webapps.service.IUserService;
import com.webapps.service.IUserWalletService;


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
	
	@Autowired private IUserAwardService iUserAwardService;
	
	@Autowired private IParamConfigService iParamConfigService;

	@Autowired private IUserAwardExchangeService iUserAwardExchangeService;

	@Autowired private IAwardConfigService iAwardConfigService;
	
	@Autowired private IPromotionConfigService iPromotionConfigService;

	/**
	 * app端登录接口
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String login(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
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
		if(flag){
			return DataUtil.encryptData(JSONObject.toJSONString(dto));
		}
		return JSONObject.toJSONString(dto);
	}
	
	/**
	 * 填写用户信息，如密码等
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String register(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		User user = new User();
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		String smsCode = jsonObj.getString("smsCode");
		Integer asmId = jsonObj.getInteger("asmId");
		String password = jsonObj.getString("password");
		String telephone = jsonObj.getString("telephone");
		/**
		 * 新增字段，用户注册时，填写邀请码
		 * @author scorpio.yang
		 * @since 2018-01-15
		 */
		String inviteCode = jsonObj.getString("inviteCode");
		//支持APP端填写小写字母，后端统一用大写。
		inviteCode = inviteCode.toUpperCase();
		user.setPassword(password);
		user.setTelephone(telephone);
		user.setAccount(telephone);
		user.setCurrentState(0);
		user.setIsPayedRecommendFee(0);
		ResultDto<User> dto = null;
		try {
			ResultDto<String> dto1 = iAliSmsMsgService.validateAliSmsCode(asmId, smsCode);
			if(dto1.getResult().equals("S")){
				/**
				 * 新增逻辑，由于没有事务管理，所以一旦保存用户信息后，邀请码异常无法回滚，所以判断邀请码是否正确，需要提前查询，具体业务，后置仍然执行。
				 * @author scorpio.yang
				 * @since 2018-01-16
				 */
				if(StringUtils.isNotBlank(inviteCode)) {
					User tmpUser = iUserService.queryByInviteCode(inviteCode);
					if(null == tmpUser) {
						dto = new ResultDto<User>();
						dto.setResult("F");
						dto.setErrorMsg("邀请码填写错误，请检查！");
					}
				}
				//默认APP注册的全部为普通会员
				user.setUserType(3);
				user.setMobile(telephone);
				dto = iUserService.saveUser(user);
				User user1 = new User();
				user1.setId(user.getId());
				dto.setData(user1);
				
				/**
				 * 新增逻辑，用户注册保存成功后，判断是否是从邀请码过来的。如果是，则需要记录邀请信息
				 * @author scorpio.yang
				 * @since 2018-01-15
				 */
				if(StringUtils.isNotBlank(inviteCode)) {
					ResultDto<String> res = iRecommendService.saveInviteRecommend(user, inviteCode, null);
					if(res.getResult().equals("F")) {
						dto.setResult("F");
						dto.setErrorMsg(res.getErrorMsg());
					}
					user.setName(res.getData());
					dto = iUserService.saveUser(user);
				}
			}else{
				return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto = new ResultDto<User>();
			dto.setResult("F");
			dto.setErrorMsg("注册时异常，请稍后重试");
		}
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@ResponseBody
	@RequestMapping(value="/resetPassword", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String resetPassword(@RequestBody String params){
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject obj = JSON.parseObject(params);
		String phoneNumber = obj.getString("phoneNumber");
		String password = obj.getString("password");
		String smsCode = obj.getString("smsCode");
//		Integer type = obj.getInteger("type");
		ResultDto<String> dto1 = iAliSmsMsgService.validateAliSmsCode(phoneNumber, 2, smsCode);
		ResultDto<String> dto2 = null;
		if("S".equals(dto1.getResult())){
			dto2 = iUserService.resetPassword(phoneNumber, password);
		}
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto2)));
		}
		return JSON.toJSONString(dto2);
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		Gson gson = new Gson();
		AliSmsMsg asm = gson.fromJson(params, AliSmsMsg.class);
		ResultDto<AliSmsMsg> dto = iAliSmsMsgService.getAliSmsCode(asm.getPhoneNumbers(), asm.getType());
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@ResponseBody
	@RequestMapping(value = "/validateSmsCode", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String validateSmsCode(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		Gson gson = new Gson();
		AliSmsMsg asm = gson.fromJson(params, AliSmsMsg.class);
		ResultDto<String> dto = iAliSmsMsgService.validateAliSmsCode(asm.getId(), asm.getValidateCode());
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	/**
	 * 用户发送邀请码给手机号
	 * 如果对象手机号已经是会员，则提示
	 * 生成发送邀请记录，24小时有效。
	 * @author scorpio.yang
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userSendInviteCode", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String userSendInviteCode(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		logger.info("用户发送邀请码给手机号：userSendInviteCode 接收参数:" + params);
		Gson gson = new Gson();
		UserSendInviteCode sendCode = gson.fromJson(params, UserSendInviteCode.class);
		ResultDto<String> dto = null;
		try {
			User user = iUserService.getById(sendCode.getFromUserId());
			if(null == user || StringUtils.isBlank(user.getInviteCode())) {
				//如果用户获取失败，或者邀请码不存在，则需要返回错误
				dto = new ResultDto<String>();
				dto.setResult("F");
				dto.setErrorMsg("当前用户暂无邀请码。");
			}else {
				dto = iRecommendService.sendUserInviteCode(sendCode.getToPhone(), sendCode.getInviteUserName(), user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto = new ResultDto<String>();
			dto.setResult("F");
			dto.setErrorMsg("当前信息错误，请稍后重试。");
		}
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	/**
	 * 用户红包列表，根据时间排序，同一用户排序在一起
	 * 用于APP端显示用户的红包列表，提供红包领取功能
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/userAwardList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String userAwardList(@RequestBody String params) {
		ResultDto<List<UserAwardListDTO>> dto = new ResultDto<List<UserAwardListDTO>>();
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		logger.info("获取用户红包列表userAwardList接收参数:" + params);
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		Integer userId = jsonObj.getInteger("userId");
		int currentPage = jsonObj.getInteger("page");
		int rows = jsonObj.getInteger("rows");
		Page page = new Page();
		page.setPage(currentPage);
		page.setRows(rows);
		try {
			page = this.iUserAwardService.getUserAwardByUserId(page, userId);
			if (page != null) {
				dto.setData(this.iUserAwardService.convertListToDTO(page.getResultList()));
				dto.setResult("S");
			}
		} catch (Exception e) {
//			dto.setResult("F");
//			dto.setErrorMsg("查询异常，请稍后再试");
			//临时方案，返回空列表
			dto.setData(new ArrayList<UserAwardListDTO>());
			dto.setResult("S");
			e.printStackTrace();
		}
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	/**
	 * 用户红包领取
	 * 用于APP端用户领取红包操作
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userAwardTake", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String userAwardTake(@RequestBody String params) {
		ResultDto<String> dto = new ResultDto<String>();
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		logger.info("获取用户领取红包userAwardTake接收参数:" + params);
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		Integer userAwardId = jsonObj.getInteger("userAwardId");
		try {
			dto = this.iUserAwardService.useAward(userAwardId);
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("红包异常，领取失败。");
		}
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	@ResponseBody
	@RequestMapping(value = "/userRecommend", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String userRecommend(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		logger.info("获取用户推荐列表getUserRecommomendList接收参数:" + params);
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		Integer userId = jsonObj.getInteger("userId");
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		Integer userId = jsonObj.getInteger("userId");
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		ResultDto<List<Recruitment>> dto = new ResultDto<List<Recruitment>>();
		JSONObject jsonObj = JSONUtil.toJSONObject(params);
		int currentPage = jsonObj.getInteger("page");
		int rows = jsonObj.getInteger("rows");
		RecruitmentRequestForm form = new RecruitmentRequestForm();
		if(jsonObj.containsKey("publishType")&&StringUtils.isNotBlank(jsonObj.getString("publishType"))){
			Integer publishType = jsonObj.getInteger("publishType");
			if(publishType!=0){
				form.setPublishType(publishType);
			}
		}
		String companyName = jsonObj.getString("companyName");
		Page page = new Page();
		page.setPage(currentPage);
		page.setRows(rows);
		//TODO use
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		String result = JSON.toJSONString(dto);
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer id = obj.getInteger("id");
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		/**
		 * 需要修改APP启动时的参数列表。
		 * 修改返回对象类型
		 * @author scorpio.yang
		 */
		ResultDto<AppConfigDTO> dto = new ResultDto<AppConfigDTO>();
		try {
			List<FeeConfig> temp = iFeeConfigService.queryAll();
			AppConfigDTO configDTO = new AppConfigDTO();
			configDTO.setFeeConfigList(temp);
			//增加红包配置的信息
			List<ParamConfig> pcList = this.iParamConfigService.queryAll();
			configDTO.setParamConfigList(pcList);
			dto.setData(configDTO);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("查询信息异常，请稍后再试");
			dto.setResult("F");
		}
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		ResultDto<List<MessageConfig>> dto = new ResultDto<List<MessageConfig>>();
		JSONObject jsonObj = JSON.parseObject(params);
		Integer rows = jsonObj.getInteger("rows");
		Integer curPage = jsonObj.getInteger("page");
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSON.toJSONString(dto);
	}
	
	/**
	 * 获取用户钱包信息
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserWallet", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getUserWallet(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject obj = JSON.parseObject(params);
		Integer userId = obj.getInteger("userId");
		UserWallet uw = iUserWalletService.getUserWalletByUserId(userId);
		ResultDto<UserWallet> dto = new ResultDto<UserWallet>();
		if(uw==null){
			dto.setErrorMsg("用户钱包信息获取失败");
			dto.setResult("F");
		}else{
			dto.setData(uw);
			dto.setResult("S");
		}
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSON.toJSONString(dto);
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject obj = JSON.parseObject(params);
		Integer userId = obj.getInteger("userId");
		Integer walletId = obj.getInteger("walletId");
		ResultDto<String> dto = iApplyExpenditureService.applyExpenditure(userId, walletId);
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSON.toJSONString(dto);
	}

	/**
	 * 查询所有Banner信息
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
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
		String result = JSON.toJSONString(dto);
		return result;
	}

	/*
	 * 更新用户信息
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String updateUserInfo(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}

	/*
	 * 获取用户信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getUserInfo(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		logger.info("获取用户信息接口getUserInfo接收参数：" + params);
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer id = obj.getInteger("id");
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@ResponseBody
	@RequestMapping(value = "/applyApproval", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String applyApproval(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		ResultDto<EnrollApproval> dto = null;
		logger.info("获取用户申请审核接口applyApproval接收参数：" + params);
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer type = obj.getInteger("approvaType");
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		ResultDto<List<ApplyExpenditure>> dto = new ResultDto<List<ApplyExpenditure>>();
		logger.info("获取用户提现申请接口applyExpenditureList接收参数：" + params);
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer walletId = obj.getInteger("walletId");
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
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		logger.info("获取用户期满审核通过接口expireApprovalSuccessList接收参数：" + params);
		ResultDto<List<EnrollApproval>> dto = new ResultDto<List<EnrollApproval>>();
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer userId = obj.getInteger("userId");
		try {
			dto = iEnrollApprovalService.queryByUserIdTypeAndState(userId, 2, 1);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("提现记录查询异常");
			return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
		}
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	/**
	 * 变更接口功能，由原先只支持type=1（推荐费）查询，扩展为，提供type参数，由APP决定需要查询的范围。
	 * 为支持以后的统一扩展，支持type值不传递，查询所有的功能。
	 * 当前接口，查询内容，包括收入和支出，暂未提供所有收入，和支出的分类。
	 * @author scorpio.yang
	 * @since 2018-01-15
	 * @param params
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loanBillRecordList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String loanBillRecordList(@RequestBody String params) {
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		logger.info("获取用户期满审核通过接口expireApprovalSuccessList接收参数：" + params);
		ResultDto<List<BillRecord>> dto = new ResultDto<List<BillRecord>>();
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer walletId = obj.getInteger("walletId");
		Integer typeId = obj.getInteger("type");
		BillRecordRequestForm form = new BillRecordRequestForm();
		form.setWalletId(walletId);
		form.setType(typeId);
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
			dto.setErrorMsg("收支记录查询异常");
		}
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSONUtil.toJSONString(JSONUtil.toJSONObject(dto));
	}
	
	@ResponseBody
	@RequestMapping(value = "/getContactInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getContactInfo() {
		ResultDto<JSONObject> dto = new ResultDto<JSONObject>();
		try {
			JSONObject obj = new JSONObject();
			String weiXin = (String) PropertiesUtil.getProperty("weiXin");
			String telephone = (String) PropertiesUtil.getProperty("telephone");
			String qq = (String) PropertiesUtil.getProperty("qq");
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
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		logger.info("获取用户期满审核通过接口expireApprovalSuccessList接收参数：" + params);
		ResultDto<JSONObject> dto = new ResultDto<JSONObject>();
		JSONObject obj = JSONUtil.toJSONObject(params);
		Integer userId = obj.getInteger("userId");
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
			if(flag){
				return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
			}
			return JSON.toJSONString(dto);
		}catch (Exception e){
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("获取用户状态时异常");
			if(flag){
				return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
			}
			return JSON.toJSONString(dto);
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
		return JSON.toJSONString(dto);
	}
	
	/**
	 * 获取用户图片
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUserPictures",produces = "text/html;charset=UTF-8")
	public String getUserPictures(@RequestBody String params){
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject obj = JSON.parseObject(params);
		Integer userId = obj.getInteger("userId");
		ResultDto<List<Picture>> dto = null;
		dto = iPictureService.queryUserPictures(userId);
		if(flag){
			return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
		}
		return JSON.toJSONString(dto);
	}

	@ResponseBody
	@RequestMapping(value = "/getEnrollmentExtraInfo",produces = "text/html;charset=UTF-8")
	public String getEnrollmentextraInfo(@RequestBody String params){
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject obj = JSON.parseObject(params);
		Integer emId = obj.getInteger("enrollmentId");
		ResultDto<List<EnrollmentExtra>> dto = new ResultDto<List<EnrollmentExtra>>();
		try {
			List<EnrollmentExtra> list = iEnrollmentExtraMapper.queryListByEnrollmentId(emId);
			dto.setData(list);
			dto.setResult("S");
			if(flag){
				return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
			}
			return JSON.toJSONString(dto);
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("报名扩展信息查询异常");
			dto.setResult("F");
			if(flag){
				return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
			}
			return JSON.toJSONString(dto);
		}
	}
	
	@RequestMapping(value="/toSharePage")
	public String toSharePage(Model model){
		String downloadUrl = (String)PropertiesUtil.getProperty("downloadUrl");
		String androidVersion = (String)PropertiesUtil.getProperty("androidVersion");
		String iosUrl = (String)PropertiesUtil.getProperty("iosUrl");
		model.addAttribute("downloadUrl",downloadUrl);
		model.addAttribute("androidVersion",androidVersion);
		model.addAttribute("iosUrl",iosUrl);
		return "/common/share";
	}

	@ResponseBody
	@RequestMapping(value = "/appVersion",produces = "text/html;charset=UTF-8")
	public String appVersion(){
		String iosVersion = (String)PropertiesUtil.getProperty("iosVersion");
		String androidVersion = (String)PropertiesUtil.getProperty("androidVersion");
		String iosUrl = (String)PropertiesUtil.getProperty("iosUrl");
		String androidUrl = (String)PropertiesUtil.getProperty("androidUrl");
		String iosUpdateLog = (String)PropertiesUtil.getProperty("iosUpdateLog");
		String androidUpdateLog = (String)PropertiesUtil.getProperty("androidUpdateLog");
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
		return JSON.toJSONString(dto);
	}

	@RequestMapping(value="/download")
	public String downloadApp(Model model){
		String downloadUrl = (String)PropertiesUtil.getProperty("downloadUrl");
		String androidVersion = (String)PropertiesUtil.getProperty("androidVersion");
		String iosUrl = (String)PropertiesUtil.getProperty("iosUrl");
		model.addAttribute("downloadUrl",downloadUrl);
		model.addAttribute("androidVersion",androidVersion);
		model.addAttribute("iosUrl",iosUrl);
		return "/common/download";
	}
	
	@RequestMapping(value="/toPrizeDraw")
	public String toPrizeDraw(Model model, String params){
		JSONObject obj = JSON.parseObject(params);
		Integer userId = obj.getInteger("userId");
		ResultDto<UserReward> dto = iUserRewardService.getUserRewardByUserIdAndType(userId, 2);
		model.addAttribute("userReward", dto.getData());
		model.addAttribute("result", dto.getResult());
		return "/common/prizeDraw";
	}

	@ResponseBody
	@RequestMapping(value = "/queryRecruitmentListByType",produces = "text/html;charset=UTF-8")
	public String queryRecruitmentListByType(@RequestBody String params){
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		ResultDto<List<Recruitment>> dto = new ResultDto<List<Recruitment>>();
		try {
			List<Recruitment> list = iRecruitmentService.queryListByType(1);
			dto.setData(list);
			dto.setResult("S");
			if(flag){
				return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
			}
			return JSON.toJSONString(dto);
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("查询发布单异常");
			if(flag){
				return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
			}
			return JSON.toJSONString(dto);
		}
	}

	@ResponseBody
	@RequestMapping(value="/saveGroupUser",produces ="text/html;charset=UTF-8" )
	public String saveGroupUser(@RequestBody String params){
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		ResultDto<String> dto = null;
		try {
			JSONObject object = JSON.parseObject(params);
			GroupUser leader = (GroupUser) JSONObject.parseObject(object.getString("leader"),GroupUser.class);
			dto = iGroupUserService.batchInsert(null, leader);
			if(flag){
				return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
			}
			return JSON.toJSONString(dto);
		} catch (Exception e) {
			e.printStackTrace();
			if(dto==null){
				dto = new ResultDto<>();
				dto.setErrorMsg("保存信息异常");
				dto.setResult("F");
			}
			if(flag){
				return DataUtil.encryptData(JSONUtil.toJSONString(JSONUtil.toJSONObject(dto)));
			}
			return JSON.toJSONString(dto);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/testDecrypt",produces ="text/html;charset=UTF-8")
	public String testDecrypt(@RequestBody String params){
		if(StringUtils.isNotBlank(params)&&params.contains("encryptData")){
			return "加密参数："+DataUtil.decryptData(params);
		}else{
			return "未加密参数："+params;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/testEncrypt",produces ="text/html;charset=UTF-8")
	public String testEncrypt(@RequestBody String params){
		if(StringUtils.isNotBlank(params)){
			return DataUtil.encryptData(params);
		}else{
			return "加密参数为空";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/testServerEncrypt",produces ="text/html;charset=UTF-8")
	public String testServerEncrypt(@RequestBody String params){
		if(StringUtils.isNotBlank(params)){
			return "加密参数："+DataUtil.testServerEncrypt(params);
		}else{
			return "加密参数为空";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/testAppDecrypt",produces ="text/html;charset=UTF-8")
	public String testAppDecrypt(@RequestBody String params){
		if(StringUtils.isNotBlank(params)){
			return "加密参数："+DataUtil.testAppDecrypt(params);
		}else{
			return "加密参数为空";
		}
	}
	
	@RequestMapping(value="/getIp")
	public static String getIp2(HttpServletRequest request) {
		String ip = "";
		String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        System.out.println("获取的IP地址："+ip);
        System.out.println("获取的IP地址："+request.getRemoteAddr());
        return request.getRemoteAddr();
    }

    @RequestMapping(value="/toAppDownloadPage")
	public String toAppDownloadPage(Model model){
        String downloadUrl = (String)PropertiesUtil.getProperty("downloadUrl");
        String androidVersion = (String)PropertiesUtil.getProperty("androidVersion");
        String iosUrl = (String)PropertiesUtil.getProperty("iosUrl");
        model.addAttribute("downloadUrl",downloadUrl);
        model.addAttribute("androidVersion",androidVersion);
        model.addAttribute("iosUrl",iosUrl);
		return "/common/appDownload";
	}

	/**
	 * 查询转盘抽奖奖品配置信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAwardConfig",produces ="text/html;charset=UTF-8")
	public String getAwardConfig(){
		ResultDto<List<AwardConfig>> dto = new ResultDto<>();
		try {
			List<AwardConfig> list = iAwardConfigService.queryAllAwardConfig();
			dto.setResult("S");
			dto.setData(list);
			return JSON.toJSONString(dto);
		} catch (Exception e) {
			logger.error("查询奖品配置信息异常");
			dto.setResult("F");
			dto.setErrorMsg("查询奖品配置信息异常");
			e.printStackTrace();
		}
		return JSON.toJSONString(dto);
	}

	/**
	 * 转盘抽奖接口
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/lotteryDraw",produces ="text/html;charset=UTF-8")
	public String lotteryDraw(@RequestBody String params){
		ResultDto<UserAwardExchange> dto = null;
		String deadtimeStr = (String)PropertiesUtil.getProperty("lotteryDeadtime");
		long nowTime = new Date().getTime();
		long deadtime = DateUtil.parseDateByStr(deadtimeStr,DateUtil.FULL_PATTERN).getTime();
		if(deadtime-nowTime<0){
			dto = new ResultDto<>();
			dto.setErrorMsg("抽奖活动已结束");
			dto.setResult("F");
			return JSON.toJSONString(dto);
		}
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject jsonObject = JSON.parseObject(params);
		Integer userId = jsonObject.getInteger("userId");
		String userName = jsonObject.getString("userName");
		String userMobile = jsonObject.getString("userMobile");
		String enrollTimeStr = jsonObject.getString("enrollTime");
		Date enrollTime = DateUtil.parseDateByStr(enrollTimeStr,DateUtil.FULL_PATTERN);
		UserAwardExchange uae = new UserAwardExchange();
		uae.setEnrollTime(enrollTime);
		uae.setUserId(userId);
		uae.setUserMobile(userMobile);
		uae.setUserName(userName);
		try {
			dto = iUserAwardExchangeService.saveUserAwardExchange(uae);
			return JSON.toJSONString(dto);
		} catch (Exception e) {
			logger.error("抽奖时异常");
			dto = new ResultDto<>();
			dto.setResult("F");
			dto.setErrorMsg("抽奖时异常");
			e.printStackTrace();
		}
		return JSON.toJSONString(dto);
	}
	
	@ResponseBody
	@RequestMapping(value="/getPromotionConfig",produces ="text/html;charset=UTF-8")
	public String getPromotionConfig(@RequestBody String params){
		PromotionConfigRequestForm form = new PromotionConfigRequestForm();
		List<PromotionConfig> list;
		ResultDto<List<PromotionConfig>> dto = new ResultDto<>();
		try {
			list = iPromotionConfigService.queryPromotionConfig(form);
			dto.setData(list);
			dto.setResult("S");
		} catch (Exception e) {
			logger.error("查询活动发布信息异常");
			dto.setResult("F");
			dto.setErrorMsg("查询活动发布信息异常");
			e.printStackTrace();
		}
		
		return JSON.toJSONString(dto);
	}
	

	@ResponseBody
	@RequestMapping(value = "/getUserAwardExchange",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
	public String getUserAwardExchange(@RequestBody String params){
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject obj = JSON.parseObject(params);
		Integer userId = obj.getInteger("userId");
		ResultDto<List<UserAwardExchange>> dto = new ResultDto<>();
		int pageSize = obj.getInteger("pageSize");
		int pageNum = obj.getInteger("pageNum");
		Page page = new Page();
		page.setPage(pageNum);
		page.setRows(pageSize);
		UserAwardExchangeRequestForm form = new UserAwardExchangeRequestForm();
		form.setUserId(userId);
		try {
			page = iUserAwardExchangeService.loadUserAwardExchangeList(page,form);
			List<UserAwardExchange> list = iUserAwardExchangeService.queryUserAwardExchangeByUserId(userId);
			dto.setResult("S");
			dto.setData(list);
		} catch (Exception e) {
			dto.setResult("F");
			dto.setErrorMsg("查询用户抽奖奖品信息异常");
			logger.error("查询用户抽奖奖品信息异常");
			e.printStackTrace();
		}
		return DataUtil.encryptData(JSON.toJSONString(dto));
	}

	@ResponseBody
	@RequestMapping(value="/getAllUserLottery",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
	public String getLotteryUser(@RequestBody String params){
		ResultDto<List<UserAwardExchange>> dto = new ResultDto<>();
		try {
			List<UserAwardExchange> list = iUserAwardExchangeService.queryAllUserLottery();
			dto.setData(list);
			dto.setResult("S");
		} catch (Exception e) {
			logger.error("查询中奖用信息异常");
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("查询中奖用信息异常");
		}
		return JSON.toJSONString(dto);
	}

	@ResponseBody
	@RequestMapping(value = "/userExchange",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
	public String userExchange(@RequestBody String params){
		boolean flag = false;
		if(StringUtils.isNotBlank(params)){
			if(params.contains("encryptData")){
				flag = true;
			}
		}
		if(flag){
			params = DataUtil.decryptData(params);
		}
		JSONObject obj = JSON.parseObject(params);
		int exId = obj.getInteger("exId");
		ResultDto<String> dto = null;
		try {
			dto = iUserAwardExchangeService.updateExchangeStatusById(exId);
		} catch (Exception e) {
			dto = new ResultDto<>();
			dto.setResult("F");
			dto.setErrorMsg("保存用户兑奖信息时异常");
			e.printStackTrace();
		}
		return DataUtil.encryptData(JSON.toJSONString(dto));
	}
	
	@ResponseBody
	@RequestMapping(value="/test",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
	public String test(HttpSession session,@RequestBody String param){
		ResultDto<User> dto = null;
		String weixin = JSONObject.parseObject(param).getString("weixin");
		try {
			dto = iUserService.queryUserByWeixin(weixin);
		} catch (Exception e) {
			e.printStackTrace();
			dto = new ResultDto<User>();
			dto.setResult("F");
			dto.setErrorMsg("查询异常");
		}
		return JSON.toJSONString(dto);
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
//		JSONObject object = JSON.parseObject(jsonStr);
		JSONArray array = JSONArray.parseArray(jsonStr);
		System.out.println();
//		List list1 = (List)JSONArray.toCollection(array, User.class);
//		System.out.println(((User)list1.get(0)).getMobile());
	}


}
