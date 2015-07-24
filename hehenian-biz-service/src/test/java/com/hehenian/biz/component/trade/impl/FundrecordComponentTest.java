/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: FundrecordComponentTest.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月15日 下午5:12:27
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.service.BaseTestCase;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月15日 下午5:12:27
 */
public class FundrecordComponentTest extends BaseTestCase {
    @Autowired
    private IFundrecordComponent fundrecordComponent;

    @Test
    public void testGetAutoIncrementId() {
        Long id = fundrecordComponent.getAutoIncrementId();
        Assert.assertTrue(id.longValue() > 0);
        System.out.println(id);
    }
}
