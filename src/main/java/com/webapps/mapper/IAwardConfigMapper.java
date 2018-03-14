package com.webapps.mapper;

import org.springframework.stereotype.Repository;

import com.webapps.common.entity.AwardConfig;
import com.webapps.common.form.AwardConfigRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IAwardConfigMapper extends IBaseMapper<AwardConfig>,IPageMapper<AwardConfig,AwardConfigRequestForm>{

}
