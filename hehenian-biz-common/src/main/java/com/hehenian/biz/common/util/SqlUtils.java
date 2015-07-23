package com.hehenian.biz.common.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SqlUtils {

	private final static String UNDERLINE = "_";

	/**
	 * 获取查询用的sql
	 * @param userId
	 * @param clazz
	 * @param isRealTime
	 * @return
	 */
	public static <T> String getSql(long userId, Class<T> clazz) {
		if (userId < 0) {
			return null;
		}
		
		return new StringBuilder().append("select * from ")
				.append(getTable(userId, clazz)).append(" where userId=")
				.append(userId).toString();
	}

	/**
	 * 根据邮箱、手机登登陆信息获取查询用sql
	 * 
	 * @param tableId
	 * @param loginInfo
	 * @param clazz
	 * @return
	 */
	public static <T> String getSqlForloginInfo(long tableId, String loginInfo,	Class<T> clazz) {
		if (tableId < 0) {
			return null;
		}
		
		String sql = new StringBuilder().append("select * from ")
				.append(getTable(tableId, clazz)).append(" where loginInfo=")
				.append("'").append(loginInfo).append("'").toString();
		return sql;
	}

	/**
	 * 从手机号、邮箱与userId对应关系分表获取用户id
	 * 
	 * @param tableId
	 * @param loginInfo
	 * @param clazz
	 * @return
	 */
	public static <T> String getUserIdFromloginInfo(int tableId,
			String loginInfo, Class<T> clazz) {
		if (tableId < 0) {
			return null;
		}
		String sql = new StringBuilder().append("select userId from ")
				.append(getTable(tableId, clazz)).append(" where loginInfo=")
				.append("'").append(loginInfo).append("'").toString();
		return sql;
	}

	/**
	 * 获得替换好表名的查询用sql
	 * 
	 * @param sql
	 * @param id
	 * @return
	 */
	public static <T> String getQuerySql(String sql, long id) {
		if (id < 0) {
			return null;
		}
		replaceTableName(sql, id);
		return sql;
	}

	/**
	 * 将sql中的包含分表的表名全部替换,如将UserInfo替换为UserInfo_xxx
	 * @param sql
	 * @param id
	 * @return
	 */
	private static String replaceTableName(String sql, long id) {
		if (id < 0) {
			return null;
		}
		// 获取所有需要分表的信息
		Map<String, Integer> tableNumber = UserHelper.getTableNumber();
		if (tableNumber.size() == 0) {
			return sql;// 不需要转换
		}
		for (Entry<String, Integer> entry : tableNumber.entrySet()) {
			String source = entry.getKey();
			String dest = getTable(id, source);
			String str1 = source + " ";
			String str2 = dest + " ";
			String str3 = source + ",";
			String str4 = dest + ",";
			sql = sql.replaceAll(str1, str2);
			sql = sql.replaceAll(str3, str4);
		}
		return sql;
	}

	/**
	 * 获取删除sql
	 * 
	 * @param userId
	 * @param clazz
	 * @return
	 */
	public static <T> String getDeleteSql(long userId, Class<T> clazz) {
		if (userId < 0) {
			return null;
		}
		String sql = new StringBuilder().append("delete from ").append(getTable(userId, clazz))
				.append(" where userId=").append(userId).append(" limit 1").toString();
		return sql;
	}

	/**
	 * 获取删除sql
	 * 
	 * @param userId
	 * @param primaryKeyValue
	 * @param clazz
	 * @return
	 */
	public static <T> String getDeleteSql(long userId,
			Map<String, String> primaryKeyValue, Class<T> clazz) {
		if (userId < 0 || primaryKeyValue == null
				|| primaryKeyValue.size() == 0) {
			return null;
		}
		StringBuilder sql1 = new StringBuilder();
		sql1.append("delete from ").append(getTable(userId, clazz));
		sql1.append(" where ");
		for (Entry<String, String> entry : primaryKeyValue.entrySet()) {
			sql1.append(entry.getKey()).append("=").append("'")
					.append(entry.getValue()).append("'").append(" and ");
		}
		return sql1.substring(0, sql1.length() - 5);

	}

	/**
	 * 根据类获得插入sql
	 * 
	 * @param id
	 * @param t
	 * @return
	 */
	public static <T> String getInsertSql(long id, T t) {
		if (id < 0) {
			return null;
		}
		// 获得真正的table名称
		String table = getTable(id, t.getClass());
		// 获取改变字段(保留缓存使用)
		Map<String, String> dataMap = PojoUtil.comparePojo(null, t);
		// 拼装sql语句
		String sql = getInsertSQL(table, dataMap);
		return sql;
	}

	/**
	 * 根据类获得更新sql
	 * 
	 * @param dest
	 * @return
	 */
	public static <T> String getUpdateSql(long id,
			Map<String, String> primaryKeyValue, T src, T dest) {
		if (id < 0 || primaryKeyValue == null || primaryKeyValue.size() == 0) {
			return null;
		}
		// 获得真正的table名称
		String table = getTable(id, dest.getClass());
		// 获取改变字段(保留缓存使用)
		Map<String, String> dataMap = PojoUtil.comparePojo(src, dest);
		// 拼装sql语句
		String sql = getUpdateSQL(table, dataMap, primaryKeyValue);
		return sql;
	}

	/**
	 * 根据值、类、获得表名
	 * @param value
	 * @param clazz
	 * @return
	 */
	public static <T> String getTable(long value, Class<T> clazz) {
		return getTable(value, clazz.getSimpleName(), UserHelper.getTableNumber(clazz.getSimpleName()));
	}

	/**
	 * 根据值、类名、获得表名
	 * 
	 * @param value
	 * @param clazzName
	 * @return
	 */
	private static <T> String getTable(long value, String clazzName) {
		return getTable(value, clazzName, UserHelper.getTableNumber(clazzName));
	}

	/**
	 * 根据值、表名前缀、分表类型获得表名
	 * 
	 * @param value
	 * @param type
	 * @param clazzName
	 * @return
	 */
	private static String getTable(long value, String clazzName, int tableNumber) {
		if (value < 0) {
			return null;
		}
		String endNum = null;
		if (tableNumber < 10) {
			return clazzName;
		} else if (tableNumber == 10) {
			endNum = String.format("%01d", value % 10);
		} else if (tableNumber == 100) {
			endNum = String.format("%02d", value % 100);
		} else if (tableNumber == 1000) {
			endNum = String.format("%03d", value % 1000);
		} else if (tableNumber == 10000) {
			endNum = String.format("%04d", value % 10000);
		}
		
		if (endNum != null) {
			return new StringBuilder(clazzName).append(UNDERLINE)
					.append(endNum).toString();
		}
		return clazzName;
	}

	
	/**
	 * 根据map和table 组装插入sql
	 * 
	 * @param table
	 * @param dataMap
	 * @return
	 */
	private static String getInsertSQL(String table, Map<String, String> dataMap) {
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
				sql2.append("'").append(StringUtil.encodeSQL(value.toString()))
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
	private static String getUpdateSQL(String table,
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
				sql1.append("'").append(StringUtil.encodeSQL(value.toString()))
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
