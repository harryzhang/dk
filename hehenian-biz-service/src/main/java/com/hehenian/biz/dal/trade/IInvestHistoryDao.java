/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.trade
 * @Title: IInvestHistoryDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月8日 下午5:14:06
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.trade;

import com.hehenian.biz.common.trade.dataobject.InvestHistoryDo;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月8日 下午5:14:06
 */
public interface IInvestHistoryDao {

    /**
     * 新增投资历史记录
     * 
     * @param investHistoryDo
     * @return
     * @author: liuzgmf
     * @date: 2014年10月8日下午5:14:15
     */
    int addInvestHistory(InvestHistoryDo investHistoryDo);

}
