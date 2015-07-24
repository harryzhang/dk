/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.loan
 * @Title: IPropertyDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:44:37
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.loan;

import com.hehenian.biz.common.loan.dataobject.PropertyDo;

/** 
 *  
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:44:37
 */
public interface IPropertyDao {

    /**
     * 修改个人资产信息
     * @param propertyDo
     */
    void updateProperty(PropertyDo propertyDo);

    /**
     * 新增个人资产信息
     * @param propertyDo
     */
    void addProperty(PropertyDo propertyDo);
    
    /**
     * 根据ID查询表的记录
     * @return
     */
    PropertyDo getPropertyByIds(Long id);
}
