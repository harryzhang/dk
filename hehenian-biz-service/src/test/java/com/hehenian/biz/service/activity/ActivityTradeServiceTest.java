/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.activity
 * @Title: ActivityTradeServiceTest.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月31日 下午5:29:00
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.activity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.hehenian.biz.common.activity.IActivityTradeService;
import com.hehenian.biz.service.BaseTestCase;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月31日 下午5:29:00
 */
public class ActivityTradeServiceTest extends BaseTestCase {
    @Autowired
    private IActivityTradeService activityTradeService;

    // @Test
    // @Rollback(false)
    public void testAddActivityTrades() {
        // simpleJdbcTemplate
        // .update("INSERT INTO t_activity_order(ordId,userId,ordNo,ordType,beginDate,endDate,deductAmount)VALUES(10000,10,'O1212',0,'20141101','20151101',2400);");
        // simpleJdbcTemplate
        // .update("INSERT INTO t_activity_auth(authId,ordId,fromUserId,toUserId,authAmount,authType,authStatus)VALUES(10000,10000,10,11,20000,0,1);");
        activityTradeService.addActivityTrades(7);
    }

    @Test
    public void testTransfer() {
        activityTradeService.transfer();
    }
}
