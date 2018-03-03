package com.webapps.common.form;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.entity.AwardConfig;

import java.io.Serializable;

public class AwardConfigResponseForm extends AwardConfig implements Serializable {

	private static final long serialVersionUID = -8076189914016566968L;
	
	Page page = new Page();

	/**
	 * 操作类型（查询，新增，更新，删除）
	 */
	private String handleType;

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
