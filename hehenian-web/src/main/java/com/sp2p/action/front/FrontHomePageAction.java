package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.FinanceService;
import com.sp2p.service.SelectedService;

/**   
 * @ClassName: FrontMyHomeAction.java
 * @Author: gang.lv
 * @Date: 2013-3-13 下午10:21:30
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的主页控制层
 */
public class FrontHomePageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontHomePageAction.class);
	private static final long serialVersionUID = 1L;

	public String platformInit(){
		return "success";
	}

	public String teamInit(){
		return "success";
	}
	
	public String legalPolicyInit(){
		return "success";
	}
	
	public String aboutUsInit(){
		return "success";
	}
	
	public String downloadInit(){
		return "success";
	}
}
