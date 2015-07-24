package com.sp2p.action.front;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.trade.IInvestRepaymentService;
import com.hehenian.biz.common.trade.IInvestService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.AsianFontProvider;
import com.shove.util.SqlInfusion;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.entity.Operator;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.RelationService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

/**
 * @ClassName: FrontMyHomeAction.java
 * @Author: gang.lv
 * @Date: 2013-3-13 下午10:21:30
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的主页控制层
 */
public class FrontMyHomeAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(FrontMyHomeAction.class);
	private static final long serialVersionUID = 1L;
    @Autowired
    private IInvestRepaymentService investRepaymentService;
    @Autowired
    private IInvestService investService;
    
	protected UserService userService;
	private RelationService relationService;
	private MyHomeService myHomeService;
	private SelectedService selectedService;
	private List<Map<String, Object>> borrowDeadlineList;
	private Map<String, String> automaticBidMap;
	private List<Operator> checkList;

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: homeInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午08:53:19
	 * @Return:
	 * @Descb: 我的主页初始化
	 * @Throws:
	 */
	public String homeInit() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		Map<String, String> userPersonInfo=userService.queryPersonById(user.getId());
		if (userPersonInfo!=null&&StringUtils.isNotBlank(userPersonInfo.get("usrCustId"))) {
			user.setUsrCustId(Long.parseLong(userPersonInfo.get("usrCustId")));
//			user.setRealName(userPersonInfo.get("realName"));
//			user.setIdNo(userPersonInfo.get("idNo"));
		}
		Map<String, String> homeMap = myHomeService.queryHomeInfo(user.getId());
		request().setAttribute("homeMap", homeMap);
		
		request().setAttribute("usrCustId", user.getUsrCustId());// 汇付会员编号
		request().setAttribute("userId", user.getId());// 会员编号
		
		
		request().setAttribute("realName", userPersonInfo.get("realName"));// 真实姓名
		request().setAttribute("idNo", userPersonInfo.get("idNo"));// 身份证

		Map<String, String> accmountStatisMap = myHomeService.queryAccountStatisInfo(user.getId()); // 查询账户统计
		double usableAmount = Double.parseDouble(accmountStatisMap.get("usableAmount")); // 系统账户余额
		double freezeSum = Double.parseDouble(accmountStatisMap.get("freezeAmount")); // 系统账户余额

		String UsrCustId = String.valueOf(user.getUsrCustId());


		// //查询成功投资总金额
		// Map<String, String> mapMoney =
		// myHomeService.queryRechargeInfoOk(user.getId());
		// if(null != mapMoney){
		// request().setAttribute("rechargeMoney",
		// mapMoney.get("sum(rechargeMoney)"));
		// }

        accmountStatisMap = myHomeService.queryAccountStatisInfo(user.getId());


		request().setAttribute("accmountStatisMap", accmountStatisMap);
	/*	Map<String, String> repayMap = myHomeService.queryRepaymentByOwner(user.getId());
		request().setAttribute("repayMap", repayMap);*/
		// 最近一个月的流水记录
		List<Map<String, Object>> fundMap = myHomeService.queryFundRecord(user.getId());
		request().setAttribute("fundMap", fundMap);
		// boolean flag = false;
		// session().setAttribute("flag", flag);
		// JSONUtils.printStr2("<script> onload=function(){alert('ss');}</script>");
		Map<String, String> map = myHomeService.queryReferee(user.getId());
		request().setAttribute("myReferee", map);
		DesSecurityUtil des = new DesSecurityUtil();
		String userI = des.encrypt(user.getId().toString());
		request().setAttribute("userI", userI);
		String uri=getPath();
		request().setAttribute("url", uri);
		
		List<Map<String, Object>> investRecords = myHomeService.queryAllInvest(user.getId(),1);
		request().setAttribute("investRecords", investRecords);
		List<Map<String, Object>> fundRecords = myHomeService.queryAllFundrecord(user.getId(),1);
		request().setAttribute("fundRecords", fundRecords);

        Double dailyIncome = investRepaymentService.getDailyIncome(user.getId());
        request().setAttribute("dailyIncome",dailyIncome);
        Double assetValue = investRepaymentService.getAssetValue(user.getId());
        request().setAttribute("assetValue",assetValue);
        Double recivedPrincipal = investRepaymentService.getRecivedPrincipal(user.getId());
        request().setAttribute("recivedPrincipal",recivedPrincipal);
        log.info("dailyIncome:"+dailyIncome+",assetValue:"+assetValue+",recivedPrincipal:"+recivedPrincipal);
        return "success";
	}

    /**
     * 异步同步用户余额
     * @return
     */
    public String updateUserUsableSum(){
        AccountUserDo user = (AccountUserDo) session().getAttribute("user");
        if (user!=null&&user.getUsrCustId()>0){
            JSONObject json = null;
            try {
                // 后台查询余额接口
                String jsonStr = ChinaPnRInterface.queryBalanceBg(user.getUsrCustId()+"");
                json = JSONObject.fromObject(jsonStr);
                int RespCode = json.getInt("RespCode");
                if (RespCode == 0) {
                    String AvlBal = json.getString("AvlBal");
                    String FrzBal = json.getString("FrzBal");
                    AvlBal = AvlBal.replaceAll(",", "");
                    FrzBal = FrzBal.replaceAll(",", "");
//                    if ((Convert.strToDouble(user.getUsableSum(),0) != Double.valueOf(AvlBal)) ) {
                        myHomeService.usableAmountUpdate(user.getId(), Double.valueOf(AvlBal), Double.valueOf(FrzBal));
                        user.setUsableSum(Double.parseDouble(AvlBal));
//                    }
                    JSONObject jsonObject =new JSONObject();
                    jsonObject.put("AvlBal",AvlBal);
                    jsonObject.put("FrzBal",FrzBal);
                    JSONUtils.printObject(jsonObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
	/*
	 * 获取更多的投资记录
	 */
	public String moreInvestRecords() {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user != null) {
			int pageIndex = Convert.strToInt(request("pageIndex"),1);
			List<Map<String, Object>> investRecords = myHomeService.queryAllInvest(user.getId(),pageIndex);
			request().setAttribute("investRecords", investRecords);
		}
		return SUCCESS;
	}
	public void ajaxHomeFlag() throws IOException {
		boolean bb = Convert.strToBoolean(session().getAttribute("hhnflag") + "", false);
		if (!bb) {
			session().setAttribute("hhnflag", true);
		}
	}

	/**
	 * @MethodName: homeBorrowPublishInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午08:53:29
	 * @Return:
	 * @Descb: 已经发布的借款初始化
	 * @Throws:
	 */
	public String homeBorrowPublishInit() {
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: loanStatisInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:57:19
	 * @Return:
	 * @Descb: 借款统计
	 * @Throws:
	 */
	public String loanStatisInit() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		Map<String, String> loanStatisMap = myHomeService.queryLoanStatis(user.getId());
		request().setAttribute("loanStatisMap", loanStatisMap);
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: financeStatisInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:57:31
	 * @Return:
	 * @Descb: 理财统计
	 * @Throws:
	 */
	public String financeStatisInit() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		Map<String, String> financeStatisMap = myHomeService.queryFinanceStatis(user.getId());
		request().setAttribute("financeStatisMap", financeStatisMap);
		return "success";
	}

	/**
	 * @MethodName: homeBorrowBackAcount
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-4-2 上午09:12:22
	 * @Return:
	 * @Descb: 查询借款回账
	 * @Throws:
	 */
	public String homeBorrowBackAcount() throws SQLException, DataException, IOException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title") == null ? "" : paramMap.get("title"));
		String publishTimeStart = SqlInfusion.FilteSqlInfusion(paramMap.get("publishTimeStart") == null ? "" : paramMap.get("publishTimeStart"));
		if (StringUtils.isNotBlank(publishTimeStart)) {
			publishTimeStart = publishTimeStart + " 00:00:00";
		}
		String publishTimeEnd = SqlInfusion.FilteSqlInfusion(paramMap.get("publishTimeEnd") == null ? "" : paramMap.get("publishTimeEnd"));
		if (StringUtils.isNotBlank(publishTimeStart)) {
			publishTimeEnd = publishTimeEnd + " 23:59:59";
		}
		Map<String, String> map = myHomeService.queryBackAcountStatis(user.getId(), publishTimeStart, publishTimeEnd, title);
		String allForPIOneMonth = map.get("allForPIOneMonth") == null ? "0" : map.get("allForPIOneMonth");
		String allForPIThreeMonth = map.get("allForPIThreeMonth") == null ? "0" : map.get("allForPIThreeMonth");
		String allForPIYear = map.get("allForPIYear") == null ? "0" : map.get("allForPIYear");
		String allForPI = map.get("allForPI") == null ? "0" : map.get("allForPI");
		obj.put("allForPIOneMonth", allForPIOneMonth);
		obj.put("allForPIThreeMonth", allForPIThreeMonth);
		obj.put("allForPIYear", allForPIYear);
		obj.put("allForPI", allForPI);
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: homeBorrowInvestList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:40:13
	 * @Return:
	 * @Descb: 投资借款列表
	 * @Throws:
	 */
	public String homeBorrowInvestList() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");

		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));

		String type = "1";
		String borrowStatus = "";
		if ("1".equals(type)) {
			borrowStatus = IConstants.BORROW_STATUS_4 + "," + IConstants.BORROW_STATUS_5;
		} else if ("2".equals(type)) {
			borrowStatus = "" + IConstants.BORROW_STATUS_2;
		}
		String title = SqlInfusion.FilteSqlInfusion(request("title"));
		String publishTimeStart = SqlInfusion.FilteSqlInfusion(request("publishTimeStart"));
		String publishTimeEnd = SqlInfusion.FilteSqlInfusion(request("publishTimeEnd"));
		myHomeService.queryBorrowInvestByCondition(title, publishTimeStart, publishTimeEnd, borrowStatus, user.getId(), pageBean);
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: homeBorrowInvestList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:40:13
	 * @Return:
	 * @Descb: 投资借款列表
	 * @Throws:
	 */
	public String homeBorrowTenderInList() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");

		pageBean.setPageNum(request("curPage"));
		String borrowStatus = IConstants.BORROW_STATUS_2 + "";

		String title = SqlInfusion.FilteSqlInfusion(request("title"));
		String publishTimeStart = SqlInfusion.FilteSqlInfusion(request("publishTimeStart"));
		String publishTimeEnd = SqlInfusion.FilteSqlInfusion(request("publishTimeEnd"));
		myHomeService.queryBorrowInvestByCondition(title, publishTimeStart, publishTimeEnd, borrowStatus, user.getId(), pageBean);
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: homeBorrowRecycleList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:41:47
	 * @Return:
	 * @Descb: 待回收借款列表
	 * @Throws:
	 */
	public String homeBorrowRecycleList() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		String title = SqlInfusion.FilteSqlInfusion(request("title"));
		myHomeService.queryBorrowRecycleByCondition(title, user.getId(), pageBean);
		this.setRequestToParamMap();
		return "success";
	}

	/**
	 * @MethodName: homeBorrowRecycledList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午01:40:27
	 * @Return:
	 * @Descb: 已回收的借款
	 * @Throws:
	 */
	public String homeBorrowRecycledList() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		String title = SqlInfusion.FilteSqlInfusion(request("title"));
		myHomeService.queryBorrowRecycledByCondition(title, user.getId(), pageBean);
		this.setRequestToParamMap();
		return "success";
	}

	/**
	 * 会员中心 当前投资 还款详情
	 */
	public String homeBorrowForpayDetail() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String iid = SqlInfusion.FilteSqlInfusion(request("iid") == null ? "" : request("iid"));
		long investId = Convert.strToLong(iid, -1);

		List<Map<String, Object>> listMap = myHomeService.queryBorrowForpayHHN(user.getId(), investId);
		request().setAttribute("listMap", listMap);
		return "success";
	}

	/**
	 * @MethodName: homeBorrowHaspayDetail
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:57:20
	 * @Return:
	 * @Descb: 查询投资人已回收借款还款详情
	 * @Throws:
	 */
	public String homeBorrowHaspayDetail() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter("id"));
		String iid = SqlInfusion.FilteSqlInfusion(request().getParameter("iid") == null ? "" : request().getParameter("iid"));

		long idLong = Convert.strToLong(id, -1);
		long iidLong = Convert.strToLong(iid, -1);
		List<Map<String, Object>> listMap = myHomeService.queryBorrowHaspayById(idLong, user.getId(), iidLong);
		request().setAttribute("listMap", listMap);
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: homeBorrowBackAcountList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:43:24
	 * @Return:
	 * @Descb: 借款回账查询列表
	 * @Throws:
	 */
	public String homeBorrowBackAcountList() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");

		Map<String, String> backAcountStatisMap = myHomeService.queryBackAcountStatis(user.getId(), "", "", "");
		request().setAttribute("backAcountStatisMap", backAcountStatisMap);
		// 回账类型
		request().setAttribute("type", "5");

		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		String title = SqlInfusion.FilteSqlInfusion(request("title"));
		String publishTimeStart = SqlInfusion.FilteSqlInfusion(request("publishTimeStart"));
		String publishTimeEnd = SqlInfusion.FilteSqlInfusion(request("publishTimeEnd"));
		myHomeService.queryBorrowBackAcountByCondition(title, publishTimeStart, publishTimeEnd, user.getId(), pageBean);

		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: homeBorrowPublishList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午09:03:01
	 * @Return:
	 * @Descb: 审核中的借款
	 * @Throws:
	 */
	public String homeBorrowAuditList() throws SQLException, DataException {
		String borrowStatus = IConstants.BORROW_STATUS_1 + "," + IConstants.BORROW_STATUS_3;
		return queryBrrowPublishList(borrowStatus);
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: homeBorrowPublishList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午09:03:01
	 * @Return:
	 * @Descb: 已经发布的借款列表
	 * @Throws:
	 */
	public String homeBorrowingList() throws SQLException, DataException {
		String borrowStatus = "" + IConstants.BORROW_STATUS_2;
		return queryBrrowPublishList(borrowStatus);
	}

	@SuppressWarnings("deprecation")
	private String queryBrrowPublishList(String borrowStatus) throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		pageBean.setPageNum(request("curPage"));
		pageBean.setPageSize(10);
		String title = SqlInfusion.FilteSqlInfusion(request("title"));
		String publishTimeStart = SqlInfusion.FilteSqlInfusion(request("publishTimeStart"));
		String publishTimeEnd = SqlInfusion.FilteSqlInfusion(request("publishTimeEnd"));
		String endTime = publishTimeEnd;
		if (endTime != null && !(endTime.equals(""))) {
			String[] strs = endTime.split("-");
			// 交易结束日期往后移一天,否则某天0点以后的数据都不呈现
			Date date = new Date();// 取时间
			long time = Date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2], -1), 0, 0, 0);
			date.setTime(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			endTime = formatter.format(date);
		}

		myHomeService.queryBorrowFinishByCondition(title, publishTimeStart, endTime, borrowStatus, user.getId(), pageBean);
		this.setRequestToParamMap();
		paramMap.put("title", title);
		return SUCCESS;
	}

	/**
	 * @MethodName: automaticBidInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午03:09:53
	 * @Return:
	 * @Descb: 查询用户自动投标设置
	 * @Throws:
	 */
	public String automaticBidInit() throws SQLException, DataException {
		return "success";
	}

	/**
	 * @throws DataException
	 * @MethodName: automaticBidSet
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:33:53
	 * @Return:
	 * @Descb: 自动投标设置
	 * @Throws:
	 */
	public String automaticBidSet() throws IOException, SQLException, DataException {
		String bidStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("s") == null ? "1" : paramMap.get("s"));
		long bidStatusLong = Convert.strToLong(bidStatus, 1);
		JSONObject obj = new JSONObject();
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		long returnId = -1;
		returnId = myHomeService.automaticBidSet(bidStatusLong, user.getId());

		if (returnId <= 0) {
			obj.put("msg", "未保存自动投标设置");
		} else {
			obj.put("msg", 1);
		}
		JSONUtils.printObject(obj);
		return null;
	}

	/** 开启或关闭自动投标计划 ,如果请求参数包含close且值为close则关闭自动投标,否则开启 */
	public String automaticBidModify() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String retUrl = request("retUrl");
		String close = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("close")), "");// 是否为关闭自动投标
		if ("close".equals(close)) {
//			String html = ChinaPnRInterface.autoTenderPlanClose(user.getUsrCustId() + "",retUrl);
//			sendHtml(html);
//			return null;
            long l = userService.updateAutoPlan(user.getUsrCustId(), 1, "");
            if (l>0){
                JSONUtils.printStr2("关闭自动投标成功");
            }else {
                JSONUtils.printStr2("关闭自动投标失败，请重试");
            }
            return null;
		}

		// 自动投标计划的类型:取值范围 P部分授权,W完全授权
		String TenderPlanType = "P";
		// String TenderPlanType =
		// SqlInfusion.FilteSqlInfusion(paramMap.get("type"));
		/*
		 * 根据汇付接口的调整,不动数据库结构的情况下,将t_automaticbid表中
		 * remandAmount(原存放:保留金额)字段调整为自动投标计划最高金额, ,存放部分授权时最高投标金额,完全授权存放0
		 */
		// double maxAmt =
		// Convert.strToDouble(SqlInfusion.FilteSqlInfusion(paramMap.get("maxAmt")),
		// 0);// 部分授权时最高投标金额
		double bidAmount = Convert.strToDouble(SqlInfusion.FilteSqlInfusion(paramMap.get("bidAmt")), 200000);// 每次投标金额
		double rateStart = Convert.strToDouble(SqlInfusion.FilteSqlInfusion(paramMap.get("rateStart")), 0);// 利率范围
		double rateEnd = Convert.strToDouble(SqlInfusion.FilteSqlInfusion(paramMap.get("rateEnd")), 24);// 利率范围
		int deadlineStart = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("deadlineStart")), 0);// 借款期限范围
		int deadlineEnd = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("deadlineEnd")), 36);// 借款期限范围
		double remandAmount = Convert.strToDouble(SqlInfusion.FilteSqlInfusion(paramMap.get("remandAmount")), 0);// 保留金额

		// if (StringUtils.isBlank(TenderPlanType)) {
		// JSONUtils.printStr2("请选择自动投标计划的类型");
		// return null;
		// }
		if (bidAmount < 100 || bidAmount % 100 != 0) {
			JSONUtils.printStr2("每次投标金额不能低于100并且为100的倍数");
			return null;
		}
		if (rateStart < 0 || rateEnd <= 0 || rateStart > rateEnd || rateEnd > 24 || rateStart > 24) {
			JSONUtils.printStr2("年利率范围为0-24%");
			return null;
		}
		// if ("P".equals(TenderPlanType)) {
		// if (maxAmt == 0) {
		// JSONUtils.printStr2("请填写自动投标计划的金额");
		// return null;
		// }
		// } else if ("W".equals(TenderPlanType)) {
		// maxAmt = 0;
		// } else {
		// JSONUtils.printStr2("请选择自动投标计划的类型");
		// return null;
		// }
		if (deadlineStart > deadlineEnd || (deadlineStart * deadlineEnd) < 0) {
			JSONUtils.printStr2("借款期限范围错误");
			return null;
		}

		long ret = myHomeService.automaticBidModify(remandAmount, user.getId(), bidAmount, rateStart, rateEnd, deadlineStart, deadlineEnd);
		if (ret > 0) {
			String html = ChinaPnRInterface.autoTenderPlan(user.getUsrCustId() + "", TenderPlanType, new DecimalFormat("0.00").format(bidAmount),retUrl);
			sendHtml(html);
		} else {
			JSONUtils.printStr2("操作失败");
		}
		return null;

	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RelationService getRelationService() {
		return relationService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public MyHomeService getMyHomeService() {
		return myHomeService;
	}

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws SQLException, DataException {
		if (borrowDeadlineList == null) {
			// 借款期限列表
			borrowDeadlineList = selectedService.borrowDeadline();
		}
		return borrowDeadlineList;
	}

	public Map<String, String> getAutomaticBidMap() throws SQLException, DataException {
		if (automaticBidMap == null) {
			// 自动投标设置
			AccountUserDo user = (AccountUserDo) session().getAttribute("user");
			automaticBidMap = myHomeService.queryAutomaticBid(user.getId());

			checkList = new ArrayList<Operator>();
			if (automaticBidMap != null) {
				// 设置ckBoxList的选中值
				String borrowWay = automaticBidMap.get("borrowWay");
				String[] ckList = borrowWay.split(",");
				if (ckList.length > 0) {
					for (String ck : ckList) {
						checkList.add(new Operator(ck, ""));
					}
				}
			}
		}
		return automaticBidMap;
	}

	public List<Operator> getCheckList() {
		return checkList;
	}

	// ///////////////////////投资记录
	// 成功投资
	@SuppressWarnings({ "deprecation" })
	public String investSuccessedRecord() throws Exception {
        AccountUserDo user = this.getUser();
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));

		long number = Convert.strToLong(request("number"), -1);
		String userName = SqlInfusion.FilteSqlInfusion(request("userName"));
		String publishTimeStart = SqlInfusion.FilteSqlInfusion(request("publishTimeStart"));
		String publishTimeEnd = SqlInfusion.FilteSqlInfusion(request("publishTimeEnd"));

		String endTime = publishTimeEnd;
		if (endTime != null && !(endTime.equals(""))) {
			String[] strs = endTime.split("-");
			// 交易结束日期往后移一天,否则某天0点以后的数据都不呈现
			Date date = new Date();// 取时间
			long time = Date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2], -1), 0, 0, 0);
			date.setTime(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			endTime = formatter.format(date);
		}

		paramMap.put("number", SqlInfusion.FilteSqlInfusion(request("number")));
		paramMap.put("userName", userName);
		paramMap.put("publishTimeStart", publishTimeStart);
		paramMap.put("publishTimeEnd", publishTimeEnd);
		
		/**
		 * 
		 * zhangyunhua 2014-10-20
		 */
		// myHomeService.queryInvestSuccessedRecord(number, publishTimeStart, endTime, user.getId(), pageBean, userName);
		PageDo page  = new PageDo();
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		if(-1 != number){
		    parameterMap.put("number", number);
		}
		parameterMap.put("investor", user.getId());
		
		if(null != userName && !"".equals(userName)){
		    parameterMap.put("userName", userName);
        }
        if(null != publishTimeStart && !"".equals(publishTimeStart)){
            parameterMap.put("publishTimeStart", publishTimeStart);
        }
        
        if(null != publishTimeEnd && !"".equals(publishTimeEnd)){
            parameterMap.put("publishTimeEnd", publishTimeEnd);
        }
        
		//新旧page类不同，需要转换
		page.setPageNum(pageBean.getPageNum());
		page.setPageSize(pageBean.getPageSize());
		page.setTotalNum(pageBean.getTotalNum());
		page.setTotalPageNum(pageBean.getTotalPageNum());
		page = investService.selectInvestSuccessRecordPage(parameterMap, page);
		if(null != page ){
		    if(pageBean.getPageNum() <= page.getPageNum() ){
		        pageBean.setPage(page.getPage());
		    }
    		pageBean.setTotalNum(page.getTotalNum());
		}
		
		Map<String, String> map = investService.getInvestSuccessAmountByUserId(user.getId());
		//end 2014-10-20
		
		// Map<String, String> map = myHomeService.queryAmount(user.getId(), "success");
		request().setAttribute("map", map);
		/* this.setRequestToParamMap(); */
		return SUCCESS;
	}
	
	public String investsInvoke() {
	    request().setAttribute("borrowId",request("borrowId"));
        request().setAttribute("blanceP",request("blanceP"));
        request().setAttribute("debtLimit",request("debtLimit"));
        request().setAttribute("investId",request("investId"));
        request().setAttribute("annualRate",request("annualRate"));
        request().setAttribute("anna",request("anna"));
        request().setAttribute("num",request("num"));
        request().setAttribute("whbj",request("whbj"));
        request().setAttribute("debtId",request("debtId"));
	    return SUCCESS;
	}

	// 多项债权合并
	public String investsCombine() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String ids = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("id")), null);
		if (ids == null || ids.length() == 0) {
			JSONUtils.printStr("-6");// 合并失败
			return null;
		}

		long result = myHomeService.investsCombine(ids, user.getId());
		JSONUtils.printStr(String.valueOf(result));
		return null;
	}

	// 多项债权拆分（主要将多项债权合并，让后拆分）
	public String queryInvestById() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String ids = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("ids")), null);
		if (ids == null) {
			JSONUtils.printStr("-6");
			return null;
		}
		Map<String, Object> data = myHomeService.investsSplit(ids, user.getId());
		if (Integer.parseInt(data.get("result").toString()) < 0) {// 不能合并,从而也就不能拆分
			JSONUtils.printStr(String.valueOf(data.get("result")));
			return null;
		}

		session().setAttribute("invests", data.get("invests"));// 保存合并结果

		Dao.Tables.t_invest invests = (Dao.Tables.t_invest) session().getAttribute("invests");
		String borrowId = String.valueOf(invests.borrowId.getValue());
		Map<String, String> map = null;
		try {
			map = myHomeService.queryInvestById(borrowId);// 得到真实姓名
			map.put("deadlineTime", String.valueOf(invests.deadline.getValue()));// 得到期限
			map.put("realAmount", String.valueOf(invests.realAmount.getValue()));// 得到实际金额
			request().setAttribute("map", map);
			request().setAttribute("ids", ids);
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	// 债权的拆分
	public String assignmentSplit() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String userId = user.getId().toString();
		String ids = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("ids")), null);
		Integer number = Convert.strToInt(paramMap.get("number"), -1);
		double realAmount = Convert.strToDouble(paramMap.get("realAmount"), -1);
		String split = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("split")), null);

		if (ids == null || realAmount < 0 || split == null || (split.equals("1") && number < 0)) {
			JSONUtils.printStr("2");// 拆分失败
			return null;
		}

		long result = 0;

		Dao.Tables.t_invest invests = (Dao.Tables.t_invest) session().getAttribute("invests");
		try {
			result = myHomeService.insertAssignmentDebt(invests, ids, userId, number, realAmount, split);
			session().removeAttribute("invests");// 清楚保存在session中的合并记录
			if (result > 0) {
				JSONUtils.printStr("1");// 拆分成功
			} else {
				JSONUtils.printStr("2");// 拆分失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryDebtSumById() throws SQLException {
		double number = Double.valueOf(request().getParameter("number"));
		double debtSum = 0;
		Map<String, String> map = new HashMap<String, String>();
		DecimalFormat formater = new DecimalFormat("#0.##");
		try {
			Dao.Tables.t_invest invests = (Dao.Tables.t_invest) session().getAttribute("invests");
			map.put("realAmount", String.valueOf(invests.realAmount.getValue()));
			debtSum = Double.valueOf(map.get("realAmount"));
			if (debtSum > 0) {
				debtSum = debtSum / number;
				JSONUtils.printStr(String.valueOf((formater.format(debtSum))));
			} else {
				JSONUtils.printStr("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据用户输入的拆分金额计算是否超过债权总额
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String queryRealAmount() {
		double realAmount = Double.valueOf(request().getParameter("realAmount"));
		Map<String, String> map = new HashMap<String, String>();
		Dao.Tables.t_invest invests = (Dao.Tables.t_invest) session().getAttribute("invests");
		try {
			map.put("realAmount", String.valueOf(invests.realAmount.getValue()));
			if (realAmount < Double.valueOf(map.get("realAmount"))) {
				JSONUtils.printStr("1");
			} else {
				JSONUtils.printStr("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加债权转让
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addInvestDebt() {
		long userId = this.getUserId();
		Map<String, String[]> m = request().getParameterMap();

		// double auctionBasePrice =
		// Convert.strToDouble(paramMap.get("auctionBasePrice"), -1);
		double auctionBasePrice = Double.valueOf(m.get("auctionBasePrice")[0]);
		String investId = m.get("investId")[0];
		String auctionDays = m.get("auctionDays")[0];
		// double debtSum = Convert.strToDouble(paramMap.get("debtSum"), -1);
		double lowerPrice = 0;// debtSum*0.5;

		if (auctionBasePrice < lowerPrice) {
			// NumberFormat nf = new DecimalFormat("0.00");/
			this.addFieldError("paramMap.auctionBasePrice", "转让价格不能低于0");// "转让价格范围为"+nf.format(lowerPrice)+"到"+debtSum+"元之间");
			return INPUT;
		}
		if (auctionDays == null || auctionDays.equals("")) {
			this.addFieldError("paramMap.auctionDays", "请选择债权转让期限");
			return INPUT;
		}
		paramMap.put("alienatorId", userId + "");
		paramMap.put("applyTime", DateUtil.dateToString(new Date()));
		paramMap.put("auctionBasePrice", String.valueOf(auctionBasePrice));
		paramMap.put("debtSum", m.get("debtSum")[0]);
		paramMap.put("investId", investId);
		paramMap.put("debtLimit", m.get("debtLimit")[0]);
		paramMap.put("publishTime", DateUtil.dateToString(new Date()));
		paramMap.put("borrowId", m.get("borrowId")[0]);
		paramMap.put("debtStatus", 2 + "");
		paramMap.put("auctionDays", auctionDays);
		paramMap.put("details", m.get("details")[0]);

		long reslut = -1;
		try {
			reslut = myHomeService.addAssignmentDebt(paramMap);

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		try {
			if (reslut > 0) {

				JSONUtils.printStr("1");

			} else {
				JSONUtils.printStr("-1");
			}
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	// 当前投资
	@SuppressWarnings("deprecation")
	public String investCurrentRecord() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));

		long id = Convert.strToLong(request("id"), -1);
		String userName = SqlInfusion.FilteSqlInfusion(request("userName"));
		String publishTimeStart = SqlInfusion.FilteSqlInfusion(request("publishTimeStart"));
		String publishTimeEnd = SqlInfusion.FilteSqlInfusion(request("publishTimeEnd"));
		String endTime = publishTimeEnd;
		if (endTime != null && !(endTime.equals(""))) {
			String[] strs = endTime.split("-");
			// 交易结束日期往后移一天,否则某天0点以后的数据都不呈现
			Date date = new Date();// 取时间
			long time = Date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2], -1), 0, 0, 0);
			date.setTime(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			endTime = formatter.format(date);
		}
		paramMap.put("id", request("id"));
		paramMap.put("userName", userName);
		paramMap.put("publishTimeStart", publishTimeStart);
		paramMap.put("publishTimeEnd", publishTimeEnd);

		myHomeService.queryInvestCurrentRecord(id, publishTimeStart, endTime, user.getId(), pageBean, userName);
		Map<String, String> map = myHomeService.queryAmount(user.getId(), "current");// 金额统计
		request().setAttribute("map", map);
		
		
		return SUCCESS;
	}

	// 详细统计
	public String investDetailCount() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");

		// 各数量总额统计
		Map<String, String> accmountStatisMap = myHomeService.queryAccountStatisInfo(user.getId());
		request().setAttribute("map", accmountStatisMap);
		// 投资信用积分统计
		List<Map<String, Object>> list = myHomeService.queryCreditScores(user.getId());
		// 统计总的投资记录条数
		if (list.size() != 0 && list != null) {
			Map<String, String> countsMap = myHomeService.queryAllInvestCount(user.getId());
			request().setAttribute("counts", countsMap.get("counts"));
		}
		request().setAttribute("list", list);

		return SUCCESS;
	}

	/**
	 * 合和年 借款列表
	 */
	public String homeBorrowAllList() throws SQLException, DataException {
		return queryBrrowPublishList("2,3,4,5,6,7,8");
	}

	/**
	 * 合和年还款 详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryPayingDetailsHHN() {
		long borrowId = Convert.strToLong(request("borrowId"), -1);
		if (borrowId <= 0)
			return SUCCESS;
		try {
			myHomeService.queryPayingDetailsHHN(borrowId, request());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 查询我的推荐人
	public String myReferee() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		Map<String, String> map = myHomeService.queryReferee(user.getId());
		request().setAttribute("map", map);
		request().setAttribute("userId", user.getId());
		return SUCCESS;
	}

	// 设置我的推荐人
	public String setMyReferee() throws Exception {
		String reffer = paramMap.get("refferee");
		long refferId = -1;
		if (StringUtils.isNotBlank(reffer)) {
			refferId = userService.findUserByIdOrPhone(reffer);
		}
		if (refferId<=0) {
			JSONUtils.printStr("-2");
			return null;
		}
		
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
        if (user.getId()==refferId){
            JSONUtils.printStr("-3");
            return null;
        }
		long result = myHomeService.setReferee(refferId+"", user.getId().toString());
		if (result>0) {
			if (StringUtils.isNotBlank(reffer)){
				try {
					userService.saveUserReffer(reffer, user.getId());
					user.setRefferee(refferId+"");
					session().setAttribute("user", user);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		JSONUtils.printStr(String.valueOf(result));
		return null;
	}

	/**
	 * 前台页面查看余额
	 * 
	 * @return
	 * @throws Exception
	 */
	public void foregroundRemainder() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String html = ChinaPnRInterface.AccountUpdate(user.getUsrCustId() + "");
		sendHtml(html);
	}

	/** 前台 借款协议 查看 */
	public String agreementDetail() throws Exception {

		String content = myHomeService.queryInvestByBorrowId(pageBean, request("borrowId"), this.getUserId());
		if (content != null && content.length() < 20) {
			JSONUtils.printStr2(content);
			return null;
		}
		// 创建pdf;
		Document document = new Document(PageSize.A4, 25, 25, 25, 25);
		OutputStream out = ServletActionContext.getResponse().getOutputStream();
		PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
		document.open();

		String path = ServletActionContext.getServletContext().getRealPath("images/admin/hhnxd-1.png");
		Image image = Image.getInstance(path);
		// image.setAbsolutePosition(366, 610);//第一页右上角
		// document.add(image);//第一页右上角
		XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
		worker.parseXHtml(pdfWriter, document, new ByteArrayInputStream(content.getBytes()), null, new AsianFontProvider());
		image.setAbsolutePosition(350, 40);// 最后一页右下角
		document.add(image);// 最后一页右下角
		document.close();
		return null;
	}

	public String exportPDF() throws Exception {
		// String content = "http://localhost/agreementDetail.do?borrowId=3";
		// String content = myHomeService.queryInvestByBorrowId(pageBean,
		// request("borrowId"));

		// JSONUtils.printStr2(PDFUtil.pdf(content, request("borrowId")));
		// document.addAuthor("ysjiang");
		// document.addCreator("ysjiang");
		// document.addSubject("test");
		// document.addCreationDate();
		// document.addTitle("XHTML to PDF");

		// Document document = new Document(PageSize.A4);
		// PdfWriter pdfWriter = PdfWriter.getInstance(document, new
		// FileOutputStream("d://testpdf.pdf"));
		// document.open();
		// XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
		// String content = myHomeService.queryInvestByBorrowId(pageBean,
		// request("borrowId"), this.getUserId());
		// worker.parseXHtml(pdfWriter, document, new
		// ByteArrayInputStream(content.getBytes()), null, new
		// AsianFontProvider());
		// document.close();
		return null;
	}

	public String bindCardInit() throws Exception {
		long usrCustId = this.getUser().getUsrCustId();

		if (usrCustId <= 0) {
			JSONUtils.printStr2("您还不是汇付天下会员!");
			return null;
		}
		String html = ChinaPnRInterface.userBindCard(usrCustId + "");
		JSONUtils.printStr2(html);
		return null;
	}
	
	public String colourTjrJiangli() {
		
		return SUCCESS;
	}
	
	
	
}
