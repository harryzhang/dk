package com.hehenian.biz.service.individualCenter.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import com.hehenian.biz.common.individualCenter.Dao.Procedures;
import com.hehenian.biz.common.individualCenter.DataBaseConfig;
import com.hehenian.biz.common.individualCenter.IAmountConstants;
import com.hehenian.biz.common.individualCenter.IInformTemplateConstants;
import com.hehenian.biz.common.individualCenter.PageBean;
import com.mysql.jdbc.Connection;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.DataSet;

/**
 * 基类
 *
 */
@Service
public class BaseService {
	
	public static ConnectionManager connectionManager;

	public static DataBaseConfig dateManager;
	/**
	 * 分页存储过程
	 * @param pageBean
	 * @param table 表名
	 * @param field 字段名
	 * @param order 排序
	 * @param where 条件
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public void dataPage(Connection conn, PageBean<Map<String, Object>> pageBean, String table, String field, String order, String condition) throws SQLException, DataException{
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		long curPage =  pageBean.getPageNum();
		Procedures.pr_page(conn, ds, outParameterValues, table, field, pageBean.getPageSize(), curPage, order, condition, 0);
		long count = (Long) outParameterValues.get(0);
		boolean result = pageBean.setTotalNum(count);
		if(result){
			ds.tables.get(0).rows.genRowsMap();
			pageBean.setPage(ds.tables.get(0).rows.rowsMap);
		}
	}
	
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public static DataBaseConfig getDateManager() {
		return dateManager;
	}

	public static void setDateManager(DataBaseConfig dateManager) {
		BaseService.dateManager = dateManager;
	}

	@SuppressWarnings("unchecked")
	public Map<String,Object> getInformTemplate(){
		return (Map<String, Object>)ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPlatformCost(){
		//获取平台收费
		Map<String,Object> platformCostMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IAmountConstants.FEE_APPLICATION);
        if(platformCostMap == null)
        	platformCostMap = new HashMap<String, Object>();
		return platformCostMap;
	}
	
}
