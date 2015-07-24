package com.hehenian.biz.facade.account;

import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;

/**
 * 银行或者第三方查询管理接口
 * 
 * @author liuzgmf
 *
 */
public interface IQueryManager {
    /**
     * 余额查询 (页面)
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryBalance(InParameter inParameter);

    /**
     * 余额查询 (后台)
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryBalanceBg(InParameter inParameter);

    /**
     * 商户子账户信息查询
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryAccts(InParameter inParameter);

    /**
     * 交易状态查询
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryTransStat(InParameter inParameter);

    /**
     * 自动投标计划状态查询
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryTenderPlan(InParameter inParameter);

    /**
     * 投标对账(放款和还款对账)
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter reconciliation(InParameter inParameter);

    /**
     * 商户扣款对账
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter trfReconciliation(InParameter inParameter);

    /**
     * 取现对账
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter cashReconciliation(InParameter inParameter);

    /**
     * 账户明细查询
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryAcctDetails(InParameter inParameter);

    /**
     * 充值对账
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter saveReconciliation(InParameter inParameter);

    /**
     * 垫资手续费返还查询
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryReturnDzFee(InParameter inParameter);

    /**
     * 担保类型企业开户状态查询接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter corpRegisterQuery(InParameter inParameter);

    /**
     * 债权查询
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter creditAssignReconciliation(InParameter inParameter);

    /**
     * 生利宝转入对账接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter fssPurchaseReconciliation(InParameter inParameter);

    /**
     * 生利宝转出对账接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter fssRedeemReconciliation(InParameter inParameter);

    /**
     * 生利宝产品信息查询
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryFss(InParameter inParameter);

    /**
     * 生利宝账户信息查询
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryFssAccts(InParameter inParameter);

    /**
     * 银行卡查询接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter queryCardInfo(InParameter inParameter);
}
