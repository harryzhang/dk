package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BorrowService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.NewsAndMediaReportService;
import com.sp2p.service.PublicModelService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.util.AmountUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的理财控制层
 */
public class MyFinanceAction extends BaseAppAction {

    public static        Log  log              = LogFactory.getLog(MyFinanceAction.class);
    private static final long serialVersionUID = 1L;

    private FinanceService            financeService;
    private SelectedService           selectedService;
    private Map<String, String>       investDetailMap;
    //	private NewsService newsService;
    private NewsAndMediaReportService newsAndMediaReportService;
    private PublicModelService        publicModelService;
    //	private SuccessStoryService successStoryService;
    //-add by C_J -- 标种类型  历史记录
    private ShoveBorrowTypeService    shoveBorrowTypeService;
    //	private LinksService linksService;
    //	private MediaReportService mediaReportService;
    //--
    //-add by houli
    private BorrowService             borrowService;
    private UserService               userService;
    @Autowired
    private IUserService              userService1;
    private BorrowManageService       borrowManageService;
    //--
    private List<Map<String, Object>> borrowMSGMap;

    private List<Map<String, Object>> borrowPurposeList;
    private List<Map<String, Object>> borrowDeadlineList;
    private List<Map<String, Object>> borrowAmountList;
    private List<Map<String, Object>> linksList;
    private List<Map<String, Object>> meikuList;

