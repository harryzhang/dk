package com.hehenian.biz.common.trade.dataobject;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class RiskDetailDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private Double riskInCome;
	private Double riskSpending;
	private java.util.Date riskDate;
	private Double riskBalance;
	private java.lang.String riskType;
	private java.lang.String resource;
	private java.lang.Long trader;
	private java.lang.Long borrowId;
	private java.lang.String remark;
	private java.lang.Long operator;
	//columns END
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public Double getRiskInCome() {
		return this.riskInCome;
	}
	
	public void setRiskInCome(Double value) {
		this.riskInCome = value;
	}
	public Double getRiskSpending() {
		return this.riskSpending;
	}
	
	public void setRiskSpending(Double value) {
		this.riskSpending = value;
	}
	public java.util.Date getRiskDate() {
		return this.riskDate;
	}
	
	public void setRiskDate(java.util.Date value) {
		this.riskDate = value;
	}
	public Double getRiskBalance() {
		return this.riskBalance;
	}
	
	public void setRiskBalance(Double value) {
		this.riskBalance = value;
	}
	public java.lang.String getRiskType() {
		return this.riskType;
	}
	
	public void setRiskType(java.lang.String value) {
		this.riskType = value;
	}
	public java.lang.String getResource() {
		return this.resource;
	}
	
	public void setResource(java.lang.String value) {
		this.resource = value;
	}
	public java.lang.Long getTrader() {
		return this.trader;
	}
	
	public void setTrader(java.lang.Long value) {
		this.trader = value;
	}
	public java.lang.Long getBorrowId() {
		return this.borrowId;
	}
	
	public void setBorrowId(java.lang.Long value) {
		this.borrowId = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public java.lang.Long getOperator() {
		return this.operator;
	}
	
	public void setOperator(java.lang.Long value) {
		this.operator = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RiskInCome",getRiskInCome())
			.append("RiskSpending",getRiskSpending())
			.append("RiskDate",getRiskDate())
			.append("RiskBalance",getRiskBalance())
			.append("RiskType",getRiskType())
			.append("Resource",getResource())
			.append("Trader",getTrader())
			.append("BorrowId",getBorrowId())
			.append("Remark",getRemark())
			.append("Operator",getOperator())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RiskDetailDo == false) return false;
		if(this == obj) return true;
		RiskDetailDo other = (RiskDetailDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

