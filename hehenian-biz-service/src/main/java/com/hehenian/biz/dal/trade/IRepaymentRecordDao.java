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

import com.hehenian.biz.common.trade.dataobject.RepaymentRecordDo;

public interface IRepaymentRecordDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public RepaymentRecordDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<RepaymentRecordDo> selectRepaymentRecord(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateRepaymentRecordById(RepaymentRecordDo newRepaymentRecordDo);
	
	/**
	 * 新增
	 */
	public int addRepaymentRecord(RepaymentRecordDo newRepaymentRecordDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
