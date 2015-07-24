package com.sp2p.service;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataRow;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.*;
import com.sp2p.dao.admin.PlatformCostDao;
import com.sp2p.dao.admin.RelationDao;
import com.sp2p.dao.admin.ShoveBorrowAmountTypeDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.entity.LoginVerify;
import com.sp2p.service.admin.SendmsgService;
import com.sp2p.util.ChinaPnRInterface;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author Administrator
 * 
 */
public class UserService extends BaseService {

    public static Log log = LogFactory.getLog(UserService.class);

    private UserDao userDao;

    private RelationDao relationDao;

    private SendmsgService sendmsgService;

    private BeVipDao beVipDao;

    private OperationLogDao operationLogDao;

    private MyHomeInfoSettingDao myHomeInfoSettingDao;

    private ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao;

    private PersonDao personDao;

    private WorkAuthDao workAuthDao;

    private BankCardDao bankCardDao;

    private BorrowDao borrowDao;

    private PlatformCostDao platformCostDao;

    public MyHomeInfoSettingDao getMyHomeInfoSettingDao() {
        return myHomeInfoSettingDao;
    }

    public void setMyHomeInfoSettingDao(MyHomeInfoSettingDao myHomeInfoSettingDao) {
        this.myHomeInfoSettingDao = myHomeInfoSettingDao;
    }

    public void setBeVipDao(BeVipDao beVipDao) {
        this.beVipDao = beVipDao;
    }

    public void setSendmsgService(SendmsgService sendmsgService) {
        this.sendmsgService = sendmsgService;
    }

    public void setPlatformCostDao(PlatformCostDao platformCostDao) {
        this.platformCostDao = platformCostDao;
    }

    /**
     * 添加用户
     *
     * @param email
     * @param userName
     * @param password
     * @param refferee
     *            推荐人
     * @param lastDate
     * @param lastIP
     * @param dealpwd
     *            交易密码
     * @param mobilePhone
     * @param rating
     *            网站积分
     * @param creditrating
     * @param vipstatus
     * @param vipcreatetime
     * @param creditlimit
     * @param authstep
     * @return
     * @throws java.sql.SQLException
     * @throws com.shove.data.DataException
     */
    public Long addUser(String email, String userName, String password, String refferee, String lastDate, String lastIP,
            String dealpwd, String mobilePhone, Integer rating,
            Integer creditrating, Integer vipstatus, String vipcreatetime, Integer creditlimit, Integer authstep,
            String headImg, Integer enable, Long servicePersonId)
            throws SQLException, DataException {

        Connection conn = connectionManager.getConnection();
        long userId = -1L;
        try {
            // 得到信息额度类型
            Map<String, String> map = shoveBorrowAmountTypeDao.queryBorrowAmountByNid(conn, "credit");
			double creditLimit = Convert.strToDouble(map.get("creditLimit"), 0);
			userId = userDao.addUser(conn, email, userName, password, refferee, lastDate, lastIP, dealpwd, mobilePhone, rating, creditrating, vipstatus, vipcreatetime, authstep,
					headImg, enable, servicePersonId, creditLimit);
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

		return userId;
	}

	/**
	 * 添加认证图片
	 *
	 * @param materAuthTypeId
	 * @param imgPath
	 * @param auditStatus
	 * @param userId
	 * @param authTime
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long addImage(long materAuthTypeId, String imgPath, long userId) throws SQLException, DataException {

		Connection conn = connectionManager.getConnection();
		long ImageId = -1L;
		try {
			ImageId = userDao.addImage(conn, materAuthTypeId, imgPath, userId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return ImageId;
	}

	/**
	 * 用户注册(存储过程处理)
	 *
	 * @param email
	 * @param username
	 * @param password
	 * @param refferee
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 * @throws com.shove.data.DataException
	 */
	public Map<String, String> userRegister1(String telephone, String userName, String password, String refferee, Map<String, Object> userMap, int typeLen, String mobilePhone,int registerType)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		int demo = Convert.strToInt(IConstants.ISDEMO, 2);
		boolean flag = false;
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_user_register(conn, ds, outParameterValues, telephone, userName, password, refferee, demo,
                    registerType, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");

			if (ret > 0 && userMap != null) {

				relationDao.addRelation(conn, ret, Convert.strToLong(userMap.get("parentId") + "", -1), Convert.strToInt(userMap.get("level") + "", -1), 1);
			}
			flag = true;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (flag) {
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.close();
		}

		return map;
	}

	/**
	 * 用户注册
	 *
	 * @param email
	 * @param username
	 * @param password
	 * @param refferee
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 * @throws com.shove.data.DataException
	 */
	public Long userRegister(String email, String userName, String password, String refferee, Map<String, Object> userMap, int typeLen, String mobilePhone) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		String dealpwd = null; // 交易密码
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		String vipcreatetime = null;// VIP创建时间
		// double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		// 系统给予默认头型
		Integer enable = 2; // 是否禁用 1、启用 2、禁用
		// 测试--跳过验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到信息额度类型
		Map<String, String> map = shoveBorrowAmountTypeDao.queryBorrowAmountByNid(conn, "credit");
		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
		if (IConstants.ISDEMO.equals("1")) {
			authstep = 1;
			enable = 1;
			vipstatus = 2;
			vipcreatetime = df.format(new Date());
			// creditlimit = creditLimit;
		}
		Long servicePersonId = null;
		long userId = -1L;
		boolean flag = false;
		try {

			userId = userDao.addUser(conn, email, userName, password, refferee, lastDate, lastIP, dealpwd, mobilePhone, rating, creditrating, vipstatus, vipcreatetime, authstep,
					headImg, enable, servicePersonId, creditLimit);
			if (userId <= 0) {
				return -1L;
			}
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				userDao.addMaterialsauth1(conn, userId, i);
			}
			if (userMap != null) {
				relationDao.addRelation(conn, userId, (Long) userMap.get("parentId"), (Integer) userMap.get("level"), 1);
			}
			flag = true;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (flag) {
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.close();
		}

		return userId;
	}

