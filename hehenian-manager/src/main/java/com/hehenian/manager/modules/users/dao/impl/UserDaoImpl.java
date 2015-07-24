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

import com.hehenian.manager.commons.PageMapper;
import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.commons.SQLHelpers;
import com.hehenian.manager.modules.users.dao.UserDao;
import com.hehenian.manager.modules.users.model.Managers;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Resource
	protected NamedParameterJdbcTemplate userNameJdbcTemplate;
	
	@Resource
	ProxoolDataSource hhn_userDataSource;
	
	@Override
	public Managers getUserDetailsByUserName(String userName) {
		List<Map<String,Object>> datas=userNameJdbcTemplate.getJdbcOperations().queryForList("select id,username,nickname,password,salt,enabled,registerTime from Managers where username=?",userName);
		Managers user=new Managers();
		if(datas!=null && datas.size()>0){
			Map<String,Object> data=datas.get(0);
			boolean enabled=Boolean.getBoolean(data.get("enabled").toString());
			user.setId(Integer.parseInt(data.get("id").toString()));
			user.setUsername(data.get("username").toString());
			user.setNickname(data.get("nickname").toString());
			user.setPassword(data.get("password").toString());
			user.setSalt(data.get("salt").toString());
			user.setEnabled(enabled);
		}
		return user;
	}

	@Override
	public int addUser(Managers user) {
		String sql="insert into Managers (username,nickname,password,salt,enabled,registerTime,lastLoginTime) values(?,?,?,?,?,now(),now())";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, user.getUsername(),user.getNickname(),user.getPassword(),user.getSalt(),user.getEnabled());
	}

	/**
	 * 修改用户信息
	 */
	@Override
	public int updateUser(Managers user) {
		StringBuilder sql=new StringBuilder("update Managers set ");
		List<Object> args=new ArrayList<Object>();
		if(StringUtils.isNotBlank(user.getUsername())){
			sql.append(" username=?,");
			args.add(user.getUsername());
		}
		if(StringUtils.isNotBlank(user.getNickname())){
			sql.append(" nickname=?,");
			args.add(user.getNickname());
		}
		if(StringUtils.isNotBlank(user.getPassword())){
			sql.append(" password=?,");
			args.add(user.getPassword());
		}
		if(StringUtils.isNotBlank(user.getSalt())){
			sql.append(" salt=?,");
			args.add(user.getSalt());
		}
		sql.setLength(sql.length()-1);
		sql.append(" where id=").append(user.getId());
		return userNameJdbcTemplate.getJdbcOperations().update(sql.toString(), args.toArray());
	}

	@Override
	public Pagination<Managers> getUsersByPage(Pagination<Managers> pagination,
			String username) {
		StringBuilder sql=new StringBuilder("select SQL_CALC_FOUND_ROWS id,username,nickname,password,salt,enabled,registerTime from Managers where 1=1");
		List<Object> args=new ArrayList<Object>();
		if(StringUtils.isNotBlank(username)){
			sql.append(" and username like '%?%'");
			args.add(username);
		}
		Pagination<Managers> userPage=SQLHelpers.getRowSize(sql.toString(), hhn_userDataSource, args.toArray(), pagination, new PageMapper<Managers>() {

			@Override
			public Managers toCustomizedBean(ResultSet rs) {
				Managers user=new Managers();
				try {
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setNickname(rs.getString("nickname"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return user;
			}
		});
		return userPage;
	}

	@Override
	public Managers getUserById(int userId) {
		String sql="select id,username,nickname,password,salt,enabled,registerTime from Managers where id="+userId;
		return userNameJdbcTemplate.getJdbcOperations().queryForObject(sql, new RowMapper<Managers>(){

			@Override
			public Managers mapRow(ResultSet rs, int arg1)
					throws SQLException {
				Managers user=new Managers();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setNickname(rs.getString("nickname"));
				user.setPassword(rs.getString("password"));
				user.setSalt(rs.getString("salt"));
				user.setEnabled(rs.getBoolean("enabled"));
				user.setRegisterTime(rs.getDate("registerTime"));
				return user;
			}
		});
	}

	@Override
	public int deleteUser(int userId) {
		String sql="delete from Managers where id=?";
		return userNameJdbcTemplate.getJdbcOperations().update(sql, userId);
	}

	@Override
	public Pagination<Managers> getUsersInRoles(Pagination<Managers> page,
			int roleId) {
		String sql="SELECT SQL_CALC_FOUND_ROWS m.id ID,m.username USERNAME,m.nickname NICKNAME FROM Managers m,UsersRoles ur WHERE m.id=ur.userId AND ur.roleId=?";
		return SQLHelpers.getRowSize(sql, hhn_userDataSource, new Object[]{roleId}, page, new ManagersPage());
	}

	@Override
	public Pagination<Managers> getUsersNotInRoles(Pagination<Managers> page,
			int roleId) {
		String sql="SELECT SQL_CALC_FOUND_ROWS m.id ID,m.username USERNAME,m.nickname NICKNAME FROM Managers m WHERE m.id NOT IN (SELECT a.id FROM Managers a,UsersRoles ur WHERE a.id=ur.userId AND ur.roleId=?)";
		return SQLHelpers.getRowSize(sql, hhn_userDataSource, new Object[]{roleId}, page, new ManagersPage());
	}
	
	class ManagersPage extends PageMapper<Managers>{


		@Override
		public Managers toCustomizedBean(ResultSet rs) throws SQLException {
			Managers m=new Managers();
			m.setId(Integer.parseInt(rs.getString("ID")));
			m.setNickname(rs.getString("NICKNAME"));
			m.setUsername(rs.getString("USERNAME"));
			return m;
		}
		
	}

}
