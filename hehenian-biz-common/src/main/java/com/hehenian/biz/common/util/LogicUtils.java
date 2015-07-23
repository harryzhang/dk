package com.hehenian.biz.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * 简单逻辑判断
 * 
 * @author liuzgmf
 *
 */
public class LogicUtils {

	private static int ZERO = 0;
	private static String EMPTY_STRING = "";

	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Collection collection) {
		if (null == collection || ZERO == collection.size()) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotNullAndEmpty(Collection collection) {
		return !isNullOrEmpty(collection);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Map map) {
		if (null == map || ZERO == map.size()) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotNullAndEmpty(Map map) {
		return !isNullOrEmpty(map);
	}

	public static boolean isNullOrEmpty(Object[] objects) {
		if (null == objects || ZERO == objects.length) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNullAndEmpty(Object[] objects) {
		return !isNullOrEmpty(objects);
	}

	public static boolean isNull(Object object) {
		if (object == null) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNull(Object Object) {
		return !isNull(Object);
	}

	public static boolean isNullOrEmpty(String subject) {
		if (null == subject || EMPTY_STRING.equals(subject)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNullAndEmpty(String subject) {
		return !isNullOrEmpty(subject);
	}

}
