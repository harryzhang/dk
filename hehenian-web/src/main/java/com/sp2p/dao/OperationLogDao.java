package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.sp2p.database.Dao;

/**
 * 系统操作日志dao
 * @author C_J
 *
 */
public class OperationLogDao {
	/**
	 * 添加操作日志
	 * @param operation_table  操作表
	 * @param operation_user  操作人
	 * @param operation_type   状态
	 * @param operation_ip   操作人Ip
	 * @param operation_money   操作金额
	 * @param operation_remarks  备注
	 * @param operation_around    区分前后台  1 前台  2  后台
	 * @return
	 * @throws SQLException 
	 */
	 public  long  addOperationLog(Connection  conn,String operation_table,String operation_user,int  operation_type,String operation_ip,
			 					  double operation_money,String operation_remarks ,int operation_around) throws SQLException{
		 Dao.Tables.t_operation_log t_opration_log  = new Dao().new Tables().new t_operation_log();
		 t_opration_log.operation_table.setValue(operation_table);
		 t_opration_log.operation_user.setValue(operation_user);
		 t_opration_log.operation_type.setValue(operation_type);
		 t_opration_log.operation_ip.setValue(operation_ip);
		 t_opration_log.operation_money.setValue(operation_money);
		 t_opration_log.operation_remarks.setValue(operation_remarks);
		 t_opration_log.operation_around.setValue(operation_around);
		 t_opration_log.operation_time.setValue(new Date());
		
		 return  t_opration_log.insert(conn);
	 }
	 
	
}
