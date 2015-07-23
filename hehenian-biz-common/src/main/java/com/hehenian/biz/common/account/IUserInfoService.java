/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.account
 * @Title: IUserInfoService.java
 * @Description: TODO
 *
 * @author: zhanbmf
 * @date 2015-3-29 下午1:55:45
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.account;

import java.util.List;
import java.util.Map;

public interface IUserInfoService {

	/**
	 * 根据用户id获得指定的类的内容
	 * @param id           用户id
	 * @param clazz        类名
	 * @param isRealTime   是否实时
	 * @return 查询的指定实体
	 */
	public <T> T get(int id, Class<T> clazz, boolean isRealTime);

	/**
	 * 根据用户电话号码、邮箱等获得指定的类的内容
	 * @param loginInfo    登陆信息
	 * @param clazz        类名
	 * @param isRealTime   是否实时
	 * @return
	 */
	public <T> T getByLoginInfo(String loginInfo, Class<T> clazz, boolean isRealTime);

	/**
	 * 将实体插入到数据库
	 * @param t     指定类
	 * @return      成功1,失败0
	 */
	public <T> int insert(T t);

	/**
	 * 将实体更新到数据库
	 * @param t      指定类
	 * @return       成功1,失败0
	 */
	public <T> int update(T t, boolean clearCache);

	/**
	 * 更新联合主键的其中一个/多个的值
	 * @param dest
	 * @param src
	 * @return
	 */
	public <T> int updateKeys(T dest,T src, boolean clearCache);
	
	/**
	 * 根据实体(主键一致删除,如果是联合主键,必须全部提供)删除数据库对象
	 * @param t
	 * @return
	 */
	public <T> int deleteByKeys(T t);

	/**
	 * 将实体插入到数据库,假如已经存在即更新
	 * @param t  指定类
	 * @return   成功1,失败0
	 */
	public <T> int insertOrUpdate(T t, boolean clearCache);

	/**
	 * 根据用户id删除内容
	 * @param   id         用户id
	 * @param   clazz      指定类
	 * @return             成功1,失败0
	 */
	public <T> int delete(int id, Class<T> clazz);

	/**
	 * 根据指定id集合获取List集合
	 * @param ids          指定id
	 * @param clazz        指定类
	 * @param isRealTime   是否实时
	 * @return
	 */
	public <T> List<T> getList(int[] ids, Class<T> clazz, boolean isRealTime);

	/**
	 * 根据指定电话号码、邮件等集合获取Map<loginInfo,T>集合
	 * @param loginInfos      登陆集合
	 * @param clazz           指定类
	 * @param isRealTime      是否实时
	 * @return
	 */
	public <T> Map<String, T> getListByLoginInfo(String[] loginInfo, Class<T> clazz, boolean isRealTime);

	/**
	 * 批量插入
	 * @param list       对象集合
	 * @return           map,key:用户id,value:成功1,失败0
	 */
	public <T> Map<Integer, Integer> insertList(List<T> list);

	/**
	 * 批量更新
	 * @param list      对象集合
	 * @return          map,key:用户id,value:成功1,失败0
	 */
	public <T> Map<Integer, Integer> updateList(List<T> list, boolean clearCache);

	/**
	 * 批量删除
	 * @param list      对象集合
	 * @return          map,key:用户id,value:成功1,失败0
	 */
	public <T> Map<Integer, Integer> deleteList(List<T> list);

	/**
	 * 查询结果返回list<Map>
	 * @param sql            指定sql
	 * @param id             用户id,用于生成和替代sql中的表名
	 * @param isRealTime     是否实时
	 * @return 返回list<Map>
	 */
	public List<Map<String, Object>> queryToListMap(String sql, int id, boolean isRealTime);

	/**
	 * 查询结果返回list<T>
	 * @param sql         指定sql
	 * @param id          用户id,用于生成和替代sql中的表名
	 * @param clazz       指定类
	 * @param isRealTime  是否实时
	 * @return 返回list<T>
	 */
	public <T> List<T> queryToListObject(String sql, int id, Class<T> clazz, boolean isRealTime);
	
	/**
	 * 查询结果返回数量
	 * @param sql             指定sql
	 * @param id              用户id,用于生成和替代sql中的表名
	 * @param clazz           指定类
	 * @param isRealTime      是否实时
	 * @return                返回数量,-1为出错,其他值为正常值
	 */
	public <T> Integer queryCount(String sql, int id, Class<T> clazz, boolean isRealTime);

}
