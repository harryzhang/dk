/*package com.hehenian.common.utils;

import java.lang.reflect.Field;
import java.util.List;
import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.hehenian.common.annotations.Table;


public class GenerateSimpleSQLUtils {

	public static SqlBean generateSqlBean(Object obj) {
		if (obj == null) {
			return null;
		}
		String sql = generateInsertSQL(obj.getClass());
		SqlParameterSource ps = new BeanPropertySqlParameterSource(obj);
		return new SqlBean(sql, ps);
	}

	public static SqlBean generateSqlBean(List<Object> objList) {
		if (objList == null || objList.size() < 1) {
			return null;
		}
		String sql = generateInsertSQL(objList.get(0).getClass());
		SqlParameterSource[] psArr = new BeanPropertySqlParameterSource[objList
				.size()];
		for (int i = 0; i < objList.size(); ++i) {
			psArr[i] = new BeanPropertySqlParameterSource(objList.get(i));
		}
		return new SqlBean(sql, psArr);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String generateInsertSQL(Class cls) {
		StringBuilder sql = new StringBuilder();
		Table tableName = (Table) cls.getAnnotation(Table.class);
		Field[] fields = cls.getDeclaredFields();
		StringBuilder values = new StringBuilder(" values (");
		sql.append("insert into ")
				.append(tableName != null ? tableName.name() : cls
						.getSimpleName()).append("(");
		try {
			for (Field f : fields) {
				// 该域上无值，直接跳过
				f.setAccessible(true);
				// 注解优先，如果有注解，先解析注解
				Column columnAnnotation = (Column) f
						.getAnnotation(Column.class);
				if (columnAnnotation != null) {
					// 注解上说明不解析该字段，则直接跳过
					if (!columnAnnotation.insertable()) {
						continue;
					}
					sql.append(StringUtils.isEmpty(columnAnnotation.name()) ? f
							.getName() : columnAnnotation.name());
				} else {
					sql.append(f.getName());
				}
				sql.append(",");
				values.append(":").append(f.getName()).append(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sql.setLength(sql.length() - 1);
		values.setLength(values.length() - 1);
		sql.append(")");
		values.append(")");
		return sql.append(values).toString();
	}

	public static void main(String[] args) {
		// String sqlBean = GenerateSimpleSQLUtils
		// .generateInsertSQL(QuesChoice.class);
		// System.out.println(sqlBean);
	}
}
*/