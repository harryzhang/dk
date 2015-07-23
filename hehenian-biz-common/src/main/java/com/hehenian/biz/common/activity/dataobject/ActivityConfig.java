/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.facade.colorlife
 * @Title: ColorLifeUtil.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月30日 下午12:39:00
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.activity.dataobject;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月30日 下午12:39:00
 */
public class ActivityConfig {
	// 彩生活的地址
    private String colorServiceURL;
	// 我们内部服务地址
    private String hehenianServiceURL;
	// 订单成功的回调地址
	private String orderSuccessURL;
	// 扣费成功的回调地址
	private String paySyntonyURL;
	//彩色生加签密匙
	private String colorSignSecret;
	//彩生活分配给的KEY
	private String colorKey;
	//hehenian 分配给彩色活的KEY
	private String hehenianKey;
	
    // 扣款冻结天数
    private String freezeDays;

    /** 
     * @return colorServiceURL 
     */
    public String getColorServiceURL() {
        return colorServiceURL;
    }

    /**
     * @param colorServiceURL the colorServiceURL to set
     */
    public void setColorServiceURL(String colorServiceURL) {
        this.colorServiceURL = colorServiceURL;
    }

	public String getHehenianServiceURL() {
		return hehenianServiceURL;
	}

    /**
     * @return freezeDays
     */
    public String getFreezeDays() {
        return freezeDays;
    }

    /**
     * @param freezeDays
     *            the freezeDays to set
     */
    public void setFreezeDays(String freezeDays) {
        this.freezeDays = freezeDays;
    }

    public void setHehenianServiceURL(String hehenianServiceURL) {
		this.hehenianServiceURL = hehenianServiceURL;
	}

	/**
	 * @return
	 */
	public String getOrderSuccessURL() {
		return this.orderSuccessURL;
	}

	/**
	 * @param orderSuccessURL
	 *            the orderSuccessURL to set
	 */
	public void setOrderSuccessURL(String orderSuccessURL) {
		this.orderSuccessURL = orderSuccessURL;
	}

	/**
	 * @return the paySyntonyURL
	 */
	public String getPaySyntonyURL() {
		return paySyntonyURL;
	}

	/**
	 * @param paySyntonyURL
	 *            the paySyntonyURL to set
	 */
	public void setPaySyntonyURL(String paySyntonyURL) {
		this.paySyntonyURL = paySyntonyURL;
	}

	/**
	 * @return the colorSignSecret
	 */
	public String getColorSignSecret() {
		return colorSignSecret;
	}

	/**
	 * @param colorSignSecret
	 *            the colorSignSecret to set
	 */
	public void setColorSignSecret(String colorSignSecret) {
		this.colorSignSecret = colorSignSecret;
	}

	/**
	 * @return the colorKey
	 */
	public String getColorKey() {
		return colorKey;
	}

	/**
	 * @param colorKey
	 *            the colorKey to set
	 */
	public void setColorKey(String colorKey) {
		this.colorKey = colorKey;
	}

	/**
	 * @return the hehenianKey
	 */
	public String getHehenianKey() {
		return hehenianKey;
	}

	/**
	 * @param hehenianKey
	 *            the hehenianKey to set
	 */
	public void setHehenianKey(String hehenianKey) {
		this.hehenianKey = hehenianKey;
	}
}
