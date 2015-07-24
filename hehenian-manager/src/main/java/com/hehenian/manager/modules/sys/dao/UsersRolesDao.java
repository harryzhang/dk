package com.hehenian.manager.modules.sys.dao;

import com.hehenian.manager.modules.sys.model.UsersRoles;

public interface UsersRolesDao {

	/**
	 * 新增用户角色关系
	 * @param ur
	 * @return
	 */
	public int addUsersRoles(UsersRoles ur);
	
	/**
	 * 删除用户角色关系
	 * @param ur
	 * @return
	 */
	public int deleteUsersRoles(UsersRoles ur);
	
	/**
	 * 验证用户角色关系是否已经存在
	 * @param ur
	 * @return
	 */
	public boolean hasExists(UsersRoles ur);
}
