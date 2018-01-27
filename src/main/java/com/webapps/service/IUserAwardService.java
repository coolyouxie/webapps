package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.dto.UserAwardListDTO;
import com.webapps.common.entity.ParamConfig;
import com.webapps.common.entity.User;
import com.webapps.common.entity.UserAward;

public interface IUserAwardService {
	
	public UserAward getById(Integer id) throws Exception;
	
	public List<UserAward> findAll() throws Exception;
	
	/**
	 * @author scorpio.yang
	 * @since 2018-01-27
	 * 转换用户红包信息记录，转换为，每一个用户，分类记录红包信息
	 */
	public List<UserAwardListDTO> convertListToDTO(List<UserAward> list);
	
	/**
	 * 根据参数，生成新的红包记录。
	 * 该方法调用，是被邀请人在触发需要发放红包的状态节点时调用。
	 * 如果邀请人，和被邀请人存在同类型的红包，则抛出异常，目前邀请一个人，每个红包，只能领取一份
	 * @param curroutUser
	 * @param pc
	 * @return
	 */
	public ResultDto<String> addNewAward(User currentUser, ParamConfig pc);
	
	/**
	 * 根据记录id，执行红包领取操作，
	 * 该方法调用，是邀请人在领取红包时调用。
	 * 领取操作：先设置状态为领取，然后设置用户账号的余额增加红包的金额
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public ResultDto<String> useAward(Integer id) throws Exception;
	
	/**
	 * 查询用户的红包列表
	 * 分页，分页逻辑，按照红包生成时间排序，groupby用户id，返回的记录为10个用户的所有红包记录
	 * @param page
	 * @param userId
	 * @return
	 */
	public Page getUserAwardByUserId(Page page, int userId);
	
}
