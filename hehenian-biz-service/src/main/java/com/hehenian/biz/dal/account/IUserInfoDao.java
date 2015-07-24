/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.account
 * @Title: IUserInfoDao.java
 * @Description: 用户基本信息操作入口 替代IUserDao
 *
 * @author: zhanbmf
 * @date 2015-3-29 下午12:08:04
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.account;

import java.util.List;
import java.util.Map;

public interface IUserInfoDao {

	/**
	 * 增删改
	 * 
	 * @param sql
	 * @return
	 */
	public abstract int update(String sql);

	/**
	 * 批量增删改
	 * 
	 * @param sqls
	 * @return
	 */
	public abstract <T> int[] batchUpdate(String[] sqls);

	/**
	 * 查询结果返回list<Map>
	 * 
	 * @param sql
	 * @param isRealTime (true master|false slave)
	 * @return
	 */
	public abstract List<Map<String, Object>> queryToListMap(String sql, boolean isRealTime);

	/**
	 * 查询结果返回list<object>
	 * 
	 * @param sql
	 * @param clazz
	 * @param isRealTime (true master|false slave)
	 * @return
	 */
	public abstract <T> List<T> queryToListObject(String sql, Class<T> clazz, boolean isRealTime);

	/**
	 * 根据sql返回int数据
	 * 
	 * @param sql
	 * @param clazz
	 * @param isRealTime (true master|false slave)
	 * @return
	 */
	public abstract int queryForInt(String sql, boolean isRealTime);

}