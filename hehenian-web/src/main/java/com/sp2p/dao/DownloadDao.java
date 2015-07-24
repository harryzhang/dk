package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class DownloadDao {

	/**
	 * 添加下载资料
	 * 
	 * @param conn
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param publisher
	 * @param visits
	 * @param state
	 * @param seqNum
	 * @param attachment
	 * @return
	 * @throws SQLException
	 */
	public Long addDownload(Connection conn, String title, String content,
			String publishTime, Long userId, Integer visits, Integer state,
			Integer seqNum, String attachment) throws SQLException,
			DataException {

		Dao.Tables.t_download download = new Dao().new Tables().new t_download();
		download.title.setValue(title);
		download.content.setValue(content);
		download.publishTime.setValue(publishTime);
		download.userId.setValue(userId);
		download.visits.setValue(visits);
		download.state.setValue(state);
		download.seqNum.setValue(seqNum);
		download.attachment.setValue(attachment);

		return download.insert(conn);
	}

	/**
	 * 更新下载资料
	 * 
	 * @param conn
	 * @param id
	 * @param title
	 * @param publishTime
	 * @param state
	 * @param seqNum
	 * @param attachment
	 * @return
	 * @throws SQLException
	 */
	public Long UpdateDownload(Connection conn, Long id, String title,
			String content, Long userId, Integer visits, Integer state,
			Integer seqNum, String publishTime, String attachment)
			throws SQLException, DataException {
		Dao.Tables.t_download download = new Dao().new Tables().new t_download();
		if (StringUtils.isNotBlank(title)) {
			download.title.setValue(title);
		}
		
		if (StringUtils.isNotBlank(content)) {
			download.content.setValue(content);
		}
		
		if (userId!=null) {
			download.userId.setValue(userId);
		}
		
		if (visits!=null) {
			download.visits.setValue(visits);
		}
		
		if (state!=null) {
			download.state.setValue(state);
		}
		
		if (seqNum!=null) {
			download.seqNum.setValue(seqNum);
		} 
		
		if (StringUtils.isNotBlank(publishTime)) {
			download.publishTime.setValue(publishTime);
		}
		
		if (StringUtils.isNotBlank(attachment)) {
			download.attachment.setValue(attachment);
		}
		
		

		return download.update(conn, " id=" + id);
	}

	/**
	 * 删除指定ID下载资料
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteDownload(Connection conn, Long id) throws SQLException,
			DataException {
		String idStr = StringEscapeUtils.escapeSql("'"+id+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_download download = new Dao().new Tables().new t_download();

		return download.delete(conn, "id in(" + idSQL + ")");
	}

	/**
	 * 根据Id获取下载资料详情
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getDownloadById(Connection conn, Long id)
			throws SQLException, DataException {
		Dao.Tables.t_download download = new Dao().new Tables().new t_download();
		DataSet dataSet = download.open(conn, "", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);

	}

	/**
	 * 通过实体查询下载资料详细
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> frontGetDownloadById(Connection conn, Long id)
			throws SQLException, DataException {
		Dao.Views.v_t_download_detail download = new Dao().new Views().new v_t_download_detail();
		DataSet dataSet = download.open(conn, "", "id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);

	}

	/**
	 * 分页查询下载资料
	 * 
	 * @param conn
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryDownloadList(Connection conn,
			String fiedList, String orderStr, String sortType, String title)
			throws SQLException, DataException {
		Dao.Tables.t_download download = new Dao().new Tables().new t_download();
		StringBuffer querydownload = new StringBuffer(" 1=1 ");

		if (StringUtils.isNotBlank(title)) {
			querydownload.append(" AND title = " + StringEscapeUtils.escapeSql(title));
		}
		

		DataSet dataSet = download.open(conn, fiedList, querydownload
				.toString(), orderStr + sortType, -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		querydownload=null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

}
