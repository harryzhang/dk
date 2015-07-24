/**  
 * @Project: com.hehenian.liumi.exchange
 * @Package com.hehenian.wechat.exchange
 * @Title: WechatTest.java
 * @Description: TODO
 *
 * @author: duanhrmf
 * @date 2015年3月18日 下午5:40:49
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.wechat.exchange;
import static org.junit.Assert.assertNotNull;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class WechatTest {

//	@Test
	public void testToken() throws KeyManagementException, NoSuchAlgorithmException{
	 WechatResult token = WechatUtils.getAccessToken("wxeae7b1b55b3a43b0", "68753837e1095566193d526bf0cf867f");
	 System.out.println(token.getAccess_token());
	 System.out.println(token.getExpires_in());
	 assertNotNull(token);
	}
}
