/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade
 * @Title: IOperationLog.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月11日 下午12:54:12
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月11日 下午12:54:12
 */
public interface IOperationLogService {
    
    
    /**
     * 新增操作日志记录
     * 
     * @param operationTable
     *            操作表
     * @param operationUser
     *            操作人
     * @param operationType
     *            操作类型 1 增加 2 修改 3 删除 4 下载 5 excel
     * @param operationIp
     *            操作人IP
     * @param operationMoney
     *            操作金额
     * @param operationRemarks
     *            操作备注
     * @param operationAround
     *            1 前台 2 后台
     * @return
     */
    Integer addOperationLog(String operationTable, String operationUser, Integer operationType,
            String operationIp, Double operationMoney, String operationRemarks,
            Integer operationAround);

}
