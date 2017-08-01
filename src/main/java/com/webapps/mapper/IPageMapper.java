package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IPageMapper<T,Obj extends T> {

	public int queryCount(@Param("obj")T obj);

    public List<T> queryPage(@Param("startRow")int startRow,@Param("endRow")int endRow,@Param("obj")Obj obj);
}
