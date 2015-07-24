package com.hehenian.web.view.trade.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctc.wstx.util.DataUtil;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.IBorrowService;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.ServletUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;

@Scope("prototype")
@Controller("borrowAction")
public class BorrowAction extends ActionSupport implements ServletRequestAware, SessionAware {
    private static final long   serialVersionUID = 1L;
    @Autowired
    private IBorrowService      newBorrowService;
    private HttpServletRequest  request;
    private Map<String, Object> session;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * 
     * @return
     */
    public String updateBorrowFullScale() {
        // 获取参数
        String borrowId = request.getParameter("paramMap.id");// 借款标的ID
        String status = request.getParameter("paramMap.status");// 放款状态，是否放款
        String auditOpinion = request.getParameter("paramMap.auditOpinion");//
        Admin admin = (Admin) session.get(IConstants.SESSION_ADMIN);

        IResult<?> result = newBorrowService.updateBorrowFullScale(Long.parseLong(borrowId), Integer.parseInt(status),
                auditOpinion, admin.getId());
        JSONObject json = new JSONObject();
        if (result.isSuccess()) {
            json.put("msg", "1");
            ServletUtils.writeJson(json.toString());
            return null;
        } else {
            json.put("msg", result.getErrorMessage());
            ServletUtils.writeJson(json.toString());
            return null;
        }
    }

    /**
     * 标的列表
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年11月21日上午9:37:17
     */
    public String queryBorrows() {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        searchItems.put("purpose", request.getParameter("purpose"));
        searchItems.put("deadline", request.getParameter("deadline"));
        searchItems.put("arStart", request.getParameter("arStart"));
        searchItems.put("arEnd", request.getParameter("arEnd"));
        AccountUserDo user = (AccountUserDo) session.get(WebConstants.SESSION_USER);
        searchItems.put("borrowGroup", user.getUserGroup());
        searchItems.put("borrowStatuses", new Integer[] { 2, 3, 4, 5 });
        searchItems.put("curPage", request.getParameter("curPage"));
        searchItems.put("pageSize", 10);
        long currentPage = Long.parseLong(request.getParameter("curPage"));
        searchItems.put("beginCount", (currentPage - 1) * 10);
        searchItems.put("pageSize", 10);

        NPageDo<BorrowDo> pageDo = newBorrowService.queryBorrows(currentPage, 10L, searchItems);

        request.setAttribute("pageDo", pageDo);
        return SUCCESS;
    }

	/**
     * @MethodName: loanBorrowList
     * @Param: BorrowAction
     * @Author: zhough
     * @Return:
     * @Descb: 合合贷与精英贷已放款标的
     * @Throws:
     */
    public String loanBorrowList() throws SQLException, DataException {
        
    	Map<String,Object> searchItems = new HashMap<String, Object>();
    	
    	String curPage = request.getParameter("curPage") == null?"1":(String)request.getParameter("curPage");
    	
        searchItems.put("curPage", curPage);
        searchItems.put("pageSize", 10);
        long currentPage = Long.parseLong(curPage);
        searchItems.put("beginCount", (currentPage - 1) * 10);
        searchItems.put("pageSize", 10);
        
        String fundWay = request.getParameter("fundWay") == null ? "" : SqlInfusion.FilteSqlInfusion(request.getParameter("fundWay"));
        String loanTime1 = request.getParameter("loanTime1") == null ? "" : SqlInfusion.FilteSqlInfusion(request.getParameter("loanTime1"));
        String loanTime2 = request.getParameter("loanTime2") == null ? "" : SqlInfusion.FilteSqlInfusion(request.getParameter("loanTime2"));
		
		if(StringUtils.isNotBlank(loanTime1)){
			searchItems.put("auditTime1", loanTime1);
		}
		
		if(StringUtils.isNotBlank(loanTime2)){
			searchItems.put("auditTime2", loanTime2);
		}
		int fundWayInt = Convert.strToInt(fundWay, -1);
		if(IConstants.DEFAULT_NUMERIC != fundWayInt){
			if(fundWayInt == 8)
				searchItems.put("paymentModes", new Integer[]{8,9});
			else if(fundWayInt == 10)
				searchItems.put("paymentModes", new Integer[]{10,11});
		}else{
			searchItems.put("paymentModes", new Integer[]{8,9,10,11});
		}

        List<Map<String,Object>> pageDo = newBorrowService.queryLoanBorrowList(searchItems);
        NPageDo<Map<String,Object>> result = new NPageDo<Map<String,Object>>(currentPage, 10L, 0L, null);
        result.setCommonModeList(pageDo);
        
        Map<Integer,String> map= new HashMap<Integer,String> ();  
        map.put(8, "合和贷");  
        map.put(10, "精英贷");  
        request.setAttribute("map", map);  

        request.setAttribute("pageDo", result);
        request.setAttribute("fundWay", fundWayInt);
        request.setAttribute("loanTime1", loanTime1);
        request.setAttribute("loanTime2", loanTime2);
        
        return "success";
    }
    
