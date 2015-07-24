/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: AuctionDebtComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:52:15
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;
import com.hehenian.biz.component.trade.IAuctionDebtComponent;
import com.hehenian.biz.dal.trade.IAuctionDebtDao;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:52:15
 */
@Component("auctionDebtComponent")
public class AuctionDebtComponentImpl implements IAuctionDebtComponent {
    @Autowired
    private IAuctionDebtDao auctionDebtDao;

    @Override
    public Long addAuctionDebt(AuctionDebtDo auctionDebtDo) {
        auctionDebtDao.addAuctionDebt(auctionDebtDo);
        return auctionDebtDo.getId();
    }

    @Override
    public boolean updateAuctionDebt(AuctionDebtDo auctionDebtDo) {
        int count = auctionDebtDao.updateAuctionDebt(auctionDebtDo);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAuctionStatus(Long auctionDebtId, AuctionStatus auctionStatus) {
        AuctionDebtDo auctionDebtDo = new AuctionDebtDo();
        auctionDebtDo.setId(auctionDebtId);
        auctionDebtDo.setAuctionStatus(auctionStatus);
        return updateAuctionDebt(auctionDebtDo);
    }

    @Override
    public AuctionDebtDo getById(Long id) {
        return auctionDebtDao.getById(id);
    }

    @Override
    public AuctionDebtDo getByDebtId(Long debtId) {
        return auctionDebtDao.getByDebtId(debtId);
    }

    @Override
    public boolean updateAuctionStatus(String id, AuctionStatus targetStatus, AuctionStatus sourceStatus) {
        int count = auctionDebtDao.updateAuctionStatus(id, targetStatus, sourceStatus);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<AuctionDebtDo> queryByIds(List<Long> idList) {
        return auctionDebtDao.queryByIds(idList);
    }

}
