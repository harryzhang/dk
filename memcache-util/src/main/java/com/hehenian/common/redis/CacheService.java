/**  
 * @Project: memcache-util
 * @Package com.hehenian.common.redis
 * @Title: CacheService.java
 * @Description: 分布式服务
 *
 * @author: zhanbmf
 * @date 2015-3-29 下午4:01:30
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.common.redis;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


public class CacheService {
	private final long FAIL = -1;
	private final String LOCAL = "local";
	private Map<String, SpringRedisCacheService> cacheService;

	public Map<String, SpringRedisCacheService> getCacheService() {
		return cacheService;
	}

	public void setCacheService(
			Map<String, SpringRedisCacheService> cacheService) {
		this.cacheService = cacheService;
	}

	/**
	 * 获取本地的默认redis
	 * @return
	 */
	public SpringRedisCacheService get() {
		if (cacheService == null || cacheService.size() == 0) {
			return null;
		}
		return cacheService.get(LOCAL);
	}

	/**
	 * 将所有的redis指定key的缓存都清空
	 * 
	 * @param key
	 */
	public void clear(Object key) {
		if (cacheService == null || cacheService.size() == 0) {
			return;
		}
		Set<Entry<String, SpringRedisCacheService>> entrySet = cacheService
				.entrySet();
		for (Entry<String, SpringRedisCacheService> entry : entrySet) {
			if(entry.getKey().equalsIgnoreCase(LOCAL)){
				continue;
			}
			long result = entry.getValue().deleteObjectKey(key);
			if (result == FAIL) {// 重试一次
				entry.getValue().deleteObjectKey(key);
			}
		}
	}
}
