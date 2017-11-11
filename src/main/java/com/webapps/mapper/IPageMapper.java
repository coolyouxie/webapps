package com.webapps.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPageMapper<T,R> {

	int queryCount(@Param("obj") R obj);

    List<T> queryPage(@Param("startRow") int startRow, @Param("endRow") int endRow, @Param("obj") R obj);
}
