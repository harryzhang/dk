/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.report.impl
 * @Title: ColorReport.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:19:55
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.report.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.report.IColorReportService;
import com.hehenian.biz.component.report.IColorReportComponent;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:19:55
 */
@Component("colorReportService")
public class ColorReportServiceImpl implements IColorReportService {
    
    @Autowired
    IColorReportComponent colorReportComponent;

    /* (no-Javadoc) 
     * <p>Title: queryColorInvest</p> 
     * <p>Description: </p> 
     * @param parameterMap
     * @param page
     * @return 
     * @see com.hehenian.biz.common.report.IColorReport#queryColorInvest(java.util.Map, com.hehenian.biz.common.base.dataobject.PageDo, java.lang.String) 
     */
    @Override
    public PageDo queryColorInvest(Map<String, Object> parameterMap, PageDo page) {
        return colorReportComponent.queryColorInvest(parameterMap, page);
    }

    @Override public Map<String, Object> queryFKByDate(String date) {
        Map<String, Object> stringObjectMap = colorReportComponent.queryFKByDate(date);
        stringObjectMap.put("jer",stringObjectMap.get("jer").toString());
        return stringObjectMap;
    }

    @Override public Map<String, Object> queryInvestByDate(String date) {
        Map<String, Object> stringObjectMap = colorReportComponent.queryInvestByDate(date);
        stringObjectMap.put("jer",stringObjectMap.get("jer").toString());
        return stringObjectMap;
    }

    @Override public Map<String, Object> queryUserByDate(String date) {

        return colorReportComponent.queryUserByDate(date);
    }

    @Override public Map<String, Object> queryFK() {
        Map<String, Object> stringObjectMap = colorReportComponent.queryFK();
        stringObjectMap.put("jer",stringObjectMap.get("jer").toString());
        return stringObjectMap;
    }

    @Override public Map<String, Object> queryInvest() {
        Map<String, Object> stringObjectMap = colorReportComponent.queryInvest();
        stringObjectMap.put("jer",stringObjectMap.get("jer").toString());
        return stringObjectMap;
    }

    @Override public Map<String, Object> queryUser() {

        return colorReportComponent.queryUser();
    }

}
