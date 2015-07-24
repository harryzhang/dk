package com.hehenian.biz.facade.account;

import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;

public interface IAccountManagerService {
    /**
     * 用户开户
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter userRegister(InParameter inParameter, AccountType accountType);

    /**
     * 后台用户开户
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter bgRegister(InParameter inParameter, AccountType accountType);

    /**
     * 用户绑卡
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter userBindCard(InParameter inParameter, AccountType accountType);

    /**
     * 后台接口绑卡
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter bgBindCard(InParameter inParameter, AccountType accountType);

    /**
     * 用户登录
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter userLogin(InParameter inParameter, AccountType accountType);

    /**
     * 账户信息修改
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter acctModify(InParameter inParameter, AccountType accountType);

    /**
     * 担保类型企业开户接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter corpRegister(InParameter inParameter, AccountType accountType);

    /**
     * 删除银行卡接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter delCard(InParameter inParameter, AccountType accountType);

    // --------------------------------------------------交易类接口---------------------------------

    /**
     * 网银充值
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter netSave(InParameter inParameter, AccountType accountType);

    /**
     * 商户无卡代扣充值
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter posWhSave(InParameter inParameter, AccountType accountType);

    /**
     * 资金（货款）冻结
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter usrFreezeBg(InParameter inParameter, AccountType accountType);

    /**
     * 资金（货款）解冻
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter usrUnFreeze(InParameter inParameter, AccountType accountType);

    /**
     * 主动投标
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter initiativeTender(InParameter inParameter, AccountType accountType);

    /**
     * 自动投标
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter autoTender(InParameter inParameter, AccountType accountType);

    /**
     * 投标撤销
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter tenderCancle(InParameter inParameter, AccountType accountType);

    /**
     * 自动投标计划
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter autoTenderPlan(InParameter inParameter, AccountType accountType);

    /**
     * 自动投标关闭
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter autoTenderPlanClose(InParameter inParameter, AccountType accountType);

    /**
     * 自动扣款（放款）
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter loans(InParameter inParameter, AccountType accountType);

    /**
     * 自动扣款（还款）
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter repayment(InParameter inParameter, AccountType accountType);

    /**
     * 转账（商户用）
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter transfer(InParameter inParameter, AccountType accountType);

    /**
     * 取现复核
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter cashAudit(InParameter inParameter, AccountType accountType);

    /**
     * 取现
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter cash(InParameter inParameter, AccountType accountType);

    /**
     * 用户账户支付
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter usrAcctPay(InParameter inParameter, AccountType accountType);

    /**
     * 商户代取现接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter merCash(InParameter inParameter, AccountType accountType);

    /**
     * 前台用户间转账接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter usrTransfer(InParameter inParameter, AccountType accountType);

    /**
     * 债权转让接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter creditAssign(InParameter inParameter, AccountType accountType);

    /**
     * 自动债权转让接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter autoCreditAssign(InParameter inParameter, AccountType accountType);

    /**
     * 生利宝交易接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter fssTrans(InParameter inParameter, AccountType accountType);

    /**
     * 定向转账授权接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter direcTrfAuth(InParameter inParameter, AccountType accountType);

    /**
     * 定向转账接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter direcTrf(InParameter inParameter, AccountType accountType);

    // --------------------------------------------------查询类接口---------------------------------

    /**
     * 余额查询 (页面)
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryBalance(InParameter inParameter, AccountType accountType);

    /**
     * 余额查询 (后台)
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryBalanceBg(InParameter inParameter, AccountType accountType);

    /**
     * 商户子账户信息查询
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryAccts(InParameter inParameter, AccountType accountType);

    /**
     * 交易状态查询
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryTransStat(InParameter inParameter, AccountType accountType);

    /**
     * 自动投标计划状态查询
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryTenderPlan(InParameter inParameter, AccountType accountType);

    /**
     * 投标对账(放款和还款对账)
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter reconciliation(InParameter inParameter, AccountType accountType);

    /**
     * 商户扣款对账
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter trfReconciliation(InParameter inParameter, AccountType accountType);

    /**
     * 取现对账
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter cashReconciliation(InParameter inParameter, AccountType accountType);

    /**
     * 账户明细查询
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryAcctDetails(InParameter inParameter, AccountType accountType);

    /**
     * 充值对账
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter saveReconciliation(InParameter inParameter, AccountType accountType);

    /**
     * 垫资手续费返还查询
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryReturnDzFee(InParameter inParameter, AccountType accountType);

    /**
     * 担保类型企业开户状态查询
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter corpRegisterQuery(InParameter inParameter, AccountType accountType);

    /**
     * 债权查询
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter creditAssignReconciliation(InParameter inParameter, AccountType accountType);

    /**
     * 生利宝转入对账接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter fssPurchaseReconciliation(InParameter inParameter, AccountType accountType);

    /**
     * 生利宝转出对账接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter fssRedeemReconciliation(InParameter inParameter, AccountType accountType);

    /**
     * 生利宝产品信息查询
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryFss(InParameter inParameter, AccountType accountType);

    /**
     * 生利宝账户信息查询
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryFssAccts(InParameter inParameter, AccountType accountType);

    /**
     * 银行卡查询接口
     * 
     * @param inParameter
     *            输入参数
     * @param accountType
     *            账户类型
     * @return
     */
    OutParameter queryCardInfo(InParameter inParameter, AccountType accountType);

}
