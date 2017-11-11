package com.webapps.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webapps.common.entity.UserReward;
import com.webapps.common.form.UserRewardRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IUserRewardMapper extends IBaseMapper<UserReward>,IPageMapper<UserReward,UserRewardRequestForm>{
	
	
	UserReward queryAvaliableUserIdTypeAndDate(@Param("userId") Integer userId, @Param("type") int type,
                                               @Param("startDate") String startDate, @Param("endDate") String endDate)throws Exception;
	
	UserReward queryByUserIdTypeAndDate(@Param("userId") Integer userId, @Param("type") int type,
                                        @Param("startDate") String startDate, @Param("endDate") String endDate)throws Exception;
	
	int resetLeftTimes(@Param("leftTimes") int leftTimes);
	
}
