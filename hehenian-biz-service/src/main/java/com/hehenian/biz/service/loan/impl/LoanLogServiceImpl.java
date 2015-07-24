package com.hehenian.biz.service.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.loan.ILoanLogService;
import com.hehenian.biz.common.loan.dataobject.LoanLogDo;
import com.hehenian.biz.component.loan.ILoanLogComponent;

@Service("loanLogService")
public class LoanLogServiceImpl implements ILoanLogService{
	
	@Autowired
	private ILoanLogComponent loanLogComponent;

	@Override
	public int addLoanLog(LoanLogDo loanLogDo) {
		return loanLogComponent.addLoanLog(loanLogDo);
	}

	@Override
	public List<LoanLogDo> getLoanLogPage(Map<String, Object> param) {
		return loanLogComponent.getLoanLogPage(param);
	}

}
