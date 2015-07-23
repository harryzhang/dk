/**  
 * @Project: common
 * @Package com.hhn.pojo
 * @Title: FundTradeAgreement.java
 * @Description: 投资交易协议
 *
 * @author: zhanbmf
 * @date 2015-3-22 下午5:38:23
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.trade.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 投资交易协议
 * @author zhanbmf
 *
 */
public class FundTradeAgreement implements java.io.Serializable {

	/**
	 * 标识
	 */
	private Integer id;
	
	/**
	 * hhn会员ID
	 */
	private Long userId;
	
	/**
	 * 协议类型 协议类型0-INVESTMENT-1-RECHARGE
	 */
	private Integer agreementType;
	
	/**
	 * 投资交易标识
	 */
	private Integer tradeId;
	
	/**
	 * 协议名称
	 */
	private String agreementFileName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(Integer agreementType) {
		this.agreementType = agreementType;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	public String getAgreementFileName() {
		return agreementFileName;
	}

	public void setAgreementFileName(String agreementFileName) {
		this.agreementFileName = agreementFileName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		.append("id", getId())
		.append("userId", getUserId())
		.append("agreementType", getAgreementType())
		.append("tradeId", getTradeId())
		.append("agreementFileName", getAgreementFileName())
		.append("createTime", getCreateTime()).toString();
		
	}

}
