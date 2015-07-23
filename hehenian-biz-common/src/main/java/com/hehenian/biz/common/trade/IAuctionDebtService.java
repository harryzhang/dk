/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade
 * @Title: IAuctionDebtService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月24日 下午5:17:19
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade;

import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月24日 下午5:17:19
 */
public interface IAuctionDebtService {

    /**
     * 修改债权认购信息
     * 
     * @param auctionDebtId
     * @param auctionStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年10月24日下午5:19:33
     */
    boolean changeAuctionStatus(Long auctionDebtId, AuctionStatus auctionStatus);
}
