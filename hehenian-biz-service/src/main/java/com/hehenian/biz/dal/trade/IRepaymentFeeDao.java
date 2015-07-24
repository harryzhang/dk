/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.dal.trade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.trade.dataobject.RepaymentFeeDo;

public interface IRepaymentFeeDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public RepaymentFeeDo getById(Long id);
	
	/**
	 * 根据还款ID 查询费用列表
	 * @param id
	 * @return  
	 * @author: zhangyunhmf
	 * @date: 2014年10月24日下午1:12:12
	 */
	public List<RepaymentFeeDo> getByRepaymentId(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<RepaymentFeeDo> selectRepaymentFee(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateRepaymentFeeById(RepaymentFeeDo newRepaymentFeeDo);
	
	/**
	 * 新增
	 */
	public int addRepaymentFee(RepaymentFeeDo newRepaymentFeeDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
