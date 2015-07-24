package com.sp2p.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shove.base.BaseService;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.QuestionnaireDao;

public class QuestionnaireService extends BaseService {
	private QuestionnaireDao questionnaireDao;

	public QuestionnaireDao getQuestionnaireDao() {
		return questionnaireDao;
	}

	public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}
	
	/**
	 * 查询所有问卷题目
	 * @return
	 * @throws Exception
	 */
	
	public void queryQuestionnaire(PageBean<Map<String,Object>> pageBean) throws Exception{
		Connection conn =MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "t_questionnaire", "*", "", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	/**
	 * 查询所有问卷题目
	 * @return
	 * @throws Exception
	 */
	
	public List<Map<String, Object>> queryQuestionnaire() throws Exception{
		Connection conn =MySQL.getConnection();
		List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
		try {
			DataSet dataSet = MySQL.executeQuery(conn, "select * from t_questionnaire where status = 1");
			dataSet.tables.get(0).rows.genRowsMap();
			list = dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return list!=null&&list.size()>0?list:null;
	}
	/**
	 * 查询所有问卷题目
	 * @return
	 * @throws Exception
	 */
	
	public List<Map<String, Object>> queryOption() throws Exception{
		Connection conn =MySQL.getConnection();
		List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
		try {
			DataSet dataSet = MySQL.executeQuery(conn, "select * from t_option ");
			dataSet.tables.get(0).rows.genRowsMap();
			list = dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return list!=null&&list.size()>0?list:null;
	}
	/**
	 * 查询一个的选项
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryOption(Long questionId) throws Exception{
		Connection conn =MySQL.getConnection();
		List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
		try {
			list = questionnaireDao.queryOption(conn, questionId);
		} catch (Exception e) {
			throw e;
		}finally{
			conn.close();
		}
		return list!=null&&list.size()>0?list:null;
	}
	/**
	 * 查找一个问题
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryQuestionnaire(Long questionId) throws Exception{
		Connection conn =MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = questionnaireDao.queryQuestionnaire(conn, questionId);
		} catch (Exception e) {
			throw e;
		}finally{
			conn.close();
		}
		return map!=null?map:null;
	}
	
	/**
	 * 
	 * 添加问题
	 * @param question
	 * @param maxSorce
	 * @param status
	 * @param options
	 * @param scores
	 * @param type
	 * @param questionType
	 * @return
	 * @throws Exception
	 */
	public Long addQuestionnaire(String question,int maxSorce,int status,String options,String scores, Long type, Long questionType) throws Exception{
		Connection conn =MySQL.getConnection();
		Long result = 0l;
		String[] option = options.split(",");
		String[] score = scores.split(",");
		try {
			result = questionnaireDao.addQuestionnaire(question, maxSorce, status, conn,type,questionType);
			for (int i = 0; i < option.length; i++) {
				questionnaireDao.addOption(option[i], result, Integer.parseInt(score[i]), conn);
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 修改问题
	 * @param maxScore
	 * @param question
	 * @param id
	 * @param status
	 * @param type
	 * @param questionType
	 * @return
	 * @throws Exception
	 */
	public Long updateQuestionnaire(int maxScore ,String question, Long id,int status, Long type, Long questionType) throws Exception{
		Connection conn =MySQL.getConnection();
		Long result = 0l;
		try {
			result = questionnaireDao.updateQuestionnaire(maxScore, question, id, status, conn,type,questionType);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	/**
	 * 删除问题
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Long deleteQuestionnaire(String id) throws Exception{
		Connection conn =MySQL.getConnection();
		Long result = 0l;
		try {
			result = questionnaireDao.deleteQuestionnaire(id, conn);
			questionnaireDao.deleteOptionByParentID(id, conn);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	/**
	 * 删除选项
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Long deleteOption(Long id) throws Exception{
		Connection conn =MySQL.getConnection();
		Long result = 0l;
		try {
			result = questionnaireDao.deleteOption(id, conn);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	/**
	 * 添加问题选项
	 * @param score
	 * @param option
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	public Long addOption(int score ,String option, Long questionId) throws Exception{
		Connection conn =MySQL.getConnection();
		Long result = 0l;
		try {
			result = questionnaireDao.addOption(option, questionId, score, conn);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	/**
	 * 修改选项
	 * @param score
	 * @param option
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Long updateOption(int score ,String option, Long id) throws Exception{
		Connection conn =MySQL.getConnection();
		Long result = 0l;
		try {
			result = questionnaireDao.updateOption(id, score, option, conn);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
}
