package com.webapps.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IBaseMapper<T> {

    public T getById(Integer id);

    public int deleteById(Integer id);

    public int updateById(Integer id,@Param("obj")T t);

    public T insert(@Param("obj")T t);

}
