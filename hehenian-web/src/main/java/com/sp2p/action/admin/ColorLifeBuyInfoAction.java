package com.sp2p.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hehenian.biz.common.colorlife.ColorLifeBuyService;
import com.shove.web.action.BasePageAction;

@SuppressWarnings("rawtypes")
public class ColorLifeBuyInfoAction extends BasePageAction {
	
	public static Log log = LogFactory.getLog(ColorLifeBuyInfoAction.class);
	
	private static final long serialVersionUID = 1L;
	
	private ColorLifeBuyService colorLifeService;

	public String listColorLifeBuyInfo() throws Exception {
		colorLifeService.listBuyInfo(null);
		System.out.println("ColorLifeBuyInfoAction.listColorLifeBuyInfo()");
		return SUCCESS;
	}
	
	public String listColorLifeBuyVerifierInit() throws Exception {
		colorLifeService.listBuyInfo(null);
		
		return SUCCESS;
	}
}
