/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade.impl
 * @Title: OperationLogServiceImpl.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月11日 下午12:57:21
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.trade.IOperationLogService;
import com.hehenian.biz.component.trade.IOperationLogComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月11日 下午12:57:21
 */
@Component("operationLogServiceImpl")
public class OperationLogServiceImpl implements IOperationLogService  {
    
    
    @Autowired    
    private IOperationLogComponent operationLogComponent;

    /* (no-Javadoc) 
     * <p>Title: addOperationLog</p> 
     * <p>Description: </p> 
     * @param operationTable
     * @param operationUser
     * @param operationType
     * @param operationIp
     * @param operationMoney
     * @param operationRemarks
     * @param operationAround
     * @return 
     * @see com.hehenian.biz.common.trade.IOperationLogService#addOperationLog(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Double, java.lang.String, java.lang.Integer) 
     */
    @Override
    public Integer addOperationLog(String operationTable, String operationUser, Integer operationType,
            String operationIp, Double operationMoney, String operationRemarks, Integer operationAround) {
        return operationLogComponent.addOperationLog(operationTable, operationUser, operationType, operationIp, operationMoney, operationRemarks, operationAround);
    }

}
