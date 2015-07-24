/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.service.wygj.impl
 * @Title: LotteryServiceImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-13 下午3:05:50
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.service.wygj.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hehenian.biz.common.wygj.ILotteryService;
import com.hehenian.biz.common.wygj.dataobject.LotteryInfo;
import com.hehenian.biz.common.wygj.dataobject.LotteryPrize;
import com.hehenian.biz.dal.wygj.ILotteryDao;
import com.hehenian.biz.dal.wygj.ILotteryPrizeDao;
import com.hehenian.common.redis.SpringRedisCacheService;

@Service("lotteryService")
public class LotteryServiceImpl implements ILotteryService, InitializingBean {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private ILotteryDao lotteryDao;
	@Resource
	private ILotteryPrizeDao lotteryPrizeDao;
	@Autowired
	private SpringRedisCacheService springRedisCacheService;
	
	private static boolean isRun = true;
	private static List<LotteryPrize> prizeList = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		prizeList = lotteryPrizeDao.queryLotteryPrize();//所有奖项
	}
	
	public List<LotteryPrize> initLotteryPrize() {
		isRun = false;
		prizeList = lotteryPrizeDao.queryLotteryPrize();
		isRun = true;
		return prizeList;
	}
	
	@Override
	public int getLotteryNumber(Integer userId) {
		int count = lotteryDao.getLotteryNumber(userId);
		int yetCount = lotteryDao.countLotteryByUser(userId);//已抽次数
		//剩余次数=该抽次数-已抽次数
		return count>yetCount?count-yetCount:0;
	}

	@Override
	public LotteryPrize addLotteryInfo(LotteryInfo info) {
		//概率数组
		Integer quantitty[] = new Integer[prizeList.size()];
		for(int i=0;i<prizeList.size();i++){
			quantitty[i] = prizeList.get(i).getTotalQuantitty();
		}
		//根据概率获取奖项id
		Integer prizeId = getLotteryRand(quantitty); 
		LotteryPrize prize = prizeList.get(prizeId);
		logger.info("Lottery===userId:"+info.getUserId()+"===prize:"+prizeId);
		//修改奖项 加锁 lock
		LotteryPrize lock = prizeList.get(prizeId);
		info.setPrizeName(lock.getPrizeName());
		synchronized (lock) {
			try{
				if(isRun && lock.getDayQuantity() > 0 && lock.getYetQuantity() > 0) {
					lock.setDayQuantity(lock.getDayQuantity() -1);
					lock.setYetQuantity(lock.getYetQuantity() -1);
					//更新奖池剩余情况
					lotteryPrizeDao.updateLotteryPrize(lock);
				}else{
					info.setPrizeName("1"); //否则默认1元
					prize = prizeList.get(0);
				}
				//生成抽奖记录
				int result = lotteryDao.addLotteryInfo(info);
				if(result>0){
					//流水记录
					BigDecimal amount = new BigDecimal(info.getPrizeName());
//					Map<String,Object> params = new HashMap<String,Object>();
//					params.put("log_type", "LOTTERY"); //交易类型：抽奖
//					params.put("trade_amount",amount); //trade_amount
//					params.put("user_id", info.getUserId());
//					params.put("remark", "物业国际抽奖活动");
//					// 查用户资金账户
//					Map<String,Object> accountMap = lotteryDao.getFundUserAccountById(info.getUserId());
//					if(accountMap!=null){
//						params.put("user_account_id",accountMap.get("user_account_id"));
//					}
//					//账户交易流水
//					lotteryDao.insertFundAccountLog(params);
					lotteryDao.updateUserBalance(info.getUserId(), amount);//更新账户余额
					logger.info("Lottery===update user balance===userId:"+info.getUserId()+"===balance amount:"+amount);
				}
			}catch(Exception ex){
				logger.error("Lottery===抽奖发生异常===userId:"+info.getUserId(), ex);
			}
		}
		return prize;
	}
	
	
	@Override
	public List<LotteryInfo> queryTopLotteryList() {
		return lotteryDao.queryTopLotteryList();
	}
	
	//根据概率获取奖项
	public Integer getLotteryRand(Integer obj[]){
		Integer result = 0;
		int total = 0;
		for(int i=0;i<obj.length;i++){
			total+=obj[i];
		}
		for(int i=0;i<obj.length;i++){//概率数组循环 
			//随机生成1到total的整数
			int randomNum = new Random().nextInt(total);
			if(randomNum<obj[i]){//中奖
				result = i;
				break;
			}else{
				total -=obj[i];
			}
		}
		return result;
	}
}
