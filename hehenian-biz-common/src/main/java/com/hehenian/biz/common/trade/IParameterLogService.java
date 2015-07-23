package com.hehenian.biz.common.trade;

import com.hehenian.biz.common.trade.dataobject.ParameterLogDo;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzgmf on 2014/12/31.
 */
public interface IParameterLogService {
    /**
     * 新增交易参数日志信息
     *
     * @param parameterLogDo
     * @return
     */
    int addParameterLog(ParameterLogDo parameterLogDo);

    /**
     * 新增交易参数日志信息
     *
     * @param params
     * @param parameterType
     * @return
     */
    int addParameterLog(Map<String, ?> params, ParameterLogDo.ParameterType parameterType);

    /**
     * 根据条件查询交易参数日志信息
     *
     * @param searchItems
     * @return
     */
    List<ParameterLogDo> queryParameterLogs(Map<String, Object> searchItems);
}
