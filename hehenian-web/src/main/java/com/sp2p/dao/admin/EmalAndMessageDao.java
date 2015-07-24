package com.sp2p.dao.admin;

import java.security.interfaces.DSAKey;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_select;

public class EmalAndMessageDao {
	/**
	 * 插入邮件设置表
	 * 
	 * @param conn
	 * @param mailaddress
	 * @param mailpassword
	 * @param sendmail
	 * @param sendname
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMailSet(Connection conn, String mailaddress,
			String mailpassword, String sendmail, String sendname,String port,String host)
			throws SQLException, DataException {
		Dao.Tables.t_mailset mailset = new Dao().new Tables().new t_mailset();
		mailset.mailaddress.setValue(mailaddress);
		mailset.mailpassword.setValue(mailpassword);
		mailset.sendmail.setValue(sendmail);
		mailset.sendname.setValue(sendname);
		mailset.port.setValue(port);
		mailset.host.setValue(host);
		return mailset.update(conn, "");
	}

	/**
	 * 插入短信设置
	 * 
	 * @param conn
	 * @param id
	 * @param username
	 * @param password
	 * @param url
	 * @param enable
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMessageSet(Connection conn, Long id, String username,
			String password, String url, Integer enable) throws SQLException,
			DataException {
		Dao.Tables.t_messageset messageset = new Dao().new Tables().new t_messageset();
		Map<String, String> map = null;
		DataSet ds = messageset.open(conn, "id", " id = " + id, "", -1, -1);
		map = BeanMapUtils.dataSetToMap(ds);
		if (map != null && map.size() > 0) {
			messageset.enable.setValue(enable);
			messageset.username.setValue(username);
			messageset.password.setValue(password);
			messageset.url.setValue(url);
			return messageset.update(conn, " id = " + id);
		} else {
			messageset.id.setValue(id);
			messageset.username.setValue(username);
			messageset.password.setValue(password);
			messageset.url.setValue(url);
			messageset.enable.setValue(enable);
			return messageset.insert(conn);
		}
	}

	public Map<String, String> queryMessageSet(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_messageset messageset = new Dao().new Tables().new t_messageset();
		DataSet dataSet = messageset.open(conn, "*", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询邮件设置参数
	 */
	public Map<String, String> queryMailSet(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_mailset mailset = new Dao().new Tables().new t_mailset();
		DataSet dataSet = mailset.open(conn, "*", " id=" + id, " id desc", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 得到当前最大的ID
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public  Map<String,String> queryMailsetMaxId(Connection  conn) throws DataException, SQLException{
		Dao.Tables.t_mailset mailset = new Dao().new Tables().new t_mailset();
		DataSet ds =  mailset.open(conn, "max(id) as id", "", "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	/**
	 * 添加借款目的
	 * 
	 * @param conn
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addTarage(Connection conn, String name) throws SQLException,
			DataException {
		// =============
		Map<String, String> map = null;
		List<Map<String, Object>> namelist = null;
		List<String> lists = new ArrayList<String>();
		DataSet dataSet = MySQL
				.executeQuery(conn,
						"select MAX(ts.selectValue) as maxv from t_select ts where ts.typeId = 1");
		map = BeanMapUtils.dataSetToMap(dataSet);
		// ===========
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select  ts.selectName as 'name' from t_select ts where ts.deleted = 1 AND ts.typeId = 1  ");
		DataSet namedataset = MySQL.executeQuery(conn, sql.toString());
		namedataset.tables.get(0).rows.genRowsMap();
		namelist = namedataset.tables.get(0).rows.rowsMap;
		if (namelist != null && namelist.size() > 0) {
			for (Map<String, Object> m : namelist) {
				for (Map.Entry<String, Object> e : m.entrySet()) {
					lists.add((String) e.getValue());
				}
			}
		}
		Long tag = -1L;
		for (String str : lists) {
			if (name.equals(str)) {
				tag = 1L;
			}
		}
		String maxv = null;
		if (map != null && map.size() > 0 && tag != 1L) {
			maxv = (Convert.strToInt(map.get("maxv"), 0) + 1) + "";
			Dao.Tables.t_select select = new Dao().new Tables().new t_select();
			select.selectName.setValue(name);
			select.selectValue.setValue(maxv);
			select.typeId.setValue(1);
			sql=null;
			lists=null;
			return select.insert(conn);
		}
		sql=null;
		lists=null;
		return -1L;
	}
	/**
	 * 增加金额范围
	 * 
	 * @param conn
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMomey(Connection conn, String name) throws SQLException,
			DataException {
		// =============
		Map<String, String> map = null;
		List<Map<String, Object>> namelist = null;
		List<String> lists = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select  ts.selectName as 'name' from t_select ts where ts.deleted = 1 AND ts.typeId = 5  ");
		DataSet namedataset = MySQL.executeQuery(conn, sql.toString());
		namedataset.tables.get(0).rows.genRowsMap();
		namelist = namedataset.tables.get(0).rows.rowsMap;
		if (namelist != null && namelist.size() > 0) {
			for (Map<String, Object> m : namelist) {
				for (Map.Entry<String, Object> e : m.entrySet()) {
					lists.add((String) e.getValue());
				}
			}
		}
		Long tag = -1L;
		for (String str : lists) {
			if (name.equals(str)) {
				tag = 1L;
			}
		}
		if (tag != 1L) {
			Dao.Tables.t_select select = new Dao().new Tables().new t_select();
			select.selectName.setValue(name);
			select.selectValue.setValue(name);
			select.typeId.setValue(5);
			sql=null;
			lists=null;
			return select.insert(conn);
		}
		sql=null;
		lists=null;
		return -1L;
	}
	/**
	 * 增加投借款期限
	 * 
	 * @param conn
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addDeadline(Connection conn, String name) throws SQLException,
			DataException {
		// =============
		Map<String, String> map = null;
		List<Map<String, Object>> namelist = null;
		List<String> lists = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ts.selectValue as 'name' from t_select ts where ts.deleted = 1 AND ts.typeId = 4 ");
		DataSet namedataset = MySQL.executeQuery(conn, sql.toString());
		namedataset.tables.get(0).rows.genRowsMap();
		namelist = namedataset.tables.get(0).rows.rowsMap;
		if (namelist != null && namelist.size() > 0) {
			for (Map<String, Object> m : namelist) {
					lists.add(m.get("name")+"");
			}
		}
		Long tag = -1L;
		for (String str : lists) {
			if (name.equals(str)) {
				tag = 1L;
			}
		}
		if (tag != 1L) {
			Dao.Tables.t_select select = new Dao().new Tables().new t_select();
			select.selectName.setValue(name+"个月");
			select.selectValue.setValue(name);
			select.typeId.setValue(4);
			sql=null;
			lists=null;
			return select.insert(conn);
		}
		sql=null;
		lists=null;
		return -1L;
	}
	/**
	 * 增加担保方式
	 * 
	 * @param conn
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addDan(Connection conn, String name) throws SQLException,
			DataException {
		Map<String, String> map = null;
		List<Map<String, Object>> namelist = null;
		List<String> lists = new ArrayList<String>();
		DataSet dataSet = MySQL.executeQuery(conn,"select MAX(ts.selectValue) as maxv from t_select ts where ts.typeId = 2");
		map = BeanMapUtils.dataSetToMap(dataSet);
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ts.selectName as 'name' from t_select ts where  ts.typeId = 2  ");
		DataSet namedataset = MySQL.executeQuery(conn, sql.toString());
		namedataset.tables.get(0).rows.genRowsMap();
		namelist = namedataset.tables.get(0).rows.rowsMap;
		if (namelist != null && namelist.size() > 0) {
			for (Map<String, Object> m : namelist) {
				for (Map.Entry<String, Object> e : m.entrySet()) {
					lists.add((String) e.getValue());
				}
			}
		}
		Long tag = -1L;
		for (String str : lists) {
			if (name.equals(str)) {
				tag = 1L;
			}
		}
		String maxv = null;
		if (map != null && map.size() > 0 && tag != 1L) {
			maxv = (Convert.strToInt(map.get("maxv"), 0) + 1) + "";
			Dao.Tables.t_select select = new Dao().new Tables().new t_select();
			select.selectName.setValue(name);
			select.selectValue.setValue(maxv);
			select.typeId.setValue(2);
			sql=null;
			lists=null;
			return select.insert(conn);
		}
		sql=null;
		lists=null;
		return -1L;
	}

	/**
	 * 增加反担保方式
	 * 
	 * @param conn
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addInver(Connection conn, String name) throws SQLException,
			DataException {
		Map<String, String> map = null;
		List<Map<String, Object>> namelist = null;
		List<String> lists = new ArrayList<String>();
		DataSet dataSet = MySQL
				.executeQuery(conn,
						"select MAX(ts.selectValue) as maxv from t_select ts where ts.typeId = 3");
		map = BeanMapUtils.dataSetToMap(dataSet);
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select  ts.selectName as 'name' from t_select ts where  ts.typeId = 3  ");
		DataSet namedataset = MySQL.executeQuery(conn, sql.toString());
		namedataset.tables.get(0).rows.genRowsMap();
		namelist = namedataset.tables.get(0).rows.rowsMap;
		if (namelist != null && namelist.size() > 0) {
			for (Map<String, Object> m : namelist) {
				for (Map.Entry<String, Object> e : m.entrySet()) {
					lists.add((String) e.getValue());
				}
			}
		}
		Long tag = -1L;
		for (String str : lists) {
			if (name.equals(str)) {
				tag = 1L;
			}
		}
		String maxv = null;
		if (map != null && map.size() > 0 && tag != 1L) {
			maxv = (Convert.strToInt(map.get("maxv"), 0) + 1) + "";
			Dao.Tables.t_select select = new Dao().new Tables().new t_select();
			select.selectName.setValue(name);
			select.selectValue.setValue(maxv);
			select.typeId.setValue(3);
			sql=null;
			lists=null;
			return select.insert(conn);
		}
		sql=null;
		lists=null;
		return -1L;
	}
	/**
	 * 查询所有担保机构
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>>  queryinstitution(Connection  conn) throws SQLException, DataException{
		Dao.Tables.t_select t_select = new Dao().new Tables().new t_select();
		DataSet dataSet = t_select.open(conn, "*", " typeId= 2 and deleted = 1", " id desc", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * 查询所有反担保方式
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>> queryguarantee(Connection  conn) throws SQLException, DataException{
		Dao.Tables.t_select t_select = new Dao().new Tables().new t_select();
		DataSet dataSet = t_select.open(conn, "*", " typeId= 3 and deleted = 1", " id desc", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		
		return dataSet.tables.get(0).rows.rowsMap;
		
	}
	
	/**
	 * 修改借款目的
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @param password
	 * @param url
	 * @param enable
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateTage(Connection conn, Long id, String name)
			throws SQLException, DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		select.selectName.setValue(name);
		return select.update(conn, " id = " + id);
	}

	/**
	 * 修改担保机构   和反担保方式
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateAccount(Connection conn, Long id, String name)
			throws SQLException, DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		select.selectName.setValue(name);
		select.selectValue.setValue(id);
		return select.update(conn, " id = " + id);
	}
	/**
	 * 修改金额范围
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateMoney(Connection conn, Long id, String name)
			throws SQLException, DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		select.selectName.setValue(name);
		select.selectValue.setValue(name);
		return select.update(conn, " id = " + id);
	}
	/**
	 *修改借款期限
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateDeadline(Connection conn, Long id, String name)
			throws SQLException, DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		select.selectName.setValue(name+"个月");
		select.selectValue.setValue(name);
		return select.update(conn, " id = " + id);
	}
	/**
	 * 更改系统头像
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateSysImage(Connection conn, Long id, String name)
			throws SQLException, DataException {
		Dao.Tables.t_sysimages sysImages = new Dao().new Tables().new t_sysimages();
		sysImages.imagePath.setValue(name);
		return sysImages.update(conn, " id = " + id);
	}

	/**
	 * 修改投标金额
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateInvers(Connection conn, Long id, String name)
			throws SQLException, DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		select.selectName.setValue(name);
		select.selectValue.setValue(name);
		return select.update(conn, " id = " + id);
	}

	/**
	 * 删除借款目的
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteTage(Connection conn, Long id) throws SQLException,
			DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		select.deleted.setValue(2);
		return select.update(conn, " id = " + id);
	}

	/**
	 * 删除反担保方式
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteacc(Connection conn, Long id) throws SQLException,
			DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();

		return select.delete(conn, " id = " + id);
	}

	/**
	 * 删除系统头像
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deletSImage(Connection conn, Long id) throws SQLException,
			DataException {
		Dao.Tables.t_sysimages sysImages = new Dao().new Tables().new t_sysimages();
		sysImages.enable.setValue(2);
		return sysImages.update(conn, " id = " + id);
	}

	/**
	 * 添加系统图片
	 * 
	 * @param conn
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addSysImage(Connection conn, String name) throws SQLException,
			DataException {
		// =============
		Map<String, String> map = null;
		List<Map<String, Object>> namelist = null;
		List<String> lists = new ArrayList<String>();
		/*
		 * DataSet dataSet = MySQL .executeQuery(conn, "select
		 * MAX(ts.selectValue) as maxv from t_select ts where ts.typeId = 3");
		 * map = BeanMapUtils.dataSetToMap(dataSet);
		 */
		// ===========
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ts.imagePath as imagePath  from t_sysImages ts where ts.enable = 1 ");
		DataSet namedataset = MySQL.executeQuery(conn, sql.toString());
		namedataset.tables.get(0).rows.genRowsMap();
		namelist = namedataset.tables.get(0).rows.rowsMap;
		if (namelist != null && namelist.size() > 0) {
			for (Map<String, Object> m : namelist) {
				for (Map.Entry<String, Object> e : m.entrySet()) {
					lists.add((String) e.getValue());
				}
			}
		}
		Long tag = -1L;
		for (String str : lists) {
			if (name.equals(str)) {
				tag = 1L;
			}
		}
		if (tag != 1L) {
			Dao.Tables.t_sysimages Images = new Dao().new Tables().new t_sysimages();
			Images.imagePath.setValue(name);
			sql=null;
			lists=null;
			return Images.insert(conn);
		}
		sql=null;
		lists=null;
		return -1L;
	}
	
	/**
	 * 根据编号和状态修改
	 * @param conn
	 * @param type
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public long updateSelectType(Connection  conn,int type,long id) throws SQLException{
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		select.deleted.setValue(type);
		
		return select.update(conn," id =  "+id);
	}

}
