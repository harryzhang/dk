package com.hehenian.manager.commons;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class SQLHelpers {

	private static String regex = "^(?i)select(?-i) ([^from]*)? (?i)from(?-i) (.*)";
	private static Pattern p = Pattern.compile(regex);

	/**
	 * 获取分页数据
	 * 
	 * @param originalSql
	 *            查询的sql，不出现limit
	 * @param template
	 *            查询的数据源
	 * @param objs
	 *            查询参数
	 * @param page
	 *            分页
	 * @param callback
	 *            回调函数
	 * @return
	 */
	public static <T> Pagination<T> getRowSize(String originalSql,
			NamedParameterJdbcTemplate template, Object[] objs,
			Pagination<T> page, PaginationCallback<T> callback) {
		int pageSize = page.getPageSize();
		int start = page.getPage();
		StringBuilder sql = new StringBuilder();
		sql.append(originalSql).append(" limit ")
				.append(pageSize * (start - 1)).append(",").append(pageSize);
		List<Map<String, Object>> items = template.getJdbcOperations()
				.queryForList(sql.toString(), objs);
		sql.setLength(0);
		sql.append("select count(1) from (").append(originalSql)
				.append(") tmp");
		int count = template.getJdbcOperations().queryForInt(sql.toString(),
				objs);
		page.setTotal(count);
		return callback.getPage(items, page);
	}

	public static <T> Pagination<T> getRowSize(String originalSql,
			ProxoolDataSource dataSource, Object[] objs, Pagination<T> page,
			PageMapper<T> callback) {
		int pageSize = page.getPageSize();
		int start = page.getPage();
		StringBuilder sql = new StringBuilder(originalSql);
		if(StringUtils.isNotBlank(page.getSortname()) && StringUtils.isNotBlank(page.getSortorder())){
			sql.append(" order by ").append(page.getSortname()).append(" ").append(page.getSortorder());
		}
		
		sql.append(" limit ").append(pageSize * (start - 1)).append(",").append(pageSize);
		PreparedStatement statement = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			if (conn == null) {
				return page;
			}
			statement = conn.prepareStatement(sql.toString());
			if (objs != null) {
				for (int i = 0, n = objs.length; i < n; i++) {
					statement.setObject(i + 1, objs[i]);
				}
			}
			rs = statement.executeQuery();
			if (callback == null) {
				return page;
			}
			Pagination<T> results = callback.getPage(rs, page);
			sql.setLength(0);
			//获取总数量
			sql.append("select FOUND_ROWS() as ct");
			rs = statement.executeQuery(sql.toString());
			if (rs.next()) {
				int count = rs.getInt(1);
				results.setTotal(count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return page;
	}
	
	/**
	 * getRowSize默认实现，返回map
	 * @param originalSql
	 * @param dataSource
	 * @param objs
	 * @param page
	 * @return
	 */
	public static  Pagination<Map<String,Object>> getRowSize(String originalSql,
			ProxoolDataSource dataSource, Object[] objs, Pagination<Map<String,Object>> page){
		return getRowSize(originalSql, dataSource, objs, page, new PageMapper<Map<String,Object>>() {

			@Override
			public Map<String, Object> toCustomizedBean(ResultSet rs) {
				Map<String, Object> data = new LinkedHashMap<String, Object>();
				try {
					ResultSetMetaData metaData = rs.getMetaData();
					int columnCount = metaData.getColumnCount();
			        for (int i = 1; i <= columnCount; ++i) {
						String key = metaData.getColumnLabel(i);
						if ((key == null) || (key.length() < 1)) {
							key = metaData.getColumnName(i);
						}
						Object obj = rs.getObject(i);
						if (obj instanceof Blob){
							obj = rs.getBytes(i);
						} else if (obj instanceof Clob) {
							obj = rs.getString(i);
						} else if ((obj != null) && (obj instanceof java.sql.Date) && ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(i)))) {
							obj = rs.getTimestamp(i);
						}

						data.put(key, obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return data;
			}
		});
	}
	/**
	 * 根据map和table 组装插入sql
	 * 
	 * @param table
	 * @param dataMap
	 * @return
	 */
	public static String getInsertSQL(String table, Map<String, String> dataMap) {
		StringBuilder sql1 = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		sql1.append("insert into ").append(table).append(" (");
		sql2.append(" ) values (");

		Set<String> keySet = dataMap.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object value = dataMap.get(key);

			sql1.append(key).append(", ");
			if (value == null) {
				sql2.append("null, ");
			} else {
				sql2.append("'").append(StringEscapeUtils.escapeSql(value.toString()))
						.append("', ");
			}
		}
		sql1 = new StringBuilder(sql1.subSequence(0, sql1.lastIndexOf(",")));
		sql2 = new StringBuilder(sql2.subSequence(0, sql2.lastIndexOf(",")));

		sql1.append(sql2).append(")");

		return sql1.toString();
	}

	/**
	 * 根据id\map和table 组装更新sql
	 * 
	 * @param table
	 * @param dataMap
	 * @param id
	 * @return
	 */
	public static String getUpdateSQL(String table,
			Map<String, String> dataMap, Map<String, String> primaryKeyValue) {
		if (dataMap == null || dataMap.size() == 0) {
			return null;
		}

		StringBuilder sql1 = new StringBuilder();
		sql1.append("update ").append(table).append(" set ");

		Set<String> keySet = dataMap.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object value = dataMap.get(key);

			sql1.append(key).append("=");
			if (value == null) {
				sql1.append("null, ");
			} else {
				sql1.append("'").append(StringEscapeUtils.escapeSql(value.toString()))
						.append("', ");
			}
		}
		sql1 = new StringBuilder(sql1.subSequence(0, sql1.lastIndexOf(",")));

		if (primaryKeyValue != null && primaryKeyValue.size() > 0) {
			sql1.append(" where ");
			for (Entry<String, String> entry : primaryKeyValue.entrySet()) {
				sql1.append(entry.getKey()).append("=").append("'")
						.append(entry.getValue()).append("'").append(" and ");
			}
			return sql1.substring(0, sql1.length() - 5);
		}

		return sql1.toString();
	}
	
}
