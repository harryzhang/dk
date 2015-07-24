package com.sp2p.action.admin;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.action.front.FrontMyFinanceAction;
import com.sp2p.service.OperationLogService;

public class OperationLogAction extends BasePageAction {
	public static Log log = LogFactory.getLog(OperationLogAction.class);
	private OperationLogService operationLogService;
	public OperationLogService getOperationLogService() {
		return operationLogService;
	}
	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	/**
	 * 查询操作初始化
	 * 
	 * @return
	 */
	public String queryRecordInit() {
		String operation_around = request("operation_around").toString();
        request().setAttribute("operation_around", operation_around);
		return SUCCESS;
	}
	
	
	//查询操作记录
	public String queryRecordInfo() throws SQLException{
		try {
			String adminName = SqlInfusion.FilteSqlInfusion(paramMap.get("adminName"));
			String operateTime=SqlInfusion.FilteSqlInfusion(paramMap.get("operateTime"));
			String operation_around = SqlInfusion.FilteSqlInfusion(request("operation_around").toString());
			operationLogService.queryAdminRecordAll(pageBean,adminName,operateTime,operation_around);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public String queryManagerOperationLogInit() {
		return SUCCESS;
	}
	public String queryManagerOperationLog() {
		String beginTime=SqlInfusion.FilteSqlInfusion(paramMap.get("beginTime"));
		String endTime=SqlInfusion.FilteSqlInfusion(paramMap.get("endTime"));
		String usename=Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("usename")), "");
		try {
			operationLogService.queryManagerOperationLog(pageBean, "2", usename, beginTime, endTime);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String queryUserOperationLogInit() {
		return SUCCESS;
	}
	public String queryUserOperationLog() {
		String beginTime= SqlInfusion.FilteSqlInfusion(paramMap.get("beginTime"));
		String endTime=SqlInfusion.FilteSqlInfusion(paramMap.get("endTime"));
		String usename=Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("usename")), "");
		try {
			operationLogService.queryManagerOperationLog(pageBean,"1", usename, beginTime, endTime);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
