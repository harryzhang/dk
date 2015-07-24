package com.hehenian.manager.modules.sys.dao;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.Authorities;
import com.hehenian.manager.modules.sys.model.RolesAuthority;

public interface RolesAuthorityDao {

	/**
	 * 新增角色权限关系
	 * @param ra
	 * @return
	 */
	public int addRolesAuthority(RolesAuthority ra);
	
	/**
	 * 删除角色权限关系
	 * @param ra
	 * @return
	 */
	public int deleteRolesAuthority(RolesAuthority ra);
	
	/**
	 * 验证是否存在
	 * @param ra
	 * @return
	 */
	public boolean isExists(RolesAuthority ra);
	
	/**
	 * 获取角色下所拥有的权限
	 * @param page
	 * @param roleId
	 * @return
	 */
	public Pagination<Authorities> getAuthoritiesInRoles(Pagination<Authorities> page,int roleId);
	
	
	/**
	 * 获取角色下没有获得的权限
	 * @param page
	 * @param roleId
	 * @return
	 */
	public Pagination<Authorities> getAuthoritiesNotInRoles(Pagination<Authorities> page,int roleId);
}
