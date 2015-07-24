package com.sp2p.action.admin;

import java.io.IOException;
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
import com.sp2p.service.admin.CloseNetWorkService;


/**
 * 
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class CloseNetWorkAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(CloseNetWorkAction.class);
	
	private CloseNetWorkService closeNetWorkService;

	public CloseNetWorkService getCloseNetWorkService() {
		return closeNetWorkService;
	}

	public void setCloseNetWorkService(CloseNetWorkService closeNetWorkService) {
		this.closeNetWorkService = closeNetWorkService;
	}
	
	/**
	 * 获取网站关闭详细
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String getCloseNetWorkDetail()throws SQLException,DataException{
		paramMap=closeNetWorkService.getNetWorkById();
		return SUCCESS;
	}
	
	
	/**
	 * 更新关闭网站
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String updateCloseNetWork()throws SQLException,DataException,IOException{
		Integer status=Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("status")), -1);
		String content=SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		Long result=0L;
		result=closeNetWorkService.updateNetWork(status, content);
		if(result>0){
			request().setAttribute("message", "更新成功");
			Map<String, String> map=closeNetWorkService.getNetWorkById();
			application().setAttribute(IConstants.Session_CLOSENETWORK, map);
		}else{
			request().setAttribute("message", "更新失败");
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_closenetwork", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新网站关闭状态", 2);
		
		return SUCCESS;
	}
	




		
	

	

}
