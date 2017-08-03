package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;


/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IBaseMapper<T> {

    public T getById(Integer id);

    public int deleteById(Integer id);

    public int updateById(@Param("id")Integer id,@Param("obj")T t);

    public int insert(@Param("obj")T obj) throws DuplicateKeyException;
    
    public List<T> queryAll();
    
	public int deleteByIdInLogic(Integer id);

}
