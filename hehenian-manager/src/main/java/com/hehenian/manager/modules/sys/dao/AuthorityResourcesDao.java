package com.hehenian.manager.modules.sys.dao;

import java.util.List;

import com.hehenian.manager.modules.sys.model.Authorities;
import com.hehenian.manager.modules.sys.model.AuthorityResources;

public interface AuthorityResourcesDao {

	/**
	 * 检查是否已经存在
	 * @param ar
	 * @return
	 */
	public boolean checkRelationExist(AuthorityResources ar);
	
	/**
	 * 新增关系
	 * @param ar
	 * @return
	 */
	public int addRelation(AuthorityResources ar);
	
	/**
	 * 删除关系
	 * @param id
	 * @return
	 */
	public int deleteRelationById(AuthorityResources ar);
	
	/**
	 * 获取权限列表
	 * @return
	 */
	public List<Authorities> getAuthorities();
}
