package com.sp2p.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.data.dao.Table;

public class DBReflectUtil {

	/**
	 * 将Map值设置进表对象中去
	 * 
	 * @param <T>
	 * @param table
	 *            表对象
	 * @param map
	 *            Map对象
	 */
	public static <T extends Table> void mapToTableValue(T table, Map<String, String> map) {
		if (table != null) {
			try {
				Class clazz = com.shove.data.dao.Field.class;
				Method setValueMethod = clazz.getMethod("setValue", Object.class);
				Class tableClazz = table.getClass();
				Field[] fields = tableClazz.getDeclaredFields();
				for (Field f : fields) {
					String value = map.get(f.getName());
					if (value != null && StringUtils.isNotBlank(value)) {
						value = StringEscapeUtils.escapeSql(value);
						Object field = f.get(table);
						if (field != null) {
							setValueMethod.invoke(field, value);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
