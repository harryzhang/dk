package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AuctionDebtService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;

/**
 * 债权转让
 */
public class DebtAppAction extends BaseAppAction {

	public static Log log = LogFactory.getLog(DebtAppAction.class);
	private static final long serialVersionUID = 1L;

	private AssignmentDebtService assignmentDebtService;

	private AuctionDebtService auctionDebtService;
	
	private FinanceService financeService;

	private SelectedService selectedService;
	
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	/**
	 * 查询前台的债权转让
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String queryFrontAllDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			pageBean.setPageNum(appInfoMap.get("curPage"));
			long debtSum = Convert.strToLong(appInfoMap.get("debtSum"), -1);
			long auctionBasePrice = Convert.strToLong(appInfoMap.get("auctionBasePrice"), -1);
			long auctionMode = Convert.strToLong(appInfoMap.get("auctionMode"), -1);
			long isLate = Convert.strToLong(appInfoMap.get("isLate"), -1);
			long publishDays = Convert.strToLong(appInfoMap.get("publishDays"), -1);
			String borrowTitle = appInfoMap.get("borrowTitle");
			assignmentDebtService.queryAllDebt(borrowTitle,debtSum, auctionBasePrice,
					auctionMode, isLate, publishDays, "2,1,3", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();
			Date nowDate = new Date();
			if (list != null) {
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("actionTime");
					String remainDays = DateUtil.remainDateToString(nowDate,
							date);
					map.put("remainDays", remainDays);
				}
			}
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 查询前台的成功的债权转让
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String queryFrontSuccessDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			pageBean.setPageNum(appInfoMap.get("curPage"));
			long debtSum = Convert.strToLong(appInfoMap.get("debtSum"), -1);
			long auctionBasePrice = Convert.strToLong(appInfoMap.get("auctionBasePrice"), -1);
			long auctionMode = Convert.strToLong(appInfoMap.get("auctionMode"), -1);
			long isLate = Convert.strToLong(appInfoMap.get("isLate"), -1);
			long publishDays = Convert.strToLong(appInfoMap.get("publishDays"), -1);
			String borrowTitle = appInfoMap.get("borrowTitle");
			assignmentDebtService.queryAllDebt(borrowTitle,debtSum, auctionBasePrice,
					auctionMode, isLate, publishDays, "3", pageBean);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 查询债权债权详情
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String queryDebtDetail() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long id = Convert.strToLong(appInfoMap.get("id"), -1);
			Map<String, String> map = assignmentDebtService
					.getAssignmentDebt(id);
			if (map != null) {
				long viewCount = Convert.strToLong(map.get("viewCount"), 0);
				viewCount++;
				paramMap.putAll(map);
				long borrowId = Convert.strToLong(map.get("borrowId"), -1);
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				Map<String, String> borrow = financeService.queryBorrowDetailById(borrowId);
				
				Map<String, String> person = userService.queryPersonById(borrowerId);
				paramMap.put("nativePlacePro", "-1");
				paramMap.put("nativePlaceCity",  "-1");
				paramMap.put("borrowerId", borrowerId+"");
				paramMap.put("viewCount", viewCount + "");
				map = new HashMap<String, String>();
				map.put("viewCount", viewCount + "");
				assignmentDebtService.updateAssignmentDebt(id,-1, map);
				String publishTime = paramMap.get("publishTime");
				long auctionDays = Convert.strToLong(paramMap
						.get("auctionDays"), 0);
				String remainDays = DateUtil.remainDateToString(new Date(),
						DateUtil.dateAddDay(DateUtil.strToDate(publishTime),
								(int) auctionDays));
				paramMap.put("remainDays", remainDays);
				long debtId = Convert.strToLong(paramMap.get("id"), -1);
				paramMap.put("debtId", paramMap.get("id"));
				paramMap.putAll(auctionDebtService
						.queryAuctionMaxPriceAndCount(debtId));
				long alienatorId = Convert.strToLong(paramMap
						.get("alienatorId"), -1);
				Map<String, String> userMap = auctionDebtService
						.getUserAddressById(alienatorId);
				if(person!= null && person.size()>0){
					userMap.put("nativePlacePro", person.get("nativePlacePro"));
					userMap.put("nativePlaceCity", person.get("nativePlaceCity"));
				}
//				request().setAttribute("userMap", userMap);
				jsonMap.put("borrow", borrow);
				jsonMap.put("userMap", userMap);
				jsonMap.put("paramMap", paramMap);
			}
			
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 
	 * 添加债权留言
	 * 
	 */
	public String addDebtMSG() throws IOException, SQLException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if(StringUtils.isBlank(uid)){
				jsonMap.put("error", "4");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);	
			String id = appInfoMap.get("id") == null ? "" : appInfoMap.get("id");
			long idLong = Convert.strToLong(id, -1);
			String msgContent = appInfoMap.get("msg") == null ? "" : appInfoMap
					.get("msg");
			long returnId = -1;
			returnId = assignmentDebtService.addDebtMsg(idLong, userId,
					msgContent);
			if (returnId <= 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", IConstants.ACTION_FAILURE);
				JSONUtils.printObject(jsonMap);
			} else {
				// 添加成功返回值
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 留言初始化
	 * @throws IOException 
	 * 
	 */
	public String debtMSGInit() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id") == null ? "" : appInfoMap.get("id");
			long idLong = Convert.strToLong(id, -1);
			String pageNum = appInfoMap.get("curPage");
			if (StringUtils.isNotBlank(pageNum)) {
				pageBean.setPageNum(pageNum);
			}
			pageBean.setPageSize(IConstants.PAGE_SIZE_6);
			if (idLong == -1) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "没有债权ID");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			assignmentDebtService.queryDebtMSGBord(idLong, pageBean);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("id", id);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 竞拍初始化
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String auctingDebtInit() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if(StringUtils.isBlank(uid)){
				jsonMap.put("error", "4");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);	
			paramMap.put("debtId", appInfoMap.get("debtId"));
			Map<String, String> map = auctionDebtService.getUserById(userId);
			if (map != null) {
				paramMap.put("usableSum", map.get("usableSum"));
				paramMap.put("totalSum", String.format("%.2f", Convert
						.strToDouble(map.get("freezeSum"), 0.0)
						+ Convert.strToDouble(map.get("usableSum"), 0.0)));
			}
			Map<String,String> debtMap =  assignmentDebtService.getAssignmentDebt(Convert.strToLong(request("debtId"), -1));
			if(debtMap != null){
				paramMap.putAll(debtMap);
			}
			jsonMap.put("paramMap", paramMap);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 参与竞拍
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String addAuctingDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if(StringUtils.isBlank(uid)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);	
			long debtId = Convert.strToLong(appInfoMap.get("debtId"), -1);
			Map<String, String> debtMap = assignmentDebtService
					.getAssignmentDebt(debtId);
			Map<String, String> userMap = auctionDebtService
					.getUserById(userId);
			
