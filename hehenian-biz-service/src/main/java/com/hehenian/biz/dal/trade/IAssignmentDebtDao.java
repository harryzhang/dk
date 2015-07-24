/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.trade
 * @Title: IAssignmentDebtDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月26日 下午3:02:14
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.trade;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.AssignmentDebtDo;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月26日 下午3:02:14
 */
public interface IAssignmentDebtDao {

    /**
     * 校验标标的是否能债权转让
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日下午3:02:24
     */
    int isHaveAssignmentDebt(Map<String, Object> searchItems);

    /**
     * 新增债权转让申请记录
     * 
     * @param assignmentDebtDo
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日下午3:46:08
     */
    int addAssignmentDebt(AssignmentDebtDo assignmentDebtDo);

    /**
     * 修改债权转让申请记录
     * 
     * @param assignmentDebtDo
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日下午3:46:08
     */
    int updateAssignmentDebt(AssignmentDebtDo assignmentDebtDo);

    /**
     * 根据债权转让ID查询债权转让申请信息
     * 
     * @param debtId
     * @return
     * @author: liuzgmf
     * @date: 2014年9月29日下午3:07:11
     */
    AssignmentDebtDo getById(Long debtId);

    /**
     * 根据投资ID和owner 还款的时候查询债券转让最后一条成功的
     * 
     * @param id
     *            投资ID
     * @param owner
     *            认购者
     * @return
     */
    AssignmentDebtDo getAssignmentDebtByInvestId(@Param(value = "investId") Long investId,
            @Param(value = "owner") Long owner);

    /**
     * 根据投资ID和owner 成功投资取最后一次转让
     * 
     * @param id
     *            投资ID
     * @param owner
     *            认购者
     * @return
     */
    AssignmentDebtDo getSuccessInvestAssignmentDebt(@Param(value = "investId") Long investId,
            @Param(value = "owner") Long owner);

    /**
     * 根据投资ID和认购者ID查询最后一次功能认购债权记录
     * 
     * @param investId
     * @param auctionerId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月30日下午2:55:32
     */
    AssignmentDebtDo getByInvestIdAndAuctionerId(@Param(value = "investId") Long investId,
            @Param(value = "auctionerId") Long auctionerId);

    /**
     * 认购债权成功修改债权转让记录
     * 
     * @param assignmentDebtDo
     * @author: liuzgmf
     * @date: 2014年11月3日下午3:13:33
     */
    int updatePurchaseDebt(AssignmentDebtDo assignmentDebtDo);

    /**
     * 修改借款标的债权转让记录的状态为失败
     * 
     * @param borrowId
     * @return
     * @author: liuzgmf
     * @date: 2014年11月13日下午3:47:02
     */
    int updateDebtStatusFailure(Long borrowId);

}
