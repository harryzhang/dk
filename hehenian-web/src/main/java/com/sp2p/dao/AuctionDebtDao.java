/**
 * 
 */
package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.util.DBReflectUtil;

/**
 * 竞拍债权
 * 
 * @author Administrator
 * 
 */
public class AuctionDebtDao {

	/**
	 * 添加竞拍债权
	 * 
	 * @param conn
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long addAuctionDebt(Connection conn, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_auction_debt t_auction_debt = new Dao().new Tables().new t_auction_debt();
		DBReflectUtil.mapToTableValue(t_auction_debt, paramMap);
		return t_auction_debt.insert(conn);

	}

	/**
	 * 修改竞拍债权
	 * 
	 * @param conn
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long updateAuctionDebt(Connection conn, long id, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_auction_debt t_auction_debt = new Dao().new Tables().new t_auction_debt();
		DBReflectUtil.mapToTableValue(t_auction_debt, paramMap);
		return t_auction_debt.update(conn, "id=" + id);

	}

	/**
	 * 删除竞拍债权，可删除多个
	 * 
	 * @param conn
	 * @param ids
	 *            id字符串，用,隔开
	 * @return
	 * @throws SQLException
	 */
	public long deleteAuctionDebt(Connection conn, String ids) throws SQLException {
		String idStr = StringEscapeUtils.escapeSql("'" + ids + "'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String[] array = idStr.split(",");
		for (int n = 0; n <= array.length - 1; n++) {
			idSQL += "," + array[n];
		}
		Dao.Tables.t_auction_debt t_auction_debt = new Dao().new Tables().new t_auction_debt();
		return t_auction_debt.delete(conn, " id in(" + idSQL + ")");
	}

	/**
	 * 根据ID获竞拍债权信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getAuctionDebt(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_auction_debt t_auction_debt = new Dao().new Tables().new t_auction_debt();
		DataSet ds = t_auction_debt.open(conn, "*", "id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 根据债权转让Id查询竞拍记录
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryAuctionDebtByDebtId(Connection conn, long debtId) throws SQLException, DataException {
		Dao.Views.v_t_auction_debt_user v_t_auction_debt_user = new Dao().new Views().new v_t_auction_debt_user();
		DataSet ds = v_t_auction_debt_user.open(conn, " id  ,  debtId  , auctionTime  ,   auctionPrice  ,f_formatting_username(username) as username  , userId   ", "debtId="
				+ debtId, "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}

	public Map<String, String> queryAuctionMaxPriceAndCount(Connection conn,long debtId) throws SQLException, DataException {
		StringBuilder conditon = new StringBuilder(" 1=1 ");
		if(debtId > 0){
			conditon.append(" and debtId=");
			conditon.append(debtId);
		}
		Dao.Tables.t_auction_debt t_auction_debt = new Dao().new Tables().new t_auction_debt();
		DataSet ds = t_auction_debt.open(conn, "id,userId,auctionPrice", conditon.toString(), " auctionPrice desc", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		List<Map<String,Object>> list = ds.tables.get(0).rows.rowsMap;
		Map<String,String>  map = new HashMap<String, String>();
		if(list != null && list.size() > 0){
			Map<String,Object> auctionMap = list.get(0);
			map.put("id",auctionMap.get("id")+"" );
			map.put("userId",auctionMap.get("userId")+"" );
			map.put("maxAuctionPrice",auctionMap.get("auctionPrice")+"" );
			t_auction_debt = new Dao().new Tables().new t_auction_debt();
			ds = t_auction_debt.open(conn, "count(1) as counts", conditon.toString(), "", -1, -1);
			map.put("auctionCount",BeanMapUtils.dataSetToMap(ds).get("counts"));
		}
		Map<String, String> m=BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, "SELECT i.recivedPrincipal-i.hasPrincipal balance," +
				"(SELECT sum(ir.recivedInterest) from t_invest_repayment ir where ir.invest_id=i.id and ir.repayStatus=1 and (ir.parentId is null or ir.parentId=0)) interest," +
				"(SELECT DATEDIFF(MAX(ir.repayDate),NOW()) from t_invest_repayment ir where ir.invest_id=i.id) dayss,(SELECT date(min(ir.repayDate)) from t_invest_repayment ir where ir.invest_id=a.investId AND ir.owner = a.alienatorId and ir.repayStatus=1) nextDay "
				+ "from t_assignment_debt a LEFT JOIN t_invest i on a.investId=i.id where a.id="+debtId));
		if(m!=null){
			map.putAll(m);
		}
		list=null;
		conditon=null;
		return map;
	}

	/**
	 * 根据用户Id获取用户信息
	 * 
	 * @param alienatorId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */

	public Map<String, String> getUserAddressById(Connection conn, long userId) throws SQLException, DataException {
		Dao.Views.v_t_user_address v_t_user_address = new Dao().new Views().new v_t_user_address();
		DataSet ds = v_t_user_address.open(conn, " * ", "id=" + userId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	public Map<String, String> getUserById(Connection conn, long userId) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet ds = t_user.open(conn, "id,username,dealpwd,usableSum,freezeSum,dueinSum", "id=" + userId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 查询用户的竞拍次数
	 * 
	 * @param debtId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long queryAuctionUserTimes(Connection conn, long debtId, long userId) throws SQLException, DataException {
		StringBuilder conditon = new StringBuilder(" 1=1 ");
		if (debtId > 0) {
			conditon.append(" and debtId=");
			conditon.append(debtId);
		}
		if (userId > 0) {
			conditon.append(" and userId=");
			conditon.append(userId);
		}
		Dao.Tables.t_auction_debt t_auction_debt = new Dao().new Tables().new t_auction_debt();
		DataSet ds = t_auction_debt.open(conn, "count(1) as autiontimes", conditon.toString(), "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		long autiontimes = 0;
		if (map != null) {
			autiontimes = Convert.strToLong(map.get("autiontimes"), 0);
		}
		conditon = null;
		map = null;
		ds = null;
		return autiontimes;
	}

	/**
	 * 查找借款人Id
	 * 
	 * @param conn
	 * @param borrowId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public long queryBorrowerByBorrowId(Connection conn, long borrowId) throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		DataSet ds = t_borrow.open(conn, "publisher", " id=" + borrowId, "", -1, -1);
		return Convert.strToLong(BeanMapUtils.dataSetToMap(ds).get("publisher"), -1);
	}

	/**
	 * 根据还款iD查询借款ID
	 * 
	 * @param repayId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public long queryBorrowIdByRepayId(Connection conn, long repayId) throws SQLException, DataException {
		Dao.Tables.t_repayment t_repayment = new Dao().new Tables().new t_repayment();
		DataSet ds = t_repayment.open(conn, "borrowId", " id=" + repayId, "", -1, -1);
		return Convert.strToLong(BeanMapUtils.dataSetToMap(ds).get("borrowId"), -1);
	}

	public Map<String, String> getAuctionDebt(Connection conn, long debtId, long userId) throws SQLException, DataException {
		Dao.Tables.t_auction_debt t_auction_debt = new Dao().new Tables().new t_auction_debt();
		DataSet ds = t_auction_debt.open(conn, "*", "debtId=" + debtId + " and userId=" + userId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

}
