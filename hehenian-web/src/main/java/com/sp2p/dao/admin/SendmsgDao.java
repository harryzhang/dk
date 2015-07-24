package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import com.sp2p.database.Dao;

/**
 * 审核模块发站内信
 * @author lw
 */
public class SendmsgDao {
	
	
	/**
	 * 发站内信
	 * @param conn
	 * @param borrowId
	 * @param reciver
	 * @param mailTitle
	 * @param content
	 * @param mailType
	 * @param sender
	 * @return
	 * @throws SQLException
	 */
	public Long sendCheckMail(Connection conn,long reciver,String mailTitle,String content,int mailType,long sender) throws SQLException{
		Dao.Tables.t_mail t_mail =  new Dao().new Tables().new t_mail();
		t_mail.reciver.setValue(reciver);
		t_mail.sender.setValue(sender);
		t_mail.mailContent.setValue(content);
		t_mail.mailTitle.setValue(mailTitle);
		//系统站内信
		t_mail.mailType.setValue(mailType);
		t_mail.sendTime.setValue(new Date());
		return t_mail.insert(conn);
	}

}
