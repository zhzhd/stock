/*
 * 

 * 
 */
package com.jinchuangan.dao;

import java.util.List;

import com.jinchuangan.entity.Area;

/**
 * Dao - 地区
 * 
 * 
 * @version 1.0
 */
public interface AreaDao extends BaseDao<Area, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Area> findRoots(Integer count);

}