package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.database.Dao;

/**
 *  平台收费标准
 * @author C_J
 *
 */
public class PlatformCostDao {
		/**
		 * 查询所有收费标准 分页显示
		 * @return
		 * @throws DataException 
		 * @throws SQLException 
		 */
		public void  queryPlatformCostAll(Connection  conn,PageBean<Map<String,Object>> pageBean) throws SQLException, DataException 
		{
			Dao.Tables.t_platform_cost  t_platform_cost = new Dao().new Tables().new t_platform_cost();
			long c=t_platform_cost.getCount(conn ," show_view = 1 ");//查询当前条件下的所有记录
			boolean  result=pageBean.setTotalNum(c);//-------->将总页数(c)放到PageBean<T>中
			if(result)
			{
				// pageBean.getPageSize()-->  在类PageBean<T>的构造方法中已经赋值  
				DataSet ds= t_platform_cost.open(conn, " * ", " show_view = 1 ", "  ", pageBean.getStartOfPage(), pageBean.getPageSize());
				ds.tables.get(0).rows.genRowsMap();//将DataSet转换成map
				pageBean.setPage(ds.tables.get(0).rows.rowsMap);//放入PageBean 类
			}
			 
			
		}
		
		/**
		 * 根据ID修改收费标准
		 * @param conn
		 * @param costFee
		 * @param id
		 * @return
		 * @throws SQLException
		 */
		public Long updatePlatformCostById(Connection  conn,Double costFee,  int id) throws SQLException
		{
			Dao.Tables.t_platform_cost  t_platform_cost = new Dao().new Tables().new t_platform_cost();
			t_platform_cost.costFee.setValue(costFee);
			t_platform_cost.createTime.setValue(new Date());
			return t_platform_cost.update(conn, " id= "+id);
		}
		/**
		 * 根据ID查询收费标准
		 * @param conn
		 * @param costFee
		 * @param id
		 * @return
		 * @throws SQLException
		 * @throws DataException 
		 */
		public Map<String,String> queryPlatformCostById(Connection  conn, int id) throws SQLException, DataException
		{
			Dao.Tables.t_platform_cost  t_platform_cost = new Dao().new Tables().new t_platform_cost();
			DataSet  ds=t_platform_cost.open(conn, " * ", " id = "+id, "", -1, -1);
			return BeanMapUtils.dataSetToMap(ds);
		}
		
		/**
		 * 
		 * @param conn
		 * @param id
		 * @param show_view
		 * @return
		 * @throws SQLException
		 */
		public long updateShow_view(Connection  conn, int id,int show_view ) throws SQLException{
			Dao.Tables.t_platform_cost  t_platform_cost = new Dao().new Tables().new t_platform_cost();
			t_platform_cost.show_view.setValue(show_view);
			return t_platform_cost.update(conn, " id = "+id);
		}
		/**
		 * 查询所有收费标准 
		 * @return
		 * @throws DataException 
		 * @throws SQLException 
		 */
		public List<Map<String,Object>>  queryPlatformCostAll(Connection  conn) throws SQLException, DataException 
		{
				Dao.Tables.t_platform_cost  t_platform_cost = new Dao().new Tables().new t_platform_cost();
				DataSet ds= t_platform_cost.open(conn, " * ", " show_view =1 ", " ",-1,-1);
				ds.tables.get(0).rows.genRowsMap();
				return ds.tables.get(0).rows.rowsMap;
		}
}
