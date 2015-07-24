/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade
 * @Title: IInvestRepaymentServiceTest.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月23日 下午4:09:23
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.trade;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Assert;

import com.hehenian.biz.common.trade.IInvestRepaymentService;
import com.hehenian.biz.service.BaseTestCase;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月23日 下午4:09:23
 */
public class IInvestRepaymentServiceTest extends BaseTestCase {
    @Autowired
    private IInvestRepaymentService investRepaymentService;

    @Test
    public void testGetDailyIncome() {
        Double dailyIncome = investRepaymentService.getDailyIncome(100004l);
        Assert.assertEquals(0, dailyIncome);//27.78
    }
}
