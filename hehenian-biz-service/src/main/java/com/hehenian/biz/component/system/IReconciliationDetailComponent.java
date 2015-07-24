/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system
 * @Title: IReconciliationDetailComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月8日 下午2:50:50
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.system;

import java.util.List;

import com.hehenian.biz.common.trade.dataobject.ReconciliationDetailDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月8日 下午2:50:50
 */
public interface IReconciliationDetailComponent {

    /**
     * 新增对账明细信息
     * 
     * @param detailDoList
     * @author: liuzgmf
     * @date: 2015年1月8日下午3:00:26
     */
    int addReconciliationDetails(List<ReconciliationDetailDo> detailDoList);

}
