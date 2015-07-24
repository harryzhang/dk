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
package com.hehenian.biz.component.report;

import java.util.List;
import java.util.Map;


/**
 * 
 * @author: zhangyunhmf
 * @date 2014年10月16日 下午2:19:08
 */
public interface IXiaoDaiBalanceReportComponent {
    
    /**
     * 小贷接口，定时任务 查询放款
     * @param jobDate
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午10:39:43
     */
    public List<Map<String,Object>> queryPublishAmountByDate(String jobDate);
    
    /**
     * 小贷接口，定时任务 查询扣款数据
     * @param jobDate
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午10:40:48
     */
    public List<Map<String,Object>> queryRepayAmountByDate(String jobDate);
    
}
