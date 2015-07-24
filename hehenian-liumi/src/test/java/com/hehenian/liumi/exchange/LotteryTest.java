package com.hehenian.liumi.exchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.junit.Test;


public class LotteryTest {

//	@Test
	public void testMobile() {
		Lottery l = new Lottery();
		Map<Integer,Integer> map = l.lookup("18682291004");
		assertNotNull(map);
		assertEquals(1,map.size());
		
		map = l.lookup("13714424246");
		assertNotNull(map);
		assertEquals(2,map.size());

		map = l.lookup("18988888888");
		assertNotNull(map);
		assertEquals(4,map.size());
		
	}
	
	
//	@Test
	public void testLottery() throws KeyManagementException, NoSuchAlgorithmException, IOException{
		Lottery l = new Lottery();
		
//		for (int i = 0; i < 10; i++) {
//			int r = l.lottery("13314424226");
//			System.out.println(Lottery.Flag+r);
//		}

//		for (int i = 0; i < 10; i++) {
			int r1 = l.lottery("18345454545"); 
			LiumiClient.placeOrder("18345454545",Lottery.Flag+r1,true);
			System.out.println(Lottery.Flag+r1);
//		}

//		for (int i = 0; i < 10; i++) {
//			int r2 = l.lottery("18614424226");
//			System.out.println(Lottery.Flag+r2);
//		}
	}
}
