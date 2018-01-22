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
	
	/**
	 * 查询数据，根据(发起邀请的)用户Id和邀请码
	 * 用于判断当前邀请是否发送过，如果发送过，则返回记录。
	 * @author scorpio.yang
	 * @since 2018-01-15
	 * @param userId
	 * @param inviteCode
	 * @return
	 */
	Recommend getByUserIdAndCodeAndPhone(@Param("userId") Integer userId, @Param("inviteCode") String inviteCode, @Param("mobile") String mobile);
	
}
