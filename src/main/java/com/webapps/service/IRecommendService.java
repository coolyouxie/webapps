package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Recommend;
import com.webapps.common.form.RecommendRequestForm;

public interface IRecommendService {
	
	Page loadRecommendList(Page page, RecommendRequestForm recommend) throws Exception;
	
	Recommend getById(Integer id) throws Exception;
	
	ResultDto<Recommend> saveRecommend(Recommend recommend) throws Exception;
	
	ResultDto<Recommend> deleteRecommendById(Integer id) throws Exception;
	
	List<Recommend> queryRecommendListByUserId(Integer userId)throws Exception;
	
	ResultDto<Recommend> userRecommend(Recommend recommend);

}
