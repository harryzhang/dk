/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.account.impl
 * @Title: UserInfoDaoImpl.java
 * @Description: TODO
 *
 * @author: zhanbmf
 * @date 2015-3-29 下午1:26:30
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.account.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.hehenian.biz.dal.account.IUserInfoDao;

public class UserInfoDaoImpl implements IUserInfoDao {
	
	private static Logger logger = Logger.getLogger("UserInfoDaoImpl");
	
	
	private  JdbcTemplate hhn_userMJdbcTemplate;

	private  JdbcTemplate hhn_userSJdbcTemplate;
	
	
	
	public void setHhn_userMJdbcTemplate(JdbcTemplate hhn_userMJdbcTemplate) {
		this.hhn_userMJdbcTemplate = hhn_userMJdbcTemplate;
	}

	public void setHhn_userSJdbcTemplate(JdbcTemplate hhn_userSJdbcTemplate) {
		this.hhn_userSJdbcTemplate = hhn_userSJdbcTemplate;
	}

	/**
	 * 增删改
	 * 
	 * @param sql
	 * @return
	 */
	public int update(String sql) {
		if (sql == null) {
			return -1;
		}
		
		logger.info("sql is :" + sql);

		try {
			return hhn_userMJdbcTemplate.update(sql);
		} catch (Exception e) {
			logger.error("update() --> sql = " + sql, e);
			return -1;
		}
	}

	/**
	 * 批量增删改
	 * 
	 * @param sqls
	 * @return
	 */
	public <T> int[] batchUpdate(String[] sqls) {
		if (sqls == null || sqls.length == 0) {
			return null;
		}
		
		for (int i = 0; i < sqls.length; i++) {
			logger.info("batchsql is :" + sqls[i]);
		}
		
		try {
			return hhn_userMJdbcTemplate.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 查询结果返回list<Map>
	 * 
	 * @param sql
	 * @param isRealTime
	 * @return
	 */
	public List<Map<String, Object>> queryToListMap(String sql,	boolean isRealTime) {
		if (sql == null) {
			return null;
		}
		JdbcTemplate template = getQueryTemplate(isRealTime);

		try {
			return template.queryForList(sql);
		} catch (Exception e) {
			logger.error("queryToListMap() --> sql = " + sql, e);
			return null;
		}
	}

	/**
	 * 查询结果返回list<object>
	 * 
	 * @param sql
	 * @param clazz
	 * @param isRealTime
	 * @return
	 */
	public <T> List<T> queryToListObject(String sql, Class<T> clazz, boolean isRealTime) {
		if (sql == null) {
			return null;
		}
		
		JdbcTemplate template = getQueryTemplate(isRealTime);
		
		try {
			ParameterizedRowMapper<T> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(clazz);
			return template.query(sql, rowMapper);
		} catch (Exception e) {
			logger.error("queryToListObject() --> sql = " + sql, e);
			return null;
		}
	}

	/**
	 * 获得查询的数据模板
	 * 
	 * @param isRealTime
	 * @return
	 */
	private JdbcTemplate getQueryTemplate(boolean isRealTime) {
		if (isRealTime) {
			return hhn_userMJdbcTemplate;
		} else {
			return hhn_userSJdbcTemplate;
		}
	}

	/**
	 * 根据sql返回int数据
	 * 
	 * @param sql
	 * @param clazz
	 * @param isRealTime
	 * @return
	 */
	public int queryForInt(String sql, boolean isRealTime) {
		if (sql == null) {
			return -1;
		}
		JdbcTemplate template = getQueryTemplate(isRealTime);
		try {
			Number result = (Number)template.queryForObject(sql, Integer.class);
			return result == null ? 0 : result.intValue();
		} catch (Exception e) {
			return -1;
		}
	}

}
