/*
 * 

 * 
 */
package com.jinchuangan.dao;

import java.util.List;
import java.util.Set;

import com.jinchuangan.entity.Parameter;
import com.jinchuangan.entity.ParameterGroup;

/**
 * Dao - 参数
 * 
 * 
 * @version 1.0
 */
public interface ParameterDao extends BaseDao<Parameter, Long> {

	/**
	 * 查找参数
	 * 
	 * @param parameterGroup
	 *            参数组
	 * @param excludes
	 *            排除参数
	 * @return 参数
	 */
	List<Parameter> findList(ParameterGroup parameterGroup, Set<Parameter> excludes);

}