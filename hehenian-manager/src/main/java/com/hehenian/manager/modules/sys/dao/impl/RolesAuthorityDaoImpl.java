package com.hehenian.manager.modules.sys.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hehenian.manager.commons.PageMapper;
import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.commons.SQLHelpers;
import com.hehenian.manager.modules.sys.dao.RolesAuthorityDao;
import com.hehenian.manager.modules.sys.model.Authorities;
import com.hehenian.manager.modules.sys.model.RolesAuthority;

@Repository("rolesAuthorityDao")
public class RolesAuthorityDaoImpl implements RolesAuthorityDao {

	@Resource
	protected NamedParameterJdbcTemplate userNameJdbcTemplate;
	
	@Resource
	ProxoolDataSource hhn_userDataSource;
	
	@Override
	public int addRolesAuthority(RolesAuthority ra) {
		String sql="insert into RolesAuthority(roleId,authorityId,enabled) values(?,?,?)";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, new Object[]{ra.getRoleId(),ra.getAuthorityId(),ra.getEnabled()});
	}

	@Override
	public int deleteRolesAuthority(RolesAuthority ra) {
		String sql="delete from RolesAuthority where roleId=? and authorityId=?";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, new Object[]{ra.getRoleId(),ra.getAuthorityId()});
	}

	@Override
	public boolean isExists(RolesAuthority ra) {
		String sql="select count(1) from RolesAuthority where roleId=? and authorityId=?";
		int count=userNameJdbcTemplate.getJdbcOperations().queryForInt(sql, new Object[]{ra.getRoleId(),ra.getAuthorityId()});
		return count>0;
	}

	@Override
	public Pagination<Authorities> getAuthoritiesInRoles(
			Pagination<Authorities> page, int roleId) {
		StringBuilder sql=new StringBuilder("select SQL_CALC_FOUND_ROWS a.name,a.authDesc,a.id FROM Authorities a ");
		sql.append("INNER JOIN RolesAuthority ra ON a.id=ra.authorityId WHERE ra.roleId=?");
		List<Object> args=new ArrayList<Object>();
		args.add(roleId);
		return SQLHelpers.getRowSize(sql.toString(), hhn_userDataSource, args.toArray(), page,getPageMapper());
	}

	@Override
	public Pagination<Authorities> getAuthoritiesNotInRoles(
			Pagination<Authorities> page, int roleId) {
		StringBuilder sql=new StringBuilder("select SQL_CALC_FOUND_ROWS a.name NAME,a.authDesc AUTHDESC,a.id ID FROM Authorities a ");
		sql.append(" where a.id not in (select auth.id from Authorities auth ");
		sql.append("INNER JOIN RolesAuthority ra ON auth.id=ra.authorityId WHERE ra.roleId=?)");
		List<Object> args=new ArrayList<Object>();
		args.add(roleId);
		return SQLHelpers.getRowSize(sql.toString(), hhn_userDataSource, args.toArray(), page, getPageMapper());
	}
	
	private PageMapper<Authorities> getPageMapper(){
		return new PageMapper<Authorities>() {

			@Override
			public Authorities toCustomizedBean(ResultSet rs) {
				Authorities auth=new Authorities();
				try {
					auth.setId(rs.getInt("ID"));
					auth.setName(rs.getString("NAME"));
					auth.setAuthDesc(rs.getString("AUTHDESC"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return auth;
			}
		};
	}

}
