package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.BannerConfig;
import com.webapps.common.form.BannerConfigRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IBannerConfigMapper extends IBaseMapper<BannerConfig>,IPageMapper<BannerConfig,BannerConfigRequestForm>{
	
	public List<BannerConfig> getByFkIdAndType(@Param("fkId")Integer fkId,@Param("type")Integer type)throws Exception;
	
	public int batchDeleteInLogic(List<BannerConfig> list)throws Exception;
}
