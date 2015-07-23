package com.hehenian.biz.common.bid;

import java.util.Date;

/**
 * 
 * @author zhangyunhmf
 * 
 */
public interface IAutoBidService {

	/**
	 * 获取自动投标金额
	 * 
	 * @param userId
	 *            用户ID
	 * @param bidDate
	 *            投标日期
	 * @return 返回结果可能会为负数或零
	 */
	Double getAutoBidAmount(long userId, Date bidDate);

}
