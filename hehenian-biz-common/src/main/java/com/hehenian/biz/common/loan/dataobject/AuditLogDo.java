package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.hehenian.biz.common.util.StringUtil;

public class AuditLogDo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long auidtId;			//主键ID
	
	private Long loanId;			//借款ID
	
	private String auditUser;       //审核人
	 
	private String auditUserId;       //审核人ID
	
	private Date auditTime;		    //审核时间
	
	private String preStatus;       //审核前状态  
	
	private String afterStatus;		//审核后状态
	
	private String reason;			//原因
	
	private String remark;			//备注

	public Long getAuidtId() {
		return auidtId;
	}

	public void setAuidtId(Long auidtId) {
		this.auidtId = auidtId;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}


	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}

	public String getAfterStatus() {
		return afterStatus;
	}

	public void setAfterStatus(String afterStatus) {
		this.afterStatus = afterStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = StringUtil.filterDangerString(reason);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = StringUtil.filterDangerString(remark);
	}
	
	
}
