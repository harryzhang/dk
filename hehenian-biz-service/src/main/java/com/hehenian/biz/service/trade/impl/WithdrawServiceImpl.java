package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.IWithdrawService;
import com.hehenian.biz.common.trade.dataobject.MerCashDo;
import com.hehenian.biz.common.trade.dataobject.WithdrawDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.account.IBankCardComponent;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.component.trade.IMerCostComponent;
import com.hehenian.biz.component.trade.IReconciliationComponent;
import com.hehenian.biz.component.trade.IWithdrawComponent;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;
import com.ibm.icu.text.DecimalFormat;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service("withdrawService")
public class WithdrawServiceImpl implements IWithdrawService {
    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private IAccountManagerService   accountManagerService;
    @Autowired
    private IWithdrawComponent       withdrawComponent;
    @Autowired
    private IUserComponent           userComponent;
    @Autowired
    private IPersonComponent         personComponent;
    @Autowired
    private IBankCardComponent       bankCardComponent;
    @Autowired
    private IMerCostComponent        merCostComponent;
    @Autowired
    private IFundrecordComponent     fundrecordComponent;
    @Autowired
    private IReconciliationComponent reconciliationComponent;

    @Override
    public IResult<?> addWithdraw(WithdrawDo withdrawDo) {
        IResult<String> result = new ResultSupport<String>();
        try {
            // 校验提现申请信息
            result = checkWithdrawApply(withdrawDo);
            if (!result.isSuccess()) {
                return result;
            }
            // 新增提现申请
            withdrawComponent.addWithdraw(withdrawDo);
            // 发送提现申请请求到汇付
            return cash(withdrawDo);
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
     * 提现申请
     *
     * @param withdrawDo
     * @return
     */
    private IResult<String> cash(WithdrawDo withdrawDo) {
        AccountUserDo accountUserDo = userComponent.getById(withdrawDo.getUserId());
        InParameter inParameter = new InParameter();
        inParameter.setOrdId(withdrawDo.getId() + "");
        inParameter.setRetUrl("cash.do");
        inParameter.setBgRetUrl("cashBg.do");
        inParameter.getParams().put("usrCustId", accountUserDo.getUsrCustId() + "");// 汇付注册客户号
        inParameter.getParams().put("transAmt", new DecimalFormat("#0.00").format(withdrawDo.getSum()));
        inParameter.getParams().put("openAcctId", (withdrawDo.getAcount() != null ? withdrawDo.getAcount() : ""));
        inParameter.getParams().put("servFee", getWithdrawServFee(withdrawDo.getUserId(), withdrawDo.getSum()));// 提现服务费
        OutParameter outParameter = accountManagerService.cash(inParameter, AccountType.CHINAPNR);

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

    /**
     * 获取提现服务费，每月三笔免费提现，其余按提现1%收取，最高2元每笔
     *
     * @param userId
     * @param transAmt
     * @return
     * @author: liuzgmf
     * @date: 2014年10月17日下午2:17:28
     */
    private Double getWithdrawServFee(Long userId, Double transAmt) {
        Date beginDate = DateUtils.truncate(DateUtils.setDays(new Date(), 1), Calendar.DATE);
        Date endDate = DateUtils.addMonths(beginDate, 1);
        int count = withdrawComponent.countWithdrawQty(userId, beginDate, endDate);
        if (count > 3) {
            Double servFee = CalculateUtils.mul(transAmt, 0.01);
            return (CalculateUtils.lt(servFee, 2.00) ? servFee : 2.00);
        } else {
            return 0.00;
        }
    }

    /**
     * 校验提现申请信息
     *
     * @param withdrawDo
     * @return
     */
    private IResult<String> checkWithdrawApply(WithdrawDo withdrawDo) {
        IResult<String> result = new ResultSupport<String>();
        AccountUserDo userDo = userComponent.getById(withdrawDo.getUserId());
        if (userDo == null) {
            result.setSuccess(false);
            result.setErrorMessage("无此用户!");
            return result;
        }
        if (userDo.getEnable().intValue() == 2) {
            result.setSuccess(false);
            result.setErrorMessage("禁用用户!");
            return result;
        }
        if (userDo.getEnable().intValue() == 3) {
            result.setSuccess(false);
            result.setErrorMessage("黑名单用户!");
            return result;
        }
        double withdrawAmt = CalculateUtils.sub(userDo.getUsableSum(), userDo.getLockAmount());
        if (CalculateUtils.lt(withdrawAmt, withdrawDo.getSum())) {
            result.setSuccess(false);
            result.setErrorMessage("账户余额不足!");
            return result;
        }

        result.setSuccess(true);
        return result;
    }

    @Override
    public Integer deleteById(Long id) {
        return withdrawComponent.deleteById(id);
    }

    @Override
    public WithdrawDo getById(Long id) {
        return withdrawComponent.getById(id);
    }

    @Override
    public IResult<?> addWithdrawCallback(WithdrawDo withdrawDo) {
        IResult<Integer> result = new ResultSupport<Integer>(true);
        try {
            WithdrawDo localWithdrawDo = withdrawComponent.getById(withdrawDo.getId());
            if (!CalculateUtils.eq(localWithdrawDo.getSum(), withdrawDo.getSum())) {
                throw new BusinessException("提现申请[" + withdrawDo.getId() + "]金额[" + localWithdrawDo.getSum()
                        + "]与汇付返回提现金额[" + withdrawDo.getSum() + "]不一致!");
            }
            AccountUserDo userDo = userComponent.getUserByCustId(withdrawDo.getUsrCustId());
            if (userDo.getId().longValue() != localWithdrawDo.getUserId().longValue()) {
                throw new BusinessException("提现[" + withdrawDo.getId() + "]用户不一致!");
            }
            if (StringUtils.isNotBlank(localWithdrawDo.getTrxId())) {
                throw new BusinessException("提现记录[" + withdrawDo.getId() + "]已处理!");
            }

            // 发送冻结请求到汇付
            InParameter inParameter = new InParameter();
            inParameter.setOrdId(fundrecordComponent.getAutoIncrementId() + "");
            inParameter.getParams().put("usrCustId", withdrawDo.getUsrCustId());
            inParameter.getParams().put("transAmt", new DecimalFormat("#0.00").format(withdrawDo.getSum()));
            OutParameter outParameter = accountManagerService.usrFreezeBg(inParameter, AccountType.CHINAPNR);
            if (!outParameter.isSuccess()) {
                withdrawComponent.updateStatus(withdrawDo.getId(), 5, 0);
                throw new BusinessException("冻结用户提现[" + withdrawDo.getId() + "]金额失败!");
            }
            withdrawDo.setTrxId(outParameter.getTrxId());

            // 执行平台提现申请操作，如果操作失败，解冻提现金额
            try {
                withdrawComponent.updateWithdrawCallback(withdrawDo);
            } catch (RuntimeException e) {
                logger.error(e.getMessage(), e);
                usrUnFreeze(withdrawDo.getId(), withdrawDo.getTrxId());
            }
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn(e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 解冻用户提现金额
     *
     * @param withdrawDo
     * @author: liuzgmf
     * @date: 2014-11-4下午4:06:33
     */
    private void usrUnFreeze(long ordId, String trxId) {
        InParameter inParameter = new InParameter();
        inParameter.setOrdId(ordId + "");
        inParameter.setTrxId(trxId);
        OutParameter outParameter = accountManagerService.usrUnFreeze(inParameter, AccountType.CHINAPNR);
        if (!outParameter.isSuccess()) {
            throw new BusinessException("解冻用户提现[" + ordId + "]金额失败!");
        }
    }

    @Override
    public IResult<?> updateWithdrawAudit(WithdrawDo withdrawDo) {
        IResult<?> result = new ResultSupport<String>(true);
        try {
            result = checkAuditingWithdraw(withdrawDo.getId());
            if (!result.isSuccess()) {
                return result;
            }

            if (withdrawDo.getStatus().intValue() == 5) {// 如果拒绝提现，则需要解冻用户的提现金额
                InParameter inParameter = new InParameter();
                WithdrawDo localWithdrawDo = withdrawComponent.getById(withdrawDo.getId());
                inParameter.setTrxId(localWithdrawDo.getTrxId());
                OutParameter outParameter = accountManagerService.usrUnFreeze(inParameter, AccountType.CHINAPNR);
                if (!outParameter.isSuccess()) {
                    throw new BusinessException("审核失败，提现金额解冻失败!");
                }
                withdrawComponent.updateWithdrawFault(withdrawDo);
            } else {
                withdrawComponent.updateWithdrawAudit(withdrawDo);
            }
            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn(e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private IResult<?> checkAuditingWithdraw(Long withdrawId) {
        IResult<String> result = new ResultSupport<String>();
        WithdrawDo localWithdrawDo = withdrawComponent.getById(withdrawId);
        if (localWithdrawDo.getStatus().intValue() == 2) {
            result.setSuccess(false);
            result.setErrorMessage("该笔提现款已完成!");
            return result;
        }
        if (localWithdrawDo.getStatus().intValue() == 3) {
            result.setSuccess(false);
            result.setErrorMessage("该笔提现款已取消!");
            return result;
        }
        if (localWithdrawDo.getStatus().intValue() == 4) {
            result.setSuccess(false);
            result.setErrorMessage("该笔提现款正在转账中!");
            return result;
        }
        if (localWithdrawDo.getStatus().intValue() == 5) {
            result.setSuccess(false);
            result.setErrorMessage("该笔提现款已受理!");
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    @Override
    public IResult<?> updateWithdrawTransfer(WithdrawDo withdrawDo) {
        IResult<?> result = new ResultSupport<String>();
        try {
            // 校验提现申请记录是否符合提现要求
            result = checkWithdrawStatus(withdrawDo);
            if (!result.isSuccess()) {
                return result;
            }
            // 解冻
            WithdrawDo localWithdrawDo = withdrawComponent.getById(withdrawDo.getId());
            InParameter inParameter = new InParameter();
            inParameter.setOrdId(localWithdrawDo.getId() + "");
            inParameter.setTrxId(localWithdrawDo.getTrxId());
            OutParameter outParameter = accountManagerService.usrUnFreeze(inParameter, AccountType.CHINAPNR);
            if (!outParameter.isSuccess()) {
                result.setSuccess(false);
                result.setErrorMessage(outParameter.getRespDesc());
                return result;
            }
            // 提现复核
            InParameter costInParameter = new InParameter();
            costInParameter.setOrdId(localWithdrawDo.getId() + "");
            AccountUserDo userDo = userComponent.getById(localWithdrawDo.getUserId());
            costInParameter.getParams().put("usrCustId", userDo.getUsrCustId() + "");
            costInParameter.getParams().put("transAmt", new DecimalFormat("#0.00").format(localWithdrawDo.getSum()));
            costInParameter.getParams().put("auditFlag", getAuditFlag(withdrawDo.getStatus()));
            outParameter = accountManagerService.cashAudit(costInParameter, AccountType.CHINAPNR);
            if (!outParameter.isSuccess() && !"重复交易".equals(outParameter.getRespDesc())) {
                result.setSuccess(false);
                result.setErrorMessage(outParameter.getRespDesc());
                return result;
            }
            // 校验提现金额
            String transAmt = (String) outParameter.getParams().get("TransAmt");
            if (StringUtils.isBlank(transAmt) || CalculateUtils.le(Double.valueOf(transAmt), 0.00)) {
                result.setSuccess(false);
                result.setErrorMessage("转账金额错误!");
                return result;
            }
            // 修改提现记录及增加交易记录
            withdrawDo.setSum(Double.valueOf(transAmt));
            boolean success = withdrawComponent.updateWithdrawTransfer(withdrawDo);
            if (success) {
                result.setSuccess(true);
                return result;
            } else {
                result.setSuccess(false);
                result.setErrorMessage("操作失败，请稍后再试!");
            }
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn(e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取审核状态
     *
     * @return
     */
    private String getAuditFlag(Integer status) {
        if (status.intValue() == 2) {
            return "S";
        } else if (status.intValue() == 5) {
            return "R";
        }
        return null;
    }

    /**
     * 交易提现申请记录的状态
     *
     * @param withdrawDo
     * @param result
     * @return
     */
    private IResult<?> checkWithdrawStatus(WithdrawDo withdrawDo) {
        IResult<String> result = new ResultSupport<String>();
        WithdrawDo localWithdrawDo = withdrawComponent.getById(withdrawDo.getId());
        if (localWithdrawDo == null) {
            result.setSuccess(false);
            result.setErrorMessage("提现申请记录不存在!");
            return result;
        }
        localWithdrawDo.setStatus(localWithdrawDo.getStatus() == null ? 5 : localWithdrawDo.getStatus());
        if (localWithdrawDo.getStatus().intValue() == 1) {
            result.setSuccess(false);
            result.setErrorMessage("该笔提现款正在审核中!");
            return result;
        }
        if (localWithdrawDo.getStatus().intValue() == 2) {
            result.setSuccess(false);
            result.setErrorMessage("该笔提现款已完成!");
            return result;
        }
        if (localWithdrawDo.getStatus().intValue() == 3) {
            result.setSuccess(false);
            result.setErrorMessage("该笔提现款已取消!");
            return result;
        }
        if (localWithdrawDo.getStatus().intValue() == 5) {
            result.setSuccess(false);
            result.setErrorMessage("该笔提现款已受理!");
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    @Override
    public IResult<?> addMerWithdraw(MerCashDo merCashDo) {
        IResult<?> result = new ResultSupport<String>();
        try {
            // 新增平台提现记录
            Long merCostId = merCostComponent.addMerCash(merCashDo);

            // 平台提现
            InParameter inParameter = new InParameter();
            inParameter.setOrdId(merCostId + "");
            inParameter.getParams().put("usrCustId", merCashDo.getUsrCustId());
            inParameter.getParams().put("transAmt", merCashDo.getSum());
            inParameter.getParams().put("remark", "");
            OutParameter outParameter = accountManagerService.merCash(inParameter, AccountType.CHINAPNR);
            if (!outParameter.isSuccess()) {
                result.setSuccess(false);
                result.setErrorMessage(outParameter.getRespDesc());
                return result;
            }

            // 修改平台提现记录
            String feeAmt = (String) outParameter.getParams().get("FeeAmt");
            merCashDo.setPoundage(Double.parseDouble(feeAmt));// 提现手续费
            merCashDo.setCardNo((String) outParameter.getParams().get("OpenAcctId"));// 银行卡号
            merCashDo.setStatus(1);// 1代表成功
            merCostComponent.updateMerCash(merCashDo);

            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn(e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean updateStatus(Long withdrawId, Integer status, Integer origStatus) {
        return withdrawComponent.updateStatus(withdrawId, status, origStatus);
    }

}
