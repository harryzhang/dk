package com.hehenian.biz.component.userhome.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.userhome.dataobject.UserIncomeDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.trade.IInvestRepaymentComponent;
import com.hehenian.biz.component.userhome.IUserIncomeComponent;

@Service("userIncomeComponent")
public class UserIncomeComponentImpl implements IUserIncomeComponent {
	@Autowired
	private IInvestRepaymentComponent investRepaymentComponent;

	@Override
	public UserIncomeDo queryUserIncome(Long userId) {
		UserIncomeDo userIncomeDo = new UserIncomeDo();

		Map<String, Object> userAmountMap = investRepaymentComponent
				.getInvestSuccessAmountByUserId(userId);
		if (userAmountMap == null || userAmountMap.isEmpty()) {
			return userIncomeDo;
		}

		BigDecimal realAmount = userAmountMap.get("realAmount") == null ? BigDecimal.ZERO
				: (BigDecimal) userAmountMap.get("realAmount");
		BigDecimal hasGetAmount = userAmountMap.get("hasGetAmount") == null ? BigDecimal.ZERO
				: (BigDecimal) userAmountMap.get("hasGetAmount");
		BigDecimal notPI = userAmountMap.get("notPI") == null ? BigDecimal.ZERO
				: (BigDecimal) userAmountMap.get("notPI");
		BigDecimal shouldGetAmount = userAmountMap.get("shouldGetAmount") == null ? BigDecimal.ZERO
				: (BigDecimal) userAmountMap.get("shouldGetAmount");
		BigDecimal hasPrincipal = userAmountMap.get("hasPrincipal") == null ? BigDecimal.ZERO
				: (BigDecimal) userAmountMap.get("hasPrincipal");
		BigDecimal recivedInterest = userAmountMap.get("recivedInterest") == null ? BigDecimal.ZERO
				: (BigDecimal) userAmountMap.get("recivedInterest");

		userIncomeDo.setTotalInvestAmount(CalculateUtils.round(
				realAmount.doubleValue(), 2));
		userIncomeDo.setHasPrincipal(CalculateUtils.round(
				hasPrincipal.doubleValue(), 2));
		userIncomeDo.setTotalInterestAmount(CalculateUtils.round(
				hasGetAmount.doubleValue() - hasPrincipal.doubleValue(), 2));
		userIncomeDo.setRecievedInterest(CalculateUtils.round(
				recivedInterest.doubleValue(), 2));
		userIncomeDo.setRecivedPrincipal(CalculateUtils.round(
				realAmount.doubleValue(), 2));
		return userIncomeDo;
	}
}
