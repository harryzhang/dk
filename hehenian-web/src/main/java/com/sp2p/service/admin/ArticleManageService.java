package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.ArticleManageDao;

/**
 * 文章管理 Service
 * @author C_J
 *
 */
public class ArticleManageService  extends BaseService{
	public static Log log = LogFactory.getLog(ArticleManageService.class);
	
	private  ArticleManageDao  articleManageDao;

	/**
	 * 增加
	 * @param title 标题
	 * @param userId  发布人
	 * @param tiemize  所属分类
	 * @param content   内容
	 * @param publishTime  发布时间
	 * @param parentID   分类ID
	 * @param sorts  排序
	 * @param stutas  状态
	 * @return
	 * @throws SQLException 
	 */
	public  long  addArticle(String title,long userId,String tiemize,String ltpic,String content,String publishTime,int parentID,
			  int sorts, int stutas,int issection) throws SQLException{
		Connection  conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = articleManageDao.addArticle(conn, title, userId, tiemize,ltpic, content, publishTime, parentID, sorts, stutas,issection);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result ;
	}
	
	/**
	 * 修改 文章
	 * @param title  标题
	 * @param userId   修改人
	 * @param tiemize 所分类
	 * @param content   内容
	 * @param publishTime  修改时间
	 * @param parentID  分类编号
	 * @param sorts  排序
	 * @param stutas  状态
	 * @param id  文章ID
	 * @return
	 * @throws SQLException
	 */
	public long updateArticle(String title,long userId,String tiemize,String ltpic,String content,String publishTime,int parentID,
			  int sorts, int stutas,long id,int issection) throws SQLException{
		Connection  conn = MySQL.getConnection();
		long result = -1L;
		try {
			result  = articleManageDao.updateArticle(conn, title, userId, tiemize, ltpic,content, publishTime, parentID, sorts, stutas, id,issection);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
		
	}
	
	/**
	 * 删除
	 * @param id  标识列
	 * @return
	 * @throws SQLException
	 */
	public long deleteArticle(long id) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = articleManageDao.deleteArticle(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		
		return result;
	}
	
	/**
	 *  根据条件 查询所有文章信息
	 * @param conn
	 * @param username   发布名
	 * @param title  标题
	 * @param parentID   类型
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public void queryArticlePage(String  userName,String  title ,long parentID, PageBean<Map<String,Object>>  pageBean  ) throws SQLException, DataException{
		Connection  conn = MySQL.getConnection();
		StringBuffer  condition = new StringBuffer();
		if(StringUtils.isNotBlank(userName)){
			condition.append(" and userName like '%"+ userName+"%'");
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and title  like  '%"+StringEscapeUtils.escapeSql(title)+"%' ");
		}
		if (parentID >= 0) {
			condition.append(" and parentID = "+ parentID);
		}
		
		try {
			dataPage(conn, pageBean,  " t_article_manage ", " * ", "  ", condition+"");
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 分类编号查询文章信息
	 * @param parentID   分类编号
	 * @return
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public List<Map<String,Object>> queryArticleParentId(long parentID) throws SQLException, DataException{
		Connection  conn = MySQL.getConnection();
		List<Map<String ,Object>> mapList= new ArrayList<Map<String,Object>>();
		try {
			mapList = articleManageDao.queryArticleParentId(conn, parentID);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally{
			conn.close();
		}
		
		return mapList;
	}
	
	/**
	 * 根据id 查询 
	 * @param id  标识列
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryArticleById(int id) throws SQLException, DataException{
		Connection  conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String, String>();
		try {
			map = articleManageDao.queryArticleById(conn, id);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return map;
	}
	
	/**
	 *  查询所有
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> qureyAllList(int issction) throws DataException, SQLException{
		Connection  conn = MySQL.getConnection();
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		try {
			mapList = articleManageDao.queryAllList(conn,issction);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return mapList;
	}


	public void setArticleManageDao(ArticleManageDao articleManageDao) {
		this.articleManageDao = articleManageDao;
	}
	

	
}
