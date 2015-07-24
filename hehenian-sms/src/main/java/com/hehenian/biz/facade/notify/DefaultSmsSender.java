package com.hehenian.biz.facade.notify;

import java.util.Date;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.Md5Utils;
import com.hehenian.biz.common.util.StringUtil;

public class DefaultSmsSender implements ISMSSender {

    private static final Logger logger = Logger.getLogger(INotifyService.class);

    @Override
    public long send(String url, String userName, String password, String phone, String content, String sign,
            String subcode, String sendtime, String msgid) {

        Options options = new Options();
        options.setTo(new EndpointReference(url));
        options.setTransportInProtocol(Constants.TRANSPORT_TCP);
        options.setAction("http://tempuri.org/SMSReceive");

        ServiceClient sender;
        try {
            sender = new ServiceClient();
            // sender.engageModule(Constants.MODULE_ADDRESSING);
            sender.setOptions(options);
            OMElement serviceResult = sender.sendReceive(getPayload(userName, password, phone, content));
            return parserResponse(serviceResult.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return -1;
    }

    /**
     * 解析返回的结果 0 发送成功
     * 
     * @param response
     * @return
     */
    private static long parserResponse(String response) {
        try {
            Document doc = DocumentHelper.parseText("<?xml version='1.0' encoding='UTF-8'?>" + response);
            Element e = (Element) (doc.getRootElement().elements().get(0));
            Element diffgram = (Element) e.elements().get(1);
            Element resultElement = diffgram.element("NewDataSet").element("Table1").element("Result");
            String resultCode = resultElement.getTextTrim();
            if ("0".equals(resultCode)) {
                Element idElement = diffgram.element("NewDataSet").element("Table2").element("ID");
                String msgIdStr = idElement.getTextTrim();
                return StringUtil.strToLong(msgIdStr, -1);
            }

            return -1;

        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
            return -1;
        }

    }

    /**
     * 发送格式： <tem:RegCode>szcfb</tem:RegCode> <!--Optional:-->
     * <tem:TimeStamp>2014-08-29 19:46:00</tem:TimeStamp> <!--Optional:-->
     * <tem:Sign>73f332d05c5d56732d82fd561160bd2a</tem:Sign> <!--Optional:-->
     * <tem:Content>test duanxi</tem:Content> <!--Optional:-->
     * <tem:To>13692177359</tem:To>
     * 
     * @return
     */
    private static OMElement getPayload(String userName, String password, String phone, String content) {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "tem");
        OMElement method = fac.createOMElement("SMSReceive", omNs);

        OMElement regCode = fac.createOMElement("RegCode", omNs);
        regCode.addChild(fac.createOMText(regCode, userName));
        method.addChild(regCode);

        Date date = new Date();
        String strDate = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
        OMElement time = fac.createOMElement("TimeStamp", omNs);
        time.addChild(fac.createOMText(time, strDate));
        method.addChild(time);

        StringBuffer signStringBuffer = new StringBuffer();
        signStringBuffer = signStringBuffer.append(userName).append(strDate).append(content).append(phone)
                .append(password);
        String singStr = Md5Utils.MD5(signStringBuffer.toString(), "utf-8");

        OMElement sign = fac.createOMElement("Sign", omNs);
        sign.addChild(fac.createOMText(sign, singStr));
        method.addChild(sign);

        OMElement contentOmelement = fac.createOMElement("Content", omNs);
        contentOmelement.addChild(fac.createOMText(contentOmelement, content));
        method.addChild(contentOmelement);

        OMElement to = fac.createOMElement("To", omNs);
        to.addChild(fac.createOMText(to, phone));
        method.addChild(to);

        return method;
    }

}
