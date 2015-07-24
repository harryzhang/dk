package com.sp2p.action.front;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IConstants;
import com.sp2p.service.GuaranteeService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;

public class UserIntegralAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(UserIntegralAction.class);
	private UserIntegralService userIntegralService;
	private GuaranteeService guaranteeService;
	private MyHomeService myHomeService;
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public void setGuaranteeService(GuaranteeService guaranteeService) {
		this.guaranteeService = guaranteeService;
	}

	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	}

	/**
	 * 前台用户查询用户的vip积分记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUserIntegral() throws Exception {
		Map<String, String> criditMap = null;
		Map<String, String> userMsg = null;
		List<Map<String, Object>> map = null;
		Long userId = -1L;
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user == null)
			return LOGIN;
		userId = user.getId();
		userMsg = guaranteeService.queryUserInformation(userId);
		// 用户认证信息
		map = guaranteeService.queryPerUserCreditforntHHN(userId);
		// 用户积分等级
		criditMap = guaranteeService.queryUserCriditPicture(userId);
		request().setAttribute("criditMap", criditMap);
		request().setAttribute("userMsg", userMsg);
		request().setAttribute("map", map);
		return SUCCESS;
		// =====统计还款分数
		// Map<String, String> UserBorrowmap1 = null;
		// Map<String, String> UserBorrowmap2 = null;
		// Map<String, String> UserBorrowmap3 = null;
		// Map<String, String> UserBorrowmap4 = null;
		// Map<String, String> UserBorrowmap5 = null;
		// Map<String, String> UserBorrowmap6 = null;
		// UserBorrowmap1 = guaranteeService.queryUserBorrowAndInver15(userId);
		// UserBorrowmap2 = guaranteeService.queryUserBorrowAndInver16(userId);
		// UserBorrowmap3 = guaranteeService.queryUserBorrowAndInver10(userId);
		// UserBorrowmap4 = guaranteeService.queryUserBorrowAndInver30(userId);
		// UserBorrowmap5 = guaranteeService.queryUserBorrowAndInver90(userId);
		// UserBorrowmap6=guaranteeService.queryUserBorrowAndInver90up(userId);
		// request().setAttribute("UserBorrowmap1", UserBorrowmap1);
		// request().setAttribute("UserBorrowmap2", UserBorrowmap2);
		// request().setAttribute("UserBorrowmap3", UserBorrowmap3);
		// request().setAttribute("UserBorrowmap4", UserBorrowmap4);
		// request().setAttribute("UserBorrowmap5", UserBorrowmap5);
		// request().setAttribute("UserBorrowmap6", UserBorrowmap6);
	}

	@SuppressWarnings("unchecked")
	public String queryUservip() throws SQLException, Exception {
		Map<String, String> userMap = null;
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		// 分页
		userIntegralService.queryUserIntegral(pageBean, user.getId(), IConstants.USER_INTERGRALTYPEVIP);
		Map<String, String> homeMap = myHomeService.queryHomeInfo(user.getId());
		request().setAttribute("homeMap", homeMap);
		userMap = userService.queryUserById(user.getId());
		request().setAttribute("userMap", userMap);
		return SUCCESS;
	}

}
