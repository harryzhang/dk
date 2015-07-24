package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.ServletUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.BorrowType;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BorrowService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.PlatformCostService;
import com.sp2p.service.admin.ShoveBorrowStyleService;
import com.sp2p.service.admin.ShoveBorrowTypeService;

public class BorrowAppAction extends BaseAppAction {
	public static Log log = LogFactory.getLog(BorrowAppAction.class);
	private static final long serialVersionUID = 7226324035784433720L;

	private BorrowService borrowService;
	private SelectedService selectedService;
	private ShoveBorrowStyleService shoveBorrowStyleService;
	private ShoveBorrowTypeService shoveBorrowTypeService;
	private UserService userService;
	private PlatformCostService platformCostService; // 平台收费参数

	public String addBorrowInit() throws IOException {
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

			int borrowWay = Convert.strToInt(appInfoMap.get("borrowWay"), -1);

			if (borrowWay < 0 || borrowWay > 5) {
				// 判断是否正常提交，并且范围在5种借款范围内
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请选择借款类型");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// if (IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)) {
			// session().setAttribute("borrowWay",
			// IConstants.BORROW_TYPE_NET_VALUE);
			// } else if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)) {
			// session().setAttribute("borrowWay",
			// IConstants.BORROW_TYPE_SECONDS);
			// }

			Map<String, String> borrowTypeMap = getBorrowTypeMap(borrowWay);
			// 判断是否开启
			if (borrowTypeMap == null || !"1".equals(borrowTypeMap.get("status"))) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "该种借款类型已关闭");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 设置是否开启密码（1开启）
			//
			// request().setAttribute("password_status",
			// borrowTypeMap.get("password_status"));

			// /---------cj____判断是否开启认购模式
			// int subscribe_status = Convert.strToInt(
			// borrowTypeMap.get("subscribe_status"),-1);
			// request().setAttribute("subscribeStatus", subscribe_status);

			Map<String, String> userMap = userService.queryUserById(userId);

			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 获取用户认证进行的步骤
			if ("1".equals(userMap.get("authStep"))) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "请先填写个人信息");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// -------------modify by houli
			/* 薪金贷，生意贷步骤 */
			if (IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay + "") || IConstants.BORROW_TYPE_SECONDS.equals(borrowWay + "")) {
				if ((IConstants.UNVIP_STATUS + "").equals(userMap.get("vipStatus"))) {// 没有成为VIP
					jsonMap.put("error", "6");
					jsonMap.put("msg", "请先申请VIP");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			} else {
				if ("2".equals(userMap.get("authStep"))) {
					// 工作信息认证步骤
					jsonMap.put("error", "7");
					jsonMap.put("msg", "请先填写工作信息");
					JSONUtils.printObject(jsonMap);
					return null;
				} else if ("3".equals(userMap.get("authStep"))) {
					// VIP申请认证步骤
					jsonMap.put("error", "6");
					jsonMap.put("msg", "请先申请VIP");
					JSONUtils.printObject(jsonMap);
					return null;
				} else if ("4".equals(userMap.get("authStep"))) {
					// 上传资料认证步骤
					jsonMap.put("error", "8");
					jsonMap.put("msg", "请先上传资料认证");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}

			// ---------------
			if ((IConstants.VIP_FAIL + "").equals(userMap.get("vipStatus"))) {
				jsonMap.put("error", "9");
				jsonMap.put("msg", "VIP已过期，请及时续费");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String nid = BorrowType.getNidByBorrowWay(borrowWay);
			// 设置是否开启奖励(1开启)
			jsonMap.put("awardStatus", borrowTypeMap.get("award_status"));
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			jsonMap.put("purposeList", selectedService.borrowPurpose());
			jsonMap.put("deadlineMonthList", shoveBorrowTypeService.queryDeadlineMonthListByNid(nid));
			jsonMap.put("maxAmountList", shoveBorrowTypeService.queryMaxAmountListByNid(nid));
			jsonMap.put("minAmountList", shoveBorrowTypeService.queryMinAmountListByNid(nid));
			jsonMap.put("raiseTermList", shoveBorrowTypeService.queryRaiseTermLisByNid(nid));
			jsonMap.put("repayWayList", shoveBorrowStyleService.queryShoveBorrowStyleByTypeNid(nid));
			jsonMap.put("sysImageList", selectedService.sysImageList());
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;

	}

	private Map<String, String> getBorrowTypeMap(int type) throws SQLException, DataException {
		String nid = BorrowType.getNidByBorrowWay(type);

		return shoveBorrowTypeService.queryShoveBorrowTypeByNid(nid);
	}

	public String addBorrow() throws IOException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
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

			// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
			Map<String, String> userMap = userService.queryUserById(userId);

			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

//			if (!(IConstants.VIP_STATUS + "").equals(userMap.get("vipStatus"))) {
//				jsonMap.put("error", "3");
//				jsonMap.put("msg", "请申请Vip");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}

			String title = appInfoMap.get("title");
			if (StringUtils.isBlank(title)) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "借款标题不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String imgPath = appInfoMap.get("imgPath");
			// if (StringUtils.isBlank(imgPath)) {
			// jsonMap.put("error", "7");
			// jsonMap.put("msg", "借款图片不能为空");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }
			int purpose = Convert.strToInt(appInfoMap.get("purpose"), -1);
			if (purpose <= 0) {
				jsonMap.put("error", "8");
				jsonMap.put("msg", "借款目的不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			int deadLine = Convert.strToInt(appInfoMap.get("deadLine"), -1);
			if (deadLine <= 0) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "借款期限不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// String deadDay = appInfoMap.get("deadDay");
			// String daythe = appInfoMap.get("daythe");
			int paymentMode = Convert.strToInt(appInfoMap.get("paymentMode"), -1);

			if (paymentMode <= 0) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "还款方式不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String isdaythe = appInfoMap.get("isDayThe");
			int daythe = IConstants.DAY_THE_1;
			if ("true".equals(isdaythe)) {
				daythe = IConstants.DAY_THE_2;
				// 为天标时默认就是按月分期还款
				paymentMode = 1;
			}

			double amount = Convert.strToDouble(appInfoMap.get("amount"), 0);

			if (amount <= 0) {
				jsonMap.put("error", "9");
				jsonMap.put("msg", "借款金额不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			double sum = Convert.strToDouble(appInfoMap.get("sum"), -1);
			if (sum > amount) {
				jsonMap.put("error", "10");
				jsonMap.put("msg", "奖励金额不能大于借款金额");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			double sumRate = Convert.strToDouble(appInfoMap.get("sumRate"), -1);

			double annualRate = Convert.strToDouble(appInfoMap.get("annualRate"), -1);

			if (annualRate <= 0) {
				jsonMap.put("error", "11");
				jsonMap.put("msg", "借款年利率不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			int minTenderedSumInt = 0;// 最小投标金额
			int maxTenderedSumInt = 0;// 最大投标金额
			// //是否启用认购模式
			int subscribe_status = Convert.strToInt(appInfoMap.get("subscribeStatus"), 2);
			// //认购金额
			String subscribe = Convert.strToStr(appInfoMap.get("subscribe"), "");
			int circulationNumber = 0;
			if (subscribe_status != 1) {
				String minTenderedSum = appInfoMap.get("minTenderedSum");
				minTenderedSumInt = Convert.strToInt(minTenderedSum, 0);
				String maxTenderedSum = appInfoMap.get("maxTenderedSum");
				maxTenderedSumInt = Convert.strToInt(maxTenderedSum, -1);
			} else {
				// 得到认购总分份数
				circulationNumber = Integer.parseInt(amount + "") / Integer.parseInt(subscribe);
			}
			int raiseTerm = Convert.strToInt(appInfoMap.get("raiseTerm"), -1);
			if (raiseTerm <= 0) {
				jsonMap.put("error", "12");
				jsonMap.put("msg", "筹标期限不能为空");
				JSONUtils.printObject(jsonMap);
				return null;

			}

			String excitationType = appInfoMap.get("excitationType");
			if (StringUtils.isNotBlank(excitationType)) {
				// 按借款金额比例奖励
				if (StringUtils.isNumericSpace(excitationType) && "3".equals(excitationType)) {
					sum = sumRate;
				}
			}
			int maxInvest = Convert.strToInt(appInfoMap.get("maxInvest"), 0);
			if(maxInvest!=0&&(maxInvest<2||maxInvest>49)){
				jsonMap.put("msg", "最大投标人数不少于2人不多于49人");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			int excitationTypeInt = Convert.strToInt(excitationType, -1);
			String detail = appInfoMap.get("detail");

			String remoteIP = ServletUtils.getRemortIp();

			int borrowWay = Convert.strToInt(appInfoMap.get("borrowWay"), -1);

			if (borrowWay <= 0 || borrowWay > 5) {
				jsonMap.put("error", "13");
				jsonMap.put("msg", "借款类型不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 查询标种类型
			Map<String, String> borrowTypeMap = this.getBorrowTypeMap(borrowWay);
			int number = Convert.strToInt(borrowTypeMap.get("subscribe_status"), -1);
			if (number != subscribe_status) {
				jsonMap.put("error", "13");
				jsonMap.put("msg", "该种借款类型已关闭");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 冻结保证金
			double frozenMargin = 0;
			if ("2".equals(userMap.get("vipStatus"))) {
				// vip冻结保证金
				frozenMargin = amount * Double.parseDouble(borrowTypeMap.get("vip_frost_scale")) / 100;
			} else {
				// 普通会员冻结保证金
				frozenMargin = amount * Double.parseDouble(borrowTypeMap.get("all_frost_scale")) / 100;
			}

			// 当借款为薪金贷时，需要验证所输入的金额小于净值的70%
			if (IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)) {
				Map<String, String> map = borrowService.queryBorrowTypeNetValueCondition(userId, amount + frozenMargin);
				if (!"1".equals(map.get("result"))) {
					jsonMap.put("error", "14");
					jsonMap.put("msg", "您发布的借款金额超过净值+保障金的70%");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			// 当借款类型为生意贷时,需要进行验证是否满足条件
			if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)) {
				Map<String, String> map = borrowService.queryBorrowTypeSecondsCondition(amount, annualRate, userId, getPlatformCost(), frozenMargin);
				if (map == null || map.size() == 0) {
					jsonMap.put("error", "15");
					jsonMap.put("msg", "您的可用金额不满足生意贷的发布条件");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			Map<String, String> maps = borrowService.queryBorrowFinMoney(frozenMargin, userId);
			if (maps == null || maps.size() == 0) {
				jsonMap.put("error", "16");
				jsonMap.put("msg", "您的可用金额不满足借款所需保障金的发布条件");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// ----modify by houli 生意贷调用的是另外一个方法，这里只需要判断薪金贷即可(否则这个中间应该用&&)
			if (!IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay + "")) {
				// if(!IConstants.BORROW_TYPE_SECONDS.equals(borrowWay) ||
				// !IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)){
				// 除了生意贷和薪金贷之外，其他要验证可用信用额度是否大于发布借款金额，同时发布成功后要扣除可用信用额度
				Map<String, String> map = borrowService.queryCreditLimit(amount, userId);
				if (map == null || map.size() == 0) {
					jsonMap.put("error", "17");
					jsonMap.put("msg", "您的可用信用额度不足以发布[" + amount + "]元的借款!");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}

			// ------add by houli
			// 进行借款的判断，如果已经发布了借款未满标通过，就不能再次发送（解决电脑卡机，造成数据重复提交）
			Long re = borrowService.queryBorrowStatus(userId);
			if (re < 0) {
				jsonMap.put("error", "18");
				jsonMap.put("msg", "你还有未审核通过的标的，暂时还不能再次发布");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// ---------------
			String investPWD = appInfoMap.get("investPWD");
			String hasPWD = appInfoMap.get("hasPWD");
			if ("true".equals(hasPWD)) {
				hasPWD = "1";
			}
			int hasPWDInt = Convert.strToInt(hasPWD, -1);
			// 得到所有平台所有收费标准
			List<Map<String, Object>> mapList = platformCostService.queryAllPlatformCost();

			Map<String, Object> mapfee = new HashMap<String, Object>();
			Map<String, Object> mapFeestate = new HashMap<String, Object>();
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
			String monerypurpose = appInfoMap.get("purpose");
			// frozenMargin 冻结保证金
			Map<String, String> result = borrowService.addBorrow(title, imgPath, borrowWay, purpose, deadLine, paymentMode, amount, annualRate, minTenderedSumInt,
					maxTenderedSumInt, raiseTerm, excitationTypeInt, sum, detail, 1, investPWD, hasPWDInt, remoteIP, userId, frozenMargin, daythe,
					getBasePath(), userMap.get("username"), Convert.strToDouble(subscribe, 0), circulationNumber, 0, subscribe_status,
					borrowTypeMap.get("identifier"), frozenMargin, json, jsonState,monerypurpose, user.getSource(),maxInvest,"","",0);
			if (Convert.strToInt(result.get("ret"), -1) < 0) {
				jsonMap.put("error", "19");
				jsonMap.put("msg", "发布借款失败");
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "您申请的借款已经提交管理人员进行审核，谢谢！");
				JSONUtils.printObject(jsonMap);
			}

		} catch (Exception e) {
			jsonMap.put("error", "20");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public String addCreditingInit() throws IOException {
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
			// Map<String, String> appInfoMap = this.getAppInfoMap();

			// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
			Map<String, String> userMap = userService.queryUserById(userId);
			if (userMap == null && userMap.size() == 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// 获取用户认证进行的步骤
			if ("1".equals(userMap.get("authStep"))) {
				// 工作信息认证步骤
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请先填写个人信息");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if ("2".equals(userMap.get("authStep"))) {
				// 工作信息认证步骤
				jsonMap.put("error", "4");
				jsonMap.put("msg", "请先填写工作信息");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if ("3".equals(userMap.get("authStep"))) {
				// VIP申请认证步骤
				jsonMap.put("error", "5");
				jsonMap.put("msg", "请先申请VIP");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if ("4".equals(userMap.get("authStep"))) {
				// 上传资料认证步骤
				// jsonMap.put("error", "6");
				// jsonMap.put("msg", "请先上传资料认证");
				// JSONUtils.printObject(jsonMap);
				// return null;
			}
			// 查询当前可用信用额度
			// Map<String, String> creditMap = borrowService
			// .queryCurrentCreditLimet(userId);

			// 查询能够再次申请信用额度的记录
			Map<String, String> applyMap = borrowService.queryCreditingApply(userId);
			if (applyMap != null && applyMap.size() > 0) {
				String applyTime = applyMap.get("applyTime") == null ? "" : applyMap.get("applyTime");
				String targetTime = applyMap.get("targetTime") == null ? "" : applyMap.get("targetTime");
				String msg = "您已在[" + applyTime + "]申请过信用额度,请于[" + targetTime + "] 以后再次申请.";
				jsonMap.put("error", "7");
				jsonMap.put("msg", msg);
				JSONUtils.printObject(jsonMap);
				return null;
			}

			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
			// 查询信用申请记录
			// borrowService.queryCreditingByCondition(userId, pageBean);

		} catch (Exception e) {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public String addCrediting() throws IOException {
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

			String applyDetail = appInfoMap.get("applyDetail");
			double applyAmount = Convert.strToDouble(appInfoMap.get("applyAmount"), -1);
			if (applyAmount <= 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请填写申请资金");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (applyAmount < 1 || applyAmount > 10000000) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "申请资金范围1到1千万金");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (applyDetail != null && applyDetail.length() > 500) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "申请详情不能超过500字符");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Long result = -1L;
			// 验证是否能够进行申请信用额度操作,防止通过URL提交的方式
			Map<String, String> applyMap = borrowService.queryCreditingApply(userId);
			if (applyMap != null && applyMap.size() > 0) {
				jsonMap.put("error", "4");
				String applyTime = applyMap.get("applyTime") == null ? "" : applyMap.get("applyTime");
				String targetTime = applyMap.get("targetTime") == null ? "" : applyMap.get("targetTime");
				String msg = "您已在[" + applyTime + "]申请过信用额度,请于[" + targetTime + "] 以后再次申请.";
				jsonMap.put("msg", msg);
				JSONUtils.printObject(jsonMap);
				return null;
			}

			result = borrowService.addCrediting(applyAmount, applyDetail, userId);
			if (result > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "6");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @MethodName: borrowConcernList
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午11:43:47
	 * @Return:
	 * @Descb: 我关注的借款列表
	 * @Throws:
	 */
	public String borrowConcernList() throws SQLException, DataException, IOException {
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

			pageBean.setPageNum(appInfoMap.get("curPage"));

			String title = appInfoMap.get("title");
			String publishTimeStart = appInfoMap.get("publishTimeStart");
			String publishTimeEnd = appInfoMap.get("publishTimeEnd");
			String deadline = appInfoMap.get("deadline");
			String borrowWay = appInfoMap.get("borrowWay");
			borrowService.queryBorrowConcernAppByCondition(title, publishTimeStart, publishTimeEnd, userId, pageBean, deadline, borrowWay);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: delBorrowConcern
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午12:18:30
	 * @Return:
	 * @Descb: 删除我关注的借款
	 * @Throws:
	 */
	public String delBorrowConcern() throws SQLException, IOException {
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
			String id = appInfoMap.get("id") == null ? "" : appInfoMap.get("id");
			long idLong = Convert.strToLong(id, -1L);
			borrowService.delBorrowConcern(idLong, userId);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public void setShoveBorrowStyleService(ShoveBorrowStyleService shoveBorrowStyleService) {
		this.shoveBorrowStyleService = shoveBorrowStyleService;
	}

	public void setShoveBorrowTypeService(ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}

}
