package com.sp2p.action.admin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.ChinaPnRInterface;

/**
 * 用户资金管理
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class UserFundRecordAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(LinksAction.class);
	private UserService userService;
	private List<Map<String, Object>> status;
	private List<Map<String, Object>> rechargeStatus;
	private List<Map<String, Object>> rechargeType;
	private AdminService adminService;
	private FundManagementService fundManagementService;
	@Autowired
	private IUserService accountUserService;

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	/**
	 * 用户资金管理页面加载
	 * 
	 * @return
	 */
	public String userFundInit() {
		return SUCCESS;
	}

	/**
	 * 查找用户资金列表信息
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundList() throws Exception {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		Map<String,Object> searchItems = new HashMap<String,Object>();
		searchItems.put("userName", StringUtils.isNotBlank(userName)?userName:null);
		searchItems.put("beginCount", pageBean.getStartOfPage());
		searchItems.put("pageSize", pageBean.getPageSize());
		NPageDo<Map<String, Object>> pageDo = accountUserService.queryUserFundRecords(searchItems);
		pageBean.setTotalNum(pageDo.getTotalCount());
		pageBean.setPage(pageDo.getModelList());
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 资金还款管理页面加载
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryUserFundRepayInit() {
		InputStream input = null;
		String number = request("numberId");
		request().setAttribute("numberId", number);
		try {
			input = new FileInputStream(this.getClass().getClassLoader().getResource("config.properties").toURI().getPath());
			Properties pro = new Properties();
			pro.load(input);
			String auto = pro.getProperty("com.shove.autoRepay");
			if (auto != null && "yes".equals(auto.trim()))
				request().setAttribute("autoRepayFunction", "关闭自动还款");
			else
				request().setAttribute("autoRepayFunction", "开启自动还款");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return SUCCESS;
	}

	/**
	 * 查找用户还款资金列表信息
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundRepayList() throws DataException, Exception {
		String username = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("username")), "");
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")), "");
		String number = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("number")), "");
		int repayStatus = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("repayStatus")), -1);

		if (number == null || number == "") {
			number = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("numberId")), "");
		}
		try {
			fundManagementService.queryUserFundRepayList(pageBean, username, realName, number, repayStatus);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 提前还款 如果入账账户是担保账户请指定 InAcctId，否则可空。
	 */
	public String updatePreRepayment() throws Exception {
		JSONObject json = new JSONObject();
		long borrowId = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("borrowId")), -1);// 要还款的borrowid
		long outCustId = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("usrCustId")), -1);// 还款人客户号,即出账账户号
		long userId = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("userId")), -1);// 还款人id
		long payId = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("payId")), -1);
		if (borrowId < 0) {
			json.put("msg", "没有找到借款信息");
			JSONUtils.printObject(json);
			return null;
		}
		if (outCustId < 0) {
			json.put("msg", "没有找到该借款人信息");
			JSONUtils.printObject(json);
			return null;
		}
		if (userId < 0) {
			json.put("msg", "没有找到该借款人信息");
			JSONUtils.printObject(json);
			return null;
		}
		if (payId < 0) {
			json.put("msg", "没有找到该借款人信息");
			JSONUtils.printObject(json);
			return null;
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (admin == null) {
			json.put("msg", "请先登录");
			JSONUtils.printObject(json);
		}
		String ret = fundManagementService.preRepayment(borrowId, outCustId + "", userId, payId,admin);
		json.put("msg", ret);
		JSONUtils.printObject(json);
		return null;
	}

	/**
	 * 查询该用户借款详情 初始化
	 * 
	 * @return
	 */
	public String queryUserFundBorrowInit() {
		request().setAttribute("userId", request().getParameter("userId"));
		return SUCCESS;
	}

	/**
	 * 通过ID 查询该用户借款详情
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundBorrowList() throws DataException, SQLException {
		long userId = Convert.strToLong(paramMap.get("userId"), -100);
		try {
			fundManagementService.queryUserFundBorrowList(pageBean, userId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 查询该用户投资详情 初始化
	 * 
	 * @return
	 */
	public String queryUserFundInvestInit() {
		request().setAttribute("userId", SqlInfusion.FilteSqlInfusion(request().getParameter("userId")));
		return SUCCESS;
	}

	/**
	 * 通过ID 查询该用户投资详情
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundInvestList() throws DataException, SQLException {
		long userId = Convert.strToLong(paramMap.get("userId"), -100);
		try {
			fundManagementService.queryUserFundInvestList(pageBean, userId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	public String userFundWithdrawInit() {
		String userId = request("userId");
		paramMap.put("userId", userId);
		return SUCCESS;
	}

	/**
	 * 查询提现记录
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundWithdrawList() throws SQLException, DataException {

		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String applyTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("applyTime")), null);
		Double sum = paramMap.get("sum") == null ? -100 : Convert.strToDouble(paramMap.get("sum"), -100);
		Integer status = paramMap.get("status") == null ? -100 : Convert.strToInt(paramMap.get("status"), -100);

		Long userId = Convert.strToLong(paramMap.get("userId"), -100);
		String startTime = applyTime;
		String endTime = FrontMyPaymentAction.changeEndTime(applyTime);
		try {
			fundManagementService.queryUserFundWithdrawInfo(pageBean, userName, startTime, endTime, sum, status, userId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 用户资金管理 充值记录
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundRechargeList() throws SQLException, DataException {

		String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("start")), null);
		String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("end")), null);

		Integer status = paramMap.get("status") == null ? -100 : Convert.strToInt(paramMap.get("status"), -100);

		Integer rt = paramMap.get("rechargeType") == null ? -100 : Convert.strToInt(paramMap.get("rechargeType"), -100);

		Long userId = Convert.strToLong(paramMap.get("userId"), -100);
		try {
			fundManagementService.queryUserFundRechargeInfo(pageBean, startTime, endTime, status, userId, rt);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String queryUserFundRecharge() throws SQLException, DataException {

		String applyTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("rechargeTime")), null);

		Integer status = paramMap.get("status") == null ? -100 : Convert.strToInt(paramMap.get("status"), -100);

		Integer rt = paramMap.get("rechargeType") == null ? -100 : Convert.strToInt(paramMap.get("rechargeType"), -100);

		Long userId = Convert.strToLong(paramMap.get("userId"), -100);
		String startTime = applyTime;
		String endTime = FrontMyPaymentAction.changeEndTime(applyTime);
		try {
			fundManagementService.queryUserFundRechargeInfoById(pageBean, startTime, endTime, status, userId, rt);

		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 用户ID 查询借款信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundBorrowInfo() throws SQLException, DataException {
		Long userId = Convert.strToLong(request().getParameter("userId"), -100);
		try {
			fundManagementService.queryUserFundBorrowById(pageBean, userId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 弹窗查询充值记录
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundRechargeForWindow() throws SQLException, DataException {
		String rechargeNumber = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("rechargeNumber")), null);
		try {
			fundManagementService.queryUserFundRechargeInfoByRechargeNumber(pageBean, rechargeNumber);

		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String exportUserFundRecharge() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Long userId = Convert.strToLong(request("userId"), -1L);
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			String applyTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("applyTime")), null);
			String endTime = "";
			if (StringUtils.isNotBlank(applyTime)) {
				endTime = FrontMyPaymentAction.changeEndTime(applyTime);

			}
			Integer status = Convert.strToInt(request().getParameter("statss"), -100);
			Integer rt = Convert.strToInt(request().getParameter("reType"), -100);
			// 充值记录
			fundManagementService.queryUserFundRechargeInfo(pageBean, applyTime, endTime, status, userId, rt);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值记录", pageBean.getPage(), new String[] { "用户名", "充值类型", "充值金额", "手续费", "到账金额", "充值时间", "状态" }, new String[] { "username",
					"rechargeType", "rechargeMoney", "poundage", "realMoney", "rechargeTime", "result" });
			operationLogService.addOperationLog("v_t_user_rechargeall_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户充值记录", 2);
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
	 * 导出 还款记录
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportRepaymentReport() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("userName")), null);
			Long borrowId = Convert.strToLong(request().getParameter("borrowId"), -100);
			fundManagementService.queryRepaymentReport(pageBean, userName, borrowId);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("还款记录", pageBean.getPage(), new String[] { "ID", "用户名", "真实姓名", "模块", "类型", "内容", "操作人员", "结果", "操作", "操作时间", "IP" },
					new String[] { "borrowId", "username", "realName", "module", "type", "content", "checkName", "result", "act", "applyDate", "IP" });
			operationLogService.addOperationLog("v_t_user_rechargeall_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户还款记录", 2);
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
	 * 导出查询提现记录
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportUserFundWithdraw() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Long userId = Convert.strToLong(request("userId"), -1L);
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			String applyTime = SqlInfusion.FilteSqlInfusion(request().getParameter("applyTime") == null ? null : request().getParameter("applyTime"));
			String endTime = null;
			if (StringUtils.isNotBlank(applyTime)) {
				endTime = FrontMyPaymentAction.changeEndTime(applyTime);
			}

			String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("userName") == null ? "" : request().getParameter("userName"));
			if (StringUtils.isNotBlank(userName)) {
				userName = URLDecoder.decode(userName, "UTF-8");
			}
			double sum = Convert.strToDouble(request().getParameter("sum"), -1);
			int statss = Convert.strToInt(request().getParameter("statss"), -1);
			// 提现记录
			fundManagementService.queryUserFundWithdrawInfo(pageBean, userName, applyTime, endTime, sum, statss, userId);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("提现记录", pageBean.getPage(), new String[] { "用户名", "真实姓名", "提现账号", "提现银行", "支行", "提现总额", "到账金额(￥)", "手续费", "提现时间" },
					new String[] { "username", "realName", "acount", "bankName", "branchBankName", "sum", "realAccount", "poundage", "applyTime" });
			this.export(wb, new Date().getTime() + ".xls");

			operationLogService.addOperationLog("v_t_user_fundwithdraw_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户提现记录", 2);
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
	 * 导出 查询 资金记录
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportUserFundRecordList() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Long userId = Convert.strToLong(request("userId"), -1L);
		String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("userName") == null ? "" : request().getParameter("userName"));
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			if (StringUtils.isNotBlank(userName)) {
				userName = URLDecoder.decode(userName, "UTF-8");
			}
			fundManagementService.queryUserFundRecordList(pageBean, userId, userName);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("提现记录", pageBean.getPage(),
					new String[] { "用户名", "类型", "操作金额", "总余额", "可用余额", "冻结金额", "待收本金", "待收利息", "待收总额", "交易对方", "记录时间" }, new String[] { "username", "fundMode", "handleSum",
							"totalSum", "usableSum", "freezeSum", "dueinCapitalSum", "dueinAccrualSum", "dueinSum", "traderName", "recordTime" });
			this.export(wb, new Date().getTime() + ".xls");

			operationLogService.addOperationLog("v_t_user_fundwithdraw_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户提现记录", 2);
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

	public String queryAllUserFundRecordInit() {
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String queryAllUserFundRecordList() throws Exception {
		try {
			String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));

			fundManagementService.queryAllUserFundRecordList(pageBean, userName);
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
	 * 导出资金明细
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportAllUserFundList() {
		pageBean.setPageSize(100000);
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("userName") == null ? "" : request().getParameter("userName"));
			userName = URLDecoder.decode(userName, "UTF-8");// 中文乱码转换
			// 资金明细
			fundManagementService.queryAllUserFundRecordList(pageBean, userName);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			fundManagementService.changeTraderName(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("资金明细表", pageBean.getPage(), new String[] { "ID", "用户名", "真实姓名", "总金额", "可用金额", "冻结金额", "待收本金", "待收利息", "待收总额", "待还金额" },
					new String[] { "id", "username", "realName", "sumcount", "usableSum", "freezeSum", "dueinCapitalSum", "dueinAccrualSum", "dueinSum", "dueoutSum" });
			operationLogService.addOperationLog("v_t_user_fundrecord_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出资金明细列表", 2);
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
	 * 资金管理 资金记录
	 * 
	 * @return
	 */
	public String userFundRecordInit() {
		String userName = SqlInfusion.FilteSqlInfusion(request("userName"));
		String userId = SqlInfusion.FilteSqlInfusion(request("userId"));
		paramMap.put("userName", userName);
		paramMap.put("userId", userId);
		return SUCCESS;
	}

	/**
	 * 查看投资曲线收益图
	 * 
	 * @return
	 */
	public String queryUserIncomeInfo() {
		long userid = Convert.strToLong(request("userId"), -1);
		if (userid < 0)
			return SUCCESS;
		try {
			String invest = fundManagementService.queryInvestForYear(userid);
			String income = fundManagementService.queryIncomeForYear(userid);

			request().setAttribute("invest", invest);
			request().setAttribute("income", income);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryUserFundInfoInit() {
		return SUCCESS;
	}

	/**
	 * 用户资金管理 资金记录
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundInfoList() throws SQLException, DataException {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		if (userName != null)
			request().setAttribute("userName", userName);
		try {
			fundManagementService.queryUserFundInfoList(pageBean, userName);
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

	@SuppressWarnings("unchecked")
	public String queryUserFundRecordList() throws SQLException, DataException {
		Long userId = paramMap.get("userId") == null ? -100 : Convert.strToLong(paramMap.get("userId"), -100);

		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		if (userName != null)
			request().setAttribute("userName", userName);
		try {
			fundManagementService.queryUserFundRecordList(pageBean, userId, userName);
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 用户借款详情
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserBorrowRecordList() throws SQLException, DataException {
		Long userId = paramMap.get("userId") == null ? -100 : Convert.strToLong(paramMap.get("userId"), -100);

		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		if (userName != null) {
			request().setAttribute("userName", userName);
		}
		try {
			fundManagementService.queryUserBorrowRecordList(pageBean, userId);
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

	public List<Map<String, Object>> getRechargeStatus() {
		if (rechargeStatus == null) {// #{0:'全部',2:'成功',5:'失败',1:'充值中'}"
			rechargeStatus = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("statusId", -100);
			mp.put("statusValue", "全部");
			rechargeStatus.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 1);
			mp.put("statusValue", "成功");
			rechargeStatus.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 0);
			mp.put("statusValue", "失败");
			rechargeStatus.add(mp);

		}
		return rechargeStatus;
	}

	public void setRechargeStatus(List<Map<String, Object>> rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public List<Map<String, Object>> getRechargeType() {
		if (rechargeType == null) {
			rechargeType = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("typeValue", "支付宝支付");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("typeValue", "环迅支付");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("typeValue", "国付宝");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 6);
			mp.put("typeValue", "线下充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 51);
			mp.put("typeValue", "手工充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 52);
			mp.put("typeValue", "虚拟充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 53);
			mp.put("typeValue", "奖励充值");
			rechargeType.add(mp);

		}
		return rechargeType;
	}

	public void setRechargeType(List<Map<String, Object>> rechargeType) {
		this.rechargeType = rechargeType;
	}

	public List<Map<String, Object>> getStatus() {
		if (status == null) {// #{0:'全部',2:'成功',5:'失败',1:'充值中'}"
			status = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("statusId", 0);
			mp.put("statusValue", "全部");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 2);
			mp.put("statusValue", "成功");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 5);
			mp.put("statusValue", "失败");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 4);
			mp.put("statusValue", "充值中");
			status.add(mp);
		}
		return status;
	}

	public void setStatus(List<Map<String, Object>> status) {
		this.status = status;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

}
