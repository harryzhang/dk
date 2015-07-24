package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jxl.write.DateFormat;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.AssignmentDebtDao;
import com.sp2p.service.SelectedService;

/**
 * 债权管理业务实现类
 * 
 * @author xiemin
 * @version [版本号, 2013-10-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AssignmentDebtService extends BaseService {
	/*
	 * 日志
	 */
	public static Log log = LogFactory.getLog(ArticleManageService.class);

	/**
	 * 注入DAO
	 */
	private AssignmentDebtDao assignmentDao;

	private SelectedService selectedService;

	/**
	 * 查询债权列表，分页
	 * 
	 * @param conn
	 * @param pageBean
	 * @param username
	 * @param id
	 * @param type
	 * @param debtStatus
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void queryAssignmentList(PageBean<Map<String, Object>> pageBean, String userName, String id, String borrowWay, String isDebt,String number)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(id)) {
			condition.append(" and invest_number like '%" + StringEscapeUtils.escapeSql(id.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay = '" + StringEscapeUtils.escapeSql(borrowWay.trim()) + "' ");
		}
		if (StringUtils.isNotBlank(isDebt)) {
			condition.append(" and isDebt = '" + StringEscapeUtils.escapeSql(isDebt.trim()) + "' ");
		}
		if (StringUtils.isNotBlank(number)) {
			condition.append(" and number like  '%" + StringEscapeUtils.escapeSql(number.trim())  + "%' ");
		}
		try {
			dataPage(conn, pageBean, "v_t_assignment_debt", "*", " order by id desc ", condition.toString());
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
	 * 根据ID查询债权信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> queryAssignmentById(PageBean pageBean, String id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = assignmentDao.queryAssignmentById(conn, id);
			if (map == null || map.size() == 0) {
				return map;
			}
			String borrowId = map.get("borrowId");
			dataPage(conn, pageBean, " t_invest_repayment ", " * ", " ", " and invest_id=" + id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 债权转让
	 * 
	 * @param id
	 * @param debtLimit
	 * @param auctionBasePrice
	 * @param details
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return Long [返回类型说明]
	 * @throws DataException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Long assignment(String id, String auctionDays, String auctionBasePrice, String details, String deadline) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, Integer.valueOf(auctionDays));// auctionDays为增加的天数，可以改变的
		Date backTime = ca.getTime(); // 竞拍结结束时间
		String auctionEndTime = df.format(backTime);

		long result = -1L;
		Integer status = 3;
		try {
			map = assignmentDao.queryAssignmentById(conn, id);
			result = assignmentDao.assginment(conn, id, auctionDays, auctionBasePrice, details, deadline, map, auctionEndTime);

			// 如果确定转让，则更新投资表债权状态
			if (result > 0) {
				assignmentDao.udadateInvest(conn, id, status);
			}
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 查询转让中的债权列表信息
	 * 
	 * @param pageBean
	 * @param userName
	 * @param id
	 * @param publisher
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void queryTransferById(PageBean<Map<String, Object>> pageBean, String userName, String publisher) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(publisher)) {
			condition.append(" and publisher = '" + StringEscapeUtils.escapeSql(publisher.trim()) + "' ");
		}

		try {
			dataPage(conn, pageBean, "v_t_assignment_transfer_debt", "*", " order by id desc ", condition.toString());
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
	 * 根据ID查询出债权总额
	 * 
	 * @param id
	 * @param debtStatus
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @throws DataException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryDebtSumById(String id) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = assignmentDao.queryAlienatorById(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * 更新转让中的债权状态--成交，撤回
	 * 
	 * @param id
	 * @param debtStatus
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @throws DataException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long udadateDebtStatus(String id, String debtStatus, String alienatorId, String investId) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		long result = -1L;
		try {
			result = assignmentDao.udadateDebtStatus(conn, id, debtStatus);
			// 如果是成交，则向投资表插入一条新的数据--暂无成交
			if (result > 0 && debtStatus.equals("3")) {
				// 根据ID查询出转让人的各项信息
				map = assignmentDao.queryAlienatorById(conn, investId);
				//
				// assignmentDao.insertInvest(conn,map,alienatorId,auctionerId);
				// 根据ID修改转让表已经插入的转让信息状态
				// assignmentDao.udadeteAssingmentDebt(conn, auctionerId,
				// debtStatus);

				// 成交，则修改投资表债权状态
				Integer status = 2;
				assignmentDao.udadateInvest(conn, id, status);
			}
			if (result > 0 && debtStatus.equals("5"))// 如果是撤回则修改投资表状态,
			{
				Integer status = 1;
				assignmentDao.udadateInvest(conn, investId, status);
				// 根据ID修改转让表已经插入的转让信息状态
				assignmentDao.udadeteAssingmentDebt(conn, id, debtStatus);
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
	 * 拆分--根据ID删除原始债权
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long deleteAssignmentDebt(String id) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = assignmentDao.deleteAssignmentDebt(conn, id);
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
	 * 拆分债权--插入新拆分的债权
	 * 
	 * @param id
	 * @param number
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long insertAssignmentDebt(String id, Integer number, double realAmount, String split) throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = MySQL.getConnection();
		long count = 0;
		double recivedPrincipal = 0; // 应收本金
		double hasPrincipal = 0;// 已收本金
		double hasInterest = 0; // 已收利息
		double recievedInterest = 0; // 应收利息
		double recivedFI = 0; // 应收罚金
		double hasFI = 0; // 已收罚金
		double reward = 0; // 奖励
		try {
			// 根据ID查询出债权的各项信息
			map = assignmentDao.queryAssignmentById(conn, id);
			recievedInterest = Double.valueOf(map.get("recievedInterest"));
			hasInterest = Double.valueOf(map.get("hasInterest"));
			recivedFI = Double.valueOf(map.get("recivedFI"));
			hasFI = Double.valueOf(map.get("hasFI"));
			if (recivedPrincipal != 0) {
				recivedPrincipal = recivedPrincipal / number; // 应收本金除以期数，得到每一期的应收本金
				map.put("recivedPrincipal", String.valueOf(recivedPrincipal));
			}
			if (hasPrincipal != 0) {
				hasPrincipal = hasPrincipal / number; // 已收本金除以期数，得到每一期的已收本金
				map.put("hasPrincipal", String.valueOf(hasPrincipal));
			}
			// 计算拆分本金、利息
			if (hasInterest != 0) {
				hasInterest = hasInterest / number; // 已收利息除以期数，得到每一期的已收利息
				map.put("hasInterest", String.valueOf(hasInterest));
			}
			if (recievedInterest != 0) {
				recievedInterest = recievedInterest / number; // 应收利息除以期数，得到每一期的应收利息
				map.put("recievedInterest", String.valueOf(recievedInterest));
			}
			if (recivedFI != 0) {
				recivedFI = recivedFI / number; // 应收罚金除以期数，得到每一期的应收罚金
				map.put("recivedFI", String.valueOf(recivedFI));
			}
			if (hasFI != 0) {
				hasFI = hasFI / number; // 已收罚金
				map.put("hasFI", String.valueOf(hasFI));
			}
			if (reward != 0) {
				reward = reward / number; // 已收罚金
				map.put("reward", String.valueOf(reward));
			}
			// 平均拆分
			if (null != map && !split.equals("") && split.equals("1")) {
				for (int i = 0; i < number; i++) {
					count = assignmentDao.insertAssignmentDebt(conn, map, realAmount);
				}
			}
			// 部分拆分
			if (null != map.get("realAmount") && !map.get("realAmount").equals("0") && split.equals("2")
					&& realAmount < Double.valueOf(map.get("realAmount"))) {
				double money = Double.valueOf(map.get("realAmount")) - realAmount;
				// 拆分金额
				count = assignmentDao.insertAssignmentDebt(conn, map, realAmount);
				// 拆分余额
				count = assignmentDao.insertAssignmentDebt(conn, map, money);
			}
			if (count != 0) {
				assignmentDao.deleteAssignmentDebt(conn, id);
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return count;
	}

	public void setAssignmentDao(AssignmentDebtDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}

	/**
	 * 查询还款中的债权信息
	 * 
	 * @param pageBean
	 * @param debtId
	 * @param startTime
	 * @param endTime
	 * @param title
	 * @param borrowType
	 * @param userGroup
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void queryReturnedDebtList_1(PageBean pageBean, long debtId, String startTime, String endTime, String title, Integer borrowType,
			String userGroup) throws Exception {
		Connection conn = connectionManager.getConnection();
		String command = "";
		if (debtId > 0)
			command += " and id =" + debtId;
		if (title != null)
			command += " and borrowTitle like '%" + StringEscapeUtils.escapeSql(title) + "%' ";
		if (borrowType > 0)
			command += " and borrowWay =" + borrowType;
		// 未知的搜索条件:用户组
		// if (userGroup != null)
		// command += " and userGroup =" + userGroup;
		if (startTime != null)
			command += " and repayDate >='" + StringEscapeUtils.escapeSql(startTime);
		if (endTime != null)
			command += "' and repayDate <='" + StringEscapeUtils.escapeSql(endTime) + "'";

		try {
			dataPage(conn, pageBean, "v_t_returned_debt_list", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}
	
	/**
	 * 查询还款中的债权信息
	 * 
	 * @param pageBean
	 * @param debtId
	 * @param startTime
	 * @param endTime
	 * @param title
	 * @param borrowType
	 * @param userGroup
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void queryReturnedDebtList(PageBean pageBean, long debtId,String number,String startTime, String endTime, String title, Integer borrowType,
			String userGroup) throws Exception {
		Connection conn = connectionManager.getConnection();
		String command = "";
		if (debtId > 0)
			command += " and id =" + debtId;
		if (number != null)
			command += " and number = '"+number+"'";
		if (title != null)
			command += " and borrowTitle like '%" + StringEscapeUtils.escapeSql(title) + "%' ";
		if (borrowType > 0)
			command += " and borrowWay =" + borrowType;
		// 未知的搜索条件:用户组
		// if (userGroup != null)
		// command += " and userGroup =" + userGroup;
		if (startTime != null)
			command += " and repayDate >='" + StringEscapeUtils.escapeSql(startTime);
		if (endTime != null)
			command += "' and repayDate <='" + StringEscapeUtils.escapeSql(endTime) + "'";

		try {
			dataPage(conn, pageBean, "v_t_returned_debt_list", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

//	/**
//	 * 还款中的债权 根据ID查询详情
//	 * 
//	 * @param pageBean
//	 * @param borrowId
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public void queryRepaymentAssignmentById(PageBean pageBean, long borrowId) throws Exception {
//		Connection conn = connectionManager.getConnection();
//		try {
//			dataPage(conn, pageBean, "v_t_returned_debt_repay_list", "*", "", " and borrowId=" + borrowId);
//		} catch (Exception e) {
//			log.error(e);
//			e.printStackTrace();
//			throw e;
//		} finally {
//			conn.close();
//		}
//
//	}
	
	/**
	 * 根据ID查询还款中的债权信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> queryRepaymentAssignmentById(PageBean pageBean, long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			dataPage(conn, pageBean, " t_invest_repayment ", " * ", " ", " and invest_id= '"+id+"'" );
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public void queryRepaymentAssignmentList(PageBean pageBean, long invest, long owner) throws Exception {
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "t_invest_repayment", "*", "", " and invest_id=" + invest + " and owner = " + owner);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	public void queryAuditDebt(PageBean pageBean, String username, String publisher, long id) throws Exception {
		Connection conn = MySQL.getConnection();
		String command = "";
		if (username != null && !"".equals(username))
			command += " and username like '%" + StringEscapeUtils.escapeSql(username) + "%' ";
		if (publisher != null && !"".equals(publisher))
			command += "' and publisher like '%" + StringEscapeUtils.escapeSql(publisher) + "%'";
		if (id > 0)
			command += " and id = " + id;

		try {
			dataPage(conn, pageBean, "v_t_update_assignmet_debt", "*", " order by applyTime desc", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void updateAuditDebt(long investId, long id, String remark, int stauts, String time) throws Exception {
		Connection conn = MySQL.getConnection();
		if (time == null || "".equals(time)) {
			time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		try {
			if (stauts == 2) {// 通过
				assignmentDao.udadeteAssingmentDebts(conn, id + "", "2", remark, time);
			}
			if (stauts == 6) {// 不通过
				assignmentDao.udadeteAssingmentDebts(conn, id + "", "6", remark, time);
				assignmentDao.udadateInvest(conn, investId + "", 1);
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

}
