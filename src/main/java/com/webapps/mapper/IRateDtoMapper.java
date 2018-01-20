package com.webapps.mapper;

import com.webapps.common.dto.RateDto;
import com.webapps.common.entity.ApplyExpenditure;
import com.webapps.common.form.ApplyExpenditureRequestForm;
import com.webapps.common.form.RateDtoRequestForm;

public interface IRateDtoMapper extends IBaseMapper<RateDto>,IPageMapper<RateDto,RateDtoRequestForm> {

}
