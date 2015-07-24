package com.hehenian.liumi.exchange;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class HttpsUtilTest {

//	@Test
	public void test() throws KeyManagementException, NoSuchAlgorithmException, IOException{
		String p = "{\"appkey\":\"caifubao\",\"appsecret\":\"1f2d5cfa83a08b4b54ec3e714fe44228\",\"sign\":\"e032a98fe27ef97176934d0be5d88a74fcc00e13\"}";
		byte[] bytes=HttpsUtil.post("https://ll.wenanji.com/server/getToken", p);
		String result = new String(bytes);
		assertNotNull(result);
	}
}
