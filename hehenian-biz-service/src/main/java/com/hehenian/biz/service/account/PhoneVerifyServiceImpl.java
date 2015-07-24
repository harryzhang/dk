package com.hehenian.biz.service.account;

import com.hehenian.biz.common.account.IPhoneVerifyService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.account.dataobject.UserBindDo;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IPhoneVerifyComponent;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.IInvestComponent;
import com.hehenian.biz.component.trade.IOperationLogComponent;
import com.hehenian.biz.component.trade.IRepaymentComponent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service("phoneVerifyService")
public class PhoneVerifyServiceImpl implements IPhoneVerifyService {
    private static final Logger LOGGER = Logger.getLogger(PhoneVerifyServiceImpl.class);
    @Autowired
    private IPhoneVerifyComponent phoneVerifyComponent;

    @Override public IResult savePhoneVerify(PhoneVerifyDo phoneVerifyDo) {
        IResult result;
        try {
            result = phoneVerifyComponent.savePhoneVerify(phoneVerifyDo);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            result = new ResultSupport();
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    @Override public PhoneVerifyDo findPhoneVerify(Long userId) {

        return phoneVerifyComponent.findPhoneVerify(userId);
    }

    @Override public int updatePhoneVerifyStatus(PhoneVerifyDo phoneVerifyDo) {

        return phoneVerifyComponent.updatePhoneVerifyStatus(phoneVerifyDo);
    }

    @Override public int disablePhoneVerify(Long userId) {
        return phoneVerifyComponent.disablePhoneVerify(userId);
    }

	@Override
	public IResult<PhoneVerifyDo> updateEmail(Long id, String email) {
		IResult<PhoneVerifyDo> result;
        try {
            result = phoneVerifyComponent.updateEmailVerify(id, email);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = new ResultSupport<PhoneVerifyDo>();
            result.setErrorMessage(e.getMessage());
        }
        return result;
	}
}
