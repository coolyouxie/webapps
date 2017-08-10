package com.webapps.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.BillRecord;
import com.webapps.common.form.BillRecordRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IBillRecordMapper extends IBaseMapper<BillRecord>,IPageMapper<BillRecord,BillRecordRequestForm>{
	
	List<BillRecord> queryByUserIdAndType(@Param("type")Integer type, @Param("userId")Integer userId);
	
	List<BillRecord> queryByUserId(Integer userId);
	
	public BigDecimal sumFeeByUserIdAndType(@Param("userId")Integer userId,@Param("type")Integer type);
	
}
