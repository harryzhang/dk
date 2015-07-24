/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade
 * @Title: IAuctionDebtComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:38:19
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade;

import java.util.List;

import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:38:19
 */
public interface IAuctionDebtComponent {

    /**
     * 新增债权转让认购信息
     * 
     * @param auctionDebtDo
     * @author: liuzgmf
     * @date: 2014年10月8日下午3:51:32
     */
    Long addAuctionDebt(AuctionDebtDo auctionDebtDo);

    /**
     * 根据ID查询债权认购信息
     * 
     * @param auctionDebtId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月24日下午4:34:33
     */
    AuctionDebtDo getById(Long id);

    /**
     * 修改债权认购信息
     * 
     * @param auctionDebtDo
     * @author: liuzgmf
     * @date: 2014年10月24日下午5:07:26
     */
    boolean updateAuctionDebt(AuctionDebtDo auctionDebtDo);

    /**
     * 修改债权认购信息
     * 
     * @param auctionDebtId
     * @param auctionStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年10月24日下午5:24:13
     */
    boolean updateAuctionStatus(Long auctionDebtId, AuctionStatus auctionStatus);

    /**
     * 查询债权转让申请的的最后一条认购记录（状态为成功）
     * 
     * @param debtId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月28日下午6:06:12
     */
    AuctionDebtDo getByDebtId(Long debtId);

    /**
     * 修改债权认购记录的状态
     * 
     * @param id
     * @param targetStatus
     * @param sourceStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年11月10日上午9:27:14
     */
    boolean updateAuctionStatus(String id, AuctionStatus targetStatus, AuctionStatus sourceStatus);

    /**
     * 根据ID查询债权认购记录
     * 
     * @param idList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月25日上午9:19:51
     */
    List<AuctionDebtDo> queryByIds(List<Long> idList);

}
