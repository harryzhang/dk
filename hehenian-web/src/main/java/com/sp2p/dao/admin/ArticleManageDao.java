package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;


/**
 * 文章管理Dao
 * @author C_J
 *
 */
public class ArticleManageDao {
	
	
	
	/**
	 * 增加文章
	 * @param conn
	 * @param title 标题	
	 * @param userId 发布人	
	 * @param tiemize	分类名称	
	 * @param content 内容
	 * @param publishTime  发布时间
	 * @param parentID   分类编号
	 * @param sorts  排序	
	 * @param stutas 状态
	 * @return
	 * @throws SQLException 
	 */
	public long addArticle(Connection  conn,String title,long userId,String tiemize,String ltpic ,String content,String publishTime,int parentID,
		  int sorts, int stutas,int issection ) throws SQLException{
		Dao.Tables.t_article_manage  t_article_manage = new Dao().new Tables().new t_article_manage();
		t_article_manage.title.setValue(title);
		t_article_manage.userId.setValue(userId);
		t_article_manage.itemize.setValue(tiemize);
		t_article_manage.content.setValue(content);
		t_article_manage.publishTime.setValue(publishTime);
		t_article_manage.parentID.setValue(parentID);
		t_article_manage.sorts.setValue(sorts);
		t_article_manage.status.setValue(stutas);
		t_article_manage.ltpic.setValue(ltpic);
		t_article_manage.issection.setValue(issection);
		
		return  t_article_manage.insert(conn);
	}
	
	/**
	 * 修改文章内容
	 * @param conn
	 * @param title  标题
	 * @param userId  修改人
	 * @param tiemize 所属分类
 	 * @param content  内容
	 * @param publishTime   修改时间
	 * @param parentID   分类编号
	 * @param sorts  排序
	 * @param stutas   状态
	 * @param id   id
	 * @return
	 * @throws SQLException
	 */
	public long  updateArticle (Connection conn,String title,long userId,String tiemize,String ltpic ,String content,String publishTime,int parentID,
			  int sorts, int stutas,long id  ,int issection) throws SQLException{
		Dao.Tables.t_article_manage  t_article_manage = new Dao().new Tables().new t_article_manage();
		t_article_manage.title.setValue(title);
		t_article_manage.userId.setValue(userId);
		t_article_manage.itemize.setValue(tiemize);
		t_article_manage.content.setValue(content);
		t_article_manage.publishTime.setValue(publishTime);
		t_article_manage.parentID.setValue(parentID);
		t_article_manage.sorts.setValue(sorts);
		t_article_manage.status.setValue(stutas);
		t_article_manage.ltpic.setValue(ltpic);
		t_article_manage.issection.setValue(issection);
		
		return  t_article_manage.update(conn, " id = "+ id);
	}
	
	/**
	 * 删除 文章
	 * @param conn
	 * @param id  文章ID
	 * @return
	 * @throws SQLException
	 */
	public long deleteArticle (Connection  conn, long id ) throws SQLException{
		Dao.Tables.t_article_manage  t_article_manage = new Dao().new Tables().new t_article_manage();
		return t_article_manage.delete(conn, " id = "+ id );
	}
	
	
	/**
	 *  根据分类 编号查询文章信息
	 * @param conn
	 * @param parentID  分类编号
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>>   queryArticleParentId(Connection  conn, long parentID) throws SQLException, DataException{
		Dao.Tables.t_article_manage  t_article_manage = new Dao().new Tables().new t_article_manage();
		DataSet  ds = t_article_manage.open(conn, " * ", " parentID = "+ parentID, " sorts desc ", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		
		return ds.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 根据编号查询文章信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String,String> queryArticleById(Connection  conn,int id) throws DataException, SQLException{
		Dao.Tables.t_article_manage  t_article_manage = new Dao().new Tables().new t_article_manage();
		DataSet ds = t_article_manage.open(conn, " * ", " id =  "+id, "", -1, -1);
		
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 查询所有
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryAllList(Connection  conn, int  issction) throws SQLException, DataException{
		Dao.Tables.t_article_manage  t_article_manage = new Dao().new Tables(). new t_article_manage();
		StringBuffer condition = new StringBuffer();
		if(issction==-1){  //根据传过来的值      -1 为查询所有    0 为查询当前所有栏目  1 为查询所有文章   
			condition.append("");
		}
		if(issction!=-1){
			condition.append("  issection = "+issction);
		}
		DataSet  ds = t_article_manage.open(conn, " * ", condition+""	, "", -1, -1);
		 ds.tables.get(0).rows.genRowsMap();
		 
		 return ds.tables.get(0).rows.rowsMap;
		
	}
}