    /**
     * @MethodName: loanBorrowCheckList
     * @Param: BorrowAction
     * @Author: zhough
     * @Return:
     * @Descb: 合和贷与精英贷查看投资人
     * @Throws:
     */
    public String loanBorrowCheckList() throws SQLException, DataException {
    	
        String borrowId = (String)request.getParameter("id") == null ? "" : SqlInfusion.FilteSqlInfusion(request.getParameter("id"));
        
        int borrowIdInt = Convert.strToInt(borrowId, -1);
        
        Map<String,Object> searchItems = new HashMap<String, Object>();
        searchItems.put("borrowId", borrowId);
        
        List<Map<String,Object>> result =  newBorrowService.queryloanBorrowUserList(searchItems);

        request.setAttribute("results", result);
        return "success";
    }
    
    /**
     * @MethodName: loanBorrowCheckList
     * @Param: BorrowManageAction
     * @Author: zhough
     * @return
     * @Descb: 合和贷与精英贷的利率管理费率组装
     */
    public String loanborrowFeeList(){
    	List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
    	
    	String rate = request.getParameter("rate") ==null?"":(String)request.getParameter("rate");
    	int irate = Integer.parseInt(rate);
    	
    	for(int i=1;i<7;i++){
    		Map<String,Object> map = new HashMap<String, Object>();
    		map.put("rate", i+"/"+irate);
    		map.put("annualRate", "1.30%");
    		map.put("feeRate", "0.80%");
    		resultList.add(map);
    	}
    	
    	for(int i=7;i<13;i++){
    		Map<String,Object> map = new HashMap<String, Object>();
    		map.put("rate", i+"/"+irate);
    		map.put("annualRate", "1.10%");
    		map.put("feeRate", "0.80%");
    		resultList.add(map);
    	}
    	
    	for(int i=13;i<19;i++){
    		Map<String,Object> map = new HashMap<String, Object>();
    		map.put("rate", i+"/"+irate);
    		map.put("annualRate", "0.70%");
    		map.put("feeRate", "0.80%");
    		resultList.add(map);
    	}
    	
    	for(int i=19;i<25;i++){
    		Map<String,Object> map = new HashMap<String, Object>();
    		map.put("rate", i+"/"+irate);
    		map.put("annualRate", "0.00%");
    		map.put("feeRate", "0.80%");
    		resultList.add(map);
    	}
    	
    	if(irate == 36){
    		for(int i=25;i<34;i++){
        		Map<String,Object> map = new HashMap<String, Object>();
        		map.put("rate", i+"/"+irate);
        		map.put("annualRate", "0.00%");
        		map.put("feeRate", "0.80%");
        		resultList.add(map);
        	}
        	
        	for(int i=34;i<37;i++){
        		Map<String,Object> map = new HashMap<String, Object>();
        		map.put("rate", i+"/"+irate);
        		map.put("annualRate", "0.00%");
        		map.put("feeRate", "0.00%");
        		resultList.add(map);
        	}
    	}
    	
    	request.setAttribute("results", resultList);
    	return "success";
    }

