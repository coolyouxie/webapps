package com.webapps.mapper;

import com.webapps.common.entity.Agency;
import com.webapps.common.form.AgencyRequestForm;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IAgencyMapper extends IBaseMapper<Agency>,IPageMapper<Agency,AgencyRequestForm>{
	
}
