package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.database.Dao;

/**
 *  标种类型dao
 * @author C_J
 *
 */
public class ShoveBorrowTypeDao {
	
	/**
	 * 增加标种类型
	 */
	public Long addShoveBorrowType(Connection conn,String nid,int status,String name,String title,String description,double account_multiple,int password_status ,
					int amount_type,double amount_first,double amount_end ,double frost_scale_vip,double apr_first,double apr_end,
					int check_first,int check_end,String tender_account_min,String tender_account_max,String period_month,String period_day,
					String validate,int award_status,double award_scale_first,double award_scale_end,double award_account_first,double award_account_end,
					int subscribe_status,int verify_auto_status,String verify_auto_remark, String styles,double vip_frost_scale,int late_days_month,
					int late_days_day,double vip_late_scale, double all_late_scale,double all_frost_scale,double locan_fee,
					double locan_fee_month,int locan_month,double day_fee) throws SQLException{
		Dao.Tables.t_borrow_type  t_shove_borrow_type = new Dao().new Tables().new t_borrow_type();

		t_shove_borrow_type.nid.setValue(nid);
		t_shove_borrow_type.status.setValue(status);
		t_shove_borrow_type._name.setValue(name);
		t_shove_borrow_type.title.setValue(title);
		t_shove_borrow_type.description.setValue(description);
		t_shove_borrow_type.account_multiple.setValue(account_multiple);
		t_shove_borrow_type.password_status.setValue(password_status);
		
		t_shove_borrow_type.amount_type.setValue(amount_type);
		t_shove_borrow_type.amount_first.setValue(amount_first);
		t_shove_borrow_type.amount_end.setValue(amount_end);
		t_shove_borrow_type.frost_scale_vip.setValue(frost_scale_vip);
		t_shove_borrow_type.apr_first.setValue(apr_first);
		t_shove_borrow_type.apr_end.setValue(apr_end);	
		
		t_shove_borrow_type.check_first.setValue(check_first);
		t_shove_borrow_type.check_end.setValue(check_end);
		t_shove_borrow_type.tender_account_min.setValue(tender_account_min);
		t_shove_borrow_type.tender_account_max.setValue(tender_account_max);
		t_shove_borrow_type.period_month.setValue(period_month);
		t_shove_borrow_type.period_day.setValue(period_day);
		
		t_shove_borrow_type.validate.setValue(validate);	
		t_shove_borrow_type.award_status.setValue(award_status);
		t_shove_borrow_type.award_scale_first.setValue(award_scale_first);
		t_shove_borrow_type.award_scale_end.setValue(award_scale_end);
		t_shove_borrow_type.award_account_first.setValue(award_account_first);
		t_shove_borrow_type.award_account_end.setValue(award_account_end);
		
		t_shove_borrow_type.subscribe_status.setValue(subscribe_status);
		t_shove_borrow_type.verify_auto_status.setValue(verify_auto_status);
		t_shove_borrow_type.verify_auto_remark.setValue(verify_auto_remark);
		t_shove_borrow_type.styles.setValue(styles);
		t_shove_borrow_type.vip_frost_scale.setValue(vip_frost_scale);
		t_shove_borrow_type.late_days_month.setValue(late_days_month);
		
		t_shove_borrow_type.late_days_day.setValue(late_days_day);
		t_shove_borrow_type.vip_late_scale.setValue(vip_late_scale);
		t_shove_borrow_type.all_late_scale.setValue(all_late_scale);
		t_shove_borrow_type.all_frost_scale.setValue(all_frost_scale);

		t_shove_borrow_type.locan_fee.setValue(locan_fee);
		t_shove_borrow_type.locan_fee_month.setValue(locan_fee_month);
		t_shove_borrow_type.locan_month.setValue(locan_month);
		t_shove_borrow_type.day_fee.setValue(day_fee);
		
		return t_shove_borrow_type.insert(conn);
	}
	
