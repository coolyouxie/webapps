package com.webapps.common.entity;

import com.webapps.common.utils.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class Enrollment extends Entity {
	
	private User user;
	
	private Recruitment recruitment;
	
	private Company company;
	
	private Integer state;
	
	private Integer isTalked;
	
	private String talkResult;
	
	private String remark;
	
	private String failedReason;
	
	private Date entryDate;
	
	private BigDecimal reward;

	private Integer cashbackDays;

	private Integer isHistory;

	private Integer talkerId;

	private Integer entryApproverId;

	private int isEntryApproved;

	private Integer expireApproverId;

	private int isExpireApproved;
	
	private String talkerName;
	
	private String entryApproverName;
	
	private String expireApproverName;

	/**
	 * 意向城市id
	 */
	private Integer intentionCityId;
	/**
	 * 是否同意面试，1同意，2不同意
	 */
	private int interviewIntention;
	/**
	 * 预约的面试时间（不是实际面试时间）
	 */
	private Date interviewTime;
	
	private String intentionCityName;


	public Integer getTalkerId() {
		return talkerId;
	}

	public void setTalkerId(Integer talkerId) {
		this.talkerId = talkerId;
	}

	public Integer getEntryApproverId() {
		return entryApproverId;
	}

	public void setEntryApproverId(Integer entryApproverId) {
		this.entryApproverId = entryApproverId;
	}

	public Integer getExpireApproverId() {
		return expireApproverId;
	}

	public void setExpireApproverId(Integer expireApproverId) {
		this.expireApproverId = expireApproverId;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public BigDecimal getReward() {
		return reward;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Recruitment getRecruitment() {
		return recruitment;
	}

	public void setRecruitment(Recruitment recruitment) {
		this.recruitment = recruitment;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Integer getIsTalked() {
		return isTalked;
	}

	public void setIsTalked(Integer isTalked) {
		this.isTalked = isTalked;
	}

	public String getTalkResult() {
		return talkResult;
	}

	public void setTalkResult(String talkResult) {
		this.talkResult = talkResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCashbackDays() {
		return cashbackDays;
	}

	public void setCashbackDays(Integer cashbackDays) {
		this.cashbackDays = cashbackDays;
	}

	public Integer getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(Integer isHistory) {
		this.isHistory = isHistory;
	}

	public String getEntryDateStr(){
		if(this.entryDate!=null){
			return DateUtil.format(this.entryDate,"yyyy-MM-dd");
		}
		return null;
	}

	public int getIsEntryApproved() {
		return isEntryApproved;
	}

	public void setIsEntryApproved(int isEntryApproved) {
		this.isEntryApproved = isEntryApproved;
	}

	public int getIsExpireApproved() {
		return isExpireApproved;
	}

	public void setIsExpireApproved(int isExpireApproved) {
		this.isExpireApproved = isExpireApproved;
	}

	public String getTalkerName() {
		return talkerName;
	}

	public void setTalkerName(String talkerName) {
		this.talkerName = talkerName;
	}

	public String getEntryApproverName() {
		return entryApproverName;
	}

	public void setEntryApproverName(String entryApproverName) {
		this.entryApproverName = entryApproverName;
	}

	public String getExpireApproverName() {
		return expireApproverName;
	}

	public void setExpireApproverName(String expireApproverName) {
		this.expireApproverName = expireApproverName;
	}

	public Integer getIntentionCityId() {
		return intentionCityId;
	}

	public void setIntentionCityId(Integer intentionCityId) {
		this.intentionCityId = intentionCityId;
	}

	public int getInterviewIntention() {
		return interviewIntention;
	}

	public void setInterviewIntention(int interviewIntention) {
		this.interviewIntention = interviewIntention;
	}

	public Date getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getInterviewTimeStr() {
		if(this.getInterviewTime()!=null){
			return DateUtil.format(this.getInterviewTime(),"yyyy-MM-dd");
		}
		return "";
	}

	public String getIntentionCityName() {
		return intentionCityName;
	}

	public void setIntentionCityName(String intentionCityName) {
		this.intentionCityName = intentionCityName;
	}
}
