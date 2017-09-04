package com.webapps.mapper;

import com.webapps.common.entity.UserWallet;
import com.webapps.common.entity.WalletRecord;
import com.webapps.common.form.WalletRecordRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IWalletRecordMapper extends IBaseMapper<WalletRecord>,IPageMapper<WalletRecord,WalletRecordRequestForm>{
	
	
	public UserWallet queryByWalletId(Integer walletId);
	
}
