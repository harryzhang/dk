/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.biz.service.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.ILoanDictDtlService;
import com.hehenian.biz.common.loan.dataobject.LoanDictDtlDo;
import com.hehenian.biz.component.loan.ILoanDictDtlComponent;
import com.hehenian.lend.manager.common.contant.Constants;

/**
 * @author liminglmf
 *
 */
@Service("loanDictDtlService")
public class LoanDictDtlServiceImlp implements ILoanDictDtlService{
	
	@Autowired
	private ILoanDictDtlComponent loanDictDtlComponent;
	
	/**
	 * @see com.hehenian.biz.common.loan.ILoanDictDtlService#getLoanDictDtlPage(Map, PageDo)
	 */
	@Override
	public PageDo<LoanDictDtlDo> getLoanDictDtlPage(Map<String, Object> param,
			PageDo<LoanDictDtlDo> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanDictDtlDo> list= loanDictDtlComponent.getLoanDictDtlPage(param);
		page.setPage(list);
		return page;
	}

	
}
