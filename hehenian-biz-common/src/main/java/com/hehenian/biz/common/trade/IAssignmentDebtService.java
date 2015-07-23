/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade
 * @Title: IAssignmentDebtService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月26日 上午11:20:18
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade;

import java.util.Map;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.dataobject.AssignmentDebtDo;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月26日 上午11:20:18
 */
public interface IAssignmentDebtService {

    /**
     * 债权转让申请
     * 
     * @param assignmentDebtDo
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日上午11:30:52
     */
    IResult<?> addAssignmentDebt(AssignmentDebtDo assignmentDebtDo);

    /**
     * 债权转让审核审核
     * 
     * @param assignmentDebtDo
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日上午11:31:09
     */
    IResult<?> updateDebtAudit(AssignmentDebtDo assignmentDebtDo);

    /**
     * 债权转让申请购买
     * 
     * @param id
     * @param debtId
     * @param auctionPrice
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日上午11:31:38
     */
    IResult<?> addPurchaseDebt(Long id, Long debtId, Double auctionPrice);

    /**
     * 债权转让申请购买汇付回调方法
     * 
     * @param auctionDebtId
     * @param auctionPrice
     * @return
     * @author: liuzgmf
     * @date: 2014年9月30日下午3:22:04
     */
    IResult<?> updatePurchaseDebt(long auctionDebtId, double auctionPrice);

    /**
     * 商户扣款对账
     * 
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日上午10:30:41
     */
    Map<String, Object> trfReconciliation(String beginDate, String endDate, String pageNum);

    /**
     * 投标对账(放款和还款对账)
     * 
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param queryTransType
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日上午11:39:40
     */
    Map<String, Object> reconciliation(String beginDate, String endDate, String pageNum, String queryTransType);

    /**
     * 充值对账
     * 
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午1:58:57
     */
    Map<String, Object> saveReconciliation(String beginDate, String endDate, String pageNum);

    /**
     * 取现对账
     * 
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午1:59:21
     */
    Map<String, Object> cashReconciliation(String beginDate, String endDate, String pageNum);

    /**
     * 账户明细查询
     * 
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午7:07:49
     */
    Map<String, Object> queryAcctDetails(Long userId);

    /**
     * 修改债权认购记录的状态
     * 
     * @param ordId
     * @param targetStatus
     * @param sourceStatus
     * @author: liuzgmf
     * @date: 2014年11月10日上午9:23:37
     */
    Boolean updateAuctionStatus(String ordId, AuctionStatus targetStatus, AuctionStatus sourceStatus);

}
