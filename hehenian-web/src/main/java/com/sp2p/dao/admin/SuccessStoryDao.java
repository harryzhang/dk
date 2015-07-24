package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 成功故事
 * 
 * @author Administrator
 * 
 */
public class SuccessStoryDao {

	/**
	 * 添加成功故事信息
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
	public Long addSuccessStory(Connection conn, String sort, String title,
			String content, String publishTime, String publisher, String browseNum,String imgPath)
			throws SQLException, DataException {
		Dao.Tables.t_successstory story = new Dao().new Tables().new t_successstory();
		story.sort.setValue(sort);
		story.title.setValue(title);
		story.content.setValue(content);
		story.publishTime.setValue(publishTime);
		story.browseNum.setValue(browseNum);
		story.publisher.setValue(publisher);
		story.imgPath.setValue(imgPath);

		return story.insert(conn);

	}


	/**
	 * 删除成功故事
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
	public int deleteSuccessStory(Connection conn, String commonIds, String delimiter)
			throws SQLException, DataException {
		DataSet dataSet = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		return Dao.Procedures.p_deleteStory(conn, dataSet, outParameterValues,
				commonIds, delimiter);
	}

	/**
	 * 更新成功故事
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
	public Long updateSuccessStory(Connection conn, Long id, String sort, String title,
			String content, String publishTime, String publisher, Long browseNum,String imgPath) throws SQLException, DataException {
		Dao.Tables.t_successstory story = new Dao().new Tables().new t_successstory();
		if (sort!=null) {
			story.sort.setValue(sort);
		}
		
		if (StringUtils.isNotBlank(title)) {
			story.title.setValue(title);
		}
		
		if (StringUtils.isNotBlank(content)) {
			story.content.setValue(content);
		}
		
		if (StringUtils.isNotBlank(publishTime)) {
			story.publishTime.setValue(publishTime);
		}
		
		if (StringUtils.isNotBlank(publisher)) {
			story.publisher.setValue(publisher);
		}
		
		if (StringUtils.isNotBlank(imgPath)) {
			story.imgPath.setValue(imgPath);
		}
		
		if (browseNum!=null) {
			story.browseNum.setValue(browseNum);
		}
		

		return story.update(conn, "id=" + id);

	}

	public Map<String, String> getSuccessStoryById(Connection conn, Long id)
			throws SQLException, DataException {
		Dao.Tables.t_successstory story = new Dao().new Tables().new t_successstory();
		DataSet dataSet = story.open(conn, "*", " id=" + id ,
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public List<Map<String, Object>> querySuccessStoryList(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_successstory story = new Dao().new Tables().new t_successstory();
		DataSet dataSet = story.open(conn, "*", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}


	public List<Map<String, Object>> queryBBSUser(Connection conn)
			throws SQLException, DataException {
		String sqlStr = "select * from jrun_members";
		DataSet dataSet = MySQL.executeQuery(conn, sqlStr);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
}
