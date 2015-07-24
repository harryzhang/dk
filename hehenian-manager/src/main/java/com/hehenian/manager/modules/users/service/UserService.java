package com.hehenian.manager.modules.users.service;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.RolesAuthority;
import com.hehenian.manager.modules.sys.model.UsersRoles;
import com.hehenian.manager.modules.users.model.Managers;
import com.hehenian.manager.modules.users.model.Roles;

public interface UserService {

	/**
	 * 新增一个用户
	 * @param user
	 * @return
	 */
	public int addUsers(Managers user);
	
	/**
	 * 获取用户列表
	 * @param pagination
	 * @return
	 */
	public Pagination<Managers> getManagersByPage(Pagination<Managers> pagination,String username);
	
	/**
	 * 根据id获取用户
	 * @param userId
	 * @return
	 */
	public Managers getUserById(int userId);
	
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	public int deleteOneUser(int userId);
	
	/**
	 * 获取一个角色中的用户（或者不在角色中的用户）
	 * @param page
	 * @param roleId
	 * @param flag true表示在角色中的用户，false表示不在角色中的用户
	 * @return
	 */
	public Pagination<Managers> getManagersInOrNotInRoles(Pagination<Managers> page,int roleId,boolean flag);
	
	
	/**
	 * 获取角色列表
	 * @param pagination
	 * @param roleName
	 * @return
	 */
	public Pagination<Roles> getRolesByPage(Pagination<Roles> pagination,String roleName);
	
	
	/**
	 * 根据id获取角色
	 * @param roleId
	 * @return
	 */
	public Roles getOneRoleById(int roleId);
	
	/**
	 * 删除一个角色
	 * @param roleId
	 * @return
	 */
	public int deleteOneRole(int roleId);
	
	/**
	 * 新增或者一个角色
	 * @param role
	 * @return
	 */
	public int updateOneRole(Roles role);
	
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
	 * 获取在权限下的角色
	 * @param page
	 * @param authId
	 * @return
	 */
	public Pagination<Roles> getRolesInOrNotInAuthorities(Pagination<Roles> page,int authId,boolean inOrNot);
	
	/**
	 * 新增角色权限关系
	 * @param ra
	 * @return
	 */
	public int addRolesAuthority(RolesAuthority ra);
	
	/**
	 * 删除角色和权限的关系
	 * @param ra
	 * @return
	 */
	public int deleteRolesAuthority(RolesAuthority ra);
	
	
	/**
	 * 修改密码
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	public int resetCurrentUserPwd(int userId,String oldPassword,String newPassword);
}
