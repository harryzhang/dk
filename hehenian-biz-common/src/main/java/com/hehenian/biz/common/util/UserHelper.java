package com.hehenian.biz.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;

import com.hehenian.agreement.common.utils.Config;

public class UserHelper {

	private final static String TABLE_TYPE_PREFIX = "tableNumber";
	private static Map<String, Integer> tableNumber = new HashMap<String, Integer>();

	static {
		// 获得config中设置的分表数量
		Map<String, String> types = Config.getPropertyWithPrefix("table.properties", TABLE_TYPE_PREFIX);
		for (Entry<String, String> entry : types.entrySet()) {
			String key = entry.getKey();
			tableNumber.put(key.substring(key.indexOf(".") + 1), Config.getPropertyAsInt("table.properties", entry.getKey()));
		}
	}

	/**
	 * 获取配置
	 * @return
	 */
	public static Map<String, Integer> getTableNumber() {
		return tableNumber;
	}

	/**
	 * 根据类名获得配置文件的表数量，如User,配置文件为: tableNumber.User = 100
	 * @param clazz   类名
	 * @return        表数量
	 */
	public static int getTableNumber(String clazzName) {
		if (tableNumber.size() == 0) {
			return -1;
		}
		Integer number = tableNumber.get(clazzName);
		return number == null ? -1 : number;
	}

	/**
	 * 将字符串加密成byte，取最后2个byte组装成int
	 * @param str
	 * @return
	 */
	public static <T> int getTableNumberByDigest(String str) {
		if (str == null || str.trim() == "") {
			return -1;
		}
		byte[] md5Digest = DigestUtils.md5(str.getBytes());
		int i = md5Digest[0] + (md5Digest[1]<<8);
		return Math.abs(i);
	}
}
