/**  
 * @Project: hehenian-sms
 * @Package com.hehenian.biz.common.identifycode
 * @Title: IIdentifyCodeServiceTest.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年12月31日 上午11:12:05
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.identifycode;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.bank.IBankBingService;
import com.hehenian.biz.component.bank.IBankBingComponent;
import com.hehenian.biz.service.BaseTestCase;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年12月31日 上午11:12:05
 */
public class IIdentifyCodeServiceTest extends BaseTestCase {

    @Autowired
    private IIdentifyCodeService identifyCodeService;

    @Autowired
    IBankBingService             bankBingService;

    @Autowired
    private IBankBingComponent   bankBingComponent;

    @Test
    public void testGenerateIdentifyCode() {
        String identifyCode = identifyCodeService.generateIdentifyCode();
        System.out.println(identifyCode);
    }


    @Test
    public void testSendIdentifyCode() {
        String identifyCode = identifyCodeService.sendIdentifyCode("13692177359");
        System.out.println(identifyCode);
    }

    @Test
    public void testCheckIdentifyCode() {
        boolean isSend = identifyCodeService.checkIdentifyCode("13692177359", "480623");
        System.out.println(isSend);
    }

    /**
     * 绑定银行卡 ，向银行卡随机充值
     * 
     * @param userId
     *            用户id
     * @param bankCode
     *            银行卡号
     * @param bankType
     *            银行标识，哪家银行
     * @return
     * @author: zhangyunhmf
     * @date: 2015年1月15日下午4:42:00
     */
    @Test
    public void testSendBankIdentifyCode() {
        long userId = 3345l;
        String bankCode = "622588121251757648";
        String bankType = "0105";
        System.out.println(identifyCodeService.sendBankIdentifyCode(userId, "测试试", bankCode, bankType));
    }

    /**
     * 
     * @param userId
     *            用户id
     * @param bankCode
     *            银行卡号
     * @param bankType
     *            银行标识，哪家银行
     * @param amount
     *            验证金额
     * @return
     * @author: zhangyunhmf
     * @date: 2015年1月15日下午4:52:14
     */
    @Test
    public void testCheckBankIdentifyCode() {
        long userId = 3345l;
        String bankCode = "622588121251757648";
        String bankType = "0105";
        String amount = "0.33";
        System.out.println(identifyCodeService.checkBankIdentifyCode(userId, bankCode, bankType, amount));
    }

    @Test
    public void testValidCard(){
        long userId = 8001246L;
        // List<BankBingDo> list = bankBingComponent.getByUserId(8001246L);
        System.out.println("==================================="
                + bankBingService.checkUserBankBingRecord("9999999999999999", userId));
    }

}
