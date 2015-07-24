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

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.loan.dataobject.LoanCheckDetailDo;

public interface ILoanCheckDetailDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanCheckDetailDo getById(Long itemCheckId);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanCheckDetailDo> selectLoanCheckDetail(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanCheckDetailById(LoanCheckDetailDo newLoanCheckDetailDo);
	
	/**
	 * 新增
	 */
	public int addLoanCheckDetail(LoanCheckDetailDo newLoanCheckDetailDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long itemCheckId);

	public List<LoanCheckDetailDo> getCheckDetailPage(Map<String, Object> param);

	public int deleteByIds(@Param("ids")List<Long> ids);

	public int countDetailByCheckId(@Param("checkId")Long checkId);

}
