package com.webapps.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.webapps.common.utils.DateUtil;

/**
 * Created by xieshuai on 2017-6-28.
 */
public class Entity implements Serializable{

	private static final long serialVersionUID = 3453671128142005054L;

	private Integer id;
    
    private Date createTime = new Date();
    
    private Date updateTime;
    
    /**
     * 操作人ID
     */
    private Integer operatorId;
    
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

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getUpdateTimeStr(){
		if(this.getUpdateTime()!=null){
			return DateUtil.format(this.getUpdateTime(), DateUtil.SIMPLE_PATTERN);
		}
		return null;
	}
	
	public String getUpdateTimeFullStr(){
		if(this.getUpdateTime()!=null){
			return DateUtil.format(this.getUpdateTime(), DateUtil.FULL_PATTERN);
		}
		return null;
	}
	
	public String getCreateTimeFullStr(){
		if(this.getCreateTime()!=null){
			return DateUtil.format(this.getCreateTime(), DateUtil.FULL_PATTERN);
		}
		return null;
	}
}
