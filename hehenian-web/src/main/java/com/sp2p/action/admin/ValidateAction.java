package com.sp2p.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.BeVipService;
import com.sp2p.service.RegionService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;
import com.sp2p.service.admin.CountWorkStatusService;

@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
public class ValidateAction extends BasePageAction {
	public static Log log = LogFactory.getLog(ValidateAction.class);
	private ValidateService validateService;
	private UserService userService;
	private RegionService regionService;
	private CountWorkStatusService countWorkStatusService;
	private BeVipService beVipService;

	private long workPro;
	private long cityId;

	private List<Map<String, Object>> provinceList;
	private List<Map<String, Object>> cityList;
	@SuppressWarnings("unused")
	private List<Map<String, Object>> areaList;
	@SuppressWarnings("unused")
	private List<Map<String, Object>> typeList;//

	public void setCountWorkStatusService(CountWorkStatusService countWorkStatusService) {
		this.countWorkStatusService = countWorkStatusService;
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

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String queryWorkDataById() {

		return SUCCESS;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public UserService getUserService() {
		return userService;
	}

	/**
	 * 后台-> 个人基本信息审核列表 和 根据名字在去数据库统计//后台统计待审核的用户数量为 X 个。总的待审核的认证数量为XX个
	 * 
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
	public String queryUserCredit() throws SQLException, DataException {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")), null);
		String adminName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("adminName")), null);
		Integer auditStatus = Convert.strToInt(paramMap.get("auditStatus"), -1);
		Integer certificateName = Convert.strToInt(paramMap.get("certificateName"), -1);
		validateService.queryUserCredit(pageBean, userName, realName, auditStatus, adminName, certificateName);
		List<Map<String, Object>> countList = null;
		@SuppressWarnings("unused")
		Map<String, String> byNamemap = null;
		countList = validateService.queryUserCreditCount(userName, realName, auditStatus, adminName, certificateName);// 获取到这个集合上面pagebean的相同集合
		@SuppressWarnings("unused")
		Vector<Long> list = new Vector<Long>();
		// 获取这个集合的username的值
		long countAll = 0;// 待审核的认证数量为XX个
		long userAll = 0;
		boolean flag = false;
		if (countList != null && countList.size() > 0) {

			for (int i = 0; i < countList.size(); i++) {
				int tmIdentityauditStatus = Convert.strToInt(
						countList.get(i).get("tmIdentityauditStatus") == null ? "" : countList.get(i).get("tmIdentityauditStatus").toString(), 0);// 得到结果集
																																					// fileUrl的值
				int tmworkauditStatus = Convert.strToInt(
						countList.get(i).get("tmworkauditStatus") == null ? "" : countList.get(i).get("tmworkauditStatus").toString(), 0);// 得到结果集
																																			// fileUrl的值
				int tmaddressauditStatus = Convert.strToInt(
						countList.get(i).get("tmaddressauditStatus") == null ? "" : countList.get(i).get("tmaddressauditStatus").toString(), 0);// 得到结果集
																																				// fileUrl的值
				int tmresponseauditStatus = Convert.strToInt(
						countList.get(i).get("tmresponseauditStatus") == null ? "" : countList.get(i).get("tmresponseauditStatus").toString(), 0);// 得到结果集
																																					// fileUrl的值
				int tmincomeeauditStatus = Convert.strToInt(
						countList.get(i).get("tmincomeeauditStatus") == null ? "" : countList.get(i).get("tmincomeeauditStatus").toString(), 0);// 得到结果集
																																				// fileUrl的值
				if (tmIdentityauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (tmworkauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (tmaddressauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (tmresponseauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (tmincomeeauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (flag) {
					userAll++;
				}
				flag = false;
			}

		}
		request().setAttribute("byNamemap", countAll);
		request().setAttribute("usercount", userAll);

		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 后台-> 个人信息审核列表
	 */
	public String queryPerUserCredit() throws SQLException {
		Long userId = Convert.strToLong(request().getParameter("userId"), -1L);
		validateService.queryPerUserCredit(pageBean, userId);
		List<Map<String, Object>> list = pageBean.getPage();
		if (list == null)
			return SUCCESS;
		// 必填认证
		List<Map<String, Object>> must = new ArrayList<Map<String, Object>>();
		// 财力(可选)认证
		List<Map<String, Object>> fince = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			if (Convert.strToInt(map.get("sort") + "", 0) == 4) {// 可选认证
				fince.add(map);
				continue;
			}
			if (Convert.strToInt(map.get("sort") + "", 0) == 3) {// 必填认证
				must.add(map);
				continue;
			}
			if (Convert.strToInt(map.get("sort") + "", 0) == 2) {// 工作资料认证
				request().setAttribute("workStatus", map.get("auditStatus"));
				continue;
			}
			if (Convert.strToInt(map.get("sort") + "", 0) == 1) {// 基本个人资料认证
				request().setAttribute("personStatus", map.get("auditStatus"));
				continue;
			}
		}
		request().setAttribute("must", must);
		request().setAttribute("fince", fince);
		return SUCCESS;

	}

	/**
	 * 查询用户的图片情况（5大证件）
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryPerUserPicturMsg() throws SQLException, DataException {
		Map<String, String> map1 = new HashMap<String, String>();
		Map<String, String> materialsauthMap = null;
		Long userId = -1L;
		String strUserId = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		Integer materAuthTypeIdStr = Convert.strToInt(paramMap.get("materAuthTypeId"), -1);
		if (strUserId != null) {
			userId = Convert.strToLong(strUserId, -1L);
			validateService.queryPerUserPicturMsg(pageBean, userId, materAuthTypeIdStr);
			List<Map<String, Object>> pMap = null;
			pMap = pageBean.getPage();// 获取到page集合
			Long tmId = -1L;
			if (pMap != null && pMap.size() > 0) {
				for (int i = 0; i < pMap.size(); i++) {
					String fileUrl = pMap.get(i).get("tmid").toString();// 得到结果集
																		// fileUrl的值
					String[] fileUrls = fileUrl.split(";");// 截取;符号 放入String数组
					if (fileUrls != null && fileUrls.length > 0) {
						tmId = Convert.strToLong(fileUrls[0], -1);
					}
				}
			}
			if (tmId != -1) {
				request().setAttribute("tmId", tmId);
			}
			map1 = validateService.queryUserNameById(userId);
			materialsauthMap = validateService.querymaterialsauth(userId, materAuthTypeIdStr);// 查询证件类型
		}
		request().setAttribute("userId", userId);
		request().setAttribute("materAuthTypeIdStr", materAuthTypeIdStr);
		if (map1 != null && map1.size() > 0) {
			request().setAttribute("username", map1.get("username").toString());
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 查询审核记录
		list = validateService.queryAdminCheckList(userId, materAuthTypeIdStr);
		request().setAttribute("checkList", list);
		request().setAttribute("materAuthTypeIdStr", materAuthTypeIdStr);// 将类型传值到后台
		request().setAttribute("materialsauthMap", materialsauthMap);
		return SUCCESS;

	}

	public String queryPerUserPicturMsginit() throws SQLException, DataException {
		Long userId = Convert.strToLong(request().getParameter("userId"), -1L);
		Long TypeId = Convert.strToLong(request().getParameter("materAuthTypeId"), -1L);
		request().setAttribute("userId", userId);
		request().setAttribute("TypeId", TypeId);
		selectvalue = TypeId;// 复制给select标签的默认值
		return SUCCESS;
	}

	/**
	 * 查看五大证件单个证件类型
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String queryPersonPictureDate() throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		String strUserId = SqlInfusion.FilteSqlInfusion(request().getParameter("userId"));
		int TypeId = Convert.strToInt(request().getParameter("TypeId"), -1);
		Long tmdid = Convert.strToLong(request().getParameter("adf"), -1);
		Long userId = -1L;
		if (strUserId != null) {
			userId = Convert.strToLong(strUserId, -1L);
			if (userId != -1 && TypeId != -1) {
				map = validateService.queryPerPictruMsgCallBack(userId, TypeId, tmdid);
			}
		}
		request().setAttribute("map", map);
		return SUCCESS;
	}

	/**
	 * 查看可选证件单个证件类型
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String queryPersonSelectPictureDate() throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		String strUserId = SqlInfusion.FilteSqlInfusion(request().getParameter("userId"));
		int TypeId = Convert.strToInt(request().getParameter("TypeId"), -1);
		Long tmdid = Convert.strToLong(request().getParameter("adf"), -1);
		Long userId = -1L;
		if (strUserId != null) {
			userId = Convert.strToLong(strUserId, -1L);
			if (userId != -1 && TypeId != -1) {
				map = validateService.queryPerPictruMsgCallBack(userId, TypeId, tmdid);
			}
		}
		request().setAttribute("map", map);
		return SUCCESS;
	}

	/**
	 * 删除单个证件
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String delcertificate() throws SQLException {
		Long tmdid = Convert.strToLong(paramMap.get("tmdid"), -1);
		validateService.deletecertificate(tmdid);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_materialimagedetal", admin.getUserName(), IConstants.DELETE, admin.getLastIP(), 0, "删除单个证件", 2);
		return null;
	}

	/**
	 * 查看图片状况但不可审核
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String queryPersonPictureDate_() throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		String strUserId = SqlInfusion.FilteSqlInfusion(request().getParameter("userId"));
		int TypeId = Convert.strToInt(request().getParameter("TypeId"), -1);
		Long userId = -1L;

		if (strUserId != null) {
			userId = Convert.strToLong(strUserId, -1L);
			if (userId != -1 && TypeId != -1) {
			}
		}
		request().setAttribute("map", map);
		return SUCCESS;
	}

	/**
	 * 用户可选资料验证
	 * 
	 * @return
	 */
	public String queryselectInit() {
		String types = Convert.strToStr(request("types"), "-1");
		request().setAttribute("types", types);
		return SUCCESS;
	}

	/**
	 * 用户可选资料验证跳转详情
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryselect() throws SQLException, DataException {
		String username = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String tausername = SqlInfusion.FilteSqlInfusion(paramMap.get("tausername"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		String materAuthTypeId = SqlInfusion.FilteSqlInfusion(paramMap.get("materAuthTypeId"));
		Integer maId = -1;
		if (StringUtils.isNotBlank(materAuthTypeId)) {
			maId = Convert.strToInt(materAuthTypeId, -1);
		}
		validateService.queryselect(pageBean, null, username, realName, tausername, maId);
		List<Map<String, Object>> lists = null;
		@SuppressWarnings("unused")
		Vector<Long> list = new Vector<Long>();
		lists = validateService.queryselect1(null, username, realName, tausername, maId);// 获取集合
		long allCount = 0;
		long userCount = 0;
		boolean flag = false;
		if (lists.size() > 0 && lists != null) {
			for (int i = 0; i < lists.size(); i++) {
				int tmIdentityauditStatus = Convert.strToInt(lists.get(i).get("wait") == null ? "" : lists.get(i).get("wait").toString(), 0);// 得到结果集
																																				// fileUrl的值
				allCount += tmIdentityauditStatus;
				if (tmIdentityauditStatus > 0) {
					flag = true;
				}
				if (flag) {
					userCount++;
				}
				flag = false;
			}

		}
		request().setAttribute("byNamemap", allCount);
		request().setAttribute("usercount", userCount);

		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 可选初始化
	 * 
	 * @return
	 */
	private Long selectvalue;

	public Long getSelectvalue() {
		return selectvalue;
	}

	public void setSelectvalue(Long selectvalue) {
		this.selectvalue = selectvalue;
	}

	/**
	 * 查询可选认证图片
	 * 
	 * @return
	 */
	public String queryselectindexMethod() {
		Long userId = Convert.strToLong(request().getParameter("userId"), -1L);
		Long TypeId = Convert.strToLong(request().getParameter("TypeId"), -1L);
		request().setAttribute("userId", userId);
		request().setAttribute("TypeId", TypeId);
		selectvalue = TypeId;
		return SUCCESS;
	}

	/**
	 * 可选认证的图片展示
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String queryselectinoforMethod() throws SQLException, Exception {
		@SuppressWarnings("unused")
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		Map<String, String> map1 = new HashMap<String, String>();
		Map<String, String> materialsauthMap = null;
		Long userId = -1L;
		String strUserId = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		Integer materAuthTypeIdStr = Convert.strToInt(paramMap.get("materAuthTypeId"), -1);
		if (strUserId != null) {
			userId = Convert.strToLong(strUserId, -1L);
			validateService.querySelectPictureDate(pageBean, userId, materAuthTypeIdStr);
			List<Map<String, Object>> pMap = null;
			pMap = pageBean.getPage();// 获取到page集合
			Long tmId = -1L;
			if (pMap != null && pMap.size() > 0) {
				for (int i = 0; i < pMap.size(); i++) {
					String fileUrl = (pMap.get(i).get("tmid")) == null ? "" : (pMap.get(i).get("tmid")).toString();// 得到结果集
																													// fileUrl的值
					String[] fileUrls = fileUrl.split(";");// 截取;符号 放入String数组
					if (fileUrls != null && fileUrls.length > 0) {
						tmId = Convert.strToLong(fileUrls[0], -1);
					}
				}
			}
			if (tmId != -1) {
				request().setAttribute("tmId", tmId);
			}

			map1 = validateService.queryUserNameById(userId);
			materialsauthMap = validateService.querymaterialsauth(userId, materAuthTypeIdStr);// 查询证件类型
		}
		request().setAttribute("userId", userId);
		request().setAttribute("materAuthTypeIdStr", materAuthTypeIdStr);
		if (map1 != null && map1.size() > 0) {
			request().setAttribute("username", map1.get("username").toString());
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 查询审核记录
		list = validateService.queryAdminCheckList(userId, materAuthTypeIdStr);
		request().setAttribute("checkList", list);
		request().setAttribute("materAuthTypeIdStr", materAuthTypeIdStr);// 将类型传值到后台
		request().setAttribute("materialsauthMap", materialsauthMap);
		return SUCCESS;
	}

	public String querycreditindexMethod() {
		String types = Convert.strToStr(request("types"), "-1");
		request().setAttribute("types", types);
		return SUCCESS;
	}

	public String querycreditinoforMethod() throws SQLException, DataException {
		String username = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String starttime = SqlInfusion.FilteSqlInfusion(paramMap.get("starttime"));
		String endtime = SqlInfusion.FilteSqlInfusion(paramMap.get("endtime"));
		Integer austatus = Convert.strToInt(paramMap.get("austatus"), -1);
		validateService.querycreditLimitApply(pageBean, username, austatus, starttime, endtime);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 申请信用额度的详情
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String querycreditMsgMethod() throws DataException, SQLException {
		@SuppressWarnings("unused")
		Long userId = Convert.strToLong(request().getParameter("uId"), -1L);
		Long ti = Convert.strToLong(request().getParameter("ti"), -1L);// 审核表中这条记录的唯一id号
		Map<String, String> map = null;
		map = validateService.queryrequestCredit(ti);
		request().setAttribute("map", map);
		request().setAttribute("ti", ti);
		return SUCCESS;
	}

	/**
	 * 申请额度处理
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String updateUserCreditLimitMethod() throws SQLException, DataException, IOException {
		Long userId = Convert.strToLong(paramMap.get("userId"), -1L);
		Integer validp = Convert.strToInt(paramMap.get("validp"), -1);
		Long ti = Convert.strToLong(paramMap.get("ti"), -1L);// 额度审核表中唯一id号
		// 审核状态
		if (validp == null || validp == -1) {
			JSONUtils.printStr("0");
			return null;
		}
		String preAmountStr = paramMap.get("preAmount");
		if (StringUtils.isBlank(preAmountStr)) {
			JSONUtils.printStr("4");
			return null;
		}
		// 申请额度
		String applyAmountStr = paramMap.get("applyAmount");
		if (StringUtils.isBlank(applyAmountStr)) {
			JSONUtils.printStr("1");
			return null;
		}

		// 正则表达式 判断是否为数字 包括 负数 /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/
		Pattern pattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?$");
		Matcher m = pattern.matcher(paramMap.get("applyAmount"));
		if (!m.matches()) {
			JSONUtils.printStr("8");
			return null;
		}

		// 审核原因
		String content = Convert.strToStr(paramMap.get("content"), null);
		if (StringUtils.isBlank(content)) {
			JSONUtils.printStr("2");
			return null;
		}
		Integer Creditstatus = null;
		if (StringUtils.isNotBlank(applyAmountStr) && validp != null && validp != -1) {
			BigDecimal applyAmount = new BigDecimal(applyAmountStr);
			@SuppressWarnings("unused")
			BigDecimal preAmount = new BigDecimal(preAmountStr);
			/*
			 * if (applyAmount.compareTo(preAmount) == 1) {
			 * JSONUtils.printStr("5"); return null; }
			 */
			if (validp == 1) {
				Creditstatus = 3;
			} else if (validp == 0) {
				Creditstatus = 2;
			} else {
				Creditstatus = 1;
			}
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			Long reslut = -1L;
			if (admin != null) {
				reslut = validateService.updateUserCreditLimit(userId, Creditstatus, applyAmount, content, admin.getId(), ti);
				operationLogService
						.addOperationLog("t_user/t_crediting", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "用户额度审核处理", 2);

				if (reslut <= 0) {
					JSONUtils.printStr("7");
					return null;
				} else {
					JSONUtils.printStr("6");
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * 查询用户的资料详情
	 * 
	 * @return
	 */
	public String queryPictureDateCount() {
		return SUCCESS;
	}

	public String queryDateCountinfo() throws SQLException {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		Integer materAuthTypeId = Convert.strToInt(paramMap.get("materAuthTypeId"), -1);
		Long roleId = Convert.strToLong(paramMap.get("roleId"), -1);

		validateService.queryPictureDateCount(pageBean, roleId, materAuthTypeId, userName, realName);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 总审核
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateallcheckMethod() throws Exception {
		String flag = SqlInfusion.FilteSqlInfusion(paramMap.get("flag"));
		if (StringUtils.isBlank(flag)) {
			JSONUtils.printStr("0");
			return null;
		}
		Integer score = 0;
		Integer checkStatus = 2;// 审核失败
		if (flag.equals("a")) {
			// 正则表达式 判断是否为数字 包括 负数 /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/
			Pattern pattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?$");
			Matcher m = pattern.matcher(paramMap.get("score"));
			if (!m.matches()) {
				JSONUtils.printStr("6");
				return null;
			}
			/*
			 * if(!StringUtils.isNumeric(paramMap.get("score"))){
			 * JSONUtils.printStr("6"); return null; }
			 */
			score = Convert.strToInt(paramMap.get("score"), -10000);
			if (null != score && score == -10000) {
				JSONUtils.printStr("3");
				return null;
			}
			checkStatus = 3;
		}

		if (score > 20 || score < -10) {
			JSONUtils.printStr("130");
			return null;
		}

		String content = paramMap.get("content");
		if (StringUtils.isBlank(content)) {
			JSONUtils.printStr("4");
			return null;
		}
		Long tmId = Convert.strToLong(paramMap.get("tmId"), -1L);
		if (tmId == -1L) {
			JSONUtils.printStr("0");
			return null;
		}
		Long userId = Convert.strToLong(paramMap.get("userId"), -1L);
		if (userId == -1L) {
			JSONUtils.printStr("0");
			return null;
		}
		// 必须是先全部图片都是通过审核之后才能够进行总审核 不然进行不了总审核(只有这样才能有失效时间才能确定)
		Map<String, String> countMap = null;
		int countcheck = 0;
		if (tmId != -1) {
			countMap = validateService.queryCheckCount(tmId);// 统计管理员未审核的证件明细个数
			if (countMap.size() > 0 && countMap != null) {
				countcheck = Convert.strToInt(countMap.get("cccc"), 0);
			}
		} else {
			return null;
		}
		if (countcheck != 0) {
			JSONUtils.printStr("5");
			return null;
		}

		Integer materAuthTypeIdStr = Convert.strToInt(paramMap.get("materAuthTypeIdStr"), -1);// 获取到类型
		if (materAuthTypeIdStr == -1) {
			JSONUtils.printStr("0");
			return null;
		}

		if (userId != -1 && materAuthTypeIdStr != -1) {
			// 获取管理员的信息
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			Long adminId = admin.getId();// 获取id

			Long result = -1L;
			// 在更新单个证件的信用积分之前查询更新前的信用积分
			Integer cCriditing = 0;// 单个证件的信用总分
			Map<String, String> cmap = countWorkStatusService.queryC(userId, materAuthTypeIdStr);
			if (cmap != null && cmap.size() > 0) {
				cCriditing = Convert.strToInt(cmap.get("criditing"), 0);
			}
			if (admin != null) {
				// 学历认证扣费
				if (materAuthTypeIdStr == 9) {// 学历认证的type为9
					Map<String, Object> platformCostMap = getPlatformCost();
					double eduAuthFee = Convert.strToDouble(platformCostMap.get(IAmountConstants.EDU_AUTH_FEE) + "", 0);
					result = validateService.addeducationcost(userId, eduAuthFee + "");// 每次审核2块的手续费
					if (result <= 0) {
						JSONUtils.printStr("0");
						return null;
					}
				}
				result = validateService.Updatecreditrating(userId, content, score, adminId, materAuthTypeIdStr, checkStatus);
				operationLogService.addOperationLog("t_user/t_materialsauth", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
						"更新信用积分和插入审核列表", 2);

			}
			if (result > 0) {
				// 添加审核记录
				validateService.addCheckRecord(userId, content, score, adminId, materAuthTypeIdStr, cCriditing);
				JSONUtils.printStr("1");
				return null;
			} else {
				JSONUtils.printStr("0");
				return null;
			}
		}
		return null;
	}

	/**
	 * 审核用户的上传资料
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException
	 * @throws DataException
	 */
	public String Updatematerialsauth() throws IOException, SQLException, ParseException, DataException {

		Long userId = Convert.strToLong(paramMap.get("userId"), -1L);
		Long valid = Convert.strToLong(paramMap.get("valid"), -1L);// 0 1
		Integer visiable = Convert.strToInt(paramMap.get("visiable"), 2);// 0 1
		Long tmdid = Convert.strToLong(paramMap.get("tmdid"), -1L);
		Long tmid = Convert.strToLong(paramMap.get("tmid"), -1L);
		Long materAuthTypeId = Convert.strToLong(paramMap.get("materAuthTypeId"), -1L);
		String option = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("option")), null);

		if (valid == -1L) {
			JSONUtils.printStr("1");
			return null;
		}
		if (option == null) {
			JSONUtils.printStr("0");
			return null;
		}
		if (userId != -1L && materAuthTypeId != -1L && valid != -1L && tmdid != -1L && tmid != -1L) {
			Integer auditStatus = null;
			if (valid == 0) {
				auditStatus = 2;
			}
			if (valid == 1) {
				auditStatus = 3;
			}
			Long result = -1L;
			result = validateService.Updatematerialsauth(tmid, userId, materAuthTypeId, option, auditStatus, tmdid, visiable);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_materialimagedetal", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "审核用户的上传资料", 2);
			if (result > 0) {
				if (materAuthTypeId <= 5) {
					JSONUtils.printStr("2");// 为成功审核
				} else {
					JSONUtils.printStr("4");// 为成功审核可选认证
				}
				return null;
			} else {
				JSONUtils.printStr("3");
				return null;
			}

		}

		return null;
	}

	/**
	 * 手机变更初始化
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updatephonex() {
		return SUCCESS;
	}

	/**
	 * 手机变更数据 第一个标签
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws Exception
	 */
	public String updatephonexf() throws Exception {
		String username = SqlInfusion.FilteSqlInfusion(paramMap.get("username"));
		String starttime = SqlInfusion.FilteSqlInfusion(paramMap.get("starttime"));
		String endtime = SqlInfusion.FilteSqlInfusion(paramMap.get("endtime"));
		// List<Map<String,Object>> tellist = null;
		// tellist = validateService.querytelphone(null);
		validateService.querytelphonePage(null, pageBean, username, starttime, endtime);

		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 手机绑定导出excel
	 * 
	 * @return
	 */
	public String exportupdatephonex() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			String username = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("username")), null);
			if (StringUtils.isNotBlank(username)) {
				username = URLDecoder.decode(username, "UTF-8"); // 中文乱码转换
			}
			String starttime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("starttime")), null);
			String endtime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("endtime")), null);
			// 手机绑定列表
			validateService.querytelphonePage(null, pageBean, username, starttime, endtime);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("手机绑定列表", pageBean.getPage(), new String[] { "用户名", "真是姓名", "手机号码", "投标总额", "申请时间" },
					new String[] { "username", "realName", "cellPhone", "amountall", "requsetTime", });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_phone_banding_review", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出手机绑定列表",
					2);

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

	public String updatephonexChange() {
		String types = Convert.strToStr(request("types"), "-1");
		request().setAttribute("types", types);
		return SUCCESS;
	}

	/**
	 * 手机变更第二个标签
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String updatephonexfChange() throws SQLException, Exception {
		String username = SqlInfusion.FilteSqlInfusion(paramMap.get("username"));
		String starttime = SqlInfusion.FilteSqlInfusion(paramMap.get("starttime"));
		String endtime = SqlInfusion.FilteSqlInfusion(paramMap.get("endtime"));
		Integer statuss = Convert.strToInt(paramMap.get("statss"), -1);
		validateService.querytelphonePage2(null, pageBean, username, starttime, endtime, statuss);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 导出已还款列表
	 * 
	 * @return
	 */
	public String exportupdatephonexfChange() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			String username = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("username")), null);
			if (StringUtils.isNotBlank(username)) {
				username = URLDecoder.decode(username, "UTF-8");
			}
			String starttime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("starttime")), null);
			String endtime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("endtime")), null);
			int statuss = Convert.strToInt(request().getParameter("statss"), -1);
			// 已还款记录列表
			validateService.querytelphonePage2(null, pageBean, username, starttime, endtime, statuss);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			if (list != null) {
				for (Map<String, Object> map : list) {
					if (map.get("tpStatus").toString().equals("1")) {
						map.put("tpStatus", "成功");
					} else if (map.get("tpStatus").toString().equals("2")) {
						map.put("tpStatus", "审核中");
					} else if (map.get("tpStatus").toString().equals("3")) {
						map.put("tpStatus", "取消");
					} else if (map.get("tpStatus").toString().equals("4")) {
						map.put("tpStatus", "失败");
					}

				}
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("手机变更列表", pageBean.getPage(),
					new String[] { "用户名", "真实姓名", "手机号码", "申请手机", "投标总额", "申请时间", "状态" }, new String[] { "username", "realName", "cellPhone",
							"mobilePhone", "amountall", "requsetTime", "tpStatus", });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_phone_binding_info", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出手机变更列表", 2);

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
	 * 查看用户手机详情和审核手机更改状态
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String queryUserTelMethod() throws SQLException {
		Map<String, String> map = null;
		Long userId = -1L;
		String userIdStr = SqlInfusion.FilteSqlInfusion(request().getParameter("id"));
		String tpdiidStr = SqlInfusion.FilteSqlInfusion(request().getParameter("tpdiid"));
		if (StringUtils.isNotBlank(userIdStr)) {
			userId = Convert.strToLong(userIdStr, -1);
			Long tpdiid = Convert.strToLong(tpdiidStr, -1);
			map = validateService.queryUserTelMsg(userId, tpdiid);
		}
		request().setAttribute("map", map);
		return SUCCESS;

	}

	/**
	 * 审核和更改用户的手机号码
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	public String updateUserPhoneMethod() throws SQLException, IOException {
		Long result = -1L;
		String userStr = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		String tpiidStr = SqlInfusion.FilteSqlInfusion(paramMap.get("tpiid"));
		Long userId = -1L;
		if (StringUtils.isNotBlank(userStr)) {
			userId = Convert.strToLong(userStr, -1L);
		} else {
			JSONUtils.printStr("0");
			return null;
		}
		Long tpiid = -1L;
		if (StringUtils.isNotBlank(tpiidStr)) {
			tpiid = Convert.strToLong(tpiidStr, -1L);
		} else {
			JSONUtils.printStr("0");
			return null;
		}

		Integer auditStatus = Convert.strToInt(paramMap.get("vali"), -1);
		if (auditStatus == null ) {
//		if (auditStatus == null && auditStatus != -1) {
			JSONUtils.printStr("1");
			return null;
		}

		String option = SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		if (StringUtils.isBlank(option)) {
			JSONUtils.printStr("2");
			return null;
		}
		String newTelNumber = SqlInfusion.FilteSqlInfusion(paramMap.get("newPhone"));
		if (StringUtils.isBlank(newTelNumber)) {
			JSONUtils.printStr("3");
			return null;
		}
		// 查询用户经过基本资料审核后已经绑定的手机号码
		Map<String, String> phoneMap = null;
		try {
			phoneMap = beVipService.queryPUser(userId);
		} catch (DataException e) {
			e.printStackTrace();
		}
		String personCellPhone = "";
		if (phoneMap.size() > 0 && phoneMap != null) {
			personCellPhone = Convert.strToStr(phoneMap.get("cellphone"), "");// 获取基本绑定的手机号码
			if (personCellPhone == "") {
				JSONUtils.printStr("5");
				return null;
			}
		} else {
			JSONUtils.printStr("5");
			return null;
		}

		result = validateService.updateUserPhoneService(userId, auditStatus, option, newTelNumber, tpiid, personCellPhone);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_person", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "审核和更改用户手机号码", 2);
		if (result >= 0) {
			JSONUtils.printStr("4");
			return null;
		} else {
			JSONUtils.printStr("5");
			return null;
		}

	}

	/**
	 * 查询可选认证中的个人信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryselectMethod() throws SQLException, DataException {
		Long userId = Convert.strToLong(request().getParameter("userId"), -1L);
		Map<String, String> map = null;
		Map<String, String> totalPass = null;
		Map<String, String> SelectPassTotal = null;
		Map<String, String> SelectCledit = null;
		// List<Map<String,Object>> SelectCledit = null;
		if (userId != null) {
			request().setAttribute("id", userId);
			map = validateService.queryselectpicture(userId);
			totalPass = validateService.queryTotaPass(userId);
			SelectPassTotal = validateService.querySelectPassTotal(userId);
			SelectCledit = validateService.querySelectCledit(userId);
			// SelectCledit = validateService.querySelectCleditList(userId);
			request().setAttribute("SelectPassTotal", SelectPassTotal);
			request().setAttribute("map", map);
			request().setAttribute("totalPass", totalPass);
			request().setAttribute("SelectCledit", SelectCledit);

		}
		return SUCCESS;
	}

	public String queryPersonInfo() throws Exception {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")), null);
		String phone = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("phone")), null);
		String idNo = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("idNo")), null);
		int source = Convert.strToInt(paramMap.get("source"), -1);

		validateService.queryPersonInfo(pageBean, userName, realName, phone, idNo, source);
		// 获取各种认证的审核状态
		List<Map<String, Object>> countList = validateService.queryUserCreditCount(userName, realName, phone, idNo);
		// 待审核的认证数量
		long countAll = 0;
		long userAll = 0;
		boolean flag = false;
		if (countList != null && countList.size() > 0)
			for (int i = 0; i < countList.size(); i++) {
				// 必填认证 身份认证
				int tmIdentityauditStatus = Convert.strToInt(
						countList.get(i).get("tmIdentityauditStatus") == null ? "" : countList.get(i).get("tmIdentityauditStatus").toString(), 0);
				// 必填认证 工作认证
				int tmworkauditStatus = Convert.strToInt(
						countList.get(i).get("tmworkauditStatus") == null ? "" : countList.get(i).get("tmworkauditStatus").toString(), 0);
				// 必填认证 居住地认证
				int tmaddressauditStatus = Convert.strToInt(
						countList.get(i).get("tmaddressauditStatus") == null ? "" : countList.get(i).get("tmaddressauditStatus").toString(), 0);
				// 必填认证 信用认证
				int tmresponseauditStatus = Convert.strToInt(
						countList.get(i).get("tmresponseauditStatus") == null ? "" : countList.get(i).get("tmresponseauditStatus").toString(), 0);
				// 必填认证 收入认证
				int tmincomeeauditStatus = Convert.strToInt(
						countList.get(i).get("tmincomeeauditStatus") == null ? "" : countList.get(i).get("tmincomeeauditStatus").toString(), 0);
				if (tmIdentityauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (tmworkauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (tmaddressauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (tmresponseauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (tmincomeeauditStatus == 1) {
					countAll++;
					flag = true;
				}
				if (flag) {
					userAll++;
				}
				flag = false;
			}

		request().setAttribute("byNamemap", countAll);
		request().setAttribute("usercount", userAll);

		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	public String queryPersonAuditStatusInfo() throws SQLException, DataException {

		Integer auditStatus = Convert.strToInt(paramMap.get("auditStatus"), -1);

		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")), null);// 真实姓名
		String cellPhone = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("cellPhone")), null);
		String idNo = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("idNo")), null);// 真实姓名

		validateService.queryPersonAuditStatusInfo(pageBean, userName, realName, auditStatus, cellPhone, idNo);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 查询个人的工作的详细情况
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryWork() throws Exception {
		long id = Convert.strToLong(request().getParameter("uid"), -1L);
		request().setAttribute("uid", id);
		Map<String, String> map = new HashMap<String, String>();
		map = validateService.queryWorkDataById(id);
		if (map == null) {// 如果工作信息为空的话
			request().setAttribute("flagw", "1");
		}
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("workPro").toString(), -1L);
			cityId = Convert.strToLong(map.get("workCity").toString(), -1L);
		}
		request().setAttribute("id", id);
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		if (workPro > 0) {
			cityList = regionService.queryRegionList(-1L, workPro, 2);
		}
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
		return SUCCESS;
	}

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

	/**
	 * 更新用户工作状态
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 */
	public String updateworkStatus() throws SQLException, IOException, DataException {
		JSONObject json = new JSONObject();
		Long flag = Convert.strToLong(paramMap.get("flag").toString(), -1);
		Long userId = Convert.strToLong(paramMap.get("userId"), -1L);
		int auditStatus = -1;
		if (flag == 1)
			auditStatus = 3;
		else if (flag == 0)
			auditStatus = 2;
		else {
			json.put("msg", "出错啦");
			JSONUtils.printObject(json);
			return null;
		}

		Long resulId = -1L;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			resulId = validateService.updateworkStatus(userId, auditStatus, null, null, null, admin.getId());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		if (resulId > 0) {
			operationLogService.addOperationLog("t_workauth", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "更新用户工作状态成功", 2);
			json.put("msg", "审核成功");
			JSONUtils.printObject(json);
		} else {
			operationLogService.addOperationLog("t_workauth", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "更新用户工作状态失败", 2);
			json.put("msg", "审核失败");
			JSONUtils.printObject(json);
		}
		return null;
	}

	/**
	 * 后台根据收索来统计数量
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String dataInfoM() throws SQLException, IOException {
		Long resulId = -1L;
		Long userId = Convert.strToLong(paramMap.get("userId").toString(), -1L);
		Integer servicePersonId = Convert.strToInt(paramMap.get("selectid").toString(), -1);
		try {
			resulId = validateService.updataUserServiceMan(userId, servicePersonId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		if (resulId > 0) {
			JSONUtils.printStr("1");
			return null;
		} else {
			JSONUtils.printStr("0");
			return null;
		}
	}

	/**
	 * 更新用户的跟踪人
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String updataUserServiceMan() throws SQLException, IOException {
		Long resulId = -1L;
		Long userId = Convert.strToLong(paramMap.get("userId").toString(), -1L);
		Integer servicePersonId = Convert.strToInt(paramMap.get("selectid").toString(), -2);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			resulId = validateService.updataUserServiceMan(userId, servicePersonId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		if (resulId > 0) {
			operationLogService.addOperationLog("t_user", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "更新用户的跟踪人成功", 2);
			JSONUtils.printStr("1");
			return null;
		} else {
			operationLogService.addOperationLog("t_user", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "更新用户的跟踪人失败", 2);
			JSONUtils.printStr("0");
			return null;
		}
	}

	public String updateUserServiceMans() throws SQLException, IOException {
		String ids = SqlInfusion.FilteSqlInfusion(paramMap.get("ids"));
		String admins = SqlInfusion.FilteSqlInfusion(paramMap.get("admins"));
		String[] allIds = ids.split(",");
		String[] allAdmins = admins.split(",");
		if (allIds.length != allAdmins.length) {
			JSONUtils.printStr("1");
			return INPUT;
		}
		if (allIds.length > 0 && allAdmins.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if (tempId == -1) {
					JSONUtils.printStr("1");
					return INPUT;
				}
			}
			for (String str : allAdmins) {
				tempId = Convert.strToLong(str, -2);
				if (tempId == -2) {
					JSONUtils.printStr("1");
					return INPUT;
				}
			}
		} else {
			JSONUtils.printStr("1");
			return INPUT;
		}
		long resultId = validateService.updataUserServiceMans(ids, admins);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_user", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "更新用户客服", 2);
		if (resultId <= 0) {
			JSONUtils.printStr("2");
			return INPUT;
		}
		return null;

	}

	public String queryPersonInfoindex() {
		String types = Convert.strToStr(request("types"), "-1");
		request().setAttribute("types", types);
		return SUCCESS;
	}

	public String querynewUserCheckindex() {
		return SUCCESS;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	/**
	 * 合和年 审核单个可选认证信息
	 * 
	 * @return
	 */
	public String updateCheckByType() throws Exception {
		int materAuthTypeIdStr = Convert.strToInt(paramMap.get("typeId"), -1);
		long userId = Convert.strToLong(paramMap.get("userId"), -1);
		int status = Convert.strToInt(paramMap.get("status"), 3);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		Long adminId = admin.getId();

		long result = -1L;
		if (admin != null) {
			result = validateService.Updatecreditrating(userId, "", 0, adminId, materAuthTypeIdStr, status);// 3表示通过审核
			operationLogService.addOperationLog("t_user/t_materialsauth", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"更新信用积分和插入审核列表", 2);
		}
		if (result > 0) {
			JSONUtils.printStr("1");
		} else {
			JSONUtils.printStr("0");
		}
		return null;
	}

	/**
	 * 和合年 查询财富认证
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryFinanceById() {
		Long userId = Convert.strToLong(request("uid"), -1L);
		if (userId <= 0)
			return SUCCESS;
		List<Map<String, Object>> list;
		try {
			list = validateService.queryUserSelectMsg(false, userId);
			List<Map<String, Object>> map1 = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> map2 = new ArrayList<Map<String, Object>>();
			float size = list.size();
			for (int i = 0; i < size; i++) {
				if (i < size / 2)
					map1.add(list.get(i));
				else
					map2.add(list.get(i));
			}
			request().setAttribute("map1", map1);
			request().setAttribute("map2", map2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 合和年 查询用户5必填证件
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryUserMustMsg() {
		Long userId = Convert.strToLong(request("uid"), -1L);
		if (userId <= 0)
			return SUCCESS;
		List<Map<String, Object>> map;
		try {
			map = validateService.queryUserSelectMsg(true, userId);
			Map<String, String> map1 = validateService.queryUserNameById(userId);
			request().setAttribute("map", map);
			if (map1 != null && map1.size() > 0) {
				request().setAttribute("realName", map1.get("realName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 合和年 根据 用户id 认证id 查询认证图片
	 */
	public String queryMaterialsauthImg() {
		long userId = Convert.strToLong(request("userId"), -1);
		long materTypeId = Convert.strToLong(request("typeId"), -1);

		if (materTypeId <= 0 || userId <= 0)
			return SUCCESS;
		try {
			validateService.querymaterialsauthImg(pageBean, userId, materTypeId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return SUCCESS;
	}
}
