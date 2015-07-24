/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: ReconciliationComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:08:17
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.trade.dataobject.ReconciliationDo;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationStatus;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationType;
import com.hehenian.biz.component.trade.IReconciliationComponent;
import com.hehenian.biz.dal.trade.IReconciliationDao;

/**
 * 
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:08:17
 */
@Component("reconciliationComponent")
public class ReconciliationComponentImpl implements IReconciliationComponent {
    @Autowired
    private IReconciliationDao reconciliationDao;

    @Override
    public int addReconciliation(List<ReconciliationDo> reconciliationDoList) {
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            ReconciliationDo localReconciliationDo = reconciliationDao.getByOrdIdAndReconciliationType(
                    reconciliationDo.getOrdId(), reconciliationDo.getReconciliationType());
            // 如果对账记录存在，并且汇付交易状态为成功，并且对账为成功，则直接跳过
            if (localReconciliationDo != null && localReconciliationDo.getTransStat().equals("S")
                    && localReconciliationDo.getReconciliationStatus().equals(ReconciliationStatus.SUCCESS)) {
                continue;
            }
            if (localReconciliationDo != null) {
                reconciliationDo.setReconciliationId(localReconciliationDo.getReconciliationId());
                reconciliationDao.updateReconciliation(reconciliationDo);
            } else {
                reconciliationDao.addReconciliation(reconciliationDo);
            }
        }
        return reconciliationDoList.size();
    }

    @Override
    public int countReconciliations(ReconciliationType reconciliationType, ReconciliationStatus reconciliationStatus) {
        return reconciliationDao.countReconciliations(reconciliationType, reconciliationStatus);
    }

    @Override
    public List<ReconciliationDo> queryReconciliations(ReconciliationType reconciliationType,
            ReconciliationStatus reconciliationStatus, int count) {
        return reconciliationDao.queryReconciliations(reconciliationType, reconciliationStatus, count);
    }

    @Override
    public int countReconciliations(ReconciliationType reconciliationType, Date beginDate, Date endDate) {
        return reconciliationDao.countReconciliations2(reconciliationType, beginDate, endDate);
    }

    @Override
    public List<ReconciliationDo> queryReconciliations(ReconciliationType reconciliationType, Date beginDate,
            Date endDate, int count) {
        return reconciliationDao.queryReconciliations2(reconciliationType, beginDate, endDate, count);
    }

    @Override
    public int updateReconciliationStatus(List<ReconciliationDo> reconciliationDoList) {
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            reconciliationDao.updateReconciliationStatus(reconciliationDo);
        }
        return reconciliationDoList.size();
    }

    @Override
    public Date getReconciliationBeginDate(ReconciliationType reconciliationType) {
        Date beginDate = reconciliationDao.getMaxPnrDate(reconciliationType);
        if (beginDate == null) {
            beginDate = reconciliationDao.getMaxOrdDate(reconciliationType);
        }
        if (beginDate != null && beginDate.after(new Date())) {
            beginDate = DateUtils.addDays(new Date(), -1);
        }
        if (beginDate == null) {
            try {
                beginDate = DateUtils.parseDate("20131001", new String[] { "yyyyMMdd" });
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return beginDate;
    }

    @Override
    public List<ReconciliationDo> getReconciliations(Map<String, Object> searchItems) {
        return reconciliationDao.getReconciliations(searchItems);
    }

    @Override
    public long countRecon(Map<String, Object> searchItems) {
        return reconciliationDao.countRecon(searchItems);
    }

}
