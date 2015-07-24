package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.service.FinanceService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.util.AmountUtil;

public class InvestAppAction extends BaseAppAction {
	private static final long serialVersionUID = -4281679738984629449L;
	public static Log log = LogFactory.getLog(InvestAppAction.class);

	private FinanceService financeService;
	private ShoveBorrowTypeService shoveBorrowTypeService;
	private BorrowManageService  borrowManageService;
	private UserService userService;

	public String queryInvestList() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			// 前台显示列表类型
			// String mode = appInfoMap.get("m");
			String title = appInfoMap.get("title");
			String paymentMode = appInfoMap.get("paymentMode");
			String purpose = appInfoMap.get("purpose");
			String deadline = appInfoMap.get("deadline");
			String reward = appInfoMap.get("reward");
			String arStart = appInfoMap.get("arStart");
			String arEnd = appInfoMap.get("arEnd");
			String type = appInfoMap.get("type");

			pageBean.setPageNum(appInfoMap.get("curPage"));

			pageBean.setPageSize(IConstants.PAGE_SIZE_10);
			String borrowWay = "";
			String borrowStatus = "";
			String borrowType = "";
			// 截取查询的类型，防止非常规操作
			if (StringUtils.isNotBlank(type)) {
				String[] types = type.split(",");
				if (types.length > 0) {
					for (int n = 0; n < types.length; n++) {
						// 是数字类型则添加到borrowType中
						if (StringUtils.isNumericSpace(types[n])) {
							borrowType += "," + types[n];
						}
					}
					if (StringUtils.isNotBlank(borrowType)) {
						borrowType = borrowType.substring(1, borrowType
								.length());
					}
				} else {
					if (StringUtils.isNumericSpace(type)) {
						borrowType = type;
					}
				}
			}
			// if ("1".equals(mode)) {
			// // 全部借款列表,显示1 等待资料 2 正在招标中 3 已满标
			// borrowStatus = "(1,2,3,4,5)";
			// // 查询条件中的借款方式
			// if (StringUtils.isNotBlank(borrowType)) {
			// borrowWay = "(" + borrowType + ")";
			// }
			// } else if ("2".equals(mode)) {
			// // 实地认证的借款
			// borrowWay = "(" + IConstants.BORROW_TYPE_FIELD_VISIT + ")";
			// } else if ("3".equals(mode)) {
			// // 信用认证的借款
			// borrowWay = "(" + IConstants.BORROW_TYPE_GENERAL + ")";
			// } else if ("4".equals(mode)) {
			// // 机构担保的借款
			// borrowWay = "(" + IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE
			// + ")";
			// } else if ("5".equals(mode)) {
			// // 最近成功的借款列表，显示4还款中 5 已还完
			// borrowStatus = "(4,5)";
			// } else {
			// borrowStatus = "(1,2,3,4,5)";
			// }
			borrowStatus = "(1,2,3,4,5)";
			financeService.queryBorrowByCondition(borrowStatus, borrowWay,
					title, paymentMode, purpose, deadline, reward, arStart,
					arEnd, IConstants.SORT_TYPE_DESC, pageBean,0);
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

