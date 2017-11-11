package com.webapps.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;


/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IBaseMapper<T> {

    T getById(Integer id) throws Exception;

    int deleteById(Integer id) throws Exception;

    int updateById(@Param("id") Integer id, @Param("obj") T t)throws Exception;

    int insert(@Param("obj") T obj) throws DuplicateKeyException;
    
    List<T> queryAll() throws Exception;
    
	int deleteByIdInLogic(Integer id) throws Exception;

}
