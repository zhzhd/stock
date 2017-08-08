/*
 * 

 * 
 */
package com.jinchuangan.dao.impl;

import com.jinchuangan.dao.RoleDao;
import com.jinchuangan.entity.Role;

import org.springframework.stereotype.Repository;

/**
 * Dao - 角色
 * 
 * 
 * @version 1.0
 */
@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

}