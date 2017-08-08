/*
 * 

 * 
 */
package com.jinchuangan.dao.impl;

import com.jinchuangan.dao.OrderLogDao;
import com.jinchuangan.entity.OrderLog;

import org.springframework.stereotype.Repository;

/**
 * Dao - 订单日志
 * 
 * 
 * @version 1.0
 */
@Repository("orderLogDaoImpl")
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog, Long> implements OrderLogDao {

}