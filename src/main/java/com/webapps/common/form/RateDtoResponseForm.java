package com.webapps.common.form;

import com.webapps.common.bean.Page;
import com.webapps.common.dto.RateDto;
import com.webapps.common.entity.ApplyExpenditure;

import java.io.Serializable;

public class RateDtoResponseForm extends RateDto implements Serializable {

	private static final long serialVersionUID = -8076189914016566968L;
	
	Page page = new Page();
}
