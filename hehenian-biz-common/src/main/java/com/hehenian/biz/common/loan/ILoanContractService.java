package com.hehenian.biz.common.loan;

public interface ILoanContractService {

	/**
	 * 调用服务获取每天的放款数据： 更新订单状态，生成还款计划，生成电子合约
	 * @param date "yyyy/MM/dd" 格式的日期参数
	 */
	public void fankuanProcess(String date);

}
