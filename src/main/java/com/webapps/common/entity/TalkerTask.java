package com.webapps.common.entity;

public class TalkerTask extends Entity{

    private Integer talkerId;

    private String talkerName;

    private int jobsCount;

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

    public int getJobsCount() {
        return jobsCount;
    }

    public void setJobsCount(int jobsCount) {
        this.jobsCount = jobsCount;
    }
}
