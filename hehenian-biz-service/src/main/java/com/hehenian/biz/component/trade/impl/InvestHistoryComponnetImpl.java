/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: InvestHistoryComponnetImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月8日 下午5:12:40
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.trade.dataobject.InvestHistoryDo;
import com.hehenian.biz.component.trade.IInvestHistoryComponnet;
import com.hehenian.biz.dal.trade.IInvestHistoryDao;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月8日 下午5:12:40
 */
@Component("investHistoryComponnet")
public class InvestHistoryComponnetImpl implements IInvestHistoryComponnet {
    @Autowired
    private IInvestHistoryDao investHistoryDao;

    @Override
    public Long addInvestHistory(InvestHistoryDo investHistoryDo) {
        int count = investHistoryDao.addInvestHistory(investHistoryDo);
        if (count != 1) {
            throw new RuntimeException("新增投资[" + investHistoryDo.getId() + "]历史记录失败!");
        }
        return investHistoryDo.getId();
    }
}
