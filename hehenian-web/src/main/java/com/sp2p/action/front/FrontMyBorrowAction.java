package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.BorrowType;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BorrowService;
import com.sp2p.service.DataApproveService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.PlatformCostService;
import com.sp2p.service.admin.ShoveBorrowStyleService;
import com.sp2p.service.admin.ShoveBorrowTypeService;

/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的借款控制层
 */
public class FrontMyBorrowAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyBorrowAction.class);
	private static final long serialVersionUID = 1L;

	// ------------add by houli
	private String from;
	private String btype;
	// -----------

	private BorrowService borrowService;
	private SelectedService selectedService;
	private DataApproveService dataApproveService;
	private ShoveBorrowTypeService shoveBorrowTypeService;
	private ShoveBorrowStyleService shoveBorrowStyleService;
	private PlatformCostService platformCostService; // 平台收费参数
	private UserService userService;

	// 下拉列表
	private List<Map<String, Object>> borrowPurposeList;
	private List<Map<String, Object>> borrowDeadlineMonthList;
	private List<Map<String, Object>> borrowDeadlineDayList;
	private List<Map<String, Object>> borrowMinAmountList;
	private List<Map<String, Object>> borrowMaxAmountList;
	private List<Map<String, Object>> borrowRaiseTermList;
	private List<Map<String, Object>> borrowRepayWayList;
	private List<Map<String, Object>> sysImageList;
	private Map<String, String> counterList;
	private Map<String, String> instiList;

	@Override
	public String doDefault() throws Exception {
		return super.doDefault();
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: borrowInit
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午07:05:56
	 * @Return:
	 * @Descb: 我要借款初始化
	 * @Throws:
	 */
	public String borrowInit() throws SQLException, DataException {
		request().setAttribute("credit", this.getUser().getCreditrating());
//		request().setAttribute("creditLevel", this.getUser().getCreditLevel());
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: creditingInit
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-7 下午05:55:26
	 * @Return:
	 * @Descb: 申请信用额度
	 * @Throws:
	 */
	public String creditingInit() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String pageNum = SqlInfusion.FilteSqlInfusion(request().getParameter("curPage") == null ? "" : request().getParameter("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		if (user != null) {

			// 设置为申请信用额度认证模式
			session().setAttribute("stepMode", "2");

			// 获取用户认证进行的步骤
			if (user.getAuthStep() == 1) {
				// 个人信息认证步骤
				return "querBaseData";
			} else if (user.getAuthStep() == 2) {
				// 工作信息认证步骤
				return "querWorkData";
			} else if (user.getAuthStep() == 3) {
				// 上传资料认证步骤
				return "userPassData";
			}
			// 查询当前可用信用额度
			Map<String, String> creditMap = borrowService.queryCurrentCreditLimet(user.getId());
			request().setAttribute("creditMap", creditMap);
			// 查询信用申请记录
			borrowService.queryCreditingByCondition(user.getId(), pageBean);
			// 清空paramMap
			paramMap = null;
		} else {
			returnParentUrl("", "index.jsp");
		}
		return "success";
	}

	/**
	 * 初始化借款
	 */
	public String addBorrowInit() throws Exception {
		// 清空paramMap
		paramMap = null;
		String t = SqlInfusion.FilteSqlInfusion(request().getParameter("t") == null ? "-1" : request().getParameter("t"));
		session().setAttribute("t", t);
		int tInt = Convert.strToInt(t, -1);
        AccountUserDo user = this.getUser();
		
		//根据登录ID查询所在地区
        Map<String, String> mapCity = borrowService.queryCity(user.getId());
       if(null != mapCity && mapCity.size() > 0){
           request().setAttribute("mapCity", mapCity.get("address"));
       }
		
		//判断是否完善个人资料
		/*if (user.getId() == null || user.getId() <= 0 || user.getRealName() == null || user.getRealName() == "") {
            sendHtml("<script>alert('您还未完善个人资料，暂时不能发布借款！请进入会员中心进行完善。');parent.location.href='" + request().getContextPath() + "/home.do';</script>");
            return null;
        }*/
		
		// 判断用户是否在汇付注册过
		if (user.getUsrCustId() == null || user.getUsrCustId() <= 0) {
			sendHtml("<script>alert('您还未注册汇付账户，暂时不能发布借款！请进入会员中心进行注册。');parent.location.href='" + request().getContextPath() + "/home.do';</script>");
			return null;
		}

		// 判断用户是否绑定银行卡
        if (mapCity.get("cardNo") == null || mapCity.get("cardNo") == "") {
            sendHtml("<script>alert('您还未绑定银行卡，暂时不能发布借款！请进入会员中心进行注册。');parent.location.href='" + request().getContextPath() + "/bankInfoSetInit.do';</script>");
            return null;
        }
        
		btype = t;
		if (tInt < 0 || tInt > 3) {
			// 判断是否正常提交，并且范围在6种借款范围内
			return "borrowinit";
		}

		/** // ----这里设置borrowway的值 否则如果在这里进行资料填写跳转后，再点击发布借款无法获得值，不能进行正确跳转
		session().setAttribute("borrowWay", "0");
		Map<String, String> borrowTypeMap = null;
		if (IConstants.BORROW_TYPE_NET_VALUE.equals(t)) {
			session().setAttribute("borrowWay", IConstants.BORROW_TYPE_NET_VALUE);
		} else if (IConstants.BORROW_TYPE_SECONDS.equals(t)) {
			session().setAttribute("borrowWay", IConstants.BORROW_TYPE_SECONDS);
		} else if (IConstants.BORROW_TYPE_GENERAL.equals(t)) {
			session().setAttribute("borrowWay", IConstants.BORROW_TYPE_GENERAL);
		}

		if (tInt != 0) {
			borrowTypeMap = getBorrowTypeMap(tInt + "");
			// 判断是否开启
			if (borrowTypeMap == null || !"1".equals(borrowTypeMap.get("status"))) {
				return "borrowinit";
			}
			// 设置是否开启密码（1开启）
			request().setAttribute("password_status", borrowTypeMap.get("password_status"));
			// 设置是否开启奖励(1开启)
			request().setAttribute("award_status", borrowTypeMap.get("award_status"));
			String validate = borrowTypeMap.get("validate");
			if ("0".equals(validate)) {
				request().setAttribute("validateDay", "1");
			}
			// /---------cj____判断是否开启认购模式
			int subscribe_status = Convert.strToInt(borrowTypeMap.get("subscribe_status"), -1);
			request().setAttribute("subscribeStatus", subscribe_status);
		}**/

		// 设置为借款认证模式
		session().setAttribute("stepMode", "1");
		// 获取用户认证进行的步骤
		if (user.getAuthStep() == 1) {
			if (IConstants.BORROW_TYPE_NET_VALUE.equals(t) || IConstants.BORROW_TYPE_SECONDS.equals(t))
				from = "1";
			// 个人信息认证步骤
			return "querBaseData";
		}

		/* 生意贷步骤 无需认证
		if (!IConstants.BORROW_TYPE_SECONDS.equals(t)) {
			from = "";
			if (user.getAuthStep() == 2) {
				// 工作信息认证步骤
				return "querWorkData";
			} else if (user.getAuthStep() == 3) {
				// 上传资料认证步骤,顺便小小的同步一下认证步骤
				int step = Convert.strToInt(userService.queryUserById(user.getId()).get("authStep"), 3);
				if (step == 3)
					return "userPassData";
				user.setAuthStep(step);
			}
		} */
		// -----------add houli 判断是否还有满标未审核通过的标的，如果存在，则不能继续发布借款
//		Long result = borrowService.queryBorrowStatus(user.getId());
		
//		if (result < 0) {
//			Long aa = borrowService.queryBaseApprove(user.getId(), 3);
//			if (aa < 0) {
//				sendHtml("<script>alert('您还有等待审核的标的，暂时还不能再次发布，如有疑问，请致电合和年在线！');parent.location.href='" + request().getContextPath()
//						+ "/borrow.do';</script>");
//			} else {
//				if (tInt == 3) {// 信用标
//					Long bb = borrowService.queryBaseFiveApprove(user.getId());
//					if (bb < 0) {
//						sendHtml("<script>alert('您还有等待等待审核的标的，暂时还不能再次发布，如有疑问，请致电合和年在线！');parent.location.href='" + request().getContextPath()
//								+ "/borrow.do';</script>");
//						return null;
//					}
//				}
//				sendHtml("<script>alert('您还有未审核通过的标的，暂时还不能再次发布！');parent.location.href='" + request().getContextPath() + "/borrow.do';</script>");
//			}
//			return null;
//		}

		if (IConstants.BORROW_OTHER.equals(t)) {
			// 其他借款
			return borrowOther();
		} else if (IConstants.BORROW_TYPE_NET_VALUE.equals(t)) {
			// 薪金贷（可用+待收-待还）*0.7 > 1万
			return borrowTypeNet();
		} else if (IConstants.BORROW_TYPE_GENERAL.equals(t)) {
			// 业主贷
			return borrowGeneral();
		} else if (IConstants.BORROW_TYPE_SECONDS.equals(t)) {
			// 生意贷
			return borrowColorLife();
		} else {
			session().removeAttribute("typeName");
			session().removeAttribute("borrowWay");
			session().removeAttribute("borrowWay1");
			return "borrowinit";
		}
	}

	private Map<String, String> getBorrowTypeMap(String type) throws SQLException, DataException {
		String nid = "";
		if (IConstants.BORROW_TYPE_NET_VALUE.equals(type)) {
			// 薪金贷
			nid = BorrowType.WORTH.getValue();
		} else if (IConstants.BORROW_TYPE_SECONDS.equals(type)) {
			// 彩生活
			nid = BorrowType.SECONDS.getValue();
		} else if (IConstants.BORROW_TYPE_GENERAL.equals(type)) {
			// 业主贷
			nid = BorrowType.ORDINARY.getValue();
		}
		session().setAttribute("nid", nid);
		return shoveBorrowTypeService.queryShoveBorrowTypeByNid(nid);
	}

	public String borrowOther() {
		// 其他借款
		session().removeAttribute("typeName");
		// --------modify by houli
		// session().removeAttribute("borrowWay");
		Map<String, String> map;
		try {
			map = shoveBorrowTypeService.queryShoveBorrowTypeByNid(BorrowType.ORDINARY.getValue());
			if (map != null) {
				request().setAttribute("minRate", map.get("apr_first"));
				request().setAttribute("maxRate", map.get("apr_end"));
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
		}

		session().setAttribute("borrowWay", "0");
		session().removeAttribute("borrowWay1");
		// ----------------
		return "borrowtype";
	}

	public String borrowTypeNet() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		// 薪金贷（可用+待收-待还）*0.7 > 1万
		session().setAttribute("typeName", "薪金贷");
		session().setAttribute("borrowWay", IConstants.BORROW_TYPE_NET_VALUE);
		// ----add by houli 因为跳转发生变化，所以需要新增一个变量
		session().setAttribute("borrowWay1", IConstants.BORROW_TYPE_NET_VALUE);
		// ----
		Map<String, String> map = borrowService.queryBorrowTypeNetValueCondition(user.getId(), 0);
		String result = map.get("result") == null ? "" : map.get("result");
		if (!"1".equals(result)) {

			// 判定不通过也要移除borrowWay
			session().removeAttribute("borrowWay");
			getOut().print("<script>alert('您的资产净值小于1万元,不能发布薪金贷!');parent.location.href='" + request().getContextPath() + "/borrow.do';</script>");
			return null;
		}
		return SUCCESS;
	}

	public String borrowTypeSeconds() {
		session().setAttribute("typeName", "生意贷");
		session().setAttribute("borrowWay", IConstants.BORROW_TYPE_SECONDS);

		session().setAttribute("borrowWay1", IConstants.BORROW_TYPE_SECONDS);

		request().setAttribute("hasPWD", "1");
		// 清空paramMap
		paramMap = null;
		return "seconds";
	}

	public String borrowGeneral() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		session().setAttribute("typeName", "业主贷");
		session().setAttribute("borrowWay", "0"// modify by houli
		// IConstants.BORROW_TYPE_GENERAL
				);

		session().setAttribute("borrowWay1", IConstants.BORROW_TYPE_GENERAL);
		// add by houli 当用户发布借款的时候，给用户提示信息
		try {
			Map<String, String> map = borrowService.queryCurrentCreditLimet(user.getId());
			if (map != null && map.size() > 0) {
				request().setAttribute("usableCreditLimit", map.get("usableCreditLimit"));
				request().setAttribute("creditLimit", map.get("creditLimit"));
				request().setAttribute("msg", "现在您发布的为业主贷，需要通过五项基本认证");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}
		// ---end
		return SUCCESS;
	}

	/**
	 * 生意贷
	 */
	public String borrowColorLife() throws Exception {
		session().setAttribute("typeName", "生意贷");
		session().setAttribute("borrowWay", "2");
		session().setAttribute("borrowWay1", IConstants.BORROW_TYPE_SECONDS);

		return SUCCESS;
	}

	public String borrowGuarantee() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		try {
			String result = queryDataApproveStatus(user.getId(), IConstants.GUARANTEE, "机构担保");
			if (result == null) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		session().setAttribute("typeName", "机构担保");
		session().setAttribute("borrowWay", "0"// modify by houli
		// 其它借款每次操作之后，都返回到业主贷主页面
		// IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE
				);

		session().setAttribute("borrowWay1", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE);

		// add by houli 当用户发布借款的时候，给用户提示信息
		try {
			Map<String, String> map = borrowService.queryCurrentCreditLimet(user.getId());
			if (map != null && map.size() > 0) {
				request().setAttribute("usableCreditLimit", map.get("usableCreditLimit"));
				request().setAttribute("creditLimit", map.get("creditLimit"));
				request().setAttribute("msg", "现在您发布的为机构担保借款，需要通过五项基本认证跟机构担保认证");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}
		// ---end
		

		return SUCCESS;
	}

	private String queryDataApproveStatus(Long userId, Long typeId, String typeStr) throws Exception {
		Map<String, String> map = dataApproveService.querySauthId(userId, typeId);
		if (map == null) {
			getOut().print(
					"<script>alert('请先上传" + typeStr + "资料！');parent.location.href='" + request().getContextPath() + "/userPassData.do';</script>");
			return null;
		} else {
			Long sauthId = Convert.strToLong(map.get("id"), -1L);
			if (sauthId > 0) {
				if (map.get("auditStatus") == null || map.get("auditStatus").equals("")) {
					getOut().print(
							"<script>alert('请先上传" + typeStr + "资料审核！');parent.location.href='" + request().getContextPath()
									+ "/userPassData.do';</script>");
					return null;
				} else {
					Long status = dataApproveService.queryApproveStatus(sauthId);
					if (status < 0) {
						getOut().print(
								"<script>alert('请等待" + typeStr + "资料审核！');parent.location.href='" + request().getContextPath()
										+ "/borrow.do';</script>");
						return null;
					}
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 流转标 c_j
	 * 
	 * @return
	 * @throws Exception
	 */
	public String borrowFlow() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		try {
			String result = queryDataApproveStatus(user.getId(), IConstants.FLOW_PHONE, "手机认证");
			if (result == null) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		session().setAttribute("typeName", "流转标");
		session().setAttribute("borrowWay", "0"// modify by houli
		// 其它借款每次操作之后，都返回到业主贷主页面
		// IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE
				);

		session().setAttribute("borrowWay1", IConstants.BORROW_TYPE_INSTITUTION_FLOW);

		// add by houli 当用户发布借款的时候，给用户提示信息
		try {
			Map<String, String> map = borrowService.queryCurrentCreditLimet(user.getId());

			session().setAttribute("borrowWay", "circulation");

			Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
			tempBorrwBidMessage = getBorrowTypeMap(IConstants.BORROW_TYPE_INSTITUTION_FLOW + "");
			// 得到流转标
			// 取得按借款金额的比例进行奖励
			paramMap = new HashMap<String, String>();
			paramMap.put("scalefirst", Convert.strToStr(tempBorrwBidMessage.get("award_scale_first"), ""));
			paramMap.put("scaleend", tempBorrwBidMessage.get("award_scale_end"));
			// 如果选择金额的话，则按此奖励的金额范围
			paramMap.put("accountfirst", tempBorrwBidMessage.get("award_account_first"));
			paramMap.put("accountend", tempBorrwBidMessage.get("award_account_end"));
			// 借款额度
			paramMap.put("borrowMoneyfirst", tempBorrwBidMessage.get("amount_first"));
			paramMap.put("borrowMoneyend", tempBorrwBidMessage.get("amount_end"));
			// 借款额度倍数
			paramMap.put("accountmultiple", tempBorrwBidMessage.get("account_multiple"));
			// 年利率
			paramMap.put("aprfirst", tempBorrwBidMessage.get("apr_first"));
			paramMap.put("aprend", tempBorrwBidMessage.get("apr_end"));

			if (map != null && map.size() > 0) {
				request().setAttribute("usableCreditLimit", map.get("usableCreditLimit"));
				request().setAttribute("creditLimit", map.get("creditLimit"));
				request().setAttribute("msg", "现在您发布的为流转标借款，需要通过五项基本认证跟手机认证");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}
		// ---end
		return "flow";
	}

	public void validateAddBorrow() throws SQLException, DataException {
//		String borrowWay = session().getAttribute("borrowWay1") + "";
//		request().setAttribute("award_status", request("award_status"));
//		request().setAttribute("password_status", request("password_status"));
//		request().setAttribute("validateDay", request("validateDay"));
//
//		Map<String, String> borrowTypeMap = this.getBorrowTypeMap(borrowWay);
//		if (borrowTypeMap == null) {
//			this.addFieldError("paramMap['allError']", "该借款类型已关闭");
//			return;
//		}
//		if (!"1".equals(borrowTypeMap.get("status"))) {
//			this.addFieldError("paramMap['allError']", "该借款类型已关闭");
//			return;
//		}
		// 金额倍数
//		long accountMultiple = Convert.strToLong(borrowTypeMap.get("account_multiple"), 0);
		// 最小年利率
//		double minRate = Convert.strToDouble(borrowTypeMap.get("apr_first"), 0.0);
//		double maxRate = Convert.strToDouble(borrowTypeMap.get("apr_end"), 0.0);

//		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
//		if (StringUtils.isBlank(title)) {
//			this.addFieldError("paramMap['title']", "借款标题不能为空");
//			return;
//		}
//		if (StringUtils.isNotBlank(title) && title.length() >= 20) {
//			this.addFieldError("paramMap['title']", "借款标题长度不得大于20个字符");
//			return;
//		}
//		String imgPath = SqlInfusion.FilteSqlInfusion(paramMap.get("imgPath"));
//		if (StringUtils.isBlank(imgPath)) {
//			this.addFieldError("paramMap['imgPath']", "请上传借款图片");
//		}
//		String amount = SqlInfusion.FilteSqlInfusion(paramMap.get("amount"));
//		if (StringUtils.isBlank(amount)) {
//			this.addFieldError("paramMap['amount']", "请填写借款总额");
//			return;
//		}

		String mapCity = SqlInfusion.FilteSqlInfusion(paramMap.get("mapCity"));
        if (StringUtils.isBlank(mapCity)) {
            this.addFieldError("paramMap['mapCity']", "请填写所在地区");
            return;
        }
        request().setAttribute("mapCity", mapCity);
        
//		if (StringUtils.isNotBlank(amount)) {
//			if (!amount.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
//				this.addFieldError("paramMap['amount']", "借款总额格式不正确");
//				return;
//			} else {
//				double aountD = Convert.strToDouble(amount, 0.0);
//
//				double minAount = Convert.strToDouble(borrowTypeMap.get("amount_first"), 0.0);
//				double maxAount = Convert.strToDouble(borrowTypeMap.get("amount_end"), 0.0);
//				if (aountD < minAount || aountD > maxAount) {
//					this.addFieldError("paramMap['amount']", "借款总额范围为" + minAount + "元 ~ " + maxAount + "元");
//					return;
//				}
//
//				if (accountMultiple != 0 && aountD % accountMultiple != 0) {
//					this.addFieldError("paramMap['amount']", "借款总额应为" + accountMultiple + "的整数倍");
//					return;
//				}
//			}
//		}

//		String annualRate = SqlInfusion.FilteSqlInfusion(paramMap.get("annualRate"));
//		if (StringUtils.isBlank(annualRate)) {
//			this.addFieldError("paramMap['annualRate']", "请填写借款年利率");
//		}
//		if (StringUtils.isNotBlank(annualRate)) {
//			if (!annualRate.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
//				this.addFieldError("paramMap['annualRate']", "年利率格式不正确");
//			} else {
//				double annualRateD = Convert.strToDouble(annualRate, 0.0);
//				if (annualRateD < minRate || annualRateD > maxRate) {
//					this.addFieldError("paramMap['annualRate']", "年利率范围为" + minRate + "%~" + maxRate + "%");
//				}
//			}
//		}
//
//		String detail = SqlInfusion.FilteSqlInfusion(paramMap.get("detail"));
//
//		if (StringUtils.isNotBlank(detail) && detail.length() > 500) {
//			this.addFieldError("paramMap['detail']", "借款详情不能超过500个字符");
//		}
//		String excitationType = SqlInfusion.FilteSqlInfusion(paramMap.get("excitationType"));
//		int excitationTypeNum = -1;
//		if (StringUtils.isNotBlank(excitationType)) {
//			if (!excitationType.matches("^([0-9]\\d{0,9})$")) {
//				this.addFieldError("paramMap['excitationType']", "非数字");
//			}
//			excitationTypeNum = Convert.strToInt(excitationType, -1);
//		}
//
//		String sum = SqlInfusion.FilteSqlInfusion(paramMap.get("sum"));
//		if (StringUtils.isNotBlank(sum)) {
//			if (!sum.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
//				this.addFieldError("paramMap['sum']", "金额格式不正确");
//			}
//			// 投标奖励
//			if ("1".equals(borrowTypeMap.get("award_status")) && excitationTypeNum == 2) {
//				double minSum = Convert.strToDouble(borrowTypeMap.get("award_account_first"), 0.0);
//				double maxSum = Convert.strToDouble(borrowTypeMap.get("award_account_end"), 0.0);
//				double sumD = Convert.strToDouble(sum, 0.0);
//				if (sumD < minSum || sumD > maxSum) {
//					this.addFieldError("paramMap['sum']", "奖励金额范围是" + minSum + "元 ~ " + maxSum + "元");
//				}
//			}
//		}
//
//		String sumRate = SqlInfusion.FilteSqlInfusion(paramMap.get("sumRate"));
//		if (StringUtils.isNotBlank(sumRate)) {
//			if (!sumRate.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
//				this.addFieldError("paramMap['sumRate']", "金额比例格式不正确");
//			} else {
//				double sumRateD = Convert.strToDouble(sumRate, 0.0);
//				// 投标奖励
//				if ("1".equals(borrowTypeMap.get("award_status")) && excitationTypeNum == 3) {
//					double minSumRate = Convert.strToDouble(borrowTypeMap.get("award_scale_first"), 0.0);
//					double maxSumRate = Convert.strToDouble(borrowTypeMap.get("award_scale_end"), 0.0);
//
//					if (sumRateD < minSumRate || sumRateD > maxSumRate) {
//						this.addFieldError("paramMap['sumRate']", "奖励比率范围是" + minSumRate + "% ~ " + maxSumRate + "%");
//					}
//				}
//			}
//		}
//
//		String investPWD = SqlInfusion.FilteSqlInfusion(paramMap.get("investPWD"));
//		if (StringUtils.isNotBlank(investPWD) && investPWD.length() > 20) {
//			this.addFieldError("paramMap['investPWD']", "投标密码长度不得大于20个字符");
//		}

		String code = SqlInfusion.FilteSqlInfusion(paramMap.get("code"));
		if (StringUtils.isBlank(code)) {
			this.addFieldError("paramMap['code']", "请填写验证码");
			code = "codss";
			return;
		}

//		String raiseTerm = SqlInfusion.FilteSqlInfusion(paramMap.get("raiseTerm"));
//		if (StringUtils.isBlank(raiseTerm)) {
//			this.addFieldError("paramMap['raiseTerm']", "请选择投标期限");
//		}

		String _code = (String) session().getAttribute("borrow_checkCode");
		if (_code == null || _code == "") {
			_code = "code";
			return;
		}
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", "验证码错误");
			return;
		}

//		String daythe = SqlInfusion.FilteSqlInfusion(paramMap.get("daythe"));
//		String deadLine = SqlInfusion.FilteSqlInfusion(paramMap.get("deadLine"));
//		String deadDay = SqlInfusion.FilteSqlInfusion(paramMap.get("deadDay"));
//		int deadLineInt = Convert.strToInt(deadLine, -1);
//		if (!"2".equals(borrowWay)) {
//			if (!"true".equals(daythe)) {
//				if (deadLineInt < 0) {
//					this.addFieldError("paramMap['deadLine']", "请选择期限");
//					return;
//				}
//			} else {
//				int deadDayInt = Convert.strToInt(deadDay, -1);
//				if (deadDayInt < 0) {
//					this.addFieldError("paramMap['deadLine']", "请选择期限");
//					return;
//				}
//			}
//		}
	}

	/**
	 * 添加业主贷
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addBorrow() throws Exception {
        AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user != null) {
			String deadLine = SqlInfusion.FilteSqlInfusion(paramMap.get("deadLine"));
			String deadDay = SqlInfusion.FilteSqlInfusion(paramMap.get("deadDay"));//筹标期限
			String daythe = SqlInfusion.FilteSqlInfusion(paramMap.get("daythe"));
			String address = SqlInfusion.FilteSqlInfusion(paramMap.get("mapCity"));//用户所在地区
			
			int daytheInt = IConstants.DAY_THE_1;
			int deadLineInt = Convert.strToInt(deadLine, -1);
			int deadDayInt = Convert.strToInt(deadDay, -1);
			// 合和年 最多投标人数
			int maxInvest = Convert.strToInt(paramMap.get("maxInvest"), 0);
			// 天标
			if ("true".equals(daythe)) {
				deadLineInt = deadDayInt;
				daytheInt = IConstants.DAY_THE_2;
				// 为天标时默认就是按月分期还款
				// paymentModeInt = 1;
			}
			// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
			Object object = session().getAttribute("borrowWay1");
			if (object == null) {
				returnParentUrl("借款发布权限不足", "borrow.do");
				return null;
			}
			// if (user.getVipStatus() != IConstants.VIP_STATUS) {
			// getOut().print("<script>alert('您的VIP已过期,请及时续费!');parent.location.href='"
			// + request().getContextPath() + "/home.do';</script>");
			// return null;
			// }
//			if (maxInvest != 0 && (maxInvest < 2 || maxInvest > 49)) {
//				getOut().print("<script>alert('最多投标人数不能少于2人或多于49人!');</script>");
//				return null;
//			}

			String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
			String imgPath = SqlInfusion.FilteSqlInfusion(paramMap.get("imgPath"));
//			String purpose = SqlInfusion.FilteSqlInfusion(paramMap.get("purpose"));
			String purpose = title;//借款目的就是借款标题
			int purposeInt = Convert.strToInt(purpose, -1);
			String amount = SqlInfusion.FilteSqlInfusion(paramMap.get("amount"));
			// String moneyPurposes = paramMap.get("moneyPurposes");
			double amountDouble = Convert.strToDouble(amount, 0);
			String sum = SqlInfusion.FilteSqlInfusion(paramMap.get("sum"));
			double sumInt = Convert.strToDouble(sum, -1);
			if (sumInt > amountDouble) {
				this.addFieldError("enough", " *   奖励金额不能大于借款金额!");
				return "input";
			}
			String sumRate = SqlInfusion.FilteSqlInfusion(paramMap.get("sumRate"));
			double sumRateDouble = Convert.strToDouble(sumRate, -1);
			double annualRateDouble = 12.0;//默认给12
			
			int minTenderedSumInt = 0;// 最小投标金额
			int maxTenderedSumInt = 0;// 最大投标金额
			// 是否启用认购模式
			int subscribe_status = Convert.strToInt(request().getParameter("subscribeStatus"), 2);
			// 认购金额
			String subscribe = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("subscribe")), "");
			int circulationNumber = 0;
			if (subscribe_status != 1) {
				String minTenderedSum = SqlInfusion.FilteSqlInfusion(paramMap.get("minTenderedSum"));
				minTenderedSumInt = Convert.strToInt(minTenderedSum, 0);
				String maxTenderedSum = SqlInfusion.FilteSqlInfusion(paramMap.get("maxTenderedSum"));
				maxTenderedSumInt = Convert.strToInt(maxTenderedSum, -1);
			} else {
				// 得到认购总分份数
				circulationNumber = Integer.parseInt(amount) / Integer.parseInt(subscribe);
			}
			int raiseTermInt = 5;//默认5天
			String excitationType = SqlInfusion.FilteSqlInfusion(paramMap.get("excitationType"));
			if (StringUtils.isNotBlank(excitationType)) {
				// 按借款金额比例奖励
				if (StringUtils.isNumericSpace(excitationType) && "3".equals(excitationType)) {
					sumInt = sumRateDouble;
				}
			}
			int excitationTypeInt = Convert.strToInt(excitationType, -1);
			String detail = SqlInfusion.FilteSqlInfusion(paramMap.get("detail"));
			String borrowWay = (String) (object == null ? "" : object);
			String remoteIP = ServletUtils.getRemortIp();
			int borrowWayInt = Convert.strToInt(borrowWay, -1);

			if (borrowWayInt <= 0) {
				this.addFieldError("enough", "无效操作!");
				return "input";

			}
			// 查询标种类型
			Map<String, String> borrowTypeMap = getBorrowTypeMap(borrowWay);
			int number = Convert.strToInt(borrowTypeMap.get("subscribe_status"), -1);
			if (number != subscribe_status) {
				this.addFieldError("paramMap['allError']", "无效操作,该模式已关闭,请重新发布借款!");
				return "input";
			}
			// 冻结保证金
			double frozenMargin = 0;
			/*
			 * if(user.getVipStatus() == 2){ vip冻结保证金 frozenMargin =
			 * amountDouble *
			 * Double.parseDouble(borrowTypeMap.get("vip_frost_scale"))/100;
			 * }else{ }
			 */
			// 普通会员冻结保证金
			//frozenMargin = amountDouble * Double.parseDouble(borrowTypeMap.get("all_frost_scale")) / 100;
			// 当借款为薪金贷时，需要验证所输入的金额小于净值的70%
			if (IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)) {
				Map<String, String> map = borrowService.queryBorrowTypeNetValueCondition(user.getId(), amountDouble + frozenMargin);
				String result = map.get("result") == null ? "" : map.get("result");
				if (!"1".equals(result)) {
					this.addFieldError("enough", "您发布的借款金额超过净值+保障金的70%!");
					return "input";
				}
			}
			// 当借款类型为生意贷时,需要进行验证是否满足条件
			if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)) {
				Map<String, String> map = borrowService.queryBorrowTypeSecondsCondition(amountDouble, annualRateDouble, user.getId(),
						getPlatformCost(), frozenMargin);
				if (map == null || map.size() == 0) {
					this.addFieldError("enough", "您的可用金额不满足生意贷的发布条件!");
					return "input";
				}
			}
//			Map<String, String> maps = borrowService.queryBorrowFinMoney(frozenMargin, user.getId());
//			if (maps == null || maps.size() == 0) {
//				this.addFieldError("enough", "您的可用金额不满足借款所需保障金的发布条件!");
//				return "input";
//			}

			// ----modify by houli 生意贷调用的是另外一个方法，这里只需要判断薪金贷即可(否则这个中间应该用&&)--和合年不需要信用额度
//			if (!IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)) {
//				// 除了生意贷和薪金贷之外，其他要验证可用信用额度是否大于发布借款金额，同时发布成功后要扣除可用信用额度
//				Map<String, String> map = borrowService.queryCreditLimit(amountDouble, user.getId());
//				if (map == null || map.size() == 0) {
//					this.addFieldError("enough", "您的可用信用额度不足以发布[" + amountDouble + "]元的借款!");
//					return "input";
//				}
//			}
			// 进行借款的判断，如果已经发布了借款未满标通过，就不能再次发送（解决电脑卡机，造成数据重复提交）
//			Long re = borrowService.queryBorrowStatus(user.getId());
//			if (re < 0) {
//				getOut().print(
//						"<script>alert('你还有未通过满标的标的，暂时还不能再次发布！');parent.location.href='" + request().getContextPath() + "/borrow.do';</script>");
//				return null;
//			}
			String investPWD = SqlInfusion.FilteSqlInfusion(paramMap.get("investPWD"));
			String hasPWD = SqlInfusion.FilteSqlInfusion(paramMap.get("hasPWD"));
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
			// frozenMargin 冻结保证金 合和年只有一个担保机构
			Map<String, String> result = borrowService.addBorrow(title, imgPath, borrowWayInt, purposeInt, deadLineInt, 1, amountDouble,
					annualRateDouble, minTenderedSumInt, maxTenderedSumInt, raiseTermInt, excitationTypeInt, sumInt, detail, 1, investPWD, hasPWDInt,
					remoteIP, user.getId(), frozenMargin, daytheInt, getBasePath(), user.getUsername(), Convert.strToDouble(subscribe, 0),
					circulationNumber, 0, subscribe_status, borrowTypeMap.get("identifier"), frozenMargin, json, jsonState, purpose,
					user.getSource(), maxInvest,"","",0);
			if (Convert.strToInt(result.get("ret"), -1) < 0) {
//				getOut().print("<script>alert('" + result.get("ret_desc") + "');</script>");
				getOut().print("<script>alert('" + result.get("ret_desc") + "');parent.location.href='" + request().getContextPath() + "/borrow.do';</script>");
				return null;
			}
			//更新用户所在地区
			borrowService.updateUserAddress(user.getId(),address);
			// 清空paramMap
			paramMap = null;
			getOut().print("<script>alert('您申请的借款已经提交管理人员进行审核，谢谢！');parent.location.href='" + request().getContextPath() + "/finance.do';</script>");
		} else {
			return "nologin";
		}
		return null;
	}

	public void validateAddBorrowSeconds() throws SQLException, DataException {
		this.validateAddBorrow();
	}

	// 校验提交借款参数
	public boolean isValidateForTturnBid(double amountDouble, String excitationType, double sumRateDouble, double annualRateDouble)
			throws SQLException, DataException {
		// String t = (String) session().getAttribute("t");
		// 获取借款的范围
		Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
		tempBorrwBidMessage = shoveBorrowTypeService.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
		// 取得按借款金额的比例进行奖励
		double accountfirst = Convert.strToDouble(tempBorrwBidMessage.get("award_account_first") + "", 0);
		double accountend = Convert.strToDouble(tempBorrwBidMessage.get("award_account_end") + "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType) && "2".equals(excitationType)) {
				if (sumRateDouble < accountfirst || sumRateDouble > accountend) {
					this.addFieldError("paramMap['sum']", "固定总额奖励填写不正确");
					return false;
				}
			}
		}
		// 如果选择金额的话，则按此奖励的金额范围
		double scalefirst = Convert.strToDouble(tempBorrwBidMessage.get("award_scale_first") + "", 0);
		double scaleend = Convert.strToDouble(tempBorrwBidMessage.get("award_scale_end") + "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType) && "3".equals(excitationType)) {
				if (sumRateDouble < scalefirst || sumRateDouble > scaleend) {
					this.addFieldError("paramMap['sumRate']", "奖励比例填写不正确");
					return false;
				}
			}
		}
		// 借款额度
		double borrowMoneyfirst = Convert.strToDouble(tempBorrwBidMessage.get("amount_first") + "", 0);
		double borrowMoneyend = Convert.strToDouble(tempBorrwBidMessage.get("amount_end") + "", 0);
		if (borrowMoneyfirst > amountDouble || borrowMoneyend < amountDouble) {
			this.addFieldError("paramMap['amount']", "输入的借款总额不正确");
			return false;
		}
		// 借款额度倍数
		double accountmultiple = Convert.strToDouble(tempBorrwBidMessage.get("account_multiple") + "", -1);
		if (accountmultiple != 0) {
			if (amountDouble % accountmultiple != 0) {
				this.addFieldError("paramMap['amount']", "输入的借款总额的倍数不正确");
				return false;
			}
		}
		// 年利率
		double aprfirst = Convert.strToDouble(tempBorrwBidMessage.get("apr_first") + "", 0);
		double aprend = Convert.strToDouble(tempBorrwBidMessage.get("apr_end") + "", 0);
		if (aprfirst > annualRateDouble || aprend < annualRateDouble) {
			this.addFieldError("paramMap['annualRate']", "输入的年利率不正确");
			return false;
		}
		return true;
	}

	/**
	 * 生意贷
	 */
	public String addBorrowSeconds() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");

		// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
		Object object = session().getAttribute("borrowWay");
		if (object == null) {
			returnParentUrl("", "borrow.do");
			return null;
		}

		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String imgPath = SqlInfusion.FilteSqlInfusion(paramMap.get("imgPath"));
		int purposeInt = Convert.strToInt(paramMap.get("purpose"), -1);
		int maxInvest = Convert.strToInt(paramMap.get("maxInvest"), -1);
		int deadLineInt = Convert.strToInt(paramMap.get("deadLine"), 0);
		int paymentModeInt = IConstants.PAY_WAY_SECONDS;
		double amountDouble = Convert.strToDouble(paramMap.get("amount"), 0);
		double sumInt = Convert.strToDouble(paramMap.get("sum"), -1);
		double annualRateDouble = Convert.strToDouble(paramMap.get("annualRate"), 0);
		// 是否启用认购模式
		int subscribe_status = Convert.strToInt(request().getParameter("subscribeStatus"), -1);
		// 认购金额
		String subscribe = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("subscribe")), "");
		int circulationNumber = 0;
		if (subscribe_status != 1) {
			// String minTenderedSum = paramMap.get("minTenderedSum");
			// minTenderedSumInt = Convert.strToInt(minTenderedSum, 0);
			// String maxTenderedSum = paramMap.get("maxTenderedSum");
			// maxTenderedSumInt = Convert.strToInt(maxTenderedSum, -1);
		} else {
			// 得到认购总分份数
			circulationNumber = Integer.parseInt(paramMap.get("amount")) / Integer.parseInt(subscribe);
		}
		String raiseTerm = SqlInfusion.FilteSqlInfusion(paramMap.get("raiseTerm"));
		int raiseTermInt = Convert.strToInt(raiseTerm, -1);
		String investPWD = SqlInfusion.FilteSqlInfusion(paramMap.get("investPWD"));
		String hasPWD = SqlInfusion.FilteSqlInfusion(paramMap.get("hasPWD"));
		if ("true".equals(hasPWD)) {
			hasPWD = "1";
		}
		int hasPWDInt = Convert.strToInt(hasPWD, -1);
		String detail = SqlInfusion.FilteSqlInfusion(paramMap.get("detail"));
		String borrowWay = IConstants.BORROW_TYPE_SECONDS;
		String remoteIP = ServletUtils.getRemortIp();
		int borrowWayInt = Convert.strToInt(borrowWay, -1);

		// 查询标种详情
		Map<String, String> borrowTypeMap = this.getBorrowTypeMap(borrowWay);
		// 冻结保证金 ----------------
		double frozenMargin = 0;
		if (user.getVipStatus() != 2) {
			// vip冻结保证金
			frozenMargin = amountDouble * Double.parseDouble(borrowTypeMap.get("vip_frost_scale")) / 100;
		} else {
			// 普通会员冻结保证金
			frozenMargin = amountDouble * Double.parseDouble(borrowTypeMap.get("all_frost_scale")) / 100;
		}

		// 平台收费
		Map<String, Object> platformCostMap = getPlatformCost();
		double costFee = Convert.strToDouble(platformCostMap.get(IAmountConstants.BORROW_FEE_RATE_1) + "", 0);
		// 生意贷冻结借款+借款利息+借款手续费 + 冻结保证金
		double fee = (amountDouble * (annualRateDouble * 0.01 / 12)) + (amountDouble * costFee) + frozenMargin;
		// 当借款类型为生意贷时,需要进行验证是否满足条件
		if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)) {
			Map<String, String> map = borrowService.queryBorrowTypeSecondsCondition(amountDouble, annualRateDouble, user.getId(), getPlatformCost(),
					frozenMargin);
			if (map == null || map.size() == 0) {
				this.addFieldError("enough", "您的可用金额不满足生意贷的发布条件!");
				return "input";
			}
		}
		// ------add by houli 进行借款的判断，如果已经发布了借款未满标通过，就不能再次发送（解决电脑卡机，造成数据重复提交）
