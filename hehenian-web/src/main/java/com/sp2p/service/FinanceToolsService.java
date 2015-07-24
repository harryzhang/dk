package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.util.ExcelRateUtil;
import com.sp2p.dao.PhoneInfoDao;

/**
 * @ClassName: FinanceToolsService.java
 * @Author: li.hou
 * @Descrb: 我要理财，工具箱
 */
public class FinanceToolsService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);

	private PhoneInfoDao phoneInfoDao = null;
	@SuppressWarnings("unused") 
	private int[] dn = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 收益计算器，按月还款
	 * 
	 * @param borrowSum
	 * @param yearRate
	 * @param borrowTime
	 *            以月为单位
	 */
	public List<Map<String, Object>> rateIncome2Month(double borrowSum, double yearRate, int borrowTime, double bidReward, double bidRewardMoney) {
		if (borrowSum < 0 || yearRate < 0 || borrowTime < 0) {
			return null;
		}
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.00");
		double monRate = yearRate / 12;// 月利率
		int monTime = borrowTime;
		double val1 = borrowSum * monRate * Math.pow((1 + monRate), monTime);
		double val2 = Math.pow((1 + monRate), monTime) - 1;
		double monRepay = val1 / val2;// 每月偿还金额
		double allSum = Double.valueOf(df.format(monRepay)) * monTime;// 还款本息总额
		bidRewardMoney = Double.parseDouble(df.format(bidRewardMoney));
		double rewardSum = Double.parseDouble(df.format(borrowSum * bidReward / 100)) + bidRewardMoney;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reward", String.format("%.2f", rewardSum));

		double netIncome = allSum - Double.valueOf(df.format(Double.valueOf(df.format(allSum - borrowSum)) * 0.1));

		map.put("allSum", String.format("%.2f", allSum));
		map.put("monPay", String.format("%.2f", monRepay));
		map.put("netIncome", String.format("%.2f", netIncome));// map.put("netIncome",
																// df.format(allSum*0.9f));
		map.put("rateSum", String.format("%.2f", allSum - borrowSum));

		/**
		 * 年化收益
		 */
		double rateVal = ExcelRateUtil.excelRate((borrowSum - rewardSum), Double.parseDouble(df.format(monRepay)), monTime, 200, 10);
		map.put("income2year", df.format(rateVal * 12 * 100));

		lists.add(map);
		return lists;
	}

	/**
	 * 天标计算
	 * 
	 * @param borrowSum
	 * @param yearRate
	 * @param borrowTime
	 * @return
	 */
	public Map<String, Object> rateCalculateDay(double borrowSum, double yearRate, int borrowTime) {
		if (borrowSum < 0 || yearRate < 0 || borrowTime < 0) {
			return null;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		// 月利率
		double i = yearRate * 1f / 12;
		// 所借本金
		double sum = Double.valueOf(df.format(borrowSum));

		// 所还利息
		double monForRate = Convert.strToDouble(df.format(sum * i * borrowTime / 30), 0);

		double val = borrowSum + monForRate;

		Map<String, Object> map = addToMap(borrowTime, val, borrowSum, monForRate, 0, i, val);

		return map;

	}

	/**
	 * 按月还款。等额本息计算
	 */
	public List<Map<String, Object>> rateCalculate2Month(double borrowSum, double yearRate, int borrowTime) {
		if (borrowSum < 0 || yearRate < 0 || borrowTime < 0) {
			return null;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		// 月利率
		double i = yearRate * 1f / 12;
		double val1 = borrowSum * i * Math.pow((1 + i), borrowTime);
		double val2 = Math.pow((1 + i), borrowTime) - 1;
		// 每月还款
		double monPay = val1 / val2;
		// 所借本金
		double sum = Double.valueOf(df.format(borrowSum));
		// 月还利息 = 剩余本金*月利率
		double monForRate = 0;
		// 月还本金=每月还款-月还利息
		double monForA = 0;
		// 每月还款，保留两位小数
		double monPay2 = Convert.strToDouble(df.format(monPay), 0);

		double allSum = monPay2 * borrowTime;// monPay * borrowTime;//还款本息总额
		double payA = 0; // add 2013-04-19
		// 本息余额
		double payRemain = Double.parseDouble(df.format(allSum));
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		double val = 0;
		try {
			for (int j = 1; j <= borrowTime; j++) {
				monForRate = Convert.strToDouble(df.format(sum * i), 0);
				monForA = Convert.strToDouble(df.format(monPay2 - monForRate), 0);
				val = Convert.strToDouble(df.format(monPay2 - monForRate), 0);
				sum = Convert.strToDouble(df.format(sum - val), 0);
				if (j == borrowTime) {
					monPay2 = payRemain;
					// 最后一个月要还的本金（总借款本金 - 已还的本金） add 2013-04-19
					monForA = borrowSum - payA;// Convert.strToDouble(df.format(monPay2
												// - monForRate),0);
					monForRate = monPay2 - monForA;
				}
				payA += monForA;
				payRemain = Convert.strToDouble(df.format(payRemain - monPay2), 0);
				if (j == borrowTime) {
					payRemain = 0;
				}
				// i*100 月利率以百分比显示
				map = addToMap(j, monPay2, monForA, monForRate, payRemain, Convert.strToDouble(df.format(i * 100), 0), Convert.strToDouble(df.format(allSum), 0));
				lists.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 按月还款，每月付息，到期还本
	 * 
	 * @param borrowSum
	 * @param yearRate
	 * @param borrowTime
	 * @return
	 */
	public List<Map<String, Object>> rateCalculate2Sum(double borrowSum, double yearRate, int borrowTime) {
		if (borrowSum < 0 || yearRate < 0 || borrowTime < 0) {
			return null;
		}

		DecimalFormat df = new DecimalFormat("0.00");
		int mon = borrowTime;
		// 月利率
		double i = yearRate * 1.0f / 12;
		borrowSum = Double.parseDouble(df.format(borrowSum));
		// 每月还息 = 借款金额*月利率
		double monPayRate = Double.parseDouble(df.format(borrowSum * i));// borrowSum
																			// *
																			// i;//
		double allSum = monPayRate * mon + borrowSum;// 还款本息总额
		// 本息余额
		double payRemain = Double.parseDouble(df.format(allSum));
		double monForA = 0;
		double monForRateA = 0;
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			for (int j = 1; j <= mon; j++) {
				payRemain = Convert.strToDouble(df.format(payRemain - monPayRate), 0);
				// 除了最后一个月，其余月份还的本息就是月还的利息
				monForRateA = monPayRate;
				if (j == mon) {
					// 最后一个月还本金
					monForRateA = Convert.strToDouble(df.format(borrowSum + monPayRate), 0);
					monForA = Convert.strToDouble(df.format(borrowSum), 0);
					payRemain = 0;
				}
				// i*100 月利率以百分比显示
				map = addToMap(j, monForRateA, monForA, monPayRate, payRemain, Convert.strToDouble((df.format(i * 100)), 0), Convert.strToDouble((df.format(allSum)), 0));
				lists.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 
	 * @param mon
	 *            月份
	 * @param monPay
	 *            月还本息
	 * @param monForA
	 *            月还本金
	 * @param monForRate
	 *            月还利息
	 * @param payRemain
	 *            本息余额
	 * @param monRate
	 *            月利率
	 * @param allPay
	 *            总还本息
	 * @return
	 */
	private Map<String, Object> addToMap(int mon, double monPay, double monForA, double monForRate, double payRemain, double monRate, double allPay) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mon", mon);
		// 保留两位小数，不够两位小数的以0补齐
		map.put("monForRateA", String.format("%.2f", monPay));
		map.put("monForA", String.format("%.2f", monForA));
		map.put("monForRate", String.format("%.2f", monForRate));
		map.put("rateARemain", String.format("%.2f", payRemain));
		map.put("monRate", String.format("%.2f", monRate));
		map.put("allPay", String.format("%.2f", allPay));
		return map;
	}

	/**
	 * 收益计算器 先息后本
	 * 
	 * @param borrowSum
	 * @param yearRate
	 * @param borrowTime
	 * @return
	 */
	public List<Map<String, Object>> rateIncome2Sum(double borrowSum, double yearRate, int borrowTime, double bidReward, double bidRewardMoney) {
		if (borrowSum < 0 || yearRate < 0 || borrowTime < 0) {
			return null;
		}
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.00");
		double monRate = yearRate / 12;// 月利率
		int monTime = borrowTime;// * 12;借款期限填月
		borrowSum = Double.parseDouble(df.format(borrowSum));
		double monRepay = Double.parseDouble(df.format(borrowSum * monRate));// 每月偿还金额
		double allSum = Double.parseDouble(df.format((monRepay * monTime))) + borrowSum;// 还款本息总额
		bidRewardMoney = Double.parseDouble(df.format(bidRewardMoney));
		double rewardSum = Double.parseDouble(df.format(borrowSum * bidReward / 100)) + bidRewardMoney;

		// 扣除10%的管理费
		double netIncome = allSum - Double.valueOf(df.format(Double.valueOf(df.format(allSum - borrowSum)) * 0.1));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reward", String.format("%.2f", rewardSum));
		map.put("allSum", String.format("%.2f", allSum));
		map.put("monPay", String.format("%.2f", monRepay));
		map.put("netIncome", String.format("%.2f", netIncome));
		map.put("rateSum", String.format("%.2f", allSum - borrowSum));

		/**
		 * 年化收益
		 */
		double rateVal = ExcelRateUtil.rateTotal(allSum, (borrowSum - rewardSum), borrowTime);
		map.put("income2year", String.format("%.2f", rateVal * 100));

		lists.add(map);
		return lists;
	}

	/**
	 * 一次还款
	 */
	public List<Map<String, Object>> rateCalculate2SumOne(double borrowSum, double yearRate, int borrowTime) {
		if (borrowSum < 0 || yearRate < 0 || borrowTime < 0) {
			return null;
		}

		DecimalFormat df = new DecimalFormat("0.00");
		int mon = borrowTime;
		// 月利率
		double i = yearRate / 12;
		borrowSum = Double.parseDouble(df.format(borrowSum));
		// 每月还息 = 借款金额*月利率
		double monPayRate = Double.parseDouble(df.format(borrowSum * i * 0.01));// borrowSum
																				// *
																				// i;//
		double monPayRateOne = monPayRate;
		double allSum = monPayRate * mon + borrowSum;// 还款本息总额
		// 本息余额
		double payRemain = Double.parseDouble(df.format(allSum));
		double monForA = 0;
		double monForRateA = 0;
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			for (int j = 1; j <= mon; j++) {
				// payRemain = Convert.strToDouble(df.format(payRemain -
				// monPayRate),0);
				payRemain = Convert.strToDouble(df.format(payRemain), 0);
				// 除了最后一个月，其余月份还的本息就是月还的利息
				// monForRateA = monPayRate;
				monForRateA = 0;
				monPayRate = 0;
				if (j == mon) {
					// 最后一个月还本金
					monForRateA = Convert.strToDouble(df.format(borrowSum + monPayRateOne * mon), 0);
					monForA = Convert.strToDouble(df.format(borrowSum), 0);
					payRemain = 0;
					monPayRate = Double.parseDouble(df.format(borrowSum * i * 0.01)) * mon;
				}
				// i*100 月利率以百分比显示
				map = addToMap(j, monForRateA, monForA, monPayRate, payRemain, Convert.strToDouble((df.format(i)), 0), Convert.strToDouble((df.format(allSum)), 0));
				lists.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 根据手机号码查询手机信息
	 * 
	 * @param phoneNum
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getPhoneNumInfo(String phoneNum) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = phoneInfoDao.queryPhoneInfoByNum(conn, phoneNum);
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
	 * 获得bt_config配置表中的静态信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryConfigList() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return phoneInfoDao.queryConfigList(conn, -1, -1);
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

	public PhoneInfoDao getPhoneInfoDao() {
		return phoneInfoDao;
	}

	public void setPhoneInfoDao(PhoneInfoDao phoneInfoDao) {
		this.phoneInfoDao = phoneInfoDao;
	}

	public List<Map<String, Object>> rateCalculateHHN(double borrowSum, double yearRate, int borrowTime) {
		if (borrowSum < 0 || yearRate < 0 || borrowTime < 0) {
			return null;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		borrowSum = Double.valueOf(df.format(borrowSum));

		double monthP = Double.valueOf(df.format(borrowSum / borrowTime));// 月还本金
		double blanceP = borrowSum;// 剩余本金
		double monthI = 0;// 月还利息
		double rate = yearRate / 12;// 月利率
		double allPay = 0;// 利息总额

		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (int i = 0; i < borrowTime; i++) {
			map = new HashMap<String, Object>();
			monthI = Double.valueOf(df.format(blanceP * rate));// 月还利息=剩余本金*月利率
			map.put("mon", i + 1);// 期数
			map.put("monForRateA", df.format(monthP + monthI));// 月还本息
			map.put("monForA", df.format(monthP));// 月还本金
			map.put("monForRate", df.format(monthI));// 月还利息
			lists.add(map);
			allPay += monthI;
			blanceP -= monthP;
		}
		lists.get(0).put("monRate", df.format(rate * 100));// 月利率
		lists.get(0).put("allPay", df.format(borrowSum + allPay));// 还款本息总额
		if (borrowSum != monthP * borrowTime) {// 缺失的本金加到最后一次还款中
			map = lists.get(lists.size() - 1);
			double temp = Double.valueOf(df.format(borrowSum - monthP * borrowTime));
			map.put("monForA", df.format(temp + Double.valueOf(map.get("monForA") + "")));
			map.put("monForRateA", df.format(temp + Double.valueOf(map.get("monForRateA") + "")));
		}

		double allPays = borrowSum + allPay;
		for (int i = 0; i < borrowTime; i++) {// 计算本息余额
			lists.get(i).put("rateARemain", df.format(allPays));
			allPays = allPays - Double.valueOf(lists.get(i).get("monForRateA") + "");
		}

		return lists;
	}
}
