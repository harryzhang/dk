package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.database.Dao;


/**
 *  提醒设置 dao
 * @author C_J
 *
 */
public class ShoveApproveNoticeStyleDao {
	
	

	/**
	 * 根据提醒方式分页查询
	 * @param conn
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryApproveNoticeStylePageAll( Connection  conn , PageBean<Map<String,Object>>  pageBean,int notice_style) throws SQLException, DataException{
		Dao.Tables.t_approve_notice_style t_shove_approve_notice_style = new Dao().new Tables().new t_approve_notice_style();
		 long c= t_shove_approve_notice_style.getCount(conn, " ");
		 boolean  result=pageBean.setTotalNum(c);//-------->将总页数(c)放到PageBean<T>中
		 if(result){
			DataSet ds= t_shove_approve_notice_style.open(conn, " * ", " notice_style = "+notice_style, " sort asc", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();//将DataSet转换成map
			pageBean.setPage(ds.tables.get(0).rows.rowsMap);//放入PageBean 类
		}	
	}
	
	
	/**
	 * 根据提醒方式查询
	 * @param conn
	 * @param notice_style
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>> queryApproveNoticeStyleAll(Connection  conn,int notice_style) throws SQLException, DataException{
		Dao.Tables.t_approve_notice_style t_shove_approve_notice_style = new Dao().new Tables().new t_approve_notice_style();
		DataSet  ds = t_shove_approve_notice_style.open(conn, "", " notice_style =  "+notice_style, "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		
		return ds.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 根据ID 查询
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryApproveNoticeStyleById(Connection  conn,int id) throws SQLException, DataException{
		Dao.Tables.t_approve_notice_style t_shove_approve_notice_style = new Dao().new Tables().new t_approve_notice_style();
		DataSet  ds = t_shove_approve_notice_style.open(conn, "", " id =  "+id, "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 修改
	 * @param conn
	 * @param id
	 * @param notice_type
	 * @param nid
	 * @param sort
	 * @return
	 * @throws SQLException 
	 */
	public Long updateApproveNoticeStyle(Connection conn,int id,String title,int sort) throws SQLException{
		Dao.Tables.t_approve_notice_style t_shove_approve_notice_style = new Dao().new Tables().new t_approve_notice_style();
		t_shove_approve_notice_style.title.setValue(title);
		t_shove_approve_notice_style.sort.setValue(sort);
		
		return  t_shove_approve_notice_style.update(conn, " id = "+id);
	}
	
	/**
	 * 增加
	 * @param conn
	 * @param id
	 * @param notic_type
	 * @param nid
	 * @param sort
	 * @return
	 * @throws SQLException 
	 */
	public Long addApproveNoticeStyle(Connection  conn,int id,int notice_style,String title ,int notic_type,String nid,int sort) throws SQLException{
		Dao.Tables.t_approve_notice_style t_shove_approve_notice_style = new Dao().new Tables().new t_approve_notice_style();
		t_shove_approve_notice_style.nid.setValue(nid);
		t_shove_approve_notice_style.notice_style.setValue(notice_style);
		t_shove_approve_notice_style.title.setValue(title);
		t_shove_approve_notice_style.notice_type.setValue(notic_type);
		t_shove_approve_notice_style.sort.setValue(sort);
		
		return  t_shove_approve_notice_style.update(conn, " id = "+id);
	}
	
	/**
	 * 删除
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Long deleteApproveNoticeStyle(Connection  conn,int id) throws SQLException{
		Dao.Tables.t_approve_notice_style t_shove_approve_notice_style = new Dao().new Tables().new t_approve_notice_style();
		
		return t_shove_approve_notice_style.delete(conn, " id = " + id);
	}
}
