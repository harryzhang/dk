package com.hehenian.biz.facade.account.chinapnr;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import chinapnr.SecureLink;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.util.FormUtil;
import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.biz.facade.account.AbstractAccountManager;
import com.hehenian.biz.facade.account.IQueryManager;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;

@Component("chinaPnrQueryManager")
public class ChinaPnrQueryManager extends AbstractAccountManager implements IQueryManager {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public OutParameter queryBalance(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter queryBalanceBg(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 版本
            params.put("CmdId", "QueryBalanceBg");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            params.put("UsrCustId", (String) inParameter.getParams().get("UsrCustId"));
            params.put("ChkValue", getChkValue(params));// 请求签名

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);

            return getSuccessOutParameter(jsonString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("商户扣款查询失败!");
        }
    }

    @Override
    public OutParameter queryAccts(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter queryTransStat(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter queryTenderPlan(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter reconciliation(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 版本
            params.put("CmdId", "Reconciliation");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            params.put("BeginDate", (String) inParameter.getParams().get("BeginDate"));
            params.put("EndDate", (String) inParameter.getParams().get("EndDate"));
            params.put("PageNum", (Integer) inParameter.getParams().get("PageNum") + "");
            params.put("PageSize", (Integer) inParameter.getParams().get("PageSize") + "");
            params.put("QueryTransType", (String) inParameter.getParams().get("QueryTransType"));// 商户号
            params.put("ChkValue", getChkValue(params));// 请求签名

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);

            return getSuccessOutParameter(jsonString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("商户扣款查询失败!");
        }
    }

    @Override
    public OutParameter trfReconciliation(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 版本
            params.put("CmdId", "TrfReconciliation");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            params.put("BeginDate", (String) inParameter.getParams().get("BeginDate"));
            params.put("EndDate", (String) inParameter.getParams().get("EndDate"));
            params.put("PageNum", (Integer) inParameter.getParams().get("PageNum") + "");
            params.put("PageSize", (Integer) inParameter.getParams().get("PageSize") + "");
            params.put("ChkValue", getChkValue(params));// 请求签名

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);

            return getSuccessOutParameter(jsonString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("商户扣款查询失败!");
        }
    }

    @Override
    public OutParameter cashReconciliation(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 版本
            params.put("CmdId", "CashReconciliation");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            params.put("BeginDate", (String) inParameter.getParams().get("BeginDate"));
            params.put("EndDate", (String) inParameter.getParams().get("EndDate"));
            params.put("PageNum", (Integer) inParameter.getParams().get("PageNum") + "");
            params.put("PageSize", (Integer) inParameter.getParams().get("PageSize") + "");
            params.put("ChkValue", getChkValue(params));// 请求签名

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            return getSuccessOutParameter(jsonString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("商户扣款查询失败!");
        }
    }

    @Override
    public OutParameter queryAcctDetails(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 接口版本号
            params.put("CmdId", "QueryAcctDetails");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            params.put("UsrCustId", (Long) inParameter.getParams().get("UsrCustId") + "");
            params.put("ChkValue", getChkValue(params));// 设置请求签名

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
            return getFailureOutParameter("账户明细查询失败!");
        }
    }

    @Override
    public OutParameter saveReconciliation(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 版本
            params.put("CmdId", "SaveReconciliation");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            params.put("BeginDate", (String) inParameter.getParams().get("BeginDate"));
            params.put("EndDate", (String) inParameter.getParams().get("EndDate"));
            params.put("PageNum", (Integer) inParameter.getParams().get("PageNum") + "");
            params.put("PageSize", (Integer) inParameter.getParams().get("PageSize") + "");
            params.put("ChkValue", getChkValue(params));// 请求签名

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            return getSuccessOutParameter(jsonString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("商户扣款查询失败!");
        }
    }

    @Override
    public OutParameter queryReturnDzFee(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter corpRegisterQuery(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter creditAssignReconciliation(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 版本
            params.put("CmdId", "CreditAssignReconciliation");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            if (StringUtils.isNotBlank(inParameter.getOrdId())) {
                params.put("OrdId", inParameter.getOrdId());// 订单号
            }
            params.put("BeginDate", (String) inParameter.getParams().get("BeginDate"));
            params.put("EndDate", (String) inParameter.getParams().get("EndDate"));
            String sellCustId = (String) inParameter.getParams().get("SellCustId");
            if (StringUtils.isNotBlank(sellCustId)) {
                params.put("SellCustId", sellCustId);// 转让者
            }
            String buyCustId = (String) inParameter.getParams().get("BuyCustId");
            if (StringUtils.isNotBlank(buyCustId)) {
                params.put("BuyCustId", buyCustId);// 认购者
            }
            params.put("PageNum", (Integer) inParameter.getParams().get("PageNum") + "");
            params.put("PageSize", (Integer) inParameter.getParams().get("PageSize") + "");
            params.put("ReqExt", "");
            params.put("ChkValue", getChkValue(params));// 请求签名

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            return getSuccessOutParameter(jsonString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("商户扣款查询失败!");
        }
    }

    @Override
    public OutParameter fssPurchaseReconciliation(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter fssRedeemReconciliation(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter queryFss(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter queryFssAccts(InParameter inParameter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutParameter queryCardInfo(InParameter inParameter) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("Version", chinaPnrConfig.getVersion());// 版本
            params.put("CmdId", "QueryCardInfo");// 消息类型
            params.put("MerCustId", chinaPnrConfig.getMerCustId());// 商户号
            params.put("UsrCustId", (String) inParameter.getParams().get("UsrCustId"));// 汇付客户号
            params.put("CardId", (String) inParameter.getParams().get("CardId"));// 取现银行的账户号
            params.put("ChkValue", getChkValue(params));// 请求签名

            String jsonString = HttpClientUtils.post(chinaPnrConfig.getGateway(), params);
            return getSuccessOutParameter(jsonString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return getFailureOutParameter("商户扣款查询失败!");
        }
    }

}