			if (debtMap != null && userMap != null) {
				if (debtMap.get("alienatorId").equals(userId + "")) {
					jsonMap.put("error", "2");
					jsonMap.put("msg", " 不能投自己转让的的债权");
					JSONUtils.printObject(jsonMap); // 不能投自己转让的的债权
					return null;
				}
				long borrowId = Convert.strToLong(debtMap.get("borrowId"), -1);
				String pwd = appInfoMap.get("pwd");
				pwd = Encrypt.MD5(pwd).toLowerCase();
				if (!pwd.equals(userMap.get("dealpwd").toLowerCase())) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", " 交易密码不对");
					JSONUtils.printObject(jsonMap); // 交易密码不对
					return null;
				}
				Map<String,String> aucctionMap = auctionDebtService.getAuctionDebt(debtId,userId);
				double oldAuctionPrice = 0.0;
				if(aucctionMap != null){
					oldAuctionPrice = Convert.strToDouble(aucctionMap.get("auctionPrice"),0.0);
				}
				
				double auctionPrice = Convert.strToDouble(appInfoMap
						.get("auctionPrice"), 0.0);
				double usableSum = Convert.strToDouble(
						userMap.get("usableSum"), 0.0);
				if (usableSum < (auctionPrice-oldAuctionPrice)) {
					jsonMap.put("error", "4");
					jsonMap.put("msg", "可用余额不足");
					JSONUtils.printObject(jsonMap); // 可用余额不足
					return null;
				}

