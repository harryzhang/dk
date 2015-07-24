package com.sp2p.action.app;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONObject;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.web.util.ExcelUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.FrontMyPaymentService;
import com.sp2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class MyPaymentAppAction extends BaseAppAction {
	public static Log log = LogFactory.getLog(MyPaymentAppAction.class);

	private FrontMyPaymentService frontpayService;
	private UserService userService;
    @Autowired
    private IUserService userService1;

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
	 * @throws IOException
	 */
	public String queryMySuccessBorrowList() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);// 获得用户编号
			pageBean.setPageNum(infoMap.get("curPage"));
			String startTime = Convert.strToStr(infoMap.get("startTime"), null);
			String endTime = Convert.strToStr(infoMap.get("endTime"), null);
			String title = Convert.strToStr(infoMap.get("title"), null);
			/*
			 * int borrowStatus = paramMap.get("borrowStatus") == null ? -1 :
			 * Convert .strToInt(paramMap.get("borrowStatus"), -1);
			 */

			int borrowStatus = infoMap.get("borrowStatus") == null ? -1 : Convert.strToInt(infoMap.get("borrowStatus"), -1);
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
					}

					if (map.get("borrowStatus").toString().equals(IConstants.BORROW_STATUS_4 + "")) {
						map.put("borrowStatus", IConstants.BORROW_STATUS_4_STR);
					} else if (map.get("borrowStatus").toString().equals(IConstants.BORROW_STATUS_5 + "")) {
						map.put("borrowStatus", IConstants.BORROW_STATUS_5_STR);
					}
				}
			}
			// this.setRequestToParamMap();
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出成功借款,正在还款的借款，已还借款 的数据excel
	 * 
	 * @return
	 */
	public String exportSuccessBorrow() {
		//
		// Long userId = this.getUserId();// 获得用户编号
		// Integer status=Convert.strToInt(request("status"), -1);
		// pageBean.pageNum = 1;
		// pageBean.setPageSize(5000);
		// try {
		// String
		// startTime=Convert.strToStr(request().getParameter("startTime"),null);
		// String
		// endTime=Convert.strToStr(request().getParameter("endTime"),null);
		// endTime = changeEndTime(endTime);
		// String title =Convert.strToStr(request().getParameter("title"),null);
		// //中文乱码转换
		// if(StringUtils.isNotBlank(title)){
		// title = URLDecoder.decode(title,"UTF-8");
		// }
		// //成功借款
		// frontpayService.queryMySuccessBorrowList(pageBean, userId,
		// startTime, endTime, title, status);
		// if(pageBean.getPage()==null)
		// {
		// getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
		// return null;
		// }
		// if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
		// {
		// getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
		// return null;
		// }
		// List<Map<String, Object>> list = pageBean.getPage();
		// if (list == null) {
		// list = new ArrayList<Map<String, Object>>();
		// }
		// if (list != null) {
		// for (Map<String, Object> map : list) {
		// if (map.get("borrowWay").toString().equals(
		// IConstants.BORROW_TYPE_NET_VALUE)) {
		// map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
		// } else if (map.get("borrowWay").toString().equals(
		// IConstants.BORROW_TYPE_SECONDS)) {
		// map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
		// } else if (map.get("borrowWay").toString().equals(
		// IConstants.BORROW_TYPE_GENERAL)) {
		// map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
		// } else if (map.get("borrowWay").toString().equals(
		// IConstants.BORROW_TYPE_FIELD_VISIT)) {
		// map
		// .put("borrowWay",
		// IConstants.BORROW_TYPE_FIELD_VISIT_STR);
		// } else if (map.get("borrowWay").toString().equals(
		// IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
		// map.put("borrowWay",
		// IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
		// }
		//
		// if (map.get("borrowStatus").toString().equals(
		// IConstants.BORROW_STATUS_4 + "")) {
		// map.put("borrowStatus", IConstants.BORROW_STATUS_4_STR);
		// } else if (map.get("borrowStatus").toString().equals(
		// IConstants.BORROW_STATUS_5 + "")) {
		// map.put("borrowStatus", IConstants.BORROW_STATUS_5_STR);
		// }
		// }
		// }
		// HSSFWorkbook wb=null;
		// if(status==-1){
		// wb = ExcelUtils.exportExcel("成功借款", pageBean.getPage(),
		// new String[] { "标题", "借款类型", "借款金额(￥)", "年利率(%)", "还款期限(月)", "借款时间",
		// "偿还本息(￥)","已还本息(￥)","未还本息(￥)",
		// "状态" }, new String[] { "borrowTitle", "borrowWay",
		// "borrowAmount", "annualRate", "deadline",
		// "publishTime", "stillTotalSum","hasPI","hasSum","borrowStatus" });
		// }else if(status==4){
		// wb = ExcelUtils.exportExcel("正在还款的借款", pageBean.getPage(),
		// new String[] { "标题", "借款类型", "借款金额(￥)", "年利率(%)", "还款期限(月)", "借款时间",
		// "偿还本息(￥)","已还本息(￥)","未还本息(￥)"
		// }, new String[] { "borrowTitle", "borrowWay",
		// "borrowAmount", "annualRate", "deadline",
		// "publishTime", "stillTotalSum","hasPI","hasSum" });
		// }else if(status==5){
		//
		// wb = ExcelUtils.exportExcel("已还完的借款", pageBean.getPage(),
		// new String[] { "标题", "借款类型", "借款金额(￥)", "年利率(%)", "还款期限(月)", "借款时间",
		// "偿还本息(￥)","已还本息(￥)","已还逾期罚息(￥)"
		// }, new String[] { "borrowTitle", "borrowWay",
		// "borrowAmount", "annualRate", "deadline",
		// "publishTime", "stillTotalSum","stillTotalSum","hasFI" });
		// }
		//
		// this.export(wb, new Date().getTime() + ".xls");
		//
		// } catch (SQLException e) {
		//
		// e.printStackTrace();
		// } catch (DataException e) {
		//
		// e.printStackTrace();
		// } catch (IOException e) {
		//
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return null;
	}

	/**
	 * 正在还款的借款
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String queryMyPayingBorrowList() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);// 获得用户编号
			pageBean.setPageNum(appInfoMap.get("curPage"));
			String startTime = Convert.strToStr(appInfoMap.get("startTime"), null);
			String endTime = Convert.strToStr(appInfoMap.get("endTime"), null);
			String title = Convert.strToStr(appInfoMap.get("title"), null);
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
					}
				}
			}

			// this.setRequestToParamMap();
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 已还完借款
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMyPayoffBorrowList() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);// 获得用户编号
			pageBean.setPageNum(appInfoMap.get("curPage"));
			String startTime = appInfoMap.get("startTime");
			String endTime = appInfoMap.get("endTime");
			String title = appInfoMap.get("title");
			/*
			 * int borrowStatus = paramMap.get("borrowStatus") == null ? -1 :
			 * Convert .strToInt(paramMap.get("borrowStatus"), -1);
			 */

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
					}

					map.put("borrowStatus", IConstants.BORROW_STATUS_5_STR);

				}
			}
			jsonMap.put("error", "-1");
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 正在还款的借款详情
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String queryPayingDetails() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);// 获得用户编号
			Long borrowId = appInfoMap.get("borrowId") == null ? -1 : Convert.strToLong(appInfoMap.get("borrowId"), -1);//

			int status = -1;
			if (appInfoMap.get("status") != null) {
				status = Convert.strToInt(appInfoMap.get("status"), -1);
			}
			// 获得统计信息
			Map<String, String> map = null;
			pageBean.setPageNum(appInfoMap.get("curPage"));
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
				// request().setAttribute("borrowTitle",
				// map.get("borrowTitle"));
				// request().setAttribute("borrowAmount",
				// map.get("borrowAmount"));
				// request().setAttribute("annualRate", map.get("annualRate"));
				// request().setAttribute("deadline", map.get("deadline"));
				// request().setAttribute("isDayThe", map.get("isDayThe"));
				jsonMap.put("borrowTitle", map.get("borrowTitle"));
				jsonMap.put("borrowAmount", map.get("borrowAmount"));
				jsonMap.put("annualRate", map.get("annualRate"));
				jsonMap.put("deadline", map.get("deadline"));
				jsonMap.put("isDayThe", map.get("isDayThe"));
				if (Convert.strToInt(map.get("paymentMode").toString(), -1) == IConstants.PAY_WAY_MONTH) {
					// request().setAttribute("paymentMode",
					// IConstants.PAY_WAY_MONTH_STR);
					jsonMap.put("paymentMode", IConstants.PAY_WAY_MONTH_STR);
				} else {
					// request().setAttribute("paymentMode",
					// IConstants.PAY_WAY_RATE_STR);
					jsonMap.put("paymentMode", IConstants.PAY_WAY_RATE_STR);
				}
				// request().setAttribute("publishTime",
				// map.get("publishTime"));
				jsonMap.put("publishTime", map.get("publishTime"));
			}
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 还款明细账
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String queryAllDetails() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);// 获得用户编号
			pageBean.setPageNum(appInfoMap.get("curPage"));
			String startTime = Convert.strToStr(appInfoMap.get("startTime"), null);
			String endTime = Convert.strToStr(appInfoMap.get("endTime"), null);
			String title = Convert.strToStr(appInfoMap.get("title"), null);
			endTime = changeEndTime(endTime);

			pageBean.setPageSize(IConstants.PAGE_SIZE_10);

			frontpayService.queryAllDetails(pageBean, userId, startTime, endTime, title);
			// this.setRequestToParamMap();
			jsonMap.put("error", "-1");
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 还款明细账，的数据导出excel文件
	 * 
	 * @return
	 */
	public String exportrepayment() {
		// AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		// Long userId = user.getId();// 获得用户编号
		//
		// pageBean.pageNum = 1;
		// pageBean.setPageSize(5000);
		// try {
		// //还款明细账
		// String
		// startTime=Convert.strToStr(request().getParameter("startTime"),null);
		// String
		// endTime=Convert.strToStr(request().getParameter("endTime"),null);
		// endTime = changeEndTime(endTime);
		// String title =Convert.strToStr(request().getParameter("title"),null);
		// //中文乱码转换
		// if(StringUtils.isNotBlank(title)){
		// title = URLDecoder.decode(title,"UTF-8");
		// }
		// frontpayService.queryAllDetails(pageBean, userId, startTime, endTime,
		// title);
		// if(pageBean.getPage()==null)
		// {
		// getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
		// return null;
		// }
		// if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
		// {
		// getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
		// return null;
		// }
		// List<Map<String, Object>> list = pageBean.getPage();
		// if (list == null) {
		// list = new ArrayList<Map<String, Object>>();
		// }
		// if (list != null) {
		// for (Map<String, Object> map : list) {
		//
		// if (map.get("repayStatus").toString().equals(
		// 1+ "")) {
		// map.put("repayStatus","未偿还");
		// } else {
		// map.put("repayStatus","已偿还");
		// }
		// }
		// }
		//
		//
		// HSSFWorkbook wb= ExcelUtils.exportExcel("还款明细账", pageBean.getPage(),
		// new String[] { "标题", "第几期", "应还款日期", "实际还款日期", "本期应还本息(￥)", "利息(￥)",
		// "逾期罚款(￥)","逾期天数(天)","还款状态"
		// }, new String[] { "borrowTitle", "repayPeriod",
		// "repayDate", "realRepayDate", "forPI",
		// "stillInterest", "lateFI","lateDay","repayStatus" });
		//
		//
		// this.export(wb, new Date().getTime() + ".xls");
		//
		// } catch (SQLException e) {
		//
		// e.printStackTrace();
		// } catch (DataException e) {
		//
		// e.printStackTrace();
		// } catch (IOException e) {
		//
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return null;
	}

	/**
	 * 借款明细账
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String queryBorrowInvestorInfo() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);// 获得用户编号
			pageBean.setPageNum(appInfoMap.get("curPage"));
			/*
			 * String investor = request("investor") == null ? "" :
			 * request("investor");
			 */
			String investor = appInfoMap.get("investor");

			frontpayService.queryBorrowInvestorInfo(pageBean, userId, investor);
			// this.setRequestToParamMap();
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 还款数据显示
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String queryMyPayData() throws DataException, SQLException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long payId = appInfoMap.get("payId") == null ? -1 : Convert.strToLong(appInfoMap.get("payId"), -1);
			Map<String, String> payMap = frontpayService.queryMyPayData(payId);
			request().setAttribute("payMap", payMap);
			jsonMap.put("payMap", payMap);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 提交还款记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String submitPay() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id") == null ? "" : appInfoMap.get("id");
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);// 获得用户编号
			AccountUserDo user = userService1.getById(userId);
//			User user = userService.jumpToWorkData(userId);
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1L);
			String pwd = appInfoMap.get("pwd") == null ? "" : appInfoMap.get("pwd");
			if (StringUtils.isBlank(pwd.trim())) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> map = frontpayService.submitPay(idLong, user.getId(), pwd, getBasePath(), user.getUsername(), user.getId(), idLong, idLong);
			String result = map.get("msg");
			if (result == null) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "操作失败");
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "操作成功");
				JSONUtils.printObject(jsonMap);
			}

		} catch (Exception e) {
			jsonMap.put("error", "6");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

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

	public static Date changeStrToDate(String dateTime) {
		if (dateTime != null) {
			String[] strs = dateTime.split("-");
			int ind = strs[2].indexOf(" ");
			if (ind >= 0) {
				strs[2] = strs[2].substring(0, ind + 1);
			}
			Date date = new Date();// 取时间
			long time = date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2].trim(), -1), 0, 0, 0);
			date.setTime(time);
			return date;
		}
		return null;
	}

}
