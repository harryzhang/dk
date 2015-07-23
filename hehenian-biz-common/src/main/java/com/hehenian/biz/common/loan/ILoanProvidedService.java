/**
 * @auther liminglong
 * @date 2015年4月22日
 */
package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.dataobject.LoanProvidedDo;

/**
 * @author liminglong
 *
 */
public interface ILoanProvidedService {
	
	/**
	 * 得到产品列表
	 * @auther liminglmf
	 * @date 2015年4月24日
	 * @param param
	 * @param page
	 * @return
	 */
	PageDo<LoanProvidedDo> getLoanProvidedPage(Map<String,Object> param, PageDo<LoanProvidedDo> page);
	
	/**
	 * 新增产品
	 * @auther liminglmf
	 * @date 2015年4月27日
	 * @param loanProductDo
	 */
	int addLoanProvidedDo(LoanProvidedDo loanProvidedDo);
	
	/**
	 * 根据ID查询产品
	 * @auther liminglmf
	 * @date 2015年4月27日
	 * @param proId
	 * @return
	 */
	LoanProvidedDo getProvById(Long id);
	
	/**
	 * 修改产品信息
	 * @auther liminglmf
	 * @date 2015年4月28日
	 * @param loanProductDo
	 * @return
	 */
	int updateLoanProvidedDo(LoanProvidedDo loanProvidedDo);
	
	/**
	 * 删除产品
	 * @auther liminglmf
	 * @date 2015年4月28日
	 * @param ids
	 * @return
	 */
	int deleteLoanProvidedDo(String ids);
	
	/**
	 * 根据条件查选产品前提
	 * @auther liminglmf
	 * @date 2015年4月28日
	 * @param param
	 * @return
	 */
	List<LoanProvidedDo> getLoanProvidList(Map<String,Object> param);
	
	/**
	 * 根据产品ID查询产品前提
	 * @auther liminglmf
	 * @date 2015年4月29日
	 * @param productId
	 * @return
	 */
	int selectCountByProdId(Long prodId);

}
