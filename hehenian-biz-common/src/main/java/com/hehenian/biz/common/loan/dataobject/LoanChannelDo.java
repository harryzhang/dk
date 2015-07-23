/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.common.loan.dataobject;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class LoanChannelDo  implements java.io.Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1401746338721098879L;
	
	//columns START
	private java.lang.Long id;
	private java.lang.Integer channelType;
	private java.lang.String sourceUserId;
	private java.lang.Long loanUserId;
	
	private LoanUserDo loanUserDo;
	//columns END
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Integer getChannelType() {
		return this.channelType;
	}
	
	public void setChannelType(java.lang.Integer value) {
		this.channelType = value;
	}
	public java.lang.String getSourceUserId() {
		return this.sourceUserId;
	}
	
	public void setSourceUserId(java.lang.String value) {
		this.sourceUserId = value;
	}
	public java.lang.Long getLoanUserId() {
		return this.loanUserId;
	}
	
	public void setLoanUserId(java.lang.Long value) {
		this.loanUserId = value;
	}

	public LoanUserDo getLoanUserDo() {
		return loanUserDo;
	}

	public void setLoanUserDo(LoanUserDo loanUserDo) {
		this.loanUserDo = loanUserDo;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ChannelType",getChannelType())
			.append("SourceUserId",getSourceUserId())
			.append("LoanUserId",getLoanUserId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanChannelDo == false) return false;
		if(this == obj) return true;
		LoanChannelDo other = (LoanChannelDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

