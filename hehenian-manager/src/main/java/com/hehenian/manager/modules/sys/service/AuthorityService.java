package com.hehenian.manager.modules.sys.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.Authorities;
import com.hehenian.manager.modules.sys.model.AuthorityResources;
import com.hehenian.manager.modules.sys.model.Resources;

public interface AuthorityService {

	
	/**
	 * 新增权限
	 * @param authority
	 * @return
	 */
	public int saveAuthority(Authorities authority);
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public Pagination<Authorities> getAuthority(Pagination<Authorities> list,String name);
	
	/**
	 * 获取单个权限信息
	 * @param id
	 * @return
	 */
	public Authorities getAuthorities(int id);
	
	/**
	 * 删除一个权限
	 * @param id
	 * @return
	 */
	public int deleteOneAuthority(int id);
	
	
	/**
	 * 获取在该权限下或者不在权限下的菜单
	 * @param list
	 * @param inOrNot true表示已经在权限下
	 * @return
	 */
	public Pagination<Resources> getResourcesInOrNotAuthority(Pagination<Resources> list,int authorityId,boolean inOrNot);
	
	
	/**
	 * 新增关系
	 * @param ar
	 * @return
	 */
	public int addAuthorityResource(AuthorityResources ar);
	
	/**
	 * 删除关系
	 * @param id
	 * @return
	 */
	public int deleteAuthorityResource(AuthorityResources ar);
	
	/**
	 * 获取资源集合
	 * @return
	 */
	public Map<String, Collection<ConfigAttribute>> getResourceMap(); 
	
	
	/**
	 * 
	 * @param list
	 * @param roleId
	 * @return
	 */
	public Pagination<Authorities> getInOrNotAuthoritiesByRoleId(Pagination<Authorities> list,int roleId,boolean inRoleOrNot);
	
	
}
