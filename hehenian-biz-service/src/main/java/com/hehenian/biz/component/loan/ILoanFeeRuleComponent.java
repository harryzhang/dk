/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system
 * @Title: IFeeRuleComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月12日 下午5:03:06
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan;

import java.util.List;

import com.hehenian.biz.common.loan.dataobject.LoanFeeRuleDo;

/**
 * 
 * @author liminglmf
 *
 */
public interface ILoanFeeRuleComponent {

	/**
	 * 根据结算方案ID，费用规则类型查询费用规则信息
	 * @auther liminglmf
	 * @date 2015年4月29日
	 * @param schemeId
	 * @param ruleType
	 * @return
	 */
    LoanFeeRuleDo getBySchemeIdAndType(Long schemeId, String type);

	int update(LoanFeeRuleDo loanFeeRuleDo);

	int add(LoanFeeRuleDo loanFeeRuleDo);

	int deleteByIds(List<Long> idsList);
	
	List<LoanFeeRuleDo> getFeeBySchemeId(Long schemeId);

	LoanFeeRuleDo getById(Long id);
}
