package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanCMobileDo;
import com.hehenian.biz.component.loan.ILoanCMobileComponent;
import com.hehenian.biz.dal.loan.ILoanCMobileDao;

/**
 * @author zhengyfmf
 */
@Component("loanCMobileComponent")
public class LoanCMobileComponentImpl implements ILoanCMobileComponent{

	@Autowired
	private ILoanCMobileDao loanCMobileDao;
	
	@Override
	public int addLoanCMobile(LoanCMobileDo loanCMobileDo) {
		return loanCMobileDao.addLoanCMobile(loanCMobileDo);
	}

	@Override
	public int updateLoanCMoblie(LoanCMobileDo loanCMobileDo) {
		return loanCMobileDao.updateLoanCMoblie(loanCMobileDo);
	}

	@Override
	public LoanCMobileDo getById(Integer id) {
		return loanCMobileDao.getById(id);
	}

	@Override
	public List<LoanCMobileDo> getLoanCMobilePage(Map<String, Object> param) {
		return loanCMobileDao.getLoanCMobilePage(param);
	}

	@Override
	public Map<String, Object> getLoanCMobileMapPage(Map<String, Object> param) {
		return loanCMobileDao.getLoanCMobileMapPage(param);
	}

	@Override
	public int deleteLoanCMobileById(Integer id) {
		return loanCMobileDao.deleteLoanCMobileById(id);
	}

	@Override
	public List<LoanCMobileDo> getAllBusinessDept() {
		return loanCMobileDao.getAllBusinessDept();
	}

}
