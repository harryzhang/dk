package com.hehenian.biz.facade.notify;

public interface ISMSSender {

    /**
     * 
     * @param url
     *            发送平台url
     * @param userName
     *            发送平台的用户名
     * @param password
     *            发送平台的密码
     * @param phone
     *            接收的电话
     * @param content
     *            发送的短信内容
     * @param sign
     * @param subcode
     * @param sendtime
     * @param msgid
     *            发送成功返回发送的消息ID
     * @return 返回 -1 代表调用失败， 否则代表 发送成功后的消息ID
     * @author: zhangyunhmf
     * @date: 2015年1月7日下午3:14:36
     */
    public long send(String url, String userName, String password, String phone, String content, String sign,
            String subcode, String sendtime, String msgid);

}
