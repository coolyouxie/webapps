package com.webapps.common.form;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.Agency;

import java.io.Serializable;

public class AgencyResponseForm extends Agency implements Serializable {

	private static final long serialVersionUID = -8076189914016566968L;
	
	Page page = new Page();
	
	private String keywords;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
