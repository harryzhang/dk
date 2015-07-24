package com.hehenian.manager.modules.users.dao;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.users.model.Roles;

public interface RolesDao {

	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	public int addRole(Roles role);
	
	/**
	 * 修改角色 
	 * @param role
	 * @return
	 */
	public int updateRole(Roles role);
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	public int deleteRole(int id);
	
	
	/**
	 * 获取角色列表
	 * @param page
	 * @param roleName
	 * @return
	 */
	public Pagination<Roles> getRolesByPage(Pagination<Roles> page,String roleName);
	
	
	/**
	 * 根据id获取角色
	 * @param id
	 * @return
	 */
	public Roles getOneRoleById(int id);
	
	
	/**
	 * 获取权限下的角色
	 * @param page
	 * @param authId
	 * @return
	 */
	public Pagination<Roles> getRolesInAuthority(Pagination<Roles> page,int authId);
	
	/**
	 * 不在权限下的角色
	 * @param page
	 * @param authId
	 * @return
	 */
	public Pagination<Roles> getRolesNotInAuthority(Pagination<Roles> page,int authId);
}
