package com.hehenian.biz.common.notify.dataobject;

public class LoanSMSNotifyDo extends SMSNotifyDo {

	private static final long serialVersionUID = 1276931930236244549L;

	public LoanSMSNotifyDo(String messageContext, String mobile,String messageTemplate) {
        super();
        this.setAsync(true);
        this.setSimpleMessage(messageContext);
        this.recievers = mobile;
        this.messageTemplate = messageTemplate;
    }
}
