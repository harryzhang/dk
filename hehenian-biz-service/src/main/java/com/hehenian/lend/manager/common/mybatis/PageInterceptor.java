package com.hehenian.lend.manager.common.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.hehenian.biz.common.base.dataobject.PageDo;

/**
 * 
 * 
 * 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。 
 * 利用拦截器实现Mybatis分页的原理： 
 * 要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象，Mybatis在执行Sql语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句 
 * 是在Statement之前产生的，所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手。
 * 在Mybatis中Statement语句是通过RoutingStatementHandler对象的 
 * prepare方法生成的。所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法，
 * 然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句，之后再调用 
 * StatementHandler对象的prepare方法，即调用invocation.proceed()。 
 * 对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少，
 * 这是通过获取到了原始的Sql语句后，把它改为对应的统计语句再利用Mybatis封装好的参数和设 
 * 置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。 
 *
 * 通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，
 * 重写sql语句实现物理分页。 
 * 目前支持mysql、oracle和sybase的分页，其它数据库暂不支持。
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {
	
    private static final Log logger = LogFactory.getLog(PageInterceptor.class);
    private static String defaultDialect = "mysql"; // 数据库类型(默认为mysql)
    private static String defaultPageSqlId = ".*Page$"; // 需要拦截的ID(正则匹配)
    private static String dialect = ""; // 数据库类型(默认为mysql)
    private static String pageSqlId = ""; // 需要拦截的ID(正则匹配)
    @SuppressWarnings("unused")
	private static Properties properties;

    @SuppressWarnings("rawtypes")
	@Override
    public Object intercept(Invocation invocation) throws Throwable {
    	System.out.println("*************************************");
    	System.out.println();
    	System.out.println("page interceptor");
    	System.out.println();
    	System.out.println("*************************************");
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, 
        		                                               MetaObject.DEFAULT_OBJECT_FACTORY, 
        		                                               MetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
        
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, 
									            		MetaObject.DEFAULT_OBJECT_FACTORY, 
									                    MetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object,
									            		MetaObject.DEFAULT_OBJECT_FACTORY, 
									                    MetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        dialect = configuration.getVariables().getProperty("dialect");
        if (null == dialect || "".equals(dialect)) {
            logger.warn("Property dialect is not setted,use default 'mysql' ");
            dialect = defaultDialect;
        }
        pageSqlId = configuration.getVariables().getProperty("pageSqlId");
        if (null == pageSqlId || "".equals(pageSqlId)) {
            logger.warn("Property pageSqlId is not setted,use default '.*Page$' ");
            pageSqlId = defaultPageSqlId;
        }
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        // 只重写需要分页的sql语句。
        //通过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
        if (mappedStatement.getId().matches(pageSqlId)) {
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null || metaStatementHandler.getValue("delegate.boundSql.parameterObject.page")==null ) {
                throw new NullPointerException("parameterObject is null or page can't found !");
            } else {
                PageDo page = (PageDo) metaStatementHandler.getValue("delegate.boundSql.parameterObject.page");
                
                String sql = boundSql.getSql();

                Connection connection = (Connection) invocation.getArgs()[0];
                // 重设分页参数里的总页数等
                setPageDo(sql, connection, mappedStatement, boundSql, page, configuration);

                // 重写sql
                String pageSql = buildPageSql(sql, page);
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
            }
        }
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }

    /**
     * 
     * 从数据库里查询总的记录数并计算总页数，回写进分页参数
     * <code>PageDo</code>,
     * 这样调用者就可用通过 分页参数 
     * <code>PageDo</code>获得相关信息。
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     * @param configuration
     */
    @SuppressWarnings("rawtypes")
	private void setPageDo(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql,
            PageDo page, Configuration configuration) {
        // 记录总记录数
        String countSql = "select count(1) from (" + sql + ") total";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(),
                    boundSql.getParameterObject());

            // 从原有BoundSql中获取参数映射，设置到count的BoundSql中，这样就可以在配置文件中使用bind标签
            for (ParameterMapping pm : boundSql.getParameterMappings()) {
                String property = pm.getProperty();
                if (null != property && "" != property) {
                    Object value = boundSql.getAdditionalParameter(property);
                    if (value != null) {
                        countBS.setAdditionalParameter(property, value);
                    }
                }
            }

            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());

            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalNum(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setTotalPageNum(totalPage);
        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                if(null != rs){
                    rs.close();
                }
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 对SQL参数(?)设值
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject)
            throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * 根据数据库类型，生成特定的分页sql
     * 
     * @param sql
     * @param page
     * @return
     */
    @SuppressWarnings("rawtypes")
	private String buildPageSql(String sql, PageDo page) {
        if (page != null) {
            boolean resetFlag = (page.getPageNum() - 1) * page.getPageSize() > page.getTotalNum();
            if (resetFlag || (page.getPageNum() > page.getTotalPageNum())) {
                page.setPageNum(page.getTotalPageNum() == 0 ? 1 : page.getTotalPageNum());
            }

            StringBuilder pageSql = new StringBuilder();
            if ("mysql".equalsIgnoreCase(dialect)) {
                pageSql = buildPageSqlForMysql(sql, page);
            } else if ("oracle".equalsIgnoreCase(dialect)) {
                pageSql = buildPageSqlForOracle(sql, page);
            } else if ("sybase".equalsIgnoreCase(dialect)) {
                pageSql = buildPageSqlForSybase(sql, page);
            } else {
                return sql;
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    /**
     * mysql的分页语句
     * 
     * @param sql
     * @param page
     * @return String
     */
    @SuppressWarnings("rawtypes")
	public StringBuilder buildPageSqlForMysql(String sql, PageDo page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getPageNum() - 1) * page.getPageSize());
        pageSql.append(sql);
        pageSql.append(" limit " + beginrow + "," + page.getPageSize());
        return pageSql;
    }

    /**
     * 
     * 使用临时表完成分页.为防止临时表数据过大，当查询的数据起始数超过总数的一半后， 
     * 采用逆序的方式查询数据，并在临时表里再采用相反的顺序将数据重新排序。
     * 因此在使用 sybase分页查询时，必须显示的指定排序字段和排序顺序。
     * 
     * @param sql
     * @param page
     * @return String
     */
    @SuppressWarnings("rawtypes")
	public static StringBuilder buildPageSqlForSybase(String sql, PageDo page) {
        StringBuilder pageSql = new StringBuilder(100);
        int beginrow = (int)(page.getPageNum() - 1) * page.getPageSize();
        int endrow =(int)( page.getPageNum() * page.getPageSize());

        // 临时表随机命名，防止名称冲突
        String temp = "#temp" + new Random().nextInt(1000000);
        String fromSql = sql.substring(sql.indexOf("from"));
        String order = "";
        String tempOrder = "asc";
        if (beginrow * 2 > page.getTotalNum()) {
            if (fromSql.lastIndexOf("desc") > 0) {
                order = "asc";
                fromSql = fromSql.substring(0, fromSql.lastIndexOf("desc")) + order;
            } else if (fromSql.lastIndexOf("asc") > 0) {
                order = "desc";
                fromSql = fromSql.substring(0, fromSql.lastIndexOf("asc")) + order;
            }
            tempOrder = "desc";
        }

        pageSql.append("select top ").append(endrow).append(" *,rownum=identity(int) into ").append(temp).append(" ");
        pageSql.append(fromSql).append(" ");
        pageSql.append("select * from ").append(temp).append(" where rownum > ").append(beginrow).append(" order by rownum ")
                .append(tempOrder).append(" ");
        pageSql.append("drop table " + temp);
        return pageSql;
    }

    /**
     * @param sql
     * @param page
     * @return String
     */
    @SuppressWarnings("rawtypes")
	public StringBuilder buildPageSqlForOracle(String sql, PageDo page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getPageNum() - 1) * page.getPageSize());
        String endrow = String.valueOf(page.getPageNum() * page.getPageSize());

        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(endrow);
        pageSql.append(") where row_id > ").append(beginrow);
        return pageSql;
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，
    	//否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @SuppressWarnings("static-access")
	@Override
    public void setProperties(Properties properties) {
    	this.properties = properties;
    }
}

