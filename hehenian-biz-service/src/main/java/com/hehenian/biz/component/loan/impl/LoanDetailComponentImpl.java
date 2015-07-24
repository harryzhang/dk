/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.loan.impl
 * @Title: LoanDetailComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月11日 上午9:55:12
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.DepositLoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.FundProductDo;
import com.hehenian.biz.common.loan.dataobject.FundUserAccountDo;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo.LoanStatus;
import com.hehenian.biz.component.loan.ILoanDetailComponent;
import com.hehenian.biz.dal.loan.ILoanDetailDao;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月11日 上午9:55:12
 */
@Component("loanDetailComponent")
public class LoanDetailComponentImpl implements ILoanDetailComponent {
    @Autowired
    private ILoanDetailDao loanDetailDao;

    @Override
    public Long addLoanDetail(LoanDetailDo loanDetailDo) {
        loanDetailDao.addLoanDetail(loanDetailDo);
        return loanDetailDo.getLoanId();
    }

    @Override
    public int changeLoanStatus(Long loanId, LoanStatus loanStatus) {
        LoanDetailDo loanDetailDo = new LoanDetailDo();
        loanDetailDo.setLoanId(loanId);
        loanDetailDo.setLoanStatus(loanStatus);
        return loanDetailDao.updateLoanDetail(loanDetailDo);
    }

    @Override
    public void updateLoanDetail(List<LoanDetailDo> loanDetailDoList) {
        if (loanDetailDoList == null || loanDetailDoList.size() == 0) {
            return;
        }
        for (LoanDetailDo loanDetailDo : loanDetailDoList) {
            loanDetailDao.updateLoanDetail(loanDetailDo);
        }
    }

    @Override
    public List<LoanDetailDo> queryLoanDetails(Map<String, Object> searchItems) {
        return loanDetailDao.queryLoanDetails(searchItems);
    }

    @Override
    public LoanDetailDo getByIdNo(String idNo) {
        return loanDetailDao.getByIdNo(idNo);
    }

    @Override
    public LoanDetailDo getByIdNoGroup(String idNo) {
        return loanDetailDao.getByIdNoGroup(idNo);
    }
    
    @Override
    public List<LoanDetailDo> queryByLoanStatus(LoanStatus loanStatus) {
        return loanDetailDao.queryByLoanStatus(loanStatus);
    }

    @Override
    public long countLoanDetails(Map<String, Object> searchItems) {
        return loanDetailDao.countLoanDetails(searchItems);
    }
    
    @Override
    public Long addDepositLoanDetail(DepositLoanDetailDo loanDetailDo) {
        loanDetailDao.addDepositLoanDetail(loanDetailDo);
        return loanDetailDo.getLoanId();
    }
    
    @Override
    public void addFundProduct(FundProductDo prd){
    	loanDetailDao.addFundProduct(prd);
    }
    
    @Override
    public void addFundUserAccount(FundUserAccountDo account){
    	loanDetailDao.addFundUserAccount(account);
    }

	@Override
	public int existUserAccount(Long userId) {
		return loanDetailDao.existUserAccount(userId);
	}
}
