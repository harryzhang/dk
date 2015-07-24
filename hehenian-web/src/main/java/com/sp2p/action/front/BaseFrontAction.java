package com.sp2p.action.front;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.entity.User;
import com.sp2p.system.exception.FrontHelpMessageException;
import com.sp2p.util.WebUtil;
import com.shove.web.action.BasePageAction;

public class BaseFrontAction extends BasePageAction {

	
	private static final long serialVersionUID = 1L;
	//PrintWriter打印输出
	private PrintWriter out;
	

	/**
	 * 跳转拦截
	 * 
	 * @param title
	 * @param msg
	 * @param url
	 * @throws FrontHelpMessageException
	 */
	public void createHelpMessage(String title, String msg, String url)
			throws FrontHelpMessageException {
		/* helpMessage.setTitle("用户不存在"); */
		helpMessage.setMsg(new String[] { "返回首页" });
		helpMessage.setUrl(new String[] { "index" });
		helpMessage.setTitle(title);
		/*
		 * helpMessage.setMsg(new String[]{msg}); helpMessage.setUrl(new
		 * String[]{url});
		 */
		throw new FrontHelpMessageException();
	}

	public PrintWriter getOut() throws Exception {
		response().setCharacterEncoding("UTF-8");
		response().setContentType("text/html; charset=UTF-8");
		out = response().getWriter();
		return out;
	}
	
	protected long getUserId() {
        AccountUserDo user = (AccountUserDo)session().getAttribute(IConstants.SESSION_USER);
		if(user != null){
			return user.getId();
		}
		return -1;
	}
	protected AccountUserDo getUser() {
        AccountUserDo user = (AccountUserDo)session().getAttribute(IConstants.SESSION_USER);
		if(user == null){
			user = new AccountUserDo();
		}
		return user;
	}
	

	protected long getAdminId() {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long id = -1;
		if (admin != null) {
			id = admin.getId();
		}
		return id;
	}
	
	
	public String returnParentUrl(String function,String url) throws Exception{
		getOut().print("<script type='text/javascript'>"+function+"parent.location.href='" + request().getContextPath()
    						+ "/"+url+";</script>");
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPlatformCost(){
		//获取平台收费
		Map<String,Object> platformCostMap = (Map<String, Object>) application().getAttribute(IAmountConstants.FEE_APPLICATION);
        if(platformCostMap == null)
        	platformCostMap = new HashMap<String, Object>();
		return platformCostMap;
	}
	
	public String getBasePath(){
		return WebUtil.getWebPath();
	}
	
	public String getPath() {
		int port = request().getServerPort();
		String portStr = "";
		if(port != 80){
			portStr = ":"+port; 
		}
		String path = request().getScheme() + "://" + request().getServerName()
		+ portStr + request().getContextPath()
		+ "/";
		return path;
	}
}
