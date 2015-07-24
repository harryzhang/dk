package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;
import com.hehenian.biz.component.loan.IManagerLoanRepaymentComponent;
import com.hehenian.biz.dal.loan.IManagerLoanRepaymentDao;
import com.hehenian.biz.dal.loan.IManagerLoanRepaymentFeeDao;

@Component("managerLoanRepaymentComponent")
public class ManagerLoanRepaymentComponent implements IManagerLoanRepaymentComponent{
	@Autowired
	private IManagerLoanRepaymentDao managerLoanRepaymentDao;
	@Autowired
	private IManagerLoanRepaymentFeeDao managerLoanRepaymentFeeDao ;
	
	@Override
	public int addLoanRepayment(LoanRepaymentDo loanRepaymentDo) {
		return managerLoanRepaymentDao.addLoanRepayment(loanRepaymentDo);
	}

	@Override
	public int updateRepayStatus(Map<String, Object> params) {
		return managerLoanRepaymentDao.updateRepayStatus(params);
	}

	@Override
	public boolean repaymentTask(Map<String, Object> params,
			List<LoanRepaymentFeeDo> lrfList) {
		managerLoanRepaymentDao.updateRepayment(params);
		if(lrfList != null && lrfList.size()>0){
			for(LoanRepaymentFeeDo lrfd:lrfList){
				managerLoanRepaymentFeeDao.addLoanRepaymentFee(lrfd);
			}
		}
		
		return true ;
	}

	@Override
	public LoanRepaymentDo selectRepaymentByOrderCodeAndPeriod(
			Map<String, Object> params) {
		return managerLoanRepaymentDao.selectRepaymentByOrderCodeAndPeriod(params);
	}

	/**
	 * 根据订单ID取还款计划
	 * @param loanId
	 * @return
	 */
	@Override
	public List<LoanRepaymentDo> selectRepaymentByLoanId(long loanId) {
		return managerLoanRepaymentDao.selectRepaymentByLoanId(loanId);
	}

	@Override
	public int deleteRepaymentByLoanId(Long loanId) {
		return managerLoanRepaymentDao.deleteRepaymentByLoanId(loanId);
	} 

}
