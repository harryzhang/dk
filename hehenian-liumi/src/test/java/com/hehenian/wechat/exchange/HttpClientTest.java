/**  
 * @Project: com.hehenian.liumi.exchange
 * @Package com.hehenian.wechat.exchange
 * @Title: HttpClientTest.java
 * @Description: TODO
 *
 * @author: duanhrmf
 * @date 2015年3月19日 上午9:59:24
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.wechat.exchange;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class HttpClientTest {

//	@Test
	public void testHttp(){
		
		WechatResult r = WechatUtils.httpRequest("https://api.weixin.qq.com/cgi-bin/user/info?", "GET", 
				"access_token=ilcIMU0FCvJNJGUH_I3v7UBhdsPLkA2zDubxRDnndSm4ajGwiQzbtdSgqrBf-ZwFVmIff1xWUhAt-j2V8agXR00gnzNoOproF20_cYK7h0E&openid=OPENID&lang=zh_CN");
		
		 assertNotNull(r);
		 System.out.println(r.getErrcode());
	}
}
