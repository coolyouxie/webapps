package com.webapps.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.entity.UserWallet;
import com.webapps.mapper.IUserWalletMapper;
import com.webapps.service.IUserWalletService;

@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class})
public class UserWalletServiceImpl implements IUserWalletService {
	
	private static Logger logger = Logger.getLogger(UserWalletServiceImpl.class);
	
	
	@Autowired
	private IUserWalletMapper iUserWalletMapper;
	

	@Override
	public UserWallet getUserWalletByUserId(Integer userId) {
		UserWallet uw = iUserWalletMapper.queryByUserId(userId);
		return uw;
	}
	
}
