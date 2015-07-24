package com.hehenian.biz.common.redis;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.service.BaseTestCase;
import com.hehenian.common.redis.SpringRedisCacheService;

public class RedisTest extends BaseTestCase {

	@Autowired
	private SpringRedisCacheService localCacheService;
	
	/**
	 * 添加到有序集合里
	 */
	@Test
    public void testPutSorted() {
		localCacheService.addToSortedSet("test", "eight", 40);
    }
	
	
	/**
	 * 获取全部排名
	 */
	@Test
	public  void testGetSorted(){
		Set<String> s = localCacheService.getSortedSetRangeReverse("test", 0, 0);
		for(String sv :s){
			System.out.println(sv);
		}
	}
	
	/**
	 * 取某个成员的排名
	 */
	@Test
	public void testGetSortedByMember(){
		Long sorted = localCacheService.getSortByMember("test", "for");
		System.out.println(sorted);
	}

//	@Test
//	public void testSubcrile(){
//		localCacheService
//	}
}
