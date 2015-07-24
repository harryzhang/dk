package com.hehenian.biz.service.bid.color.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.dataobject.ActivityConfig;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.activity.dataobject.ActivityTradeDo;
import com.hehenian.biz.common.bid.IAutoBidAmountService;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.activity.IActivityOrderComponent;
import com.hehenian.biz.component.activity.IActivityTradeComponent;

@Service("colorlifeBidAmountService")
public class ColorlifeBidAmountServiceImpl implements IAutoBidAmountService {

	@Autowired
	private IActivityTradeComponent activityTradeComponent;
	@Autowired
	private IUserComponent userComponent;
	@Autowired
	private IActivityOrderComponent activityOrderComponent;

    @Autowired
    private ActivityConfig          activityConfig;

	@Override
	public double getAutoBidAmount(long userId, Date bidDate) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("fromUserId", userId);

		// 取用户
		AccountUserDo accountUser = userComponent.getById(userId);
		if (null == accountUser) {
			return 0d;
		}

		// 查询用户是否有订单， 有订单用户表示参加活动
		List<ActivityOrderDo> orderList = activityOrderComponent
				.getByUserId(userId);
		if (null == orderList || orderList.isEmpty()) {
			return accountUser.getUsableSum();// 未参加活动给可用金额
		}

		// 取最小未扣款记录
        List<ActivityTradeDo> unDeductTradeDoList = activityTradeComponent
				.selectLastUnDeductActivityTrade(parameterMap);

        if (null == unDeductTradeDoList || unDeductTradeDoList.size() < 1) { // 未生成扣款记录返回用户可用余额
			return accountUser.getUsableSum();
		}

        // 冻结日期
        double freezeDays = StringUtil.strToInt(activityConfig.getFreezeDays());
        double freezeAmount = 0;
        for (ActivityTradeDo unDeductTradeDo : unDeductTradeDoList) {
            Date endDate = unDeductTradeDo.getTradeTime();
            // 扣款日7天内扣留扣款金额
            long diffDay = DateUtil.diffDays(bidDate, endDate);
            if (diffDay < freezeDays) {
                freezeAmount = CalculateUtils.add(freezeAmount, unDeductTradeDo.getAmount());
            }
		}

        return CalculateUtils.sub(accountUser.getUsableSum(), freezeAmount);

	}

}
