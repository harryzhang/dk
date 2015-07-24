package com.hehenian.manager.modules.sys.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hehenian.manager.modules.sys.dao.AuthorityResourcesDao;
import com.hehenian.manager.modules.sys.model.Authorities;
import com.hehenian.manager.modules.sys.model.AuthorityResources;
import com.hehenian.manager.modules.sys.model.Resources;

@Repository("authorityResourcesDao")
public class AuthorityResourcesDaoImpl implements AuthorityResourcesDao {

	@Resource
	protected NamedParameterJdbcTemplate userNameJdbcTemplate;
	
	@Resource
	ProxoolDataSource hhn_userDataSource;
	
	@Override
	public boolean checkRelationExist(AuthorityResources ar) {
		String sql="select count(1) from AuthorityResources where authorityId=? and resourceId =? ";
		List<Object> args=new ArrayList<Object>();
		args.add(ar.getAuthorityId());
		args.add(ar.getResourceId());
		int count=userNameJdbcTemplate.getJdbcOperations().queryForInt(sql, args.toArray());
		return count==1;
	}

	@Override
	public int addRelation(AuthorityResources ar) {
		String sql="insert into AuthorityResources(authorityId,resourceId,enabled) values(?,?,?)";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, ar.getAuthorityId(),ar.getResourceId(),ar.getEnabled());
	}

	@Override
	public int deleteRelationById(AuthorityResources ar) {
		String sql="delete from AuthorityResources where authorityId=? and resourceId=?";
		return userNameJdbcTemplate.getJdbcOperations().update(sql,  ar.getAuthorityId(),ar.getResourceId());
	}

	
	@Override
	public List<Authorities> getAuthorities() {
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT r.resourceStr RESOURCESTR,a.id ID,a.name NAME,r.id RESOURCEID,r.name RESOURCENAME FROM Authorities a INNER JOIN AuthorityResources ar ON a.id=ar.authorityId ");
		sql.append(" INNER JOIN Resources r ON r.id=ar.resourceId WHERE a.enabled=1");
		List<Map<String,Object>> datas=userNameJdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		Map<Integer,Authorities> tmpMap=new HashMap<Integer,Authorities>();
		Authorities auth=null;
		/**
		 * 整理成相应的权限列表
		 */
		for(Map<String,Object> data:datas){
			Integer authId=Integer.parseInt(data.get("ID").toString());
			String resourceStr=data.get("RESOURCESTR").toString();
			int resourceId=Integer.parseInt(data.get("RESOURCEID").toString());
			String resourceName=data.get("RESOURCENAME").toString();
			auth=tmpMap.get(authId);
			if(auth==null){
				String authorityName=data.get("NAME").toString();
				auth=new Authorities();
				auth.setId(authId);
				auth.setName(authorityName);
				auth.setResources(new ArrayList<Resources>());
				tmpMap.put(authId, auth);
			}
			Resources resource=new Resources();
			resource.setId(resourceId);
			resource.setName(resourceName);
			resource.setResourceStr(resourceStr);
			auth.getResources().add(resource);
		}
		return new ArrayList<Authorities>(tmpMap.values());
	}
}
