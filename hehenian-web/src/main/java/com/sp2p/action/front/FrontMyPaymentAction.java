package com.sp2p.action.front;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.config.ChinaPnrConfig;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.util.ExcelUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.FrontMyPaymentService;
import com.sp2p.service.UserService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

@SuppressWarnings("serial")
public class FrontMyPaymentAction extends BaseFrontAction {

	private FrontMyPaymentService frontpayService;

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public FrontMyPaymentService getFrontpayService() {
		return frontpayService;
	}

	public void setFrontpayService(FrontMyPaymentService frontpayService) {
		this.frontpayService = frontpayService;
	}

	/**
	 * 成功的借款
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryMySuccessBorrowList() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startTime")), null);
		String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endTime")), null);
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("title")), null);

		int borrowStatus = request("borrowStatus") == null ? -1 : Convert.strToInt(request("borrowStatus"), -1);
		endTime = changeEndTime(endTime);

		if (borrowStatus == -1) {
			frontpayService.queryMySuccessBorrowList(pageBean, userId, startTime, endTime, title, -1);
		} else {// 还款中的借款 已还完的借款
			frontpayService.queryMySuccessBorrowList(pageBean, userId, startTime, endTime, title, borrowStatus);
		}

		List<Map<String, Object>> lists = pageBean.getPage();

		if (lists != null) {
			for (Map<String, Object> map : lists) {
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

				if (map.get("borrowStatus").toString().equals(IConstants.BORROW_STATUS_4 + "")) {
					map.put("borrowStatus", IConstants.BORROW_STATUS_4_STR);
				} else if (map.get("borrowStatus").toString().equals(IConstants.BORROW_STATUS_5 + "")) {
					map.put("borrowStatus", IConstants.BORROW_STATUS_5_STR);
				}
			}
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 导出成功借款,正在还款的借款，已还借款 的数据excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportSuccessBorrow() {

		Long userId = this.getUserId();// 获得用户编号
		Integer status = Convert.strToInt(request("status"), -1);
		pageBean.pageNum = 1;
		pageBean.setPageSize(5000);
		try {
			String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("startTime")), null);
			String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("endTime")), null);
			endTime = changeEndTime(endTime);
			String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("title")), null);
			// 中文乱码转换
			if (StringUtils.isNotBlank(title)) {
				title = URLDecoder.decode(title, "UTF-8");
			}
			// 成功借款
			frontpayService.queryMySuccessBorrowList(pageBean, userId, startTime, endTime, title, status);
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
					}

					if (map.get("borrowStatus").toString().equals(IConstants.BORROW_STATUS_4 + "")) {
						map.put("borrowStatus", IConstants.BORROW_STATUS_4_STR);
					} else if (map.get("borrowStatus").toString().equals(IConstants.BORROW_STATUS_5 + "")) {
						map.put("borrowStatus", IConstants.BORROW_STATUS_5_STR);
					}
				}
			}
			HSSFWorkbook wb = null;
			if (status == -1) {
				wb = ExcelUtils.exportExcel("成功借款", pageBean.getPage(),
						new String[] { "标题", "借款类型", "借款金额(￥)", "年利率(%)", "还款期限(月)", "借款时间", "偿还本息(￥)", "已还本息(￥)", "未还本息(￥)", "状态" }, new String[] { "borrowTitle", "borrowWay",
								"borrowAmount", "annualRate", "deadline", "publishTime", "stillTotalSum", "hasPI", "hasSum", "borrowStatus" });
			} else if (status == 4) {
				wb = ExcelUtils.exportExcel("正在还款的借款", pageBean.getPage(), new String[] { "标题", "借款类型", "借款金额(￥)", "年利率(%)", "还款期限(月)", "借款时间", "偿还本息(￥)", "已还本息(￥)", "未还本息(￥)" },
						new String[] { "borrowTitle", "borrowWay", "borrowAmount", "annualRate", "deadline", "publishTime", "stillTotalSum", "hasPI", "hasSum" });
			} else if (status == 5) {

				wb = ExcelUtils.exportExcel("已还完的借款", pageBean.getPage(), new String[] { "标题", "借款类型", "借款金额(￥)", "年利率(%)", "还款期限(月)", "借款时间", "偿还本息(￥)", "已还本息(￥)", "已还逾期罚息(￥)" },
						new String[] { "borrowTitle", "borrowWay", "borrowAmount", "annualRate", "deadline", "publishTime", "stillTotalSum", "stillTotalSum", "hasFI" });
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
	 * 正在还款的借款
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryMyPayingBorrowList() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startTime")), null);
		String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endTime")), null);
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("title")), null);
		/*
		 * int borrowStatus = paramMap.get("borrowStatus") == null ? -1 :
		 * Convert .strToInt(paramMap.get("borrowStatus"), -1);
		 */

