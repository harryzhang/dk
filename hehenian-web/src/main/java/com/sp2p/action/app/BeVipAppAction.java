package com.sp2p.action.app;

import java.io.IOException;
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
import com.sp2p.constants.IAmountConstants;
import com.sp2p.service.AwardMoneyService;
import com.sp2p.service.BeVipService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.UserService;
import com.sp2p.task.JobTaskService;

public class BeVipAppAction extends BaseAppAction {
	public static Log log = LogFactory.getLog(BeVipAppAction.class);
	private static final long serialVersionUID = 7226324035784433720L;

	private BeVipService beVipService;
	private JobTaskService jobTaskService;
	private RecommendUserService recommendUserService;
	private AwardMoneyService awardMoneyService;
	private UserService userService;

	public String addVipInit() throws Exception {
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
			Map<String, String> userMap = beVipService
					.queryVipParamList(userId);
			if (userMap == null) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "发送成功");
			jsonMap.putAll(userMap);
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public String addVip() throws Exception {
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
			Map<String, Object> platformCostMap = this.getPlatformCost();

			Map<String, String> appInfoMap = this.getAppInfoMap();
			String servicePersonId = appInfoMap.get("serviceId");// 客服跟踪人
			if (StringUtils.isBlank(servicePersonId)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请选择客服");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String content = appInfoMap.get("content");

			String username = "";

			// 判断是否已经是vip
			Map<String, String> userMap = beVipService.queryUserById(userId);
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			int vipStatus = Convert.strToInt(userMap.get("vipStatus"), 1);
			username = userMap.get("username");
			if (vipStatus == 2) {// 1表示非vip会员
				jsonMap.put("error", "4");
				jsonMap.put("msg", "用户已经是VIP会员了");
				JSONUtils.printObject(jsonMap);
			}

			// Map<String, String> vipfree = null;
			// 扣除会费

			double vipFee = Convert.strToDouble(platformCostMap
					.get(IAmountConstants.VIP_FEE_RATE)
					+ "", 0);
			long result = beVipService.updataUserVipStatus(userId, 2, Convert
					.strToInt(servicePersonId, -1), content, vipFee + "",
					username);
			if (result > 0) {
				if ("3".equals(userMap.get("authStep"))) {
					jsonMap.put("authStep", "3");
				}
				// 奖励邀请人
				// 获取邀请人ID
				Long recommendId = -1L;
				Long reId = -1L;
				Map<String, String> maps = recommendUserService
						.getRecommendUserByuserId(userId);
				if (maps != null) {
					recommendId = Convert.strToLong(
							maps.get("recommendUserId"), -1L);
				}

				Map<String, String> recomends = recommendUserService
						.getRecommendUserByuserId(userId);
				if (recomends != null && recommendId > 0) {
					// ---add by houli
					Map<String, String> recommendMap = beVipService
							.queryUserById(recommendId);
					String recommendName = "";
					if (recommendMap != null && recommendMap.size() > 0) {
						recommendName = recommendMap.get("username");
					}
					// 获取邀请表中关联被邀请人的ID
					reId = Convert.strToLong(recomends.get("id"), -1L);
					/*
					 * Map<String, String>
					 * map=costManagerService.getCostManagerByType(3); Double
					 * money=Convert.strToDouble(map.get("number"),-1L);
					 */
					double money = Convert.strToDouble(platformCostMap
							.get(IAmountConstants.ORDINARY_FRIENDS_FEE)
							+ "", 0);
					// 为邀请人添加奖励
					Long moneyId = awardMoneyService.addAwardMoney(recommendId,
							money, username, userId, recommendName);
					// 奖励已邀请人关联
					recommendUserService.updateRecommendUser(reId, moneyId);
				}

				// 如果帐户上有足够的可用余额支付会员费用，那么立即进行扣费
				jobTaskService.beToVip(userId, platformCostMap);
				// ------end

				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				jsonMap.put("vipStatus", 2);
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "失败");
				JSONUtils.printObject(jsonMap);
			}

		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}
	public static void main(String[] args) {
	}

	public String queryServiceList() throws DataException, SQLException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> serviceList = userService.querykefylist();
			for (Map<String, Object> lists : serviceList) {
				String htmlStr = lists.get("remark").toString();
				htmlStr = htmlStr.replaceAll("<[^>]+>", "");
				htmlStr = htmlStr.replaceAll("[\\d+\\s*`~!@#$%^&*\\(\\)\\+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘：”“’_]", "");
				lists.put("remark", htmlStr);
			}
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			jsonMap.put("serviceList", serviceList);
			JSONUtils.printObject(jsonMap);

		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}
	

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setJobTaskService(JobTaskService jobTaskService) {
		this.jobTaskService = jobTaskService;
	}

	public void setRecommendUserService(
			RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public void setAwardMoneyService(AwardMoneyService awardMoneyService) {
		this.awardMoneyService = awardMoneyService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
