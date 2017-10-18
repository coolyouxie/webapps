package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IApplyExpenditureMapper extends IBaseMapper<ApplyExpenditure>,IPageMapper<ApplyExpenditure,ApplyExpenditureRequestForm>{
	
	List<ApplyExpenditure> queryByWalletId(@Param("walletId")Integer walletId)throws Exception;
	
	List<ApplyExpenditure> queryByWalletIdAndState(@Param("walletId")Integer walletId,@Param("state")Integer state)throws Exception;
	
}
