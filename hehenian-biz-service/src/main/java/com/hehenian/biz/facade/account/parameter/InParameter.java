package com.hehenian.biz.facade.account.parameter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 输入数据参数
 * 
 * @author liuzgmf
 *
 */
public class InParameter implements Serializable {
	private static final long serialVersionUID = 1L;
	private String version;// 版本号
	private String ordId;// 订单号（商户生成）
	private String trxId;// 交易编号（银行或第三方生成）
	private String retUrl;// 页面返回URL
	private String bgRetUrl;// 商户后台应答地址
	private Map<String, Object> params = new HashMap<String, Object>(); // 输入参数

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
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

}
