/**
 * 
 */
package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Views.v_t_borrow_h_flowmark;
import com.sp2p.util.DBReflectUtil;

/**
 * 债权转让
 * 
 * @author Administrator
 * 
 */
public class AssignmentDebtDao {

	/**
	 * 添加债权转让
	 * 
	 * @param conn
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long addAssignmentDebt(Connection conn, Map<String, String> paramMap)
			throws SQLException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DBReflectUtil.mapToTableValue(t_assignment_debt, paramMap);
		return t_assignment_debt.insert(conn);

	}

	/**
	 * 修改债权转让
	 * 
	 * @param conn
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long updateAssignmentDebt(Connection conn, long id,String debtStatus,
			Map<String, String> paramMap) throws SQLException {
		String idStr = StringEscapeUtils.escapeSql("'"+debtStatus+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DBReflectUtil.mapToTableValue(t_assignment_debt, paramMap);
		StringBuilder condition = new StringBuilder( " 1=1 ");
		condition.append(" and id=");
		condition.append(id);
		if(StringUtils.isNotBlank(debtStatus)){
			condition.append(" and debtStatus in(");
			condition.append(idSQL); 
			condition.append(")");
		}
		t_assignment_debt.debtStatus.setValue(paramMap.get("debtStatus"));
		long result=t_assignment_debt.update(conn, condition.toString());
		condition=null;
		return result;

	}

	/**
	 * 删除债权转让，可删除多个
	 * 
	 * @param conn
	 * @param ids
	 *            id字符串，用,隔开
	 * @return
	 * @throws SQLException
	 */
	public long deleteAssignmentDebt(Connection conn, String ids)
			throws SQLException {
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		return t_assignment_debt.delete(conn, " id in("
				+ idSQL + ")");
	}

