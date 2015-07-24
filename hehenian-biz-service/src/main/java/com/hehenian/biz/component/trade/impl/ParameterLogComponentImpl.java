package com.hehenian.biz.component.trade.impl;

import com.hehenian.biz.common.trade.dataobject.ParameterLogDo;
import com.hehenian.biz.component.trade.IParameterLogComponent;
import com.hehenian.biz.dal.trade.IParameterLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzgmf on 2014/12/31.
 */
@Component("parameterLogComponent")
public class ParameterLogComponentImpl implements IParameterLogComponent {
    @Autowired
    private IParameterLogDao parameterLogDao;

    @Override public int addParameterLog(ParameterLogDo parameterLogDo) {
        return parameterLogDao.addParameterLog(parameterLogDo);
    }

    @Override public List<ParameterLogDo> queryParameterLogs(Map<String, Object> searchItems) {
        return parameterLogDao.queryParameterLogs(searchItems);
    }
}
