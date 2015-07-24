/**
 * @auther liminglong
 * @date 2015年4月22日
 */
package com.hehenian.biz.service.loan.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.ILoanProvidedService;
import com.hehenian.biz.common.loan.dataobject.LoanProvidedDo;
import com.hehenian.biz.component.loan.ILoanProvidedComponent;
import com.hehenian.lend.manager.common.contant.Constants;
/**
 * @author liminglong
 *
 */
@Service("loanProvidedService")
public class LoanProvidedServiceImpl implements ILoanProvidedService{
	
	@Autowired
	private ILoanProvidedComponent loanProvidedComponent;
	
	/**
	 * @see com.hehenian.biz.common.loan.ILoanProvidedService#getLoanProvidedPage(Map, PageDo)
	 */
	@Override
	public PageDo<LoanProvidedDo> getLoanProvidedPage(
			Map<String, Object> param, PageDo<LoanProvidedDo> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanProvidedDo> list= loanProvidedComponent.getLoanProvidedPage(param);
		page.setPage(list);
		return page;
	}

	@Override
	public int addLoanProvidedDo(LoanProvidedDo loanProvidedDo) {
		return loanProvidedComponent.addLoanProvidedDo(loanProvidedDo);
	}

	@Override
	public LoanProvidedDo getProvById(Long id) {
		return loanProvidedComponent.getProdById(id);
	}

	@Override
	public int updateLoanProvidedDo(LoanProvidedDo loanProvidedDo) {
		return loanProvidedComponent.updateLoanProvidedDo(loanProvidedDo);
	}

	@Override
	public int deleteLoanProvidedDo(String ids) {
		String[] arr = ids.split(",");
		Long[] longArr = new Long[arr.length];
		for (int i = 0; i < longArr.length; i++) {
			longArr[i] = Long.parseLong(arr[i]);
		}
		List<Long> idsList = Arrays.asList(longArr);
		return loanProvidedComponent.deleteLoanProvidedDo(idsList);
	}

	@Override
	public List<LoanProvidedDo> getLoanProvidList(Map<String, Object> param) {
		return loanProvidedComponent.getLoanProvidedPage(param);
	}

	@Override
	public int selectCountByProdId(Long prodId) {
		return loanProvidedComponent.selectCountByProdId(prodId);
	}

}
