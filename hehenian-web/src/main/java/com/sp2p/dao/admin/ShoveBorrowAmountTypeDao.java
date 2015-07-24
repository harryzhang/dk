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
 * 信用额度类型 dao
 * @author C_J
 *
 */
public class ShoveBorrowAmountTypeDao {
	
	/**
	 * 分页查询所有额度类型
	 * @param conn
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void  queryBorrowAmountPageAll(Connection  conn,PageBean<Map<String,Object>> pageBean) throws SQLException, DataException {
		Dao.Tables.t_borrow_amount_type  t_shove_borrow_amount_type=  new Dao().new Tables().new t_borrow_amount_type();
		long c =t_shove_borrow_amount_type.getCount(conn, "");
		boolean  result=pageBean.setTotalNum(c);
		if(result){
			DataSet ds=t_shove_borrow_amount_type.open(conn, " * ", "", "", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();
			
			pageBean.setPage(ds.tables.get(0).rows.rowsMap);
		}
	}
	
	/**
	 * 查询所有额度信息
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>>  queryBorrowAmountAll( Connection  conn) throws SQLException, DataException{
		Dao.Tables.t_borrow_amount_type  t_shove_borrow_amount_type=  new Dao().new Tables().new t_borrow_amount_type();
		DataSet  ds=t_shove_borrow_amount_type.open(conn, " * ","","", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 根据 ID 查询额度信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String>  queryBorrowAmountById(Connection conn,int id ) throws SQLException, DataException{
		Dao.Tables.t_borrow_amount_type  t_shove_borrow_amount_type=  new Dao().new Tables().new t_borrow_amount_type();
		DataSet  ds = t_shove_borrow_amount_type.open(conn, " * ", " id = " + id, "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		
		return BeanMapUtils.dataSetToMap(ds);
		
		
	}
	/**
	 * 根据  标识列查询额度信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String>  queryBorrowAmountByNid(Connection conn,String nid ) throws SQLException, DataException{
		Dao.Tables.t_borrow_amount_type  t_shove_borrow_amount_type=  new Dao().new Tables().new t_borrow_amount_type();
		DataSet  ds = t_shove_borrow_amount_type.open(conn, " * ", " nid = '" + StringEscapeUtils.escapeSql(nid)+"'", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		
		return BeanMapUtils.dataSetToMap(ds);
		
		
	}
	
	/**
	 * 增加信用额度
	 * @param conn
	 * @param name
	 * @param nid
	 * @param once_status
	 * @param title
	 * @param descriptionm
	 * @param status
	 * @param remark
	 * @param init_credit
	 * @return
	 * @throws SQLException
	 */
	public Long  addBorrowAmount(Connection   conn,String name,String nid,int once_status,String title,String descriptionm,
								int status,String remark,String init_credit) throws SQLException{
		Dao.Tables.t_borrow_amount_type  t_shove_borrow_amount_type=  new Dao().new Tables().new t_borrow_amount_type();
		t_shove_borrow_amount_type._name.setValue(name);
		t_shove_borrow_amount_type.nid.setValue(nid);
		t_shove_borrow_amount_type.once_status.setValue(once_status);
		t_shove_borrow_amount_type.title.setValue(title);
		t_shove_borrow_amount_type.description.setValue(descriptionm);
		t_shove_borrow_amount_type.status.setValue(status);
		t_shove_borrow_amount_type.remark.setValue(remark);
		t_shove_borrow_amount_type.init_credit.setValue(init_credit);
		
		return   t_shove_borrow_amount_type.insert(conn);
	 }
	
	/**
	 * 修改额度类型
	 * @param conn
	 * @param id
	 * @param name
	 * @param nid
	 * @param once_status
	 * @param title
	 * @param descriptionm
	 * @param status
	 * @param remark
	 * @param init_credit
	 * @return
	 * @throws SQLException
	 */
	public Long  updateBorrowAmount(Connection   conn,int id ,String title,String descriptionm,
			int status,String remark,double init_credit) throws SQLException{
		Dao.Tables.t_borrow_amount_type  t_shove_borrow_amount_type=  new Dao().new Tables().new t_borrow_amount_type();
		
		t_shove_borrow_amount_type.title.setValue(title);
		t_shove_borrow_amount_type.description.setValue(descriptionm);
		t_shove_borrow_amount_type.status.setValue(status);
		t_shove_borrow_amount_type.remark.setValue(remark);
		t_shove_borrow_amount_type.init_credit.setValue(init_credit);
		
		return   t_shove_borrow_amount_type.update(conn, " id = "+ id);
		}
	
	/**
	 * 删除
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public  Long deleteBorrowAmount(Connection conn ,int id) throws SQLException{
		Dao.Tables.t_borrow_amount_type  t_shove_borrow_amount_type=  new Dao().new Tables().new t_borrow_amount_type();
		
		return t_shove_borrow_amount_type.delete(conn, " id = "+id);
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
		Dao.Tables.t_borrow_amount_type  t_shove_borrow_amount_type=  new Dao().new Tables().new t_borrow_amount_type();
		DataSet  ds = t_shove_borrow_amount_type.open(conn, " title ", " id in (" + idSQL+")", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
}
