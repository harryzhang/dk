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
import com.hehenian.biz.common.loan.ILoanProductService;
import com.hehenian.biz.common.loan.dataobject.LoanFeeRuleDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.loan.dataobject.LoanSettSchemeDo;
import com.hehenian.biz.component.loan.ILoanFeeRuleComponent;
import com.hehenian.biz.component.loan.ILoanProductComponent;
import com.hehenian.biz.component.loan.ILoanSettSchemeComponent;
import com.hehenian.lend.manager.common.contant.Constants;
/**
 * @author liminglong
 *
 */
@Service("loanProductService")
public class LoanProductServiceImpl implements ILoanProductService{
	
	@Autowired
	private ILoanProductComponent loanProductComponent;
	
	@Autowired
	private ILoanSettSchemeComponent loanSettSchemeComponent;
	
	@Autowired
	private ILoanFeeRuleComponent loanFeeRuleComponent;
	
	/**
	 * @see com.hehenian.biz.common.loan.ILoanProductService#getLoanProductPage(Map, PageDo)
	 */
	@Override
	public PageDo<LoanProductDo> getLoanProductPage(
			Map<String, Object> param, PageDo<LoanProductDo> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanProductDo> list= loanProductComponent.getLoanProductPage(param);
		page.setPage(list);
		return page;
	}

	@Override
	public int addLoanProductDo(LoanProductDo loanProductDo) {
		return loanProductComponent.addLoanProductDo(loanProductDo);
	}

	@Override
	public LoanProductDo getProdById(Long proId) {
		return loanProductComponent.getProdById(proId);
	}

	@Override
	public int updateLoanProductDo(LoanProductDo loanProductDo) {
		return loanProductComponent.updateLoanProductDo(loanProductDo);
	}

	@Override
	public int deleteLoanProductDo(String ids) {
		Long[] longArr = stringToLongArr(ids);
		List<Long> idsList = Arrays.asList(longArr);
		return loanProductComponent.deleteLoanProductDo(idsList);
	}
	
	/********************************产品方案****************************************************/	
	@Override
	public List<LoanSettSchemeDo> queryByProdId(Long prodId) {
		return loanSettSchemeComponent.queryByProdId(prodId);
	}

	@Override
	public int updateLoanSettSchemeDo(LoanSettSchemeDo loanSettSchemeDo) {
		return loanSettSchemeComponent.update(loanSettSchemeDo);
	}

	@Override
	public int addLoanSettSchemeDo(LoanSettSchemeDo loanSettSchemeDo) {
		return loanSettSchemeComponent.add(loanSettSchemeDo);
	}

	@Override
	public int deleteLoanSettDo(String ids) {
		Long[] longArr = stringToLongArr(ids);
		List<Long> idsList = Arrays.asList(longArr);
		return loanSettSchemeComponent.deleteByIds(idsList);
	}
	
	@Override
	public PageDo<LoanSettSchemeDo> getLoanSettPage(Map<String, Object> param,
			PageDo<LoanSettSchemeDo> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanSettSchemeDo> list= loanSettSchemeComponent.queryLoanSettPage(param);
		page.setPage(list);
		return page;
	}
	
	@Override
	public LoanSettSchemeDo getLoanSettById(Long id) {
		return loanSettSchemeComponent.getById(id);
	}
	/********************************产品方案费用规则****************************************************/	
	@Override
	public int updateLoanFeeDo(LoanFeeRuleDo loanFeeRuleDo) {
		return loanFeeRuleComponent.update(loanFeeRuleDo);
	}

	@Override
	public int addLoanFeeDo(LoanFeeRuleDo loanFeeRuleDo) {
		return loanFeeRuleComponent.add(loanFeeRuleDo);
	}

	@Override
	public int deleteLoanFeeDo(String ids) {
		Long[] longArr = stringToLongArr(ids);
		List<Long> idsList = Arrays.asList(longArr);
		return loanFeeRuleComponent.deleteByIds(idsList);
	}
	
	private Long[] stringToLongArr(String ids){
		String[] arr = ids.split(",");
		Long[] longArr = new Long[arr.length];
		for (int i = 0; i < longArr.length; i++) {
			longArr[i] = Long.parseLong(arr[i]);
		}
		return longArr;
	}

	@Override
	public PageDo<LoanFeeRuleDo> getLoanFeePage(Map<String, Object> param,
			PageDo<LoanFeeRuleDo> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanFeeRuleDo> list= loanFeeRuleComponent.getFeeBySchemeId((Long)param.get("settId"));
		page.setPage(list);
		return page;
	}

	@Override
	public LoanFeeRuleDo getLoanFeeById(Long id) {
		return loanFeeRuleComponent.getById(id);
	}

}
