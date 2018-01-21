package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.ParamConfig;
import com.webapps.common.entity.Recommend;
import com.webapps.common.entity.User;
import com.webapps.common.entity.UserAward;
import com.webapps.common.entity.UserWallet;
import com.webapps.mapper.IUserAwardMapper;
import com.webapps.mapper.IUserWalletMapper;
import com.webapps.service.IParamConfigService;
import com.webapps.service.IRecommendService;
import com.webapps.service.IUserAwardService;
import com.webapps.service.IUserService;
import com.webapps.service.IUserWalletService;

@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class})
public class UserAwardServiceImpl implements IUserAwardService {

	@Autowired private IUserAwardMapper iUserAwardMapper;
	
	@Autowired private IUserWalletMapper iUserWalletMapper;
	
	@Autowired private IRecommendService iRecommendService;
	
	@Autowired private IUserWalletService iUserWalletService;
	
	@Autowired private IParamConfigService iParamConfigService;
	
	@Autowired private IUserService iUserService;
	
	/**
	 * 根据id，获取记录
	 */
	public UserAward getById(Integer id) throws Exception{
		return this.iUserAwardMapper.getById(id);
	}
	
	/**
	 * 获取所有信息列表
	 * @throws Exception 
	 */
	public List<UserAward> findAll() throws Exception{
		return this.iUserAwardMapper.queryAll();
	}
	
	/**
	 * 根据参数，生成新的红包记录。
	 * 该方法调用，是被邀请人在触发需要发放红包的状态节点时调用。
	 * 如果邀请人，和被邀请人存在同类型的红包，则抛出异常，目前邀请一个人，每个红包，只能领取一份
	 * @param curroutUser
	 * @param pc
	 * @return
	 */
	public ResultDto<String> addNewAward(User currentUser, ParamConfig pc){
		ResultDto<String> dto = new ResultDto<String>();
		if( this.checkUserAwardFlag(currentUser.getAwardFlag(), pc) ) {
			//获取邀请列表信息，找到邀请我的人。
			try {
				List<Recommend> recommendList = iRecommendService.getByMobile(currentUser.getMobile());
				if(null != recommendList && recommendList.size()==1) {
					Recommend recommend = recommendList.get(0);
					UserAward ua = new UserAward();
					ua.setUserId(recommend.getUser().getId());
					ua.setInviteUserId(currentUser.getId());
					ua.setParamConfigId(pc.getId());
					BigDecimal price = this.iParamConfigService.getParamConfigPrice(pc);
					if(null != price) {
						ua.setPrice(price);
						ua.setStat(1);//默认未领取状态
						this.iUserAwardMapper.insert(ua);
						dto.setResult("S");
						dto.setErrorMsg("红包创建完成");
						dto.setData(price.toEngineeringString());
						//完成红包生成，标记该红包已经生成
						currentUser.setAwardFlag( this.updateUserAwardFlagTakeNum(currentUser.getAwardFlag(), pc) );
						this.iUserService.saveUser(currentUser);
					}else {
						dto.setResult("F");
						dto.setErrorMsg("红包金额异常，创建红包失败。");
					}
				}else{
					dto.setResult("F");
					dto.setErrorMsg("未找到邀请人，红包未创建。");
				}
			} catch (Exception e) {
				e.printStackTrace();
				dto.setResult("F");
				dto.setErrorMsg("红包创建遇到意外。");
			}
		}else {
			dto.setResult("F");
			dto.setErrorMsg("红包领取超过上限，不再发放红包。");
		}
		return dto;
	}
	
	/**
	 * 根据记录id，执行红包领取操作，
	 * 该方法调用，是邀请人在领取红包时调用。
	 * 领取操作：先设置状态为领取，然后设置用户账号的余额增加红包的金额
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public ResultDto<String> useAward(Integer id) throws Exception{
		ResultDto<String> dto = new ResultDto<String>();
		//获取红包记录
		UserAward ua = this.iUserAwardMapper.getById(id);
		if(null != ua) {
			//判断红包金额是否正确，如果不正确，抛出错误，避免钱包金额异常
			if(null != ua.getPrice() && ua.getPrice().compareTo(BigDecimal.ZERO)==1){
				//设置状态，并保存
				ua.setStat(2);//已经领取
				//获取邀请人的钱包
				UserWallet userWallet = iUserWalletService.getUserWalletByUserId(ua.getUserId());
				//邀请人的钱包，余额增加红包的金额
				userWallet.setFee( userWallet.getFee().add(ua.getPrice()) );
				userWallet.setUpdateTime(new Date());
				//保存变更
				this.iUserWalletMapper.updateById(userWallet.getId(), userWallet);
				this.iUserAwardMapper.updateById(ua.getId(), ua);
				dto.setResult("S");
				dto.setErrorMsg("领取红包成功。");
				dto.setData(ua.getPrice().toEngineeringString());
			}else {
				dto.setResult("F");
				dto.setErrorMsg("红包金额异常，领取红包失败。");
			}
		}else {
			dto.setResult("F");
			dto.setErrorMsg("未找到红包信息，领取红包失败。");
		}
		return dto;
	}
	
	/**
	 * 查询用户的红包列表
	 * 分页，分页逻辑，按照红包生成时间排序，groupby用户id，返回的记录为10个用户的所有红包记录
	 * @param page
	 * @param userId
	 * @return
	 */
	public Page getUserAwardByUserId(Page page, int userId) {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		List<UserAward> list = iUserAwardMapper.queryPage(startRow, rows, userId);
		page.setResultList(list);
		return page;
	}
	
	/**
	 * 根据配置参数，获取相应位数的值，判断是否可以进行红包生成
	 * 目前，设置领取次数只有1次，所以设置了0才能进行领取
	 * @param userAwardFlag
	 * @param pc
	 * @return
	 */
	public boolean checkUserAwardFlag(String userAwardFlag, ParamConfig pc) {
		int site = pc.getId();
		if(userAwardFlag.length() >= site) {
			if(userAwardFlag.toCharArray()[site-1] == '0') {
				return true;
			}
		}
		return true;
	}
	
	/**
	 * 根据配置参数，设置相应位数的值，标记为指定红包领取的次数，返回完整字符串
	 * 如果出现字符串长度错误，则只更新对应位置，其他不足，以0补充
	 * @param userAwardFlag
	 * @param pc
	 * @return
	 */
	public String updateUserAwardFlagTakeNum(String userAwardFlag, ParamConfig pc) {
		int site = pc.getId();
		while(userAwardFlag.length() < site){
			userAwardFlag += "0";
		}
		char[] cStr = userAwardFlag.toCharArray();
		int siteNum = Character.getNumericValue(cStr[site-1]);
		siteNum++;
		cStr[site-1] = (""+siteNum).toCharArray()[0];
		return new String(cStr);
	}

}
