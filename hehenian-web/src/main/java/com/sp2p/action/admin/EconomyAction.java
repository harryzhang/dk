package com.sp2p.action.admin;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.RegionService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RelationService;


/**
 * 经纪人
 * @author md002
 *
 */
@SuppressWarnings({ "unchecked", "serial" })
public class EconomyAction extends BasePageAction {
	
	public static Log log = LogFactory.getLog(EconomyAction.class);
	
	private AdminService adminService;
	private RegionService regionService;
	private RelationService relationService;
	
	private List<Map<String, Object>> provinceList;
	private List<Map<String,Object>> parentList;
	
	
	public String addEconomyInit(){
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		paramMap.put("enable", "1");
		paramMap.put("sex", "1");
		paramMap.put("parentId",admin.getId()+"");
		return SUCCESS;
	}
	
	public String addEconomy() throws SQLException{
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String password = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("password")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")), null);
		String telphone = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("telphone")), null);
		String email = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("email")), null);
		String img = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("img")), null);
		String card = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("card")), null);
		String summary = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("summary")), null);
		String address = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("address")), null);
		String qq = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("qq")), null);
		int enable = Convert.strToInt(paramMap.get("enable"), -1);
		long roleId = IConstants.ADMIN_ROLE_ECONOMY;//经纪人角色默认为1
		int sex = Convert.strToInt(paramMap.get("sex"), -1);
		int nativePlacePro = Convert.strToInt(paramMap.get("nativePlacePro"), -1);
		int nativePlaceCity = Convert.strToInt(paramMap.get("nativePlaceCity"), -1);
		long returnId;
		try {
			returnId = adminService.addAdminGroup(userName, password, enable, roleId, realName, telphone, qq, email, img, null, sex, card, summary, nativePlacePro, nativePlaceCity, address,0L,IConstants.RELATION_LEVEL2);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			this.addFieldError("paramMap.allError", "添加失败!");
			operationLogService.addOperationLog("t_admin", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "新增经纪人失败", 2);
			return INPUT;
		}
		if(returnId<=0){
			this.addFieldError("paramMap.allError", "添加失败!");
			operationLogService.addOperationLog("t_admin", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "新增经纪人成功", 2);
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	public String queryEconomyInit(){
		return SUCCESS;
	}
	
	/**
	 * 经济人管理
	 * @return
	 */
	public String queryEconomyInfo(){
		String userName1 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName1")), null);
		String userName2 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName2")), null);
		String realName2 = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("realName2")), null);
		String startDate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startDate")),null);
		String endDate = Convert.strToStr(request("endDate"),null);
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
			relationService.queryRelationBy2(userName1, userName2, realName2, startDate, endDate,parentId, pageBean,admin.getRoleId(),admin.getUserName());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String updateEconomyInit() throws Exception{
		long id = Convert.strToLong(request("id"), -1);
		if(id<=0){
			return SUCCESS;
		}
		List<Map<String,Object>> list = relationService.queryRelationByPeopleId(id);
		if(list==null||list.size()<=0){
			return SUCCESS;
		}
		paramMap = adminService.queryAdminById(id);
		paramMap.put("parentId", list.get(0).get("parentId")+"");
		paramMap.put("relationId", list.get(0).get("id")+"");
		return SUCCESS;
	}
	
	public String updateEconomy() throws Exception{
		long adminId = Convert.strToLong(paramMap.get("id"), -1);
		if(adminId<=0){
			this.addFieldError("paramMap.allError", "修改失败,数据错误！");
			JSONUtils.printStr("2");
		}
		String password = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("password")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")), null);
		String telphone = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("telphone")), null);
		String email = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("email")), null);
		String img = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("img")), null);
		String card = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("card")), null);
		String summary = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("summary")), null);
		String address = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("address")), null);
		String qq = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("qq")), null);
		int enable = Convert.strToInt(paramMap.get("enable"), -1);
		long roleId = IConstants.ADMIN_ROLE_ECONOMY;//军团长角色默认为1
		int sex = Convert.strToInt(paramMap.get("sex"), -1);
		int nativePlacePro = Convert.strToInt(paramMap.get("nativePlacePro"), -1);
		int nativePlaceCity = Convert.strToInt(paramMap.get("nativePlaceCity"), -1);
		long relationId = Convert.strToLong(paramMap.get("relationId"), -1);
		try {
			adminService.updateAdminGroup(adminId, null, password, enable, roleId, realName, telphone, qq, email, img, null, sex, card, summary, nativePlacePro, nativePlaceCity, address,relationId,0L);
			JSONUtils.printStr("1");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			this.addFieldError("paramMap.allError", "修改失败，数据错误！");
			JSONUtils.printStr("2");
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_admin", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "修改团队长或经纪人信息", 2);
		return null;
	}
	
	public String updateRelationRelation() throws IOException, SQLException{
		JSONObject obj = new JSONObject();
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long id = Convert.strToLong(request("id"), -1);
		if(id<=0){
			obj.put("msg", "解除失败！");
			JSONUtils.printObject(obj);
			return null;
		}
		int enable = IConstants.RELATION_ENABLE_FALSE;
		try {
			relationService.updateRelationEnable(id, enable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(enable==IConstants.RELATION_ENABLE_FALSE){
			obj.put("text", "进行关联");
			obj.put("enable", IConstants.RELATION_ENABLE_TRUE);
			operationLogService.addOperationLog("t_relation", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "添加与经纪人关联", 2);
		}else{
			obj.put("text", "解除关系");
			obj.put("enable", IConstants.RELATION_ENABLE_FALSE);
			operationLogService.addOperationLog("t_relation", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "解除与经纪人的关联关系", 2);
		}
		obj.put("msg", 1);
		JSONUtils.printObject(obj);
		return null;
		
	}
	
	public String updateRelationParentId() throws IOException{
		JSONObject obj = new JSONObject();
		long id = Convert.strToLong(request("id"), -1);
		long parentId = Convert.strToLong(request("parentId"), -1);
		if(id<=0||parentId<=0){
			obj.put("msg", "重置失败，数据错误！");
			JSONUtils.printObject(obj);
			return null;
		}
		long returnId = -1;
		try {
			returnId = relationService.updateRelation(id, null, parentId, null, IConstants.RELATION_ENABLE_TRUE);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_relation", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "修改角色之间的关系", 2);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			obj.put("msg", "重置失败！");
			JSONUtils.printObject(obj);
			return null;
		}
		if(returnId<=0){
			obj.put("msg", "重置失败！");
			JSONUtils.printObject(obj);
			return null;
		}
		obj.put("msg", 1);
		JSONUtils.printObject(obj);
		return null;
	}
	
	
	
	// ======地区列表
		public String ajaxqueryRegionAdmin() throws SQLException, DataException,
				IOException {
			Long parentId = Convert.strToLong(request("parentId"), -1);
			Integer regionType = Convert.strToInt(request("regionType"), -1);
			List<Map<String, Object>> list;
			try {
				list = regionService.queryRegionList(-1L, parentId, regionType);
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} catch (DataException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}
			String jsonStr = JSONArray.toJSONString(list);
			JSONUtils.printStr(jsonStr);
			return null;
		}
		/**
		 * 重置投资人的经纪人
		 * @return
		 * @throws IOException
		 */
		public String updateAgentParentId() throws IOException {
			JSONObject obj = new JSONObject();
			long id = Convert.strToLong(request("id"), -1);
			long parentId = Convert.strToLong(request("parentId"), -1);
			if (id <= 0 || parentId <= 0) {
				obj.put("msg", "重置失败，数据错误！");
				JSONUtils.printObject(obj);
				return null;
			}
			long returnId = -1;
			try {
				returnId = relationService.updateRelation(id, null, parentId, null,
						IConstants.RELATION_ENABLE_TRUE);
				Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_relation", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "重置投资人的经纪人", 2);
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				obj.put("msg", "重置失败！");
				JSONUtils.printObject(obj);
				return null;
			}
			if (returnId <= 0) {
				obj.put("msg", "重置失败！");
				JSONUtils.printObject(obj);
				return null;
			}
			obj.put("msg", 1);
			JSONUtils.printObject(obj);
			return null;
		}
		
		/**
		 * 重置理财人的投资人
		 * @return
		 * @throws IOException
		 */
		public String updateInvestorParentId() throws IOException {
			JSONObject obj = new JSONObject();
			long id = Convert.strToLong(request("id"), -1);
			long parentId = Convert.strToLong(request("parentId"), -1);
			if (id <= 0 || parentId <= 0) {
				obj.put("msg", "重置失败，数据错误！");
				JSONUtils.printObject(obj);
				return null;
			}
			long returnId = -1;
			try {
				returnId = relationService.updateRelation(id, null, parentId, null,
						IConstants.RELATION_ENABLE_TRUE);
				Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_relation", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "重置理财人的投资人", 2);
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				obj.put("msg", "重置失败！");
				JSONUtils.printObject(obj);
				return null;
			}
			if (returnId <= 0) {
				obj.put("msg", "重置失败！");
				JSONUtils.printObject(obj);
				return null;
			}
			obj.put("msg", 1);
			JSONUtils.printObject(obj);
			return null;
		}

		
		
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	public List<Map<String, Object>> getProvinceList() throws DataException, SQLException {
		if (provinceList == null) {
			try {
				provinceList = regionService.queryRegionList(-1L, 1L, 1);
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} catch (DataException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return provinceList;
	}
	public String economyAwardInit() {
		return SUCCESS;
	}
	public String queryEconomyList() throws Exception {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String starttime = SqlInfusion.FilteSqlInfusion(paramMap.get("startDate"));
		String endTime = SqlInfusion.FilteSqlInfusion(paramMap.get("endDate"));
		try {
			adminService.queryEconomyList(userName, pageBean, starttime, endTime);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	public String queryEconomyAward() throws Exception {
		String userName = paramMap.get("userName");
		try {
			adminService.queryEconomyAward(userName, pageBean);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
	public List<Map<String, Object>> getParentList() throws Exception {
		if(parentList==null){
			parentList = adminService.queryAdminByRoleId(IConstants.ADMIN_ROLE_GROUP);
		}
		return parentList;
	}
	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}
	
	
	
	
}
