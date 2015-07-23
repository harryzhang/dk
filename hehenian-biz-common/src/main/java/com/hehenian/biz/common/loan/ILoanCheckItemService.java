/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.common.loan;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckItemDo;


public interface ILoanCheckItemService{

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
	 * 评分项分页
	 * @auther liminglmf
	 * @date 2015年6月2日
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<LoanCheckItemDo> getCheckItemPage(Map<String, Object> param,
			PageDo<LoanCheckItemDo> page);
	
	/**
	 * 评分项条目明细分页
	 * @auther liminglmf
	 * @date 2015年6月2日
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<LoanCheckDetailDo> getCheckDetailPage(
			Map<String, Object> param, PageDo<LoanCheckDetailDo> page);
	
	public LoanCheckDetailDo getCheckDetailById(Long id);
	
	public int  updateLoanCheckDetailById(LoanCheckDetailDo loanCheckDetailDo);
	
	public int addLoanCheckDetail(LoanCheckDetailDo loanCheckDetailDo);
	
	public int deleteCheckDetailById(Long id);
	
	int deleteCheckDetailIds(String ids);
	
	/**
	 * 根据评分项Id查询条目数量
	 * @auther liminglmf
	 * @date 2015年6月3日
	 * @param id
	 * @return
	 */
	public int countDetailByCheckId(Long id);
}
