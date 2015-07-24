package com.sp2p.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.web.action.BasePageAction;
import com.sp2p.service.admin.SendmsgService;

@SuppressWarnings({ "serial", "unchecked" })
public class SendmsgAction extends BasePageAction{
	public static Log log = LogFactory.getLog(SendmsgAction.class);
	@SuppressWarnings("unused")
	private SendmsgService sendmsgService;
	public void setSendmsgService(SendmsgService sendmsgService) {
		this.sendmsgService = sendmsgService;
	}
	
}
