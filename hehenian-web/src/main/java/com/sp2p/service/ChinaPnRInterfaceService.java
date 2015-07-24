package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import chinapnr.SecureLink;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.config.ChinaPnrConfig;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.ChinaPnrDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RechargeDetailDao;
import com.sp2p.dao.RepamentDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.AdminDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.util.AmountUtil;

public class ChinaPnRInterfaceService extends BaseService {
	private ChinaPnrDao chinaPnrDao;
	private AwardService awardService;
	private BorrowDao borrowDao;
	private AdminDao adminDao;
	private OperationLogDao operationLogDao;
	private UserDao userDao;
	private RechargeDetailDao rechargeDetailDao;
	private RepamentDao repamentDao;
	private BorrowManageService borrowManageService;
	private AdminService adminService;
	public static Log log = LogFactory.getLog(ChinaPnRInterfaceService.class);
	private SecureLink secureLink = new SecureLink();// 汇付天下接口,签名验证

	public void setChinaPnrDao(ChinaPnrDao chinaPnrDao) {
		this.chinaPnrDao = chinaPnrDao;
	}

	/**
	 * 汇付 用户账户支付
	 */
	public String UsrAcctPay(Map<String, String[]> requestMap, String cmdId, String pgKeyFile, String basePath) throws SQLException {
		return null;
		/*
		 * String RespCode = requestMap.get("RespCode")[0]; // 应答返回码 String
		 * MerCustId = requestMap.get("MerCustId")[0]; // 商户号 String UsrCustId =
		 * requestMap.get("UsrCustId")[0]; // 用户号 String OrdId =
		 * requestMap.get("OrdId")[0]; // 订单号 String InAcctId =
		 * requestMap.get("InAcctId")[0]; // 入账账户 用户在汇付的虚拟资金账号 String TransAmt =
		 * requestMap.get("TransAmt")[0]; // 交易金额 // BASEDT基本借记户; DEP保证金账户;
		 * MERDT专属借记帐户;等 String InAcctType = requestMap.get("InAcctType")[0];//
		 * 账户类型 String RetUrl = requestMap.get("RetUrl")[0].trim(); // 前台回调 可选
		 * String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台回调 String
		 * MerPriv = requestMap.get("MerPriv")[0].trim(); // 商户私有域 可选 //
		 * 请求时传的是购买债权的userId=xxxx String ChkValue =
		 * requestMap.get("ChkValue")[0]; // 签名信息 String MerDate = cmdId +
		 * RespCode + OrdId + UsrCustId + MerCustId + TransAmt + InAcctId +
		 * InAcctType + RetUrl + BgRetUrl + MerPriv;
		 * 
		 * // 验证签名 if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) !=
		 * 0) { return "签名错误 ,数据可能被篡改!"; }
		 * 
		 * if (Convert.strToInt(RespCode, -1) != 0) { return
		 * requestMap.get("RespDesc")[0] + " RespCode:" + RespCode; }
		 * 
		 * Connection conn = MySQL.getConnection(); long result = -1L; DataSet
		 * ds = new DataSet(); List<Object> outParameterValues = new
		 * ArrayList<Object>(); try { Procedures.p_borrow_debt_add(conn, ds,
		 * outParameterValues, Convert.strToLong(OrdId, -1),
		 * Convert.strToLong(MerPriv.substring(MerPriv.indexOf("=") + 1), -1),
		 * new BigDecimal(TransAmt), "", basePath, 0, ""); result =
		 * Convert.strToLong(outParameterValues.get(0) + "", -1); if (result >
		 * 0) { conn.commit(); return "支付成功!"; } conn.rollback(); return
		 * outParameterValues.get(1) + ""; } catch (Exception e) {
		 * conn.rollback(); log.info(e); return "支付出现异常!"; } finally {
		 * conn.close(); }
		 */
	}

