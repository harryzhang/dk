package com.hehenian.biz.service.userhome.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.UserType;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.trade.IInvestRepaymentService;
import com.hehenian.biz.common.userhome.IUserIncomeService;
import com.hehenian.biz.common.userhome.dataobject.UserIncomeDo;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.component.userhome.IUserIncomeComponent;

@Service("userIncomeService")
public class UserIncomeServiceImpl implements IUserIncomeService {
	@Autowired
	private IUserIncomeComponent userIncomeComponent;
	@Autowired
	private IUserComponent userComponent;

	@Autowired
	private IInvestRepaymentService investRepaymentService;

	@Autowired
	private IFundrecordComponent fundrecordComponent;

	@Override
	public UserIncomeDo queryUserIncome(String userId, String userType) {

		AccountUserDo user = null;
		// 通过彩生活用户id 获取我们系统的用户对象
		if (UserType.COLOR_LIFE.name().equals(userType)) {
			user = userComponent.getUserByColorId(Long.valueOf(userId));
		}

		if (UserType.HEHENIAN.name().equals(userType)) {
			user = userComponent.getById(Long.valueOf(userId));
		}
		if (null == user) {
			return null;
		}
		// 查询用户投资数据
		UserIncomeDo userIncomeDo = userIncomeComponent.queryUserIncome(user
				.getId());
		if (null == userIncomeDo) {
			userIncomeDo = new UserIncomeDo();
		}
		// 设置用户可用金额和冻结金额
		userIncomeDo.setWithdrawalAmount(user.getUsableSum());
		userIncomeDo.setFreezeAmount(user.getFreezeSum());

		// 昨日增值和资产估值
		Double dailyIncome = investRepaymentService
				.getDailyIncome(user.getId());
		userIncomeDo.setDailyIncome(dailyIncome);

		Double assetValue = investRepaymentService.getAssetValue(user.getId());
		userIncomeDo.setAssetValue(assetValue);

		// 奖励
		Double reward = fundrecordComponent.getDailyIncentiveAmount(user
				.getId());

		userIncomeDo.setReward(reward);

		return userIncomeDo;
	}

}
