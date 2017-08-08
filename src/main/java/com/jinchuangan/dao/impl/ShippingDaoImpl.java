/*
 * 

 * 
 */
package com.jinchuangan.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import com.jinchuangan.dao.ShippingDao;
import com.jinchuangan.entity.Shipping;

import org.springframework.stereotype.Repository;

/**
 * Dao - 发货单
 * 
 * 
 * @version 1.0
 */
@Repository("shippingDaoImpl")
public class ShippingDaoImpl extends BaseDaoImpl<Shipping, Long> implements ShippingDao {

	public Shipping findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select shipping from Shipping shipping where lower(shipping.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Shipping.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}