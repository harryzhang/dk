package com.sp2p.action.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BecomeToFinanceService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;

public class MessageAppAction extends BaseAppAction {
	private static final long serialVersionUID = -5564311857229967682L;

	public static Log log = LogFactory.getLog(MessageAppAction.class);

	private HomeInfoSettingService homeInfoSettingService;
	private UserService userService;

	public String querySendMsgList() throws IOException {
		return queryMsgList(1);
	}

	private String queryMsgList(int type) throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			Map<String, String> infoMap = this.getAppInfoMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			pageBean.setPageNum(infoMap.get("curPage"));
			homeInfoSettingService.queryMailList(pageBean, userId, type);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			jsonMap.put("pageBean", pageBean);
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public String queryReviceMsgList() throws IOException {
		return queryMsgList(2);
	}

	public String deleteMsg() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> appInfoMap = this.getAppInfoMap();

			String ids = appInfoMap.get("ids");
			if (StringUtils.isBlank(ids)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请选择需要删除的站内信");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String newIds = "";
			String[] allIds = ids.split(",");// 进行全选删除的时候获得多个id值
			if (allIds.length > 0) {
				long tempId = 0;
				for (String str : allIds) {
					tempId = Convert.strToLong(str, -1);
					newIds += "," + tempId;
				}
			}
			long result = -1;
			if (newIds.length() > 0) {
				ids = newIds.substring(1);
				result = homeInfoSettingService.deleteMails(ids,userId);
			}

			if (result > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "失败");
				JSONUtils.printObject(jsonMap);
			}

		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public String updateMsgReadStatus() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> appInfoMap = this.getAppInfoMap();

			String ids = appInfoMap.get("ids");
			if (StringUtils.isBlank(ids)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请选择需要标记的站内信");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			long result = -1;
			long type = Convert.strToLong(appInfoMap.get("type"), -1);
			if (type == -1) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请设置标志的类型");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String newIds = "";
			String[] allIds = ids.split(",");// 进行全选删除的时候获得多个id值
			if (allIds.length > 0) {
				long tempId = 0;
				for (String str : allIds) {
					tempId = Convert.strToLong(str, -1);
					newIds += "," + tempId;
				}
			}
			if (newIds.length() > 0) {
				ids = newIds.substring(1);
				if (type == IConstants.MAIL_READED) {// 标记为已读
					result = homeInfoSettingService.updateMails(ids,
							IConstants.MAIL_READED);
				} else if (type == IConstants.MAIL_UN_READ) {// 标记为未读
					result = homeInfoSettingService.updateMails(ids,
							IConstants.MAIL_UN_READ);
				}
			}
			// 站内信状态(1 默认未读 2 删除 3 已读)

			if (result > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "失败");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "5");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public String sendMsg() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> appInfoMap = this.getAppInfoMap();

			String receiver = appInfoMap.get("receiver");
			if (StringUtils.isBlank(receiver)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "接收人不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String title = appInfoMap.get("title");

			if (StringUtils.isBlank(title)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "标题不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String content = appInfoMap.get("content");

			if (StringUtils.isBlank(content)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "内容不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// 前台页面进行了判断，这里名称不可能为空
			Map<String, String> map = userService.queryIdByUser(receiver);
			if (map == null) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "收件人不存在");
				JSONUtils.printObject(jsonMap);
			}
			Long receiverId = -2L;
			// if(map == null || map.size() < 0){//到t_admin表中查数据
			// List<Map<String,Object>> lists =
			// adminService.queryAdminList(receiver, 1);
			// receiverId = Convert.strToLong(lists.get(0).get("id").toString(),
			// -1L);
			// }else{
			receiverId = Convert.strToLong(map.get("id"), -1L);
			// }

			if (receiverId < 0) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "收件人不存在");
				JSONUtils.printObject(jsonMap);
			}

			long result = -1;
			/**
			 * 如果是发给admin，系统管理员，则该邮件为系统邮件(如果发件人或者收件人为admin,则为系统消息)
			 */
			if (receiver.equalsIgnoreCase(IConstants.MAIL_SYS)) {// 新发送的邮件默认为未读
				// IConstants.MAIL_UN_READ
				result = homeInfoSettingService.addMail(userId, receiverId,
						title, content, IConstants.MAIL_UN_READ,
						IConstants.MALL_TYPE_SYS);
			} else {
				result = homeInfoSettingService.addMail(userId, receiverId,
						title, content, IConstants.MAIL_UN_READ,
						IConstants.MALL_TYPE_COMMON);
			}

			if (result > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "失败");
				JSONUtils.printObject(jsonMap);
			}

		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public String queryMsgDetail() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> appInfoMap = this.getAppInfoMap();

			long mailId = Convert.strToLong(appInfoMap.get("id"), -1);

			if (mailId == -1) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "该站内信详情不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Map<String, String> msgMap = homeInfoSettingService
					.queryEmailDetailById(mailId);
			if (msgMap == null) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "该站内信详情不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

		
			if (IConstants.MAIL_READED == Convert.strToInt(msgMap
					.get("mailStatus"), -1)) {// 如果是未读信息，则更新数据库，将状态改为已读
				homeInfoSettingService.updateMails(mailId + "",
						IConstants.MAIL_READED);

			}
			
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			jsonMap.putAll(msgMap);
			JSONUtils.printObject(jsonMap);
			
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
