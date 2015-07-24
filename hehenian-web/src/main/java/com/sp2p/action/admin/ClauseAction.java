package com.sp2p.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.PublicModelService;

/**
 * 条款编辑处理
 * @author li.hou
 *
 */
@SuppressWarnings("unchecked")
public class ClauseAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(ClauseAction.class);
	private PublicModelService clauseService;
	public static int messageTypeId = 1;
	
	
	/**
	* 添加合和年在线帮助中心数据初始化
	* @return String
	 * @throws DataException 
	 * @throws SQLException 
	* @throws
	*/
	public String addClauseInit() throws SQLException, DataException {
		return SUCCESS;
	}
	
	/**
	* 添加合和年在线帮助问题数据
	* @return String
	* @throws SQLException 
	 * @throws DataException 
	 * @throws NumberFormatException 
	*/
	public String addClause() throws SQLException, NumberFormatException,DataException {
		String columnName = SqlInfusion.FilteSqlInfusion(paramMap.get("columName")); //标题
		String content = SqlInfusion.FilteSqlInfusion(paramMap.get("content")); //内容
		
		@SuppressWarnings("unused")
		String messageInfo = "添加失败";
		long result = clauseService.addClause(columnName,content,messageTypeId);
		if (result > 0) {
			messageInfo = "添加成功";
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_message", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "添加条款信息编辑", 2);
		return SUCCESS;
	}
	
	/**
	* 修改初始化
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String updateClauseInit() throws DataException, SQLException {
		long id = Convert.strToLong((request("commonId")),-1);
		try {
			paramMap = clauseService.queryClauseInfoByid(id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		paramMap.put("commonId", id+"");//设置id，用来根据id修改条款内容
		return SUCCESS;
	}
	
	/**
	* 修改
	* @throws SQLException
	* @return String
	 * @throws DataException 
	 * @throws SQLException 
	*/
	public String updateClause() throws SQLException {
		String columnName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("columName")),null); //标题
		String content = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("content")),null);//条款内容
		int id = Convert.strToInt(paramMap.get("commonId"), -1);//接收隐藏的id
		@SuppressWarnings("unused")
		String messageInfo = "修改失败";
		try {
			long result = clauseService.updateClause(columnName,content,id);
			if (result > 0) {
				messageInfo = "修改成功";
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_message", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "根据ＩＤ修改对应的条款信息", 2);
		return SUCCESS;
	}
	
	public String queryClauseListPageInit() throws DataException, SQLException {
		return SUCCESS;
	}
	
	public String queryClauseListPage() throws DataException, SQLException {

		try {
			clauseService.queryClausePage(pageBean,messageTypeId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	* 删除
	* @throws SQLException
	* @throws DataException
	* @return String
	*/
	public String deleteClause() throws SQLException, DataException {
		String clauseIds = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("commonId")),-1+"");
		String[] allIds = clauseIds.split(",");//进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if(tempId == -1){
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
 		clauseService.deleteClauses(clauseIds);
 		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_message", admin.getUserName(),IConstants.DELETE, admin.getLastIP(), 0, "删除条款信息", 2);
		
		return SUCCESS;
	}
	
	/**
	 * 加载借款协议范本
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String queryBorrowClause() throws SQLException, DataException, IOException{
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("title")), null);
		if(title == null)
			return null;
		Map<String,String>  map  = clauseService.queryBorrowClause(title, messageTypeId);
		String str = map.get("columName")+"|"+map.get("content");

		response().setCharacterEncoding("UTF-8");
		response().setContentType("text/html; charset=UTF-8");
		PrintWriter out = response().getWriter();
		out.print(str);
		
		return null;
	}

	public PublicModelService getClauseService() {
		return clauseService;
	}

	public void setClauseService(PublicModelService clauseService) {
		this.clauseService = clauseService;
	}
		
}
