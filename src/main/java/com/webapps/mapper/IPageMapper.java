package com.webapps.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPageMapper<T,R> {

	public int queryCount(@Param("obj")R obj);

    public List<T> queryPage(@Param("startRow")int startRow,@Param("endRow")int endRow,@Param("obj")R obj);
}
