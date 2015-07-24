package com.hehenian.biz.service.trade;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.trade.IRepaymentService;
import com.hehenian.biz.common.trade.RepayOperationType;
import com.hehenian.biz.service.BaseTestCase;

public class IRepaymentServiceTest extends BaseTestCase {
	
	@Autowired
	private IRepaymentService repaymentService;
	
	@Test
	public void testDoRepay(){
		
		long borrowId = 4;
		long payId = 25;
		String outCustId = "6000060000329210";
		long userId = 100012;
		String username="testhk";
		String pwd = "";
		double needSum = 1230.00;
		String url ="http://localhost:8080/hehenian-web";
		
		repaymentService.doRepay(RepayOperationType.NORMAL_REPAY.name(), 
				                 borrowId, 
				                 payId, 
				                 outCustId, 
				                 userId, 
				                 username, 
				                 pwd, 
				                 url);
	}
	
	//代偿
	@Test
	public void testDoCompRepay(){
		
		long borrowId = 4;
		long payId = 23;
		String outCustId = "6000060000329210";
        long userId = 100012;
        String username="testhk";
        String pwd = "";
		double needSum = 1230.00;
		String url ="http://localhost:8080/hehenian-web";
		
		repaymentService.doRepay(RepayOperationType.COMP_REPAY.name(), 
				                 borrowId, 
				                 payId, 
				                 outCustId, 
				                 userId, 
				                 username, 
				                 pwd, 
				                 url);
	}
	
	//提前结清
	@Test
	public void testDoPreSettleRepay(){
		
	    long borrowId = 6;
        long payId = 68;
        String outCustId = "6000060000329210";
        long userId = 100012;
        String pwd = "";
        String url ="http://localhost:8080/hehenian-web";
		
		repaymentService.doRepay(RepayOperationType.PRE_SETTLE_REPAY.name(), 
				                 borrowId, 
				                 payId, 
				                 outCustId, 
				                 userId, 
				                 "", 
				                 pwd, 
				                 url);
	}

}
