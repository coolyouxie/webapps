package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Recommend;
import com.webapps.common.entity.TalkerTask;
import com.webapps.common.entity.User;
import com.webapps.common.entity.UserPermission;
import com.webapps.common.entity.UserWallet;
import com.webapps.common.form.UserRequestForm;
import com.webapps.common.utils.PasswordEncryptUtil;
import com.webapps.mapper.IPermissionMapper;
import com.webapps.mapper.IPermissionRelationMapper;
import com.webapps.mapper.ITalkerTaskMapper;
import com.webapps.mapper.IUserMapper;
import com.webapps.mapper.IUserPermissionMapper;
import com.webapps.mapper.IUserWalletMapper;
import com.webapps.service.IRecommendService;
import com.webapps.service.IUserService;

@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class})
public class UserServiceImpl implements IUserService {
	
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private IUserMapper iUserMapper;
	
	@Autowired
	private IUserWalletMapper iUserWalletMapper;

	@Autowired
	private IPermissionMapper iPermissionMapper;

	@Autowired
	private IPermissionRelationMapper iPermissionRelationMapper;

	@Autowired
	private IUserPermissionMapper iUserPermissionMapper;
	
	@Autowired private IRecommendService iRecommendService;

	@Autowired
	private ITalkerTaskMapper iTalkerTaskMapper;

