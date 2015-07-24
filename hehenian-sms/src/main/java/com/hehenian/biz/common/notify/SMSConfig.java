/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.excel.util
 * @Title: DirConfig.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年12月22日 下午3:13:42
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.notify;

/**
 * 
 * @author: zhangyunhmf
 * @date 2014年12月22日 下午3:13:42
 */
public class SMSConfig {

    /**
     * 短信有效时长单位分钟 hehenian.sms.validate.time=30
     */
    private String smsValidateTime;

    private String dqlcServiceUrl;
    // 设置可绑卡的总数（发送成功2次算一种卡，或绑定成功算一张卡）
    private String sendBankCardCount;
    // 每张卡的发送次数
    private String sendPerCardCount;
    // 验证金额的时候重试次数
    private String tryCountWhenCheck;

    /**
     * @return smsValidateTime
     */
    public String getSmsValidateTime() {
        return smsValidateTime;
    }

    /**
     * @param smsValidateTime
     *            the smsValidateTime to set
     */
    public void setSmsValidateTime(String smsValidateTime) {
        this.smsValidateTime = smsValidateTime;
    }

    /**
     * @return dqlcServiceUrl
     */
    public String getDqlcServiceUrl() {
        return dqlcServiceUrl;
    }

    /**
     * @param dqlcServiceUrl
     *            the dqlcServiceUrl to set
     */
    public void setDqlcServiceUrl(String dqlcServiceUrl) {
        this.dqlcServiceUrl = dqlcServiceUrl;
    }

    /**
     * @return sendBankCardCount
     */
    public String getSendBankCardCount() {
        return sendBankCardCount;
    }

    /**
     * @param sendBankCardCount
     *            the sendBankCardCount to set
     */
    public void setSendBankCardCount(String sendBankCardCount) {
        this.sendBankCardCount = sendBankCardCount;
    }

    /**
     * @return sendPerCardCount
     */
    public String getSendPerCardCount() {
        return sendPerCardCount;
    }

    /**
     * @param sendPerCardCount
     *            the sendPerCardCount to set
     */
    public void setSendPerCardCount(String sendPerCardCount) {
        this.sendPerCardCount = sendPerCardCount;
    }

    /**
     * @return tryCountWhenCheck
     */
    public String getTryCountWhenCheck() {
        return tryCountWhenCheck;
    }

    /**
     * @param tryCountWhenCheck
     *            the tryCountWhenCheck to set
     */
    public void setTryCountWhenCheck(String tryCountWhenCheck) {
        this.tryCountWhenCheck = tryCountWhenCheck;
    }


}
