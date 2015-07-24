/**  
 * @Project: hehenian-sms
 * @Package com.hehenian.biz.facade.notify
 * @Title: MonternetSmsSender.java
 * @Description: TODO
 *
 * @author: zhangjhmf
 * @date 2015-6-15 下午3:10:11
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.facade.notify;

import org.apache.log4j.Logger;

import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.facade.notify.montnets.mwgate.common.ISms;
import com.hehenian.biz.facade.notify.montnets.mwgate.common.SmsTool;
import com.hehenian.biz.facade.notify.montnets.mwgate.common.StaticValue;
import com.hehenian.biz.facade.notify.montnets.mwgate.common.ValidateParamTool;
import com.hehenian.biz.facade.notify.montnets.mwgate.httppost.CHttpPost;

public class MonternetSmsSender implements ISMSSender {
	private static final Logger   logger      = Logger.getLogger(INotifyService.class);
	public long send(String url, String userName, String password,
			String phone, String content, String sign, String subcode,
			String sendtime, String msgid) {
		long result = -1;
		if (phone != null && !"".equals(phone)) {
			String[] phones = phone.split(",");
			if (phones.length > 0 && phones.length <= 100) {
				for (int i = 0; i < phones.length; i++) {
					try {
						// 如果输入的对象号码不合法则要重新输入
						if ("".equals(phones[i]) || phones[i].length() != 11
								|| !SmsTool.isUnSignDigit(phones[i])) {
							logger.error("手机号码输入不合法:" + phones[i]);
							continue;
						}
					} catch (Exception e) {
						logger.error("手机号码输入不合法:" + phones[i]);
						e.printStackTrace();
					}
				}
			} else {
				logger.error("手机号码个数超过100个");
				return result;
			}
		}
		if (ValidateParamTool.validateMessage(content)) {
			logger.info(content);
		} else if (content == null || "".equals(content)) {
			logger.error("短信长度为0");
			return result;
		} else {
			logger.error("短信长度超过350个汉字");
			return result;
		}
		userName = StaticValue.UserId;
		password = StaticValue.Pwd;
		ISms sms = new CHttpPost();
		StringBuffer strPtMsgId = new StringBuffer("");
		// 短信息发送接口（相同内容群发，可自定义流水号）POST请求,httpClient为空，则是短连接,httpClient不为空，则是长连接。
		int ret = sms.SendSms(strPtMsgId,userName,password,phone, content, "*", "0", false, null);
		if (ret == 0) {
			result = ret;
			logger.info("发送成功：" +strPtMsgId.toString());
			return result;
		} else {
			logger.info("发送失败：" +strPtMsgId.toString());
			return result;
		}
	}

	public static void main(String[] args) {//亲,每月定投时间快到了，请确保在2015-06-19号之前账户有足够资金，再忙也不要耽误您赚钱哟~。
		new MonternetSmsSender().send(null, null, null, "13728679372", "亲，恭喜您的加薪宝产品本月已扣款成功。还有更多理财产品有空来看看吧~。如有疑问，请致电4008-303-737。", null, null, null, null);
		new MonternetSmsSender().send(null, null, null, "13728679372", "亲，您购买的加薪宝本月扣款失败了~请确保账户金额足够，不然要耽误您赚钱了。下个月也要记得按时存款哦。如有疑问，请致电4008-303-737。", null, null, null, null);
		new MonternetSmsSender().send(null, null, null, "13728679372", "亲,每月定投时间快到了，请确保在2015-06-19号之前账户有足够资金，再忙也不要耽误您赚钱哟~。如有疑问，请致电4008-303-737。", null, null, null, null);
		new MonternetSmsSender().send(null, null, null, "13728679372", "亲，经过再次努力，你的加薪宝本月扣款终于成功了，请下月注意不要逾期哟~。如有疑问，请致电4008-303-737。", null, null, null, null);
		new MonternetSmsSender().send(null, null, null, "13728679372", "亲亲，您的加薪宝本月扣款再次失败了，订单现在逾期中，已影响到了您的收益及信用，请尽快充值。如有疑问，请致电4008-303-737。", null, null, null, null);
}
}
