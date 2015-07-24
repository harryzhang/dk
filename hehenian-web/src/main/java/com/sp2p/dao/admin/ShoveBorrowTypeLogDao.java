package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 标题种类历史记录    
 * @author C_J
 *
 */
public class ShoveBorrowTypeLogDao {
	
	public Long addShoveBoorowTypeLog(Connection conn,String nid,int status,String name,String title,String description,double account_multiple,int password_status ,
			int amount_type,double amount_first,double amount_end ,double apr_first,double apr_end,
			int check_first,int check_end,String tender_account_min,String tender_account_max,String period_month,String period_day,
			String validate,int award_status,double award_scale_first,double award_scale_end,double award_account_first,double award_account_end,
			int subscribe_status,int verify_auto_status,String verify_auto_remark,String institution,String counter_guarantee,String styles,double vip_frost_scale,int late_days_month,
			int late_days_day,double vip_late_scale, double all_late_scale,double all_frost_scale,long user_id,long update_time,String update_ip,String identifier,
			double locan_fee,int locan_month, double locan_fee_month,double day_fee) throws SQLException{
			Dao.Tables.t_borrow_type_log  t_shove_borrow_typeLog = new Dao().new Tables().new t_borrow_type_log();
			
			t_shove_borrow_typeLog.nid.setValue(nid);
			t_shove_borrow_typeLog._name.setValue(name);
			t_shove_borrow_typeLog.title.setValue(title);
			t_shove_borrow_typeLog.description.setValue(description);
			t_shove_borrow_typeLog.account_multiple.setValue(account_multiple);
			t_shove_borrow_typeLog.password_status.setValue(password_status);
			t_shove_borrow_typeLog.status.setValue(status);
			
			t_shove_borrow_typeLog.amount_type.setValue(amount_type);
			t_shove_borrow_typeLog.amount_first.setValue(amount_first);
			t_shove_borrow_typeLog.amount_end.setValue(amount_end);
			t_shove_borrow_typeLog.apr_first.setValue(apr_first);
			t_shove_borrow_typeLog.apr_end.setValue(apr_end);	
			
			t_shove_borrow_typeLog.check_first.setValue(check_first);
			t_shove_borrow_typeLog.check_end.setValue(check_end);
			t_shove_borrow_typeLog.tender_account_min.setValue(tender_account_min);
			t_shove_borrow_typeLog.tender_account_max.setValue(tender_account_max);
			t_shove_borrow_typeLog.period_month.setValue(period_month);
			t_shove_borrow_typeLog.period_day.setValue(period_day);
			
			t_shove_borrow_typeLog.validate.setValue(validate);	
			t_shove_borrow_typeLog.award_status.setValue(award_status);
			t_shove_borrow_typeLog.award_scale_first.setValue(award_scale_first);
			t_shove_borrow_typeLog.award_scale_end.setValue(award_scale_end);
			t_shove_borrow_typeLog.award_account_first.setValue(award_account_first);
			t_shove_borrow_typeLog.award_account_end.setValue(award_account_end);
			
			t_shove_borrow_typeLog.subscribe_status.setValue(subscribe_status);
			t_shove_borrow_typeLog.verify_auto_status.setValue(verify_auto_status);
			t_shove_borrow_typeLog.verify_auto_remark.setValue(verify_auto_remark);
			t_shove_borrow_typeLog.institution.setValue(institution);
			t_shove_borrow_typeLog.counter_guarantee.setValue(counter_guarantee);
			t_shove_borrow_typeLog.styles.setValue(styles);
			t_shove_borrow_typeLog.vip_frost_scale.setValue(vip_frost_scale);
			t_shove_borrow_typeLog.late_days_month.setValue(late_days_month);
			
			t_shove_borrow_typeLog.late_days_day.setValue(late_days_day);
			t_shove_borrow_typeLog.vip_late_scale.setValue(vip_late_scale);
			t_shove_borrow_typeLog.all_late_scale.setValue(all_late_scale);
			t_shove_borrow_typeLog.all_frost_scale.setValue(all_frost_scale);
			t_shove_borrow_typeLog.user_id.setValue(user_id);
			t_shove_borrow_typeLog.update_ip.setValue(update_ip);
			t_shove_borrow_typeLog.update_time.setValue(update_time);
			t_shove_borrow_typeLog.identifier.setValue(identifier);
			
			t_shove_borrow_typeLog.locan_fee.setValue(locan_fee);
			t_shove_borrow_typeLog.locan_fee_month.setValue(locan_fee_month);
			t_shove_borrow_typeLog.locan_month.setValue(locan_month); 
			t_shove_borrow_typeLog.day_fee.setValue(day_fee);
			return t_shove_borrow_typeLog.insert(conn);
}
	
	/**
	 * 根据唯一标识名查询标种类型
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String>  queryBorrowTypeLogByNid(Connection  conn,String nid_log) throws SQLException, DataException{
		Dao.Tables.t_borrow_type_log  t_shove_borrow_typeLog = new Dao().new Tables().new t_borrow_type_log();
		DataSet  ds = t_shove_borrow_typeLog.open(conn, " * ", " identifier = '"+StringEscapeUtils.escapeSql(nid_log)+"'","", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		
		return BeanMapUtils.dataSetToMap(ds);
	}

}
