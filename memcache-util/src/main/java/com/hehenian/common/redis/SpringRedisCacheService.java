package com.hehenian.common.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * reids工具类
 * 
 * @author fengli
 * @date 2013-3-19 下午12:30:24
 */
public class SpringRedisCacheService {

	private JedisPool jedisPool;
	private String serverName;
	private boolean ALIVE = true;
	private boolean EXCEPTION_FALG = false;

	private static final Logger logger = Logger.getLogger("redis");

	public class MonitorThread extends Thread {

		@Override
		public void run() {
			int sleepTime = 30000;
			int baseSleepTime = 1000;
			while (true) {
				try {
					// 30秒执行监听
					int n = sleepTime / baseSleepTime;
					for (int i = 0; i < n; i++) {
						// 检查到异常，立即进行检测处理
						if (EXCEPTION_FALG) {
							break;
						}
						Thread.sleep(baseSleepTime);
					}
					// 连续做3次连接获取
					int errorTimes = 0;
					for (int i = 0; i < 3; i++) {
						try {
							Jedis jedis = jedisPool.getResource();
							if (jedis == null) {
								errorTimes++;
								continue;
							}
							returnConnection(jedis);
							break;
						} catch (Exception e) {
							errorTimes++;
							continue;
						}
					}
					if (errorTimes == 3) {// 3次全部出错，表示服务器出现问题
						ALIVE = false;
						EXCEPTION_FALG = false;	// 只是在异常出现第一次进行跳出处理，后面的按异常检查时间进行延时处理
						logger.error("redis[" + serverName + "] 服务器连接不上！ ！ ！");
						// 修改休眠时间为5秒，尽快恢复服务
						sleepTime = 5000;
					} else {
						if (ALIVE == false) {
							ALIVE = true;
							// 修改休眠时间为30秒，尽快恢复服务
							sleepTime = 30000;
							logger.info("redis[" + serverName + "] 服务器恢复正常！ ！ ！");
						}
						EXCEPTION_FALG = false;
						Jedis jedis = jedisPool.getResource();
						logger.info("redis[" + serverName + "] 当前记录数：" + jedis.dbSize());
						returnConnection(jedis);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 设置连接池
	 * 
	 * @param 数据源
	 */
	public void setJedisPool(JedisPool JedisPool) {
		this.jedisPool = JedisPool;
		// 启动监听线程
		new MonitorThread().start();
	}

	/**
	 * 获取连接池
	 * 
	 * @return 数据源
	 */
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * 判断服务器是否活动
	 * @return
	 */
	public boolean isServerAlive() {
		return ALIVE;
	}

	/**
	 * 获取连接
	 * @return
	 */
	public Jedis getConnection() {
		Jedis jedis = null;
		try {
			if (ALIVE) {// 当前状态为活跃才获取连接，否则直接返回null
				jedis = jedisPool.getResource();
			}
		} catch (Exception e) {
			EXCEPTION_FALG = true;
		}
		return jedis;
	}

	/**
	 * 关闭数据库连接
	 * @param conn
	 */
	public void returnConnection(Jedis jedis) {
		if (null != jedis) {
			try {
				jedisPool.returnResource(jedis);
			} catch (Exception e) {
				jedisPool.returnBrokenResource(jedis);
			}
		}
	}

	/**
	 * 关闭错误连接
	 * @param jedis
	 */
	public void returnBorkenConnection(Jedis jedis) {
		if (null != jedis) {
			jedisPool.returnBrokenResource(jedis);
		}
	}

	/**
	 * 设置key-value失效时间，序列化类型key
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public long expireObjectKey(Object key, int seconds) {
		return expire(serializable(key), seconds);
	}

	/**
	 * 设置key-value失效时间，字符串类型key
	 * @param key
	 * @param seconds
	 * @return
	 */
	public long expire(String key, int seconds) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.expire(key, seconds);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return -1;
	}

	/**
	 * 设置key-value失效时间，字节类型key
	 * @param key
	 * @param seconds
	 * @return
	 */
	public long expire(byte[] key, int seconds) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.expire(key, seconds);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return -1;
	}

	/**
	 * 检查key是否存在缓存
	 * @param key
	 * @return
	 */
	public boolean checkKeyExisted(Object key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return false;
		}
		boolean result = false;
		try {
			if (key instanceof String) {
				if (conn.exists((String) key)) {// 字符串key存在，直接返回
					returnConnection(conn);
					return true;
				}
			}
			result = conn.exists(serializable(key));
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return result;
	}

	/**
	 * 检查key是否存在
	 * @param key
	 * @return 返回操作后的值
	 */
	public boolean checkKeyExisted(byte[] key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return false;
		}
		boolean result = false;
		try {
			result = conn.exists(key);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return result;
	}

	/**
	 * 加1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public long increase(String key) {
		return increase(key, 1);
	}

	/**
	 * 加操作，指定加的量
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public long increase(String key, int num) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.incrBy(key, num);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return -1;
	}

	/**
	 * 加1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public long increase(byte[] key) {
		return increase(key, 1);
	}

	/**
	 * 加操作，指定加的量
	 * @param key
	 * @param num
	 * @return
	 */
	public long increase(byte[] key, int num) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.incrBy(key, num);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return -1;
	}

	/**
	 * 减1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public long decrease(String key) {
		return decrease(key, 1);
	}

	/**
	 * 减操作，指定减的值
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public long decrease(String key, int num) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.decrBy(key, num);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return -1;
	}

	/**
	 * 减1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public long decrease(byte[] key) {
		return decrease(key, 1);
	}

	/**
	 * 减操作，指定减的值
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public long decrease(byte[] key, int num) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.decrBy(key, num);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return -1;
	}

	/**
	 * 删除缓存记录，先做字符串判断，不存在再对key做序列化处理
	 * @param key
	 */
	public long delete(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.del(key);
			if (result == 0) {
				result = conn.del(serializable(key));
			}
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return -1;
	}

	/**
	 * 删除缓存记录，直接对key做序列化处理
	 * @param key
	 * @return
	 */
	public long deleteObjectKey(Object key) {
		return delete(serializable(key));
	}

	/**
	 * 删除记录
	 * @param key
	 * @return
	 */
	public long delete(byte[] key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.del(key);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return -1;
	}

	/**
	 * 设置对象类型缓存项，无失效时间
	 * @param key
	 * @param value
	 */
	public void set(Object key, Object value) {
		set(serializable(key), serializable(value), -1);
	}

	/**
	 * 设置对象类型缓存项，加入失效时间，单位为秒
	 * 
	 * @param key
	 * @param value
	 * @param exp
	 */
	public void set(Object key, Object value, int exp) {
		set(serializable(key), serializable(value), exp);
	}

	/**
	 * 设置key-value项，字节类型
	 * @param key
	 * @param value
	 */
	public void set(byte[] key, byte[] value, int exp) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			if (exp > 0) {
				conn.setex(key, exp, value);
			} else {
				conn.set(key, value);
			}
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 获取对象类型
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		byte[] data = get(serializable(key));
		if (data != null) {
			return unserialize(data);
		}
		return null;
	}

