package com.sp2p.action.front;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.web.common.contant.WebConstants;

public class CfAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(CfAction.class);
	
	
	public String bjbz() {
		return SUCCESS;
	}
	public String faq() {
		String liumi = request("liumi");
		session().setAttribute("liumi", liumi);
		return SUCCESS;
	}
	public String czzy() {
		return SUCCESS;
	}
	public String home() {
        AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user==null) {
			return "login";
		}
		return SUCCESS;
	}
	public String cfWeb() {
		session().setAttribute("platform", "colorlifeweb");
        session().setAttribute("sourcefrom", WebConstants.SOURCEFROM_COLOURLIFE);
		return SUCCESS;
	}
	public String cfApp() {
		session().setAttribute("platform", "colorlifeapp");
        session().setAttribute("sourcefrom", WebConstants.SOURCEFROM_COLOURLIFE_APP);
        session().setAttribute("appstyle","cf");
		return SUCCESS;
	}
	public String heartbeat() {
		return null;
	}
	
	public String appComm() {
		session().setAttribute("platform", "appcomm");
        Object via = request("via");
        if (via!=null&&StringUtils.isNotBlank(via.toString())){
            if (session("appvia")!=null){
                via = session("appvia").toString();
            }
        }else{
            via = session("appvia");
        }
        if ("jfq".equals(via)){
            via = "11";
        }
		session().setAttribute("appvia",via);
        if (via!=null&&StringUtils.isNotBlank(via.toString())){
            session().setAttribute("sourcefrom", via);
        }

        //设置手机端展示风格
//        int appstyle = Convert.strToInt(request("appstyle"),0);
        String appstyle = request("appstyle");
        if (StringUtils.isNotBlank(appstyle)){
            session().setAttribute("appstyle",appstyle);
        }
        if (session("user")!=null){
           return "index";
        }
		return SUCCESS;
	}
}
