	public String queryBorrowDetail() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long borrowId = Convert.strToLong(appInfoMap.get("borrowId"), -1);
			if (borrowId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// 借款详细
			Map<String, String> borrowDetailMap = financeService
					.queryBorrowDetailById(borrowId);
			// 查询借款信息得到借款时插入的平台收费标准
			Map<String, String> map = borrowManageService
					.queryBorrowInfo(borrowId);

			if (borrowDetailMap == null || borrowDetailMap.isEmpty()) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "借款不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			borrowDetailMap.put("borrowId", borrowDetailMap.get("id"));
			borrowDetailMap.remove("borrowInfo");
			borrowDetailMap.remove("id");

			double borrowSum = Convert.strToDouble(borrowDetailMap
					.get("borrowSum")
					+ "", 0);
			double annualRate = Convert.strToDouble(borrowDetailMap
					.get("annualRate")
					+ "", 0);
			int deadline = Convert.strToInt(borrowDetailMap.get("deadline")
					+ "", 0);
			int paymentMode = Convert.strToInt(borrowDetailMap
					.get("paymentMode")
					+ "", -1);
			int isDayThe = Convert.strToInt(borrowDetailMap.get("isDayThe")
					+ "", 1);
			double investAmount = borrowSum;
			String earnAmount = "";
			// if(borrowSum < investAmount){
			// investAmount = borrowSum;
			// }
			AmountUtil au = new AmountUtil();
			Map<String, String> earnMap = null;

			String feelog = Convert.strToStr(map.get("feelog"), "");
			Map<String, Double> feeMap = (Map<String, Double>) JSONObject
					.toBean(JSONObject.fromObject(feelog), HashMap.class);

			double costFee = Convert.strToDouble(feeMap
					.get(IAmountConstants.INVEST_FEE_RATE)
					+ "", 0);
			if (paymentMode == 1 || paymentMode == 4) {
				// 按月等额还款
				earnMap = au.earnCalculateMonth(investAmount, borrowSum,
						annualRate, deadline, 0, isDayThe, 2, costFee);
				earnAmount = earnMap.get("msg") + "";
			} else if (paymentMode == 2) {
				// 先息后本
				earnMap = au.earnCalculateSum(investAmount, borrowSum,
						annualRate, deadline, 0, isDayThe, 2);
				earnAmount = earnMap.get("msg") + "";
			} else if (paymentMode == 3) {
				// 秒还
				earnMap = au.earnSecondsSum(investAmount, borrowSum,
						annualRate, deadline, 0, 2);
				earnAmount = earnMap.get("msg") + "";
			}

			// 每次点击借款详情时新增浏览量
			financeService.addBrowseCount(borrowId);

			// 借款人资料
			Map<String, String> borrowUserMap = financeService
					.queryUserInfoById(borrowId);

			jsonMap.putAll(borrowDetailMap);
			jsonMap.put("earnAmount", earnAmount);
			jsonMap.put("username", borrowUserMap.get("username"));
			jsonMap.put("address", borrowUserMap.get("address"));
			jsonMap.put("credit", borrowUserMap.get("credit"));
			jsonMap.put("creditrating", borrowUserMap.get("creditrating"));
			jsonMap.put("createTime", borrowUserMap.get("createTime"));
			jsonMap.put("lastDate", borrowUserMap.get("lastDate"));
			jsonMap.put("nativePlace", borrowUserMap.get("nativePlace"));
			jsonMap.put("vipStatus", borrowUserMap.get("vipStatus"));
			jsonMap.put("personalHead", borrowUserMap.get("personalHead"));
			// 
			jsonMap.put("nativePlaceCity", borrowUserMap.get("nativePlaceCity"));
			jsonMap.put("nativePlacePro", borrowUserMap.get("nativePlacePro"));
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);

		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 借款人认证资料审核记录
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryBorrowAuthList() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long borrowId = Convert.strToLong(appInfoMap.get("borrowId"), -1);
			if (borrowId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			List<Map<String, Object>> authList = financeService
					.queryUserIdentifiedByid(borrowId);
			if (authList != null) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				jsonMap.put("authList", authList);
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "借款不存在");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 还款记录
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryRepayRecord() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long borrowId = Convert.strToLong(appInfoMap.get("borrowId"), -1);
			if (borrowId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			List<Map<String, Object>> repayList = financeService
					.queryRePayByid(borrowId);
			;
			if (repayList != null) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				jsonMap.put("repayList", repayList);
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "借款不存在");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 催收记录
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryCollcionRecord() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long borrowId = Convert.strToLong(appInfoMap.get("borrowId"), -1);
			if (borrowId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			List<Map<String, Object>> collectionList = financeService
					.queryCollectionByid(borrowId);
			if (collectionList != null) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				jsonMap.put("collectionList", collectionList);
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "借款不存在");
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

