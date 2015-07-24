/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.report
 * @Title: IXiaoDaiBalanceReportComponent.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月16日 下午2:19:08
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.component.report.IXiaoDaiBalanceReportComponent;
import com.hehenian.biz.dal.report.IXiaoDaiBalanceReportDao;

/**
 * 
 * @author: zhangyunhmf
 * @date 2014年10月16日 下午2:19:08
 */
@Component("xiaoDaiBalanceReportComponent")
public class XiaoDaiBalanceReportComponentImpl implements IXiaoDaiBalanceReportComponent  {

    @Autowired
    private IXiaoDaiBalanceReportDao xiaoDaiBalanceReportDao;
    
    public List<Map<String,Object>> queryPublishAmountByDate(String jobDate){
        
        return xiaoDaiBalanceReportDao.queryPublishAmountByDate(jobDate);
        
    }
    
    /* (no-Javadoc) 
     * <p>Title: queryRepayAmountByDate</p> 
     * <p>Description: </p> 
     * @param jobDate
     * @return 
     * @see com.hehenian.biz.component.report.IXiaoDaiBalanceReportComponent#queryRepayAmountByDate(java.lang.String) 
     */
    @Override
    public List<Map<String,Object>> queryRepayAmountByDate(String jobDate) {
        return xiaoDaiBalanceReportDao.queryRepayAmountByDate(jobDate);
    }
}
