package com.sp2p.action.admin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.config.ChinaPnrConfig;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.ServletUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ExcelUtils;
import com.sp2p.action.front.FrontMyFinanceAction;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.FrontMyPaymentService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SendMessageService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

/**
 * 后台财务管理
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
public class FIManageAction extends BasePageAction {

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private UserService userService;
	private AdminService adminService;
	private SelectedService selectedService;
	private SendMessageService sendMessageService;
	private FundManagementService fundManagementService;
	private FrontMyPaymentService frontpayService;
	private MyHomeService myHomeSerivce;

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	/**
	 * 充值扣费
	 */

	private long userId;
	/**
	 * 财务管理 充值提现审核 全部提现状态
	 */
	private List<Map<String, Object>> operateType;
	private List<Map<String, Object>> status;
	private List<Map<String, Object>> results;
	/**
	 * 财务管理 充值记录管理
	 */
	private List<Map<String, Object>> rechargeTypes;

	public String queryRechargeRecordInit() {
		return SUCCESS;
	}

	/**
	 * 还款管理 还款报表初始化
	 * 
	 * @return
	 */
	public String queryRepaymentReportInit() {
		String id = request().getParameter("borrowId");
		request().setAttribute("borrowId", id);
		return SUCCESS;
	}

	/**
	 * 还款管理 还款报表
	 */
	public String queryRepaymentReport() throws Exception {
		Long borrowId = Convert.strToLong(paramMap.get("borrowId"), -100);
		try {
			// 查询还款记录
			fundManagementService.queryRepaymentReport(pageBean, null, borrowId);
			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
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
	 * 还款管理 还款报表--初始化
	 */
	public String queryRepaymentInvestIndex() {
		String borrowId = request("investId");
		request().setAttribute("borrowId", borrowId);
		return SUCCESS;
	}

	/**
	 * 还款管理 投资人报表
	 */
	public String queryRepaymentInvest() throws Exception {
		String borrowId = Convert.strToStr(paramMap.get("borrowId"), "-1");
		String userName = Convert.strToStr(paramMap.get("userName"), "");
		try {
			// 查询还款记录
			fundManagementService.queryRechargeInversList(pageBean, userName, borrowId);
			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 处理 还款
	 */
	public String updateRepayment() throws Exception {
		JSONObject json = new JSONObject();

		long repaymentId = Convert.strToLong(paramMap.get("repaymentId"), -1L);
		double stillAmount = Convert.strToDouble(paramMap.get("stillAmount"), 0);// 本期应还总额
		String usrCustId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("usrCustId")), ""); // 汇付客户号
		long userId = Convert.strToLong(paramMap.get("userId"), -1L);
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), "");

		if (repaymentId <= 0 || stillAmount <= 0 || userId <= 0 || StringUtils.isBlank(userName) || StringUtils.isBlank(usrCustId)) {
			json.put("msg", "非法操作");
			JSONUtils.printObject(json);
			return null;
		}
		doNormalRepay(repaymentId, usrCustId, userId, userName, "", stillAmount);
		return null;

		// long borrowId = Convert.strToLong(paramMap.get("borrowId"), -1L);
		// String deadline = Convert.strToStr(paramMap.get("deadline"), "0"); //
		// 提前还款期限
		// Admin admin = (Admin)
		// session().getAttribute(IConstants.SESSION_ADMIN);
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前月的天数
		// Calendar a = Calendar.getInstance();
		// a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		// a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		// obj = doNormalRepay(repaymentId, usrCustId, userId, userName,
		// repayDate, stillAmount);
		// Date repayDate = sf.parse(Convert.strToStr(paramMap.get("repayDate"),
		// UtilDate.getDate()));
	}

	/**
	 * 普通还款
	 */
	private void doNormalRepay(long payId, String outCustId, long userId, String username, String pwd, double needSum) throws Exception {
		String Fee = "0.00";
		String divDetails = "";
		String transAmt = "";
		String inAcctId = "";
		String outAcctId = "";

		List<Map<String, Object>> list = frontpayService.queryAllInvestInfo(payId);// 查询所有投资人的投资信息
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
				 transAmt = map.get("compensatorySum")+"";
			}
			JSONObject jsonObject=JSONObject.fromObject(ChinaPnRInterface.repayment("10", ordId, outCustId, outAcctId, transAmt, inCustId, inAcctId, subOrdId, subOrdDate, Fee, divDetails));
			System.out.println("ChinaPnRInterface repayment result:"+jsonObject);
		}
		Map<String, String> map = frontpayService.submitPay(payId, userId, pwd, getBasePath(), username, needSum, fee, consultFee);
		int ret = Convert.strToInt(map.get("ret"), -1);
		if (ret > 0) {
			obj.put("msg", "ok");
		} else {
			obj.put("msg", Convert.strToStr(map.get("ret_desc"), "还款失败"));
		}
		JSONUtils.printObject(obj);
	}


	/**
	 * 财务管理 充值记录查询
	 */
	public String queryRechargeRecordList() throws DataException, SQLException {

		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String reStartTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("rechargeTime")), null);
		int rechargeType = Convert.strToInt(paramMap.get("rechargeType"), -100);
		String reEndTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("rechargeTime")), null));
		Integer result = paramMap.get("status") == null ? -100 : Convert.strToInt(paramMap.get("status"), -100);
		try {

			fundManagementService.queryRechargeRecordList(pageBean, userName, reStartTime, reEndTime, rechargeType, result);

			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 导出充值记录查询
	 */
	public String exportRechargeRecord() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("userName")), "");
			userName = URLDecoder.decode(userName, "UTF-8");
			String rechargeTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("rechargeTime")), null);
			String reEndTime = null;
			if (StringUtils.isNotBlank(rechargeTime)) {
				reEndTime = FrontMyPaymentAction.changeEndTime(rechargeTime);
			}
			int rechargeType = Convert.strToInt(paramMap.get("rechargeType"), -100);
			int statss = Convert.strToInt(request().getParameter("statss"), -1);
			// 待还款详情
			fundManagementService.queryRechargeRecordList(pageBean, userName, rechargeTime, reEndTime, rechargeType, statss);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			fundManagementService.changeFigure(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值记录", pageBean.getPage(), new String[] { "用户名", "充值类型", "充值金额", "费率", "到账金额", "充值时间", "状态" }, new String[] { "username",
					"rechargeType", "rechargeMoney", "cost", "realMoney", "rechargeTime", "result" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_user_rechargedetails_list", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出充值记录列表", 2);
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
	 * 财务管理 充值记录 第一次充值查询
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryRechargeFirstList() throws DataException, SQLException {

		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String reStartTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("reStartTime")), null);
		int rechargeType = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("rechargeType")), -100);
		String reEndTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("reEndTime")), null));
		try {

			fundManagementService.queryRechargeFirstList(pageBean, userName, reStartTime, reEndTime, rechargeType);
			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 导出充值记录 第一次充值查询
	 * 
	 * @return
	 */
	public String exportRechargeFirst() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("userName")), null);
			if (StringUtils.isNotBlank(userName)) {
				userName = URLDecoder.decode(userName, "UTF-8");
			}
			String reStartTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("reStartTime")), null);
			int rechargeType = Convert.strToInt(paramMap.get("rechargeType"), -100);
			String reEndTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("reEndTime")), null));

			// 待还款详情
			fundManagementService.queryRechargeFirstList(pageBean, userName, reStartTime, reEndTime, rechargeType);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			fundManagementService.changeFigure(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值记录", pageBean.getPage(), new String[] { "用户名", "充值类型", "充值金额", "费率", "到账金额", "充值时间", "状态" }, new String[] { "username",
					"rechargeType", "rechargeMoney", "cost", "realMoney", "rechargeTime", "result" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_user_rechargefirst_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出充值记录第一次充值查询", 2);
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
	 * 查看第一次充值数据
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryOneFirstChargeDetails() throws SQLException, DataException {
		Long rechargeId = request("rechargeId") == null ? -100 : Convert.strToLong(request("rechargeId"), -100);
		try {
			paramMap = fundManagementService.queryOneFirstChargeDetails(rechargeId, true);
			if (paramMap != null) {
				String resultId = paramMap.get("result").toString();
				if (resultId.equals(0 + "")) {// 失败
					paramMap.put("realMoney", "0.00");
				}
			}

		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 财务管理 充值提现审核 全部提现
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAllWithdrawList() throws Exception {

		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("startTime")), null);
		String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("endTime")), null);
		endTime = FrontMyPaymentAction.changeEndTime(endTime);

		Integer status = paramMap.get("status") == null ? -100 : Convert.strToInt(paramMap.get("status"), -100);

		try {
			fundManagementService.queryAllWithdrawList(pageBean, userName, startTime, endTime, status);

//			List<Map<String, Object>> list = pageBean.getPage();
//				long id = 0;
//				String AvlBal = "0.00";
//				String FrzBal = "0.00";

				// 后台查询余额 接口
//				for (int i = 0; i < list.size(); i++) {
//					Map<String, Object> map = list.get(i);
//					id = Convert.strToLong(map.get("id") + "", -1);
//					String usrCustId = map.get("usrCustId") + "";
//					if (Convert.strToLong(usrCustId, -1) <= 0) {
//						continue;
//					}
//					String usableSumStr = map.get("usableSum") + "";
//					String freezeSum = map.get("freezeSum") + ""; // 系统中用户冻结金额
//					String jsonStr = ChinaPnRInterface.queryBalanceBg(usrCustId);
//					JSONObject json = JSONObject.fromObject(jsonStr);
//					int RespCode = json.getInt("RespCode");
//					if (RespCode != 0) {
//						continue;
//					}
//					AvlBal = json.getString("AvlBal"); // 用户在汇付可用余额
//					FrzBal = json.getString("FrzBal"); // 用户在汇付冻结金额
//					AvlBal = AvlBal.replaceAll(",", "");
//					FrzBal = FrzBal.replaceAll(",", "");
//					if ((AvlBal.equals(usableSumStr)) || FrzBal.equals(freezeSum)) {
//						myHomeSerivce.usableAmountUpdate(id, Double.valueOf(AvlBal), Double.valueOf(FrzBal));
//					}
//				}
//			} else {
				// fundManagementService.queryAllUserFundRecordList(pageBean,
				// userName);
//			}
			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 提现 --- 导出
	 */
	public String exportAllWithdraw() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Integer status = Convert.strToInt(request().getParameter("statss"), -1);
		String exporName = "";
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("userName") == null ? "" : request().getParameter("userName"));
			String startTime = SqlInfusion.FilteSqlInfusion(request().getParameter("startTime") == null ? "" : request().getParameter("startTime"));
			String endTime = SqlInfusion.FilteSqlInfusion(request().getParameter("endTime") == null ? "" : request().getParameter("endTime"));
			userName = URLDecoder.decode(userName, "UTF-8");// 中文乱码转换
			startTime = URLDecoder.decode(startTime, "UTF-8");
			endTime = URLDecoder.decode(endTime, "UTF-8");
			if (StringUtils.isNotBlank(endTime)) {
				endTime = FrontMyPaymentAction.changeEndTime(endTime);
			}
			// 提现详情
			fundManagementService.queryAllWithdrawList(pageBean, userName, startTime, endTime, status);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}

			if (status == 0) {
				exporName = "全部提现列表";
			} else if (status == 1) {
				exporName = "待审核提现列表";
			} else if (status == 4) {
				exporName = "转账中的提现列表";
			} else if (status == 2) {
				exporName = "成功的提现列表";
			} else if (status == 5) {
				exporName = "失败的提现列表";
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel(exporName, pageBean.getPage(), new String[] { "用户名", "真实姓名", "提现账号", "提现银行", "支行", "提现总额", "到账金额(￥)", "手续费", "提现时间", "状态" },
					new String[] { "name", "realName", "acount", "bankName", "branchBankName", "sum", "realMoney", "poundage", "applyTime", "status", });
			this.export(wb, new Date().getTime() + ".xls");
			// 系统操作日志
			operationLogService.addOperationLog("v_t_user_withdraw_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出" + exporName, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出 -平台提现
	 */
	public String exportProxyWithdraw() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Integer status = Convert.strToInt(request().getParameter("statss"), -1);// 1成功
																				// 0
																				// 失败
		String userName = SqlInfusion.FilteSqlInfusion(request("userName") == null ? "" : request("userName"));
		String startTime = SqlInfusion.FilteSqlInfusion(request("startTime") == null ? "" : request("startTime"));
		String endTime = SqlInfusion.FilteSqlInfusion(request("endTime") == null ? "" : request("endTime"));
		String exporName = "";
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			if (StringUtils.isNotBlank(endTime)) {
				endTime = FrontMyPaymentAction.changeEndTime(endTime);
			}
			// 提现详情
			fundManagementService.queryProxyWithList(pageBean, userName, startTime, endTime, status);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}

			if (status == 0) {
				exporName = "失败的平台提现列表";
			} else if (status == 1) {
				exporName = "成功的平台提现列表";
			} else {
				exporName = "全部平台提现列表";
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel(exporName, pageBean.getPage(), new String[] { "用户名", "真实姓名", "提现账号", "提现银行", "支行", "提现总额", "到账金额(￥)", "手续费", "提现时间", "状态" },
					new String[] { "username", "realName", "cardNo", "bankName", "branchBankName", "sum", "realSum", "poundage", "applyTime", "status", });
			this.export(wb, new Date().getTime() + ".xls");
			// 系统操作日志
			operationLogService.addOperationLog("v_t_mer_cash_hhn", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出" + exporName, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 财务管理 充值记录
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryUserCashList() throws DataException, SQLException {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName") == null ? null : Convert.strToStr(paramMap.get("userName"), null));
		try {
			fundManagementService.queryUserCashList(pageBean, userName);
			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 财务管理 充值管理 充值/扣费
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	public String queryBackCashInit() {
		String userId = request("userId");
		paramMap.put("userId", userId);

		return SUCCESS;
	}

	public String queryBackCashList() throws Exception {
		String checkUser = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("checkUser")), null);
		String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("startTime")), null);
		String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("endTime")), null);
		if (StringUtils.isNotBlank(endTime)) {
			endTime = FrontMyPaymentAction.changeEndTime(endTime);
		}
		Integer type = Convert.strToInt(paramMap.get("type"), -100);
		String uname = Convert.strToStr(paramMap.get("uname"), null);
		try {
			fundManagementService.queryBackCashList(pageBean, -1L, checkUser, startTime, endTime, type, uname);
			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 导出充值明细
	 * 
	 * @return
	 */
	public String exportBackCash() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		String userId = request("userId");
		Long uid = -100L;
		if (userId != null) {
			uid = Convert.strToLong(userId, -100);
		}
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			String checkUser = SqlInfusion.FilteSqlInfusion(request().getParameter("checkUser") == null ? "" : request().getParameter("checkUser"));
			String startTime = SqlInfusion.FilteSqlInfusion(request().getParameter("startTime") == null ? "" : request().getParameter("startTime"));
			String endTime = SqlInfusion.FilteSqlInfusion(request().getParameter("endTime") == null ? "" : request().getParameter("endTime"));
			if (StringUtils.isNotBlank(endTime)) {
				endTime = FrontMyPaymentAction.changeEndTime(endTime);
			}
			Integer type = Convert.strToInt(SqlInfusion.FilteSqlInfusion(request().getParameter("type")), -100);
			String uname = SqlInfusion.FilteSqlInfusion(request().getParameter("userName") == null ? "" : request().getParameter("userName"));
			// 中文乱码转换
			checkUser = URLDecoder.decode(checkUser, "UTF-8");
			uname = URLDecoder.decode(uname, "UTF-8");
			// 待还款详情
			fundManagementService.queryBackCashList(pageBean, uid, checkUser, startTime, endTime, type, uname);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			fundManagementService.changeFigure2(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值明细", pageBean.getPage(), new String[] { "用户名", "真实姓名", "类型", "操作金额", "手续费", "操作人员", "充值结果", "操作时间", "备注" }, new String[] {
					"uname", "realName", "type", "dealMoney", "cost", "userName", "result", "checkTime", "remark" });
			this.export(wb, new Date().getTime() + ".xls");
			// 添加系统操作日志
			operationLogService.addOperationLog("v_t_user_backrw_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出充值明细", 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出充值明细
	 * 
	 * @return
	 */
	public String exportBackCashSimple() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);

			long userId = Convert.strToLong(request("userId"), -100);

			fundManagementService.queryBackCashList(pageBean, userId, null, null, null, -100, null);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			fundManagementService.changeFigure2(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值明细", pageBean.getPage(), new String[] { "用户名", "真实姓名", "类型", "操作金额", "备注", "操作人员", "操作时间" }, new String[] { "uname",
					"realName", "type", "dealMoney", "remark", "userName", "checkTime" });
			this.export(wb, new Date().getTime() + ".xls");
			// 添加系统操作日志
			operationLogService.addOperationLog("v_t_user_backrw_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出充值明细", 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryR_WShow() throws DataException, SQLException {
		Long rwId = request("rwId") == null ? -100 : Convert.strToLong(request("rwId"), -100);

		try {
			paramMap = fundManagementService.queryR_WInfo(rwId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String addBackRechargeInit() {
		String userId = SqlInfusion.FilteSqlInfusion(request("userId"));
		try {
			Map<String, String> map = userService.queryUserBankById(Convert.strToLong(userId, -100));
			if (map == null || map.size() == 0) {
				JSONUtils.printStr2("<script>alert('未找到该用户信息');window.parent.jBox.close();</script>");
				return null;
			}
			if (Convert.strToLong(map.get("usrCustId"), -1) < 0) {
				JSONUtils.printStr2("<script>alert('该用户还不是汇付天下会员');window.parent.jBox.close();</script>");
				return null;
			}

			request().setAttribute("map", map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addBackUserRechargeInit() {
		String userId = SqlInfusion.FilteSqlInfusion(request("userId"));
		try {
			Map<String, String> map = userService.queryUserBankById(Convert.strToLong(userId, -100));
			if (map == null || map.size() == 0) {
				JSONUtils.printStr2("<script>alert('未找到该用户信息');window.parent.jBox.close();</script>");
				return null;
			}
			if (Convert.strToLong(map.get("usrCustId"), -1) < 0) {
				JSONUtils.printStr2("<script>alert('该用户还不是汇付天下会员');window.parent.jBox.close();</script>");
				return null;
			}

			request().setAttribute("map", map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addBackWithdrawInit() {
		String userId = SqlInfusion.FilteSqlInfusion(request("userId"));
		try {
			if (userId != null) {
				Map<String, String> map = userService.queryUserById(Convert.strToLong(userId, -100));
				if (map != null) {
					paramMap.put("userName", map.get("username"));
					paramMap.put("userId", userId);
					request().setAttribute("usrCustId", map.get("usrCustId"));
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 添加扣费
	 */
	public String addBackWithdraw() throws Exception {
		String dealMoneyParam = SqlInfusion.FilteSqlInfusion(paramMap.get("dealMoney"));
		double dealMoney = Convert.strToDouble(dealMoneyParam, 0);
		String _code = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("code")), null);
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId")); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		if (code == null || !code.equals(_code)) {
			this.addFieldError("paramMap['code']", "验证码错误");
			return "input";
		}

		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		Long adminId = admin.getId();
		String userIdParam = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		long userId = Convert.strToLong(userIdParam, -1);
		String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark"));

		try {
			Map<String, String> userMap = userService.queryUserById(userId);
			String addIP = ServletUtils.getRemortIp();
			long result = fundManagementService.addBackW(userId, adminId, IConstants.WITHDRAW, dealMoney, remark, new Date(), IConstants.FUNDMODE_WITHDRAW_HANDLE, addIP,
					userMap.get("username"), "手动扣费，备注：" + remark);
			if (result == -99) {
				JSONUtils.printStr("-99");
				return null;
			}
			this.setUserId(userId);
			operationLogService.addOperationLog("t_recharge_withdraw_info", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, " 进行充值扣费信息添加处理", 2);
			JSONUtils.printStr("1");
		} catch (Exception e) {
			JSONUtils.printStr("0");
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加会员奖励
	 */
	public String addBackRecharge() throws Exception {
		String moneyStr = SqlInfusion.FilteSqlInfusion(paramMap.get("dealMoney"));
		double dealMoney = Convert.strToDouble(moneyStr, 0);
		if (dealMoney <= 0) {
			JSONUtils.printStr2("会员奖励金额错误");
			return null;
		}
		String _code = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("code")), null);
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId")); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		if (code == null || !code.equals(_code)) {
			JSONUtils.printStr2("验证码错误");
			return null;
		}

		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String userIdParam = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		long userId = Convert.strToLong(userIdParam, -1);
		String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark"));
		// String username =
		// SqlInfusion.FilteSqlInfusion(paramMap.get("username"));
		// String addIP = ServletUtils.getRemortIp();

		Date now = new Date();
		String usrCustId = SqlInfusion.FilteSqlInfusion(paramMap.get("usrCustId"));

		try {
			long ordId = fundManagementService.addBankRecharge(userId, admin.getId(), dealMoney, remark, now, usrCustId);
			if (ordId <= 0) {
				JSONUtils.printStr2("会员奖励失败");
				return null;
			}
			// fundManagementService.addBackW(userId, admin.getId(),
			// IConstants.RECHARAGE, dealMoney, remark, new Date(),
			// IConstants.FUNDMODE_RECHARGE_HANDLE, addIP, username, "会员奖励，备注："
			// + remark);
			this.setUserId(userId);
			operationLogService.addOperationLog("t_recharge_withdraw_info", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, " 进行充值扣费信息添加处理", 2);
			JSONUtils.printStr2("奖励成功");
		} catch (Exception e) {
			JSONUtils.printStr2("奖励失败");
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 商户无卡代扣
	 */
	public String addBackUserRecharge() throws Exception {
		String moneyStr = SqlInfusion.FilteSqlInfusion(paramMap.get("dealMoney"));
		double dealMoney = Convert.strToDouble(moneyStr, 0);
		if (dealMoney <= 0) {
			JSONUtils.printStr2("充值金额错误");
			return null;
		}
		String _code = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("code")), null);
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId")); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		if (code == null || !code.equals(_code)) {
			JSONUtils.printStr2("验证码错误");
			return null;
		}

		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String userIdParam = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		long userId = Convert.strToLong(userIdParam, -1);
		String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark"));

		Date now = new Date();
		String usrCustId = SqlInfusion.FilteSqlInfusion(paramMap.get("usrCustId"));

		try {
			long ordId = fundManagementService.addBankUserRecharge(userId, admin.getId(), dealMoney, remark, now, usrCustId);
			if (ordId <= 0) {
				JSONUtils.printStr2("商户代扣充值失败");
				return null;
			}
			this.setUserId(userId);
			operationLogService.addOperationLog("t_recharge_withdraw_info", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, " 进行充值扣费信息添加处理", 2);
			JSONUtils.printStr2("商户代扣充值成功");
		} catch (Exception e) {
			JSONUtils.printStr2("商户代扣充值失败");
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	public String updateWithdraws() throws SQLException, DataException, UnsupportedEncodingException {
		String wids = SqlInfusion.FilteSqlInfusion(request("wids"));
		Long adminId = Convert.strToLong(request("adminId"), -100);
		Integer oldStatus = request("oldStatus") == null ? -1 : Convert.strToInt(request("oldStatus"), -1);
		String[] allIds = wids.split(",");// 进行全选删除的时候获得多个id值
		if (allIds.length <= 0) {
			return INPUT;
		}

		String[] strs = null;
		double mm, sum;
		float poundage;
		Long wid = 0L, userId = -100L;
		long result = -1L;

		for (int i = 0, n = allIds.length; i < n; i++) {
			result = -1L;
			strs = allIds[i].split(";");
			wid = Convert.strToLong(strs[0], -1);
			mm = sum = Convert.strToDouble(strs[1], 0);
			poundage = Convert.strToFloat(strs[2], 0);
			userId = Convert.strToLong(strs[3], -1);
			if (poundage > 0) {// 有手续费的时候，操作金额为减掉手续费的值
				mm = sum - poundage;
			}
		}
		return SUCCESS;
	}

	public List<Map<String, Object>> getRechargeTypes() {
		if (rechargeTypes == null) {
			rechargeTypes = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("typeValue", "支付宝支付");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("typeValue", "环迅支付");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("typeValue", "国付宝");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("typeValue", "线下充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 5);
			mp.put("typeValue", "手工充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 6);
			mp.put("typeValue", "虚拟充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 7);
			mp.put("typeValue", "奖励充值");
			rechargeTypes.add(mp);

		}
		return rechargeTypes;
	}

	public void setRechargeTypes(List<Map<String, Object>> rechargeTypes) {
		this.rechargeTypes = rechargeTypes;
	}

	public MyHomeService getMyHomeSerivce() {
		return myHomeSerivce;
	}

	public void setMyHomeSerivce(MyHomeService myHomeSerivce) {
		this.myHomeSerivce = myHomeSerivce;
	}

	public FrontMyPaymentService getFrontpayService() {
		return frontpayService;
	}

	public List<Map<String, Object>> getResults() {
		if (results == null) {
			results = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("resultId", -100);
			mp.put("resultValue", "全部");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 1);
			mp.put("resultValue", "成功");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 0);
			mp.put("resultValue", "失败");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 2);
			mp.put("resultValue", "审核中");
			results.add(mp);
		}
		return results;
	}

	public void setResults(List<Map<String, Object>> results) {
		this.results = results;
	}

	public List<Map<String, Object>> getStatus() {
		if (status == null) {
			status = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("statusId", 0);
			mp.put("statusValue", "全部");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 1);
			mp.put("statusValue", "审核中");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 2);
			mp.put("statusValue", "成功");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 3);
			mp.put("statusValue", "取消");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 4);
			mp.put("statusValue", "转账中");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 5);
			mp.put("statusValue", "失败");
			status.add(mp);
		}
		return status;
	}

	public void setStatus(List<Map<String, Object>> status) {
		this.status = status;
	}

	public List<Map<String, Object>> getOperateType() {
		if (operateType == null) {
			operateType = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("tvalue", "手动充值");
			operateType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("tvalue", "手动扣费");
			operateType.add(mp);
		}
		return operateType;
	}

	public void setOperateType(List<Map<String, Object>> operateType) {
		this.operateType = operateType;
	}

	public String queryWithdrawInfo() throws SQLException, DataException {
		Long wid = request("wid") == null ? -100 : Convert.strToLong(request("wid"), -100);
		try {
			getWithdrawInfo(wid, true);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryWithdrawTransInfo() throws SQLException, DataException {
		Long wid = request("wid") == null ? -100 : Convert.strToLong(request("wid"), -100);
		try {
			getWithdrawInfo(wid, false);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void getWithdrawInfo(Long wid, boolean check) throws Exception {
		Map<String, String> b_map = fundManagementService.queryOneWithdraw(wid);
		request().setAttribute("wid", wid);
		String checkId = "", checkTime = "", remark = "";
		if (b_map != null) {
			request().setAttribute("realName", b_map.get("realName"));
			request().setAttribute("branchBankName", b_map.get("bankName") + " " + b_map.get("branchBankName"));
			request().setAttribute("cardNo", b_map.get("acount"));
			request().setAttribute("sum", b_map.get("sum"));
			request().setAttribute("realMoney", b_map.get("realMoney"));
			request().setAttribute("poundage", b_map.get("poundage"));
			request().setAttribute("applyTime", b_map.get("applyTime"));
			request().setAttribute("ipAddress", b_map.get("ipAddress"));
			request().setAttribute("userId", b_map.get("userId"));
			request().setAttribute("username", b_map.get("name"));
			request().setAttribute("trxId", b_map.get("trxId"));
			String status = b_map.get("status");
			if (status.equals(IConstants.WITHDRAW_CHECK + "")) {
				request().setAttribute("status", IConstants.WITHDRAW_CHECK_STR);
			} else if (status.equals(IConstants.WITHDRAW_SUCCESS + "")) {
				request().setAttribute("status", IConstants.WITHDRAW_SUCCESS_STR);
			} else if (status.equals(IConstants.WITHDRAW_TRANS + "")) {
				request().setAttribute("status", IConstants.WITHDRAW_TRANS_STR);
			} else if (status.equals(IConstants.WITHDRAW_FAIL + "")) {
				request().setAttribute("status", IConstants.WITHDRAW_FAIL_STR);
			}
			userId = Convert.strToLong(b_map.get("userId"), -100);
			checkId = b_map.get("checkId");
			checkTime = b_map.get("checkTime");
			remark = b_map.get("remark");
		}
		String defaultValue = "0.00";
		Map<String, String> rw_map = fundManagementService.queryUserRWInfo(userId, IConstants.RECHARGE_SUCCESS, IConstants.WITHDRAW_SUCCESS);
		if (rw_map != null) {
			if (rw_map.get("r_total").equals(""))
				request().setAttribute("r_total", defaultValue);// 充值成功总额
			else
				request().setAttribute("r_total", rw_map.get("r_total"));// 充值成功总额

			if (rw_map.get("w_total").equals(""))
				request().setAttribute("w_total", defaultValue);// 提现成功总额
			else
				request().setAttribute("w_total", rw_map.get("w_total"));// 提现成功总额

			if (rw_map.get("real_Amount").equals(""))
				request().setAttribute("real_Amount", defaultValue);// 投标成功总额
			else
				request().setAttribute("real_Amount", rw_map.get("real_Amount"));// 投标成功总额

			if (rw_map.get("retSum").equals(""))
				request().setAttribute("real_Amount", defaultValue);
			else
				request().setAttribute("retSum", rw_map.get("retSum"));

			request().setAttribute("withdraw_max", IConstants.WITHDRAW_MAX);
		}

		Map<String, String> u_map = userService.queryUserById(userId);
		if (u_map != null) {
			request().setAttribute("username", u_map.get("username"));
			request().setAttribute("usableSum", u_map.get("usableSum"));
			request().setAttribute("usrCustId", u_map.get("usrCustId"));
		}

		if (!check) {// 查看跟审核的时候，还要查询审核信息
			Map<String, String> ms = adminService.queryAdminById(Convert.strToLong(checkId, -100));
			String rk = "审核人:" + ms.get("userName") + ",   审核时间:" + checkTime + ",   审核备注:" + remark;
			request().setAttribute("rk", rk);
		}
	}

	/**
	 * 合和年 后台复核--转账中的提现
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public String updateWithdrawTransfer() throws Exception {
		String ordId = SqlInfusion.FilteSqlInfusion(paramMap.get("wid"));
		String transAmt = SqlInfusion.FilteSqlInfusion(paramMap.get("sum"));
		String usrCustId = SqlInfusion.FilteSqlInfusion(paramMap.get("usrCustId"));
		String status = SqlInfusion.FilteSqlInfusion(paramMap.get("status"));
		String trxId = SqlInfusion.FilteSqlInfusion(paramMap.get("trxId"));
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);

		String auditFlag = "";
		if (status.equals("2")) {
			auditFlag = "S";
		} else if (status.equals("5")) {
			auditFlag = "R";
		} else {
			JSONUtils.printStr2("转账状态错误");
			return null;
		}
		String result = fundManagementService.updateWithdrawTransfer(Convert.strToLong(ordId, -1), Convert.strToInt(status, -1), admin.getId(), ServletUtils.getRemortIp(), trxId,
				transAmt, usrCustId, auditFlag);
		JSONUtils.printStr2(result);
		return null;
	}

	/**
	 * 转账中的提现--后台管理员审核
	 */
	public String updateWithdrawCheck() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		Integer status = paramMap.get("status") == null ? -1 : Convert.strToInt(paramMap.get("status"), -1);
		Double money = paramMap.get("money") == null ? 0 : Convert.strToDouble(paramMap.get("money"), 0);
		float poundage = paramMap.get("poundage") == null ? 0 : Convert.strToFloat(paramMap.get("poundage"), 0);
		String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark") == null ? "" : paramMap.get("remark"));
		Long wid = paramMap.get("wid") == null ? -1 : Convert.strToLong(paramMap.get("wid"), -1);
		Long adminId = admin.getId();
		Long userId = paramMap.get("userId") == null ? -1 : Convert.strToLong(paramMap.get("userId"), -1);
		String ipAddress = ServletUtils.getRemortIp();
		String trxId = Convert.strToStr(paramMap.get("trxId"), "");

		if (money <= 0) {
			obj.put("msg", "到账金额格式错误");
			JSONUtils.printObject(obj);
			return null;
		}

		if (poundage < 0) {
			obj.put("msg", "手续费格式错误");
			JSONUtils.printObject(obj);
			return null;
		}

		Map<String, String> retMap = fundManagementService.updateWithdraw(wid, money, poundage, status, remark, adminId, userId, ipAddress, trxId);
		long retVal = -1;
		retVal = Convert.strToLong(retMap.get("ret") + "", -1);
		session().removeAttribute("randomCode");
		if (retVal <= 0) {
			obj.put("msg", retMap.get("ret_desc"));
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加操作日志
			operationLogService.addOperationLog("t_withdraw", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "提现审核", 2);
			obj.put("msg", "审核成功");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * 根据用户通知，发送站内信、邮件、手机短信提醒
	 * 
	 * @throws DataException
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 *             flag 标志是否有余额可扣
	 */
	protected void sendMessage(String title, String content, Long userId, String mode, boolean flag) throws SQLException, DataException, UnsupportedEncodingException {
		try {
			// String title = "资金变动提醒";
			// 查找通知类型的通知状态
			List<Map<String, Object>> lists = selectedService.queryNoticeMode(userId, mode);
			if (lists != null && lists.size() > 0) {

				// [通知方式(1 邮件 2 站内信 3 短信]
				if (lists.get(0).get("flag").toString().equals(String.valueOf(IConstants.NOTICE_ON))) {
					sendMessageService.emailSendTemplate(title, content, userId);
				}
				if (lists.get(1).get("flag").toString().equals(String.valueOf(IConstants.NOTICE_ON))) {
					sendMessageService.mailSend(title, content, userId);
				}
				if (lists.get(2).get("flag").toString().equals(String.valueOf(IConstants.NOTICE_ON))) {
					if (flag) {// 有余额可扣的清空下，才进行短信的发送
						String dateStr = DateUtil.dateToString(new Date());
						Map<String, String> u_map = userService.queryUserById(userId);
						String userName = "";
						if (u_map != null && u_map.size() > 0) {
							userName = u_map.get("username");
						}
						String newContent = "尊敬的" + userName + ":[" + dateStr + "]" + content;
						@SuppressWarnings("unused")
						Long result = sendMessageService.noteSend(newContent, userId);
					}
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	public String queryBackCashDetailsInit() {
		return SUCCESS;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public SendMessageService getSendMessageService() {
		return sendMessageService;
	}

	public void setSendMessageService(SendMessageService sendMessageService) {
		this.sendMessageService = sendMessageService;
	}

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	/**
	 * 打开或关闭 自动还款
	 * 
	 * @return
	 * @throws Exception
	 */
	public String openOrCloseAutoRepay() throws Exception {
		FileOutputStream fos = null;
		try {
			String filepath = this.getClass().getClassLoader().getResource("config.properties").toURI().getPath();
			InputStream input = new FileInputStream(filepath);
			Properties pro = new Properties();
			pro.load(input);
			input.close();

			String auto = pro.getProperty("com.shove.autoRepay");
			fos = new FileOutputStream(filepath);
			if (auto != null && "yes".equals(auto.trim()))
				pro.setProperty("com.shove.autoRepay", "no");
			else
				pro.setProperty("com.shove.autoRepay", "yes");
			pro.store(fos, "");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fos != null)
				fos.close();
		}
		return SUCCESS;
	}

	/**
	 * 查询 充值扣费 详情初始化
	 * 
	 * @return
	 */
	public String queryRechargeAndFeeInit() {
		request().setAttribute("userId", request("userId"));
		return SUCCESS;
	}

	/**
	 * 查询 充值扣费 详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryRechargeAndFeeList() throws Exception {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		try {
			fundManagementService.queryRechargeAndFeeList(pageBean, userName);
			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 导出充值扣费明细
	 * 
	 * @return
	 */
	public String exportRechargeAndFee() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		String userName = SqlInfusion.FilteSqlInfusion(request("userName"));
		if (userName != null)
			userName = Convert.strToStr(userName, "");

		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			// 中文乱码转换
			userName = URLDecoder.decode(userName, "UTF-8");
			// 待还款详情
			// fundManagementService.queryBackCashList(pageBean, uid, checkUser,
			// startTime, endTime, type, uname);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			fundManagementService.changeFigure2(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值明细", pageBean.getPage(), new String[] { "用户名", "真实姓名", "类型", "操作金额", "手续费", "操作人员", "充值结果", "操作时间", "备注" }, new String[] {
					"uname", "realName", "type", "dealMoney", "cost", "userName", "result", "checkTime", "remark" });
			this.export(wb, new Date().getTime() + ".xls");
			// 添加系统操作日志
			operationLogService.addOperationLog("v_t_user_backrw_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出充值明细", 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setFrontpayService(FrontMyPaymentService frontpayService) {
		this.frontpayService = frontpayService;
	}

	/** 平台取现列表 初始化 */
	public String proxyWithdrawInit() {
		return SUCCESS;
	}

	/** 平台取现 列表 */
	public String proxyWithdrawList() throws Exception {

		String username = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("username")), "");
		String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("startTime")), "");
		String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("endTime")), "");
		int status = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("status")), -1);
		if (StringUtils.isNotBlank(endTime)) {
			endTime = FrontMyPaymentAction.changeEndTime(endTime);
		}
		try {
			fundManagementService.queryProxyWithList(pageBean, username, startTime, endTime, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		List<Map<String, Object>> list = pageBean.getPage();
//		if (null != list) {
//			long id = 0;
//			String AvlBal = "0.00";
//			String FrzBal = "0.00";
//
//			// 后台查询余额 接口
//			for (int i = 0; i < list.size(); i++) {
//				Map<String, Object> map = list.get(i);
//				id = Convert.strToLong(map.get("id") + "", -1);
//				String usrCustId = map.get("usrCustId") + "";
//				if (Convert.strToLong(usrCustId, -1) <= 0) {
//					continue;
//				}
//				String usableSumStr = map.get("usableSum") + "";
//				String freezeSum = map.get("freezeSum") + ""; // 系统中用户冻结金额
//				String jsonStr = ChinaPnRInterface.queryBalanceBg(usrCustId);
//				JSONObject json = JSONObject.fromObject(jsonStr);
//				int RespCode = json.getInt("RespCode");
//				if (RespCode != 0) {
//					continue;
//				}
//				AvlBal = json.getString("AvlBal"); // 用户在汇付可用余额
//				FrzBal = json.getString("FrzBal"); // 用户在汇付冻结金额
//				AvlBal = AvlBal.replaceAll(",", "");
//				FrzBal = FrzBal.replaceAll(",", "");
//				if ((usableSumStr != AvlBal) || freezeSum != FrzBal) {
//					myHomeSerivce.usableAmountUpdate(id, Double.valueOf(AvlBal), Double.valueOf(FrzBal));
//				}
//			}
//		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("totalNum", pageBean.getTotalNum());
		return SUCCESS;
	}

	/** 平台取现 初始化 */
	public String merCashInit() {
		request().setAttribute("userId", request("userId"));
		request().setAttribute("username", request("username"));
		request().setAttribute("usrCustId", request("usrCustId"));
		request().setAttribute("cardNo", request("cardNo"));
		return SUCCESS;
	}

	/** 平台提交取现请求 */
	public String merCashSubmit() throws Exception {
		String usrCustId = request("usrCustId");
		String userId = request("userId");
		String username = Convert.strToStr(request("username"), "");
		double transAmt = Convert.strToDouble(request("transAmt"), 0);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);

		String ret = fundManagementService.addMerCash(transAmt, userId, admin.getId(), usrCustId, username);
		JSONUtils.printStr2(ret);
		return null;
	}
}
