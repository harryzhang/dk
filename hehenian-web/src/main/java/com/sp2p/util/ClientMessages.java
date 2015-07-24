package com.sp2p.util;

import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class ClientMessages {

	// ws访问地址000
	public final static String url = "http://ws.iems.net.cn/GeneralSMS/ws/SmsInterface?wsdl";

	/**
	 * 取得用户信息
	 * 
	 * @param username
	 *            用户名,由机构ID+:+用户登录名组成.如10001:admin
	 * @param password
	 *            用户登录密码
	 * @return
	 * @throws Exception
	 */
	public static String getUserInfo(String username, String password)
			throws Exception {
		String result = "";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(url, "getUserInfo"));

			result = (String) call.invoke(new Object[] { username, password });

			System.out.println("返回结果:" + result);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param username
	 *            用户名,由机构ID+:+用户登录名组成.如10001:admin
	 * @param password
	 *            用户登录密码
	 * @param newPassword
	 *            新密码
	 * @return
	 * @throws Exception
	 */
	public static String setUserInfo(String username, String password,
			String newPassword) throws Exception {
		String result = "";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(url, "setUserInfo"));

			result = (String) call.invoke(new Object[] { username, password,
					newPassword });

			System.out.println("返回结果:" + result);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}

	/**
	 * 发送短信
	 * 
	 * @param username
	 *            用户名,由机构ID+:+用户登录名组成.如10001:admin
	 * @param password
	 *            用户登录密码
	 * @param from
	 *            发送手机号码
	 * @param to接收手机号码
	 *            ,支持多个.号码间用,号分开.最大一次不能超过100个号码
	 * @param text
	 *            短信内容
	 * @param presendTime
	 *            短信发送时间,可为空.默认为系统当前时间
	 * @param isVoice
	 *            是否语音短信是否语音(0表示普通短信,1表示语音短信)|重听次数|重拨次数|是否回复(0表示不回复,1表示回复)如:isVoice
	 *            =”1|1|2|0” 即:语音短信,重听次数1,重拨次数2,不回复
	 * @return
	 * @throws Exception
	 */
	public static String clusterSend(String username, String password,
			String from, String to, String text, String presendTime,
			String isVoice) throws Exception {
		String result = "";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(url, "clusterSend"));

			result = (String) call.invoke(new Object[] { username, password,
					from, to, text, presendTime, isVoice });

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}

	/**
	 * 接收上行短信
	 * 
	 * @param username
	 *            用户名,由机构ID+:+用户登录名组成.如10001:admin
	 * @param password
	 *            用户登录密码
	 * @param lastId
	 *            收取大于该短信ID的短信，全部收取则送入0
	 * @return
	 * @throws Exception
	 */
	public static String getMoMsg(String username, String password,
			String lastId) throws Exception {
		String result = "";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(url, "getMoMsg"));

			result = (String) call.invoke(new Object[] { username, password,
					lastId });

			System.out.println("返回结果:" + result);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}

	/**
	 * 取得发送统计信息
	 * 
	 * @param username
	 *            用户名,由机构ID+:+用户登录名组成.如10001:admin
	 * @param password
	 *            用户登录密码
	 * @param startDate
	 *            统计开始时间
	 * @param endDate
	 *            统计结束时间
	 * @return
	 * @throws Exception
	 */
	public static String getSendCount(String username, String password,
			String startDate, String endDate) throws Exception {
		String result = "";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(url, "getSendCount"));

			result = (String) call.invoke(new Object[] { username, password,
					startDate, endDate });

			System.out.println("返回结果:" + result);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}

	public static void main(String[] args) {
		String result = "";
		try {
			result = ClientMessages.clusterSend("67229:admin", "hyn12345",
					"", "13428938888", "这是神马？", "", "0");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println(result);
	}
}
