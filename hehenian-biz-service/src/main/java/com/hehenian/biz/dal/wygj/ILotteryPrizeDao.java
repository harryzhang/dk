/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: ILotteryPrizeDao.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-13 下午5:25:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.LotteryPrize;

public interface ILotteryPrizeDao {
	
	/**
	 * 获取奖品对象
	 * @Description: TODO
	 * @param prize
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-13 下午5:29:34
	 */
	public LotteryPrize getLotteryPrize(LotteryPrize prize);
	
	/**
	 * 查询奖品
	 * @Description: TODO
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-13 下午5:27:12
	 */
	public List<LotteryPrize> queryLotteryPrize();
	
	/**
	 * 修改奖品池信息
	 * @Description: TODO
	 * @param prize
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-13 下午5:28:48
	 */
	public int updateLotteryPrize(LotteryPrize prize);

}
