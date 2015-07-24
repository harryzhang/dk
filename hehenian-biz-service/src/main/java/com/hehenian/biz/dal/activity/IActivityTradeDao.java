/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.dal.activity;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.activity.dataobject.ActivityTradeDo;

public interface IActivityTradeDao {

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public ActivityTradeDo getById(int id);

    /**
     * 更新
     */
    public int updateActivityTradeById(ActivityTradeDo newActivityTradeDo);

    /**
     * 根据条件查询列表
     */
    public List<ActivityTradeDo> selectActivityTrade(Map<String, Object> parameterMap);

    /**
     * 新增
     */
    public int addActivityTrade(ActivityTradeDo newActivityTradeDo);

    /**
     * 删除
     */
    public int deleteById(int id);

    /**
     * 根据应转日期查询未转账记录
     * 
     * @param tradeTime
     * @return
     * @author: liuzgmf
     * @date: 2014年11月1日下午2:54:04
     */
    List<ActivityTradeDo> queryNoTransferTrades(Date tradeTime);

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
    List<ActivityTradeDo> queryByIds(@Param("idList") List<Long> idList);

}
