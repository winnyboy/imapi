package com.huione.im.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BaseDao<T> {
    List<T> select(T t);
	
	int save(T t);
	
	int update(T t);
	
	int delete(T t);
}
