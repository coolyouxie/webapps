package com.webapps.common.form;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.entity.EnrollmentExtra;

import java.io.Serializable;

public class EnrollmentExtraResponseForm extends EnrollmentExtra implements Serializable {

	private static final long serialVersionUID = -8076189914016566968L;
	
	Page page = new Page();
	
	private String keyWords;
	
	/**
	 * 操作类型（查询，新增，更新，删除）
	 */
	private String handleType;

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

}
