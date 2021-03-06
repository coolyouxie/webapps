package com.webapps.common.entity;

import java.util.List;

/**
 * Created by xieshuai on 2017-6-27.
 */
public class User extends Entity{

    private String account;

    private String password;

    private String name;

    private String idCardNo;

    private Integer gender;

    private Integer age;

    private String telephone;

    private String mobile;

    private String qq;

    private String weixin;

    private String email;

    private String homeAddress;

    private String address;

    private Integer userType;

    private Integer loginState;

    private Integer educationId;
    
    private String token;
    
    private UserWallet userWallet;
    
    /**
     * 0:用户未报名，1用户报名未入职，2用户已入职，3用户已期满，4用户已离职
     */
    private Integer currentState;

    private Integer isPayedRecommendFee;

    private List<Picture> pictures;

    private String bankCardNum;
    
    private String inviteCode;
    
    private String awardFlag;

    private Integer agencyId;

    private Integer agencyProvinceId;

    private Integer agencyCityId;

    private Integer agencyAreaId;

    public Integer getIsPayedRecommendFee() {
        return isPayedRecommendFee;
    }

    public void setIsPayedRecommendFee(Integer isPayedRecommendFee) {
        this.isPayedRecommendFee = isPayedRecommendFee;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getLoginState() {
        return loginState;
    }

    public void setLoginState(Integer loginState) {
        this.loginState = loginState;
    }

	public Integer getEducationId() {
		return educationId;
	}

	public void setEducationId(Integer educationId) {
		this.educationId = educationId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserWallet getUserWallet() {
		return userWallet;
	}

	public void setUserWallet(UserWallet userWallet) {
		this.userWallet = userWallet;
	}

	public Integer getCurrentState() {
		return currentState;
	}

	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getAwardFlag() {
		return awardFlag;
	}

	public void setAwardFlag(String awardFlag) {
		this.awardFlag = awardFlag;
	}

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Integer getAgencyProvinceId() {
        return agencyProvinceId;
    }

    public void setAgencyProvinceId(Integer agencyProvinceId) {
        this.agencyProvinceId = agencyProvinceId;
    }

    public Integer getAgencyCityId() {
        return agencyCityId;
    }

    public void setAgencyCityId(Integer agencyCityId) {
        this.agencyCityId = agencyCityId;
    }

    public Integer getAgencyAreaId() {
        return agencyAreaId;
    }

    public void setAgencyAreaId(Integer agencyAreaId) {
        this.agencyAreaId = agencyAreaId;
    }
}
