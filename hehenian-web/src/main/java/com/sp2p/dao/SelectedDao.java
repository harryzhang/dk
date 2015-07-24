package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * @ClassName: SelectedDao.java
 * @Author: gang.lv
 * @Date: 2013-3-6 下午03:20:55
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 下拉列表动态显示处理
 */
public class SelectedDao {

	public Map<String, String> queryBorrowDetailById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_detail borrowDetail = new Dao().new Views().new v_t_borrow_detail();
		DataSet dataSet = borrowDetail.open(conn, " * ", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: borrowPurpose
	 * @Param: SelectedDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午03:38:05
	 * @Return:
	 * @Descb:
	 * @Throws: 借款目标下拉列表
	 */
	public List<Map<String, Object>> borrowPurpose(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_select t_select = new Dao().new Tables().new t_select();
		DataSet dataSet = t_select.open(conn, "selectValue,selectName",
				" typeId=1 and deleted = 1 ", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: borrowDeadline
	 * @Param: SelectedDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午04:12:49
	 * @Return:
	 * @Descb: 借款期限下拉列表
	 * @Throws:
	 */
	public List<Map<String, Object>> borrowDeadline(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_select t_select = new Dao().new Tables().new t_select();
		DataSet dataSet = t_select.open(conn, "selectValue,selectName",
				" typeId=4 and deleted = 1 ", "  selectValue asc  ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: borrowAmountRange
	 * @Param: SelectedDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午04:13:05
	 * @Return:
	 * @Descb: 借款金额下拉列表
	 * @Throws:
	 */
	public List<Map<String, Object>> borrowAmountRange(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_select t_select = new Dao().new Tables().new t_select();
		DataSet dataSet = t_select.open(conn, "selectValue,selectName",
				" typeId=5 and deleted = 1 ", " selectValue asc ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: borrowRaiseTerm
	 * @Param: SelectedDao
	 * @Author: gang.lv
	 * @Date: 2013-3-7 下午02:40:53
	 * @Return:
	 * @Descb: 筹标期限下拉列表
	 * @Throws:
	 */
	public List<Map<String, Object>> borrowRaiseTerm(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_select t_select = new Dao().new Tables().new t_select();
		DataSet dataSet = t_select.open(conn, "selectValue,selectName",
				" typeId=6 and deleted = 1 ", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * @MethodName: userGroup
	 * @Param: SelectedDao
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午11:31:58
	 * @Return:
	 * @Descb: 用户分组下拉列表
	 * @Throws:
	 */
	public List<Map<String, Object>> userGroup(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_group t_group = new Dao().new Tables().new t_group();
		DataSet dataSet = t_group.open(conn,
				"id as selectValue,groupName as selectName", " ", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	
	/**   
	 * @MethodName: sysImageList  
	 * @Param: SelectedDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-4 下午11:45:28
	 * @Return:    
	 * @Descb: 系统头像列表
	 * @Throws:
	*/
	public List<Map<String, Object>> sysImageList(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_sysimages t_sysImages = new Dao().new Tables().new t_sysimages();
		DataSet dataSet = t_sysImages.open(conn,
				"id as selectValue,imagePath as selectName ", " enable=1 ", " id asc ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}


	public List<Map<String, Object>> getDebtAuctionDays(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_select t_select = new Dao().new Tables().new t_select();
		DataSet dataSet = t_select.open(conn, "selectValue,selectName",
				" typeId=6 and deleted = 1 ", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**   
	 * @MethodName: queryNoticeType  
	 * @Param: SelectedDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-12 下午08:14:19
	 * @Return:    
	 * @Descb: 查询通知类型的通知状态
	 * @Throws:
	*/
	public List<Map<String, Object>> queryNoticeType(Connection conn,
			long userId, String noticeMode) throws SQLException, DataException {
		String condition = " userId = "+userId;
		Dao.Tables.t_noticecon t_noticecon = new Dao().new Tables().new t_noticecon();
		DataSet dataSet = t_noticecon.open(conn,
				noticeMode+" as flag", condition, " noticeMode asc ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition= null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**   
	 * @MethodName: queryNoticeMSG  
	 * @Param: SelectedDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-12 下午11:49:35
	 * @Return:    
	 * @Descb: 查询通知内容
	 * @Throws:
	*/
	public List<Map<String, Object>> queryNoticeMSG(Connection conn,
			long userId) throws SQLException, DataException {
		String condition = " userId = "+userId;
		Dao.Tables.t_notice_msg t_notice_msg = new Dao().new Tables().new t_notice_msg();
		DataSet dataSet = t_notice_msg.open(conn,
				" * ", condition, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	/**   
	 * @MethodName: delNoticeMSG  
	 * @Param: SelectedDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-13 下午02:27:52
	 * @Return:    
	 * @Descb: 删除通知内容
	 * @Throws:
	*/
	public long delNoticeMSG(Connection conn,long userId) throws SQLException{
		Dao.Tables.t_notice_msg t_notice_msg = new Dao().new Tables().new t_notice_msg();
		return t_notice_msg.delete(conn, " userId = "+userId);
	}
}
