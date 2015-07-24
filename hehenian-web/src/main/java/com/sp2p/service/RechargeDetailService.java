/**
 * 
 */
package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.config.IPayConfig;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.security.Encrypt;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.RechargeDao;
import com.sp2p.dao.RechargeDetailDao;
import com.sp2p.dao.UserDao;
import com.sp2p.util.DateUtil;

/**
 * 充值详细
 * 
 * @author Administrator
 * 
 */
public class RechargeDetailService extends BaseService {
	public static Log log = LogFactory.getLog(RechargeDetailService.class);

	private RechargeDetailDao rechargeDetailDao;

	private FundRecordDao fundRecordDao;

	private UserDao userDao;

	private FinanceDao financeDao;

	private RechargeDao rechargeDao;

	/**
	 * 添加充值详细
	 * 
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long addRechargeDetail(Map<String, String> paramMap)
			throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = rechargeDetailDao.addRechargeDetail(conn, paramMap);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 添加充值详细
	 * 
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long updateRechargeDetail(long id, Map<String, String> paramMap)
			throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = rechargeDetailDao.updateRechargeDetail(conn, id, paramMap);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;

	}

	/**
	 * 删除充值详细，可删除多个
	 * 
	 * @param conn
	 * @param ids
	 *            id字符串，用,隔开
	 * @return
	 * @throws SQLException
	 */
	public long deleteRechargeDetail(String ids) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = rechargeDetailDao.deleteRechargeDetail(conn, ids);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 根据ID获取充值详细信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getRechargeDetail(long id) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = rechargeDetailDao.getRechargeDetail(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 在线充值
	 * 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws ParseException
	 */
	public int userPay(Map<String, Object> paramMap) throws SQLException,
			DataException, ParseException {
		int returnId = -1;

		try {
			log.info("1-");
			String[] extraCommonParam = (String[]) paramMap
					.get("extraCommonParam");
			BigDecimal total_fee = (BigDecimal) paramMap.get("total_fee");
			String paynumber = (String) paramMap.get("paynumber");
			String paybank = (String) paramMap.get("paybank");
			String notify_timeStr = (String) paramMap.get("notify_time");
			String buyer_email = (String) paramMap.get("buyer_email");
			Long orderId = Convert.strToLong(extraCommonParam[0], -1);// 获得订单编号
			if (orderId < 0) {
				return -2;// 订单编号错误
			}
			Long userId = Convert.strToLong(extraCommonParam[1], -1);// 获得用户编号
			if (userId < 0) {
				return -3;// 用户编号错误
			}
			log.info("2-");

			Date notify_time = DateUtil.strToDate(notify_timeStr);
			log.info("3-");

			returnId = userPayIn(orderId, userId, total_fee, paynumber,
					paybank, buyer_email, notify_time);

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return returnId;// 成功!
	}
	/**
	 * 到用户支付
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws ParseException
	 */
	public int usergoPay(Map<String, Object> paramMap) throws SQLException,
			DataException, ParseException {
		int returnId = -1;

		try {
			log.info("1-");

			BigDecimal total_fee = new BigDecimal(paramMap.get("tranAmt") + "");
			String paynumber = (String) paramMap.get("orderId");
			String paybank = (String) paramMap.get("paybank");
			String notify_timeStr = (String) paramMap.get("tranFinishTime");
			String buyer_email = (String) paramMap.get("buyerName");
			Long orderId = Convert.strToLong(paramMap.get("merRemark1") + "",
					-1);// 获得订单编号
			if (orderId < 0) {
				return -2;// 订单编号错误
			}
			Long userId = Convert
					.strToLong(paramMap.get("merRemark2") + "", -1);// 获得用户编号
			if (userId < 0) {
				return -3;// 用户编号错误
			}
			log.info("2-");

			Date notify_time = DateUtil.YYYYMMDDHHMMSS.parse(notify_timeStr);
			log.info("3-");

			returnId = userPayIn(orderId, userId, total_fee, paynumber,
					paybank, buyer_email, notify_time);

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return returnId;// 成功!
	}
	/**
	 * 用户支付
	 * @param orderId
	 * @param userId
	 * @param total_fee
	 * @param paynumber
	 * @param paybank
	 * @param buyer_email
	 * @param notify_time
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	private int userPayIn(Long orderId, Long userId, BigDecimal total_fee,
			String paynumber, String paybank, String buyer_email,
			Date notify_time) throws DataException, SQLException {
		Connection conn = MySQL.getConnection();
		int result = -7;
		try {
			Map<String, String> detailMap = rechargeDetailDao
					.getRechargeDetail(conn, orderId);
			long rechargeId = Convert.strToLong(detailMap.get("rechargeId"), -1);
			long uId = Convert.strToLong(detailMap.get("userId")+"", -1);
			if(userId != uId){
				return -1;
			}
			//根据订单号 查询订单
			
			if (detailMap != null) {
				if ("1".equals(detailMap.get("result"))) {
					result = -4;// 已支付
				} else if (total_fee.compareTo(new BigDecimal(detailMap
						.get("rechargeMoney"))) != 0) {
					result = -5;// 充值金额不相等
				} else if (rechargeId == -1) {
					result = -8; // 充值表不存在
				} else {
					Map<String, String> paramMap = new HashMap<String, String>();
					// 修改充值详细状态
					String rechargeTime = DateUtil.dateToString(notify_time);
					paramMap.put("rechargeMoney", total_fee + "");
					paramMap.put("rechargeTime", rechargeTime);
					paramMap.put("result", "1");
					paramMap.put("paynumber", paynumber);
					paramMap.put("bankName", paybank);
					paramMap.put("buyerEmail", buyer_email);
					
					long resultCount = rechargeDetailDao.updateRechargeDetail(conn, orderId,
							paramMap);
					if(resultCount > 0){
						if(uId == -1){
							conn.rollback();
							return -3;// 用户编号错误
						}
						// 向用户帐号打款
						userDao.addUserUsableAmount(conn, total_fee
								.doubleValue(), uId);
							
						Map<String, String> userAmountMap = financeDao
								.queryUserAmountAfterHander(conn, uId);

						if (userAmountMap == null) {
							userAmountMap = new HashMap<String, String>();
						}

						// 向资金记录充值
						double operMoney = total_fee.doubleValue();
						Map<String, String> fundMap = new HashMap<String, String>();
						fundMap.put("userId", uId + "");
						fundMap.put("fundMode", "在线充值");
						fundMap.put("handleSum", operMoney + "");
						fundMap.put("recordTime", DateUtil
								.dateToString(new Date()));
						fundMap.put("trader", "-1");
						fundMap.put("usableSum", userAmountMap.get("usableSum"));
						fundMap.put("freezeSum", userAmountMap.get("freezeSum"));
						fundMap.put("dueinSum", userAmountMap.get("forPI"));
						fundMap.put("remark", "在线充值");
						fundMap.put("income", operMoney+"");
						fundMap.put("operateType", 2 + "");
						fundRecordDao.addFundRecord(conn, fundMap);
						// 修改充值表
						Map<String, String> rechargeMap = new HashMap<String, String>();
						rechargeMap.put("rechargeMoney", operMoney + "");
						rechargeMap.put("result", "1");
						rechargeDao.updateRecharge(conn, rechargeId,
								rechargeMap);
					
					result = 1; // 成功
				}else{
					conn.rollback();
					return -4;
				}
			  }
			} else {
				result = -6;// 支付详细不存在
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}
	/**
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int userIPay(Map<String, Object> paramMap) throws Exception {
		int returnId = -1;

		try {
			log.info("1-");
			BigDecimal total_fee = new BigDecimal(paramMap.get("amount") + "");
			String paynumber = (String) paramMap.get("ipsbillno");
			String paybank = (String) paramMap.get("paybank");
			String[] paras = Encrypt.decryptSES(paramMap.get("attach")+"", IPayConfig.ipay_see_key).split("_");
			if(paras == null || paras.length != 2){
				return -2;
			}
			Long orderId = Convert.strToLong(paras[0],
					-1);// 获得订单编号
			if (orderId < 0) {
				return -2;// 订单编号错误
			}
			Long userId = Convert.strToLong(paras[1], -1);// 获得用户编号
			if (userId < 0) {
				return -3;// 用户编号错误
			}
			log.info("2-");

			Date notify_time = new Date();
			log.info("3-");

			returnId = userPayIn(orderId, userId, total_fee, paynumber,
					paybank, "", notify_time);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return returnId;// 成功!
	}
	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setRechargeDao(RechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}

	public void setRechargeDetailDao(RechargeDetailDao rechargeDetailDao) {
		this.rechargeDetailDao = rechargeDetailDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

}
