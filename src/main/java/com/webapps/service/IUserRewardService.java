package com.webapps.service;

import java.math.BigDecimal;

import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.UserReward;

public interface IUserRewardService {
	
	ResultDto<UserReward> getUserRewardByUserIdAndType(Integer userId, int type);
	
	ResultDto<UserReward> saveUserReward(Integer urId, Integer userId, BigDecimal fee);
	
	ResultDto<String> resetLeftTimes();
	

}
