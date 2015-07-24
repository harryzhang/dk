package com.hehenian.biz.service.bid.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.UserType;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.bid.IAutoBidAmountService;
import com.hehenian.biz.common.bid.IAutoBidService;
import com.hehenian.biz.component.account.IUserComponent;

@Service("AutoBidService")
public class AutoBidServiceImpl implements IAutoBidService {
	@Autowired
	private IAutoBidAmountService colorlifeBidAmountService;
	@Autowired
	private IUserComponent userComponent;

	// private IAutoBidAmountPicker TelecomBidAmountPicker;

	/**
	 * 根据用户类型获取自动投标金额接口
	 * 
	 * @param userType
	 *            用户类型
	 * @return
	 */
	public IAutoBidAmountService getAutoBidAmountPicker(
			UserType userType) {
		if (UserType.COLOR_LIFE.equals(userType)) {
			return colorlifeBidAmountService;
		} else {
			return null;
		}
	}

	public UserType getUserType(long userId) {
		AccountUserDo user = userComponent.getById(userId);
		UserType userType = UserType.COLOR_LIFE;
		if (null != user.getColorid() && user.getColorid() > 0) {
			userType = UserType.COLOR_LIFE;
		} else {
			userType = null;
		}
		return userType;
	}

	@Override
	public Double getAutoBidAmount(long userId, Date bidDate) {
		UserType userType = this.getUserType(userId);
		return this.getAutoBidAmountPicker(userType).getAutoBidAmount(userId,
				bidDate);
	}

}
