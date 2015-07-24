/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade
 * @Title: IInvestHistoryComponnet.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:45:05
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade;

import com.hehenian.biz.common.trade.dataobject.InvestHistoryDo;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:45:05
 */
public interface IInvestHistoryComponnet {

    /**
     * 新增投资历史记录
     * 
     * @param investHistoryDo
     * @author: liuzgmf
     * @date: 2014年10月8日下午5:11:57
     */
    Long addInvestHistory(InvestHistoryDo investHistoryDo);

}
