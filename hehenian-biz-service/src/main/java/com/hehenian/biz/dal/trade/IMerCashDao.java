package com.hehenian.biz.dal.trade;

import com.hehenian.biz.common.trade.dataobject.MerCashDo;

public interface IMerCashDao {

	/**
	 * 新增商品提现记录
	 * 
	 * @param merCashDo
	 * @return
	 */
	int addMerCash(MerCashDo merCashDo);

	/**
	 * 新增商品提现记录
	 * 
	 * @param merCashDo
	 * @return
	 */
	int updateMerCash(MerCashDo merCashDo);

}
