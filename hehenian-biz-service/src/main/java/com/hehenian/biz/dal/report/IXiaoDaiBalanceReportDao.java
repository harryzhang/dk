/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.report
 * @Title: IXiaoDaiBalanceReportDao.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午10:21:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.report;

import java.util.List;
import java.util.Map;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午10:21:53
 */
public interface IXiaoDaiBalanceReportDao {

    /** 
     * 小贷接口，定时任务 查询放款
     * @param jobDate
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午10:22:00
     */
    List<Map<String,Object>> queryPublishAmountByDate(String jobDate);

    /** 
     * 小贷接口，定时任务 查询扣款数据
     * @param jobDate
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午10:42:20
     */
    List<Map<String,Object>> queryRepayAmountByDate(String jobDate);

}
