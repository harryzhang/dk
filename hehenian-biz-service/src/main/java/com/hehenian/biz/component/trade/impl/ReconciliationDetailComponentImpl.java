/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: ReconciliationDetailComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月9日 上午11:39:36
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.trade.dataobject.ReconciliationDetailDo;
import com.hehenian.biz.component.system.IReconciliationDetailComponent;
import com.hehenian.biz.dal.trade.IReconciliationDetailDao;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月9日 上午11:39:36
 */
@Component("reconciliationDetailComponent")
public class ReconciliationDetailComponentImpl implements IReconciliationDetailComponent {
    @Autowired
    private IReconciliationDetailDao reconciliationDetailDao;

    @Override
    public int addReconciliationDetails(List<ReconciliationDetailDo> detailDoList) {
        if (detailDoList == null || detailDoList.size() == 0) {
            return 0;
        }
        for (ReconciliationDetailDo reconciliationDetailDo : detailDoList) {
            reconciliationDetailDao.addReconciliationDetails(reconciliationDetailDo);
        }
        return detailDoList.size();
    }

}
