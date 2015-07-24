package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.UserIntegralDao;
import com.sp2p.dao.admin.UserManageDao;

public class UserIntegralService extends BaseService {
	public static Log log = LogFactory.getLog(UserIntegralService.class);
	private UserIntegralDao userIntegralDao;
	private  UserDao  userDao ;
	private OperationLogDao  operationLogDao;
	private UserManageDao userManageDao;

	public UserManageDao getUserManageDao() {
		return userManageDao;
	}

	public void setUserManageDao(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
	}

	public void setUserIntegralDao(UserIntegralDao userIntegralDao) {
		this.userIntegralDao = userIntegralDao;
	}
	
	public void queryUserIntegral(PageBean<Map<String, Object>> pageBean,Long userid, int type)
	throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			//condition.append(" 1 = 1");
			condition.append(" AND userid = "+userid+" AND type = "+ type);
			dataPage(conn, pageBean, "t_userintegraldetail", "*", " ORDER BY id", condition.toString());
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 添加用户积分明细记录
	 * 
	 * @param userid
	 * @param intergraltype
	 * @param remark
	 * @param changetype
	 * @param changerecore
	 * @param time
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	/**
	 * 登录添加积分处理
	 * @return
	 * @throws SQLException 
	 */
	
	
	
	/**
	 * 用户登录日志表
	 */
	public Long addUserLoginLog(Long id) throws SQLException {
		Long resultId = -1L;
		Connection conn = MySQL.getConnection();
		try {
			resultId = userIntegralDao.addUserLoginLog(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return resultId;
	}
	//用户投标积分
	public Long UpdateFinnceRating(Long id,int score,int prescore) throws SQLException, DataException {
		Long resultId = -1L;
		Connection conn = MySQL.getConnection();
		String intergraltype = "投标";
		String remark = "投标成功";
		String changetype = "增加";
		Integer type =  IConstants.USER_INTERGRALTYPEVIP;
		try {
			
			resultId = userIntegralDao.UpdateUserRating(conn, id, score, prescore);
			if(resultId<=0){
				conn.rollback();
				return -1L;
			}
			Map<String,String> map = userIntegralDao.queryUserIntegral2(conn, id, 2, intergraltype);
			if(map==null){
				resultId = userManageDao.addserintegraldetail(conn, id, score,intergraltype, type,remark, changetype);
			}else{
				
				long changerecore = Convert.strToInt((String) map.get("changerecore"),1);
				long minId = Convert.strToInt(map.get("minId"), 1);
				resultId=userIntegralDao.updateUserIntegral(conn,changerecore,score,minId);
				}
			if(resultId<=0){
				conn.rollback();
				return -1L;
			}
			conn.commit();
			
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return resultId;
	}
	/**
	 * 举报审核通过后积分添加
	 * @param id
	 * @param score
	 * @param prescore
	 * @return
	 * @throws SQLException
	 * @throws DataException 
	 */
	public Long UpdateJubaoRating(Long id,int score,int prescore) throws SQLException, DataException {
		Long resultId = -1L;
		Connection conn = MySQL.getConnection();
		String intergraltype = "举报";
		String remark = "举报属实";
		String changetype = "增加";
		Integer type =  IConstants.USER_INTERGRALTYPEVIP;
		try {
			
			resultId = userIntegralDao.UpdateUserRating(conn, id, score, prescore);
			if(resultId<=0){
				conn.rollback();
				return -1L;
			}
			Map<String,String> map = userIntegralDao.queryUserIntegral2(conn, id, 2, intergraltype);
			if(map==null){
				resultId = userManageDao.addserintegraldetail(conn, id, score,intergraltype, type,remark, changetype);
			}else{
				
				long changerecore = Convert.strToInt((String) map.get("changerecore"),1);
				long minId = Convert.strToInt(map.get("minId"), 1);
				resultId=userIntegralDao.updateUserIntegral(conn,changerecore,score,minId);
				}
			
			if(resultId<=0){
				conn.rollback();
				return -1L;
			}
			conn.commit();
			
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return resultId;
	}
	public Long UpdateLoginRating(Long id,int score,int prescore,Integer loignCount) throws SQLException, DataException {
		Long resultId = -1L;
		Connection conn = MySQL.getConnection();
		String intergraltype = "登录";
		String remark = "登录积分";
		String changetype = "增加";
		Integer type =  IConstants.USER_INTERGRALTYPEVIP;
		try {
			
			if(loignCount<=2){
			resultId = userIntegralDao.UpdateUserRating(conn, id, score, prescore);
			if(resultId<=0){
				conn.rollback();
				return -1L;
			}
			Map<String,String> map = userIntegralDao.queryUserIntegral2(conn, id, 2, intergraltype);
			if(map==null){
				resultId = userManageDao.addserintegraldetail(conn, id, score,intergraltype, type,remark, changetype);
			}else{
				
				long changerecore = Convert.strToInt((String) map.get("changerecore"),1);
				long minId = Convert.strToInt(map.get("minId"), 1);
				resultId=userIntegralDao.updateUserIntegral(conn,changerecore,score,minId);
				}
			if(resultId<=0){
				conn.rollback();
				return -1L;
			}
			conn.commit();
			}
			
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally{
			conn.close();
		}
		return resultId;
	}
	
	public Map<String, String> queryUserLoginLong(Long id)
	throws SQLException {
		Map<String, String> map = null;
		Map<String, String> userMap = new HashMap<String, String>();
		Connection conn = connectionManager.getConnection();
		try {
			map = userIntegralDao.queryUserLoginLong(conn, id);
			userMap = userDao.queryUserById(conn, id);
			operationLogDao.addOperationLog(conn, "t_user",Convert.strToStr(userMap.get("username"),"") , IConstants.UPDATE, Convert.strToStr(userMap.get("lastIP"), ""), 0, "用户登陆", 1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserIntegralDao getUserIntegralDao() {
		return userIntegralDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}
}
