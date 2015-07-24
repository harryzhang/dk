/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.trade
 * @Title: IReconciliationDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:08:40
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.trade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.ReconciliationDo;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationStatus;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationType;

/**
 * 
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:08:40
 */
public interface IReconciliationDao {

    /**
     * 根据订单号，对账类型查询对账信息
     * 
     * @param ordId
     * @param reconciliationType
     * @return
     * @author: liuzgmf
     * @date: 2014年11月24日上午11:26:01
     */
    ReconciliationDo getByOrdIdAndReconciliationType(@Param("ordId") Long ordId,
            @Param("reconciliationType") ReconciliationType reconciliationType);

    /**
     * 新增对账信息
     * 
     * @param reconciliationDo
     * @author: liuzgmf
     * @date: 2014年11月24日上午11:36:03
     */
    int addReconciliation(ReconciliationDo reconciliationDo);

    /**
     * 修改对账信息
     * 
     * @param reconciliationDo
     * @author: liuzgmf
     * @date: 2014年11月24日上午11:36:46
     */
    int updateReconciliation(ReconciliationDo reconciliationDo);

    /**
     * 根据对账类型，对账状态查询记录数
     * 
     * @param reconciliationType
     * @param reconciliationStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年11月24日下午3:14:19
     */
    int countReconciliations(@Param("reconciliationType") ReconciliationType reconciliationType,
            @Param("reconciliationStatus") ReconciliationStatus reconciliationStatus);

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
    List<ReconciliationDo> queryReconciliations(@Param("reconciliationType") ReconciliationType reconciliationType,
            @Param("reconciliationStatus") ReconciliationStatus reconciliationStatus, @Param("count") int count);

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
    int countReconciliations2(@Param("reconciliationType") ReconciliationType reconciliationType,
            @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

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
    List<ReconciliationDo> queryReconciliations2(@Param("reconciliationType") ReconciliationType reconciliationType,
            @Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("count") Integer count);

    /**
     * 修改对账记录的状态
     * 
     * @param reconciliationStatus
     * @param reconciliationId
     * @return
     * @author: liuzgmf
     * @date: 2014年11月24日下午3:25:09
     */
    int updateReconciliationStatus(ReconciliationDo reconciliationDo);

    /**
     * 获取最大汇付交易日期
     * 
     * @param reconciliationType
     * @return
     * @author: liuzgmf
     * @date: 2014年11月25日上午8:26:47
     */
    Date getMaxPnrDate(ReconciliationType reconciliationType);

    /**
     * 获取最大汇付订单日期
     * 
     * @param reconciliationType
     * @return
     * @author: liuzgmf
     * @date: 2014年11月25日上午8:27:11
     */
    Date getMaxOrdDate(ReconciliationType reconciliationType);

    /**
     * 查询对账信息
     * 
     * @param searchItems
     * @return
     * @author: xiexiangmf
     * @date: 2014年12月22日下午4:46:03
     */
    List<ReconciliationDo> getReconciliations(Map<String, Object> searchItems);

    /**
     * @param searchItems
     * @return
     * @author: xiexiangmf
     * @date: 2014年12月23日下午6:05:05
     */
    long countRecon(Map<String, Object> searchItems);
}
