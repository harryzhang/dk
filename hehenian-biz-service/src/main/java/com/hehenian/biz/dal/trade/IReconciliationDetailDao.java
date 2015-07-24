/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.trade
 * @Title: IReconciliationDetailDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月9日 上午11:42:36
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.trade;

import com.hehenian.biz.common.trade.dataobject.ReconciliationDetailDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月9日 上午11:42:36
 */
public interface IReconciliationDetailDao {
    /**
     * 新增对账明细信息
     * 
     * @param reconciliationDetailDo
     * @author: liuzgmf
     * @date: 2015年1月8日下午3:00:26
     */
    int addReconciliationDetails(ReconciliationDetailDo reconciliationDetailDo);
}
