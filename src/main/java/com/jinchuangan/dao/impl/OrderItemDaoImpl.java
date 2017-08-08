/*
 * 

 * 
 */
package com.jinchuangan.dao.impl;

import com.jinchuangan.dao.OrderItemDao;
import com.jinchuangan.entity.OrderItem;

import org.springframework.stereotype.Repository;

/**
 * Dao - 订单项
 * 
 * 
 * @version 1.0
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

}