		int borrowStatus = IConstants.BORROW_STATUS_4;
		endTime = changeEndTime(endTime);

		if (borrowStatus == -1) {
			frontpayService.queryMySuccessBorrowList(pageBean, userId, startTime, endTime, title, -1);
		} else {// 还款中的借款 已还完的借款
			frontpayService.queryMySuccessBorrowList(pageBean, userId, startTime, endTime, title, borrowStatus);
		}

		List<Map<String, Object>> lists = pageBean.getPage();

		if (lists != null) {
			for (Map<String, Object> map : lists) {
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

			}
		}

		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 已还完借款
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryMyPayoffBorrowList() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		String startTime = SqlInfusion.FilteSqlInfusion(request("startTime"));
		String endTime = SqlInfusion.FilteSqlInfusion(request("endTime"));
		String title = SqlInfusion.FilteSqlInfusion(request("title"));
		int borrowStatus = IConstants.BORROW_STATUS_5;
		endTime = changeEndTime(endTime);

		frontpayService.queryMySuccessBorrowList(pageBean, userId, startTime, endTime, title, borrowStatus);

		List<Map<String, Object>> lists = pageBean.getPage();

		if (lists != null) {
			for (Map<String, Object> map : lists) {
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

				map.put("borrowStatus", IConstants.BORROW_STATUS_5_STR);

			}
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 正在还款的借款详情
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryPayingDetails() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		Long borrowId = paramMap.get("borrowId") == null ? -1 : Convert.strToLong(paramMap.get("borrowId"), -1);//
		int status = -1;
		if (paramMap.get("status") != null) {
			status = Convert.strToInt(paramMap.get("status"), -1);
		}
		// 获得统计信息
		Map<String, String> map = null;

		pageBean.setPageSize(IConstants.PAGE_SIZE_6);

		if (borrowId == -1) {
			return null;
		}
		frontpayService.queryPayingDetails(pageBean, borrowId, status);
		map = frontpayService.queryOneBorrowInfo(userId, borrowId);

		List<Map<String, Object>> lists = pageBean.getPage();
		if (lists != null) {
			for (Map<String, Object> mp : lists) {
				if (Convert.strToInt(mp.get("repayStatus").toString(), -1) == IConstants.PAYING_STATUS_NON) {
					mp.put("repayStatus", IConstants.PAYING_STATUS_NON_STR);
				} else if (Convert.strToInt(mp.get("repayStatus").toString(), -1) == IConstants.PAYING_STATUS_PAYING) {
					mp.put("repayStatus", IConstants.PAYING_STATUS_PAYING_STR);
				} else if (Convert.strToInt(mp.get("repayStatus").toString(), -1) == IConstants.PAYING_STATUS_SUCCESS) {// 已偿还完
					mp.put("repayStatus", IConstants.PAYING_STATUS_SUCCESS_STR);
				}
			}
		}

		// map 首次加载时，为Null
		if (map != null) {
			request().setAttribute("borrowTitle", map.get("borrowTitle"));
			request().setAttribute("borrowAmount", map.get("borrowAmount"));
			request().setAttribute("annualRate", map.get("annualRate"));
			request().setAttribute("deadline", map.get("deadline"));
			request().setAttribute("isDayThe", map.get("isDayThe"));
			if (Convert.strToInt(map.get("paymentMode").toString(), -1) == IConstants.PAY_WAY_MONTH) {
				request().setAttribute("paymentMode", IConstants.PAY_WAY_MONTH_STR);
			} else {
				request().setAttribute("paymentMode", IConstants.PAY_WAY_RATE_STR);
			}
			request().setAttribute("publishTime", map.get("publishTime"));
		}
		return SUCCESS;
	}

