package com.sp2p.service.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.config.ChinaPnrConfig;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.AdminDao;
import com.sp2p.dao.admin.FIManageDao;
import com.sp2p.dao.admin.merDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

/**
 * 商户管理 service
 */
public class merService extends BaseService {
	private merDao merDao;
	private AdminDao adminDao;
	private FIManageDao fiManageDao;

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public void setMerDao(merDao merDao) {
		this.merDao = merDao;
	}

	public void setFiManageDao(FIManageDao fiManageDao) {
		this.fiManageDao = fiManageDao;
	}

	public String merRecharge(String subAcct, double amount, String cardDcFlag) throws SQLException {
		Connection conn = MySQL.getConnection();
		Date now = new Date();
		String html = "";
		String ordDate = DateUtil.dateToYMD(now);
		String moneyStr = new DecimalFormat("0.00").format(amount);
		try {
			// 为了不和前台充值冲突,出现重复交易,取个ordIdR
			long ordId = merDao.addRecharge(conn);
			merDao.deleteRechargeDetails(conn, ordId);
			ordId = merDao.addMerRecharge(conn, ordId, amount, subAcct, now);
			if (ordId <= 0) {
				conn.rollback();
				return html;
			}
			html = ChinaPnRInterface.netSave("商户充值", ordId + "", "", ChinaPnrConfig.chinapnr_merCustId, ordDate, moneyStr, "B2B", "merSave", ChinaPnrConfig.chinapnr_retUrl_bg,
					cardDcFlag);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return html;
	}

	/** 充值成功 */
	public String updateMerRecharge(String id, String trxId, String fee) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			// 查询子账户,同步数据
			String usableMoney = "";
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.queryAccts());
			JSONArray js = json.getJSONArray("AcctDetails");
			for (int i = 0; i < js.size(); i++) {
				JSONObject j = js.getJSONObject(i);
				String subAcct = j.getString("SubAcctId");// 子账户
				if (ChinaPnrConfig.chinapnr_cfb.equals(subAcct)) {
					usableMoney = j.getString("AcctBal");// 账户余额
				}
				String subAcctMoney = j.getString("AcctBal");// 账户余额
				adminDao.updateAdminMoney(conn, subAcct, subAcctMoney);
			}
			if (StringUtils.isBlank(usableMoney)) {
				usableMoney = adminDao.queryAdminByAcctId(conn, ChinaPnrConfig.chinapnr_cfb);
			}
			merDao.updateMerRechargeSuccess(conn, id, trxId, fee, usableMoney);
			conn.commit();
			return "充值成功";
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return "系统异常";
	}

	/** 充值失败处理 **/
	public void netSaveFail(String ordId, String trxId, String fee) throws SQLException {
		long id = Convert.strToLong(ordId, -1);
		if (id < 0)
			return;
		Connection conn = MySQL.getConnection();
		try {
			// 查询子账户,同步数据
			String usableMoney = "";
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.queryAccts());
			JSONArray js = json.getJSONArray("AcctDetails");
			for (int i = 0; i < js.size(); i++) {
				JSONObject j = js.getJSONObject(i);
				String subAcct = j.getString("SubAcctId");// 子账户
				if (ChinaPnrConfig.chinapnr_cfb.equals(subAcct)) {
					usableMoney = j.getString("AcctBal");// 账户余额
				}
				String subAcctMoney = j.getString("AcctBal");// 账户余额
				adminDao.updateAdminMoney(conn, subAcct, subAcctMoney);
			}
			if (StringUtils.isBlank(usableMoney)) {
				usableMoney = adminDao.queryAdminByAcctId(conn, ChinaPnrConfig.chinapnr_cfb);
			}
			long ret = merDao.netSaveFail(conn, id, trxId, fee, usableMoney);
			if (ret > 0) {
				conn.commit();
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 商户转账
	 * 
	 * @param adminId
	 * @param amount
	 * @param outSubAcct
	 * @param inSubAcct
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public String merRechargeTransfer(long adminId, double amount, String outSubAcct, String inSubAcct) throws Exception {
		Connection conn = MySQL.getConnection();
		// Date now = new Date();
		// String html = "";
		// String ordDate = DateUtil.dateToYMD(now);
		// String moneyStr = new DecimalFormat("0.00").format(amount);
		// 查询出账账户信息
		Map<String, String> outMap = merDao.queryOutSubAcct(conn, outSubAcct);
		// 查询入账账户信息
		Map<String, String> inMap = merDao.queryInSubAcct(conn, inSubAcct);
		// JSONObject transfer = null;
		JSONObject json = null;
		// JSONObject json1 = null;
		try {

			// long ordId = merDao.addRepayment(conn, new Date());
			long ordId = merDao.addBack_w(conn);
			// merDao.deleteBack_w(conn,ordId);
			if (ordId <= 0) {
				conn.rollback();
				return null;
			}
			if (outMap == null || inMap == null) {
				json.getString("系统异常");
				return null;
			}
			String jsonTransfer = ChinaPnRInterface.transfer(ordId + "", ChinaPnrConfig.chinapnr_merCustId, outSubAcct, amount + "", ChinaPnrConfig.chinapnr_merCustId, inSubAcct);
			json = JSONObject.fromObject(jsonTransfer);
			int codeTransfer = json.getInt("RespCode");
			if (codeTransfer == 0) {
				// 删除插入的测试数据
				// merDao.deleteRepayment(conn, ordId);

				String jsonStr = ChinaPnRInterface.queryBalanceBg(outMap.get("usrCustId"));// 汇付查询专属账户余额
				json = JSONObject.fromObject(jsonStr);
				int RespCode = json.getInt("RespCode");
				String outAvlBal = json.getString("AvlBal");
				outAvlBal = outAvlBal.replaceAll(",", "");
				// 更新出账账户余额--汇付结果
				if (RespCode == 0) {
					if ((outMap.get("subAcctMoney") != outAvlBal) || (!outMap.get("subAcctMoney").equals(outAvlBal))) {
						merDao.updateOutSubAcct(conn, outAvlBal, outSubAcct);
					}
				}

				// if (RespCode == 0) {
				// JSONArray array = json.getJSONArray("AcctDetails");
				// for (int i = 0; i < array.size(); i++) {
				// JSONObject jjj = array.getJSONObject(i);
				// if (jjj.getString("SubAcctId").equals(
				// outMap.get("subAcct"))) {
				// String avlBal = jjj.getString("AvlBal");
				// avlBal = avlBal.replace(",", "");
				// // 更新出账账户余额--汇付结果
				// if ((outMap.get("subAcctMoney") != outAvlBal) ||
				// (!outMap.get("subAcctMoney").equals(outAvlBal))) {
				// merDao.updateOutSubAcct(conn, outAvlBal,outSubAcct);
				// }
				// }
				// }
				// }

				// 汇付查询专属账户余额
				String jsonStr1 = ChinaPnRInterface.queryBalanceBg(inMap.get("usrCustId"));
				json = JSONObject.fromObject(jsonStr1);
				int RespCode1 = json.getInt("RespCode");
				String inAvlBal = json.getString("AvlBal");
				inAvlBal = inAvlBal.replace(",", "");

				// 更新入账账户余额--汇付结果
				if (RespCode1 == 0) {

					if ((inMap.get("inAcctMoney") != inAvlBal) || (!inMap.get("inAcctMoney").equals(inAvlBal))) {
						merDao.updateInSubAcct(conn, inAvlBal, inSubAcct);
					}
				}
				json.getString("RespDesc");
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return json.getString("RespDesc");
	}

	/** 充值记录 */
	@SuppressWarnings("unchecked")
	public void queryMerRechargeList(PageBean pageBean, String start, String end) throws SQLException {
		Connection conn = MySQL.getConnection();
		String cmd = "";
		if (!StringUtils.isBlank(start)) {
			cmd += " and rechargeTime >= '" + start + "'";
		}
		if (!StringUtils.isBlank(end)) {
			cmd += " and  DATE_ADD(rechargeTime,INTERVAL -1 day)<= '" + end + "'";
		}
		try {
			dataPage(conn, pageBean, " t_merRecharge ", " id,money,rechargeTime,subAcct,result,ifnull(trxId,'--') as trxId,usableSum,fee ", " order by rechargeTime desc ", cmd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 充值记录
	 * 
	 * @param queryTrade
	 * @param endTime
	 */
	public List<Map<String, Object>> queryOrdIdList(String userName, String startTime, String endTime, String queryTrade) throws SQLException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = null;
		// 查询充值ID
		try {
			if (null != queryTrade && (queryTrade == "NETSAVE" || queryTrade.equals("NETSAVE"))) {
				map = merDao.queryTrechargeDetail(conn, userName, startTime, endTime);
			}
			if (null != queryTrade && (queryTrade == "LOANS" || queryTrade.equals("LOANS"))) {
				map = merDao.queryLoans(conn, userName, startTime, endTime);
			}
			if (null != queryTrade && (queryTrade == "REPAYMENT" || queryTrade.equals("REPAYMENT"))) {
				map = merDao.queryRepayment(conn, userName, startTime, endTime);
			}
			if (null != queryTrade && (queryTrade == "CASH" || queryTrade.equals("CASH"))) {
				map = merDao.queryCash(conn, userName, startTime, endTime);
			}
			if (null != queryTrade && (queryTrade == "CASHAUDIT" || queryTrade.equals("CASHAUDIT"))) {
				map = merDao.queryCashAudit(conn, userName, startTime, endTime);
			}
			if (null != queryTrade && (queryTrade == "INITIATIVETENDER" || queryTrade.equals("INITIATIVETENDER"))) {
				map = merDao.queryInitiativeTender(conn, userName, startTime, endTime);
			}
			if (null != queryTrade && (queryTrade == "MERCASH" || queryTrade.equals("MERCASH"))) {
				map = merDao.queryMerCash(conn, userName, startTime, endTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 交易状态查询
	 * 
	 * @param ordId
	 * @param startTime
	 * @param queryTrade
	 * @return
	 */
	public String queryTransStatus(String ordId, String startTime, String queryTrade) throws Exception {
		JSONObject json = null;
		try {
			String jsonStr = ChinaPnRInterface.queryTransStat(ordId, startTime, queryTrade);// 汇付查询交易状态
			json = JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.getString("RespDesc");
	}

	/**
	 * 对账时,根据userCustId获取用户名
	 * 
	 * @param pageBean
	 *            汇付返回的数据,封装在pageBean里面
	 * */
	@SuppressWarnings("unchecked")
	public void getUsernameByCustId(PageBean pageBean) throws SQLException {
		List<Map<String, String>> list = pageBean.getPage();
		if (list == null || list.size() == 0) {
			return;
		}

		StringBuilder cmd = new StringBuilder(" select username,usrCustId from t_user where usrCustId in (");
		for (Map<String, String> map : list) {
			cmd.append(map.get("UsrCustId")).append(",");
		}
		cmd.deleteCharAt(cmd.length() - 1).append(") ");
		Connection conn = MySQL.getConnection();
		try {
			DataSet dataSet = MySQL.executeQuery(conn, cmd.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			List<Map<String, Object>> names = dataSet.tables.get(0).rows.rowsMap;
			Map<String, Object> name = new HashMap<String, Object>();
			for (Map<String, Object> map : names) {// 获取usrCustId:username键值对
				name.put(map.get("usrCustId") + "", map.get("username"));
			}

			for (Map<String, String> map : list) {// 加入username属性
				if (ChinaPnrConfig.chinapnr_merCustId.equals(map.get("UsrCustId"))) {
					map.put("username", "商户");
				} else {
					map.put("username", name.get(map.get("UsrCustId")) + "");
				}
			}
			pageBean.setPage(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	/**
	 * 投标对账时调用,根据InvestCustId 和 BorrCustId查找用户名
	 */
	@SuppressWarnings("unchecked")
	public void getUsernameByCustId2(PageBean pageBean) throws SQLException {
		List<Map<String, String>> list = pageBean.getPage();
		if (list == null || list.size() == 0) {
			return;
		}

		StringBuilder cmd = new StringBuilder(" select username,usrCustId from t_user where usrCustId in (");
		for (Map<String, String> map : list) {
			cmd.append(map.get("InvestCustId")).append(",").append(map.get("BorrCustId")).append(",");
		}
		cmd.deleteCharAt(cmd.length() - 1).append(") ");
		Connection conn = MySQL.getConnection();
		try {
			DataSet dataSet = MySQL.executeQuery(conn, cmd.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			List<Map<String, Object>> names = dataSet.tables.get(0).rows.rowsMap;
			Map<String, Object> name = new HashMap<String, Object>();
			for (Map<String, Object> map : names) {// 获取usrCustId:username键值对
				name.put(map.get("usrCustId") + "", map.get("username"));
			}

			for (Map<String, String> map : list) {// 加入username属性
				map.put("investor", name.get(map.get("InvestCustId")) + "");
				map.put("borrower", name.get(map.get("BorrCustId")) + "");
			}
			pageBean.setPage(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 子账户取现
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	@SuppressWarnings("null")
	public String addMerCash(double transAmt, String userId, Long adminId, String subAcct, String usrCustId) throws Exception {
		Connection conn = MySQL.getConnection();
		JSONObject json = null;
		try {
			// 添加提现记录
			long ordId = fiManageDao.addMerCash(conn, userId, transAmt, adminId);
			if (ordId < 0) {
				conn.rollback();
				return json.getString("操作失败");
			}

			String amtStr = new DecimalFormat("0.00").format(transAmt);
			json = JSONObject.fromObject(ChinaPnRInterface.merCash(ordId + "", subAcct, amtStr, ""));
			if (json == null) {
				return json.getString("签名错误");
			}
			if (null != json && json.getInt("RespCode") != 0) {
				conn.rollback();
				return json.getString("RespDesc");
			}
			fiManageDao.updateMerCash(conn, ordId, json.getString("OpenAcctId"), json.getString("FeeAmt"));

			// 更新子账户余额
			String jsonStr = ChinaPnRInterface.queryAccts();// 汇付查()询专属账户余额
			if (null != jsonStr && !jsonStr.equals("签名错误")) {
				json = JSONObject.fromObject(jsonStr);
				int RespCode = json.getInt("RespCode");
				if (RespCode == 0) {
					JSONArray array = json.getJSONArray("AcctDetails");
					for (int i = 0; i < array.size(); i++) {
						JSONObject jjj = array.getJSONObject(i);
						if (jjj.getString("SubAcctId").equals(subAcct)) {
							String avlBal = jjj.getString("AvlBal");
							avlBal = avlBal.replace(",", "");
							// 更新出账账户余额--汇付结果
							adminDao.updateSubAcct(conn, avlBal, subAcct.toString());
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return json.getString("RespDesc");
		} finally {
			conn.close();
		}
		return json.toString();
	}

	/**
	 * 查询子账户取现记录
	 * 
	 * @param pageBean
	 * @param userID
	 * @throws Exception
	 *             [参数说明]
	 */
	public List<Map<String, Object>> queryCfbMerCashList(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = null;
		try {
			map = merDao.queryCfbMerCashList(conn, userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public String getUsrId(String busiCode) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			long id = MySQL.executeNonQuery(conn, " insert into t_user (enable) values (3)");
			MySQL.executeNonQuery(conn, " delete from t_user where id=" + id);
			MySQL.executeNonQuery(conn, " insert into t_company (id,busiCode) values (" + id + ",'" + busiCode + "')");
			conn.commit();
			return id + "";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			conn.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> queryCompanyInfo(long usrCustId, PageBean pageBean) throws Exception {
		Map<String, String> map = null;
		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.queryBalanceBg(usrCustId + ""));
		String AvlBal = json.getString("AvlBal").replaceAll(",", "");// 可用余额
		String FrzBal = json.getString("FrzBal").replaceAll(",", "");// 冻结金额
		String AcctBal = json.getString("AcctBal").replaceAll(",", "");// 资产金额

		Connection conn = MySQL.getConnection();
		try {
			map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, "select id from t_user where usrCustId=" + usrCustId));
			if (map == null) {
				return null;
			}
			long userId = Convert.strToLong(map.get("id"), 0);
			if (userId <= 0) {
				return null;
			}
			MySQL.executeNonQuery(conn, "update t_user set usableSum=" + AvlBal + ",freezeSum=" + FrzBal + " where id=" + userId);
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getUserAmountSumary(conn, ds, outParameterValues, userId);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
			map.put("acctBal", AcctBal);
			map.put("usable", AvlBal);
			map.put("free", FrzBal);

			map.putAll(BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, "select name,busiCode from t_company where usrCustId=" + usrCustId)));

			dataPage(conn, pageBean, "v_t_bankcard_query", "*", " order by commitTime", " and userId=" + userId);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> addRecharge(String amount, String usrCustId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> usr = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, "select id from t_user where usrCustId=" + usrCustId));
			if (usr == null || Convert.strToLong(usr.get("id"), -1) < 0) {
				return null;
			}
			long userId = Convert.strToLong(usr.get("id"), -1);
			if (userId <= 0) {
				return null;
			}
			long ordId = MySQL.executeNonQuery(conn, "insert into t_recharge_detail (userId, rechargeTime, rechargeType, rechargeMoney, cost)" + "	values (" + userId
					+ ", now(), 1, " + amount + ", 0.0)");
			map.put("ordId", ordId + "");
			map.put("userId", userId + "");
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public long comWithdraw(double money, String usrCustId) throws SQLException {
		Connection conn = MySQL.getConnection();
		long ordId = -1L;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Map<String, String> usr = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, "select id from t_user where usrCustId=" + usrCustId));
			if (usr == null || Convert.strToLong(usr.get("id"), -1) < 0) {
				return ordId;
			}
			long userId = Convert.strToLong(usr.get("id"), -1);
			if (userId <= 0) {
				return ordId;
			}
			Procedures.p_amount_withdraw(conn, ds, outParameterValues, userId, "", new BigDecimal(money), -1, "", "", DateUtil.dateToYMD(new Date()), -1, "", "");
			long ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (ret <= 0) {
				conn.rollback();
				return ordId;
			}
			ordId = Convert.strToLong(outParameterValues.get(2) + "", -1);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ordId;
	}

}
