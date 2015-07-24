package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;

public interface IManagerLoanRepaymentComponent {

	public int addLoanRepayment(LoanRepaymentDo loanRepaymentFeeDo);
	
	public int updateRepayStatus(Map<String,Object> params);
	
	public boolean repaymentTask(Map<String, Object> params,List<LoanRepaymentFeeDo> lrfList);
	
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
