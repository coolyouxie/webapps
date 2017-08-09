package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Recommend;
import com.webapps.common.form.RecommendRequestForm;
import com.webapps.mapper.IRecommendMapper;
import com.webapps.service.IRecommendService;

@Service
@Transactional
public class RecommendServiceImpl implements IRecommendService {
	
	@Autowired
	private IRecommendMapper iRecommendMapper;

	@Override
	public Page loadRecommendList(Page page, RecommendRequestForm recommend) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iRecommendMapper.queryCount(recommend);
		List<Recommend> list = iRecommendMapper.queryPage(startRow, endRow, recommend);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public Recommend getById(Integer id) throws Exception {
		Recommend recommend = iRecommendMapper.getById(id);
		return recommend;
	}

	@Override
	public ResultDto<Recommend> saveRecommend(Recommend recommend) throws Exception {
		int result = 0;
		ResultDto<Recommend> dto = new ResultDto<Recommend>();
		if(recommend.getId()==null){
			result = iRecommendMapper.insert(recommend);
			if(result!=1){
				dto.setData(recommend);
				dto.setResult("fail");
				dto.setErrorMsg("新增失败");
				return dto;
			}else{
				dto.setData(recommend);
				dto.setResult("success");
				return dto;
			}
		}else{
			result = iRecommendMapper.updateById(recommend.getId(), recommend);
			if(result!=1){
				dto.setData(recommend);
				dto.setResult("fail");
				dto.setErrorMsg("更新失败");
				return dto;
			}else{
				dto.setData(recommend);
				dto.setResult("success");
				return dto;
			}
		}
	}

	@Override
	public ResultDto<Recommend> deleteRecommendById(Integer id) throws Exception {
		ResultDto<Recommend> dto = new ResultDto<Recommend>();
		int result = iRecommendMapper.deleteByIdInLogic(id);
		if(result!=1){
			dto.setResult("fail");
			dto.setErrorMsg("删除失败");
		}else{
			dto.setResult("success");
		}
		return null;
	}

}
