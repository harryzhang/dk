/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade
 * @Title: IReconciliationService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:07:05
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade;

import java.util.Map;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo;

/**
 * 
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:07:05
 */
public interface IReconciliationService {
    /**
     * 取现对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午9:09:52
     */
    void cashReconciliation();

    /**
     * 放款对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午9:25:52
     */
    void loansReconciliation();

    /**
     * 还款对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午9:25:52
     */
    void repaymentReconciliation();

    /**
     * 债权转让对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午9:26:52
     */
    void creditAssignReconciliation();

    /**
     * 充值对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午9:25:52
     */
    void rechargeReconciliation();

    /**
     * 商户扣款对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午9:25:52
     */
    void trfReconciliation();

    /** 
     * 查询对账信息
     * @param parameterMap
     * @param pageBean
     * @return  
     * @author: xiexiangmf
     * @date: 2014年12月23日下午2:41:01
     */
    NPageDo<ReconciliationDo> getReconciliations(Map parameterMapn);

}
