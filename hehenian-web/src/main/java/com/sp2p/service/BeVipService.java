package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataRow;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.BeVipDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_person;
import com.sp2p.database.Dao.Tables.t_user;
import com.sp2p.service.admin.SendmsgService;

public class BeVipService extends BaseService{
	public static Log log = LogFactory.getLog(BeVipService.class);
	private BeVipDao beVipDao;
	private SendmsgService sendmsgService;
	private OperationLogDao   operationLogDao;
	private SelectedService	selectedService;
	
	public void setSendmsgService(SendmsgService sendmsgService) {
		this.sendmsgService = sendmsgService;
	}
	public void setBeVipDao(BeVipDao beVipDao) {
		this.beVipDao = beVipDao;
	}
	/**
	 * 用于手机注册    查询t_user表中手机号码
	 * @param phone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryIsPhoneonUser(String phone) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = beVipDao.queryIsPhoneonUser(conn, phone);
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
		return map;
	}
	/**
	 * 验证手机的唯一性
	 * @param phone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
  public Map<String, String> queryIsPhone(String phone) throws SQLException, DataException {
	Connection conn = connectionManager.getConnection();

	Map<String, String> map = new HashMap<String, String>();
	try {
		map = beVipDao.queryIsPhone(conn, phone);
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
	return map;
}
  /**
   * 查询用户基本信息
   * @param id
   * @return
   * @throws SQLException
   * @throws DataException
   */
  public Map<String, String> queryPUser(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = beVipDao.queryPUser(conn, id);
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
		return map;
	}
  
	/**
	 * 查询vip页面状态参数
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryVipParamList(long id) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = beVipDao.queryVipParamList(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param id
	 * @throws DataException
	 * @throws SQLException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserById(long id) throws DataException,
			SQLException {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = beVipDao.queryUserById(conn, id);
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
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	/**
	 * 修改用户的VIP状态
	 * @param userId
	 * @param vipStatus
	 * @param servicePersonId
	 * @param content
	 * @param vipFee
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public Long updataUserVipStatus(Long userId, int vipStatus,
			int servicePersonId, String content, String vipFee,String username)
			throws Exception {
		StringBuffer msg = new StringBuffer();
		Long resultId = -1L;
		int authStep = 1 ;
		Connection conn = MySQL.getConnection();
		try {
			DataSet dataSet = Database.executeQuery(conn,"select authStep,username,lastIP from t_user where id = "+userId );
			Map<String,String> UserMap = BeanMapUtils.dataSetToMap(dataSet);
			
			if(UserMap!=null&&UserMap.size()>0){
				authStep  = Convert.strToInt(UserMap.get("authStep"), 1);
			}else{
				conn.rollback();
				return -1L;
			}

			resultId = beVipDao.updateUser(conn, userId, vipStatus,
					servicePersonId, content, vipFee,authStep);
			//添加系统操作日志
			resultId = operationLogDao.addOperationLog(conn, "t_user", Convert.strToStr(UserMap.get("username"), ""), IConstants.UPDATE, Convert.strToStr(UserMap.get("lastIP"), ""), 0, "申请会员", 1);
			
			if (resultId <= 0) {
				conn.rollback();
				return -1L;
			}else{
				//发送站内信
				msg.append("尊敬的"+username+",你申请vip成功");
				//发站内信
				resultId = sendmsgService.sendCheckMail(userId, " 申请vip审核通知", msg.toString(), 2, -1);//2管理员信件  -1 后台管理员
				if(resultId<=0){
					conn.rollback();
					return -1L;
				}
			}
			
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return resultId;
	}
	
	
	
	/**
	 * 更新用户的认证状态 
	 * @param id 
	 * @param austept
	 * @return
	 * @throws Exception
	 */
	public Long updateUserAustep(Long id,Integer austept)
			throws Exception {
		Long resultId = -1L;
		Connection conn = MySQL.getConnection();
		try {
			resultId = beVipDao.updateUserAustep(conn, id, austept);
			if (resultId <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return resultId;
	}
	
	
	/**
	 * 查询身份证号码是否已经被注册
	 * @param idCard
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryIDCard(String idCard) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = beVipDao.queryIDCard(conn, StringEscapeUtils.escapeSql(idCard));
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
		return map;
	}
	
	public Map<String, String> beVip(Long userId,Long server, double vipFee, double money) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_bevip(conn, ds, outParameterValues, userId,server, new BigDecimal(vipFee), new BigDecimal(money), -1, "", "", "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret < 0) {
				conn.rollback();
			}else{
				String out_vip_desc = outParameterValues.get(2)+"";
				if(!"-1".equals(out_vip_desc)){
					Map<String,String> noticeMap = new HashMap<String, String>();
					noticeMap.put("mail", out_vip_desc);
					noticeMap.put("email", out_vip_desc);
					noticeMap.put("note", out_vip_desc);
					//发送通知
	            	selectedService.sendNoticeMSG(conn, userId, "VIP会员成功续费", noticeMap, IConstants.NOTICE_MODE_5);
				}
				String out_friend_desc = outParameterValues.get(3)+"";
				if(!"-1".equals(out_friend_desc)){
					String[] msg = out_friend_desc.split("#");
					long uId = Convert.strToLong(msg[0], -1);
					Map<String,String> noticeMap = new HashMap<String, String>();
					noticeMap.put("mail", msg[1]);
					noticeMap.put("email", msg[1]);
					noticeMap.put("note", msg[1]);
					//发送通知
	            	selectedService.sendNoticeMSG(conn, uId, "好友邀请奖励", noticeMap, IConstants.NOTICE_MODE_5);
				}
				
				conn.commit();
			}
		} catch (SQLException e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
			conn = null;
			ds = null;
			outParameterValues = null;
		}
		return map;
	}



	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}
	public SelectedService getSelectedService() {
		return selectedService;
	}
	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}
	public BeVipDao getBeVipDao() {
		return beVipDao;
	}
	public SendmsgService getSendmsgService() {
		return sendmsgService;
	}
	
	
}
