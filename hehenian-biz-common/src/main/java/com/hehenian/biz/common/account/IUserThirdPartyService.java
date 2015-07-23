package com.hehenian.biz.common.account;

import com.hehenian.biz.common.account.dataobject.UserThirdPartyDo;

public interface IUserThirdPartyService {

	UserThirdPartyDo getByTheThirdUserName(String theThirdUserName);

	UserThirdPartyDo getByUserId(Integer userId);

	int saveUserThirdParty(UserThirdPartyDo userThirdPartyDo);
	int deleteUserThirdPartyByUserId(Integer userId);
	int deleteUserThirdPartyBytheThirdUserName(String theThirdUserName);
	void saveUserColor();
}
