/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.task
 * @Title: XiaoDaiBalanceTaskTest.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月17日 下午4:33:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.task;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.service.BaseTestCase;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月17日 下午4:33:53
 */
public class XiaoDaiBalanceTaskTest extends BaseTestCase {
    
    @Autowired
    private ITask xiaoDaiBalanceTask;
    
    @Test
    public void testExcuteJob(){
        xiaoDaiBalanceTask.execute();
    }

}
