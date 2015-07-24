package com.hehenian.biz.component.trade;

import com.hehenian.biz.common.trade.dataobject.MerCashDo;

public interface IMerCostComponent {

	/**
	 * 新增商户提现记录（平台提现）
	 * 
	 * @param merCashDo
	 * @return
	 */
	Long addMerCash(MerCashDo merCashDo);

	/**
	 * 修改商户提现记录（平台提现） 修改平台提现记录，新增提现申请记录，新增资金变动记录
	 * 
	 * @param merCashDo
	 * @return
	 */
	Boolean updateMerCash(MerCashDo merCashDo);

}
