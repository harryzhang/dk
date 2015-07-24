package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 网站公告
 * 
 * @author Administrator
 * 
 */
public class NewsDao {

	/**
	 * 添加网站公告信息
	 * 
	 * @param conn
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addNews(Connection conn, Integer sort, String title,
			String content, String publishTime, Long userId, String visits,
			int type) throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		news.sort.setValue(sort);
		news.title.setValue(title);
		news.content.setValue(content);
		news.publishTime.setValue(publishTime);
		news.userId.setValue(userId);
		news.visits.setValue(visits);
		news.type.setValue(type);
		return news.insert(conn);

	}

	/**
	 * 删除网站公告
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteNews(Connection conn, Long id) throws SQLException,
			DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		news.state.setValue(2);
		return news.update(conn, "id=" + id);
	}

	/**
	 * 删除网站公告
	 * 
	 * @param conn
	 * @param ids
	 *            id字符串拼接
	 * @param delimiter
	 *            拼接符号
	 * @return long
	 * @throws DataException
	 * @throws SQLException
	 */
	public int deleteNews(Connection conn, String commonIds, String delimiter)
			throws SQLException, DataException {
		DataSet dataSet = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		return Dao.Procedures.p_deleteNews(conn, dataSet, outParameterValues,
				commonIds, delimiter);
	}

	/**
	 * 更新网站公告
	 * 
	 * @param conn
	 * @param id
	 * @param sort
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param publisher
	 * @param visits
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateNews(Connection conn, Long id, Integer sort,
			String title, String content, String publishTime, Long userId,
			Integer visits) throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		if (sort != null) {
			news.sort.setValue(sort);
		}

		if (StringUtils.isNotBlank(title)) {
			news.title.setValue(title);
		}

		if (StringUtils.isNotBlank(content)) {
			news.content.setValue(content);
		}

		if (StringUtils.isNotBlank(publishTime)) {
			news.publishTime.setValue(publishTime);
		}

		if (userId != null) {
			news.userId.setValue(userId);
		}

		if (visits != null) {
			news.visits.setValue(visits);
		}

		return news.update(conn, "id=" + id);

	}

	/**
	 * 通过新闻Id获取新闻公告
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getNewsById(Connection conn, Long id)
			throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		DataSet dataSet = news.open(conn, "*", " id=" + id + " AND state=1",
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public List<Map<String, Object>> queryNewsList(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		DataSet dataSet = news.open(conn, "*", "state=1", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	public List<Map<String, Object>> queryNewsType(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_news_type t_news_type = new Dao().new Tables().new t_news_type();
		DataSet dataSet = t_news_type.open(conn, "*", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * add by houli 查找表里最大的排列序号
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getMaxSerial(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		DataSet dataSet = news
				.open(conn, "max(sort) as sortId", "", "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * add by houli 排序处理
	 * 
	 * @param conn
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public Long updateNewsIndex(Connection conn, String ids)
			throws SQLException {
		String[] transIds = ids.split(",");
		Long result = 0L;
		Long re = -1L;
		long tempId = 0;
		if (transIds.length > 0) {
			for (int i = 0; i < transIds.length; i++) {
				tempId = 0;
				re = -1L;// 重新赋值
				// 看是否是正规的int值
				tempId = Convert.strToLong(transIds[i], -1);
				if (tempId != -1) {
					re = MySQL.executeNonQuery(conn, "update t_news set sort="
							+ i + " where id=" + tempId);
					if (re > 0)
						result += 1;
				}
			}
		}
		return result == (transIds.length - 1) ? 1L : -1L;
	}

	/**
	 * add by houli 判断sort是否存在
	 * 
	 * @param conn
	 * @param sortId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> isExistSortId(Connection conn, int sortId)
			throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		DataSet dataSet = news.open(conn, " sort", " sort=" + sortId, "", -1,
				-1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * add by houli 判断修改后的sort是否存在
	 * 
	 * @param conn
	 * @param sortId
	 * @param originalSortId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> isExistToUpdate(Connection conn, int sortId,
			int originalSortId) throws SQLException, DataException {
		String command = "SELECT id,sort from (select id,sort from t_news "
				+ " where sort != " + originalSortId + " ) b where sort="
				+ sortId;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

}
