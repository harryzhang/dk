package com.hehenian.manager.modules.sys.dao.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hehenian.manager.modules.sys.dao.UsersRolesDao;
import com.hehenian.manager.modules.sys.model.UsersRoles;

@Repository("usersRolesDao")
public class UsersRolesDaoImpl implements UsersRolesDao {

	@Resource
	protected NamedParameterJdbcTemplate userNameJdbcTemplate;
	
	@Override
	public int addUsersRoles(UsersRoles ur) {
		String sql="insert into UsersRoles(userId,roleId,enabled) values(?,?,?)";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, new Object[]{ur.getUserId(),ur.getRoleId(),ur.getEnabled()});
	}

	@Override
	public int deleteUsersRoles(UsersRoles ur) {
		String sql="delete from UsersRoles where userId=? and roleId=?";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, new Object[]{ur.getUserId(),ur.getRoleId()});
	}

	@Override
	public boolean hasExists(UsersRoles ur) {
		String sql="select count(1) from UsersRoles where userId=? and roleId=?";
		int count=userNameJdbcTemplate.getJdbcOperations().queryForInt(sql, new Object[]{ur.getUserId(),ur.getRoleId()});
		return count>0;
	}

}
