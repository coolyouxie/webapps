package com.webapps.common.form;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.UserAwardExchange;

import java.io.Serializable;

public class UserAwardExchangeResponseForm extends UserAwardExchange implements Serializable {

	private static final long serialVersionUID = -8076189914016566968L;
	
	Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