	/**
	 * 获取key value
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.get(key);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}

	/**
	 * 设置字符串类型缓存项
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		setString(key, value, -1);
	}

	/**
	 * 存储字符串类型缓存项，加入失效时间，单位为秒
	 * @param key
	 * @param value
	 * @param exp
	 */
	public void setString(String key, String value, int exp) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			if (exp > 0) {
				conn.setex(key, exp, value);
			} else {
				conn.set(key, value);
			}
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 获取字符串类型
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			String value = conn.get(key);
			returnConnection(conn);
			return value;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}

	/**
	 * 获取所有列表(默认从左边第一个开始)
	 * @param listKey
	 * @return
	 */
	public List<Object> getListAll(Object listKey) {
		List<byte[]> data = getListAll(serializable(listKey));
		List<Object> result = new ArrayList<Object>();
		if (data != null && data.size() > 0) {
			for (byte[] item : data) {
				result.add(unserialize(item));
			}
			return result;
		}
		return null;
	}

	/**
	 * 获取列表所有数据
	 * @param listKey
	 * @return
	 */
	public List<byte[]> getListAll(byte[] listKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			List<byte[]> data = conn.lrange(serializable(listKey), 0, 1000000000); // 默认设置一个大数
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}

	/**
	 * 从左边添加到list
	 * 
	 * @param listKey
	 * @param value
	 */
	public void addToListLeft(Object listKey, Object value) {
		addToListLeft(serializable(listKey), serializable(value));
	}

