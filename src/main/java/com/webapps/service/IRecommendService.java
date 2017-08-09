package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Recommend;
import com.webapps.common.form.RecommendRequestForm;

public interface IRecommendService {
	
	public Page loadRecommendList(Page page,RecommendRequestForm recommend) throws Exception;
	
	public Recommend getById(Integer id) throws Exception;
	
	public ResultDto<Recommend> saveRecommend(Recommend recommend) throws Exception;
	
	public ResultDto<Recommend> deleteRecommendById(Integer id) throws Exception;

}
