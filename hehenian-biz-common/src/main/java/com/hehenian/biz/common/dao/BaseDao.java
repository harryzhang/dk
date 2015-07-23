
package com.hehenian.biz.common.dao;  

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


/** 
* @ClassName: BaseDao 
* @Description: 该类是具体
* @author yeqiang
* @param <T> 
*/
public interface BaseDao<T>{
	
	/** 
	* 保存bean对象到数据库  返回主键  用于分表
	* @param entity 泛型类，当时机创建的时候制定
	* @param entitySuffix 表后缀
	* @return 返回存入数据的对象的主键值
	*/
	Integer insertObject(JdbcTemplate template, T entity, String entitySuffix) throws SQLException;
	
	/**
	 * 保存bean对象到数据库  不返回主键   用于分表
	 * @param template
	 * @param entity    待保存的实体类
	 * @param entitySuffix   分表序列号
	 * @return  不返回插入对象主键
	 * @throws SQLException
	 */
	boolean insertObjectNotRtnKey(JdbcTemplate template, T entity, String entitySuffix) throws SQLException;
	
	/**
	 * 保存bean对象到数据库   返回主键   用于单表
	 * @param template  jdbc
	 * @param entity  待保存的实体对象
	 * @return  返回插入对象主键
	 */
	Integer insertObject(JdbcTemplate template, T entity) throws SQLException;
	
	/**
	 * 保存bean对象到数据库  不返回主键    用于单表
	 * @param template jdbc
	 * @param entity 带保存的实体对象
	 * @return  不返回插入对象主键
	 */
	boolean  insertObjectNotRtnKey(JdbcTemplate template, T entity) throws SQLException;;
	
	/**
	 * @Title insertBySql 
	 * @Description 将数据保存到数据库 
	 * @param sql sql插入语句
	 * @return  返回存入数据对象主键值
	 * @throws SQLException
	 */
	Integer insertBySql(JdbcTemplate template,final String sql) throws SQLException; 
	public boolean insertBySqlNotRtnKey(JdbcTemplate template,final String sql) throws SQLException;
	
	
	/** 
	* @Title queryObject 查询SQL语句返回一个对象的接口方法
	* @Description 从数据库中获取某个对象可调用的接口方法：
	* @param sql String类型，一个‘？’号的sql语句
	* @param obj 查询条件，一个Object[]数组
	* @return 返回对应的对象
	 * @throws SQLException 
	*/
	T queryObject(JdbcTemplate template, String sql, Object[] obj) throws SQLException;
	
	/** 
	* @Title queryObject 查询SQL语句返回一个对象的接口方法
	* @Description 从数据库中获取某个对象可调用的接口方法：
	* @param sql String类型，一个‘？’号的sql语句
	* @param obj 查询条件，一个Object[]数组
	* @return 返回对应的对象
	 * @throws SQLException 
	*/
	T queryObject(JdbcTemplate jdbcTemplate, String sql, Object[] obj, RowMapper<T> rm) throws SQLException;
	
	/**
	 * 带参数查询SQL返回Map接口方法
	 * @param jdbcTemplate  
	 * @param sql   
	 * @param paramsMap
	 * @return
	 * @throws SQLException
	 */
	Map<String,Object> queryForMap(NamedParameterJdbcTemplate jdbcTemplate, String sql, 
			Map<String, Object> paramsMap) throws SQLException;
	
	/**
	 * 带参数查询SQL返回Map接口方法
	 * @param jdbcTemplate  
	 * @param sql   
	 * @param paramsMap
	 * @return
	 * @throws SQLException
	 */
	List<Map<String,Object>> queryForMap(JdbcTemplate jdbcTemplate, String sql, 
			Object[] params) throws SQLException;
	
	/** 
	* @Title queryObject 
	* @Description 从数据库中获取某个对象可调用的接口方法：
	* @param sql 具体的sql语句
	* @return 返回一个泛型对象，需要值实际调用中指定，如果不指定则返回一个Object对象
	*/
	T queryObject(JdbcTemplate template, String sql) throws SQLException;
	
	/** 
	* @Title query 
	* @Description 根据查询条件从数据库中获取结果列表可调用的接口方法：
	* @param sql sql语句带？号的形式
	* @param obj 参数数组表
	* @return 返回一个泛型的List对象，需要在实际调用中指定具体泛型值，如果不指定则返回一个Object类型的List列表
	*/
	List<T> queryList(JdbcTemplate template, String sql, Object[] obj) throws SQLException;
	
	/** 
	* @Title query 
	* @Description 根据查询条件从数据库中获取结果列表可调用的接口方法：
	* @param sql sql语句带？号的形式
	* @param obj  参数数组表
	* @param maxValue 查询最大值
	* @return返回一个泛型的List对象，需要在实际调用中指定具体泛型值，如果不指定则返回一个Object类型的List列表
	* @throws SQLException
	*/
	List<T> queryList(JdbcTemplate template, String sql, Object[] obj, Integer maxValue) throws SQLException;
	
	/** 
	* @Title query 
	* @Description 根据查询条件从数据库中获取结果列表可调用的接口方法：
	* @param sql sql语句
	* @return 返回一个泛型的List对象，需要在实际调用中指定具体泛型值，如果不指定则返回一个Object类型的List列表
	*/
	List<T> queryList(JdbcTemplate template, String sql) throws SQLException;
	
