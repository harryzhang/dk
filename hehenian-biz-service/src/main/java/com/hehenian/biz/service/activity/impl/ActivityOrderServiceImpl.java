/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.service.activity.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.IActivityOrderService;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.userhome.dataobject.UserIncomeDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.activity.IActivityOrderComponent;
import com.hehenian.biz.component.activity.IActivityTradeComponent;
import com.hehenian.biz.facade.colorlife.IColorOrderFacade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Service("activityOrderService")
public class ActivityOrderServiceImpl implements IActivityOrderService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IActivityOrderComponent activityOrderComponent;

    @Autowired
    private IUserComponent userComponent;

    @Autowired
    private IColorOrderFacade       colorOrderFacade;
    @Autowired
    private IActivityTradeComponent activityTradeComponent;

    /**
     * 根据ID 查询
     *
     * @parameter id
     */
    public ActivityOrderDo getById(int id) {
        return activityOrderComponent.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<ActivityOrderDo> selectActivityOrder(
            Map<String, Object> parameterMap) {
        return activityOrderComponent.selectActivityOrder(parameterMap);
    }

    /**
     * 更新
     */
    public int updateActivityOrderById(ActivityOrderDo newActivityOrderDo) {
        return activityOrderComponent
                .updateActivityOrderById(newActivityOrderDo);
    }

    /**
     * 新增
     */
    public Long addActivityOrder(ActivityOrderDo newActivityOrderDo) {
        int i = activityOrderComponent.addActivityOrder(newActivityOrderDo);
        if (i > 0) {
            return newActivityOrderDo.getOrdId();
        }
        return 0l;
    }

    /**
     * 删除
     */
    public int deleteById(int id) {
        return activityOrderComponent.deleteById(id);
    }

    @Override
    public ActivityOrderDo queryOrderDetail(String userId, String orderSN,
            String orderType) {
        AccountUserDo user = userComponent.getUserByColorId(Long
                .valueOf(userId));
        if (null == user) {
            return null;
        }
        ActivityOrderDo order = activityOrderComponent.queryOrderDetail(
				String.valueOf(user.getId()), orderSN, orderType);
		if (null == order) {
			return null;
		}

		// 可提现金额 = 订单金额 * （1+3.5%）
		// 订单利息 = 订单金额 * 3.5%
		int status = order.getOrdStatus() == null ? 0 : order.getOrdStatus();
		if (1 == status) {// 成功的才计算收益
			int amount = order.getInvestAmount() == null ? 0 : order
					.getInvestAmount().intValue();
            // order.setOrderInterestAmount(CalculateUtils.mul(amount, 0.035d));
            // // 订单利息
            // order.setOrderWithdrawalAmount(CalculateUtils.mul(amount,
            // 1.035d)); // 订单可提取金额

            order.setOrderInterestAmount(order.getProfit()); // 订单利息
            order.setOrderWithdrawalAmount(amount + order.getProfit()); // 订单可提取金额

		}
		return order;
	}

	@Override
	public ActivityOrderDo getByOrdNo(ActivityOrderDo activityOrderDo) {
		return activityOrderComponent.getByOrdNo(activityOrderDo);
	}

    public int updateStatus(AccountUserDo accountUserDo,ActivityOrderDo activityOrderDo){

		ActivityOrderDo newActivityOrderDo = new ActivityOrderDo();
		newActivityOrderDo.setOrdId(activityOrderDo.getOrdId());
		Date now = new Date();
		newActivityOrderDo.setUpdateTime(now);
		newActivityOrderDo.setOrdStatus(1);
		int i = activityOrderComponent.updateActivityOrderById(newActivityOrderDo);
        Boolean b = activityTradeComponent.addActivityTrades(activityOrderDo.getOrdId());
        if (b){
            logger.info("生成扣款计划表成功");
        }else {
            logger.error("生成扣款计划表失败");
        }
        int result = colorOrderFacade.sendOrderStatus(accountUserDo.getColorid(), activityOrderDo.getOrdNo(),
				activityOrderDo.getInvestAmount(), now,
                newActivityOrderDo.getOrdStatus() + "",
 activityOrderDo.getRemark(),
				activityOrderDo.getOrdId());
        if (result==0){
            logger.info("订单完成后通知彩生活成功");
        }else if (result==1){
            logger.error("订单完成后通知彩生活失败");
        }else{
            logger.error("订单完成后通知彩生活,参数非法");
        }
        return i;
    }

    public long hasOrder( int ordType, long userId){
        return activityOrderComponent.hasOrder( ordType, userId);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hehenian.biz.common.activity.IActivityOrderService#getAllOrderIncome
	 * (java.lang.String, java.util.Date)
	 */
	@Override
	public UserIncomeDo getAllOrderIncome(long userId, Date queryDate) {
        return getAllOrderIncomeByOrderSubType(userId, queryDate, null);
    }

    public int addRechargeMoney(long ordid, long addMoney, long userId) {
        return activityOrderComponent.addRechargeMoney(ordid, addMoney, userId);
    }

    /*
     * (no-Javadoc) <p>Title: getAllOrderIncome</p> <p>Description: </p>
     * 
     * @param userId
     * 
     * @param queryDate
     * 
     * @param orderSubType
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.common.activity.IActivityOrderService#getAllOrderIncome
     * (long, java.util.Date, java.lang.String)
     */
    @Override
    public UserIncomeDo getAllOrderIncomeByOrderSubType(long userId, Date queryDate, String orderSubType) {

        // 彩生活用户转和合年用户
        AccountUserDo user = userComponent.getUserByColorId(userId);
        if (null == user) {
            return null;
        }

        List<ActivityOrderDo> orderList = activityOrderComponent.getByUserId(user.getId(), orderSubType);
        if (null == orderList || orderList.isEmpty()) {
            return null;
        }

        double totalOrderAmount = 0;// 订单金额汇总
        double totalInterest = 0; // 合计利息
        for (ActivityOrderDo order : orderList) {
            int status = order.getOrdStatus() == null ? 0 : order.getOrdStatus().intValue();
            if (1 != status) { // 过滤未成功的订单
                continue;
            }
            if (order.getEndDate().getTime() >= queryDate.getTime()) { // 统计有效时间内的订单
                totalOrderAmount = CalculateUtils.add(order.getInvestAmount(), totalOrderAmount);
                // 订单利息
                // double interest = CalculateUtils.mul(order.getInvestAmount(),
                // order.getRate());
                double interest = order.getProfit();
                totalInterest = CalculateUtils.add(interest, totalInterest);

            }
        }
        UserIncomeDo userIncome = new UserIncomeDo();
        userIncome.setTotalInvestAmount(totalOrderAmount);

        // 可提现金额 = 订单金额 + 收益
        // 订单利息 = 订单金额 * 收益率
        userIncome.setRecievedInterest(totalInterest); // 订单利息汇总
        userIncome.setWithdrawalAmount(CalculateUtils.add(totalOrderAmount, totalInterest)); // 订单可提取金额汇总

        return userIncome;

    }

    @Override public boolean notifyColorLife(AccountUserDo accountUserDo, ActivityOrderDo activityOrderDo) {
        int result = colorOrderFacade.sendOrderStatus(accountUserDo.getColorid(), activityOrderDo.getOrdNo(),
                activityOrderDo.getInvestAmount(), new Date(),
                activityOrderDo.getOrdStatus() + "",
                activityOrderDo.getRemark(),
                activityOrderDo.getOrdId());
        if (result==0){
            logger.info("订单授权完成后通知彩生活成功");
            return true;
        }else if (result==1){
            logger.error("订单授权完成后通知彩生活失败");
        }else{
            logger.error("订单授权完成后通知彩生活,参数非法");
        }
        return false;
    }

}
