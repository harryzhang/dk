package com.hehenian.manager.modules.users.dao.impl;

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
import com.hehenian.manager.modules.users.dao.RolesDao;
import com.hehenian.manager.modules.users.model.Roles;

@Repository("rolesDao")
public class RolesDaoImpl implements RolesDao {

	@Resource
	protected NamedParameterJdbcTemplate userNameJdbcTemplate;
	
	@Override
	public int addRole(Roles role) {
		String sql="insert into Roles(name,roleDesc,enabled) values(?,?,1)";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, role.getName(),role.getRoleDesc());
	}

	@Override
	public int updateRole(Roles role) {
		StringBuilder sql=new StringBuilder("update Roles set ");
		List<Object> args=new ArrayList<Object>();
		if(StringUtils.isNotBlank(role.getName())){
			sql.append(" name=?,");
			args.add(role.getName());
		}
		if(StringUtils.isNotBlank(role.getRoleDesc())){
			sql.append(" roleDesc=?,");
			args.add(role.getRoleDesc());
		}
		sql.setLength(sql.length()-1);
		sql.append(" where id=").append(role.getId());
		return userNameJdbcTemplate.getJdbcOperations().update(sql.toString(), args.toArray());
	}

	@Override
	public int deleteRole(int id) {
		String sql="delete from Roles where id="+id;
		return userNameJdbcTemplate.getJdbcOperations().update(sql);
	}

	@Override
	public Pagination<Roles> getRolesByPage(Pagination<Roles> page,
			String roleName) {
		StringBuilder sql=new StringBuilder("select id,name,roleDesc,enabled from Roles where 1=1 ");
		List<Object> args=new ArrayList<Object>();
		if(StringUtils.isNotBlank(roleName)){
			sql.append(" and name like '%?%'");
			args.add(roleName);
		}
		return SQLHelpers.getRowSize(sql.toString(), userNameJdbcTemplate, args.toArray(), page, new PaginationCallback<Roles>() {

			@Override
			public Roles toCustomizedBean(Map<String, Object> data) {
				Roles r=new Roles();
				r.setId(Integer.parseInt(data.get("id").toString()));
				r.setName(data.get("name").toString());
				Object roleDesc=data.get("roleDesc");
				r.setRoleDesc(roleDesc!=null?roleDesc.toString():"");
				Object enable=data.get("enabled");
				r.setEnabled(enable!=null?Boolean.parseBoolean(enable.toString()):true);
				return r;
			}
		});
	}

	@Override
	public Roles getOneRoleById(int id) {
		String sql="select id,name,roleDesc,enabled from Roles where id=?";
		return userNameJdbcTemplate.getJdbcOperations().queryForObject(sql,
				new Object[] { id }, new RowMapper<Roles>() {

			@Override
			public Roles mapRow(ResultSet rs, int arg1)
					throws SQLException {
				Roles r=new Roles();
				r.setId(rs.getInt("id"));
				r.setName(rs.getString("name"));
				r.setRoleDesc(rs.getString("roleDesc"));
				return r;
			}
		});
	}

	@Override
	public Pagination<Roles> getRolesInAuthority(Pagination<Roles> page,
			int authId) {
		String sql="select r.id ID,r.name NAME,r.roleDesc ROLEDESC from Roles r,RolesAuthority ra where r.id=ra.roleId and ra.authorityId=?";
		return SQLHelpers.getRowSize(sql, userNameJdbcTemplate, new Object[]{authId}, page, new RolesPage());
	}

	@Override
	public Pagination<Roles> getRolesNotInAuthority(Pagination<Roles> page,
			int authId) {
		String sql="select r.id ID,r.name NAME,r.roleDesc ROLEDESC from Roles r where r.id not in (select a.id from Roles a,RolesAuthority ra where a.id=ra.roleId and ra.authorityId=?)";
		return SQLHelpers.getRowSize(sql, userNameJdbcTemplate, new Object[]{authId}, page, new RolesPage());
	}
	
	class RolesPage extends PaginationCallback<Roles>{

		@Override
		public Roles toCustomizedBean(Map<String, Object> data) {
			Roles r=new Roles();
			r.setId(Integer.parseInt(data.get("ID").toString()));
			r.setName(data.get("NAME").toString());
			r.setRoleDesc(data.get("ROLEDESC").toString());
			return r;
		}
		
	}

}
