/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.facade.account
 * @Title: AccountManagerServiceTest.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月13日 上午9:42:34
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.facade.account;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;
import com.hehenian.biz.service.BaseTestCase;

/**
 * 
 * @author: liuzgmf
 * @date 2014年10月13日 上午9:42:34
 */
public class AccountManagerServiceTest extends BaseTestCase {
    @Autowired
    private IAccountManagerService accountManagerService;

    // @Test
    public void testTrfReconciliation() {
        InParameter inParameter = new InParameter();
        inParameter.getParams().put("BeginDate", "20141001");
        inParameter.getParams().put("EndDate", "20141013");
        inParameter.getParams().put("PageNum", 1);
        inParameter.getParams().put("PageSize", 1000);

        OutParameter outParameter = accountManagerService.trfReconciliation(inParameter, AccountType.CHINAPNR);
        System.out.println(outParameter.getParams());
    }

    // @Test
    public void testDirecTrfAuth() {
        InParameter inParameter = new InParameter();
        inParameter.getParams().put("UsrCustId", "6000060000362628");
        inParameter.getParams().put("InUsrCustId", "6000060000294550");
        inParameter.getParams().put("AuthAmt", 10000.00);
        OutParameter outParameter = accountManagerService.direcTrfAuth(inParameter, AccountType.CHINAPNR);
        Assert.assertTrue(outParameter.isSuccess());
    }

    // @Test
    public void testDirecTrf() {
        InParameter inParameter = new InParameter();
        inParameter.setOrdId(100002 + "");
        inParameter.getParams().put("UsrCustId", "6000060000362628");
        inParameter.getParams().put("InUsrCustId", "6000060000294550");
        inParameter.getParams().put("TransAmt", 10.00);
        OutParameter outParameter = accountManagerService.direcTrf(inParameter, AccountType.CHINAPNR);
        Assert.assertTrue(outParameter.isSuccess());
    }

    // @Test
    public void testQueryBalanceBg() {
        InParameter inParameter = new InParameter();
        inParameter.getParams().put("UsrCustId", "6000060000395781");
        OutParameter outParameter = accountManagerService.queryBalanceBg(inParameter, AccountType.CHINAPNR);
        Assert.assertTrue(outParameter.isSuccess());
    }

    @Test
    public void testTransfer() {
        InParameter inParameter = new InParameter();
        inParameter.setOrdId("1003");
        inParameter.getParams().put("OutCustId", "6000060000146587");
        inParameter.getParams().put("OutAcctId", "MDT000001");
        inParameter.getParams().put("TransAmt", 1.00);
        inParameter.getParams().put("InCustId", "6000060000395781");
        inParameter.getParams().put("InAcctId", "");
        OutParameter outParameter = accountManagerService.transfer(inParameter, AccountType.CHINAPNR);
        Assert.assertTrue(outParameter.isSuccess());
    }
}
