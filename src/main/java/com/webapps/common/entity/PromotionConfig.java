package com.webapps.common.entity;

import com.webapps.common.utils.DateUtil;

import java.util.Date;
import java.util.List;

public class PromotionConfig extends Entity {

	private String name;
	
	private String remark;

	private String brief;

	private int status;

	private Date effectiveDate;

	private Date expiryDate;

	private List<Picture> pictureList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public List<Picture> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}

	public String getEffectiveDateSimpleStr() {
		if(this.getEffectiveDate()!=null){
			return DateUtil.format(this.getEffectiveDate(),DateUtil.SIMPLE_PATTERN);
		}
		return "";
	}

	public String getExpiryDateSimpleStr() {
		if(this.getExpiryDate()!=null){
			return DateUtil.format(this.getExpiryDate(),DateUtil.SIMPLE_PATTERN);
		}
		return "";
	}

}
