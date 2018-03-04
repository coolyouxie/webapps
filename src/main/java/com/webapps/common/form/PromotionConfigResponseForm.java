package com.webapps.common.form;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.AwardConfig;
import com.webapps.common.entity.PromotionConfig;

import java.io.Serializable;

public class PromotionConfigResponseForm extends PromotionConfig implements Serializable {

	private static final long serialVersionUID = -8076189914016566968L;
	
	Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
