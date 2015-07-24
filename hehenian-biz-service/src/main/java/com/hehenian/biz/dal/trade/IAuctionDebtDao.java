/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.trade
 * @Title: IAuctionDebtDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:53:35
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.trade;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:53:35
 */
public interface IAuctionDebtDao {

    /**
     * 新增债权转让认购信息
     * 
     * @param auctionDebtDo
     * @return
     * @author: liuzgmf
     * @date: 2014年10月8日下午3:53:42
     */
    int addAuctionDebt(AuctionDebtDo auctionDebtDo);

    /**
     * 根据ID查询债权认购信息
     * 
     * @param id
     * @return
     * @author: liuzgmf
     * @date: 2014年10月24日下午4:35:51
     */
    AuctionDebtDo getById(Long id);

    /**
     * 修改债权认购信息
     * 
     * @param auctionDebtDo
     * @return
     * @author: liuzgmf
     * @date: 2014年10月24日下午5:08:40
     */
    int updateAuctionDebt(AuctionDebtDo auctionDebtDo);

    /**
     * 查询债权转让申请的的最后一条认购记录（状态为成功）
     * 
     * @param debtId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月28日下午6:08:10
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
     * @date: 2014年11月10日上午10:06:18
     */
    int updateAuctionStatus(@Param("id") String id, @Param("targetStatus") AuctionStatus targetStatus,
            @Param("sourceStatus") AuctionStatus sourceStatus);

    /**
     * 根据ID查询债权认购记录
     * 
     * @param idList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月25日上午9:21:07
     */
    List<AuctionDebtDo> queryByIds(@Param("idList") List<Long> idList);

}
