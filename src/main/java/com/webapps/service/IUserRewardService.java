package com.webapps.service;

import java.math.BigDecimal;

import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.UserReward;

public interface IUserRewardService {
	
	public ResultDto<UserReward> getUserRewardByUserIdAndType(Integer userId,int type);
	
	public ResultDto<UserReward> saveUserReward(Integer urId,Integer userId,BigDecimal fee);
	
	public ResultDto<String> resetLeftTimes();
	

}
