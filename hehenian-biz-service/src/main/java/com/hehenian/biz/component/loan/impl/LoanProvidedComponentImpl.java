/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.biz.component.loan.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanProvidedDo;
import com.hehenian.biz.component.loan.ILoanProvidedComponent;
import com.hehenian.biz.dal.loan.IManagerLoanProvidedDao;

/**
 * @author liminglmf
 *
 */
@Component("loanProvidedComponent")
public class LoanProvidedComponentImpl implements ILoanProvidedComponent{
	
	@Autowired
	private IManagerLoanProvidedDao managerLoanProvidedDao;

	@Override
	public List<LoanProvidedDo> getLoanProvidedPage(
			Map<String, Object> param) {
		return managerLoanProvidedDao.queryLoanProvidedList(param);
	}

	@Override
	public int addLoanProvidedDo(LoanProvidedDo loanProvidedDo) {
		return managerLoanProvidedDao.addLoanProvidedDo(loanProvidedDo);
	}

	@Override
	public LoanProvidedDo getProdById(Long proId) {
		return managerLoanProvidedDao.getProdById(proId);
	}

	@Override
	public int updateLoanProvidedDo(LoanProvidedDo loanProvidedDo) {
		return managerLoanProvidedDao.updateProv(loanProvidedDo);
	}

	@Override
	public int deleteLoanProvidedDo(List<Long> idsList) {
		return managerLoanProvidedDao.deleteProv(idsList);
	}

	@Override
	public int selectCountByProdId(Long prodId) {
		Map<String,Object> searchItem = new HashMap<String,Object>();
		searchItem.put("prodId", prodId);
		return managerLoanProvidedDao.queryLoanProvidedCount(searchItem);
	}
	
	
}
