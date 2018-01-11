package com.webapps.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webapps.common.dto.UserApproveCountDto;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IUserApproveCountMapper extends IBaseMapper<UserApproveCountDto>{
	
	List<UserApproveCountDto> queryTalkCount();
	
	List<UserApproveCountDto> queryEntryApproveCount();
	
	List<UserApproveCountDto> queryExpireApproveCount();
}
