package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;

/**
 * 贷款 日志
 * @author zhengyfmf
 *
 */
public class LoanLogDo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id ;
	
	private Integer logType ; // 日志类型：1-上标，2-生成还款计划表

	private Long loanId ;
	
	private String orderCode;
	
	private Integer logStatus ; // 状态：0-成功，1-失败

	private String logMsg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

	public Integer getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	
	
}
