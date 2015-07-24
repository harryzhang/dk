package com.hehenian.biz.facade.account.chinapnr;

public class ChinaPnrConfig {
    private String version;        // 版本号
    private String gateway;        // 请求地址
    private String merCustId;      // 商户号
    private String merId;          // 客户号
    private String retHost;        // 返回主机连接
    private String retUrl;         // 页面返回URL
    private String bgRetUrl;       // 商户后台应答地址
    private String merKeyFile;     // 签名文件
    private String feeCustId;     // 服务费账户汇付账号
    private String feeAccount;     // 服务费账户
    private String consFeeCustId; // 咨询费账户汇付账号
    private String consFeeAccount; // 咨询费账户
    private String compAccount;    // 代偿账户
    private String compCustId;    // 代偿账户汇付账号
    private String coloAccount;    // 彩付宝账户
    private String fxAccount;      //罚息子账户
    

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMerCustId() {
        return merCustId;
    }

    public void setMerCustId(String merCustId) {
        this.merCustId = merCustId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getRetHost() {
        return retHost;
    }

    public void setRetHost(String retHost) {
        this.retHost = retHost;
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

    public String getMerKeyFile() {
        return merKeyFile;
    }

    public void setMerKeyFile(String merKeyFile) {
        this.merKeyFile = merKeyFile;
    }

    public String getFeeAccount() {
        return feeAccount;
    }

    public void setFeeAccount(String feeAccount) {
        this.feeAccount = feeAccount;
    }

    public String getConsFeeAccount() {
        return consFeeAccount;
    }

    public void setConsFeeAccount(String consFeeAccount) {
        this.consFeeAccount = consFeeAccount;
    }

    public String getCompAccount() {
        return compAccount;
    }

    public void setCompAccount(String compAccount) {
        this.compAccount = compAccount;
    }

    public String getColoAccount() {
        return coloAccount;
    }

    public void setColoAccount(String coloAccount) {
        this.coloAccount = coloAccount;
    }

    public String getFeeCustId() {
        return feeCustId;
    }

    public void setFeeCustId(String feeCustId) {
        this.feeCustId = feeCustId;
    }

    public String getConsFeeCustId() {
        return consFeeCustId;
    }

    public void setConsFeeCustId(String consFeeCustId) {
        this.consFeeCustId = consFeeCustId;
    }

    public String getCompCustId() {
        return compCustId;
    }

    public void setCompCustId(String compCustId) {
        this.compCustId = compCustId;
    }

    /** 
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月16日上午10:00:23
     */
    public String getFxAccount() {
        // TODO Auto-generated method stub
        return fxAccount;
    }

    /**
     * @param fxAccount the fxAccount to set
     */
    public void setFxAccount(String fxAccount) {
        this.fxAccount = fxAccount;
    }

}
