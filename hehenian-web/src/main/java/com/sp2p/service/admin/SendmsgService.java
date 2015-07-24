package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.admin.SendmsgDao;
import com.shove.base.BaseService;

public class SendmsgService extends BaseService {
	public static Log log = LogFactory.getLog(SendmsgService.class);
	private SendmsgDao sendmsgDao;

	public void setSendmsgDao(SendmsgDao sendmsgDao) {
		this.sendmsgDao = sendmsgDao;
	}
   /**
    * 发送审核后的信息
    * @param reciver
    * @param mailTitle
    * @param content
    * @param mailType
    * @param sender
    * @return
    * @throws Exception
    */
	public Long sendCheckMail( long reciver, String mailTitle,
			String content, int mailType, long sender) throws Exception {
		Connection conn = connectionManager.getConnection();
		Long resultId = -1L;
		try {
			resultId = sendmsgDao.sendCheckMail(conn,  reciver, mailTitle,
					content, mailType, sender);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return resultId;
	}

}
