/**
 * @auther liminglong
 * @date 2015年4月22日
 */
package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.dataobject.LoanFeeRuleDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.loan.dataobject.LoanSettSchemeDo;

/**
 * @author liminglong
 *
 */
public interface ILoanProductService {
	
	/**
	 * 得到产品列表
	 * @auther liminglmf
	 * @date 2015年4月24日
	 * @param param
	 * @param page
	 * @return
	 */
	PageDo<LoanProductDo> getLoanProductPage(Map<String,Object> param, PageDo<LoanProductDo> page);
	
	/**
	 * 新增产品
	 * @auther liminglmf
	 * @date 2015年4月27日
	 * @param loanProductDo
	 */
	int addLoanProductDo(LoanProductDo loanProductDo);
	
	/**
	 * 根据ID查询产品
	 * @auther liminglmf
	 * @date 2015年4月27日
	 * @param proId
	 * @return
	 */
	LoanProductDo getProdById(Long proId);
	
	/**
	 * 修改产品信息
	 * @auther liminglmf
	 * @date 2015年4月28日
	 * @param loanProductDo
	 * @return
	 */
	int updateLoanProductDo(LoanProductDo loanProductDo);
	
	/**
	 * 删除产品
	 * @auther liminglmf
	 * @date 2015年4月28日
	 * @param ids
	 * @return
	 */
	int deleteLoanProductDo(String ids);
	
/********************************产品方案****************************************************/			
	/**
	 * 
	 * @auther liminglmf
	 * @date 2015年4月28日
	 * @param prodId
	 * @return
	 */
	List<LoanSettSchemeDo> queryByProdId(Long prodId);

	
	/**
	 * 修改产品方案
	 * @auther liminglmf
	 * @date 2015年4月30日
	 * @param loanSettSchemeDo
	 * @return
	 */
	int updateLoanSettSchemeDo(LoanSettSchemeDo loanSettSchemeDo);
	
	/**
	 * 新增产品方案
	 * @auther liminglmf
	 * @date 2015年4月30日
	 * @param loanSettSchemeDo
	 * @return
	 */
	int addLoanSettSchemeDo(LoanSettSchemeDo loanSettSchemeDo);
	
	/**
	 * 删除产品方案
	 * @auther liminglmf
	 * @date 2015年4月30日
	 * @param ids
	 * @return
	 */
	int deleteLoanSettDo(String ids);
	
	PageDo<LoanSettSchemeDo> getLoanSettPage(Map<String, Object> param,
			PageDo<LoanSettSchemeDo> page);
	
/********************************产品方案费用规则****************************************************/	
	/**
	 * 修改产品方案费用规则
	 * @auther liminglmf
	 * @date 2015年4月30日
	 * @param loanSettSchemeDo
	 * @return
	 */
	int updateLoanFeeDo(LoanFeeRuleDo loanFeeRuleDo);
	
	/**
	 * 新增产品方案费用规则
	 * @auther liminglmf
	 * @date 2015年4月30日
	 * @param loanSettSchemeDo
	 * @return
	 */
	int addLoanFeeDo(LoanFeeRuleDo loanFeeRuleDo);
	
	/**
	 * 删除产品方案费用规则
	 * @auther liminglmf
	 * @date 2015年4月30日
	 * @param ids
	 * @return
	 */
	int deleteLoanFeeDo(String ids);

	PageDo<LoanFeeRuleDo> getLoanFeePage(Map<String, Object> param,
			PageDo<LoanFeeRuleDo> page);

	LoanSettSchemeDo getLoanSettById(Long id);

	LoanFeeRuleDo getLoanFeeById(Long id);

}
