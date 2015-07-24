/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.dal.loan;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanCheckItemDo;

public interface ILoanCheckItemDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanCheckItemDo getById(Long checkId);
	
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
	public int deleteById(Long checkId);

	public List<LoanCheckItemDo> getCheckItemPage(Map<String, Object> param);

}
