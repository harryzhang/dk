package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sp2p.constants.IAmountConstants;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.MyHomeInfoSettingDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao.Procedures;

/**
 * @ClassName: CallCenterService.java
 * @Author: li.hou
 * @Descrb: 我的帐户 个人设置
 */
public class HomeInfoSettingService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);
	private MyHomeInfoSettingDao myHomeInfoSettingDao;
	private FundRecordDao fundRecordDao;
	private OperationLogDao operationLogDao;
	private UserDao userDao;

	private SelectedService selectedService;

	public Long updateUserPassword(long userId, String password, String type) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		Map<String, String> userMap = new HashMap<String, String>();
		try {
			userMap = userDao.queryUserById(conn, userId);
			result = myHomeInfoSettingDao.updateUserPassword(conn, userId, password, type);
			if (type.endsWith("login")) {
				// 添加系统操作日志
				result = operationLogDao.addOperationLog(conn, "t_user", Convert.strToStr(userMap.get("username"), ""), IConstants.UPDATE,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "修改会员登陆密码", 1);
			} else {
				// 添加系统操作日志
				result = operationLogDao.addOperationLog(conn, "t_user", Convert.strToStr(userMap.get("username"), ""), IConstants.UPDATE,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "修改会员交易密码", 1);
			}

			if (result <= 0) {
				conn.rollback();
				return -1L;
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
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 根据用户名修改密保问题密码
	 * 
	 * @param userId
	 * @param password
	 * @param type
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return Long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Long updatePhonePwd(String userName, String password, String type, String email) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.updatePhonePwd(conn, userName, password, type, email);
			if (type.endsWith("login")) {
				// 添加系统操作日志
				result = operationLogDao.addOperationLog(conn, "t_user", Convert.strToStr(userName, ""), IConstants.UPDATE, "", 0, "修改会员登陆密码", 1);
			} else {
				// 添加系统操作日志
				result = operationLogDao.addOperationLog(conn, "t_user", Convert.strToStr(userName, ""), IConstants.UPDATE, "", 0, "修改会员交易密码", 1);
			}

			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 根据id获得交易密码
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> getDealPwd(long userId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.getDealPwd(conn, userId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryCardStatus(long userId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryBankInfoCardStauts(conn, userId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/** 
	 * 查询银行卡是否存在
	 * @param userId
	 * @param openAcctId
	 * @return
	 * @throws DataException
	 * @throws SQLException [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long queryBankCard(long userId,String openAcctId) throws DataException, SQLException {
        Connection conn = connectionManager.getConnection();
        try {
            Map<String,String> map = myHomeInfoSettingDao.queryBankCard(conn, userId,openAcctId);
            if(null != map){
                return 1;
            }else
            {
                return -1L;
            }
        } catch (DataException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } finally {
            conn.close();
        }
    }
	
	public Long addBankCardInfo(long userId, String cardUserName, String bankName, String subBankName, String bankCard, int cardStatus,
			String province, String city, String openBankId, String provinceId, String cityId) throws SQLException {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addBankCardInfo(conn, userId, cardUserName, bankName, subBankName, bankCard, cardStatus, province, city,
					openBankId, provinceId, cityId);

			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public List<Map<String, Object>> queryBankInfoList(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryBankInfoList(conn, userId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> queryBankInfoList2(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryBankInfoList2(conn, userId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> querySuccessBankInfoList(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.querySuccessBankInfoList(conn, userId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public long deleteBankInfo(long bankId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.deleteBankInfo(conn, bankId);
		} finally {
			conn.close();
		}
	}

	public Map<String, String> getRealNameByUserId(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.getUserRealName(conn, userId, -1, -1);
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

	public Long addBindingMobile(String mobile, long userId, int status, String content, String oldPhone) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.addBindingMobile(conn, mobile, userId, status, content, IConstants.INSERT_CHANGE_TYPE, oldPhone);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询绑定的手机号码的状态（手机号码是唯一的）
	 * 
	 * @param mobile
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBindingMobile(String mobile) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryBindingMoble(conn, mobile, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查看手机绑定表里面是否有该用户的手机绑定信息
	 * 
	 * @param mobile
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBindingMobileUserInfo(long userId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryBindingInfoByUserId(conn, userId, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询某用户的手机绑定信息
	 * 
	 * @param userId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBindingInfoByUserId(long userId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryBindingInfoByUserId(conn, userId, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// public Map<String,String> querySucessBindingInfoByUserId(long userId,int
	// status) throws DataException, SQLException{
	// Connection conn = connectionManager.getConnection();
	// try {
	// return myHomeInfoSettingDao.querySucessBindingInfoByUserId(conn,
	// userId,status, -1, -1);
	// } catch (SQLException e) {
	// log.error(e);
	// e.printStackTrace();
	// throw e;
	// } finally{
	// conn.close();
	// }
	// }

	public Map<String, String> querySucessBindingInfoByUserId(long userId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.querySucessBindingInfoByUserId(conn, userId, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> queryBindingsByUserId(long userId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryBindingsByUserId(conn, userId, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public long addNotes(long userId, boolean message, boolean mail, boolean notes) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addNotes(conn, userId, message, mail, notes);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 添加通知设置
	 * 
	 * @param userId
	 * @param messageReceive
	 * @param messageDeposit
	 * @param messageBorrow
	 * @param messageRecharge
	 * @param messageChange
	 * @param mailReceive
	 * @param mailDeposit
	 * @param mailBorrow
	 * @param mailRecharge
	 * @param mailChange
	 * @param noteReceive
	 * @param noteDeposit
	 * @param noteBorrow
	 * @param noteRecharge
	 * @param noteChange
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addNotesSetting(long userId, boolean messageReceive, boolean messageDeposit, boolean messageBorrow, boolean messageRecharge,
			boolean messageChange, boolean mailReceive, boolean mailDeposit, boolean mailBorrow, boolean mailRecharge, boolean mailChange,
			boolean noteReceive, boolean noteDeposit, boolean noteBorrow, boolean noteRecharge, boolean noteChange) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addNotesSetting(conn, userId, messageReceive, messageDeposit, messageBorrow, messageRecharge,
					messageChange, mailReceive, mailDeposit, mailBorrow, mailRecharge, mailChange, noteReceive, noteDeposit, noteBorrow,
					noteRecharge, noteChange);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 获取通知设置总类型
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryNotesList(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryNotes(conn, userId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 获得某种通知设置下面的详细通知设置
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryNotesSettingList(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryNotesSetting(conn, userId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 加载站内信信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryMailList(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryMailList(conn, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 添加邮件信息
	 * 
	 * @param sendUserId
	 * @param receiverUserId
	 * @param title
	 * @param content
	 * @param mailStatus
	 * @param mailType
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addMail(long sendUserId, long receiverUserId, String title, String content, int mailStatus, int mailType) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addMail(conn, sendUserId, receiverUserId, title, content, mailStatus, mailType);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, sendUserId);
				// 添加操作日志
				operationLogDao.addOperationLog(conn, "t_mail", Convert.strToStr(userMap.get("username"), ""), IConstants.DELETE,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "发送站内信", 1);
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 添加邮件信息
	 * 
	 * @param sendUserId
	 * @param receiverUserId
	 * @param title
	 * @param content
	 * @param mailStatus
	 * @param mailType
	 * @return
	 * @throws SQLException
	 */
	public long addMail(long sendUserId, long receiverUserId, String title, String content, int mailStatus, Integer mailMode, int mailType)
			throws SQLException {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addMail(conn, sendUserId, receiverUserId, title, content, mailStatus, mailMode, mailType);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 获得收件箱信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryReceiveMails(PageBean pageBean, long userId, int mailType, String type, int mailStatus) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String condition = "";
		if (type.equalsIgnoreCase("sys")) {
			condition = " and reciver='" + userId + "' and mailType=" + mailType + "  and mailMode=" + IConstants.MAIL_SYS_;
		} else {
			condition = " and reciver='" + userId + "' and mailType=" + mailType + "  and mailMode=" + IConstants.MAIL_MODE;
		}
		if (mailStatus > 0) {
			condition += " and mailStatus=" + mailStatus;
		}
		try {
			dataPage(conn, pageBean, "t_mail", "*", " order by sendTime desc ", condition);// return
																							// myHomeInfoSettingDao.queryReceiveMails(conn,
																							// userId,mailType,
																							// -1,
																							// -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 获得发件箱消息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void querySendMails(PageBean pageBean, long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String condition = " and sender=" + userId;
		try {
			dataPage(conn, pageBean, "t_mail", "*", " order by sendTime desc ", condition);// return
																							// myHomeInfoSettingDao.querySendMails(conn,
																							// userId,
																							// -1,
																							// -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public long deleteMails(String ids, long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long result = -1L;
		try {

			result = myHomeInfoSettingDao.deleteMail(conn, ids);
			if (result > 0) {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_mail", Convert.strToStr(userMap.get("username"), ""), IConstants.DELETE,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "删除站内信", 1);
			}
			return result;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询邮件信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryEmailDetailById(long mailId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {

			return myHomeInfoSettingDao.queryEmailDetailById(conn, mailId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public long updateMails(String ids, int type) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.updateMail(conn, ids, type);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 查询邮件信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryEmailById(long mailId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {

			return myHomeInfoSettingDao.queryEmailById(conn, mailId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryBankInfoById(long bankId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {

			return myHomeInfoSettingDao.queryBankInfoById(conn, bankId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 修改个人头像的时候判断是否填写过个人信息
	 * 
	 * @return
	 */
	public Map<String, String> queryHeadImg(String username) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeInfoSettingDao.queryHeadImg(conn, username);
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
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: updatePersonImg
	 * @Param: HomeInfoSettingService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:03:11
	 * @Return:
	 * @Descb: 修改个人头像
	 * @Throws:
	 */
	public long updatePersonImg(String imgPath, Long id) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			Map<String, String> personMap = myHomeInfoSettingDao.queryPerson(conn, id);
			if (personMap != null) {
				result = myHomeInfoSettingDao.updatePersonImg(conn, imgPath, id);
			} else {
				result = myHomeInfoSettingDao.addPersonImg(conn, imgPath, id);
			}
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public MyHomeInfoSettingDao getMyHomeInfoSettingDao() {
		return myHomeInfoSettingDao;
	}

	public void setMyHomeInfoSettingDao(MyHomeInfoSettingDao myHomeInfoSettingDao) {
		this.myHomeInfoSettingDao = myHomeInfoSettingDao;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryRenewalVIP
	 * @Param: HomeInfoSettingService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:38:26
	 * @Return:
	 * @Descb: 查询用户的续费情况
	 * @Throws:
	 */
	public Map<String, String> queryRenewalVIP(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeInfoSettingDao.queryRenewalVIP(conn, id);
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
	 * @MethodName: renewalVIPSubmit
	 * @Param: HomeInfoSettingService
	 * @Author: gang.lv
	 * @Date: 2013-4-11 上午10:50:33
	 * @Return:
	 * @Descb: vip续费提交
	 * @Throws:
	 */
	public Map<String, String> renewalVIPSubmit(Long id, Map<String, Object> platformCostMap) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		long returnId = -1;
		String result = "";
		Map<String, String> noticeMap = new HashMap<String, String>();
		String username = "";
		try {
			// 查询VIP会费
			double vipFee = Convert.strToDouble(platformCostMap.get(IAmountConstants.VIP_FEE_RATE) + "", 0);
			// 查询用户是否为VIP
			Map<String, String> isVIPMap = myHomeInfoSettingDao.isVIP(conn, id);
			if (isVIPMap != null && isVIPMap.size() > 0) {
				// 查询VIP会员是否需要续费
				Map<String, String> renewalMap = myHomeInfoSettingDao.isRenewal(conn, id);
				if (renewalMap != null && renewalMap.size() > 0) {
					// 查询用户可用金额是否够VIP续费
					Map<String, String> userAmount = myHomeInfoSettingDao.isForVIPFee(conn, id, vipFee);
					if (userAmount != null && userAmount.size() > 0) {
						username = userAmount.get("username") + "";
						// 扣除VIP会费
						myHomeInfoSettingDao.decucatedVIPFee(conn, id, vipFee);
						// 添加VIP会费扣除记录
						//myHomeInfoSettingDao.addVIPFeeRecord(conn, id, vipFee);
						// 查询扣除VIP会费后的账户金额
						Map<String, String> userSumMap = myHomeInfoSettingDao.queryUserAmountAfterHander(conn, id);
						if (userSumMap == null) {
							userSumMap = new HashMap<String, String>();
						}
						double usableSum = Convert.strToDouble(userSumMap.get("usableSum") + "", 0);
						double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum") + "", 0);
						double forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
						// 添加资金流动记录
						// returnId = fundRecordDao.addFundRecord(conn, id,
						// "VIP会员续费成功",
						// vipFee, usableSum, freezeSum, forPI,
						// -1,"扣除vip会员费",0.0,vipFee,-1,-1,803,0.0);
						// 消息模版
						// 站内信
						// noticeMap.put("mail",
						// "您的VIP会员续费成功,本次扣除VIP会费：￥"+vipFee+"元");
						// 邮件
						// noticeMap.put("email","您的VIP会员续费成功,本次扣除VIP会费：￥"+vipFee+"元");
						// 短信
						// noticeMap.put("note",
						// "尊敬的"+username+":\n    您的VIP会员续费成功,本次扣除VIP会费：￥"+vipFee+"元");

						if (returnId < 1) {
							returnId = -1;
						} else {
							// 给用户发送通知
							// selectedService.sendNoticeMSG(conn, id,
							// "VIP会员续费成功", noticeMap,
							// IConstants.NOTICE_MODE_5);
						}
						result = "1";
					} else {
						result = "您的账户可用余额不足,请充值";
					}
				} else {
					result = "您的VIP会员暂未过期,无需续费";
				}
			} else {
				result = "您还不是VIP会员,请先申请VIP会员";
			}
			if (returnId < 0) {
				conn.rollback();
			}
			map.put("result", result);
		} finally {
			conn.close();
		}
		return map;
	}

	public Long getConcernList(long userId, String receiver) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = myHomeInfoSettingDao.getConcernList(conn, userId, receiver, -1, -1);
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
		return list.size() <= 0 ? -1L : 1L;
	}

	/**
	 * 查询用户资金状态
	 * 
	 * @param userId
	 * @param cashStatus
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryUserCashStatus(Long userId, int cashStatus) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			Map<String, String> map = myHomeInfoSettingDao.queryUserCashStatus(conn, userId, cashStatus);
			if (map == null || map.size() <= 0) {// 用户提现权限没有被禁用
				return 1L;
			}
			return -1L;
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
	 * 
	 * @param pageBean
	 * @param userId
	 * @param type
	 *            1为查询发件箱 2为查询收件箱
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMailList(PageBean pageBean, long userId, int type) throws SQLException, DataException {

		Connection conn = connectionManager.getConnection();
		String condition = "";
		if (type == 1) {
			condition = " and sender=" + userId;
		} else if (type == 2) {
			condition = " and reciver=" + userId;
		}

		try {
			dataPage(conn, pageBean, "v_t_mail_user", "*", " order by sendTime desc ", condition);// return
																									// myHomeInfoSettingDao.querySendMails(conn,
																									// userId,
																									// -1,
																									// -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
