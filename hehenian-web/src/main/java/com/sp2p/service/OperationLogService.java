package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.OperationLogDao;

/**
 * 系统操作表    1 前台/  2 后台
 * @author C_J
 *
 */
public class OperationLogService  extends BaseService{
	
	//public static Log log = LogFactory.getLog(OperationLogService.class);

	private  OperationLogDao operationLogDao;

	/**
	 * 添加 系统操作日志     
	 * @param operation_table  操作表
	 * @param operation_user  操作人
	 * @param operation_type   操作类型
	 * @param operation_ip    IP
	 * @param operation_money   操作金额
	 * @param operation_remarks   备注
	 * @param operation_around   区 分 1 前台/ 2 后台   
	 * @return
	 * @throws SQLException 
	 */
	 public  long  addOperationLog(String operation_table,String operation_user,int operation_type,String operation_ip,
			  double operation_money,String operation_remarks ,int operation_around) throws SQLException{
		 Connection   conn = MySQL.getConnection();
		 long result = -1L;
		 	try {
			 result = operationLogDao.addOperationLog(conn, operation_table, operation_user, operation_type, operation_ip, operation_money, operation_remarks, operation_around);
			 conn.commit();
		 	} catch (SQLException e) {
				//log.error(e);
				conn.rollback();
				e.printStackTrace();
				throw e ;
			}finally{
				conn.close();
			}
		 return result ;
	 }

	 
	 /**
		 * 查询所有操作记录
		 * 
		 * @return
		 * @throws DataException
		 * @throws SQLException
		 */
		public void queryAdminRecordAll(PageBean pageBean, String adminName,
				String operateTime,String operation_around) throws DataException, SQLException {

			Connection conn = connectionManager.getConnection();
			try {
				StringBuffer condition = new StringBuffer();
				if (StringUtils.isNotBlank(adminName)) {
					condition
							.append(" and  operation_user  like '%"
									+ StringEscapeUtils.escapeSql(adminName.trim())
									+ "%' ");
				}
				if (StringUtils.isNotBlank(operateTime)) {
					condition.append(" AND Date_format(operation_time,'%Y-%m-%d') = '" +  StringEscapeUtils.escapeSql(operateTime.trim())+"'");
				}
				condition.append("AND operation_around='"+operation_around+"'");
				dataPage(conn, pageBean, "t_operation_log", "*", "order by operation_time desc", condition.toString());
			} catch (SQLException e) {
				//log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}

		}
		public void queryManagerOperationLog(PageBean pageBean,String operation_around,String keyword,String startime ,String endtime) throws DataException, SQLException {
			StringBuffer condition = new StringBuffer();
			condition.append("AND operation_around='"+operation_around+"'");
			if(StringUtils.isNotBlank(keyword)){
				condition.append(" and operation_user like '%");
				condition.append(StringEscapeUtils.escapeSql(keyword));
				condition.append("%'");
			}
			if(StringUtils.isNotBlank(startime)){
				condition.append(" and operation_time >= '");
				condition.append(StringEscapeUtils.escapeSql(startime));
				condition.append("'");
			}
			if(StringUtils.isNotBlank(endtime)){
				condition.append(" and operation_time <= '");
				condition.append(StringEscapeUtils.escapeSql(endtime));
				condition.append("'");
			}
			Connection conn = MySQL.getConnection();
			try {
				
				
				dataPage(conn, pageBean, "t_operation_log", "*", "order by operation_time desc", condition.toString());
			} catch (SQLException e) {
				//log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}

		}
	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}
}
