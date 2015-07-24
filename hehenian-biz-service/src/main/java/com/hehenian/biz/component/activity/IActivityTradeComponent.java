/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.activity;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.activity.dataobject.ActivityTradeDo;

public interface IActivityTradeComponent {

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public ActivityTradeDo getById(int id);

    /**
     * 根据条件查询列表
     */
    public List<ActivityTradeDo> selectActivityTrade(Map<String, Object> parameterMap);

    /**
     * 更新
     */
    public int updateActivityTradeById(ActivityTradeDo newActivityTradeDo);

    /**
     * 新增
     */
    public int addActivityTrade(ActivityTradeDo newActivityTradeDo);

    /**
     * 删除
     */
    public int deleteById(int id);

    /**
     * 生产扣款计划记录
     * 
     * @param ordId
     * @author: liuzgmf
     * @date: 2014年10月31日下午4:08:45
     */
    boolean addActivityTrades(long ordId);

    /**
     * 根据应转日期查询未转账记录
     * 
     * @param tradeTime
     * @return
     * @author: liuzgmf
     * @date: 2014年11月1日下午12:01:46
     */
    List<ActivityTradeDo> queryNoTransferTrades(Date tradeTime);

    /**
     * @param activityTradeDo
     * @author: liuzgmf
     * @date: 2014年11月1日下午3:13:44
     */
    boolean updateActivityTradeTransfer(ActivityTradeDo activityTradeDo);

    /**
     * 取最小的未扣款记录
     * 
     * @param parameterMap
     * @return
     */
    public List<ActivityTradeDo> selectLastUnDeductActivityTrade(Map<String, Object> parameterMap);

    /**
     * 根据ID查询转账交易记录
     * 
     * @param idList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月26日上午11:50:03
     */
    List<ActivityTradeDo> queryByIds(List<Long> idList);

}
