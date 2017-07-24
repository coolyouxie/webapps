package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;
import com.webapps.common.utils.PasswordEncryptUtil;
import com.webapps.mapper.IUserMapper;
import com.webapps.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserMapper iUserMapper;

	@Override
	public Page loadUserList(Page page,UserRequestForm user) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iUserMapper.queryCount(user);
		List<User> list = iUserMapper.queryUserList(startRow, endRow, user);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public User login(User user) throws Exception {
		String account = user.getAccount();
		User user1 = iUserMapper.queryUserByAccount(account);
		String tempPwd = user.getPassword();
		String salt = user1.getToken();
		if(user1!=null){
			boolean flag = PasswordEncryptUtil.authenticate(tempPwd, user1.getPassword(),salt);
			if(flag){
				return user1;
			}
		}
		return null;
	}

	@Override
	public User insert(User user) throws Exception {
		User user1 = iUserMapper.insert( user);
		return user1;
	}

	@Override
	public User saveUser(User user) throws Exception {
		if(user.getId()!=null){
			iUserMapper.updateById(user.getId(), user);
		}else{
			String password = user.getPassword();
			String token = PasswordEncryptUtil.generateSalt();
			user.setToken(token);
			String encryptPwd = PasswordEncryptUtil.getEncryptedPassword(password, token);
			user.setPassword(encryptPwd);
			iUserMapper.insert(user);
		}
		return user;
	}

	@Override
	public User getById(Integer id) throws Exception {
		User user = iUserMapper.getById(id);
		return user;
	}
	
	
	
}
