package com.hehenian.biz.component.loan;

import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;

public interface ILoanRelationComponent {

	/**
	 * 修改个人联系信息
	 * @param loanRelationDo
	 */
    void updateLoanRelation(LoanRelationDo loanRelationDo);
    
    /**
     * 新增个人联系信息
     * @param loanRelationDo
     */
    void addLoanRelation(LoanRelationDo loanRelationDo);
    
    
    /**
     * 根据ID查询表的记录
     * @return
     */
    int getCountByIds(Long id);

    /**
     * 根据订单ID删除联系人
     * @param loanId
     */
	int deleteLoanRelationByLoanId(long loanId);
}