	/**
	 * 还款明细账
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryAllDetails() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startTime")), null);
		String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endTime")), null);
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("title")), null);
		endTime = changeEndTime(endTime);

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);

		frontpayService.queryAllDetails(pageBean, userId, startTime, endTime, title);
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 还款明细账，的数据导出excel文件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportrepayment() {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号

		pageBean.pageNum = 1;
		pageBean.setPageSize(5000);
		try {
			// 还款明细账
			String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("startTime")), null);
			String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("endTime")), null);
			endTime = changeEndTime(endTime);
			String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("title")), null);
			// 中文乱码转换
			if (StringUtils.isNotBlank(title)) {
				title = URLDecoder.decode(title, "UTF-8");
			}
			frontpayService.queryAllDetails(pageBean, userId, startTime, endTime, title);
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
			if (list != null) {
				for (Map<String, Object> map : list) {

					if (map.get("repayStatus").toString().equals(1 + "")) {
						map.put("repayStatus", "未偿还");
					} else {
						map.put("repayStatus", "已偿还");
					}
				}
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("还款明细账", pageBean.getPage(),
					new String[] { "标题", "第几期", "应还款日期", "实际还款日期", "本期应还本息(￥)", "利息(￥)", "逾期罚款(￥)", "逾期天数(天)", "还款状态" }, new String[] { "borrowTitle", "repayPeriod", "repayDate",
							"realRepayDate", "forPI", "stillInterest", "lateFI", "lateDay", "repayStatus" });

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
	 * 借款明细账
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryBorrowInvestorInfo() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		String investor = SqlInfusion.FilteSqlInfusion(request("investor"));

		frontpayService.queryBorrowInvestorInfo(pageBean, userId, investor);
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 还款数据显示
	 */
	public String queryMyPayData() throws DataException, SQLException, ParseException {
		long payId = request("payId") == null ? -1 : Convert.strToLong(request("payId"), -1);
		Map<String, String> payMap = frontpayService.queryMyPayData(payId);
		/*
		 * SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); String
		 * consultFee =
		 * "--".equals(SqlInfusion.FilteSqlInfusion(request("consultFee"))) ?
		 * "0" : SqlInfusion.FilteSqlInfusion(request( "consultFee")); // 咨询费
		 * String stillInterest =
		 * "--".equals(SqlInfusion.FilteSqlInfusion(request("stillInterest"))) ?
		 * "0" : SqlInfusion.FilteSqlInfusion(request( "stillInterest")); //
		 * 应还利息 //获取当前月的天数 Calendar a = Calendar.getInstance();
		 * a.set(Calendar.DATE, 1);// 把日期设置为当月第一天 a.roll(Calendar.DATE, -1);//
		 * 日期回滚一天，也就是最后一天 int maxDate = a.get(Calendar.DATE); Date repayDate =
		 * sf.parse(SqlInfusion.FilteSqlInfusion(request("repayDate"))); Date
		 * times = new Date(); //计算当前时间是否等于还款时间：date = 0,则时间相等 int date = (int)
		 * DateUtil.diffDays(times, repayDate); if (3 <= date) {// 提前还款 // 剩余本金
		 * double principalBalance =
		 * Double.valueOf(payMap.get("principalBalance")); // 提前还款金额 =
		 * 剩余本金×（1+3%）+应还利息*提前还款天数/30+本期咨询费 double nowTotaSum = principalBalance
		 * * (1 + 0.03) + Double.valueOf(stillInterest) * (date + 1) / maxDate +
		 * Double.valueOf(consultFee) +
		 * Double.valueOf(payMap.get("repayFee"));// 管理费,第一期收取,其他时候为0 // 本期待还本息
		 * = 剩余本金 + 本期应还利息 double forPi = principalBalance +
		 * Double.valueOf(stillInterest); double repayFee =
		 * Double.valueOf(payMap.get("repayFee")); double transAmt = nowTotaSum
		 * - repayFee; // 取小数点后面长度 String str = nowTotaSum + "000";
		 * payMap.put("needSum", str.substring(0, str.indexOf('.') + 3)); str =
		 * forPi + "000"; payMap.put("forPI", str.substring(0, str.indexOf('.')
		 * + 3)); str = repayFee + "0000"; payMap.put("repayFee",
		 * str.substring(0, str.indexOf('.') + 3)); str = transAmt + "0000";
		 * payMap.put("transAmt", str.substring(0, str.indexOf('.') + 3));
		 * request().setAttribute("flag", "9527"); }
		 */
		request().setAttribute("payMap", payMap);
		return SUCCESS;
	}