	/**
	 * 修改标种类型
	 */
	public Long updateShoveBorrowType(Connection conn,int id,int status,String title,String description,double account_multiple,int password_status ,
			int amount_type,double amount_first,double amount_end,double apr_first,double apr_end,
			int check_first,int check_end,String tender_account_min,String tender_account_max,String period_month,String period_day,
			String validate,int award_status,double award_scale_first,double award_scale_end,double award_account_first,double award_account_end,
			int subscribe_status,String institution,String counter_guarantee , String styles,double vip_frost_scale,int late_days_month,
			int late_days_day,double vip_late_scale, double all_late_scale,double all_frost_scale,int version,String identifier,
			double locan_fee,int locan_month, double locan_fee_month,double day_fee) throws SQLException{
		Dao.Tables.t_borrow_type  t_shove_borrow_type = new Dao().new Tables().new t_borrow_type();

		t_shove_borrow_type.title.setValue(title);
		t_shove_borrow_type.description.setValue(description);
		t_shove_borrow_type.account_multiple.setValue(account_multiple);
		t_shove_borrow_type.password_status.setValue(password_status);
		t_shove_borrow_type.status.setValue(status);
		
		t_shove_borrow_type.amount_type.setValue(amount_type);
		t_shove_borrow_type.amount_first.setValue(amount_first);
		t_shove_borrow_type.amount_end.setValue(amount_end);
		t_shove_borrow_type.identifier.setValue(identifier);
		t_shove_borrow_type.apr_first.setValue(apr_first);
		t_shove_borrow_type.apr_end.setValue(apr_end);	
		
		t_shove_borrow_type.check_first.setValue(check_first);
		t_shove_borrow_type.check_end.setValue(check_end);
		t_shove_borrow_type.tender_account_min.setValue(tender_account_min);
		t_shove_borrow_type.tender_account_max.setValue(tender_account_max);
		t_shove_borrow_type.period_month.setValue(period_month);
		t_shove_borrow_type.period_day.setValue(period_day);
		
		t_shove_borrow_type.validate.setValue(validate);	
		t_shove_borrow_type.award_status.setValue(award_status);
		t_shove_borrow_type.award_scale_first.setValue(award_scale_first);
		t_shove_borrow_type.award_scale_end.setValue(award_scale_end);
		t_shove_borrow_type.award_account_first.setValue(award_account_first);
		t_shove_borrow_type.award_account_end.setValue(award_account_end);
		
		t_shove_borrow_type.subscribe_status.setValue(subscribe_status);
		t_shove_borrow_type.institution.setValue(institution);
		t_shove_borrow_type.counter_guarantee.setValue(counter_guarantee);
		t_shove_borrow_type.styles.setValue(styles);
		t_shove_borrow_type.vip_frost_scale.setValue(vip_frost_scale);
		t_shove_borrow_type.late_days_month.setValue(late_days_month);
		
		t_shove_borrow_type.late_days_day.setValue(late_days_day);
		t_shove_borrow_type.vip_late_scale.setValue(vip_late_scale);
		t_shove_borrow_type.all_late_scale.setValue(all_late_scale);
		t_shove_borrow_type.all_frost_scale.setValue(all_frost_scale);
		t_shove_borrow_type.version.setValue(version);
		
		t_shove_borrow_type.locan_fee.setValue(locan_fee);
		t_shove_borrow_type.locan_fee_month.setValue(locan_fee_month);
		t_shove_borrow_type.locan_month.setValue(locan_month);
		t_shove_borrow_type.day_fee.setValue(day_fee);
		
		return  t_shove_borrow_type.update(conn, " id = " + id);
	}
	
	/**
	 * 根据编号查询标种
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryShoveBorrowTypeById(Connection  conn, int id) throws SQLException, DataException{

		Dao.Tables.t_borrow_type  t_shove_borrow_type = new Dao().new Tables().new t_borrow_type();
		DataSet  ds= t_shove_borrow_type.open(conn, " * ", " id = "+ id, "", -1, -1);

		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public Map<String, String> queryShoveBorrowTypeByNid(Connection conn,
			String nid) throws SQLException, DataException {
		Dao.Tables.t_borrow_type  t_shove_borrow_type = new Dao().new Tables().new t_borrow_type();
		DataSet  ds=t_shove_borrow_type.open(conn, " * ", " nid = '"+ StringEscapeUtils.escapeSql(nid)+"'", "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	
	/**
	 * 分页查询
	 * @param conn
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryShoveBorrowTypePageAll( Connection  conn , PageBean<Map<String,Object>>  pageBean) throws SQLException, DataException{
		Dao.Tables.t_borrow_type  t_shove_borrow_type = new Dao().new Tables().new t_borrow_type();
		 long c= t_shove_borrow_type.getCount(conn, " ");
		 boolean  result=pageBean.setTotalNum(c);//-------->将总页数(c)放到PageBean<T>中
		 if(result){
			DataSet ds= t_shove_borrow_type.open(conn, " * ", " show_type = 1", " ", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();//将DataSet转换成map
			pageBean.setPage(ds.tables.get(0).rows.rowsMap);//放入PageBean 类
		}	
	}
	
	
	/**
	 * 删除
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public Long deleteShoveBorrowType(Connection conn,int id) throws SQLException{
		Dao.Tables.t_borrow_type  t_shove_borrow_type = new Dao().new Tables().new t_borrow_type();
		
		return t_shove_borrow_type.delete(conn, " id = " + id);
	}

	/**
	 * 根据所传字段名查询相应结果
	 * @param conn
	 * @param field 数据库字段名（只能传一个）
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String queryOneByNid(Connection conn, String field, String nid) throws SQLException, DataException {
		Dao.Tables.t_borrow_type  t_shove_borrow_type = new Dao().new Tables().new t_borrow_type();
		DataSet  ds=t_shove_borrow_type.open(conn, field+" as fieldName ", " nid = '"+ StringEscapeUtils.escapeSql(nid)+"'", "", -1, -1);
		String result = "";
		Map<String,String> map = BeanMapUtils.dataSetToMap(ds);
		if(map != null){
			result = map.get("fieldName");
		}
		return result;
	}

	public long updateShoveBorrowType(Connection conn, long id, String identifier) throws SQLException {
		Dao.Tables.t_borrow_type  t_shove_borrow_type = new Dao().new Tables().new t_borrow_type();
		t_shove_borrow_type.identifier.setValue(identifier);
		return t_shove_borrow_type.update(conn, " id = " + id);
	}

	
}