    /**
     * @throws IOException
     * @MethodName: financeList
     * @Param: FrontMyFinanceAction
     * @Author: gang.lv
     * @Date: 2013-3-4 下午08:44:15
     * @Return:
     * @Descb:

     * @Throws:
     */
    public String financeList() throws Exception {
        // 前台显示列表类型
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            Map<String, String> infoMap = this.getAppInfoMap();
            String mode = infoMap.get("mode");
            String title = infoMap.get("title");
            String paymentMode = infoMap.get("paymentMode");
            String purpose = infoMap.get("purpose");
            String deadline = infoMap.get("deadline");
            String reward = infoMap.get("reward");
            String arStart = infoMap.get("arStart");
            String arEnd = infoMap.get("arEnd");
            String type = infoMap.get("type");
            pageBean.setPageNum(infoMap.get("curPage"));

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
                        borrowType = borrowType.substring(1, borrowType.length());
                    }
                } else {
					if (StringUtils.isNumericSpace(type)) {
						borrowType = type;
					}
				}
			}
			if ("1".equals(mode)) {
				// 全部借款列表,显示1 等待资料 2 正在招标中 3 已满标
				borrowStatus = "(1,2,3,4,5)";
				// 查询条件中的借款方式
				if (StringUtils.isNotBlank(borrowType)) {
					borrowWay = "(" + borrowType + ")";
				}
			} else if ("2".equals(mode)) {
				// 实地认证的借款
				borrowWay = "(" + IConstants.BORROW_TYPE_FIELD_VISIT + ")";
			} else if ("3".equals(mode)) {
				// 信用认证的借款
				borrowWay = "(" + IConstants.BORROW_TYPE_GENERAL + ")";
			} else if ("4".equals(mode)) {
				// 机构担保的借款
				borrowWay = "(" + IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE
						+ ")";
			} else if ("5".equals(mode)) {
				// 最近成功的借款列表，显示4还款中 5 已还完
				borrowStatus = "(4,5)";
			}else if("6".equals(mode)){
				//正在招标中的借款
				borrowStatus = "(2)";
			}
			else{
				borrowStatus = "(1,2,3,4,5)";
			}
			financeService.queryBorrowByCondition(borrowStatus, borrowWay, title,
					paymentMode, purpose, deadline, reward, arStart, arEnd,
					IConstants.SORT_TYPE_DESC, pageBean,0);
	
			borrowPurposeList = selectedService
			.borrowPurpose();
			jsonMap.put("borrowPurposeList", borrowPurposeList);
			jsonMap.put("borrowDeadlineList", borrowDeadlineList);
			jsonMap.put("borrowAmountList", borrowAmountList);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (IOException e) {
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

//	/**
//	 * @throws IOException 
//	 * @MethodName: financeList
//	 * @Param: FrontMyFinanceAction
//	 * @Author: gang.lv
//	 * @Date: 2013-3-4 下午08:44:15
//	 * @Return:
//	 * @Descb: 正在招标中的借款
//	 * @Throws:
//	 */
//	public String financeBorrowingList() throws SQLException, DataException, IOException {
//		// 前台显示列表类型
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		try {
//			Map<String, String> infoMap = this.getAppInfoMap();
//			String title = infoMap.get("title");
//			String paymentMode = infoMap.get("paymentMode");
//			String purpose = infoMap.get("purpose");
//			String deadline = infoMap.get("deadline");
//			String reward = infoMap.get("reward");
//			String arStart = infoMap.get("arStart");
//			String arEnd = infoMap.get("arEnd");
//			String type = infoMap.get("type");
//			pageBean.setPageNum(infoMap.get("curPage"));
//			
//			pageBean.setPageSize(IConstants.PAGE_SIZE_10);
//			String borrowWay = "";
//			String borrowStatus = "";
//			String borrowType = "";
//			// 截取查询的类型，防止非常规操作
//			if (StringUtils.isNotBlank(type)) {
//				String[] types = type.split(",");
//				if (types.length > 0) {
//					for (int n = 0; n < types.length; n++) {
//						// 是数字类型则添加到borrowType中
//						if (StringUtils.isNumericSpace(types[n])) {
//							borrowType += "," + types[n];
//						}
//					}
//					if (StringUtils.isNotBlank(borrowType)) {
//						borrowType = borrowType.substring(1, borrowType.length());
//					}
//				} else {
//					if (StringUtils.isNumericSpace(type)) {
//						borrowType = type;
//					}
//				}
//			}
//				// 全部借款列表,显示1 等待资料 2 正在招标中 3 已满标
//				borrowStatus = "(1,2,3,4,5)";
//				// 查询条件中的借款方式
//				if (StringUtils.isNotBlank(borrowType)) {
//					borrowWay = "(" + borrowType + ")";
//				}
//			financeService.queryBorrowByCondition(borrowStatus, borrowWay, title,
//					paymentMode, purpose, deadline, reward, arStart, arEnd,
//					IConstants.SORT_TYPE_DESC, pageBean);
//	
////			this.setRequestToParamMap();
//			jsonMap.put("borrowPurposeList", borrowPurposeList);
//			jsonMap.put("pageBean", pageBean);
//			jsonMap.put("error", "-1");
//			jsonMap.put("msg", "查询成功");
//			JSONUtils.printObject(jsonMap);
//		} catch (IOException e) {
//			paramMap.put("error", "1");
//			paramMap.put("msg", "未知异常");
//			JSONUtils.printObject(paramMap);
//			log.error(e);
//		}
//		return null;
//	}
	
	/**
	 * @MethodName: financeLastestList
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:29:33
	 * @Return:
	 * @Descb: 最新借款列表前10条记录
	 * @Throws:
	 */
	public String financeLastestList() throws SQLException, DataException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> mapList = financeService
					.queryLastestBorrow();
			request().setAttribute("mapList", mapList);
			jsonMap.put("mapList", mapList);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @MethodName: investRank
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午11:24:23
	 * @Return:
	 * @Descb: 投资排名前20条记录
	 * @Throws:
	 */
	public String investRank() {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> rankList = new ArrayList<Map<String,Object>>();
			Map<String, String> infoMap = this.getAppInfoMap();
			String num = infoMap.get("number");
			if(StringUtils.isBlank(num)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请选择查询的方式");
				JSONUtils.printObject(jsonMap);
			}
			int number =Convert.strToInt( num,1);
			if(number ==1){
				//当前年
				  rankList = financeService.investRank(1,8);
			}else{
				// 当月
				  rankList = financeService.investRank(3,8);
			}
			
//			request().setAttribute("rankList", rankList);
			jsonMap.put("rankList", rankList);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException 
	 * @MethodName: index
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:46:12
	 * @Return:
	 * @Descb: 首页加载内容
	 * @Throws:
	 */
	public String index() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			Map<String, String> totalRiskMap = financeService.queryTotalRisk();
			Map<String, String> currentRiskMap = financeService.queryCurrentRisk();
			jsonMap.put("totalRiskMap", totalRiskMap);
			jsonMap.put("currentRiskMap", currentRiskMap);
	        //最新借款列表
			List<Map<String, Object>> mapList = financeService
			.queryLastestBorrow();
//			request().setAttribute("mapList", mapList);
			jsonMap.put("mapList", mapList);
			//排名前8条记录
			//当前年投标统计
			// 投标排名查询
			// 当年
			List<Map<String, Object>> rankList = financeService.investRank(1,8);
//			request().setAttribute("rankList",rankList);
			jsonMap.put("rankList", rankList);
			//公告
			List<Map<String,Object>> newsList = new ArrayList<Map<String,Object>>();
			pageBean.setPageSize(5);
	//		newsService.frontQueryNewsPage(pageBean);
			newsAndMediaReportService.frontQueryNewsPage(pageBean);
			newsList = pageBean.getPage();
			pageBean.setPage(null);
//			request().setAttribute("newsList", newsList);
			jsonMap.put("newsList", newsList);
			//成功故事
			List<Map<String,Object>> storyList = new ArrayList<Map<String,Object>>();
			pageBean.setPageSize(2);
	//	    successStoryService.querySuccessStoryPage(pageBean);
		    publicModelService.querySuccessStoryPage(pageBean);
		    storyList = pageBean.getPage();
			pageBean.setPage(null);
//			request().setAttribute("storyList", storyList);
			jsonMap.put("storyList", storyList);
			//友情链接
			if (IConstants.ISDEMO.equals("1")) {
				pageBean.setPageSize(7);
			}else{
				pageBean.setPageSize(IConstants.PAGE_SIZE_20);
			}
	//		linksService.queryLinksPage(pageBean);
			publicModelService.queryLinksPage(pageBean);
			linksList =pageBean.getPage();
			pageBean.setPage(null);
//			request().setAttribute("linksList", linksList);
			jsonMap.put("linksList", linksList);
			//媒体报道 取 2条记录
			pageBean.setPageSize(2);
	//		mediaReportService.queryMediaReportPage(pageBean);
			newsAndMediaReportService.queryMediaReportPage(pageBean);
			meikuList = pageBean.getPage();
			pageBean.setPage(null);
//			request().setAttribute("meikuList", meikuList);
			jsonMap.put("meikuList", meikuList);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * @MethodName: financeToolInit
	 * 
	 * @Param: FrontMyFinanceAction
	 * 
	 * @Author: gang.lv
	 * 
	 * @Date: 2013-3-4 下午01:30:25
	 * 
	 * @Return:理财工具箱
	 * 
	 * @Descb:
	 * 
	 * @Throws:
	 */
	public String financeToolInit() {
		return "success";
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 * @MethodName: financeDetail
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:40:38
	 * @Return:
	 * @Descb: 理财中的借款明细
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
    public String financeDetail() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			Map<String, String> infoMap = this.getAppInfoMap();
			String idStr = infoMap.get("borrowId");
			if(StringUtils.isBlank(idStr)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!"".equals(idStr) && StringUtils.isNumericSpace(idStr)) {
				Long id = Convert.strToLong(idStr, -1);
				// 借款详细
				Map<String, String> borrowDetailMap = financeService
						.queryBorrowDetailById(id);
						
				if (borrowDetailMap != null && borrowDetailMap.size() > 0) {
					//-- 7 - 9
					//查询借款信息得到借款时插入的平台收费标准
					Map<String,String> map = borrowManageService.queryBorrowInfo(id);
					//得到收费标准的json代码
					String feelog = Convert.strToStr(map.get("feelog"), "");
					Map<String,Double> feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
					//得到收费标准的说明信息
					String feestate = Convert.strToStr(map.get("feestate"), "");
					@SuppressWarnings("unused")
                    Map<String,String> feestateMap = (Map<String,String>)JSONObject.toBean(JSONObject.fromObject(feestate),HashMap.class);
					//--end
					String nid_log = borrowDetailMap.get("nid_log");
					Map<String, String> person = userService.queryPersonById(Convert.strToLong(borrowDetailMap.get("publisher"), -1l));
					paramMap.put("nativePlacePro", "-1");
					paramMap.put("nativePlaceCity",  "-1");
					if(person!= null && person.size()>0){
						paramMap.put("nativePlacePro", person.get("nativePlacePro"));
						paramMap.put("nativePlaceCity", person.get("nativePlaceCity"));
					}
					Map<String,String>  TypeLogMap = null;
					if (StringUtils.isNotBlank(nid_log)) {
						TypeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
						int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"),-1);
						jsonMap.put("subscribes",stauts);
					}
				
					double borrowSum = Convert.strToDouble(borrowDetailMap.get("borrowSum")+"", 0);
					double annualRate = Convert.strToDouble(borrowDetailMap.get("annualRate")+"", 0);
					int deadline = Convert.strToInt(borrowDetailMap.get("deadline")+"", 0);
					int paymentMode = Convert.strToInt(borrowDetailMap.get("paymentMode")+"", -1);
					int isDayThe = Convert.strToInt(borrowDetailMap.get("isDayThe")+"", 1);
					double investAmount = 10000;
					String earnAmount = "";
					if(borrowSum < investAmount){
						investAmount = borrowSum;
					}
					AmountUtil au = new AmountUtil();
					Map<String,String> earnMap = null;
					/*Map<String, Object> platformCostMap = getPlatformCost();*/
					/*double costFee = Convert.strToDouble(platformCostMap
							.get(IAmountConstants.INVEST_FEE_RATE)
							+ "", 0);*/
					double costFee = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE)+"",0);
					if(paymentMode == 1){
						//按月等额还款
						earnMap = au.earnCalculateMonth(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2,costFee);
						earnAmount = earnMap.get("msg")+"";
					}else if(paymentMode == 2){
						//先息后本
						earnMap = au.earnCalculateSum(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2);
						earnAmount = earnMap.get("msg")+"";
					}else if(paymentMode == 3){
						//秒还
						earnMap = au.earnSecondsSum(investAmount, borrowSum, annualRate, deadline,0, 2);
						earnAmount = earnMap.get("msg")+"";
					}
					//----------add by houli 借款类型判断，前台借款详细信息中需要显示
					String borrowWay = borrowDetailMap.get("borrowWay");
					if(borrowWay.equals(IConstants.BORROW_TYPE_NET_VALUE)){
						jsonMap.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
					}else if(borrowWay.equals(IConstants.BORROW_TYPE_SECONDS)){
						jsonMap.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
					}else if(borrowWay.equals(IConstants.BORROW_TYPE_GENERAL)){
						jsonMap.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
					}else if(borrowWay.equals(IConstants.BORROW_TYPE_FIELD_VISIT)){
						jsonMap.put("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
					}else if(borrowWay.equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)){
						jsonMap.put("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
					}
					//--------------------
					
					//催收记录
					List<Map<String, Object>> collection = financeService.queryCollectionByid(id);
					if(collection != null && collection.size()>0)
						jsonMap.put("colSize",collection.size());
					
//					request().setAttribute("earnAmount", earnAmount);
					// 每次点击借款详情时新增浏览量
					financeService.addBrowseCount(id);
//					request().setAttribute("borrowDetailMap", borrowDetailMap);
					// 借款人资料
					Map<String, String> borrowUserMap = financeService
							.queryUserInfoById(id);
//					request().setAttribute("borrowUserMap", borrowUserMap);
					// 借款人认证资料
					List<Map<String, Object>> list = financeService
							.queryUserIdentifiedByid(id);
//					request().setAttribute("list", list);
					// 投资记录
					List<Map<String, Object>> investList = financeService
							.queryInvestByid(id);
//					request().setAttribute("investList", investList);
					request().setAttribute("idStr", idStr);
					Map<String,String> borrowRecordMap = financeService.queryBorrowRecord(id);
//					request().setAttribute("borrowRecordMap", borrowRecordMap);
					//-----------add by houli
					String wStatus = judgeStatus(Convert.strToInt(borrowWay, -1),
							Convert.strToLong(borrowDetailMap.get("publisher"), -100));
					if(wStatus == null){
						request().setAttribute("wStatus", "");
					}else{
						request().setAttribute("wStatus", wStatus);
					}
					//-------end by houli
					jsonMap.put("borrowRecordMap",borrowRecordMap);
					jsonMap.put("borrowDetailMap",borrowDetailMap);
					jsonMap.put("borrowUserMap",borrowUserMap);
					jsonMap.put("investList",investList);
					jsonMap.put("earnAmount",earnAmount);
					jsonMap.put("list",list);
					jsonMap.put("idStr",idStr);
					jsonMap.put("wStatus",wStatus);
					jsonMap.put("error", "-1");
					jsonMap.put("msg", "查询成功");
					JSONUtils.printObject(jsonMap);
				} else {
					jsonMap.put("error", "2");
					jsonMap.put("msg", "没有这条借款信息");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			} else {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请指定要查看的借款");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		}catch(Exception e){
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 点击查看详情的时候，判断某标的的状态
	 * @param tInt
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	private String  judgeStatus(int tInt,Long userId) throws SQLException, DataException{
		if(tInt < 3){//秒还、净值标的
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if(aa < 0){
				return "waitBorrow";
				
			}
		}else{//其它借款
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if(aa < 0){
				return "waitBorrow";
			}else{
				Long bb = borrowService.queryBaseFiveApprove(userId);
				if(bb < 0){
					return "waitBorrow";
				}
			}
		}
		return null;
	}
	
	/**
	 * 债权转让借款详情
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String queryDebtBorrowDetail() throws SQLException, DataException, IOException{
		return financeDetail();
	}

	/**
	 * @throws IOException 
	 * @MethodName: financeAudit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:26:02
	 * @Return:
	 * @Descb: 借款人认证资料
	 * @Throws:
	 */
	public String financeAudit() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if(StringUtils.isBlank(id)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款人ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			// 借款人认证资料
			List<Map<String, Object>> list = financeService
					.queryUserIdentifiedByid(idLong);
			request().setAttribute("auditList", list);
			jsonMap.put("auditList", list);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException 
	 * @MethodName: financeRepay
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:27:02
	 * @Return:
	 * @Descb: 借款人还款记录
	 * @Throws:
	 */
	public String financeRepay() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if(StringUtils.isBlank(id)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			// 借款人还款记录
			List<Map<String, Object>> list = financeService
					.queryRePayByid(idLong);
			request().setAttribute("repayList", list);
			jsonMap.put("repayList", list);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	
	/**   
	 * @throws IOException 
	 * @MethodName: financeCollection  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:29:12
	 * @Return:    
	 * @Descb: 借款人催款记录
	 * @Throws:
	*/
	public String financeCollection() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if(StringUtils.isBlank(id)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			// 借款人催款记录
			List<Map<String, Object>> list = financeService
					.queryCollectionByid(idLong);
//			request().setAttribute("collectionList", list);
			jsonMap.put("collectionList",list);
			jsonMap.put("error",  "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @MethodName: financeInvestInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:20:57
	 * @Return:
	 * @Descb: 理财投标初始化
	 * @Throws:
	 */
	public String financeInvestInit() throws Exception {
		Map<String, String> jsonMap = new HashMap<String, String>();
		AccountUserDo user = null;
		try{
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if(userId != -1){
				user =  userService1.getById(userId);
			}
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if(StringUtils.isBlank(id)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			if (idLong == -1) {
				// 非法操作直接返回
				jsonMap.put("error", "2");
				jsonMap.put("msg", IConstants.ACTOIN_ILLEGAL);
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 获取用户认证进行的步骤
			if (user.getAuthStep() == 1) {
				// 个人信息认证步骤
				return "querBaseData";
			} else if (user.getAuthStep() == 2) {
				// 工作信息认证步骤
				return "querWorkData";
			} else if (user.getAuthStep() == 3) {
				// VIP申请认证步骤
				return "quervipData";
			} else if (user.getAuthStep() == 4) {
				// 上传资料认证步骤
				return "userPassData";
			}
			Map<String, String> investMap = financeService.getInvestStatus(idLong);
			String nid_log= "";
			if(investMap!=null && investMap.size()>0){
				 nid_log = Convert.strToStr(investMap.get("nid_log"),"");
				 Map<String,String>  typeLogMap = null;
					if (StringUtils.isNotBlank(nid_log)) {
						typeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
						int stauts = Convert.strToInt(typeLogMap.get("subscribe_status"),-1);
						request().setAttribute("subscribes",stauts );
						request().setAttribute("investMap",investMap );
					}
				String hasPWD = investMap.get("hasPWD") == null?"-1":investMap.get("hasPWD");
				investDetailMap = financeService.queryBorrowInvest(idLong);
	
				String userid = investDetailMap.get("userId") == null ? ""
						: investDetailMap.get("userId");
				if (userid.equals(user.getId().toString())) {
					// 不满足投标条件,返回
					jsonMap.put("error", "3");
					jsonMap.put("msg", "不能投标自己发布的借款");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				
				session().setAttribute("investStatus","ok");
				Map<String, String> userMap = financeService.queryUserMonney(user
						.getId());
				request().setAttribute("userMap", userMap);
				session().setAttribute("hasPWD", hasPWD);
				
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "初始化成功");
				JSONUtils.printObject(jsonMap);
	//			if(!"-1".equals(hasPWD)){
	//				request().setAttribute("id", id);
	//				return "pwdBorrow";
	//			}
			} else {
				// 不满足投标条件,返回
				jsonMap.put("error", "4");
				jsonMap.put("msg", "该借款投标状态已失效");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		}catch(Exception e){
			jsonMap.put("error", "5");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	
	/**   
	 * @throws Exception 
	 * @MethodName: financeInvestLoad  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:04:52
	 * @Return:    
	 * @Descb: 输入密码后的投标
	 * @Throws:
	*/
	public String financeInvestLoad() throws Exception{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			AccountUserDo user = null;
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if(userId != -1){
				user =  userService1.getById(userId);
			}
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if(StringUtils.isBlank(id)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String investPWD =  appInfoMap.get("investPWD");
			if(StringUtils.isBlank(investPWD)){
				jsonMap.put("error", "2");
				jsonMap.put("msg", "密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
	    
			if (idLong == -1) {
				// 非法操作直接返回
				jsonMap.put("error", "3");
				jsonMap.put("msg", IConstants.ACTOIN_ILLEGAL);
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> investPWDMap = financeService.getInvestPWD(idLong,investPWD);
			if (investPWDMap == null || investPWDMap.size() ==0) {
//				this.addFieldError("paramMap['investPWD']", "投标密码错误");
				jsonMap.put("error", "4");
				jsonMap.put("msg", "投标密码错误");
				JSONUtils.printObject(jsonMap);
				return null;						
			}
			// 判断是否进行了资料审核
			Object object = session().getAttribute("investStatus");
			if (object == null) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "投标资料未审核通过");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> investMap = financeService.getInvestStatus(idLong);
			if (investMap != null && investMap.size() > 0) {
				investDetailMap = financeService.queryBorrowInvest(idLong);

				String userid = investDetailMap.get("userId") == null ? ""
						: investDetailMap.get("userId");
				if (userid.equals(user.getId().toString())) {
					// 不满足投标条件,返回
					jsonMap.put("error", "6");
					jsonMap.put("msg", "不能投标自己发布的借款");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				Map<String, String> userMap = financeService.queryUserMonney(user
						.getId());
				jsonMap.put("userMap", userMap);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "投标初始化成功");
				JSONUtils.printObject(jsonMap);
			} else {
				// 不满足投标条件,返回
				jsonMap.put("error", "7");
				jsonMap.put("msg", "该借款投标状态已失效");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	/**   
	 * @MethodName: financeInvest  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv+
	 * @Date: 2013-3-30 下午03:53:34
	 * @Return:    
	 * @Descb: 投标借款
	 * @Throws:
	*/
	public String financeInvest() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			AccountUserDo user = null;
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if(userId != -1){
				user =  userService1.getById(userId);
			}
			String code = (String) session().getAttribute("invest_checkCode");
//			String _code = paramMap.get("code") == null ? "" : paramMap`
//					.get("code");
//			if (!code.equals(_code)) {
//				this.addFieldError("paramMap['code']", "验证码错误");
//				return "input";
//			}
//			// 判断是否进行了资料审核
//			Object object = session().getAttribute("investStatus");
//			if (object == null) {
//				return null;
//			}
		    Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if(StringUtils.isBlank(id)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			String amount = appInfoMap.get("amount");
			if(StringUtils.isBlank(amount)){
				jsonMap.put("error", "2");
				jsonMap.put("msg", "金额不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			double amountDouble = Convert.strToDouble(amount, 0);
//			String pwd = paramMap.get("pwd") == null?"":paramMap.get("pwd");
			String hasPWD = ""+session().getAttribute("hasPWD");
			
			
		    int status =Convert.strToInt( appInfoMap.get("subscribes"),2);
		    if("1".equals(hasPWD)){
		    	String investPWD =  appInfoMap.get("investPWD") == null?"":appInfoMap.get("investPWD");
		    	if(StringUtils.isBlank(investPWD)){
					jsonMap.put("error", "3");
					jsonMap.put("msg", "密码不能为空");
					JSONUtils.printObject(jsonMap);
					return null;
				}
		    	Map<String, String> investPWDMap = financeService.getInvestPWD(idLong,investPWD);
				if (investPWDMap == null || investPWDMap.size() ==0) {
					if(status == 1){
						jsonMap.put("error", "4");
						jsonMap.put("msg", "投标密码错误");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					this.addFieldError("paramMap['investPWD']", "投标密码错误");
					return null;						
				}
		    }
		    
		    int num =0;
		    if(status ==1){
		    	double smallestFlowUnit = Convert.strToDouble(appInfoMap.get("smallestFlowUnit"), 0.0);
		    	if (smallestFlowUnit==0) {
		    		jsonMap.put("error", "5");
					jsonMap.put("msg", "操作失败");
					JSONUtils.printObject(jsonMap);
					return null;
				}
		    	String result = Convert.strToStr(appInfoMap.get("result"),"");
		    	if(StringUtils.isBlank(result)){
		    		jsonMap.put("error", "6");
					jsonMap.put("msg", "请输入购买的份数");
					JSONUtils.printObject(jsonMap);
					return null;
		    	}
		    	boolean b=result.matches("[0-9]*");
		    	if(!b){
		    		jsonMap.put("error", "7");
					jsonMap.put("msg", "请输入购买的份数");
					JSONUtils.printObject(jsonMap);
					return null;
		    	}
		    	 num = Integer.parseInt(result);
		    	if (num<1) {
		    		jsonMap.put("error", "8");
					jsonMap.put("msg", "请输入购买的份数");
					JSONUtils.printObject(jsonMap);
					return null;
				}
		    	amountDouble = num * smallestFlowUnit;
		    }
//		    user = new User();
//		    user.setId(232l);
//		    user.setUserName("ling");
		    Map<String, String> result = financeService.addBorrowInvest(idLong, user
					.getId(),"", amountDouble,getBasePath(),user.getUsername(),status,num);
			if("".equals(result.get("ret_desc"))){
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "投标成功");
				JSONUtils.printObject(jsonMap);
			}
			else{
				jsonMap.put("error", "10");
				jsonMap.put("msg", "投标失败");
				JSONUtils.printObject(jsonMap);
			}
		}catch(Exception e){
			jsonMap.put("error", "9");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException 
	 * @MethodName: borrowMSGInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:08:51
	 * @Return:
	 * @Descb: 借款留言初始化
	 * @Throws:
	 */
	public String borrowMSGInit() throws SQLException, DataException, IOException {
		Map<String, Object>	jsonMap = new HashMap<String, Object>();
		try {
			   Map<String, String> appInfoMap = getAppInfoMap();
				String id = appInfoMap.get("id");
				if(StringUtils.isBlank(id)){
					jsonMap.put("error", "1");
					jsonMap.put("msg", "借款ID不能为空");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				long idLong = Convert.strToLong(id, -1);
				String pageNum = appInfoMap.get("curPage");
				if (StringUtils.isNotBlank(pageNum)) {
					pageBean.setPageNum(pageNum);
				}
				pageBean.setPageSize(IConstants.PAGE_SIZE_6);
				if (idLong == -1) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "借款ID不能为空");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				financeService.queryBorrowMSGBord(idLong, pageBean);
//				request().setAttribute("id", id);
				jsonMap.put("id", id);
				jsonMap.put("pageBean", pageBean);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "初始化成功");
				JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "9");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
	 
		return null;
	}

	/**
	 * @throws IOException
	 * @throws SQLException
	 * @MethodName: addBorrowMSG
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:09:06
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public String addBorrowMSG() throws IOException, SQLException {
		
		Map<String, String>	jsonMap = new HashMap<String, String>();
		try {
			   Map<String, String> appInfoMap = getAppInfoMap();
			   Map<String, String> authMap = getAppAuthMap();
				long userId = Convert.strToLong(authMap.get("uid"), -1);
				if(userId == -1){
					jsonMap.put("error", "1");
					jsonMap.put("msg", "用户不存在");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				String id = appInfoMap.get("id");
				if(StringUtils.isBlank(id)){
					jsonMap.put("error", "1");
					jsonMap.put("msg", "借款ID不能为空");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			long idLong = Convert.strToLong(id, -1);
			String msgContent = appInfoMap.get("msg");
			if(StringUtils.isBlank(msgContent)){
				jsonMap.put("error", "4");
				jsonMap.put("msg", "留言内容不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long returnId = -1;
			returnId = financeService .addBorrowMSG(idLong, userId, msgContent);
			if (returnId <= 0) {
				jsonMap.put("error", "5");
				jsonMap.put("msg",  IConstants.ACTION_FAILURE);
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				// 添加成功返回值
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "添加成功");
				JSONUtils.printObject(jsonMap);
				
			}
		}catch (Exception e) {
			jsonMap.put("error", "6");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @throws DataException
	 * @MethodName: focusOnBorrow
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午09:06:16
	 * @Return:
	 * @Descb: 我关注的借款
	 * @Throws:
	 */
//	public String focusOnBorrow() throws SQLException, IOException,
//			DataException {
//		Map<String, String>	jsonMap = new HashMap<String, String>();
//		try{
//			
//			 Map<String, String> appInfoMap = getAppInfoMap();
//			 User user =  session().getAttribute("user");
//			 String id = appInfoMap.get("id");
//			 if(StringUtils.isBlank(id)){
//				jsonMap.put("error", "1");
//				jsonMap.put("msg", "借款ID不能为空");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			long returnId = -1L;
//			long idLong = Convert.strToLong(id, -1);
//			Map<String, String> map = financeService.hasFocusOn(idLong, user
//					.getId(), IConstants.FOCUSON_BORROW);
//			if (map != null && map.size() > 0) {
//				jsonMap.put("error", "2");
//				jsonMap.put("msg", "您已关注过该借款");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//	
//			returnId = financeService.addFocusOn(idLong, user.getId(),
//					IConstants.FOCUSON_BORROW);
//			if (returnId <= 0) {
//				jsonMap.put("error", "3");
//				jsonMap.put("msg",  IConstants.ACTION_FAILURE);
//				JSONUtils.printObject(jsonMap);
//				return null;
//			} else {
//				jsonMap.put("error", "-1");
//				jsonMap.put("msg",  "关注成功");
//				JSONUtils.printObject(jsonMap);
//			}
//		}catch (Exception e) {
//			jsonMap.put("error", "6");
//			jsonMap.put("msg", "未知异常");
//			JSONUtils.printObject(jsonMap);
//			log.error(e);
//			e.printStackTrace();
//		}
//		return null;
//	}

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
//	public String focusOnUser() throws SQLException, IOException, DataException {
//		Map<String, String>	jsonMap = new HashMap<String, String>();
//		try {
//			 Map<String, String> appInfoMap = getAppInfoMap();
//			 User user =  session().getAttribute("user");
//			 String id = appInfoMap.get("id");
//			 if(StringUtils.isBlank(id)){
//				jsonMap.put("error", "1");
//				jsonMap.put("msg", "用户ID不能为空");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			long returnId = -1L;
//			long idLong = Convert.strToLong(id, -1);
//			Map<String, String> map = financeService.hasFocusOn(idLong, user
//					.getId(), IConstants.FOCUSON_USER);
//			if (map != null && map.size() > 0) {
//				jsonMap.put("error", "2");
//				jsonMap.put("msg", "您已关注过该用户");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			returnId = financeService.addFocusOn(idLong, user.getId(),
//					IConstants.FOCUSON_USER);
//			if (returnId <= 0) {
//				jsonMap.put("error", "2");
//				jsonMap.put("msg", IConstants.ACTION_FAILURE);
//				JSONUtils.printObject(jsonMap);
//				return null;
//			} else {
//				jsonMap.put("error", "2");
//				jsonMap.put("msg", "关注成功!");
//				JSONUtils.printObject(jsonMap);
//			}
//		} catch (Exception e) {
//			jsonMap.put("error", "6");
//			jsonMap.put("msg", "未知异常");
//			JSONUtils.printObject(jsonMap);
//			log.error(e);
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * @throws IOException 
	 * @MethodName: mailInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:31
	 * @Return:
	 * @Descb: 发送站内信初始化
	 * @Throws:
	 */
	public String mailInit() throws IOException {
		Map<String, String>	jsonMap = new HashMap<String, String>();
		try {
			 Map<String, String> appInfoMap = getAppInfoMap();
			 String id = appInfoMap.get("id");
			 if(StringUtils.isBlank(id)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		String userName = appInfoMap.get("username");
		 if(StringUtils.isBlank(userName)){
			jsonMap.put("error", "2");
			jsonMap.put("msg", "用户名不能为空");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		request().setAttribute("id", id);
		request().setAttribute("userName", userName);
		jsonMap.put("id", id);
		jsonMap.put("userName", userName);
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "初始化成功");
		JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException 
	 * @MethodName: reportInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:48
	 * @Return:
	 * @Descb: 举报用户初始化
	 * @Throws:
	 */
	public String reportInit() throws IOException {
		Map<String, String>	jsonMap = new HashMap<String, String>();
		try {
			 Map<String, String> appInfoMap = getAppInfoMap();
			 String id = appInfoMap.get("id");
			 if(StringUtils.isBlank(id)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String userName = appInfoMap.get("username");
			 if(StringUtils.isBlank(userName)){
				jsonMap.put("error", "2");
				jsonMap.put("msg", "用户名不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			request().setAttribute("id", id);
			request().setAttribute("userName", userName);
			jsonMap.put("id", id);
			jsonMap.put("userName", userName);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "初始化成功");
			JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "未知异常");
				JSONUtils.printObject(jsonMap);
				log.error(e);
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * @Descb: 发送邮件
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public String mailAdd() throws IOException, SQLException {
		Map<String, String>	jsonMap = new HashMap<String, String>();
		AccountUserDo user = null;
		try {
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if(userId != -1){
				user =  userService1.getById(userId);
			}
			
			String code = (String) session().getAttribute("code_checkCode");
			 Map<String, String> appInfoMap = getAppInfoMap();
			 String _code = appInfoMap.get("code");
			 if(StringUtils.isBlank(_code)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!code.equals(_code)) {
				this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
				jsonMap.put("error", "2");
				jsonMap.put("msg", "验证码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String id = appInfoMap.get("id");
			if (!code.equals(id)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long reciver = Convert.strToLong(id, -1);
			String title = appInfoMap.get("title");
			if (!code.equals(title)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "标题不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String content = appInfoMap.get("content");
			if (!code.equals(content)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "内容不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long returnId = -1;
			Integer enable=user.getEnable();
			if(enable==3){
				jsonMap.put("error", "6");
				jsonMap.put("msg", "用户被禁用");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			returnId = financeService.addUserMail(reciver, user.getId(), title,
					content, IConstants.MALL_TYPE_COMMON);
			if (returnId <= 0) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", IConstants.ACTION_FAILURE);
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				// 添加成功返回值
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "添加成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @MethodName: reportAdd
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:16:11
	 * @Return:
	 * @Descb: 添加用户举报
	 * @Throws:
	 */
	public String reportAdd() throws SQLException, IOException {
		Map<String, String>	jsonMap = new HashMap<String, String>();
		AccountUserDo user = null;
		try {
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if(userId != -1){
				user =  userService1.getById(userId);
			}
			String code = (String) session().getAttribute("code_checkCode");
			 Map<String, String> appInfoMap = getAppInfoMap();
			 String _code = appInfoMap.get("code");
			 if(StringUtils.isBlank(_code)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!code.equals(_code)) {
				this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
				jsonMap.put("error", "2");
				jsonMap.put("msg", "验证码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String id = appInfoMap.get("id");
			if (!code.equals(id)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long reporter = Convert.strToLong(id, -1);
			String title = appInfoMap.get("title");
			if (!code.equals(id)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "标题不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String content = appInfoMap.get("content");
			if (!code.equals(content)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "内容不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long returnId = -1;
			returnId = financeService.addUserReport(reporter, user.getId(), title,
					content);
			if (returnId <= 0) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", IConstants.ACTION_FAILURE);
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				// 添加成功返回值
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "举报成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	
	/**   
	 * @throws IOException 
	 * @MethodName: showImg  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:24:03
	 * @Return:    
	 * @Descb: 查看图片
	 * @Throws:
	*/
	public String showImg() throws SQLException, DataException, IOException{
		Map<String, Object>	jsonMap = new HashMap<String, Object>();
		try{
			 Map<String, String> appInfoMap = getAppInfoMap();
			 Map<String, String> appAuthMap = getAppAuthMap();
			 String typeId = appInfoMap.get("typeId");
			 if(StringUtils.isBlank(typeId)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "类型ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			 String userId =  appAuthMap.get("userId");
			if (StringUtils.isBlank(userId)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long typeIdLong = Convert.strToLong(typeId, -1);
			long userIdLong = Convert.strToLong(userId, -1);
			List<Map<String,Object>> imgList = financeService.queryUserImageByid(typeIdLong, userIdLong);
//			request().setAttribute("imgList", imgList);
			jsonMap.put("imgList", imgList);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查看成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	public Map<String, String> getInvestDetailMap() throws SQLException, IOException {
		Map<String, String>	jsonMap = new HashMap<String, String>();
		try{
			 Map<String, String> appInfoMap = getAppInfoMap();
			 String id = appInfoMap.get("id");
			 if(StringUtils.isBlank(id)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			if (investDetailMap == null) {
				investDetailMap = financeService.queryBorrowInvest(idLong);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "查询成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

//	public NewsService getNewsService() {
//		return newsService;
//	}
//
//	public void setNewsService(NewsService newsService) {
//		this.newsService = newsService;
//	}

//	public SuccessStoryService getSuccessStoryService() {
//		return successStoryService;
//	}
//
//	public void setSuccessStoryService(SuccessStoryService successStoryService) {
//		this.successStoryService = successStoryService;
//	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public List<Map<String, Object>> getBorrowPurposeList() throws SQLException, DataException {
		 borrowPurposeList = selectedService
			.borrowPurpose();

		return borrowPurposeList;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws SQLException, DataException {
		  borrowDeadlineList = selectedService
			.borrowDeadline();

		return borrowDeadlineList;
	}

	public List<Map<String, Object>> getBorrowAmountList() throws SQLException, DataException {
		 borrowAmountList = selectedService
			.borrowAmountRange();
		return borrowAmountList;
	}

	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setBorrowMSGMap(List<Map<String, Object>> borrowMSGMap) {
		this.borrowMSGMap = borrowMSGMap;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	public void setInvestDetailMap(Map<String, String> investDetailMap) {
		this.investDetailMap = investDetailMap;
	}


	public void setBorrowPurposeList(List<Map<String, Object>> borrowPurposeList) {
		this.borrowPurposeList = borrowPurposeList;
	}

	public void setBorrowDeadlineList(List<Map<String, Object>> borrowDeadlineList) {
		this.borrowDeadlineList = borrowDeadlineList;
	}

	public void setBorrowAmountList(List<Map<String, Object>> borrowAmountList) {
		this.borrowAmountList = borrowAmountList;
	}

//	public void setLinksService(LinksService linksService) {
//		this.linksService = linksService;
//	}

	public List<Map<String, Object>> getLinksList() {
		return linksList;
	}

	public void setLinksList(List<Map<String, Object>> linksList) {
		this.linksList = linksList;
	}

//	public void setMediaReportService(MediaReportService mediaReportService) {
//		this.mediaReportService = mediaReportService;
//	}

	public List<Map<String, Object>> getMeikuList() {
		return meikuList;
	}

	public void setMeikuList(List<Map<String, Object>> meikuList) {
		this.meikuList = meikuList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public NewsAndMediaReportService getNewsAndMediaReportService() {
		return newsAndMediaReportService;
	}

	public void setNewsAndMediaReportService(
			NewsAndMediaReportService newsAndMediaReportService) {
		this.newsAndMediaReportService = newsAndMediaReportService;
	}

	public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}

	
}
