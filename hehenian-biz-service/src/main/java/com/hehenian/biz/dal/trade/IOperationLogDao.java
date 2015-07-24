package com.hehenian.biz.dal.trade;

import com.hehenian.biz.common.trade.dataobject.OperationLogDo;

public interface IOperationLogDao {

	/**
	 * 新增操作日志信息
	 * 
	 * @param operationLog
	 * @return
	 */
	Integer addOperationLog(OperationLogDo operationLogDo);

}
