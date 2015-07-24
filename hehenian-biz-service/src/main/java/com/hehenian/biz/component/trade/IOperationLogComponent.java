package com.hehenian.biz.component.trade;


public interface IOperationLogComponent {

	/**
	 * 新增操作日志记录
	 * 
	 * @param operationTable
	 *            操作表
	 * @param operationUser
	 *            操作人
	 * @param operationType
	 *            操作类型 1 增加 2 修改 3 删除 4 下载 5 excel
	 * @param operationIp
	 *            操作人IP
	 * @param operationMoney
	 *            操作金额
	 * @param operationRemarks
	 *            操作备注
	 * @param operationAround
	 *            1 前台 2 后台
	 * @return
	 */
	Integer addOperationLog(String operationTable, String operationUser, Integer operationType,
			String operationIp, Double operationMoney, String operationRemarks,
			Integer operationAround);

}
