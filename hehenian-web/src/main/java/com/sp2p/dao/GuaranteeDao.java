package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Tables.t_materialsauth;
import com.sp2p.database.Dao.Views.v_t_borrow_index;
import com.sp2p.database.Dao.Views.v_t_user_adminchecklist;
import com.sp2p.database.Dao.Views.v_t_user_credit_msg;

public class GuaranteeDao {
	/**
	 * 前台查询用户的个人信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserInformation(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_user_frontmeg frontMeg = new Dao().new Views().new v_t_user_frontmeg();
		DataSet dataSet = frontMeg.open(conn, " *  ", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * * 查询个人信息信息
	 * 
	 * @param conn
	 * @param id
	 *            用户id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPerUserCreditfornt(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_user_credit_msg msg = new Dao().new Views().new v_t_user_credit_msg();
		DataSet dataSet = msg.open(conn, "", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 动态显示用户的等级图标vip
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserVipPicture(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_vippicture vippicture = new Dao().new Views().new v_t_vippicture();
		DataSet dataSet = vippicture.open(conn, "", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 动态显示用户的信用分数等级
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserCriditPicture(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_criditpicture criditpictur = new Dao().new Views().new v_t_criditpicture();
		DataSet dataSet = criditpictur.open(conn, "", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户的提前还款的统计 和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver15(Connection conn, long id) throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"select  IFNULL(count(*),0) counts, a.borrowId ,IFNULL(count(*),0) * 0 as fenshu from t_repayment a left join t_borrow b on a.borrowId=b.id where DATEDIFF(repayDate,realRepayDate) BETWEEN 0 and 15  and b.publisher= "
								+ id + " group by a.borrowId ");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户的提前还款的统计 和分数 大于16天的还款统计和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver16(Connection conn, long id) throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"select  IFNULL(count(*),0) counts, a.borrowId ,IFNULL(count(*),0) * 1 as fenshu from t_repayment a left join t_borrow b on a.borrowId=b.id where DATEDIFF(repayDate,realRepayDate) > 16  and b.publisher= "
								+ id + " group by a.borrowId ");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户的逾期还款的统计 和分数 大于1-10天的还款统计和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver10(Connection conn, long id) throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"select IFNULL(count(*),0) counts, a.borrowId ,IFNULL(count(*),0) * -3 as fenshu from t_repayment a left join t_borrow b on a.borrowId=b.id where a.lateDay  BETWEEN 1 and 10   and b.publisher= "
								+ id + " group by a.borrowId ");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户的逾期还款的统计 和分数 大于11-30天的还款统计和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver30(Connection conn, long id) throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"select  IFNULL(count(*),0) counts, a.borrowId ,IFNULL(count(*),0) * -10 as fenshu from t_repayment a left join t_borrow b on a.borrowId=b.id where a.lateDay  BETWEEN 11 and 30   and b.publisher= "
								+ id + " group by a.borrowId ");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户的逾期还款的统计 和分数 大于31-90天的还款统计和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver90(Connection conn, long id) throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"select  IFNULL(count(*),0) counts, a.borrowId ,IFNULL(count(*),0) * -30 as fenshu from t_repayment a left join t_borrow b on a.borrowId=b.id where a.lateDay  BETWEEN 31 and 90   and b.publisher= "
								+ id + " group by a.borrowId ");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户的逾期还款的统计 和分数 大于90天以上的还款统计和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver90up(Connection conn, long id) throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"select  IFNULL(count(*),0) counts, a.borrowId ,IFNULL(count(*),0) * -50 as fenshu from t_repayment a left join t_borrow b on a.borrowId=b.id where a.lateDay  > 90   and b.publisher= "
								+ id + " group by a.borrowId ");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/*
	 * //查询用户逾期的次数和信用分数 public Map<String, String> queryReplament(Connection
	 * conn, long userid) throws SQLException, DataException { DataSet dataSet =
	 * MySQL.executeQuery(conn,
	 * "select * from T_REPLAMENT_INTEGRAL where USERID = "+userid); return
	 * BeanMapUtils.dataSetToMap(dataSet); }
	 */

	/**
	 * 查询个人的信用总分
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserCredit(Connection conn, Long userId) throws SQLException, DataException {
		DataSet dataSet = MySQL.executeQuery(conn, " select tm.materAuthTypeId from t_materialsauth tm where tm.userId = " + userId
				+ " and tm.auditStatus = 3 ");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户的好友列表
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryUserFriends(Connection conn, Long userId) throws SQLException, DataException {
		// Dao.Views.v_t_books_common booksCommon = new Dao().new Views().new
		// v_t_books_common();
		Dao.Views.v_t_user_frends frends = new Dao().new Views().new v_t_user_frends();
		DataSet dataSet = frends.open(conn, "", " 1 = 1 AND userId = " + userId, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 删除关注好友
	 * 
	 * @param conn
	 * @param recommendUserId
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long DeteleUserFriends(Connection conn, Long attentionUserId, Long userId) throws SQLException, DataException {
		Dao.Tables.t_concern concern = new Dao().new Tables().new t_concern();
		return concern.delete(conn, "moduleType=1 AND moduleId=" + attentionUserId + " AND userId=" + userId);
	}

	/**
	 * 统计个人的借款记录
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserInerseRecode(Connection conn, Long userId) throws SQLException, DataException {
		DataSet dataSet = MySQL.executeQuery(conn, "select IFNULL(COUNT(*),0) as investor from t_invest ti where ti.investor = " + userId);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 统计个人的投标记录
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowRecode(Connection conn, Long userId) throws SQLException, DataException {
		DataSet dataSet = MySQL.executeQuery(conn, "select  IFNULL(COUNT(*),0) as publisher from t_borrow tb where tb.publisher = " + userId);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询个人的借款列表
	 * 
	 * @param conn
	 * @return userId 用户id
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryMyBorrowList(Connection conn, Long userId) throws SQLException, DataException {

		Dao.Views.v_t_user_myborrowlist myBorrowLis = new Dao().new Views().new v_t_user_myborrowlist();
		DataSet dataSet = myBorrowLis.open(conn, " * ", "   uid = " + userId, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 合和年查询 信用积分
	 * 
	 * @param conn
	 * @param id
	 *            用户id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryPerUserCreditforntHHN(Connection conn, long userId) throws SQLException, DataException {
		String sql = "SELECT ifnull(tm.criditing, 0) criditing , ty.`name` AS materName, tm.auditStatus AS statu FROM t_user tu JOIN t_materialsauth tm ON tm.userId = tu.id ";
		sql += " JOIN t_materialsauthtype ty ON tm.materAuthTypeId = ty.id AND ty.id > 5 WHERE tu.id = " + userId + " ORDER BY ty.id";
		DataSet dataSet = MySQL.executeQuery(conn, sql);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

}
