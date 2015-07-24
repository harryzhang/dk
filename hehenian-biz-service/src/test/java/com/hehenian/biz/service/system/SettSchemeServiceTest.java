/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.system
 * @Title: SettSchemeServiceTest.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月7日 下午3:59:58
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.system;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.system.ISettSchemeService;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.service.BaseTestCase;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月7日 下午3:59:58
 */
public class SettSchemeServiceTest extends BaseTestCase {
    @Autowired
    private ISettSchemeService settSchemeService;

    @Test
    public void testGetBySchemeId() {
        SettSchemeDo settSchemeDo = settSchemeService.getBySchemeId(1l);
        Assert.assertTrue(settSchemeDo.getSchemeId() > 0);
    }

    public void testGetBySchemeCode() {
        SettSchemeDo settSchemeDo = settSchemeService.getBySchemeCode("JTD");
        Assert.assertTrue(settSchemeDo.getSchemeId() > 0);
    }

    public void testGetBySchemeIdAndRuleType() {
        FeeRuleDo feeRuleDo = settSchemeService.getBySchemeIdAndRuleType(1l, RuleType.SERV_FEE);
        Assert.assertTrue(feeRuleDo.getRuleId() > 0);
    }

}
