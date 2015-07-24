/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.report
 * @Title: IColorReportComponent.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:21:09
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.report;

import java.util.Date;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:21:09
 */
public interface IColorReportComponent {
    
    /**
     * 根据条件查询列表，用于翻页
     * @param parameterMap 查询条件
     * @param page 翻页对象， 列表的数据放在page里
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月10日下午6:55:50
     */
    public PageDo queryColorInvest(Map<String,Object> parameterMap,PageDo page);

    public Map<String, Object> queryFKByDate(String date) ;

    public Map<String, Object> queryInvestByDate(String date) ;

    public Map<String, Object> queryUserByDate(String date) ;

    public Map<String, Object> queryFK() ;

    public Map<String, Object> queryInvest() ;

    public Map<String, Object> queryUser() ;

}
