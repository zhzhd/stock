/*
 * 

 * 
 */
package com.jinchuangan.dao.impl;

import javax.persistence.FlushModeType;

import com.jinchuangan.dao.LogDao;
import com.jinchuangan.entity.Log;

import org.springframework.stereotype.Repository;

/**
 * Dao - 日志
 * 
 * 
 * @version 1.0
 */
@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

	public void removeAll() {
		String jpql = "delete from Log log";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
	}

}