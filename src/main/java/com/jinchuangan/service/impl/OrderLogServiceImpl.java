/*
 * 

 * 
 */
package com.jinchuangan.service.impl;

import javax.annotation.Resource;

import com.jinchuangan.dao.OrderLogDao;
import com.jinchuangan.entity.OrderLog;
import com.jinchuangan.service.OrderLogService;

import org.springframework.stereotype.Service;

/**
 * Service - 订单日志
 * 
 * 
 * @version 1.0
 */
@Service("orderLogServiceImpl")
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLog, Long> implements OrderLogService {

	@Resource(name = "orderLogDaoImpl")
	public void setBaseDao(OrderLogDao orderLogDao) {
		super.setBaseDao(orderLogDao);
	}

}