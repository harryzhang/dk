/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade.impl
 * @Title: AbstractBaseDaoImpl.java
 * @Description: BaseDao impl
 * @author: zhanbmf
 * @date 2015年03月15日 上午11:09:16
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.hehenian.biz.common.dao.BaseDao;
import com.hehenian.biz.common.dao.JsonPage;
import com.mysql.jdbc.Statement;

public abstract class AbstractBaseDaoImpl<T> implements BaseDao<T> {

	private Logger log = Logger.getLogger(this.getClass().getName());
	private T entity;
	private String entitySuffix;
	
	private String getSafeSql(String sql) {
		return StringEscapeUtils.escapeSql(sql);
	}

	@Override
	public Integer insertObject(JdbcTemplate template, T entity,
			String entitySuffix) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (StringUtils.isNotBlank(entitySuffix)) {
			entitySuffix = "_" + entitySuffix;
		}
		template.update(this.getPreparedStatementCreator(entity, entitySuffix),
				keyHolder);
		return  keyHolder.getKey().intValue();
	}
	
	@Override
	public boolean insertObjectNotRtnKey(JdbcTemplate template, T entity, String entitySuffix){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (StringUtils.isNotBlank(entitySuffix)) {
			entitySuffix = "_" + entitySuffix;
		}
		int effectRow = template.update(this.getPreparedStatementCreator(entity, entitySuffix),
				keyHolder);
		return effectRow > 0 ? true: false;
	}
	
	@Override
	public Integer insertObject(JdbcTemplate template, T entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(this.getPreparedStatementCreator(entity, ""), keyHolder);
		return  keyHolder.getKey().intValue();
	}
    
	@Override
	public boolean  insertObjectNotRtnKey(JdbcTemplate template, T entity){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int effectRow = template.update(this.getPreparedStatementCreator(entity, ""), keyHolder);
		return  effectRow >0 ? true: false;
	}
	
	@Override
	public Integer insertBySql(JdbcTemplate jdbcTemplate, final String sql)
			throws SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator(){

			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				return ps;
			}
			
		}, keyHolder);
		Number key = keyHolder.getKey();
		if(key != null){
			return key.intValue();
		}
		return 0;
	}
	
	@Override
	public boolean insertBySqlNotRtnKey(JdbcTemplate jdbcTemplate,final String sql) {
		
		int res = 0;
		
		try {
			res = jdbcTemplate.update(sql);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return res>0;
		
	}

	@Override
	public T queryObject(JdbcTemplate jdbcTemplate, String sql, Object[] obj)
			throws SQLException {
		List<T> list = jdbcTemplate.query(getSafeSql(sql), obj, this
				.getRowMapper());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return entity;
		}
	}
	
	@Override
	public T queryObject(JdbcTemplate jdbcTemplate, String sql, Object[] obj, RowMapper<T> rm)
			throws SQLException {
		List<T> list = jdbcTemplate.query(getSafeSql(sql), obj, rm);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return entity;
		}
	}
	
	@Override
	public Map<String,Object> queryForMap(NamedParameterJdbcTemplate jdbcTemplate, String sql, 
			Map<String, Object> paramsMap){
		if(paramsMap==null || paramsMap.size()<1){
			return null;
		}
		return jdbcTemplate.queryForMap(sql, paramsMap);
	}
	
	@Override
	public List<Map<String,Object>> queryForMap(JdbcTemplate jdbcTemplate, String sql, 
			Object[] params){
		if(params==null || params.length<1){
			return jdbcTemplate.queryForList(sql);
		}
		return jdbcTemplate.queryForList(sql, params);
	}
	
	@Override
	public T queryObject(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		List<T> list = jdbcTemplate.query(getSafeSql(sql), this.getRowMapper());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return entity;
		}
	}

	@Override
	public List<T> queryList(JdbcTemplate jdbcTemplate, String sql,
			Object[] obj, Integer maxValue) throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append(getSafeSql(sql));
		builder.append(" limit 0, ");
		builder.append(maxValue);
		return jdbcTemplate.query(builder.toString(), obj, this.getRowMapper());
	}

	@Override
	public List<T> queryList(JdbcTemplate jdbcTemplate, String sql, Object[] obj)
			throws SQLException {
		return jdbcTemplate.query(getSafeSql(sql), obj, this.getRowMapper());
	}
	
	@Override
	public List<T> queryList(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		return jdbcTemplate.query(getSafeSql(sql), this.getRowMapper());
	}

	@Override
	public List<Map<String, Object>> queryMapList(NamedParameterJdbcTemplate jdbcTemplate, 
			String sql, Map<String, Object>paramsMap){
		return jdbcTemplate.queryForList(sql, paramsMap);
	}

	@Override
	public List<String> queryString(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		return jdbcTemplate.query(getSafeSql(sql), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int index) throws SQLException {
				return rs.getString(1);
			}
		});
	}

	@Override
	public List<Integer> queryInteger(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		return jdbcTemplate.query(getSafeSql(sql), new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int index) throws SQLException {
				return rs.getInt(1);
			}
		});
	}

	@Override
	public List<Long> queryLong(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		return jdbcTemplate.query(getSafeSql(sql), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int index) throws SQLException {
				return rs.getLong(1);
			}
		});
	}

	@Override
	public List<Double> queryDouble(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		return jdbcTemplate.query(getSafeSql(sql), new RowMapper<Double>() {
			@Override
			public Double mapRow(ResultSet rs, int index) throws SQLException {
				return rs.getDouble(1);
			}
		});
	}

	@Override
	public Integer modify(JdbcTemplate jdbcTemplate, String sql, Object[] obj)
			throws SQLException {
		return jdbcTemplate.update(getSafeSql(sql), obj);
	}

	@Override
	public Integer modify(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		return jdbcTemplate.update(getSafeSql(sql));
	}

	@Override
	public Integer delete(JdbcTemplate jdbcTemplate, String sql, Object[] obj)
			throws SQLException {
		return jdbcTemplate.update(getSafeSql(sql), obj);
	}

	@Override
	public Integer delete(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		return jdbcTemplate.update(getSafeSql(sql));
	}

	@Override
	public Integer queryCount(JdbcTemplate jdbcTemplate, T t, int tableIndex)
			throws SQLException {
		String sql = "select count(1) from `" + t.getClass().getSimpleName()
				+ "_" + tableIndex + "` as tt";
		return jdbcTemplate.queryForInt(getSafeSql(sql));
	}

	@Override
	public Integer queryCount(JdbcTemplate jdbcTemplate, T t) throws SQLException {
		String sql = "select count(1) from `" + t.getClass().getSimpleName()
				+ "` as tt";
		return jdbcTemplate.queryForInt(getSafeSql(sql));
	}

	@Override
	public Integer queryCount(JdbcTemplate jdbcTemplate, String sql, Object[] obj)
			throws SQLException {
		return jdbcTemplate.queryForInt(getSafeSql(sql), obj);
	}

	@Override
	public Integer queryCount(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		return jdbcTemplate.queryForInt(getSafeSql(sql));
	}

	public abstract RowMapper<T> getRowMapper();

	public PreparedStatementCreator getPreparedStatementCreator(T entityParam,
			String suffix) {
		this.setEntity(entityParam);
		this.setEntitySuffix(suffix);
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement prepStatement = null;
				if (entity != null) {
					String InsertSql = "INSERT INTO `{?TABLE?}` ({?FIELDS?}) VALUES ({?VALUES?})";
					String tableName;
					if (entitySuffix.isEmpty() || entitySuffix == null) {
						tableName = entity.getClass().getSimpleName();
					} else {
						tableName = entity.getClass().getSimpleName()
								+ entitySuffix;
					}
					String fieldsL = "";
					String valuesL = "";
					Method[] methods = entity.getClass().getMethods();
					String[] fields = new String[methods.length / 2];
					Object[] typeValue = new Object[methods.length / 2];
					int j = 0;
					for (int i = 0; i < methods.length; i++) {
						String str = methods[i].getName();
						if (str.startsWith("set")) {
							fields[j] = str.substring(3, 4).toLowerCase()
									+ str.substring(4);
							typeValue[j] = methods[i].getParameterTypes()[0];
							j++;
						}
					}
					for (int i = 0; i < fields.length; i++) {
						// Field f = fields[i];
						if (fields[i] == null) {
							break;
						}
						fieldsL += "`" + fields[i] + "`,";
						valuesL += "?,";
					}
					InsertSql = InsertSql.replace("{?TABLE?}", tableName);
					InsertSql = InsertSql.replace("{?FIELDS?}", fieldsL
							.substring(0, fieldsL.length() - 1));
					InsertSql = InsertSql.replace("{?VALUES?}", valuesL
							.substring(0, valuesL.length() - 1));
					prepStatement = conn.prepareStatement(InsertSql,
							Statement.RETURN_GENERATED_KEYS);
					prepStatement = this.initPrepStatement(fields, typeValue,
							entity, prepStatement);
				}
				return prepStatement;
			}

			private PreparedStatement initPrepStatement(String[] fieldsName,
					Object[] typeValue, T entity,
					PreparedStatement prepStatement) {
				for (int i = 0; i < fieldsName.length; i++) {
					try {
						// Field field =
						// entity.getClass().getField(fields[i]);
						if (fieldsName[i] == null) {
							break;
						}
						String getterName = "";
						if (typeValue[i] instanceof Boolean
								|| typeValue[i].getClass() == boolean.class) {
							getterName = "is"
									+ fieldsName[i].substring(0, 1)
											.toUpperCase()
									+ fieldsName[i].substring(1);
						} else {
							getterName = "get"
									+ fieldsName[i].substring(0, 1)
											.toUpperCase()
									+ fieldsName[i].substring(1);
						}
						if (getterName != null && !getterName.isEmpty()) {
							Method gm = entity.getClass().getMethod(getterName);
							Object val = gm.invoke(entity, new Object[0]);
							prepStatement.setObject(i + 1, val);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return prepStatement;
			}
		};
		return psc;
	}

	@SuppressWarnings( { "unchecked", "unused" })
	private Object refectSetValue(ResultSet rs, T entity) {
		Object obj = null;
		try {
			obj = entity.getClass().newInstance();
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		Field[] fields = entity.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				Object value = rs.getObject(fieldName);
				if (value != null) {
					this.invokeSet((T) obj, fieldName, value);
				}
			}
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	private Method getGetMethod(Class objectClass, String fieldName) {
		StringBuffer sb = new StringBuffer();
		sb.append("get");
		sb.append(fieldName.substring(0, 1).toUpperCase());
		sb.append(fieldName.substring(1));
		try {
			return objectClass.getMethod(sb.toString());
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Method getSetMethod(Class objectClass, String fieldName) {
		try {
			Class[] parameterTypes = new Class[1];
			Field field = objectClass.getDeclaredField(fieldName);
			parameterTypes[0] = field.getType();
			StringBuffer sb = new StringBuffer();
			sb.append("set");
			sb.append(fieldName.substring(0, 1).toUpperCase());
			sb.append(fieldName.substring(1));
			Method method = objectClass
					.getMethod(sb.toString(), parameterTypes);
			return method;
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return null;
	}

	private void invokeSet(T t, String fieldName, Object value) {
		Method method = getSetMethod(t.getClass(), fieldName);
		try {
			method.invoke(t, new Object[] { value });
		} catch (Exception e) {
			e.printStackTrace();
			log.error("invokeSet", e);
		}
	}

	@SuppressWarnings("unused")
	private Object invokeGet(final T t, final String fieldName) {
		Method method = getGetMethod(t.getClass(), fieldName);
		try {
			return method.invoke(t, new Object[0]);
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
		return null;
	}

	@Override
	public JsonPage<T> getPage(JdbcTemplate jdbcTemplate, String sql,
			Object[] obj, Integer pageNow, Integer pageSize)
			throws SQLException {

		JsonPage<T> page = null;
		if (!sql.isEmpty() && obj != null && pageNow > 0 && pageSize > 0) {
			page = new JsonPage<T>();
			page.setPageNow(pageNow);
			page.setPageSize(pageSize);
			page.setTotalSize(getCount(jdbcTemplate, getSafeSql(sql), obj));
			page.setList(getList(jdbcTemplate, getSafeSql(sql), obj, pageNow,
					pageSize));
		}
		return page;
	}

	@Override
	public JsonPage<T> getPage(JdbcTemplate jdbcTemplate, String sql,
			Integer pageNow, Integer pageSize) throws SQLException {
		JsonPage<T> page = null;
		if (!sql.isEmpty() && pageNow > 0 && pageSize > 0) {
			page = new JsonPage<T>();
			page.setPageNow(pageNow);
			page.setPageSize(pageSize);
			page.setTotalSize(getCount(jdbcTemplate, getSafeSql(sql)));
			page.setList(getList(jdbcTemplate, getSafeSql(sql), pageNow,
					pageSize));
		}
		return page;
	}

	private List<T> getList(JdbcTemplate jdbcTemplate, String sql,
			Object[] obj, Integer pageNow, Integer pageSize)
			throws SQLException {
		List<T> list = new ArrayList<T>();
		try {
			if (!sql.isEmpty() && pageNow != null) {
				sql = "select * from (" + sql + ") as zzzz limit "
						+ (pageNow - 1) * pageSize + " ," + pageSize;
				list = jdbcTemplate.query(getSafeSql(sql), obj, this
						.getRowMapper());
			}
		} catch (Exception e) {
			log.error(sql, e);
		}
		return list;
	}

	private List<T> getList(JdbcTemplate jdbcTemplate, String sql,
			Integer pageNow, Integer pageSize) throws SQLException {
		List<T> list = new ArrayList<T>();
		try {
			if (!sql.isEmpty()) {
				sql =   sql + " limit "
						+ (pageNow - 1) * pageSize + " ," + pageSize;
				list = jdbcTemplate.query(getSafeSql(sql), this.getRowMapper());
			}
		} catch (Exception e) {
			log.error(sql, e);
		}
		return list;
	}

	private Integer getCount(JdbcTemplate jdbcTemplate, String sql, Object[] obj)
			throws SQLException {
		Integer count = 0;
		try {
			if (!sql.isEmpty() && obj != null) {
				sql = "select count(1) from (" + sql + ") as zzzz";
				count = jdbcTemplate.queryForInt(sql, obj);
			}
		} catch (Exception e) {
			log.error(sql, e);
		}
		return count;
	}

	private Integer getCount(JdbcTemplate jdbcTemplate, String sql)
			throws SQLException {
		Integer count = 0;
		try {
			if (!sql.isEmpty()) {
				sql = "select count(1) from (" + sql + ") as zzzz";
				count = jdbcTemplate.queryForInt(sql);
			}
		} catch (Exception e) {
			log.error(sql, e);
		}
		return count;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public String getEntitySuffix() {
		return entitySuffix;
	}

	public void setEntitySuffix(String entitySuffix) {
		this.entitySuffix = entitySuffix;
	}
}