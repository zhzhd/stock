/*
 * 

 * 
 */
package com.jinchuangan.dao;

import com.jinchuangan.Page;
import com.jinchuangan.Pageable;
import com.jinchuangan.entity.Deposit;
import com.jinchuangan.entity.Member;

/**
 * Dao - 预存款
 * 
 * 
 * @version 1.0
 */
public interface DepositDao extends BaseDao<Deposit, Long> {

	/**
	 * 查找预存款分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 预存款分页
	 */
	Page<Deposit> findPage(Member member, Pageable pageable);

}