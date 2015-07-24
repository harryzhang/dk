/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade.impl
 * @Title: AuctionDebtServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月24日 下午5:22:37
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.trade.IAuctionDebtService;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;
import com.hehenian.biz.component.trade.IAuctionDebtComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月24日 下午5:22:37
 */
@Component("auctionDebtService")
public class AuctionDebtServiceImpl implements IAuctionDebtService {
    @Autowired
    private IAuctionDebtComponent auctionDebtComponent;

    @Override
    public boolean changeAuctionStatus(Long auctionDebtId, AuctionStatus auctionStatus) {
        return auctionDebtComponent.updateAuctionStatus(auctionDebtId, auctionStatus);
    }

}
