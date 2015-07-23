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

public class GuaranteeInstitutionsDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.String organName;
	private Double organMoney;
	private java.lang.Integer organNameber;
	private java.lang.Integer userId;
	//columns END
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.String getOrganName() {
		return this.organName;
	}
	
	public void setOrganName(java.lang.String value) {
		this.organName = value;
	}
	public Double getOrganMoney() {
		return this.organMoney;
	}
	
	public void setOrganMoney(Double value) {
		this.organMoney = value;
	}
	public java.lang.Integer getOrganNameber() {
		return this.organNameber;
	}
	
	public void setOrganNameber(java.lang.Integer value) {
		this.organNameber = value;
	}
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("OrganName",getOrganName())
			.append("OrganMoney",getOrganMoney())
			.append("OrganNameber",getOrganNameber())
			.append("UserId",getUserId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof GuaranteeInstitutionsDo == false) return false;
		if(this == obj) return true;
		GuaranteeInstitutionsDo other = (GuaranteeInstitutionsDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}
