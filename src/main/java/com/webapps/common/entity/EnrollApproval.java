package com.webapps.common.entity;

import com.webapps.common.utils.DateUtil;

public class EnrollApproval extends Enrollment {

    private Integer enrollmentId;

    private Integer state;

    private Integer type;

    private String remark;

    private String failedReason;

    private Integer enrollmentExtraId;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Integer getEnrollmentExtraId() {
        return enrollmentExtraId;
    }

    public void setEnrollmentExtraId(Integer enrollmentExtraId) {
        this.enrollmentExtraId = enrollmentExtraId;
    }

    public String getCreateTimeStr() {
        if (this.getCreateTime() != null) {
            return DateUtil.format(this.getCreateTime(), DateUtil.SIMPLE_PATTERN);
        }
        return null;
    }
}
