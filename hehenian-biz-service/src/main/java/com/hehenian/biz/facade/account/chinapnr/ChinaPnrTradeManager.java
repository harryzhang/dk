package com.hehenian.biz.facade.account.chinapnr;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hehenian.biz.common.trade.IParameterLogService;
import com.hehenian.biz.common.trade.dataobject.ParameterLogDo;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.FormUtil;
import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.biz.facade.account.AbstractAccountManager;
import com.hehenian.biz.facade.account.ITradeManager;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;
import com.ibm.icu.text.DecimalFormat;

@Component("chinaPnrTradeManager")
public class ChinaPnrTradeManager extends AbstractAccountManager implements ITradeManager {
    private final Logger         logger      = Logger.getLogger(this.getClass());
    private final String         NEW_VERSION = "20";
    @Autowired
    private IParameterLogService parameterLogService;

    @Override
    public OutParameter cash(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 接口版本号
            params.put("CmdId", "Cash");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            params.put("OrdId", inParameter.getOrdId());
            params.put("UsrCustId", (String) inParameter.getParams().get("usrCustId"));
            params.put("TransAmt", (String) inParameter.getParams().get("transAmt"));
            Double servFee = (Double) inParameter.getParams().get("servFee");
            if (params.get("Version").equals(NEW_VERSION) && servFee != null && CalculateUtils.gt(servFee, 0)) {
                params.put("ServFee", new DecimalFormat("#0.00").format(servFee));// 提现服务费
                params.put("ServFeeAcctId", chinaPnrConfig.getFeeAccount());// 提现账号
            }
            params.put("OpenAcctId", (String) inParameter.getParams().get("openAcctId"));
            params.put("RetUrl", getRetUrl(inParameter.getRetUrl()));
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("Remark", "hhnCash");// 取现描述，长度有限（64 个汉字）
            params.put("MerPriv", "Cash");// 商户私有域
            params.put("ChkValue", getChkValue(params));// 设置请求签名
            params.put("CharSet", DEFAULT_CHARSET);

            logger.info("提现请求参数：" + params);
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数
            String htmlText = FormUtil.buildHtmlForm(params, chinaPnrConfig.getGateway(), "post");
            OutParameter outParameter = new OutParameter();
            outParameter.setSuccess(true);
            outParameter.getParams().put("htmlText", htmlText);
            return outParameter;
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("提现申请失败!");
        }
    }

    @Override
    public OutParameter usrFreezeBg(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 版本
            params.put("CmdId", "UsrFreezeBg");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            params.put("UsrCustId", (String) inParameter.getParams().get("usrCustId"));// 商户客户号，汇付生成，用户的唯一性标识
            params.put("SubAcctType", "");// 子账号类型
            params.put("SubAcctId", "");// 子账户号
            params.put("OrdId", inParameter.getOrdId());// 订单号
            params.put("OrdDate", DateFormatUtils.format(new Date(), "yyyyMMdd"));
            params.put("TransAmt", (String) inParameter.getParams().get("transAmt"));
            params.put("RetUrl", "");
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("MerPriv", "UsrFreezeBg");

            params.put("ChkValue", getChkValue(params));// 设置请求签名
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);

            OutParameter outParameter = getSuccessOutParameter(jsonString);
            parameterLogService.addParameterLog(outParameter.getParams(), ParameterLogDo.ParameterType.RESP);// 记录交易参数
            return outParameter;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("冻结账户资金失败!");
        }
    }

    @Override
    public OutParameter usrUnFreeze(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());
            params.put("CmdId", "UsrUnFreeze");
            params.put("MerCustId", chinaPnrConfig.getMerCustId());
            params.put("OrdId", inParameter.getTrxId()); // 订单号
            params.put("OrdDate", DateFormatUtils.format(new Date(), "yyyyMMdd"));
            params.put("TrxId", inParameter.getTrxId());// 商户平台交易唯一定长18位
            params.put("RetUrl", "");
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("MerPriv", "UsrUnFreeze");
            params.put("ChkValue", getChkValue(params));// 设置请求签名

            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            OutParameter outParameter = getSuccessOutParameter(jsonString);
            parameterLogService.addParameterLog(outParameter.getParams(), ParameterLogDo.ParameterType.RESP);// 记录交易参数
            return outParameter;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("解冻账户资金失败!");
        }
    }

    @Override
    public OutParameter cashAudit(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());
            params.put("CmdId", "CashAudit");
            params.put("MerCustId", chinaPnrConfig.getMerCustId());
            params.put("OrdId", inParameter.getOrdId());
            params.put("UsrCustId", (String) inParameter.getParams().get("usrCustId"));
            params.put("TransAmt", (String) inParameter.getParams().get("transAmt"));
            params.put("AuditFlag", (String) inParameter.getParams().get("auditFlag"));
            params.put("RetUrl", "");
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("MerPriv", "CashAudit");
            params.put("ChkValue", getChkValue(params));// 设置请求签名
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);

            OutParameter outParameter = getSuccessOutParameter(jsonString);
            parameterLogService.addParameterLog(outParameter.getParams(), ParameterLogDo.ParameterType.RESP);// 记录交易参数
            return outParameter;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("提现复核失败!");
        }
    }

    @Override
    public OutParameter netSave(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());
            params.put("CmdId", "NetSave");
            params.put("MerCustId", chinaPnrConfig.getMerCustId());
            params.put("UsrCustId", (String) inParameter.getParams().get("usrCustId"));

            params.put("OrdId", inParameter.getOrdId());
            params.put("OrdDate", (String) inParameter.getParams().get("OrdDate"));
            params.put("GateBusiId", (String) inParameter.getParams().get("gateBusiId"));
            params.put("OpenBankId", (String) inParameter.getParams().get("OpenBankId"));
            params.put("DcFlag", (String) inParameter.getParams().get("DcFlag"));
            params.put("TransAmt", (String) inParameter.getParams().get("transAmt"));
            params.put("RetUrl", getRetUrl(inParameter.getRetUrl()));
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("MerPriv", (String) inParameter.getParams().get("MerPriv"));
            params.put("ChkValue", getChkValue(params));// 设置请求签名
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            String htmlText = FormUtil.buildHtmlForm(params, chinaPnrConfig.getGateway(), "post");

            OutParameter outParameter = new OutParameter();
            outParameter.setSuccess(true);
            outParameter.getParams().put("htmlText", htmlText);
            return outParameter;
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("充值失败!");
        }
    }

    @Override
    public OutParameter posWhSave(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter initiativeTender(InParameter inParameter) {
        try {
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("Version", "20");
            map.put("CmdId", "InitiativeTender");
            map.put("MerCustId", chinaPnrConfig.getMerCustId());
            map.put("OrdId", inParameter.getOrdId());
            map.put("OrdDate", (String) inParameter.getParams().get("OrdDate"));
            map.put("TransAmt", (String) inParameter.getParams().get("TransAmt"));
            map.put("UsrCustId", (String) inParameter.getParams().get("UsrCustId"));
            map.put("MaxTenderRate", "0.10"); // 最大投资手 续费率
            map.put("BorrowerDetails", (String) inParameter.getParams().get("BorrowerDetails")); // 借款人信息
            // ,
            // 变长
            map.put("IsFreeze", "Y");
            map.put("FreezeOrdId", (String) inParameter.getParams().get("FreezeOrdId"));
            map.put("RetUrl", getRetUrl(inParameter.getRetUrl()));
            map.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            map.put("MerPriv", (String) inParameter.getParams().get("borrowId"));
            map.put("ReqExt", "");
            map.put("ChkValue", getChkValue(map));// 设置请求签名
            parameterLogService.addParameterLog(map, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            String htmlText = FormUtil.buildHtmlForm(map, chinaPnrConfig.getGateway(), "post");

            OutParameter outParameter = new OutParameter();
            outParameter.setSuccess(true);
            outParameter.getParams().put("htmlText", htmlText);
            return outParameter;
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter(e.getMessage());
        }

    }

    @Override
    public OutParameter autoTender(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter tenderCancle(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter autoTenderPlan(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter autoTenderPlanClose(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter loans(InParameter inParameter) {

        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());
            params.put("CmdId", "Loans");
            params.put("MerCustId", chinaPnrConfig.getMerCustId());
            params.put("OrdId", inParameter.getOrdId());
            String ordDate = DateFormatUtils.format((Date) inParameter.getParams().get("OrdDate"), "yyyyMMdd");
            params.put("OrdDate", ordDate);

            params.put("OutCustId", (Long) inParameter.getParams().get("OutCustId") + "");
            String transAmt = new DecimalFormat("##0.00").format((Double) inParameter.getParams().get("TransAmt"));
            params.put("TransAmt", transAmt);
            String fee = new DecimalFormat("##0.00").format((Double) inParameter.getParams().get("Fee"));
            params.put("Fee", fee);
            params.put("SubOrdId", inParameter.getOrdId());
            params.put("SubOrdDate", ordDate);

            params.put("InCustId", (Long) inParameter.getParams().get("InCustId") + "");
            params.put("DivDetails", (String) inParameter.getParams().get("DivDetails"));
            params.put("IsDefault", (String) inParameter.getParams().get("IsDefault"));
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("MerPriv", "Loans");
            params.put("ChkValue", getChkValue(params));// 设置请求签名
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            OutParameter outParameter = getSuccessOutParameter(jsonString);
            parameterLogService.addParameterLog(outParameter.getParams(), ParameterLogDo.ParameterType.RESP);// 记录交易参数
            return outParameter;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("放款失败!");
        }
    }

    @Override
    public OutParameter repayment(InParameter inParameter) {

        String jsonString = "";
        Map<String, String> params = new LinkedHashMap<String, String>();
        try {
            params.put("Version", "20");
            params.put("CmdId", "Repayment");
            params.put("MerCustId", chinaPnrConfig.getMerCustId());
            params.put("OrdId", inParameter.getOrdId());
            String OrdDate = DateUtil.dateToYMD(new Date());
            params.put("OrdDate", OrdDate);

            params.put("OutCustId", (String) inParameter.getParams().get("OutCustId"));// 出账客户号
            params.put("SubOrdId", (String) inParameter.getParams().get("SubOrdId"));// 订单号
            // 变长
            // 20
            // 位
            // 由商户的系统生成，必须保证唯一。
            params.put("SubOrdDate", (String) inParameter.getParams().get("SubOrdDate"));// 关联投标订单流水日期是
            params.put("OutAcctId", (String) inParameter.getParams().get("OutAcctId"));// 出账子账户
            params.put("TransAmt", (String) inParameter.getParams().get("TransAmt"));// 金额
            params.put("Fee", (String) inParameter.getParams().get("Fee"));// Fee
            // 扣款手续费,
            // 放款或扣款的手续费
            params.put("InCustId", (String) inParameter.getParams().get("InCustId"));// 入账客户号
            params.put("InAcctId", (String) inParameter.getParams().get("InAcctId"));// 入账子账户

            params.put("DivDetails", (String) inParameter.getParams().get("DivDetails"));// 分账账户串
            // 变长
            // FeeObjectFlag = 定长 1 位 I：向入款客户号InCustId收取 O：向出款客户号OutCustId收取
            params.put("FeeObjFlag", "O");// 商户可以选择向借款人或者还款人收取手续
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("MerPriv", (String) inParameter.getParams().get("MerPriv"));

            params.put("ChkValue", getChkValue(params));// 设置请求签名
            params.put("CharSet", DEFAULT_CHARSET);
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数
            logger.info("调用汇付回款开始， 参数：" + params);

            jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            OutParameter outParameter = getSuccessOutParameter(jsonString);
            parameterLogService.addParameterLog(outParameter.getParams(), ParameterLogDo.ParameterType.RESP);// 记录交易参数
            return outParameter;
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("还款失败!");
        } finally {
            logger.info("调用汇付回款结束， 参数：" + params + "; 汇付返回结果：" + jsonString);
        }

    }

    @Override
    public OutParameter transfer(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());
            params.put("CmdId", "Transfer");
            params.put("OrdId", inParameter.getOrdId());
            params.put("OutCustId", (String) inParameter.getParams().get("OutCustId"));
            params.put("OutAcctId", (String) inParameter.getParams().get("OutAcctId"));

            Double transAmt = (Double) inParameter.getParams().get("TransAmt");
            params.put("TransAmt", new DecimalFormat("##0.00").format(transAmt));
            params.put("InCustId", (String) inParameter.getParams().get("InCustId"));
            params.put("InAcctId", (String) inParameter.getParams().get("InAcctId"));
            params.put("RetUrl", "");
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));

            params.put("MerPriv", "Transfer");

            params.put("ChkValue", getChkValue(params));// 设置请求签名
            params.put("CharSet", DEFAULT_CHARSET);

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            OutParameter outParameter = getSuccessOutParameter(jsonString);
            parameterLogService.addParameterLog(outParameter.getParams(), ParameterLogDo.ParameterType.RESP);// 记录交易参数
            return outParameter;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("提现复核失败!");
        }
    }

    @Override
    public OutParameter usrAcctPay(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter merCash(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());
            params.put("CmdId", "MerCash");
            params.put("MerCustId", chinaPnrConfig.getMerCustId());
            params.put("OrdId", inParameter.getOrdId());
            params.put("UsrCustId", (String) inParameter.getParams().get("usrCustId"));

            Double transAmt = (Double) inParameter.getParams().get("transAmt");
            params.put("TransAmt", new DecimalFormat("##0.00").format(transAmt));
            params.put("RetUrl", "");
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("Remark", (String) inParameter.getParams().get("remark"));
            params.put("MerPriv", "MerCash");

            params.put("ChkValue", getChkValue(params));// 设置请求签名
            params.put("CharSet", DEFAULT_CHARSET);
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            OutParameter outParameter = getSuccessOutParameter(jsonString);
            parameterLogService.addParameterLog(outParameter.getParams(), ParameterLogDo.ParameterType.RESP);// 记录交易参数
            return outParameter;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("提现复核失败!");
        }
    }

    @Override
    public OutParameter usrTransfer(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter creditAssign(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());
            params.put("CmdId", "CreditAssign");
            params.put("MerCustId", chinaPnrConfig.getMerCustId());
            params.put("SellCustId", (Long) inParameter.getParams().get("SellCustId") + "");
            DecimalFormat df = new DecimalFormat("##0.00");
            params.put("CreditAmt", df.format((Double) inParameter.getParams().get("CreditAmt")));

            params.put("CreditDealAmt", df.format((Double) inParameter.getParams().get("CreditDealAmt")));
            params.put("BidDetails", (String) inParameter.getParams().get("BidDetails"));
            params.put("Fee", (String) inParameter.getParams().get("Fee"));
            params.put("DivDetails", (String) inParameter.getParams().get("DivDetails"));
            params.put("BuyCustId", (Long) inParameter.getParams().get("BuyCustId") + "");

            params.put("OrdId", inParameter.getOrdId());
            params.put("OrdDate", (String) inParameter.getParams().get("OrdDate"));
            params.put("RetUrl", getRetUrl(inParameter.getRetUrl()));
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("MerPriv", (String) inParameter.getParams().get("MerPriv"));

            params.put("ReqExt", "");
            params.put("ChkValue", getChkValue(params));// 设置请求签名
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            logger.info("债权转让请求参数：" + params);

            String htmlText = FormUtil.buildHtmlForm(params, chinaPnrConfig.getGateway(), "post");
            htmlText = htmlText.replace("value=\"{\"BidDetails", "value='{\"BidDetails");
            htmlText = htmlText.replace("}]}]}\"/>", "}]}]}'/>");
            OutParameter outParameter = new OutParameter();
            outParameter.setSuccess(true);
            outParameter.getParams().put("htmlText", htmlText);
            return outParameter;
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("债权转让失败!");
        }
    }

    @Override
    public OutParameter autoCreditAssign(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter fssTrans(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter direcTrfAuth(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());
            params.put("CmdId", "DirecTrfAuth");
            params.put("MerCustId", chinaPnrConfig.getMerCustId());
            params.put("UsrCustId", (Long) inParameter.getParams().get("UsrCustId") + "");
            params.put("InUsrCustId", (Long) inParameter.getParams().get("InUsrCustId") + "");
            DecimalFormat df = new DecimalFormat("##0.00");
            params.put("AuthAmt", df.format((Double) inParameter.getParams().get("AuthAmt")));
            params.put("RetUrl", getRetUrl(inParameter.getRetUrl()));
            params.put("ReqExt", "");
            params.put("ChkValue", getChkValue(params));// 设置请求签名
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            String htmlText = FormUtil.buildHtmlForm(params, chinaPnrConfig.getGateway(), "post");
            OutParameter outParameter = new OutParameter();
            outParameter.setSuccess(true);
            outParameter.getParams().put("htmlText", htmlText);
            return outParameter;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("提现复核失败!");
        }
    }

    @Override
    public OutParameter direcTrf(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());
            params.put("CmdId", "DirecTrf");
            params.put("MerCustId", chinaPnrConfig.getMerCustId());
            params.put("OrdId", inParameter.getOrdId());
            params.put("UsrCustId", (Long) inParameter.getParams().get("UsrCustId") + "");
            params.put("InUsrCustId", (Long) inParameter.getParams().get("InUsrCustId") + "");
            DecimalFormat df = new DecimalFormat("##0.00");
            params.put("TransAmt", df.format((Double) inParameter.getParams().get("TransAmt")));
            params.put("RetUrl", "");
            params.put("BgRetUrl", getBgRetUrl(inParameter.getBgRetUrl()));
            params.put("MerPriv", "DirecTrf");
            params.put("ReqExt", "");
            params.put("ChkValue", getChkValue(params));// 设置请求签名
            parameterLogService.addParameterLog(params, ParameterLogDo.ParameterType.REQU);// 记录交易参数

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            OutParameter outParameter = getSuccessOutParameter(jsonString);
            parameterLogService.addParameterLog(outParameter.getParams(), ParameterLogDo.ParameterType.RESP);// 记录交易参数
            return outParameter;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("提现复核失败!");
        }
    }

}
