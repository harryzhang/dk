package com.hehenian.biz.facade.account.parameter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回参数对象
 * 
 * @author liuzgmf
 *
 */
public class OutParameter implements Serializable {
    private static final long   serialVersionUID = 1L;
    private boolean             success;                                         // 操作成功状态
    private String              respCode;                                        // 应答返回码
    private String              respDesc;                                        // 应答描叙
    private String              trxId;                                           // 交易编号（银行或第三方生成）
    private String              retUrl;                                          // 页面返回URL
    private String              bgRetUrl;                                        // 商户后台应答地址
    private Map<String, Object> params           = new HashMap<String, Object>(); // 返回参数

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    public String getBgRetUrl() {
        return bgRetUrl;
    }

    public void setBgRetUrl(String bgRetUrl) {
        this.bgRetUrl = bgRetUrl;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "OutParameter [success=" + success + ", respCode=" + respCode + ", respDesc=" + respDesc + ", trxId="
                + trxId + ", retUrl=" + retUrl + ", bgRetUrl=" + bgRetUrl + ", params=" + params + "]";
    }

}
