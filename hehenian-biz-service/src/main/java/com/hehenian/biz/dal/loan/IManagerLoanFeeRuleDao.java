
package com.hehenian.biz.dal.loan;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.loan.dataobject.LoanFeeRuleDo;

/**
 * 
 * @author liminglmf
 *
 */
public interface IManagerLoanFeeRuleDao {

	/**
	 * 根据方案ID查询费用规则信息
	 * @auther liminglmf
	 * @date 2015年4月29日
	 * @param schemeId
	 * @return
	 */
    List<LoanFeeRuleDo> queryBySchemeId(Long schemeId);

    /**
	 * 根据方案ID，规则类型查询费用规则信息
	 * @auther liminglmf
	 * @date 2015年4月29日
	 * @param schemeId type
	 * @return
	 */
    LoanFeeRuleDo getBySchemeIdAndType(@Param("schemeId") Long schemeId, @Param("type") String type);

    /**
	 * 新增费用规则信息
	 * @auther liminglmf
	 * @date 2015年4月29日
	 * @param loanFeeRuleDo
	 * @return
	 */
    int add(LoanFeeRuleDo loanFeeRuleDo);

    /**
	 *  删除结算方案的费用规则信息
	 * @auther liminglmf
	 * @date 2015年4月29日
	 * @param loanFeeRuleDo
	 * @return
	 */
    int deleteBySchemeId(Long schemeId);

	int update(LoanFeeRuleDo loanFeeRuleDo);

	int deleteByIds(@Param("ids")List<Long> ids);

	LoanFeeRuleDo getById(Long id);
}
