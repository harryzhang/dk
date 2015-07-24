package com.hehenian.biz.dal.account;

import com.hehenian.biz.common.account.dataobject.UserBindDo;
import org.apache.ibatis.annotations.Param;

public interface IUserBindDao {

    int saveUserBind(UserBindDo userBindDo);

    UserBindDo findUserBindByPartner(@Param("partnerId")int partnerId , @Param("partnerUserId")String partnerUserId);

    UserBindDo findUserBindByUser(@Param("partnerId")int partnerId , @Param("userId")Long userId);
}
