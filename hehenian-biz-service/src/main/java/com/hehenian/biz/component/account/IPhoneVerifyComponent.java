package com.hehenian.biz.component.account;

import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.base.result.IResult;

public interface IPhoneVerifyComponent {

    IResult savePhoneVerify(PhoneVerifyDo phoneVerifyDo);

    PhoneVerifyDo findPhoneVerify(Long userId);

    int updatePhoneVerifyStatus(PhoneVerifyDo phoneVerifyDo);

    PhoneVerifyDo findPhoneVerifyByPhone(String mobilePhone);

    int disablePhoneVerify(Long userId);

	IResult<PhoneVerifyDo> updateEmailVerify(Long userId, String email);

}
