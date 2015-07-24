package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RoleRightsService;

public class ShowSubMenuAction extends BasePageAction {
	public static Log log = LogFactory.getLog(ShowSubMenuAction.class);
	private RoleRightsService roleRightsService;
	private AdminService adminService;

	public void setRoleRightsService(RoleRightsService roleRightsService) {
		this.roleRightsService = roleRightsService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 查询一级菜单下二级菜单的权限
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String showsubmenu() throws SQLException, DataException {
		String index = SqlInfusion.FilteSqlInfusion(request().getParameter("index"));
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long roleId = admin.getRoleId();
		List<Map<String, Object>> list = null;
		try {
			list = roleRightsService.queryAdminRoleRightMenu(roleId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		// --审核管理
		Map<String, String> map = adminService.queryCheckCount(admin.getId());
		session().setAttribute("map", map);

		session().setAttribute("adminRoleMenuList", list);

		session().setAttribute(IConstants.SESSION_ADMIN, admin);
		session().setAttribute("index", index);
		return SUCCESS;
	}

	/**
	 * 搜索模块
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String modesearch() throws Exception {
		String searchCode = request().getParameter("modeSearch");
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long roleId = admin.getRoleId();
		List<Map<String, Object>> list = null;
		try {
			list = roleRightsService.queryAdminRoleRightMenuSearch(roleId, searchCode);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		// --审核管理
		// Map<String,String> map = adminService.queryCheckCount(admin.getId());
		// session().setAttribute("map", map);
		if (list.size() == 0) {
			session().setAttribute("issearchCount", list.size());
		} else {
			session().setAttribute("issearchCount", 1);
		}
		if (searchCode.equals("")) {
			session().setAttribute("issearchCount", 0);
		}
		session().setAttribute("adminRoleMenuLists", list);

		session().setAttribute(IConstants.SESSION_ADMIN, admin);
		session().setAttribute("index", 1);
		session().setAttribute("searchCode", searchCode);
		return SUCCESS;
	}
}
