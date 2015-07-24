/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.service.account
 * @Title: UserInfoServiceImpl.java
 * @Description: 用户信息服务接口 替代UserServiceImpl
 *
 * @author: zhanbmf
 * @date 2015-3-29 下午2:06:47
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.service.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.IUserInfoService;
import com.hehenian.biz.common.account.dataobject.LoginInfoRelate;
import com.hehenian.biz.common.util.SqlUtils;
import com.hehenian.biz.common.util.UserHelper;
import com.hehenian.biz.dal.account.IUserInfoDao;
import com.hehenian.common.redis.CacheService;

@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {
	private static Logger logger = Logger.getLogger("user");

	@Autowired
	private IUserInfoDao userInfoDao;

	@Autowired
	private CacheService redisCacheService;

	private static final String IN_CACHE = "in cache";
	private static final String NOT_USE_CACHE = "not use cache";
	private static final String DELETE_CACHE = "delete cache";
	private static final String DELETE_ALL_CACHE = "delete all cache";
	private static final String ERROR = "error";
	private static final String SUCCESS = "success";
	private static final String NULL = "get null";
	private final int expireTime = 60 * 15;

	@Override
	public <T> T get(int userId, Class<T> clazz, boolean isRealTime) {
		long startTime = System.currentTimeMillis();
		String info = NOT_USE_CACHE;
		try {
			if (userId < 0) {
				info = ERROR;
				return null;
			}
			
			// UserXXX是联合主键不能根据userId直接取
			if (clazz.getSimpleName().equalsIgnoreCase("UserXXX")) {
				info = " not provide for UserContact ";
				return null;
			}
			
			// 非实时优先取缓存返回
			if (!isRealTime) {
				T cacheObject = (T) redisCacheService.get().get(clazz.getName() + userId);
				if (cacheObject != null) {
					info = IN_CACHE;
					return cacheObject;
				}
			}
			
			// 查询数据库
			List<T> queryToListObject = userInfoDao.queryToListObject(SqlUtils.getSql(userId, clazz), clazz, isRealTime);
			if (queryToListObject == null || queryToListObject.size() == 0) {
				info = NULL;
				return null;
			}
			
			T result = queryToListObject.get(0);
			// 添加缓存
			redisCacheService.get().set(clazz.getName() + userId, result, expireTime);
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("get() ")
					.append(clazz.getSimpleName()).append(" id=")
					.append(userId).append(",time=").append(endTime)
					.append(",info=").append(info).toString());
		}
	}

	@Override
	public <T> int insert(T t) {
		long startTime = System.currentTimeMillis();
		String info = NOT_USE_CACHE;
		int userId = -1;
		try {
			// 获取用户id用于分表
			userId = getUserIdForAction(t);
			if (userId < 0) {
				info = ERROR;
				return 0;
			}
			// 插入数据库
			int result = userInfoDao.update(SqlUtils.getInsertSql(userId, t));
			if (result != 0) {
				// 成功,马上更新缓存
				info = SUCCESS;
				this.get(userId, t.getClass(), true);
			} else {
				info = ERROR;
			}
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("insert() ")
					.append(t.getClass().getSimpleName()).append(" id=")
					.append(userId).append(",time=").append(endTime)
					.append(",info=").append(info).toString());
		}
	}

	@Override
	public <T> int update(T t, boolean clearCache) {
		long startTime = System.currentTimeMillis();
		String info = NOT_USE_CACHE;
		int userId = -1;
		try {
			// 因为使用邮件手机号码分表,不能直接更新LoginInfoRelate类.只能通过insert+delete
			if (t instanceof LoginInfoRelate) {
				return 0;
			}
			// 获取用户id用于分表
			userId = getUserIdForAction(t);
			if (userId < 0) {
				info = ERROR;
				return 0;
			}
			// 获取表的主键用于设置where语句
			Map<String, String> primaryKeyValue = getPrimaryKeyValue(t);
			// 获取旧数据库对象
			T old = (T) get(getUserIdByQuery(t, true), t.getClass(), true);
			// 比较新旧对象获取更新sql
			String updateSql = SqlUtils.getUpdateSql(userId, primaryKeyValue, old, t);
			// 不需要更新
			if (updateSql == null) {
				info = SUCCESS;
				return 1;
			}
			// 执行更新
			int result = userInfoDao.update(updateSql);
			if (result != 0) {
				// 需要清空缓存防止脏数据(只有客户端更换resin请求才会触发)
				if (clearCache) {
					info = DELETE_ALL_CACHE;
					redisCacheService.clear(t.getClass().getName() + userId);
				}
				
				// 成功,马上更新缓存
				this.get(userId, t.getClass(), true);
				info = SUCCESS;
			} else {
				info = ERROR;
			}
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("update() ")
					.append(t.getClass().getSimpleName()).append(" id=")
					.append(userId).append(",time=").append(endTime)
					.append(",info=").append(info).append(",clearCache=")
					.append(clearCache).toString());
		}
	}

	@Override
	public <T> int updateKeys(T dest, T src, boolean clearCache) {
		long startTime = System.currentTimeMillis();
		String info = NOT_USE_CACHE;
		int userId = -1;
		try {
			// 因为使用邮件手机号码分表,不能直接更新LoginInfoRelate类.只能通过insert+delete
			if (dest instanceof LoginInfoRelate) {
				return 0;
			}
			
			// 获取用户id用于分表
			userId = getUserIdForAction(dest);
			if (userId < 0) {
				info = ERROR;
				return 0;
			}
			
			// 获取表的主键用于设置where语句
			Map<String, String> primaryKeyValue = getPrimaryKeyValue(src);
			// 比较新旧对象获取更新sql
			String updateSql = SqlUtils.getUpdateSql(userId, primaryKeyValue, src, dest);
			// 不需要更新
			if (updateSql == null) {
				info = SUCCESS;
				return 1;
			}
			
			// 执行更新
			int result = userInfoDao.update(updateSql);
			if (result != 0) {
				// 需要清空缓存防止脏数据(只有客户端更换resin请求才会触发)
				if (clearCache) {
					info = DELETE_ALL_CACHE;
					redisCacheService.clear(dest.getClass().getName() + userId);
				}
				
				// 成功,马上更新缓存
				// 添加缓存
				redisCacheService.get().set(getCacheKey(dest), result, expireTime);
				info = SUCCESS;
			} else {
				info = ERROR;
			}
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("updateKeys() ")
					.append(dest.getClass().getSimpleName()).append(" id=")
					.append(userId).append(",time=").append(endTime)
					.append(",info=").append(info).append(",clearCache=")
					.append(clearCache).toString());
		}
	}

	@Override
	public <T> int insertOrUpdate(T t, boolean clearCache) {
		// 查询数据库是否存在
		Object object = get(getUserIdByQuery(t, true), t.getClass(), true);
		if (object == null) {
			return insert(t);
		} else {
			return update(t, clearCache);
		}
	}

	@Override
	public <T> int delete(int id, Class<T> clazz) {
		long startTime = System.currentTimeMillis();
		String info = SUCCESS;
		try {
			int result = userInfoDao.update(SqlUtils.getDeleteSql(id, clazz));
			if (result != 0) {
				info = DELETE_CACHE;
				// 成功,马上删除缓存
				redisCacheService.clear(clazz.getName() + id);
			} else {
				info = ERROR;
			}
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("delete() ")
					.append(clazz.getSimpleName()).append(" id=").append(id)
					.append(",time=").append(endTime).append(",info=")
					.append(info).toString());
		}
	}

	@Override
	public <T> int deleteByKeys(T t) {
		long startTime = System.currentTimeMillis();
		String info = SUCCESS;
		int userId = -1;
		try {
			// 获取用户id用于分表
			userId = getUserIdForAction(t);
			if (userId < 0) {
				return 0;
			}
			// 获取表的主键用于设置where语句
			Map<String, String> primaryKeyValue = getPrimaryKeyValue(t);
			int result = userInfoDao.update(SqlUtils.getDeleteSql(userId, primaryKeyValue, t.getClass()));
			if (result != 0) {
				info = DELETE_CACHE;
				// 成功,马上删除缓存
				redisCacheService.clear(t.getClass().getName() + userId);
			}
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("deleteByKeys() ")
					.append(t.getClass().getSimpleName()).append(" id=")
					.append(userId).append(",time=").append(endTime)
					.append(",info=").append(info).toString());
		}
	}

	@Override
	public <T> List<T> getList(int[] ids, Class<T> clazz, boolean isRealTime) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < ids.length; i++) {
			// 为空也写入返回
			list.add(get(ids[i], clazz, isRealTime));
		}
		return list;
	}

	@Override
	public <T> Map<Integer, Integer> insertList(List<T> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		long startTime = System.currentTimeMillis();
		StringBuilder info = new StringBuilder();
		Class<?> clazz = list.get(0).getClass();
		List<Integer> temp = new ArrayList<Integer>();
		try {
			// 根据userId组装不同的sql
			String[] sqls = new String[list.size()];
			Map<Integer, Integer> result = new HashMap<Integer, Integer>();
			for (int i = 0; i < sqls.length; i++) {
				T t = list.get(i);
				int userId = getUserIdForAction(t);
				sqls[i] = SqlUtils.getInsertSql(userId, t);
				temp.add(userId);
			}
			
			// 批量更新
			int[] batchUpdate = userInfoDao.batchUpdate(sqls);
			if (batchUpdate == null || batchUpdate.length == 0) {
				return null;
			}
			
			// 批量组装结果
			for (int i = 0; i < temp.size(); i++) {
				Integer userId = temp.get(i);
				// 执行成功添加缓存
				if (batchUpdate[i] > 0) {
					this.get(userId, list.get(i).getClass(), true);
					result.put(userId, 1);
					info.append(userId).append(":").append(SUCCESS).append(",");
				} else {
					result.put(userId, 0);
					info.append(userId).append(":").append(ERROR).append(",");
				}
			}
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("insertList() ")
					.append(clazz.getSimpleName()).append(" ids=")
					.append(Arrays.toString(temp.toArray())).append(",time=")
					.append(endTime).append(",info=").append(info).toString());
		}
	}

	@Override
	public <T> Map<Integer, Integer> updateList(List<T> list, boolean clearCache) {
		if (list == null || list.size() == 0) {
			return null;
		}
		long startTime = System.currentTimeMillis();
		StringBuilder info = new StringBuilder();
		Class<?> clazz = list.get(0).getClass();
		List<Integer> temp = new ArrayList<Integer>();
		try {
			// 根据userId组装不同的sql
			String[] sqls = new String[list.size()];
			Map<Integer, Integer> result = new HashMap<Integer, Integer>();
			for (int i = 0; i < sqls.length; i++) {
				T t = list.get(i);
				int userId = getUserIdByQuery(t, true);
				T old = (T) get(userId, list.get(i).getClass(), true);
				sqls[i] = SqlUtils.getUpdateSql(getUserIdForAction(t), getPrimaryKeyValue(t), old, t);
				temp.add(userId);
			}
			
			// 批量更新
			int[] batchUpdate = userInfoDao.batchUpdate(sqls);
			if (batchUpdate == null || batchUpdate.length == 0) {
				return null;
			}
			// 批量组装结果
			for (int i = 0; i < temp.size(); i++) {
				Integer userId = temp.get(i);
				// 执行成功添加缓存
				if (batchUpdate[i] > 0) {
					// 需要清空缓存防止脏数据(只有客户端更换resin请求才会触发)
					if (clearCache) {
						redisCacheService.clear(list.get(i).getClass().getName() + userId);
					}
					this.get(userId, list.get(i).getClass(), true);
					result.put(userId, 1);
					info.append(userId).append(":").append(SUCCESS).append(",");
				} else {
					result.put(userId, 0);
					info.append(userId).append(":").append(ERROR).append(",");
				}
			}
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("updateList() ")
					.append(clazz.getSimpleName()).append(" ids=")
					.append(Arrays.toString(temp.toArray())).append(",time=")
					.append(endTime).append(",info=").append(info).toString());
		}
	}

	@Override
	public <T> Map<Integer, Integer> deleteList(List<T> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		
		long startTime = System.currentTimeMillis();
		String info = SUCCESS;
		Class<?> clazz = list.get(0).getClass();
		List<Integer> temp = new ArrayList<Integer>();
		try {
			// 根据userId组装不同的sql
			String[] sqls = new String[list.size()];
			Map<Integer, Integer> result = new HashMap<Integer, Integer>();
			for (int i = 0; i < sqls.length; i++) {
				int userId = getUserIdByQuery(list.get(i), true);
				sqls[i] = SqlUtils.getDeleteSql(userId, list.get(i).getClass());
				temp.add(userId);
			}
			
			int[] batchUpdate = userInfoDao.batchUpdate(sqls);
			for (int i = 0; i < temp.size(); i++) {
				Integer userId = temp.get(i);
				if (batchUpdate[i] != -1) {
					// 成功,马上删除缓存
					info = DELETE_ALL_CACHE;
					redisCacheService.clear(list.get(i).getClass().getName() + userId);
					result.put(userId, 1);
				} else {
					info = ERROR;
					result.put(userId, 0);
				}
			}
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("deleteList() ")
					.append(clazz.getSimpleName()).append(" ids=")
					.append(Arrays.toString(temp.toArray())).append(",time=")
					.append(endTime).append(",info=").append(info).toString());
		}
	}

	@Override
	public List<Map<String, Object>> queryToListMap(String sql, int id,
			boolean isRealTime) {
		long startTime = System.currentTimeMillis();
		String info = SUCCESS;
		try {
			// 非实时优先取缓存返回
			if (!isRealTime) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				List<Object> cacheObject = redisCacheService.get().getListAll(sql);
				if (cacheObject != null) {
					info = IN_CACHE;
					for (Object object : cacheObject) {
						Map<String, Object> map = (Map<String, Object>) object;
						list.add(map);
					}
					return list;
				}
			}
			
			List<Map<String, Object>> result = userInfoDao.queryToListMap(SqlUtils.getQuerySql(sql, id), isRealTime);
			// 添加缓存
			redisCacheService.get().set(sql, result, expireTime);
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("queryToListMap() ").append(" id=")
					.append(id).append(",sql=").append(sql).append(",time=")
					.append(endTime).append(",info=").append(info).toString());
		}
	}

	@Override
	public <T> List<T> queryToListObject(String sql, int id, Class<T> clazz,
			boolean isRealTime) {
		long startTime = System.currentTimeMillis();
		String info = SUCCESS;
		try {
			// 非实时优先取缓存返回
			if (!isRealTime) {
				List<T> cacheObject = (List<T>) redisCacheService.get().getListAll(sql);
				if (cacheObject != null) {
					info = IN_CACHE;
					return cacheObject;
				}
			}
			List<T> result = userInfoDao.queryToListObject(sql, clazz, isRealTime);
			// 添加缓存
			redisCacheService.get().set(sql, result, expireTime);
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("queryToListObject() ")
					.append(" id=").append(id).append(",sql=").append(sql)
					.append(",time=").append(endTime).append(",info=")
					.append(info).toString());
		}
	}

	@Override
	public <T> Integer queryCount(String sql, int id, Class<T> clazz, boolean isRealTime) {
		long startTime = System.currentTimeMillis();
		String info = SUCCESS;
		try {
			// 非实时优先取缓存返回
			if (!isRealTime) {
				Integer cacheObject = (Integer) redisCacheService.get().get(sql);
				if (cacheObject != null) {
					info = IN_CACHE;
					return cacheObject;
				}
			}
			int result = userInfoDao.queryForInt(sql, isRealTime);
			if (result > 0) {// 执行成功
				// 添加缓存
				redisCacheService.get().set(sql, result, expireTime);
			} else {
				info = ERROR;
			}
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("queryCount() ").append(" id=")
					.append(id).append(",sql=").append(sql).append(",time=")
					.append(endTime).append(",info=").append(info).toString());
		}
	}

	/**
	 * 根据用户邮箱、电话查询用户id
	 * @param loginInfo
	 * @param isRealTime
	 * @return
	 */
	private int getUserIdByOthers(String loginInfo, boolean isRealTime) {
		int tableId = UserHelper.getTableNumberByDigest(loginInfo);
		int result = userInfoDao.queryForInt(SqlUtils.getUserIdFromloginInfo(tableId, loginInfo, LoginInfoRelate.class), isRealTime);
		if (result == -1) {
			logger.info("*******logininfo not exist: " + loginInfo);
		}
		return result;
	}

	@Override
	public <T> T getByLoginInfo(String loginInfo, Class<T> clazz, boolean isRealTime) {
		String sql;
		int id = -1;
		long startTime = System.currentTimeMillis();
		String info = NOT_USE_CACHE;
		try {
			// 需要查询自身
			if (clazz.equals(LoginInfoRelate.class)) {
				// 获取id用于分表
				id = UserHelper.getTableNumberByDigest(loginInfo);
				if (id < 0) {
					return null;
				}
				sql = SqlUtils.getSqlForloginInfo(id, loginInfo, clazz);
			} else {
				// 需要根据loginInfo查询其他的类
				// 从数据库表LoginInfoRelate查询获得用户id
				id = getUserIdByOthers(loginInfo, true);
				// 根据用户id获得查询指定表sql
				sql = SqlUtils.getSql(id, clazz);
			}
			
			// 非实时优先取缓存返回
			if (!isRealTime) {
				T cacheObject = (T) redisCacheService.get().get(clazz.getName() + id);
				if (cacheObject != null) {
					info = IN_CACHE;
					return cacheObject;
				}
			}
			
			// 查询指定类
			List<T> queryToListObject = userInfoDao.queryToListObject(sql, clazz, isRealTime);
			if (queryToListObject == null || queryToListObject.size() == 0) {
				return null;
			}
			
			T result = queryToListObject.get(0);
			// 添加缓存
			redisCacheService.get().set(clazz.getName() + id, result, expireTime);
			return result;
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			logger.info(new StringBuilder("getByLoginInfo() ")
					.append(clazz.getSimpleName()).append(" id=").append(id)
					.append(",time=").append(endTime).append(",info=")
					.append(info).toString());
		}
	}

	@Override
	public <T> Map<String, T> getListByLoginInfo(String[] loginInfo, Class<T> clazz, boolean isRealTime) {
		Map<String, T> map = new HashMap<String, T>();
		for (int i = 0; i < loginInfo.length; i++) {
			map.put(loginInfo[i], getByLoginInfo(loginInfo[i], clazz, isRealTime));
		}
		
		//to do...
		return map;
	}

	/**
	 * 获取用户id,用于进行分表
	 * @param t
	 * @return
	 */
	private <T> int getUserIdForAction(T t) {
		 if (t instanceof LoginInfoRelate) {
			 // LoginInfoRelate 需要由字符串转换为整型
			return UserHelper.getTableNumberByDigest(((LoginInfoRelate) t).getLoginInfo());
		}
		return -1;
	}

	/**
	 * 获取用户id,用于进行分表
	 * @param t
	 * @param isRealTime
	 * @return
	 */
	private <T> int getUserIdByQuery(T t, boolean isRealTime) {
		 if (t instanceof LoginInfoRelate) {
			 // 需要查询LoginInfoRelate才能获得用户id
			return getUserIdByOthers(((LoginInfoRelate) t).getLoginInfo(), isRealTime);
		}
		 
		//to do...
		return -1;
	}

	/**
	 * 获取主键
	 * 
	 * @param t
	 * @return
	 */
	private <T> Map<String, String> getPrimaryKeyValue(T t) {
		Map<String, String> map = new HashMap<String, String>();
		if (t instanceof LoginInfoRelate) {
			if (((LoginInfoRelate) t).getId() > 0) {
				map.put("id", String.valueOf(((LoginInfoRelate) t).getId()));
			}
		} 
		
		//to do...
		return map;
	}

	/**
	 * 获取缓存键
	 * @param clazz
	 * @param t
	 * @return
	 */
	private <T> String getCacheKey(T t) {
		int id = -1;
		if (t instanceof LoginInfoRelate) {
			if (((LoginInfoRelate) t).getId() > 0) {
				id = ((LoginInfoRelate) t).getId();
				return t.getClass().getName() + id;
			}
		} else {
			return "-1";
		}
		
		//to do...
		return t.getClass().getName() + id;
	}
}