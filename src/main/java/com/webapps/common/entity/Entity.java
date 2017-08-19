package com.webapps.common.entity;

import java.util.Date;

import com.webapps.common.utils.DateUtil;

/**
 * Created by xieshuai on 2017-6-28.
 */
public class Entity {

    private Integer id;
    private Date createTime = new Date();
    
    private Date updateTime;
    
    public String getCreateTimeStr(){
		if(this.getCreateTime()!=null){
			return DateUtil.format(this.getCreateTime(), DateUtil.SIMPLE_PATTERN);
		}
		return null;
	}
    
    private int dataState = 1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getDataState() {
        return dataState;
    }

    public void setDataState(int dataState) {
        this.dataState = dataState;
    }
}
