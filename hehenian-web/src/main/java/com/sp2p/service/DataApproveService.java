package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.DataApproveDao;

/**
 * houli 
 * @author Administrator
 *
 */
public class DataApproveService extends BaseService{

	private DataApproveDao dataApproveDao;

	/**
	 * 查询上传资料信息
	 * @param userId
	 * @param typeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> querySauthId(Long userId,Long typeId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		
		try {
			return dataApproveDao.querySauthId(conn,userId,typeId,-1,-1);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (DataException e) {
			e.printStackTrace();
		} finally {
		  conn.close();
		}
		return null;
	}
	/**
	 * 查询认证状态
	 * @param sauthId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryApproveStatus(Long sauthId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		int count = 0,status ;
		try {
			List<Map<String,Object>> lists = dataApproveDao.queryApproveStatus(conn,sauthId,-1,-1);
			if(lists == null || lists.size() <= 0){
				return -1L;
			}else{
				for(Map<String,Object> map : lists){
					if(map.get("auditStatus") != null){
						status = Convert.strToInt(map.get("auditStatus").toString(), -1);
						if(status == IConstants.APPROVE_PASS){
							count ++;
						}
					}
				}
				return count == lists.size()?1L:-1L;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (DataException e) {
			e.printStackTrace();
		} finally {
		   conn.close();
		}
		return result;
	}
	
	/**
	 * ----5项基本认证
	 * @return
	 * @throws SQLException 
	 */
	public Long queryBaseApproveStatus(Long userId,Long typeId) throws SQLException{
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		int count = 0,status ;
		long sauthId = -1;
		try {
			Map<String,String> mp = dataApproveDao.querySauthId(conn,userId,typeId,-1,-1);
			if(mp == null  || mp.get("id")==null){
				return -1L;
			}else{
				sauthId =  Convert.strToLong(mp.get("id"),-1L);
			}
			List<Map<String,Object>> lists = dataApproveDao.queryApproveStatus(conn,sauthId,-1,-1);
			if(lists == null || lists.size() <= 0){
				return -1L;
			}else{
				for(Map<String,Object> map : lists){
					if(map.get("auditStatus") != null){
						status = Convert.strToInt(map.get("auditStatus").toString(), -1);
						if(status == IConstants.APPROVE_PASS){
							count ++;
						}
					}
				}
				return count == lists.size()?1L:-1L;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (DataException e) {
			e.printStackTrace();
		} finally {
		   conn.close();
		}
		return result;
	}
	
	/**
	 * ------个人信息认证
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryPersonalStatus(Long userId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		
		try {
			Map<String,String> map = dataApproveDao.queryPersonInfo(conn,userId,-1,-1);
			if(map == null || map.get("auditStatus")==null){
				return -1L;
			}else{
				int auditStatus = Convert.strToInt(map.get("auditStatus"), -1);
				if(auditStatus < 3){
					return -1L;
				}else{//auditStatus=3 代表审核通过
					return 1L;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		  conn.close();
		}
		return -1L;
	}
	
	/**
	 * 工作认证
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryWorkStatus(Long userId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		
		try {
			Map<String,String> map = dataApproveDao.queryWorkInfo(conn,userId,-1,-1);
			if(map == null ){
				return -1L;
			}else{
				int auditStatus = Convert.strToInt(map.get("auditStatus"), -1);
				int directedStatus = Convert.strToInt(map.get("directedStatus"), -1);
				int otherStatus = Convert.strToInt(map.get("otherStatus"), -1);
				int moredStatus = Convert.strToInt(map.get("moredStatus"), -1);
				
				/**
				 * 工作认证有四个认证模块，只要有一个认证模块不通过，那么该认证就不通过
				 */
				if(auditStatus < 3 || directedStatus<3 ||otherStatus<3 || moredStatus<3 ){
					return -1L;
				}else{//auditStatus=3 代表审核通过
					return 1L;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		  conn.close();
		}
		return null;
	}
	
	public DataApproveDao getDataApproveDao() {
		return dataApproveDao;
	}

	public void setDataApproveDao(DataApproveDao dataApproveDao) {
		this.dataApproveDao = dataApproveDao;
	}
}