	/**
	 * 提交还款记录
	 */
	public String submitPay() throws Exception {
        AccountUserDo user = this.getUser();
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("invest_checkCode");
		String _code = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("code")), "");
		long id = Convert.strToLong(paramMap.get("id"), -1L);
		if (id < 0) {
			obj.put("msg", "还款信息未找到");
			JSONUtils.printObject(obj);
			return null;
		}
		String pwd = Convert.strToStr(paramMap.get("pwd"), "");
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// // 得到还款时间
		// Date repayDate = null;
		// try {
		// repayDate =
		// sf.parse(SqlInfusion.FilteSqlInfusion(paramMap.get("repayDate")));
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		double needSum = Convert.strToDouble(paramMap.get("needSum"), 0); // 需还总额
		if (needSum <= 0) {
			obj.put("msg", "还款金额错误");
			JSONUtils.printObject(obj);
			return null;
		}
		if (StringUtils.isBlank(pwd.trim())) {
			obj.put("msg", "密码不能为空");
			JSONUtils.printObject(obj);
			return null;
		}
		if (!code.equals(_code)) {
			obj.put("msg", "验证码错误");
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String, String> investPWDMap = null;
		try {
			investPWDMap = frontpayService.getDealPWD(user.getId(), pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (investPWDMap == null || investPWDMap.size() == 0) {
			obj.put("msg", "密码错误");
			JSONUtils.printObject(obj); // 密码错误呀
			return null;
		}

		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.queryBalanceBg(this.getUser().getUsrCustId() + ""));
		String AvlBalString = json.getString("AvlBal");// 用户在汇付可用余额
		AvlBalString = AvlBalString.replaceAll(",", "");
		double AvlBal = Convert.strToDouble(AvlBalString + "", 0);
		if (AvlBal < needSum) {
			obj.put("msg", "余额不足,请充值");
			JSONUtils.printObject(obj); // 余额不足
			return null;
		}

		try {
			doNormalRepay(id, user, pwd, needSum);
			// // 提前还款
			// if (Convert.strToLong(paramMap.get("flag"), 0) > 0) {
			// doPreRepay(id, user, pwd, repayDate, needSum);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("msg", "系统异常");
			JSONUtils.printObject(obj);
		}
		return null;
	}

	/**
	 * 普通还款
	 */
	private void doNormalRepay(long id, AccountUserDo user, String pwd, double needSum) throws Exception {
		String Fee = "0.00";
		String divDetails = "";
		String transAmt = "";
		String inAcctId = "";
		String outAcctId = "";
		String outCustId = user.getUsrCustId() + "";

		List<Map<String, Object>> list = frontpayService.queryAllInvestInfo(id);//查询所有投资人的投资信息
		JSONObject obj = new JSONObject();
		double consultFee = Convert.strToDouble(list.get(0).get("consultFee") + "", 0);// 咨询费,查询时已将本期还款咨询费统计在第一个记录中
		double fee = Convert.strToDouble(list.get(0).get("repayFee") + "", 0);// 手续费,同咨询费
		
		for (int i = 0, size = list.size(); i < size; i++) {
			Map<String, Object> map = list.get(i);
			if (i == size - 1 && (fee + consultFee) > 0) { // 在最后一次还款时加上手续费和咨询费
				String feeStr = new DecimalFormat("0.00").format((fee + consultFee));
				Fee = feeStr;
				JSONObject json1 = new JSONObject();
				json1.put("DivAcctId", ChinaPnrConfig.chinapnr_zxf);
				json1.put("DivAmt", new DecimalFormat("0.00").format(consultFee));
				divDetails = "[" + json1.toString();
				if (fee > 0) {
					feeStr = fee + "000";
					json1 = new JSONObject();
					json1.put("DivAcctId", ChinaPnrConfig.chinapnr_cfb);
					json1.put("DivAmt", new DecimalFormat("0.00").format(fee));
					divDetails += "," + json1.toString();
				}
				divDetails += "]";
			}

			String ordId = map.get("ordId") + "";
			String inCustId = map.get("inCustId") + "";
			String subOrdId = map.get("subOrdId") + "";
			String subOrdDate = DateUtil.dateToYMD(DateUtil.strToDate(map.get("subOrdDate") + ""));
			transAmt = map.get("inSum") + "";
			if (Convert.strToInt(map.get("isWebRepay") + "", -1) == 2) {// 如果已代偿,收款人为担保公司,inCustId为商户子账户
				inCustId = ChinaPnrConfig.chinapnr_dc;
				transAmt = map.get("compensatorySum") + "";
			}
			JSONObject.fromObject(ChinaPnRInterface.repayment("10",ordId, outCustId, outAcctId, transAmt, inCustId, inAcctId, subOrdId, subOrdDate, Fee, divDetails));
		}
		Map<String, String> map = frontpayService.submitPay(id, user.getId(), pwd, getBasePath(), user.getUsername(), needSum, fee, consultFee);
		int ret = Convert.strToInt(map.get("ret"), -1);
		if (ret > 0) {
			obj.put("msg", "ok");
		} else {
			obj.put("msg", Convert.strToStr(map.get("ret_desc"), "还款失败"));
		}
		JSONUtils.printObject(obj);
	}

	/**
	 * 提前还款 发送汇付请求 如果入账账户是担保账户请指定 InAcctId，否则可空。
	 */
	protected void doPreRepay(long id, AccountUserDo user, String pwd, Date repayDate, double needSum) throws Exception {
		List<Map<String, Object>> list = frontpayService.queryPreInvestInfo(id);
		String Fee = "0.00";
		String divDetails = "";
		String inAcctId = "";
		String outAcctId = "";
		String outCustId = user.getUsrCustId() + "";

		JSONObject obj = new JSONObject();
		double fee = 0;// 手续费
		double repaySum = 0;// 还款总额
		Calendar a = Calendar.getInstance();
		long day = a.get(Calendar.DAY_OF_MONTH);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int days = a.get(Calendar.DATE);// 当前月的天数
		double rate = (1.0 * day) / days;
		double consultFee = 0;
		for (int i = 0, size = list.size(); i < size; i++) {
			Map<String, Object> map = list.get(i);
			String transAmt = (Convert.strToDouble(map.get("principal") + "", 0) + Convert.strToDouble(map.get("interest") + "", 0) * rate) + "000";
			transAmt = transAmt.substring(0, transAmt.indexOf(".") + 3);
			String ordId = map.get("ordId") + "";
			String inCustId = map.get("inCustId") + "";
			String subOrdId = map.get("subOrdId") + "";
			String subOrdDate = DateUtil.dateToYMD(DateUtil.strToDate(map.get("subOrdDate") + ""));

			repaySum += Convert.strToDouble(transAmt, 0);// 累加还款金额
			fee += Convert.strToDouble(map.get("repayFee") + "", 0);// 累计手续费
			if (i == size - 1) {// 在最后一次还款时计算手续费和咨询费
				consultFee = needSum - repaySum - fee;// 咨询费=还款总额-已还金额-手续费
				double tempfee = fee + consultFee;
				String feeStr = tempfee + "000";
				Fee = feeStr.substring(0, feeStr.indexOf(".") + 3);
				feeStr = consultFee + "000";
				JSONObject json1 = new JSONObject();
				json1.put("DivAcctId", ChinaPnrConfig.chinapnr_zxf);
				json1.put("DivAmt", feeStr.substring(0, feeStr.indexOf(".") + 3));
				divDetails = "[" + json1.toString();
				if (fee > 0) {
					feeStr = fee + "000";
					json1 = new JSONObject();
					json1.put("DivAcctId", ChinaPnrConfig.chinapnr_cfb);
					json1.put("DivAmt", feeStr.substring(0, feeStr.indexOf(".") + 3));
					divDetails += "," + json1.toString();
				}
				divDetails += "]";
			}
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.repayment("10",ordId, outCustId, outAcctId, transAmt, inCustId, inAcctId, subOrdId, subOrdDate, Fee, divDetails));
			if (json.getInt("RespCode") != 0) {
				obj.put("msg", "失败:" + json.getString("RespDesc"));
				break;
			}
		}
		if (!obj.containsKey("msg")) {
			Map<String, String> map = frontpayService.submitPay(id, user.getId(), pwd, getBasePath(), user.getUsername(), needSum, fee, consultFee);
			int ret = Convert.strToInt(map.get("ret"), -1);
			if (ret > 0) {
				obj.put("msg", "ok");
			} else {
				obj.put("msg", Convert.strToStr(map.get("ret_desc"), "还款失败"));
			}
		}
		JSONUtils.printObject(obj);
	}

	@SuppressWarnings("deprecation")
	public static String changeEndTime(String endTime) {
		if (endTime != null && !endTime.equals("")) {
			String[] strs = endTime.split("-");
			// 结束日期往后移一天,否则某天0点以后的数据都不呈现
			Date date = new Date();// 取时间
			long time = Date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2], -1), 0, 0, 0);
			date.setTime(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(date);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static Date changeStrToDate(String dateTime) {
		if (dateTime != null) {
			String[] strs = dateTime.split("-");
			int ind = strs[2].indexOf(" ");
			if (ind >= 0) {
				strs[2] = strs[2].substring(0, ind + 1);
			}
			Date date = new Date();// 取时间
			long time = Date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2].trim(), -1), 0, 0, 0);
			date.setTime(time);
			return date;
		}
		return null;
	}

	/**
	 * 合和年 正在还款的借款详情
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryPayingDetailsHHN() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();//
		Long borrowId = request("borrowId") == null ? -1 : Convert.strToLong(request("borrowId"), -1);//
		int status = -1;
		// 获得统计信息
		Map<String, String> map = null;

		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		if (borrowId == -1) {
			return null;
		}
		frontpayService.queryPayingDetails(pageBean, borrowId, status);
		map = frontpayService.queryOneBorrowInfo(userId, borrowId);

		List<Map<String, Object>> lists = pageBean.getPage();
		if (lists != null) {
			for (Map<String, Object> mp : lists) {
				if (Convert.strToInt(mp.get("repayStatus").toString(), -1) == IConstants.PAYING_STATUS_NON) {
					mp.put("repayStatus", IConstants.PAYING_STATUS_NON_STR);
				} else if (Convert.strToInt(mp.get("repayStatus").toString(), -1) == IConstants.PAYING_STATUS_PAYING) {
					mp.put("repayStatus", IConstants.PAYING_STATUS_PAYING_STR);
				} else if (Convert.strToInt(mp.get("repayStatus").toString(), -1) == IConstants.PAYING_STATUS_SUCCESS) {// 已偿还完
					mp.put("repayStatus", IConstants.PAYING_STATUS_SUCCESS_STR);
				}
			}
		}

		// map 首次加载时，为Null
		if (map != null) {
			request().setAttribute("borrowTitle", map.get("borrowTitle"));
			request().setAttribute("borrowAmount", map.get("borrowAmount"));
			request().setAttribute("annualRate", map.get("annualRate"));
			request().setAttribute("deadline", map.get("deadline"));
			request().setAttribute("isDayThe", map.get("isDayThe"));
			if (Convert.strToInt(map.get("paymentMode").toString(), -1) == IConstants.PAY_WAY_MONTH) {
				request().setAttribute("paymentMode", IConstants.PAY_WAY_MONTH_STR);
			} else {
				request().setAttribute("paymentMode", IConstants.PAY_WAY_RATE_STR);
			}
			request().setAttribute("publishTime", map.get("publishTime"));
		}
		return SUCCESS;
	}

}
