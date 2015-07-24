package com.hehenian.biz.dal.account;

import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.account.dataobject.UserBindDo;

import org.apache.ibatis.annotations.Param;

public interface IPhoneVerifyDao {

    int savePhoneVerify(PhoneVerifyDo phoneVerifyDo);

    PhoneVerifyDo findPhoneVerify(@Param("userId")Long userId);

    int updatePhoneVerifyStatus(PhoneVerifyDo phoneVerifyDo);

    PhoneVerifyDo findPhoneVerifyByPhone(@Param("mobilePhone")String mobilePhone);

    int disablePhoneVerify(@Param("userId")Long userId);

    /**
     * 根据用户ID禁用邮箱
     * @param userId
     * @return
     */
	int disableEmailVerify(@Param("userId") Long userId);
}