	/**
	 * 汇付 自动扣款 (放款)
	 * 
	 * @desc 投资人放款给借款人，同时通过分账串商户可以收取费用等。 后台方式，需在页面上打印 RECV_ORD_ID_ 和 OrdId
	 * @param requestMap
	 *            响应参数
	 */
	public String Loans(Map<String, String[]> requestMap, String cmdId, String pgKeyFile, String basePath) throws SQLException {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户号
		String OrdId = requestMap.get("OrdId")[0]; // 订单号
		String OrdDate = requestMap.get("OrdDate")[0]; // 订单日期
		String OutCustId = requestMap.get("OutCustId")[0]; // 出账客户(汇付)

		String OutAcctId = requestMap.get("OutAcctId")[0].trim(); // 出账客户在汇付的虚拟资金账号
																	// 可选
		String TransAmt = requestMap.get("TransAmt")[0]; // 交易金额
		String Fee = requestMap.get("Fee")[0]; // 签名信息
		String InCustId = requestMap.get("InCustId")[0]; // 入账客户(汇付)
		String InAcctId = requestMap.get("InAcctId")[0].trim(); // 入账账户
																// 用户在汇付的虚拟资金账号
																// 可选

		String SubOrdId = requestMap.get("SubOrdId")[0]; // 关联的交易流水
		String SubOrdDate = requestMap.get("SubOrdDate")[0]; // 关联的交易流水日期
		String IsDefault = requestMap.get("IsDefault")[0];
		String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台回调
		String OpenBankId = requestMap.get("OpenBankId")[0].trim(); // 开户银行代号 可选

		String OpenAcctId = requestMap.get("OpenAcctId")[0].trim(); // 取现银行卡号 可选
		String MerPriv = requestMap.get("MerPriv")[0].trim(); // 商户私有域 可选
																// 请求时传递adminId
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名信息
		String MerDate = cmdId + RespCode + MerCustId + OrdId + OrdDate + OutCustId + OutAcctId + TransAmt + Fee + InCustId + InAcctId + SubOrdId + SubOrdDate + IsDefault
				+ BgRetUrl + OpenBankId + OpenAcctId + MerPriv;

		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ,数据可能被篡改!";
		}