				double debtSum = Convert.strToDouble(debtMap.get("debtSum"),
						0.0);
				if (debtSum < auctionPrice) {
					jsonMap.put("error", "5");
					jsonMap.put("msg", "大于债权金额");
					JSONUtils.printObject(jsonMap); // 大于债权金额
					return null;
				}

				double auctionBasePrice = Convert.strToDouble(debtMap
						.get("auctionBasePrice"), 0.0);
				if (auctionBasePrice > auctionPrice) {
					jsonMap.put("error", "6");
					jsonMap.put("msg", "小于最小竞拍金额");
					JSONUtils.printObject(jsonMap); // 小于最小竞拍金额
					return null;
				}
				
				long actionTimes = auctionDebtService.queryAuctionUserTimes(debtId, userId);
				if (actionTimes >= 2) {
					jsonMap.put("error", "7");
					jsonMap.put("msg", "该债权您只能竞拍两次");
					JSONUtils.printObject(jsonMap); // 该债权您只能竞拍两次
					return null;
				}
				
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				if(borrowerId==userId){
					jsonMap.put("error", "8");
					jsonMap.put("msg", "借款者不能竞拍该债权");
					JSONUtils.printObject(jsonMap); // 借款者不能竞拍该债权
					return null;
				}
				
				if(oldAuctionPrice >= auctionPrice){
					jsonMap.put("error", "9");
					jsonMap.put("msg", "第二次竞拍金额应大于第一次竞拍金额");
					JSONUtils.printObject(jsonMap); // 第二次竞拍金额应大于第一次竞拍金额
					return null;
				}
				double auctionHighPrice = Convert.strToDouble(debtMap.get("auctionHighPrice"), -1);
				if(auctionHighPrice != -1 && auctionHighPrice > auctionPrice){
					jsonMap.put("error", "10");
					jsonMap.put("msg", "竞拍金额要大于最高竞拍金额");
					JSONUtils.printObject(jsonMap); // 竞拍金额要大于最高竞拍金额
					return null;
				}
				if(!"2".equals(debtMap.get("debtStatus"))){
					jsonMap.put("error", "11");
					jsonMap.put("msg", "竞拍失败");
					JSONUtils.printObject(jsonMap); //竞拍失败
					return null;
				}
				paramMap.put("userId", userId + "");
				paramMap.put("auctionTime", DateUtil.dateToString(new Date()));
				paramMap.put("userName", ((AccountUserDo)session().getAttribute(IConstants.SESSION_USER)).getUsername());
				paramMap.put("basePath", this.getBasePath());
				long result = -1;
				if (actionTimes==1) {
					paramMap.put("oldAuctionPrice",oldAuctionPrice+"");
					result = auctionDebtService.upadteAuctionDebt(paramMap,Convert.strToLong(aucctionMap.get("id"), -1),userMap);
				}else{
					result = auctionDebtService.addAuctionDebt(paramMap, userMap);
				}
				
				if(result != -1){
					jsonMap.put("error", "-1");
					jsonMap.put("msg", "成功");
					JSONUtils.printObject(jsonMap);
				}else{
					jsonMap.put("error", "11");
					jsonMap.put("msg", "竞拍失败");
					JSONUtils.printObject(jsonMap);
				}
				
			}
		} catch (Exception e) {
			jsonMap.put("error", "12");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 查询竞拍记录
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String queryAcutionRecordInfo() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long id = Convert.strToLong(appInfoMap.get("id"), -1);
			List<Map<String, Object>> list = auctionDebtService
					.queryAuctionDebtByDebtId(id);
			Map<String, String> debt = assignmentDebtService.getAssignmentDebt(id);
			if(list == null && list.size() <= 0){
				jsonMap.put("error", "2");
				jsonMap.put("msg", "没有竞拍记录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			jsonMap.put("auctionHighPrice", debt.get("auctionHighPrice"));
			jsonMap.put("auctionDays", debt.get("auctionDays"));
			jsonMap.put("publishTime", debt.get("publishTime"));
			String remainDays = DateUtil.remainDateToString(new Date(),
					DateUtil.dateAddDay(DateUtil.strToDate(debt.get("publishTime")),
							Convert.strToInt(debt.get("auctionDays"), 0)));
			jsonMap.put("remainDays", remainDays);
			jsonMap.put("list", list);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

}