	/**
     * 根据日期查询放款
     * 
     * @return
     * @author: zhough
     * @throws ParseException 
     * @date: 2015年5月7日
     */
    public String loanQuery() throws ParseException{
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> searchItems = new HashMap<String, Object>();
        
        String loanDate = request.getParameter("loanDate");
        String signTmp = request.getParameter("sign");
        
        if (StringUtils.isBlank(loanDate)
                || StringUtils.isBlank(signTmp)
                || !DigestUtils.md5Hex(WebConstants.XD_PASS_KEY + loanDate + WebConstants.XD_PASS_KEY)
                        .equalsIgnoreCase(signTmp)) {
            jsonObject.put("success", false);
            jsonObject.put("message", "请求参数非法!");
            ServletUtils.writeJson(jsonObject.toString());
            return null;
        }
        
        if(StringUtils.isNotBlank(loanDate)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    		Date date;
    		try {
    			date = format.parse(loanDate);
    			format = new SimpleDateFormat("yyyy-MM-dd");
    			searchItems.put("auditTime1", format.format(date));
    			searchItems.put("auditTime2", format.format(date));
    		} catch (ParseException e) {
    			jsonObject.put("success",false);
    	        jsonObject.put("message","还款日期格式不正确!");
    	        ServletUtils.writeJson(jsonObject.toString());
    	        return null;
    		}//有异常要捕获
        }else{
        	jsonObject.put("success",false);
	        jsonObject.put("message","请输入还款日期!");
	        ServletUtils.writeJson(jsonObject.toString());
	        return null;
        }
//        searchItems.put("auditTime1", "2015-05-05");
//		searchItems.put("auditTime2", "2015-05-05");
  
        List<Map<String,Object>> loanList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> pageDo = newBorrowService.queryLoanBorrowList(searchItems);
        if(pageDo != null && pageDo.size()>=0){
        	for(Map<String,Object> tmpMap:pageDo){
        		Map<String,Object> loanMap = new HashMap<String, Object>();
        		loanMap.put("realName", (String)tmpMap.get("realName"));
        		loanMap.put("idNo", (String)tmpMap.get("idNo"));
        		
        		String auditTime = (String)tmpMap.get("auditTime");
        		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        		Date date = format.parse(auditTime);//有异常要捕获
        		format = new SimpleDateFormat("yyyy/MM/dd");
        		String newD = format.format(date);
        		
        		loanMap.put("loanDate", newD);
        		loanMap.put("borrowAmount", tmpMap.get("borrowAmount").toString());
        		loanMap.put("loanPeriod", tmpMap.get("deadline").toString());
        		loanMap.put("businessNo", (String)tmpMap.get("businessNo"));
        		loanMap.put("platform", "合和年在线");
        		loanMap.put("annualRate", tmpMap.get("annualRate").toString());
        		loanMap.put("channel", tmpMap.get("type").toString());
        		loanList.add(loanMap);
        	}
        }
        
        jsonObject.put("success",true);
        jsonObject.put("loanList",loanList);
        
        String jsonString = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
        	jsonString = mapper.writeValueAsString(loanList);
		} catch (Exception e) {
			jsonObject.put("success",false);
	        jsonObject.put("message","结果列表转换错误");
	        ServletUtils.writeJson(jsonObject.toString());
	        return null;
		} 

        System.out.println("哇哈哈"+WebConstants.XD_PASS_KEY + jsonString + WebConstants.XD_PASS_KEY);
        String sign = DigestUtils.md5Hex(WebConstants.XD_PASS_KEY + jsonString + WebConstants.XD_PASS_KEY);
        
        jsonObject.put("sign",sign);
        
