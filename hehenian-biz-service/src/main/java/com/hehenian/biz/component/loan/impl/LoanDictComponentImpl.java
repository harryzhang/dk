/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanDictDo;
import com.hehenian.biz.component.loan.ILoanDictComponent;
import com.hehenian.biz.dal.loan.IManagerLoanDictDao;

/**
 * @author liminglmf
 *
 */
@Component("loanDictComponent")
public class LoanDictComponentImpl implements ILoanDictComponent{
	
	@Autowired
	private IManagerLoanDictDao managerLoanDictDao;

	@Override
	public List<LoanDictDo> getLoanDictPage(Map<String, Object> param) {
		return managerLoanDictDao.queryListPage(param);
	}

	@Override
	public LoanDictDo getDictById(long dictId) {
		return managerLoanDictDao.getDictById(dictId);
	}

	@Override
	public int addDict(LoanDictDo loanDictDo) {
		return managerLoanDictDao.addLoanDictDo(loanDictDo) ;
	}

	@Override
	public int updateDict(LoanDictDo loanDictDo) {
		return managerLoanDictDao.updateDict(loanDictDo) ;
	}

	@Override
	public int deleteDict(long dictId) {
		return managerLoanDictDao.deleteDict(dictId);
	}

	
	
}
