/*
 * <p>Title: PojoUtil.java </p>
 * <p>Description:  </p>
 * <p>Copyright: D3space (c) 2007 </p>
 * <p>Company: 第三空间信息技术有限公司</p>
 */
package com.hehenian.biz.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class PojoUtil {

	public static String EMPTY = "";
	public static final String INNERSPLITE = "^!^,^!^";

	public static final String SPLITE = "^!^:^!^";

	/**
	 * 比较两个同类型的对象,把dest对象里面与src对象里不同的值记录到map中
	 * 
	 * @param src
	 *            源对象
	 * @param dest
	 *            目标对象
	 * @return map
	 */
	public static Map<String, String> comparePojo(Object src, Object dest) {
		if (dest == null) {
			return null;
		}
		// 判断两个obj是不是同一种类型
		// src==null的情况,循环里作判断,记录dest的每个值
		if (src != null && !dest.getClass().equals(src.getClass())) {
			return null;
		}

		Field[] fs = dest.getClass().getDeclaredFields();
		Map<String, String> valuesMap = new HashMap<String, String>();
		for (int i = 0; i < fs.length; i++) {
			String name = fs[i].getName();
			Object value = null;
			if ("serialVersionUID".equals(name))
				continue;
			Object v1 = null;
			Object v2 = null;
			try {

				Method m = dest.getClass().getMethod("get" + getFirstUpperString(name), null);
				v1 = m.invoke(dest, null);
				if (src != null) {
					v2 = m.invoke(src, null);
				}
			} catch (Exception e) {
				System.err.println(e + " : name=" + name + ",value=" + value);
			}
			if (v1 != null && !v1.equals(v2)) {
				valuesMap.put(name, v1.toString());
			}
		}

		return valuesMap;
	}

	public static String getFirstUpperString(String s) {
		if (s == null || s.length() < 1) {
			return EMPTY;
		}
		String s1 = s.substring(0, 1);
		String s2 = s.substring(1);
		return s1.toUpperCase() + s2;
	}

	public static Long intToLong(Integer i) {
		return intToLong(i, null);
	}

	public static Integer longToInt(Long l) {
		return longToInt(l, null);
	}

	public static Short intToShort(Integer i) {
		return intToShort(i, null);
	}

	public static Integer shortToInt(Short s) {
		return shortToInt(s, null);
	}

	public static Long intToLong(Integer i, Long defaultValue) {
		if (i == null) {
			return defaultValue;
		} else {
			return i.longValue();
		}
	}

	public static Integer longToInt(Long l, Integer defaultValue) {
		if (l == null) {
			return defaultValue;
		} else {
			return l.intValue();
		}
	}

	public static Short intToShort(Integer i, Short defaultValue) {
		if (i == null) {
			return defaultValue;
		} else {
			return i.shortValue();
		}
	}

	public static Integer shortToInt(Short s, Integer defaultValue) {
		if (s == null) {
			return defaultValue;
		} else {
			return s.intValue();
		}
	}

	public static Byte intToByte(Integer i, Byte defaultValue) {
		if (i == null) {
			return defaultValue;
		} else {
			return (byte) i.intValue();
		}
	}

	public static Integer byteToInt(Byte b, Integer defaultValue) {
		if (b == null) {
			return defaultValue;
		} else {
			return b.intValue();
		}
	}

	public static String parseStr(String s, String defaultValue) {
		return s == null ? defaultValue : s;
	}

	public static String parseStr(String s) {
		return s == null ? "" : s;
	}

	public static Timestamp dateToTimestamp(Date d, Timestamp defaultValue) {
		return d == null ? defaultValue : new Timestamp(d.getTime());
	}

	public static Date timestampToDate(Timestamp t, Date defaultValue) {
		return t == null ? defaultValue : t;
	}

	public static long[] list2long(List l) {
		long[] l2 = new long[l.size()];
		for (int i = 0; i < l.size(); i++) {
			l2[i] = new Long(l.get(i).toString()).longValue();
		}
		return l2;
	}

	public static Map listToMap(List<Map> list, String keyTxt, String valueKey) {

		if (list == null || list.size() == 0 || StringUtils.isEmpty(keyTxt)
				|| StringUtils.isEmpty(valueKey)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (Map<String, String> m : list) {
				String keystr = null;
				String va = null;

				keystr = m.get(keyTxt);

				va = m.get(valueKey);

				if (keystr != null && va != null) {
					map.put(keystr, va);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static String toStr(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

	public static String getStr(Object obj) {
		String str = "";
		if (obj == null)
			str = "";
		else
			str = obj.toString();
		return str;
	}

	public static String cutStr(String str, int num) {

		if (num < str.length())
			return str.substring(0, num) + "...";
		else
			return str;
	}

	public static String encodeInnerText(String str) {
		if (str == null) {
			return "";
		}
		// 不用正则表达式替换，直接通过循环，节省cpu时间
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			switch (c) {
			case '\r':
				break;
			case '\n':
				sb.append("\\n\\ ");
				break;
			default:
				if (c >= '\u0000' && c <= '\u001F')
					continue;
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	public static String getKeyValueStr(HashMap map, String str1, String str2) {
		String returnStr = "";
		if (map == null)
			return returnStr;
		String key;
		String values;
		Set entry = map.entrySet();
		for (Object iter : entry) {
			Map.Entry mp = (Map.Entry) iter;
			key = mp.getKey().toString();
			values = mp.getValue() == null ? "" : (String) mp.getValue()
					.toString();
			returnStr += str1 + key.toString() + str2 + values.toString();

		}

		returnStr = returnStr.replaceAll("\r\n", "<br/>");
		returnStr = returnStr.replaceAll("\r", "<br/>");
		returnStr = returnStr.replaceAll("\n", "<br/>");
		returnStr = returnStr.replaceAll("\t", "&nbsp;&nbsp;");
		returnStr = returnStr.replaceAll("\"", "'");
		returnStr = returnStr.replaceAll("\\\\n\\\\", "<br/>");
		return returnStr;
	}

	public static String long2CodeStr(long source, int offset) {
		String str = "";
		char[] charArray = Long.toBinaryString(source).toString().toCharArray();

		for (int i = 0; i < charArray.length; i++) {
			if (charArray[charArray.length - i - 1] == '1') {
				if (str == "") {
					str += (i + 1 + offset);
				} else {
					str += ("," + (i + 1 + offset));
				}
			}
		}

		return str;
	}
}
