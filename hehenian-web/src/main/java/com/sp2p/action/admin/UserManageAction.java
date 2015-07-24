package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.FinanceService;
import com.sp2p.service.RegionService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;
import com.sp2p.service.admin.UserManageServic;
import com.sp2p.util.ChinaPnRInterface;

/**
 * 后台用户管理
 * 
 * @author lw
 * 
 */
public class UserManageAction extends BaseFrontAction {
	/**
	 * 哈哈
	 */
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(UserManageAction.class);
	private UserManageServic userManageServic;
	private RegionService regionService;
	private UserService userService;
	private ValidateService validateService;
	private List<Map<String, Object>> provinceList;
	private List<Map<String, Object>> provinceList_work;
	private List<Map<String, Object>> cityList;
	private List<Map<String, Object>> regcityList;
	private FinanceService financeService;// 理财
	private long workPro = -1L;// 初始化省份默认值
	private long cityId = -1L;// 初始化话默认城市
	private long regPro = -1L;// 户口区域默认值
	private long regCity = -1L;// 户口区域默认值

	public long getWorkPro() {
		return workPro;
	}

	public void setWorkPro(long workPro) {
		this.workPro = workPro;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getRegPro() {
		return regPro;
	}

	public void setRegPro(long regPro) {
		this.regPro = regPro;
	}

	public long getRegCity() {
		return regCity;
	}

	public void setRegCity(long regCity) {
		this.regCity = regCity;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setUserManageServic(UserManageServic userManageServic) {
		this.userManageServic = userManageServic;
	}

	/**
	 * 跳转到用户基本信息管理初始化
	 * 
	 * @return
	 */
	public String queryUserManageBaseInfoindex() {
		return SUCCESS;
	}

	/**
	 * 实名认证管理初始化
	 * 
	 * @return
	 */
	public String realNameAuthentication() {
		return SUCCESS;
	}

	/**
	 * 跳转到用户基本信息管理详细信息
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserManageBaseInfo() throws SQLException, DataException {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		String idNo = SqlInfusion.FilteSqlInfusion(paramMap.get("idNo"));
		String cellPhone = SqlInfusion.FilteSqlInfusion(paramMap.get("cellPhone"));
		int source = Convert.strToInt(paramMap.get("source"), -1);

		userManageServic.queryUserManageBaseInfo(pageBean, userName, id, idNo, cellPhone, source);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 用户基本信息管理初始化
	 * 
	 * @return
	 */
	public String queryUserManageInfoIndex() {
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String queryUserManageInfo() throws SQLException, DataException {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));

		userManageServic.queryUserManageInfo(pageBean, userName, realName);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	// 用户列表中 查看个人信息
	public String queryUserInfo() {
		long id = Integer.parseInt(request("id"));
		Map<String, String> map = userManageServic.queryUserInfo(id);
		request().setAttribute("username", map.get("username"));
		request().setAttribute("realName", map.get("realName"));
		request().setAttribute("rating", map.get("rating"));
		request().setAttribute("creditrating", map.get("creditrating"));
		request().setAttribute("createTime", map.get("createTime"));
		request().setAttribute("lastIP", map.get("lastIP"));
		request().setAttribute("email", map.get("email"));
		request().setAttribute("cellPhone", map.get("cellPhone"));
		request().setAttribute("qq", map.get("qq"));
		request().setAttribute("userId", map.get("id"));
		return SUCCESS;
	}

	public String updateUserqq() {
		JSONObject obj = new JSONObject();
		String qq = SqlInfusion.FilteSqlInfusion(paramMap.get("qq"));
		long userId = Integer.parseInt(paramMap.get("userId"));
		long result = -1L;
		result = userManageServic.updateUserqq(userId, qq);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);

		try {
			if (result >= 1) {
				operationLogService.addOperationLog("t_person", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "更新用户基本信息（QQ）", 2);
				obj.put("msg", "1");
				JSONUtils.printObject(obj);
				return null;
			} else {
				obj.put("msg", "操作失败");
				JSONUtils.printObject(obj);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 导出用户列表信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportusermanageinfo() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 用户名
			String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
			String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
			// 待还款详情
			userManageServic.queryUserManageInfo(pageBean, userName, realName);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("用户列表", pageBean.getPage(), new String[] { "用户名", "真是姓名", "邮箱", "QQ号码", "手机号码", "注册时间", "最后登录IP" }, new String[] { "username",
					"realName", "email", "qq", "cellPhone", "createTime", "lastIP", });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_person", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户列表基本信息", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 跳转到用户信用积分管理
	 * 
	 * @return
	 */
	public String queryUserManageintegralindex() {
		return SUCCESS;
	}

	/**
	 * 跳转到用户信用积分管理详细
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserManageintegralinfo() throws SQLException, DataException {
		String username = SqlInfusion.FilteSqlInfusion(paramMap.get("username"));
		int viprecode = Convert.strToInt(paramMap.get("viprecode"), -1);
		int creditcode = Convert.strToInt(paramMap.get("creditcode"), -1);
		userManageServic.queryUserManageintegralinfo(pageBean, username, viprecode, creditcode);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 导出用户积分信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportintegralinfo() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			// 用户名
			String username = request().getParameter("username") == null ? "" : request().getParameter("username");
			username = URLDecoder.decode(username, "UTF-8");
			// 会员积分
			int viprecode = Convert.strToInt(request().getParameter("viprecode"), -1);
			// 用户积分排序
			int creditcode = Convert.strToInt(request().getParameter("creditcode"), -1);
			// 待还款详情
			userManageServic.queryUserManageintegralinfo(pageBean, username, viprecode, creditcode);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("用户积分", pageBean.getPage(), new String[] { "用户名", "真是姓名", "信用积分", "会员积分", "最后调整时间" }, new String[] { "username", "realName",
					"creditrating", "rating", "repayDate", });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_usermanage_integralinfo", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户积分信息列表", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * vip记录表初始化
	 * 
	 * @return
	 */
	public String queryUservipRecoderindex() {
		return SUCCESS;
	}

	/**
	 * vip记录表详细内容
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String queryUservipRecoderinfo() throws SQLException, DataException {
		String username = SqlInfusion.FilteSqlInfusion(paramMap.get("username"));
		String apptime = SqlInfusion.FilteSqlInfusion(paramMap.get("apptime"));
		String lasttime = SqlInfusion.FilteSqlInfusion(paramMap.get("lasttime"));
		userManageServic.queryUservipRecoderinfo(pageBean, username, apptime, lasttime);

		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 用户基本信息管理详细信息里面的详细
	 */
	public String queryUserManageBaseInfoinner() throws Exception {

		// 个人信息
		Long userId = Convert.strToLong(request().getParameter("i"), -1L);
		Map<String, String> map = null;
		Map<String, String> UserMsgmap = null;//
		String birth = null;
		String rxedate = null;
		map = userService.queryPersonById(userId);
		UserMsgmap = userManageServic.queryUserManageInnerMsg(userId);// 用户基本信息里面的查看用户的基本信息
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("nativePlacePro") + "", -1L);
			cityId = Convert.strToLong(map.get("nativePlaceCity") + "", -1L);
			regPro = Convert.strToLong(map.get("registedPlacePro") + "", -1L);
			regCity = Convert.strToLong(map.get("registedPlaceCity") + "", -1L);

			birth = Convert.strToStr(map.get("birthday"), null);
			rxedate = Convert.strToStr(map.get("eduStartDay"), null);
			if (birth != null) {
				birth = birth.substring(0, 10);
			}
			if (rxedate != null) {
				rxedate = rxedate.substring(0, 10);
			}
		}

		// 工作信息
		Map<String, String> map_work = new HashMap<String, String>();
		map_work = validateService.queryWorkDataById(userId);

		if (map_work != null && map_work.size() > 0) {
			workPro = Convert.strToLong(map_work.get("workPro") + "", -1L);
			cityId = Convert.strToLong(map_work.get("workCity") + "", -1L);
		}

		provinceList_work = regionService.queryRegionList(-1L, 1L, 1);

		cityList = regionService.queryRegionList(-1L, workPro, 2);

		if (cityId == 0) {
			request().setAttribute("map_work", map_work);
			request().setAttribute("provinceList_work", provinceList_work);
			cityList = regionService.queryRegionList(-1L, 1L, 1);
			request().setAttribute("cityList", cityList);
		} else {
			request().setAttribute("map_work", map_work);
			request().setAttribute("provinceList_work", provinceList_work);
			request().setAttribute("cityList", cityList);
		}

		// 财富信息,目前有19个类型
		validateService.queryFinance(pageBean, userId);
		request().setAttribute("map_work", map_work);
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		regcityList = regionService.queryRegionList(-1L, regPro, 2);
		request().setAttribute("map", map);
		request().setAttribute("provinceList", provinceList);
		request().setAttribute("cityList", cityList);
		request().setAttribute("regcityList", regcityList);
		request().setAttribute("birth", birth);
		request().setAttribute("rxedate", rxedate);
		request().setAttribute("UserMsgmap", UserMsgmap);
		return SUCCESS;
	}

	/**
	 * 更新用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String userManageUpd() throws Exception {
		// String username = paramMap.get("username");
		String userid = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		String highestEdu = SqlInfusion.FilteSqlInfusion(paramMap.get("highestEdu"));
		if (highestEdu.equals("-1"))
			highestEdu = "";
		String idNo = SqlInfusion.FilteSqlInfusion(paramMap.get("idNo"));
		String address = SqlInfusion.FilteSqlInfusion(paramMap.get("address"));
		String sex = SqlInfusion.FilteSqlInfusion(paramMap.get("sex"));
		// String email = paramMap.get("email");
		String birthday = SqlInfusion.FilteSqlInfusion(paramMap.get("birthday"));
		String cellPhone = SqlInfusion.FilteSqlInfusion(paramMap.get("cellPhone"));

		Long returnupd1 = userManageServic.updateUserAllInfo(Long.parseLong(userid), realName, highestEdu, idNo, address, sex, birthday, cellPhone);
		if (returnupd1 < 0) {
			JSONUtils.printStr("0");
			return "success";
		}
		String usrCustId = paramMap.get("usrCustId");
		if (returnupd1 == 1) {
			// String cmdId = "AcctModify";
			// 账户信息修改
			String html = ChinaPnRInterface.AccountUpdate(usrCustId);
			JSONUtils.printStr("1");
			sendHtml(html);
		}

		String work_id = SqlInfusion.FilteSqlInfusion(paramMap.get("work_id"));
		if (work_id.equals(""))
			work_id = "0";
		String job = SqlInfusion.FilteSqlInfusion(paramMap.get("job"));
		String monthlyIncome = SqlInfusion.FilteSqlInfusion(paramMap.get("monthlyIncome"));
		String orgName = SqlInfusion.FilteSqlInfusion(paramMap.get("orgName"));
		String companyTel = SqlInfusion.FilteSqlInfusion(paramMap.get("companyTel"));
		String companyType = SqlInfusion.FilteSqlInfusion(paramMap.get("companyType"));
		String workEmail = SqlInfusion.FilteSqlInfusion(paramMap.get("workEmail"));
		String companyScale = SqlInfusion.FilteSqlInfusion(paramMap.get("companyScale"));
		String companyAddress = SqlInfusion.FilteSqlInfusion(paramMap.get("companyAddress"));
		String workYear = SqlInfusion.FilteSqlInfusion(paramMap.get("workYear"));

		Long returnupd2 = userManageServic.updUserWorkAllInfo(Long.parseLong(work_id), Long.parseLong(userid), job, monthlyIncome, orgName, companyTel, companyType, workEmail,
				companyScale, companyAddress, workYear);
		if (returnupd2 < 0) {
			JSONUtils.printStr("0");
			return "success";
		}

		JSONUtils.printStr("1");
		return "success";
	}

	/**
	 * 用户资金信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUserMoneyInfoinner() throws Exception {
		// 个人信息
		Long userId = Convert.strToLong(request().getParameter("i"), -1L);

		// 工作信息
		Map<String, String> map_work = new HashMap<String, String>();
		Map<String, String> UserMsgmaps_work = null;//
		map_work = validateService.queryWorkDataById(userId);

		UserMsgmaps_work = userManageServic.queryUserManageInnerMsg(userId);// 用户基本信息里面的查看用户的基本信息

		if (map_work != null && map_work.size() > 0) {
			workPro = Convert.strToLong(map_work.get("workPro") + "", -1L);
			cityId = Convert.strToLong(map_work.get("workCity") + "", -1L);
		}
		request().setAttribute("id", userId);
		provinceList_work = regionService.queryRegionList(-1L, 1L, 1);

		cityList = regionService.queryRegionList(-1L, workPro, 2);

		if (cityId == 0) {
			request().setAttribute("map_work", map_work);
			request().setAttribute("provinceList_work", provinceList_work);
			cityList = regionService.queryRegionList(-1L, 1L, 1);
			request().setAttribute("cityList", cityList);
		} else {
			request().setAttribute("map_work", map_work);
			request().setAttribute("provinceList_work", provinceList_work);
			request().setAttribute("cityList", cityList);
		}
		request().setAttribute("UserMsgmaps_work", UserMsgmaps_work);

		// Map<String, String> map_money = new HashMap<String, String>();
		// map_money = validateService.queryPerUserCredit(userId);
		// request().setAttribute("userId", userId);
		//
		// request().setAttribute("map_money",map_money);

		/**
		 * 根据用户ID查询用户资金
		 * */
		Map<String, String> map_zij = new HashMap<String, String>();
		map_zij = userService.queryPerson_MoneyById(userId);
		request().setAttribute("map_zij", map_zij);

		/**
		 * 根据用户ID查询资金变动情况
		 * */
		List<Map<String, Object>> map_money_change = new ArrayList<Map<String, Object>>();
		map_money_change = userService.queryUserMoneyChange(userId);
		request().setAttribute("map_money_change", map_money_change);

		return SUCCESS;
	}

	/**
	 * 用户投资信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUserTouzInfoinner() throws Exception {
		// 个人信息
		Long userId = Convert.strToLong(request().getParameter("i"), -1L);

		// 工作信息
		Map<String, String> map_touz = new HashMap<String, String>();
		Map<String, String> userMsgmaps_touz = null;//
		map_touz = validateService.queryWorkDataById(userId);

		userMsgmaps_touz = userManageServic.queryUserManageInnerMsg(userId);// 用户基本信息里面的查看用户的基本信息

		if (map_touz != null && map_touz.size() > 0) {
			workPro = Convert.strToLong(map_touz.get("workPro") + "", -1L);
			cityId = Convert.strToLong(map_touz.get("workCity") + "", -1L);
		}
		request().setAttribute("id", userId);

		// borrowRecordMaop借款记录
		Map<String, String> borrowRecordMap_touz = financeService.queryBorrowRecord_user(userId);
		request().setAttribute("map_touz", map_touz);
		request().setAttribute("userMsgmaps_touz", userMsgmaps_touz);
		request().setAttribute("borrowRecordMap_touz", borrowRecordMap_touz);

		// provinceList_work = regionService.queryRegionList(-1L, 1L, 1);
		//
		// cityList = regionService.queryRegionList(-1L, workPro, 2);
		//
		// if (cityId == 0) {
		// request().setAttribute("map_work", map_work);
		// request().setAttribute("provinceList_work", provinceList_work);
		// cityList = regionService.queryRegionList(-1L, 1L, 1);
		// request().setAttribute("cityList", cityList);
		// } else {
		// request().setAttribute("map_work", map_work);
		// request().setAttribute("provinceList_work", provinceList_work);
		// request().setAttribute("cityList", cityList);
		// }
		// request().setAttribute("UserMsgmaps_work", UserMsgmaps_work);
		//

		// Map<String, String> map_money = new HashMap<String, String>();
		// map_money = validateService.queryPerUserCredit(userId);
		// request().setAttribute("userId", userId);
		//
		// request().setAttribute("map_money",map_money);

		return SUCCESS;
	}

	/**
	 * 用户基本信息管理详细信息里面的详细
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUserInfoinner() throws Exception {

		// 个人信息
		Long userId = Convert.strToLong(request().getParameter("i"), -1L);
		Map<String, String> map = null;
		Map<String, String> UserMsgmap = null;//
		String birth = null;
		String rxedate = null;
		map = userService.queryPersonById(userId);
		UserMsgmap = userManageServic.queryUserManageInnerMsg(userId);// 用户基本信息里面的查看用户的基本信息
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("nativePlacePro") + "", -1L);
			cityId = Convert.strToLong(map.get("nativePlaceCity") + "", -1L);
			regPro = Convert.strToLong(map.get("registedPlacePro") + "", -1L);
			regCity = Convert.strToLong(map.get("registedPlaceCity") + "", -1L);

			birth = Convert.strToStr(map.get("birthday"), null);
			rxedate = Convert.strToStr(map.get("eduStartDay"), null);
			if (birth != null) {
				birth = birth.substring(0, 10);
			}
			if (rxedate != null) {
				rxedate = rxedate.substring(0, 10);
			}
		}

		/**
		 * 根据ID查询认证情况
		 * */

		Map<String, String> map_rez = new HashMap<String, String>();
		map_rez = validateService.queryPerUserrez(userId);
		request().setAttribute("map_rez", map_rez);

		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		regcityList = regionService.queryRegionList(-1L, regPro, 2);
		request().setAttribute("map", map);
		request().setAttribute("provinceList", provinceList);
		request().setAttribute("cityList", cityList);
		request().setAttribute("regcityList", regcityList);
		request().setAttribute("birth", birth);
		request().setAttribute("rxedate", rxedate);
		request().setAttribute("UserMsgmap", UserMsgmap);

		//

		return SUCCESS;

	}

