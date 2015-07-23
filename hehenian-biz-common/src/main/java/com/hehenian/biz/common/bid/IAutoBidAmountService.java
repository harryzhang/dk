package com.hehenian.biz.common.bid;

import java.util.Date;

/**
 * 根据用户类型获取自动投标金额
 * 
 * @author zhangyunhmf
 * 
 */
public interface IAutoBidAmountService {

	/**
	 * 可投标金额
	 * 
	 * @param userId
	 *            用户id
	 * @param bidDate
	 *            投标日期
	 * @return
	 */
	double getAutoBidAmount(long userId, Date bidDate);

}
