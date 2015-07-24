/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: PreRepaymentComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月24日 下午2:03:52
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.dataobject.PreRepaymentDo;
import com.hehenian.biz.component.trade.IPreRepaymentComponent;
import com.hehenian.biz.dal.trade.IPreRepaymentDao;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月24日 下午2:03:52
 */
@Component("preRepaymentComponent")
public class PreRepaymentComponentImpl implements IPreRepaymentComponent {
    @Autowired
    private IPreRepaymentDao preRepaymentDao;

    @Override
    public Long addPreRepayment(PreRepaymentDo preRepaymentDo) {
        int count = preRepaymentDao.addPreRepayment(preRepaymentDo);
        if (count != 1) {
            throw new BusinessException("新增预还款记录失败!");
        }
        return preRepaymentDo.getId();
    }

}
