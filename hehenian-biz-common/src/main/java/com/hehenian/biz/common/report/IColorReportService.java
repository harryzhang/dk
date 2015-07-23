/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.report
 * @Title: IColorInvestReport.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:16:12
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.report;

import java.util.Date;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:16:12
 */
public interface IColorReportService {
    
    /**
     * 根据条件查询列表，用于翻页
     * @param parameterMap 查询条件
     * @param page 翻页对象， 列表的数据放在page里
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月10日下午6:55:50
     */
    public PageDo queryColorInvest(Map<String,Object> parameterMap,PageDo page);

    /**
     * 查询某天放款额
     * @param date
     * @return
     */
    public Map<String,Object> queryFKByDate(String date);

    /**
     * 查询某天投资额
     * @param date
     */
    public Map<String,Object> queryInvestByDate(String date);

    /**
     * 注册数
     */
    public Map<String,Object> queryUserByDate(String date);

    public Map<String,Object> queryFK();
    public Map<String,Object> queryInvest();
    public Map<String,Object> queryUser();
}
