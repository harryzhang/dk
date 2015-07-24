package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.ShowShipinDao;
import com.sp2p.dao.admin.ShowShipinAdminDao;
import com.sp2p.service.ValidateService;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;

public class ShowShipinAdminService extends BaseService {
	public static Log log = LogFactory.getLog(ShowShipinAdminService.class);
	private ShowShipinAdminDao showShipinAdminDao;
	private ValidateService validateService;

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setShowShipinAdminDao(ShowShipinAdminDao showShipinAdminDao) {
		this.showShipinAdminDao = showShipinAdminDao;
	}
	/**
	 * 查询视频状态值
	 * @param tmid
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> querydateMa1(Long tmid) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = showShipinAdminDao.querydateMa1(conn, tmid);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	/**
	 * 资料认证统计查询 
	 * @param typeid
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryCountMsg( Long typeid,Long userId ) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = showShipinAdminDao.queryCountMsg(conn, typeid, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}
	
	/**
	 * 查询图片信息和审核情况 
	 * @param tmdid  证件类型下明细表的唯一id
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryonemsg( Long tmdid ) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = showShipinAdminDao.queryonemsg(conn, tmdid);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}
	/**
	 * 查询用户名称 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryuser( Long id ) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = showShipinAdminDao.queryuser(conn,id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	/**
	 * 查询materaldetal的id 
	 * @param typeid 证件类型
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> querytmid( Long typeid,Long userId ) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = showShipinAdminDao.querytmid(conn, typeid, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}
	/**
	 * 资料认证统计图片类表  t_materialsauth 的id
	 * @param tmid 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryCountPictureList(Long tmid) throws SQLException {
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		Connection conn = connectionManager.getConnection();
		try {
			try {
				map = showShipinAdminDao.queryCountPictureList(conn, tmid);
			} catch (DataException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	
	
	/**
	 * 更新视频类型的状态
	 * @param tmid
	 * @param status
	 * @param content
	 * @param sro
	 * @return
	 * @throws SQLException
	 */
	public Long updateMa1(Long tmid, int status,String content,Integer sro,Long userId,Long adminId) throws SQLException {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			//向user表增加信用积分
			Integer type = 13;//13视频认证
			result =	showShipinAdminDao.Updatecreditrating(conn, userId, content, sro, type,status);
			if(result<=0){
				conn.rollback();
				return -1L;
			}
			//添加审核记录
			result = showShipinAdminDao.addCheckRecord(conn, sro, adminId, userId, type);
			if(result<=0){
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

}
