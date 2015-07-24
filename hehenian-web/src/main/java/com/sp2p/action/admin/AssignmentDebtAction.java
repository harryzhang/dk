package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.vo.PageBean;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.AssignmentDebtService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

/**
 * 债权管理控制类
 * 
 * @author xiemin
 * @version [版本号, 2013-10-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AssignmentDebtAction extends BaseFrontAction {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(AssignmentDebtAction.class);

	private List<Map<String, Object>> borrowType;

	private List<Map<String, Object>> userGroup;

	/*
	 * 注入service
	 */
	private AssignmentDebtService assignmentService;

	/**
	 * 债权列表初始化
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String queryAssignmentIndex() {
		return SUCCESS;
	}

	/**
	 * 查询债权列表
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @throws DataException
	 * @throws SQLException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String queryAssignmentList() throws SQLException, DataException {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay"));
		String isDebt = SqlInfusion.FilteSqlInfusion(paramMap.get("isDebt"));
		String number = SqlInfusion.FilteSqlInfusion(paramMap.get("number"));

		assignmentService.queryAssignmentList(pageBean, userName, id, borrowWay, isDebt,number);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 根据ID查询债权信息
	 */
	@SuppressWarnings("unchecked")
	public String queryAssignmentById() throws DataException, SQLException {
		String id = request().getParameter("id");
		Map<String, String> map = null;
		try {
			pageBean.setPageSize(1000000);
			map = assignmentService.queryAssignmentById(pageBean, id);
			request().setAttribute("map", map);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 债权转让
	 */
	public String assignment() throws SQLException, IOException, DataException {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		String auctionDays = SqlInfusion.FilteSqlInfusion(paramMap.get("auctionDays"));
		String auctionBasePrice = SqlInfusion.FilteSqlInfusion(paramMap.get("auctionBasePrice"));
		String details = SqlInfusion.FilteSqlInfusion(paramMap.get("details"));
		String deadline = SqlInfusion.FilteSqlInfusion(paramMap.get("deadline"));

		long result = -1L;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {

			result = assignmentService.assignment(id, auctionDays, auctionBasePrice, details, deadline);
			if (result > 0) {
				// 添加操作日志
				operationLogService.addOperationLog("t_assignment_debt", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "修改债权转让状态", 2);
				JSONUtils.printStr("1");
			} else {
				JSONUtils.printStr("2");
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询转让中的债权列表信息初始化
	 */
	public String queryTransferByIdIndex() {
		return SUCCESS;
	}

	/**
	 * 查询转让中的债权列表信息
	 */
	@SuppressWarnings("unchecked")
	public String queryTransferById() throws SQLException, DataException {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String publisher = SqlInfusion.FilteSqlInfusion(paramMap.get("publisher"));
		assignmentService.queryTransferById(pageBean, userName, publisher);
		List<Map<String, Object>> list = pageBean.getPage();
		Date nowDate = new Date();
		if (list != null) {
			for (Map<String, Object> map : list) {
				Date date = (Date) map.get("auctionEndTime");
				String auctionEndTime = DateUtil.remainDateToString(nowDate, date);
				map.put("auctionEndTime", auctionEndTime);
			}
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}

	/**
	 * 更新转让状态--撤回
	 */
	public String udadateDebtStatus() throws SQLException, IOException, DataException {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		String debtStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("debtStatus"));
		String investId = SqlInfusion.FilteSqlInfusion(paramMap.get("investId"));
		String insertName = SqlInfusion.FilteSqlInfusion(paramMap.get("insertName"));
		String alienatorId = SqlInfusion.FilteSqlInfusion(paramMap.get("alienatorId"));// 转让人ID
		// String auctionerId = paramMap.get("auctionerId");//认购者ID
		// String auctionBasePrice = paramMap.get("auctionBasePrice"); //
		// 转让金额,必须满额才转让
		long result = 0;
		try {
			if (null == insertName && debtStatus == "3" && debtStatus.equals("3")) {
				JSONUtils.printStr("3");
				return null;
			}
			// result = assignmentService.udadateDebtStatus(id, debtStatus,
			// investId,alienatorId,auctionerId,auctionBasePrice);
			result = assignmentService.udadateDebtStatus(id, debtStatus, alienatorId, investId);
			if (result > 0) {
				JSONUtils.printStr("1");
			} else {
				JSONUtils.printStr("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算拆分金额
	 */
	public String queryDebtSumById() throws SQLException {
		String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id"));
		double number = Double.valueOf(request().getParameter("number"));
		double debtSum = 0;
		Map<String, String> map = new HashMap<String, String>();
		DecimalFormat formater = new DecimalFormat("#0.##");
		try {
			map = assignmentService.queryAssignmentById(pageBean, id);
			debtSum = Double.valueOf(map.get("realAmount"));
			if (debtSum > 0) {
				debtSum = debtSum / number;
				JSONUtils.printStr(String.valueOf((formater.format(debtSum))));
			} else {
				JSONUtils.printStr("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据用户输入的拆分金额计算是否超过债权总额
	 */
	public String queryRealAmount() {
		String id = request().getParameter("id");
		double realAmount = Double.valueOf(request().getParameter("realAmount"));
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = assignmentService.queryDebtSumById(id);
			if (realAmount < Double.valueOf(map.get("realAmount"))) {
				JSONUtils.printStr("1");
			} else {
				JSONUtils.printStr("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拆分债权--根据份数插入批量插入新的拆分债权
	 * 
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String assignmentSplit() throws SQLException {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		Integer number = Integer.valueOf(paramMap.get("number"));
		double realAmount = Double.valueOf(paramMap.get("realAmount"));
		String split = paramMap.get("split");
		long result = 0;
		try {
			result = assignmentService.insertAssignmentDebt(id, number, realAmount, split);
			if (result > 0) {
				JSONUtils.printStr("1");
			} else {
				JSONUtils.printStr("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setAssignmentService(AssignmentDebtService assignmentService) {
		this.assignmentService = assignmentService;
	}

	/**
	 * 还款中的债权初始化
	 * 
	 * @return
	 */
	public String queryReturnDebtIndex() {
		return SUCCESS;
	}

	/**
	 * 还款中的债权
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryReturnDebtList() throws Exception {
		long id = Convert.strToLong(paramMap.get("id"), -1);
		String number = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("number")), null);
		String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("startTime")), null);
		String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("endTime")), null);
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("title")), null);
		Integer borrowType = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("borrowType")), -1);
		String userGroup = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userGroup")), null);

		try {
			assignmentService.queryReturnedDebtList(pageBean, id,number, startTime, endTime, title, borrowType, userGroup);
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

	
//	/**
//	 * 还款中的债权 根据ID查询详情
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String queryRepaymentAssignmentById() throws Exception {
//		long borrowId = Convert.strToLong(request("borrowId"), -1);
//		if (borrowId == -1)
//			return INPUT;
//		try {
//			assignmentService.queryRepaymentAssignmentById(pageBean, borrowId);
//			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
//			request().setAttribute("pageNum", pageNum);
//			request().setAttribute("totalNum", pageBean.getTotalNum());
//		} catch (Exception e) {
//			log.error(e);
//			e.printStackTrace();
//			throw e;
//		}
//		return SUCCESS;
//	}
	
	/**
	 * 还款中的债权 根据ID查询详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryRepaymentAssignmentById() throws Exception {
		long borrowId = Convert.strToLong(request("invest_id"), -1);
		if (borrowId == -1)
			return INPUT;
		try {
			pageBean.setPageSize(1000000);
			assignmentService.queryRepaymentAssignmentById(pageBean, borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	

	/**
	 * 审核债权初始化
	 * 
	 * @return
	 */
	public String queryAuditDebtInit() {
		return SUCCESS;
	}

	/**
	 * 审核中的债权
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAuditDebt() throws Exception {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String publisher = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("publisher")), null);
		try {
			assignmentService.queryAuditDebt(pageBean, userName, publisher, 0);
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

	@SuppressWarnings("unchecked")
	public String showAuditDebt() throws Exception {
		long id = Convert.strToLong(request("id"), 0L);
		assignmentService.queryAuditDebt(pageBean, null, null, id);
		Map<String, Object> map = (Map<String, Object>) pageBean.getPage().get(0);
		request().setAttribute("paramMap", map);
		pageBean.setPage(null);
		assignmentService.queryRepaymentAssignmentList(pageBean, (Long) map.get("investId"), (Long) map.get("alienatorId"));
		return SUCCESS;
	}

	public String auditDebt() throws Exception {
		int status = Convert.strToInt(paramMap.get("status"), -1);
		Long id = Convert.strToLong(paramMap.get("id"), -1);
		Long investId = Convert.strToLong(paramMap.get("investId"), -1);
		String remark = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("remark")), null);
		String time = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("time")), null);
		try {
			assignmentService.updateAuditDebt(investId, id, remark, status, time);
			JSONUtils.printStr("1");
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtils.printStr("2");
		}
		return null;
	}

	/**
	 * 导出 还款中的债权
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportReturnDebtList() {
		long id = Convert.strToLong(request("id"), -1);
		String number = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("number")), null);
		String startTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("startTime")), null);
		String endTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("endTime")), null);
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("title")), null);
		Integer borrowType = Convert.strToInt(request("borrowtype"), -1);
		String userGroup = Convert.strToStr(request("usergroup"), null);
		try {
			assignmentService.queryReturnedDebtList(pageBean, id,number, startTime, endTime, title, borrowType, userGroup);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			changeFigure(pageBean);
//			HSSFWorkbook wb = ExcelUtils.exportExcel("还款中的债权", pageBean.getPage(), new String[] { "债权编号", "用户名", "姓名", "借款时间", "标的类型", "借款标题",
//					"期数/总期数", "应还时间", "应还金额" }, new String[] { "id", "username", "realName", "passTime", "borrowWay", "borrowTitle", "repayPeriod",
//					"repayDate", "stillAmount" });
			 	 	 	 	 		 	 			
			HSSFWorkbook wb = ExcelUtils.exportExcel("还款中的债权", pageBean.getPage(), new String[] { "债权编号", "借款编号", "债权人", "会员号", "借款标题",
			"金额 ", "利率", "期限","起息时间 ","到期时间 ","下一还款日" }, new String[] { "invest_number", "number", "investor" ,"id", "borrowTitle", "investAmount", "monthRate", "deadline",
			"passTime", "endTime","nextDate" });			
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_returned_debt_list", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出还款中债权记录列表", 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void changeFigure(PageBean pageBean) {
		List<Map<String, Object>> ll = pageBean.getPage();
		if (ll != null && ll.size() > 0) {// borrowType userGroup 中文显示
			for (Map<String, Object> mp : ll) {
				if (mp.get("borrowType") != null) {
					String typeId = mp.get("borrowType").toString();
					for (Map<String, Object> cc : this.getBorrowType()) {
						if (cc.get("borrowTypeId").toString().equals(typeId)) {
							mp.put("borrowType", cc.get("borrowTypeValue"));
							break;
						}
					}
				}
				if (mp.get("userGroup") != null) {
					String resultId = mp.get("userGroup").toString();
					for (Map<String, Object> cc : this.getUserGroup()) {
						if (cc.get("userGroupId").toString().equals(resultId)) {
							mp.put("userGroup", cc.get("userGroupValue"));
							break;
						}
					}
				}
			}
		}
	}

	public List<Map<String, Object>> getBorrowType() {
		if (borrowType == null) {// 1 薪金贷 2 生意贷 3 普通借款 4 实地考察借款 5 机构担保借款 6
									// 流转标
			borrowType = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("borrowTypeId", 0);
			mp.put("borrowTypeValue", "全部");
			borrowType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("borrowTypeId", 1);
			mp.put("borrowTypeValue", "薪金贷");
			borrowType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("borrowTypeId", 2);
			mp.put("borrowTypeValue", "生意贷");
			borrowType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("borrowTypeId", 3);
			mp.put("borrowTypeValue", "普通借款");
			borrowType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("borrowTypeId", 4);
			mp.put("borrowTypeValue", "实地考察借款");

			borrowType.add(mp);
			mp = new HashMap<String, Object>();
			mp.put("borrowTypeId", 5);
			mp.put("borrowTypeValue", "机构担保借款");

			borrowType.add(mp);
			mp = new HashMap<String, Object>();
			mp.put("borrowTypeId", 6);
			mp.put("borrowTypeValue", "流转标");
			borrowType.add(mp);
		}
		return borrowType;
	}

	public void setBorrowType(List<Map<String, Object>> borrowType) {
		this.borrowType = borrowType;
	}

	public List<Map<String, Object>> getUserGroup() {
		if (userGroup == null) {
			userGroup = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("userGroupId", 0);
			mp.put("userGroupValue", "全部");
			userGroup.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("userGroupId", 1);
			mp.put("userGroupValue", "用户组1");
			userGroup.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("userGroupId", 2);
			mp.put("userGroupValue", "不知道什么组");
			userGroup.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("userGroupId", 3);
			mp.put("userGroupValue", "西西组");
			userGroup.add(mp);
		}
		return userGroup;
	}

	public void setUserGroup(List<Map<String, Object>> userGroup) {
		this.userGroup = userGroup;
	}
}
