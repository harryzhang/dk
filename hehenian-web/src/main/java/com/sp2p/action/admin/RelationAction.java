package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RelationService;

@SuppressWarnings({ "unchecked", "serial" })
public class RelationAction extends BasePageAction {
	
	public static Log log = LogFactory.getLog(RelationAction.class);

	private RelationService relationService;
	private AdminService adminService;
	
	/**
	 * 查询投资人 初始化
	 * @return
	 */
	public String queryRelationInit(){
		return SUCCESS;
	}
	
	/**
	 * 查询投资人
	 * @return
	 */
	public String queryRelationInfo(){
		String userName1 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName1")), null);
		String userName2 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName2")), null);
		String userName3 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName3")), null);
		String realName3 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("realName3")), null);
		String startDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startDate")),null);
		String endDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endDate")),null);
		long parentId = Convert.strToLong(request("parentId"), -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if(StringUtils.isNotBlank(startDate)){
			try {
				sdf.parse(startDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				startDate = null;
			}
		}
		if(StringUtils.isNotBlank(endDate)){
			try {
				sdf.parse(endDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				endDate = null;
			}
		}
		try {
			relationService.queryRelationBy3(userName1, userName2,userName3, realName3, startDate, endDate, parentId, pageBean,admin.getRoleId(),admin.getUserName());
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 查询理财人 初始化
	 * @return
	 */
	public String queryFinancialInit(){
		return SUCCESS;
	}
	
	/**
	 * 查询理财人
	 * @return
	 */
	public String queryFinancialInfo(){
		String userName1 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName1")), null);
		String userName2 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName2")), null);
		String userName3 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName3")), null);
		String userName4 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName4")), null);
		String realName4 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("realName4")), null);
		String startDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startDate")),null);
		String endDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endDate")),null);
		long parentId = Convert.strToLong(request("parentId"), -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if(StringUtils.isNotBlank(startDate)){
			try {
				sdf.parse(startDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				startDate = null;
			}
		}
		if(StringUtils.isNotBlank(endDate)){
			try {
				sdf.parse(endDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				endDate = null;
			}
		}
		try {
			relationService.queryRelationBy4(userName1, userName2, userName3, userName4, realName4, startDate, endDate, parentId, pageBean,admin.getRoleId(),admin.getUserName());
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String updateRelation() throws IOException{
		JSONObject obj = new JSONObject();
		long id = Convert.strToLong(request("id"), -1);
		if(id<=0){
			obj.put("msg", "解除失败，数据错误！");
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String,String> relationMap = new HashMap<String, String>();
		try {
			relationMap = relationService.queryRelationById(id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			obj.put("msg", "解除失败!");
			JSONUtils.printObject(obj);
			return null;
		}
		int enable = Convert.strToInt(relationMap.get("enable"), -1);
		if(enable==1){
			try {
				relationService.updateRelationEnable(id, IConstants.RELATION_ENABLE_FALSE);
				Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_relation", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "修改角色之间的关系", 2);
			} catch (SQLException e) {
				e.printStackTrace();
				obj.put("msg", "解除失败!");
				JSONUtils.printObject(obj);
				return null;
			}
		}else{
			obj.put("msg", "解除失败，数据错误！");
			JSONUtils.printObject(obj);
			return null;
		}
		
		obj.put("msg", 1);
		JSONUtils.printObject(obj);
		return null;
	}
	
	public String relationLevelInit(){
		request().setAttribute("id", request("id"));
		
		return SUCCESS;
	}
	
	public String relationLevelInfo() throws Exception{
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("realName")), null);
		String startDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startDate")),null);
		String endDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endDate")),null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(startDate)){
			try {
				sdf.parse(startDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				startDate = null;
			}
		}
		if(StringUtils.isNotBlank(endDate)){
			try {
				sdf.parse(endDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				endDate = null;
			}
		}
		try {
			adminService.queryGroupCaptain(userName, realName, startDate, endDate ,pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 经济人关联客户初始化
	 * @return
	 */
	public String relationLeveladdInit(){
		request().setAttribute("level2Id", request("level2Id"));
		return SUCCESS;
	}
	/**
	 *  经济人关联客户
	 * @return
	 */
	public String relationLeveladdInfo() throws Exception{
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("realName")), null);
		String startDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startDate")),null);
		String endDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endDate")),null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(startDate)){
			try {
				sdf.parse(startDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				startDate = null;
			}
		}
		if(StringUtils.isNotBlank(endDate)){
			try {
				sdf.parse(endDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				endDate = null;
			}
		}
		try {
			relationService.relationLeveladdInfo(userName, realName, startDate, endDate ,pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 经济人和客户添加关系
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 */
	public String addRelationkhId() throws SQLException, DataException, IOException{
		String ids = paramMap.get("ids")+"";
		long level2Id = Convert.strToLong(paramMap.get("level2Id")+"", -2);
		long result = -1;
		result =	relationService.addRelationkhId(ids,level2Id);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_relation", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "添加角色之间的关系", 2);
		if(result>0){
			JSONUtils.printStr("1");
			return null;	
		}
		
		return null;
	}
	
	/**
	 * 查询系统中的经纪人初始化
	 * @param relationService
	 */
	public String queryrelationeconomicInit(){
		request().setAttribute("id", request("id"));
		return SUCCESS;
	}
	/**
	 * 查询系统中的经济人
	 * @return
	 * @throws Exception
	 */
	public String queryrelationeconomicInfo() throws Exception{
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("realName")), null);
		String startDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startDate")),null);
		String endDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endDate")),null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(startDate)){
			try {
				sdf.parse(startDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				startDate = null;
			}
		}
		if(StringUtils.isNotBlank(endDate)){
			try {
				sdf.parse(endDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				endDate = null;
			}
		}
		try {
			adminService.queryrelationeconomicInfo(userName, realName, startDate, endDate ,pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询系统中的投资人初始化
	 * @param relationService
	 */
	public String queryrelationinvestorsInit(){
		request().setAttribute("id", request("id"));
		return SUCCESS;
	}
	/**
	 * 查询系统中的投资人
	 * @return
	 * @throws Exception
	 */
	public String queryrelationinvestorsInfo() throws Exception{
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("realName")), null);
		String startDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startDate")),null);
		String endDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endDate")),null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(startDate)){
			try {
				sdf.parse(startDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				startDate = null;
			}
		}
		if(StringUtils.isNotBlank(endDate)){
			try {
				sdf.parse(endDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				endDate = null;
			}
		}
		try {
			relationService.queryrelationinvestorsInfo(userName, realName, startDate, endDate, pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
}
