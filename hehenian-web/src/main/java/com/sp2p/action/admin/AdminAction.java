package com.sp2p.action.admin;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.config.ChinaPnrConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.BeanMapUtils;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RoleRightsService;
import com.sp2p.service.admin.RoleService;

@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
public class AdminAction extends BasePageAction {

	public static Log log = LogFactory.getLog(AdminAction.class);

	private AdminService adminService;
	private RoleService roleService;
	private RoleRightsService roleRightsService;
	private UserService userService;
	private List<Map<String, Object>> roleList;

	/**
	 * 论坛后台登陆
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logging() throws Exception {
		getOut().print("<script>parent.location.href='" + IConstants.BBS_URL + "logging.do?action=toLogin&admin=admin';</script>");
		return null;
	}

	/**
	 * 查询管理员初始化
	 * 
	 * @return
	 */
	public String queryAdminInit() {
		return SUCCESS;
	}

	/**
	 * 查询管理员
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryAdminInfo() throws SQLException, DataException {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		Integer enable = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("enable")), -1);
		Long roleId = Convert.strToLong(paramMap.get("roleId"), -1);
		adminService.queryAdminPage(userName, enable, roleId, pageBean);
		return SUCCESS;
	}

	/**
	 * 查询管理员
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String isenableAdmin() throws SQLException, DataException {
		long id = Convert.strToLong(request("id"), -1);
		int enable = Convert.strToInt(request("enable"), -1);
		long result = adminService.isenableAdmin(id, enable);
		if (result > 0) {
			return SUCCESS;
		}
		return INPUT;
	}

	/**
	 * 添加管理员初始化
	 * 
	 * @return
	 */
	public String addAdminInit() {
		paramMap.put("enable", 1 + "");
		return SUCCESS;
	}

