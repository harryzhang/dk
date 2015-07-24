package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.config.ChinaPnrConfig;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.SelectedService;
import com.sp2p.service.admin.AfterCreditManageService;

/**
 * @ClassName: AfterCreditManageAction.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:22:02
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 贷后管理控制层
 */
public class AfterCreditManageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(AfterCreditManageAction.class);
	private static final long serialVersionUID = 1L;

	private AfterCreditManageService afterCreditManageService;
	private SelectedService selectedService;

	private List<Map<String, Object>> borrowDeadlineList;
	private List<Map<String, Object>> userGroupList;

	/**
	 * @MethodName: lastRepayMentInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午10:26:42
	 * @Return:
	 * @Descb: 最近3天还款记录初始化
	 * @Throws:
	 */
	public String lastRepayMentInit() {
		return "success";
	}

	/**
	 * @MethodName: forPaymentInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:13:00
	 * @Return:
	 * @Descb: 待收款记录初始化
	 * @Throws:
	 */
	public String forPaymentInit() {
		return "success";
	}

	/**
	 * @MethodName: forPaymentInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:13:00
	 * @Return:
	 * @Descb: 待收款记录初始化
	 * @Throws:
	 */
	public String forPaymentDueInInit() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 7);// 得到前7
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(c.getTime());
		String strStart = mDateTime.substring(0, 16);//
		request().setAttribute("strStart", strStart);
		return "success";
	}

	/**
	 * @MethodName: forPaymentTotalInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午10:06:48
	 * @Return:
	 * @Descb: 代收款统计初始化
	 * @Throws:
	 */
	public String forPaymentTotalInit() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 7);// 得到前7
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(c.getTime());
		String strStart = mDateTime.substring(0, 16);//
		request().setAttribute("strStart", strStart);
		return "success";
	}

	/**
	 * @MethodName: lateRepayInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:13:22
	 * @Return:
	 * @Descb: 逾期的还款初始化
	 * @Throws:
	 */
	public String lateRepayInit() {
		return "success";
	}

	/**
	 * @MethodName: overduePaymentInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:13:58
	 * @Return:
	 * @Descb: 逾期垫付初始化
	 * @Throws:
	 */
	public String overduePaymentInit() {
		return "success";
	}

	/**
	 * 合和年 回购初始化
	 */
	public String backBuyInit() {
		return "success";
	}

	/**
	 * @MethodName: hasRepayInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:14:29
	 * @Return:
	 * @Descb: 已还款初始化
	 * @Throws:
	 */
	public String hasRepayInit() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -7);// 得到前7
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(c.getTime());
		String strStart = mDateTime.substring(0, 16);//
		request().setAttribute("strStart", strStart);

		request().setAttribute("endStart", formatter.format(new Date()));
		return "success";
	}

	/**
	 * @MethodName: lastRepayMentList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午10:26:54
	 * @Return:
	 * @Descb: 最近3天还款记录
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String lastRepayMentList() throws SQLException, DataException {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? "" : request().getParameter("curPage")));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
		String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName") == null ? "" : paramMap.get("realName"));
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title") == null ? "" : paramMap.get("title"));
		String status = SqlInfusion.FilteSqlInfusion(paramMap.get("status") == null ? "" : paramMap.get("status"));
		String type = SqlInfusion.FilteSqlInfusion(paramMap.get("type") == null ? "" : paramMap.get("type"));
		int statusInt = Convert.strToInt(status, -1);
		int borrowWayInt = Convert.strToInt(borrowWay, -1);

		afterCreditManageService.queryLastRepayMentByCondition(userName, borrowWayInt, realName, title, statusInt, type, pageBean);
		// 最近借款统计总额
		Map<String, String> repaymentMap = afterCreditManageService.queryRepaymentAmount(userName, borrowWayInt, realName, title, statusInt, type);
		request().setAttribute("repaymentMap", repaymentMap);
		// 当前页金额统计
		double currentAmount = 0;
		List<Map<String, Object>> mapList = pageBean.getPage();
		if (mapList != null) {
			for (Map<String, Object> map : mapList) {
				currentAmount = currentAmount + Convert.strToDouble(map.get("totalSum") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.##");
		request().setAttribute("currentAmount", fmt.format(currentAmount));

		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出最近3天还款记录excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportlastRepayMent() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		String type = request().getParameter("type");

		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			// 用户名
			String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("userName") == null ? "" : request().getParameter("userName"));
			userName = URLDecoder.decode(userName, "UTF-8");// 中文乱码转换
			// 真实姓名
			String realName = SqlInfusion.FilteSqlInfusion(request().getParameter("realName") == null ? "" : request().getParameter("realName"));
			realName = URLDecoder.decode(realName, "UTF-8");// 中文乱码转换
			// 借款标题
			String titles = SqlInfusion.FilteSqlInfusion(request().getParameter("titles") == null ? "" : request().getParameter("titles"));
			titles = URLDecoder.decode(titles, "UTF-8");
			// 借款类型
			int borrowWay = Convert.strToInt(request().getParameter("borrowWay"), -1);
			// 还款状态
			int status = Convert.strToInt(SqlInfusion.FilteSqlInfusion(request().getParameter("status")), -1);
			// 待还款详情
			afterCreditManageService.queryLastRepayMentByCondition(userName, borrowWay, realName, titles, status, type, pageBean);
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
					if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_NET_VALUE)) {
						map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
					} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_SECONDS)) {
						map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
					} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_GENERAL)) {
						map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
					} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_FIELD_VISIT)) {
						map.put("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
					} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
						map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
					} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
						map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
					}
					if (map.get("repayStatus").toString().equals("1")) {
						map.put("repayStatus", "未偿还");
					} else if (map.get("repayStatus").toString().equals("2")) {
						map.put("repayStatus", "已偿还");
					} else if (map.get("repayStatus").toString().equals("3")) {
						map.put("repayStatus", "偿还中");
					}
					if (map.get("servier") == null || map.get("servier") == "") {
						map.put("servier", "未分配");
					}
				}
			}

			HSSFWorkbook wb = null;
			if (type.equals("") || type == null) {
				wb = ExcelUtils
						.exportExcel("今天到期还款", pageBean.getPage(), new String[] { "ID", "用户名", "真是姓名", "借款标题", "期数", "类型", "到期时间", "应还金额(￥)", "还款时间", "跟踪客服", "是否已还款" },
								new String[] { "id", "username", "realName", "borrowTitle", "repayPeriod", "borrowWay", "repayDate", "totalSum", "realRepayDate", "servier",
										"repayStatus" });
				// 添加操作日志
				operationLogService.addOperationLog("v_t_repayment_h", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出今天到期还款", 2);
			} else if (type.equals("1")) {
				wb = ExcelUtils
						.exportExcel("明天到期还款", pageBean.getPage(), new String[] { "ID", "用户名", "真是姓名", "借款标题", "期数", "类型", "到期时间", "应还金额(￥)", "还款时间", "跟踪客服", "是否已还款" },
								new String[] { "id", "username", "realName", "borrowTitle", "repayPeriod", "borrowWay", "repayDate", "totalSum", "realRepayDate", "servier",
										"repayStatus" });
				// 添加操作日志
				operationLogService.addOperationLog("v_t_repayment_h", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出明天到期还款", 2);
			} else if (type.equals("2")) {
				wb = ExcelUtils
						.exportExcel("后天到期还款", pageBean.getPage(), new String[] { "ID", "用户名", "真是姓名", "借款标题", "期数", "类型", "到期时间", "应还金额(￥)", "还款时间", "跟踪客服", "是否已还款" },
								new String[] { "id", "username", "realName", "borrowTitle", "repayPeriod", "borrowWay", "repayDate", "totalSum", "realRepayDate", "servier",
										"repayStatus" });
				// 添加操作日志
				operationLogService.addOperationLog("v_t_repayment_h", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出后天到期还款", 2);
			}

			this.export(wb, new Date().getTime() + ".xls");
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

	// 应收款款详情
	@SuppressWarnings("unchecked")
	public String forPaymentList() throws SQLException, DataException {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? "" : request().getParameter("curPage")));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String investor = SqlInfusion.FilteSqlInfusion(paramMap.get("investor") == null ? "" : paramMap.get("investor"));
		String timeStart = SqlInfusion.FilteSqlInfusion(paramMap.get("timeStart") == null ? "" : paramMap.get("timeStart"));
		String timeEnd = SqlInfusion.FilteSqlInfusion(paramMap.get("timeEnd") == null ? "" : paramMap.get("timeEnd"));
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title") == null ? "" : paramMap.get("title"));
		String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay"));
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		String group = paramMap.get("group") == null ? "" : paramMap.get("group");
		int groupInt = Convert.strToInt(group, -1);
		// ----------add by houli 判断是否反选
		boolean inverse = paramMap.get("inverse") == null ? false : true;
		// --------------
		afterCreditManageService.queryForPaymentByCondition(investor, timeStart, timeEnd, title, borrowWayInt, groupInt, pageBean, inverse);
		// 应收款款统计总额

		Map<String, String> repaymentMap = afterCreditManageService.queryForPaymentAmount(investor, timeStart, timeEnd, title, borrowWayInt, groupInt, inverse);
		request().setAttribute("repaymentMap", repaymentMap);

		// 统计当前页应收款
		double receivableAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				receivableAmount = receivableAmount + Convert.strToDouble(map.get("forTotalSum") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.##");
		request().setAttribute("receivableAmount", fmt.format(receivableAmount));

		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出应收款详情
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportforPayment() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			// ---------add by houli 看是否是反选
			boolean inverse = request().getParameter("inverse").equals("checked") ? true : false;
			// --------
			int group = Convert.strToInt(request().getParameter("groupId"), -1);
			int borrowWay = Convert.strToInt(request().getParameter("borrowWayid"), -1);
			String investor = SqlInfusion.FilteSqlInfusion(request().getParameter("investor") == null ? "" : request().getParameter("investor"));
			String timeStart = SqlInfusion.FilteSqlInfusion(request().getParameter("timeStart") == null ? "" : request().getParameter("timeStart"));
			String timeEnd = SqlInfusion.FilteSqlInfusion(request().getParameter("timeEnd") == null ? "" : request().getParameter("timeEnd"));
			String title = SqlInfusion.FilteSqlInfusion(request().getParameter("titles") == null ? "" : request().getParameter("titles"));
			// 转换中文乱码
			title = URLDecoder.decode(title, "UTF-8");
			investor = URLDecoder.decode(investor, "UTF-8");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// --------

			// 待还款详情
			afterCreditManageService.queryForPaymentByCondition(investor, timeStart, timeEnd, title, borrowWay, group, pageBean, inverse);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			if (list == null) {
				list = new ArrayList<Map<String, Object>>();
			}
			for (Map<String, Object> map : list) {
				if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}
				if (map.get("groupName") == null || map.get("groupName") == "") {
					map.put("groupName", "未分配");
				}
				if (map.get("realName") == null || map.get("realName") == "") {
					map.put("realName", "未填写");
				}
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("应收款详情", pageBean.getPage(), new String[] { "投资人", "姓名",// modify
																												// by
																												// houli
																												// 添加用户组
					"用户组", "投资时间", "还款期数/总期数", "金额" }, new String[] { "investor", "realName", "groupName", "investTime", "repayPeriod", "forTotalSum" });
			this.export(wb, new Date().getTime() + ".xls");

			operationLogService.addOperationLog("v_t_forpayment_h", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出应收款列表", 2);
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

	// 待还款详情
	@SuppressWarnings("unchecked")
	public String forPaymentDueInList() throws SQLException, DataException {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? "" : request().getParameter("curPage")));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String investor = SqlInfusion.FilteSqlInfusion(paramMap.get("investor") == null ? "" : paramMap.get("investor"));
		String timeStart = SqlInfusion.FilteSqlInfusion(paramMap.get("timeStart") == null ? "" : paramMap.get("timeStart"));
		String timeEnd = SqlInfusion.FilteSqlInfusion(paramMap.get("timeEnd") == null ? "" : paramMap.get("timeEnd"));
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title") == null ? "" : paramMap.get("title"));
		String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay"));
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		String group = SqlInfusion.FilteSqlInfusion(paramMap.get("group") == null ? "" : paramMap.get("group"));
		int groupInt = Convert.strToInt(group, -1);
		// ----------add by houli 判断是否反选
		boolean inverse = paramMap.get("inverse") == null ? false : true;
		// --------------
		afterCreditManageService.queryForPaymentByDueIn(investor, timeStart, timeEnd, title, borrowWayInt, groupInt, pageBean, inverse);
		// 待收款款统计总额

		Map<String, String> repaymentMap = afterCreditManageService.queryForPaymentAmount(investor, timeStart, timeEnd, title, borrowWayInt, groupInt, inverse);
		request().setAttribute("repaymentMap", repaymentMap);
		// 得到当前页待收款金额统计
		double repayAmount = 0;
		List<Map<String, Object>> repayList = pageBean.getPage();

		if (repayList != null) {
			for (Map<String, Object> map : repayList) {
				repayAmount = repayAmount + Convert.strToDouble(map.get("forTotalSum") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.##");
		request().setAttribute("repayAmount", fmt.format(repayAmount));

		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出待还款详情
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportforPaymentDueIn() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			// ---------add by houli 看是否是反选
			boolean inverse = request().getParameter("inverse").equals("checked") ? true : false;
			// --------
			int group = Convert.strToInt(request().getParameter("groupId"), -1);
			int borrowWay = Convert.strToInt(request().getParameter("borrowWayid"), -1);
			String investor = SqlInfusion.FilteSqlInfusion(request().getParameter("investor") == null ? "" : request().getParameter("investor"));
			String timeStart = SqlInfusion.FilteSqlInfusion(request().getParameter("timeStart") == null ? "" : request().getParameter("timeStart"));
			String timeEnd = SqlInfusion.FilteSqlInfusion(request().getParameter("timeEnd") == null ? "" : request().getParameter("timeEnd"));
			String title = SqlInfusion.FilteSqlInfusion(request().getParameter("titles") == null ? "" : request().getParameter("titles"));
			// 转换中文乱码
			title = URLDecoder.decode(title, "UTF-8");
			investor = URLDecoder.decode(investor, "UTF-8");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// --------

			// 待还款详情
			afterCreditManageService.queryForPaymentByDueIn(investor, timeStart, timeEnd, title, borrowWay, group, pageBean, inverse);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			if (list == null) {
				list = new ArrayList<Map<String, Object>>();
			}
			for (Map<String, Object> map : list) {
				if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}
				if (map.get("groupName") == null || map.get("groupName") == "") {
					map.put("groupName", "未分配");
				}
				if (Convert.strToLong(map.get("isDayThe") + "", -1L) == 1) {
					map.put("isDayThe", "否");
				} else {
					map.put("isDayThe", "是");
				}
				if (map.get("realNames") == null || map.get("realNames") == "") {
					map.put("realNames", "未填写");
				}
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("待还款列表", pageBean.getPage(), new String[] { "借款人", "姓名",// modify
																												// by
																												// houli
																												// 添加用户组
					"借款标题", "用户组", "借款时间", "标旳类型", "是否天标 ", "期数/总期数  ", "应还时间 ", "应还金额  " }, new String[] { "username", "realNames", "borrowTitle", "groupName", "publishTime",
					"borrowWay", "isDayThe", "repayPeriod", "repayDate", "forTotalSum" });
			this.export(wb, new Date().getTime() + ".xls");

			operationLogService.addOperationLog("v_t_deuin_list", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出待还款列表", 2);

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
	 * @MethodName: forPaymentTotalList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午10:07:25
	 * @Return:
	 * @Descb: 待收款统计记录
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String forPaymentTotalList() throws SQLException, DataException {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? "" : request().getParameter("curPage")));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String investor = SqlInfusion.FilteSqlInfusion(paramMap.get("investor") == null ? "" : paramMap.get("investor"));
		String timeStart = SqlInfusion.FilteSqlInfusion(paramMap.get("timeStart") == null ? "" : paramMap.get("timeStart"));
		String timeEnd = SqlInfusion.FilteSqlInfusion(paramMap.get("timeEnd") == null ? "" : paramMap.get("timeEnd"));
		String deadline = SqlInfusion.FilteSqlInfusion(paramMap.get("deadline") == null ? "" : paramMap.get("deadline"));
		int deadlineWayInt = Convert.strToInt(deadline, -1);
		String group = SqlInfusion.FilteSqlInfusion(paramMap.get("group") == null ? "" : paramMap.get("group"));
		int groupInt = Convert.strToInt(group, -1);
		// -------add by houli 添加反选条件
		boolean inverse = paramMap.get("inverse") == null ? false : true;
		//
		afterCreditManageService.queryForPaymentTotalByCondition(investor, timeStart, timeEnd, deadlineWayInt, groupInt, pageBean, inverse);
		// 待收款款总计统计总额
		Map<String, String> repaymentMap = afterCreditManageService.queryForPaymentTotalAmount(investor, timeStart, timeEnd, deadlineWayInt, groupInt, inverse);
		request().setAttribute("repaymentMap", repaymentMap);
		// 得到当前页待收款统计
		double payAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				payAmount = payAmount + Convert.strToDouble(map.get("forTotalSum") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.##");
		request().setAttribute("payAmount", fmt.format(payAmount));
		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出待收款总计
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportPaymentTotal() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			// 查看是否是反转
			boolean inverse = request().getParameter("inverse").equals("checked") ? true : false;
			// --------
			int group = Convert.strToInt(request().getParameter("groupId"), -1);
			String investor = SqlInfusion.FilteSqlInfusion(request().getParameter("investor") == null ? "" : request().getParameter("investor"));
			String timeStart = SqlInfusion.FilteSqlInfusion(request().getParameter("timeStart") == null ? "" : request().getParameter("timeStart"));
			String timeEnd = SqlInfusion.FilteSqlInfusion(request().getParameter("timeEnd") == null ? "" : request().getParameter("timeEnd"));
			int deadline = Convert.strToInt(request().getParameter("deadline"), -1);
			investor = URLDecoder.decode(investor, "UTF-8");// 中文乱码转换
			timeStart = URLDecoder.decode(timeStart, "UTF-8");// 中文乱码转换
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");// 中文乱码转换
			// 中文乱码转换
			investor = URLDecoder.decode(investor, "UTF-8");
			// 待还款详情
			afterCreditManageService.queryForPaymentTotalByCondition(investor, timeStart, timeEnd, deadline, group, pageBean, inverse);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			if (list == null) {
				list = new ArrayList<Map<String, Object>>();
			}
			for (Map<String, Object> map : list) {
				if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}

				if (map.get("groupName") == null || map.get("groupName") == "") {
					map.put("groupName", "未分配");
				}
				if (Convert.strToLong(map.get("isDayThe") + "", -1L) == 1) {
					map.put("isDayThe", "否");
				} else {
					map.put("isDayThe", "是");
				}

			}
			// --------add by houli 添加 "用户组"
			HSSFWorkbook wb = ExcelUtils.exportExcel("待收款详情", pageBean.getPage(), new String[] { "投资人", "姓名", "用户组", "投资金额", "投资占比", "借款时间", "标旳总金额 ", "标旳类型 ", "是否天标", "期数/总期数",
					"应收时间", "应收金额 " }, new String[] { "investor", "realName", "groupName", "investAmount", "scale", "publishTime", "borrowAmount", "borrowWay", "isDayThe",
					"repayPeriod", "repayDate", "forTotalSum" });
			this.export(wb, new Date().getTime() + ".xls");
			// 添加操作日志
			operationLogService.addOperationLog("v_t_forpayment_h", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出待收款总计列表", 2);
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
	 * @MethodName: lateRepayList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:02:43
	 * @Return:
	 * @Descb: 逾期还款记录
	 * @Throws:
	 */
	public String lateRepayList() throws SQLException, DataException {
		String pageNum = (String) (request().getParameter("curPage") == null ? "" : request().getParameter("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
		String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay"));
		String status = SqlInfusion.FilteSqlInfusion(paramMap.get("status") == null ? "" : paramMap.get("status"));
		int statusInt = Convert.strToInt(status, -1);
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		afterCreditManageService.queryLateRepayByCondition(userName, borrowWayInt, statusInt, pageBean);
		/*
		 * 逾期的借款统计总额 Map<String, String> lateRepayMap =
		 * afterCreditManageService.queryLateRepayAmount(userName, borrowWayInt,
		 * statusInt); request().setAttribute("lateRepayMap", lateRepayMap); //
		 * 得到当前页逾期借款金额 double lateAmount = 0; double lateFI = 0; double totalSum
		 * = 0; List<Map<String, Object>> lateList = pageBean.getPage(); if
		 * (lateList != null) { for (Map<String, Object> map : lateList) {
		 * lateAmount = lateAmount + Convert.strToDouble(map.get("repaySum") +
		 * "", 0); lateFI = lateFI + Convert.strToDouble(map.get("lateFI") + "",
		 * 0); totalSum = totalSum + Convert.strToDouble(map.get("totalSum") +
		 * "", 0); } } request().setAttribute("lateAmount", lateAmount);
		 * request().setAttribute("lateFI", lateFI);
		 * request().setAttribute("totalSum", totalSum);
		 */
		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出逾期还款记录,或逾期垫付
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportlateRepay() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Integer status = Convert.strToInt(request().getParameter("status"), -1);
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			// 用户名
			String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("userName") == null ? "" : request().getParameter("userName"));
			// 中文乱码转换
			userName = URLDecoder.decode(userName, "UTF-8");
			// 借款类型
			int borrowWay = Convert.strToInt(SqlInfusion.FilteSqlInfusion(request().getParameter("borrowWay")), -1);
			if (status != -1) {
				// 逾期垫付
				afterCreditManageService.queryOverduePaymentByCondition(userName, borrowWay, status, pageBean);
			} else {
				// 逾期的还款
				afterCreditManageService.queryLateRepayByCondition(userName, borrowWay, status, pageBean);
			}
			// 待还款详情
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			if (list == null) {
				list = new ArrayList<Map<String, Object>>();
			}
			for (Map<String, Object> map : list) {
				if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}

				if (map.get("isWebRepay").toString().equals("1")) {
					map.put("isWebRepay", "否");
				} else if (map.get("isWebRepay").toString().equals("2")) {
					map.put("isWebRepay", "是");
				}

				if (map.get("repayStatus").toString().equals("1")) {
					map.put("repayStatus", "未偿还");
				} else if (map.get("repayStatus").toString().equals("2")) {
					map.put("repayStatus", "已偿还");
				} else if (map.get("repayStatus").toString().equals("3")) {
					map.put("repayStatus", "偿还中");
				}

			}

			HSSFWorkbook wb = null;
			if (status == -1) {
				wb = ExcelUtils.exportExcel("逾期的还款", pageBean.getPage(), new String[] { "ID", "用户名", "真实姓名", "借款标题", "期数", "类型", "应还时间", "逾期天数", "应还金额(￥)", "逾期金额(￥)", "总还款(￥)",
						"网站待还", "还款状态" }, new String[] { "id", "username", "realName", "borrowTitle", "repayPeriod", "borrowWay", "repayDate", "lateDay", "totalSum", "lateFI",
						"repaySum", "isWebRepay", "repayStatus" });
				// 操作日志
				operationLogService.addOperationLog("v_t_overduepayment_h", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出逾期的还款列表", 2);
			} else {
				wb = ExcelUtils.exportExcel("逾期垫付", pageBean.getPage(), new String[] { "ID", "用户名", "真实姓名", "借款标题", "期数", "类型", "应还时间", "逾期天数", "应还金额(￥)", "逾期金额(￥)", "总还款(￥)",
						"网站待还", "还款状态" }, new String[] { "id", "username", "realName", "borrowTitle", "repayPeriod", "borrowWay", "repayDate", "lateDay", "totalSum", "lateFI",
						"repaySum", "isWebRepay", "repayStatus" });
				// 操作日志
				operationLogService.addOperationLog("v_t_laterepay_h", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出逾期垫付列表", 2);
			}

			this.export(wb, new Date().getTime() + ".xls");
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
	 * @MethodName: overduePaymentList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:40:57
	 * @Return:
	 * @Descb: 逾期垫付记录
	 * @Throws:
	 */
	public String overduePaymentList() throws Exception {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? "" : request().getParameter("curPage")));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
		String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay"));
		String status = SqlInfusion.FilteSqlInfusion(paramMap.get("status") == null ? "" : paramMap.get("status"));
		int statusInt = Convert.strToInt(status, -1);
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		afterCreditManageService.queryOverduePaymentByCondition(userName, borrowWayInt, statusInt, pageBean);
		/*
		 * // 逾期垫付的借款统计总额 Map<String, String> overduePaymentMap =
		 * afterCreditManageService.queryOverduePaymentAmount(userName,
		 * borrowWayInt, statusInt); request().setAttribute("overduePaymentMap",
		 * overduePaymentMap); // 得到当前页逾期还款总金额 double overAmount = 0; double
		 * lateFI = 0; double totalSum = 0; List<Map<String, Object>> overList =
		 * pageBean.getPage(); if (overList != null) { for (Map<String, Object>
		 * map : overList) { overAmount = overAmount +
		 * Convert.strToDouble(map.get("repaySum") + "", 0); lateFI = lateFI +
		 * Convert.strToDouble(map.get("lateFI") + "", 0); totalSum = totalSum +
		 * Convert.strToDouble(map.get("totalSum") + "", 0); } }
		 * request().setAttribute("overAmount", overAmount);
		 * request().setAttribute("lateFI", lateFI);
		 * request().setAttribute("totalSum", totalSum);
		 */
		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 代偿(合和年)
	 */
	public String overduePaymentRepaySubmit() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		String borrowId = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowId") == null ? "" : paramMap.get("borrowId"));
		long payId = Convert.strToLong(id, -1L);
		if (payId < -1) {
			JSONUtils.printStr2(IConstants.ACTOIN_ILLEGAL);
			return null;
		}
		if (StringUtils.isBlank(ChinaPnrConfig.chinapnr_dc)) {
			JSONUtils.printStr2("代偿账户未开启,不能代偿");
			return null;
		}

		String message  = afterCreditManageService.overduePaymentRepaySubmit(payId, admin.getId(), getBasePath(), borrowId, admin);
		if (message.equals("操作成功")) {
			// 添加操作日志
			operationLogService.addOperationLog("t_repayment", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "管理员进行逾期垫付操作", 2);
		}
		JSONUtils.printStr2(message);
		return null;
	}

	/**
	 * @Descb: 已还款查看详情
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String queryByrepayId() {
		int id = Convert.strToInt(request("repayId"), 0);
		try {
			afterCreditManageService.queryByrepayId(id, pageBean);
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
	 * @Descb: 已还款查看详情
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String queryByrepayIdDueId() {
		int id = Convert.strToInt(request("repayId"), 0);
		try {
			afterCreditManageService.queryByrepayIdDueId(id, pageBean);
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
	 * @MethodName: hasRepayList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午02:24:40
	 * @Return:
	 * @Descb: 已还款列表
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String hasRepayList() throws SQLException, DataException {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? "" : request().getParameter("curPage")));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName") == null ? "" : paramMap.get("realName"));

		String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay"));
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		String deadline = SqlInfusion.FilteSqlInfusion(paramMap.get("deadline") == null ? "" : paramMap.get("deadline"));
		int deadlineInt = Convert.strToInt(deadline, -1);
		String repayStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("status") == null ? "" : paramMap.get("status"));
		int repayStatusInt = Convert.strToInt(repayStatus, -1);
		String flag = SqlInfusion.FilteSqlInfusion(paramMap.get("flag"));
		String timeStart1 = null;
		String timeEnd1 = null;
		String timeStart = null;
		String timeEnd = null;
		if (flag.equals("2")) {
			// -----------add by houli
			timeStart1 = paramMap.get("timeStart") == null ? "" : paramMap.get("timeStart");
			timeEnd1 = paramMap.get("timeEnd") == null ? "" : paramMap.get("timeEnd");
			// -------
		} else {
			timeStart = paramMap.get("timeStart") == null ? "" : paramMap.get("timeStart");
			timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap.get("timeEnd");
		}
		afterCreditManageService.queryHasRepayByCondition(userName, realName, timeStart, timeEnd, borrowWayInt, deadlineInt, repayStatusInt, pageBean,
		// ---modify by houli
				timeStart1, timeEnd1);
		// 已收款总额
		Map<String, String> hasRePayMap = afterCreditManageService.queryHasRePayAmount(userName, realName, timeStart, timeEnd, borrowWayInt, deadlineInt, repayStatusInt,
				timeStart1, timeEnd1);
		request().setAttribute("hasRePayMap", hasRePayMap);

		// 得到当前页已收款金额
		double hasAmount = 0;
		List<Map<String, Object>> hasList = pageBean.getPage();
		if (hasList != null) {
			for (Map<String, Object> map : hasList) {
				hasAmount = hasAmount + Convert.strToDouble(map.get("hasPI") + "", 0);
			}
		}
		request().setAttribute("hasAmount", hasAmount);
		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出已还款列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exporthasRepay() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			// 得到页面传来的信息
			// 用户名
			String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("userName") == null ? "" : request().getParameter("userName"));
			// 真实姓名
			String realName = SqlInfusion.FilteSqlInfusion(request().getParameter("realName") == null ? "" : request().getParameter("realName"));
			// 还款日期
			String timeStart = SqlInfusion.FilteSqlInfusion(request().getParameter("timeStart") == null ? "" : request().getParameter("timeStart"));
			String timeEnd = SqlInfusion.FilteSqlInfusion(request().getParameter("timeEnd") == null ? "" : request().getParameter("timeEnd"));
			// 到期日期
			String timeStart1 = SqlInfusion.FilteSqlInfusion(request().getParameter("timeStart1") == null ? "" : request().getParameter("timeStart1"));
			String timeEnd1 = SqlInfusion.FilteSqlInfusion(request().getParameter("timeEnd1") == null ? "" : request().getParameter("timeEnd1"));
			// 借款期数
			int deadline = Convert.strToInt(request().getParameter("deadline"), -1);
			// 借款类型
			int borrowWay = Convert.strToInt(request().getParameter("borrowWay"), -1);
			// 中文乱码转换
			userName = URLDecoder.decode(userName, "UTF-8");
			realName = URLDecoder.decode(realName, "UTF-8");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			timeStart1 = URLDecoder.decode(timeStart1, "UTF-8");
			timeEnd1 = URLDecoder.decode(timeEnd1, "UTF-8");
			// 已还款记录列表
			afterCreditManageService.queryHasRepayByCondition(userName, realName, timeStart, timeEnd, borrowWay, deadline, -1, pageBean, timeStart1, timeEnd1);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			if (list == null) {
				list = new ArrayList<Map<String, Object>>();
			}
			for (Map<String, Object> map : list) {
				if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				} else if (map.get("borrowWay").toString().equals(IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}

				if (map.get("repayStatus").toString().equals("1")) {
					map.put("repayStatus", "未偿还");
				} else if (map.get("repayStatus").toString().equals("2")) {
					map.put("repayStatus", "已偿还");
				} else if (map.get("repayStatus").toString().equals("3")) {
					map.put("repayStatus", "偿还中");
				}
				if (map.get("groupName") == null || map.get("groupName") == "") {
					map.put("groupName", "未分配");
				}
				if (Convert.strToLong(map.get("isDayThe") + "", -1L) == 1) {
					map.put("isDayThe", "否");
				} else {
					map.put("isDayThe", "是");
				}
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("已还款详情", pageBean.getPage(), new String[] { "ID", "借款人", "姓名", "用户组 ", "借款时间", "标旳类型", "借款标题", "是否天标", "期数/总期数 ", "应还时间 ",
					"还款时间 ", "应还金额  ", "还款状态" }, new String[] { "id", "username", "realName", "groupName", "publishTime", "borrowWay", "borrowTitle", "isDayThe", "repayPeriod",
					"repayDate", "realRepayDate", "hasPI", "repayStatus" });
			this.export(wb, new Date().getTime() + ".xls");

			operationLogService.addOperationLog("v_t_hasrepay_h", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出已还款列表", 2);
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
	 * @Descb: 待收款查看详情
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String queryByrepayIdDieIn() {
		int id = Convert.strToInt(request("repayId"), 0);
		try {
			afterCreditManageService.queryByrepayId(id, pageBean);
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
	 * @MethodName: repaymentNoticeInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午07:24:07
	 * @Return:
	 * @Descb: 还款记录沟通初始化
	 * @Throws:
	 */
	public String repaymentNoticeInit() {
		String id = request().getParameter("id") == null ? "" : request().getParameter("id");
		request().setAttribute("id", id);
		return "success";
	}

	/**
	 * @MethodName: repayMentNoticeList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午02:43:32
	 * @Return:
	 * @Descb: 还款沟通记录
	 * @Throws:
	 */
	public String repayMentNoticeList() throws SQLException, DataException {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? "" : request().getParameter("curPage")));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToInt(id, -1);
		afterCreditManageService.queryRepayMentNoticeByCondition(idLong, pageBean);
		return "success";
	}

	/**
	 * @MethodName: addRepayMentNotice
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午07:02:45
	 * @Return:
	 * @Descb: 添加还款沟通记录
	 * @Throws:
	 */
	public String addRepayMentNotice() throws SQLException, IOException {
		JSONObject obj = new JSONObject();
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		String content = SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		long result = -1;
		result = afterCreditManageService.addRepayMentNotice(idLong, content);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_repayment_service", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加还款沟通记录失败", 2);
			return null;
		}
		// 前台跳转地址
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		operationLogService.addOperationLog("t_repayment_service", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加还款沟通记录成功", 2);
		return null;
	}

	/**
	 * @MethodName: repaymentDetail
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午02:22:17
	 * @Return:
	 * @Descb: 还款记录详情
	 * @Throws:
	 */
	public String repaymentDetail() throws SQLException, DataException {
		String frontUrl = ServletUtils.serverUrl();
		String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter("id"));
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> map = afterCreditManageService.queryRepaymentDetail(idLong);
		List<Map<String, Object>> serviceList = afterCreditManageService.queryRepaymentService(idLong);
		List<Map<String, Object>> collectionList = afterCreditManageService.queryRepaymentCollection(idLong);
		request().setAttribute("map", map);
		request().setAttribute("serviceList", serviceList);
		request().setAttribute("collectionList", collectionList);
		request().setAttribute("frontUrl", frontUrl);
		return "success";
	}

	public String addCollection() throws SQLException, IOException {
		JSONObject obj = new JSONObject();
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1L);
		String colResult = paramMap.get("colResult") == null ? "" : paramMap.get("colResult");

		String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark") == null ? "" : paramMap.get("remark"));
		long returnId = -1L;
		if (StringUtils.isBlank(colResult.trim())) {
			obj.put("msg", "催收结果不能为空!");
			JSONUtils.printObject(obj);
			return null;
		}
		if (StringUtils.isBlank(remark.trim())) {
			obj.put("msg", "署名备注不能为空!");
			JSONUtils.printObject(obj);
			return null;
		}
		returnId = afterCreditManageService.addCollection(idLong, colResult, remark);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_collection", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加催收记录失败", 2);
			return null;
		} else {
			obj.put("msg", IConstants.ACTION_SUCCESS);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_collection", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加催收记录成功", 2);
			return null;
		}
	}

	/**
	 * @MethodName: delCollection
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午04:37:26
	 * @Return:
	 * @Descb: 删除催收记录
	 * @Throws:
	 */
	public String delCollection() throws IOException, SQLException {
		JSONObject obj = new JSONObject();
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1L);
		long returnId = -1L;
		returnId = afterCreditManageService.delCollection(idLong);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_collection", admin.getUserName(), IConstants.DELETE, admin.getLastIP(), 0, "删除催收记录失败", 2);
			return null;
		} else {
			obj.put("msg", IConstants.ACTION_SUCCESS + ",请刷新");
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_collection", admin.getUserName(), IConstants.DELETE, admin.getLastIP(), 0, "删除催收记录成功", 2);
			return null;
		}
	}

	public AfterCreditManageService getAfterCreditManageService() {
		return afterCreditManageService;
	}

	public void setAfterCreditManageService(AfterCreditManageService afterCreditManageService) {
		this.afterCreditManageService = afterCreditManageService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws SQLException, DataException {
		if (borrowDeadlineList == null) {
			borrowDeadlineList = selectedService.borrowDeadline();
		}
		return borrowDeadlineList;
	}

	public List<Map<String, Object>> getUserGroupList() throws SQLException, DataException {
		if (userGroupList == null) {
			userGroupList = selectedService.userGroup();
		}
		return userGroupList;
	}

	/**
	 * 合和年回购 列表
	 */
	public String backBuyInitList() throws Exception {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request("curPage") == null ? "" : request("curPage")));
		if (StringUtils.isNotBlank(pageNum))
			pageBean.setPageNum(pageNum);

		String userName = paramMap.get("userName") == null ? "" : paramMap.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		int status = 1;//1待回购

		afterCreditManageService.queryBackBuyList(userName, borrowWayInt,status, pageBean);

		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	public String backBuyDetails() throws Exception {
		long borrowId = Convert.strToLong(request("borrowId"), -1);

		List<Map<String, Object>> list = afterCreditManageService.queryBackBuyDetails(borrowId);
		request().setAttribute("list", list);
		return SUCCESS;
	}

	/** 根据investId 回购 更改使用债权转让接口 */
	public String backBuyByborrowId() throws Exception {
		long investId = Convert.strToLong(request("investId"), -1);
		double price = Convert.strToDouble(request("price"), 0);//转让的债权金额
		double dealPrice = Convert.strToDouble(request("dealPrice"), 0);//债权交易价格
		if (investId < 0) {
			JSONUtils.printStr2("提交数据错误!");
			return null;
		}
		if (dealPrice <= 0) {
			JSONUtils.printStr2("回购金额错误!");
			return null;
		}
		if (price <= 0) {
			JSONUtils.printStr2("债权金额错误!");
			return null;
		}
		if (StringUtils.isBlank(ChinaPnrConfig.chinapnr_dc)) {
			JSONUtils.printStr2("代偿账户未设置!");
			return null;
		}
		DecimalFormat format = new DecimalFormat("0.00");
		String html = afterCreditManageService.backBuyByborrowId(investId, format.format(price), format.format(dealPrice));
		sendHtml(html);
		return null;
	}

	/** 导出待回购列表 */
	@SuppressWarnings("unchecked")
	public String exportBackBuyList() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			// 用户名
			String userName = SqlInfusion.FilteSqlInfusion(request("userName") == null ? "" : request("userName"));
			userName = URLDecoder.decode(userName, "UTF-8");
			int borrowWay = Convert.strToInt(request().getParameter("borrowWay"), -1);
			int status = 1;
			
			afterCreditManageService.queryBackBuyList(userName, borrowWay, status,pageBean);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			if (list == null) {
				list = new ArrayList<Map<String, Object>>();
			}
			for (Map<String, Object> map : list) {
				if (map.get("borrowWay").toString().equals("1")) {
					map.put("borrowWay", "薪金贷");
				} else if (map.get("borrowWay").toString().equals("2")) {
					map.put("borrowWay", "生意贷");
				} else if (map.get("borrowWay").toString().equals("3")) {
					map.put("borrowWay", "业主贷");
				}

			}

			HSSFWorkbook wb = null;
			wb = ExcelUtils.exportExcel("待回购", pageBean.getPage(), new String[] { "ID", "用户名", "真实姓名", "借款标题", "借款金额", "借款类型", "借款期限", "借款时间", "逾期天数", "已还总额", "代偿次数" },
					new String[] { "borrowId", "username", "realName", "borrowTitle", "borrowAmount", "borrowWay", "deadline", "publishTime", "overDay", "hasPay", "webPayCount" });

			operationLogService.addOperationLog("v_t_back_buy_list", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出待回购列表", 2);

			this.export(wb, new Date().getTime() + ".xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 添加回购初始化 **/
	public String addBackBuyInit() {
		return SUCCESS;
	}

	public String addBackBuyList() throws Exception {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request("curPage") == null ? "" : request("curPage")));
		if (StringUtils.isNotBlank(pageNum))
			pageBean.setPageNum(pageNum);

		String userName = paramMap.get("userName") == null ? "" : paramMap.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);

		afterCreditManageService.addBackBuyList(userName, borrowWayInt, pageBean);
		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/** 发起回购 **/
	public String addBackBuy() throws Exception {
		long borrowId = Convert.strToLong(request("borrowId"), 0);
		String ret = afterCreditManageService.addBackBuy(borrowId);
		JSONUtils.printStr2(ret);
		return null;
	}
	
	public String hasBackBuyInit(){
		return SUCCESS;
	}
	
	/**  合和年已回购 列表 */
	public String hasBackBuyList() throws Exception {
		String pageNum = SqlInfusion.FilteSqlInfusion((String) (request("curPage") == null ? "" : request("curPage")));
		if (StringUtils.isNotBlank(pageNum))
			pageBean.setPageNum(pageNum);

		String userName = paramMap.get("userName") == null ? "" : paramMap.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		int status = 2;//2已回购

		afterCreditManageService.queryBackBuyList(userName, borrowWayInt,status, pageBean);

		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

}