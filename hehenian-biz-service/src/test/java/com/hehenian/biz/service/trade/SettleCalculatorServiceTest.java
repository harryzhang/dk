/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade
 * @Title: SettleCalculatorServiceTest.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月12日 下午5:09:59
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.trade;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.trade.ISettleCalculatorService;
import com.hehenian.biz.service.BaseTestCase;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月12日 下午5:09:59
 */
public class SettleCalculatorServiceTest extends BaseTestCase {
    @Autowired
    private ISettleCalculatorService settleCalculatorService;

    @Test
    public void testCalSettDetail() {
        List<SettDetailDo> settDetailDoList = settleCalculatorService.calSettDetail(60000d, 7.8d, 6, 2l);
        for (SettDetailDo settDetailDo : settDetailDoList) {
            System.out.println(settDetailDo);
        }
    }
}
