package com.webapps.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;


/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IBaseMapper<T> {

    public T getById(Integer id) throws Exception;

    public int deleteById(Integer id) throws Exception;

    public int updateById(@Param("id")Integer id,@Param("obj")T t)throws Exception;

    public int insert(@Param("obj")T obj) throws DuplicateKeyException;
    
    public List<T> queryAll() throws Exception;
    
	public int deleteByIdInLogic(Integer id) throws Exception;

}
