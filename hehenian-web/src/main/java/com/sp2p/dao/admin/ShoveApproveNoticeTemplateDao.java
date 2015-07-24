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
 * 提醒方式 记录
 * @author C_J
 *
 */
public class ShoveApproveNoticeTemplateDao {
	
		/**
		 * 增加记录
		 * @param conn
		 * @param notice_id
		 * @param name
		 * @param template
		 * @param nid
		 * @return
		 * @throws SQLException 
		 */
		public Long addApproveNoticeTemplate(Connection  conn,int notice_id ,String name,String template,String nid  ) throws SQLException{
			Dao.Tables.t_approve_notice_template  t_shove_approve_notice_template = new Dao(). new Tables(). new t_approve_notice_template();
			t_shove_approve_notice_template.notice_id.setValue(notice_id);
			t_shove_approve_notice_template._name.setValue(name);
			t_shove_approve_notice_template.template.setValue(template);
			t_shove_approve_notice_template.nid.setValue(nid);
			
			return  t_shove_approve_notice_template.insert(conn);
		}
		
		/**
		 * 修改
		 * @param conn
		 * @param id
		 * @param notice_id
		 * @param name
		 * @param template  模板
		 * @param nid
		 * @return
		 * @throws SQLException
		 */
		public Long updateApproveNoticeTemplate(Connection  conn,int id,int notice_id ,String name,String template,int sort ) throws SQLException{
			Dao.Tables.t_approve_notice_template  t_shove_approve_notice_template = new Dao(). new Tables(). new t_approve_notice_template();
			t_shove_approve_notice_template.notice_id.setValue(notice_id);
			t_shove_approve_notice_template._name.setValue(name);
			t_shove_approve_notice_template.template.setValue(template);
			t_shove_approve_notice_template.sort.setValue(sort);
			
			return t_shove_approve_notice_template.update(conn, " id = "+id);
		}
		
		/**
		 * 删除
		 * @param conn
		 * @param id
		 * @return
		 * @throws SQLException 
		 */
		public Long deleteApproveNoticeTemplate(Connection  conn,int id) throws SQLException{
			Dao.Tables.t_approve_notice_template  t_shove_approve_notice_template = new Dao(). new Tables(). new t_approve_notice_template();
			
			return t_shove_approve_notice_template.delete(conn, " id = "+ id);
		}
		
		
		/**
		 * 分页查询
		 * @param conn
		 * @param pageBean
		 * @throws SQLException 
		 * @throws DataException 
		 */
		public void queryApproveTemplatePageAll( Connection  conn,PageBean<Map<String,Object>>  pageBean) throws SQLException, DataException{
			Dao.Tables.t_approve_notice_template  t_shove_approve_notice_template = new Dao(). new Tables(). new t_approve_notice_template();
			long c = t_shove_approve_notice_template.getCount(conn, "");
			boolean  result= pageBean.setTotalNum(c);
			if(result){
				DataSet ds= t_shove_approve_notice_template.open(conn, " * ", " ", " ", pageBean.getStartOfPage(), pageBean.getPageSize());
				ds.tables.get(0).rows.genRowsMap();//将DataSet转换成map
				pageBean.setPage(ds.tables.get(0).rows.rowsMap);//放入PageBean 类
			}
		}
		
		/**
		 * 根据ID 查询
		 * @param conn
		 * @param id
		 * @return
		 * @throws DataException 
		 * @throws SQLException 
		 */
		public Map<String,String> queryApproveTemplateById(Connection  conn,int id) throws SQLException, DataException{
			Dao.Tables.t_approve_notice_template  t_shove_approve_notice_template = new Dao(). new Tables(). new t_approve_notice_template();
			DataSet ds = t_shove_approve_notice_template.open(conn, "", " id = "+id,"", -1, -1);
			ds.tables.get(0).rows.genRowsMap();
			
			return BeanMapUtils.dataSetToMap(ds);
		}
		
		/**
		 * 查询所有通知模板
		 * @param conn
		 * @return
		 * @throws SQLException
		 * @throws DataException
		 */
		public List<Map<String,Object>> queryAllInformTemplate(Connection  conn) throws SQLException, DataException{
			Dao.Tables.t_approve_notice_template  shove_approve_notice_template = new Dao().new Tables().new t_approve_notice_template();
			DataSet ds= shove_approve_notice_template.open(conn, " * ", " ", " ",-1,-1);
			ds.tables.get(0).rows.genRowsMap();
			return ds.tables.get(0).rows.rowsMap;
		}
}
