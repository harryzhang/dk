package com.hehenian.biz.common.loan;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;

public interface IManagerLoanRepaymentFeeService{

	/**
	 * 新增
	 */
	public int addLoanRepaymentFee(LoanRepaymentFeeDo newLoanRepaymentFeeDo);
}