	/**
	 * 手机用户注册
	 *
	 * @param email
	 * @param userName
	 * @param password
	 * @param refferee
	 * @param userMap
	 * @param typeLen
	 * @param mobilePhone
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long userAppRegister(String email, String userName, String password, String refferee, Map<String, Object> userMap, int typeLen, String mobilePhone) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		String dealpwd = null; // 交易密码
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		// DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String vipcreatetime = null;// VIP创建时间
		// double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		// 系统给予默认头型
		Integer enable = 1; // 是否禁用 1、启用 2、禁用
		// 测试--跳过验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到信息额度类型
		Map<String, String> map = shoveBorrowAmountTypeDao.queryBorrowAmountByNid(conn, "credit");
		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
		if (IConstants.ISDEMO.equals("1")) {
			authstep = 1;
			enable = 1;
			vipstatus = 2;
			vipcreatetime = df.format(new Date());
			// creditlimit = creditLimit;
		}
		Long servicePersonId = null;
		long userId = -1L;
		boolean flag = false;
		try {
			userId = userDao.addUser(conn, email, userName, password, refferee, lastDate, lastIP, dealpwd, mobilePhone, rating, creditrating, vipstatus, vipcreatetime, authstep,
					headImg, enable, servicePersonId, creditLimit);
			if (userId <= 0) {
				return -1L;
			}
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				userDao.addMaterialsauth1(conn, userId, i);
			}
			if (userMap != null) {
				relationDao.addRelation(conn, userId, (Long) userMap.get("parentId"), (Integer) userMap.get("level"), 1);
			}
			flag = true;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (flag) {
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.close();
		}

		return userId;
	}

	/**
	 * 投资人填写个人信息
	 *
	 * @param realName
	 * @param cellPhone
	 * @param sex
	 * @param birthday
	 * @param highestEdu
	 * @param eduStartDay
	 * @param school
	 * @param maritalStatus
	 * @param hasChild
	 * @param hasHourse
	 * @param hasHousrseLoan
	 * @param hasCar
	 * @param hasCarLoan
	 * @param nativePlacePro
	 * @param nativePlaceCity
	 * @param registedPlacePro
	 * @param registedPlaceCity
	 * @param address
	 * @param telephone
	 * @param personalHead
	 * @param userId
	 * @param idNo
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public long updateUserBaseTT(String realName, String cellPhone, String sex, String birthday, String highestEdu, String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan, String hasCar, String hasCarLoan, Long nativePlacePro, Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone, String personalHead, Long userId, String idNo) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userDao.updateUserBaseWWc(conn, realName, cellPhone, sex, birthday, highestEdu, eduStartDay, school, maritalStatus, hasChild, hasHourse, hasHousrseLoan,
					hasCar, hasCarLoan, nativePlacePro, nativePlaceCity, registedPlacePro, registedPlaceCity, address, telephone, personalHead, userId, idNo);
			// add by houli
			// add by houli 如果个人信息填写成功，将填写的手机号码同步到T_PHONE_BINDING_INFO表中
			Map<String, String> p_map = myHomeInfoSettingDao.queryBindingInfoByUserId(conn, userId, -1, -1);
			Long result1 = -1L;
			if (p_map == null || p_map.size() <= 0) {// 如果没有记录则插入手机绑定数据，否则进行更新
				result1 = myHomeInfoSettingDao.addBindingMobile(conn, cellPhone, userId, IConstants.PHONE_BINDING_CHECK, "个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
			} else {
				result1 = myHomeInfoSettingDao.updateBindingMobile(conn, cellPhone, userId, IConstants.PHONE_BINDING_CHECK, "个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE);
			}
			if (result1 <= 0) {
				conn.rollback();
				return -1L;
			}
			// end
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

		return personId;
	}

	/**
	 * 存储过程添加或更新用户基本资料
	 *
	 * @param realName
	 * @param cellPhone
	 * @param sex
	 * @param birthday
	 * @param highestEdu
	 * @param eduStartDay
	 * @param school
	 * @param maritalStatus
	 * @param hasChild
	 * @param hasHourse
	 * @param hasHousrseLoan
	 * @param hasCar
	 * @param hasCarLoan
	 * @param nativePlacePro
	 * @param nativePlaceCity
	 * @param registedPlacePro
	 * @param registedPlaceCity
	 * @param address
	 * @param telephone
	 * @param personalHead
	 * @param userId
	 * @param idNo
	 * @param num
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Map<String, String> updateUserBaseData1(String realName, String cellPhone, String sex, String birthday, String highestEdu, String eduStartDay, String school,
			String maritalStatus, String hasChild, String hasHourse, String hasHousrseLoan, String hasCar, String hasCarLoan, Long nativePlacePro, Long nativePlaceCity,
			Long registedPlacePro, Long registedPlaceCity, String address, String telephone, String personalHead, Long userId, String idNo, String num) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		conn.setAutoCommit(false);// 后面添加这么一句即可,设置无法自动提交,手动提交
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		Map<String, String> user = new HashMap<String, String>();
		user = userDao.queryUserById(conn, userId);
		String userName = Convert.strToStr(user.get("username"), "");
		String lastip = Convert.strToStr(user.get("lastIP"), "");
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_userInfo_update(conn, ds, outParameterValues, realName, cellPhone, sex, birthday, highestEdu,
                    eduStartDay, school, maritalStatus, hasChild, hasHourse,
                    hasHousrseLoan, hasCar, hasCarLoan, nativePlacePro, nativePlaceCity, registedPlacePro,
                    registedPlaceCity, address, telephone, personalHead, userId, idNo,
                    userName, lastip, num, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret < 0) {
				conn.rollback();
				return map;
			}
			conn.commit();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 更新用户基础资料
	 *
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long updateUserBaseData(String realName, String cellPhone, String sex, String birthday, String highestEdu, String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan, String hasCar, String hasCarLoan, Long nativePlacePro, Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone, String personalHead, Long userId, String idNo) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> user = new HashMap<String, String>();
		long personId = -1L;
		try {
			user = userDao.queryUserById(conn, userId);
			personId = userDao.updateUserBaseData(conn, realName, cellPhone, sex, birthday, highestEdu, eduStartDay, school, maritalStatus, hasChild, hasHourse, hasHousrseLoan,
					hasCar, hasCarLoan, nativePlacePro, nativePlaceCity, registedPlacePro, registedPlaceCity, address, telephone, personalHead, userId, idNo,
					Convert.strToStr(user.get("username"), ""), Convert.strToStr(user.get("lastIP"), ""));
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			}

			// add by houli
			// add by houli 如果个人信息填写成功，将填写的手机号码同步到T_PHONE_BINDING_INFO表中
			Map<String, String> p_map = myHomeInfoSettingDao.queryBindingInfoByUserId(conn, userId, -1, -1);
			Long result1 = -1L;
			if (p_map == null || p_map.size() <= 0) {// 如果没有记录则插入手机绑定数据，否则进行更新
				result1 = myHomeInfoSettingDao.addBindingMobile(conn, cellPhone, userId, IConstants.PHONE_BINDING_CHECK, "个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
			} else {
				result1 = myHomeInfoSettingDao.updateBindingMobile(conn, cellPhone, userId, IConstants.PHONE_BINDING_CHECK, "个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE);
			}
			if (result1 <= 0) {
				conn.rollback();
				return -1L;
			}
			// end
			conn.commit();

		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return personId;

	}

	/**
	 * 审核用户基础资料
	 *
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long updateUserBaseDataCheck(long userId, int auditStatus, Long adminId) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		StringBuffer msg = new StringBuffer();

		long personId = -1L;
		try {
			personId = userDao.updateUserBaseDataCheck(conn, userId, auditStatus);
			userDao.updateAuditStatus(conn, userId, 2);
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				/*
				 * int phoneStatus = 2;// 默认审核中 if (auditStatus == 2) {// 失败
				 * phoneStatus = 4;// bangding 失败 } else if (auditStatus == 3) {
				 * phoneStatus = 1;// 审核成功 } // 更新用户绑定手机状态 personId =
				 * beVipDao.updatePhoneBanding(conn, userId, phoneStatus); if
				 * (personId <= 0) { conn.rollback(); return -1L; } else {
				 */
				// 发站内信
				String m = "";
				if (auditStatus == 2) {
					m = "不通过";
				} else if (auditStatus == 3) {
					m = "通过";
				}
				msg.append("您的基本信息审核状况:");
				msg.append(m);
				// 发站内信
				personId = sendmsgService.sendCheckMail(userId, " 基本信息审核通知", msg.toString(), 2, adminId);// 2管理员信件
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return personId;
	}

	/**
	 * 该用户上传资料的类型的审核状态
	 *
	 * @param id
	 * @param materAuthTypeId
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long updateUserPicturStatus(Long id, Long materAuthTypeId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		long personId = -1L;
		try {
			personId = userDao.updateUserPicturStatus(conn, id, materAuthTypeId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return personId;

	}

	/**
	 * 增加用户的图片
	 *
	 * @param auditStatus
	 * @param uploadingTime
	 * @param imagePath
	 * @param materialsauthid
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long addUserImage(Integer auditStatus, String uploadingTime, List<Long> lists, List<String> imgListsy, List<String> imgListsn, Long materialsauthid, Long id,
			Long materAuthTypeId, Long tmid, int addCount) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long personId = -1L;
		Long userId = -1L;
		Long userIdauthd = -1L;
		try {
			// 将用户选择可见的设置为可见
			// if(lists.size()>0&&lists!=null){
			personId = userDao.updatematerialImagedetalvisiable(conn, materialsauthid);// 将重置图片的可见性
			// 设置为不可见
			for (Long list : lists) {
				personId = userDao.updatevisiable(conn, list);// 根据传来的id集合重新设置哪个可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			// 插于图片为可见的
			for (String vimg : imgListsy) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus, uploadingTime, vimg, materialsauthid, 1);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}

			}
			// 插于图片为不可见的
			for (String vimg : imgListsn) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus, uploadingTime, vimg, materialsauthid, 2);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}// --------modify by houli 如果未新添加上传图片，那么不修改总审核状态
			if (addCount > 0) {
				userId = userDao.updateUserPicturStatus(conn, id, materAuthTypeId);// 更新用户总证件状态
				if (userId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			userId = userDao.updateUserPicturStatus(conn, id, materAuthTypeId);// 更新用户总证件状态
			if (userId <= 0) {
				conn.rollback();
				return -1L;
			}
			// 更新user authod表中的状态
			userIdauthd = userDao.updateUserauthod(conn, id);// 当5大基本资料上传完成后更新用户的认证步骤
			if (userIdauthd <= 0) {
				conn.rollback();
				return -1L;
			}
			userMap = userDao.queryUserById(conn, id);
			operationLogDao.addOperationLog(conn, "t_materialimagedetal", Convert.strToStr(userMap.get("username"), ""), IConstants.UPDATE,
					Convert.strToStr(userMap.get("lastIP"), ""), 0, "上传图片", 1);
			conn.commit();

		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return personId;

	}

	/**
	 * 更新用户上传图片的可见性
	 *
	 * @param tmdid
	 *            //主图片类型下的图片明细id
	 * @param tmid
	 *            //主图片类型id
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long updatevisiable(Long tmid, List<Long> lists) {
		Connection conn = null;
		try {
			conn = MySQL.getConnection();
			long personId = -1L;
			Long resetvisable = -1L;

			resetvisable = userDao.updatematerialImagedetalvisiable(conn, tmid);// 将重置图片的可见性
			// 重置为不可见
			if (resetvisable <= 0) {
				conn.rollback();
				return -1L;
			}
			for (Long list : lists) {
				personId = userDao.updatevisiable(conn, list);// 根据传来的id集合重新设置哪个可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			conn.commit();

		} catch (Exception e) {
			log.error(e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1L;

	}

	/**
	 * 添加用户的基础资料
	 *
	 * @return
	 * @throws java.sql.SQLException
	 */
	public Long userBaseData(String realName, String cellPhone, String sex, String birthday, String highestEdu, String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan, String hasCar, String hasCarLoan, Long nativePlacePro, Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone, String personalHead, Long userId, String idNo) throws SQLException {
		Connection conn = connectionManager.getConnection();
		long personId = -1L;
		try {
			personId = userDao.addUserBaseData(conn, realName, cellPhone, sex, birthday, highestEdu, eduStartDay, school, maritalStatus, hasChild, hasHourse, hasHousrseLoan,
					hasCar, hasCarLoan, nativePlacePro, nativePlaceCity, registedPlacePro, registedPlaceCity, address, telephone, personalHead, userId, idNo);

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return personId;

	}

	/**
	 * 添加用户工作认证信息
	 *
	 * @throws java.sql.SQLException
	 */
	public Long addUserWorkData(String orgName, String occStatus, Long workPro, Long workCity, String companyType, String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel, String workEmail, String companyAddress, String directedName, String directedRelation, String directedTel,
			String otherName, String otherRelation, String otherTel, String moredName, String moredRelation, String moredTel, Long userId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		long workDataId = -1L;
		try {
			workDataId = userDao.addUserWorkData(conn, orgName, occStatus, workPro, workCity, companyType, companyLine, companyScale, job, monthlyIncome, workYear, companyTel,
					workEmail, companyAddress, directedName, directedRelation, directedTel, otherName, otherRelation, otherTel, moredName, moredRelation, moredTel, userId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return workDataId;

	}

	/**
	 * 修改用户工作认证信息
	 *
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Map<String, String> updateUserWorkData1(String orgName, String occStatus, Long workPro, Long workCity, String companyType, String companyLine, String companyScale,
			String job, String monthlyIncome, String workYear, String companyTel, String workEmail, String companyAddress, String directedName, String directedRelation,
			String directedTel, String otherName, String otherRelation, String otherTel, String moredName, String moredRelation, String moredTel, Long userId, Integer vipStatus,
			Integer newutostept) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		try {
			userMap = userDao.queryUserById(conn, userId);
			String lastip = Convert.strToStr(userMap.get("lastIP"), "");

			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_userWorkInfo_update(conn, ds, outParameterValues, orgName, occStatus, workPro, workCity,
                    companyType, companyLine, companyScale, job, monthlyIncome,
                    workYear, companyTel, workEmail, companyAddress, directedName, directedRelation, directedTel,
                    otherName, otherRelation, otherTel, moredName, moredRelation,
                    moredTel, userId, vipStatus, newutostept, lastip, -1, "");

			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");

			if (ret <= 0) {
				conn.rollback();
				return map;
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
		return map;
	}

	public Long updateUserWorkData(String orgName, String occStatus, Long workPro, Long workCity, String companyType, String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel, String workEmail, String companyAddress, String directedName, String directedRelation, String directedTel,
			String otherName, String otherRelation, String otherTel, String moredName, String moredRelation, String moredTel, Long userId, Integer vipStatus, Integer newutostept)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long workDataId = -1L;
		try {
			workDataId = userDao.updateUserWorkData(conn, orgName, occStatus, workPro, workCity, companyType, companyLine, companyScale, job, monthlyIncome, workYear, companyTel,
					workEmail, companyAddress, directedName, directedRelation, directedTel, otherName, otherRelation, otherTel, moredName, moredRelation, moredTel, userId);

			userMap = userDao.queryUserById(conn, userId);
			workDataId = operationLogDao.addOperationLog(conn, "t_workauth", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
					Convert.strToStr(userMap.get("lastIP"), ""), 0.00, "填写工作认证信息", 1);
			if (workDataId <= 0) {
				conn.rollback();
				return -1L;
			}

			// 跟新用户的认证步骤
			if (newutostept == 2) {
				workDataId = beVipDao.updateUserAustep(conn, userId, 3);// 3为填写完了工作信息的认证步骤
				if (workDataId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			if (vipStatus != 1) {// 如果此时用户的vip状态为会员 那么要更新user的认证步骤
				if (newutostept <= 3) {
					workDataId = beVipDao.updateUserAustep(conn, userId, 4);// 4为填写完了vip的认证步骤
					if (workDataId <= 0) {
						conn.rollback();
						return -1L;
					}
				}
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

		return workDataId;

	}

	/**
	 * 修改用户
	 *
	 * @param email
	 *            电子邮箱
	 * @param userName
	 *            用户名
	 * @param password
	 *            用户密码
	 * @param name
	 *            真实姓名
	 * @param gender
	 *            性别
	 * @param mobilePhone
	 *            手机号码
	 * @param qq
	 * @param provinceId
	 *            省Id
	 * @param cityId
	 *            城市id
	 * @param areaId
	 *            区/镇/县id
	 * @param postalcode
	 *            邮政编码
	 * @param headImg
	 *            头像
	 * @param status
	 *            邮箱是否验证通过 (0:未通过1:通过)
	 * @param balances
	 *            E币账户余额
	 * @param enable
	 *            是否禁用 1、启用 2、禁用
	 * @param rating
	 *            会员等级(1:普通会员2:铜牌会员3:银牌会员4:金牌会员)
	 * @param lastDate
	 *            最后登录时间
	 * @param lastIP
	 *            最后登录ip
	 * @throws java.sql.SQLException
	 * @return Long
	 */
	public Long updateUser(Long id, String email, String userName, String password, String name, String gender, String mobilePhone, String qq, Long provinceId, Long cityId,
			Long areaId, String postalcode, String headImg, Integer status, String balances, Integer enable, Integer rating, String lastDate, String lastIP) throws SQLException {

		Connection conn = connectionManager.getConnection();
		long userId = -1;

		try {
			userId = userDao.updateUser(conn, id, email, userName, password, name, gender);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return userId;
	}

	/**
	 * 判断重复登录
	 *
	 * @param email
	 * @param userName
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long isExistEmailORUserName(String email, String userName) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUserEmaiORUseName(conn, email, userName);
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

	// =====================================
	/**
	 * 用户登录时候验证邮箱和用户名是否激活
	 */
	public Long isUEjihuo(String email, String userName) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUEjihuo(conn, email, userName);
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
	 * 用户登录时候验证邮箱和用户名是否激活
	 */
	public Long isUEjihuo_(String email, String userName) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUEjihuo_(conn, email, userName);
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
	 * 用户登录时候验证邮箱和用户名是否激活
	 */
	public Long checkUserExits(String userName) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			Map<String, String> map = userDao.checkUserExits(conn, userName);
			if (map == null || map.size() == 0)
				return -1L;
			return Convert.strToLong(map.get("id"), -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return -1L;
	}

	// =========================================

	/**
	 * 用户登录处理
	 *
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @param password
	 *            用户密码
	 * @param lastIP
	 *            最后登录ip
	 * @param loginType
	 *            登录类型，1用户名或邮箱登录，
	 * @return User
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	/*public User userLogin1(String userName, String password, String lastIP, String lastTime) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		password = StringEscapeUtils.escapeSql(password.trim());
		if ("1".equals(IConstants.ENABLED_PASS)) {
			password = com.shove.security.Encrypt.MD5(password.trim());
		} else {
			password = com.shove.security.Encrypt.MD5(password.trim() + IConstants.PASS_KEY);
		}
		AccountUserDo user = null;
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_user_login(conn, ds, outParameterValues, userName, password, lastIP, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret <= 0) {
				if (ret == -5) {
					conn.close();
					user = new User();
					user.setEnable(2);
					return user;
				} else {
					return null;
				}
			} else {
				String userid = userDao.queryIdByUser(conn, userName).get("id");
				if (userid != null && !userid.equals("")) {
					Map<String, String> usermap = new HashMap<String, String>();
					usermap = userDao.queryUserById(conn, Convert.strToInt(userid, 0));
					Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();
					DataSet dataSet = sessionuser.open(conn, "", " id=" + Convert.strToLong(userid, -1l), "", -1, -1);
					Map<String, String> sessionmap = new HashMap<String, String>();
					sessionmap = BeanMapUtils.dataSetToMap(dataSet);
					if (sessionmap != null && sessionmap.size() > 0) {
						user = new User();
						user.setAuthStep(Convert.strToInt(sessionmap.get("authStep"), -1));
						user.setMobilePhone(Convert.strToStr(sessionmap.get("mobilePhone"), null));
						user.setSource(Convert.strToInt(sessionmap.get("source"), -1));
						user.setUsrCustId(Convert.strToLong(sessionmap.get("usrCustId"), -1));
						user.setCreditrating(Convert.strToInt(sessionmap.get("creditrating"), 0));
						user.setCreditLevel(Convert.strToInt(sessionmap.get("creditLevel"), 0));
						user.setEmail(Convert.strToStr(sessionmap.get("email"), null));
						user.setPassword(Convert.strToStr(sessionmap.get("password"), null));
						user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
						user.setRealName(Convert.strToStr(sessionmap.get("realName"), null));
						user.setKefuname(Convert.strToStr(sessionmap.get("kefuname"), null));
						user.setUserName(Convert.strToStr(sessionmap.get("username"), null));
						user.setIdNo(Convert.strToStr(sessionmap.get("idNo"), null));
						user.setVipStatus(Convert.strToInt(sessionmap.get("vipStatus"), -1));
						user.setHeadImage(Convert.strToStr(usermap.get("headImg"), null));
						user.setEnable(Convert.strToInt(usermap.get("enable"), -1));
						user.setPersonalHead(Convert.strToStr(sessionmap.get("personalHead"), null));
						user.setKefuid(Convert.strToInt(sessionmap.get("tukid"), -1));
						user.setCreditLimit(Convert.strToStr(sessionmap.get("usableCreditLimit"), null));
						user.setUsableSum(Convert.strToStr(usermap.get("usableSum"), null));
						*//*
						 * 20140610 by 刘文韬
						 * 增加用户群组字段
						 *//*
						user.setUserGroup(Convert.strToInt(sessionmap.get("userGroup"), 0));
						user.setRefferee(Convert.strToStr(usermap.get("refferee"), "-1"));
					}
				}
			}
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
		return user;
	}*/

	/**
	 * 用户登录处理
	 *
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @param password
	 *            用户密码
	 * @param lastIP
	 *            最后登录ip
	 * @param lastTime
	 *            最后登录时间
	 * @param loginType
	 *            登录类型，1用户名或邮箱登录，
	 * @return User
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	/*public User userLogin(String userName, String password, String lastIP, String lastTime) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		User user = null;
		try {
			password = StringEscapeUtils.escapeSql(password.trim());
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim() + IConstants.PASS_KEY);
			}
			Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
			DataSet ds = t_user.open(conn, "id,username,headImg,enable,vipStatus,email,authStep,lastIP ", "(email ='" + StringEscapeUtils.escapeSql(userName.trim())
					+ "' OR username='" + StringEscapeUtils.escapeSql(userName.trim()) + "' OR mobilePhone='" + StringEscapeUtils.escapeSql(userName.trim())
					+ "') AND password = '" + password + "' AND enable != 2", "", -1, -1);
			int returnId = ds.tables.get(0).rows.getCount();
			if (returnId <= 0) {
				conn.close();
				return null;
			} else {
				user = new User();
				DataRow dr = ds.tables.get(0).rows.get(0);

				// ======
				Map<String, String> sessionmap = new HashMap<String, String>();

				Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

				DataSet dataSet = sessionuser.open(conn, "", " id=" + (Long) (dr.get("id") == null ? -1l : dr.get("id")), "", -1, -1);
				sessionmap = BeanMapUtils.dataSetToMap(dataSet);

				if (sessionmap != null && sessionmap.size() > 0) {
					user = new User();
					user.setAuthStep(Convert.strToInt(sessionmap.get("authStep"), -1));
					user.setSource(Convert.strToInt(sessionmap.get("source"), -1));
					user.setUsrCustId(Convert.strToLong(sessionmap.get("usrCustId"), -1));
					user.setCreditrating(Convert.strToInt(sessionmap.get("creditrating"), 0));
					user.setCreditLevel(Convert.strToInt(sessionmap.get("creditLevel"), 0));
					user.setEmail(Convert.strToStr(sessionmap.get("email"), null));
					user.setPassword(Convert.strToStr(sessionmap.get("password"), null));
					user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
					user.setRealName(Convert.strToStr(sessionmap.get("realName"), null));
					user.setKefuname(Convert.strToStr(sessionmap.get("kefuname"), null));
					user.setUserName(Convert.strToStr(sessionmap.get("username"), null));
					user.setIdNo(Convert.strToStr(sessionmap.get("idNo"), null));
					user.setVipStatus(Convert.strToInt(sessionmap.get("vipStatus"), -1));
					user.setHeadImage((String) (dr.get("headImg") == null ? "" : dr.get("headImg")));
					user.setEnable((Integer) (dr.get("enable") == null ? -1 : dr.get("enable")));
					user.setPersonalHead(Convert.strToStr(sessionmap.get("personalHead"), null));
					user.setKefuid(Convert.strToInt(sessionmap.get("tukid"), -1));
					user.setCreditLimit(Convert.strToStr(sessionmap.get("usableCreditLimit"), null));
				}
				if (StringUtils.isNotBlank(lastIP)) {
					t_user.lastDate.setValue(lastTime);
					t_user.lastIP.setValue(lastIP);
					t_user.update(conn, " id=" + user.getId());
				}

			}
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
		return user;
	}*/

	/**
	 * 虚拟用户登录
	 *
	 * @param userName
	 * @param password
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	/*public User userVirtualLogin(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		User user = null;

		try {
			Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
			DataSet ds = t_user.open(conn, "id  ,username ,headImg  ,enable  ,vipStatus  ,email ,authStep  ", " id=" + id, "", -1, -1);
			int returnId = ds.tables.get(0).rows.getCount();
			if (returnId <= 0) {
				conn.close();
				return null;
			} else {
				user = new User();
				DataRow dr = ds.tables.get(0).rows.get(0);

				// ======
				Map<String, String> sessionmap = new HashMap<String, String>();

				Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

				DataSet dataSet = sessionuser.open(conn, "", " id =" + (dr.get("id") == null ? -1l : dr.get("id")), "", -1, -1);
				sessionmap = BeanMapUtils.dataSetToMap(dataSet);

				if (sessionmap != null && sessionmap.size() > 0) {
					user = new User();
					user.setAuthStep(Convert.strToInt(sessionmap.get("authStep"), -1));
					user.setEmail(Convert.strToStr(sessionmap.get("email"), null));
					user.setPassword(Convert.strToStr(sessionmap.get("password"), null));
					user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
					user.setRealName(Convert.strToStr(sessionmap.get("realName"), null));
					user.setKefuname(Convert.strToStr(sessionmap.get("kefuname"), null));
					user.setUserName(Convert.strToStr(sessionmap.get("username"), null));
					user.setVipStatus(Convert.strToInt(sessionmap.get("vipStatus"), -1));
					user.setHeadImage((String) (dr.get("headImg") == null ? "" : dr.get("headImg")));
					user.setEnable(Convert.strToInt(dr.get("enable") + "", -1));
					user.setPersonalHead(Convert.strToStr(sessionmap.get("personalHead"), null));
					user.setKefuid(Convert.strToInt(sessionmap.get("tukid"), -1));
					user.setCreditLimit(Convert.strToStr(sessionmap.get("usableCreditLimit"), null));
				}
			}
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
		return user;
	}*/

	/**
	 * @MethodName: loginCountReFresh
	 * @Param: UserService
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午10:34:45
	 * @Return:
	 * @Descb: 刷新登录计数
	 * @Throws:
	 */
	public void loginCountReFresh(long userId) throws SQLException {
		if (userId > -1) {
			Connection conn = null;
			try {
				conn = connectionManager.getConnection();
				MySQL.executeNonQuery(conn, " update t_user set loginCount = loginCount + 1 where id=" + userId);
			} finally {
				conn.close();
			}
		}
	}

	// =================登陆中初始化LoginVerify
	public LoginVerify getLoginVerify(Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		LoginVerify loginVerify = null;
		Map<String, String> spmap = new HashMap<String, String>();
		Map<String, String> vpmap = new HashMap<String, String>();
		try {
			Dao.Views.v_t_user_loginsession_user sessond = new Dao().new Views().new v_t_user_loginsession_user();
			Dao.Views.v_t_login_session_verify verify = new Dao().new Views().new v_t_login_session_verify();
			DataSet sdataSet = sessond.open(conn, "", " id=" + userId, "", -1, -1);
			spmap = BeanMapUtils.dataSetToMap(sdataSet);
			DataSet vdataSet = verify.open(conn, "", " id=" + userId, "", -1, -1);
			vpmap = BeanMapUtils.dataSetToMap(vdataSet);
			if (spmap != null && spmap.size() > 0 && vpmap != null && vpmap.size() > 0) {
				loginVerify = new LoginVerify();
				loginVerify.setJbStatus(Convert.strToInt(spmap.get("tpauditStatus"), -1));
				loginVerify.setAllworkjbStatus(Convert.strToInt(spmap.get("twsum"), -1));
				loginVerify.setIdentyVerifyStatus(Convert.strToInt(vpmap.get("identyauditStatus"), -1));
				loginVerify.setWorkVerifyStatus(Convert.strToInt(vpmap.get("workauditStatus"), -1));
				loginVerify.setAddressVerifyStatus(Convert.strToInt(vpmap.get("addryauditStatus"), -1));
				loginVerify.setResponseVerifyStatus(Convert.strToInt(vpmap.get("responsauditStatus"), -1));
				loginVerify.setIncomeVerifyStatus(Convert.strToInt(vpmap.get("incomeauditStatus"), -1));
				loginVerify.setFangchanVerifyStatus(Convert.strToInt(vpmap.get("fcyauditStatus"), -1));
				loginVerify.setGcVerifyStatus(Convert.strToInt(vpmap.get("gcauditStatus"), -1));
				loginVerify.setJhVerifyStatus(Convert.strToInt(vpmap.get("jhauditStatus"), -1));
				loginVerify.setXlVerifyStatus(Convert.strToInt(vpmap.get("xlauditStatus"), -1));
				loginVerify.setJsVerifyStatus(Convert.strToInt(vpmap.get("jsauditStatus"), -1));
				loginVerify.setSjVerifyStatus(Convert.strToInt(vpmap.get("sjauditStatus"), -1));
				loginVerify.setWbVerifyStatus(Convert.strToInt(vpmap.get("wbauditStatus"), -1));
				loginVerify.setSpVerifyStatus(Convert.strToInt(vpmap.get("spauditStatus"), -1));
				loginVerify.setXcVerifyStatus(Convert.strToInt(vpmap.get("xcauditStatus"), -1));
				loginVerify.setDbVerifyStatus(Convert.strToInt(vpmap.get("dbauditStatus"), -1));
				loginVerify.setDyVerifyStatus(Convert.strToInt(vpmap.get("dyauditStatus"), -1));
			}
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return loginVerify;
	}

	/*public User jumpToWorkData(Long userId) throws Exception {
		// 更新user表中的认证状态
		User user = null;
		Connection conn = connectionManager.getConnection();
		Map<String, String> sessionmap = new HashMap<String, String>();
		try {
			Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

			DataSet dataSet = sessionuser.open(conn, "", " id=" + userId, "", -1, -1);
			sessionmap = BeanMapUtils.dataSetToMap(dataSet);

			if (sessionmap != null && sessionmap.size() > 0) {
				user = new User();
				user.setAuthStep(Convert.strToInt(sessionmap.get("authStep"), -1));
				user.setEmail(Convert.strToStr(sessionmap.get("email"), null));
				user.setPassword(Convert.strToStr(sessionmap.get("password"), null));
				user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
				user.setRealName(Convert.strToStr(sessionmap.get("realName"), null));
				user.setKefuname(Convert.strToStr(sessionmap.get("kefuname"), null));
				user.setUserName(Convert.strToStr(sessionmap.get("username"), null));
				user.setVipStatus(Convert.strToInt(sessionmap.get("vipStatus"), -1));
				user.setKefuid(Convert.strToInt(sessionmap.get("tukid"), -1));

			}
		} catch (Exception e) {
		} finally {
			conn.close();
		}

		return user;

		// ===============================================================================

	}*/

	// 查询用户的最新状态
	public Map<String, String> querynewStatus(Long userId) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> sessionmap = null;
		try {
			Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

			DataSet dataSet = sessionuser.open(conn, "", " id=" + userId, "", -1, -1);
			sessionmap = BeanMapUtils.dataSetToMap(dataSet);

		} catch (Exception e) {
		} finally {
			conn.close();
		}

		return sessionmap;

		// ===============================================================================
	}

	/**
	 * 更新申请vip时候的步骤和状态
	 *
	 * @param userId
	 * @param authStep
	 *            认证步骤
	 * @param vipStatus
	 *            会员状态
	 * @return User实体
	 * @throws Exception
	 */
	public Long updataUserVipStatus(Long userId, int authStep, int vipStatus, int servicePersonId, String content, String vipFee, String username) throws Exception {
		StringBuffer msg = new StringBuffer();
		long uId = -1L;
		Connection conn = MySQL.getConnection();
		try {
			uId = userDao.updateUser(conn, userId, authStep, vipStatus, servicePersonId, content, vipFee);
			if (uId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				// 发送站内信
				msg.append("尊敬的" + username + ",你申请vip成功");
				// 发站内信
				uId = sendmsgService.sendCheckMail(userId, " 申请vip审核通知", msg.toString(), 2, -1);// 2管理员信件
				// -1
				// 后台管理员
				if (uId <= 0) {
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
		return uId;
	}

	public Long frontVerificationEmial(Long userId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Long retut = -1L;

		user.enable.setValue(1);
		try {
			retut = user.update(conn, " id=" + userId);
		} finally {
			conn.close();
		}
		return retut;
	}

	public Long frontUpdateUser(Long id, String name, String gender, String mobilePhone, String qq, Long provinceId, Long cityId, Long areaId, String postalcode)
			throws SQLException {
		Long retut = -1L;
		try {
			retut = updateUser(id, null, null, null, name, gender, mobilePhone, qq, provinceId, cityId, areaId, postalcode, null, null, null, null, null, null, null);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return retut;
	}

	/**
	 * 处理前台用户修改头像
	 *
	 * @param userId
	 *            用户id
	 * @param headImg
	 *            图片地址
	 * @return Long
	 * @throws java.sql.SQLException
	 */
	public Long frontUpdateUserHeadImg(Long userId, String headImg) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Long retut = -1L;
		try {
			user.headImg.setValue(headImg);
			retut = user.update(conn, " id=" + userId);
		} finally {
			conn.close();
		}
		return retut;
	}

	/**
	 * 处理前台用户修改密码
	 *
	 * @param userId
	 *            用户id
	 * @param password
	 *            新密码
	 * @return Long
	 * @throws java.sql.SQLException
	 */
	public Long frontUpdatePassword(Long userId, String password) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Long retut = -1L;
		try {
			user.password.setValue(password);
			retut = user.update(conn, " id=" + userId);
		} finally {
			conn.close();
		}
		return retut;
	}

	/**
	 * 修改用户邮箱验证状态
	 *
	 * @Title: updateUserEmailStatus
	 * @param id
	 *            用户ID
	 * @param status
	 *            标志邮箱是否验证通过 (0:未通过1:通过)
	 * @throws java.sql.SQLException
	 * @return Long
	 */
	public Long updateUserEmailStatus(Long id, Integer status) throws SQLException {
		Connection conn = connectionManager.getConnection();
		long userId = -1;

		try {
			userId = userDao.updateUserEmailStatus(conn, id, status);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return userId;
	}

	/**
	 * 修改用户密码
	 *
	 * @param id
	 * @param password
	 * @return
	 * @throws java.sql.SQLException
	 */
	public Long updateUserPassword(Long id, String password) throws SQLException {
		Connection conn = connectionManager.getConnection();
		long userId = -1;

		try {
			userId = userDao.updateUserPassword(conn, id, password);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return userId;
	}

	/**
	 * 根据用户id查询用户信息
	 *
	 * @param id
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserById(long id) throws SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 根据用户名查询用户信息
	 *
	 * @param id
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserByUsername(String userName) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserByUsername(conn, userName);
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
	 * 查询用户的五大基本资料的计数
	 *
	 * @param id
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */
	public Map<String, String> queryPicturStatuCount(long id) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPicturStatuCount(conn, id);
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
	 * 查询前台上传图片的图片状态
	 *
	 * @param id
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */
	public Map<String, String> queryUserPictureStatus(long id) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserPictureStatus(conn, id);
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
	 * 查询用户前台五大基本资料信息和显示详细图片的第一张
	 *
	 * @param id
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */
	public List<Map<String, Object>> queryBasePicture(long id) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryBasePicture(conn, id);
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
	 * 查询用户前台可选大基本资料信息和显示详细图片的第一张
	 *
	 * @param id
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */
	public List<Map<String, Object>> querySelectPicture(long id) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.querySelectPicture(conn, id);
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
	 * 查询每一个证件的上传类型的图片数据显示
	 *
	 * @param tmid
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */
	public List<Map<String, Object>> queryPerTyhpePicture(Long tmid) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryPerTyhpePicture(conn, tmid);
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
	 * 查询图片类型
	 *
	 * @param userId
	 * @param tmid
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */

	public Map<String, String> queryPitcturTyep(Long userId, long tmid) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPitcturTyep(conn, tmid, userId);
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
	 * 查询图片id
	 *
	 * @param userId
	 * @param tmid
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */

	public Map<String, String> queryPitcturId(Long userId, long tmid) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPitcturId(conn, tmid, userId);
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

	public Map<String, String> queryIdByUser(String userName) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryIdByUser(conn, userName);
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
	 * 根据用户ID查询ID(推荐人)
	 */
	public Map<String, String> queryById(String userId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryById(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 根据用户ID查询用户信息
	 */
	public Map<String, String> queryUserInfo(String userId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryByUserInfo(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 根据用户名称查询ID(推荐人)
	 */
	public Map<String, String> queryByName(String userName) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryByName(conn, userName);
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

	public Map<String, String> queryUserByName(String userName) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserByName(conn, userName);
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

	public Map<String, String> queryPassword(String email) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPassword(conn, email);
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

	public Map<String, String> queryPassword(String email, String username) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = null;
		try {
			map = userDao.queryPassword(conn, StringEscapeUtils.escapeSql(email), StringEscapeUtils.escapeSql(username));
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
	 * 查询客服列表
	 *
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */
	public List<Map<String, Object>> querykefylist() throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.querykefylist(conn);
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
	 * 用户基本信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryPersonById(long id) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPersonById(conn, id);
			Map<String, String> map1 = userDao.queryUserById(conn, id);
			if (map1 != null) {
				if (map == null)
					map = new HashMap<String, String>();
				map.put("username", map1.get("username"));
				map.put("refferee", map1.get("refferee"));
				map.put("source", map1.get("source"));
				map.put("email", map1.get("email"));
				map.put("usrCustId", map1.get("usrCustId"));
				map.put("mobilePhone", map1.get("mobilePhone"));
			}
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
	 * 用户资金
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryPerson_MoneyById(long id) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPerson_MoneyById(conn, id);
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
	 * 根据用户ID查询资金变动情况
	 * */
	public List<Map<String, Object>> queryUserMoneyChange(long id) throws Exception {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryUserMoneyChange(conn, id);
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
			map = userDao.queryVipParamList(conn, id);
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
	 * add by houli 查找用户资金管理信息
	 *
	 * @param userDao
	 */
	public void queryUserFundInfo(PageBean<Map<String, Object>> pageBean, String userName) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		// 手机变更状态为空
		String command = " ";
		if (userName != null) {
			command += " and username like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		try {
			dataPage(conn, pageBean, "v_t_user_fund_lists", "*", "", command);
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
	 * 查询黑名单用户
	 *
	 * @param pageBean
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public void queryBlacklistUser(PageBean<Map<String, Object>> pageBean, String userName) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		// 手机变更状态为空
		StringBuffer command = new StringBuffer();
		command.append("and enable=3");
		if (StringUtils.isNotBlank(userName) && !userName.equals("")) {
			command.append(" and username like '%");
			command.append(StringEscapeUtils.escapeSql(userName));
			command.append("%'");
		}

		try {
			dataPage(conn, pageBean, "v_blacklist_list", "*", "", command.toString());
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
	 * 更新黑名单用户
	 *
	 * @param conn
	 * @param id
	 * @param enable
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long updateEnable(Long id, Integer enable) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
			result = userDao.updateEnable(conn, id, enable);
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
		return result;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 查询锁定用户或未锁定用户
	 *
	 * @param userName
	 *            用户名
	 * @param realName
	 *            真实名字
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param lockType
	 *            判断是否锁定，1为未锁定，2为锁定
	 * @param pageBean
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	@SuppressWarnings("unchecked")
	public void queryLockUsers(String userName, String realName, String cellPhone, String idNo, int lockType, PageBean pageBean) throws SQLException, DataException {

		Connection conn = connectionManager.getConnection();
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username like '%");
			condition.append(StringEscapeUtils.escapeSql(userName));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName like '%");
			condition.append(StringEscapeUtils.escapeSql(realName));
			condition.append("%' ");
		}
		if (lockType == 1) {// 启用
			if (StringUtils.isNotBlank(cellPhone)) {
				condition.append(" and cellPhone like '%");
				condition.append(StringEscapeUtils.escapeSql(cellPhone));
				condition.append("%' ");
			}
			if (StringUtils.isNotBlank(idNo)) {
				condition.append(" and idNo like '%");
				condition.append(StringEscapeUtils.escapeSql(idNo));
				condition.append("%' ");
			}
			condition.append(" and enable=1 ");
		} else if (lockType == 2) { // 锁定
			if (StringUtils.isNotBlank(idNo)) {
				condition.append(" and idNo like '%");
				condition.append(StringEscapeUtils.escapeSql(idNo));
				condition.append("%' ");
			}
			if (StringUtils.isNotBlank(cellPhone)) {
				condition.append(" and cellPhone like '%");
				condition.append(StringEscapeUtils.escapeSql(cellPhone));
				condition.append("%' ");
			}
			condition.append(" and enable=2 ");
		}

		try {
			dataPage(conn, pageBean, "v_t_user_lock", "*", "", condition.toString());
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

	public Map<String, String> querymaterialsauthtypeCount() throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.querymaterialsauthtypeCount(conn);
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
	 * 更新锁定用户状态
	 *
	 * @param ids
	 * @param enable
	 * @return
	 * @throws java.sql.SQLException
	 */
	public long updateLockedStatus(String ids, int enable) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
			result = userDao.updateLockedStatus(conn, ids, enable);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * add by houli 查看用户是否已经绑定了手机号码
	 *
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */
	public Map<String, String> queryBindingMobleUserInfo(Long userId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queryBindingMobleUserInfo(conn, userId, -1, -1);
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
	 * 查询所有用户
	 *
	 * @return
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 */
	public List<Map<String, Object>> queryUserAll() throws DataException, SQLException {

		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = userDao.queryUserAll(conn);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;

	}

	public long queryUserIdByPhone(String cellPhone) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		long result = -1;
		try {
			result = userDao.queryUserIdByPhone(conn, cellPhone);

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
		return result;
	}

	/**
	 * 修改用户密码
	 *
	 * @param id
	 * @param password
	 * @return
	 * @throws java.sql.SQLException
	 */
	public long updateLoginPwd(Long userId, String password) throws SQLException {
		Connection conn = connectionManager.getConnection();
		long result = -1;
		try {
			result = userDao.updatePwd(conn, userId, password, 1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public Long updateDealPwd(long userId, String password) throws SQLException {
		Connection conn = connectionManager.getConnection();
		long result = -1;
		try {
			result = userDao.updatePwd(conn, userId, password, 2);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 查询用户信息
	 *
	 * @param userName
	 *            邮箱号，手机号，用户名
	 * @param pwd
	 *            密码
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Map<String, String> queryUserByUserAndPwd(String userName, String pwd) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> result = null;
		try {
			result = userDao.queryUserByUserAndPwd(conn, userName, pwd);
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
		return result;
	}

	public Map<String, String> queryUserAmount(Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return userDao.queryUserAmount(conn, userId);
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
	 * 手机查询
	 *
	 * @param cellphone
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Long isPhoneExist(String cellphone) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isPhoneExist(conn, cellphone);
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

	public long updateEmalByid(long id, String email) throws Exception {
		long result = -1;
		Connection conn = connectionManager.getConnection();
		try {
			result = userDao.updateEmalByid(conn, id, email);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public Map<String, String> queEmailUser(Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return myHomeInfoSettingDao.queEmailUser(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	/**
	 * 激活账户
	 *
	 * @param userId
	 * @return
	 * @throws java.sql.SQLException
	 */
	public long updateUserActivate(long userId) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = userDao.updateUserActivate(conn, userId);
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

	public List<Map<String, Object>> queryUserByIds(String... ids) throws Exception {
		Connection conn = connectionManager.getConnection();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			for (String id : ids) {
				list.add(userDao.queryUserAll(conn, Convert.strToLong(id, -1)).get(0));
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	public void setShoveBorrowAmountTypeDao(ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao) {
		this.shoveBorrowAmountTypeDao = shoveBorrowAmountTypeDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	// public Long addUser(Map<String,String> paramMap) throws SQLException,
	// DataException{
	// Connection conn = connectionManager.getConnection();
	// long userId = -1L;
	// try {
	// Map<String,String> map = userDao.queryIdByUser(conn,
	// paramMap.get("USERNAME"));
	// if(map != null && !map.isEmpty()){//用户名重复
	// return -2L;
	// }
	// userId = userDao.addUser(conn,paramMap);
	// } catch (SQLException e) {
	// log.error(e);
	// e.printStackTrace();
	// throw e;
	// } finally {
	// conn.close();
	// }
	//
	// return userId;
	// }
	// 得到所有平台所有收费标准
	public Map<String, String> getFeeMap(Connection conn) {
		// 得到所有平台所有收费标准
		List<Map<String, Object>> mapList = null;
		try {
			mapList = platformCostDao.queryPlatformCostAll(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mapList == null)
			return null;
		Map<String, Object> mapfee = new HashMap<String, Object>();
		Map<String, Object> mapFeestate = new HashMap<String, Object>();
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Map<String, Object> platformCostMap : mapList) {
			double costFee = Convert.strToDouble(platformCostMap.get("costFee") + "", 0);
			int costMode = Convert.strToInt(platformCostMap.get("costMode") + "", 0);
			String remark = Convert.strToStr(platformCostMap.get("remark") + "", "");
			if (costMode == 1) {
				mapfee.put(platformCostMap.get("alias") + "", costFee * 0.01);
			} else {
				mapfee.put(platformCostMap.get("alias") + "", costFee);
			}
			mapFeestate.put(platformCostMap.get("alias") + "", remark); // 费用说明
			platformCostMap = null;
		}
		String json = JSONObject.fromObject(mapfee).toString();
		String jsonState = JSONObject.fromObject(mapFeestate).toString();
		resultMap.put("feelog", json);
		resultMap.put("feestate", jsonState);
		return resultMap;
	}

	/**
	 * 导入用户资料
	 */
	public List<String> addImportUser(List<Map<String, String>> userList, List<Map<String, String>> personList, List<Map<String, String>> workList,
			List<Map<String, String>> bankList, List<Map<String, String>> borrowList) throws SQLException {
		Connection conn = MySQL.getConnection();
		long userId = -1L, result = -1L;
		List<String> errorList = new ArrayList<String>();
		Map<String, String> map;
		Map<String, String> feeMap = getFeeMap(conn);
		try {
			for (int i = 0, n = userList.size(); i < n; i++) {
				// 导入前先进行的判断.如果身份证存在,不导入用户,该标添加到该用户名下
				if (this.importBefore(conn, personList.get(i).get("idNo"), personList.get(i).get("mobilePhone"), errorList, borrowList.get(i), feeMap, i)) {
					continue;
				}

				map = userList.get(i);
				Map<String, String> userMap = userDao.queryIdByUser(conn, map.get("userName"));
				if (userMap != null && !userMap.isEmpty()) {// 用户名重复
					errorList.add("第" + (i + 3) + "行导入失败，用户名" + userList.get(i).get("userName") + "重复");
					break;
				}

				userId = userDao.addUser(conn, map);
				if (userId <= 0) {
					errorList.add("第" + (i + 3) + "行导入失败，个人基本信息数据错误");
					break;
				}
				// 汇付后台开户
				JSONObject json = JSONObject.fromObject(ChinaPnRInterface
                        .bgRegister("BgRegister", userId + "", map.get("userName"), map.get("oldPass"),
                                map.get("oldPass"),
                                personList.get(i).get("idNo"), map.get("mobilePhone"), ""));
				if (json.getInt("RespCode") != 0) {// 失败
					userDao.deleteUser(conn, userId + "", "");
					errorList.add("第" + (i + 3) + "行导入失败，汇付注册失败:" + json.getString("RespDesc"));
					break;
				} else {
					long usrCustId = json.getLong("UsrCustId");
					String email = json.getString("UsrEmail");
					userDao.updateUser(conn, userId, usrCustId, email);
				}

				map = personList.get(i);
				map.put("USERID", userId + "");
				result = personDao.addPerson(conn, map);
				if (result <= 0) {
					errorList.add("第" + (i + 3) + "行导入失败，个人基本信息数据错误");
					break;
				}

				map = workList.get(i);
				map.put("USERID", userId + "");
				result = workAuthDao.addWorkAuth(conn, map);
				if (result <= 0) {
					errorList.add("第" + (i + 3) + "行导入失败，工作信息数据错误");
					break;
				}

				map = bankList.get(i);
				map.put("USERID", userId + "");
				result = bankCardDao.addBankCard(conn, map);
				if (result <= 0) {
					errorList.add("第" + (i + 3) + "行导入失败，银行卡数据错误");
					break;
				}
				// 汇付后台绑卡
				json = JSONObject.fromObject(ChinaPnRInterface
                        .bgBindCard("BgBindCard", json.getString("UsrCustId"), map.get("OpenBankId"), map.get("bankNo"),
                                map.get("OpenProvId"), map.get("OpenAreaId"), map.get("bankName")));
				if (json.getInt("RespCode") != 0) {// 失败
					bankCardDao.deleteBank(conn, result);
					errorList.add("第" + (i + 3) + "行导入失败，汇付绑定银行卡失败:" + json.getString("RespDesc"));
					break;
				}
				/*
				 * //担保机构 map = companyList.get(i); String companyName =
				 * map.get("COMPANYNAME"); Map<String,String>
				 * companyMap=guaranteeCompanyDao.getGuaranteeCompany(conn,
				 * companyName); if (companyMap==null) { guaranCompanyId=
				 * guaranteeCompanyDao.addGuaranteeCompany(conn, map);
				 * if(guaranCompanyId <= 0){
				 * errorList.add("第"+i+"行导入失败，借款数据错误"); break; } }else {
				 * guaranCompanyId=Convert.strToLong(companyMap.get("id"), -1);
				 * }
				 */
				map = borrowList.get(i);
				map.put("PUBLISHER", userId + "");
				if (!feeMap.isEmpty()) {
					map.put("feelog", feeMap.get("feelog"));
					map.put("feestate", feeMap.get("feestate"));
				}
				// 担保机构ID
				// map.put("GUARANCOMPANYID", guaranCompanyId+"");
				result = borrowDao.addBorrow(conn, map);
				if (result <= 0) {
					errorList.add("第" + (i + 3) + "行导入失败，借款数据错误");
					break;
				}
				borrowDao.updateNumber(conn, result);// 更新债权编号
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			errorList.add("系统出现异常");
		} finally {
			conn.commit();
			conn.close();
		}
		return errorList;

	}

	/**
	 * 导入前先进行的判断.如果身份证存在,不导入用户,该标添加到该用户名下
	 */
	private boolean importBefore(Connection conn, String idNo, String phone, List<String> error, Map<String, String> borrow, Map<String, String> feeMap, int row)
			throws SQLException, DataException {
		String cmd = "select userId from t_person where idNo='" + idNo + "' or cellPhone='" + phone + "'";
		Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, cmd));
		if (map == null || map.size() == 0)
			return false;
		long userId = Convert.strToLong(map.get("userId"), -1);
		if (userId <= 0) {
			return false;
		}
		borrow.put("PUBLISHER", userId + "");
		if (!feeMap.isEmpty()) {
			borrow.put("feelog", feeMap.get("feelog"));
			borrow.put("feestate", feeMap.get("feestate"));
		}
		long result = borrowDao.addBorrow(conn, borrow);
		borrowDao.updateNumber(conn, result);
		if (result <= 0) {
			error.add("第" + (row + 3) + "行导入失败，借款数据错误");
		}
		return true;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public void setWorkAuthDao(WorkAuthDao workAuthDao) {
		this.workAuthDao = workAuthDao;
	}

	public void setBankCardDao(BankCardDao bankCardDao) {
		this.bankCardDao = bankCardDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public Map<String, String> queryFamilyById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return workAuthDao.queryFamilyById(conn, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			conn.close();
		}
	}

	/**
	 * 合和年 个人详细信息 家庭信息
	 *
	 * @param familyName
	 * @param familyRelation
	 * @param familyAdd
	 * @param familyTel
	 * @param familyIdNo
	 * @param otherAdd
	 * @param otherIdNo
	 * @param otherName
	 * @param otherRelation
	 * @param otherTel
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long updateUserWorkData2(String familyName, String familyRelation, String familyAdd, String familyTel, String familyIdNo, String otherAdd, String otherIdNo,
			String otherName, String otherRelation, String otherTel, Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		conn.setAutoCommit(false);
		long result = -1L;
		try {
			Map<String, String> temp = workAuthDao.queryFamilyById(conn, id);
			if (temp == null || temp.size() == 0)
				result = workAuthDao.addWorkAuth(conn, familyName, familyRelation, familyAdd, familyTel, familyIdNo, otherAdd, otherIdNo, otherName, otherRelation, otherTel, id);
			else
				result = workAuthDao.updateWorkAuth(conn, id);
			conn.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 合和年更新用户个人信息 成功true 失败返回false
	 */
	public boolean updateUserBaseData2(String realName, String sex, String birthday, String highestEdu, String eduStartDay, String school, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro, Long registedPlaceCity, String address, Long userId, String idNo, String email) throws Exception {
		Connection conn = MySQL.getConnection();
		conn.setAutoCommit(false);
		long result1 = -1L;
		try {
			result1 = personDao.updatePerson(conn, realName, sex, birthday, highestEdu, eduStartDay, school, nativePlacePro, nativePlaceCity, registedPlacePro, registedPlaceCity,
					address, userId, idNo, email);
			result1 = personDao.updateUser(conn, userId, email);
			if (result1 > 0) {
				conn.commit();
				return true;
			}
			conn.rollback();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return false;
	}

	/**
	 * 新增安全问题
	 *
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 *
	 * @return Long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Long addQuestion(long userId, String question, String answer) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = userDao.addQuestion(conn, userId, question, answer);
			if (result > 0) {
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 查询安全问题
	 *
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 *
	 * @return Long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryQuestion(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = userDao.queryQuestion(conn, userId);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 修改安全问题
	 *
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 *
	 * @return Long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long updateQuestion(long userId, String question, String answer) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = userDao.updateQuestion(conn, userId, question, answer);
			if (result > 0) {
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 判断安全问题是否正确
	 */
	public Map<String, String> queryOldAnswer(long userId, String oldAnswer, String oldQuestion) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = userDao.queryOldAnswer(conn, userId, oldAnswer, oldQuestion);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 根据用户名查询手机是否存在
	 *
	 * @param phone
	 * @param userName
	 * @return
	 * @return Long [返回类型说明]
	 * @throws Exception
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> isExistPhone(String phone, String userName) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = userDao.isExistPhone(conn, phone, userName);
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
	 * 根据用户名查询用户信息
	 *
	 * @param phone
	 * @param userName
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 *
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryUserList(String userName) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = userDao.queryUserList(conn, userName);
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
	 * 根据用户ID--新增、修改邮箱
	 *
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 *
	 * @return Long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long updateEmail(long userId, String email) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = userDao.updateEmail(conn, userId, email);
			if (result > 0) {
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 验证邮箱的唯一性
	 *
	 * @param phone
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	public Map<String, String> queryIsEimal(String email) throws Exception {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryIsEimal(conn, email);
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
	 * 查询用户的基本信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryPersonBasecMessageById(long id) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = userDao.queryPersonBasecMessageById(conn, id);
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
	 * 汇付回调 更新用户 usrCustId和所有认证状态
	 */
	public long updateUser(long userId, long usrCustId, String email, String idNo, String realName) throws SQLException {
		Connection conn = null;
		long ret = -1L;
		try {
			conn = MySQL.getConnection();
			ret = userDao.updateUser(conn, userId, usrCustId, email);
			personDao.updatePerson(conn, userId, realName, idNo);
			workAuthDao.updateWorkAuthImport(conn, userId);
			userDao.updateMater(conn, userId);
			conn.commit();
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			log.error(e);
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return ret;
	}

	/**
	 * 导入彩生活数据
	 */
	public void importColorLife() {

	}

	public List<Map<String, Object>> queryUsers() throws SQLException {
		Connection conn = null;
		try {
			conn = MySQL.getConnection();
			return userDao.queryUsers(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}

	public List<Map<String, Object>> queryUsers(String uname) throws SQLException {
		Connection conn = null;
		try {
			conn = MySQL.getConnection();
			return userDao.queryUsers(conn, uname);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}

	public List<Map<String, Object>> queryUsersForImport(String uname) throws SQLException {
		Connection conn = null;
		try {
			conn = MySQL.getConnection();
			return userDao.queryUsersForImport(conn, uname);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}

	public void afterRegister(JSONObject json, long userId, String realName, String idNo) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			if (json.getInt("RespCode") != 0) {// 失败
				userDao.deleteUser(conn, userId + "", "");
			} else {
				long usrCustId = json.getLong("UsrCustId");
				userDao.updateUserImport(conn, userId, usrCustId);
				userDao.updateMater(conn, userId);
				personDao.updatePerson(conn, userId, realName, idNo);
				workAuthDao.updateWorkAuthImport(conn, userId);
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryUserBankById(long userId) throws SQLException {
		Connection conn = MySQL.getConnection();
		String command = "select u.id userId,u.usrCustId usrCustId,u.username username,ifnull(b.cardNo,'--') cardNo	 from t_user u ";
		command += " left join t_bankcard b on b.userId=u.id and cardStatus=1 and modifiedCardNo is null  where u.id=" + userId + " limit 1";
		try {
			return BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, command));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	public Map<String, String> queryByIdNo(String idNo) throws SQLException {
		Connection conn = MySQL.getConnection();
		String command = "select u.usrCustId as usrCustId,u.id as userId ";
		command += " from t_user u left join t_person p on p.userId=u.id  where p.idNo='" + idNo + "' limit 1";
		Map<String, String> map = null;
		try {
			map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, command));
			if (map != null && map.size() > 0) {
				if (Convert.strToInt(map.get("authStep"), 0) < 5) {
					MySQL.executeNonQuery(conn, "update t_user set authStep=5 where id=" + map.get("userId"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public String queryIdNO(Long id) throws SQLException {
		Connection conn = MySQL.getConnection();
		String command = "select idNo from t_person where userId=" + id;
		String idNo = "";
		try {
			Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, command));
			if (map != null && map.size() > 0)
				idNo = map.get("idNo");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return idNo;
	}

	public Map<String,String> queryIdNOsex(Long id) throws SQLException {
		Connection conn = MySQL.getConnection();
		String command = "select realName,idNo from t_person where userId=" + id;
		Map<String, String> map = null;
		try {
			 map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, command));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public long updateAutoPlan(long usrCustId, int status,String ordId) {
		Connection conn = MySQL.getConnection();
		StringBuilder sb = new StringBuilder("update t_automaticbid d , (select u.id from t_user u where u.usrCustId=");
		sb.append(usrCustId).append(") a set d.bidStatus=").append(status).append(", d.ordId = '" + ordId+"'");
		if (status == 2) {
			sb.append(" ,d.bidSetTime=now() ");
		}
		sb.append(" where d.userId=a.id");
		long ret = -1;
		try {
			ret = MySQL.executeNonQuery(conn, sb.toString());
			if (ret > 0)
				conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * 1.用户已注册过,map.ret返回0,map.msg返回已注册用户名;
	 * 2.未注册且可以注册(手机不重复),map.ret返回1,map.msg返回可注册用户名;
	 * 3.手机重复,返回map.ret返回-1,map.msg返回提示信息;
	 *
	 * 4.信息被篡改,返回 100
	 **/
	public Map<String, String> getColourlifeName(String userName, String telephone, String colorId, String check,String via) {
		Connection conn = MySQL.getConnection();
		Map<String, String> ret = new HashMap<String, String>();
		String retName;
		if (StringUtils.isNotBlank(userName)) {
			retName = userName.trim();
		}else{
			retName = via+"_"+telephone;
		}

		try {
			// 是否已经注册过.
			Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn,
                    "select count(1) num,username,colorcheck,id from t_user where colorid='" + colorId + "' and via='"
                            + via + "'"));
			if (map != null && Convert.strToInt(map.get("num"), -1) > 0) {
				/*String colorcheck = Convert.strToStr(map.get("colorcheck"), "");
				if(!check.equals(colorcheck)){
					ret.put("ret", "100");
					return ret;
				}*/
				ret.put("ret", "0");
				ret.put("msg", map.get("username"));
				ret.put("userId", map.get("id"));
				return ret;
			}
			// 手机是否重复.
			map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn,
                    "select count(1) num,id from t_user where mobilePhone='" + telephone + "'"));
			if (map != null && Convert.strToInt(map.get("num"), -1) > 0) {
				ret.put("ret", "-1");
				ret.put("msg", "手机号码已存在,请手动注册!");
                ret.put("userId", map.get("id"));
				return ret;
			}

			int start = 0;
			while (existUserName(conn, retName)) {
				retName = userName + appendStr(start++);
			}
			ret.put("ret", "1");
			ret.put("msg", retName);
		} catch (Exception e) {
			log.info(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	private String appendStr(int i) {
		StringBuilder ret = new StringBuilder();
		for (char ch : Integer.toString(i, 26).toCharArray()) {
			if (ch < 58) {
				ch += 49;
			} else {
				ch += 10;
			}
			ret.append(ch);
		}
		return ret.toString();
	}

	private boolean existUserName(Connection conn, String userName) {
		StringBuilder sb = new StringBuilder("select count(1) num from t_user where username='").append(userName).append("'");
		try {
			Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, sb.toString()));
			if (map == null || Convert.strToInt(map.get("num"), -1) == 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			log.info(e);
		}
		return false;
	}

	public void updateUserCheck(String userid, String check,String via, Long userId) {
		Connection conn = MySQL.getConnection();
		try {
			long ret = MySQL.executeNonQuery(conn, "update t_user set colorcheck='" + check + "',colorid='"+userid+"',via='"+via+"' where id=" + userId);
			if (ret > 0)
				conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/*
	 * 更新才生活推荐人id
	 */
	public void updateCshTjr(long userId, String tjr) {
		Connection conn = MySQL.getConnection();
		try {
			long ret = MySQL.executeNonQuery(conn, "update t_user set colorTjr='" + tjr + "' where id=" + userId);
			if (ret > 0)
				conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/*
	 * 更新用户userGroup
	 */
	public void updateUserGroup(long userId, int userGroup) {
		Connection conn = MySQL.getConnection();
		try {
			long ret = MySQL.executeNonQuery(conn, "update t_user set userGroup='" + userGroup + "' where id=" + userId);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 用户登录处理
	 *
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @param password
	 *            用户密码
	 * @param lastIP
	 *            最后登录ip
	 * @param loginType
	 *            登录类型，1用户名或邮箱登录，
	 * @return User
	 * @throws java.sql.SQLException
	 * @throws com.shove.data.DataException
	 */
	/*public User userLogin2(String userName, String password, String lastIP, String lastTime) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		password = StringEscapeUtils.escapeSql(password.trim());
		User user = null;
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_user_login(conn, ds, outParameterValues, userName, password, lastIP, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret <= 0) {
				if (ret == -5) {
					conn.close();
					user = new User();
					user.setEnable(2);
					return user;
				} else {
					return null;
				}
			} else {
				String userid = userDao.queryIdByUser(conn, userName).get("id");
				if (userid != null && !userid.equals("")) {
					Map<String, String> usermap = new HashMap<String, String>();
					usermap = userDao.queryUserById(conn, Convert.strToInt(userid, 0));
					Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();
					DataSet dataSet = sessionuser.open(conn, "", " id=" + Convert.strToLong(userid, -1l), "", -1, -1);
					Map<String, String> sessionmap = new HashMap<String, String>();
					sessionmap = BeanMapUtils.dataSetToMap(dataSet);
					if (sessionmap != null && sessionmap.size() > 0) {
						user = new User();
						user.setAuthStep(Convert.strToInt(sessionmap.get("authStep"), -1));
						user.setMobilePhone(Convert.strToStr(sessionmap.get("mobilePhone"), null));
						user.setSource(Convert.strToInt(sessionmap.get("source"), -1));
						user.setUsrCustId(Convert.strToLong(sessionmap.get("usrCustId"), -1));
						user.setCreditrating(Convert.strToInt(sessionmap.get("creditrating"), 0));
						user.setCreditLevel(Convert.strToInt(sessionmap.get("creditLevel"), 0));
						user.setEmail(Convert.strToStr(sessionmap.get("email"), null));
						user.setPassword(Convert.strToStr(sessionmap.get("password"), null));
						user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
						user.setRealName(Convert.strToStr(sessionmap.get("realName"), null));
						user.setKefuname(Convert.strToStr(sessionmap.get("kefuname"), null));
						user.setUserName(Convert.strToStr(sessionmap.get("username"), null));
						user.setIdNo(Convert.strToStr(sessionmap.get("idNo"), null));
						user.setVipStatus(Convert.strToInt(sessionmap.get("vipStatus"), -1));
						user.setHeadImage(Convert.strToStr(usermap.get("headImg"), null));
						user.setEnable(Convert.strToInt(usermap.get("enable"), -1));
						user.setPersonalHead(Convert.strToStr(sessionmap.get("personalHead"), null));
						user.setKefuid(Convert.strToInt(sessionmap.get("tukid"), -1));
						user.setCreditLimit(Convert.strToStr(sessionmap.get("usableCreditLimit"), null));
						user.setUsableSum(Convert.strToStr(usermap.get("usableSum"), null));
                        *//*
						 * 20140610 by 刘文韬
						 * 增加用户群组字段
						 *//*
                        user.setUserGroup(Convert.strToInt(sessionmap.get("userGroup"), 0));
                        user.setRefferee(Convert.strToStr(usermap.get("refferee"), "-1"));
					}
				}
			}
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
		Connection conn1 = MySQL.getConnection();
		operationLogDao.addOperationLog(conn1, "t_user", Convert.strToStr(user.getUserName(), ""), IConstants.INSERT,
				Convert.strToStr("", ""), 0, "用户通过彩生活登陆", 1);
		conn1.close();
		return user;
	}*/

	/**
	 * 通过彩之云唯一ID查询平台登录密码
	 * @param conn
	 * @param userid
	 * @return
	 */
	public String getUserPassword( String uid,String via) {
		Connection conn = connectionManager.getConnection();
		String password="";
		try {
			String cmd = "select password from t_user where colorid= '" + uid+"' and via='"+via+"'" ;
			Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, cmd));
			if (map == null || map.size() == 0)
				return "";
			password = map.get("password");
		} catch (Exception e) {
			log.info(e);
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return password;
	}

	/**
	 * 根据用户ID查询用户客户号
	 * @param conn
	 * @param userid
	 * @return
	 * @throws java.sql.SQLException
	 * @throws Exception
	 */
	public String queryUserCustId( long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		String usrCustId="";
		try {
			String cmd = "select usrCustId from t_user where id = " + userId ;
			Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, cmd));
			if (map == null || map.size() == 0)
				return "";
			usrCustId = map.get("usrCustId");
		} catch (Exception e) {
			log.info(e);
		}finally{
			conn.close();
		}
		return usrCustId;
	}

	/**
	 * 根据用户ordId查询自动投标计划表ordId
	 * @param conn
	 * @param userid
	 * @return
	 * @throws java.sql.SQLException
	 * @throws Exception
	 */
	public String queryAutoInvestOrdId( String  ordId) throws Exception {
		Connection conn = connectionManager.getConnection();
		String retordId="";
		try {
			String cmd = "select ordId from t_automaticbid where ordId = '" + ordId+"'" ;
			Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, cmd));
			if (map == null || map.size() == 0)
				return "";
			retordId = map.get("usrCustId");
		} catch (Exception e) {
			log.info(e);
		}finally{
			conn.close();
		}
		return retordId;
	}

	/**
	 * 判断用户是否为花样会会员，是的话就将userGroup设为2
	 *
	 */
	public void joinHyh(String name ,String idNo,long userId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			String cmd = "select * from t_hyn_user where name='" + name+"' and idNo='"+idNo+"'" ;
			Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, cmd));
			if (map != null && !map.isEmpty()){
				updateUserGroup(userId, 2);
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			conn.close();
		}
	}
	/**
	 * 保存用户彩生活信息
	 * @param userId
	 * @param colourId
	 * @param cid
	 * @param cname
	 * @param caddress
	 * @param tjrid
	 * @throws java.sql.SQLException
	 */
	public void saveColourInfo(long userId,int colourId,int cid,String cname,String caddress,int tjrid,String busGroupName) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			//
			String cmd = "replace t_colourlife_info (userId,colourId,cid,cname,caddress,tjrid,busGroupName) values("+userId+","+colourId+","+cid+",'"+cname+"','"+caddress+"',"+tjrid+",'"+busGroupName+"')" ;
			MySQL.executeNonQuery(conn, cmd);
		} catch (Exception e) {
			log.error(e);
		}finally{
			conn.close();
		}

	}
	/**
	 * 通过用户填写的推荐人 查询对应的用户id
	 * @param x
	 * @return
	 * @throws java.sql.SQLException
	 */
	public long findUserByIdOrPhone(String x) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			String cmd = "select id from t_user where id="+x+" or mobilePhone='"+x+"'";
			Map<String, String> map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, cmd));
			if (map != null && !map.isEmpty()){
				return Long.parseLong(map.get("id"));
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			conn.close();
		}
		return -1;
	}

	/**
	 * 保存用户填写的推荐人信息
	 * @param reffer
	 * @param userId
	 * @throws java.sql.SQLException
	 */
	public void saveUserReffer(String reffer,long userId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			String cmd = "update t_user set reffer = '"+reffer+"' where id="+userId  ;
			MySQL.executeNonQuery(conn, cmd);
		} catch (Exception e) {
			log.error(e);
		}finally{
			conn.close();
		}
	}
}



