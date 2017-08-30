package com.webapps.common.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xieshuai on 2017-6-28.
 */
public class Recruitment extends Entity{

    private Company company;
    private String title;
    private String briefInfo;
    private Date endDate;
    private Integer recruitmentNumber;
    private BigDecimal commision;
    private String workType;
    private String workAddress;
    private BigDecimal salaryLow;
    private BigDecimal salaryHigh;
    private String requirement;
    private Date publishTime;
    //发布人
    private Integer userId;
    /**
     * 发布单类型，1热招，2高返费，3工作轻松，4高工资
     */
    private Integer type;
    private BigDecimal cashback;
    private Integer cashbackDays;
    private BigDecimal cashbackForBroker;
    
    private String mobile;
    
    private String QQ;
    
    private String weiXin;

    /**
     * 发布类型，1普招，2直招，3兼职
     */
    private Integer publishType;

    /**
     * 薪酬福利
     */
    private String salaryBriefs;
    
    /**
     * 补贴详情
     */
    private String cashbackBriefs;
    
    /**
     * 岗位职责
     */
    private String jobBriefs;
    
    /**
     * 是否作为banner展示
     */
    private Integer isBanner;
    
    private BannerConfig bannerConfig;
    
    private Integer isMessage;
    
    private MessageConfig message;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getRecruitmentNumber() {
        return recruitmentNumber;
    }

    public void setRecruitmentNumber(Integer recruitmentNumber) {
        this.recruitmentNumber = recruitmentNumber;
    }

    public BigDecimal getCommision() {
        return commision;
    }

    public void setCommision(BigDecimal commision) {
        this.commision = commision;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public BigDecimal getSalaryLow() {
        return salaryLow;
    }

    public void setSalaryLow(BigDecimal salaryLow) {
        this.salaryLow = salaryLow;
    }

    public BigDecimal getSalaryHigh() {
        return salaryHigh;
    }

    public void setSalaryHigh(BigDecimal salaryHigh) {
        this.salaryHigh = salaryHigh;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getCashback() {
        return cashback;
    }

    public void setCashback(BigDecimal cashback) {
        this.cashback = cashback;
    }

    public Integer getCashbackDays() {
        return cashbackDays;
    }

    public void setCashbackDays(Integer cashbackDays) {
        this.cashbackDays = cashbackDays;
    }

    public BigDecimal getCashbackForBroker() {
        return cashbackForBroker;
    }

    public void setCashbackForBroker(BigDecimal cashbackForBroker) {
        this.cashbackForBroker = cashbackForBroker;
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getBriefInfo() {
		return briefInfo;
	}

	public void setBriefInfo(String briefInfo) {
		this.briefInfo = briefInfo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWeiXin() {
		return weiXin;
	}

	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	public Integer getPublishType() {
		return publishType;
	}

	public void setPublishType(Integer publishType) {
		this.publishType = publishType;
	}

	public String getSalaryBriefs() {
		return salaryBriefs;
	}

	public void setSalaryBriefs(String salaryBriefs) {
		this.salaryBriefs = salaryBriefs;
	}

	public String getCashbackBriefs() {
		return cashbackBriefs;
	}

	public void setCashbackBriefs(String cashbackBriefs) {
		this.cashbackBriefs = cashbackBriefs;
	}

	public String getJobBriefs() {
		return jobBriefs;
	}

	public void setJobBriefs(String jobBriefs) {
		this.jobBriefs = jobBriefs;
	}

	public Integer getIsBanner() {
		return isBanner;
	}

	public void setIsBanner(Integer isBanner) {
		this.isBanner = isBanner;
	}

	public BannerConfig getBannerConfig() {
		return bannerConfig;
	}

	public void setBannerConfig(BannerConfig bannerConfig) {
		this.bannerConfig = bannerConfig;
	}

	public Integer getIsMessage() {
		return isMessage;
	}

	public void setIsMessage(Integer isMessage) {
		this.isMessage = isMessage;
	}

	public MessageConfig getMessage() {
		return message;
	}

	public void setMessage(MessageConfig message) {
		this.message = message;
	}

}
