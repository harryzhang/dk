/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.common.loan.dataobject;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class LoanPersonCreditDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long creditId;
	private java.lang.Long userId;
	private java.lang.Long creditAmt;
	private java.lang.String status;
	private java.lang.String remark;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	private Long loanId; //订单ID
	
	private List<LoanCreditRecDo> creditRecordList = new ArrayList<LoanCreditRecDo>();
	//columns END
	
	public void addCreditRecord(LoanCreditRecDo creditRecord){
		creditRecordList.add(creditRecord);
		creditRecord.setCreditId(this.creditId);
	}
	
	public Long sumCreditAmt(){
		long result = 0l;
		if(this.creditRecordList == null ){
			result =  0l;
		}else{
			for(LoanCreditRecDo creditRecord : creditRecordList ){
				long itemAmt = creditRecord.getCreditAmt() == null ? 0 : creditRecord.getCreditAmt() ;
				result += itemAmt;
			}
		}
		return result;
	}
	
	public java.lang.Long getCreditId() {
		return this.creditId;
	}
	
	public void setCreditId(java.lang.Long value) {
		this.creditId = value;
	}
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	public java.lang.Long getCreditAmt() {
		return this.creditAmt;
	}
	
	public void setCreditAmt(java.lang.Long value) {
		this.creditAmt = value;
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}

	public List<LoanCreditRecDo> getCreditRecordList() {
		return creditRecordList;
	}

	public void setCreditRecordList(List<LoanCreditRecDo> creditRecordList) {
		this.creditRecordList = creditRecordList;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("CreditId",getCreditId())
			.append("UserId",getUserId())
			.append("CreditAmt",getCreditAmt())
			.append("Status",getStatus())
			.append("Remark",getRemark())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCreditId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanPersonCreditDo == false) return false;
		if(this == obj) return true;
		LoanPersonCreditDo other = (LoanPersonCreditDo)obj;
		return new EqualsBuilder()
			.append(getCreditId(),other.getCreditId())
			.isEquals();
	}
}

