package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanLogDo;

public interface ILoanLogComponent {

	int addLoanLog(LoanLogDo loanLogDo);
	
	List<LoanLogDo> getLoanLogPage(Map<String,Object> param);
	
	int updateLoanLog(LoanLogDo loanLogDo);
	
	int countLoanLog(Map<String,Object> param);
}