	public String queryPersonAuditStatusInfoAlert() throws Exception {

		// 个人信息
		Long userId = Convert.strToLong(request().getParameter("i"), -1L);
		Map<String, String> map = null;
		Map<String, String> UserMsgmap = null;//
		String birth = null;
		String rxedate = null;
		map = userService.queryPersonById(userId);
		UserMsgmap = userManageServic.queryUserManageInnerMsg(userId);// 用户基本信息里面的查看用户的基本信息
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("nativePlacePro") + "", -1L);
			cityId = Convert.strToLong(map.get("nativePlaceCity") + "", -1L);
			regPro = Convert.strToLong(map.get("registedPlacePro") + "", -1L);
			regCity = Convert.strToLong(map.get("registedPlaceCity") + "", -1L);

			birth = Convert.strToStr(map.get("birthday"), null);
			rxedate = Convert.strToStr(map.get("eduStartDay"), null);
			if (birth != null) {
				birth = birth.substring(0, 10);
			}
			if (rxedate != null) {
				rxedate = rxedate.substring(0, 10);
			}
		}
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		regcityList = regionService.queryRegionList(-1L, regPro, 2);
		request().setAttribute("map", map);
		request().setAttribute("provinceList", provinceList);
		request().setAttribute("cityList", cityList);
		request().setAttribute("regcityList", regcityList);
		request().setAttribute("birth", birth);
		request().setAttribute("rxedate", rxedate);
		request().setAttribute("UserMsgmap", UserMsgmap);
		/**
		 * 根据ID查询认证情况
		 * */

		Map<String, String> map_rez = new HashMap<String, String>();
		map_rez = validateService.queryPerUserrez(userId);
		request().setAttribute("map_rez", map_rez);
		return SUCCESS;
	}

	/**
	 * 查询用户管理模块中的基本信息管理中的工作信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUserMangework() throws Exception {
		long id = Convert.strToLong(request().getParameter("uid"), -1L);
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> UserMsgmap = null;//
		try {
			map = validateService.queryWorkDataById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserMsgmap = userManageServic.queryUserManageInnerMsg(id);// 用户基本信息里面的查看用户的基本信息
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("workPro") + "", -1L);
			cityId = Convert.strToLong(map.get("workCity") + "", -1L);
		}
		request().setAttribute("id", id);
		provinceList = regionService.queryRegionList(-1L, 1L, 1);

		cityList = regionService.queryRegionList(-1L, workPro, 2);

		if (cityId == 0) {
			request().setAttribute("map", map);
			request().setAttribute("provinceList", provinceList);
			cityList = regionService.queryRegionList(-1L, 1L, 1);
			request().setAttribute("cityList", cityList);
		} else {
			request().setAttribute("map", map);
			request().setAttribute("provinceList", provinceList);
			request().setAttribute("cityList", cityList);
		}
		request().setAttribute("UserMsgmap", UserMsgmap);
		return SUCCESS;
	}

	/**
	 * 跳转到投资信息明细
	 * 
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserManageInvest() throws SQLException {
		Long uerId = Convert.strToLong(request().getParameter("i"), -1L);
		String createtimeStart = paramMap.get("createtimeStart");
		String createtimeEnd = paramMap.get("createtimeEnd");
		userManageServic.queryUserManageInvest(pageBean, uerId, createtimeStart, createtimeEnd);
		Map<String, String> UserMsgmap = null;
		UserMsgmap = userManageServic.queryUserManageInnerMsg(uerId);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("UserMsgmap", UserMsgmap);
		request().setAttribute("userId", uerId);
		return SUCCESS;
	}

	/**
	 * 导出用户列表信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportuserInvestInfo() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 用户名
			Long uerId = Convert.strToLong(request().getParameter("i"), -1L);
			String createtimeStart = SqlInfusion.FilteSqlInfusion(paramMap.get("createtimeStart"));
			String createtimeEnd = SqlInfusion.FilteSqlInfusion(paramMap.get("createtimeEnd"));
			// 待还款详情
			userManageServic.queryUserManageInvest(pageBean, uerId, createtimeStart, createtimeEnd);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}

			userManageServic.changeFigure(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("用户投资信息列表", pageBean.getPage(), new String[] { "用户名", "真时姓名", "手机号码", "投资日期", "还款方式", "投资期限", "投资标题", "投资金额" }, new String[] {
					"username", "realName", "cellPhone", "investTime", "paymentMode", "deadline", "borrowTitle", "investAmount", });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_userManage_invest", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户投资信息列表", 2);
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 跳转到信用分明细
	 * 
	 * @return
	 */
	public String userintegralcreditindex() {
		String userId = SqlInfusion.FilteSqlInfusion(request().getParameter("id"));
		String type = SqlInfusion.FilteSqlInfusion(request().getParameter("y"));
		request().setAttribute("i", userId);
		request().setAttribute("y", type);
		return SUCCESS;
	}

	/**
	 * 跳转到会员分明细info
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String userintegralcreditinfo() throws SQLException, DataException {
		Long userId = Convert.strToLong(paramMap.get("userId"), -1L);
		Integer type = Convert.strToInt(paramMap.get("type"), -1);
		request().setAttribute("userId", userId);
		request().setAttribute("type", type);
		userManageServic.userintegralcreditinfo(pageBean, userId, type);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 导出用户积分明细信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportuserintegralcreditinfo() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Long userId = Convert.strToLong(request("userId"), -1L);
		Integer type = Convert.strToInt(request("type"), -1);

		try {

			// 待还款详情
			userManageServic.userintegralcreditinfo(pageBean, userId, type);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("用户积分明细", pageBean.getPage(), new String[] { "用户名", "真实姓名", "积分类型", "备注", "变动类型", "变动分值", "操作时间" }, new String[] { "username",
					"realName", "intergraltype", "remark", "changetype", "changerecore", "changtime" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_userManage_integralinner", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户积分明细", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 弹出框初始化
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String addintegralindex() throws SQLException {
		Map<String, String> popmap = null;
		Long id = Convert.strToLong(request().getParameter("id"), -1L);
		popmap = userManageServic.queryUserManageaddInteral(id);
		request().setAttribute("popmap", popmap);
		return SUCCESS;
	}

	/**
	 * 弹出框初始添加信用积分
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 */
	public String addintegralreal() throws SQLException, IOException, DataException {
		Long userId = Convert.strToLong(paramMap.get("id"), -1L);
		Integer type = Convert.strToInt(paramMap.get("type"), -1);
		if (type == -1) {
			JSONUtils.printStr("0");
			return null;
		}
		String scoreStr = paramMap.get("s");
		if (StringUtils.isBlank(scoreStr)) {
			JSONUtils.printStr("1");
			return null;
		}
		if (!StringUtils.isNumeric(scoreStr)) {
			JSONUtils.printStr("5");
			return null;
		}
		String remark = paramMap.get("remark");
		if (StringUtils.isBlank(remark)) {
			JSONUtils.printStr("2");
			return null;
		}
		String typeStr = null;
		Long result = -1L;
		if (type == 1) {
			typeStr = "信用积分";
		}
		if (type == 2) {
			typeStr = "会员积分";
		}
		/*
		 * DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String
		 * time=format.format(new Date());
		 */
		String changetype = "增加";// 先设置为增加类型
		Integer score = Convert.strToInt(scoreStr, -1);
		result = userManageServic.addIntervalDelt(userId, score, type, typeStr, remark, changetype);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_user/t_userintegraldetail", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加信用积分及其明细", 2);
		if (result > 0) {
			JSONUtils.printStr("3");
			return null;
		} else {
			JSONUtils.printStr("4");
			return null;
		}
	}

	/**
	 * add by houli 获得用户资金信息
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String queryUserCashInfo() throws SQLException, DataException, IOException {
		try {
			Long userId = Convert.strToLong(request("userId"), -100);
			Map<String, String> map = userManageServic.queryUserCashInfo(userId);
			if (map != null) {
				// request().setAttribute("map_", map);
				JSONObject obj = new JSONObject();
				obj.put("map_", map);

				JSONUtils.printObject(obj);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户注册初始化
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String findUserRegistListIndex() {
		return SUCCESS;
	}

	/**
	 * 用户注册管理列表
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String findUserRegistList() throws Exception {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String mobilePhone = SqlInfusion.FilteSqlInfusion(paramMap.get("phone"));
		String startTime = SqlInfusion.FilteSqlInfusion(paramMap.get("startTime"));
		String endTiem = SqlInfusion.FilteSqlInfusion(paramMap.get("endTiem"));
		String userSource = SqlInfusion.FilteSqlInfusion(paramMap.get("source"));

		userManageServic.findUserRegister(pageBean, userName, mobilePhone, startTime, endTiem, userSource);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
//		session().setAttribute("paramMap", paramMap);
		return SUCCESS;
	}

	/**
	 * 用户基本信息管理导出excel
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String exportUserInfo() {
		pageBean.pageNum = 1;
		String ids = request().getParameter("ids"); // id拼接 用，隔开
		pageBean.setPageSize(100000);
		try {
			String username = Convert.strToStr(request().getParameter("userName"), null);
			if (StringUtils.isNotBlank(username)) {
				username = URLDecoder.decode(username, "UTF-8"); // 中文乱码转换
			}

			// 根据选中ID查询用户列表
			// userManageServic.queryUserList(pageBean,ids);
			userManageServic.queryUserManageBaseInfo_id(pageBean, ids);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}// 序号 用户名 真实姓名 手机号码 身份证 用户来源 注册时间 可用金额 冻结金额 待收金额
			HSSFWorkbook wb = ExcelUtils.exportExcel("用户注册管理列表", pageBean.getPage(), new String[] { "序号", "用户名", "真实姓名", "手机号码", "身份证", "用户来源", "注册时间", "可用金额", "冻结金额", "待收金额" },
					new String[] { "id", "username", "realName", "cellPhone", "idNo", "source", "createTime", "usableSum", "freezeSum", "dueinSum" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户注册管理列表", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户注册管理导出excel
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String exportUserRegisterx() {
		pageBean.pageNum = 1;
		String ids = request().getParameter("ids"); // id拼接 用，隔开
		pageBean.setPageSize(100000);
		try {
			String username = Convert.strToStr(request().getParameter("userName"), null);
			if (StringUtils.isNotBlank(username)) {
				username = URLDecoder.decode(username, "UTF-8"); // 中文乱码转换
			}

			// 根据选中ID查询用户列表
			userManageServic.queryUserList(pageBean, ids);

			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("用户注册管理列表", pageBean.getPage(), new String[] { "用户名", "手机号码", "推荐人", "客户来源", "注册时间", "注册IP", "最后登录时间" }, new String[] {
					"username", "mobilePhone", "refferee", "source", "createTime", "registerIp", "lastDate" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户注册管理列表", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Map<String, Object>> getCityList() {
		return cityList;
	}

	public void setCityList(List<Map<String, Object>> cityList) {
		this.cityList = cityList;
	}

	public List<Map<String, Object>> getRegcityList() {
		return regcityList;
	}

	public void setRegcityList(List<Map<String, Object>> regcityList) {
		this.regcityList = regcityList;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public String referencerInit() {
		return SUCCESS;
	}

	/**
	 * 推荐人管理列表
	 */
	public String referencerList() throws SQLException {
		String username = request("username");
		pageBean.setPageSize(10);
		userManageServic.referencerList(username,pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	/**
	 * 更新代理人账户类型
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	
	public String updateUserAccountType() throws SQLException, DataException {
		JSONObject obj = new JSONObject();
		long userId = Integer.parseInt(request("id"));
		long result = -1L;
		result = userManageServic.updateUserAccountType(userId);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			if (result >= 1) {
				operationLogService.addOperationLog("t_user", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "更新用户基本信息（代理人账户类型）", 2);
				obj.put("msg", "Y");
				JSONUtils.printObject(obj);
				return SUCCESS;
			} else {
				obj.put("msg", "N");
				JSONUtils.printObject(obj);
				return SUCCESS;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}
	
	public String findListAfterSetAcconutType() throws Exception {
//		this.paramMap = (Map<String, String>) session().getAttribute("paramMap");
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String mobilePhone = SqlInfusion.FilteSqlInfusion(paramMap.get("phone"));
		String startTime = SqlInfusion.FilteSqlInfusion(paramMap.get("startTime"));
		String endTiem = SqlInfusion.FilteSqlInfusion(paramMap.get("endTiem"));
		String userSource = SqlInfusion.FilteSqlInfusion(paramMap.get("source"));

		userManageServic.findUserRegister(pageBean, userName, mobilePhone, startTime, endTiem, userSource);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
//		session().setAttribute("paramMap", paramMap);
		return SUCCESS;
	}
	
}
