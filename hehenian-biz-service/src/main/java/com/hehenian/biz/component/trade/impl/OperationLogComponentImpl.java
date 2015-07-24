package com.hehenian.biz.component.trade.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.trade.dataobject.OperationLogDo;
import com.hehenian.biz.component.trade.IOperationLogComponent;
import com.hehenian.biz.dal.trade.IOperationLogDao;

@Component("operationLogComponent")
public class OperationLogComponentImpl implements IOperationLogComponent {
	@Autowired
	private IOperationLogDao operationLogDao;

	@Override
	public Integer addOperationLog(String operationTable, String operationUser,
			Integer operationType, String operationIp, Double operationMoney,
			String operationRemarks, Integer operationAround) {
		OperationLogDo operationLogDo = new OperationLogDo();
		operationLogDo.setOperationTable(operationTable);
		operationLogDo.setOperationUser(operationUser);
		operationLogDo.setOperationType(operationType);// 新增
		operationLogDo.setOperationIp(operationIp);
		operationLogDo.setOperationTime(new Date());
		operationLogDo.setOperationMoney(operationMoney);
		operationLogDo.setOperationRemarks(operationRemarks);
		operationLogDo.setOperationAround(operationAround);// 前台
		return operationLogDao.addOperationLog(operationLogDo);
	}

}
