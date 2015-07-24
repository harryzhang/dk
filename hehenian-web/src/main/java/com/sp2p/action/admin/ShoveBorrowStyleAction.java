package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.admin.ShoveBorrowStyleService;

/**
 * 还款方式  action 
 * @author C_J
 *
 */
public class ShoveBorrowStyleAction extends BasePageAction{
	public static Log log = LogFactory.getLog(ShoveBorrowStyleAction.class);

	private static final long serialVersionUID = 1L;
	
	private ShoveBorrowStyleService shoveBorrowStyleService;
	
	/**
	 * 查询初始化
	 * @return
	 * @throws Exception
	 */
	public String queryShoveBorrowAllInit()  {
		return SUCCESS;
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String queryShoveBorrowAllPageInfo(){
		try {
			shoveBorrowStyleService.queryBorrowStylePageAll(pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		 return SUCCESS;
	}
	
	/**
	 * 修改初始化
	 * @return
	 */
	public String updateShoveBorrowStyleInit(){
		int id=Convert.strToInt(request().getParameter("id"), -1);
		Map<String,String> map=null;
		try {
			paramMap =shoveBorrowStyleService.queryShoveBorrowStyleById(id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
	
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 * @throws IOException 
	 */
	public String updateShoveBorrowStyleInfo() throws IOException {
		
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		int status = Integer.valueOf(paramMap.get("status"));
		String contents = SqlInfusion.FilteSqlInfusion(paramMap.get("contents"));
		int id = Integer.valueOf(paramMap.get("id"));
		
//		String title= request("paramMap.title");
//		int  status = Convert.strToInt(request("paramMap_status"),-1);
//		String contents = request("paramMap.contents");
//		int id = Convert.strToInt( request("paramMap.id"),-1);
		long result = -1L;
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			result=	shoveBorrowStyleService.updateShoveBorrowStyle(id, status, title, contents, "", 0);
			if (result > 0 ) {
				JSONUtils.printStr("1");
				//添加操作日志
				operationLogService.addOperationLog("t_borrow_style", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "修改还款方式信息", 2);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			JSONUtils.printStr("1");
		}
		return null;
	}

	public void setShoveBorrowStyleService(
			ShoveBorrowStyleService shoveBorrowStyleService) {
		this.shoveBorrowStyleService = shoveBorrowStyleService;
	}

	public ShoveBorrowStyleService getShoveBorrowStyleService() {
		return shoveBorrowStyleService;
	}
	
}
