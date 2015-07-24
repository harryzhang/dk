package com.hehenian.web.view.system;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.system.ICommonQueryService;
import com.ibm.icu.text.DecimalFormat;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.ExcelUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.util.DateUtil;

@Scope("prototype")
@Component("queryExamplelAction")
public class queryExamplelAction extends BaseFrontAction {
    private static final long   serialVersionUID = 1L;
    @Autowired
    private ICommonQueryService commonQueryService;

	//传递参数
	private Map<String, Object> searchItems = new HashMap<String, Object>();
   
	protected HttpServletRequest request() {
		return ServletActionContext.getRequest();
	}
    
    /**
     * 将request中的参数设置到searchItems中去
     */
    @SuppressWarnings("unchecked")
    protected void setRequestToParamMap(){
        Enumeration<String> keyNames = request().getParameterNames();
        while(keyNames.hasMoreElements()){
            String attrName = keyNames.nextElement();
            searchItems.put(attrName, request().getParameter(attrName));
        }
    }
    
    /**
     * 查询借款受理信息
     * @return
     */
    public String getData() {
    	//把页面的参数设置到map中
    	setRequestToParamMap();
    	Object obj = searchItems.get("createTime");
    	if(obj == null) {
    		searchItems.put("createTime",new SimpleDateFormat("yyyy-MM").format(new Date()));
    	}
        long currentPage = (StringUtils.isNotBlank(request().getParameter("curPage")) ? Long.parseLong(request()
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request().getParameter("pageSize")) ? Long.parseLong(request()
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;
        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        if(searchItems.get("selectMethodId") == null){
        	searchItems.put("selectMethodId","com.hehenian.biz.dal.system.ICommonQueryDao.getCapitalAccount");
			searchItems.put("countMethod","com.hehenian.biz.dal.system.ICommonQueryDao.getTotalCountAccount");
        }
        NPageDo<List<Map<String, Object>>> pageDo = commonQueryService.getMap(searchItems);
        pageDo.setCurrentPage(currentPage);
        pageDo.setPageSize(pageSize); 
        request().setAttribute("pageDo", pageDo);
        request().setAttribute("createTime", searchItems.get("createTime"));
        return SUCCESS;
    }
    
    
    /**
	 * 各渠道进出账资金汇总表导出excel
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String exportAccountMsg() {
		setRequestToParamMap();
		String ids = request().getParameter("ids"); // id拼接 用，隔开
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			if (StringUtils.isNotBlank(ids)) {
				String idStr = StringEscapeUtils.escapeSql("'" + ids + "'");
				idStr = idStr.replaceAll("'", "");
				String[] array = idStr.split(",");
				for (int n = 0; n <= array.length - 1; n++) {
					searchItems.put("accountId", array[n]);
					listMap.add(commonQueryService.getData(searchItems));
				}
			}
			if (listMap.size() == 0) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (listMap.size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}// 序号 用户名 真实姓名 手机号码 身份证 用户来源 注册时间 可用金额 冻结金额 待收金额
			HSSFWorkbook wb = ExcelUtils.exportExcel("各渠道进出账资金汇总表", listMap, new String[] { "日期", "彩富人生", "彩生活散标", "彩生活定期理财", "彩生活红包理财","彩生活总额", "平台散标", "平台定期理财", "平台总额","总进账金额","放款金额","取现金额", "汇付天下可用余额", "通联账户可用余额","总额" },
					new String[] { "createTime", "rechargeMoney", "investAmount","tradeAmount", "hbBuyMoney", "cliftTotalMoney", "platformInvestAmount", "platformTradeAmount", "platformTotalMoney", "totalMoney", "borrowAmount", "withdrawal","tlAvailableMoney","hfAvailableMoney","totalAmount" });
			this.export(wb, new Date().getTime() + ".xls");

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	 /**
     * 查询KPI考核情况
     * @return
     */
    public String getKPI() {
    	//把页面的参数设置到map中
    	setRequestToParamMap();
    	Object obj = searchItems.get("applyTime");
    	 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	 String sDate = null;
    	 String eDate = null;
    	 String nameSpace = "com.hehenian.biz.dal.system.ICommonQueryDao" ;
    	 try {
		    	if(obj == null) {
		    		searchItems.put("applyTime",DateUtil.dateFormartYMD(DateUtil.dateAddDay(new Date(),-1)));
		    		searchItems.put("endTime",DateUtil.dateFormartYMD(new Date()));
		    	}
		    	sDate = searchItems.get("applyTime")+"";
		    	eDate = searchItems.get("endTime")+"";
		    	
		    	getKpiListData(list, sDate, eDate,nameSpace);
    	 
 		} catch (Exception e) {
 			throw new BusinessException("查询KPI考核出错！");
 		}
        request().setAttribute("pageDo", list);
        request().setAttribute("applyTime", sDate);
        request().setAttribute("endTime", eDate);
        return SUCCESS;
    }

	/**
	 * @param list
	 * @param sDate
	 * @param eDate
	 */
	private void getKpiListData(List<Map<String, Object>> list, String sDate,
			String eDate,String nameSpace) {
		Map<String, Object> seachr;
		Map<String, Object> map;
		long size = DateUtil.diffDays(DateUtil.strToYYMMDDDate(sDate), DateUtil.strToYYMMDDDate(eDate));
		for(int i = 0; i < size; i++ ) {
			seachr = new HashMap<String, Object>();
			
			//投资人数
			searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
			searchItems.put("applyTime", DateUtil.dateFormartYMD(DateUtil.dateAddDay(DateUtil.strToYYMMDDDate(sDate),i)));
			searchItems.put("endTime", DateUtil.dateFormartYMD(DateUtil.dateAddDay(DateUtil.strToYYMMDDDate(sDate),i+1)));
			map = commonQueryService.getData(searchItems);
			seachr.put("totalMoney", map!=null ? map.get("totalMoney"):"0.00");
			
			//注册用户数
			searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
			map = commonQueryService.getData(searchItems);
			seachr.put("registerNumber",map!=null ? map.get("registerNumber"):"0");
			
			//放款金额
			searchItems.put("selectMethodId", nameSpace+".getTodayFkMoney");
			map = commonQueryService.getData(searchItems);
			seachr.put("borrowAmount",map!=null ? map.get("borrowAmount"):"0.00");
			
			searchItems.put("selectMethodId", nameSpace+".getDkRegisterNumber");
			map = commonQueryService.getData(searchItems);
			seachr.put("dkRegisterNumber",map!=null ? map.get("dkRegisterNumber"):"0");
			
			seachr.put("createTime", searchItems.get("applyTime"));
			list.add(seachr);
		}
	}
	
	   /**
	    * KPI完成情况
	    * @return
	    */
		public String exportKpiList() {
			//把页面的参数设置到map中
	    	setRequestToParamMap();
	    	Object obj = searchItems.get("applyTime");
	    	 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    	 String sDate = null;
	    	 String eDate = null;
	    	 String nameSpace = "com.hehenian.biz.dal.system.ICommonQueryDao" ;
	    	 try {
			    	if(obj == null) {
			    		searchItems.put("applyTime",DateUtil.dateFormartYMD(DateUtil.dateAddDay(new Date(),-1)));
			    		searchItems.put("endTime",DateUtil.dateFormartYMD(new Date()));
			    	}
			    	sDate = searchItems.get("applyTime")+"";
			    	eDate = searchItems.get("endTime")+"";
			    	
			    	getKpiListData(list, sDate, eDate,nameSpace);
	    	 
				if (list.size() == 0) {
					getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
					return null;
				}
				if (list.size() > IConstants.EXCEL_MAX) {
					getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
					return null;
				}// 序号 用户名 真实姓名 手机号码 身份证 用户来源 注册时间 可用金额 冻结金额 待收金额
				HSSFWorkbook wb = ExcelUtils.exportExcel("KPI完成", list, new String[] { "日期","投资额", "注册用户数", "贷款金额", "贷款注册用户" },
						new String[] { "createTime", "totalMoney", "registerNumber","borrowAmount", ""});
				this.export(wb, new Date().getTime() + ".xls");

			} catch (SQLException e) {

				e.printStackTrace();
			} catch (DataException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	public String kpiContinueStatus() {
		//把页面的参数设置到map中
    	setRequestToParamMap();
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	 
 	     try {
 	    	 kpiData(list);
 		} catch (Exception e) {
 			throw new BusinessException("查询KPI考核出错！");
 		}
        request().setAttribute("pageDo", list);
        return SUCCESS;
	}

	private void kpiData(List<Map<String, Object>> list) throws ParseException {
		
		 SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
    	 Map<String, Object> tzMap = new HashMap<String, Object>(); //投资的
    	 Map<String, Object> yhMap = new HashMap<String, Object>();//用户的
    	 Map<String, Object> dkMap = new HashMap<String, Object>();//贷款金额
    	 Map<String, Object> dkYhMap = new HashMap<String, Object>();//贷款注册用户
    	 String nameSpace = "com.hehenian.biz.dal.system.ICommonQueryDao" ;//命名空间
    	 
		 Map<String, Object> map;
		 String thisMonDay = DateUtil.getMonDay();//这这周的星期一
		 String lastMonDay = DateUtil.getWeekDay(thisMonDay, -7);//上周星期一
		 String lastSunday = thisMonDay;//上周日
		 String monthFirstDay = DateUtil.getMonthFirstDay();//这个月第一天
		 String today =formatter.format(new Date()); //今天
		 String lastMonthMonDay = DateUtil.getWeekDay(thisMonDay, -28);//上月同周星期五
		 String lastMonthSunDay = DateUtil.getWeekDay(thisMonDay, -21);//上月同周星期四
		 
		 long size = DateUtil.diffDays(DateUtil.strToYYMMDDDate(thisMonDay), DateUtil.strToYYMMDDDate(today));
		 
		 /**
		  * 本周
		  */
		searchItems.put("applyTime",thisMonDay);
		searchItems.put("endTime",today);
		//投资额
		searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
		map = commonQueryService.getData(searchItems);
		tzMap.put("weeksData", getTwoXs(Convert.strToDouble(map.get("totalMoney")+"", 0.00)/size));
		
		//注册用户数
		searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
		map = commonQueryService.getData(searchItems);
		yhMap.put("weeksData",Math.floor(Convert.strToDouble(map.get("registerNumber")+"", 0.00)/size));
		
		//放款金额
		searchItems.put("selectMethodId", nameSpace+".getFkMoney");
		map = commonQueryService.getData(searchItems);
		dkMap.put("weeksData",getTwoXs(Convert.strToDouble(map.get("borrowAmount")+"", 0.00)/size));
		

		//融资注册人数
		searchItems.put("selectMethodId", nameSpace+".getDkRegisterNumber");
		map = commonQueryService.getData(searchItems);
		dkYhMap.put("weeksData",Math.floor(Convert.strToDouble(map.get("dkRegisterNumber")+"", 0.00)/size));
		
		/**
		 * 上周的
		 */
		searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
		searchItems.put("applyTime",lastMonDay);
		searchItems.put("endTime",lastSunday);
		
		searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
		map = commonQueryService.getData(searchItems);
		tzMap.put("lastWeeksData", getTwoXs(Convert.strToDouble(map.get("totalMoney")+"", 0.00)/7));
		
		//注册用户数
		searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
		map = commonQueryService.getData(searchItems);
		yhMap.put("lastWeeksData",Math.floor(Convert.strToDouble(map.get("registerNumber")+"", 0.00)/7));
		
		//放款金额
		searchItems.put("selectMethodId", nameSpace+".getFkMoney");
		map = commonQueryService.getData(searchItems);
		dkMap.put("lastWeeksData",getTwoXs(Convert.strToDouble(map.get("borrowAmount")+"", 0.00)/7));
		

		//融资注册人数
		searchItems.put("selectMethodId", nameSpace+".getDkRegisterNumber");
		map = commonQueryService.getData(searchItems);
		dkYhMap.put("lastWeeksData",Math.floor(Convert.strToDouble(map.get("dkRegisterNumber")+"", 0.00)/7));
		
		/**
		 * 上月同周的
		 */
		searchItems.put("applyTime",lastMonthMonDay);
		searchItems.put("endTime",lastMonthSunDay);
		
		searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
		map = commonQueryService.getData(searchItems);
		tzMap.put("lastMonthData", getTwoXs(Convert.strToDouble(map.get("totalMoney")+"", 0.00)/7));
		
		//注册用户数
		searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
		map = commonQueryService.getData(searchItems);
		yhMap.put("lastMonthData",Math.floor(Convert.strToDouble(map.get("registerNumber")+"", 0.00)/7));
		
		//放款金额
		searchItems.put("selectMethodId", nameSpace+".getFkMoney");
		map = commonQueryService.getData(searchItems);
		dkMap.put("lastMonthData",getTwoXs(Convert.strToDouble(map.get("borrowAmount")+"", 0.00)/7));
		

		//融资注册人数
		searchItems.put("selectMethodId", nameSpace+".getDkRegisterNumber");
		map = commonQueryService.getData(searchItems);
		dkYhMap.put("lastMonthData",Math.floor(Convert.strToDouble(map.get("dkRegisterNumber")+"", 0.00)/7));
		/**
		 * 截止到本月当天的
		 */
		searchItems.put("applyTime",monthFirstDay);
		searchItems.put("endTime",today);
		
		searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
		map = commonQueryService.getData(searchItems);
		tzMap.put("monthData", map!=null ? map.get("totalMoney"):"0");
		
		//注册用户数
		searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
		map = commonQueryService.getData(searchItems);
		yhMap.put("monthData",map!=null ? map.get("registerNumber"):"0");
		
		//放款金额
		searchItems.put("selectMethodId", nameSpace+".getFkMoney");
		map = commonQueryService.getData(searchItems);
		dkMap.put("monthData",map!=null ? map.get("borrowAmount"):"0");
		
		//融资注册人数
		searchItems.put("selectMethodId", nameSpace+".getDkRegisterNumber");
		map = commonQueryService.getData(searchItems);
		dkYhMap.put("monthData",map!=null ? map.get("dkRegisterNumber"):"0");
		
		searchItems.put("selectMethodId", nameSpace+".getKpiTargetVal");
		searchItems.put("targetMonth", DateUtil.getCurrentMonth());		
		map = commonQueryService.getData(searchItems);
	    tzMap.put("kpiVal", getTwoXs(Convert.strToDouble(map.get("investMoney")+"", 0.00)/30));
	    yhMap.put("kpiVal", Math.floor(Convert.strToDouble(map.get("investRegister")+"", 0.00)/30));
	    dkMap.put("kpiVal", getTwoXs(Convert.strToDouble(map.get("investMoney")+"", 0.00)/30));
	    dkYhMap.put("kpiVal", Math.floor(Convert.strToDouble(map.get("loanRegister")+"", 0.00)/30));
	    
	    tzMap.put("lastTb",  ( Double.valueOf(tzMap.get("weeksData")+"") ) / (Convert.strToDouble(tzMap.get("lastMonthData")+"",1)!= 0 ? Convert.strToDouble(tzMap.get("lastMonthData")+"",1) : 1)*100);
	    yhMap.put("lastTb",  ( Double.valueOf(yhMap.get("weeksData")+"") ) / (Convert.strToDouble(yhMap.get("lastMonthData")+"",1)!= 0 ? Convert.strToDouble(yhMap.get("lastMonthData")+"",1) : 1)*100);
	    dkMap.put("lastTb",  ( Double.valueOf(dkMap.get("weeksData")+"") ) / (Convert.strToDouble(dkMap.get("lastMonthData")+"",1)!= 0 ? Convert.strToDouble(dkMap.get("lastMonthData")+"",1) : 1)*100);
	    dkYhMap.put("lastTb",( Double.valueOf(dkYhMap.get("weeksData")+"")) / (Convert.strToDouble(dkYhMap.get("lastMonthData")+"",1)!= 0 ? Convert.strToDouble(dkYhMap.get("lastMonthData")+"",1) : 1)*100);
		
		long days = DateUtil.diffDays(formatter.parse(monthFirstDay),formatter.parse(today));
		long totalDays = DateUtil.diffDays(formatter.parse(monthFirstDay),formatter.parse(DateUtil.getMonthLastDay()));
		
		 tzMap.put("name","投资额（万元）");
		 yhMap.put("name", "注册用户数");
		 dkMap.put("name", "贷款金额（万元）");
		 dkYhMap.put("name", "贷款注册用户数");
		 
		 tzMap.put("growthRate", ( Double.valueOf(tzMap.get("weeksData")+"") - Double.valueOf(tzMap.get("lastWeeksData")+"")) / (Convert.strToDouble(tzMap.get("lastWeeksData")+"",1)!= 0 ? Convert.strToDouble(tzMap.get("lastWeeksData")+"",1) : 1)*100);
		 yhMap.put("growthRate", ( Double.valueOf(yhMap.get("weeksData")+"") - Double.valueOf(yhMap.get("lastWeeksData")+"")) / (Convert.strToDouble(yhMap.get("lastWeeksData")+"",1)!= 0 ? Convert.strToDouble(yhMap.get("lastWeeksData")+"",1) : 1)*100);
		 dkMap.put("growthRate", ( Double.valueOf(dkMap.get("weeksData")+"") - Double.valueOf(dkMap.get("lastWeeksData")+"")) / (Convert.strToDouble(dkMap.get("lastWeeksData")+"",1)!= 0 ? Convert.strToDouble(dkMap.get("lastWeeksData")+"",1) : 1) *100);
		 dkYhMap.put("growthRate", ( Double.valueOf(dkYhMap.get("weeksData")+"") - Double.valueOf(dkYhMap.get("lastWeeksData")+"")) / (Convert.strToDouble(dkYhMap.get("lastWeeksData")+"",1)!= 0 ? Convert.strToDouble(dkYhMap.get("lastWeeksData")+"",1) : 1)*100);
		 
		 tzMap.put("timeP", (days+1)+"/"+(totalDays+1));
		 yhMap.put("timeP", (days+1)+"/"+(totalDays+1));
		 dkMap.put("timeP", (days+1)+"/"+(totalDays+1));
		 dkYhMap.put("timeP", (days+1)+"/"+(totalDays+1));
		 
		 list.add(tzMap);
		 list.add(yhMap);
		 list.add(dkMap);
		 list.add(dkYhMap);
	}
	
	public String getTwoXs(Double number){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(number);
	}
	
	private void getkpiMonthData(List<Map<String, Object>> list) throws ParseException {
		
		 SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
   	 Map<String, Object> tzMap = new HashMap<String, Object>(); //投资的
   	 Map<String, Object> yhMap = new HashMap<String, Object>();//用户的
   	 Map<String, Object> dkMap = new HashMap<String, Object>();//贷款金额
   	 Map<String, Object> dkYhMap = new HashMap<String, Object>();//贷款注册用户
   	 String nameSpace = "com.hehenian.biz.dal.system.ICommonQueryDao" ;//命名空间
   	 
	 Map<String, Object> map;
	 String thisMonDay = DateUtil.getMonDay();//这这周的星期一
	 String thisSunday = DateUtil.getWeekDay(thisMonDay, 4);//这周星期星期五
	 String lastMonDay = DateUtil.getWeekDay(thisMonDay, -7);//上周星期一
	 String lastSunday = DateUtil.getWeekDay(thisMonDay, -3);//上周的星期四
	 String monthFirstDay = DateUtil.getMonthFirstDay();//这个月第一天
	 String today =formatter.format(new Date()); //今天
	 String lastMonthMonDay = DateUtil.getWeekDay(thisMonDay, -28);//上月同周星期五
	 String lastMonthSunDay = DateUtil.getWeekDay(thisMonDay, -24);//上月同周星期四
		 

		 /**
		  * 本周
		  */
		searchItems.put("applyTime",thisMonDay);
		searchItems.put("endTime",thisSunday);
		//投资额
		searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
		
		
		map = commonQueryService.getData(searchItems);
		tzMap.put("weeksData", map!=null ? map.get("totalMoney"):"0");
		
		//注册用户数
		searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
		map = commonQueryService.getData(searchItems);
		yhMap.put("weeksData",map!=null ? map.get("registerNumber"):"0");
		
		//放款金额
		searchItems.put("selectMethodId", nameSpace+".getFkMoney");
		map = commonQueryService.getData(searchItems);
		dkMap.put("weeksData",map!=null ? map.get("borrowAmount"):"0");
		
		//融资注册人数
		searchItems.put("selectMethodId", nameSpace+".getDkRegisterNumber");
		map = commonQueryService.getData(searchItems);
		dkYhMap.put("weeksData",map!=null ? map.get("dkRegisterNumber"):"0");
		/**
		 * 上周的
		 */
		searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
		searchItems.put("applyTime",lastMonDay);
		searchItems.put("endTime",lastSunday);
		
		map = commonQueryService.getData(searchItems);
		tzMap.put("lastWeeksData", map!=null ? map.get("totalMoney"):"0");
		
		//注册用户数
		searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
		map = commonQueryService.getData(searchItems);
		yhMap.put("lastWeeksData",map!=null ? map.get("registerNumber"):"0");
		
		//放款金额
		searchItems.put("selectMethodId", nameSpace+".getFkMoney");
		map = commonQueryService.getData(searchItems);
		dkMap.put("lastWeeksData",map!=null ? map.get("borrowAmount"):"0");
		
		//融资注册人数
		searchItems.put("selectMethodId", nameSpace+".getDkRegisterNumber");
		map = commonQueryService.getData(searchItems);
		dkYhMap.put("lastWeeksData",map!=null ? map.get("dkRegisterNumber"):"0");
		
		/**
		 * 上月同周的
		 */
		searchItems.put("applyTime",lastMonthMonDay);
		searchItems.put("endTime",lastMonthSunDay);
		
		searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
		map = commonQueryService.getData(searchItems);
		tzMap.put("lastMonthData", map!=null ? map.get("totalMoney"):"0");
		
		//注册用户数
		searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
		map = commonQueryService.getData(searchItems);
		yhMap.put("lastMonthData",map!=null ? map.get("registerNumber"):"0");
		
		//放款金额
		searchItems.put("selectMethodId", nameSpace+".getFkMoney");
		map = commonQueryService.getData(searchItems);
		dkMap.put("lastMonthData",map!=null ? "0.00".equals(map.get("borrowAmount")) ? 0: map.get("borrowAmount") :"0");
		

		//融资注册人数
		searchItems.put("selectMethodId", nameSpace+".getDkRegisterNumber");
		map = commonQueryService.getData(searchItems);
		dkYhMap.put("lastMonthData",map!=null ? map.get("dkRegisterNumber"):"0");
		/**
		 * 截止到本月当天的
		 */
		searchItems.put("applyTime",monthFirstDay);
		searchItems.put("endTime",today);
		
		searchItems.put("selectMethodId", nameSpace+".getInvestMoney");
		map = commonQueryService.getData(searchItems);
		tzMap.put("monthData", map!=null ? map.get("totalMoney"):"0");
		
		//注册用户数
		searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
		map = commonQueryService.getData(searchItems);
		yhMap.put("monthData",map!=null ? map.get("registerNumber"):"0");
		
		//放款金额
		searchItems.put("selectMethodId", nameSpace+".getFkMoney");
		map = commonQueryService.getData(searchItems);
		dkMap.put("monthData",map!=null ? map.get("borrowAmount"):"0");
		
		//融资注册人数
		searchItems.put("selectMethodId", nameSpace+".getDkRegisterNumber");
		map = commonQueryService.getData(searchItems);
		dkYhMap.put("monthData",map!=null ? map.get("dkRegisterNumber"):"0");
		
		searchItems.put("selectMethodId", nameSpace+".getKpiTargetVal");
		searchItems.put("targetMonth", DateUtil.getCurrentMonth());		
		map = commonQueryService.getData(searchItems);
	    tzMap.put("kpiVal", map.get("investMoney"));
	    yhMap.put("kpiVal", map.get("investRegister"));
	    dkMap.put("kpiVal", map.get("investMoney"));
	    dkYhMap.put("kpiVal", map.get("loanRegister"));
		
		long days = DateUtil.diffDays(formatter.parse(monthFirstDay),formatter.parse(today));
		long totalDays = DateUtil.diffDays(formatter.parse(monthFirstDay),formatter.parse(DateUtil.getMonthLastDay()));
		
		 tzMap.put("name","投资额（万元）");
		 yhMap.put("name", "注册用户数");
		 dkMap.put("name", "贷款金额（万元）");
		 dkYhMap.put("name", "贷款注册用户数");
		 
		 tzMap.put("growthRate", ( Double.valueOf(tzMap.get("weeksData")+"") - Double.valueOf(tzMap.get("lastWeeksData")+"")) / (Convert.strToDouble(tzMap.get("lastWeeksData")+"",1)!= 0 ? Convert.strToDouble(tzMap.get("lastWeeksData")+"",1) : 1)*100);
		 yhMap.put("growthRate", ( Double.valueOf(yhMap.get("weeksData")+"") - Double.valueOf(yhMap.get("lastWeeksData")+"")) / (Convert.strToDouble(yhMap.get("lastWeeksData")+"",1)!= 0 ? Convert.strToDouble(yhMap.get("lastWeeksData")+"",1) : 1)*100);
		 dkMap.put("growthRate", ( Double.valueOf(dkMap.get("weeksData")+"") - Double.valueOf(dkMap.get("lastWeeksData")+"")) / (Convert.strToDouble(dkMap.get("lastWeeksData")+"",1)!= 0 ? Convert.strToDouble(dkMap.get("lastWeeksData")+"",1) : 1) *100);
		 dkYhMap.put("growthRate", ( Double.valueOf(dkYhMap.get("weeksData")+"") - Double.valueOf(dkYhMap.get("lastWeeksData")+"")) / (Convert.strToDouble(dkYhMap.get("lastWeeksData")+"",1)!= 0 ? Convert.strToDouble(dkYhMap.get("lastWeeksData")+"",1) : 1)*100);
		 
		 tzMap.put("timeP", (days+1)+"/"+(totalDays+1));
		 yhMap.put("timeP", (days+1)+"/"+(totalDays+1));
		 dkMap.put("timeP", (days+1)+"/"+(totalDays+1));
		 dkYhMap.put("timeP", (days+1)+"/"+(totalDays+1));
		 
		 list.add(tzMap);
		 list.add(yhMap);
		 list.add(dkMap);
		 list.add(dkYhMap);
	}
	
	
	   /**
	    * KPI完成情况
	    * @return
	    */
		public String exportKpiData() {
			setRequestToParamMap();
			
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			try {
				kpiData(listMap);
				if (listMap.size() == 0) {
					getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
					return null;
				}
				if (listMap.size() > IConstants.EXCEL_MAX) {
					getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
					return null;
				}// 序号 用户名 真实姓名 手机号码 身份证 用户来源 注册时间 可用金额 冻结金额 待收金额
				HSSFWorkbook wb = ExcelUtils.exportExcel("KPI完成情况", listMap, new String[] { "KPI","上周（日均）kpi", "实际", "前周（日均）实际", "周增长率", "上月同周实际","上月同比", "本月（4月）到本周为止", "时间进度" },
						new String[] { "name", "", "weeksData","lastWeeksData", "growthRate", "lastMonthData", "", "monthData", "timeP"});
				this.export(wb, new Date().getTime() + ".xls");

			} catch (SQLException e) {

				e.printStackTrace();
			} catch (DataException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
    
}
