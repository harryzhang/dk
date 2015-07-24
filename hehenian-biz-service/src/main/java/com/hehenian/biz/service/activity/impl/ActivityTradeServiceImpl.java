/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.service.activity.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.IActivityTradeService;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.activity.dataobject.ActivityTradeDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.activity.IActivityOrderComponent;
import com.hehenian.biz.component.activity.IActivityTradeComponent;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;
import com.hehenian.biz.facade.colorlife.IColorFeeFacade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Service("activityTradeService")
public class ActivityTradeServiceImpl implements IActivityTradeService {
    private final Logger            logger = Logger.getLogger(this.getClass());
    @Autowired
    private IActivityTradeComponent activityTradeComponent;
    @Autowired
    private IAccountManagerService  accountManagerService;
    @Autowired
    private IUserComponent          userComponent;
    @Autowired
    private IActivityOrderComponent activityOrderComponent;
    @Autowired
    private IColorFeeFacade         colorFeeFacade;

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public ActivityTradeDo getById(int id) {
        return activityTradeComponent.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<ActivityTradeDo> selectActivityTrade(Map<String, Object> parameterMap) {
        return activityTradeComponent.selectActivityTrade(parameterMap);
    }

    /**
     * 更新
     */
    public int updateActivityTradeById(ActivityTradeDo newActivityTradeDo) {
        return activityTradeComponent.updateActivityTradeById(newActivityTradeDo);
    }

    /**
     * 新增
     */
    public int addActivityTrade(ActivityTradeDo newActivityTradeDo) {
        return activityTradeComponent.addActivityTrade(newActivityTradeDo);
    }

    /**
     * 删除
     */
    public int deleteById(int id) {
        return activityTradeComponent.deleteById(id);
    }

    @Override
    public IResult<?> addActivityTrades(long ordId) {
        IResult<String> result = new ResultSupport<String>();
        try {
            activityTradeComponent.addActivityTrades(ordId);
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

    @Override
    public IResult<?> transfer() {
        IResult<String> result = new ResultSupport<String>();
        try {
            Date tradeTime = org.apache.commons.lang.time.DateUtils.addDays(new Date(), 1);
            List<ActivityTradeDo> tradeDoList = activityTradeComponent
                    .queryNoTransferTrades(org.apache.commons.lang.time.DateUtils.truncate(tradeTime, Calendar.DATE));
            for (ActivityTradeDo activityTradeDo : tradeDoList) {
                transfer(activityTradeDo);
            }
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
     * 转账
     * 
     * @param activityTradeDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月1日下午2:25:09
     */
    private boolean transfer(ActivityTradeDo activityTradeDo) {
        try {
            InParameter inParameter = new InParameter();
            inParameter.setOrdId(activityTradeDo.getTradeId() + "");
            AccountUserDo fromUserDo = userComponent.getById(activityTradeDo.getFromUserId());
            inParameter.getParams().put("UsrCustId", fromUserDo.getUsrCustId());
            AccountUserDo toUserDo = userComponent.getById(activityTradeDo.getToUserId());
            inParameter.getParams().put("InUsrCustId", toUserDo.getUsrCustId());
            inParameter.getParams().put("TransAmt", activityTradeDo.getAmount());
            OutParameter outParameter = accountManagerService.direcTrf(inParameter, AccountType.CHINAPNR);
            if (!outParameter.isSuccess()) {
                logger.error("扣款记录[" + activityTradeDo.getTradeId() + "]转账失败:" + outParameter.getRespDesc());
                return false;
            }

            // 转账
            activityTradeDo.setRealAmount(Double.parseDouble((String) outParameter.getParams().get("TransAmt")));// 转账金额
            activityTradeComponent.updateActivityTradeTransfer(activityTradeDo);

            // 通知彩生活
            ActivityOrderDo orderDo = activityOrderComponent.getById(activityTradeDo.getOrdId());
            colorFeeFacade.sendFeeStatus(fromUserDo.getColorid(), orderDo.getOrdNo(), activityTradeDo.getAmount(),
                    new Date(), 12, "1", "扣款成功");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
