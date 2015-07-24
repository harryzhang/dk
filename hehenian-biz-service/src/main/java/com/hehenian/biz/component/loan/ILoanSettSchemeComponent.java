/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system
 * @Title: ISettSchemeComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:36:14
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanFeeRuleDo;
import com.hehenian.biz.common.loan.dataobject.LoanSettSchemeDo;


/**
 * 
 * @author liminglmf
 *
 */
public interface ILoanSettSchemeComponent {
    
	/**
	 * 根据方案ID查询结算方案信息
	 * @auther liminglmf
	 * @date 2015年4月29日
	 * @param schemeId
	 * @return
	 */
    LoanSettSchemeDo getById(Long id);

    /**
     * 根据方案代码查询结算方案信息
     * @auther liminglmf
     * @date 2015年4月29日
     * @param schemeCode
     * @return
     */
    LoanSettSchemeDo getByCode(String schemeCode);

    /**
     * 根据方案ID，规则类型查询费用规则信息
     * @auther liminglmf
     * @date 2015年4月29日
     * @param schemeId
     * @param type
     * @return
     */
    LoanFeeRuleDo getBySchemeIdAndType(Long schemeId, String type);

    /**
     * 根据条件查询借款方案记录数
     * @auther liminglmf
     * @date 2015年4月29日
     * @param searchItems
     * @return
     */
    int countList(Map<String, Object> searchItems);

    /**
     * 根据条件查询借款方案信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:06:04
     */
    List<LoanSettSchemeDo> queryList(Map<String, Object> searchItems);

    /**
     * 新增结算方案信息
     * 
     * @param LoanSettSchemeDo
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:07:04
     */
    int add(LoanSettSchemeDo loanSettSchemeDo);

    /**
     * 修改结算方案信息
     * 
     * @param LoanSettSchemeDo
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:07:04
     */
    int update(LoanSettSchemeDo loanSettSchemeDo);

    /**
     * 根据方案ID删除结算方案信息
     * 
     * @param schemeId
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:08:19
     */
    int deleteById(Long id);
    
    /**
     * 
     * @auther liminglmf
     * @date 2015年4月28日
     * @param prodId
     * @return
     */
    List<LoanSettSchemeDo> queryByProdId(Long prodId);
    
    /**
     * 
     * @auther liminglmf
     * @date 2015年4月30日
     * @param idsList
     * @return
     */
	int deleteByIds(List<Long> idsList);
	
	/**
	 * 查询规则分页信息
	 * @auther liminglmf
	 * @date 2015年5月13日
	 * @param param
	 * @return
	 */
	List<LoanSettSchemeDo> queryLoanSettPage(Map<String, Object> param);
}
