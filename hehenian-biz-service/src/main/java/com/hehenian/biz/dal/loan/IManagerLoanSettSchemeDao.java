
package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.loan.dataobject.LoanSettSchemeDo;

/**
 * 
 * @author liminglmf
 *
 */
public interface IManagerLoanSettSchemeDao {
	/**
	 * 根据方案ID查询结算方案信息
	 * @auther liminglmf
	 * @date 2015年4月29日
	 * @param id
	 * @return
	 */
	LoanSettSchemeDo getById(Long id);

	/**
	 * 根据方案代码查询结算方案信息
	 * @auther liminglmf
	 * @date 2015年4月29日
	 * @param code
	 * @return
	 */
	LoanSettSchemeDo getByCode(String code);

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
     * @auther liminglmf
     * @date 2015年4月29日
     * @param searchItems
     * @return
     */
    List<LoanSettSchemeDo> queryList(Map<String, Object> searchItems);

    /**
     * 新增结算方案信息
     * @auther liminglmf
     * @date 2015年4月29日
     * @param loanSettSchemeDo
     * @return
     */
    int add(LoanSettSchemeDo loanSettSchemeDo);

    /**
     * 修改结算方案信息
     * @auther liminglmf
     * @date 2015年4月29日
     * @param loanSettSchemeDo
     * @return
     */
    int update(LoanSettSchemeDo loanSettSchemeDo);

    /**
     * 根据方案ID删除结算方案信息
     * @auther liminglmf
     * @date 2015年4月29日
     * @param schemeId
     * @return
     */
    int deleteById(Long schemeId);
    
    
    /**
     * 根据产品Id查询方案
     * @auther liminglmf
     * @date 2015年4月29日
     * @param prodId
     * @return
     */
    List<LoanSettSchemeDo> queryByProdId(Long prodId);
    
    /**
     * 批量删除
     * @auther liminglmf
     * @date 2015年4月30日
     * @param idsList
     * @return
     */
	int deleteByIds(@Param("ids")List<Long> idsList);
	
	/**
	 * 查询分页
	 * @auther liminglmf
	 * @date 2015年5月13日
	 * @param searchItems
	 * @return 
	 */
	List<LoanSettSchemeDo> queryPage(Map<String, Object> searchItems);
}
