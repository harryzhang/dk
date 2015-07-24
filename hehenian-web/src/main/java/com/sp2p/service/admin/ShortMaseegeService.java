package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;
import com.shove.util.SMSUtil;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.admin.ShortMassegeDao;
import com.sp2p.entity.Admin;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.SendMailService;
import com.sp2p.service.UserService;

public class ShortMaseegeService extends BaseService {

	private UserManageServic userManageServic;
	private SMSInterfaceService sMSInterfaceService;
	private AdminService adminService;
	private SendMailService sendMailService;
	private ShortMassegeDao shortMassegeDao;
	private UserService userService;

	private HomeInfoSettingService homeInfoSettingService;
	private Map<String, String> paramMap = new HashMap<String, String>();
	private boolean isSending = false;

	public static Log log = LogFactory.getLog(ShortMaseegeService.class);

	public String sendShortMaseege(Map<String, String> paramMap, Admin admin) throws Exception {
		int style = Convert.strToInt(paramMap.get("style"), -1);
		this.paramMap = paramMap;
		String title = Convert.strToStr(paramMap.get("title"), null);
		String content = Convert.strToStr(paramMap.get("content"), null);
		int status = Convert.strToInt(paramMap.get("status"), -1);
		int receiverType = Convert.strToInt(paramMap.get("users"), -1);
		String receiverId = Convert.strToStr(paramMap.get("receiverId"), "");
		Date date = getSendTime(status);
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = shortMassegeDao.addShortMassege(title, content, style, date, (status == 1) ? 3 : status, receiverType, receiverId, conn);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		if (result <= 0) {
			return "INPUT";
		}
		return "SUCCESS";
	}

	private List<Map<String, Object>> getUserType(int user, Map<String, Object> map) throws Exception {
		if (user == 1) { // 所有人
			return userManageServic.queryUserList();
		}
		if (user == 2) {
			return adminService.queryAdminList("", 1);// 管理员
		}
		if (user == 3) {// 批量发送
			String ids = map.get("receiverId").toString();
			ids = ids.substring(0, ids.lastIndexOf(":"));
			String[] str = ids.split(":");
			return userService.queryUserByIds(str);
		}
		return null;
	}

	private int getStyle(int style, List<Map<String, Object>> user, Map<String, Object> paramMap) throws Exception {
		if (style == 1) {// 邮件
			return sendEmail(estimateEmail(user), paramMap);

		}
		if (style == 2) {// 站内信
			return sendMail(user, paramMap);
		}
		if (style == 3) {// 短信
			return sendPhoneMassege(estimatePhone(user), paramMap);
		}
		return 0;
	}

	private Date getSendTime(int status) throws Exception {
		if (status == 1) {// 立即发送\
			isSending = true;
			return new Date();
		}
		if (status == 3) {// 定时发送
			String str = paramMap.get("date");
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		}
		// 默认存草稿
		return null;
	}

	/**
	 * 
	 * 发送邮件
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private int sendEmail(List<Map<String, Object>> user, Map<String, Object> paramMap) throws Exception {
		String title = paramMap.get("title").toString();
		String content = paramMap.get("content").toString();
		try {
			for (Map<String, Object> map : user) {
				sendMailService.sendUserGroupEmail(map.get("email").toString(), map.get("userName").toString(), title, content);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public SendMailService getSendMailService() {
		return sendMailService;
	}

	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}

	/**
	 * 发送手机短信
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private int sendPhoneMassege(List<Map<String, Object>> user, Map<String, Object> paramMap) throws Exception {
		String cellphones = "";
		String content = paramMap.get("content").toString();

		for (Map<String, Object> map : user) {
			String phone = map.get("telphone").toString();
			cellphones += phone + ",";
		}
		try {
			Map<String, String> map = sMSInterfaceService.getSMSById(1);
			StringBuffer buffer = new StringBuffer();
			buffer.append(cellphones);
			buffer.delete(buffer.lastIndexOf(","), buffer.lastIndexOf(",") + 1);
			String result = SMSUtil.sendSMS(map.get("Account"), map.get("Password"), content, buffer.toString(), null);
			if ("Sucess".equals(result)) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return 0;
	}

	/**
	 * 发送站内信
	 * 
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public int sendMail(List<Map<String, Object>> users, Map<String, Object> paramMap) throws Exception {
		String title = paramMap.get("title").toString();
		String content = paramMap.get("content").toString();
		if (isSending) {
			for (Map<String, Object> map : users) {
				homeInfoSettingService.addMail(-1L, (Long) map.get("id"), title, content, IConstants.MAIL_UN_READ, 2, IConstants.MALL_TYPE_SYS);
			}
		}
		return 1;
	}

	/**
	 * 去掉email为NULL或者""的用户
	 * 
	 * @param users
	 * @return
	 */
	private List<Map<String, Object>> estimateEmail(List<Map<String, Object>> users) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : users) {
			if (map.get("email") != null && !("").equals(map.get("email"))) {
				list.add(map);
			}

		}
		return list;
	}

	/**
	 * 去掉没有预留手机的用户
	 * 
	 * @param users
	 * @return
	 */
	private List<Map<String, Object>> estimatePhone(List<Map<String, Object>> users) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : users) {
			if (map.get("telphone") != null && !("").equals(map.get("telphone"))) {
				list.add(map);
			}
			if (map.get("mobilePhone") != null && !("").equals(map.get("mobilePhone"))) {
				map.put("telphone", map.get("mobilePhone"));
				list.add(map);
			}

		}
		return list;
	}

	private List<Map<String, Object>> estimateUser(List<Map<String, Object>> users) {
		if (users != null)
			for (Map<String, Object> map : users) {
				if (map.get("username") != null) {
					map.put("userName", map.get("username"));
				}

			}
		return users;
	}

	public void queryShortMassegePage(PageBean<Map<String, Object>> pageBean, int status, String beginTime, String endTime) throws Exception {
		StringBuffer condition = new StringBuffer(" and 1=1");
		if (status != 0) {
			condition.append(" and status =" + status);
		}
		if (StringUtils.isNotBlank(beginTime)) {
			condition.append(" and sendTime >= '");
			condition.append(StringEscapeUtils.escapeSql(beginTime));
			condition.append("'");
		}
		if (StringUtils.isNotBlank(endTime)) {
			condition.append(" and sendTime <= '");
			condition.append(StringEscapeUtils.escapeSql(endTime));
			condition.append("'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " t_short_massege  ", " * ", " order by id  ", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	public int jobTaskSend(Map<String, Object> map) throws Exception {
		Admin admin = new Admin();
		admin.setId(-1L);
		List<Map<String, Object>> users = getUserType((Integer) map.get("receiverType"), map);
		return getStyle((Integer) map.get("style"), estimateUser(users), map);

	}

	public void deleteShortMassege(String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			shortMassegeDao.deleteShortMassege(conn, ids);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Long sendMaseege(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -0L;
		try {
			result = shortMassegeDao.updateShortMassege(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public UserManageServic getUserManageServic() {
		return userManageServic;
	}

	public void setUserManageServic(UserManageServic userManageServic) {
		this.userManageServic = userManageServic;
	}

	public SMSInterfaceService getsMSInterfaceService() {
		return sMSInterfaceService;
	}

	public void setsMSInterfaceService(SMSInterfaceService sMSInterfaceService) {
		this.sMSInterfaceService = sMSInterfaceService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public ShortMassegeDao getShortMassegeDao() {
		return shortMassegeDao;
	}

	public void setShortMassegeDao(ShortMassegeDao shortMassegeDao) {
		this.shortMassegeDao = shortMassegeDao;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

}
