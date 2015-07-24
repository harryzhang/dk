package com.sp2p.service;

import java.sql.Connection;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.QuestionDao;

public class QuestionService extends BaseService {
	public static Log log =LogFactory.getLog(BaseService.class);
	
	private QuestionDao questionDao ;
	
	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}
	@SuppressWarnings("unchecked")
	public void queryQuestionList(PageBean pageBean, String keyword, String username) throws Exception{
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(keyword)){
			sb.append(" and question like '%");
			sb.append(StringEscapeUtils.escapeSql(keyword));
			sb.append("%'");
		}
		if(StringUtils.isNotBlank(username)){
			sb.append(" and username like '%");
			sb.append(StringEscapeUtils.escapeSql(username));
			sb.append("%'");
		}
		Connection conn=MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "t_question", "*", "order by  puttime  desc ", sb.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	public void deleteQuestion(String  id) throws Exception{
		Connection conn=MySQL.getConnection();
		try {
			questionDao.deleteQuestion(conn, id);
			conn.commit();
			
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}

}
