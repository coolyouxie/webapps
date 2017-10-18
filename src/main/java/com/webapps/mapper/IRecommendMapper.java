package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.Recommend;
import com.webapps.common.form.RecommendRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IRecommendMapper extends IBaseMapper<Recommend>,IPageMapper<Recommend,RecommendRequestForm>{
	
	List<Recommend> queryByUserId(@Param("userId")Integer userId)throws Exception;
	
	List<Recommend> queryByMobile(@Param("mobile")String mobile)throws Exception;
	
}
