/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.activity;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.activity.dataobject.ActivityTradeDo;
import com.hehenian.biz.common.base.result.IResult;

public interface IActivityTradeService {

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
     * @return
     * @author: liuzgmf
     * @date: 2014年10月31日下午3:58:32
     */
    IResult<?> addActivityTrades(long ordId);

    /**
     * 自动转账
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年11月1日上午11:49:27
     */
    IResult<?> transfer();
}
