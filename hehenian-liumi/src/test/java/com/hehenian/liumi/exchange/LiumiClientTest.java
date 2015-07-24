package com.hehenian.liumi.exchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class LiumiClientTest {

	
//	@Test
	public void testToken() throws KeyManagementException, NoSuchAlgorithmException{
		Result r = LiumiClient.getToken(); 
		assertNotNull(r);
		assertEquals("000", r.getCode());
		assertNotNull(r.getData().getToken());
	}
	 
	@Test
	public void testPlaceOrder() throws KeyManagementException, NoSuchAlgorithmException, IOException{
		String mobile="18934567656";
		//抢过红包的用户不能再次参与抢红包 
		String mo = PropertiesUtils.readValue("order.properties", mobile);
		if(mo == null){
			int s = Lottery.lottery(mobile);
			Result r = LiumiClient.placeOrder(mobile,"YD10;DX10;LT50", true);//手机号，流量规格
//			Result r = LiumiClient.placeOrder(mobile,Lottery.Flag+s);//手机号，流量规格
			System.out.println(r.getData().getOrderNO());
			assertNotNull(r);
			assertEquals("000", r.getCode());
			assertNotNull(r.getData());
		}
		System.out.println("123");  
	}
	
//	@Test
	public void testgetOrderInfo() throws KeyManagementException, NoSuchAlgorithmException, IOException, ClassNotFoundException{
		Result r = LiumiClient.getOrderInfo("13812345677");
		System.out.println(r.getData().getOrderNo());
		assertNotNull(r);
		assertEquals("000", r.getCode());
		assertNotNull(r.getData());
	}
	
	//@Test
	public void testProperty() { 
		PropertiesUtils.writeProperties(Thread.currentThread().getClass().getResource("/").getPath() + "/order.properties", "sdfsdfs", "sdfsdf");
//		String v = PropertiesUtils.readValue(Thread.currentThread().getContextClassLoader().getResource("/").getPath() + "/order.properties", "1234463463");
//		System.out.println(v);
		
		 
	}
	
}
