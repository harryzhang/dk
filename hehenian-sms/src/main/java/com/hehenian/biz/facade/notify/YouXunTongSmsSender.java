package com.hehenian.biz.facade.notify;

import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.util.StringUtil;

/*
 *
 * 用document 对象封装XML
 * 
 *
 */
public class YouXunTongSmsSender implements ISMSSender {

    private static final Logger   logger      = Logger.getLogger(INotifyService.class);

    private static final String[] xmlElements = { "account", "password", "msgid", "phones", "content", "sign",
            "subcode", "sendtime"            };

    /*
     * ############################参数说明############################ userName =
     * "311101"; // 用户名 password = "311101"; // 密码 phone = "13692177359"; //
     * 要发送的手机号码 content = "hello my duanxi tes<%%%*&^>t1"; // 短信内容 sign = ""; //
     * 短信内容 msgid =""; //成功后会返回 subcode =""; sendtime =""; //定时发送用的
     * url="http://3tong.net/http/sms/Submit"; //无限通使用地址 /*
     * ############################此部分参数需要修改#####################
     * 
     * 
     * /** 发送短信 短信内容不可以超过350个字 消息参数规格： <?xml version='1.0' encoding='UTF-8'?>
     * <message> <account>userName</account>
     * <password>MD5Encode(password)</password> <msgid></msgid>
     * <phones>phone</phones> <content>content</content> <sign>sign</sign>
     * <subcode></subcode> <sendtime></sendtime> </message> 发送短信方法使用document
     * 对象方法封装XML字符串
     */
    public long send(String url, String userName, String password, String phone, String content, String sign,
            String subcode, String sendtime, String msgid) {
    	String[] phones = phone.split(",");
    	long result = -1;
    	for(String tele :phones){
	        Map<String, String> params = new LinkedHashMap<String, String>();
	        String message = docXml(userName, MD5Encode(password), msgid, tele, content, sign, subcode, sendtime);
	        params.put("message", message);
	
	        String resp = doPost(url, params);
	        
	        result= parserResponse(resp);
	        if(-1 == result ){
	        	logger.error("短信内容:"+ content +" 发送回复："+resp);
	        }
    	}
    	return result;
    }

    /**
     * 状态报告 参数格式: <?xml version='1.0' encoding='UTF-8'?> <message>
     * <account>userName</account> <password>MD5Encode(password)</password>
     * <msgid></msgid> <phone></phone> </message>
     */
    private static void getReport(String userName, String password, String msgid, String phone, String url) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        String message = "";
        params.put("message", message);

        String resp = doPost(url, params);
        logger.debug(resp);
    }

    /**
     * 查询余额 参数格式： <?xml version='1.0' encoding='UTF-8'?> <message>
     * <account>userName</account> <password>MD5Encode(password)</password>
     * </message>
     */
    private static void getBalance(String userName, String password, String url) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        String message = "";
        params.put("message", message);

        String resp = doPost(url, params);
        logger.debug(resp);
    }

    /**
     * 
     * 获取上行 参数格式： <?xml version='1.0' encoding='UTF-8'?> <message>
     * <account>userName</account> <password>MD5Encode(password)</password>
     * </message>
     */
    private static void getSms(String userName, String password, String url) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        String message = "";
        params.put("message", message);
        String resp = doPost(url, params);
        logger.debug(resp);
    }

    /**
     * 解析返回的结果 0 发送成功
     * 
     * @param response
     * @return
     */
    private static long parserResponse(String response) {
        try {
            Document doc = DocumentHelper.parseText(response);
            String msgidStr = doc.getRootElement().element("msgid").getText();
            String resultCode = doc.getRootElement().element("result").getText().trim();
            if ("0".equals(resultCode)) {
                long msgid = StringUtil.strToLong(msgidStr, -1);
                return msgid;
            } else {
                return -1;
            }
        } catch (DocumentException e) {
            logger.error(e);
        }
        return -1;

    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     * 
     * @param url
     *            请求的URL地址
     * @param params
     *            请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    private static String doPost(String url, Map<String, String> params) {
        String response = null;
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

        // 设置Post数据
        if (!params.isEmpty()) {
            int i = 0;
            NameValuePair[] data = new NameValuePair[params.size()];
            for (Entry<String, String> entry : params.entrySet()) {
                data[i] = new NameValuePair(entry.getKey(), entry.getValue());
                i++;
            }

            postMethod.setRequestBody(data);

        }
        try {
            client.executeMethod(postMethod);
            if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            postMethod.releaseConnection();
        }
        return response;
    }

    /**
     * 使用document 对象封装XML
     * 
     * @param userName
     * @param pwd
     * @param id
     * @param phone
     * @param contents
     * @param sign
     * @param subcode
     * @param sendtime
     * @return
     */
    private static String docXml(String userName, String pwd, String msgid, String phone, String contents, String sign,
            String subcode, String sendtime) {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");
        Element message = doc.addElement("response");
        Element account = message.addElement("account");
        account.setText(userName);
        Element password = message.addElement("password");
        password.setText(pwd);
        Element msgid1 = message.addElement("msgid");
        msgid1.setText(null == msgid ? "" : msgid);
        Element phones = message.addElement("phones");
        phones.setText(phone);
        Element content = message.addElement("content");
        content.setText(contents);
        Element sign1 = message.addElement("sign");
        sign1.setText(null == sign ? "" : sign);
        Element subcode1 = message.addElement("subcode");
        subcode1.setText(null == subcode ? "" : subcode);
        Element sendtime1 = message.addElement("sendtime");
        sendtime1.setText(null == sendtime ? "" : sendtime);
        return message.asXML();
    }

    /**
     * 使用document 对象封装XML
     * 
     * @param userName
     * @param pwd
     * @param id
     * @param phone
     * @param contents
     * @param sign
     * @param subcode
     * @param sendtime
     * @return
     */
    private static String docXml(Map<String, String> sendMap) {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");
        Element message = doc.addElement("response");
        for (String xmlElement : xmlElements) {
            String val = sendMap.get(xmlElement);
            if (null == val) {
                val = "";
            }
            Element element = message.addElement(xmlElement);
            element.setText(val);
        }
        return message.asXML();
    }

    // MD5加密函数
    private static String MD5Encode(String sourceString) {
        String resultString = null;
        try {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
            logger.error(ex);
        }
        return resultString;
    }

    private static final String byte2hexString(byte[] bytes) {
        StringBuffer bf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                bf.append("0");
            }
            bf.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return bf.toString();
    }

}