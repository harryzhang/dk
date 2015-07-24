/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.component.loan.ILoanProductComponent;
import com.hehenian.biz.dal.loan.IManagerLoanProductDao;

/**
 * @author liminglmf
 *
 */
@Component("loanProductComponent")
public class LoanProductComponentImpl implements ILoanProductComponent{
	
	@Autowired
	private IManagerLoanProductDao managerLoanProductDao;

	@Override
	public List<LoanProductDo> getLoanProductPage(
			Map<String, Object> param) {
		return managerLoanProductDao.queryLoanProductPage(param);
	}

	@Override
	public int addLoanProductDo(LoanProductDo loanProductDo) {
		return managerLoanProductDao.addLoanProductDo(loanProductDo);
	}

	@Override
	public LoanProductDo getProdById(Long proId) {
		return managerLoanProductDao.getProdById(proId);
	}

	@Override
	public int updateLoanProductDo(LoanProductDo loanProductDo) {
		return managerLoanProductDao.updateProd(loanProductDo);
	}

	@Override
	public int deleteLoanProductDo(List<Long> idsList) {
		return managerLoanProductDao.deleteByIds(idsList);
	}
	
	
}
