package com.webapps.mapper;

import com.webapps.common.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2018-2-28.
 */
@Repository
public interface ITagMapper extends IBaseMapper<Tag>{

    List<Tag> queryByIds(@Param("array")Integer[] ids)throws Exception;

}
