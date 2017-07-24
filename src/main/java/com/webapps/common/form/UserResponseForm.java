package com.webapps.common.form;

import java.io.Serializable;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.User;

public class UserResponseForm extends User implements Serializable {

	private static final long serialVersionUID = -8076189914016566968L;
	
	Page page = new Page();
	
	private String keyWords;

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

}
