package com.hehenian.manager.modules.sys.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.commons.PaginationCallback;
import com.hehenian.manager.commons.SQLHelpers;
import com.hehenian.manager.modules.sys.dao.ResourcesDao;
import com.hehenian.manager.modules.sys.model.Resources;

@Repository("resourcesDao")
public class ResourcesDaoImpl implements ResourcesDao {

	@Resource
	protected NamedParameterJdbcTemplate userNameJdbcTemplate;
	
	@Resource
	ProxoolDataSource hhn_userDataSource;
	
	@Override
	public int addResources(Resources resource) {
		String sql = "insert into Resources(name,resourceDesc,resourceStr,enabled,issys,module,icon,resouceType) values(?,?,?,?,?,?,?,?)";
		return userNameJdbcTemplate.getJdbcOperations().update(sql,
				resource.getName(), resource.getResourceDesc(),
				resource.getResourceStr(), resource.getEnabled(),
				resource.getIssys(), resource.getModule(), resource.getIcon(),resource.getResouceType().toString());
	}

	@Override
	public int updateResources(Resources resource) {
		StringBuilder sql = new StringBuilder("update Resources set ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(resource.getName())) {
			sql.append("name=?,");
			args.add(resource.getName());
		}
		if (StringUtils.isNotBlank(resource.getResourceDesc())) {
			sql.append("resourceDesc=?,");
			args.add(resource.getResourceDesc());
		}
		if(StringUtils.isNotBlank(resource.getResourceStr())){
			sql.append("resourceStr=?,");
			args.add(resource.getResourceStr());
		}
		if(StringUtils.isNotBlank(resource.getModule())){
			sql.append("module=?,");
			args.add(resource.getModule());
		}
		if(StringUtils.isNotBlank(resource.getIcon())){
			sql.append("icon=?,");
			args.add(resource.getIcon());
		}
		
		//资源类型
		if(StringUtils.isNotBlank(resource.getResouceType().toString())){
			sql.append("resouceType=?,");
			args.add(resource.getResouceType().toString());
		}
		sql.setLength(sql.length()-1);
		sql.append(" where id=").append(resource.getId());
		return userNameJdbcTemplate.getJdbcOperations().update(sql.toString(), args.toArray());
	}

	@Override
	public int disabledResource(int resourceId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Pagination<Resources> getResourcesInModule(
			Pagination<Resources> page, String resourceName) {
		StringBuilder sql = new StringBuilder(
				"select id,name,resourceDesc,resourceStr,enabled,module,icon from Resources where 1=1");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(resourceName)) {
			sql.append(" and name like '%?%' or resourceStr like '%?%'");
			args.add(resourceName);
			args.add(resourceName);
		}
		return SQLHelpers.getRowSize(sql.toString(), userNameJdbcTemplate,
				args.toArray(), page, new PaginationCallback<Resources>() {

					@Override
					public Resources toCustomizedBean(Map<String, Object> data) {
						Resources r = new Resources();
						r.setId(Integer.parseInt(data.get("id").toString()));
						r.setName(data.get("name").toString());
						r.setResourceStr(data.get("resourceStr").toString());
						r.setResourceDesc(data.get("resourceDesc").toString());
						r.setEnabled(Boolean.parseBoolean(data.get("enabled")
								.toString()));
						r.setModule(data.get("module").toString());
						Object icon = data.get("icon");
						r.setIcon(icon != null ? icon.toString() : "");
						return r;
					}
				});
	}

	@Override
	public int deleteOneResource(int resourceId) {
		String sql = "delete from Resources where id=?";
		return userNameJdbcTemplate.getJdbcOperations().update(sql,
				resourceId);
	}

	@Override
	public Resources getOneResource(Integer id) {
		String sql = "select id,name,resourceDesc,resourceStr,enabled,module,icon from Resources where id=?";
		return userNameJdbcTemplate.getJdbcOperations().queryForObject(sql,
				new Object[] { id }, new RowMapper<Resources>() {

					@Override
					public Resources mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Resources r = new Resources();
						r.setId(rs.getInt("id"));
						r.setName(rs.getString("name"));
						r.setResourceDesc(rs.getString("resourceDesc"));
						r.setResourceStr(rs.getString("resourceStr"));
						r.setModule(rs.getString("module"));
						r.setIcon(rs.getString("icon"));
						return r;
					}
				});
	}

	@Override
	public Pagination<Resources> getResourcesInInAuthority(
			Pagination<Resources> page, int authorityId) {
		StringBuilder sql=new StringBuilder("SELECT r.id ID,r.name NAME,r.resourceStr RESOURCESTR,r.module MODULE FROM Resources r,AuthorityResources ar ");
		sql.append(" WHERE r.id=ar.resourceId AND ar.authorityId");
		sql.append(" = ").append(authorityId);
		return SQLHelpers.getRowSize(sql.toString(), userNameJdbcTemplate, new Object[]{}, page, new ResourcePagination());
	}

	@Override
	public Pagination<Resources> getResourceNotInAuthority(
			Pagination<Resources> page, int authorityId) {
		StringBuilder sql=new StringBuilder("SELECT r.id ID,r.name NAME,r.resourceStr RESOURCESTR,r.module MODULE FROM Resources r WHERE r.id NOT IN (SELECT a.id FROM Resources a,AuthorityResources ar WHERE a.id=ar.resourceId AND ar.authorityId=?)");
		return SQLHelpers.getRowSize(sql.toString(), userNameJdbcTemplate, new Object[]{authorityId}, page, new ResourcePagination());
	}
	
	class ResourcePagination extends PaginationCallback<Resources> {

		@Override
		public Resources toCustomizedBean(Map<String, Object> data) {
			Resources r=new Resources();
			r.setId(Integer.parseInt(data.get("ID").toString()));
			r.setName(data.get("NAME").toString());
			r.setResourceStr(data.get("RESOURCESTR").toString());
			r.setModule(data.get("MODULE").toString());
			return r;
		}
	}

}
