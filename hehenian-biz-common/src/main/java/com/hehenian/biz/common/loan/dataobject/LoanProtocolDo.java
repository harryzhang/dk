package com.hehenian.biz.common.loan.dataobject;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class LoanProtocolDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.Long loanId;
	private java.lang.String fileName;
	private java.lang.String filePath;
	private java.lang.String fileType;
	private java.lang.String proType;
	//columns END
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Long getLoanId() {
		return this.loanId;
	}
	
	public void setLoanId(java.lang.Long value) {
		this.loanId = value;
	}
	public java.lang.String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(java.lang.String value) {
		this.fileName = value;
	}
	public java.lang.String getFilePath() {
		return this.filePath;
	}
	
	public void setFilePath(java.lang.String value) {
		this.filePath = value;
	}
	public java.lang.String getFileType() {
		return this.fileType;
	}
	
	public void setFileType(java.lang.String value) {
		this.fileType = value;
	}
	public java.lang.String getProType() {
		return this.proType;
	}
	
	public void setProType(java.lang.String value) {
		this.proType = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("LoanId",getLoanId())
			.append("FileName",getFileName())
			.append("FilePath",getFilePath())
			.append("FileType",getFileType())
			.append("ProType",getProType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanProtocolDo == false) return false;
		if(this == obj) return true;
		LoanProtocolDo other = (LoanProtocolDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

