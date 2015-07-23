package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;


public interface IManagerLoanRepaymentService{

	/**
	 * 新增
	 */
	public int addLoanRepayment(LoanRepaymentDo newLoanRepaymentDo);
	
	public int updateRepayStatus(Map<String, Object> params);
	
	public boolean repaymentTask(Map<String, Object> params,List<LoanRepaymentFeeDo> lrfList);

	LoanRepaymentDo selectRepaymentByOrderCodeAndPeriod(Map<String, Object> params);

	/**
	 * 根据订单ID取还款计划
	 * @param loanId
	 * @return
	 */
	public List<LoanRepaymentDo> selectRepaymentByLoanId(long loanId);

	/**
	 * 根据订单ID删除原还款计划
	 * @param loanId
	 * @return
	 */
	public int deleteRepaymentByLoanId(Long loanId);
}
