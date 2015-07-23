package com.hehenian.biz.common.trade;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.dataobject.RechargeDo;

import java.util.List;
import java.util.Map;

/**
 * User: liuwtmf
 * Date: 2014/9/23
 * Time: 10:48
 */
public interface INewRechargeService {
    /**
     * 插入充值记录
     *
     * @param rechargeDo
     * @return
     */
    IResult<?> addRecharge(RechargeDo rechargeDo);

    /**
     * 充值后汇付回调修改充值状态
     *
     * @param rechargeDo
     * @return
     */
    IResult<?> addRechargeCallback(RechargeDo rechargeDo);

    /**
     * 查询充值记录
     * @param parameterMap
     * @return
     */
    List<RechargeDo> selectRecharges(Map<String, Object> parameterMap);

    RechargeDo getById(long id);
}
