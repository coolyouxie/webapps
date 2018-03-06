package com.webapps.mapper;

import com.webapps.common.entity.PromotionConfig;
import com.webapps.common.form.PromotionConfigRequestForm;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IPromotionConfigMapper extends IBaseMapper<PromotionConfig>,IPageMapper<PromotionConfig,PromotionConfigRequestForm>{

    int updateStatusById(@Param("id")Integer id, @Param("status")int status)throws Exception;

    int updateStatusDate(@Param("obj")PromotionConfigRequestForm form)throws Exception;
    
    List<PromotionConfig> queryListByCondition(@Param("obj")PromotionConfigRequestForm form)throws Exception;
}