        ServletUtils.writeJson(jsonObject.toString());
        return null;
    }
    
    /**
     * 根据还款日期查询还款
     * 
     * @return
     * @author: zhough
     * @throws ParseException 
     * @date: 2015年5月7日
     */
    public String repaymentQuery() {
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> searchItems = new HashMap<String, Object>();
        
        String repayDate = request.getParameter("repayDate");
        String signTmp = request.getParameter("sign");
        
        if (StringUtils.isBlank(repayDate)
                || StringUtils.isBlank(signTmp)
                || !DigestUtils.md5Hex(WebConstants.XD_PASS_KEY + repayDate + WebConstants.XD_PASS_KEY)
                        .equalsIgnoreCase(signTmp)) {
            jsonObject.put("success", false);
            jsonObject.put("message", "请求参数非法!");
            ServletUtils.writeJson(jsonObject.toString());
            return null;
        }
        
        if(StringUtils.isNotBlank(repayDate)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    		Date date;
    		try {
    			date = format.parse(repayDate);
    			format = new SimpleDateFormat("yyyy-MM-dd");
    			searchItems.put("repayDate", format.format(date));
//    			searchItems.put("repayDate", repayDate);
    		} catch (ParseException e) {
    			jsonObject.put("success",false);
    	        jsonObject.put("message","还款日期格式不正确!");
    	        ServletUtils.writeJson(jsonObject.toString());
    	        return null;
    		}//有异常要捕获
        }else{
        	jsonObject.put("success",false);
	        jsonObject.put("message","请输入还款日期!");
	        ServletUtils.writeJson(jsonObject.toString());
	        return null;
        }
		
		DecimalFormat fmt = new DecimalFormat("0.##");
  
        List<Map<String,Object>> loanList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> pageDo = newBorrowService.queryRepaymentList(searchItems);
        if(pageDo != null && pageDo.size()>=0){
        	for(Map<String,Object> tmpMap:pageDo){
        		Map<String,Object> loanMap = new HashMap<String, Object>();
        		loanMap.put("realName", (String)tmpMap.get("realName"));
        		loanMap.put("idNo", (String)tmpMap.get("idNo"));
        		loanMap.put("businessNo", (String)tmpMap.get("businessNo"));
        		loanMap.put("loanPeriod", tmpMap.get("repayPeriod").toString());
        		loanMap.put("repayDate", repayDate);
        		
        		loanMap.put("principalAmt", fmt.format(tmpMap.get("stillPrincipal")).toString());
        		loanMap.put("iterestAmt", fmt.format(tmpMap.get("stillInterest")).toString());
        		loanMap.put("consultFeeAmt", fmt.format(tmpMap.get("consultFee")).toString());
        		loanMap.put("repayAmt", fmt.format(tmpMap.get("stillSumAmount")).toString());
        		loanMap.put("preSettleAmt", fmt.format(tmpMap.get("preSettleAmount")).toString());
        		
        		loanMap.put("remainPrincipalAmt", fmt.format(tmpMap.get("principalBalance")).toString());
        		loanMap.put("lateFeeAmt", fmt.format(tmpMap.get("lateFI")).toString());
        		loanMap.put("lateDay", tmpMap.get("lateDay").toString());
        		loanMap.put("repayStatus", tmpMap.get("finishedFlag").toString());
        		loanMap.put("repayFee", fmt.format(tmpMap.get("repayFee")).toString());
        		
        		loanMap.put("servFeeAmt", fmt.format(tmpMap.get("serviceFee")).toString());
        		loanMap.put("creditFeeAmt", fmt.format(tmpMap.get("zhengxinFee")).toString());
        		loanMap.put("parkingFeeAmt", fmt.format(tmpMap.get("tingcheFee")).toString());
        		loanMap.put("regFeeAmt", fmt.format(tmpMap.get("registerFee")).toString());
        		loanMap.put("preSettleFeeAmt", fmt.format(tmpMap.get("preSettleFee")).toString());

        		loanMap.put("realRepayAmt", fmt.format(tmpMap.get("realSumAmount")).toString());
        		loanList.add(loanMap);
        	}
        }
        
        jsonObject.put("success",true);
        jsonObject.put("repaymentList",loanList);
        
        String jsonString = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
        	jsonString = mapper.writeValueAsString(loanList);
		} catch (Exception e) {
			jsonObject.put("success",false);
	        jsonObject.put("message","结果列表转换错误");
	        ServletUtils.writeJson(jsonObject.toString());
	        return null;
		} 
        
        String sign = DigestUtils.md5Hex(WebConstants.XD_PASS_KEY + jsonString + WebConstants.XD_PASS_KEY);
        
        jsonObject.put("sign",sign);
        
        ServletUtils.writeJson(jsonObject.toString());
        return null;
    }

}
