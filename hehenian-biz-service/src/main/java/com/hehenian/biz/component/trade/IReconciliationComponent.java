/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade
 * @Title: IReconciliationComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:08:06
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.trade.dataobject.ReconciliationDo;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationStatus;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationType;

/**
 * 
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:08:06
 */
public interface IReconciliationComponent {

    /**
     * 新增（修改）对账信息
     * 
     * @param reconciliationDoList
     * @author: liuzgmf
     * @date: 2014年11月24日上午11:08:55
     */
    int addReconciliation(List<ReconciliationDo> reconciliationDoList);

    /**
     * 根据对账类型，对账状态查询记录数
     * 
     * @param reconciliationType
     * @param reconciliationStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年11月24日下午3:14:19
     */
    int countReconciliations(ReconciliationType reconciliationType, ReconciliationStatus reconciliationStatus);

    /**
     * 根据对账类型，对账状态查询对账记录
     * 
     * @param cash
     * @param unreconciliation
     * @param count
     * @return
     * @author: liuzgmf
     * @date: 2014年11月24日下午3:16:04
     */
    List<ReconciliationDo> queryReconciliations(ReconciliationType reconciliationType,
            ReconciliationStatus reconciliationStatus, int count);

    /**
     * 修改对账记录的状态
     * 
     * @param reconciliationDoList
     * @author: liuzgmf
     * @date: 2014年11月24日下午3:16:43
     */
    int updateReconciliationStatus(List<ReconciliationDo> reconciliationDoList);

    /**
     * 获取对账开始日期
     * 
     * @param cash
     * @return
     * @author: liuzgmf
     * @date: 2014年11月25日上午8:20:10
     */
    Date getReconciliationBeginDate(ReconciliationType reconciliationType);

    /**
     * 查询对账信息
     * 
     * @param searchItems
     * @return
     * @author: xiexiangmf
     * @date: 2014年12月22日下午5:17:55
     */
    public List<ReconciliationDo> getReconciliations(Map<String, Object> searchItems);

    /**
     * @param searchItems
     * @return
     * @author: xiexiangmf
     * @date: 2014年12月23日下午5:59:32
     */
    long countRecon(Map<String, Object> searchItems);

    /**
     * 根据对账类型，交易日期查询对账记录数
     * 
     * @param reconciliationType
     * @param beginDate
     * @param endDate
     * @return
     * @author: liuzgmf
     * @date: 2015年1月8日下午2:54:42
     */
    int countReconciliations(ReconciliationType reconciliationType, Date beginDate, Date endDate);

    /**
     * 根据对账类型，交易日期查询对账信息
     * 
     * @param reconciliationType
     * @param beginDate
     * @param endDate
     * @param count
     * @return
     * @author: liuzgmf
     * @date: 2015年1月8日下午2:56:28
     */
    List<ReconciliationDo> queryReconciliations(ReconciliationType reconciliationType, Date beginDate, Date endDate,
            int count);

}
