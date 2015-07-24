/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.loan;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanCheckDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckItemDo;

public interface ILoanCheckItemComponent {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanCheckItemDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanCheckItemDo> selectLoanCheckItem(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanCheckItemById(LoanCheckItemDo newLoanCheckItemDo);
	
	/**
	 * 新增
	 */
	public int addLoanCheckItem(LoanCheckItemDo newLoanCheckItemDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long id);
	
	/**
	 * 分页
	 * @auther liminglmf
	 * @date 2015年6月2日
	 * @param param
	 * @return
	 */
	public List<LoanCheckItemDo> getCheckItemPage(Map<String, Object> param);
	
	/**
	 * 条目明细分页
	 * @auther liminglmf
	 * @date 2015年6月2日
	 * @param param
	 * @return
	 */
	public List<LoanCheckDetailDo> getCheckDetailPage(Map<String, Object> param);
	
	public LoanCheckDetailDo getCheckDetailById(Long id);
	
	public int  updateLoanCheckDetailById(LoanCheckDetailDo loanCheckDetailDo);
	
	public int addLoanCheckDetail(LoanCheckDetailDo loanCheckDetailDo);
	
	public int deleteCheckDetailById(Long id);

	public int deleteCheckDetailIds(List<Long> idsList);

	public int countDetailByCheckId(Long id);

}
