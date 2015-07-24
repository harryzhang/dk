/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanDictDtlDo;
import com.hehenian.biz.component.loan.ILoanDictDtlComponent;
import com.hehenian.biz.dal.loan.IManagerLoanDictDtlDao;

/**
 * @author liminglmf
 *
 */
@Component("loanDictDtlComponent")
public class LoanDictDtlComponentImpl implements ILoanDictDtlComponent{
	
	@Autowired
	private IManagerLoanDictDtlDao managerLoanDictDtlDao;

	@Override
	public List<LoanDictDtlDo> getLoanDictDtlPage(Map<String, Object> param) {
		 return managerLoanDictDtlDao.queryListPage(param);
	}

}
