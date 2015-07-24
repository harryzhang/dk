package com.hehenian.biz.component.trade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.trade.dataobject.BorrowTypeLogDo;

public interface IBorrowTypeLogComponent {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public BorrowTypeLogDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<BorrowTypeLogDo> selectBorrowTypeLog(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateBorrowTypeLogById(BorrowTypeLogDo newBorrowTypeLogDo);
	
	/**
	 * 新增
	 */
	public int addBorrowTypeLog(BorrowTypeLogDo newBorrowTypeLogDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
