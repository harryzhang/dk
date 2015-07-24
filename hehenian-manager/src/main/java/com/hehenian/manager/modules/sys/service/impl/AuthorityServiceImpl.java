package com.hehenian.manager.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import com.hehenian.manager.commons.Constants;
import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.dao.AuthorityDao;
import com.hehenian.manager.modules.sys.dao.AuthorityResourcesDao;
import com.hehenian.manager.modules.sys.dao.ResourcesDao;
import com.hehenian.manager.modules.sys.dao.RolesAuthorityDao;
import com.hehenian.manager.modules.sys.model.Authorities;
import com.hehenian.manager.modules.sys.model.AuthorityResources;
import com.hehenian.manager.modules.sys.model.Resources;
import com.hehenian.manager.modules.sys.service.AuthorityService;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	protected AuthorityDao authorityDao;

	@Autowired
	protected ResourcesDao resourcesDao;

	@Autowired
	protected AuthorityResourcesDao authorityResourcesDao;
	
	@Autowired
	protected RolesAuthorityDao rolesAuthorityDao;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	@Override
	public int saveAuthority(Authorities authority) {
		int ret = -1;
		if (authority.getId() != null) {
			ret = authorityDao.updateAuthority(authority);
		} else {
			ret = authorityDao.addAuthority(authority);
		}
		return ret == 1 ? Constants.SUCCESS : Constants.FAIL;
	}

	@Override
	public Pagination<Authorities> getAuthority(Pagination<Authorities> list,
			String name) {
		return authorityDao.getAuthorities(list, name);
	}

	@Override
	public Authorities getAuthorities(int id) {
		return authorityDao.getAuthority(id);
	}

	@Override
	public int deleteOneAuthority(int id) {
		int ret = authorityDao.deleteAuthority(id);
		return ret == 1 ? Constants.SUCCESS : Constants.FAIL;
	}

	@Override
	public Pagination<Resources> getResourcesInOrNotAuthority(
			Pagination<Resources> list, int authorityId, boolean inOrNot) {
		return inOrNot ? resourcesDao.getResourcesInInAuthority(list,
				authorityId) : resourcesDao.getResourceNotInAuthority(list,
				authorityId);
	}

	@Override
	public int addAuthorityResource(AuthorityResources ar) {
		// int ret=1;
		int ret = 0;
		// 数据库没存在关系才加，已经存在了不加
		if (!authorityResourcesDao.checkRelationExist(ar)) {
			ret = authorityResourcesDao.addRelation(ar);
			// 加入权限访问列表中
			Resources res = resourcesDao.getOneResource(ar.getResourceId());
			Authorities auth = authorityDao.getAuthority(ar.getAuthorityId());
			String url = res.getResourceStr();
			/*
			 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中 。
			 */
			ConfigAttribute ca = new SecurityConfig(auth.getName());
			if (resourceMap.containsKey(url)) {

				Collection<ConfigAttribute> value = resourceMap.get(url);
				value.add(ca);
				resourceMap.put(url, value);
			} else {
				Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
				atts.add(ca);
				resourceMap.put(url, atts);
			}
		}
		return ret == 1 ? Constants.SUCCESS : Constants.FAIL;
	}

	@Override
	public int deleteAuthorityResource(AuthorityResources ar) {
		int ret = authorityResourcesDao.deleteRelationById(ar);
		return ret == 1 ? Constants.SUCCESS : Constants.FAIL;
	}

	@Override
	public Map<String, Collection<ConfigAttribute>> getResourceMap() {
		/*
		 * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
		 */
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Authorities> authList = authorityResourcesDao.getAuthorities();
			for (Authorities auth : authList) {
				ConfigAttribute ca = new SecurityConfig(auth.getName());

				for (Resources res : auth.getResources()) {
					String url = res.getResourceStr();

					/*
					 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中
					 * 。
					 */
					if (resourceMap.containsKey(url)) {

						Collection<ConfigAttribute> value = resourceMap
								.get(url);
						value.add(ca);
						resourceMap.put(url, value);
					} else {
						Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
						atts.add(ca);
						resourceMap.put(url, atts);
					}
				}
			}
		}
		return resourceMap;
	}

	@Override
	public Pagination<Authorities> getInOrNotAuthoritiesByRoleId(
			Pagination<Authorities> list, int roleId, boolean inRoleOrNot) {
		return inRoleOrNot?rolesAuthorityDao.getAuthoritiesInRoles(list, roleId):rolesAuthorityDao.getAuthoritiesNotInRoles(list, roleId);
	}

}
