package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webapps.common.entity.Agency;
import com.webapps.common.form.AgencyRequestForm;

@Repository
public interface IAgencyMapper extends IBaseMapper<Agency>,IPageMapper<Agency,AgencyRequestForm>{

	List<Agency> queryAllBy(@Param("obj")AgencyRequestForm agency)throws Exception;
	
}
