package com.hehenian.biz.service.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.loan.IManagerLoanRepaymentService;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;
import com.hehenian.biz.component.loan.IManagerLoanRepaymentComponent;

@Service("managerLoanRepaymentService")
public class ManagerLoanRepaymentServiceImpl implements IManagerLoanRepaymentService{

	@Autowired
	private IManagerLoanRepaymentComponent managerLoanRepaymentComponent ;
	
	@Override
	public int addLoanRepayment(LoanRepaymentDo newLoanRepaymentDo) {
		return managerLoanRepaymentComponent.addLoanRepayment(newLoanRepaymentDo);
	}

	@Override
	public int updateRepayStatus(Map<String, Object> params) {
		return managerLoanRepaymentComponent.updateRepayStatus(params);
	}

	@Override
	public boolean repaymentTask(Map<String, Object> params,
			List<LoanRepaymentFeeDo> lrfList) {
		try {
			managerLoanRepaymentComponent.repaymentTask(params, lrfList);
			return true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false ;
	}

	@Override
	public LoanRepaymentDo selectRepaymentByOrderCodeAndPeriod(Map<String, Object> params) {
		return managerLoanRepaymentComponent.selectRepaymentByOrderCodeAndPeriod(params);
	}

	@Override
	public List<LoanRepaymentDo> selectRepaymentByLoanId(long loanId) {
		return managerLoanRepaymentComponent.selectRepaymentByLoanId(loanId);
	}

	@Override
	public int deleteRepaymentByLoanId(Long loanId) {
		return managerLoanRepaymentComponent.deleteRepaymentByLoanId(loanId);
	}

	
}
