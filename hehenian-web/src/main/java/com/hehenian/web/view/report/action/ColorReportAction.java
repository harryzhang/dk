/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.view.report.action
 * @Title: ColorReportAction.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月13日 下午2:39:40
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.web.view.report.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.report.IColorReportService;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.web.base.action.PageAction;
import com.hehenian.web.common.util.ServletUtils;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月13日 下午2:39:40
 */
@Scope("prototype")
@Component("colorReportAction")
public class ColorReportAction  extends PageAction {
    
    private final Logger        logger = Logger.getLogger(this.getClass());
    
    @Autowired
    private IColorReportService   colorReportService;
    
    /**
     * 初始化查询页面
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月13日下午3:01:23
     */
    public String queryColorInvestReportInit(){
        return this.SUCCESS;
    }
    
    
    /**
     * 后台查询还款列表。 查找用户还款资金列表信息
     * 
     * @return
     */
    public String queryColorInvestReport() {
        //投资人的小区
        String cName = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("ciCname")), "");
        //推荐人的小区
        String refcName = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("refCname")), "");
        //投资人的所属事业部
        String busGroupName = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("ciBusGroupName")), "");
        //推荐人的所属事业部
        String refBusGroupName = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("refBusGroupName")), "");
        //投资开始时间
        String investStartDate = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("investTimeStart")), "");
        //投资结束时间
        String investEndDate = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("investTimeEnd")), "");
        //用户创建开始时间
        String registerStartDate = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("regTimeStart")), "");
        //用户创建结束时间
        String registerEndDate = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("regTimeEnd")), "");
        
        
        //求在库投资查询日期
        Map parameterMap = new HashMap();
        
        if(null != cName && !"".equals(cName)){
            parameterMap.put("cName", cName);
        }
        if(null != refcName && !"".equals(refcName)){
            parameterMap.put("refcName", refcName);
        }
        if(null != busGroupName && !"".equals(busGroupName)){
            parameterMap.put("busGroupName", busGroupName);
        }
        if(null != refBusGroupName && !"".equals(refBusGroupName)){
            parameterMap.put("refBusGroupName", refBusGroupName);
        }
        if(null != investStartDate && !"".equals(investStartDate)){
            parameterMap.put("investStartDate", investStartDate);
        }
        if(null != investEndDate && !"".equals(investEndDate)){
            parameterMap.put("investEndDate", investEndDate);
        }
        if(null != registerStartDate && !"".equals(registerStartDate)){
            parameterMap.put("registerStartDate", registerStartDate);
        }
        if(null != registerEndDate && !"".equals(registerEndDate)){
            parameterMap.put("registerEndDate", registerEndDate);
        }
        
        if(parameterMap.isEmpty()){
            return SUCCESS;
        }
        
        
        //在库金额查询时间， 如果没有指定，这设置为当前日期
        String inStockDate ="";
        if(null == investEndDate || "".equals(investEndDate)){
            inStockDate = DateUtils.formatDate(new Date());
        }else{
            inStockDate = DateUtils.getLastDayOfMonth(investEndDate);
            parameterMap.put("inStockDate", inStockDate);
        }
        
        pageBean = colorReportService.queryColorInvest(parameterMap, pageBean);
        int pageNum = (int) (pageBean.getPageNum()) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);
        return SUCCESS;
    }

}