	/**
	 * 投资记录
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryInvestRecord() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long borrowId = Convert.strToLong(appInfoMap.get("borrowId"), -1);
			if (borrowId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> borrowDetailMap = financeService
					.queryBorrowDetailById(borrowId);
			if (borrowDetailMap == null || borrowDetailMap.size() == 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "借款不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			List<Map<String, Object>> investList = financeService
					.queryInvestByid(borrowId);
			if (investList != null) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				jsonMap.put("hasInvestAmount", borrowDetailMap
						.get("hasInvestAmount"));
				jsonMap
						.put("investAmount", borrowDetailMap
								.get("investAmount"));
				jsonMap.put("remainTime", borrowDetailMap.get("remainTime"));
				jsonMap.put("investList", investList);
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "借款不存在");
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

	/**
	 * 借款描述
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryBorrowInfo() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long borrowId = Convert.strToLong(appInfoMap.get("borrowId"), -1);
			if (borrowId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> borrowDetailMap = financeService
					.queryBorrowDetailById(borrowId);

			if (borrowDetailMap != null) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				jsonMap.put("borrowInfo", borrowDetailMap.get("borrowInfo"));
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "借款不存在");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 关注借款
	 * 
	 */
	public String focusOnBorrow() throws SQLException, IOException,
			DataException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long borrowId = Convert.strToLong(appInfoMap.get("borrowId"), -1);
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (borrowId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (userId == -1) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请先登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> map = financeService.hasFocusOn(borrowId,
					userId, IConstants.FOCUSON_BORROW);
			if (map != null && map.size() > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "你已关注过此借款");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			long returnId = financeService.addFocusOn(borrowId, userId,
					IConstants.FOCUSON_BORROW);
			if (returnId <= 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "关注失败");
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "关注成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @throws DataException
	 * @MethodName: focusOnUser
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午09:07:20
	 * @Return:
	 * @Descb: 我关注的用户
	 * @Throws:
	 */
	public String focusOnUser() throws SQLException, IOException, DataException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(appInfoMap.get("userId"), -1);
			long uid = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (uid == -1) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请先登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> map = financeService.hasFocusOn(userId, uid,
					IConstants.FOCUSON_USER);
			if (map != null && map.size() > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "你已关注过此人");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			long returnId = financeService.addFocusOn(userId, uid,
					IConstants.FOCUSON_USER);
			if (returnId <= 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "关注失败");
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "关注成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 
	 * 投标借款
	 * 
	 */
	public String addInvest() throws Exception {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long borrowId = Convert.strToLong(appInfoMap.get("borrowId"), -1);
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			double amountDouble = Convert.strToDouble(appInfoMap.get("amount"),
					0);

			if (borrowId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (userId == -1) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请先登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (amountDouble == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请输入投标金额");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Map<String, String> investDetailMap = financeService
					.queryBorrowInvest(borrowId);

			String usid = investDetailMap.get("userId") == null ? ""
					: investDetailMap.get("userId");
			long uid = Convert.strToLong(usid, -1);
			if (userId == uid) {
				// 不满足投标条件,返回
				jsonMap.put("error", "7");
				jsonMap.put("msg", "不能投标自己发布的借款");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			int status = Convert.strToInt(appInfoMap.get("subscribes"), 2);
			int num = 0;
			String dealPwd = appInfoMap.get("dealPwd");
			if (StringUtils.isBlank(dealPwd)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "请输入交易密码");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> userMap = userService.queryUserById(userId);
			if (userMap == null) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请先登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (!dealPwd.equals(userMap.get("dealpwd"))) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "交易密码输入错误");
				JSONUtils.printObject(jsonMap);
			}

			Map<String, String> result = financeService.addBorrowInvest(
					borrowId, userId, "", amountDouble, getBasePath(), userMap
							.get("username"), status, num);
			if ("".equals(result.get("ret_desc"))) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "投标成功");
				JSONUtils.printObject(jsonMap);

			} else {
				jsonMap.put("error", "6");
				jsonMap.put("msg", result);
				JSONUtils.printObject(jsonMap);
			}

		} catch (Exception e) {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	/**
	 * 借款人详细
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryBorrowerInfo() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long borrowId = Convert.strToLong(appInfoMap.get("borrowId"), -1);
			// 借款人资料

			if (borrowId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Map<String, String> borrowUserMap = financeService
					.queryUserInfoById(borrowId);
			Map<String, String> borrowRecordMap = financeService
					.queryBorrowRecord(borrowId);
			if (borrowUserMap != null) {
				jsonMap.putAll(borrowUserMap);
			}
			if (borrowRecordMap != null) {
				jsonMap.putAll(borrowRecordMap);
			}

			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);

		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public BorrowManageService getBorrowManageService() {
		return borrowManageService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

}
