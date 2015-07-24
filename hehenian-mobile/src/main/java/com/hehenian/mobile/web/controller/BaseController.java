/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.controller
 * @Title: BaseController.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-3-26 上午11:21:59
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.mobile.common.utils.CommonUtils;

public class BaseController {
	@Autowired
	protected HttpServletRequest request;

	protected AccountUserDo getAccountUser() {
		AccountUserDo aud = CommonUtils.getAccountUserDo();
		if (aud == null) {
			aud = new AccountUserDo();
		}
		return aud;
	}
	
	protected PersonDo getPerson() {
		PersonDo pd = CommonUtils.getPersonDo();
		if (pd == null) {
			pd = new PersonDo();
		}
		return pd;
	}
	
	protected long getUserId() {
		return CommonUtils.getLoginId() != null ? CommonUtils.getLoginId() : -1;
	}
	
	protected Integer getCurrentUserId(){
		return CommonUtils.getLoginId() != null ? CommonUtils.getLoginId().intValue() : -1;
	}
	
	protected String getSessionStrAttr(String attrName) {
		try {
			Object obj = request.getSession().getAttribute(attrName);
	        if (obj!=null){
	            return obj.toString();
	        }
	        
	        return null;
		} catch (Exception e) {
			return null;
		}
	}
	
    public String getSourcFrom(){
        HttpSession session = request.getSession();
        Object sourceFromObj = session.getAttribute("sourcefrom");
        if (sourceFromObj!=null){
            return sourceFromObj.toString();
        }else {
            return "1";
        }
    }
    
    protected int getSessionIntAttr(String attrName,int defaultValue){
        try {
            return (Integer)request.getSession().getAttribute(attrName);
        }catch (Exception e){
            return defaultValue;
        }
    }
    
	public static String FilteSqlInfusion(String input) {
		if ((input == null) || (input.trim() == "")) {
			return "";
		}
		if (!StringUtils.isNumeric(input)) {
			return input.replace("'", "’").replace("update", "ｕｐｄａｔｅ").replace(
					"drop", "ｄｒｏｐ").replace("delete", "ｄｅｌｅｔｅ").replace("exec",
					"ｅｘｅｃ").replace("create", "ｃｒｅａｔｅ").replace("execute",
					"ｅｘｅｃｕｔｅ").replace("where", "ｗｈｅｒｅ").replace("truncate",
					"ｔｒｕｎｃａｔｅ").replace("insert", "ｉｎｓｅｒｔ");
		} else {
			return input;
		}
	}
	
	public String getPath() {
        int port = request.getServerPort();
        String portStr = "";
        if(port != 80){
            portStr = ":"+port; 
        }
        String path = request.getScheme() + "://" + request.getServerName()
        + portStr + request.getContextPath()
        + "/";
        return path;
    }
	
	
}
