package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;

public interface IManagerLoanRepaymentDao {

	public int addLoanRepayment(LoanRepaymentDo loanRepaymentDo);
	
	public int updateRepayStatus(Map<String,Object> params);
	
	public int updateRepayment(Map<String,Object> params);
	
	public LoanRepaymentDo selectRepaymentByOrderCodeAndPeriod(Map<String,Object> params);

	/**
	 * 根据订单ID取还款计划
	 * @param loanId
	 * @return
	 */
	public List<LoanRepaymentDo> selectRepaymentByLoanId(long loanId);

	/**
	 * 删除还款计划
	 * @param loanId
	 * @return
	 */
	public int deleteRepaymentByLoanId(Long loanId);
}
