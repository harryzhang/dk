package com.hehenian.biz.component.loan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanFeeRuleDo;
import com.hehenian.biz.component.loan.ILoanFeeRuleComponent;
import com.hehenian.biz.dal.loan.IManagerLoanFeeRuleDao;

/**
 * 
 * @author liminglmf
 *
 */
@Component("loanFeeRuleComponent")
public class LoanFeeRuleComponentImpl implements ILoanFeeRuleComponent{
	
	@Autowired
	private IManagerLoanFeeRuleDao managerLoanFeeRuleDao;
	
	@Override
	public LoanFeeRuleDo getBySchemeIdAndType(Long schemeId, String type) {
		return managerLoanFeeRuleDao.getBySchemeIdAndType(schemeId, type);
	}

	@Override
	public int update(LoanFeeRuleDo loanFeeRuleDo) {
		return managerLoanFeeRuleDao.update(loanFeeRuleDo);
	}

	@Override
	public int add(LoanFeeRuleDo loanFeeRuleDo) {
		return managerLoanFeeRuleDao.add(loanFeeRuleDo);
	}

	@Override
	public int deleteByIds(List<Long> idsList) {
		return managerLoanFeeRuleDao.deleteByIds(idsList);
	}

	@Override
	public List<LoanFeeRuleDo> getFeeBySchemeId(Long schemeId) {
		List<LoanFeeRuleDo> list = managerLoanFeeRuleDao.queryBySchemeId(schemeId);
        return list;
	}

	@Override
	public LoanFeeRuleDo getById(Long id) {
		return managerLoanFeeRuleDao.getById(id);
	}

}