	/**
	 * 从左边添加到list
	 * @param listKey
	 * @param value
	 */
	public void addToListLeft(byte[] listKey, byte[] value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.lpush(listKey, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 从右边添加到list
	 * 
	 * @param listKey
	 * @param value
	 */
	public void addToListRight(Object listKey, Object value) {
		addToListRight(serializable(listKey), serializable(value));
	}

	/**
	 * 从右边添加到list
	 * @param listKey
	 * @param value
	 */
	public void addToListRight(byte[] listKey, byte[] value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.rpush(listKey, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 从左边移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public Object popFromListLeft(Object listKey) {
		return unserialize(popFromListLeft(serializable(listKey)));
	}

	/**
	 * 从左边移除一个对象，并返回该对象
	 * @param listKey
	 * @return
	 */
	public byte[] popFromListLeft(byte[] listKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.lpop(listKey);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}

	/**
	 * 从右边移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public Object popFromListRight(Object listKey) {
		return unserialize(popFromListRight(serializable(listKey)));
	}

	/**
	 * 从右边移除一个对象，并返回该对象
	 * @param listKey
	 * @return
	 */
	public byte[] popFromListRight(byte[] listKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.rpop(listKey);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}

	/**
	 * 获取列表长度
	 * 
	 * @param listKey
	 * @return
	 */
	public int getLengthOfList(Object listKey) {
		return getLengthOfList(serializable(listKey));
	}

	/**
	 * 获取列表长度
	 * @param listKey
	 * @return
	 */
	public int getLengthOfList(byte[] listKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return 0;
		}
		try {
			int length = conn.llen(listKey).intValue();
			returnConnection(conn);
			return length;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			return 0;
		}
	}

	/**
	 * 获取list某一范围的段
	 * @param listKey
	 * @param start
	 * @param size
	 * @return
	 */
	public List<Object> getListRange(Object listKey, int start, int size) {
		List<byte[]> data = getListRange(serializable(listKey), start, start + size - 1);
		if (data != null && data.size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (byte[] item : data) {
				result.add(unserialize(item));
			}
			return result;
		}
		return null;
	}

	/**
	 * 获取list某一范围的段
	 * @param listKey
	 * @param start
	 * @param size
	 * @return
	 */
	public List<byte[]> getListRange(byte[] listKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		
		try {
			List<byte[]> data = conn.lrange(listKey, start, start + size - 1);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			return null;
		}
	}

	/**
	 * 获取Map结构所有数据
	 * @param mapKey
	 * @return
	 */
	public Map<Object, Object> getMapAll(Object mapKey) {
		Map<byte[], byte[]> data = getMapAll(serializable(mapKey));
		if (data != null && data.size() > 0) {
			Map<Object, Object> result = new HashMap<Object, Object>();
			Set<byte[]> keys = data.keySet();
			for (byte[] key : keys) {
				result.put(unserialize(key), unserialize(data.get(key)));
			}
			return result;
		}
		return null;
	}

	/**
	 * 获取Map结构所有数据
	 * @param mapKey
	 * @return
	 */
	public Map<byte[], byte[]> getMapAll(byte[] mapKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Map<byte[], byte[]> data = conn.hgetAll(mapKey);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			return null;
		}
	}

	/**
	 * 获取Map结构所有数据(key为String)
	 * @param mapKey
	 * @return
	 */
	public Map<String, String> getStringMapAll(String mapKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Map<String, String> result = conn.hgetAll(mapKey);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			return null;
		}
	}

	/**
	 * 获取Map所有数据，直接返回未序列化的结果，对一些特殊的应用场景更高效
	 * @param mapKey
	 * @return
	 */
	public Map<byte[], byte[]> getMapAllByte(Object mapKey) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Map<byte[], byte[]> result = conn.hgetAll(serializable(mapKey));
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			return null;
		}
	}

	/**
	 * 添加到Map结构
	 * @param mapKey
	 * @param field
	 * @param value
	 */
	public void putToMap(Object mapKey, Object field, Object value) {
		putToMap(serializable(mapKey), serializable(field), serializable(value));
	}

	/**
	 * 添加到Map结构
	 * @param mapKey
	 * @param field
	 * @param value
	 */
	public void putToMap(byte[] mapKey, byte[] field, byte[] value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.hset(mapKey, field, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 添加到Map结构(key为String)
	 * @param mapKey
	 * @param field
	 * @param value
	 */
	public void putStringToMap(String mapKey, String field, String value) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.hset(mapKey, field, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 批量设置到hash数据结果，采用byte类型存储，
	 * 取的时候得注意数据类型转换（例如：Map<key,value>中put数据时key的类型为String
	 * ，那么get的时候需严格用String类型,否则get时会得不到你想要的）
	 * 
	 * @param mapKey
	 * @param data
	 */
	public void putToMap(Object mapKey, Map<Object, Object> data) {
		putToMap(serializable(mapKey), serializeMap(data));
	}

	/**
	 * 批量设置到hash数据结果，采用byte类型存储
	 * @param mapKey
	 * @param data
	 */
	public void putToMap(byte[] mapKey, Map<byte[], byte[]> data) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.hmset(mapKey, data);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 添加到Map结构（key为String）
	 * @param mapKey
	 * @param data
	 */
	public void putStringToMap(String mapKey, Map<String, String> data) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.hmset(mapKey, data);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 从Map结构中获取数据
	 * 
	 * @param mapKey
	 * @param field
	 * @return
	 */
	public Object getFromMap(Object mapKey, Object field) {
		return unserialize(getFromMap(serializable(mapKey), serializable(field)));
	}

	/**
	 * 从Map结构中获取数据
	 * @param mapKey
	 * @param field
	 * @return
	 */
	public byte[] getFromMap(byte[] mapKey, byte[] field) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.hget(mapKey, field);
			returnConnection(conn);
			return data;
		} catch (Exception e) {
			returnBorkenConnection(conn);
			return null;
		}
	}

	/**
	 * 从map中移除记录
	 * 
	 * @param mapKey
	 * @param field
	 */
	public void removeFromMap(Object mapKey, Object field) {
		removeFromMap(serializable(mapKey), serializable(field));
	}

	/**
	 * 从map中移除记录
	 * @param mapKey
	 * @param field
	 */
	public void removeFromMap(byte[] mapKey, byte[] field) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.hdel(mapKey, field);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 添加到sorted set队列，字符串类型
	 * @param setKey
	 * @param value
	 * @param score
	 */
	public void addToSortedSet(String setKey, String value, double score) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.zadd(setKey, score, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 添加到sorted set队列，java序列化对象类型
	 * 
	 * @param setKey
	 * @param value
	 * @param score
	 */
	public void addToSortedSet(Object setKey, Object value, double score) {
		addToSortedSet(serializable(setKey), serializable(value), score);
	}

	/**
	 * 添加到sorted set队列，字节类型
	 * @param setKey
	 * @param value
	 * @param score
	 */
	public void addToSortedSet(byte[] setKey, byte[] value, double score) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.zadd(setKey, score, value);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 批量添加到sorted set队列，字符串类型
	 * @param setKey
	 * @param valueMap
	 */
	public void addToSortedSet(String setKey, Map<Double, String> valueMap) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.zadd(setKey, valueMap);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 批量添加到sorted set队列，java序列化类型
	 * @param setKey
	 * @param valueMap
	 */
	public void addToSortedSet(Object setKey, Map<Double, Object> valueMap) {
		Set<Double> keys = valueMap.keySet();
		Map<Double, byte[]> buf = new HashMap<Double, byte[]>();
		if (keys != null && keys.size() > 0) {
			for (Double key : keys) {
				buf.put(key, serializable(valueMap.get(key)));
			}
			addToSortedSet(serializable(setKey), buf);
		}
	}

	/**
	 * 批量添加到sorted set队列，字节类型
	 * @param setKey
	 * @param valueMap
	 */
	public void addToSortedSet(byte[] setKey, Map<Double, byte[]> valueMap) {
		Jedis conn = getConnection();
		if (conn == null) {
			return;
		}
		try {
			conn.zadd(setKey, valueMap);
			returnConnection(conn);
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
	}

	/**
	 * 从sorted set中获取一定范围的段，按score从低到高
	 * @param sortKey
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<String> getSortedSetRange(String sortKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.zrange(sortKey, start, start + size - 1);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}

	/**
	 * 从sorted set中获取一定范围的段，按score从高到低
	 * @param sortKey
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<String> getSortedSetRangeReverse(String sortKey, int start,
			int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<String> result = conn.zrevrange(sortKey, start, start + size
					- 1);
			
			
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}
	
	/**
	 * 返回有序SET里成员的值
	 * @param key    set对应的key
	 * @param member 成员名称
	 * @return
	 */
	public Double getScore(String key, String member){
		
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Double score = conn.zscore(key, member);
			returnConnection(conn);
			return score;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}
	
	/**
	 * 返回有序SET里成员的排名
	 * @param key
	 * @param member
	 * @return
	 */
	public Long  getSortByMember(String key, String member){

		
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Long sorted = conn.zrevrank(key, member);
			sorted = sorted+1; //排名是从0开始
			returnConnection(conn);
			return sorted;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	
	}

	/**
	 * 从sorted set中获取一定范围的段，字节类型，按score从低到高
	 * @param sortKey
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<byte[]> getSortedSetRange(byte[] sortKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<byte[]> result = conn.zrange(sortKey, start, start + size - 1);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}

	/**
	 * 从sorted set中获取一定范围的段，字节类型，按score从高到低
	 * @param sortKey
	 * @param start
	 * @param size
	 * @return
	 */
	public Set<byte[]> getSortedSetRangeReverse(byte[] sortKey, int start, int size) {
		Jedis conn = getConnection();
		if (conn == null) {
			return null;
		}
		try {
			Set<byte[]> result = conn.zrevrange(sortKey, start, start + size - 1);
			returnConnection(conn);
			return result;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return null;
	}

	/**
	 * 根据score从sorted set中移除记录
	 * @param keySet
	 * @param score
	 */
	public long removeFromSortedSetByScore(String keySet, double score) {
		Jedis conn = getConnection();
		if (conn == null) {
			return 0;
		}
		try {
			long cnt = conn.zremrangeByScore(keySet, score, score);
			returnConnection(conn);
			return cnt;
		} catch (Exception e) {
			returnBorkenConnection(conn);
		}
		return 0;
	}

	/**
	 * map数据序列化转换
	 * @param data
	 * @return
	 */
	public static Map<byte[], byte[]> serializeMap(Map<Object, Object> data) {
		Map<byte[], byte[]> result = new HashMap<byte[], byte[]>();
		try {
			Set<Object> keys = data.keySet();
			if (keys != null && keys.size() > 0) {
				for (Object key : keys) {
					result.put(serializable(key), serializable(data.get(key)));
				}
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 序列化处理
	 * @param obj
	 * @return
	 */
	public static byte[] serializable(Object obj) {
		if (obj == null) {
			return null;
		}
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
		} finally {
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(baos);
		}
		return null;
	}

	/**
	 * 反序列化处理
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(bais);
		}
		return null;
	}
}
