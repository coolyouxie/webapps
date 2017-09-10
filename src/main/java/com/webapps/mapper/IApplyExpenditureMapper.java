package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.form.ApplyExpenditureRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IApplyExpenditureMapper extends IBaseMapper<ApplyExpenditure>,IPageMapper<ApplyExpenditure,ApplyExpenditureRequestForm>{
	
	List<ApplyExpenditure> queryByWalletId(@Param("walletId")Integer walletId);
	
}