	/**
	 * 添加管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAdmin() throws Exception {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		String telphone = SqlInfusion.FilteSqlInfusion(paramMap.get("telphone"));
		String qq = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("qq")), "");
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));
		String img = SqlInfusion.FilteSqlInfusion(paramMap.get("img"));
		String isLeader = SqlInfusion.FilteSqlInfusion(paramMap.get("isLeader"));
		Integer enable = Integer.parseInt(paramMap.get("enable"));
		long roleId = Convert.strToLong(paramMap.get("roleId"), -1);
		Long adminID = -1L;
        try
        {
            adminID = adminService.addAdmin(userName, password, enable, roleId, realName, telphone, qq, email, img, isLeader);
        }
        catch (SQLException e)
        {
            log.error(e);
            e.printStackTrace();
            JSONUtils.printStr2("添加失败");
            throw e;
        }
        if (adminID == -2) {
            JSONUtils.printStr2("用户名已存在");
            return null;
        }
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);

		// 后台用户开户
//		String usrId = adminID + "";
//		String usrName = userName;
//		String idNo = "";
//		String usrMp = telphone;
//		String usrEmail = email;
//		String cmdId = "BgRegister";
		//发送请求得到响应参数
//		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.bgRegister(cmdId, usrId, usrName, password, password, idNo, usrMp, usrEmail));
//		int ret = json.getInt("RespCode");
//		String usrCustId = json.getString("UsrCustId");
//        if (ret == 0)
//        {
            try
            {
                adminService.updateAdmin(adminID,ChinaPnrConfig.chinapnr_merCustId);
                operationLogService.addOperationLog("t_admin",admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0,"添加新管理员",2);
                JSONUtils.printStr2("添加成功");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                JSONUtils.printStr2("更新日志异常");
//                return "添加管理员异常!";
            }
//        }
//        else
//        {
//            adminService.deleteAdmin(adminID+"");
//            JSONUtils.printStr2("失败:"+json.getString("RespDesc"));
//        }

		return null;
	}

	/**
	 * 修改管理员初始化
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String updateAdminInit() throws SQLException, DataException {
		Long id = Long.parseLong(request("id"));
		paramMap = adminService.queryAdminById(id);
		return SUCCESS;
	}

	/**
	 * 修改管理员
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String updateAdmin() throws SQLException {
		Long id = Long.parseLong(paramMap.get("id"));
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		String telphone = SqlInfusion.FilteSqlInfusion(paramMap.get("telphone"));
		String qq = SqlInfusion.FilteSqlInfusion(paramMap.get("qq"));
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));
		String img = SqlInfusion.FilteSqlInfusion(paramMap.get("img"));
		String isLeader = SqlInfusion.FilteSqlInfusion(paramMap.get("isLeader"));
		Integer enable = Integer.parseInt(paramMap.get("enable"));
		long roleId = Convert.strToLong(paramMap.get("roleId"), -1);
		try {
			adminService.updateAdmin(id, password, enable, null, roleId, realName, telphone, qq, email, img, isLeader);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_admin", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "修改管理员信息", 2);
		return SUCCESS;
	}

	/**
	 * 删除书籍
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String deleteAdmin() throws DataException, SQLException {
		String adminIds = SqlInfusion.FilteSqlInfusion(request("id"));

		String[] adminids = adminIds.split(",");
		int length = adminids.length;
		if (length <= 0) {
			return SUCCESS;
		}
		long[] teacherid = new long[length];
		for (int i = 0; i < adminids.length; i++) {
			teacherid[i] = Convert.strToLong(adminids[i], -1);
			if (teacherid[i] == -1) {
				return SUCCESS;
			}
		}
		try {
			adminService.deleteAdmin(adminIds);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_admin", admin.getUserName(), IConstants.DELETE, admin.getLastIP(), 0, "删除id为" + adminIds + "的管理员",
					2);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 登陆
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws Exception 
	 * @throws AdminHelpMessageException
	 */
	public String adminLogin() throws Exception {
		String pageId = SqlInfusion.FilteSqlInfusion(request().getParameter("pageId"));
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code").toString().trim());
		if (code == null || !_code.equals(code)) {
			this.addFieldError("paramMap.code", "验证码错误！");
			return INPUT;
		}
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName").toString().trim());
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password").toString().trim());

		Admin admin = null;
		try {
			admin = adminService.adminLogin(userName, password, ServletUtils.getRemortIp());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		if (admin == null) {
			this.addFieldError("paramMap.userName", "用户名或密码错误");
			return INPUT;
		}
		if (admin.getEnable() != 1) {
			this.addFieldError("paramMap.password", "你的帐号被停用请联系站点管理员");
			return INPUT;
		}

		long roleId = admin.getRoleId();
		// 获得管理员的权限
		String roleName;
		try {
			roleName = adminService.queryAdminRoleName(roleId);
			session().setAttribute("roleName", roleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 后台登录初始页面
		// --审核管理
		Map<String, String> map = adminService.queryCheckCount(admin.getId());
		session().setAttribute("map", map);
		// 添加后台操作日志
		operationLogService.addOperationLog("t_admin", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "后台管理员登陆", 2);

		List<Map<String, Object>> list = roleRightsService.queryAdminRoleRightMenu(roleId);
		session().setAttribute("index", -1);
		if(22==roleId){
			session().setAttribute("index", -10);//企业用户
		}
		session().setAttribute("adminRoleMenuList", list);

		session().setAttribute(IConstants.SESSION_ADMIN, admin);
		return SUCCESS;
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	public String adminLoginOut() {
		session().removeAttribute(IConstants.SESSION_ADMIN);
		return SUCCESS;
	}

	/**
	 * 修改密码初始化
	 * 
	 * @return
	 */
	public String updatePasswordInit() {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		paramMap = BeanMapUtils.beanToMap(admin);
		paramMap.put("password", "");
		paramMap.put("oldPassword", "");
		return SUCCESS;
	}

	/**
	 * 同步用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String syncBBSUser() throws Exception {
		try {
			List<Map<String, Object>> list = userService.queryUserAll();
			if (list != null) {
				String strURL = IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL + "otherweb.do?action=memberInitAdd" : IConstants.BBS_URL
						+ "/otherweb.do?action=memberInitAdd";
				URL url = new URL(strURL);
				for (Map<String, Object> map : list) {

					String parameters = "groupid=10&regsubmit=yes&alipay=&answer=&bday=0000-00-00&bio=&dateformat=0&email="
							+ URLEncoder.encode(map.get("email") + "", "UTF-8")
							+ "&formHash=6a36c78f&gender=0&icq=&location=&msn=&newsletter=1&password="
							+ URLEncoder.encode(map.get("password") + "", "UTF-8")
							+ "&password2="
							+ URLEncoder.encode(map.get("password") + "", "UTF-8")
							+ "&pmsound=1&ppp=0&qq=&questionid=0&showemail=1&signature=&site=&styleid=0&taobao=&timeformat=0&timeoffset=9999&tpp=0&username="
							+ URLEncoder.encode(map.get("userName") + "", "UTF-8") + "&yahoo=&k="
							+ Encrypt.encryptSES(IConstants.BBS_KEY, IConstants.BBS_SES_KEY);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					conn.setRequestMethod("POST");
					conn.setAllowUserInteraction(false);
					conn.setRequestProperty("User-Agent", "Internet Explorer");
					BufferedOutputStream buf = new BufferedOutputStream(conn.getOutputStream());
					buf.write(parameters.getBytes(), 0, parameters.length());
					buf.flush();
					buf.close();
					// String cookie = conn.getHeaderField("Set-Cookie");
					// String sessionId = cookie.substring(0,
					// cookie.indexOf(";"));
					conn.disconnect();
				}

			}
		} catch (DataException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		OutputStream output = this.response().getOutputStream();
		PrintWriter pw = new PrintWriter(output);
		pw.write("同步成功！");
		pw.flush();
		pw.close();
		output.close();
		return null;
	}

	/**
	 * 修改当前用户密码
	 * 
	 * @return String
	 * @throws SQLException
	 */
	public String updatePassword() throws SQLException {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String oldPassword = SqlInfusion.FilteSqlInfusion(paramMap.get("oldPassword").trim());
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password").trim());
		if ("1".equals(IConstants.ENABLED_PASS)) {
			oldPassword = Encrypt.MD5(oldPassword.trim());
		} else {
			oldPassword = Encrypt.MD5(oldPassword.trim() + IConstants.PASS_KEY);
		}

		String confirmPassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmPassword").trim());
		if (!admin.getPassword().equals(oldPassword)) {
			this.addFieldError("paramMap.oldPassword", "旧密码输入错误");
			return INPUT;
		} else if (!password.equals(confirmPassword)) {
			this.addFieldError("paraMap.oldPassword", "确认密码与新密码不一致");
			return INPUT;
		} else {
			try {
				adminService.updateAdmin(admin.getId(), password, null, null, null, null, null, null, null, null, null);
				// 后台操作日志
				operationLogService.addOperationLog("t_admin", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "管理员修改密码", 2);
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}
		}

		return SUCCESS;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public List<Map<String, Object>> getRoleList() throws SQLException, DataException {
		if (roleList != null) {
			return roleList;
		}
		roleList = roleService.queryRoleList();
		return roleList;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setRoleRightsService(RoleRightsService roleRightsService) {
		this.roleRightsService = roleRightsService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public RoleRightsService getRoleRightsService() {
		return roleRightsService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setRoleList(List<Map<String, Object>> roleList) {
		this.roleList = roleList;
	}

}