		if (Convert.strToInt(RespCode, -1) != 0) {
			return requestMap.get("RespDesc")[0] + " RespCode:" + RespCode;
		}

		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			// 放款业务处理
			Map<String, String> map = borrowFullDeal(conn, Convert.strToLong(OrdId, -1), 4, "", Convert.strToLong(MerPriv.substring(MerPriv.indexOf("=" + 1)), -1), basePath);
			result = Convert.strToLong(map.get(0), -1);
			if (result > 0) {
				conn.commit();
				return "放款成功!";
			}
			conn.rollback();
			return map.get(1);
		} catch (Exception e) {
			conn.rollback();
			log.info(e);
			return "放款出现异常!";
		} finally {
			conn.close();
		}
	}

	/**
	 * 汇付 自动扣款 (还款)
	 * 
	 * @desc 如果出账账户是担保账户，请指定 OutAcctId ，否则可空。如果入账账户请指定 InAcctId ，否则可空。
	 * @param requestMap
	 *            响应参数
	 */
	@SuppressWarnings("unchecked")
	public String Repayment(Map<String, String[]> requestMap, String cmdId, String pgKeyFile, String basePath) throws SQLException {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户号
		String OrdId = requestMap.get("OrdId")[0]; // 订单号
		String InAcctId = requestMap.get("InAcctId")[0]; // 入账账户 用户在汇付的虚拟资金账号
		String TransAmt = requestMap.get("TransAmt")[0]; // 交易金额
		String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台回调
		String MerPriv = requestMap.get("MerPriv")[0].trim(); // 商户私有域 可选
																// 请求传递的是userId
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名信息
		String OrdDate = requestMap.get("OrdDate")[0]; // 订单日期
		String OutCustId = requestMap.get("OutCustId")[0]; // 出账用户id(汇付)
		String SubOrdId = requestMap.get("SubOrdId")[0]; // 关联流水号
		String SubOrdDate = requestMap.get("SubOrdDate")[0];
		String OutAcctId = requestMap.get("OutAcctId")[0]; // 出账账户 用户在汇付的虚拟资金账号
		String InCustId = requestMap.get("InCustId")[0]; // 入账用户id(汇付)
		String Fee = requestMap.get("Fee")[0]; // 手续费
		String ConsultFee = requestMap.get("ConsultFee")[0]; // 咨询费
		String MerDate = cmdId + RespCode + MerCustId + OrdId + OrdDate + OutCustId + SubOrdId + SubOrdDate + OutAcctId + TransAmt + Fee + InCustId + InAcctId + BgRetUrl + MerPriv;

		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ,数据可能被篡改!";
		}

		if (Convert.strToInt(RespCode, -1) != 0) {
			return requestMap.get("RespDesc")[0] + " RespCode:" + RespCode;
		}

		Connection conn = MySQL.getConnection();
		long result = -1L;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Map<String, String> mapacc = borrowDao.queryBorrowCostByPayId(conn, Convert.strToLong(OrdId, -1));
			String feelog = Convert.strToStr(mapacc.get("feelog"), "");
			Map<String, Double> feeMap = (Map<String, Double>) JSONObject.toBean(JSONObject.fromObject(feelog), HashMap.class);
			double investFeeRate = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE) + "", 0);
			Procedures.p_borrow_repayment(conn, ds, outParameterValues, Convert.strToLong(OrdId, -1), Convert.strToLong(MerPriv.substring(MerPriv.indexOf("=") + 1), -1), "",
					basePath, new Date(), new BigDecimal(investFeeRate), new DecimalFormat("0.00").format(TransAmt), new DecimalFormat("0.00").format(Fee), new DecimalFormat(
							"0.00").format(ConsultFee), -1, "");
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (result > 0) {
				conn.commit();
				return "还款成功!";
			}
			conn.rollback();
			return outParameterValues.get(1) + "";
		} catch (Exception e) {
			conn.rollback();
			log.info(e);
			return "还款出现异常!";
		} finally {
			conn.close();
		}
	}

	/**
	 * 满标审核处理
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> borrowFullDeal(Connection conn, long id, long status, String auditOpinion, long adminId, String basePath) throws Exception {
		if (auditOpinion == null)
			auditOpinion = "";
		double investFeeRate = 0;// 投资管理费
		double borrowFeeHhn = 0;// 合和年借款管理费
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String identify = id + "_" + System.currentTimeMillis() + "";
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		List<Object> outParameters = new ArrayList<Object>();

		// 满标审核前判断处理
		Procedures.p_borrow_auth_fullscale(conn, ds, outParameterValues, id, status, -1, "", new BigDecimal(0.00), new BigDecimal(0.00), 0, 0, 0);
		ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
		if (ret <= 0) {
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			return map;
		}
		// 审核通过才生成还款记录
		if (ret == 4) {
			double borrowAmount = Convert.strToDouble(outParameterValues.get(2) + "", 0);
			double annualRate = Convert.strToDouble(outParameterValues.get(3) + "", 0);
			int deadline = Convert.strToInt(outParameterValues.get(4) + "", 0);
			int isDayThe = Convert.strToInt(outParameterValues.get(5) + "", 1);

			// 生成还款记录
			List<Map<String, Object>> repayMapList = null;
			AmountUtil au = new AmountUtil();
			// 等额本金还款 合和年只有此种还款方式
			repayMapList = (List<Map<String, Object>>) au.earnCalculateMonthHhn(borrowAmount, deadline, isDayThe, annualRate);
			String repayPeriod = ""; // 还款期数
			double stillPrincipal = 0;
			double stillInterest = 0;
			double principalBalance = 0; // 剩余本金
			double interestBalance = 0; // 剩余利息
			double totalSum = 0; // 本息余额
			double totalAmount = 0; // 还款总额
			double mRate = 0; // 月利率
			double consultFee = 0;// 咨询费
			String repayDate = "";
			int count = 1;
			for (Map<String, Object> paymentMap : repayMapList) {
				repayPeriod = paymentMap.get("deadline") + "";
				stillPrincipal = Convert.strToDouble(paymentMap.get("monPayAmount") + "", 0);
				stillInterest = Convert.strToDouble(paymentMap.get("monPayRate") + "", 0);
				principalBalance = Convert.strToDouble(paymentMap.get("principalBalance") + "", 0);
				interestBalance = Convert.strToDouble(0.00 + "", 0);
				totalSum = Convert.strToDouble(0.00 + "", 0);
				totalAmount = Convert.strToDouble(paymentMap.get("totalAmount") + "", 0);
				repayDate = Convert.strToStr(paymentMap.get("repayDate") + "", "");
				mRate = Convert.strToDouble(paymentMap.get("monthRate") + "", 0);
				consultFee = Convert.strToDouble(paymentMap.get("iManageFee") + "", 0);
				// 添加预还款记录
				ret = repamentDao.addPreRepament(conn, id, identify, repayPeriod, stillPrincipal, stillInterest, principalBalance, interestBalance, totalSum, totalAmount, mRate,
						repayDate, count, consultFee);
				count++;
				if (ret <= 0) {
					break;
				}
			}
			if (ret <= 0) {
				map.put("ret", ret + "");
				map.put("ret_desc", "执行失败");
				return map;
			}
			// 查询借款信息得到借款时插入的平台收费标准
			Map<String, String> mapacc = borrowDao.queryBorrowCost(conn, id);
			String feelog = Convert.strToStr(mapacc.get("feelog"), "");
			Map<String, Double> feeMap = (Map<String, Double>) JSONObject.toBean(JSONObject.fromObject(feelog), HashMap.class);
			investFeeRate = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE) + "", 0);
			borrowFeeHhn = Convert.strToDouble(feeMap.get(IAmountConstants.BORROW_FEE_HHN) + "", 0);
		}

		// 满标审核处理
		Procedures.p_borrow_deal_fullscale(conn, ds, outParameters, id, adminId, status, sf.format(new Date()), auditOpinion, identify, basePath, investFeeRate, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, borrowFeeHhn, -1, "");

		ret = Convert.strToLong(outParameters.get(0) + "", -1);
		if (ret > 0 && status == 4) {
			// 添加系统操作日志
			adminMap = adminDao.queryAdminById(conn, adminId);
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(adminMap.get("userName"), ""), IConstants.UPDATE, Convert.strToStr(adminMap.get("lastIP"), ""), 0,
					"满标复审通过", 2);
			// 提成奖励
			String sql = " select DISTINCT a.id as id,a.investor as userId,a.realAmount as realAmount,c.publisher as publisher from t_invest a left join t_repayment b on a.borrowId = b.borrowId  left join t_borrow c on a.borrowId = c.id ";
			DataSet ds1 = MySQL.executeQuery(conn, sql + "  where c.id =" + id);
			ds1.tables.get(0).rows.genRowsMap();
			List<Map<String, Object>> list = ds1.tables.get(0).rows.rowsMap;
			for (Map<String, Object> map2 : list) {
				long uId = Convert.strToLong(map2.get("userId") + "", -1);
				long investId = Convert.strToLong(map2.get("id") + "", -1);
				Object obj = map2.get("realAmount");
				BigDecimal amounts = BigDecimal.ZERO;
				if (obj != null)
					amounts = new BigDecimal(obj + "");
				ret = awardService.updateMoneyNew(conn, uId, amounts, IConstants.MONEY_TYPE_1, investId);
			}
			map.put("ret", ret + "");
			map.put("ret_desc", outParameters.get(1) + "");
		}
		return map;
	}

	/**
	 * 资金解冻--后台返回
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @throws SQLException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String usrFreezeBgResp(Map<String, String[]> requestMap, String cmdId, String pgKeyFile, String basePath) throws SQLException {
		String CmdId = requestMap.get("CmdId")[0]; // 消息类型
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String RespDesc = requestMap.get("RespDesc")[0]; // 应答描述
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户号
		String OrdId = requestMap.get("OrdId")[0]; // 订单号
		String OrdDate = requestMap.get("OrdDate")[0]; // 订单日期
		String TrxId = requestMap.get("TrxId")[0]; // 商户专属平台 3 交易唯一标识 定长 18 位
													// 组成规则为：8 位商户专属平台日期+10
													// 位系统流水号

		String RetUrl = requestMap.get("RetUrl")[0].trim(); // 前台返回
		String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台返回
		String MerPriv = requestMap.get("MerPriv")[0].trim(); // 商户私有域 可选
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名信息
		String MerDate = CmdId + RespCode + MerCustId + OrdId + OrdDate + TrxId + RetUrl + BgRetUrl + MerPriv;// 签名信息

		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ,数据可能被篡改!";
		}

		if (Convert.strToInt(RespCode, -1) != 0) {
			return RespDesc + " RespCode:" + RespCode;
		}

		// Connection conn = MySQL.getConnection();
		// long result = -1L;
		// try {
		// // 放款业务处理
		// Map<String, String> map = borrowFullDeal(conn,
		// Convert.strToLong(OrdId, -1), 4, "",
		// Convert.strToLong(MerPriv.substring(MerPriv.indexOf("=" + 1)), -1),
		// basePath);
		// result = Convert.strToLong(map.get(0), -1);
		// if (result > 0) {
		// conn.commit();
		// return "放款成功!";
		// }
		// conn.rollback();
		// return map.get(1);
		// } catch (Exception e) {
		// conn.rollback();
		// log.info(e);
		// return "放款出现异常!";
		// } finally {
		// conn.close();
		// }
		return null;
	}

	/**
	 * 前台用户查询余额--返回数据
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @throws SQLException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String queryBalanceResp(Map<String, String[]> requestMap, String cmdId, String pgKeyFile, String basePath) throws SQLException {
		String CmdId = requestMap.get("CmdId")[0]; // 消息类型
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String RespDesc = requestMap.get("RespDesc")[0]; // 应答描述
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户号
		String UsrCustId = requestMap.get("UsrCustId")[0]; // 客户号
		String AvlBal = requestMap.get("AvlBal")[0]; // 可用余额
		String AcctBal = requestMap.get("AcctBal")[0]; // 账户余额
		String FrzBal = requestMap.get("FrzBal")[0]; // 冻结金额

		String ChkValue = requestMap.get("ChkValue")[0]; // 签名信息
		String MerDate = CmdId + RespCode + MerCustId + UsrCustId + AvlBal + AcctBal + FrzBal;// 签名信息

		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ,数据可能被篡改!";
		}
		if (Convert.strToInt(RespCode, -1) != 0) {
			return RespDesc + " RespCode:" + RespCode;
		}

		// Connection conn = MySQL.getConnection();
		// long result = -1L;
		// try {
		// // 放款业务处理
		// Map<String, String> map = borrowFullDeal(conn,
		// Convert.strToLong(OrdId, -1), 4, "",
		// Convert.strToLong(MerPriv.substring(MerPriv.indexOf("=" + 1)), -1),
		// basePath);
		// result = Convert.strToLong(map.get(0), -1);
		// if (result > 0) {
		// conn.commit();
		// return "放款成功!";
		// }
		// conn.rollback();
		// return map.get(1);
		// } catch (Exception e) {
		// conn.rollback();
		// log.info(e);
		// return "放款出现异常!";
		// } finally {
		// conn.close();
		// }
		return null;
	}

	/**
	 * 后台商户查询余额--返回数据
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @throws SQLException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String queryBalanceBgResp(Map<String, String[]> requestMap, String cmdId, String pgKeyFile, String basePath) throws SQLException {
		String CmdId = requestMap.get("CmdId")[0]; // 消息类型
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String RespDesc = requestMap.get("RespDesc")[0]; // 应答描述
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户号
		// String AcctDetails = requestMap.get("AcctDetails")[0]; // 财务结果串

		String ChkValue = requestMap.get("ChkValue")[0]; // 签名信息
		String MerDate = CmdId + RespCode + MerCustId;// 签名信息

		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ,数据可能被篡改!";
		}
		if (Convert.strToInt(RespCode, -1) != 0) {
			return RespDesc + " RespCode:" + RespCode;
		}
		return null;
	}

	/**
	 * 投标撤销--返回数据--验证签名
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @throws SQLException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String tenderCancleResp(Map<String, String[]> requestMap, String cmdId, String pgKeyFile, String basePath) throws SQLException {
		String CmdId = requestMap.get("CmdId")[0]; // 消息类型
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String RespDesc = requestMap.get("RespDesc")[0]; // 应答描述
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户号
		String OrdId = requestMap.get("OrdId")[0];
		String OrdDate = requestMap.get("OrdDate")[0];
		String TransAmt = requestMap.get("TransAmt")[0];
		String UsrCustId = requestMap.get("UsrCustId")[0];
		String RetUrl = requestMap.get("RetUrl")[0];
		String BgRetUrl = requestMap.get("BgRetUrl")[0];
		String MerPriv = requestMap.get("MerPriv")[0];
		if (Convert.strToInt(RespCode, -1) != 0) {
			return RespDesc + " RespCode:" + RespCode;
		}

		String ChkValue = requestMap.get("ChkValue")[0]; // 签名信息
		String MerDate = CmdId + RespCode + MerCustId + OrdId + OrdDate + TransAmt + UsrCustId + RetUrl + BgRetUrl + MerPriv;// 签名信息

		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ,数据可能被篡改!";
		}

		long result = -1;
		try {
			result = borrowManageService.updateBring(OrdId);
		} catch (Exception e) {
			log.info(e);
		}
		if (result > 0) {
			return "撤销成功";
		}
		return "撤销失败";
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public void setRechargeDetailDao(RechargeDetailDao rechargeDetailDao) {
		this.rechargeDetailDao = rechargeDetailDao;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public void setSecureLink(SecureLink secureLink) {
		this.secureLink = secureLink;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public void setRepamentDao(RepamentDao repamentDao) {
		this.repamentDao = repamentDao;
	}

	/**
	 * 汇付 用户注册 签名验证
	 */
	public String userRegister(Map<String, String[]> requestMap, String CmdId, String pgKeyFile) {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户客户号
		String UsrId = requestMap.get("UsrId")[0]; // 用户号(合和年)
		String UsrCustId = requestMap.get("UsrCustId")[0]; // 用户号(汇付)
		String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台返回

		String TrxId = requestMap.get("TrxId")[0]; // 随机唯一流水号
		String RetUrl = requestMap.get("RetUrl")[0]; // 前台返回
		String MerPriv = requestMap.get("MerPriv")[0]; // 商户私有域
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名
		String MerDate = CmdId + RespCode + MerCustId + UsrId + UsrCustId + BgRetUrl + TrxId + RetUrl + MerPriv;
		try {
			// 验证签名
			if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
				return "签名错误 ! ";
			} else if (Convert.strToInt(RespCode, -1) == 0) {
				return "ok";
			} else {
				return requestMap.get("RespDesc")[0] + " RespCode:" + RespCode;
			}
		} catch (Exception e) {
			log.info(e);
			return "签名验证异常";
		}
	}

	/**
	 * 失败后删除本地记录
	 * 
	 * @throws SQLException
	 */
	public void userRegisterFail(String userId) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			int ret = userDao.deleteUser(conn, userId, "");
			if (ret > 0) {
				conn.commit();
				return;
			}
			conn.rollback();
		} catch (DataException e) {
			conn.rollback();
			log.info(e);
		} finally {
			conn.close();
		}
	}

	/**
	 * 汇付充值失败后 修改充值详细表
	 */
	public void netSaveFail(String ordId) throws SQLException {
		long id = Convert.strToLong(ordId, -1);
		if (id < 0)
			return;
		Connection conn = MySQL.getConnection();
		try {
			long ret = rechargeDetailDao.updateRechargeDetailHHN(conn, id, 0);
			if (ret > 0) {
				conn.commit();
			}
		} catch (Exception e) {
			conn.rollback();
			log.info(e);
		} finally {
			conn.close();
		}
	}

	/**
	 * 汇付 冻结 更新冻结资金
	 * 
	 * @throws SQLException
	 */
	public String UsrFreezeBg(Map<String, String[]> requestMap, String cmdId, String pgKeyFile, String basePath) throws SQLException {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户客户号
		String UsrCustId = requestMap.get("UsrCustId")[0]; // 用户号(汇付)
		String SubAcctType = "";
		String SubAcctId = "";
		String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台返回
		String OrdId = requestMap.get("OrdId")[0]; // 随机唯一流水号
		String OrdDate = requestMap.get("OrdDate")[0]; // 随机唯一流水号
		String TransAmt = requestMap.get("TransAmt")[0]; // 随机唯一流水号
		String RetUrl = ""; // 前台返回
		String MerPriv = requestMap.get("MerPriv")[0]; // 商户私有域
		String TrxId = requestMap.get("TrxId")[0]; // 商户私有域
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名
		String MerDate = cmdId + RespCode + MerCustId + UsrCustId + SubAcctType + SubAcctId + OrdId + OrdDate + TransAmt + RetUrl + BgRetUrl + TrxId + MerPriv;
		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ! ";
		}
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Connection conn = MySQL.getConnection();
		try {
			Procedures.p_borrow_join_call_back(conn, ds, outParameterValues, Convert.strToLong(OrdId, -1), new BigDecimal(TransAmt), basePath);
			long ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (ret <= 0) {
				conn.rollback();
				return "资金冻结失败";
			} else {
				conn.commit();
				return "资金冻结成功";
			}
		} catch (Exception e) {
			log.info(e);
			return "资金冻结异常";
		} finally {
			conn.close();
		}
	}

	/**
	 * 汇付 更新冻结资金 解冻
	 * 
	 * @throws SQLException
	 */
	public String UsrUnFreeze(Map<String, String[]> requestMap, String cmdId, String pgKeyFile, String basePath) throws SQLException {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户客户号
		String UsrCustId = requestMap.get("UsrCustId")[0]; // 用户号(汇付)
		String SubAcctType = "";
		String SubAcctId = "";
		String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台返回
		String OrdId = requestMap.get("OrdId")[0]; // 随机唯一流水号
		String OrdDate = requestMap.get("OrdDate")[0]; // 随机唯一流水号
		String TransAmt = requestMap.get("TransAmt")[0]; // 随机唯一流水号
		String RetUrl = ""; // 前台返回
		String MerPriv = requestMap.get("MerPriv")[0]; // 商户私有域
		String TrxId = requestMap.get("TrxId")[0]; // 商户私有域
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名
		String MerDate = cmdId + RespCode + MerCustId + UsrCustId + SubAcctType + SubAcctId + OrdId + OrdDate + TransAmt + RetUrl + BgRetUrl + TrxId + MerPriv;
		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ! ";
		}

		Connection conn = MySQL.getConnection();
		try {
			long ret = chinaPnrDao.updateUserFreezeSum(conn, UsrCustId, TransAmt);
			if (ret > 0) {
				conn.commit();
				return "资金冻结成功";
			}
			conn.rollback();
			return "资金冻结失败";
		} catch (Exception e) {
			log.info(e);
			return "资金冻结异常";
		} finally {
			conn.close();
		}
	}

	public String cashCallBack(Map<String, String[]> requestMap, String CmdId, String pgKeyFile) {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户客户号
		String OrdId = requestMap.get("OrdId")[0];
		String UsrCustId = requestMap.get("UsrCustId")[0]; // 用户号(汇付)
		String TransAmt = requestMap.get("TransAmt")[0]; //

		String OpenAcctId = requestMap.get("OpenAcctId")[0];
		String OpenBankId = requestMap.get("OpenBankId")[0];
		String RetUrl = requestMap.get("RetUrl")[0]; // 前台返回
		String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台返回
		String MerPriv = requestMap.get("MerPriv")[0]; // 商户私有域

		String ChkValue = requestMap.get("ChkValue")[0]; // 签名
		String MerDate = CmdId + RespCode + MerCustId + OrdId + UsrCustId + TransAmt + OpenAcctId + OpenBankId + RetUrl + BgRetUrl + MerPriv;
		try {
			// 验证签名
			if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
				return "签名错误 ! ";
			} else if (Convert.strToInt(RespCode, -1) == 0) {
				return "ok";
			} else {
				return requestMap.get("RespDesc")[0] + " RespCode:" + RespCode;
			}
		} catch (Exception e) {
			log.info(e);
			return "签名验证异常";
		}
	}

	public long cashFail(String ordId) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {

			long ret = chinaPnrDao.deleteWithDraw(conn, ordId);
			if (ret > 0) {
				conn.commit();
				return ret;
			}
			conn.rollback();
		} catch (Exception e) {
			conn.rollback();
			log.info(e);
		} finally {
			conn.close();
		}
		return -1L;
	}

	/**
	 * 删除投资记录
	 * 
	 * @param ordId
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long deleteBorrowInvest(String ordId) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {

			long ret = chinaPnrDao.deleteBorrowInvest(conn, ordId);
			if (ret > 0) {
				conn.commit();
				return ret;
			}
			conn.rollback();
		} catch (Exception e) {
			conn.rollback();
			log.info(e);
		} finally {
			conn.close();
		}
		return -1L;
	}

	/**
	 * 汇付 冻结 验证签名
	 */
	public String InitiativeTenderBg(Map<String, String[]> requestMap, String CmdId, String pgKeyFile) throws SQLException {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		if (Convert.strToInt(RespCode, -1) < 0) {
			return requestMap.get("RespDesc")[0];
		}
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户客户号
		String OrdId = requestMap.get("OrdId")[0]; // 随机唯一流水号
		String OrdDate = requestMap.get("OrdDate")[0];
		String TransAmt = requestMap.get("TransAmt")[0];
		String UsrCustId = requestMap.get("UsrCustId")[0]; // 用户号(汇付)
		String TrxId = requestMap.get("TrxId")[0]; // 商户私有域
		String RetUrl = requestMap.get("RetUrl")[0]; // 前台返回
		String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台返回
		String MerPriv = requestMap.get("MerPriv")[0]; // 商户私有域
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名
		String MerDate = CmdId + RespCode + MerCustId + OrdId + OrdDate + TransAmt + UsrCustId + TrxId + RetUrl + BgRetUrl + MerPriv;
		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ! ";
		}
		return RespCode = "ok";
	}

	/**
	 * 更新取现表的 TrxId(汇付返回)
	 */
	public void updateCashTrxId(String ordId, String trxId) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			chinaPnrDao.updateCashTrxId(conn, ordId, trxId);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.info(e);
		} finally {
			conn.close();
		}
	}

	/**
	 * 插入取现的 资金记录
	 */
	public void insertMoney(String fundMode, String remarks, String income, String spending, String type, String userId) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			chinaPnrDao.insertMoney(conn, fundMode, remarks, income, spending, type, userId);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.info(e);
		} finally {
			conn.close();
		}
	}

	/**
	 * 更新投资表的 TrxId(汇付返回)
	 */
	public void updateInvestTrxId(String ordId, String trxId) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = chinaPnrDao.updatInvestTrxId(conn, ordId, trxId);
			if (result > 0)
				conn.commit();
		} catch (Exception e) {
			log.info(e);
		} finally {
			conn.close();
		}
	}

	/**
	 * 后台添加管理员
	 * 
	 */
	public String BgRegisterResp(Map<String, String[]> requestMap, String CmdId, String pgKeyFile, String basePath) throws SQLException {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		String RespDesc = requestMap.get("RespDesc")[0]; //
		if (Convert.strToInt(RespCode, -1) < 0) {
			return RespDesc;
		}
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户号
		String UsrId = requestMap.get("UsrId")[0]; // 用户ID
		String UsrCustId = requestMap.get("UsrCustId")[0]; // 商户客户号
		String MerPriv = requestMap.get("MerPriv")[0].trim(); // 商户私有域
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名信息
		String MerDate = CmdId + RespCode + MerCustId + UsrId + UsrCustId + MerPriv;

		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ,数据可能被篡改!";
		}

		if (Convert.strToInt(RespCode, -1) != 0) {
			return requestMap.get("RespDesc")[0] + " RespCode:" + RespCode;
		}

		Connection conn = MySQL.getConnection();
		try {
			// adminService.addAdmin(userName, password, enable, roleId,
			// realName, telphone, qq, email, img, isLeader);
			return "添加管理成功!";
		} catch (Exception e) {
			conn.rollback();
			log.info(e);
			return "添加管理员异常!";
		} finally {
			conn.close();
		}
	}

	/**
	 * 汇付 交易状态查询 验证签名
	 */
	public String queryTradeStatusBg(Map<String, String[]> requestMap, String CmdId, String pgKeyFile) throws SQLException {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		if (Convert.strToInt(RespCode, -1) < 0) {
			return requestMap.get("RespDesc")[0];
		}
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户客户号
		String OrdId = requestMap.get("OrdId")[0]; // 随机唯一流水号
		String OrdDate = requestMap.get("OrdDate")[0];
		String TrxId = requestMap.get("TrxId")[0]; // 商户私有域
		String RetUrl = requestMap.get("RetUrl")[0]; // 前台返回
		String BgRetUrl = requestMap.get("BgRetUrl")[0]; // 后台返回
		String MerPriv = requestMap.get("MerPriv")[0]; // 商户私有域
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名
		String MerDate = CmdId + RespCode + MerCustId + OrdId + OrdDate + TrxId + RetUrl + BgRetUrl + MerPriv;
		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ! ";
		}
		return RespCode = "ok";
	}

	/**
	 * 汇付 商户子账户查询 验证签名
	 */
	public String queryAcctsBg(Map<String, String[]> requestMap, String CmdId, String pgKeyFile) throws SQLException {
		String RespCode = requestMap.get("RespCode")[0]; // 应答返回码
		if (Convert.strToInt(RespCode, -1) < 0) {
			return requestMap.get("RespDesc")[0];
		}
		String MerCustId = requestMap.get("MerCustId")[0]; // 商户客户号
		String ChkValue = requestMap.get("ChkValue")[0]; // 签名
		String MerDate = CmdId + RespCode + MerCustId;
		// 验证签名
		if (secureLink.VeriSignMsg(pgKeyFile, MerDate, ChkValue) != 0) {
			return "签名错误 ! ";
		}
		return RespCode = "ok";
	}

	/** 添加用户绑卡 */
	public long addBankCard(String sql) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = MySQL.executeNonQuery(conn, sql);
			if (result > 0)
				conn.commit();
		} catch (Exception e) {
			log.info(e);
		} finally {
			conn.close();
		}
		return result;
	}

	/** 添加或更新企业开户 **/
	public void doCorpRegister(String usrId, String usrName, String UsrCustId, String auditStat, String auditDesc, String openBankId, String cardId) throws Exception {
		Connection conn = MySQL.getConnection();
		long usrCustId = Convert.strToLong(UsrCustId, 0);
		try {
			String sql = "select count(1) count from t_company where id=" + usrId;
			Map<String, String> result = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, sql));
			if (result != null && Convert.strToInt(result.get("count"), 0) > 0) {// 存在记录则更新
				sql = "update t_company set name='" + usrName + "',usrCustId=" + usrCustId + ",auditStat='" + auditStat + "',auditDesc='" + auditDesc + "' where id=" + usrId;
			} else {// 否则插入
				sql = "insert into t_company (name,usrCustId,auditStat,auditDesc) values ('" + usrName + "'," + usrCustId + ",'" + auditStat + "','" + auditDesc + "')";
			}
			MySQL.executeNonQuery(conn, sql);
			if ("开户成功".equals(auditStat)) {// 开户成功,则插入到admin表
				sql = "select count(1) count from t_admin where id=" + usrId;
				result = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, sql));
				if (result != null && Convert.strToInt(result.get("count"), 0) == 0) {
					sql = "insert into t_admin (userName,usrCustId,realName,addDate,password,roleId) values ('" + usrName + "'," + usrCustId + ",'" + usrName
							+ "',now(),'371821df11665276e5b9b74fec86226a',0)";
					MySQL.executeNonQuery(conn, sql);
				}

				sql = "select count(1) count from t_user where id=" + usrId;
				result = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, sql));
				if (result != null && Convert.strToInt(result.get("count"), 0) == 0) {
					sql = "insert into t_user (id,username,usrCustId,`password`,dealpwd,authStep) values (" + usrId + ",'" + usrName + "'," + usrCustId
							+ ",'371821df11665276e5b9b74fec86226a','371821df11665276e5b9b74fec86226a',5)";
					MySQL.executeNonQuery(conn, sql);
				}

				sql = "select count(1) count from t_person where userId=" + usrId;
				result = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, sql));
				if (result != null && Convert.strToInt(result.get("count"), 0) == 0) {
					sql = "insert into t_person (userId) values (" + usrId + ")";
					MySQL.executeNonQuery(conn, sql);
				}
				if (!StringUtils.isBlank(cardId)) {
					sql = "select count(1) count from t_bankcard where userId=" + usrId + " and cardNo='" + cardId + "'";
					result = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, sql));
					if (result != null && Convert.strToInt(result.get("count"), 0) == 0) {
						sql = "insert into t_bankcard (bankName,openBankId,userId,cardNo,cardStatus,commitTime) values ('" + ChinaPnrConfig.bankMap.get(openBankId) + "','"
								+ openBankId + "'," + usrId + ",'" + cardId + "',1,now())";
						MySQL.executeNonQuery(conn, sql);
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			log.info(e);
		} finally {
			conn.close();
		}
	}

		/**
	 * 根据用户ordId查询提现记录流水号
	 * @param conn
	 * @param userid
	 * @return
		 * @throws SQLException 
	 */
	public String queryWithdrawTrxId( long ordId) throws Exception {
		Connection conn = MySQL.getConnection();
		String trxIdstr="";
		try {
			String cmd = "select trxId from t_withdraw where id = " + ordId ;
			Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, cmd));
			if (map == null || map.size() == 0)
				return "";
			trxIdstr = map.get("trxId");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return trxIdstr;
	}

}
