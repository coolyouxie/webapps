package com.webapps.mapper;

import com.webapps.common.entity.UserWallet;
import com.webapps.common.form.UserWalletRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IUserWalletMapper extends IBaseMapper<UserWallet>,IPageMapper<UserWallet,UserWalletRequestForm>{
	
	
	public UserWallet queryByUserId(Integer userId);
	
}
