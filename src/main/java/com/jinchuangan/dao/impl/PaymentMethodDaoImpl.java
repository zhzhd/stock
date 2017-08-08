/*
 * 

 * 
 */
package com.jinchuangan.dao.impl;

import com.jinchuangan.dao.PaymentMethodDao;
import com.jinchuangan.entity.PaymentMethod;

import org.springframework.stereotype.Repository;

/**
 * Dao - 支付方式
 * 
 * 
 * @version 1.0
 */
@Repository("paymentMethodDaoImpl")
public class PaymentMethodDaoImpl extends BaseDaoImpl<PaymentMethod, Long> implements PaymentMethodDao {

}