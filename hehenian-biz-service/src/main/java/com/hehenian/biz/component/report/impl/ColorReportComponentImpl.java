/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.report.impl
 * @Title: ColorReportComponentImpl.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:23:52
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.report.impl;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.component.report.IColorReportComponent;
import com.hehenian.biz.dal.report.IColorReportDao;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:23:52
 */
@Component("colorReportComponent")
public class ColorReportComponentImpl implements IColorReportComponent {
    
    @Autowired
    IColorReportDao colorReportDao;

    /* (no-Javadoc) 
     * <p>Title: queryColorInvest</p> 
     * <p>Description: </p> 
     * @param parameterMap
     * @param page
     * @return 
     * @see com.hehenian.biz.component.report.IColorReportComponent#queryColorInvest(java.util.Map, com.hehenian.biz.common.base.dataobject.PageDo, java.lang.String) 
     */
    @Override
    public PageDo queryColorInvest(Map<String, Object> parameterMap, PageDo page) {
        
        parameterMap.put("page", page);
        
        List resultList = colorReportDao.queryColorInvestorPage(parameterMap);
        if(null == resultList || resultList.isEmpty()) return page;
        //提取本页用户ID用来查询汇总的投资额
        Map<String,Object> rowMap = wrapColorInvest(resultList);
        List userIdList = (List)rowMap.get("userIdList");
        String startDate = (String) parameterMap.get("investStartDate");
        String endDate = (String) parameterMap.get("investEndDate");
        
        //在库投资额查询日期
        String inStockDate = (String) parameterMap.get("inStockDate");
        
        //汇总投资 , hessian 不支持BigDecimal数据类型， 所以要转成double
        List<Map<String,Object>> investAmountList = colorReportDao.queryInvestTotalAmount(userIdList, startDate, endDate );
        if(null != investAmountList && !investAmountList.isEmpty()){
            for(Map<String,Object> investMap :investAmountList){
                Long investor = (Long)investMap.get("investor");
                //BigDecimal recivedPrincipal  = investMap.get("recivedPrincipal") == null? BigDecimal.ZERO :(BigDecimal)investMap.get("recivedPrincipal");
                BigDecimal investAmount = investMap.get("investAmount") == null? BigDecimal.ZERO :(BigDecimal)investMap.get("investAmount");
                BigDecimal hasInterest = investMap.get("hasInterest") == null? BigDecimal.ZERO :(BigDecimal)investMap.get("hasInterest");
                
                //计算彩生活收益 *0.02/12
                //double colorAmount = CalculateUtils.round(CalculateUtils.mul(recivedPrincipal.doubleValue(), 0.02/12),2);
                
                Map<String,Object> userMap= (Map)rowMap.get(investor+"");
                if(null != userMap){
                    //userMap.put("recivedPrincipal", recivedPrincipal.doubleValue()+"");
                    userMap.put("investAmount", investAmount.doubleValue()+"");
                    userMap.put("hasInterest", hasInterest.doubleValue()+"");
                    //userMap.put("colorAmount", colorAmount+"");
                }
                
            }
        }
        
        //在库投资额   hessian 不支持BigDecimal数据类型， 所以要转成double
        List<Map<String,Object>> reffereeAmountList = colorReportDao.queryInStockInvestAmount(userIdList,inStockDate);
        if(null != reffereeAmountList && !reffereeAmountList.isEmpty()){
            for(Map<String,Object> investMap :reffereeAmountList){
                Long investor = (Long)investMap.get("investor");
                BigDecimal recivedPrincipal = investMap.get("recivedPrincipal") == null? BigDecimal.ZERO:(BigDecimal)investMap.get("recivedPrincipal");
                
                //计算彩生活收益 *0.02/12
                double colorAmount = CalculateUtils.round(CalculateUtils.mul(recivedPrincipal.doubleValue(), 0.02/12),2,BigDecimal.ROUND_DOWN);
                //推荐人收益
                double refAmount = CalculateUtils.round(CalculateUtils.mul(recivedPrincipal.doubleValue(), 0.005/12),2,BigDecimal.ROUND_DOWN);

                Map<String,Object> userMap= (Map)rowMap.get(investor+"");
                if(null != userMap){
                    userMap.put("recivedPrincipal", recivedPrincipal.doubleValue()+"");
                    userMap.put("colorAmount", colorAmount+"");
                    userMap.put("refAmount", refAmount+"");
                }
            }
        }
        
        rowMap.remove("userIdList");
        resultList.clear();
        resultList.addAll(rowMap.values());
        page.setPage(resultList);
        return page;
    }

    @Override public Map<String, Object> queryFKByDate(String date) {
        return colorReportDao.queryFKByDate(date);
    }

    @Override public Map<String, Object> queryInvestByDate(String date) {
        return colorReportDao.queryInvestByDate(date);
    }

    @Override public Map<String, Object> queryUserByDate(String date) {
        return colorReportDao.queryUserByDate(date);
    }

    @Override public Map<String, Object> queryFK() {
        return colorReportDao.queryFK();
    }

    @Override public Map<String, Object> queryInvest() {
        return colorReportDao.queryInvest();
    }

    @Override public Map<String, Object> queryUser() {
        return colorReportDao.queryUser();
    }

    private Map<String,Object> wrapColorInvest(List<Map<String,Object>> userList){
        if(null == userList || userList.isEmpty()) return null;
        Map<String,Object>  rowMap = new HashMap<String,Object>();
        List userIdList = new ArrayList();
        for(Map<String,Object> row : userList){
           long userId = (Long)row.get("userId");
           Date investTime = (Date)row.get("investTime"); 
           userIdList.add(userId);
           //start 初始投资金额为0
           row.put("investAmount", 0);
           row.put("recivedPrincipal", 0);
           row.put("firstMonthInvestAmount", 0);
           row.put("hasInterest", 0);
           row.put("investTime", DateUtils.formatDate(investTime));
           //end 初始投资金额为0
           rowMap.put(userId+"", row);
           
        }
        rowMap.put("userIdList", userIdList);
        return rowMap;
    }

}
