package com.hehenian.biz.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.trade.IParameterLogService;
import com.hehenian.biz.common.trade.dataobject.ParameterLogDo;
import com.hehenian.biz.component.trade.IParameterLogComponent;

/**
 * Created by liuzgmf on 2014/12/31.
 */
@Service("parameterLogService")
public class ParameterLogServiceImpl implements IParameterLogService {
    private final Logger           logger = Logger.getLogger(this.getClass());
    @Autowired
    private IParameterLogComponent parameterLogComponent;

    @Override
    public int addParameterLog(ParameterLogDo parameterLogDo) {
        return parameterLogComponent.addParameterLog(parameterLogDo);
    }

    @Override
    public int addParameterLog(Map<String, ?> params, ParameterLogDo.ParameterType parameterType) {
        try {
            if (params == null || params.isEmpty()) {
                return 0;
            }
            ParameterLogDo parameterLogDo = new ParameterLogDo();
            String cmdId = (String) params.get("CmdId");
            if (StringUtils.isNotBlank(cmdId)) {
                ParameterLogDo.TradeType tradeType = ParameterLogDo.TradeType.valueOf(cmdId.toUpperCase());
                parameterLogDo.setTradeType(tradeType);
            }
            if (StringUtils.isNotBlank((String) params.get("OrdId"))) {
                parameterLogDo.setOrdId(Long.parseLong((String) params.get("OrdId")));
            }
            if (StringUtils.isNotBlank((String) params.get("OrdDate"))) {
                parameterLogDo.setOrdDate(DateUtils.parseDate((String) params.get("OrdDate"), new String[] {
                        "yyyyMMdd", "yyyy-MM-dd" }));
            }
            if (StringUtils.isNotBlank((String) params.get("TrxId"))) {
                parameterLogDo.setTrxId(Long.parseLong((String) params.get("TrxId")));
            }
            parameterLogDo.setOther(params.toString());
            parameterLogDo.setParameterType(parameterType);
            return parameterLogComponent.addParameterLog(parameterLogDo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public List<ParameterLogDo> queryParameterLogs(Map<String, Object> searchItems) {
        return parameterLogComponent.queryParameterLogs(searchItems);
    }
}
