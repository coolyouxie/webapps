package com.webapps.common.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xieshuai on 2017-6-28.
 */
public class Recruitment extends Entity{

    private Company company;
    private String title;
    private String breifInfo;
    private Date endDate;
    private int recruitmentNumber;
    private BigDecimal commision;
    private String workType;
    private String workAddress;
    private BigDecimal salaryLow;
    private BigDecimal salaryHigh;
    private String requirement;
    private Date publishTime;
    //发布人
    private Integer userId;
    private int type;
    private BigDecimal cashback;
    private int cashbackDays;
    private BigDecimal cashbackForBroker;



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

    public String getBreifInfo() {
        return breifInfo;
    }

    public void setBreifInfo(String breifInfo) {
        this.breifInfo = breifInfo;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRecruitmentNumber() {
        return recruitmentNumber;
    }

    public void setRecruitmentNumber(int recruitmentNumber) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getCashback() {
        return cashback;
    }

    public void setCashback(BigDecimal cashback) {
        this.cashback = cashback;
    }

    public int getCashbackDays() {
        return cashbackDays;
    }

    public void setCashbackDays(int cashbackDays) {
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

}
