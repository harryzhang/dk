/**
 * 
 */
package com.hehenian.biz.facade.colorlife;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.service.BaseTestCase;

/**
 * @author zhangyunhmf
 *
 */
public class ColorFeeFacadeImplTest extends BaseTestCase {
	@Autowired
	private IColorFeeFacade colorFeeFacade;
	@Autowired
	private IColorOrderFacade colorOrderFacade;

	@Test
	public void testCallColorLifeSendFee() throws Exception {
		System.out
				.println("start=============================================================");
		
		long colorUserId = 186489;
		String orderSN = "1040000141103142411620";
		double feeAmount = 10000d;
		Date feeTime = new Date();
		int month = 2;
		String status = "1";
		String remark = "afaafsfs是否";
        
		colorFeeFacade.sendFeeStatus(colorUserId, 
									 orderSN, 
									 feeAmount, 
									 feeTime,
				                     month, 
				                     status, 
				                     remark);

		Thread.sleep(10000);
		System.out
				.println("end==============================================================");
	}

	@Test
	public void testCallColorLifeOrdercallback() throws Exception {
		System.out
				.println("start=============================================================");

		// /1.0/activity/paySyntony?userId=169156&orderSN=1040000141028154610898&orderAmount=20000.00
		// &orderSuccessTime=1415169999&orderPaySn=10003&key=3&ts=1415170069&ve=1.0.0&sign=c3035c59d5159d90e2fc9dbbe1e49f05

        long colorUserId = 443290;
        String orderSN = "1040000141115191511025";
        double orderAmount = 10000;
        Date orderSubmitTime = new Date();
        String investStatus = "1";
		String remark = "";
        long orderPaySn = 11672;

        // {userid=430788,
        // orderSn=1040000141115163511529, orderAmount=50000.00,
        // orderSuccessTime=1416045406, orderPaySn=11583, orderStatus=1, key=3,
        // ts=1416045434, ve=1.0.0, sign=1b2da65464c3e2f2c882636c53ebab28}

		colorOrderFacade.sendOrderStatus(colorUserId, orderSN, orderAmount,
				orderSubmitTime, investStatus, remark, orderPaySn);
		Thread.sleep(100000);
		System.out
				.println("end==============================================================");
	}
}
