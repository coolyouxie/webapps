package com.webapps.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.ParamConfig;
import com.webapps.common.entity.Recommend;
import com.webapps.common.entity.User;
import com.webapps.common.form.RecommendRequestForm;
import com.webapps.common.form.UserRequestForm;
import com.webapps.common.utils.DateUtil;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.mapper.IRecommendMapper;
import com.webapps.mapper.IUserMapper;
import com.webapps.service.IAliSmsMsgService;
import com.webapps.service.IParamConfigService;
import com.webapps.service.IRecommendService;
import com.webapps.service.IUserAwardService;
import com.webapps.service.impl.ParamConfigService.ParamConfigType;

@Service
@Transactional
public class RecommendServiceImpl implements IRecommendService {
	
	private static Logger logger = Logger.getLogger(RecommendServiceImpl.class);
	
	private final int timeOverHour = -1;
	
	@Autowired private IRecommendMapper iRecommendMapper;
	
	@Autowired private IUserMapper iUserMapper;
	
	@Autowired private IAliSmsMsgService iAliSmsMsgService;
	
	@Autowired private IUserAwardService iUserAwardService;
	
	@Autowired private IParamConfigService iParamConfigService;

	@Override
	public Page loadRecommendList(Page page, RecommendRequestForm recommend) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iRecommendMapper.queryCount(recommend);
		List<Recommend> list = iRecommendMapper.queryPage(startRow, rows, recommend);
		page.setResultList(list);
		page.setRecords(count);
		page.countRecords(count);
		return page;
	}

	@Override
	public Recommend getById(Integer id) throws Exception {
		Recommend recommend = iRecommendMapper.getById(id);
		return recommend;
	}

	@Override
	public ResultDto<Recommend> saveRecommend(Recommend recommend) throws Exception {
		int result = 0;
		ResultDto<Recommend> dto = new ResultDto<Recommend>();
		if(recommend.getId()==null){
			recommend.setDataState(1);
			recommend.setState(1);
			result = iRecommendMapper.insert(recommend);
			if(result!=1){
				dto.setData(recommend);
				dto.setResult("F");
				dto.setErrorMsg("新增失败");
				return dto;
			}else{
				dto.setData(recommend);
				dto.setResult("S");
				return dto;
			}
		}else{
			result = iRecommendMapper.updateById(recommend.getId(), recommend);
			if(result!=1){
				dto.setData(recommend);
				dto.setResult("F");
				dto.setErrorMsg("更新失败");
				return dto;
			}else{
				dto.setData(recommend);
				dto.setResult("S");
				return dto;
			}
		}
	}
	
	/**
	 * 注册用户填写邀请码，进行数据入库
	 * 如果注册码没有对应的用户，则返回失败提示
	 * 如果短信发送过邀请码，则更新记录。
	 * 如果未发送短信，则新增邀请记录。
	 * 增加逻辑 2018-01-19 scorpio.yang
	 * 用户用邀请码注册后，发放红包到邀请人账户
	 * @author scorpio.yang
	 * @since 2018-01-15
	 * @param registUser
	 * @param inviteCode
	 * @return
	 * @throws Exception 
	 */
	public ResultDto<String> saveInviteRecommend(User registUser , String inviteCode, String inviteUserName) throws Exception{
		ResultDto<String> dto = new ResultDto<String>();
		//获取注册码是否正确，找到对应的用户
		User user = iUserMapper.queryByInviteCode(inviteCode);
		if(null != user) {
			//查找，是否已经有邀请记录
			Recommend recommend = iRecommendMapper.getByUserIdAndCodeAndPhone(user.getId(), inviteCode, registUser.getMobile());
			if(null != recommend) {
				inviteUserName = recommend.getName();
				recommend.setState(2);
				recommend.setUpdateTime(new Date());
				this.iRecommendMapper.updateById(recommend.getId(), recommend);
			}else {
				recommend = this.createNewRecommendByUser(user, registUser.getMobile(), inviteUserName);
				recommend.setState(2);
				this.iRecommendMapper.insert(recommend);
			}
			/**
			 * 增加逻辑 2018-01-19 scorpio.yang
			 * 用户用邀请码注册后，发放红包到邀请人账户
			 */
			ParamConfig pc = iParamConfigService.getParamConfigByAwardType(ParamConfigType.注册红包);
			pc.setId(1);
			iUserAwardService.addNewAward(registUser, pc);
			dto.setResult("S");
			dto.setErrorMsg("接受邀请操作成功。");
			dto.setData(inviteUserName);
		}else {
			dto.setResult("F");
			dto.setErrorMsg("邀请码填写错误，请检查！");
		}
		return dto;
	}

	@Override
	public ResultDto<Recommend> deleteRecommendById(Integer id) throws Exception {
		ResultDto<Recommend> dto = new ResultDto<Recommend>();
		int result = iRecommendMapper.deleteByIdInLogic(id);
		if(result!=1){
			dto.setResult("F");
			dto.setErrorMsg("删除失败");
		}else{
			dto.setResult("S");
		}
		return null;
	}

	@Override
	public List<Recommend> queryRecommendListByUserId(Integer userId) throws Exception {
		List<Recommend> list = iRecommendMapper.queryByUserId(userId);
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class})
	public ResultDto<Recommend> userRecommend(Recommend recommend) {
		RecommendRequestForm form = new RecommendRequestForm();
		ResultDto<Recommend> dto = new ResultDto<Recommend>();
		form.setMobile(recommend.getMobile());
		List<Recommend> list = iRecommendMapper.queryPage(0,1,form);
		Recommend r = null;
		try {
			//先根据传入的被推荐人手机号查询用户表，如果能查到数据说明该用户已注册
			User user = iUserMapper.queryUserByAccount(recommend.getMobile());
			if(user!=null&&user.getId()!=null){
				dto.setResult("F");
				dto.setErrorMsg("该用户已注册会员，不能被推荐");
				return dto;
			}
			if(CollectionUtils.isNotEmpty(list)){
				r = list.get(0);
			}else{
				dto = saveRecommend(recommend);
				return dto;
			}
			//如果被推荐人之前被推荐过，则要判断之前最近一条推荐记录是否在有效期内，目前为20天
			Date now = new Date();
			double days = DateUtil.getDaysBetweenTwoDates(r.getCreateTime(), now);
			Integer recommendOverDays = Integer.valueOf((String)PropertiesUtil.getProperty("recommend_over_days"));
			if(days>recommendOverDays){
				recommend.setDataState(1);
				recommend.setCreateTime(new Date());
				recommend.setState(1);
				r.setDataState(0);
				r.setState(3);
				r.setUpdateTime(new Date());
				iRecommendMapper.updateById(r.getId(),r);
				iRecommendMapper.insert(recommend);
				dto.setResult("S");
				return dto;
			}else{
				dto = new ResultDto<Recommend>();
				dto.setResult("F");
				dto.setErrorMsg("该用户还在有效推荐期内，不可重复推荐");
				return dto;
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error(e.getMessage());
			dto = new ResultDto<Recommend>();
			dto.setResult("F");
			dto.setErrorMsg("推荐时异常，请稍后再试");
			return dto;
		}
	}
	
	/**
	 * 发送用户邀请码到指定的手机号码
	 * 如果对象手机号已经是会员，则提示
	 * 生成发送邀请记录，24小时有效。
	 * @author scorpio.yang
	 * @since 2018-01-15
	 * 
	 * 增加被邀请人姓名，用户红包列表用户名称显示
	 * @author scorpio.yang
	 * @since 2018-01-20
	 * 
	 * @param phoneNum
	 * @param inviteCode
	 * @return
	 * @throws Exception 
	 */
	public ResultDto<String> sendUserInviteCode(String phoneNum, String inviteUserName, User fromUser) throws Exception{
		ResultDto<String> dto = new ResultDto<String>();
		//判断对象手机号是否已经是会员
		UserRequestForm form = new UserRequestForm();
		form.setMobile(phoneNum);
		int num = iUserMapper.queryCount(form);
		if(num == 0) {
			//判断是否已经有邀请记录，
			Recommend recommend = this.iRecommendMapper.getByUserIdAndCodeAndPhone(fromUser.getId(), fromUser.getInviteCode(), phoneNum);
			if(null != recommend) {
				//如果未超过24小时，则提示已经发送，并告知截止使用时间。
				if(this.checkTimeValid(recommend.getUpdateTime())) {
					dto.setResult("S");
					dto.setErrorMsg("被邀请的手机号已经发送短信，使用截止时间为：" + this.deadline(recommend.getUpdateTime()));
				}else {
					//是否已经超过24小时，超过则重新发送短信，并更新记录
					//发送短信
					ResultDto<String> res = iAliSmsMsgService.sendInviteCode(phoneNum, fromUser.getInviteCode());
					if(res.getResult().equals("F")){
						logger.error("短信发送失败，请稍后重试");
						return res;
					}
					//记录信息
					recommend.setUpdateTime(new Date());
					recommend.setName(inviteUserName);
					this.saveRecommend(recommend);
					dto.setResult("S");
					dto.setErrorMsg("被邀请的手机号发送邀请短信成功");
				}
			}else {
				//发送短信
				ResultDto<String> res = iAliSmsMsgService.sendInviteCode(phoneNum, fromUser.getInviteCode());
				if(res.getResult().equals("F")){
					logger.error("短信发送失败，请稍后重试");
					return res;
				}
				//记录信息
				this.saveRecommend(this.createNewRecommendByUser(fromUser, phoneNum, inviteUserName));
				dto.setResult("S");
				dto.setErrorMsg("被邀请的手机号发送邀请短信成功");
			}
		}else {
			//电话已经是会员，则返回信息
			dto.setResult("F");
			dto.setErrorMsg("被邀请的手机号已经是会员。");
		}
		return dto;
	}
	
	/**
	 * 根据手机号码获取对象。
	 * 用于被邀请人，获取邀请人信息使用
	 * 被邀请人可能有多条被邀请记录，所以只获取state=2（已注册）的，记录
	 * @param mobile
	 * @return
	 * @throws Exception 
	 */
	public List<Recommend> getByMobile(String mobile) throws Exception {
		return this.iRecommendMapper.queryByMobile(mobile);
	}
	
	private Recommend createNewRecommendByUser(User user, String phone, String inviteUserName) {
		Recommend recommend = new Recommend();
		recommend.setUser(user);
		if(StringUtils.isNoneBlank(inviteUserName)) {
			recommend.setName(inviteUserName);
		}else {
			recommend.setName("");
		}
		recommend.setMobile(phone);
		recommend.setInviteCode(user.getInviteCode());
		recommend.setState(1);
		recommend.setGender(0);
		recommend.setAge(0);
		recommend.setCreateTime(new Date());
		recommend.setUpdateTime(new Date());
		return recommend;
	}
	
	/**
	 * 检查是否超过有效期，本类final参数，timeOverHour
	 * true，有效
	 * false，无效
	 * @return
	 */
	private boolean checkTimeValid(Date time) {
		if(timeOverHour >= 0) {
			Calendar now = Calendar.getInstance();
			Calendar codeTime = Calendar.getInstance();
			codeTime.setTime(time);
			codeTime.add(Calendar.HOUR_OF_DAY,timeOverHour);
			if(codeTime.compareTo(now) > 0) {
				return true;
			}else {
				return false;
			}
		}else {
			return true;
		}
	}
	
	/**
	 * 获取当前邀请码的最后有效日期
	 * @param time
	 * @return
	 */
	private String deadline(Date time) {
		if(timeOverHour >= 0) {
			Calendar codeTime = Calendar.getInstance();
			codeTime.setTime(time);
			codeTime.add(Calendar.HOUR_OF_DAY,timeOverHour);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
			return sdf.format(codeTime.getTime());
		}else {
			return "长期有效";
		}
	}

}
