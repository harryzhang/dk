package com.sp2p.action.admin;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.admin.ShoveBorrowAmountTypeService;

/**
 * 额度信息 action
 * @author C_J
 *
 */
public class ShoveBorrowAmountTypeAction  extends BaseFrontAction{
	public static Log log = LogFactory.getLog(MailBoxManagerAction.class);
	private static final long serialVersionUID = 1L;
	
	
	private ShoveBorrowAmountTypeService shoveBorrowAmountTypeService;
	

	
	/**
	 *额度信息初始化
	 * @return
	 * @throws SQLException 
	 */
	public String queryBorrowAmountInit(){
		
		return SUCCESS;
	}
	
	/**
	 *查询所有额度信息
	 * @return
	 * @throws SQLException 
	 */
	public String queryBorrowAmountInfo(){
		try {
			shoveBorrowAmountTypeService.queryBorrowAmountPageAll(pageBean);
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
	 * 修改额度信息初始化
	 * @return
	 * @throws SQLException 
	 */
	public String updatgeBorrowAmountInit(){
		int id = Convert.strToInt(request().getParameter("id"),-1);
		try {
			paramMap=shoveBorrowAmountTypeService.queryBorrowAmountById(id);
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
	 * 修改额度信息
	 * @return
	 * @throws SQLException 
	 */
	public String updatgeBorrowAmount(){
		int id =Convert.strToInt(request().getParameter("paramMap.id"), -1);
		String title = SqlInfusion.FilteSqlInfusion(request().getParameter("paramMap.title"));
		String descriptionm = SqlInfusion.FilteSqlInfusion(request().getParameter("paramMap.description"));
		int status =Convert.strToInt( request().getParameter("paramMap_status"), -1);
		String remark = SqlInfusion.FilteSqlInfusion(SqlInfusion.FilteSqlInfusion(request().getParameter("paramMap.remark")));
		double  init_credit = Convert.strToDouble(request().getParameter("paramMap.init_credit"),0);
		long result= -1L;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			result = shoveBorrowAmountTypeService.updateBorrowAmount(id, title, descriptionm, status, remark,init_credit);
			if(result > 0 ){
				//添加操作日志
				operationLogService.addOperationLog("t_borrow_amount_type",admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "修改借款额度类型", 2);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		if(result > 0)
			return SUCCESS;
		else
		    return INPUT;
	}
	
	public void setShoveBorrowAmountTypeService(
			ShoveBorrowAmountTypeService shoveBorrowAmountTypeService) {
		this.shoveBorrowAmountTypeService = shoveBorrowAmountTypeService;
	}

	public ShoveBorrowAmountTypeService getShoveBorrowAmountTypeService() {
		return shoveBorrowAmountTypeService;
	}

}
