/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj.dataobject
 * @Title: LotteryService.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-13 下午2:49:42
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.LotteryInfo;
import com.hehenian.biz.common.wygj.dataobject.LotteryPrize;

public interface ILotteryService {
	
	/**
	 * 根据会员id获取抽奖次数
	 * @Description: TODO
	 * @param userId
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-13 下午2:53:21
	 */
	public int getLotteryNumber(Integer userId);
	
	/**
	 * 抽奖
	 * @Description: TODO
	 * @param info
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-13 下午2:51:43
	 */
	public LotteryPrize addLotteryInfo(LotteryInfo info);
	
	/**
	 * 查询最新抽奖记录
	 * @Description: TODO
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-13 下午2:54:40
	 */
	public List<LotteryInfo> queryTopLotteryList();
	
	/**
	 * 初始缓存
	 * @Description: TODO
	 * @author: chenzhpmf
	 * @date 2015-5-14 下午2:32:31
	 */
	public List<LotteryPrize> initLotteryPrize();

}
