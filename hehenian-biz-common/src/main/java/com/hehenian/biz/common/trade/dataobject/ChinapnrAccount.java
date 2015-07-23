/**
 * 
 */
package com.hehenian.biz.common.trade.dataobject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 汇付账户
 * 
 * @author zhangyunhmf
 * 
 */
public class ChinapnrAccount implements java.io.Serializable {
	/**
	 * 汇付子账户
	 */
	private String accountId;
	/**
	 * 汇付账户
	 */
	private String custId;

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId
	 *            the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("custId", getCustId())
				.append("accountId", getAccountId()).toString();
	}

}
