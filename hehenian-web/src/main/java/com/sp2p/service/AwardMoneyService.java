package com.sp2p.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shove.Convert;
import com.shove.base.BaseService;

import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;

import com.sp2p.constants.IConstants;
import com.sp2p.dao.AwardMoneydDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.RechargeDao;
import com.sp2p.task.JobTaskDao;
import com.sp2p.util.WebUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 奖励
 * @author Administrator
 *
 */
public class AwardMoneyService extends BaseService {
	public static Log log=LogFactory.getLog(AwardMoneyService.class);
	
	private AwardMoneydDao awardMoneyDao;
	
	private ConnectionManager connectionManager;
	private FundRecordDao fundRecordDao;
	private RechargeDao rechargeDao;
	private JobTaskDao jobTaskDao;
	private SelectedService selectedService;

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public AwardMoneydDao getAwardMoneyDao() {
		return awardMoneyDao;
	}

	public void setAwardMoneyDao(AwardMoneydDao awardMoneyDao) {
		this.awardMoneyDao = awardMoneyDao;
	}
	/**
	 * 添加奖励信息
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param publisher
	 * @param visits
	 * @param state
	 * @param seqNum
	 * @param attachment
	 * @return
	 * @throws SQLException
	 */
	public Long addAwardMoney(Long userId,Double money,String userName,Long id,String recommendName)throws SQLException,DataException{
		
		Connection conn =MySQL.getConnection();//modify by houli  这里用手动提交connectionManager.getConnection();
		Long downloadId=0L,result=0L;
		try {
			downloadId=awardMoneyDao.addMoneyThing(conn, userId, money);
			//-----add by houli 向被奖励的人添加资金记录
			String basePath = WebUtil.getBasePath();
			Map<String, String> userAmountMap = rechargeDao
			.queryMoneyRecords(conn, userId);
			if (userAmountMap == null) {
			userAmountMap = new HashMap<String, String>();
			}
			double usableSum = Convert.strToDouble(userAmountMap.get("usableSum")+"", 0)+money;
			String remarks = "您邀请的好友[<a href='" + basePath
			+ "/userMeg.do?id=" + id + "' target='_blank'>"
			+ userName + "</a>]成功申请为vip";
			result = fundRecordDao.addFundRecord(conn, userId,"好友邀请奖励",money,usableSum,
			Convert.strToDouble(userAmountMap.get("freezeSum")+"", 0),Convert.strToDouble(userAmountMap.get("forPI")+"", 0),
			-1,remarks,money,0.0,-1,-1,251,0.0);
			//------更新用户可用余额
			long result3 = rechargeDao.updateFundrecord(conn, userId, money, 100);
			// 更新已奖励状态为已奖励
			long result4 = jobTaskDao.updateRewardStatus(conn,  userId);

			//
			if(downloadId <=0 || result <= 0 || result3<=0 || result4 <=0){
			conn.rollback();
			return -1L;
			}
			//更改风险保障金，更改奖励发放状态
			// 扣除风险保障金
			Map<String,String>riskMap = jobTaskDao.queryRiskBalance(conn);
			if (riskMap == null) {
			riskMap = new HashMap<String, String>();
			}
			double riskBalance = 1000000 + Convert.strToDouble(riskMap
			.get("RISKBALANCE")
			+ "", 0);
			jobTaskDao.spendingRiskAmount(conn, riskBalance, money, -1, id,
			"好友邀请奖励");
			// 消息模版
			Map<String, String> noticeMap = new HashMap<String, String>();
			// 站内信
			noticeMap.put("mail", "您邀请的好友[" + userName
			+ "]已成为VIP会员,在此奖励￥" + money + "元,再接再厉!");
			// 邮件
			noticeMap.put("email", "您邀请的好友[" + userName
			+ "]已成为VIP会员,在此奖励￥" + money + "元,再接再厉!");
			// 短信
			noticeMap.put("note", "尊敬的" + recommendName + ":\n    您邀请的好友["
			+ userName + "]已成为VIP会员,在此奖励￥" + money + "元,再接再厉!");
			 
			selectedService.sendNoticeMSG(conn, userId, "好友邀请奖励",
			noticeMap, IConstants.NOTICE_MODE_5);
			conn.commit();
			//------

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		
		
		return 1L;
		
	}
	
	/**
	 * 更新下载资料
	 * @param id
	 * @param title
	 * @param publishTime
	 * @param state
	 * @param seqNum
	 * @param attachment
	 * @return
	 * @throws SQLException
	 */
	public Long updateAwardMoney(Long id, Long userId,Integer status,Long money,String endData)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long downloadId=1L;
		try {
			downloadId=awardMoneyDao.UpdateMoney(conn, id, userId, status, money, endData);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return downloadId;
	}

	public static void setLog(Log log) {
		AwardMoneyService.log = log;
	}

	public void setRechargeDao(RechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}
	
	public void setJobTaskDao(JobTaskDao jobTaskDao) {
		this.jobTaskDao = jobTaskDao;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}
	
}
