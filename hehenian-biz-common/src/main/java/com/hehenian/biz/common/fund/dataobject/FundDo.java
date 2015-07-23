/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.fund.dataobject;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class FundDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.String fundCode;
	private java.lang.String fundName;
	private java.lang.String remark;
	private java.lang.String validate;
	//columns END
	public java.lang.String getFundCode() {
		return this.fundCode;
	}
	
	public void setFundCode(java.lang.String value) {
		this.fundCode = value;
	}
	public java.lang.String getFundName() {
		return this.fundName;
	}
	
	public void setFundName(java.lang.String value) {
		this.fundName = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public java.lang.String getValidate() {
		return this.validate;
	}
	
	public void setValidate(java.lang.String value) {
		this.validate = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("FundCode",getFundCode())
			.append("FundName",getFundName())
			.append("Remark",getRemark())
			.append("Validate",getValidate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getFundCode())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof FundDo == false) return false;
		if(this == obj) return true;
		FundDo other = (FundDo)obj;
		return new EqualsBuilder()
			.append(getFundCode(),other.getFundCode())
			.isEquals();
	}
}

