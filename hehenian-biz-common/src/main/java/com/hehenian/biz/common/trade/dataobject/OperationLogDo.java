package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 * 
 * @author liuzgmf
 *
 */
public class OperationLogDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String operationTable;// 操作表
	private String operationUser;// 操作人
	private Integer operationType;// 1 增加 2 修改 3 删除 4 下载 5 excel
	private String operationIp;// 操作人IP
	private Date operationTime;// 操作时间
	private Double operationMoney;// 操作金额
	private String operationRemarks;// 操作备注
	private Integer operationAround;// 1 前台 2 后台

	public String getOperationTable() {
		return operationTable;
	}

	public void setOperationTable(String operationTable) {
		this.operationTable = operationTable;
	}

	public String getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public String getOperationIp() {
		return operationIp;
	}

	public void setOperationIp(String operationIp) {
		this.operationIp = operationIp;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public Double getOperationMoney() {
		return operationMoney;
	}

	public void setOperationMoney(Double operationMoney) {
		this.operationMoney = operationMoney;
	}

	public String getOperationRemarks() {
		return operationRemarks;
	}

	public void setOperationRemarks(String operationRemarks) {
		this.operationRemarks = operationRemarks;
	}

	public Integer getOperationAround() {
		return operationAround;
	}

	public void setOperationAround(Integer operationAround) {
		this.operationAround = operationAround;
	}

}
