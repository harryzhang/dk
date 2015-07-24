package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.ScoreDao;

public class ScoreService extends BaseService {
	public static Log log =LogFactory.getLog(ScoreService.class);
	
	private ScoreDao scoreDao;
	
	public ScoreDao getScoreDao() {
		return scoreDao;
	}

	public void setScoreDao(ScoreDao scoreDao) {
		this.scoreDao = scoreDao;
	}

	public void queryScorePage(PageBean<Map<String,Object>> pageBean, String attitude, int levels, String username,String id)throws Exception{
		Connection conn=MySQL.getConnection();
		StringBuffer sb = new StringBuffer("");
		if (StringUtils.isNotBlank(username)) {
			sb.append(" and  username  like '%"
							+ StringEscapeUtils.escapeSql(username.trim())
							+ "%' ");
		}
		if (levels>0) {
			sb.append(" AND levels = " +  levels+"");
		}
		if (StringUtils.isNotBlank(attitude)) {
			sb.append(" and  attitude  like '%"
							+ StringEscapeUtils.escapeSql(attitude.trim())
							+ "%' ");
		}
		if (StringUtils.isNotBlank(id)) {
			sb.append(" and  id  in (%"
							+ StringEscapeUtils.escapeSql(id.trim())
							+ "%) ");
		}
		try {
			dataPage(conn, pageBean, "v_t_score_list", "*", "", sb.toString());
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	public void queryScoreById(PageBean<Map<String,Object>> pageBean ,Long id )throws SQLException,DataException{
		Connection conn=MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_score_list", "*", "", " and userId = " + id);
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	public void addScore(long userId ,int total,int questionScore,int degree,int degreeScore,int internet,int intnetScore,
			int predilection,int prediScore)throws Exception{
		Connection conn=MySQL.getConnection();
		try {
			int sum= total+degree+internet+predilection;
			int sumTotal=questionScore+degreeScore+intnetScore+prediScore;
			scoreDao.addScore(userId, total, questionScore, degree, degreeScore, internet, intnetScore, 
					predilection, prediScore, 
					conn, degree/degreeScore*100/33+1, sum/sumTotal*100/25+1);
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
