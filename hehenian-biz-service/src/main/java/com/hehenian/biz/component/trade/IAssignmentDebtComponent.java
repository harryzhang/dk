/**
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade
 * @Title: IAssignmentDebtComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月26日 下午2:03:01
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
package com.hehenian.biz.component.trade;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.AssignmentDebtDo;

/**
 *
 * @author: liuzgmf
 * @date 2014年9月26日 下午2:03:01
 */
public interface IAssignmentDebtComponent {

    /**
     * 校验标标的是否能债权转让
     *
     * @param investId
     * @param alienatorId
     * @return true 可以债权转让，否则为不可以转让
     * @author: liuzgmf
     * @date: 2014年9月26日下午2:54:20
     */
    boolean isHaveAssignmentDebt(Long investId, Long alienatorId);

    /**
     * 新增债权转让申请信息
     *
     * @param assignmentDebtDo
     * @author: liuzgmf
     * @date: 2014年9月26日下午3:56:45
     */
    Long addAssignmentDebt(AssignmentDebtDo assignmentDebtDo);

    /**
     * 债权转让申请审核
     *
     * @param assignmentDebtDo
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日下午4:18:29
     */
    boolean updateDebtAudit(AssignmentDebtDo assignmentDebtDo);

    /**
     * 根据债权转让ID查询债权转让申请信息
     *
     * @param debtId
     * @return
     * @author: liuzgmf
     * @date: 2014年9月29日下午3:06:07
     */
    AssignmentDebtDo getById(Long debtId);

    /**
     * 债权转让购买
     *
     * @param auctionDebtId
     * @param auctionPrice
     * @author: liuzgmf
     * @date: 2014年9月30日下午3:33:11
     */
    boolean updatePurchaseDebt(long auctionDebtId, double auctionPrice);

    /**
     * 修改债权转让申请记录
     *
     * @param updAsignmentDebtDo
     * @author: liuzgmf
     * @date: 2014年10月16日下午5:49:45
     */
    Long updateAssignmentDebt(AssignmentDebtDo updAsignmentDebtDo);

    /**
     * 根据投资ID和owner 还款的时候查询债券转让最后一条成功的
     *
     * @param id
     *            投资ID
     * @param owner
     *            认购者
     * @return
     */
    AssignmentDebtDo getLastAssignmentDebtByInvestId(Long investId, Long owner);

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
	 * @param borrowId
	 *            标的id
	 */
	void updateDebtStatusFailure(long borrowId);

}
