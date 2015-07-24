package com.sp2p.service.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.config.ChinaPnrConfig;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.JSONUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IFundConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.MyHomeInfoSettingDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.admin.AccountPaymentDao;
import com.sp2p.dao.admin.FIManageDao;
import com.sp2p.dao.admin.RiskManageDao;
import com.sp2p.dao.admin.UserBankManagerDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.database.Dao.Views.intentionfund_user;
import com.sp2p.entity.Admin;
import com.sp2p.service.FinanceService;
import com.sp2p.service.SelectedService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;
import com.sp2p.util.WebUtil;

public class FundManagementService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);

	private FIManageDao fiManageDao;
	private FundRecordDao fundRecordDao;
	private AccountUsersDao accountUsersDao;
	private SelectedService selectedService;
	private UserBankManagerDao userBankDao;
	private RiskManageDao riskManageDao;
	private AccountPaymentDao accountPaymentDao;
	private OperationLogDao operationLogDao;
	private MyHomeInfoSettingDao myHomeInfoSettingDao;
	private List<Map<String, Object>> rechargeTypes;
	private List<Map<String, Object>> results;

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public FIManageDao getFiManageDao() {
		return fiManageDao;
	}

	public void setFiManageDao(FIManageDao fiManageDao) {
		this.fiManageDao = fiManageDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public UserBankManagerDao getUserBankDao() {
		return userBankDao;
	}

	public void setUserBankDao(UserBankManagerDao userBankDao) {
		this.userBankDao = userBankDao;
	}

	public RiskManageDao getRiskManageDao() {
		return riskManageDao;
	}

	public void setRiskManageDao(RiskManageDao riskManageDao) {
		this.riskManageDao = riskManageDao;
	}

	public AccountPaymentDao getAccountPaymentDao() {
		return accountPaymentDao;
	}

	public void setAccountPaymentDao(AccountPaymentDao accountPaymentDao) {
		this.accountPaymentDao = accountPaymentDao;
	}

	public MyHomeInfoSettingDao getMyHomeInfoSettingDao() {
		return myHomeInfoSettingDao;
	}

	public void setMyHomeInfoSettingDao(MyHomeInfoSettingDao myHomeInfoSettingDao) {
		this.myHomeInfoSettingDao = myHomeInfoSettingDao;
	}

	public void queryRepaymentReport(PageBean<Map<String, Object>> pageBean, String userName, Long borrowId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = " and borrowId=" + borrowId;
		if (!StringUtils.isBlank(userName)) {
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		try {
			dataPage(conn, pageBean, "v_t_repay_history", " * ", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public Long updateRepayment(String repaymentId, String repayAmount, String borrowId, String hasDeadline, Long adminId, String addIP) throws Exception {
		Connection conn = MySQL.getConnection();
		conn.setAutoCommit(false);
		try {

			fiManageDao.updateRepaymentById(conn, repaymentId);
			fiManageDao.updateBorrowById(conn, borrowId, hasDeadline);
			fiManageDao.insertRepaymentRecord(conn, repaymentId, repayAmount, adminId, addIP);
			conn.commit();
			return 1L;
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 财务管理 充值记录查询
	 * 
	 * @return
	 */
	public void queryRechargeRecordList(PageBean<Map<String, Object>> pageBean, String userName, String reStartTime, String reEndTime, int rechargeType, Integer result)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		// 手机变更状态不为空
		String command = "";
		if (userName != null) {
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (rechargeType != -100) {//
			command += " and rechargeType =" + rechargeType;
		}
		if (reStartTime != null) {
			command += " and rechargeTime >='" + StringEscapeUtils.escapeSql(reStartTime) + "' and rechargeTime <='" + StringEscapeUtils.escapeSql(reEndTime) + "'";
		}
		if (result >= 0) {
			command += " and result =" + result;
		}
		try {
			dataPage(conn, pageBean, "v_t_user_rechargedetails_list", "*", " order by id desc ", command);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 投资人记录查询
	 * 
	 * @return
	 */
	public void queryRechargeInversList(PageBean<Map<String, Object>> pageBean, String userName, String borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		// 手机变更状态不为空
		String command = "";
		if (userName != null) {
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (borrowId != null) {
			command += " and borrowId = '" + StringEscapeUtils.escapeSql(borrowId) + "' ";
		}
		try {
			dataPage(conn, pageBean, "v_t_user_rechargeinvest_list", "*", " order by id desc ", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryRechargeFirstList(PageBean<Map<String, Object>> pageBean, String userName, String reStartTime, String reEndTime, int rechargeType) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		// 手机变更状态不为空
		String command = "";
		if (userName != null) {
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (rechargeType != -100) {//
			command += " and rechargeType =" + rechargeType;
		}
		if (reStartTime != null) {
			command += " and first_recharge >='" + StringEscapeUtils.escapeSql(reStartTime) + "'";
		}
		if (reEndTime != null) {
			command += " and first_recharge <='" + StringEscapeUtils.escapeSql(reEndTime) + "'";
		}
		try {
			dataPage(conn, pageBean, "v_t_user_rechargefirst_lists", "*", "", command);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryOneFirstChargeDetails(Long rechargeId, boolean first) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			if (first) {
				return fiManageDao.queryOneFirstChargeDetails(conn, rechargeId);
			} else {
				return fiManageDao.queryOneChargeDetails(conn, rechargeId);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryAllWithdrawList(PageBean<Map<String, Object>> pageBean, String userName, String startTime, String endTime, Integer status) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = "";
		if (StringUtils.isNotBlank(userName)) {
			command += " and name like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (status > 0) {//
			command += " and status =" + status;
		}
		if (StringUtils.isNotBlank(startTime)) {
			command += " and applyTime >='" + StringEscapeUtils.escapeSql(startTime) + "'";
		}
		if (StringUtils.isNotBlank(endTime)) {
			command += " and applyTime <='" + StringEscapeUtils.escapeSql(endTime) + "'";
		}
		command += " and status != 0 ";
		try {
			dataPage(conn, pageBean, "v_t_user_withdraw_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryOneWithdraw(Long wid) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return fiManageDao.queryOneWithdraw(conn, wid);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryUserCashList(PageBean<Map<String, Object>> pageBean, String userName) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = "";
		if (userName != null) {
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%'";
		}
		StringBuffer cmd = new StringBuffer();
		cmd.append("(SELECT a.id AS userId, a.username, IFNULL(f.forRePaySum, 0) AS dueoutSum, ( a.usableSum + r.rechargeMoney ) usableSum, ");
		cmd.append("a.freezeSum, ifnull(b.dueinSum,0) as dueinSum,( a.usableSum + r.rechargeMoney + ifnull(b.dueinSum,0) +a.freezeSum ) as totalSum, ifnull(b.hasInterest,0) as hasInterest, d.realName realName  FROM t_user a  ");
		cmd.append("LEFT JOIN ( SELECT investor,sum(IFNULL(hasInterest, 0)) AS hasInterest,SUM(recivedPrincipal+recievedInterest-hasPrincipal-hasInterest)"
				+ "dueinSum  FROM t_invest   GROUP BY investor  ) b ON a.id = b.investor LEFT JOIN t_person d ON d.userId = a.id  LEFT JOIN t_recharge_detail r ON r.userId = a.id  LEFT JOIN ( SELECT forRePaySum, publisher ");
		cmd.append("FROM ( SELECT sum( IFNULL(( c.stillPrincipal + c.stillInterest - c.hasPI + c.lateFI - c.hasFI ), 0 )) AS forRePaySum, d.publisher  FROM t_repayment c ");
		cmd.append(" LEFT JOIN t_borrow d ON c.borrowId = d.id WHERE c.repayStatus = 1 GROUP BY d.publisher ) t ) f ON f.publisher = a.id ");
		cmd.append(" GROUP BY a.ID, a.usableSum, a.freezeSum, f.forRePaySum, d.realName, a.username) u");
		try {
			dataPage(conn, pageBean, cmd.toString(), "*", "", command);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// public void queryUserCashList(PageBean<Map<String, Object>> pageBean,
	// String userName) throws SQLException, DataException {
	// Connection conn = connectionManager.getConnection();
	// String command = "";
	// if (userName != null) {
	// command += " and username like '%" +
	// StringEscapeUtils.escapeSql(userName.trim()) + "%'";
	// }
	// StringBuffer cmd = new StringBuffer();
	// cmd.append("select a.id as userId,a.username,IFNULL(f.forRePaySum,0) as dueoutSum,a.usableSum,a.freezeSum,a.dueinCapitalSum,a.dueinAccrualSum, ");
	// //cmd.append("round(sum(IFNULL(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest,0)),2) as dueinSum,"
	// cmd.append("d.realName realName from t_user a left join t_invest b on a.id = b.investor ");
	// cmd.append(" left join t_person d on d.userId=a.id left join ");
	// cmd.append("(select forRePaySum,publisher from (select sum(IFNULL((c.stillPrincipal+c.stillInterest-c.hasPI+c.lateFI-c.hasFI),0)) as forRePaySum,d.publisher  from t_repayment c left join t_borrow d on c.borrowId = d.id where c.repayStatus = 1 GROUP BY d.publisher) t) f");
	// cmd.append(" on f.publisher = a.id  group by a.ID,a.usableSum,a.freezeSum,f.forRePaySum,d.realName,a.username ");
	// try {
	// dataPage(conn, pageBean, cmd.toString(), "*", "", command);
	// } catch (SQLException e) {
	// log.error(e);
	// e.printStackTrace();
	// throw e;
	// } catch (DataException e) {
	// log.error(e);
	// e.printStackTrace();
	// throw e;
	// } finally {
	// conn.close();
	// }
	// }

	public void queryUserFundRepayList(PageBean<Map<String, Object>> pageBean, String username, String realName, String number, int repayStatus) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = "";
		if (!StringUtils.isBlank(username)) {
			command += " and username like '%" + username + "%' ";
		}
		if (!StringUtils.isBlank(realName)) {
			command += " and realName like '%" + realName + "%' ";
		}
		if (!StringUtils.isBlank(number)) {
			command += " and number like '%" + number + "%' ";
		}
		if (repayStatus > 0) {
			command += " and borrowStatus=" + repayStatus + " ";
		}
		try {
			dataPage(conn, pageBean, " v_t_hhn_repay_list ", "*,datediff(nextDate,preDate) days", "", command);
			List<Map<String, Object>> page = pageBean.getPage();
			if(page == null)
				return;
			Calendar a = Calendar.getInstance();
			a.set(Calendar.DATE, 1);
			a.roll(Calendar.DATE, -1);
			int days = a.get(Calendar.DATE);// 当月天数
			DecimalFormat format = new DecimalFormat("0.00");
			for (Map<String, Object> map : page) {
				Object obj = map.get("days");
				if (obj == null) {// 已还完
					map.put("preSum", "0.00");//提前还款金额
					continue;
				}
				// 剩余本金×（1+3%）+本期已产生利息+本期咨询费+手续费
				long day = Convert.strToLong(obj + "", 0);
				double balance = Convert.strToDouble(map.get("principalBalance")+"", 0);
				double interest = Convert.strToDouble(map.get("stillInterest")+"", 0);
				double preSum = 1.03 * balance + interest * day / days + Convert.strToDouble(map.get("fee")+"", 0);
				map.put("preSum", format.format(preSum));//提前还款金额
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询后台还款记录
	 * 
	 * @param borrowId
	 * @param request
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void queryPayingDetailsHHN(long borrowId, HttpServletRequest request) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			this.fiManageDao.queryBorrowneedpayByIdAdmin(conn, borrowId, request);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void queryBackCashList(PageBean<Map<String, Object>> pageBean, Long userId, String checkUser, String startTime, String endTime, Integer type, String uname)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			/*String condition = " ";
			if (userId > 0) {
				condition += " and userId=" + userId;
			}
			if (StringUtils.isNotBlank(checkUser)) {
				condition += " and checkUser like '%" + StringEscapeUtils.escapeSql(checkUser) + "%'";
			}
			if (StringUtils.isNotBlank(startTime)) {
				condition += " and checkTime >= '" + StringEscapeUtils.escapeSql(startTime) + "'";
			}
			if (StringUtils.isNotBlank(endTime)) {
				condition += " and checkTime <= '" + StringEscapeUtils.escapeSql(endTime) + "'";
			}
			if (type > 0) {
				condition += " and type = " + type;
			}
			if (StringUtils.isNotBlank(uname)) {
				condition += " and uname like '%" + StringEscapeUtils.escapeSql(uname) + "%'";
			}
			dataPage(conn, pageBean, "v_t_user_backrw_lists", "*", " ", condition);*/

            DataSet dataSet = MySQL.executeQuery(conn, "SELECT\n"
                    + "\t`a`.`id` AS `id`,\n"
                    + "\t`b`.`id` AS `userId`,\n"
                    + "\t`b`.`usrCustId` AS `usrCustId`,\n"
                    + "\t`a`.`type` AS `type`,\n"
                    + "\tifnull(`a`.`dealMoney`, '--')AS `dealMoney`,\n"
                    + "\tifnull(`a`.`remark`, '--')AS `remark`,\n"
                    + "\tifnull(`c`.`userName`, '--')AS `checkUser`,\n"
                    + "\t`a`.`checkTime` AS `checkTime`,\n"
                    + "\t`b`.`username` AS `uname`,\n"
                    + "\t`b`.`username` AS `userName`,\n"
                    + "\tifnull(`d`.`realName`, '--')AS `realName`,\n"
                    + "\t`a`.`result` AS `result`,\n"
                    + "\tifnull(`a`.`cost`, '--')AS `cost`,\n"
                    + "\tifnull(\n"
                    + "\t\t(\n"
                    + "\t\t\tSELECT\n"
                    + "\t\t\t\t`bank`.`cardNo`\n"
                    + "\t\t\tFROM\n"
                    + "\t\t\t\t`t_bankcard` `bank`\n"
                    + "\t\t\tWHERE\n"
                    + "\t\t\t\t(\n"
                    + "\t\t\t\t\t(`bank`.`userId` = `b`.`id`)\n"
                    + "\t\t\t\tAND isnull(`bank`.`modifiedCardNo`)\n"
                    + "\t\t\tAND(`bank`.`cardStatus` = 1)\n"
                    + "\t\t\t\t)\n"
                    + "\t\t\tLIMIT 1\n"
                    + "\t\t),\n"
                    + "\t\t'--'\n"
                    + "\t)AS `cardNo`\n"
                    + "FROM\n"
                    + "\t(\n"
                    + "\t\t(\n"
                    + "\t\t\t(\n"
                    + "\t\t\t\t`t_user` `b`\n"
                    + "\t\t\tLEFT JOIN `t_recharge_withdraw_info` `a` ON((`a`.`userId` = `b`.`id`))\n"
                    + "\t\t\t)\n"
                    + "\t\tLEFT JOIN `t_admin` `c` ON((`a`.`checkUser` = `c`.`id`))\n"
                    + "\t\t)\n"
                    + "\tLEFT JOIN `t_person` `d` ON((`b`.`id` = `d`.`userId`))\n"
                    + "\t) where `b`.username='"+uname+"' order by `a`.`id` desc limit 10");
            dataSet.tables.get(0).rows.genRowsMap();
            List<Map<String, Object>> list = dataSet.tables.get(0).rows.rowsMap;
            pageBean.setPage(list);
        } catch (Exception e) {
			log.error(e);
			e.printStackTrace();

		} finally {
			conn.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Long addBackR(Long userId, Long adminId, Integer type, double money, String remark, Date date, String fundMode, String addIP, String userName, String remarks)
			throws SQLException, DataException {
		Connection conn = Database.getConnection();
		Long ret = -1L;
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_useraddmoneymanual(conn, ds, outParameterValues, userId, type, money, remarks, adminId, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (ret > 0) {
				// 发送通知，通过通知模板
				Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext()
						.getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);

				Map<String, String> noticeMap = new HashMap<String, String>();
				// 消息模版
				// 站内信
				String informTemplate = informTemplateMap.get(IInformTemplateConstants.HAND_RECHARGE).toString();
				if (type == IConstants.WITHDRAW) {//
					informTemplate = informTemplateMap.get(IInformTemplateConstants.HAND_WITHDRAW).toString();
				}
				informTemplate = informTemplate.replace("date", DateUtil.dateToString((new Date())));
				informTemplate = informTemplate.replace("money", money + "");
				informTemplate = informTemplate.replace("remark", remark);
				noticeMap.put("mail", informTemplate);

				// 邮件
				String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_HAND_RECHARGE).toString();
				if (type == IConstants.WITHDRAW) {//
					e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_HAND_WITHDRAW).toString();
				}
				e_informTemplate = e_informTemplate.replace("date", DateUtil.dateToString((new Date())));
				e_informTemplate = e_informTemplate.replace("money", money + "");
				e_informTemplate = e_informTemplate.replace("remark", remark);
				noticeMap.put("email", e_informTemplate);

				// 短信
				String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_HAND_RECHARGE).toString();
				if (type == IConstants.WITHDRAW) {//
					s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_HAND_WITHDRAW).toString();
				}
				s_informTemplate = s_informTemplate.replace("userName", userName);
				s_informTemplate = s_informTemplate.replace("date", DateUtil.dateToString((new Date())));
				s_informTemplate = s_informTemplate.replace("money", money + "");
				s_informTemplate = s_informTemplate.replace("remark", remark);
				noticeMap.put("note", e_informTemplate);

				selectedService.sendNoticeMSG(conn, userId, "充值成功", noticeMap, IConstants.NOTICE_MODE_5);
				conn.commit();
			}
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public Long addBackW(Long userId, Long adminId, Integer type, double money, String remark, Date date, String fundMode, String addIP, String userName, String remarks)
			throws Exception {
		Connection conn = MySQL.getConnection();
		conn.setAutoCommit(false);
		Long result = -1L;
		try {
			// 更新用户金额
			long result2 = fiManageDao.updateFundrecord(conn, userId, money, type);
			if (result2 <= 0) {
				conn.rollback();
				return result2;
			}

			result = fiManageDao.addBackR_W(conn, userId, adminId, type, money, remark, date);
			if (result <= 0) {
				conn.rollback();
				return result;
			}

			// 查询待收金额
			Map<String, String> dueinMap = fiManageDao.queryDueInSum(conn, userId);
			double dueinSum = 0d;
			if (dueinMap != null && dueinMap.size() > 0) {
				dueinSum = Convert.strToDouble(dueinMap.get("forPI"), 0);
			}
			long result3 = -1;
			result3 = fundRecordDao.addFundRecord(conn, userId, fundMode, money, Convert.strToDouble(dueinMap.get("usableSum"), 0d),
					Convert.strToDouble(dueinMap.get("freezeSum"), 0d), dueinSum, -1L, remarks, 0.0, money, -1, -1, 502, 0.0);

			if (result3 <= 0) {
				conn.rollback();
				return result3;
			}

			// ----往新表插入数据
			if (type == IConstants.RECHARAGE) {// 充值
				int result4 = accountUsersDao.addAccountUsers(conn, IFundConstants.HAND_RECHARGE, userId, new BigDecimal(money), adminId, remark, addIP);
				if (result4 < 0) {
					conn.rollback();
					return -1L;
				}
			} else {// 扣费
				int result4 = accountUsersDao.addAccountUsers(conn, IFundConstants.HAND_WITHDRAW, userId, new BigDecimal(money), adminId, remark, addIP);
				if (result4 < 0) {
					conn.rollback();
					return -1L;
				}
			}

			// 发送通知，通过通知模板
			Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext()
					.getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);

			Map<String, String> noticeMap = new HashMap<String, String>();

			// 消息模版
			// 站内信
			String informTemplate = informTemplateMap.get(IInformTemplateConstants.HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				informTemplate = informTemplateMap.get(IInformTemplateConstants.HAND_WITHDRAW).toString();
			}
			informTemplate = informTemplate.replace("date", DateUtil.dateToString((new Date())));
			informTemplate = informTemplate.replace("money", money + "");
			informTemplate = informTemplate.replace("remark", remark);
			noticeMap.put("mail", informTemplate);

			// 邮件
			String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_HAND_WITHDRAW).toString();
			}
			e_informTemplate = e_informTemplate.replace("date", DateUtil.dateToString((new Date())));
			e_informTemplate = e_informTemplate.replace("money", money + "");
			e_informTemplate = e_informTemplate.replace("remark", remark);
			noticeMap.put("email", e_informTemplate);

			// 短信
			String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_HAND_WITHDRAW).toString();
			}
			s_informTemplate = s_informTemplate.replace("userName", userName);
			s_informTemplate = s_informTemplate.replace("date", DateUtil.dateToString((new Date())));
			s_informTemplate = s_informTemplate.replace("money", money + "");
			s_informTemplate = s_informTemplate.replace("remark", remark);
			noticeMap.put("note", e_informTemplate);

			selectedService.sendNoticeMSG(conn, userId, "扣费成功", noticeMap, IConstants.NOTICE_MODE_5);

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public Map<String, String> queryR_WInfo(Long rwId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return fiManageDao.queryR_WInfo(conn, rwId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 更新资金流动信息表
	 * 
	 * @param userId
	 * @param handleSum
	 * @param usableSum
	 * @return
	 */
	public Long updateFundrecord(long userId, double money, int type) throws SQLException {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = fiManageDao.updateFundrecord(conn, userId, money, type);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public void queryUserFundRecordList(PageBean<Map<String, Object>> pageBean, Long userId, String userName) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = " and userId=" + userId;
		if (StringUtils.isNotBlank(userName))
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%'";
		try {
			dataPage(conn, pageBean, "v_t_user_fundrecord_lists", "*", " order by id desc", command);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryUserBorrowRecordList(PageBean<Map<String, Object>> pageBean, Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			String command = " and userId=" + userId;
			dataPage(conn, pageBean, "t_user", "*", "", command);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryUserFundInfoList(PageBean<Map<String, Object>> pageBean, String userName) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer cmd = new StringBuffer();
		if (StringUtils.isNotBlank(userName))
			cmd.append(" and username like '%" + StringEscapeUtils.escapeSql(userName) + "%'");
		try {
			dataPage(conn, pageBean, "v_t_person_user", "*", "", cmd.toString());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryAllUserFundRecordList(PageBean<Map<String, Object>> pageBean, String userName) throws Exception {
		Connection conn = MySQL.getConnection();
		String command = "";
		String command2 = "";
		if (userName != null) {
			command += " and username like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'";
			command2 += " where u.username like '%"
				+ StringEscapeUtils.escapeSql(userName.trim()) + "%'";
		}
		StringBuffer cmd = new StringBuffer();
		cmd
				.append("(select a.id as userId,a.username,IFNULL(f.forRePaySum,0) as dueoutSum,a.usableSum,a.freezeSum,");
		cmd
				.append("round(sum(ifnull((b.recivedPrincipal-b.hasPrincipal),0)),2) as dueinCapitalSum,round(sum(ifnull((b.recievedInterest-b.hasInterest),0)),2) as dueinAccrualSum,round(sum(IFNULL(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest,0)),2) as dueinSum,d.realName realName from t_user a left join t_invest b on a.id = b.investor ");
		cmd.append(" left join t_person d on d.userId=a.id left join ");
		cmd
				.append("(select forRePaySum,publisher from (select sum(IFNULL((c.stillPrincipal+c.stillInterest-c.hasPI+c.lateFI-c.hasFI),0)) as forRePaySum,d.publisher  from t_repayment c left join t_borrow d on c.borrowId = d.id where c.repayStatus = 1 GROUP BY d.publisher) t) f");
		cmd
				.append(" on f.publisher = a.id  group by a.ID,a.usableSum,a.freezeSum,f.forRePaySum,d.realName,a.username) u");
		try {
			dataPage(conn, pageBean, cmd.toString(), "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 通过ID查询单个用户所有借款信息
	 * 
	 * @param pageBean
	 * @param userName
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserFundBorrowList(PageBean<Map<String, Object>> pageBean, Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String cmd = "";
		if (userId > 0)
			cmd = " and publisherId=" + userId;
		try {
			dataPage(conn, pageBean, "v_t_borrow_invest_list_borrow", "*", " order by id  ", cmd);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 通过ID 查询该用户所有投资信息
	 * 
	 * @param pageBean
	 * @param userName
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserFundInvestList(PageBean<Map<String, Object>> pageBean, Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String cmd = "";
		if (userId > 0)
			cmd = " and investorId=" + userId;
		try {
			dataPage(conn, pageBean, "v_t_borrow_invest_list_invest", "*", " order by id  ", cmd);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 后台 提现列表
	 * 
	 * @param pageBean
	 * @param userName
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserFundWithdrawInfo(PageBean<Map<String, Object>> pageBean, String userName, String startTime, String endTime, double sum, int status, long userId)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		// 手机变更状态为空
		String command = " and userId=" + userId;
		if (userName != null) {
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (sum > 0) {
			command += " and sum =" + sum;
		}
		if (startTime != null) {
			command += " and applyTime >='" + StringEscapeUtils.escapeSql(startTime) + "' and applyTime <='" + StringEscapeUtils.escapeSql(endTime) + "'";
		}
		if (status > 0) {// (1 默认审核中 2 已提现 3 取消 4转账中 5 失败)
			command += " and status =" + status;
		}
		try {
			dataPage(conn, pageBean, "v_t_user_fundwithdraw_lists", "*", "", command);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryUserFundRechargeInfo(PageBean<Map<String, Object>> pageBean, String startTime, String endTime, int status, long userId, int rechargeType) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		String command = " and userId=" + userId;
		if (!StringUtils.isBlank(startTime)) {
			command += " and rechargeTime >='" + StringEscapeUtils.escapeSql(startTime) + "'";
		}
		if (!StringUtils.isBlank(endTime)) {
			command += " and rechargeTime <='" + StringEscapeUtils.escapeSql(endTime) + "'";
		}
		if (status >= 0) {// (0 失败 1 成功)
			command += " and result =" + status;
		}
		if (rechargeType > 0) {//
			command += " and rechargeType =" + rechargeType;
		}
		try {
			dataPage(conn, pageBean, "v_t_user_rechargeall_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public void queryUserFundRechargeInfoById(PageBean<Map<String, Object>> pageBean, String startTime, String endTime, int status, long userId, int rechargeType)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = " and userId=" + userId;
		if (startTime != null) {
			command += " and rechargeTime >='" + StringEscapeUtils.escapeSql(startTime) + "' and rechargeTime <='" + StringEscapeUtils.escapeSql(endTime) + "'";
		}
		if (status >= 0) // (0 失败 1 成功)
			command += " and result =" + status;
		if (rechargeType > 0) // (1 失败 2 成功)
			command += " and type =" + rechargeType;
		try {
			dataPage(conn, pageBean, "v_t_user_rechargedetails_list", "*", "", command);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryUserFundBorrowById(PageBean<Map<String, Object>> pageBean, long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = " and userId=" + userId;
		try {
			dataPage(conn, pageBean, "v_t_user_rechargedetails_list", "*", "", command);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryUserFundRechargeInfoByRechargeNumber(PageBean<Map<String, Object>> pageBean, String rechargeNumber) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = " and rechargeNumber='" + rechargeNumber + "'";
		try {
			dataPage(conn, pageBean, "v_t_user_rechargedetails_list", "*", "", command);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 获得充值提现审核中的审核信息
	 * 
	 * @param userId
	 * @param money
	 * @param type
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserRWInfo(long userId, int rs, int ws) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();

		try {
			Map<String, String> map = fiManageDao.querySuccessRecharge(conn, userId, rs);// 充值总额
			Map<String, String> mp = fiManageDao.querySuccessBid(conn, userId);// 投标成功总额
			Map<String, String> _map = fiManageDao.querySuccessWithdraw(conn, userId, ws);// 2个月内的提现
			Map<String, String> mm = fiManageDao.queryReturnMoney(conn, userId);// 2个月的收款
			if (_map == null || _map.size() <= 0) {
				map = new HashMap<String, String>();
				map.put("w_total", "0.00");
			}

			if (map == null || map.size() <= 0) {
				_map.put("r_total", "0.00");

			} else {
				_map.put("r_total", map.get("r_total"));
			}
			if (mm == null || mm.size() <= 0) {
				_map.put("retSum", "0.00");

			} else {
				_map.put("retSum", mm.get("retSum"));
			}
			if (mp == null || mp.size() <= 0) {
				_map.put("real_Amount", "0.00");

			} else {
				_map.put("real_Amount", mp.get("realAmount"));
			}
			conn.commit();
			return _map;
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 添加数据到资金记录表（转账成功的情况下）
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Long addFundRecord(Long userId, Map<String, String> map) throws SQLException {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = fundRecordDao.addFundRecord(conn, userId, map.get("fundMode"), Convert.strToDouble(map.get("handleSum"), 0d), Convert.strToDouble(map.get("usableSum"), 0d),
					Convert.strToDouble(map.get("freezeSum"), 0d), Convert.strToDouble(map.get("dueinSum"), 0d), -1, map.get("remark"), 0.0,
					Convert.strToDouble(map.get("handleSum"), 0d), -1, -1, 801, Convert.strToDouble(map.get("dueOutSum"), 0d));
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public Map<String, String> queryTransStatus(Long wid) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return fiManageDao.queryTransStatus(conn, wid);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> updateWithdraw(Long wid, double money, double poundage, int status, String remark, long adminId, Long userId, String ipAddress, String trxId)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_amount_withdraw_auth(conn, ds, outParameterValues, userId, adminId, wid, new BigDecimal(money), new BigDecimal(poundage), status, ipAddress, remark, "",
					-1, "");
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", outParameterValues.get(0) + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (result <= 0) {
				conn.rollback();
				return map;
			}
			if (status == 5) {// 拒绝提现, 解冻用户金额
				JSONObject json = JSONObject.fromObject(ChinaPnRInterface.usrUnFreeze(wid + "", trxId));
				if (json.getInt("RespCode") != 0) {// 失败
					map.put("ret", "-10010");
					map.put("ret_desc", "解冻失败:" + json.getString("RespDesc"));
					conn.rollback();
					return map;
				}
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
			conn = null;
			ds = null;
			outParameterValues = null;
		}
		return map;
	}

	/**
	 * 查询待收金额
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryDueInSum(Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return fiManageDao.queryDueInSum(conn, userId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> queryLastRecord() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return fiManageDao.queryLastRecord(conn);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public String updateWithdrawTransfer(Long wid, Integer status, Long adminId, String ipAddress, String trxId, String transAmt, String usrCustId, String auditFlag)
			throws Exception {
		// 解冻
		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.usrUnFreeze(wid + "", trxId));
		if (json.getInt("RespCode") != 0) {
			return "解冻失败:" + json.getString("RespDesc");
		}

		// 提现
		json = JSONObject.fromObject(ChinaPnRInterface.cashAudit(wid + "", usrCustId, transAmt, auditFlag));
		if (json.getInt("RespCode") != 0) {
			return "提现失败:" + json.getString("RespDesc");
		}

		Connection conn = MySQL.getConnection();
		Long result = -1L;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		String realSum = json.getString("TransAmt");
		String feeAmt = json.getString("FeeAmt");
		try {
			Procedures.p_amount_withdraw_transfer(conn, ds, outParameterValues, adminId, wid, status, ipAddress, DateUtil.dateToYMD(new Date()), realSum, feeAmt, -1, "");
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (result <= 0) {
				conn.rollback();
				return outParameterValues.get(1) + "";
			}
			// 提现成功，更新当期提现金额为0.00
			// fiManageDao.updateWithdrawMoney(conn,wid);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
			conn = null;
			ds = null;
		}
		return outParameterValues.get(1) + "";
	}

	// userBank
	/**
	 * 查询银行卡信息
	 * 
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @param checkUser
	 * @param moStartTime
	 * @param moEndTime
	 * @param checkStartTime
	 * @param checkEndTime
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryBankCardInfos(PageBean<Map<String, Object>> pageBean, String userName, String realName, long checkUser, String commitStartTime, String commitEndTime,
			int cardStatus) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = " ";
		if (StringUtils.isNotBlank(userName))
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";

		if (StringUtils.isNotBlank(realName))
			command += " and realName like '%" + StringEscapeUtils.escapeSql(realName) + "%'";

		if (checkUser != -100) // admin的用户id目前为 -1
			command += " and checkUser =" + checkUser;

		if (StringUtils.isNotBlank(commitStartTime))
			command += " and commitTime >='" + StringEscapeUtils.escapeSql(commitStartTime) + "'";

		if (StringUtils.isNotBlank(commitEndTime))
			command += " and commitTime <='" + StringEscapeUtils.escapeSql(commitEndTime) + "'";

		if (cardStatus > 0)
			command += " and cardStatus=" + cardStatus;

		try {
			dataPage(conn, pageBean, "t_bankcard_lists", "*", "", command);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询修改银行卡信息
	 * 
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @param checkUser
	 * @param cStartTime
	 * @param cEndTime
	 * @param cardStatus
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryModifyBankCardInfos(PageBean<Map<String, Object>> pageBean, String userName, String realName, long checkUser, String cStartTime, String cEndTime,
			int cardStatus) throws Exception {
		Connection conn = connectionManager.getConnection();
		// 手机变更状态不为空
		String command = " and modifiedCardStatus is not null";
		if (userName != null) {
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (realName != null) {
			command += " and realName like '%" + StringEscapeUtils.escapeSql(realName) + "%'";
		}
		if (checkUser != -100) {// admin的用户id目前为 -1
			command += " and checkUser =" + checkUser;
		}
		if (cStartTime != null) {
			command += " and modifiedTime >='" + StringEscapeUtils.escapeSql(cStartTime) + "'";
		}
		if (cEndTime != null) {
			command += " and modifiedTime <='" + StringEscapeUtils.escapeSql(cEndTime) + "'";
		}
		if (cardStatus > -1) {
			command += " and modifiedCardStatus = " + cardStatus;
		}
		try {
			dataPage(conn, pageBean, "v_t_bankcard_update_list", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryOneBankCardInfo(Long bankId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return userBankDao.queryBankCardUpdate(conn, bankId, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 新增银行卡--审核查询
	 * 
	 * @param bankId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryBankCard(Long bankId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return userBankDao.queryBankCard(conn, bankId, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 审核 银行卡
	 * 
	 * @param checkUserId
	 * @param bankId
	 * @param remark
	 * @param result
	 * @param username
	 * @param lastIP
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateBankInfo(Long checkUserId, Long bankId, String remark, Integer status, String username, String lastIP) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		long resultId = -1L;
		try {

			resultId = userBankDao.updateBankInfo(conn, checkUserId, bankId, remark, status);
			if (resultId > 0) {
				operationLogDao.addOperationLog(conn, "t_bankcard", username, IConstants.UPDATE, lastIP, 0, "银行卡审核", 2);
			}

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return resultId;
	}

	public Long updateModifyBankInfo(Long checkUserId, Long bankId, String remark, Integer result, String bankName, String branchBankName, String bankCardNo, String date,
			boolean success) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {

			return userBankDao.updateModifyBankInfo(conn, checkUserId, bankId, remark, result, bankName, branchBankName, bankCardNo, date, success);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryOneBank(Long bankId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {

			return userBankDao.queryOneBankInfo(conn, bankId, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Long updateChangeBank(Long bankId, String bankName, String modifiedOpenBankId, String mSubBankName, String province, String city, String bankCard, int status,
			Date date, boolean modify, String provinceId, String cityId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return userBankDao.updateChangeBankInfo(conn, bankId, bankName, modifiedOpenBankId, mSubBankName, province, city, bankCard, status, date, modify, provinceId, cityId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// riskManage 风险保证金
	/**
	 * @MethodName: queryRiskByCondition
	 * @Param: RiskManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-7 上午11:22:29
	 * @Return:
	 * @Descb: 查询风险保障金列表
	 * @Throws:
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void queryRiskByCondition(String resource, String timeStart, String timeEnd, String riskType, PageBean pageBean) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(resource)) {
			// condition.append(" and resource ='"+StringEscapeUtils.escapeSql(resource.trim())+"'");
			condition.append(" and resource  like '%" + StringEscapeUtils.escapeSql(resource.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(riskType)) {
			condition.append(" and riskType ='" + StringEscapeUtils.escapeSql(riskType.trim()) + "'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and riskDate >='" + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and riskDate <='" + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		try {
			dataPage(conn, pageBean, "v_t_risk_list_h", "*", " ", condition.toString());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryRiskDetailById
	 * @Param: RechargeService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午11:10:26
	 * @Return:
	 * @Descb: 查询风险保证金详情
	 * @Throws:
	 */
	public Map<String, String> queryRiskDetailById(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = riskManageDao.queryRiskDetailById(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: addRisk
	 * @Param: RiskManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午03:03:45
	 * @Return:
	 * @Descb: 手动添加风险保障金
	 * @Throws:
	 */
	public long addRisk(double amountDouble, long adminId, String remark) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();

		long result = -1L;
		try {
			Map<String, String> map = riskManageDao.queryRiskBalance(conn);
			String riskBalance = map.get("riskBalance");
			double riskBalanceDouble = 1000000 + Convert.strToDouble(riskBalance, 0);
			Date riskDate = new Date();
			String riskType = "收入";
			String resource = "手动添加风险保障金";
			result = riskManageDao.addRisk(conn, amountDouble, adminId, remark, riskBalanceDouble, riskDate, riskType, resource);
			if (result <= 0) {
				conn.rollback();
				return -1;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * @MethodName: deductedRisk
	 * @Param: RiskManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午03:03:33
	 * @Return:
	 * @Descb: 手动扣除风险保障金
	 * @Throws:
	 */
	public long deductedRisk(double amountDouble, long adminId, String remark) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();

		long result = -1L;
		try {
			Map<String, String> map = riskManageDao.queryRiskBalance(conn);
			String riskBalance = map.get("riskBalance");
			double riskBalanceDouble = 1000000 + Convert.strToDouble(riskBalance, 0);
			Date riskDate = new Date();
			String riskType = "支出";
			String resource = "手动扣除风险保障金";
			result = riskManageDao.deductedRisk(conn, amountDouble, adminId, remark, riskBalanceDouble, riskDate, riskType, resource);
			if (result <= 0) {
				conn.rollback();
				return -1;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	// AccountPayment 支付方式

	/**
	 * 增加支付方式
	 * 
	 * @param conn
	 * @param name
	 * @param nid
	 * @param status
	 * @param litpic
	 * @param style
	 * @param config
	 * @param description
	 * @param order
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public long addAccountPayment(String name, String nid, long status, String litpic, int style, String config, String description, int order) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = accountPaymentDao.addAccountPayment(conn, name, nid, status, litpic, style, config, description, order);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return result;

	}

	/**
	 * 分页查询所有
	 * 
	 * @param conn
	 * @param pageBean
	 * @throws DataException
	 * @throws SQLException
	 */
	public void queryAccountPaymentPage(PageBean<Map<String, Object>> pageBean) throws DataException, SQLException {
		Connection conn = MySQL.getConnection();
		try {
			accountPaymentDao.queryAccountPaymentPage(conn, pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 修改
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @param nid
	 * @param status
	 * @param litpic
	 * @param style
	 * @param config
	 * @param description
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public long updateAccountPaymentPage(long id, String name, String litpic, String config, String description, int order) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = accountPaymentDao.updateAccountPaymentPage(conn, id, name, litpic, config, description, order);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public long deleteAccountPaymentPage(long id, long status) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = accountPaymentDao.deleteAccountPaymentPage(conn, id, status);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 根据ID 查询
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryAccountPaymentById(String nid) throws DataException, SQLException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = accountPaymentDao.queryAccountPaymentById(conn, nid);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询所有支付信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAccountPaymentList() throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			mapList = accountPaymentDao.queryAccountPaymentList(conn);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return mapList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void changeFigure(PageBean pageBean) {
		List<Map<String, Object>> ll = pageBean.getPage();
		if (ll != null && ll.size() > 0) {// result rechargeType 中文显示
			for (Map<String, Object> mp : ll) {
				if (mp.get("rechargeType") != null) {
					String typeId = mp.get("rechargeType").toString();
					for (Map<String, Object> cc : this.getRechargeTypes()) {
						if (cc.get("typeId").toString().equals(typeId)) {
							mp.put("rechargeType", cc.get("typeValue"));
							break;
						}
					}
				}
				if (mp.get("result") != null) {
					String resultId = mp.get("result").toString();
					for (Map<String, Object> cc : this.getResults()) {
						if (cc.get("resultId").toString().equals(resultId)) {
							if (resultId.equals(0 + "")) {// 失败
								mp.put("realMoney", "0.00");
							}
							mp.put("result", cc.get("resultValue"));
							break;
						}
					}
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void changeFigure2(PageBean pageBean) {
		List<Map<String, Object>> list = pageBean.getPage();
		if (list != null) {
			for (Map<String, Object> map : list) {
				map.put("type", "手动充值");
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void changeTraderName(PageBean pageBean) {
		List<Map<String, Object>> lists = pageBean.getPage();
		if (lists != null) {
			for (Map<String, Object> map : lists) {
				// 从后台管理员表中查询用户信息
				if (map.get("traderName") == null) {
					map.put("traderName", IConstants.OPERATOR_ONLINE);
				}
			}
		}
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
			mp.put("typeId", 6);
			mp.put("typeValue", "线下充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 51);
			mp.put("typeValue", "手工充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 52);
			mp.put("typeValue", "虚拟充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 53);
			mp.put("typeValue", "奖励充值");
			rechargeTypes.add(mp);

		}
		return rechargeTypes;
	}

	public void setRechargeTypes(List<Map<String, Object>> rechargeTypes) {
		this.rechargeTypes = rechargeTypes;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void queryRechargeAndFeeList(PageBean pageBean, String userName) throws Exception {
		Connection conn = connectionManager.getConnection();
		String command = "";
		if (userName.length() > 0)
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";

		try {
			dataPage(conn, pageBean, "v_t_user_rechargeall_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 合和年 投资曲线收益 根据ID查询投资数据
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String queryInvestForYear(long userid) throws Exception {
		StringBuilder data = new StringBuilder();
		Connection conn = null;
		try {
			Map<Integer, Object> map = new HashMap<Integer, Object>();
			conn = MySQL.getConnection();
			List<Map<String, Object>> list = fiManageDao.queryInvestForYear(conn, userid);
			// 转换为map<月,收益>形式
			if (list != null) {
				for (Map<String, Object> m : list)
					map.put(Integer.parseInt(m.get("months").toString()), m.get("amount"));
				for (int i = 1; i <= 12; i++) {
					if (map.get(i) == null)
						data.append(0 + ",");
					else
						data.append(map.get(i) + ",");
				}
				data.deleteCharAt(data.length() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
		return data.toString().trim();
	}

	/**
	 * 合和年 投资曲线收益 根据ID查询收益数据
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String queryIncomeForYear(long userid) throws Exception {
		StringBuilder data = new StringBuilder();
		Connection conn = null;
		try {
			Map<Integer, Object> map = new HashMap<Integer, Object>();
			conn = MySQL.getConnection();
			List<Map<String, Object>> list = fiManageDao.queryIncomeForYear(conn, userid);
			// 转换为map<月,收益>形式
			if (list != null) {
				for (Map<String, Object> m : list)
					map.put(Integer.parseInt(m.get("months").toString()), m.get("amount"));
				for (int i = 1; i <= 12; i++) {
					if (map.get(i) == null)
						data.append(0 + ",");
					else
						data.append(map.get(i) + ",");
				}
				data.deleteCharAt(data.length() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
		return data.toString().trim();
	}

	public long addBankRecharge(long userId, Long adminId, double dealMoney, String remark, Date date, String usrCustId) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = fiManageDao.addBackR_W(conn, userId, adminId, 1, dealMoney, remark, date);
			// 汇付自动扣款转账(会员奖励)
			if (result <= 0) {
				conn.rollback();
				return result;
			}
			String transAmt = new DecimalFormat("0.00").format(dealMoney);
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface
					.transfer(result + "", ChinaPnrConfig.chinapnr_merCustId, ChinaPnrConfig.chinapnr_cfb, transAmt, usrCustId, ""));
			if (json.getInt("RespCode") != 0) {
				JSONUtils.printStr2("失败:" + json.getString("RespDesc"));
				conn.rollback();
				return -1L;
			}
			fiManageDao.addFundRecode(conn, userId + "", "会员奖励", dealMoney, new Date(), "会员奖励金额[" + transAmt + "]元,备注[" + remark + "]", 0, dealMoney, 0);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 商户无卡代扣
	 */
	public long addBankUserRecharge(long userId, Long adminId, double dealMoney, String remark, Date date, String usrCustId) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			Map<String, String> acctIdMap = myHomeInfoSettingDao.getCardNo(conn, userId, -1, -1);
			if (acctIdMap == null) {
				JSONUtils.printStr2("该用户还未绑定银行卡，不能充值");
				conn.rollback();
				return result;
			}
			String openAcctId = (String) acctIdMap.get("cardNo");
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSS");
			String reTime = format.format(date);
			result = fiManageDao.addBackR_W(conn, userId, adminId, 3, dealMoney, remark, date);
			if (result <= 0) {
				conn.rollback();
				return result;
			}
			String transAmt = new DecimalFormat("0.00").format(dealMoney);
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.posWhSave(usrCustId, openAcctId, transAmt, reTime, UtilDate.getDate(), UtilDate.getDate()));
			if (json.getInt("RespCode") != 0) {
				JSONUtils.printStr2("失败:" + json.getString("RespDesc"));
				conn.rollback();
				return -1L;
			}
			fiManageDao.addFundRecode(conn, userId + "", "商户无卡代扣", dealMoney, new Date(), "商户无卡代扣金额[" + transAmt + "]元,备注[" + remark + "]", 0, dealMoney, 0);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Long updateBackRecharge(Long userId, Long adminId, Integer type, double money, String remark, Date date, String fundMode, String addIP, String userName, String remarks)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			// 更新用户金额
			result = fiManageDao.updateFundrecord(conn, userId, money, type);

			// 发送通知，通过通知模板
			Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext()
					.getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
			Map<String, String> noticeMap = new HashMap<String, String>();

			// 消息模版
			// 站内信
			String informTemplate = informTemplateMap.get(IInformTemplateConstants.HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				informTemplate = informTemplateMap.get(IInformTemplateConstants.HAND_WITHDRAW).toString();
			}
			informTemplate = informTemplate.replace("date", DateUtil.dateToString((new Date())));
			informTemplate = informTemplate.replace("money", money + "");
			informTemplate = informTemplate.replace("remark", remark);
			noticeMap.put("mail", informTemplate);

			// 邮件
			String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_HAND_WITHDRAW).toString();
			}
			e_informTemplate = e_informTemplate.replace("date", DateUtil.dateToString((new Date())));
			e_informTemplate = e_informTemplate.replace("money", money + "");
			e_informTemplate = e_informTemplate.replace("remark", remark);
			noticeMap.put("email", e_informTemplate);

			// 短信
			String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_HAND_WITHDRAW).toString();
			}
			s_informTemplate = s_informTemplate.replace("userName", userName);
			s_informTemplate = s_informTemplate.replace("date", DateUtil.dateToString((new Date())));
			s_informTemplate = s_informTemplate.replace("money", money + "");
			s_informTemplate = s_informTemplate.replace("remark", remark);
			noticeMap.put("note", e_informTemplate);

			selectedService.sendNoticeMSG(conn, userId, "扣费成功", noticeMap, IConstants.NOTICE_MODE_5);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	public long deleteRechargeRecode(long ordId) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			fiManageDao.deleteRechargeRecode(conn, ordId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return -1L;
	}

	@SuppressWarnings("unchecked")
	public void queryProxyWithList(PageBean pageBean, String username, String startTime, String endTime, int status) throws SQLException {
		Connection conn = connectionManager.getConnection();
		String command = "";
		if (StringUtils.isNotBlank(username)) {
			command += " and username like '%" + StringEscapeUtils.escapeSql(username) + "%' ";
		}
		if (status > 0) {
			command += " and status =" + status;
		}
		if (StringUtils.isNotBlank(startTime)) {
			command += " and applyTime >='" + StringEscapeUtils.escapeSql(startTime) + "'";
		}
		if (StringUtils.isNotBlank(endTime)) {
			command += " and applyTime <='" + StringEscapeUtils.escapeSql(endTime) + "'";
		}
		try {
			dataPage(conn, pageBean, "v_t_mer_cash_hhn", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public String addMerCash(double transAmt, String userId, Long adminId, String usrCustId, String username) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			long ordId = fiManageDao.addMerCash(conn, userId, transAmt, adminId);
			if (ordId < 0) {
				conn.rollback();
				return "操作失败";
			}

			String amtStr = new DecimalFormat("0.00").format(transAmt);
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.merCash(ordId + "", usrCustId, amtStr, ""));
			if (json.getInt("RespCode") != 0) {
				conn.rollback();
				return "失败:" + json.getString("RespDesc");
			}
			fiManageDao.updateMerCash(conn, ordId, json.getString("OpenAcctId"), json.getString("FeeAmt"));
			// 添加提现记录
			fiManageDao.addWithdraw(conn, username, json.getString("OpenAcctId"), json.getString("TransAmt"), json.getString("FeeAmt"), UtilDate.getDateFormatter(),
					json.getString("OpenAcctId"), userId);
			// 添加资金流水记录
			fiManageDao.addFundRecode(conn, userId, "平台取现", transAmt, new Date(), "平台取现金额[" + amtStr + "]元,手续费[" + json.getString("FeeAmt") + "]元",
					Convert.strToDouble(json.getString("FeeAmt"), 0), 0, transAmt + json.getDouble("FeeAmt"));
			conn.commit();
			return "取现成功";
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			return "操作失败";
		} finally {
			conn.close();
		}
	}

	@SuppressWarnings("unchecked")
	public String preRepayment(long borrowId, String outCustId, long userId, long payId, Admin admin) throws Exception {
		Connection conn = MySQL.getConnection();
		// 投资人信息
		String sql = "SELECT ordId,inCustId,subOrdDate,subOrdId,sum(repayFees) repayFee,sum(consultFees) consultFee,sum(interests) interest,principal ";
		sql += " FROM (SELECT ordId,inCustId,subOrdDate,subOrdId, repayFee,outCustId,IF (payId = " + payId + ", repayFee, 0)  repayFees,";
		sql += " IF (payId = " + payId + ",consultFee,0) consultFees,IF (payId = " + payId + ", interest, 0) interests, SUM(principal) principal ";
		sql += " FROM v_t_chianpnr_repayhhn WHERE payId >= " + payId + " AND borrowId = " + borrowId + " GROUP BY subOrdId ) T GROUP BY T.subOrdId ";
		// 借款人信息
		String sql2 = "select r.principalBalance,r.consultFee,r.repayFee from t_repayment r where repayStatus=1 and borrowId=" + borrowId + " order by id limit 1";
		List<Map<String, Object>> list = null;
		Map<String, String> bMap = null;
		Date lastDate = null;
		try {
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			dataSet.tables.get(0).rows.genRowsMap();
			list = dataSet.tables.get(0).rows.rowsMap;
			dataSet = MySQL.executeQuery(conn, sql2);
			bMap = BeanMapUtils.dataSetToMap(dataSet);
			sql = "select ifnull(max(repayDate),b.auditTime) lastDay from t_borrow b left join t_repayment r on r.borrowId=b.id where r.repayStatus=2 and r.borrowId=" + borrowId;
			String lastDay = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, sql)).get("lastDay");
			lastDate = DateUtil.YYYY_MM_DD_MM_HH_SS.parse(lastDay);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (list == null || list.size() == 0) {
			conn.close();
			return "没有找到投资人信息";
		}
		if (bMap == null || bMap.size() == 0) {
			conn.close();
			return "没有找到借款人信息";
		}

		DecimalFormat df = new DecimalFormat("0.00");
		String Fee = "0.00";
		String divDetails = "";
		String inAcctId = "";
		String outAcctId = "";

		JSONObject obj = new JSONObject();
		// 手续费,提前还款额外收取 剩余本金*3%手续费
		double fee = Convert.strToDouble(bMap.get("repayFee"), 0) + Convert.strToDouble(bMap.get("principalBalance"), 0) * 0.03;
		fee = Double.valueOf(df.format(fee));
		double consultFee = Convert.strToDouble(bMap.get("consultFee"), 0);// 咨询费
		double repaySum = Convert.strToDouble(bMap.get("principalBalance"), 0) + fee + consultFee;// 还款总额
		String AvlBalString = JSONObject.fromObject(ChinaPnRInterface.queryBalanceBg(outCustId)).getString("AvlBal").replaceAll(",", "");
		if (repaySum > Double.valueOf(AvlBalString)) {
			conn.close();
			return "借款人余额不足";
		}
		long day = Math.abs(DateUtil.diffDays(lastDate, new Date()));
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int days = a.get(Calendar.DATE);// 当月天数
		double rate = (1.0 * day) / days;
		for (int i = 0, size = list.size(); i < size; i++) {
			Map<String, Object> map = list.get(i);
			String transAmt = df.format(Convert.strToDouble(map.get("principal") + "", 0) + Convert.strToDouble(map.get("interest") + "", 0) * rate);
			String ordId = map.get("ordId") + "";
			String inCustId = map.get("inCustId") + "";
			String subOrdId = map.get("subOrdId") + "";
			String subOrdDate = DateUtil.dateToYMD(DateUtil.strToDate(map.get("subOrdDate") + ""));

			if (i == size - 1) {// 在最后一次还款时计算手续费和咨询费
				Fee = df.format(fee + consultFee);// 总手续费
				JSONObject json1 = new JSONObject();
				json1.put("DivAcctId", ChinaPnrConfig.chinapnr_zxf);
				json1.put("DivAmt", df.format(consultFee));
				divDetails = "[" + json1.toString();
				if (fee > 0) {
					json1 = new JSONObject();
					json1.put("DivAcctId", ChinaPnrConfig.chinapnr_cfb);
					json1.put("DivAmt", df.format(fee));
					divDetails += "," + json1.toString();
				}
				divDetails += "]";
			}
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.repayment("10", ordId, outCustId, outAcctId, transAmt, inCustId, inAcctId, subOrdId, subOrdDate, Fee,
					divDetails));
			if (json.getInt("RespCode") != 0) {
				obj.put("msg", "失败:" + json.getString("RespDesc"));
				break;
			}
		}

		if (obj.containsKey("msg")) {
			return obj.getString("msg");
		}
		Map<String, String> map = null;
		try {
			map = submitPay(conn, payId, userId, repaySum, fee, consultFee, admin);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		int ret = Convert.strToInt(map.get("ret"), -1);
		if (ret > 0) {
			return "1";
		} else {
			return Convert.strToStr(map.get("ret_desc"), "还款失败");
		}
	}

	private Map<String, String> submitPay(Connection conn, long id, long userId, double needSum, double fee, double consultFee, Admin admin) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		long ret = -1;
		Procedures.p_borrow_repayment_time(conn, ds, outParameterValues, id, userId, "", WebUtil.getWebPath(), new Date(), new BigDecimal(0), new BigDecimal(needSum),
				new DecimalFormat("0.00").format(fee), new DecimalFormat("0.00").format(consultFee), -1, "");
		ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
		map.put("ret", ret + "");
		map.put("ret_desc", outParameterValues.get(1) + "");
		if (ret <= 0) {
			conn.rollback();
		} else {
			operationLogDao.addOperationLog(conn, "t_repayment", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "用户提前还款", 1);
		}
		return map;
	}
}
