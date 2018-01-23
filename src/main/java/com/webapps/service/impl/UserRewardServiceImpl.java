package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.UserReward;
import com.webapps.common.entity.UserWallet;
import com.webapps.common.utils.DateUtil;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.mapper.IUserRewardMapper;
import com.webapps.mapper.IUserWalletMapper;
import com.webapps.service.IUserRewardService;

@Service
public class UserRewardServiceImpl implements IUserRewardService {

	@Autowired
	private IUserRewardMapper iUserRewardMapper;
	
	@Autowired
	private IUserWalletMapper iUserWalletMapper;
	
	@Override
	public ResultDto<UserReward> getUserRewardByUserIdAndType(Integer userId, int type) {
		ResultDto<UserReward> dto = new ResultDto<UserReward>();
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.getSimplePatternCurrentDate());
		c.add(Calendar.DATE, 1);
		String startDate = DateUtil.getSimplePatternCurrentDateStr()+" 00:00:00";
		String endDate = DateUtil.format(c.getTime(), "yyyy-MM-dd hh:mm:ss");
		try {
			//查询抽奖记录，如果当天没有，则生成一条，如果有则判断是否还有抽奖次数
			UserReward ur = iUserRewardMapper.queryByUserIdTypeAndDate(userId, type, startDate, endDate);
			if(ur==null||ur.getId()==null){
				ur = new UserReward();
				ur.setCreateTime(new Date());
				ur.setFee(new BigDecimal(0));
				ur.setUserId(userId);
				ur.setType(type);
				int leftTimes = Integer.valueOf((String)PropertiesUtil.getProperty("initleftTimes"));
				ur.setLeftTimes(leftTimes);
				ur.setDataState(1);
				iUserRewardMapper.insert(ur);
				dto.setData(ur);
				dto.setResult("S");
				return dto;
			}
			if(ur.getLeftTimes()==0){
				dto.setData(ur);
				dto.setErrorMsg("今日次数已用完");
				dto.setResult("F");
				return dto;
			}
			dto.setData(ur);
			dto.setResult("S");
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("查询异常");
			dto.setResult("F");
			return dto;
		}
	}

	@Override
	public ResultDto<UserReward> saveUserReward(Integer urId, Integer userId, BigDecimal fee) {
		try {
			UserReward ur = iUserRewardMapper.getById(urId);
			if(ur.getType()==2){
				int leftTimes = ur.getLeftTimes()-1;
				ur.setLeftTimes(leftTimes);
			}
			ur.setUpdateTime(new Date());
			iUserRewardMapper.updateById(urId, ur);
			UserWallet uw = iUserWalletMapper.queryByUserId(userId);
			BigDecimal uwFee = uw.getFee();
			if(uwFee==null){
				uwFee = new BigDecimal(0);
			}
			uwFee = uwFee.add(fee);
			uw.setFee(uwFee);
			uw.setUpdateTime(new Date());
			iUserWalletMapper.updateById(uw.getId(), uw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultDto<String> resetLeftTimes() {
		int leftTimes = Integer.valueOf((String)PropertiesUtil.getProperty("initLeftTimes"));
		iUserRewardMapper.resetLeftTimes(leftTimes);
		return null;
	}

}
