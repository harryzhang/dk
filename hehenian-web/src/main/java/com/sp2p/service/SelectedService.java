package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.SelectedDao;

/**
 * @ClassName: SelectedService.java
 * @Author: gang.lv
 * @Date: 2013-3-6 下午03:19:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 动态下拉列表显示业务层
 */
public class SelectedService extends BaseService {

	public static Log log = LogFactory.getLog(SelectedService.class);

	private SelectedDao selectedDao;
	private SendMessageService sendMessageService;

	public SelectedDao getSelectedDao() {
		return selectedDao;
	}

	public void setSelectedDao(SelectedDao selectedDao) {
		this.selectedDao = selectedDao;
	}

	public List<Map<String, Object>> borrowPurpose() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = selectedDao.borrowPurpose(conn);
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @MethodName: borrowDeadline
	 * @Param: SelectedService
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午04:14:20
	 * @Return:
	 * @Descb: 借款期限下拉列表
	 * @Throws:
	 */
	public List<Map<String, Object>> borrowDeadline() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = selectedDao.borrowDeadline(conn);
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @MethodName: borrowAmountRange
	 * @Param: SelectedService
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午04:14:18
	 * @Return:
	 * @Descb: 借款金额下拉列表
	 * @Throws:
	 */
	public List<Map<String, Object>> borrowAmountRange() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = selectedDao.borrowAmountRange(conn);
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: borrowRaiseTerm
	 * @Param: SelectedService
	 * @Author: gang.lv
	 * @Date: 2013-3-7 下午02:38:14
	 * @Return:
	 * @Descb: 筹标期限下拉列表
	 * @Throws:
	 */
	public List<Map<String, Object>> borrowRaiseTerm() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = selectedDao.borrowRaiseTerm(conn);
		} finally {
			conn.close();
		}
		return list;
	}

	public List<Map<String, Object>> userGroup() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = selectedDao.userGroup(conn);
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @MethodName: sysImageList
	 * @Param: SelectedService
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午07:55:23
	 * @Return:
	 * @Descb: 系统头像列表
	 * @Throws:
	 */
	public List<Map<String, Object>> sysImageList() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = selectedDao.sysImageList(conn);
		} finally {
			conn.close();
		}
		return list;
	}

	public List<Map<String, Object>> getDebtAuctionDays() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = selectedDao.getDebtAuctionDays(conn);
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @MethodName: queryNoticeMode
	 * @Param: SelectedService
	 * @Author: gang.lv
	 * @Date: 2013-4-12 下午08:15:54
	 * @Return:
	 * @Descb: 查询通知类型的通知状态
	 * @Throws:
	 */
	public List<Map<String, Object>> queryNoticeMode(long userId, String noticeMode) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = selectedDao.queryNoticeType(conn, userId, noticeMode);
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @throws SQLException
	 * @MethodName: sendNotice
	 * @Param: SelectedService
	 * @Author: gang.lv
	 * @Date: 2013-4-13 下午07:21:05
	 * @Return:
	 * @Descb: 发送通知
	 * @Throws:
	 */
	public void sendNotice(Connection conn, long userId) throws SQLException {
		// 收到还款提醒
		List<Map<String, Object>> listMap;
		try {
			listMap = selectedDao.queryNoticeType(conn, userId, IConstants.NOTICE_MODE_1);

			// 提醒信息
			List<Map<String, Object>> msgMapList = selectedDao.queryNoticeMSG(conn, userId);
			String status = "1";
			int count = 1;
			String title = "";
			String content = "";
			// 通知方式：1 邮件 2 站内信 3 短信
			for (Map<String, Object> noticeMap : listMap) {
				// 获取提醒状态
				status = String.valueOf(noticeMap.get("flag") == null ? "1" : noticeMap.get("flag"));
				if ("2".equals(status)) {
					for (Map<String, Object> msgMap : msgMapList) {
						title = String.valueOf(msgMap.get("msgTitle"));
						content = String.valueOf(msgMap.get("msgCotent"));
						if (count == 1) {
							// 调用发邮件接口
							sendMessageService.emailSend(title, content, userId);
						} else if (count == 2) {
							// 调用发站内信接口
							sendMessageService.mailSend(title, content, userId);
						} else if (count == 3) {
							// 调用发短信接口
							sendMessageService.noteSend(content, userId);
						}
					}
				}
				count++;// modify by houli 2012-04-23
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
		} finally {
			// 删除通知内容
			selectedDao.delNoticeMSG(conn, userId);
		}
	}

	/**
	 * @MethodName: cancelNoticeMSG
	 * @Param: SelectedService
	 * @Author: gang.lv
	 * @Date: 2013-4-15 下午04:46:40
	 * @Return:
	 * @Descb: 取消发送消息
	 * @Throws:
	 */
	public void cancelNoticeMSG(Connection conn, long userId) throws SQLException {
		// 删除通知内容
		selectedDao.delNoticeMSG(conn, userId);
	}

	/**
	 * @MethodName: sendNoticeMSG
	 * @Param: SelectedService
	 * @Author: gang.lv
	 * @Date: 2013-4-24 下午10:30:24
	 * @Return:
	 * @Descb: 发送通知消息
	 * @Throws:
	 */
	public void sendNoticeMSG(Connection conn, long userId, String msgTitle, Map<String, String> msgContent, String noticeMode) throws SQLException {
		try {
			if (!StringUtils.isBlank(msgContent.get("email") + "")) {// 调用发邮件接口
				sendMessageService.emailSend(conn, msgTitle, msgContent.get("email") + "", userId);
			}
			if (!StringUtils.isBlank(msgContent.get("note") + "")) {// 调用发短信接口,发送短信
				if (!IConstants.ISDEMO.equals("1")) { // 不是演示版本时
					sendMessageService.noteSend(conn, (msgContent.get("note") + "").replaceAll("<br/>", "\n"), userId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		}
	}

	public SendMessageService getSendMessageService() {
		return sendMessageService;
	}

	public void setSendMessageService(SendMessageService sendMessageService) {
		this.sendMessageService = sendMessageService;
	}

}
