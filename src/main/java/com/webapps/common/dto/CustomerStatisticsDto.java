package com.webapps.common.dto;

import java.util.Date;

public class CustomerStatisticsDto {

    private Integer customerId;

    private String customerName;

    private String customerMobile;

    private String companyName;

    private String recruitmentTitle;

    private String customerIdCardNo;

    private Date customerEnrollDate;

    private int state;

    private int type;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRecruitmentTitle() {
        return recruitmentTitle;
    }

    public void setRecruitmentTitle(String recruitmentTitle) {
        this.recruitmentTitle = recruitmentTitle;
    }

    public String getCustomerIdCardNo() {
        return customerIdCardNo;
    }

    public void setCustomerIdCardNo(String customerIdCardNo) {
        this.customerIdCardNo = customerIdCardNo;
    }

    public Date getCustomerEnrollDate() {
        return customerEnrollDate;
    }

    public void setCustomerEnrollDate(Date customerEnrollDate) {
        this.customerEnrollDate = customerEnrollDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
