package com.hehenian.biz.component.loan;

import com.hehenian.biz.common.loan.dataobject.PropertyDo;

public interface ILoanPropertyComponent {

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
    PropertyDo getCountByIds(Long id);
}
