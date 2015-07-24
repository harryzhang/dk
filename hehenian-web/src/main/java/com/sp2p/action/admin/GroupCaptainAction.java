package com.sp2p.action.admin;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;

import com.sp2p.service.UserService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.RegionService;
import com.sp2p.service.admin.AdminService;


/**
 * 团队长
 * @author md002
 *
 */
@SuppressWarnings({ "serial", "unchecked" })
public class GroupCaptainAction extends BasePageAction {
	
	public static Log log = LogFactory.getLog(GroupCaptainAction.class);
	
	private AdminService adminService;
	private RegionService regionService;
	//add by houli 后台团队长的名字不能与前台存在的用户名一样
	private UserService userService;
	
	private List<Map<String, Object>> provinceList;
	
	
	public String addGroupCaptainInit(){
		paramMap.put("enable", "1");
		paramMap.put("sex", "1");
		return SUCCESS;
	}
	
	public String addGroupCaptain(){
		
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
		long roleId = IConstants.ADMIN_ROLE_GROUP;//军团长角色默认为1
		int sex = Convert.strToInt(paramMap.get("sex"), -1);
		int nativePlacePro = Convert.strToInt(paramMap.get("nativePlacePro"), -1);
		int nativePlaceCity = Convert.strToInt(paramMap.get("nativePlaceCity"), -1);
		
		long returnId;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			returnId = adminService.addAdminGroup(userName, password, enable, roleId, realName, telphone, qq, email, img, null, sex, card, summary, nativePlacePro, nativePlaceCity, address,-1,IConstants.RELATION_LEVEL1);
			operationLogService.addOperationLog("t_admin", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "添加用户为团队长", 2);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			this.addFieldError("paramMap.allError", "添加失败!");
			return INPUT;
		}
		if(returnId<=0){
			this.addFieldError("paramMap.allError", "添加失败!");
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	public String queryGroupCaptainInit(){
		return SUCCESS;
	}
	
	public String queryGroupCaptainInfo(){
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
	
	public String updateGroupCaptainInit() throws SQLException, DataException{
		long id = Convert.strToLong(request("id"), -1);
		if(id<=0){
			return SUCCESS;
		}
		paramMap = adminService.queryAdminById(id);
		return SUCCESS;
	}
	
	public String updateGroupCaptain(){
		long adminId = Convert.strToLong(paramMap.get("id"), -1);
		if(adminId<=0){
			this.addFieldError("paramMap.allError", "修改失败,数据错误！");
			return INPUT;
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
		long roleId = 1;//军团长角色默认为1
		int sex = Convert.strToInt(paramMap.get("sex"), -1);
		int nativePlacePro = Convert.strToInt(paramMap.get("nativePlacePro"), -1);
		int nativePlaceCity = Convert.strToInt(paramMap.get("nativePlaceCity"), -1);
		try {
			adminService.updateAdminGroup(adminId, null, password, enable, roleId, realName, telphone, qq, email, img, null, sex, card, summary, nativePlacePro, nativePlaceCity, address,null,null);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_admin", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "修改团队长信息", 2);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			this.addFieldError("paramMap.allError", "修改失败，数据错误！");
		}
		return SUCCESS;
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
		 * add by houli 判断用户名是否重复
		 * @return
		 * @throws IOException 
		 * @throws SQLException 
		 * @throws DataException 
		 */
		public String judgeName() throws IOException, SQLException, DataException{
			String userName = SqlInfusion.FilteSqlInfusion(request("userName")==null?null:Convert.strToStr(request("userName"), null));
			try{
				if(userName == null){
					JSONUtils.printStr("1");
					return null;
				}
				Map<String,String> adminMap = adminService.queryIdByUser(userName);
				if(adminMap == null || adminMap.size() <=0){
					//从前台用户表查询是否有重复的用户名
					Map<String,String> userMap = userService.queryIdByUser(userName);
					if(userMap == null || userMap.size()<=0){
						JSONUtils.printStr("3");
						return null;
					}else{
						JSONUtils.printStr("2");
						return null;
					}
				}else{
					JSONUtils.printStr("2");
					return null;
				}
			}catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} catch (DataException e) {
				e.printStackTrace();
				throw e;
			}catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
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

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
	
}
