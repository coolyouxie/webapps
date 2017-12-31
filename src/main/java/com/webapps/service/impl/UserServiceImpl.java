package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.webapps.common.entity.UserPermission;
import com.webapps.mapper.*;
import com.webapps.service.IPermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.User;
import com.webapps.common.entity.UserWallet;
import com.webapps.common.form.UserRequestForm;
import com.webapps.common.utils.PasswordEncryptUtil;
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
				user1.setWeiXin(user.getWeiXin());
				user1.setBankCardNum(user.getBankCardNum());
				iUserMapper.updateById(user1.getId(), user1);
				dto.setData(user1);
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
					//会员注册成功后为会员新增钱包信息
					UserWallet obj = new UserWallet();
					obj.setCreateTime(new Date());
					obj.setDataState(1);
					obj.setFee(new BigDecimal(0));
					obj.setState(0);
					obj.setUserId(user.getId());
					iUserWalletMapper.insert(obj );
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

}
