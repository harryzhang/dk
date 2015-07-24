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
import com.hehenian.biz.common.loan.ILoanDictService;
import com.hehenian.biz.common.loan.dataobject.LoanDictDo;
import com.hehenian.biz.component.loan.ILoanDictComponent;
import com.hehenian.lend.manager.common.contant.Constants;

/**
 * @author liminglmf
 *
 */
@Service("loanDictService")
public class LoanDictServiceImlp implements ILoanDictService{
	
	@Autowired
	private ILoanDictComponent loanDictComponent;
	
	
	/**
	 * @see com.hehenian.biz.common.loan.ILoanDictService#getLoanDictPage(Map, PageDo)
	 */
	@Override
	public PageDo<LoanDictDo> getLoanDictPage(Map<String, Object> param,
			PageDo<LoanDictDo> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanDictDo> list= loanDictComponent.getLoanDictPage(param);
		page.setPage(list);
		return page;
	}


	@Override
	public LoanDictDo getDictById(long dictId) {
		return loanDictComponent.getDictById(dictId);
	}


	@Override
	public int addDict(LoanDictDo loanDictDo) {
		return loanDictComponent.addDict(loanDictDo);
	}


	@Override
	public int updateDict(LoanDictDo loanDictDo) {
		return loanDictComponent.updateDict(loanDictDo);
	}


	@Override
	public int deleteDict(long dictId) {
		return loanDictComponent.deleteDict(dictId);		
	}

}
