package com.hehenian.web.view.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.system.ICommonQueryService;
import com.shove.Convert;
import com.shove.web.util.ExcelUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.util.DateUtil;

@Scope("prototype")
@Component("cliftAction")
public class CliftAction extends BaseFrontAction {
    private static final long   serialVersionUID = 1L;
    @Autowired
    private ICommonQueryService commonQueryService;
    public static String nameSpace = "com.hehenian.biz.dal.system.ICliftQueryDao";

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
    	Object obj = searchItems.get("startTime");
    	 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	 String sDate = null;
    	 String eDate = null;
    	 try {
		    	if(obj == null) {
		    		searchItems.put("startTime",DateUtil.dateFormartYMD(DateUtil.dateAddDay(new Date(),-1)));
		    		searchItems.put("endTime",DateUtil.dateFormartYMD(new Date()));
		    	}
		    	sDate = searchItems.get("startTime")+"";
		    	eDate = searchItems.get("endTime")+"";
		    	getClistData(list, sDate, eDate);
    	 
 		} catch (Exception e) {
 			throw new BusinessException("E理财每日数据表查询出错！");
 		}
        request().setAttribute("pageDo", list);
        request().setAttribute("startTime", sDate);
        request().setAttribute("endTime", eDate);
        return SUCCESS;
    }

	/**
	 * @param list
	 * @param sDate
	 * @param eDate
	 */
	private void getClistData(List<Map<String, Object>> list, String sDate,
			String eDate) {
		Map<String, Object> seachr;
		Map<String, Object> map;
		long size = DateUtil.diffDays(DateUtil.strToYYMMDDDate(sDate), DateUtil.strToYYMMDDDate(eDate));
		for(int i = 0; i < size; i++ ) {
			seachr = new HashMap<String, Object>();
			
			//注册人数
			searchItems.put("selectMethodId", nameSpace+".getRegisterNumber");
			searchItems.put("startTime", DateUtil.dateFormartYMD(DateUtil.dateAddDay(DateUtil.strToYYMMDDDate(sDate),i)));
			searchItems.put("endTime", DateUtil.dateFormartYMD(DateUtil.dateAddDay(DateUtil.strToYYMMDDDate(sDate),i+1)));
			map = commonQueryService.getData(searchItems);
			seachr.put("registerNumber", map!=null ? map.get("registerNumber"):"0");
			
			//总注册人数
			searchItems.put("selectMethodId", nameSpace+".getRegisterTotalNumber");
			map = commonQueryService.getData(searchItems);
			seachr.put("totalRegisterNumber", map!=null ? map.get("totalRegisterNumber"):"0");
			
			//今日登陆人数
			searchItems.put("selectMethodId", nameSpace+".getLoginNumber");
			map = commonQueryService.getData(searchItems);
			seachr.put("loginNumber",map!=null ? map.get("loginNumber"):"0");
			
			//今日投资人数
			searchItems.put("selectMethodId", nameSpace+".getInvestNumber");
			map = commonQueryService.getData(searchItems);
			seachr.put("investor",map!=null ? map.get("investor"):"0");
			
			//散标今日投资金额
			searchItems.put("selectMethodId", nameSpace+".getInvestAmount");
			map = commonQueryService.getData(searchItems);
			seachr.put("investAmount",map.get("investAmount")!=null ? map.get("investAmount"):"0.00");
			seachr.put("sbInvestNumber",map!=null ? map.get("sbInvestNumber"):"0");
			
			//散标总投资金额
			searchItems.put("selectMethodId", nameSpace+".getTotalInvestAmount");
			map = commonQueryService.getData(searchItems);
			seachr.put("totalInvestAmount",map.get("totalInvestAmount")!=null ? map.get("totalInvestAmount"):"0.00");
			seachr.put("totalSbInvestNumber",map!=null ? map.get("totalSbInvestNumber"):"0");
			
			//彩富人生 每天的充值金额
			searchItems.put("selectMethodId", nameSpace+".getRechargeMoney");
			map = commonQueryService.getData(searchItems);
			seachr.put("rechargeMoney",map.get("rechargeMoney")!=null ? map.get("rechargeMoney"):"0.00");
			seachr.put("ordNumber",map!=null ? map.get("ordNumber"):"0");
			
			//彩富人生购买单数
			searchItems.put("selectMethodId", nameSpace+".getOrdNumber");
			map = commonQueryService.getData(searchItems);
			seachr.put("tatolOrdNumber",map!=null ? map.get("tatolOrdNumber"):"0");
			seachr.put("totalRechargeMoney",map!=null ? map.get("totalRechargeMoney"):"0.00");
			
			//爱定宝今日交易笔数和今日购买金额
			searchItems.put("selectMethodId", nameSpace+".getTradeAmount");
			map = commonQueryService.getData(searchItems);
			seachr.put("tradeAmount",map.get("tradeAmount")!=null ? map.get("tradeAmount"):"0.00");
			seachr.put("tradeNumber",map!=null ? map.get("tradeNumber"):"0");
			
			//爱定宝累计交易笔数
			searchItems.put("selectMethodId", nameSpace+".getTotalTradeAmount");
			map = commonQueryService.getData(searchItems);
			seachr.put("totalTradeNumber",map!=null ? map.get("totalTradeNumber"):"0");
			seachr.put("totalTradeAmount",map!=null ? map.get("totalTradeAmount"):"0.00");
			
			//红包理财
			searchItems.put("selectMethodId", nameSpace+".getHbBuyMoney");
			map = commonQueryService.getData(searchItems);
			seachr.put("hbCount",map!=null ? map.get("hbCount"):"0");
			seachr.put("hbBuyMoney",map.get("hbBuyMoney")!=null ? map.get("hbBuyMoney"):"0.00");
			
			
			searchItems.put("selectMethodId", nameSpace+".getToTalHbBuyMoney");
			map = commonQueryService.getData(searchItems);
			seachr.put("totalHbCount",map!=null ? map.get("totalHbCount"):"0");
			seachr.put("totalHbBuyMoney",map.get("totalHbBuyMoney")!=null ? map.get("totalHbBuyMoney"):"0.00");
			
			seachr.put("sumAmount", Convert.strToDouble(seachr.get("tradeAmount")+"", 0.00) +Convert.strToDouble(seachr.get("rechargeMoney")+"", 0.00) + Convert.strToDouble(seachr.get("investAmount")+"", 0.00)+Convert.strToDouble(seachr.get("hbBuyMoney")+"", 0.00));
			
			seachr.put("createTime", searchItems.get("startTime"));

			list.add(seachr);
		}
	}
    
    /**
     * 查询彩生活投资明细
     * @return
     */
    public String InvestDeteail() {
    	//把页面的参数设置到map中
    	 setRequestToParamMap();
     	 Object obj = searchItems.get("startTime");
    	 List<Map<String,Object>> list = null;
    	 Map<String,Object> map = null;
    	 try {
	    	list = getInvestData(obj);
	    	

	    	
	    	Object flag = searchItems.get("flag");
			if(flag != null) {
				if(flag.equals("CFRS")) {//彩富人生
					searchItems.put("selectMethodId", nameSpace+".getCfTotalCount");
				}else if (flag.equals("SB")) {//散标
					searchItems.put("selectMethodId", nameSpace+".getSbTotalCount");
				}else if (flag.equals("DQLC")) {//爱定宝
					searchItems.put("selectMethodId", nameSpace+".getDqlcTotalCount");
				}else if(flag.equals("HBLC")) {
					searchItems.put("selectMethodId", nameSpace+".getHbTotalCount");
				}
			}else {
				searchItems.put("flag", "CFRS");
				searchItems.put("selectMethodId", nameSpace+".getCfTotalCount");
			}
			
	    	map = commonQueryService.getData(searchItems);
	    	
	    	request().setAttribute("orderDs", map.get("orderDs"));
	    	request().setAttribute("totalMoney", map.get("totalMoney"));
	    	
	    	searchItems.put("selectMethodId", nameSpace+".getSyb");
	    	request().setAttribute("syb",commonQueryService.getListMap(searchItems));
 		} catch (Exception e) {
 			throw new BusinessException("E理财每日数据表查询出错！");
 		}
        request().setAttribute("pageDo", list);
        request().setAttribute("flag", searchItems.get("flag"));
        request().setAttribute("startTime", searchItems.get("startTime"));
        request().setAttribute("endTime", searchItems.get("endTime"));
        request().setAttribute("buniess", request().getParameter("buniess"));
    	return SUCCESS;
    }

	/**
	 * @param obj
	 * @return
	 */
	private List<Map<String, Object>> getInvestData(Object obj) {
		List<Map<String, Object>> list;
		if(obj == null) {
			searchItems.put("startTime",DateUtil.dateFormartYMD(DateUtil.dateAddDay(new Date(),-1)));
			searchItems.put("endTime",DateUtil.dateFormartYMD(new Date()));
		}
		Object flag = searchItems.get("flag");
		if(flag != null) {
			if(flag.equals("CFRS")) {//彩富人生
				searchItems.put("selectMethodId", nameSpace+".getCfOrderData");
			}else if (flag.equals("SB")) {//散标
				searchItems.put("selectMethodId", nameSpace+".getSbInvestData");
			}else if (flag.equals("DQLC")) {//爱定宝
				searchItems.put("selectMethodId", nameSpace+".getDqlcInvestData");
			}else if(flag.equals("HBLC")) {
				searchItems.put("selectMethodId", nameSpace+".getHbbuyMoneyData");
			}
		}else {
			searchItems.put("flag", "CFRS");
			searchItems.put("selectMethodId", nameSpace+".getCfOrderData");
		}
		list = commonQueryService.getListMap(searchItems);
		return list;
	}
    
    /**
	 * E理财每日数据表导出excel
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String exportEMsg() {
		setRequestToParamMap();
    	List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		try {
			getClistData(listMap,searchItems.get("startTime")+"",searchItems.get("endTime")+"");
			if (listMap.size() == 0) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (listMap.size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}// 序号 用户名 真实姓名 手机号码 身份证 用户来源 注册时间 可用金额 冻结金额 待收金额
			HSSFWorkbook wb = ExcelUtils.exportExcel("E理财每日数据表", listMap, new String[] { "日期", "今日注册人数", "累计注册人数", "今日登陆人数", "今日投资人数", "散标今日投资金额(不包含彩富人生)", "今日交易笔数","累计交易笔数", "总投资金额(不包含彩富人生)","彩富人生今日订单完成数","累计订单完成数","今日订单完成金额","累计完成订单金额", "爱定宝今日购买金额", "交易笔数","累计交易笔数","累计购买金额", "红包理财交易笔数","累计交易笔数","今日购买金额","累计购买金额", "今日进账总额" },
					new String[] { "createTime", "registerNumber", "totalRegisterNumber", "loginNumber", "investor", "investAmount", "sbInvestNumber", "totalSbInvestNumber", "totalInvestAmount", "ordNumber", "tatolOrdNumber","rechargeMoney","totalRechargeMoney","tradeAmount","tradeNumber","totalTradeNumber","totalTradeAmount","hbCount","totalHbCount","hbBuyMoney","totalHbBuyMoney","sumAmount" });
			this.export(wb, new Date().getTime() + ".xls");

		}  catch (Exception e) {
			throw new BusinessException("E理财每日数据表导出execl出错！");
		}
		return null;
	}
	
	
    /**
	 * 投资明细表导出excel
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String exportInvestData() {
		setRequestToParamMap();
    	List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
    	Object obj = searchItems.get("startTime");
		try {
			searchItems.put("buniess", java.net.URLDecoder.decode(searchItems.get("buniess").toString(), "UTF-8"));
			listMap = getInvestData(obj);
			if (listMap.size() == 0) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (listMap.size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}// 序号 用户名 真实姓名 手机号码 身份证 用户来源 注册时间 可用金额 冻结金额 待收金额
			HSSFWorkbook wb = ExcelUtils.exportExcel("投资明细表", listMap, new String[] {"日期", "订单号", "用户名", "姓名", "手机号码", "推荐人", "推荐手机号码", "小区","小区信息/社区", "事业部","期数","投资金额" },
					new String[] {"investTime","bh", "username", "realName", "mobilePhone", "refferee", "tMobilePhone", "cName","caddress", "bname","rate", "investAmount" });
			this.export(wb, new Date().getTime() + ".xls");

		}  catch (Exception e) {
			throw new BusinessException("投资明细表导出execl出错！");
		}
		return null;
	}
    
}
