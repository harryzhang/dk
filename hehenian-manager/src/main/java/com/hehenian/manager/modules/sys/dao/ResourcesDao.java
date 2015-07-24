package com.hehenian.manager.modules.sys.dao;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.Resources;

public interface ResourcesDao {

	/**
	 * 新增一个菜单项
	 * @param resource
	 * @return
	 */
	public int addResources(Resources resource);
	
	/**
	 * 修改菜单项
	 * @param resource
	 * @return
	 */
	public int updateResources(Resources resource);
	
	/**
	 * 删除一个菜单项
	 * @param resourceId
	 * @return
	 */
	public int deleteOneResource(int resourceId);
	
	/**
	 * 禁止菜单
	 * @param resourceId
	 * @return
	 */
	public int disabledResource(int resourceId);
	
	/**
	 * 获取一个模块下的菜单
	 * @param page
	 * @param moduleId
	 * @return
	 */
	public Pagination<Resources> getResourcesInModule(Pagination<Resources> page,String resourceName);
	
	/**
	 * 获取一个菜单
	 * @param id
	 * @return
	 */
	public Resources getOneResource(Integer id);
	
	/**
	 * 在权限下的菜单
	 * @param page
	 * @param authorityId
	 * @return
	 */
	public Pagination<Resources> getResourcesInInAuthority(Pagination<Resources> page,int authorityId);
	
	/**
	 * 不在权限下的菜单
	 * @param page
	 * @param authorityId
	 * @return
	 */
	public Pagination<Resources> getResourceNotInAuthority(Pagination<Resources> page,int authorityId);
}
