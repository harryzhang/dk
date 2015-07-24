package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.database.Dao;


/**
 * 还款方式 dao
 * @author C_J
 *
 */
public class ShoveBorrowStyleDao {
	/**
	 * 分页显示所有还款方式
	 * @param conn
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void  queryBorrowStylePageAll(Connection  conn,PageBean<Map<String,Object>> pageBean) throws SQLException, DataException
	{
		 Dao.Tables.t_borrow_style  t_shove_borrow_style =new Dao().new Tables().new t_borrow_style();
		 long c= t_shove_borrow_style.getCount(conn, " ");
		 boolean  result=pageBean.setTotalNum(c);//-------->将总页数(c)放到PageBean<T>中
		 if(result){
			// pageBean.getPageSize()-->  在类PageBean<T>的构造方法中已经赋值  
			DataSet ds= t_shove_borrow_style.open(conn, " * ", " ", " ", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();//将DataSet转换成map
			pageBean.setPage(ds.tables.get(0).rows.rowsMap);//放入PageBean 类
		}	
	}
	
	/**
	 * 查询所有还款方式
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryBorrowAll(Connection  conn) throws SQLException, DataException{
		 Dao.Tables.t_borrow_style  t_shove_borrow_style =new Dao().new Tables().new t_borrow_style();
		DataSet ds=t_shove_borrow_style.open(conn, " * ", "", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		
		 return ds.tables.get(0).rows.rowsMap;
		
	}
	
	/**
	 * 根据编号查询还款方式
	 * @param conn
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String>   queryBorrowStylById (Connection  conn,int id) throws SQLException, DataException{
		 Dao.Tables.t_borrow_style  t_shove_borrow_style =new Dao().new Tables().new t_borrow_style();
		 DataSet ds=t_shove_borrow_style.open(conn, " * ", " id ="+id, "", -1,-1);
		 ds.tables.get(0).rows.genRowsMap();
		 
		 return  BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 增加
	 * @param conn
	 * @param nid
	 * @param status
	 * @param name
	 * @param title
	 * @param contents
	 * @param remark
	 * @param sort
	 * @return
	 * @throws SQLException
	 */
	public long addShoveBorrowStyle(Connection  conn,String nid,int status,String name,String title,String contents,String remark,int sort) throws SQLException{
		 Dao.Tables.t_borrow_style  t_shove_borrow_style =new Dao().new Tables().new t_borrow_style();
		 t_shove_borrow_style.nid.setValue(nid);
		 t_shove_borrow_style.status.setValue(status);
		 t_shove_borrow_style._name.setValue(name);
		 t_shove_borrow_style.title.setValue(title);
		 t_shove_borrow_style.contents.setValue(contents);
		 t_shove_borrow_style.remark.setValue(remark);
		 t_shove_borrow_style.sort.setValue(sort);
		 
		 return t_shove_borrow_style.insert(conn);
		 
	}
	
	/**
	 * 修改还款方式 信息
	 * @param conn
	 * @param id
	 * @param status
	 * @param title
	 * @param contents
	 * @param remark
	 * @param sort
	 * @return
	 * @throws SQLException
	 */
	public long updateShoveBorrowStyle(Connection conn,int id,int status,String title,String contents,String remark,int sort) throws SQLException{
		Dao.Tables.t_borrow_style  t_shove_borrow_style =new Dao().new Tables().new t_borrow_style();
		t_shove_borrow_style.status.setValue(status);
		t_shove_borrow_style.title.setValue(title);
		t_shove_borrow_style.contents.setValue(contents);
		t_shove_borrow_style.remark.setValue(remark);
		t_shove_borrow_style.sort.setValue(sort);
		
		return t_shove_borrow_style.update(conn, " id = " + id);
	}
	
	/**
	 * 删除还款方式
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public long deleteShoveBorrowStyle(Connection conn, int id) throws SQLException{
		Dao.Tables.t_borrow_style  t_shove_borrow_style =new Dao().new Tables().new t_borrow_style();
		
		return t_shove_borrow_style.delete(conn, " id = " + id);
	}
	public List<Map<String, Object>> queryBorrowAmountNamesByIds(Connection conn,
			String ids) throws SQLException, DataException {
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_borrow_style  t_shove_borrow_style=  new Dao().new Tables().new t_borrow_style();
		DataSet  ds = t_shove_borrow_style.open(conn, " title ", " id in (" +ids+")", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}

	public List<Map<String, Object>> queryShoveBorrowStyleByTypeNid(
			Connection conn, String styles) throws SQLException, DataException {
		String idStr = StringEscapeUtils.escapeSql("'"+styles+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_borrow_style  t_shove_borrow_style=  new Dao().new Tables().new t_borrow_style();
		DataSet  ds = t_shove_borrow_style.open(conn, " id,name ", " id in ("+idSQL+")", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	

	
	/**
	 * 得到机构担保列表   和反担保方式
	 * @param conn
	 * @param styles
	 * @param type  2  担保机构  3 反担保方式
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String>  querySelectDanbyTypenid(Connection conn,String styles,int typeId) throws SQLException, DataException{
		String idStr = StringEscapeUtils.escapeSql("'"+styles+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_select  t_select=  new Dao().new Tables().new t_select();
		DataSet  ds = t_select.open(conn, " selectValue,selectName ", " typeId ="+typeId+"  and selectValue in ("+idSQL+")"  , "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		
		return BeanMapUtils.dataSetToMap(ds);
	}
}
