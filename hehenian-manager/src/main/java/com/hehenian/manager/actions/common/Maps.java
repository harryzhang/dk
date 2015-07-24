/**
 * @auther liminglmf
 * @date 2015年5月8日
 */
package com.hehenian.manager.actions.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * @author liminglmf
 *
 */
public class Maps extends MapUtils{
	
	/**
	 * 根据一组可变参数的数组对象生成一个Map，但不会对K，V使用泛型，用法如下：
	 * 
	 * <pre>
	 * Maps.map(key,value,key,value,key,value......);
	 * </pre>
	 * 
	 * @param keyValues
	 *            可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
	 * @return
	 */
	public static Map<Object, Object> mapByAarray(Object... keyValues) {
		Map<Object, Object> m = Maps.newMap();
		int i = 1;
		Object key = null;
		for (Object value : keyValues) {
			if (i % 2 == 0) {
				m.put(key, value);
			}
			key = value;
			i++;
		}
		return m;
	}
	
	/**
     * 创建一个空的HashMap
     * 
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> newMap() {
        return new HashMap<K, V>();
    }
}
