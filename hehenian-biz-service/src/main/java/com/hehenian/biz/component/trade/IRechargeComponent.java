package com.hehenian.biz.component.trade;

import com.hehenian.biz.common.trade.dataobject.RechargeDo;

import java.util.List;
import java.util.Map;

/**
 * User: liuwtmf Date: 2014/9/23 Time: 14:21
 */
public interface IRechargeComponent {

    RechargeDo getById(long id);

    /**
     * 插入充值记录
     *
     * @param rechargeDo
     * @return
     */
    int addRecharge(RechargeDo rechargeDo);

    /**
     * 修改充值记录
     *
     * @param rechargeDo
     * @return
     */
    int updateRecharge(RechargeDo rechargeDo);

    /**
     * 查询充值记录
     * 
     * @param parameterMap
     * @return
     */
    List<RechargeDo> selectRecharges(Map<String, Object> parameterMap);

    void updatetestU();

    /**
     * 根据ID查询充值记录
     * 
     * @param idList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月26日上午11:46:58
     */
    List<RechargeDo> queryByIds(List<Long> idList);
}
