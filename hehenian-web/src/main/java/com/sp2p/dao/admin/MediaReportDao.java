package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
/**
 * 后台媒体报道管理
 * @author Administrator
 *
 */
public class MediaReportDao {
	
	/**
	 * 添加媒体报道信息
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
	public Long addReport(Connection conn,Integer sort,String title,String source,String url,String imgPath,String content,String publishTime,int state,int stick)throws SQLException,DataException{
		Dao.Tables.t_mediareport report=new Dao().new Tables().new t_mediareport();
		report.sort.setValue(sort);
		report.title.setValue(title);
		report.source.setValue(source);
		report.url.setValue(url);
		report.imgPath.setValue(imgPath);
		report.content.setValue(content);
		report.publishTime.setValue(publishTime);
		report.stick.setValue(stick);
		report.state.setValue(state);
		
		return report.insert(conn);
		
	}
	/**
	 * 删除媒体报道信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteReport(Connection conn,Long id)throws SQLException,DataException{
		Dao.Tables.t_mediareport report=new Dao().new Tables().new t_mediareport();
		
		 return report.delete(conn, "id="+id);	 
	}
	
	/**
	* 删除媒体报道信息（批量）
	* @param conn
	* @param ids  id字符串拼接
	* @param delimiter  拼接符号
	* @return long
	* @throws DataException 
	* @throws SQLException 
	*/
	public int deleteReport(Connection conn,String commonIds, String delimiter) throws SQLException, DataException{
		DataSet dataSet = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		return Dao.Procedures.p_deleteMediaReport(conn, dataSet, outParameterValues, commonIds, delimiter);
	}
	
	/**
	 * 更新媒体报道信息
	 * @param conn
	 * @param id
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateReport(Connection conn,Long id,Integer sort,String title,String source,String url,String imgPath,String content,String publishTime,int state,int stick)throws SQLException,DataException{
		Dao.Tables.t_mediareport report=new Dao().new Tables().new t_mediareport();
		report.sort.setValue(sort);
		report.title.setValue(title);
		report.source.setValue(source);
		report.url.setValue(url);
		report.imgPath.setValue(imgPath);
		report.content.setValue(content);
		report.publishTime.setValue(publishTime);
		report.stick.setValue(stick);
		report.state.setValue(state);
		
		return report.update(conn, "id="+id);
		
	}
	
	/**
	 * 根据Id获取媒体报道信息详情
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getReportById(Connection conn,Long id)throws SQLException,DataException{
		Dao.Tables.t_mediareport report=new Dao().new Tables().new t_mediareport();
		DataSet dataSet=report.open(conn, "*", " id="+id+" ", "", -1, -1);
	     return BeanMapUtils.dataSetToMap(dataSet);		
	}
	
	public List<Map<String,Object>> queryReportList(Connection conn)throws SQLException,DataException{
		Dao.Tables.t_mediareport report=new Dao().new Tables().new t_mediareport();
		DataSet dataSet=report.open(conn, "*", "state=1", "",-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询置顶数据
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryReportSticklList(Connection conn)throws SQLException,DataException{
		Dao.Tables.t_mediareport report=new Dao().new Tables().new t_mediareport();
		DataSet dataSet=report.open(conn, "*", " state=1 and stick =2", "",-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * 修改置顶状态
	 * @param conn
	 * @param id
	 * @param stick
	 * @return
	 * @throws SQLException
	 */
	public long  updateReportStick(Connection  conn,int id,int stick) throws SQLException{
		Dao.Tables.t_mediareport report=new Dao().new Tables().new t_mediareport();
		report.stick.setValue(stick);
		
		return report.update(conn, " id = "+id);
	}
	/**
	 * 查询首页滚动图片
	 * @return
	 * @author Administrator
	 */
	public List<Map<String,Object>> queryIndexRollImg(Connection conn)throws SQLException,DataException{
		Dao.Tables.t_links report=new Dao().new Tables().new t_links();
		DataSet dataSet=report.open(conn, "*", " type=3 ", " ordershort ", 0, 4);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
}
