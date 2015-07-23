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



public class LoanCheckDetailDo  implements java.io.Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//columns START
	private java.lang.Long checkId;
	private java.lang.Long itemCheckId;
	private java.lang.Integer valType;
	private java.lang.Integer checkVal;
	private java.lang.Double startItemVal;
	private java.lang.Double endItemVal;
	private java.lang.Double fixItemVal;
	private java.lang.Double coefficient;
	private java.lang.String remark;
	//columns END
	
	/*liminglong add  for select */
	private String checkItemName;
	private String status;
	
	public java.lang.Long getCheckId() {
		return this.checkId;
	}
	
	public void setCheckId(java.lang.Long value) {
		this.checkId = value;
	}
	public java.lang.Long getItemCheckId() {
		return this.itemCheckId;
	}
	
	public void setItemCheckId(java.lang.Long value) {
		this.itemCheckId = value;
	}
	public java.lang.Integer getValType() {
		return this.valType;
	}
	
	public void setValType(java.lang.Integer value) {
		this.valType = value;
	}
	public java.lang.Integer getCheckVal() {
		return this.checkVal;
	}
	
	public void setCheckVal(java.lang.Integer value) {
		this.checkVal = value;
	}
	public java.lang.Double getStartItemVal() {
		return this.startItemVal;
	}
	
	public void setStartItemVal(java.lang.Double value) {
		this.startItemVal = value;
	}
	public java.lang.Double getEndItemVal() {
		return this.endItemVal;
	}
	
	public void setEndItemVal(java.lang.Double value) {
		this.endItemVal = value;
	}
	public java.lang.Double getFixItemVal() {
		return this.fixItemVal;
	}
	
	public void setFixItemVal(java.lang.Double value) {
		this.fixItemVal = value;
	}
	public java.lang.Double getCoefficient() {
		return this.coefficient;
	}
	
	public void setCoefficient(java.lang.Double value) {
		this.coefficient = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}

	/**
	 * @return the checkItemName
	 */
	public String getCheckItemName() {
		return checkItemName;
	}

	/**
	 * @param checkItemName the checkItemName to set
	 */
	public void setCheckItemName(String checkItemName) {
		this.checkItemName = checkItemName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}

