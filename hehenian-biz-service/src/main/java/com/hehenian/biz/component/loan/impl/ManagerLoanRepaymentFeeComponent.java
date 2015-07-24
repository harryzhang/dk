package com.hehenian.biz.component.loan.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;
import com.hehenian.biz.component.loan.IManagerLoanRepaymentFeeComponent;
import com.hehenian.biz.dal.loan.IManagerLoanRepaymentFeeDao;

@Component("managerLoanRepaymentFeeComponent")
public class ManagerLoanRepaymentFeeComponent implements IManagerLoanRepaymentFeeComponent{

	@Autowired
	private IManagerLoanRepaymentFeeDao managerLoanRepaymentFeeDao;
	
	@Override
	public int addLoanRepaymentFee(LoanRepaymentFeeDo newLoanRepaymentFeeDo) {
		managerLoanRepaymentFeeDao.addLoanRepaymentFee(newLoanRepaymentFeeDo);
		return 0;
	}

}
