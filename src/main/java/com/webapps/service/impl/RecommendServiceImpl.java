package com.webapps.service.impl;

import java.util.Date;
import java.util.List;

import com.webapps.common.utils.PropertyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Recommend;
import com.webapps.common.entity.User;
import com.webapps.common.form.RecommendRequestForm;
import com.webapps.common.utils.DateUtil;
import com.webapps.mapper.IRecommendMapper;
import com.webapps.mapper.IUserMapper;
import com.webapps.service.IRecommendService;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional
public class RecommendServiceImpl implements IRecommendService {
	
	private static Logger logger = Logger.getLogger(RecommendServiceImpl.class);
	
	@Autowired
	private IRecommendMapper iRecommendMapper;
	
	@Autowired
	private IUserMapper iUserMapper;

	@Override
	public Page loadRecommendList(Page page, RecommendRequestForm recommend) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iRecommendMapper.queryCount(recommend);
		List<Recommend> list = iRecommendMapper.queryPage(startRow, rows, recommend);
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
			recommend.setDataState(1);
			recommend.setState(1);
			result = iRecommendMapper.insert(recommend);
			if(result!=1){
				dto.setData(recommend);
				dto.setResult("F");
				dto.setErrorMsg("新增失败");
				return dto;
			}else{
				dto.setData(recommend);
				dto.setResult("S");
				return dto;
			}
		}else{
			result = iRecommendMapper.updateById(recommend.getId(), recommend);
			if(result!=1){
				dto.setData(recommend);
				dto.setResult("F");
				dto.setErrorMsg("更新失败");
				return dto;
			}else{
				dto.setData(recommend);
				dto.setResult("S");
				return dto;
			}
		}
	}

	@Override
	public ResultDto<Recommend> deleteRecommendById(Integer id) throws Exception {
		ResultDto<Recommend> dto = new ResultDto<Recommend>();
		int result = iRecommendMapper.deleteByIdInLogic(id);
		if(result!=1){
			dto.setResult("F");
			dto.setErrorMsg("删除失败");
		}else{
			dto.setResult("S");
		}
		return null;
	}

	@Override
	public List<Recommend> queryRecommendListByUserId(Integer userId) throws Exception {
		List<Recommend> list = iRecommendMapper.queryByUserId(userId);
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class})
	public ResultDto<Recommend> userRecommend(Recommend recommend) {
		RecommendRequestForm form = new RecommendRequestForm();
		ResultDto<Recommend> dto = new ResultDto<Recommend>();
		form.setMobile(recommend.getMobile());
		List<Recommend> list = iRecommendMapper.queryPage(0,1,form);
		Recommend r = null;
		try {
			//先根据传入的被推荐人手机号查询用户表，如果能查到数据说明该用户已注册
			User user = iUserMapper.queryUserByAccount(recommend.getMobile());
			if(user!=null&&user.getId()!=null){
				dto.setResult("F");
				dto.setErrorMsg("该用户已注册会员，不能被推荐");
				return dto;
			}
			if(CollectionUtils.isNotEmpty(list)){
				r = list.get(0);
			}else{
				dto = saveRecommend(recommend);
				return dto;
			}
			//如果被推荐人之前被推荐过，则要判断之前最近一条推荐记录是否在有效期内，目前为20天
			Date now = new Date();
			double days = DateUtil.getDaysBetweenTwoDates(r.getCreateTime(), now);
			Integer recommendOverDays = Integer.valueOf((String)PropertyUtil.getProperty("recommend_over_days"));
			if(days>recommendOverDays){
				recommend.setDataState(1);
				recommend.setCreateTime(new Date());
				recommend.setState(1);
				r.setDataState(0);
				r.setState(3);
				r.setUpdateTime(new Date());
				iRecommendMapper.updateById(r.getId(),r);
				iRecommendMapper.insert(recommend);
				dto.setResult("S");
				return dto;
			}else{
				dto = new ResultDto<Recommend>();
				dto.setResult("F");
				dto.setErrorMsg("该用户还在有效推荐期内，不可重复推荐");
				return dto;
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error(e.getMessage());
			dto = new ResultDto<Recommend>();
			dto.setResult("F");
			dto.setErrorMsg("推荐时异常，请稍后再试");
			return dto;
		}
	}

}
