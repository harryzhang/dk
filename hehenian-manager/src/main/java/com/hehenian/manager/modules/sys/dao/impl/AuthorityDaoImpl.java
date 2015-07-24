package com.hehenian.manager.modules.sys.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import com.hehenian.manager.commons.PageMapper;
import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.commons.SQLHelpers;
import com.hehenian.manager.modules.sys.dao.AuthorityDao;
import com.hehenian.manager.modules.sys.model.Authorities;

@Repository("authorityDao")
public class AuthorityDaoImpl implements AuthorityDao{

	@Resource
	protected NamedParameterJdbcTemplate userNameJdbcTemplate;
	
	@Resource
	ProxoolDataSource hhn_userDataSource;

	@Override
	public List<GrantedAuthority> getGrantedAuthority(int userId) {
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT a.name NAME FROM Managers m INNER JOIN UsersRoles u ");
		sql.append("ON m.id=u.userId INNER JOIN Roles r ON u.roleId=r.id INNER JOIN RolesAuthority ra ");
		sql.append("ON r.id=ra.roleId INNER JOIN Authorities a ON ra.authorityId=a.id WHERE m.id=? ");
		List<Map<String,Object>> datas=userNameJdbcTemplate.getJdbcOperations().queryForList(sql.toString(), userId);
		List<GrantedAuthority> results=new ArrayList<GrantedAuthority>();
		for(Map<String,Object> data:datas){
			GrantedAuthority ga=new Authorities(data.get("NAME").toString());
			results.add(ga);
		}
		return results;
	}


	/**
	 * 新增权限
	 */
	@Override
	public int addAuthority(Authorities authority) {
		String sql="insert into Authorities (name,authDesc,enabled,issys,module) values(?,?,?,?,?)";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, authority.getName(),authority.getAuthDesc(),1,authority.getIssys(),authority.getModule());
	}


	/**
	 * 修改权限
	 */
	@Override
	public int updateAuthority(Authorities authority) {
		StringBuilder sql=new StringBuilder("update Authorities set ");
		List<Object> objs=new ArrayList<Object>();
		if(StringUtils.isNotBlank(authority.getName())){
			sql.append("name=? ,");
			objs.add(authority.getName());
		}
		if(StringUtils.isNotBlank(authority.getAuthDesc())){
			sql.append("authDesc=? ,");
			objs.add(authority.getAuthDesc());
		}
		sql.setLength(sql.length()-1);
		sql.append(" where id=").append(authority.getId());
		return userNameJdbcTemplate.getJdbcOperations().update(sql.toString(), objs.toArray());
	}


	/**
	 * 删除权限
	 */
	@Override
	public int deleteAuthority(int authorityId) {
		String sql="delete from Authorities where id=?";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, authorityId);
	}


	/**
	 * 根据条件获取权限（条件目前还没有）
	 */
	@Override
	public Pagination<Authorities> getAuthorities(
			Pagination<Authorities> page,String name) {
		StringBuilder sql=new StringBuilder("select SQL_CALC_FOUND_ROWS id,name,authDesc,enabled,issys from Authorities where 1=1 ");
		List<Object>args=new ArrayList<Object>();
		if(StringUtils.isNotBlank(name)){
			sql.append(" and name like '%?%'");
			args.add(name);
		}
		return SQLHelpers.getRowSize(sql.toString(), hhn_userDataSource, args.toArray(), page,new PageMapper<Authorities>() {

			@Override
			public Authorities toCustomizedBean(ResultSet rs) {
				Authorities auth=new Authorities();
				try {
					auth.setId(rs.getInt("id"));
					auth.setName(rs.getString("name"));
					auth.setAuthDesc(rs.getString("authDesc"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return auth;
			}
			
		});
	}


	@Override
	public Authorities getAuthority(int id) {
		String sql="select id,name,authDesc,enabled,issys from Authorities where id=?";
		return userNameJdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{id}, new RowMapper<Authorities>(){

			@Override
			public Authorities mapRow(ResultSet rs, int arg1)
					throws SQLException {
				Authorities auth=new Authorities();
				auth.setId(rs.getInt("id"));
				auth.setName(rs.getString("name"));
				auth.setAuthDesc(rs.getString("authDesc"));
				auth.setEnabled(rs.getBoolean("enabled"));
				return auth;
			}
			
		});
	}

}
