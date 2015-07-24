package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Tables.t_help_question;
import com.sp2p.database.Dao.Views.v_t_borrow_list;

/**
 * 友情链接列表处理
 * @author li.hou
 *
 */
public class LinksDao {

	/**
	 * 添加友情链接信息
	 * @param conn 数据库连接
	 * @param companyName 公司名称
	 * @param companyImg 公司图片
	 * @param companyURL 公司网址
	 * @param serialCount 序列号
	 * @param dateTime 时间
	 * @return
	 * @throws SQLException
	 */
	public Long addLinks(Connection conn,String companyName,String companyImg,String companyURL,
			Long serialCount,String dateTime) throws SQLException{
		Dao.Tables.t_links tlinks = new Dao().new Tables().new t_links();
		if(companyName != null)
			tlinks.companyName.setValue(companyName);
		if(companyImg != null)
			tlinks.companyImg.setValue(companyImg);
		if(companyURL != null)
			tlinks.companyURL.setValue(companyURL);
		if(serialCount != null && serialCount > 0)
			tlinks.serialCount.setValue(serialCount);
		if(dateTime != null)
			tlinks.publishTime.setValue(dateTime);
		return tlinks.insert(conn);
	}
	
	/**
	 * 添加首页滚动图片
	 * @return
	 * @author Administrator
	 */
	public Long addIndexRollImg(Connection conn,String companyImg,
			Long serialCount,String dateTime,Long ordershort,int cardStatus) throws SQLException{
		Dao.Tables.t_links tlinks = new Dao().new Tables().new t_links();
		if(companyImg != null)
			tlinks.companyImg.setValue(companyImg);
		if(serialCount != null && serialCount > 0)
			tlinks.serialCount.setValue(serialCount);
		if(dateTime != null)
			tlinks.publishTime.setValue(dateTime);
		if(ordershort != null)
			tlinks.ordershort.setValue(ordershort);
		tlinks.type.setValue(cardStatus);//3表示是首页滚动图片
		return tlinks.insert(conn);
	}
	
	/**
	 * 删除友情链接信息
	 * @param conn
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public Long deleteLinks(Connection conn,String ids) throws SQLException{
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_links t_links = new Dao().new Tables().new t_links();
		return t_links.delete(conn, " serialCount in("+idSQL+")");
	}
	
	/**
	 * 根据友情链接id找友情链接信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queyLinksInfoById(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_links t_links = new Dao().new Tables().new t_links();
		DataSet dataSet = t_links.open(conn, "", " serialCount=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 友情链接信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queyLinksInfoList(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_links t_links = new Dao().new Tables().new t_links();
		DataSet dataSet = t_links.open(conn, "", "LIMIT 0,7 ", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * 根据友情链接序列号更新对应序列号的友情链接信息
	 * @param conn
	 * @param companyName
	 * @param companyImg
	 * @param companyURL
	 * @param serialCount
	 * @param dateTime
	 * @return
	 * @throws SQLException
	 */
	public Long updateLinks(Connection conn,String companyName,String companyImg,String companyURL,
			Long serialCount,String dateTime) throws SQLException{
		Dao.Tables.t_links t_links = new Dao().new Tables().new t_links();
		if(companyName!=null){
			t_links.companyName.setValue(companyName);
		}
		if(companyImg!=null){
			t_links.companyImg.setValue(companyImg);
		}
		if(companyURL!=null){
			t_links.companyURL.setValue(companyURL);
		}
		if(serialCount!=null && serialCount > 0){
			t_links.serialCount.setValue(serialCount);
		}
		if(dateTime!=null){
			t_links.publishTime.setValue(dateTime);
		}
		/*if(publisher!=null){
			questions.publisher.setValue(publisher);
		}
		if(publishTime!=null){
			questions.publishTime.setValue(publishTime);
		}*/
		return t_links.update(conn, " serialCount="+serialCount);
	}
	
	/**
	 * 修改首页滚动图片
	 * @return
	 * @author Administrator
	 */
	public Long updateIndexRollImg(Connection conn,String companyImg,
			Long serialCount,Long ordershort,String dateTime) throws SQLException{
		Dao.Tables.t_links t_links = new Dao().new Tables().new t_links();
		if(companyImg!=null){
			t_links.companyImg.setValue(companyImg);
		}
		if(ordershort!=null){
			t_links.ordershort.setValue(ordershort);
		}
		if(serialCount!=null && serialCount > 0){
			t_links.serialCount.setValue(serialCount);
		}
		if(dateTime!=null){
			t_links.publishTime.setValue(dateTime);
		}
		return t_links.update(conn, " serialCount="+serialCount+" AND type = 3 ");
	}
	
	public Map<String,String> getMaxSerial(Connection conn)throws SQLException, DataException{
		Dao.Tables.t_links tLinks = new Dao().new Tables().new t_links();
		DataSet dataSet = tLinks.open(conn, "max(serialCount)", " type = 1 ", "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> getMaxIndexRollImgSerial(Connection conn)throws SQLException, DataException{
		Dao.Tables.t_links tLinks = new Dao().new Tables().new t_links();
		DataSet dataSet = tLinks.open(conn, "max(serialCount)", "", "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
}
