package com.hehenian.manager.modules.users.dao;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.users.model.Managers;

public interface UserDao {

	/**
	 * 获取用户详细信息
	 * @param userName
	 * @return
	 */
	public Managers getUserDetailsByUserName(String userName);
	
	/**
	 * 新增一个用户
	 * @param user
	 * @return
	 */
	public int addUser(Managers user);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateUser(Managers user);
	
	
	/**
	 * 
	 * @param pagination
	 * @param username
	 * @return
	 */
	public Pagination<Managers> getUsersByPage(Pagination<Managers> pagination,String username);
	
	/**
	 * 根据id获取用户信息
	 * @param userId
	 * @return
	 */
	public Managers getUserById(int userId);
	
	/**
	 * 根据id删除用户
	 * @param userId
	 * @return
	 */
	public int deleteUser(int userId);
	
	
	/**
	 * 获取角色下的人员
	 * @param page
	 * @param roleId
	 * @return
	 */
	public Pagination<Managers> getUsersInRoles(Pagination<Managers> page,int roleId);
	
	
	/**
	 * 
	 * @param page
	 * @param roleId
	 * @return
	 */
	public Pagination<Managers> getUsersNotInRoles(Pagination<Managers> page,int roleId);
	
	
}
