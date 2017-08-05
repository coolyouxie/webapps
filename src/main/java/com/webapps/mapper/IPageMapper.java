package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IPageMapper<T,R extends T> {

	public int queryCount(@Param("obj")R obj);

    public List<T> queryPage(@Param("startRow")int startRow,@Param("endRow")int endRow,@Param("obj")R obj);
}
