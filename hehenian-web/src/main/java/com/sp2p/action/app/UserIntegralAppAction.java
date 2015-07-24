package com.sp2p.action.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.GuaranteeService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;

public class UserIntegralAppAction extends BaseAppAction {
	public static Log log = LogFactory.getLog(UserIntegralAppAction.class);
	private UserIntegralService userIntegralService;
	private GuaranteeService  guaranteeService;
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
	 * @return
	 * @throws Exception 
	 */
	public String queryUserIntegral() throws Exception{
		Map<String,String> criditMap = null;
		Map<String,String> UserBorrowmap1 = null;
		Map<String,String> UserBorrowmap2 = null;
		Map<String,String> UserBorrowmap3 = null;
		Map<String,String> UserBorrowmap4 = null;
		Map<String,String> UserBorrowmap5 = null;
		Map<String,String> UserBorrowmap6 = null;
		Map<String, String> userMsg = null;
		Map<String, String> map = null;
		Map<String, String> creditmap = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			List<Integer> typeList = new ArrayList<Integer>();
			Long userId = -1L;
			AccountUserDo user = (AccountUserDo) session().getAttribute("user");
			if (user == null) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			userId = user.getId();
	
			userMsg = guaranteeService.queryUserInformation(userId);
			map = guaranteeService.queryPerUserCreditfornt(userId);
			//=====统计还款分数
			UserBorrowmap1 = guaranteeService.queryUserBorrowAndInver15(userId);
			UserBorrowmap2 = guaranteeService.queryUserBorrowAndInver16(userId);
			UserBorrowmap3 = guaranteeService.queryUserBorrowAndInver10(userId);
			UserBorrowmap4 = guaranteeService.queryUserBorrowAndInver30(userId);
			UserBorrowmap5 = guaranteeService.queryUserBorrowAndInver90(userId);
			UserBorrowmap6 = guaranteeService.queryUserBorrowAndInver90up(userId);
			criditMap = guaranteeService.queryUserCriditPicture(userId);
//			request().setAttribute("UserBorrowmap1", UserBorrowmap1);
//			request().setAttribute("UserBorrowmap2", UserBorrowmap2);
//			request().setAttribute("UserBorrowmap3", UserBorrowmap3);
//			request().setAttribute("UserBorrowmap4", UserBorrowmap4);
//			request().setAttribute("UserBorrowmap5", UserBorrowmap5);
//			request().setAttribute("UserBorrowmap6", UserBorrowmap6);
//			request().setAttribute("criditMap", criditMap);
//			request().setAttribute("userMsg", userMsg);
//			request().setAttribute("map", map);
			jsonMap.put("UserBorrowmap1", UserBorrowmap1);
			jsonMap.put("UserBorrowmap2", UserBorrowmap2);
			jsonMap.put("UserBorrowmap3", UserBorrowmap3);
			jsonMap.put("UserBorrowmap4", UserBorrowmap4);
			jsonMap.put("UserBorrowmap5", UserBorrowmap5);
			jsonMap.put("UserBorrowmap6", UserBorrowmap6);
			jsonMap.put("criditMap", criditMap);
			jsonMap.put("userMsg", userMsg);
			jsonMap.put("map", map);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
	
		}
		return null;
	}
	
	/**
	 * 查看用户VIP 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String queryUservip() throws SQLException, Exception{
		Map<String, String>  userMap = null;
		List<Map<String, Object>> IntegralvipMap = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
            AccountUserDo user = (AccountUserDo)session().getAttribute(IConstants.SESSION_USER);
		    if (user == null) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		    //分页
		    pageBean.setPageNum(infoMap.get("curPage"));
		    userIntegralService.queryUserIntegral(pageBean,user.getId(), IConstants.USER_INTERGRALTYPEVIP);
			Map<String, String> homeMap = myHomeService.queryHomeInfo(user.getId());
//			request().setAttribute("homeMap", homeMap);
			userMap = userService.queryUserById(user.getId());
//			request().setAttribute("userMap", userMap);
			jsonMap.put("homeMap", homeMap);
			jsonMap.put("userMap", userMap);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
	
		}
		return null;
	}
	

}