	@Override
	public Page loadUserList(Page page,UserRequestForm user) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iUserMapper.queryCount(user);
		List<User> list = iUserMapper.queryPage(startRow, rows, user);
		page.setResultList(list);
		page.setRecords(count);
		page.countRecords(count);
		return page;
	}

	@Override
	public User login(User user) throws Exception {
		String account = user.getAccount();
		String tempPwd = user.getPassword();
		User user1 = iUserMapper.queryUserByAccount(account);
		if(user1==null){
			return null;
		}
		String salt = null;
		if(user1!=null){
			salt = user1.getToken();
			boolean flag = PasswordEncryptUtil.authenticate(tempPwd, user1.getPassword(),salt);
			if(flag){
				return user1;
			}
		}
		return null;
	}

	@Override
	public int insert(User user) throws Exception {
		int result = iUserMapper.insert( user);
		return result;
	}

	@Override
	public ResultDto<User> saveUser(User user) {
		ResultDto<User> dto = new ResultDto<User>();
		dto.setResult("S");
		try {
			if(user.getId()!=null){
				User user1 = iUserMapper.getById(user.getId());
				user1.setTelephone(user.getTelephone());
				user1.setName(user.getName());
				user1.setGender(user.getGender());
				user1.setAge(user.getAge());
				user1.setIdCardNo(user.getIdCardNo());
				user1.setQq(user.getQq());
				user1.setWeixin(user.getWeixin());
				user1.setBankCardNum(user.getBankCardNum());
				user1.setAwardFlag(user.getAwardFlag());
				iUserMapper.updateById(user1.getId(), user1);
				dto.setData(user1);
				/**
				 * 增加用户更新信息后，邀请列表中的被邀请人姓名，也进行同步更新。
				 * @author scorpio.yang
				 * @since 2018-01-20
				 */
				if(StringUtils.isNotBlank(user1.getName())) {
					List<Recommend> recommendList = iRecommendService.getByMobile(user1.getMobile());
					if(null != recommendList && recommendList.size()>0) {
						Recommend recommend = recommendList.get(0);
						if(null != recommend) {
							recommend.setName(user1.getName());
							iRecommendService.saveRecommend(recommend);
						}
					}
				}
				return dto;
			}else{
				String password = user.getPassword();
				String token = PasswordEncryptUtil.generateSalt();
				user.setToken(token);
				String encryptPwd = PasswordEncryptUtil.getEncryptedPassword(password, token);
				user.setPassword(encryptPwd);
				try {
					user.setCurrentState(0);
					int result = iUserMapper.insert(user);
					if(result==0){
						dto.setData(user);
						dto.setResult("F");
						dto.setErrorMsg("新增失败");
						return dto;
					}
					//如果是招聘员，则创建招聘员待沟通数据记录
					if(user.getUserType()==4){
						TalkerTask tt = new TalkerTask();
						tt.setCreateTime(new Date());
						tt.setTalkerId(user.getId());
						tt.setTalkerName(user.getName());
						tt.setDataState(1);
						tt.setJobsCount(0);
						iTalkerTaskMapper.insert(tt);
					}
					//为用户生成邀请码
					//记录保存后，获取id信息，生成邀请码，然后重新入库
					user.setInviteCode(this.createInviteCodeCreate(user.getId()));
					//用户的红包状态标签，此处设置3位，皆是未生成状态。
					user.setAwardFlag("000");
					iUserMapper.updateById(user.getId(), user);
					
					//会员注册成功后为会员新增钱包信息
					UserWallet obj = new UserWallet();
					obj.setCreateTime(new Date());
					obj.setDataState(1);
					obj.setFee(new BigDecimal(0));
					obj.setState(0);
					obj.setUserId(user.getId());
					iUserWalletMapper.insert(obj);
				} catch (DuplicateKeyException e) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					logger.error(e.getMessage());
					dto.setData(user);
					dto.setResult("F");
					dto.setErrorMsg("该账号已被注册，请更换重试");
					return dto;
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			dto.setResult("F");
			dto.setErrorMsg("注册会员信息时异常，请稍后重试");
			return dto;
		}
		return dto;
	}
	
	@Override
	public User getById(Integer id) throws Exception {
		User user = iUserMapper.getById(id);
		return user;
	}

	@Override
	public ResultDto<User> deleteUserById(Integer id) throws Exception {
		iUserMapper.deleteById(id);
		ResultDto<User> dto = new ResultDto<User>();
		dto.setResult("S");
		return dto;
	}

	@Override
	public ResultDto<String> getSmsValidateMsg(String phoneNum) {
		return null;
	}

	@Override
	public ResultDto<String> resetPassword(String phoneNum,String password) {
		ResultDto<String> dto = new ResultDto<String>();
		User user = iUserMapper.queryUserByAccount(phoneNum);
		if(user==null){
			dto.setErrorMsg("会员信息不存在，请先注册");
			dto.setResult("F");
			return dto;
		}
		String token;
		try {
			token = PasswordEncryptUtil.generateSalt();
			user.setToken(token);
			String encryptPwd = PasswordEncryptUtil.getEncryptedPassword(password, token);
			user.setPassword(encryptPwd);
			user.setUpdateTime(new Date());
			iUserMapper.updateById(user.getId(), user);
			dto.setResult("S");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("修改密码时异常，请稍后再试");
		}
		return dto;
	}

	@Override
	public Map<String, String> loadUserPermission(Integer userId) {
		UserPermission up = new UserPermission();
		up.setUserId(userId);
		Map<String,String> permissions = new HashMap<>();
		try {
			List<UserPermission> list = iUserPermissionMapper.queryByConditions(up);
			if(CollectionUtils.isNotEmpty(list)){
				for(UserPermission temp1 : list){
					for(UserPermission temp2:list){
						if(temp1.getLevel()==2){
							permissions.put(temp1.getCode(),temp1.getCode());
						}else if(temp1.getLevel()==3){
							if(temp1.getParentPermissionId().equals(temp2.getPermissionId())){
								permissions.put(temp2.getCode()+"_"+temp1.getCode(),
										temp2.getCode()+"_"+temp1.getCode());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permissions;
	}

	/**
	 * 根据邀请码查询用户对象。
	 * @author scorpio.yang
	 * @since 2018-01-16
	 * @param inviteCode
	 * @return
	 */
	public User queryByInviteCode(String inviteCode){
		return this.iUserMapper.queryByInviteCode(inviteCode);
	}

	@Override
	public List<User> queryUserByType(int type) {
		try {
			List<User> list = iUserMapper.queryUsersByUserType(type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据用户的id，创建用户唯一的邀请码。
	 * 返回4位字符串，默认4位，如果id值大于能提供唯一代码上限，则将会自动生成多一位的邀请码。
	 * @author scorpio.yang
	 * @since 2018-01-14
	 * @param userId 用户id，用来生成的种子序号，基本上每个人都是不一样的，用于决定邀请码不一致
	 * @return
	 */
	public String createInviteCodeCreate(long userId) {
		int len = 0;
		//判断当前userId生成的code，是否长度符合
		long tmpId = userId;
		while(tmpId / 26 >1) {
			tmpId = tmpId / 26;
			len++;
		}
		//默认下限长度4位
		if(len < 4) {
			len = 4;
		}
		//生成邀请码的基数
		String[] sourceString = {"D","O","F","H","T","W","Z","M",
				"B","C","E","G","I","J","K",
				"L","A","N","P","Y","R","S","U",
				"V","X","Q"};
		int mod = 0;
		System.out.println("sourceString:" + sourceString.length);
		String code = "";
		while(userId > 0){
	        mod = (int)(userId % 26); 
	        userId = (userId - mod) / 26;
	        code += sourceString[mod];
	    }
		while(code.length()<len) {
			code += RandomUtils.nextInt(10)%10;
		}
		//不用数字0，改用1，区别字母O
		code = code.replaceAll("0", "1");
		return code;
	}

	@Override
	public void transactionTest()throws Exception {
		//经测试，这里如果捕获异常后，数据库不回滚。
//		try {
			User user = iUserMapper.getById(1);
			user.setGender(2);
			iUserMapper.updateById(1, user);
			User test = new User();
			test.setAccount("admin");
			iUserMapper.insert(test);
//		} catch (Exception e) {
//			
//		}
	}

	public ResultDto<String> resetPassword(HttpSession session, Integer id, String oldPwd,
										   String newPwd, String confirmNewPwd)throws Exception{
		ResultDto<String> dto = new ResultDto<String>();
		if(id==null){
			dto.setErrorMsg("用户信息异常");
			dto.setResult("F");
			return dto;
		}
		if(org.apache.commons.lang.StringUtils.isBlank(oldPwd)){
			dto.setErrorMsg("请输入原密码");
			dto.setResult("F");
			return dto;
		}
		if(org.apache.commons.lang.StringUtils.isBlank(newPwd)){
			dto.setErrorMsg("请输入新密码");
			dto.setResult("F");
			return dto;
		}
		if(org.apache.commons.lang.StringUtils.isBlank(confirmNewPwd)){
			dto.setErrorMsg("请输入确认密码");
			dto.setResult("F");
			return dto;
		}
		if(!newPwd.equals(confirmNewPwd)){
			dto.setErrorMsg("两次输入的新密码不一致");
			dto.setResult("F");
			return dto;
		}
		User user = iUserMapper.getById(id);
		User sessionUser = (User)session.getAttribute("user");
		if(sessionUser==null||user==null){
			dto.setErrorMsg("获取用户信息异常");
			dto.setResult("F");
			return dto;
		}
		if(!user.getId().equals(sessionUser.getId())){
			dto.setErrorMsg("重置密码权限异常");
			dto.setResult("F");
			return dto;
		}
		if(!PasswordEncryptUtil.authenticate(oldPwd,user.getPassword(),user.getToken())){
			dto.setErrorMsg("原密码错误");
			dto.setResult("F");
			return dto;
		}
		String newToken = PasswordEncryptUtil.generateSalt();
		String encrptNewPwd = PasswordEncryptUtil.getEncryptedPassword(newPwd,newToken);
		user.setToken(newToken);
		user.setPassword(encrptNewPwd);
		user.setUpdateTime(new Date());
		iUserMapper.updateById(user.getId(),user);
		dto.setResult("S");
		return dto;
	}

	@Override
	public ResultDto<User> queryUserByWeixin(String weixin) throws Exception {
		UserRequestForm form = new UserRequestForm();
		form.setWeixin(weixin);
		List<User> list = iUserMapper.queryByConditin(form);
		ResultDto<User> dto = new ResultDto<>();
		if(CollectionUtils.isNotEmpty(list)){
			dto.setResult("S");
		}else{
			dto.setErrorMsg("未找到微信账号对应会员信息");
			dto.setResult("F");
		}
		return dto;
	}
}
