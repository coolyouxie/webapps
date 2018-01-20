package com.webapps.service;

import java.util.List;

import com.aliyuncs.exceptions.ClientException;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Recommend;
import com.webapps.common.entity.User;
import com.webapps.common.form.RecommendRequestForm;

public interface IRecommendService {
	
	Page loadRecommendList(Page page, RecommendRequestForm recommend) throws Exception;
	
	Recommend getById(Integer id) throws Exception;
	
	ResultDto<Recommend> saveRecommend(Recommend recommend) throws Exception;
	
	ResultDto<Recommend> deleteRecommendById(Integer id) throws Exception;
	
	List<Recommend> queryRecommendListByUserId(Integer userId)throws Exception;
	
	ResultDto<Recommend> userRecommend(Recommend recommend);
	
	/**
	 * 发送用户邀请码到指定的手机号码
	 * 如果对象手机号已经是会员，则提示
	 * 生成发送邀请记录，24小时有效。
	 * @author scorpio.yang
	 * @since 2018-01-15
	 * 
	 * 增加被邀请人姓名，用户红包列表用户名称显示
	 * @author scorpio.yang
	 * @since 2018-01-20
	 * 
	 * @param phoneNum
	 * @param inviteCode
	 * @param inviteUserName
	 * @return
	 * @throws ClientException 
	 * @throws Exception 
	 */
	public ResultDto<String> sendUserInviteCode(String phoneNum , String inviteUserName, User fromUser) throws ClientException, Exception;
	
	/**
	 * 注册用户填写邀请码，进行数据入库
	 * 如果注册码没有对应的用户，则返回失败提示
	 * 如果短信发送过邀请码，则更新记录。
	 * 如果未发送短信，则新增邀请记录。
	 * @author scorpio.yang
	 * @since 2018-01-15
	 * @param registUser
	 * @param inviteCode
	 * @param inviteUserName
	 * @return
	 * @throws Exception 
	 */
	public ResultDto<String> saveInviteRecommend(User registUser , String inviteCode, String inviteUserName) throws Exception;
	
	/**
	 * 根据手机号码获取对象。
	 * 用于被邀请人，获取邀请人信息使用
	 * 被邀请人可能有多条被邀请记录，所以只获取state=2（已注册）的，记录
	 * @param mobile
	 * @return
	 * @throws Exception 
	 */
	public List<Recommend> getByMobile(String mobile) throws Exception;
	
}
