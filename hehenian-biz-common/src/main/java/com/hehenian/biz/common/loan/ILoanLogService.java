package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanLogDo;

public interface ILoanLogService {

	int addLoanLog(LoanLogDo loanLogDo);
	
	List<LoanLogDo> getLoanLogPage(Map<String,Object> param);
}
