package com.hehenian.biz.facade.account;

import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;

/**
 * 银行或者第三方支付交易管理接口
 * 
 * @author liuzgmf
 *
 */
public interface ITradeManager {

    /**
     * 提现申请
     * 
     * @param inParameter
     * @return
     */
    OutParameter cash(InParameter inParameter);

    /**
     * 资金（货款）冻结
     * 
     * @param inParameter
     * @return
     */
    OutParameter usrFreezeBg(InParameter inParameter);

    /**
     * 资金（货款）解冻
     * 
     * @param inParameter
     * @return
     */
    OutParameter usrUnFreeze(InParameter inParameter);

    /**
     * 取现复核
     * 
     * @param inParameter
     * @return
     */
    OutParameter cashAudit(InParameter inParameter);

    /**
     * 网银充值
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter netSave(InParameter inParameter);

    /**
     * 商户无卡代扣充值
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter posWhSave(InParameter inParameter);

    /**
     * 主动投标
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter initiativeTender(InParameter inParameter);

    /**
     * 自动投标
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter autoTender(InParameter inParameter);

    /**
     * 投标撤销
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter tenderCancle(InParameter inParameter);

    /**
     * 自动投标计划
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter autoTenderPlan(InParameter inParameter);

    /**
     * 自动投标关闭
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter autoTenderPlanClose(InParameter inParameter);

    /**
     * 自动扣款（放款）
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter loans(InParameter inParameter);

    /**
     * 自动扣款（还款）
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter repayment(InParameter inParameter);

    /**
     * 转账（商户用）
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter transfer(InParameter inParameter);

    /**
     * 用户账户支付
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter usrAcctPay(InParameter inParameter);

    /**
     * 商户代取现接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter merCash(InParameter inParameter);

    /**
     * 前台用户间转账接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter usrTransfer(InParameter inParameter);

    /**
     * 债权转让接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter creditAssign(InParameter inParameter);

    /**
     * 自动债权转让接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter autoCreditAssign(InParameter inParameter);

    /**
     * 生利宝交易接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter fssTrans(InParameter inParameter);

    /**
     * 定向转账授权接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter direcTrfAuth(InParameter inParameter);

    /**
     * 定向转账接口
     * 
     * @param inParameter
     *            输入参数
     * @return
     */
    OutParameter direcTrf(InParameter inParameter);

}
