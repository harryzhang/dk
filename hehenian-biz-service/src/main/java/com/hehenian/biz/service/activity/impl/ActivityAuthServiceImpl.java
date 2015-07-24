/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.service.activity.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.IActivityAuthService;
import com.hehenian.biz.common.activity.dataobject.ActivityAuthDo;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.activity.IActivityAuthComponent;
import com.hehenian.biz.component.activity.IActivityOrderComponent;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

public class ActivityAuthServiceImpl implements IActivityAuthService {
    private final Logger            logger = Logger.getLogger(this.getClass());
    private long                    userId0;                                   // 彩生活用户ID
    private long                    userId1;                                   // 电信用户ID
    @Autowired
    private IAccountManagerService  accountManagerService;
    @Autowired
    private IUserComponent          userComponent;
    @Autowired
    private IActivityAuthComponent  activityAuthComponent;
    @Autowired
    private IActivityOrderComponent activityOrderComponent;

    public void setUserId0(long userId0) {
        this.userId0 = userId0;
    }

    public void setUserId1(long userId1) {
        this.userId1 = userId1;
    }

    public ActivityAuthDo getById(int id) {
        return activityAuthComponent.getById(id);
    }

    public List<ActivityAuthDo> selectActivityAuth(Map<String, Object> parameterMap) {
        return activityAuthComponent.selectActivityAuth(parameterMap);
    }

    public int updateActivityAuthById(ActivityAuthDo newActivityAuthDo) {
        return activityAuthComponent.updateActivityAuthById(newActivityAuthDo);
    }

    public int deleteById(int id) {
        return activityAuthComponent.deleteById(id);
    }

    public ActivityAuthDo getByOrdId(ActivityAuthDo activityAuthDo) {
        activityAuthDo.setToUserId(activityAuthDo.getAuthType().intValue() == 0 ? userId0 : userId1);// 收费用户ID
        return activityAuthComponent.getByOrdId(activityAuthDo);
    }

    @Override
    public IResult<?> transferAuth(long ordId, long fromUserId) {
        IResult<String> result = new ResultSupport<String>();
        try {
            ActivityOrderDo orderDo = activityOrderComponent.getById(ordId);
            if (orderDo == null) {
                throw new RuntimeException("活动订单[" + ordId + "]记录不存在!");
            }
            ActivityAuthDo activityAuthDo = new ActivityAuthDo();
            activityAuthDo.setOrdId(ordId);
            activityAuthDo.setFromUserId(fromUserId);
            double rate = orderDo.getRate() + 1;
            activityAuthDo.setAuthAmount(CalculateUtils.mul(orderDo.getInvestAmount(), rate));// 授权金额
            activityAuthDo.setAuthType(orderDo.getOrdType());// 授权类型
            activityAuthDo.setToUserId(activityAuthDo.getAuthType().intValue() == 0 ? userId0 : userId1);// 收费用户ID
            result = transferAuth(activityAuthDo);
            if (!result.isSuccess()) {
                return result;
            }

            // 新增活动授权记录
            addActivityAuth(activityAuthDo);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 新增活动授权记录
     * 
     * @param fromUserId
     * @param toUserId
     * @param authAmt
     * @author: liuzgmf
     * @date: 2014年10月31日上午10:42:38
     */
    private void addActivityAuth(ActivityAuthDo activityAuthDo) {
        activityAuthDo.setAuthTime(new Date());
        activityAuthDo.setAuthStatus(2);// 授权中
        activityAuthComponent.addActivityAuth(activityAuthDo);
    }

    /**
     * 转账授权
     * 
     * @param fromUserId
     * @param toUserId
     * @param authAmt
     * @param result
     * @return
     * @author: liuzgmf
     * @date: 2014年10月31日上午10:37:07
     */
    private IResult<String> transferAuth(ActivityAuthDo activityAuthDo) {
        InParameter inParameter = new InParameter();
        inParameter.setRetUrl("transferAuthCb.do");
        AccountUserDo fromUserDo = userComponent.getById(activityAuthDo.getFromUserId());
        inParameter.getParams().put("UsrCustId", fromUserDo.getUsrCustId());
        AccountUserDo toUserDo = userComponent.getById(activityAuthDo.getToUserId());
        inParameter.getParams().put("InUsrCustId", toUserDo.getUsrCustId());
        Double authAmt = activityAuthComponent.getAuthAmtByFromUserIdAndToUserId(activityAuthDo.getFromUserId(),
                activityAuthDo.getToUserId());
        inParameter.getParams().put("AuthAmt", CalculateUtils.add(authAmt, activityAuthDo.getAuthAmount()));
        OutParameter outParameter = accountManagerService.direcTrfAuth(inParameter, AccountType.CHINAPNR);

        IResult<String> result = new ResultSupport<String>();
        if (outParameter.isSuccess()) {
            result.setSuccess(true);
            result.setModel((String) outParameter.getParams().get("htmlText"));
        } else {
            result.setSuccess(false);
            result.setErrorMessage(outParameter.getRespDesc());
        }
        return result;
    }

    @Override
    public IResult<?> transferAuthCb(long usrCustId, long inUsrCustId, double authAmt) {
        IResult<String> result = new ResultSupport<String>();
        try {
            activityAuthComponent.updateTransferAuth(usrCustId, inUsrCustId, authAmt);
            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

}
