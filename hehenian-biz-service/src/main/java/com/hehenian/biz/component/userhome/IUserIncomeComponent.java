package com.hehenian.biz.component.userhome;

import com.hehenian.biz.common.userhome.dataobject.UserIncomeDo;

public interface IUserIncomeComponent {

	UserIncomeDo queryUserIncome(Long userId);

}