//		Long re = borrowService.queryBorrowStatus(user.getId());
//		if (re < 0) {
//			getOut().print("<script>alert('你还有未通过满标的标的，暂时还不能再次发布！');parent.location.href='" + request().getContextPath() + "/borrow.do';</script>");
//			return null;
//		}
		// 得到所有平台所有收费标准
		List<Map<String, Object>> mapList = platformCostService.queryAllPlatformCost();

		Map<String, Object> mapfee = new HashMap<String, Object>();
		Map<String, Object> mapFeestate = new HashMap<String, Object>();
		for (Map<String, Object> platformCostMaps : mapList) {
			double costFees = Convert.strToDouble(platformCostMaps.get("costFee") + "", 0);
			int costMode = Convert.strToInt(platformCostMaps.get("costMode") + "", 0);
			String remark = Convert.strToStr(platformCostMaps.get("remark") + "", "");
			if (costMode == 1) {
				mapfee.put(platformCostMaps.get("alias") + "", costFees * 0.01);
			} else {
				mapfee.put(platformCostMaps.get("alias") + "", costFees);
			}
			mapFeestate.put(platformCostMaps.get("alias") + "", remark); // 费用说明
			platformCostMaps = null;
		}
		String json = JSONObject.fromObject(mapfee).toString();
		String jsonState = JSONObject.fromObject(mapFeestate).toString();
		Map<String, String> result = borrowService.addBorrow(title, imgPath, borrowWayInt, purposeInt, deadLineInt, paymentModeInt, amountDouble,
				annualRateDouble, 0, 999999999, raiseTermInt, 1, sumInt, detail, 1, investPWD, hasPWDInt, remoteIP, user.getId(), fee,
				IConstants.DAY_THE_1, getBasePath(), user.getUsername(), Convert.strToDouble(subscribe, 0), circulationNumber, 0, subscribe_status,
				borrowTypeMap.get("identifier"), frozenMargin, json, jsonState, purposeInt + "", user.getSource(), maxInvest,"","",0);
		if (Convert.strToInt(result.get("ret"), -1) < 0) {
			getOut().print("<script>alert('" + result.get("ret_desc") + "');</script>");
			return null;
		}
		// 清空paramMap
		paramMap = null;
		// modify by houli 客户要求发布借款之后要给出提示
		getOut().print("<script>alert('您申请的借款已经提交管理人员进行审核，谢谢！');parent.location.href='" + request().getContextPath() + "/finance.do';</script>");
		return null;
	}

	/**
	 * 流转标
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addCirculationBorrow() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String code = (String) session().getAttribute("borrow_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code") == null ? "" : paramMap.get("code"));
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", "验证码错误");
			return "input";
		}
		// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
		Object object = session().getAttribute("borrowWay");
		if (object == null) {
			returnParentUrl("", "borrow.do");
			return null;
		}
		// if (user.getVipStatus() != IConstants.VIP_STATUS) {
		// getOut().print("<script>alert('您的VIP已过期,请及时续费!');parent.location.href='"
		// + request().getContextPath() + "/home.do';</script>");
		// return null;
		// }
		String remoteIP = ServletUtils.getRemortIp();
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String imgPath = SqlInfusion.FilteSqlInfusion(paramMap.get("imgPath"));
		String purpose = SqlInfusion.FilteSqlInfusion(paramMap.get("purpose"));
		int purposeInt = Convert.strToInt(purpose, -1);
		int maxInvest = Convert.strToInt(paramMap.get("maxInvest"), -1);
		String deadLine = SqlInfusion.FilteSqlInfusion(paramMap.get("deadLine"));
		int deadLineInt = Convert.strToInt(deadLine, 0);
		int paymentMode = 4;
		int borrowWay = Convert.strToInt(IConstants.BORROW_TYPE_INSTITUTION_FLOW, 6);
		String amount = SqlInfusion.FilteSqlInfusion(paramMap.get("amount"));
		double amountDouble = Convert.strToDouble(amount, 0);
		String annualRate = SqlInfusion.FilteSqlInfusion(paramMap.get("annualRate"));
		double annualRateDouble = Convert.strToDouble(annualRate, 0);
		String smallestFlowUnit = SqlInfusion.FilteSqlInfusion(paramMap.get("smallestFlowUnit"));
		double smallestFlowUnitDouble = Convert.strToDouble(smallestFlowUnit, 0);
		String businessDetail = SqlInfusion.FilteSqlInfusion(paramMap.get("businessDetail"));
		String assets = SqlInfusion.FilteSqlInfusion(paramMap.get("assets"));
		String moneyPurposes = SqlInfusion.FilteSqlInfusion(paramMap.get("moneyPurposes"));
		int circulationNumber = (int) (amountDouble / smallestFlowUnitDouble);

		Map<String, String> tempBorrwBidMessage = shoveBorrowTypeService.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
		if (smallestFlowUnitDouble > amountDouble) {
			this.addFieldError("paramMap['smallestFlowUnit']", "最小流转单位不能超过借款总额");
			return "input";
		}
		if (amountDouble < Convert.strToDouble(tempBorrwBidMessage.get("amount_first"), 0)) {
			this.addFieldError("paramMap['amount']", "借款总额必须大于等于" + Convert.strToDouble(tempBorrwBidMessage.get("amount_first"), 0));
			return "input";
		}
		if (amountDouble > Convert.strToDouble(tempBorrwBidMessage.get("amount_end"), 0)) {
			this.addFieldError("paramMap['amount']", "借款总额小于等于" + Convert.strToDouble(tempBorrwBidMessage.get("amount_end"), 0));
			return "input";
		}
		if (paymentMode == -1) {
			this.addFieldError("paramMap['paymentMode']", "请选择还款方式");
			return "input";
		}
		if (smallestFlowUnitDouble < 1) {
			this.addFieldError("paramMap['smallestFlowUnit']", "最小流转单位必须大于等于1");
			return "input";
		}
		if (smallestFlowUnitDouble > amountDouble) {
			this.addFieldError("paramMap['smallestFlowUnit']", "最小流转单位不能超过借款总额");
			return "input";
		}
		if (amountDouble % smallestFlowUnitDouble != 0) {
			this.addFieldError("paramMap['smallestFlowUnit']", "借款总额必须整除最小流转单位");
			return "input";
		}
		String excitationType = SqlInfusion.FilteSqlInfusion(paramMap.get("excitationType"));
		String sum = SqlInfusion.FilteSqlInfusion(paramMap.get("sum"));
		double sumInt = Convert.strToDouble(sum, -1);
		String sumRate = SqlInfusion.FilteSqlInfusion(paramMap.get("sumRate"));
		double sumRateDouble = Convert.strToDouble(sumRate, -1);

		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType) && "3".equals(excitationType)) {
				sumInt = sumRateDouble;
			}
		}

		if (excitationType.equals("2")) {
			if (!isValidateForTturnBid(amountDouble, excitationType, sumInt, annualRateDouble)) {
				return "input";
			}
		} else if (excitationType.equals("3")) {
			if (!isValidateForTturnBid(amountDouble, excitationType, sumRateDouble, annualRateDouble)) {
				return "input";
			}
		} else {
			if (!isValidateForTturnBid(amountDouble, excitationType, sumRateDouble, annualRateDouble)) {
				return "input";
			}
		}

		int excitationTypeInt = Convert.strToInt(excitationType, 1);
		// -------------
		// 查询标种详情
		Map<String, String> borrowTypeMap = this.getBorrowTypeMap(IConstants.BORROW_TYPE_INSTITUTION_FLOW);
		Map<String, String> counterList = shoveBorrowStyleService.querySlectStyleByTypeNid(IConstants.BORROW_TYPE_FLOW, 3);
		// /得到反担保方式
		String counterAgent = counterList.get("selectName");
		// 得到担保结构
		Map<String, String> instiList = shoveBorrowStyleService.querySlectStyleByTypeNid(IConstants.BORROW_TYPE_FLOW, 2);
		String agent = instiList.get("selectName");
		// 冻结保证金 ----------------
		double frozenMargin = 0;
		if (user.getVipStatus() == 2) {
			// vip冻结保证金
			frozenMargin = amountDouble * Double.parseDouble(borrowTypeMap.get("vip_frost_scale")) / 100;
		} else {
			// 普通会员冻结保证金
			frozenMargin = amountDouble * Double.parseDouble(borrowTypeMap.get("all_frost_scale")) / 100;
		}
		Map<String, String> maps = borrowService.queryBorrowFinMoney(frozenMargin, user.getId());
		if (maps == null || maps.size() == 0) {
			this.addFieldError("enough", "您的可用金额不满足借款所需保障金的发布条件!");
			return "input";
		}
		Long result = -1L;

		Long re = borrowService.queryBorrowStatus(user.getId());
//		if (re < 0) {
//			getOut().print("<script>alert('你还有未通过满标的标的，暂时还不能再次发布！');parent.location.href='" + request().getContextPath() + "/borrow.do';</script>");
//			return null;
//		}
		// 得到所有平台所有收费标准
		List<Map<String, Object>> mapList = platformCostService.queryAllPlatformCost();

		Map<String, Object> mapfee = new HashMap<String, Object>();
		Map<String, Object> mapFeestate = new HashMap<String, Object>();
		for (Map<String, Object> platformMap : mapList) {
			double costFee = Convert.strToDouble(platformMap.get("costFee") + "", 0);
			int costMode = Convert.strToInt(platformMap.get("costMode") + "", 0);
			String remark = Convert.strToStr(platformMap.get("remark") + "", "");
			if (costMode == 1) {
				mapfee.put(platformMap.get("alias") + "", costFee * 0.01);
			} else {
				mapfee.put(platformMap.get("alias") + "", costFee);
			}
			mapFeestate.put(platformMap.get("alias") + "", remark); // 费用说明
			platformMap = null;
		}

		String json = JSONObject.fromObject(mapfee).toString();
		String jsonState = JSONObject.fromObject(mapFeestate).toString();
		// --------------
		result = borrowService.addCirculationBorrow(title, imgPath, borrowWay, purposeInt, deadLineInt, paymentMode, amountDouble, annualRateDouble,
				remoteIP, circulationNumber, smallestFlowUnitDouble, user.getId(), businessDetail, assets, moneyPurposes, IConstants.DAY_THE_1,
				getBasePath(), user.getUsername(), excitationTypeInt, sumInt, json, jsonState, borrowTypeMap.get("identifier"), agent, counterAgent,
				frozenMargin, user.getSource(), maxInvest);
		if (result < 0)
			return "fail";

		getOut().print("<script>alert('您申请的借款已经提交管理人员进行审核，谢谢！');parent.location.href='" + request().getContextPath() + "/finance.do';</script>");
		return null;
	}

	/**
	 * @throws DataException
	 * @throws IOException
	 * @MethodName: addCrediting
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午04:39:43
	 * @Return:
	 * @Descb: 添加信用申请
	 * @Throws:
	 */
	public String addCrediting() throws SQLException, DataException, IOException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String applyAmount = SqlInfusion.FilteSqlInfusion(paramMap.get("applyAmount"));
		String applyDetail = SqlInfusion.FilteSqlInfusion(paramMap.get("applyDetail"));
		double applyAmountDouble = Convert.strToDouble(applyAmount, 0);
		if (StringUtils.isBlank(applyAmount)) {
			obj.put("msg", "请填写申请资金");
			JSONUtils.printObject(obj);
			return null;
		} else if (applyAmountDouble <= 0) {
			obj.put("msg", "申请资金格式错误");
			JSONUtils.printObject(obj);
			return null;
		} else if (applyAmountDouble < 1 || applyAmountDouble > 10000000) {
			obj.put("msg", "申请资金范围1到1千万");
			JSONUtils.printObject(obj);
			return null;
		}
		if (applyDetail.length() > 500) {
			obj.put("msg", "申请详情不能超过500字符");
			JSONUtils.printObject(obj);
			return null;
		}
		Long result = -1L;
		// 验证是否能够进行申请信用额度操作,防止通过URL提交的方式
		int validate = validatedCreditingApply();
		if (validate == 1) {
			result = borrowService.addCrediting(applyAmountDouble, applyDetail, user.getId());
		} else {
			obj.put("msg", "您已经申请过信用额度,请等待一个月后再次申请.");
			JSONUtils.printObject(obj);
			return null;
		}
		if (result <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @MethodName: validatedCreditingApply
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-9 上午08:58:02
	 * @Return: 0 无法操作 1 可以操作
	 * @Descb: 验证是否能够进行申请信用额度操作
	 * @Throws:
	 */
	public int validatedCreditingApply() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		// 查询能够再次申请信用额度的记录
		Map<String, String> applyMap = borrowService.queryCreditingApply(user.getId());
		if (applyMap != null && applyMap.size() > 0) {
			String applyTime = applyMap.get("applyTime") == null ? "" : applyMap.get("applyTime");
			String targetTime = applyMap.get("targetTime") == null ? "" : applyMap.get("targetTime");
			String msg = "您已在[" + applyTime + "]申请过信用额度,<br/>请于[" + targetTime + "] 以后再次申请.";
			request().setAttribute("apply", msg);
			return 0;
		} else {
			request().removeAttribute("apply");
			return 1;
		}
	}

	/**
	 * @MethodName: borrowConcernList
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午11:43:47
	 * @Return:
	 * @Descb: 关注的借款列表
	 * @Throws:
	 */
	public String borrowConcernList() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");

		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));

		String title = SqlInfusion.FilteSqlInfusion(request("title"));
		String publishTimeStart = SqlInfusion.FilteSqlInfusion(request("publishTimeStart"));
		String publishTimeEnd = SqlInfusion.FilteSqlInfusion(request("publishTimeEnd"));
		borrowService.queryBorrowConcernByCondition(title, publishTimeStart, publishTimeEnd, user.getId(), pageBean);
		this.setRequestToParamMap();

		return SUCCESS;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: delBorrowConcern
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午12:18:30
	 * @Return:
	 * @Descb: 删除我关注的借款
	 * @Throws:
	 */
	public String delBorrowConcern() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1L);
		borrowService.delBorrowConcern(idLong, user.getId());
		return "success";
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getBorrowPurposeList() throws SQLException, DataException {
		if (borrowPurposeList == null) {
			// 借款目的列表
			borrowPurposeList = selectedService.borrowPurpose();
		}
		return borrowPurposeList;
	}

	//借款期限
	public List<Map<String, Object>> getBorrowDeadlineMonthList() throws SQLException, DataException {
		if (borrowDeadlineMonthList == null) {
			borrowDeadlineMonthList = new ArrayList<Map<String, Object>>();
//			String nid = session().getAttribute("nid") + "";//借款类型
			String nid = "ordinary";//信用借款
			List<String> list = shoveBorrowTypeService.queryDeadlineMonthListByNid(nid);

			String month = "个月";
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value + month);
				borrowDeadlineMonthList.add(map);
			}
		}
		return borrowDeadlineMonthList;
	}

	//按天计算
	public List<Map<String, Object>> getBorrowDeadlineDayList() throws SQLException, DataException {
		if (borrowDeadlineDayList == null) {
			borrowDeadlineDayList = new ArrayList<Map<String, Object>>();
//			String nid = session().getAttribute("nid") + "";
			String nid = "ordinary";//信用借款
			List<String> list = shoveBorrowTypeService.queryDeadlineDayListByNid(nid);

			String day = "天";
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value + day);
				borrowDeadlineDayList.add(map);
			}
		}
		return borrowDeadlineDayList;
	}

	public List<Map<String, Object>> getBorrowMinAmountList() throws SQLException, DataException {
		if (borrowMinAmountList == null) {
			borrowMinAmountList = new ArrayList<Map<String, Object>>();
			String nid = session().getAttribute("nid") + "";
			List<String> list = shoveBorrowTypeService.queryMinAmountListByNid(nid);

			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value);
				borrowMinAmountList.add(map);
			}
		}

		return borrowMinAmountList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Map<String, Object>> getBorrowMaxAmountList() throws SQLException, DataException {
		if (borrowMaxAmountList == null) {
			borrowMaxAmountList = new ArrayList<Map<String, Object>>();
			String nid = session().getAttribute("nid") + "";
			List<String> list = shoveBorrowTypeService.queryMaxAmountListByNid(nid);

			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value);
				borrowMaxAmountList.add(map);
			}
		}
		return borrowMaxAmountList;
	}

	// 筹款期限列表
	public List<Map<String, Object>> getBorrowRaiseTermList() throws SQLException, DataException {
		if (borrowRaiseTermList == null) {
			// 筹款期限列表
		    String nid = "ordinary";//信用借款
//			String nid = session().getAttribute("nid") + "";
			borrowRaiseTermList = new ArrayList<Map<String, Object>>();
			List<String> list = shoveBorrowTypeService.queryRaiseTermLisByNid(nid);
			String day = "天";
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value + day);
				borrowRaiseTermList.add(map);
			}
		}
		return borrowRaiseTermList;
	}

	public List<Map<String, Object>> getSysImageList() throws SQLException, DataException {
		if (sysImageList == null) {
			// 系统列表
			sysImageList = selectedService.sysImageList();
		}
		return sysImageList;
	}

	public List<Map<String, Object>> getBorrowRepayWayList() throws SQLException, DataException {
		if (borrowRepayWayList == null) {
			String nid = session().getAttribute("nid") + "";
			borrowRepayWayList = shoveBorrowStyleService.queryShoveBorrowStyleByTypeNid(nid);
		}
		return borrowRepayWayList;
	}

	public Map<String, String> getCounterList() throws SQLException {
		if (counterList == null) {
			String nid = session().getAttribute("nid") + "";
			counterList = shoveBorrowStyleService.querySlectStyleByTypeNid(nid, 3);
		}
		return counterList;
	}

	public void setCounterList(Map<String, String> counterList) {
		this.counterList = counterList;
	}

	public Map<String, String> getInstiList() throws SQLException {
		if (instiList == null) {
			String nid = session().getAttribute("nid") + "";
			instiList = shoveBorrowStyleService.querySlectStyleByTypeNid(nid, 2);
		}
		return instiList;
	}

	public void setInstiList(Map<String, String> instiList) {
		this.instiList = instiList;
	}

	public int getCreditingApplyStatus() throws SQLException, DataException {
		return validatedCreditingApply();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}

	public DataApproveService getDataApproveService() {
		return dataApproveService;
	}

	public void setDataApproveService(DataApproveService dataApproveService) {
		this.dataApproveService = dataApproveService;
	}

	public void setShoveBorrowTypeService(ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setShoveBorrowStyleService(ShoveBorrowStyleService shoveBorrowStyleService) {
		this.shoveBorrowStyleService = shoveBorrowStyleService;
	}

	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}

}