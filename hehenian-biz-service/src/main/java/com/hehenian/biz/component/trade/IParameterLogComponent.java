package com.hehenian.biz.component.trade;

import com.hehenian.biz.common.trade.dataobject.ParameterLogDo;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzgmf on 2014/12/31.
 */
public interface IParameterLogComponent {
    /**
     * 新增交易参数日志信息
     *
     * @param parameterLogDo
     * @return
     */
    int addParameterLog(ParameterLogDo parameterLogDo);

    /**
     * 根据条件查询交易参数日志信息
     *
     * @param searchItems
     * @return
     */
    List<ParameterLogDo> queryParameterLogs(Map<String, Object> searchItems);
}
