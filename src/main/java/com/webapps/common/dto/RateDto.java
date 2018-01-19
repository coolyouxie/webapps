package com.webapps.common.dto;

/**
 * 招聘专员绩效统计信息
 * @author xieshuai
 *
 */
public class RateDto {

    private Integer talkerId;

    private String talkerName;

    private String talkerMobile;

    private Integer entryCount;

    private Integer talkCount;

    private Integer expireCount;

    private double entryRate;

    private double expireRate;

    private int state;

    /**
     * 1:入职，2:期满
     */
    private int type;

    public Integer getTalkerId() {
        return talkerId;
    }

    public void setTalkerId(Integer talkerId) {
        this.talkerId = talkerId;
    }

    public String getTalkerName() {
        return talkerName;
    }

    public void setTalkerName(String talkerName) {
        this.talkerName = talkerName;
    }

    public String getTalkerMobile() {
        return talkerMobile;
    }

    public void setTalkerMobile(String talkerMobile) {
        this.talkerMobile = talkerMobile;
    }

    public Integer getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(Integer entryCount) {
        this.entryCount = entryCount;
    }

    public Integer getTalkCount() {
        return talkCount;
    }

    public void setTalkCount(Integer talkCount) {
        this.talkCount = talkCount;
    }

    public Integer getExpireCount() {
        return expireCount;
    }

    public void setExpireCount(Integer expireCount) {
        this.expireCount = expireCount;
    }

    public double getEntryRate() {
        return entryRate;
    }

    public void setEntryRate(double entryRate) {
        this.entryRate = entryRate;
    }

    public double getExpireRate() {
        return expireRate;
    }

    public void setExpireRate(double expireRate) {
        this.expireRate = expireRate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
