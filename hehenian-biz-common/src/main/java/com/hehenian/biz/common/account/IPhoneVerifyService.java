package com.hehenian.biz.common.account;

import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.base.result.IResult;

public interface IPhoneVerifyService {

    IResult savePhoneVerify(PhoneVerifyDo phoneVerifyDo);

    PhoneVerifyDo findPhoneVerify(Long userId);

    int updatePhoneVerifyStatus(PhoneVerifyDo phoneVerifyDo);

    int disablePhoneVerify(Long userId);

    /**
     * 更改邮箱
     * @param id
     * @param email
     * @return TODO
     */
	IResult<PhoneVerifyDo> updateEmail(Long id, String email);


}
