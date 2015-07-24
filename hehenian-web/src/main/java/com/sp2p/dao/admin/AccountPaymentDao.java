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
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_phone_binding_info;

/**
 * 支付方式
 * @author Administrator
 *
 */
public class AccountPaymentDao {

	/**
	 * 增加支付方式
	 * @param conn
	 * @param name
	 * @param nid
	 * @param status
	 * @param litpic
	 * @param style
	 * @param config
	 * @param description
	 * @param order
	 * @return
	 * @throws SQLException 
	 */
		public long  addAccountPayment(Connection conn,String name,String nid ,long status,
						String litpic,int style,String config,String description,int order) throws SQLException{
			
			Dao.Tables.t_account_payment  t_account_payment = new Dao().new Tables().new t_account_payment();
			t_account_payment._name.setValue(name);
			t_account_payment.nid.setValue(nid);
			t_account_payment.status.setValue(status);
			t_account_payment.litpic.setValue(litpic);
			t_account_payment.style.setValue(style);
			t_account_payment.config.setValue(config);
			t_account_payment.description.setValue(description);
			t_account_payment.orders.setValue(order);
			
			return t_account_payment.insert(conn);
		}
		
		/**
		 * 查询所有
		 * @param conn
		 * @return
		 * @throws DataException 
		 * @throws SQLException 
		 */
		public List<Map<String,Object>>  queryAccountPaymentList( Connection  conn) throws SQLException, DataException{
			Dao.Tables.t_account_payment t_account_payment = new Dao().new Tables().new t_account_payment();
			DataSet  ds = t_account_payment.open(conn, " * ", "", "", -1, -1);
			ds.tables.get(0).rows.genRowsMap();
			
			return  ds.tables.get(0).rows.rowsMap;
		}
		
		
		/**
		 * 分页查询所有
		 * @param conn
		 * @param pageBean
		 * @throws SQLException
		 * @throws DataException
		 */
		public void  queryAccountPaymentPage(Connection  conn,PageBean<Map<String,Object>>  pageBean) throws SQLException, DataException{
			Dao.Tables.t_account_payment  t_account_payment = new Dao().new Tables().new t_account_payment();
			long c = t_account_payment.getCount(conn, "");
				boolean  result= pageBean.setTotalNum(c);
				if(result){
					DataSet ds= t_account_payment.open(conn, " * ", " ", " orders asc", pageBean.getStartOfPage(), pageBean.getPageSize());
					ds.tables.get(0).rows.genRowsMap();//将DataSet转换成map
					pageBean.setPage(ds.tables.get(0).rows.rowsMap);//放入PageBean 类
				}
		}
		
		/**
		 * 修改
		 * @param conn
		 * @param id
		 * @param name
		 * @param nid
		 * @param status
		 * @param litpic
		 * @param style
		 * @param config
		 * @param description
		 * @param order
		 * @return
		 * @throws SQLException
		 */
		public long updateAccountPaymentPage(Connection  conn,long id,String name,
				String litpic,String config,String description,int order) throws SQLException{
			Dao.Tables.t_account_payment  t_account_payment = new Dao().new Tables().new t_account_payment();
			t_account_payment._name.setValue(name);
			t_account_payment.litpic.setValue(litpic);
			//t_account_payment.status.setValue(status);
			t_account_payment.config.setValue(config);
			t_account_payment.description.setValue(description);
			t_account_payment.orders.setValue(order);
			
			return t_account_payment.update(conn, " id = " +id);
		}
		
		/**
		 * 删除
		 * @param conn
		 * @param id
		 * @return
		 * @throws SQLException
		 */
		public  long deleteAccountPaymentPage(Connection  conn,long id,long status) throws SQLException{
			Dao.Tables.t_account_payment  t_account_payment = new Dao().new Tables().new t_account_payment();
			t_account_payment.status.setValue(status);
			
			return t_account_payment.update(conn, " id = " +id);
		}
		
		/**
		 * 根据ID 查询
		 * @param conn
		 * @param id
		 * @return
		 * @throws SQLException
		 * @throws DataException
		 */
		public Map<String,String>  queryAccountPaymentById(Connection conn, String nid) throws SQLException, DataException{
			Dao.Tables.t_account_payment  t_account_payment = new Dao().new Tables().new t_account_payment();
			DataSet  ds = t_account_payment.open(conn, " * ", " nid = '"+ StringEscapeUtils.escapeSql(nid) +"'", "", -1, -1);
			ds.tables.get(0).rows.genRowsMap();
			
			return  BeanMapUtils.dataSetToMap(ds);
		} 
} 