	/**
	 * 根据查询条件从数据库中获取结果列表可调用的接口方法 返回List<MAP<String, Object>>：
	 * @param jdbcTemplate
	 * @param sql
	 * @param paramsMap
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> queryMapList(NamedParameterJdbcTemplate jdbcTemplate, String sql, Map<String, Object>paramsMap)
	     throws SQLException;
	
	/** 
	* @Title queryString 
	* @Description 查询结果返回String集合
	* @param sql sql语句
	* @return
	* @throws 返回一个String类型的List
	*/
	List<String> queryString(JdbcTemplate template, String sql) throws SQLException;
	
	/** 
	* @Title queryInteger 
	* @Description 查询返回Integer集合
	* @param sql sql语句
	* @return
	* @throws 返回一个Integer类型的List
	*/
	List<Integer> queryInteger(JdbcTemplate template, String sql) throws SQLException;
	
	/** 
	* @Title queryLong 
	* @Description 查询返回Long集合
	* @param sql sql语句
	* @return
	* @throws 返回一个Long类型的List
	*/
	List<Long> queryLong(JdbcTemplate template, String sql) throws SQLException;
	
	/** 
	* @Title queryLong 
	* @Description 查询返回Double集合
	* @param sql sql语句
	* @return
	* @throws 返回一个Double类型的List
	*/
	List<Double> queryDouble(JdbcTemplate template, String sql) throws SQLException;
	
	/** 
	* @Title modify 
	* @Description 修改数据所调用的方法，已sql带问号形式， 
	* @param sql 带问号的sql 
	* @param obj 参数列表
	* @return 返回更新的影响结果集
	*/
	
	Integer modify(JdbcTemplate template, String sql, Object[] obj) throws SQLException;
	
	/** 
	* @Title modify 
	* @Description 修改数据库中某条记录操作可调用的接口方法：
	* @param sql 更新用的sql语句
	* @return 返回共影响的结果条数
	*/
	
	Integer modify(JdbcTemplate template, String sql) throws SQLException;
	
	/** 
	* @Title delete 
	* @Description 删除数据库中某条记录的需要调用的接口方法：
	* @param sql sql语句：带？号
	* @param obj 查询条件数组
	* @return 返回共影响的结果条数
	*/
	
	Integer delete(JdbcTemplate template, String sql, Object[] obj) throws SQLException;
	
	/** 
	* @Title delete 
	* @Description 删除数据库中某条记录需要调用的接口方法：
	* @param sql sql语句
	* @return 返回共影响的结果条数
	*/
	
	Integer delete(JdbcTemplate template, String sql) throws SQLException;
	
	/** 
	* @Title queryCount 
	* @Description 根据传入的对象获取数据库与之对应的表的总条数可调用的接口方法，不适用于夺标查询的情况
	* @param t 泛型对象，在实际调用的制定，不能传入Object基类作为参数值
	* @return 返回该对象与之对应的数据库表的总条数
	*/
	Integer queryCount(JdbcTemplate template, T t) throws SQLException;
	
	/**
	 * 
	 * @param template
	 * @param t
	 * @param tableIndex
	 * @return
	 * @throws SQLException
	 */
	Integer queryCount(JdbcTemplate template, T t, int tableIndex) throws SQLException;
	
	/** 
	* @Title queryCount 
	* @Description 根据条件获取总条数可调用的接口方法
	* @param sql sql语句
	* @param obj 查询条件的参数列表
	* @return 返回一个Long类型的总条数结果集
	*/
	Integer queryCount(JdbcTemplate template, String sql, Object[] obj) throws SQLException;
	
	/** 
	* @Title queryCount 
	* @Description  根据条件获取总条数可调用的接口方法
	* @param sql sql语句
	* @return 返回Long类型的总条数结果集
	*/
	Integer queryCount(JdbcTemplate template, String sql) throws SQLException;
	/**
	 * @Title getPage
	 * @Description 根据sql语句和查询条件返回jsonPage<T>对象，sql语句不需要指定limit返回结果集范围，已冲内部实现
	 * @param sql 普通的sql语句 带问号
	 * @param obj 查询条件
	 * @param pageNow 当前页码
	 * @param pageSize 每页结果条数
	 * @return 返回一个JsonPage<T>对象
	 * @throws SQLException 跑出SQL异常
	 */
	JsonPage<T> getPage(JdbcTemplate template, String sql, Object[] obj, Integer pageNow,
			Integer pageSize) throws SQLException;

	/**
	 * @Title getPage
	 * @Description 根据sql语句和查询条件返回jsonPage<T>对象，sql语句不需要指定limit返回结果集范围，已冲内部实现
	 * @param sql 普通的sql语句 带问号
	 * @param pageNow 当前页码
	 * @param pageSize 每页结果条数
	 * @return 返回一个JsonPage<T>对象
	 * @throws SQLException 跑出SQL异常
	 */
	JsonPage<T> getPage(JdbcTemplate template, String sql, Integer pageNow, Integer pageSize) throws SQLException;
 }
  