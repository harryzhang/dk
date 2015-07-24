/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.report
 * @Title: IColorReportDao.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:25:49
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月13日 上午11:25:49
 */
public interface IColorReportDao {
    
    /**
     * 根据条件查询列表，用于翻页
     * @param parameterMap 查询条件
     * @param page 翻页对象， 列表的数据放在page里
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月10日下午6:55:50
     */
    public List<Map<String,Object>> queryColorInvestorPage(Map<String,Object> parameterMap);

    /** 
     * 查询指定日期范围和用户列表的投资总额
     * @param userIdList
     * @param startDate
     * @param endDate
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月13日上午11:38:14
     */
    public List<Map<String, Object>> queryInvestTotalAmount(@Param("userIdList")List userIdList, @Param("investStartDate")String investStartDate, @Param("investEndDate")String investEndDate);

    /** 
     * 查询推荐首月的投资额
     * @param userIdList
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月13日上午11:38:24
     */
    public List<Map<String, Object>> queryFirstMonthInvestAmount(@Param("userIdList")List userIdList);
    
    
    /** 
     * 查询在库投资额
     * @param userIdList
     * @param inStockDate 查询在库金额的日期
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月13日上午11:38:24
     */
    public List<Map<String, Object>> queryInStockInvestAmount(@Param("userIdList")List userIdList, @Param("inStockDate") String inStockDate);

    public Map<String, Object> queryFKByDate(String date) ;

    public Map<String, Object> queryInvestByDate(String date) ;

    public Map<String, Object> queryUserByDate(String date) ;

    public Map<String, Object> queryFK() ;

    public Map<String, Object> queryInvest() ;

    public Map<String, Object> queryUser() ;

}
