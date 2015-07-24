/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.loan
 * @Title: ILoanDetailComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月11日 上午9:54:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.component.loan.ILoanRelationComponent;
import com.hehenian.biz.dal.loan.ILoanRelationDao;

/**
 * @author xiexiangmf
 *
 */
@Component("loanRelationComponent")
public class LoanRelationComponentImpl implements ILoanRelationComponent{
	
    @Autowired
    private ILoanRelationDao loanRelationDao;

	@Override
	public int getCountByIds(Long id) {
		return loanRelationDao.getCountByIds(id);
	}

	@Override
	public void updateLoanRelation(LoanRelationDo loanRelationDo) {
		loanRelationDao.updateLoanRelation(loanRelationDo);
	}

	@Override
	public void addLoanRelation(LoanRelationDo loanRelationDo) {
		loanRelationDao.addLoanRelation(loanRelationDo);
	}
	
	@Override
	public int deleteLoanRelationByLoanId(long loanId) {
		return loanRelationDao.deleteByLoanId(loanId);
	}
	
}