	/**
	 * 根据ID获债权转让信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getAssignmentDebt(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DataSet ds = t_assignment_debt.open(conn, "*", "id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 *       跳转到审核页面
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryDebtBacking(Connection conn,
			long borrowId, long userId, long investId) throws SQLException,
			DataException {

		Dao.Views.v_debt_invest v_debt_invest = new Dao().new Views().new v_debt_invest();
		StringBuilder condition = new StringBuilder(" 1=1   ");
		if (borrowId > 0) {
			condition.append(" and borrowId =");
			condition.append(borrowId);
		}
		if (userId > 0) {
			condition.append(" and investor =");
			condition.append(userId);
		}

		if (investId > 0) {
			condition.append(" and investId =");
			condition.append(investId);
		}
		DataSet ds = v_debt_invest.open(conn, "*", condition.toString(), "",
				-1, -1);
		ds.tables.get(0).rows.genRowsMap();
		condition=null;
		return ds.tables.get(0).rows.rowsMap;
	}

	public long addDebtMsg(Connection conn, long id, Long userId,
			String msgContent) throws SQLException {
		Dao.Tables.t_msgboard t_msgboard = new Dao().new Tables().new t_msgboard();
		t_msgboard.msgContent.setValue(msgContent);
		t_msgboard.modeId.setValue(id);
		// 债权转让留言类型
		t_msgboard.msgboardType.setValue(2);
		t_msgboard.msger.setValue(userId);
		t_msgboard.msgTime.setValue(new Date());
		return t_msgboard.insert(conn);
	}

	/**
	 * 根据借款Id查询借款
	 * 
	 * @param conn
	 * @param borrowId
	 * @param debtStatus
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAssignmentDebtIds(Connection conn,
			long borrowId, String debtStatus) throws SQLException,
			DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		if (borrowId > 0) {
			condition.append(" and borrowId=");
			condition.append(borrowId);
		}

		if (StringUtils.isNotBlank(debtStatus)) {
			String idStr = StringEscapeUtils.escapeSql("'"+debtStatus+"'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String [] array = idStr.split(",");
			for(int n=0;n<=array.length-1;n++){
			   idSQL += ","+array[n];
			}
			condition.append(" and debtStatus in (");
			condition.append(idSQL);
			condition.append(") ");
		}

		DataSet ds = t_assignment_debt.open(conn, "id", condition.toString(),
				"", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		condition=null;
		return ds.tables.get(0).rows.rowsMap;
	}

	public List<Map<String, Object>> queryDueDebt(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DataSet ds = t_assignment_debt.open(conn,"id","date_add(publishTime, interval 1 day)<=now() and debtStatus=2","", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}

	/**
	 * 获取借款标题
	 * 
	 * @param strToLong
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String getBorrowTitle(Connection conn, long debtId)
			throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		DataSet ds = t_borrow.open(conn, "borrowTitle",
				" id = (select borrowId from t_assignment_debt t where t.id="
						+ debtId + ")", "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		String borrowTitle = null;
		if (map != null) {
			borrowTitle = map.get("borrowTitle");
		}
		map=null;
		return borrowTitle;
	}

	/**
	 * 获取用户名称
	 * 
	 * @param userId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryUserNameById(Connection conn, long userId)
			throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet ds = t_user
				.open(conn, "username", " id =" + userId, "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		String username = null;
		if (map != null) {
			username = map.get("username");
		}
		map=null;
		return username;
	}
	/**
	 * 判断是否可以债权转让
	 * @param strToLong
	 * @param strToLong2
	 * @return 返回true则不可以转让，否则可以
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public boolean isHaveAssignmentDebt(Connection conn, long investId, long userId) throws SQLException, DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		StringBuilder condition = new StringBuilder( " 1=1 ");
		condition.append(" and investId = ");
		condition.append(investId);
		condition.append(" and alienatorId = ");
		condition.append(userId);
		condition.append(" and debtStatus in (1,2,3)");
		DataSet ds = t_assignment_debt.open(conn,"count(1) as counts",condition.toString(),"", -1, -1);
		long count = Convert.strToLong(BeanMapUtils.dataSetToMap(ds).get("counts"),-1);
		condition=null;
		return count > 0;
	}
	/**
	 * 判断债权转让是否在某一状态
	 * @param strToLong
	 * @param strToLong2
	 * @return 返回true则在，否则不在
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public boolean isDebtInStatus(Connection conn,long id,String debtStatus) throws SQLException, DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		String idStr = StringEscapeUtils.escapeSql("'"+debtStatus+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		StringBuilder condition = new StringBuilder( " 1=1 ");
		condition.append(" and id = ");
		condition.append(id);
		condition.append(" and debtStatus in (");
		condition.append(idSQL);
		condition.append(")");
		DataSet ds = t_assignment_debt.open(conn,"count(1) as counts ",condition.toString(),"", -1, -1);
		long count = Convert.strToLong(BeanMapUtils.dataSetToMap(ds).get("counts"),-1);
		condition=null;
		return count > 0;
	}
		/**
	 * 查询债权转让人和竞拍成功人
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String>  queryDebtUserName(Connection  conn,long aid) throws DataException, SQLException{
		Dao.Views.v_t_assignment_debt_username  v_t_assignment_debt_username = new Dao().new Views().new v_t_assignment_debt_username();
		DataSet  ds = v_t_assignment_debt_username.open(conn, " * ", " id = " + aid, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	
	public Map<String, String> queryApplyDebtDetail(Connection conn) throws SQLException, DataException{
		Dao.Views.v_t_assignment_debt_audit t_assignment_debt_audit = new Dao().new Views().new v_t_assignment_debt_audit();
		DataSet dataSet = t_assignment_debt_audit.open(conn, "sum(debtSum) as applydebtSum", "debtStatus=1", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryApplySuccessDebtDetail(Connection conn) throws SQLException, DataException{
		Dao.Views.v_admin_assignment_debt_borrow t_assignment_debt_audit = new Dao().new Views().new v_admin_assignment_debt_borrow();
		DataSet dataSet = t_assignment_debt_audit.open(conn, "sum(debtSum) as successapplydebtSum", "debtStatus=3", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryApplyFailDebtDetail(Connection conn) throws SQLException, DataException{
		Dao.Views.v_admin_assignment_debt_borrow admin_assignment_debt_borrow = new Dao().new Views().new v_admin_assignment_debt_borrow();
		DataSet dataSet = admin_assignment_debt_borrow.open(conn, "sum(debtSum) as failapplydebtSum", "debtStatus in (4,5,6,7)", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public List<Map<String, Object>> queryAssignmentDebtable(Connection conn, long userId) throws SQLException, DataException {
		Dao.Views.v_t_can_assignment_borrow vt = new Dao().new Views().new v_t_can_assignment_borrow();
		DataSet ds = vt.open(conn," * "," investor="+userId,"", 0, 8);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
}
