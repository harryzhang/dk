package com.hehenian.biz.service.notify.impl;

import com.hehenian.biz.common.notify.dataobject.SmsType;
import com.hehenian.biz.facade.notify.DefaultSmsSender;
import com.hehenian.biz.facade.notify.ISMSSender;
import com.hehenian.biz.facade.notify.MonternetSmsSender;
import com.hehenian.biz.facade.notify.YouXunTongSmsSender;

public class SMSSenderFactory {
	
	public static ISMSSender getSMSSender(String supplier){
		if("youxuntong".equals(supplier)){//优讯通
			return new YouXunTongSmsSender();
		}else if(SmsType.MONTERNET.name().equals(supplier)){
			return new MonternetSmsSender();
		}else{
			return new DefaultSmsSender();
		}
	}

}
