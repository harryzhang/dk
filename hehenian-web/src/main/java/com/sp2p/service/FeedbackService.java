package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.CategoryDao;
import com.sp2p.dao.FeedbackDao;
import com.sp2p.dao.LinksDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_links;

/**
 * 前台意见反馈
 * @author li.hou
 *
 */
public class FeedbackService extends BaseService {
	public static Log log = LogFactory.getLog(FeedbackService.class);
	private FeedbackDao feedbackDao;
	
	public Long addFeedback(String content,long id) throws SQLException{
		Connection conn = MySQL.getConnection();
		Long newsId = -1L;
		try {
			newsId = feedbackDao.addFeedback(conn, id, content);
			if(newsId<0){//如果小于0，添加失败，回滚
				conn.rollback();
			}
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return newsId;
	}

	public FeedbackDao getFeedbackDao() {
		return feedbackDao;
	}

	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}
	
}
