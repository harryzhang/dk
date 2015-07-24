package com.sp2p.action.front;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.config.ChinaPnrConfig;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.SqlInfusion;
import com.shove.vo.PageBean;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BorrowService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.NewsAndMediaReportService;
import com.sp2p.service.PublicModelService;
import com.sp2p.service.RegionService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.ChinaPnRInterface;

/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的理财控制层
 */
public class FrontMyFinanceAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);

	private static final long serialVersionUID = 1L;

	private FinanceService financeService;

	public MyHomeService getMyHomeService() {
		return myHomeService;
	}

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	private MyHomeService myHomeService;
	
	private SelectedService selectedService;

	private Map<String, String> investDetailMap;

	private NewsAndMediaReportService newsService;

	// -add by C_J -- 标种类型 历史记录
	private ShoveBorrowTypeService shoveBorrowTypeService;

	private PublicModelService publicModelService;

	private RegionService regionService;

	private UserService userService;

	// -add by houli
	private BorrowService borrowService;

	private BorrowManageService borrowManageService;

	@SuppressWarnings("unused")
	private List<Map<String, Object>> borrowMSGMap;

	private List<Map<String, Object>> borrowPurposeList;

	private List<Map<String, Object>> borrowDeadlineList;

	private List<Map<String, Object>> borrowAmountList;

	private List<Map<String, Object>> linksList;

	private List<Map<String, Object>> meikuList;

	private List<Map<String, Object>> meikuStick;

	private List<Map<String, Object>> listsGGList;

	private List<Map<String, Object>> bannerList;

	private List<Map<String, Object>> provinceList;

	private List<Map<String, Object>> cityList;

	private long workPro = -1L;// 初始化省份默认值

	private long cityId = -1L;// 初始化话默认城市

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: financeInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-4 上午11:16:54
	 * @Return: String
	 * @Descb: 我的理财初始化(此方法不用了)
	 * @Throws:
	 */
	@Deprecated
	public String financeInit() throws SQLException, DataException {
		String mode = SqlInfusion.FilteSqlInfusion(request().getParameter("m") == null ? "1" : request().getParameter("m"));
		request().setAttribute("m", mode);
		String curPage = SqlInfusion.FilteSqlInfusion(request().getParameter("curPage"));
		if (StringUtils.isNotBlank(curPage)) {
			request().setAttribute("curPage", curPage);
		}

		// 初始化查询条件
		String title = SqlInfusion.FilteSqlInfusion(request().getParameter("title"));
		String paymentMode = SqlInfusion.FilteSqlInfusion(request().getParameter("paymentMode"));
		String purpose = SqlInfusion.FilteSqlInfusion(request().getParameter("purpose"));
		String raiseTerm = SqlInfusion.FilteSqlInfusion(request().getParameter("raiseTerm"));
		String reward = SqlInfusion.FilteSqlInfusion(request().getParameter("reward"));
		String arStart = SqlInfusion.FilteSqlInfusion(request().getParameter("arStart"));
		String arEnd = SqlInfusion.FilteSqlInfusion(request().getParameter("arEnd"));
		String type = SqlInfusion.FilteSqlInfusion(request().getParameter("type"));
		request().setAttribute("title", title);
		request().setAttribute("paymentMode", paymentMode);
		request().setAttribute("purpose", purpose);
		request().setAttribute("raiseTerm", raiseTerm);
		request().setAttribute("reward", reward);
		request().setAttribute("arStart", arStart);
		request().setAttribute("arEnd", arEnd);
		request().setAttribute("type", type);

		// 获取页面上需要的动态下拉列表
		request().setAttribute("borrowPurposeList", borrowPurposeList);
		request().setAttribute("borrowDeadlineList", borrowDeadlineList);
		request().setAttribute("borrowAmountList", borrowAmountList);
		return "success";
	}

	/**
	 * @MethodName: financeList
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-4 下午08:44:15
	 * @Return:
	 * @Descb: 我的理财列表
	 * @Throws:
	 */
	public String financeList() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
        Object platform = session("platform");
        int userGroup = 0;
        if (user!=null&&("appcomm".equals(platform)||"colorlifeapp".equals(platform))){
            userGroup = user.getUserGroup();
        }
		if (null != user) {
			String userName = user.getUsername();
			request().setAttribute("userName", userName);
		} else {
			request().setAttribute("userName", "");
		}

		// 前台显示列表类型
		String paymentMode = SqlInfusion.FilteSqlInfusion(request("paymentMode"));
		String purpose = SqlInfusion.FilteSqlInfusion(request("purpose"));
		String deadline = SqlInfusion.FilteSqlInfusion(request("deadline"));
		String reward = SqlInfusion.FilteSqlInfusion(request("reward"));
		String arStart = SqlInfusion.FilteSqlInfusion(request("arStart"));
		String arEnd = SqlInfusion.FilteSqlInfusion(request("arEnd"));
		String province = SqlInfusion.FilteSqlInfusion(request("province"));
		String regCity = SqlInfusion.FilteSqlInfusion(request("regCity"));
		pageBean.setPageNum(request("curPage"));
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);

		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		String borrowStatus = " (2,3,4,5) ";
		
		/*
		 * 20140610 by 刘文韬
		 * 查询标的列表 只查出来对应群组的标的
		 */
		financeService.queryBorrowByConditions(borrowStatus, null, null, paymentMode, purpose, deadline, reward, arStart, arEnd,
				IConstants.SORT_TYPE_DESC, pageBean, null, province, regCity, userGroup);
		this.setRequestToParamMap();
		return SUCCESS;
	}

	public String ajaxqueryRegions() throws SQLException, DataException, IOException {
		// Long regionId = Convert.strToLong(paramMap.get("regionId"), -1);
		Long parentId = Convert.strToLong(request("parentId"), -1);
		Integer regionType = Convert.strToInt(request("regionType"), -1);
		List<Map<String, Object>> list;
		try {
			list = regionService.queryRegionList(-1L, parentId, regionType);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		String jsonStr = JSONArray.toJSONString(list);
		JSONUtils.printStr(jsonStr);
		return null;
	}

	/**
	 * 和合年获取省市地区
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String ajaxqueryRegionsHHN() throws SQLException, DataException, IOException {
		Long parentId = Convert.strToLong(request("parentId"), -1);
		Integer regionType = Convert.strToInt(request("regionType"), -1);
		List<Map<String, Object>> list;
		try {
			list = regionService.queryRegionListHHN(-1L, parentId, regionType);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		String jsonStr = JSONArray.toJSONString(list);
		JSONUtils.printStr(jsonStr);
		return null;
	}

	/**
	 * 
	 * @Descb: 即将发布的标的
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String soonPublishList() throws Exception {
		// 前台显示列表类型
		String purpose = SqlInfusion.FilteSqlInfusion(request("purpose"));
		String deadline = SqlInfusion.FilteSqlInfusion(request("deadline"));
		String reward = SqlInfusion.FilteSqlInfusion(request("reward"));
		String arStart = SqlInfusion.FilteSqlInfusion(request("arStart"));
		String arEnd = SqlInfusion.FilteSqlInfusion(request("arEnd"));
		String province = SqlInfusion.FilteSqlInfusion(request("province"));
		String regCity = SqlInfusion.FilteSqlInfusion(request("regCity"));
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String borrowWay = "";
		String borrowStatus = "";
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		borrowStatus = "(8)";
		try {
			financeService.queryBorrowByConditions(borrowStatus, borrowWay, null, null, purpose, deadline, reward, arStart, arEnd,
					IConstants.SORT_TYPE_DESC, pageBean, null, province, regCity, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setRequestToParamMap();
		return SUCCESS;
	}

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
		try {
			List<Map<String, Object>> mapList = financeService.queryLastestBorrow();
			request().setAttribute("mapList", mapList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
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
		List<Map<String, Object>> rankList = new ArrayList<Map<String, Object>>();
		try {
			int number = Convert.strToInt(paramMap.get("number"), 1);
			// 当前年
			rankList = financeService.investRank(number, 8);
			request().setAttribute("rankList", rankList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: index
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:46:12
	 * @Return:
	 * @Descb: 首页加载内容
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String index() throws Exception {
		/*Map<String, String> totalRiskMap = financeService.queryTotalRisk();
		Map<String, String> currentRiskMap = financeService.queryCurrentRisk();
		request().setAttribute("totalRiskMap", totalRiskMap);
		request().setAttribute("currentRiskMap", currentRiskMap);*/
		// 最新借款列表
		List<Map<String, Object>> mapList = financeService.queryLastestBorrow();
		request().setAttribute("mapList", mapList);
		// 排名前8条记录
		// 全部
		/*List<Map<String, Object>> rankList = financeService.investRank(0, 8);
		request().setAttribute("rankList", rankList);*/
		// 公告
		List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
		pageBean.setPageSize(9);
		newsService.frontQueryNewsPage(pageBean);
		newsList = pageBean.getPage();
		pageBean.setPage(null);
		request().setAttribute("newsList", newsList);
		// //成功故事
		// List<Map<String,Object>> storyList = new
		// ArrayList<Map<String,Object>>();
		// pageBean.setPageSize(2);
		// / successStoryService.querySuccessStoryPage(pageBean);
		/*
		 * publicModelService.querySuccessStoryPage(pageBean); storyList =
		 * pageBean.getPage(); pageBean.setPage(null);
		 */
		/* request().setAttribute("storyList", storyList); */
		// 友情链接
		//pageBean.setPageSize(100);
		// / linksService.queryLinksPage(pageBean);
		//publicModelService.queryLinksPage(pageBean);
		//linksList = pageBean.getPage();
		//pageBean.setPage(null);
		//request().setAttribute("linksList", linksList);
		// 媒体报道 取4条记录
		//pageBean.setPageSize(4);
		// / mediaReportService.queryMediaReportPage(pageBean,1);
		//newsService.queryMediaReportPage(pageBean, 1);

		//meikuList = pageBean.getPage();
		//pageBean.setPage(null);
		//request().setAttribute("meikuList", meikuList);
		// 投资广告
		//pageBean.setPageSize(3);
		// / linksService.queryLinksGGPage(pageBean);
		//publicModelService.queryLinksGGPage(pageBean);
		//listsGGList = pageBean.getPage();
		//pageBean.setPage(null);
		//request().setAttribute("listsGGList", listsGGList);
		// 图片滚动
		//pageBean.setPageSize(3);
		// / linksService.queryBannerListPage(pageBean);
		//publicModelService.queryBannerListPage(pageBean);
		//bannerList = pageBean.getPage();
		//pageBean.setPage(null);
		//request().setAttribute("bannerList", bannerList);
		// 得到置顶的媒体报道
		//pageBean.setPageSize(2);
		// / mediaReportService.queryMediaReportPage(pageBean,2);
		//newsService.queryMediaReportPage(pageBean, 2);
		//meikuStick = pageBean.getPage();
		//pageBean.setPage(null);
		//request().setAttribute("meikuStick", meikuStick);
		// 得到用户对象
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user != null) {
            Map<String, String> userInfo=userService.queryUserById(user.getId());
            if (userInfo!=null&&StringUtils.isNotBlank(userInfo.get("usrCustId"))) {
                user.setUsrCustId(Long.parseLong(userInfo.get("usrCustId")));
                user.setUsableSum(Double.parseDouble(userInfo.get("usableSum")));
            }
            request().setAttribute("usableSum", userInfo.get("usableSum"));
//            request().setAttribute("idNo", userInfo.getIdNo());

			
		}
		/*
		 * } else { Cookie[] cookies = request().getCookies(); if (cookies !=
		 * null) { for (Cookie cookie : cookies) { if
		 * ("user".equals(cookie.getName())) { String value = cookie.getValue();
		 * request().setAttribute("email", value); DateFormat dateformat = new
		 * SimpleDateFormat(UtilDate.simple); String lastIP =
		 * ServletUtils.getRemortIp(); String lastTime = dateformat.format(new
		 * Date()); if (StringUtils.isNotBlank(value)) { String[] split =
		 * value.split(","); String username = split[0]; String password =
		 * split[1]; user = userService.userLogin1(username, password, lastIP,
		 * lastTime); if (user != null) { session().setAttribute("user",
		 * user);// 添加用户到session中 } } } } } }
		 */
		boolean colourLife = "colorlifeapp".equals(session("platform"));
		int sumInvest=financeService.querySumInvest(colourLife);
		int shouyi=(int) (sumInvest*0.12);
		request().setAttribute("s1_", dealMoney(sumInvest));
		request().setAttribute("s1_c", dealMoney(financeService.querySumInvest(true)));
		request().setAttribute("s2_", dealMoney(shouyi));

		request().setAttribute("countUser", financeService.countUser(colourLife));
		request().setAttribute("countUser_c", financeService.countUser(true));
		request().setAttribute("countUser7", financeService.countUserIn7Days(colourLife));
		return "success";
	}
	private String dealMoney(int money) {
		char[]cs=(money+"").toCharArray();
        StringBuilder sb=new StringBuilder();
        for(int i=cs.length-1;i>=0;i--){
            sb.insert(0,cs[i]);
            if ((cs.length-i)%3==0&&i>0){
                sb.insert(0,",");
            }
        }
        return sb.toString();
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
	 * @throws Exception
	 * @MethodName: financeDetail
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:40:38
	 * @Return:
	 * @Descb: 理财中的借款明细
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String financeDetail() throws Exception {
		session().setAttribute("DEMO", IConstants.ISDEMO);
		String idStr = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter("id"));
		request().setAttribute("id", Convert.strToInt(idStr, 0));
		AccountUserDo user = (AccountUserDo) request().getSession().getAttribute(IConstants.SESSION_USER);
		long userId = -1L;
		// 如果是登录用户,则显示余额
		if (null != user) {
			userId = user.getId();
			Map<String, String> userMap = financeService.queryUserMonney(userId);
			request().setAttribute("userMap", userMap);
		} else {
			request().setAttribute("usableSum", "--");
		}
		if (!"".equals(idStr) && StringUtils.isNumericSpace(idStr)) {
			Long id = Convert.strToLong(idStr, -1);
			// 借款详细
			Map<String, String> borrowDetailMap = financeService.queryBorrowDetailById(id);
			// -- 7 - 9
			// 查询借款信息得到借款时插入的平台收费标准
			Map<String, String> map = borrowManageService.queryBorrowInfo(id);
			if (map == null) {
				return "404";
			}
			// 得到收费标准的json代码
			String feelog = Convert.strToStr(map.get("feelog"), "");
			Map<String, Double> feeMap = (Map<String, Double>) JSONObject.toBean(JSONObject.fromObject(feelog), HashMap.class);
			if (borrowDetailMap != null && borrowDetailMap.size() > 0) {
				double borrowSum = Convert.strToDouble(borrowDetailMap.get("borrowSum") + "", 0);
				double annualRate = Convert.strToDouble(borrowDetailMap.get("annualRate") + "", 0);
				int deadline = Convert.strToInt(borrowDetailMap.get("deadline") + "", 0);
				int isDayThe = Convert.strToInt(borrowDetailMap.get("isDayThe") + "", 1);
				double investAmount = 10000;
				String earnAmount = "";
				if (borrowSum < investAmount) {
					investAmount = borrowSum;
				}
				AmountUtil au = new AmountUtil();
				Map<String, String> earnMap = null;
				double costFee = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE) + "", 0);
				// 按月等额还款
				earnMap = au.earnCalculateMonth(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2, costFee);
				earnAmount = earnMap.get("msg") + "";
				String borrowWay = borrowDetailMap.get("borrowWay");
				if (borrowWay.equals(IConstants.BORROW_TYPE_NET_VALUE)) {
					request().setAttribute("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (borrowWay.equals(IConstants.BORROW_TYPE_SECONDS)) {
					request().setAttribute("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (borrowWay.equals(IConstants.BORROW_TYPE_GENERAL)) {
					request().setAttribute("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				}
				// 还款详情
				PageBean<Map<String, Object>> pageOne = new PageBean<Map<String, Object>>();
				financeService.queryRepaymentById(pageOne, idStr);
				int pageNum = (int) (pageOne.getPageNum() - 1) * pageOne.getPageSize();
				request().setAttribute("pageNum", pageNum);
				request().setAttribute("pageBeanes", pageOne.getPage());

				// 催收记录
				// PageBean<Map<String, Object>> pageTwo = new
				// PageBean<Map<String, Object>>();
				// financeService.queryCollectionById(pageTwo, idStr);
				// request().setAttribute("pageNum", pageNum);
				// request().setAttribute("pageBeanrepay", pageTwo);

				List<Map<String, Object>> collection = financeService.queryCollectionByid(id);
				if (collection != null && collection.size() > 0)
					request().setAttribute("colSize", collection.size());

				request().setAttribute("earnAmount", earnAmount);
				// 每次点击借款详情时新增浏览量
				financeService.addBrowseCount(id);
				// 借款进度百分比
				pageBean.setPageSize(60);
				financeService.queryInvestPercent(id, pageBean);
				// 处理一下多出来的百分比小数点	
				if (pageBean.getPage() != null && pageBean.getPage().size() > 0) {
					double sum = 0;
					List<Map<String, Object>> list = pageBean.getPage();
					for (Map<String, Object> m : list) {
						sum += Convert.strToDouble(m.get("percent") + "", 0);
					}
					if (sum > 100) {
						Map<String, Object> m = list.get(list.size() - 1);
						double last = Convert.strToDouble(m.get("percent") + "", 0) - sum + 100;
						m.put("percent", last);
					}
				}
				request().setAttribute("borrowDetailMap", borrowDetailMap);
				// 借款人资料
				Map<String, String> borrowUserMap = financeService.queryUserInfoById(id);
				/*String orgName = null;
				StringBuffer sb = new StringBuffer();
				if(borrowUserMap.get("orgName").indexOf("市") != -1 && borrowUserMap.get("orgName").lastIndexOf("公") != -1)
				{
                    orgName =
                        sb.append(borrowUserMap.get("orgName").substring(0,borrowUserMap.get("orgName").indexOf("市")+1))
                          .append(borrowUserMap.get("orgName").replaceAll(borrowUserMap.get("orgName").substring(0,borrowUserMap.get("orgName").indexOf("市") + 2), "*")
                          .replaceAll(borrowUserMap.get("orgName").substring(borrowUserMap.get("orgName").lastIndexOf("公")-1),"*"))
                          .append(borrowUserMap.get("orgName").substring(borrowUserMap.get("orgName").lastIndexOf("公"), borrowUserMap.get("orgName").length())).toString();
				}
				
				if(borrowUserMap.get("orgName").indexOf("市") != -1 && borrowUserMap.get("orgName").lastIndexOf("公") == -1)
                {
                    orgName = sb.append(borrowUserMap.get("orgName").substring(0,borrowUserMap.get("orgName").indexOf("市")+1))
                    .append(borrowUserMap.get("orgName").replaceAll(borrowUserMap.get("orgName").substring(0,borrowUserMap.get("orgName").indexOf("市") + 2), "***")).toString();
                }
				
				if(borrowUserMap.get("orgName").indexOf("市") == -1 && borrowUserMap.get("orgName").lastIndexOf("公") != -1)
                {
                    orgName = sb.append(borrowUserMap.get("orgName").substring(0,borrowUserMap.get("orgName").indexOf("公")-2))
                    .append(borrowUserMap.get("orgName").replaceAll(borrowUserMap.get("orgName").substring(0,borrowUserMap.get("orgName").indexOf("公") - 1), "***")).toString();
                }
				*/
				request().setAttribute("orgName", borrowUserMap.get("orgName"));
				
				request().setAttribute("borrowUserMap", borrowUserMap);
				// 借款人认证资料
				List<Map<String, Object>> list = financeService.queryUserIdentifiedByid(id);
				request().setAttribute("list", list);
				// 投资记录
				List<Map<String, Object>> investList = financeService.queryInvestByid(id);
				request().setAttribute("investList", investList);
				request().setAttribute("idStr", idStr);

				// borrowRecordMaop借款记录
				Map<String, String> borrowRecordMap = financeService.queryBorrowRecord(id);
				request().setAttribute("borrowRecordMap", borrowRecordMap);
				// -----------add by houli
				String wStatus = judgeStatus(Convert.strToInt(borrowWay, -1), Convert.strToLong(borrowDetailMap.get("publisher"), -100));
				if (wStatus == null) {
					request().setAttribute("wStatus", "");
				} else {
					request().setAttribute("wStatus", wStatus);
				}

			} else {
				return "404";
			}
		} else {
			return "404";
		}
		return "success";
	}

	/**
	 * 点击查看详情的时候，判断某标的的状态
	 * 
	 * @param tInt
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	private String judgeStatus(int tInt, Long userId) throws SQLException, DataException {
		if (tInt < 3) {// 秒还、净值标的
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if (aa < 0) {
				return "waitBorrow";

			}
		} else {// 其它借款
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if (aa < 0) {
				return "waitBorrow";
			} else {
				Long bb = borrowService.queryBaseFiveApprove(userId);
				if (bb < 0) {
					return "waitBorrow";
				}
			}
		}
		return null;
	}

	/**
	 * 债权转让借款详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryDebtBorrowDetail() throws Exception {
		return financeDetail();
	}

	/**
	 * @MethodName: financeAudit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:26:02
	 * @Return:
	 * @Descb: 借款人认证资料
	 * @Throws:
	 */
	public String financeAudit() throws SQLException, DataException {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		// 借款人认证资料
		List<Map<String, Object>> list = financeService.queryUserIdentifiedByid(idLong);
		request().setAttribute("auditList", list);
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: financeRepay
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:27:02
	 * @Return:
	 * @Descb: 借款人还款记录
	 * @Throws:
	 */
	public String financeRepay() throws SQLException, DataException {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		// 借款人还款记录
		List<Map<String, Object>> list = financeService.queryRePayByid(idLong);
		request().setAttribute("repayList", list);
		return "success";
	}

	/**
	 * @MethodName: financeCollection
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:29:12
	 * @Return:
	 * @Descb: 借款人催款记录
	 * @Throws:
	 */
	public String financeCollection() throws SQLException, DataException {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		// 借款人催款记录
		List<Map<String, Object>> list = financeService.queryCollectionByid(idLong);
		request().setAttribute("collectionList", list);
		return "success";
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
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter("id"));
		long idLong = Convert.strToLong(id, -1);
		if (idLong == -1) {
			// 非法操作直接返回
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String, String> investMaps = financeService.getInvestStatus(idLong);
		String nid_log = "";
		if (investMaps != null && investMaps.size() > 0) {
			nid_log = Convert.strToStr(investMaps.get("nid_log"), "");
			Map<String, String> typeLogMap = null;
			if (StringUtils.isNotBlank(nid_log)) {
				typeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(typeLogMap.get("subscribe_status"), -1);
				request().setAttribute("subscribes", stauts);
				request().setAttribute("investMaps", investMaps);
			}
		}

		if (investMaps != null && investMaps.size() > 0) {
			String hasPWD = investMaps.get("hasPWD") == null ? "-1" : investMaps.get("hasPWD");
			investDetailMap = financeService.queryBorrowInvest(idLong);
			double residue = Convert.strToDouble(investDetailMap.get("residue"), 0);
			double minTenderedSum = Convert.strToDouble(investDetailMap.get("minTenderedSum"), 0);
			if (residue < minTenderedSum) {
				request().setAttribute("minTenderedSum", residue);
			} else {
				request().setAttribute("minTenderedSum", minTenderedSum);
			}
			String userId = investDetailMap.get("userId") == null ? "" : investDetailMap.get("userId");
			if (userId.equals(user.getId().toString())) {
				// 不满足投标条件,返回
				obj.put("msg", "不能投标自己发布的借款");
				JSONUtils.printObject(obj);
				return null;
			}
			session().setAttribute("investStatus", "ok");
			Map<String, String> userMap = financeService.queryUserMonney(user.getId());
			request().setAttribute("userMap", userMap);
			session().setAttribute("hasPWD", hasPWD);
		} else {
			// 不满足投标条件,返回
			obj.put("msg", "该借款投标状态已失效");
			JSONUtils.printObject(obj);
			return null;
		}
		return "success";
	}

	public String userInvestBorrwo() {
		JSONObject obj = new JSONObject();
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		String dealPwd = paramMap.get("investPWD") == null ? "" : paramMap.get("investPWD");
		long idLong = Convert.strToLong(id, -1);

		if (idLong == -1) {
			// 非法操作直接返回
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			// JSONUtils.printObject(obj);
			return null;
		}
		if ("".equals(dealPwd)) {
			this.addFieldError("paramMap['investPWD']", "请输入交易密码");
			return "input";
		}
		// Map<String, String> investPWDMap = financeService.getDealPWD(idLong,
		// dealPwd);
		// if (investPWDMap == null || investPWDMap.size() == 0)
		// {
		// this.addFieldError("paramMap['investPWD']", "交易密码错误");
		// JSONUtils.printStr("1");
		// return "input";
		// }
		// 验证码
		String code = (String) session().getAttribute("userregister_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		if (!code.equals(_code)) {
			// JSONUtils.printStr("2");
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
	public String financeInvestLoad() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		String investPWD = SqlInfusion.FilteSqlInfusion(paramMap.get("investPWD") == null ? "" : paramMap.get("investPWD"));
		long idLong = Convert.strToLong(id, -1);

		if (idLong == -1) {
			// 非法操作直接返回
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}
		if ("".equals(investPWD)) {
			this.addFieldError("paramMap['investPWD']", "请输入交易密码");
			return "input";
		}
		Map<String, String> investPWDMap = financeService.getDealPWD(idLong, investPWD);
		if (investPWDMap == null || investPWDMap.size() == 0) {
			this.addFieldError("paramMap['investPWD']", "交易密码错误");
			return "input";
		}
		// 判断是否进行了资料审核
		Object object = session().getAttribute("investStatus");
		if (object == null) {
			return null;
		}
		Map<String, String> investMaps = financeService.getInvestStatus(idLong);
		if (investMaps != null && investMaps.size() > 0) {
			investDetailMap = financeService.queryBorrowInvest(idLong);

			String userId = investDetailMap.get("userId") == null ? "" : investDetailMap.get("userId");
			if (userId.equals(user.getId().toString())) {
				// 不满足投标条件,返回
				obj.put("msg", "不能投标自己发布的借款");
				JSONUtils.printObject(obj);
				return null;
			}
			Map<String, String> userMap = financeService.queryUserMonney(user.getId());
			request().setAttribute("userMap", userMap);
		} else {
			// 不满足投标条件,返回
			obj.put("msg", "该借款投标状态已失效");
			JSONUtils.printObject(obj);
			return null;
		}
		return "success";
	}

	/**
	 * 立即投标
	 * 
	 * @MethodName: financeInvest
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-30 下午03:53:34
	 * @Return:
	 * @Descb: 投标借款
	 * @Throws:
	 */
	public String financeInvest() throws Exception {
		String urlString=paramMap.get("url");
		urlString=StringUtils.trim(urlString);
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String platform=(String) session().getAttribute("platform");
        if ("appcomm".equals(platform)){
            platform=(String) session().getAttribute("sourcefrom");
        }
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		String amount = SqlInfusion.FilteSqlInfusion(paramMap.get("amount") == null ? "" : paramMap.get("amount"));
		double amountDouble = Convert.strToDouble(amount, 0);
//		String investPWD = SqlInfusion.FilteSqlInfusion(paramMap.get("investPWD") == null ? "" : paramMap.get("investPWD"));
		int status = Convert.strToInt(paramMap.get("subscribes"), 2);

		// 验证密码
//		Map<String, String> investPWDMap = financeService.getDealPWD(user.getId(), investPWD);
//		if (investPWDMap == null || investPWDMap.size() == 0) {
//			JSONUtils.printStr2("密码错误");
//			return null;
//		}

		// 验证码
		String code = (String) session().getAttribute("userregister_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code") == null ? "" : paramMap.get("code"));
		if (!("colorlifeapp".equals(session("platform"))||"appcomm".equals(session("platform")))&&!_code.equals(code)) {
//			JSONUtils.printStr2("验证码错误");
			
			if("colorlifeapp".equals(session("platform"))){
				this.response().sendRedirect("/webapp/webapp-financeDetail.do?id="+idLong+"&retMsg="+URLEncoder.encode("验证码错误","utf-8"));
			}else{
				JSONUtils.printStr2("<script>alert(\"验证码错误\");window.close();</script>");
			}
			return null;
		}

		int num = 0;
		investDetailMap = financeService.queryBorrowInvest(idLong);

		String userId = investDetailMap.get("userId") == null ? "" : investDetailMap.get("userId");
		if (userId.equals(user.getId().toString())) {
//			JSONUtils.printStr2("不能投标自己发布的借款");
			
			if("colorlifeapp".equals(session("platform"))){
				this.response().sendRedirect("/webapp/webapp-financeDetail.do?id="+idLong+"&retMsg="+URLEncoder.encode("不能投标自己发布的借款","utf-8"));
			}else{
				JSONUtils.printStr2("<script>alert(\"不能投标自己发布的借款\");window.close();</script>");
			}
			return null;
		}

		/*
		 * 20140610 by 刘文韬
		 * 用户所属群组不是标的对应群组时，不能投标
		 */
		int borrowGroup = Integer.parseInt(investDetailMap.get("borrowGroup"));
		if (borrowGroup > 0 && user.getUserGroup() != borrowGroup ) {
			if("colorlifeapp".equals(session("platform"))){
				this.response().sendRedirect("/webapp/webapp-financeDetail.do?id="+idLong+"&retMsg="+URLEncoder.encode("投标失败","utf-8"));
			}else{
				JSONUtils.printStr2("<script>alert(\"投标失败！\");window.close();</script>");
			}
			return null;
		}

		
		session().removeAttribute("userregister_checkCode");
		Map<String, String> result = financeService.addBorrowInvest(idLong, getUserId(), "", amountDouble, "", user.getUsername(), status, num);
		String ordId = result.get("ret_ordid");
		if (Convert.strToInt(result.get("ret"), -1) < 0 || Convert.strToLong(result.get("ret_ordid"), -1) < 0) {
//			JSONUtils.printStr2(result.get("ret_desc"));
			
			if("colorlifeapp".equals(session("platform"))){
				this.response().sendRedirect("/webapp/webapp-financeDetail.do?id="+idLong+"&retMsg="+URLEncoder.encode(result.get("ret_desc"),"utf-8"));
			}else{
				JSONUtils.printStr2("<script>alert(\""+result.get("ret_desc")+"\");window.close();</script>");
			}
			return null;
		}

		String transAmt =new DecimalFormat("0.00").format(Convert.strToDouble(amount, 0)); 
		String usrCustId = user.getUsrCustId() + "";
		if (user.getUsrCustId() < 0) {
//			JSONUtils.printStr2("您还未在汇付注册!请先注册!");
			
			if("colorlifeapp".equals(session("platform"))){
				this.response().sendRedirect("/webapp/webapp-financeDetail.do?id="+idLong+"&url="+URLEncoder.encode("/webapp/webapp-userinfo.do","utf-8")+"&retMsg="+URLEncoder.encode("您还未在汇付注册!请先注册!","utf-8"));
			}else{
				JSONUtils.printStr2("<script>alert(\"您还未在汇付注册!请先注册!\");window.close();</script>");
			}
			return null;
		}
		
		/* 如果用户是在彩生活平台投资，在插入投标记录成功后，修改flag的值，标记该笔投资
		 * 20140609 by 刘文韬
		 *  */
		financeService.updateInvestFlag(ordId, platform);
		
		
//		String borrowerRate = ChinaPnrConfig.BorrowerRate;
		String borrowerRate = "1.00";
		JSONObject json = new JSONObject();
		json.put("BorrowerCustId", investDetailMap.get("usrCustId"));
		json.put("BorrowerAmt", transAmt);
		// 汇付还款总额为借款金额*(1+利率).改为1防止出现手续费过多出现:本次还款金额加上已还金额超过还款总额的情况
		json.put("BorrowerRate", borrowerRate);
		// 汇付主动投标
		String html = ChinaPnRInterface.initiativeTender1(id, ordId, usrCustId, transAmt, "[" + json.toString() + "]",urlString);
		html = html.replace("\"[{", "'[{");
		html = html.replace("}]\"", "}]'");
		sendHtml(html);
		session().removeAttribute(code);
		return null;
	}

	/**
	 * @MethodName: borrowMSGInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:08:51
	 * @Return:
	 * @Descb: 借款留言初始化
	 * @Throws:
	 */
	public String borrowMSGInit() throws SQLException, DataException {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		String pageNum = SqlInfusion.FilteSqlInfusion(paramMap.get("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		if (idLong == -1) {
			return "404";
		}
		financeService.queryBorrowMSGBord(idLong, pageBean);
		request().setAttribute("id", id);
		return "success";
	}

	/**
	 * @throws IOException
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: addBorrowMSG
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:09:06
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public String addBorrowMSG() throws IOException, SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = SqlInfusion.FilteSqlInfusion((String) session().getAttribute("msg_checkCode"));
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code") == null ? "" : paramMap.get("code"));
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		String msgContent = SqlInfusion.FilteSqlInfusion(paramMap.get("msg") == null ? "" : paramMap.get("msg"));
		long returnId = -1;
		returnId = financeService.addBorrowMSG(idLong, user.getId(), msgContent);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
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
	public String focusOnBorrow() throws SQLException, IOException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		long returnId = -1L;
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		if (idLong == -1) {
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}

		Map<String, String> map = financeService.hasFocusOn(idLong, user.getId(), IConstants.FOCUSON_BORROW);
		if (map != null && map.size() > 0) {
			obj.put("msg", "您已关注过该借款");
			JSONUtils.printObject(obj);
			return null;
		}

		returnId = financeService.addFocusOn(idLong, user.getId(), IConstants.FOCUSON_BORROW);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "关注成功!");
			JSONUtils.printObject(obj);
			return null;
		}
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
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		long returnId = -1L;
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		if (idLong == -1) {
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}

		Map<String, String> map = financeService.hasFocusOn(idLong, user.getId(), IConstants.FOCUSON_USER);
		if (map != null && map.size() > 0) {
			obj.put("msg", "您已关注过该用户");
			JSONUtils.printObject(obj);
			return null;
		}
		returnId = financeService.addFocusOn(idLong, user.getId(), IConstants.FOCUSON_USER);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "关注成功!");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @MethodName: mailInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:31
	 * @Return:
	 * @Descb: 发送站内信初始化
	 * @Throws:
	 */
	public String mailInit() {
		String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id"));
		String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("username"));
		request().setAttribute("id", id);
		request().setAttribute("userName", userName);
		return "success";
	}

	/**
	 * @MethodName: reportInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:48
	 * @Return:
	 * @Descb: 举报用户初始化
	 * @Throws:
	 */
	public String reportInit() {
		String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id"));
		String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("username"));
		request().setAttribute("id", id);
		request().setAttribute("userName", userName);
		return "success";
	}

	public String mailAdd() throws IOException, SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("code_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code") == null ? "" : paramMap.get("code"));
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long reciver = Convert.strToLong(id, -1);
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title") == null ? "" : paramMap.get("title"));
		String content = SqlInfusion.FilteSqlInfusion(paramMap.get("content") == null ? "" : paramMap.get("content"));
		long returnId = -1;
		Integer enable = user.getEnable();
		if (enable == 3) {
			obj.put("msg", "8");
			JSONUtils.printObject(obj);
			return null;
		}
		returnId = financeService.addUserMail(reciver, user.getId(), title, content, IConstants.MALL_TYPE_COMMON);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @throws DataException
	 * @MethodName: reportAdd
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:16:11
	 * @Return:
	 * @Descb: 添加用户举报
	 * @Throws:
	 */
	public String reportAdd() throws SQLException, IOException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("code_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code") == null ? "" : paramMap.get("code"));
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long reporter = Convert.strToLong(id, -1);
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title") == null ? "" : paramMap.get("title"));
		String content = SqlInfusion.FilteSqlInfusion(paramMap.get("content") == null ? "" : paramMap.get("content"));
		long returnId = -1;
		returnId = financeService.addUserReport(reporter, user.getId(), title, content);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * 用户举报--修改版
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String reportUserAdd() throws SQLException, IOException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String code = (String) session().getAttribute("code_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code") == null ? "" : paramMap.get("code"));
		if (!code.equals(_code)) {
			JSONUtils.printStr("1");
		}
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long reporter = Convert.strToLong(id, -1);
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title") == null ? "" : paramMap.get("title"));
		String content = SqlInfusion.FilteSqlInfusion(paramMap.get("content") == null ? "" : paramMap.get("content"));
		long returnId = -1;
		returnId = financeService.addUserReport(reporter, user.getId(), title, content);
		if (returnId <= 0) {
			JSONUtils.printStr("2");
			return null;
		} else {
			// 添加成功返回值
			JSONUtils.printStr("3");
			return null;
		}
	}

	/**
	 * @MethodName: showImg
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:24:03
	 * @Return:
	 * @Descb: 查看图片
	 * @Throws:
	 */
	public String showImg() throws SQLException, DataException {
		String typeId = SqlInfusion.FilteSqlInfusion(request().getParameter("typeId") == null ? "" : request().getParameter("typeId"));
		String userId = SqlInfusion.FilteSqlInfusion(request().getParameter("userId") == null ? "" : request().getParameter("userId"));
		long typeIdLong = Convert.strToLong(typeId, -1);
		long userIdLong = Convert.strToLong(userId, -1);
		List<Map<String, Object>> imgList = financeService.queryUserImageByid(typeIdLong, userIdLong);
		request().setAttribute("imgList", imgList);
		return "success";
	}

	/**
	 * 跳转流转标购买 页面
	 */
	public String subscribeinit() {
		long borrowid = Convert.strToLong(request("borrowid"), -1);
		try {
			Map<String, String> borrowDetailMap = financeService.queryBorrowDetailById(borrowid);
			request().setAttribute("borrowDetailMap", borrowDetailMap);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: subscribe
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午08:22:15
	 * @Return:
	 * @Descb: 认购流转标
	 * @Throws:
	 */
	public String subscribe() throws IOException, SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		String result = SqlInfusion.FilteSqlInfusion(paramMap.get("result"));
		int resultLong = Convert.strToInt(result, -1);

		if (idLong == -1) {
			obj.put("msg", "无效认购标的");
			JSONUtils.printObject(obj);
			return null;
		}
		if (resultLong == -1) {
			obj.put("msg", "非法的认购份数");
			JSONUtils.printObject(obj);
			return null;
		} else if (resultLong <= 0) {
			obj.put("msg", "请输入正确的认购份数");
			JSONUtils.printObject(obj);
			return null;
		}

		String resultStr = financeService.subscribeSubmit(idLong, resultLong, user.getId(), getBasePath(), user.getUsername(), getPlatformCost());
		obj.put("msg", resultStr);
		JSONUtils.printObject(obj);
		return null;
	}

	public Map<String, String> getInvestDetailMap() throws SQLException {
		String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter("id"));
		long idLong = Convert.strToLong(id, -1);
		if (investDetailMap == null) {
			investDetailMap = financeService.queryBorrowInvest(idLong);
		}
		return investDetailMap;
	}

	/**
	 * 根据借款ID查询还款记录
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String queryRepaymentById() throws Exception {
		String borrowId = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		financeService.queryRepaymentById(pageBean, borrowId);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("pageBeanes", pageBean);
		return SUCCESS;
	}

	/**
	 * 根据借款ID查询催收记录
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String queryCollectionById() throws Exception {
		String borrowId = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
		financeService.queryCollectionById(pageBean, borrowId);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("pageBeanrepay", pageBean);
		return SUCCESS;
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

	public NewsAndMediaReportService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsAndMediaReportService newsService) {
		this.newsService = newsService;
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public List<Map<String, Object>> getBorrowPurposeList() throws SQLException, DataException {
		borrowPurposeList = selectedService.borrowPurpose();

		return borrowPurposeList;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws SQLException, DataException {
		borrowDeadlineList = selectedService.borrowDeadline();

		return borrowDeadlineList;
	}

	/**
	 * 按条件搜索 -- 查询金额范围
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return List<Map<String,Object>> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public List<Map<String, Object>> getBorrowAmountList() throws SQLException, DataException {
		borrowAmountList = selectedService.borrowAmountRange();
		return borrowAmountList;
	}

	public void setShoveBorrowTypeService(ShoveBorrowTypeService shoveBorrowTypeService) {
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

	// public void setLinksService(LinksService linksService) {
	// this.linksService = linksService;
	// }
	// public LinksService getLinksService() {
	// return linksService;
	// }

	public List<Map<String, Object>> getLinksList() {
		return linksList;
	}

	public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}

	public void setLinksList(List<Map<String, Object>> linksList) {
		this.linksList = linksList;
	}

	// public void setMediaReportService(MediaReportService mediaReportService)
	// {
	// this.mediaReportService = mediaReportService;
	// }

	public List<Map<String, Object>> getMeikuList() {
		return meikuList;
	}

	public void setMeikuList(List<Map<String, Object>> meikuList) {
		this.meikuList = meikuList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Map<String, Object>> getMeikuStick() {
		return meikuStick;
	}

	public void setMeikuStick(List<Map<String, Object>> meikuStick) {
		this.meikuStick = meikuStick;
	}

	public List<Map<String, Object>> getListsGGList() {
		return listsGGList;
	}

	public void setListsGGList(List<Map<String, Object>> listsGGList) {
		this.listsGGList = listsGGList;
	}

	public List<Map<String, Object>> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<Map<String, Object>> bannerList) {
		this.bannerList = bannerList;
	}

	public List<Map<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Map<String, Object>> getCityList() {
		return cityList;
	}

	public void setCityList(List<Map<String, Object>> cityList) {
		this.cityList = cityList;
	}

	public long getWorkPro() {
		return workPro;
	}

	public void setWorkPro(long workPro) {
		this.workPro = workPro;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
	
	/*
	 * 20140610 by 刘文韬
	 * 获取用户的群组
	 */
	private int getUserGroup(){
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if( user != null){
			return user.getUserGroup();
		}
		return 0;
	}
	
	/*
	 * 一键投标
	 */
	public String oneKeyBid() {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String sql="select * from t_automaticbid where userId="+user.getId();
		Connection conn = BaseService.connectionManager.getConnection();
		try {
			DataSet ds = MySQL.executeQuery(conn, sql);
			Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
			if (map!=null&&"2".equals(map.get("bidStatus"))) {
				//已开启自动投标
				financeService.oneKeyBid(user.getId());
				JSONUtils.printStr2("1");
			}else {
				JSONUtils.printStr2("2");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
