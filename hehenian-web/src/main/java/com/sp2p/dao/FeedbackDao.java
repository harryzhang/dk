package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.shove.util.UtilDate;
import com.sp2p.database.Dao;

/**
 * @ClassName: FeedbackDao.java
 * @Author: wangjingshi
 * @Date: 2013-8-20 下午16:15:29
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 意见反馈
 */
public class FeedbackDao {


	/**
	 * @MethodName: addBrowseCount
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:51:36
	 * @Return:
	 * @Descb: 更新浏览量
	 * @Throws:
	 */
	public long addFeedback(Connection conn, Long id,String content) throws SQLException {
		long returnId = -1L;
		Dao.Tables.t_feedback t_feedback = new Dao().new Tables().new t_feedback();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		t_feedback.user_id.setValue(id);
		t_feedback.content.setValue(content);
		t_feedback.publish_time.setValue(sf.format(new Date()));
		return t_feedback.insert(conn);
	}
}
