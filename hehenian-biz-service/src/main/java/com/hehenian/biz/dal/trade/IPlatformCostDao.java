/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.trade
 * @Title: IPlatformCostDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月25日 上午11:42:35
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.trade;

import com.hehenian.biz.common.trade.dataobject.PlatformCostDo;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月25日 上午11:42:35
 */
public interface IPlatformCostDao {

    /**
     * 根据费用ID查询平台费用信息
     * 
     * @param id
     * @return
     * @author: liuzgmf
     * @date: 2014年9月25日下午2:08:59
     */
    PlatformCostDo getById(long id);

}
