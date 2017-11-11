package com.webapps.service;

import com.webapps.common.entity.UserWallet;

public interface IUserWalletService {
	
	UserWallet getUserWalletByUserId(Integer userId);
	

}
