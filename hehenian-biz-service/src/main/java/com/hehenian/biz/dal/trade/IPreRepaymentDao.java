/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.trade
 * @Title: IPreRepaymentDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月24日 上午10:59:54
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.trade;

import com.hehenian.biz.common.trade.dataobject.PreRepaymentDo;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月24日 上午10:59:54
 */
public interface IPreRepaymentDao {

    /**
     * 新增预还款记录
     * 
     * @param preRepaymentDo
     * @return
     * @author: liuzgmf
     * @date: 2014年9月24日下午2:07:14
     */
    int addPreRepayment(PreRepaymentDo preRepaymentDo);

}
