package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.sp2p.dao.ShowShipinDao;

public class ShowShipinService extends BaseService {
	public static Log log = LogFactory.getLog(ShowShipinService.class);
	private ShowShipinDao showShipinDao;
	public void setShowShipinDao(ShowShipinDao showShipinDao) {
		this.showShipinDao = showShipinDao;
	}
	
	/**
	 * 更新视频
	 * @param tmid
	 * @param tmtype
	 * @return
	 * @throws SQLException
	 */
	public Long updateShiping(Long tmid,Long tmtype) throws SQLException {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;
		Map<String, String>  map = null;
        boolean flag = false;
        Long tmdid = null;
		try {
			map = showShipinDao.queryMade(conn, tmid);
			if(map==null){
				flag = true;
			}
			resultId =	showShipinDao.updateMa(conn, tmid, 1);//1代表审核中
			if(resultId<=0){
				conn.rollback();
				return -1L;
			}
			if(map!=null&&map.size()>0){
			tmdid = Convert.strToLong(map.get("id"), -1); 
			resultId =	showShipinDao.updateMade(conn, tmid, tmtype, 1, flag, tmdid);//1代表审核中
			if(resultId<=0){
				conn.rollback();
				return -1L;
			}
			}else{
			resultId =	showShipinDao.updateMade(conn, tmid, tmtype, 1, flag, tmdid);	
			if(resultId<=0){
				conn.rollback();
				return -1L;
			}
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return resultId;

	}
	

}
