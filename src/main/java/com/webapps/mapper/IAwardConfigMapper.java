package com.webapps.mapper;

import com.webapps.common.entity.AliSmsMsg;
import com.webapps.common.entity.AwardConfig;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.form.AwardConfigRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IAwardConfigMapper extends IBaseMapper<AwardConfig>,IPageMapper<AwardConfig,AwardConfigRequestForm>{

}
