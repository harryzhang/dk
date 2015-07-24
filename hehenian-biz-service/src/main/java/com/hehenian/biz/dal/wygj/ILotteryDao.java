/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: ILotteryDao.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-13 下午3:08:01
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.wygj.dataobject.LotteryInfo;

public interface ILotteryDao {
	
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
	 * 添加抽奖记录
	 * @Description: TODO
	 * @param info
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-13 下午2:51:43
	 */
	public int addLotteryInfo(LotteryInfo info);
	
	/**
	 * 根据userId获取已抽奖次数
	 * @Description: TODO
	 * @param userId
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-13 下午3:46:41
	 */
	public int countLotteryByUser(Integer userId);
	
	/**
	 * 查询最新抽奖记录
	 * @Description: TODO
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-13 下午2:54:40
	 */
	public List<LotteryInfo> queryTopLotteryList();
	
	/**
	 * 更新账户余额
	 * @Description: TODO
	 * @param userId
	 * @param amount
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-14 上午10:38:09
	 */
	public int updateUserBalance(Integer userId,BigDecimal amount);
	
	/**
	 * 交易流水
	 * @Description: TODO
	 * @param params
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-14 下午5:42:33
	 */
	public int insertFundAccountLog(Map<String,Object> params);
	
	/**
	 * 用户资金账户
	 * @Description: TODO
	 * @param userId
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-14 下午6:02:49
	 */
	public Map<String,Object> getFundUserAccountById(Integer userId);

}
