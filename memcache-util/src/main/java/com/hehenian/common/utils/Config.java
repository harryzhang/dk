package com.hehenian.common.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class Config {

	private static Map<String, Properties> propMap = null;

	public static String getProperty(String file, String name) {
		if (propMap == null) {
			propMap = new HashMap<String, Properties>();
		}
		String key = getFileNameFromPath(file);
		if (!propMap.containsKey(key)) {
			Properties prop = new Properties();
			try {
				InputStream is = Config.class.getResourceAsStream("/" + file);
				prop.load(is);
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			propMap.put(key, prop);
			return prop.getProperty(name);
		}
		return propMap.get(key).getProperty(name);
	}

	public static int getPropertyAsInt(String file, String name) {
		String value = getProperty(file, name);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static long getPropertyAsLong(String file, String name) {
		String value = getProperty(file, name);
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static double getPropertyAsDouble(String file, String name) {
		String value = getProperty(file, name);
		if (value != null) {
			try {
				return Double.parseDouble(value);
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static float getPropertyAsFloat(String file, String name) {
		String value = getProperty(file, name);
		if (value != null) {
			try {
				return Float.parseFloat(value);
			} catch (Exception e) {
			}
		}
		return 0;
	}

	private static String getFileNameFromPath(String path) {
		int location = path.lastIndexOf("/");
		if (location == -1) {
			return path;
		}
		String name = path.substring(location + 1);
		return name;
	}

	/**
	 * 获得以指定前缀开头的多个key的value
	 * 
	 * @param keyPrefix
	 * @return
	 */
	public static Map<String, String> getPropertyWithPrefix(String file,
			String keyPrefix) {
		if (propMap == null) {
			getProperty(file, "");
		}
		Set<Object> configKeys = propMap.get(file).keySet();
		Map<String, String> map = new HashMap<String, String>();
		if (configKeys != null && configKeys.size() > 0) {
			for (Object key : configKeys) {
				String k = key.toString();
				if (k.startsWith(keyPrefix)) {
					map.put(k, getProperty(file, k));
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println(getProperty("file.properties", "uploadServer_1"));
		Map<String, String> propertyWithPrefix = getPropertyWithPrefix(
				"photo.properties", "uploadServer_");
		Set<Entry<String, String>> entrySet = propertyWithPrefix.entrySet();
		for (Entry<String, String> entry : entrySet) {
			System.out.println(entry.getValue());
		}

	}
}
