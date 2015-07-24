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

import com.hehenian.biz.common.loan.dataobject.LoanChannelDo;

public interface ILoanChannelDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanChannelDo getById(Long id);
	
	public LoanChannelDo getBySourceId(Map<String,Object> parameterMap) ;
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanChannelDo> selectLoanChannel(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanChannelById(LoanChannelDo newLoanChannelDo);
	
	/**
	 * 新增
	 */
	public int addLoanChannel(LoanChannelDo newLoanChannelDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long id);

}
