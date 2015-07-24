package com.sp2p.action.admin;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.ShoveApproveNoticeStyleService;
import com.sp2p.service.admin.ShoveApproveNoticeTemplateService;

/**
 * 提醒方式
 * @author C_J
 *
 */
public class ShoveApproveNoticeStyleAction extends  BasePageAction{

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(ShoveApproveNoticeStyleAction.class);
	
	private ShoveApproveNoticeStyleService   shoveApproveNoticeStyleService;
	private ShoveApproveNoticeTemplateService  shoveApproveNoticeTemplateService;

	
	/**
	 * 查询所有初始化
	 * @return
	 */
	public String queryNoticeStyleAllInit() {
		int sid = Convert.strToInt(request().getParameter("sid"),0);
		request().setAttribute("sid", sid);
		return SUCCESS;
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public String queryNoticeStyleAllList() {
		int notice_style = Convert.strToInt(request("sid"),0);
		if(notice_style == 0 ){
			notice_style=1;
		}
		try {
			shoveApproveNoticeStyleService.queryApproveNoticeStylePageAll(pageBean, notice_style);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
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
	 * 修改初始化
	 * @return
	 */
	public String updateNoticeStyleInit() {
		int id = Convert.strToInt(request().getParameter("id"),0);
		try {
			paramMap=shoveApproveNoticeStyleService.queryApproveNoticeStyleById(id);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 修改
	 * @return
	 * @throws SQLException 
	 */
	public String updateNoticeStyleAllinfo() throws SQLException {
		String title = Convert.strToStr( SqlInfusion.FilteSqlInfusion(request("paramMap.title")),null);
		int sort = Convert.strToInt(request("paramMap.sort"),0);
		int id = Convert.strToInt(request("paramMap.id"), -1);
		int notice_style = Convert.strToInt(request("notice_style"), 0);
		long result= -1L;
		try {
		    result = shoveApproveNoticeStyleService.updateApproveNoticeStyle(id, title, sort);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		request().setAttribute("sid", notice_style);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if(result > 0 ){
			operationLogService.addOperationLog("t_approve_notice_style", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "修改提醒设置成功", 2);
			return SUCCESS;
		}
		else{
			operationLogService.addOperationLog("t_approve_notice_style", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "修改提醒设置失败", 2);
			return INPUT;
		}
	}

	/**
	 * 管理初始化
	 * @return
	 */
	public String queryNoticeTemplateAllInit() {
		int id=Convert.strToInt(request().getParameter("id"), 0);
		int notice_style=Convert.strToInt(request().getParameter("notice_style"), -1);
		request().setAttribute("id", id);
		request().setAttribute("notice_style", notice_style);
		return SUCCESS;
	}
	
	/**
	 * 管理
	 * @return
	 */
	public String queryNoticeTemplateAllList() {
		int notice_style=Convert.strToInt(request().getParameter("notice_style"), 0);
		int id = Convert.strToInt(request().getParameter("id"), 0);
		try {
			shoveApproveNoticeTemplateService.queryOrderRechargeRecords(pageBean, notice_style,id,0);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		request().setAttribute("id", id);
		return SUCCESS;
	}
	

	
	/**
	 * 提醒管理修改初始化
	 * @return
	 */
	public String updateNoticeTemplateInit() {
		int id=Convert.strToInt(request().getParameter("id"),0);
		int notice_style=Convert.strToInt(request().getParameter("notice_style"), 0);
		try {
			shoveApproveNoticeTemplateService.queryOrderRechargeRecords(pageBean, notice_style,id,-1);
			} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 提醒管理修改
	 * @return
	 * @throws SQLException 
	 */
	public String updateNoticeTemplateInfo() throws SQLException {
		String name = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("bean_name")), null);
		String template = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("bean_template")), null);
		int sort = Convert.strToInt(request("bean_sort"), 0);
		int id = Convert.strToInt(request("id"), 0);
		int sid = Convert.strToInt(request("sid"), 0);
		int notice_id = Convert.strToInt(request("notice_id"), 0);
		int notice_style=Convert.strToInt(request().getParameter("notice_style"), -1);
		request().setAttribute("notice_style", notice_style);
		request().setAttribute("id", sid);
		request().setAttribute("ntid", notice_id);
		long result = -1L;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			result = shoveApproveNoticeTemplateService.updateApproveNoticeTemplate(id, notice_id, name, template, sort);
			//request().setAttribute("sid", notice_style);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		if(result >0){
			operationLogService.addOperationLog("t_approve_notice_template", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "提醒管理修改成功", 2);
			return SUCCESS;}
		else {
			operationLogService.addOperationLog("t_approve_notice_template", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "提醒管理修改失败", 2);
			return INPUT;
		}
	}

	
	public void setShoveApproveNoticeStyleService(
			ShoveApproveNoticeStyleService shoveApproveNoticeStyleService) {
		this.shoveApproveNoticeStyleService = shoveApproveNoticeStyleService;
	}

	public void setShoveApproveNoticeTemplateService(
			ShoveApproveNoticeTemplateService shoveApproveNoticeTemplateService) {
		this.shoveApproveNoticeTemplateService = shoveApproveNoticeTemplateService;
	}
	
	
	
}
