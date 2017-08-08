/*
 * 

 * 
 */
package com.jinchuangan.service.impl;

import javax.annotation.Resource;

import com.jinchuangan.dao.OrderItemDao;
import com.jinchuangan.entity.OrderItem;
import com.jinchuangan.service.OrderItemService;

import org.springframework.stereotype.Service;

/**
 * Service - 订单项
 * 
 * 
 * @version 1.0
 */
@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long> implements OrderItemService {

	@Resource(name = "orderItemDaoImpl")
	public void setBaseDao(OrderItemDao orderItemDao) {
		super.setBaseDao(orderItemDao);
	}

}