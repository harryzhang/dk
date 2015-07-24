package com.sp2p.action.front;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.sp2p.service.GuaranteeService;

@SuppressWarnings("serial")
public class GuaranteeAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(GuaranteeAction.class);
	private GuaranteeService guaranteeService;

	public void setGuaranteeService(GuaranteeService guaranteeService) {
		this.guaranteeService = guaranteeService;
	}

	public String guaranteeaindexMethod() {
		return SUCCESS;
	}

	/**
	 * 点击投资人显示投资人的详细信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String userMegMethod() throws Exception {
		Map<String, String> userMsg = null;
		Map<String,String> userVipPicture = null;
		Map<String,String> BorrowRecode = null;
		Map<String,String> inverseRecorde = null;
		Long userId = Convert.strToLong(request().getParameter("id"), -1);
		userVipPicture = guaranteeService.queryUserVipPicture(userId);
		userMsg = guaranteeService.queryUserInformation(userId);
		BorrowRecode = guaranteeService.queryUserBorrowRecode(userId);
		inverseRecorde = guaranteeService.queryUserInerseRecode(userId);
		request().setAttribute("userMsg", userMsg);
		request().setAttribute("userVipPicture", userVipPicture);
		request().setAttribute("BorrowRecode", BorrowRecode);
		request().setAttribute("inverseRecorde", inverseRecorde);
		return SUCCESS;
	}

	/**
	 * 查看用户的信用详情
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String userCriditMethod() throws Exception {
		Map<String, String> userMsg = null;
		Map<String, String> map = null;
		Map<String,String> criditMap = null;
		Map<String,String> userVipPicture = null;
		Map<String,String> BorrowRecode = null;
		Map<String,String> inverseRecorde = null;
		Map<String,String> UserBorrowmap1 = null;
		Map<String,String> UserBorrowmap2 = null;
		Map<String,String> UserBorrowmap3 = null;
		Map<String,String> UserBorrowmap4 = null;
		Map<String,String> UserBorrowmap5 = null;
		Map<String,String> UserBorrowmap6 = null;
		Long userId = Convert.strToLong(request().getParameter("id"), -1);
		if (userId == null || userId == -1) {
			return LOGIN;
		}
		criditMap = guaranteeService.queryUserCriditPicture(userId);
		userMsg = guaranteeService.queryUserInformation(userId);
		userVipPicture = guaranteeService.queryUserVipPicture(userId);
		map = guaranteeService.queryPerUserCreditfornt(userId);
		BorrowRecode = guaranteeService.queryUserBorrowRecode(userId);
		inverseRecorde = guaranteeService.queryUserInerseRecode(userId);
		//=====统计还款分数
		UserBorrowmap1 = guaranteeService.queryUserBorrowAndInver15(userId);
		UserBorrowmap2 = guaranteeService.queryUserBorrowAndInver16(userId);
		UserBorrowmap3 = guaranteeService.queryUserBorrowAndInver10(userId);
		UserBorrowmap4 = guaranteeService.queryUserBorrowAndInver30(userId);
		UserBorrowmap5 = guaranteeService.queryUserBorrowAndInver90(userId);
		UserBorrowmap6 = guaranteeService.queryUserBorrowAndInver90up(userId);
		request().setAttribute("UserBorrowmap1", UserBorrowmap1);
		request().setAttribute("UserBorrowmap2", UserBorrowmap2);
		request().setAttribute("UserBorrowmap3", UserBorrowmap3);
		request().setAttribute("UserBorrowmap4", UserBorrowmap4);
		request().setAttribute("UserBorrowmap5", UserBorrowmap5);
		request().setAttribute("UserBorrowmap6", UserBorrowmap6);
		//=========
		request().setAttribute("userMsg", userMsg);
		request().setAttribute("map", map);
		request().setAttribute("criditMap", criditMap);
		request().setAttribute("userVipPicture", userVipPicture);
		request().setAttribute("BorrowRecode", BorrowRecode);
		request().setAttribute("inverseRecorde", inverseRecorde);
		return SUCCESS;
	}

	/**
	 * 查看用户的合和年在线认证
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String userRenRenMethod() throws Exception {
		String flag = SqlInfusion.FilteSqlInfusion(request().getParameter("flag"));
		request().setAttribute("flag", flag);//区别从什么地方访问的
		return SUCCESS;
	}

	/**
	 * 查询用户的好友列表
	 * 
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFriendList() throws SQLException, Exception {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		request().setAttribute("id", id);
		String attention = SqlInfusion.FilteSqlInfusion(paramMap.get("attention"));
		request().setAttribute("attention", attention);
		if (StringUtils.isNotBlank(id)) {
			Long userId = Convert.strToLong(id, -1L);
			guaranteeService.queryUserFriends(pageBean, userId);
		}
		if(StringUtils.isNotBlank(attention)){
			return "attention";
		}
		return SUCCESS;
	}
	
	/**
	 * 删除关注好友
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws Exception
	 */
	public String deleteUserFriend()throws SQLException,DataException,Exception{
		Long userId=Convert.strToLong(paramMap.get("userId"), -1L);
		Long attentionUserId=Convert.strToLong(paramMap.get("attentionId"),-1L);
		guaranteeService.DeteleUserFriends(attentionUserId, userId);
		paramMap.put("attention", "attention");
		paramMap.put("id", userId.toString());
		return queryUserFriendList();
		
	}
	/**
	 * 查询用户的借款列表
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public String queryUserBorrowLists() throws SQLException, DataException{
		String userIdStr = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		if(StringUtils.isNotBlank(userIdStr)){
			request().setAttribute("id", userIdStr);
			Long userId = Convert.strToLong(userIdStr,-1L);
		//myborrowlist = guaranteeService.queryMyBorrowList(userId);
			guaranteeService.queryMyBorrowList(pageBean, userId);
		}
		return SUCCESS;
	}
	/**
	 * 查询用户的投资记录
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryMyborrowRecorde() throws SQLException, DataException{
		String userIdStr = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		if(StringUtils.isNotBlank(userIdStr)){
			request().setAttribute("id", userIdStr);
			Long userId = Convert.strToLong(userIdStr,-1L);
		//myborrowlist = guaranteeService.queryMyBorrowList(userId);
			guaranteeService.queryMyborrowRecorde(pageBean, userId);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询用户动态
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserRecore() throws SQLException, DataException{
			Long userId = Convert.strToLong(paramMap.get("id"),-1L);
			request().setAttribute("id", userId);
			guaranteeService.queryUserRecore(pageBean, userId);
		return SUCCESS;
	}
	/**
	 * 查询用户好友的动态
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public String queryfrendsRecore() throws SQLException, DataException{
		Long userId = Convert.strToLong(paramMap.get("id"),-1L);
		if(userId!=null&&userId!=-1){
			request().setAttribute("id",userId);
			guaranteeService.queryfrendsRecore(pageBean, userId);
		}
		return SUCCESS;
	}
}
