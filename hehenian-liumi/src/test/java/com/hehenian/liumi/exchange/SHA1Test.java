package com.hehenian.liumi.exchange;

import static org.junit.Assert.*;

import org.junit.Test;



public class SHA1Test { 

	
	@Test
	public void test(){
		String result = SHA1.getSHA1("appkeycaifubaoappsecret1f2d5cfa83a08b4b54ec3e714fe44228");
		assertNotNull(result);
		assertEquals("e032a98fe27ef97176934d0be5d88a74fcc00e13", result);
		
		result = SHA1.getSHA1("appkeycaifubaoappsecret1f2d5cfa83a08b4b54ec3e714fe44228appverHttpextnofixtimemobile13714424246postpackageYD30;DX50;LT20tokenoEOulVhmkND8rDD4mlpLFIoKkwYCuqqWoDBJmhVCtMHq0rU17iOTMg2MhwolwRYQ");
		assertNotNull(result);
		assertEquals("d085697b5a3cba51829589205d7db90c20008046", result);
	}
}
