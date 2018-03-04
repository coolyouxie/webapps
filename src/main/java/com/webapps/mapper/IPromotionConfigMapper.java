package com.webapps.mapper;

import com.webapps.common.entity.PromotionConfig;
import com.webapps.common.form.PromotionConfigRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IPromotionConfigMapper extends IBaseMapper<PromotionConfig>,IPageMapper<PromotionConfig,PromotionConfigRequestForm>{

    int updateStatusById(@Param("id")Integer id, @Param("status")int status)throws Exception;
}
