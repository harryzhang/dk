package com.hehenian.manager.modules.sys.dao;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.Authorities;

public interface AuthorityDao {

	
	
	/**
	 * 获取用户的权限
	 * @param userId
	 * @return
	 */
	public List<GrantedAuthority> getGrantedAuthority(int userId);
	
	/**
	 * 
	 * @param authority
	 * @return
	 */
	public int addAuthority(Authorities authority);
	
	/**
	 * 修改权限
	 * @param authority
	 * @return
	 */
	public int updateAuthority(Authorities authority);
	
	/**
	 * 删除权限
	 * @param authorityId
	 * @return
	 */
	public int deleteAuthority(int authorityId);
	
	/**
	 * 分页获取权限
	 * @param authority
	 * @param name
	 * @return
	 */
	public Pagination<Authorities> getAuthorities(Pagination<Authorities> authority,String name);
	
	/**
	 * 根据id获取权限
	 * @param id
	 * @return
	 */
	public Authorities getAuthority(int id);
}
