package com.webapps.mapper;

import com.webapps.common.entity.UserAwardExchange;
import com.webapps.common.form.UserAwardExchangeRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IUserAwardExchangeMapper extends IBaseMapper<UserAwardExchange>,IPageMapper<UserAwardExchange,UserAwardExchangeRequestForm>{

    List<UserAwardExchange> queryUserAwardExchangeForExport(@Param("obj") UserAwardExchangeRequestForm form);

}
